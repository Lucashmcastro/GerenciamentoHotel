/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prjSistemaHoteis_Telas;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import prjSistemaHoteis_Connection.Modulo_ConexaoMySQL;

/**
 *
 * @author rapha
 */
public class Tela_AdmProdutosServicos extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void RemoverServico() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esse serviço ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from servico where CodServico=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCodServico.getText());
                int removido = pst.executeUpdate();
                if (removido > 0) {
                    JOptionPane.showMessageDialog(null, "Serviço removido do sistema com sucesso.");
                    cmbTipoServico.setSelectedIndex(0);
                    txtDescricao.setText(null);
                    txtCodServico.setText(null);
                    txtValor.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void EditarServico() {
        String sql = "update servico set TipoServico=?, Descricao=?, valorServico=? where CodServico=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cmbTipoServico.getSelectedItem().toString());
            pst.setString(2, txtDescricao.getText());
            pst.setDouble(3, Double.parseDouble(txtValor.getText()));
            pst.setString(4, txtCodServico.getText());

            int atualizado = pst.executeUpdate();
            if (atualizado > 0) {
                JOptionPane.showMessageDialog(null, "Serviço atualizado no sistema com sucesso.");
                cmbTipoServico.setSelectedIndex(0);
                txtDescricao.setText(null);
                txtCodServico.setText(null);
                txtValor.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void AdicionarServico() {
        String sql = "insert into servico (TipoServico, Descricao, valorServico) values (?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cmbTipoServico.getSelectedItem().toString());
            pst.setString(2, txtDescricao.getText());
            pst.setDouble(3, Double.parseDouble(txtValor.getText()));

            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Serviço cadastrado no sistema com sucesso.");
                cmbTipoServico.setSelectedIndex(0);
                txtDescricao.setText(null);
                txtCodServico.setText(null);
                txtValor.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void SetarCampos() {
        int setar = tabelaServicos.getSelectedRow();
        txtCodServico.setText(tabelaServicos.getModel().getValueAt(setar, 0).toString());
        cmbTipoServico.setSelectedItem(tabelaServicos.getModel().getValueAt(setar, 1).toString());
        txtDescricao.setText(tabelaServicos.getModel().getValueAt(setar, 2).toString());
        txtValor.setText(tabelaServicos.getModel().getValueAt(setar, 3).toString());
    }
    
    public void PesquisarServicosPorDescricao() {
        String sql = "select * from servico where Descricao like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisarServico.getText()+"%");
            rs = pst.executeQuery();
            tabelaServicos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void PesquisarServicos() {
        String sql = "select * from servico";
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1, txtPesquisarServico.getText()+"%");
            rs = pst.executeQuery();
            tabelaServicos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Setar_cmbTipoServico() {
        String sql = "select distinct TipoFuncionario from funcionario";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cmbTipoServico.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }

    public Tela_AdmProdutosServicos() {
        initComponents();
        conexao = Modulo_ConexaoMySQL.Conexao();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtPesquisarServico = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaServicos = new javax.swing.JTable();
        btnAddServico = new javax.swing.JButton();
        btnEdtServico = new javax.swing.JButton();
        btnRemServico = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        cmbTipoServico = new javax.swing.JComboBox<>();
        txtCodServico = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Administração - SERVIÇOS");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        txtPesquisarServico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarServicoKeyReleased(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/pesquisar.png"))); // NOI18N

        tabelaServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelaServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaServicosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaServicos);

        btnAddServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/plus.png"))); // NOI18N
        btnAddServico.setToolTipText("Adiiconar serviço");
        btnAddServico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddServico.setPreferredSize(new java.awt.Dimension(64, 64));
        btnAddServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddServicoActionPerformed(evt);
            }
        });

        btnEdtServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/exchange.png"))); // NOI18N
        btnEdtServico.setToolTipText("Editar serviço");
        btnEdtServico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdtServico.setPreferredSize(new java.awt.Dimension(64, 64));
        btnEdtServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdtServicoActionPerformed(evt);
            }
        });

        btnRemServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/x-button.png"))); // NOI18N
        btnRemServico.setToolTipText("Remover serviço");
        btnRemServico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemServico.setPreferredSize(new java.awt.Dimension(64, 64));
        btnRemServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemServicoActionPerformed(evt);
            }
        });

        jLabel2.setText("CódServiço:");

        jLabel3.setText("Tipo de serviço:");

        jLabel4.setText("Descrição:");

        jLabel7.setText("Valor unitário:");

        cmbTipoServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        txtCodServico.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(btnAddServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEdtServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(214, 214, 214)
                                .addComponent(btnRemServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel4))
                                        .addGap(13, 13, 13)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCodServico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtPesquisarServico, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(63, 63, 63)
                                        .addComponent(jLabel1)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(88, 88, 88))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisarServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdtServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        setBounds(0, 0, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Setar_cmbTipoServico();
        PesquisarServicos();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnAddServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddServicoActionPerformed
        AdicionarServico();
        PesquisarServicos();
    }//GEN-LAST:event_btnAddServicoActionPerformed

    private void tabelaServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaServicosMouseClicked
        SetarCampos();
        btnAddServico.setEnabled(false);
    }//GEN-LAST:event_tabelaServicosMouseClicked

    private void txtPesquisarServicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarServicoKeyReleased
        PesquisarServicosPorDescricao();
    }//GEN-LAST:event_txtPesquisarServicoKeyReleased

    private void btnEdtServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdtServicoActionPerformed
        EditarServico();
        PesquisarServicos();
        btnAddServico.setEnabled(true);
    }//GEN-LAST:event_btnEdtServicoActionPerformed

    private void btnRemServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemServicoActionPerformed
        RemoverServico();
        PesquisarServicos();
        btnAddServico.setEnabled(true);
    }//GEN-LAST:event_btnRemServicoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddServico;
    private javax.swing.JButton btnEdtServico;
    private javax.swing.JButton btnRemServico;
    private javax.swing.JComboBox<String> cmbTipoServico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaServicos;
    private javax.swing.JTextField txtCodServico;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtPesquisarServico;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
