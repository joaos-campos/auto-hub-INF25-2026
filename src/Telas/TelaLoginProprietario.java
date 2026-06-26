package Telas;

import javax.swing.JOptionPane;

import java.sql.*;
import java.awt.Color;
import AcessoDB.ModuloDbConnect;

public class TelaLoginProprietario extends javax.swing.JFrame {
    
    // 2 - criar as variáveis necessárias à conexão
    Connection conexao = null;  // É a variável que retorna a conexao
    PreparedStatement pst = null; // É variável com o comando SQL
    ResultSet rs = null; // Variável com o resultado do comando executado 
        
    // 4 - Criar o método/rotina "logar()"
    public void  logar() {
        // Declarar a variável com o comando SQL do LOGIN!!!
        String sql = "select * from t_aah_proprietario where ds_nome_usuario = ? and ds_senha = ? ";
        // Fazer o acesso na tabela desejada
       
        try {
            // 5 - Não ocorrendo erro
            // Colocar o comando na conexao do banco e executá-lo
            pst = conexao.prepareStatement(sql);
            // Substituir as " ? " pelos campos da tela
            pst.setString(1, TxtUsuario.getText());
            pst.setString(2, TxtSenha.getText());
            // Executar a conexa....
            rs = pst.executeQuery();  // Faz a consulta no banco!
            // Verificar se encontrou o usuário e senha!
            if  (rs.next()){  // na Variável "rs", temos o resultado da consulta!
                // Se OK/encontrou... abaixo, cria-se a variável tlPrincipal
                // e carregamos nela a Classe "TelaPrincipal";
                TelaPrincipalProp tlPrincipalProp = new TelaPrincipalProp(rs.getString(2));
                // Com a Classe/TelaPrincipal na memória, devemos fazê-la visível!
                tlPrincipalProp.setVisible(true);
                this.dispose();  // O comando "this", acessar os atributos da Classe atual!                
            }else {
                JOptionPane.showMessageDialog(this, "Usuário/Senha INVÁLIDOS!!! Tente outra Vez! ");
                // Escrever na Tela, limpando o campo
                TxtUsuario.setText("");
                TxtSenha.setText("");                
            }
            
        }catch(Exception varERRO) {
            // Tratando o erro ao banco de dados
            JOptionPane.showMessageDialog(null,"Erro no acesso ao banco de dados: " + varERRO.toString() );
        }
    }
    
    public TelaLoginProprietario() {
        
        initComponents();
        
        conexao = ModuloDbConnect.connector();
        
         if (conexao != null) {
            lblMensagens.setText("Conexão OK!!!");
            lblMensagens.setForeground(Color.blue);
        }else {
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

        javax.swing.GroupLayout PnlLoginLayout = new javax.swing.GroupLayout(PnlLogin);
        PnlLogin.setLayout(PnlLoginLayout);
        PnlLoginLayout.setHorizontalGroup(
            PnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlLoginLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(PnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TxtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(LblSenha)
                    .addComponent(LblUsuario)
                    .addComponent(TxtSenha)
                    .addComponent(BtnEntrar)
                    .addComponent(BtnCadastrar))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        PnlLoginLayout.setVerticalGroup(
            PnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlLoginLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(LblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(LblSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtnEntrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnCadastrar)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        lblMensagens.setText("Mensagens...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PnlLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(lblMensagens)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addComponent(PnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMensagens)
                .addGap(16, 16, 16))
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
       TelaCadastroPropri telaCadUser = new TelaCadastroPropri();
       telaCadUser.setLocationRelativeTo(null);
       telaCadUser.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_BtnCadastrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            java.util.logging.Logger.getLogger(TelaLoginProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLoginProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLoginProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLoginProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLoginProprietario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCadastrar;
    private javax.swing.JButton BtnEntrar;
    private javax.swing.JLabel LblSenha;
    private javax.swing.JLabel LblUsuario;
    private javax.swing.JPanel PnlLogin;
    private javax.swing.JPasswordField TxtSenha;
    private javax.swing.JTextField TxtUsuario;
    private javax.swing.JLabel lblMensagens;
    // End of variables declaration//GEN-END:variables
}
