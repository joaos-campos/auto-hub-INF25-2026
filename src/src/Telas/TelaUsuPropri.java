
package Telas;

import AcessoDB.ModuloDbConnect;
import AcessoDB.ModuloDbDML;
import AutoHubUtil.DateUtil;
import AutoHubUtil.ValidarCpfUtil;
import Telas.TelaLoginProprietario.SessaoUsuario;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class TelaUsuPropri extends javax.swing.JFrame {

    Connection conexao = null;
    private static int clicked = 0;
    private static boolean shouldSave = false;
   
    private final int idAtual = SessaoUsuario.getIdUsuarioLogado();
    private String _txtNome;
    private LocalDate _txtDataNasc;
    private String _txtCPF;
    private String _txtEmail;
    private String _txtTelefone;
    private String _txtSenha;
    private String _txtNomeUsu;
    
    public TelaUsuPropri() throws ParseException 
    {
        if (ValidarUsuarioLogado())
        {
            initComponents();
            PostInitComponents();
            DefineBehaviourScreenTextFields();
            PopularCamposSelect();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Usuário não logado! Você será redirecionado para a tela de login!");
            TelaLoginProprietario tlLogProp = new TelaLoginProprietario();
            tlLogProp.setLocationRelativeTo(null);
            tlLogProp.setVisible(true);
            this.dispose();
        }
    }
    
    private static void ActivateOnEnter(JButton button) 
    {
        InputMap im = button.getInputMap(JComponent.WHEN_FOCUSED);
        im.put(KeyStroke.getKeyStroke("ENTER"), "pressed");
        im.put(KeyStroke.getKeyStroke("released ENTER"), "released");
    }
    
    private boolean ValidarUsuarioLogado()
    {
        return idAtual > 0;
    }
    
    private void PostInitComponents() throws ParseException
    {
        this.getContentPane().setBackground(new Color(198, 200, 200));
        this.setSize(575, 650);
        
        SetEditable(false);
        
        ActivateOnEnter(btnEditar);
        ActivateOnEnter(btnExcluir);
        ActivateOnEnter(btnVoltar);
        
        try
        {
            MaskFormatter mask = new MaskFormatter("###.###.###-##");
            mask.setValueContainsLiteralCharacters(false);   // value = 11 raw digits
            mask.setPlaceholderCharacter('_');
            TxtCPF.setFormatterFactory(new DefaultFormatterFactory(mask));
            TxtCPF.setFocusLostBehavior(JFormattedTextField.PERSIST);
        } 
        catch (ParseException e) 
        {
            e.printStackTrace();
        }
        
        TxtSenha.setEchoChar('*');
        btnEditar.requestFocus();
    }
    
    private void UpdateLblPerfilText(String text)
    {
        lblPerfil.setVisible(true);
        lblPerfil.setText("Perfil: " + text);
        lblPerfil.getParent().revalidate();
        lblPerfil.getParent().repaint();
    }
    
    private void SetEditable(boolean enable)
    {
        this.TxtNomeUsu.setEditable(enable);
        this.TxtNome.setEditable(enable);
        this.TxtDataNasc.setEditable(enable);
        this.TxtCPF.setEditable(enable);
        this.TxtEmail.setEditable(enable);
        this.TxtTelefone.setEditable(enable);
        this.TxtSenha.setEditable(enable);
    }
    
    private void DefineBehaviourScreenTextFields()
    {
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtCPF"> 
        // =================================================================================
        /*
        TxtCPF.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        String TxtCPFPlaceholder = "000.000.000-00";
        
        TxtCPF.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtCPF.getText().equals(TxtCPFPlaceholder)) 
                {
                    SwingUtilities.invokeLater(() -> 
                    {
                        TxtCPF.setCaretPosition(0);
                    });
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtCPF.getText().isEmpty() || TxtCPF.getText().equals(TxtCPFPlaceholder)) 
                {
                    TxtCPF.setText(TxtCPFPlaceholder);
                }
            }
        });
        */
        // =================================================================================
         // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtDataNasc">
        // =================================================================================
        TxtDataNasc.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        String TxtDataNascPlaceholder = "00/00/0000";
        
        TxtDataNasc.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtDataNasc.getText().equals(TxtDataNascPlaceholder)) 
                {
                    SwingUtilities.invokeLater(() -> 
                    {
                        TxtDataNasc.setCaretPosition(0);
                    });
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtDataNasc.getText().isEmpty() || TxtDataNasc.getText().equals(TxtDataNascPlaceholder)) 
                {
                    TxtDataNasc.setText(TxtDataNascPlaceholder);
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtNome"> 
        // =================================================================================
        String TxtNomePlaceholder = "Nome completo";
        
        TxtNome.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtNome.getText().equals(TxtNomePlaceholder)) 
                {
                    TxtNome.setText("");
                    TxtNome.setCaretPosition(0);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtNome.getText().isEmpty() || TxtNome.getText().equals(TxtNomePlaceholder)) 
                {
                    TxtNome.setText(TxtNomePlaceholder);
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtEmail"> 
        // =================================================================================
        String TxtEmailPlaceholder = "seu_nome@site.com.br";
        
        TxtEmail.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtEmail.getText().equals(TxtEmailPlaceholder)) 
                {
                    TxtEmail.setText("");
                    TxtEmail.setCaretPosition(0);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtEmail.getText().isEmpty() || TxtEmail.getText().equals(TxtEmailPlaceholder)) 
                {
                    TxtEmail.setText(TxtEmailPlaceholder);
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtTelefone"> 
        // =================================================================================
        String TxtTelefonePlaceholder = "(00)0 0000-0000";
        
        TxtTelefone.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        TxtTelefone.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtTelefone.getText().equals(TxtTelefonePlaceholder)) 
                {
                    TxtTelefone.setCaretPosition(0);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtTelefone.getText().isEmpty() || TxtTelefone.getText().equals(TxtTelefonePlaceholder)) 
                {
                    TxtTelefone.setText(TxtTelefonePlaceholder);
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtNomeUsu"> 
        // =================================================================================
        String TxtNomeUsuPlaceholder = "user_01";
        
        TxtNomeUsu.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtNomeUsu.getText().equals(TxtNomeUsuPlaceholder)) 
                {
                    TxtNomeUsu.setText("");
                    TxtNomeUsu.setCaretPosition(0);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtNomeUsu.getText().isEmpty() || TxtNomeUsu.getText().equals(TxtNomeUsuPlaceholder)) 
                {
                    TxtNomeUsu.setText(TxtNomeUsuPlaceholder);
                }
            }
        });
                // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtSenha"> 
        // =================================================================================        
        String TxtSenhaPlaceholder = "Digite sua senha";
        
        TxtSenha.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (String.valueOf(TxtSenha.getPassword()).equals(TxtSenhaPlaceholder)) 
                {
                    TxtSenha.setText("");
                    TxtSenha.setEchoChar('*'); // Restaura o caractere de senha padrão
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (String.valueOf(TxtSenha.getPassword()).isEmpty() || String.valueOf(TxtSenha.getPassword()).equals(TxtSenhaPlaceholder)) 
                {
                    TxtSenha.setText(TxtSenhaPlaceholder);
                    TxtSenha.setEchoChar((char) 0); // Remove os asteriscos para mostrar o placeholder
                }
            }
        });
        // =================================================================================
        // </editor-fold>
    }
    
    private boolean CamposUpdate_SaoValidos()
    {
        boolean[] validacoes = new boolean[7];
        
        for (int i = 0; i < validacoes.length; i++)
        {
            validacoes[i] = true;
            //System.out.println(validacoes[i]);
        }
        
        if ((TxtNome.getText().isEmpty() == true) || (TxtNome.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um nome válido!");
            validacoes[0] = false;
        }
        
        if ((TxtDataNasc.getText().isEmpty() == true) || (TxtDataNasc.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite uma data de nascimento válida!");
            validacoes[1] = false;
        }
        
        if ((TxtCPF.getText().isEmpty() == true) || (TxtCPF.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um CPF válido!");
            validacoes[2] = false;
        }
        else
        {
            if (ValidarCpfUtil.ValidarCpf(TxtCPF.getValue().toString()) == false)
            {
                JOptionPane.showMessageDialog(null, "Digite um CPF válido!");
                validacoes[2] = false;
            }
        }
        
        if ((TxtEmail.getText().isEmpty() == true) || (TxtEmail.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um e-mail válido!");
            validacoes[3] = false;
        }
        
        if ((TxtTelefone.getText().isEmpty() == true) || (TxtTelefone.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um telefone válido!");
            validacoes[4] = false;
        }
        
        if ((TxtNomeUsu.getText().isEmpty() == true) || (TxtNomeUsu.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um nome de usuário válido!");
            validacoes[5] = false;
        }
        
        if (((TxtSenha.getText().isEmpty() == true) || (TxtSenha.getText().isBlank() == true)))
        {
            JOptionPane.showMessageDialog(null, "Digite uma senha válida!");
            validacoes[6] = false;
        }
        
        boolean is_valid = true;
        
        for (int i = 0; i < validacoes.length; i++)
        {
            if (validacoes[i] == false)
            {
                //System.out.println(validacoes[i]);
                is_valid = false;
            }
        }
        
        return is_valid;
    }
    
    private void PopularCamposSelect()
    {
        conexao = ModuloDbConnect.connector();
        
        ModuloDbDML dbDML = new ModuloDbDML(conexao);
        
        String sql = "SELECT nm_proprietario, dt_nascimento, nr_cpf, ds_email, nr_telefone, ds_senha, ds_nome_usuario FROM t_aah_proprietario WHERE id_proprietario = ?";
        
        try 
        {  
            ResultSet rs = dbDML.consultarResultSet(sql, idAtual); 
            
            if (rs.next()) 
            {
                UpdateLblPerfilText(rs.getString("nm_proprietario"));
                TxtNome.setText(rs.getString("nm_proprietario"));
                LocalDate dtNascPropi = rs.getObject("dt_nascimento", LocalDate.class);
                TxtDataNasc.setValue(DateUtil.toDisplay(dtNascPropi));
                String rawCPF = rs.getString("nr_cpf");
                String cleaned = (rawCPF == null) ? "" : rawCPF.replaceAll("\\D", "");
                TxtCPF.setValue(cleaned);
                TxtEmail.setText(rs.getString("ds_email"));
                TxtTelefone.setText(rs.getString("nr_telefone"));
                TxtNomeUsu.setText(rs.getString("ds_nome_usuario"));
                TxtSenha.setText(rs.getString("ds_senha"));
                System.out.println(rs.getString("ds_senha"));
            } 
            else 
            {
                javax.swing.JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
            }
        } 
        catch (Exception e) 
        {
             javax.swing.JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
             e.printStackTrace();               
        } 
    }
    
    private String RetornarNmProp(int id)
    {
        String nmProp = "";
        
        conexao = ModuloDbConnect.connector();
        
        ModuloDbDML dbDML = new ModuloDbDML(conexao);
        
        String sql = "SELECT nm_proprietario from t_aah_proprietario WHERE id_proprietario = ?";
        
        try 
        {  
            ResultSet rs = dbDML.consultarResultSet(sql, id); 
            
            if (rs.next()) 
            {
                nmProp = rs.getString("nm_proprietario");
            } 
            else 
            {
                javax.swing.JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
            }
        } 
        catch (Exception e) 
        {
             javax.swing.JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
             e.printStackTrace();               
        }
        
        return nmProp;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        TxtNomeUsu = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        TxtNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TxtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        lblPerfil = new javax.swing.JLabel();
        TxtCPF = new javax.swing.JFormattedTextField();
        TxtDataNasc = new javax.swing.JFormattedTextField();
        TxtTelefone = new javax.swing.JFormattedTextField();
        lblSenha = new javax.swing.JLabel();
        TxtSenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela de perfil");
        setResizable(false);

        jLabel1.setText("Nome de usuário:");

        TxtNomeUsu.setText("NmUsu");
        TxtNomeUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNomeUsuActionPerformed(evt);
            }
        });

        jLabel2.setText("Nome completo:");

        TxtNome.setText("NmCompleto");
        TxtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNomeActionPerformed(evt);
            }
        });

        jLabel3.setText("Data de nascimento:");

        jLabel4.setText("CPF:");

        jLabel5.setText("E-mail:");

        TxtEmail.setText("DsEmail");
        TxtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtEmailActionPerformed(evt);
            }
        });

        jLabel6.setText("Telefone:");

        btnEditar.setBackground(new java.awt.Color(255, 212, 59));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(33, 40, 68));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(255, 212, 59));
        btnExcluir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExcluir.setForeground(new java.awt.Color(33, 40, 68));
        btnExcluir.setText("Excluir conta");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnVoltar.setBackground(new java.awt.Color(32, 32, 63));
        btnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnVoltar.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        lblPerfil.setBackground(new java.awt.Color(32, 32, 63));
        lblPerfil.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblPerfil.setForeground(new java.awt.Color(247, 250, 255));
        lblPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPerfil.setText("Perfil: ");
        lblPerfil.setOpaque(true);

        try {
            TxtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCPFActionPerformed(evt);
            }
        });

        try {
            TxtDataNasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtDataNasc.setToolTipText("");
        TxtDataNasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtDataNascActionPerformed(evt);
            }
        });

        try {
            TxtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTelefoneActionPerformed(evt);
            }
        });

        lblSenha.setText("Senha:");

        TxtSenha.setText("jPasswordField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TxtNomeUsu)
                    .addComponent(TxtDataNasc)
                    .addComponent(TxtCPF)
                    .addComponent(TxtEmail)
                    .addComponent(TxtTelefone, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TxtSenha)
                    .addComponent(TxtNome)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(lblSenha)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lblPerfil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNomeUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVoltar)
                .addGap(27, 27, 27))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TxtNomeUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNomeUsuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNomeUsuActionPerformed

    private void TxtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNomeActionPerformed

    private void TxtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtEmailActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // TODO add your handling code here:
        TelaPrincipalProp telaPrincipal = new TelaPrincipalProp();
        telaPrincipal.setLocationRelativeTo(null);
        telaPrincipal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void TxtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCPFActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        clicked++;
        
        if (clicked == 1)
        {
            SetEditable(true);
            TxtNomeUsu.requestFocus();
            TxtNomeUsu.setCaretPosition(0);
            btnEditar.setText("Salvar");
            btnEditar.revalidate();
            btnEditar.repaint();
            shouldSave = true;
        }
        
        if (clicked > 1)
        {
            if (shouldSave == true)
            {
                int result = JOptionPane.showConfirmDialog
                (
                    null,
                    "Deseja atualizar seus dados?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION)
                {
                    if (CamposUpdate_SaoValidos() == true)
                    {
                        _txtNome = TxtNome.getText();
                        _txtDataNasc = DateUtil.fromDisplay(TxtDataNasc.getText());
                        _txtCPF = TxtCPF.getText();
                        _txtEmail = TxtEmail.getText();
                        _txtTelefone = TxtTelefone.getText();
                        _txtSenha = TxtSenha.getText();
                        _txtNomeUsu = TxtNomeUsu.getText();

                        ModuloDbDML db = new ModuloDbDML(conexao);

                        boolean atualizou = db.atualizar
                        ("UPDATE t_aah_proprietario set nm_proprietario = ?,"
                                + "dt_nascimento = ?,"
                                + "nr_cpf = ?,"
                                + "ds_email = ?,"
                                + "nr_telefone = ?,"
                                + "ds_senha = ?,"
                                + "ds_nome_usuario = ? "
                                + "where id_proprietario = ?", 
                                _txtNome, _txtDataNasc, _txtCPF, _txtEmail, _txtTelefone, _txtSenha, _txtNomeUsu, idAtual);

                        if (atualizou == true) 
                        {
                            JOptionPane.showMessageDialog(null, "Atualização no cadastro do usuário " + TxtNome.getText() + " efetuada com sucesso!");

                            PopularCamposSelect();
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Campo(s) inválido(s)! Corrija e tente novamente!");
                        TxtNomeUsu.requestFocus();
                        TxtNomeUsu.setCaretPosition(0);
                    }
                }
                else 
                {
                    // Do nothing
                }
            }
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        
        //ImageIcon myIcon = new ImageIcon("path/to/icon.png");
        
        int pergunta1 = JOptionPane.showConfirmDialog
        (
            null,
            "Tem certeza que deseja excluir seu cadastro?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
        
        if (pergunta1 == JOptionPane.YES_OPTION)
        { 
            int pergunta2 = JOptionPane.showConfirmDialog
            (
                null,
                "<html><center><b><h3>Atenção</h3>"
                        + "<br>Ao clicar em 'Sim', todos os seus dados serão apagados,"
                        + "<br>seu perfil será excluído permanentemente,"
                        + "<br>e seu acesso ao sistema será perdido!</center></b></html>",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
            );
            
            if (pergunta2 == JOptionPane.YES_OPTION)
            {
                ModuloDbDML db = new ModuloDbDML(conexao);

                boolean excluiu = db.deletar
                ("DELETE FROM t_aah_proprietario WHERE id_proprietario = ?", idAtual);

                if (excluiu == true) 
                {
                    JOptionPane.showMessageDialog(null, "Perfil do usuário " + TxtNome.getText() + " excluído com sucesso! "
                            + "Você será redirecionado para a página de login.");
                    
                    SessaoUsuario.setIdUsuarioLogado(-1);
                    TelaInicial telaInicio = new TelaInicial();
                    telaInicio.setLocationRelativeTo(null);
                    telaInicio.setVisible(true);
                    this.dispose();
                }
            }
            else
            {
                // Do nothing
            }
        }
        else 
        {
            // Do nothing
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void TxtDataNascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtDataNascActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtDataNascActionPerformed

    private void TxtTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTelefoneActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaUsuPropri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaUsuPropri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaUsuPropri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaUsuPropri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaUsuPropri().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(TelaUsuPropri.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField TxtCPF;
    private javax.swing.JFormattedTextField TxtDataNasc;
    private javax.swing.JTextField TxtEmail;
    private javax.swing.JTextField TxtNome;
    private javax.swing.JTextField TxtNomeUsu;
    private javax.swing.JPasswordField TxtSenha;
    private javax.swing.JFormattedTextField TxtTelefone;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblSenha;
    // End of variables declaration//GEN-END:variables
}
