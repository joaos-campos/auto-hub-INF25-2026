/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas;
import AcessoDB.*;
import static AutoHubUtil.ButtonsUtil.ActivateOnEnter;
import static AutoHubUtil.ValidarCpfUtil.ValidarCpf;
import AutoHubUtil.DateUtil;
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
import java.sql.ResultSet;
import java.time.LocalDate;

public class TelaCadastroPropri extends javax.swing.JFrame {
    
    Connection conexao = null;
    
    private String _txtNome;
    private String _txtDataNasc;
    private String _txtCPF;
    private String _txtEmail;
    private String _txtTelefone;
    private String _txtSenha;
    private String _txtNomeUsu;
    
    public boolean CamposCadastro_SaoValidos()
    {
        boolean[] validacoes = new boolean[8];
        
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
            if (ValidarCpf(TxtCPF.getText()) == false)
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
        
        if (((TxtSenha.getText().isEmpty() == true) || (TxtSenha.getText().isBlank() == true)) && 
                ((TxtConfSenha.getText().isEmpty() == true) || (TxtConfSenha.getText().isBlank() == true)))
        {
            JOptionPane.showMessageDialog(null, "Digite uma senha válida!");
            validacoes[6] = false;
        }
        else if (!TxtSenha.getText().equals(TxtConfSenha.getText()))
        {
            JOptionPane.showMessageDialog(null, "As senhas são diferentes! Confira a senha digita no campo 'Senha' e 'Confirme sua senha'!");
            validacoes[7] = false;
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

    /**
     * Creates new form TelaCadastroPropri
     * @throws java.text.ParseException
     */
    public TelaCadastroPropri() throws ParseException
    {   
        initComponents();
        PostInitComponents();
        DefineTextFieldsBehaviour();
    }
    
    private void PostInitComponents()
    {
        conexao = ModuloDbConnect.connector();
        ActivateOnEnter(BtnCadastrar);
        ActivateOnEnter(BtnVoltar);
        this.getContentPane().setBackground(new Color(198, 200, 200));   
        this.setSize(575, 650); 
    }
    
    /**
    * @throws java.text.ParseException
    */
    private void DefineTextFieldsBehaviour()  throws ParseException 
    {
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtCPF"> 
        // =================================================================================
        TxtCPF.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        String TxtCPFPlaceholder = "000.000.000-00";
        
        TxtCPF.setText(TxtCPFPlaceholder);
        
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
                        TxtCPF.setForeground(Color.BLACK);
                    });
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtCPF.getText().isEmpty() || TxtCPF.getText().equals(TxtCPFPlaceholder)) 
                {
                    TxtCPF.setText(TxtCPFPlaceholder);
                    TxtCPF.setForeground(Color.GRAY);
                }
            }
        });
        // =================================================================================
         // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtDataNasc">
        // =================================================================================
        TxtDataNasc.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        String TxtDataNascPlaceholder = "0000/00/00";
        
        TxtDataNasc.setText(TxtDataNascPlaceholder);
        
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
                        TxtDataNasc.setForeground(Color.BLACK);
                    });
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtDataNasc.getText().isEmpty() || TxtDataNasc.getText().equals(TxtDataNascPlaceholder)) 
                {
                    TxtDataNasc.setText(TxtDataNascPlaceholder);
                    TxtDataNasc.setForeground(Color.GRAY);
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtNome"> 
        // =================================================================================
        String TxtNomePlaceholder = "Nome completo";
        
        TxtNome.setText(TxtNomePlaceholder);
        
        TxtNome.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtNome.getText().equals(TxtNomePlaceholder)) 
                {
                    TxtNome.setText("");
                    TxtNome.setCaretPosition(0);
                    TxtNome.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtNome.getText().isEmpty() || TxtNome.getText().equals(TxtNomePlaceholder)) 
                {
                    TxtNome.setText(TxtNomePlaceholder);
                    TxtNome.setForeground(Color.GRAY);
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtEmail"> 
        // =================================================================================
        String TxtEmailPlaceholder = "seu_nome@site.com.br";
        
        TxtEmail.setText(TxtEmailPlaceholder);
        
        TxtEmail.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtEmail.getText().equals(TxtEmailPlaceholder)) 
                {
                    TxtEmail.setText("");
                    TxtEmail.setCaretPosition(0);
                    TxtEmail.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtEmail.getText().isEmpty() || TxtEmail.getText().equals(TxtEmailPlaceholder)) 
                {
                    TxtEmail.setText(TxtEmailPlaceholder);
                    TxtEmail.setForeground(Color.GRAY);
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtTelefone"> 
        // =================================================================================
        String TxtTelefonePlaceholder = "(00)00000-0000";
        
        TxtTelefone.setText(TxtTelefonePlaceholder);
        
        TxtTelefone.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        TxtTelefone.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtTelefone.getText().equals(TxtTelefonePlaceholder)) 
                {
                    TxtTelefone.setCaretPosition(0);
                    TxtTelefone.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtTelefone.getText().isEmpty() || TxtTelefone.getText().equals(TxtTelefonePlaceholder)) 
                {
                    TxtTelefone.setText(TxtTelefonePlaceholder);
                    TxtTelefone.setForeground(Color.GRAY);
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtNomeUsu"> 
        // =================================================================================
        String TxtNomeUsuPlaceholder = "user_01";
        
        TxtNomeUsu.setText(TxtNomeUsuPlaceholder);
        
        TxtNomeUsu.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtNomeUsu.getText().equals(TxtNomeUsuPlaceholder)) 
                {
                    TxtNomeUsu.setText("");
                    TxtNomeUsu.setCaretPosition(0);
                    TxtNomeUsu.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtNomeUsu.getText().isEmpty() || TxtNomeUsu.getText().equals(TxtNomeUsuPlaceholder)) 
                {
                    TxtNomeUsu.setText(TxtNomeUsuPlaceholder);
                    TxtNomeUsu.setForeground(Color.GRAY);
                }
            }
        });
                // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtSenha"> 
        // =================================================================================        
        String TxtSenhaPlaceholder = "Digite sua senha";
        
        TxtSenha.setEchoChar((char) 0);
        
        TxtSenha.setText(TxtSenhaPlaceholder);
        
        TxtSenha.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (String.valueOf(TxtSenha.getPassword()).equals(TxtSenhaPlaceholder)) 
                {
                    TxtSenha.setText("");
                    TxtSenha.setForeground(Color.BLACK);
                    TxtSenha.setEchoChar('*'); // Restaura o caractere de senha padrão
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (String.valueOf(TxtSenha.getPassword()).isEmpty() || String.valueOf(TxtSenha.getPassword()).equals(TxtSenhaPlaceholder)) 
                {
                    TxtSenha.setText(TxtSenhaPlaceholder);
                    TxtSenha.setForeground(Color.GRAY);
                    TxtSenha.setEchoChar((char) 0); // Remove os asteriscos para mostrar o placeholder
                }
            }
        });
        // =================================================================================
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtConfSenha"> 
        // =================================================================================        
        String TxtConfSenhaPlaceholder = "Confirme sua senha";
        
        TxtConfSenha.setEchoChar((char) 0);
        
        TxtConfSenha.setText(TxtConfSenhaPlaceholder);
        
        TxtConfSenha.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (String.valueOf(TxtConfSenha.getPassword()).equals(TxtConfSenhaPlaceholder)) 
                {
                    TxtConfSenha.setText("");
                    TxtConfSenha.setForeground(Color.BLACK);
                    TxtConfSenha.setEchoChar('*'); // Restaura o caractere de senha padrão
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (String.valueOf(TxtConfSenha.getPassword()).isEmpty() || String.valueOf(TxtConfSenha.getPassword()).equals(TxtConfSenhaPlaceholder)) 
                {
                    TxtConfSenha.setText(TxtConfSenhaPlaceholder);
                    TxtConfSenha.setForeground(Color.GRAY);
                    TxtConfSenha.setEchoChar((char) 0); // Remove os asteriscos para mostrar o placeholder
                }
            }
        });
        // =================================================================================
        // </editor-fold>
    }
    
    private void LimparTela() throws ParseException
    {
        PostInitComponents();
        TxtNome.requestFocus();
        TxtNome.setCaretPosition(0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        PnlCadastro = new javax.swing.JPanel();
        LblNome = new javax.swing.JLabel();
        LblDate = new javax.swing.JLabel();
        TxtNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        LblCPF = new javax.swing.JLabel();
        LblEmail = new javax.swing.JLabel();
        LblTelefone = new javax.swing.JLabel();
        TxtEmail = new javax.swing.JTextField();
        LblSenha = new javax.swing.JLabel();
        TxtSenha = new javax.swing.JPasswordField();
        LblConfSenha = new javax.swing.JLabel();
        TxtConfSenha = new javax.swing.JPasswordField();
        BtnCadastrar = new javax.swing.JButton();
        BtnVoltar = new javax.swing.JButton();
        TxtNomeUsu = new javax.swing.JTextField();
        LblNomeUsu = new javax.swing.JLabel();
        TxtCPF = new javax.swing.JFormattedTextField();
        TxtDataNasc = new javax.swing.JFormattedTextField();
        TxtTelefone = new javax.swing.JFormattedTextField();

        jFormattedTextField1.setText("jFormattedTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro Proprietário");
        setResizable(false);

        PnlCadastro.setBackground(new java.awt.Color(198, 200, 200));

        LblNome.setText("Insira seu nome");

        LblDate.setText("Data de nascimento");

        TxtNome.setForeground(new java.awt.Color(204, 204, 204));
        TxtNome.setText("Nome completo");
        TxtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNomeActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(32, 32, 63));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(247, 250, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Crie uma conta no AutoHub");
        jLabel3.setOpaque(true);

        LblCPF.setText("Digite seu CPF");

        LblEmail.setText("E-mail");

        LblTelefone.setText("Telefone");

        TxtEmail.setForeground(new java.awt.Color(204, 204, 204));
        TxtEmail.setText("seu_nome@site.com.br");
        TxtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtEmailActionPerformed(evt);
            }
        });

        LblSenha.setText("Senha");

        TxtSenha.setForeground(new java.awt.Color(204, 204, 204));
        TxtSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtSenhaActionPerformed(evt);
            }
        });

        LblConfSenha.setText("Confirme sua senha");

        TxtConfSenha.setForeground(new java.awt.Color(204, 204, 204));
        TxtConfSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtConfSenhaActionPerformed(evt);
            }
        });

        BtnCadastrar.setBackground(new java.awt.Color(255, 212, 59));
        BtnCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnCadastrar.setForeground(new java.awt.Color(33, 40, 68));
        BtnCadastrar.setText("Cadastrar-se");
        BtnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCadastrarActionPerformed(evt);
            }
        });

        BtnVoltar.setBackground(new java.awt.Color(32, 32, 63));
        BtnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnVoltar.setForeground(new java.awt.Color(255, 255, 255));
        BtnVoltar.setText("Voltar");
        BtnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVoltarActionPerformed(evt);
            }
        });

        TxtNomeUsu.setForeground(new java.awt.Color(204, 204, 204));
        TxtNomeUsu.setText("user_01");
        TxtNomeUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNomeUsuActionPerformed(evt);
            }
        });

        LblNomeUsu.setText("Nome de usuário");

        TxtCPF.setForeground(new java.awt.Color(204, 204, 204));
        try {
            TxtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtCPF.setText("000.000.000-00"); // NOI18N
        TxtCPF.setToolTipText("Informe seu CPF");
        TxtCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCPFActionPerformed(evt);
            }
        });

        TxtDataNasc.setForeground(new java.awt.Color(204, 204, 204));
        try {
            TxtDataNasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####/##/##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtDataNasc.setText("0000/00/00"); // NOI18N
        TxtDataNasc.setToolTipText("Informe sua data de nascimento");
        TxtDataNasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtDataNascActionPerformed(evt);
            }
        });

        TxtTelefone.setForeground(new java.awt.Color(204, 204, 204));
        try {
            TxtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtTelefone.setText("(00)00000-0000"); // NOI18N
        TxtTelefone.setToolTipText("Informe seu telefone");
        TxtTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTelefoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlCadastroLayout = new javax.swing.GroupLayout(PnlCadastro);
        PnlCadastro.setLayout(PnlCadastroLayout);
        PnlCadastroLayout.setHorizontalGroup(
            PnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtTelefone)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                    .addComponent(TxtNomeUsu)
                    .addGroup(PnlCadastroLayout.createSequentialGroup()
                        .addGroup(PnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblNome)
                            .addComponent(LblDate)
                            .addComponent(LblSenha)
                            .addComponent(LblConfSenha)
                            .addComponent(LblCPF)
                            .addComponent(LblEmail)
                            .addComponent(LblTelefone)
                            .addComponent(LblNomeUsu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(TxtSenha)
                    .addComponent(TxtConfSenha)
                    .addComponent(TxtEmail)
                    .addComponent(TxtCPF)
                    .addComponent(TxtDataNasc)
                    .addComponent(TxtNome)
                    .addComponent(BtnCadastrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PnlCadastroLayout.setVerticalGroup(
            PnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(9, 9, 9)
                .addComponent(LblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(LblDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(LblCPF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LblTelefone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LblNomeUsu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNomeUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LblSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblConfSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtConfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtnCadastrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnVoltar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PnlCadastro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PnlCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVoltarActionPerformed
        TelaLoginProprietario telaLogin = new TelaLoginProprietario();
        telaLogin.setLocationRelativeTo(null);
        telaLogin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnVoltarActionPerformed

    private void BtnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCadastrarActionPerformed
        if (CamposCadastro_SaoValidos() == true)
        {
            _txtNome = TxtNome.getText();
            _txtDataNasc = TxtDataNasc.getText();
            _txtDataNasc = _txtDataNasc.replace('/', '-');
            _txtCPF = TxtCPF.getText();
            _txtEmail = TxtEmail.getText();
            _txtTelefone = TxtTelefone.getText();
            _txtSenha = TxtSenha.getText();
            _txtNomeUsu = TxtNomeUsu.getText();
            
            ModuloDbDML db = new ModuloDbDML(conexao);
            
            String sql = "SELECT nr_cpf FROM t_aah_proprietario where nr_cpf = ?";
            
            boolean ehCpfRepetido = false;
            String cpfRepetido = "";
            
            try 
            {  
                ResultSet rs = db.consultarResultSet(sql, _txtCPF);

                if (rs.next())
                {
                    cpfRepetido = rs.getString("nr_cpf");
                    
                    if (cpfRepetido.equals(_txtCPF) || cpfRepetido.replaceAll("\\D", "").equals(_txtCPF))
                    {
                        ehCpfRepetido = true;
                    }
                }
            } 
            catch (Exception e) 
            {
                 javax.swing.JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
                 e.printStackTrace();               
            }
            
            if (!ehCpfRepetido)
            {
                long novo_id = db.inserirRetornandoId("INSERT INTO t_aah_proprietario "
                        + "(nm_proprietario, dt_nascimento, nr_cpf, ds_email, nr_telefone, ds_senha, ds_nome_usuario) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)", 
                        _txtNome, _txtDataNasc, _txtCPF, _txtEmail, _txtTelefone, _txtSenha, _txtNomeUsu);

                if (novo_id != -1) 
                {
                    JOptionPane.showMessageDialog(null, "Cadastro do usuário " + TxtNome.getText() + " efetuado com sucesso!");
                    JOptionPane.showMessageDialog(null, "Você será redirecionado para a tela de Login!");

                    TelaLoginProprietario tlLgProprietario = new TelaLoginProprietario();
                    tlLgProprietario.setLocationRelativeTo(null);
                    tlLgProprietario.setVisible(true);
                    this.dispose();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "O nº de CPF " + _txtCPF + " já está cadastrado! Verifique o CPF e tente novamente!");
                TxtCPF.requestFocus();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Campo(s) inválido(s)! Corrija e tente novamente!");
            TxtNome.requestFocus();
            TxtNome.setCaretPosition(0);
        }
    }//GEN-LAST:event_BtnCadastrarActionPerformed

    private void TxtConfSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtConfSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtConfSenhaActionPerformed

    private void TxtNomeUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNomeUsuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNomeUsuActionPerformed

    private void TxtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtEmailActionPerformed

    private void TxtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCPFActionPerformed

    private void TxtDataNascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtDataNascActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtDataNascActionPerformed

    private void TxtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNomeActionPerformed

    private void TxtTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTelefoneActionPerformed

    private void TxtSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtSenhaActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadastroPropri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroPropri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroPropri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroPropri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaCadastroPropri().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(TelaCadastroPropri.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCadastrar;
    private javax.swing.JButton BtnVoltar;
    private javax.swing.JLabel LblCPF;
    private javax.swing.JLabel LblConfSenha;
    private javax.swing.JLabel LblDate;
    private javax.swing.JLabel LblEmail;
    private javax.swing.JLabel LblNome;
    private javax.swing.JLabel LblNomeUsu;
    private javax.swing.JLabel LblSenha;
    private javax.swing.JLabel LblTelefone;
    private javax.swing.JPanel PnlCadastro;
    private javax.swing.JFormattedTextField TxtCPF;
    private javax.swing.JPasswordField TxtConfSenha;
    private javax.swing.JFormattedTextField TxtDataNasc;
    private javax.swing.JTextField TxtEmail;
    private javax.swing.JTextField TxtNome;
    private javax.swing.JTextField TxtNomeUsu;
    private javax.swing.JPasswordField TxtSenha;
    private javax.swing.JFormattedTextField TxtTelefone;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
