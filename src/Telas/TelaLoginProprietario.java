
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

public class TelaLoginProprietario extends javax.swing.JFrame {
    
    Connection conexao = null; 
    PreparedStatement pst = null; 
    ResultSet rs = null;
        
    public void  logar() 
    {
        String sql = "select * from t_aah_proprietario where ds_nome_usuario = ? and ds_senha = ? ";
       
        try 
        {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1, TxtUsuario.getText());
            pst.setString(2, TxtSenha.getText());
            
            rs = pst.executeQuery();
            
            if  (rs.next())
            { 
                TelaPrincipalProp tlPrincipalProp = new TelaPrincipalProp(rs.getString(2));
                tlPrincipalProp.setLocationRelativeTo(null);
                tlPrincipalProp.setVisible(true);
                this.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Usuário/Senha INVÁLIDOS!!! Tente outra Vez! ");
                TxtUsuario.setText("");
                TxtSenha.setText("");                
            }  
        }
        catch(Exception varERRO) 
        {
            JOptionPane.showMessageDialog(null,"Erro no acesso ao banco de dados: " + varERRO.toString() );
        }
    }
    
    public TelaLoginProprietario() 
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
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlLogin = new javax.swing.JPanel();
        LblUsuario = new javax.swing.JLabel();
        LblSenha = new javax.swing.JLabel();
        TxtUsuario = new javax.swing.JTextField();
        TxtSenha = new javax.swing.JPasswordField();
        BtnEntrar = new javax.swing.JButton();
        BtnCadastrar = new javax.swing.JButton();
        BtnVoltarLogProp = new javax.swing.JButton();
        lblMensagens = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login proprietário");
        setResizable(false);

        LblUsuario.setText("Usuário");

        LblSenha.setText("Senha");

        TxtUsuario.addActionListener(this::TxtUsuarioActionPerformed);

        BtnEntrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnEntrar.setText("Entrar");
        BtnEntrar.addActionListener(this::BtnEntrarActionPerformed);

        BtnCadastrar.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        BtnCadastrar.setText("Cadastrar-se");
        BtnCadastrar.addActionListener(this::BtnCadastrarActionPerformed);

        BtnVoltarLogProp.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        BtnVoltarLogProp.setText("Voltar");
        BtnVoltarLogProp.addActionListener(this::BtnVoltarLogPropActionPerformed);

        javax.swing.GroupLayout PnlLoginLayout = new javax.swing.GroupLayout(PnlLogin);
        PnlLogin.setLayout(PnlLoginLayout);
        PnlLoginLayout.setHorizontalGroup(
            PnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LblUsuario)
                    .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LblSenha)
                    .addComponent(TxtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEntrar)
                    .addGroup(PnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(BtnVoltarLogProp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnCadastrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        PnlLoginLayout.setVerticalGroup(
            PnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlLoginLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(LblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtnEntrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnCadastrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnVoltarLogProp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblMensagens.setText("Mensagens...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PnlLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblMensagens)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(PnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMensagens)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TxtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtUsuarioActionPerformed

    private void BtnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEntrarActionPerformed
        logar();
    }//GEN-LAST:event_BtnEntrarActionPerformed

    private void BtnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCadastrarActionPerformed
       
        TelaCadastroPropri telaCadUser;
                
        try 
        {
            telaCadUser = new TelaCadastroPropri();
            telaCadUser.setLocationRelativeTo(null);
            telaCadUser.setVisible(true);
            this.dispose();
        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(TelaLoginProprietario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnCadastrarActionPerformed

    private void BtnVoltarLogPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVoltarLogPropActionPerformed
        // TODO add your handling code here:
        TelaInicial telaInicio = new TelaInicial();
        telaInicio.setLocationRelativeTo(null);
        telaInicio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnVoltarLogPropActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TelaLoginProprietario().setVisible(true);
        });
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
            
            
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaLoginProprietario().setVisible(true));
    }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCadastrar;
    private javax.swing.JButton BtnEntrar;
    private javax.swing.JButton BtnVoltarLogProp;
    private javax.swing.JLabel LblSenha;
    private javax.swing.JLabel LblUsuario;
    private javax.swing.JPanel PnlLogin;
    private javax.swing.JPasswordField TxtSenha;
    private javax.swing.JTextField TxtUsuario;
    private javax.swing.JLabel lblMensagens;
    // End of variables declaration//GEN-END:variables
}
