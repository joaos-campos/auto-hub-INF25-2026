/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas;

import AcessoDB.ModuloDbConnect;
import AcessoDB.ModuloDbDML;
import static AutoHubUtil.ButtonsUtil.ActivateOnEnter;
import AutoHubUtil.LettersOnlyFilterUtil;
import AutoHubUtil.AlphanumericFilterUtil;
import AutoHubUtil.DateUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class TelaCadastroVeiculo extends javax.swing.JFrame {
    
    Connection conexao = null;
   
    private final int _idProprietarioAtual = TelaLoginProprietario.SessaoUsuario.getIdUsuarioLogado();
    private String _txtMarca;
    private String _txtModelo;
    private String _txtAno;
    private String _txtAnoModelo;
    private String _txtPlaca;
    private String _txtCor;
    
    public TelaCadastroVeiculo() 
    {
        if (ValidarSeUsuarioLogado())
        {
            initComponents();
            PostInitComponents();

            try {
                SetTextComponentsBehaviour();
            } catch (ParseException ex) {
                Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean ValidarSeUsuarioLogado()
    {
        if (!(_idProprietarioAtual > 0))
        {
            System.out.println(_idProprietarioAtual);
            JOptionPane.showMessageDialog(this, "Você precisa estar logado para fazer o cadastro de veículos! Você será redirecionado para a página de login!");
            TelaLoginProprietario tlLgProp = new TelaLoginProprietario();
            tlLgProp.setLocationRelativeTo(null);
            tlLgProp.setVisible(true);
            this.dispose();
            return false;
        }
        return true;
    }
    
    private void PostInitComponents()
    {
        ActivateOnEnter(BtnCadastrar);
        ActivateOnEnter(BtnVoltar);
        
        CbMarca.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    CbMarca.showPopup();
                    e.consume();
                }
            }
        });
        
        conexao = ModuloDbConnect.connector();
        CarregarMarcas();
        this.getContentPane().setBackground(new Color(198, 200, 200));      
        this.setSize(575, 650);
    }
    
    private void CarregarMarcas()
    {
        ModuloDbDML db = new ModuloDbDML(conexao);
        
        List<Map<String, Object>> resultado;
        
        try 
        {
            CbMarca.removeAllItems();
            
            resultado = db.consultar("select nm_marca from t_aah_marca order by nm_marca asc");
            System.out.println("carregarMarcas rodou, linhas = " + resultado.size());
            
            for (Map<String, Object> linha : resultado) 
            {
                Object valor = linha.get("nm_marca");
                
                if (valor != null) 
                {
                    CbMarca.addItem(valor.toString());
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void SetTextComponentsBehaviour() throws ParseException
    {
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtPlaca"> 
        // =================================================================================
        //TxtPlaca.setForeground(Color.GRAY);
        MaskFormatter formatterPlaca = new MaskFormatter("UUU#U##");
        char placaPlaceholder = '_';
        formatterPlaca.setPlaceholderCharacter(placaPlaceholder);
        formatterPlaca.setCommitsOnValidEdit(true);
        //TxtPlaca.setFont(new Font("Monospaced", Font.BOLD, 12));
        TxtPlaca.setColumns(10);
        TxtPlaca.setFormatterFactory(new DefaultFormatterFactory(formatterPlaca));
        TxtPlaca.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        TxtPlaca.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (!(TxtPlaca.getText().indexOf('_') >= 0)) 
                {
                    SwingUtilities.invokeLater(() -> 
                    {
                        TxtPlaca.setCaretPosition(0);
                        TxtPlaca.setForeground(Color.BLACK);
                    });
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtPlaca.getText().isEmpty() || TxtPlaca.getText().isBlank() || (TxtPlaca.getText().indexOf('_') >= 0)) 
                {
                    TxtPlaca.setForeground(Color.GRAY);
                }
            }
        });
        // =================================================================================
         // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtAno"> 
        // =================================================================================
        TxtAno.setForeground(Color.GRAY);
        MaskFormatter formatterAno = new MaskFormatter("####");
        char yearCharPlaceholder = '0';
        String yearPlaceholder = "0000";
        formatterAno.setPlaceholderCharacter(yearCharPlaceholder);
        formatterAno.setCommitsOnValidEdit(true);
        TxtAno.setColumns(4);
        TxtAno.setFormatterFactory(new DefaultFormatterFactory(formatterAno));
        TxtAno.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        TxtAno.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtAno.getText().equals(yearPlaceholder)) 
                {
                    SwingUtilities.invokeLater(() -> 
                    {
                        TxtAno.setCaretPosition(0);
                        TxtAno.setForeground(Color.BLACK);
                    });
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtAno.getText().isEmpty() || TxtAno.getText().equals(yearPlaceholder)) 
                {
                    TxtAno.setText(yearPlaceholder);
                    TxtAno.setForeground(Color.GRAY);
                }
            }
        });
        // =================================================================================
         // </editor-fold>
         
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtAnoModelo"> 
        // =================================================================================
        TxtAnoModelo.setForeground(Color.GRAY);
        formatterAno.setPlaceholderCharacter(yearCharPlaceholder);
        formatterAno.setCommitsOnValidEdit(true);
        TxtAnoModelo.setColumns(4);
        TxtAnoModelo.setFormatterFactory(new DefaultFormatterFactory(formatterAno));
        TxtAnoModelo.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        TxtAnoModelo.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if (TxtAnoModelo.getText().equals(yearPlaceholder)) 
                {
                    SwingUtilities.invokeLater(() -> 
                    {
                        TxtAnoModelo.setCaretPosition(0);
                        TxtAnoModelo.setForeground(Color.BLACK);
                    });
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) 
            {
                if (TxtAnoModelo.getText().isEmpty() || TxtAnoModelo.getText().equals(yearPlaceholder)) 
                {
                    TxtAnoModelo.setText(yearPlaceholder);
                    TxtAnoModelo.setForeground(Color.GRAY);
                }
            }
        });
        // =================================================================================
         // </editor-fold>
         
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtCor"> 
        // ================================================================================= 
        int modeloCorLenght = 45;
        
        ((AbstractDocument) TxtCor.getDocument()).setDocumentFilter(new LettersOnlyFilterUtil(modeloCorLenght));
        // =================================================================================
         // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Define comportamento TxtModelo"> 
        // =================================================================================  
        int modeloMaxLength = 80;
        ((AbstractDocument) TxtModelo.getDocument()).setDocumentFilter(new AlphanumericFilterUtil(modeloMaxLength));
        // =================================================================================
         // </editor-fold>
    }
    
    private boolean ValidarAnoAnoModelo()
    {
        if ((TxtAno.getText().isBlank()) || (TxtAno.getText().isEmpty()) || (TxtAno.getText().equals("0000")))
        {
            JOptionPane.showMessageDialog(this, "Digite um ano válido!");
            TxtAno.requestFocus();
            return false;
        }
        
        if ((TxtAnoModelo.getText().isBlank()) || (TxtAnoModelo.getText().isEmpty()) || (TxtAnoModelo.getText().equals("0000")))
        {
            JOptionPane.showMessageDialog(this, "Digite um ano-modelo válido!");
            TxtAnoModelo.requestFocus();
            return false;
        }
        
        int anoAtual = Year.now().getValue();
        int anoVeiculo = Integer.parseInt(TxtAno.getValue().toString());
        int anoModeloVeiculo = Integer.parseInt(TxtAnoModelo.getValue().toString());
        
        if (anoVeiculo > anoAtual)
        {
            JOptionPane.showMessageDialog(this, "O ano de fabricação do veículo não pode ser maior que o ano atual! Digite um ano válido!");
            TxtAno.requestFocus();
            return false;
        }
        
        if (anoModeloVeiculo > anoAtual + 1)
        {
            JOptionPane.showMessageDialog(this, "O ano-modelo do veículo não pode ser mais que um ano maior que o ano atual! Digite um ano-modelo válido!");
            TxtAnoModelo.requestFocus();
            return false;
        }
        
        if ((anoModeloVeiculo < anoVeiculo))
        {
            JOptionPane.showMessageDialog(this, "O ano-modelo do véiculo não pode ser menor que o ano de fabricação do veículo! Digite um ano-modelo válido!");
            TxtAnoModelo.requestFocus();
            return false;
        }
        
        if (anoModeloVeiculo > anoVeiculo + 1)
        {
            JOptionPane.showMessageDialog(this, "O ano-modelo do véiculo não pode ter mais que um ano de diferença que o ano de fabricação do veículo! Digite um ano-modelo válido!");
            TxtAnoModelo.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private boolean ValidarModelo()
    {
        if (TxtModelo.getText().length() > 80)
        {
            JOptionPane.showMessageDialog(this, "Nome do modelo é muito comprido! Digite um modelo com menos de 80 caracters!");
            TxtModelo.requestFocus();
            return false;
        }
        return true;
    }
    
    private boolean ValidarCor()
    {
        if (TxtCor.getText().length() > 45)
        {
            JOptionPane.showMessageDialog(this, "Nome da cor do veículo é muito comprido! Digite uma cor com menos de 45 caracters!");
            TxtCor.requestFocus();
            return false;
        }
        return true;
    }
    
    private void LimparTela()
    {
        try {
            SetTextComponentsBehaviour();
        } catch (ParseException ex) {
            Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CbMarca.setSelectedIndex(0);
        CbMarca.requestFocus();  
        
        TxtPlaca.setValue(null);
        TxtAno.setValue(null);
        TxtAnoModelo.setValue(null);
        TxtCor.setText("");
        TxtCor.setCaretPosition(0);
        TxtModelo.setText("");
        TxtModelo.setCaretPosition(0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        LblMarca = new javax.swing.JLabel();
        LblModelo = new javax.swing.JLabel();
        TxtModelo = new javax.swing.JTextField();
        LblAno = new javax.swing.JLabel();
        LblPlaca = new javax.swing.JLabel();
        LblCor = new javax.swing.JLabel();
        TxtCor = new javax.swing.JTextField();
        BtnCadastrar = new javax.swing.JButton();
        BtnVoltar = new javax.swing.JButton();
        CbMarca = new javax.swing.JComboBox<>();
        TxtAno = new javax.swing.JFormattedTextField();
        TxtPlaca = new javax.swing.JFormattedTextField();
        LblAnoModelo = new javax.swing.JLabel();
        TxtAnoModelo = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastre seu veículo");
        setBackground(new java.awt.Color(198, 200, 200));
        setForeground(java.awt.Color.white);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(198, 200, 200));

        LblMarca.setText("Marca");

        LblModelo.setText("Modelo");

        LblAno.setText("Ano");

        LblPlaca.setText("Placa");

        LblCor.setText("Cor");

        BtnCadastrar.setBackground(new java.awt.Color(255, 212, 59));
        BtnCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnCadastrar.setForeground(new java.awt.Color(33, 40, 68));
        BtnCadastrar.setText("Cadastrar");
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

        CbMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbMarcaActionPerformed(evt);
            }
        });

        TxtAno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####"))));
        TxtAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtAnoActionPerformed(evt);
            }
        });

        TxtPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtPlacaActionPerformed(evt);
            }
        });

        LblAnoModelo.setText("Ano-modelo");

        TxtAnoModelo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        TxtAnoModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtAnoModeloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CbMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TxtModelo)
                    .addComponent(BtnCadastrar, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .addComponent(BtnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TxtAno)
                    .addComponent(TxtAnoModelo)
                    .addComponent(TxtCor)
                    .addComponent(TxtPlaca, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblMarca)
                            .addComponent(LblModelo)
                            .addComponent(LblAno)
                            .addComponent(LblAnoModelo)
                            .addComponent(LblPlaca)
                            .addComponent(LblCor))
                        .addGap(0, 504, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblMarca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblModelo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblAno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LblAnoModelo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtAnoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LblPlaca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblCor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addComponent(BtnCadastrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnVoltar)
                .addGap(38, 38, 38))
        );

        jLabel2.setBackground(new java.awt.Color(32, 32, 63));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(247, 250, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cadastre seu veículo");
        jLabel2.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVoltarActionPerformed
        TelaPrincipalProp telaPrincipal = new TelaPrincipalProp();
        telaPrincipal.setLocationRelativeTo(null);
        telaPrincipal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnVoltarActionPerformed

    private void CbMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbMarcaActionPerformed

    }//GEN-LAST:event_CbMarcaActionPerformed

    private void TxtAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtAnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtAnoActionPerformed

    private void TxtPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtPlacaActionPerformed

    private void BtnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCadastrarActionPerformed
        if (_idProprietarioAtual > 0)
        {
            if (ValidarAnoAnoModelo() && ValidarModelo() && ValidarCor())
            {
                _txtMarca = CbMarca.getSelectedItem().toString();
                _txtModelo = TxtModelo.getText();
                _txtAno = TxtAno.getValue().toString();
                _txtAnoModelo = TxtAnoModelo.getValue().toString();
                _txtPlaca = TxtPlaca.getValue().toString();
                _txtCor = TxtCor.getText();

                //System.out.println(_txtMarca + " " + _txtModelo + " " + _txtAno + " " + _txtAnoModelo + " " + _txtPlaca + " " + _txtCor);

                ModuloDbDML db = new ModuloDbDML(conexao);

                String sql = "SELECT ds_placa FROM t_aah_veiculo WHERE ds_placa = ?";
        
                boolean ehPlacaRepetida = false;

                try 
                {
                    ResultSet rs = db.consultarResultSet(sql, _txtPlaca); 

                    if (rs.next())
                    {
                        ehPlacaRepetida = true;
                        System.out.println("placa repetida");
                    }
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (ehPlacaRepetida == false)
                {
                    long _novoIdVeiculo = db.inserirRetornandoId("INSERT INTO t_aah_veiculo (id_marca, ds_modelo, nr_ano, nr_ano_modelo, ds_placa, ds_cor) "
                            + " VALUES ((SELECT id_marca FROM t_aah_marca WHERE nm_marca = ?), ?, ?, ?, ?, ?);", 
                            _txtMarca, _txtModelo, _txtAno, _txtAnoModelo, _txtPlaca, _txtCor);

                    if (_novoIdVeiculo != -1) 
                    {
                        LocalDate dataHoje = LocalDate.now();

                        long _novaPropriedadeVeiculo = db.inserirRetornandoId("INSERT INTO t_aah_proprietario_veiculo (id_proprietario, id_veiculo, dt_inicio, ds_status) "
                            + " VALUES (?, ?, ?, ?);",
                            _idProprietarioAtual, _novoIdVeiculo, dataHoje, 1);

                        System.out.println(_idProprietarioAtual + " " + _novoIdVeiculo + " " + dataHoje);

                        if (_novaPropriedadeVeiculo != -1)
                        {
                            JOptionPane.showMessageDialog(null, "Cadastro do veículo " + _txtModelo + " efetuado com sucesso!");

                            LimparTela();
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "A placa " + _txtPlaca + " já está cadastrada para outro veículo! Confira os dados e tente novamente!");
                    TxtPlaca.requestFocus();
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Usuário não autenticado! Você será redirecionado para a tela de Login!");
            TelaLoginProprietario tlLgProp = new TelaLoginProprietario();
            tlLgProp.setLocationRelativeTo(null);
            tlLgProp.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_BtnCadastrarActionPerformed

    private void TxtAnoModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtAnoModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtAnoModeloActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastroVeiculo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCadastrar;
    private javax.swing.JButton BtnVoltar;
    private javax.swing.JComboBox<String> CbMarca;
    private javax.swing.JLabel LblAno;
    private javax.swing.JLabel LblAnoModelo;
    private javax.swing.JLabel LblCor;
    private javax.swing.JLabel LblMarca;
    private javax.swing.JLabel LblModelo;
    private javax.swing.JLabel LblPlaca;
    private javax.swing.JFormattedTextField TxtAno;
    private javax.swing.JFormattedTextField TxtAnoModelo;
    private javax.swing.JTextField TxtCor;
    private javax.swing.JTextField TxtModelo;
    private javax.swing.JFormattedTextField TxtPlaca;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
