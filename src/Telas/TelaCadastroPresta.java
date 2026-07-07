
package Telas;
import AcessoDB.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Color;

public class TelaCadastroPresta extends javax.swing.JFrame {
    
    Connection conexao = null;
    
    private String _txtNmEmpresa;
    private String _txtCNPJ;
    private String _txtTelefonePresta;
    private String _txtEmailPresta;
    private String _txtSenhaPresta;
    private String _txtNmUsuPresta;
    
    
    public boolean CamposCadastroPresta_SaoValidos()
    {
        boolean[] validacoespresta = new boolean[5];
        
        for (int i = 0; i < validacoespresta.length; i++)
        {
            validacoespresta[i] = true;
            //System.out.println(validacoes[i]);
        }
        
        if ((TxtNmEmpresa.getText().isEmpty() == true) || (TxtNmEmpresa.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um nome válido!");
            validacoespresta[0] = false;
        }
        if ((TxtEmailPresta.getText().isEmpty() == true) || (TxtEmailPresta.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um e-mail válido!");
            validacoespresta[1] = false;
        }
        if ((TxtCNPJ.getText().isEmpty() == true) || (TxtCNPJ.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um CNPJ válido!");
            validacoespresta[2] = false;
        }
        if ((TxtTelefonePresta.getText().isEmpty() == true) || (TxtTelefonePresta.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um telefone válido!");
            validacoespresta[3] = false;
        }
        if ((TxtNmUsuPresta.getText().isEmpty() == true) || (TxtNmUsuPresta.getText().isBlank() == true))
        {
            JOptionPane.showMessageDialog(null, "Digite um nome de usuário válido!");
            validacoespresta[4] = false;
        }
        if (((TxtSenhaPresta.getText().isEmpty() == true) || (TxtSenhaPresta.getText().isBlank() == true)) && 
                ((TxtConfSenhaPresta.getText().isEmpty() == true) || (TxtConfSenhaPresta.getText().isBlank() == true)))
        {
            JOptionPane.showMessageDialog(null, "Digite uma senha válida!");
            validacoespresta[5] = false;
        }
        else if (!TxtSenhaPresta.getText().equals(TxtConfSenhaPresta.getText()))
        {
            JOptionPane.showMessageDialog(null, "As senhas são diferentes! Confira a senha digita no campo 'Senha' e 'Confirme sua senha'!");
            validacoespresta[6] = false;
        }
        boolean is_valido = true;
        
        for (int i = 0; i < validacoespresta.length; i++)
        {
            if (validacoespresta[i] == false)
            {
                //System.out.println(validacoes[i]);
                is_valido = false;
            }
        }
        
        return is_valido;
    }
    
    public TelaCadastroPresta() throws ParseException {
        initComponents();
        
        PostInitComponents();
        
        this.getContentPane().setBackground(new Color(198, 200, 200));
        
        conexao = ModuloDbConnect.connector();
        
        this.setSize(575, 650);
    }
    private void PostInitComponents()  throws ParseException 
    {   
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtCNPJ"> 
        // =================================================================================
        TxtCNPJ.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        String TxtCNPJPlaceholder = "00.000.000/0000-00";
        
        TxtCNPJ.setText(TxtCNPJPlaceholder);
        TxtCNPJ.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtCNPJ.getText().equals(TxtCNPJPlaceholder)) 
                {
                    SwingUtilities.invokeLater(() -> 
                    {
                        TxtCNPJ.setCaretPosition(0);
                        TxtCNPJ.setForeground(Color.BLACK);
                    });
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtCNPJ.getText().isEmpty() || TxtCNPJ.getText().equals(TxtCNPJPlaceholder)) 
                {
                    TxtCNPJ.setText(TxtCNPJPlaceholder);
                    TxtCNPJ.setForeground(Color.GRAY);
                }
            }
        });
         // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtNmEmpresa"> 
        // =================================================================================
        String TxtNmEmpresaPlaceholder = "Nome completo";
        
        TxtNmEmpresa.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtNmEmpresa.getText().equals(TxtNmEmpresaPlaceholder)) 
                {
                    TxtNmEmpresa.setText("");
                    TxtNmEmpresa.setCaretPosition(0);
                    TxtNmEmpresa.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtNmEmpresa.getText().isEmpty() || TxtNmEmpresa.getText().equals(TxtNmEmpresaPlaceholder)) 
                {
                    TxtNmEmpresa.setText(TxtNmEmpresaPlaceholder);
                    TxtNmEmpresa.setForeground(Color.GRAY);
                }
            }
        });
         // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtTelefonePresta"> 
        // =================================================================================
        String TxtTelefonePrestaPlaceholder = "(00)00000-0000";
        
        TxtTelefonePresta.setText(TxtTelefonePrestaPlaceholder);
        
        TxtTelefonePresta.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        TxtTelefonePresta.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtTelefonePresta.getText().equals(TxtTelefonePrestaPlaceholder)) 
                {
                    TxtTelefonePresta.setCaretPosition(0);
                    TxtTelefonePresta.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtTelefonePresta.getText().isEmpty() || TxtTelefonePresta.getText().equals(TxtTelefonePrestaPlaceholder)) 
                {
                    TxtTelefonePresta.setText(TxtTelefonePrestaPlaceholder);
                    TxtTelefonePresta.setForeground(Color.GRAY);
                }
            }
        });
         // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtEmail"> 
        // =================================================================================
        String TxtEmailPrestaPlaceholder = "seu_nome@site.com.br";
        
        TxtEmailPresta.setText(TxtEmailPrestaPlaceholder);
        
        TxtEmailPresta.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtEmailPresta.getText().equals(TxtEmailPrestaPlaceholder)) 
                {
                    TxtEmailPresta.setText("");
                    TxtEmailPresta.setCaretPosition(0);
                    TxtEmailPresta.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtEmailPresta.getText().isEmpty() || TxtEmailPresta.getText().equals(TxtEmailPrestaPlaceholder)) 
                {
                    TxtEmailPresta.setText(TxtEmailPrestaPlaceholder);
                    TxtEmailPresta.setForeground(Color.GRAY);
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtNomeUsu"> 
        // =================================================================================
        String TxtNmUsuPrestaPlaceholder = "user_01";
        
        TxtNmUsuPresta.setText(TxtNmUsuPrestaPlaceholder);
        
        TxtNmUsuPresta.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtNmUsuPresta.getText().equals(TxtNmUsuPrestaPlaceholder)) 
                {
                    TxtNmUsuPresta.setText("");
                    TxtNmUsuPresta.setCaretPosition(0);
                    TxtNmUsuPresta.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtNmUsuPresta.getText().isEmpty() || TxtNmUsuPresta.getText().equals(TxtNmUsuPrestaPlaceholder)) 
                {
                    TxtNmUsuPresta.setText(TxtNmUsuPrestaPlaceholder);
                    TxtNmUsuPresta.setForeground(Color.GRAY);
                }
            }
        });
                // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtSenha"> 
        // =================================================================================        
        String TxtSenhaPrestaPlaceholder = "Digite sua senha";
        
        TxtSenhaPresta.setEchoChar((char) 0);
        
        TxtSenhaPresta.setText(TxtSenhaPrestaPlaceholder);
        
        TxtSenhaPresta.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (String.valueOf(TxtSenhaPresta.getPassword()).equals(TxtSenhaPrestaPlaceholder)) 
                {
                    TxtSenhaPresta.setText("");
                    TxtSenhaPresta.setForeground(Color.BLACK);
                    TxtSenhaPresta.setEchoChar('*'); // Restaura o caractere de senha padrão
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (String.valueOf(TxtSenhaPresta.getPassword()).isEmpty() || String.valueOf(TxtSenhaPresta.getPassword()).equals(TxtSenhaPrestaPlaceholder)) 
                {
                    TxtSenhaPresta.setText(TxtSenhaPrestaPlaceholder);
                    TxtSenhaPresta.setForeground(Color.GRAY);
                    TxtSenhaPresta.setEchoChar((char) 0); // Remove os asteriscos para mostrar o placeholder
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtConfSenha"> 
        // =================================================================================        
        String TxtConfSenhaPrestaPlaceholder = "Confirme sua senha";
        
        TxtConfSenhaPresta.setEchoChar((char) 0);
        
        TxtConfSenhaPresta.setText(TxtConfSenhaPrestaPlaceholder);
        
        TxtConfSenhaPresta.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (String.valueOf(TxtConfSenhaPresta.getPassword()).equals(TxtConfSenhaPrestaPlaceholder)) 
                {
                    TxtConfSenhaPresta.setText("");
                    TxtConfSenhaPresta.setForeground(Color.BLACK);
                    TxtConfSenhaPresta.setEchoChar('*'); // Restaura o caractere de senha padrão
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (String.valueOf(TxtConfSenhaPresta.getPassword()).isEmpty() || String.valueOf(TxtConfSenhaPresta.getPassword()).equals(TxtConfSenhaPrestaPlaceholder)) 
                {
                    TxtConfSenhaPresta.setText(TxtConfSenhaPrestaPlaceholder);
                    TxtConfSenhaPresta.setForeground(Color.GRAY);
                    TxtConfSenhaPresta.setEchoChar((char) 0); // Remove os asteriscos para mostrar o placeholder
                }
            }
        });
        // =================================================================================
        // </editor-fold>
    }
    
    private void LimparTelaPresta() throws ParseException
    {
        PostInitComponents();
        TxtNmEmpresa.requestFocus();
        TxtNmEmpresa.setCaretPosition(0);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LblNmEmpresa = new javax.swing.JLabel();
        TxtNmEmpresa = new javax.swing.JTextField();
        LblCNPJ = new javax.swing.JLabel();
        LblTelefone = new javax.swing.JLabel();
        BtnAvancar = new javax.swing.JButton();
        BtnVoltar = new javax.swing.JButton();
        TxtCNPJ = new javax.swing.JFormattedTextField();
        TxtTelefonePresta = new javax.swing.JFormattedTextField();
        LblSenhaPresta = new javax.swing.JLabel();
        TxtSenhaPresta = new javax.swing.JPasswordField();
        LblConfSenhaPresta = new javax.swing.JLabel();
        TxtConfSenhaPresta = new javax.swing.JPasswordField();
        LblEmailPresta = new javax.swing.JLabel();
        TxtEmailPresta = new javax.swing.JTextField();
        LblNmUsuPresta = new javax.swing.JLabel();
        TxtNmUsuPresta = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(198, 200, 200));

        LblNmEmpresa.setText("Insira o nome da sua empresa");

        TxtNmEmpresa.setForeground(new java.awt.Color(204, 204, 204));
        TxtNmEmpresa.setText("Nome completo");
        TxtNmEmpresa.addActionListener(this::TxtNmEmpresaActionPerformed);

        LblCNPJ.setText("CNPJ");

        LblTelefone.setText("Telefone");

        BtnAvancar.setBackground(new java.awt.Color(255, 212, 59));
        BtnAvancar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnAvancar.setForeground(new java.awt.Color(33, 40, 68));
        BtnAvancar.setText("Avançar");
        BtnAvancar.addActionListener(this::BtnAvancarActionPerformed);

        BtnVoltar.setBackground(new java.awt.Color(32, 32, 63));
        BtnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnVoltar.setForeground(new java.awt.Color(247, 250, 255));
        BtnVoltar.setText("Voltar");
        BtnVoltar.addActionListener(this::BtnVoltarActionPerformed);

        TxtCNPJ.setForeground(new java.awt.Color(204, 204, 204));
        try {
            TxtCNPJ.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtCNPJ.setText("00.000.000/0000-00"); // NOI18N
        TxtCNPJ.setToolTipText("Informe seu CNPJ");
        TxtCNPJ.addActionListener(this::TxtCNPJActionPerformed);

        TxtTelefonePresta.setForeground(new java.awt.Color(204, 204, 204));
        try {
            TxtTelefonePresta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtTelefonePresta.setText("(00)00000-0000");
        TxtTelefonePresta.setToolTipText("");
        TxtTelefonePresta.addActionListener(this::TxtTelefonePrestaActionPerformed);

        LblSenhaPresta.setText("Digite uma senha");

        TxtSenhaPresta.setForeground(new java.awt.Color(204, 204, 204));
        TxtSenhaPresta.setText("jPasswordField1");

        LblConfSenhaPresta.setText("Confirme sua senha");

        TxtConfSenhaPresta.setForeground(new java.awt.Color(204, 204, 204));
        TxtConfSenhaPresta.setText("jPasswordField1");

        LblEmailPresta.setText("E-mail");

        TxtEmailPresta.setForeground(new java.awt.Color(204, 204, 204));
        TxtEmailPresta.setText("seu_nome@site.com.br");

        LblNmUsuPresta.setText("Nome de usuário");

        TxtNmUsuPresta.setForeground(new java.awt.Color(204, 204, 204));
        TxtNmUsuPresta.setText("user_01");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblEmailPresta)
                            .addComponent(LblTelefone)
                            .addComponent(LblCNPJ))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(BtnVoltar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnAvancar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TxtConfSenhaPresta, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtSenhaPresta, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtNmUsuPresta, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtEmailPresta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                            .addComponent(TxtTelefonePresta, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtCNPJ, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblNmUsuPresta, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblSenhaPresta, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblConfSenhaPresta, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblNmEmpresa, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtNmEmpresa, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(LblNmEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNmEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblCNPJ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblTelefone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtTelefonePresta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblEmailPresta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtEmailPresta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LblNmUsuPresta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNmUsuPresta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblSenhaPresta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtSenhaPresta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblConfSenhaPresta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtConfSenhaPresta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(BtnAvancar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnVoltar)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(32, 32, 63));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(247, 250, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Crie uma conta no AutoHub");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVoltarActionPerformed
        TelaLoginPrestador telaLoginPresta = new TelaLoginPrestador();
        telaLoginPresta.setLocationRelativeTo(null);
        telaLoginPresta.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnVoltarActionPerformed

    private void BtnAvancarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAvancarActionPerformed
        // TODO add your handling code here:
        if (CamposCadastroPresta_SaoValidos() == true)
        {
        _txtNmEmpresa = TxtNmEmpresa.getText();
        _txtCNPJ = TxtCNPJ.getText();
        _txtTelefonePresta = TxtTelefonePresta.getText();
        _txtEmailPresta = TxtEmailPresta.getText();
        _txtSenhaPresta = TxtSenhaPresta.getText();
        _txtNmUsuPresta = TxtNmUsuPresta.getText();
        
        ModuloDbDML db = new ModuloDbDML(conexao);
        
        /*
        
        boolean funcionou = db.deletar("delete from t_aah_proprietario where id_proprietario = ? ", TxtIdProprietario.getText());
        
        if (funcionou == true)
        {
            JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso!");
        }
        */
        
        long novo_idpresta = db.inserirRetornandoId("INSERT INTO t_aah_prestador "
                    + "(nm_fantasia, nr_cnpj, nr_telefone, ds_senha, ds_email, ds_nome_usuario) "
                    + "VALUES (?, ?, ?, ?, ?, ?)",
                _txtNmEmpresa, _txtCNPJ, _txtTelefonePresta, _txtSenhaPresta, _txtEmailPresta, _txtNmUsuPresta);
        
        if (novo_idpresta != -1) 
            {
                JOptionPane.showMessageDialog(null, "Cadastro do usuário " + TxtNmEmpresa.getText() + " efetuado com sucesso!");
                System.out.println(novo_idpresta);
                try 
                {
                    LimparTelaPresta();
                } 
                catch (ParseException ex) 
                {
                     Logger.getLogger(TelaCadastroPresta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Campo(s) inválido(s)! Corrija e tente novamente!");
            TxtNmEmpresa.requestFocus();
            TxtNmEmpresa.setCaretPosition(0);
        }
    }//GEN-LAST:event_BtnAvancarActionPerformed

    private void TxtNmEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNmEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNmEmpresaActionPerformed

    private void TxtCNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCNPJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCNPJActionPerformed

    private void TxtTelefonePrestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTelefonePrestaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTelefonePrestaActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new TelaCadastroPresta().setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(TelaCadastroPresta.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAvancar;
    private javax.swing.JButton BtnVoltar;
    private javax.swing.JLabel LblCNPJ;
    private javax.swing.JLabel LblConfSenhaPresta;
    private javax.swing.JLabel LblEmailPresta;
    private javax.swing.JLabel LblNmEmpresa;
    private javax.swing.JLabel LblNmUsuPresta;
    private javax.swing.JLabel LblSenhaPresta;
    private javax.swing.JLabel LblTelefone;
    private javax.swing.JFormattedTextField TxtCNPJ;
    private javax.swing.JPasswordField TxtConfSenhaPresta;
    private javax.swing.JTextField TxtEmailPresta;
    private javax.swing.JTextField TxtNmEmpresa;
    private javax.swing.JTextField TxtNmUsuPresta;
    private javax.swing.JPasswordField TxtSenhaPresta;
    private javax.swing.JFormattedTextField TxtTelefonePresta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
