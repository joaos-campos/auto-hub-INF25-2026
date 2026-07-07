/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas;

import AcessoDB.ModuloDbConnect;
import AcessoDB.ModuloDbDML;
import AutoHubUtil.DateUtil;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ALUNO
 */
public class TelaPrincipalProp extends javax.swing.JFrame {

    private final int _idProprietarioAtual = TelaLoginProprietario.SessaoUsuario.getIdUsuarioLogado();
    
    Connection conexao = null;
    
    public TelaPrincipalProp()
    {
        initComponents();
        postInitComponents();
        GetNomeProprietario();
        PopulateTableVeiculos();
        ReturnCountTotalVeiculos();
    }
    
    private void PopulateTableVeiculos()
    {
        try {
            conexao = ModuloDbConnect.connector();
            
            ModuloDbDML dbDML = new ModuloDbDML(conexao);
            
            DefaultTableModel tableResultado = dbDML.consultarTabela
                (
                        " SELECT " +
                                " t_aah_marca.nm_marca as Marca, " +
                                " t_aah_veiculo.ds_modelo as Modelo, " +
                                " t_aah_veiculo.ds_cor as Cor, " +
                                " t_aah_veiculo.ds_placa as Placa " +
                                " FROM t_aah_marca" +
                                " INNER JOIN t_aah_veiculo ON t_aah_marca.id_marca = t_aah_veiculo.id_marca " +
                                " INNER JOIN t_aah_proprietario_veiculo ON t_aah_veiculo.id_veiculo = t_aah_proprietario_veiculo.id_veiculo " +
                                " INNER JOIN t_aah_proprietario ON t_aah_proprietario.id_proprietario = t_aah_proprietario_veiculo.id_proprietario " +
                                " AND t_aah_proprietario.id_proprietario = ? ORDER BY t_aah_proprietario_veiculo.id_propriedade ASC;", _idProprietarioAtual
                );
            
            TblVeiculos.setModel(tableResultado);
        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipalProp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ReturnCountTotalVeiculos()
    {
        conexao = ModuloDbConnect.connector();
        
        ModuloDbDML dbDML = new ModuloDbDML(conexao);
        
        String sql =    "SELECT COUNT(v.id_veiculo) as Total " +
                        "FROM t_aah_marca m " +
                        "INNER JOIN t_aah_veiculo v ON m.id_marca = v.id_marca " +
                        "INNER JOIN t_aah_proprietario_veiculo pv ON v.id_veiculo = pv.id_veiculo " +
                        "INNER JOIN t_aah_proprietario p ON p.id_proprietario = pv.id_proprietario " +
                        "AND p.id_proprietario = ?;";
        
        try 
        {  
            ResultSet rs = dbDML.consultarResultSet(sql, _idProprietarioAtual); 
            
            if (rs.next()) 
            {
                this.lblTotalVeiculos.setText(this.lblTotalVeiculos.getText() + rs.getString("Total"));
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
    
    private void GetNomeProprietario()
    {
        conexao = ModuloDbConnect.connector();
        
        ModuloDbDML dbDML = new ModuloDbDML(conexao);
        
        String sql = "SELECT nm_proprietario FROM t_aah_proprietario WHERE id_proprietario = ?";
        
        try 
        {  
            ResultSet rs = dbDML.consultarResultSet(sql, _idProprietarioAtual); 
            
            if (rs.next()) 
            {
                this.lblNmUsuario.setText(this.lblNmUsuario.getText() + rs.getString("nm_proprietario"));
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

    private void postInitComponents()
    {
        this.getContentPane().setBackground(new java.awt.Color(198, 200, 200));
        Color corMenu = new Color(32, 32, 63);
        MenuPrincipal.setBackground(corMenu);
        MenuPrincipal.setUI(new BasicMenuBarUI());
        MenuVeiculos.setForeground(Color.WHITE);
        MenuAgendamento.setForeground(Color.WHITE);
        MenuLembretes.setForeground(Color.WHITE);
        MenuPerfil.setForeground(Color.WHITE);
        setMenuBarFont(MenuPrincipal, new Font("Segoe UI", Font.BOLD, 16));
        applyFontToMenu(MenuVeiculos, new Font("Segoe UI", Font.BOLD, 16));
        this.setSize(575, 650);
        this.revalidate();
        this.repaint();
    }
    
    public static void setMenuBarFont(JMenuBar menuBar, Font font) 
    {
        for (int i = 0; i < menuBar.getMenuCount(); i++) 
        {
            JMenu menu = menuBar.getMenu(i);
            if (menu != null) 
            {
                menu.setFont(font);           // the top-level menu label
                applyFontToMenu(menu, font);  // its items
            }
        }
    }

    private static void applyFontToMenu(JMenu menu, Font font) 
    {
        for (int i = 0; i < menu.getItemCount(); i++) 
        {
            JMenuItem item = menu.getItem(i);
            if (item != null) 
            {               // null = separator
                item.setFont(font);
                if (item instanceof JMenu) {  // a submenu — recurse
                    applyFontToMenu((JMenu) item, font);
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnGerenciar1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblVeiculos = new javax.swing.JTable();
        lblVeiculos = new javax.swing.JLabel();
        lblNmUsuario = new javax.swing.JLabel();
        btnGerenciarVeiculos = new javax.swing.JButton();
        lblAgendamentos = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAgendamentos = new javax.swing.JTable();
        lblTotalVeiculos = new javax.swing.JLabel();
        lblTotalAgendamentos = new javax.swing.JLabel();
        btnGerenciarAgendamentos = new javax.swing.JButton();
        MenuPrincipal = new javax.swing.JMenuBar();
        MenuVeiculos = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        MenuAgendamento = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        MenuLembretes = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        MenuPerfil = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();

        jLabel3.setBackground(new java.awt.Color(32, 32, 63));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(247, 250, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Meus veículos");
        jLabel3.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(32, 32, 63));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(247, 250, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Total de veículos: ");
        jLabel5.setOpaque(true);

        btnGerenciar1.setBackground(new java.awt.Color(239, 60, 0));
        btnGerenciar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGerenciar1.setForeground(new java.awt.Color(33, 40, 68));
        btnGerenciar1.setText("Gerenciar");
        btnGerenciar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerenciar1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela principal");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(198, 200, 200));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 575, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        TblVeiculos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TblVeiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Meus veículos", "Marca", "Ano", "Placa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblVeiculos.setShowGrid(true);
        TblVeiculos.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(TblVeiculos);
        if (TblVeiculos.getColumnModel().getColumnCount() > 0) {
            TblVeiculos.getColumnModel().getColumn(0).setResizable(false);
            TblVeiculos.getColumnModel().getColumn(1).setResizable(false);
            TblVeiculos.getColumnModel().getColumn(2).setResizable(false);
            TblVeiculos.getColumnModel().getColumn(3).setResizable(false);
        }

        lblVeiculos.setBackground(new java.awt.Color(32, 32, 63));
        lblVeiculos.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblVeiculos.setForeground(new java.awt.Color(247, 250, 255));
        lblVeiculos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVeiculos.setText("Meus veículos");
        lblVeiculos.setOpaque(true);

        lblNmUsuario.setBackground(new java.awt.Color(255, 212, 59));
        lblNmUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNmUsuario.setForeground(new java.awt.Color(2, 2, 3));
        lblNmUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNmUsuario.setText("Usuário: ");
        lblNmUsuario.setOpaque(true);

        btnGerenciarVeiculos.setBackground(new java.awt.Color(255, 212, 59));
        btnGerenciarVeiculos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGerenciarVeiculos.setForeground(new java.awt.Color(33, 40, 68));
        btnGerenciarVeiculos.setText("Gerenciar");
        btnGerenciarVeiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerenciarVeiculosActionPerformed(evt);
            }
        });

        lblAgendamentos.setBackground(new java.awt.Color(32, 32, 63));
        lblAgendamentos.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblAgendamentos.setForeground(new java.awt.Color(247, 250, 255));
        lblAgendamentos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAgendamentos.setText("Meus agendamentos");
        lblAgendamentos.setOpaque(true);

        tblAgendamentos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblAgendamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Data da solicitação", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAgendamentos.setShowGrid(true);
        tblAgendamentos.setSurrendersFocusOnKeystroke(true);
        jScrollPane2.setViewportView(tblAgendamentos);
        if (tblAgendamentos.getColumnModel().getColumnCount() > 0) {
            tblAgendamentos.getColumnModel().getColumn(0).setResizable(false);
            tblAgendamentos.getColumnModel().getColumn(1).setResizable(false);
        }

        lblTotalVeiculos.setBackground(new java.awt.Color(32, 32, 63));
        lblTotalVeiculos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalVeiculos.setForeground(new java.awt.Color(247, 250, 255));
        lblTotalVeiculos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalVeiculos.setText("Total: ");
        lblTotalVeiculos.setOpaque(true);

        lblTotalAgendamentos.setBackground(new java.awt.Color(32, 32, 63));
        lblTotalAgendamentos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalAgendamentos.setForeground(new java.awt.Color(247, 250, 255));
        lblTotalAgendamentos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalAgendamentos.setText("Total: ");
        lblTotalAgendamentos.setOpaque(true);

        btnGerenciarAgendamentos.setBackground(new java.awt.Color(255, 212, 59));
        btnGerenciarAgendamentos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGerenciarAgendamentos.setForeground(new java.awt.Color(33, 40, 68));
        btnGerenciarAgendamentos.setText("Gerenciar");
        btnGerenciarAgendamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerenciarAgendamentosActionPerformed(evt);
            }
        });

        MenuPrincipal.setBackground(new java.awt.Color(32, 32, 63));
        MenuPrincipal.setForeground(new java.awt.Color(255, 255, 255));
        MenuPrincipal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        MenuPrincipal.setOpaque(true);

        MenuVeiculos.setText("Veículos");

        jMenuItem1.setText("Acessar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MenuVeiculos.add(jMenuItem1);

        jMenuItem2.setText("Cadastrar veículo");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        MenuVeiculos.add(jMenuItem2);

        jMenuItem6.setText("Gerenciar veículo");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        MenuVeiculos.add(jMenuItem6);

        MenuPrincipal.add(MenuVeiculos);

        MenuAgendamento.setText("Agendamento");

        jMenuItem3.setText("Acessar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        MenuAgendamento.add(jMenuItem3);

        MenuPrincipal.add(MenuAgendamento);

        MenuLembretes.setText("Lembretes");

        jMenuItem5.setText("Acessar");
        MenuLembretes.add(jMenuItem5);

        jMenuItem4.setText("Adicionar");
        MenuLembretes.add(jMenuItem4);

        MenuPrincipal.add(MenuLembretes);

        MenuPerfil.setText("Perfil");

        jMenuItem7.setText("Acessar");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        MenuPerfil.add(jMenuItem7);

        jMenuItem8.setText("Sair");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        MenuPerfil.add(jMenuItem8);

        MenuPrincipal.add(MenuPerfil);

        setJMenuBar(MenuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                    .addComponent(lblVeiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                    .addComponent(lblAgendamentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblNmUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblTotalVeiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGerenciarVeiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTotalAgendamentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGerenciarAgendamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(162, Short.MAX_VALUE)
                .addComponent(lblNmUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblVeiculos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalVeiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerenciarVeiculos))
                .addGap(54, 54, 54)
                .addComponent(lblAgendamentos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalAgendamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerenciarAgendamentos))
                .addGap(39, 39, 39)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        TelaInicial telaInicio = new TelaInicial();
        telaInicio.setLocationRelativeTo(null);
        telaInicio.setVisible(true);
        TelaLoginProprietario.SessaoUsuario.setIdUsuarioLogado(0);
        this.dispose();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        TelaCadastroVeiculo telaCadVei = new TelaCadastroVeiculo();
        telaCadVei.setLocationRelativeTo(null);
        telaCadVei.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        try {
            TelaVeiculo telaGerencia = new TelaVeiculo();
            telaGerencia.setLocationRelativeTo(null);
            telaGerencia.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipalProp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            TelaVeiculo telaVeiculos = new TelaVeiculo();
            telaVeiculos.setLocationRelativeTo(null);
            telaVeiculos.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipalProp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        TelaAgendamento telaAgenda = new TelaAgendamento();
        telaAgenda.setLocationRelativeTo(null);
        telaAgenda.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        TelaUsuPropri telaperfil;
        try {
            telaperfil = new TelaUsuPropri();
            telaperfil.setLocationRelativeTo(null);
            telaperfil.setVisible(true);
            this.dispose();
        } catch (ParseException ex) {
            Logger.getLogger(TelaPrincipalProp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void btnGerenciarVeiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerenciarVeiculosActionPerformed
        try {
            TelaVeiculo tlVeic = new TelaVeiculo();
            tlVeic.setLocationRelativeTo(null);
            tlVeic.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipalProp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGerenciarVeiculosActionPerformed

    private void btnGerenciar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerenciar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGerenciar1ActionPerformed

    private void btnGerenciarAgendamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerenciarAgendamentosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGerenciarAgendamentosActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipalProp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipalProp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipalProp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipalProp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipalProp().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MenuAgendamento;
    private javax.swing.JMenu MenuLembretes;
    private javax.swing.JMenu MenuPerfil;
    private javax.swing.JMenuBar MenuPrincipal;
    private javax.swing.JMenu MenuVeiculos;
    private javax.swing.JTable TblVeiculos;
    private javax.swing.JButton btnGerenciar1;
    private javax.swing.JButton btnGerenciarAgendamentos;
    private javax.swing.JButton btnGerenciarVeiculos;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAgendamentos;
    private javax.swing.JLabel lblNmUsuario;
    private javax.swing.JLabel lblTotalAgendamentos;
    private javax.swing.JLabel lblTotalVeiculos;
    private javax.swing.JLabel lblVeiculos;
    private javax.swing.JTable tblAgendamentos;
    // End of variables declaration//GEN-END:variables
}
