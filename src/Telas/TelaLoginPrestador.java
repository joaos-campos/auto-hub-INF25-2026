
package Telas;

import AcessoDB.ModuloDbConnect;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TelaLoginPrestador extends javax.swing.JFrame {
        
    Connection conexao = null; 
    PreparedStatement pst = null; 
    ResultSet rs = null; 
    
    public void  logarPresta() 
    {
        String sql = "select * from t_aah_prestador where ds_nome_usuario  = ? and ds_senha = ? ";
        
        try 
        {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1, TxtUsuarioPresta.getText());
            pst.setString(2, TxtSenhaPresta.getText());
            
            rs = pst.executeQuery(); 
            if  (rs.next())
            { 
                TelaPrincipalPresta tlPrincipalPresta = new TelaPrincipalPresta(rs.getString(2)); 
                tlPrincipalPresta.setLocationRelativeTo(null);
                tlPrincipalPresta.setVisible(true);
                this.dispose();                  
            }else {
                JOptionPane.showMessageDialog(this, "Usuário/Senha INVÁLIDOS!!! Tente outra Vez! ");               
                TxtUsuarioPresta.setText("");
                TxtSenhaPresta.setText("");                
            }
            
        }
        catch(Exception varERRO) 
        { 
            JOptionPane.showMessageDialog(null,"Erro no acesso ao banco de dados: " + varERRO.toString() );
        }
    }

    public TelaLoginPrestador() 
    {
        initComponents();
        
        conexao = ModuloDbConnect.connector();
        
        if (conexao != null) 
        {
            lblMensagens.setText("Conexão OK!!!");
            lblMensagens.setForeground(Color.blue);
        }
        else
        {
            lblMensagens.setText("ERRO - NÃO CONECTADO!");
            lblMensagens.setForeground(Color.red);
        }
        
        lblMensagens.setVisible(false);
        
        this.getContentPane().setBackground(new Color(198, 200, 200));
        
        this.setSize(575, 650);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LblUsuario = new javax.swing.JLabel();
        TxtUsuarioPresta = new javax.swing.JTextField();
        LblSenha = new javax.swing.JLabel();
        TxtSenhaPresta = new javax.swing.JPasswordField();
        BtnEntrarPrest = new javax.swing.JButton();
        BtnCadastrarPrestador = new javax.swing.JButton();
        lblMensagens = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login prestador");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(198, 200, 200));

        LblUsuario.setText("Usuário");

        TxtUsuarioPresta.addActionListener(this::TxtUsuarioPrestaActionPerformed);

        LblSenha.setText("Senha");

        BtnEntrarPrest.setBackground(new java.awt.Color(255, 212, 59));
        BtnEntrarPrest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnEntrarPrest.setForeground(new java.awt.Color(33, 40, 68));
        BtnEntrarPrest.setText("Entrar");
        BtnEntrarPrest.addActionListener(this::BtnEntrarPrestActionPerformed);

        BtnCadastrarPrestador.setBackground(new java.awt.Color(32, 32, 63));
        BtnCadastrarPrestador.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnCadastrarPrestador.setForeground(new java.awt.Color(255, 255, 255));
        BtnCadastrarPrestador.setText("Cadastrar-se");
        BtnCadastrarPrestador.addActionListener(this::BtnCadastrarPrestadorActionPerformed);

        lblMensagens.setText("Mensagens...");

        jButton1.setBackground(new java.awt.Color(32, 32, 63));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Voltar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtUsuarioPresta, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TxtSenhaPresta, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnCadastrarPrestador, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                    .addComponent(BtnEntrarPrest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMensagens)
                            .addComponent(LblSenha)
                            .addComponent(LblUsuario))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtUsuarioPresta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtSenhaPresta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnEntrarPrest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnCadastrarPrestador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 213, Short.MAX_VALUE)
                .addComponent(lblMensagens)
                .addContainerGap())
        );

        jLabel1.setBackground(new java.awt.Color(32, 32, 63));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TxtUsuarioPrestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtUsuarioPrestaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtUsuarioPrestaActionPerformed

    private void BtnEntrarPrestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEntrarPrestActionPerformed

        logarPresta();
    }//GEN-LAST:event_BtnEntrarPrestActionPerformed

    private void BtnCadastrarPrestadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCadastrarPrestadorActionPerformed
        TelaCadastroPresta telaCadPrest = null;
        try {
            telaCadPrest = new TelaCadastroPresta();
        } catch (ParseException ex) {
            Logger.getLogger(TelaLoginPrestador.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaCadPrest.setLocationRelativeTo(null);
        telaCadPrest.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnCadastrarPrestadorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        TelaInicial telaInicio = new TelaInicial();
        telaInicio.setLocationRelativeTo(null);
        telaInicio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaLoginPrestador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCadastrarPrestador;
    private javax.swing.JButton BtnEntrarPrest;
    private javax.swing.JLabel LblSenha;
    private javax.swing.JLabel LblUsuario;
    private javax.swing.JPasswordField TxtSenhaPresta;
    private javax.swing.JTextField TxtUsuarioPresta;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMensagens;
    // End of variables declaration//GEN-END:variables
}
