package prjSistemaHoteis_Telas;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import prjSistemaHoteis_Connection.Modulo_ConexaoMySQL;

public class Tela_MenuHospedes extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void RemoverDependente() {

        String sql = "delete from dependente where CPFHospede=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCPFDependente.getText());
            int removido = pst.executeUpdate();
            if (removido > 0) {
                txtCPFDependente.setText(null);
                txtNomeDependente.setText(null);
                txtParDependente.setText(null);
                cmbSexoDependente.setSelectedIndex(0);
                txtDataDependente.setDate(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void RemoverHospede() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esse hóspede ?", "Atenção !", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from hospede where CPFHospede=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCPFHospede.getText());
                int removido = pst.executeUpdate();
                if (removido > 0) {
                    txtCPFHospede.setText(null);
                    txtNomeHospede.setText(null);
                    txtTelHospede.setText(null);
                    txtEndHospede.setText(null);
                    cmbSexoHospede.setSelectedIndex(0);
                    txtDataHospede.setDate(null);
                    txtCPFDependente.setText(null);
                    txtCPFHospede.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void EditarDependente() {
        String sql = "update dependente set NomeDependente=?, ParentescoDependente=?, SexoDependente=?, DataNascimentoDependente=? where CPFHospede=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtNomeDependente.getText());
            pst.setString(2, txtParDependente.getText());
            pst.setString(3, cmbSexoDependente.getSelectedItem().toString());

            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataDependente = DataBaseDate.format(txtDataDependente.getDate());
            
            pst.setString(4, DataDependente);
            pst.setString(5, txtCPFHospede.getText());

            if ((txtCPFHospede.getText().isEmpty()) || (txtNomeHospede.getText().isEmpty()) || (txtTelHospede.getText().isEmpty()) || (txtEndHospede.getText().isEmpty()) || (cmbSexoHospede.getSelectedItem().toString().isEmpty()) || (txtDataHospede.getDate().toString().isEmpty()) || (txtCPFDependente.getText().isEmpty()) || (txtNomeDependente.getText().isEmpty()) || (txtParDependente.getText().isEmpty()) || (cmbSexoDependente.getSelectedItem().toString().isEmpty()) || (txtDataDependente.getDate().toString().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos do hospede");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dependente atualizado no sistema com sucesso.");
                    txtCPFDependente.setText(null);
                    txtNomeDependente.setText(null);
                    txtParDependente.setText(null);
                    cmbSexoDependente.setSelectedIndex(0);
                    txtDataDependente.setDate(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void EditarHospede() {
        String sql = "update hospede set NomeHospede=?, TelefoneHospede=?, EnderecoHospede=?, SexoHospede=?, DataNascHospede=? where CPFHospede=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeHospede.getText());
            pst.setString(2, txtTelHospede.getText());
            pst.setString(3, txtEndHospede.getText());
            pst.setString(4, cmbSexoHospede.getSelectedItem().toString());

            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataHospede = DataBaseDate.format(txtDataHospede.getDate());
            pst.setString(5, DataHospede);
            pst.setString(6, txtCPFHospede.getText());

            if ((txtCPFHospede.getText().isEmpty()) || (txtNomeHospede.getText().isEmpty()) || (txtTelHospede.getText().isEmpty()) || (txtEndHospede.getText().isEmpty()) || (cmbSexoHospede.getSelectedItem().toString().isEmpty()) || txtDataHospede.getDate().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos do hospede");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Hospede atualizado no sistema com sucesso.");
                    txtCPFHospede.setText(null);
                    txtNomeHospede.setText(null);
                    txtTelHospede.setText(null);
                    txtEndHospede.setText(null);
                    cmbSexoHospede.setSelectedIndex(0);
                    txtDataHospede.setDate(null);
                    txtCPFDependente.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void AdicionarDependente() {
        String sql = "insert into dependente (CPFHospede, NomeDependente, ParentescoDependente, SexoDependente, DataNascimentoDependente) values (?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCPFDependente.getText());
            pst.setString(2, txtNomeDependente.getText());
            pst.setString(3, txtParDependente.getText());
            pst.setString(4, cmbSexoDependente.getSelectedItem().toString());

            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataDependente = DataBaseDate.format(txtDataDependente.getDate());
            pst.setString(5, DataDependente);

            if ((txtCPFHospede.getText().isEmpty()) || (txtNomeHospede.getText().isEmpty()) || (txtTelHospede.getText().isEmpty()) || (txtEndHospede.getText().isEmpty()) || (cmbSexoHospede.getSelectedItem().toString().isEmpty()) || (txtDataHospede.getDate().toString().isEmpty()) || (txtCPFDependente.getText().isEmpty()) || (txtNomeDependente.getText().isEmpty()) || (txtParDependente.getText().isEmpty()) || (cmbSexoDependente.getSelectedItem().toString().isEmpty()) || (txtDataDependente.getDate().toString().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos do hospede");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dependente adicionado no sistema com sucesso.");
                    txtCPFDependente.setText(null);
                    txtNomeDependente.setText(null);
                    txtParDependente.setText(null);
                    cmbSexoDependente.setSelectedIndex(0);
                    txtDataDependente.setDate(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void AdicionarHospede() {
        String sql = "insert into hospede (CPFHospede, NomeHospede, TelefoneHospede, EnderecoHospede, SexoHospede, DataNascHospede) values (?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCPFHospede.getText());
            pst.setString(2, txtNomeHospede.getText());
            pst.setString(3, txtTelHospede.getText());
            pst.setString(4, txtEndHospede.getText());
            pst.setString(5, cmbSexoHospede.getSelectedItem().toString());

            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataHospedeFormatada = DataBaseDate.format(txtDataHospede.getDate());
            pst.setString(6, DataHospedeFormatada);

            if ((txtCPFHospede.getText().isEmpty()) || (txtNomeHospede.getText().isEmpty()) || (txtTelHospede.getText().isEmpty()) || (txtEndHospede.getText().isEmpty()) || (cmbSexoHospede.getSelectedItem().toString().isEmpty()) || txtDataHospede.getDate().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos do hospede");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Hospede adicionado no sistema com sucesso.");
                    txtCPFHospede.setText(null);
                    txtNomeHospede.setText(null);
                    txtTelHospede.setText(null);
                    txtEndHospede.setText(null);
                    cmbSexoHospede.setSelectedIndex(0);
                    txtDataHospede.setDate(null);
                    txtCPFDependente.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void SetarCamposHospedes() {
        int setar = tabelaHospedes.getSelectedRow();
        txtCPFHospede.setText(tabelaHospedes.getModel().getValueAt(setar, 0).toString());
        txtCPFDependente.setText(tabelaHospedes.getModel().getValueAt(setar, 0).toString());
        txtNomeHospede.setText(tabelaHospedes.getModel().getValueAt(setar, 1).toString());
        txtTelHospede.setText(tabelaHospedes.getModel().getValueAt(setar, 2).toString());
        txtEndHospede.setText(tabelaHospedes.getModel().getValueAt(setar, 3).toString());
        cmbSexoHospede.setSelectedItem(tabelaHospedes.getModel().getValueAt(setar, 4));
        try {
            txtDataHospede.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(tabelaHospedes.getModel().getValueAt(setar, 5).toString()));

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void SetarCamposDependente() {
        int setar = tabelaDependentes.getSelectedRow();
        txtCPFDependente.setText(tabelaDependentes.getModel().getValueAt(setar, 0).toString());
        txtNomeDependente.setText(tabelaDependentes.getModel().getValueAt(setar, 1).toString());
        try {
            txtDataDependente.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(tabelaDependentes.getModel().getValueAt(setar, 4).toString()));

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        txtParDependente.setText(tabelaDependentes.getModel().getValueAt(setar, 2).toString());
        cmbSexoDependente.setSelectedItem(tabelaDependentes.getModel().getValueAt(setar, 3).toString());
    }

    public void PesquisarHospedes() {
        String sql = "select CPFHospede as CPF, NomeHospede as Nome, TelefoneHospede as Telefone, EnderecoHospede as Endereço, SexoHospede as Sexo, date_format(DataNascHospede, '%d/%m/%Y') as DataNasc from hospede where NomeHospede like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisarHospedes.getText() + "%");
            rs = pst.executeQuery();
            tabelaHospedes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Pesquisar_All_Dependentes() {
        String sql = "select CPFHospede as CPFTitular, NomeDependente as Nome, ParentescoDependente as Parentesco, SexoDependente as Sexo, date_format(DataNascimentoDependente, '%d/%m/%Y') as DataNasc from dependente where CPFHospede like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisarDependente.getText() + "%");
            rs = pst.executeQuery();
            tabelaDependentes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void PesquisarDependentesDoHospede() {
        String sql = "select CPFHospede as CPFTitular, NomeDependente as Nome, ParentescoDependente as Parentesco, SexoDependente as Sexo, date_format(DataNascimentoDependente, '%d/%m/%Y') as DataNasc from dependente where CPFHospede=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCPFHospede.getText());
            rs = pst.executeQuery();
            tabelaDependentes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Tela_MenuHospedes() {
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

        lblPesquisarDependentes = new javax.swing.JLabel();
        txtPesquisarHospedes = new javax.swing.JTextField();
        lblPesquisarHospedes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaHospedes = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaDependentes = new javax.swing.JTable();
        txtPesquisarDependente = new javax.swing.JTextField();
        btnAddHospede = new javax.swing.JButton();
        btnEditarHospede = new javax.swing.JButton();
        btnRemHospede = new javax.swing.JButton();
        btnAddDependente = new javax.swing.JButton();
        btnEditarDependente = new javax.swing.JButton();
        btnRemDependente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCPFHospede = new javax.swing.JTextField();
        txtNomeHospede = new javax.swing.JTextField();
        txtEndHospede = new javax.swing.JTextField();
        txtTelHospede = new javax.swing.JTextField();
        txtNomeDependente = new javax.swing.JTextField();
        txtCPFDependente = new javax.swing.JTextField();
        txtParDependente = new javax.swing.JTextField();
        cmbSexoDependente = new javax.swing.JComboBox<>();
        lblSexo = new javax.swing.JLabel();
        cmbSexoHospede = new javax.swing.JComboBox<>();
        txtDataHospede = new com.toedter.calendar.JDateChooser();
        txtDataDependente = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Menu - Hóspedes");
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

        lblPesquisarDependentes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/pesquisar.png"))); // NOI18N

        txtPesquisarHospedes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarHospedesKeyReleased(evt);
            }
        });

        lblPesquisarHospedes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/pesquisar.png"))); // NOI18N

        tabelaHospedes.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaHospedes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaHospedesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaHospedes);

        tabelaDependentes.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaDependentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaDependentesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaDependentes);

        txtPesquisarDependente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarDependenteKeyReleased(evt);
            }
        });

        btnAddHospede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/plus.png"))); // NOI18N
        btnAddHospede.setToolTipText("Adicionar hóspede");
        btnAddHospede.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddHospede.setPreferredSize(new java.awt.Dimension(64, 64));
        btnAddHospede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddHospedeActionPerformed(evt);
            }
        });

        btnEditarHospede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/exchange.png"))); // NOI18N
        btnEditarHospede.setToolTipText("Editar hóspede");
        btnEditarHospede.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarHospede.setPreferredSize(new java.awt.Dimension(64, 64));
        btnEditarHospede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarHospedeActionPerformed(evt);
            }
        });

        btnRemHospede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/x-button.png"))); // NOI18N
        btnRemHospede.setToolTipText("Remover hospede");
        btnRemHospede.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemHospede.setPreferredSize(new java.awt.Dimension(64, 64));
        btnRemHospede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemHospedeActionPerformed(evt);
            }
        });

        btnAddDependente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/plus.png"))); // NOI18N
        btnAddDependente.setToolTipText("Adicionar dependente");
        btnAddDependente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddDependente.setPreferredSize(new java.awt.Dimension(64, 64));
        btnAddDependente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDependenteActionPerformed(evt);
            }
        });

        btnEditarDependente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/exchange.png"))); // NOI18N
        btnEditarDependente.setToolTipText("Editar dependente");
        btnEditarDependente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarDependente.setPreferredSize(new java.awt.Dimension(64, 64));
        btnEditarDependente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarDependenteActionPerformed(evt);
            }
        });

        btnRemDependente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/x-button.png"))); // NOI18N
        btnRemDependente.setToolTipText("Remover dependente");
        btnRemDependente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemDependente.setPreferredSize(new java.awt.Dimension(64, 64));
        btnRemDependente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemDependenteActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome:");

        jLabel2.setText("CPF:");

        jLabel3.setText("Endereço:");

        jLabel4.setText("Data Nasc:");

        jLabel5.setText("Telefone:");

        jLabel6.setText("Nome:");

        jLabel7.setText("Parentesco:");

        jLabel8.setText("Sexo:");

        jLabel10.setText("Data Nasc:");

        jLabel11.setText("CPF:");

        txtCPFHospede.setMaximumSize(new java.awt.Dimension(50, 20));
        txtCPFHospede.setMinimumSize(new java.awt.Dimension(50, 20));
        txtCPFHospede.setPreferredSize(new java.awt.Dimension(50, 20));

        txtNomeHospede.setPreferredSize(new java.awt.Dimension(50, 20));

        txtEndHospede.setPreferredSize(new java.awt.Dimension(50, 20));

        txtTelHospede.setPreferredSize(new java.awt.Dimension(50, 20));

        txtNomeDependente.setPreferredSize(new java.awt.Dimension(50, 20));

        txtCPFDependente.setEditable(false);
        txtCPFDependente.setPreferredSize(new java.awt.Dimension(50, 20));

        txtParDependente.setPreferredSize(new java.awt.Dimension(50, 20));

        cmbSexoDependente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Masculino", "Feminino" }));

        lblSexo.setText("Sexo:");

        cmbSexoHospede.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Masculino", "Feminino" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPesquisarHospedes, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPesquisarHospedes))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnAddHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditarHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(btnRemHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEndHospede, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtCPFHospede, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDataHospede, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNomeHospede, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTelHospede, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(lblSexo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbSexoHospede, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNomeDependente, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(txtPesquisarDependente)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblPesquisarDependentes))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAddDependente, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(76, 76, 76)
                            .addComponent(btnEditarDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRemDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(116, 116, 116))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCPFDependente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtParDependente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(10, 10, 10)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbSexoDependente, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDataDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPesquisarHospedes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPesquisarHospedes)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPesquisarDependentes)
                        .addComponent(txtPesquisarDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addComponent(jLabel11)
                        .addComponent(txtCPFHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCPFDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addComponent(txtDataHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNomeDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cmbSexoDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNomeHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtEndHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtParDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTelHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSexo)
                            .addComponent(cmbSexoHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAddHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditarHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRemHospede, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditarDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnRemDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        setBounds(0, 0, 800, 569);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        PesquisarHospedes();
        Pesquisar_All_Dependentes();
    }//GEN-LAST:event_formInternalFrameOpened

    private void tabelaHospedesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaHospedesMouseClicked
        SetarCamposHospedes();
        btnAddHospede.setEnabled(false);
        PesquisarDependentesDoHospede();
        txtNomeDependente.setText(null);
        txtParDependente.setText(null);
        cmbSexoDependente.setSelectedIndex(0);
        txtDataDependente.setDate(null);
    }//GEN-LAST:event_tabelaHospedesMouseClicked

    private void tabelaDependentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDependentesMouseClicked
        SetarCamposDependente();
    }//GEN-LAST:event_tabelaDependentesMouseClicked

    private void btnAddHospedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddHospedeActionPerformed
        AdicionarHospede();
        PesquisarHospedes();
    }//GEN-LAST:event_btnAddHospedeActionPerformed

    private void btnEditarHospedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarHospedeActionPerformed
        EditarHospede();
        PesquisarHospedes();
        btnAddHospede.setEnabled(true);
    }//GEN-LAST:event_btnEditarHospedeActionPerformed

    private void btnRemHospedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemHospedeActionPerformed
        RemoverDependente();
        RemoverHospede();
        PesquisarHospedes();
        btnAddHospede.setEnabled(true);
    }//GEN-LAST:event_btnRemHospedeActionPerformed

    private void btnAddDependenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDependenteActionPerformed
        AdicionarDependente();
        PesquisarDependentesDoHospede();

    }//GEN-LAST:event_btnAddDependenteActionPerformed

    private void btnEditarDependenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarDependenteActionPerformed
        EditarDependente();
        PesquisarDependentesDoHospede();
    }//GEN-LAST:event_btnEditarDependenteActionPerformed

    private void btnRemDependenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemDependenteActionPerformed
        RemoverDependente();
        PesquisarDependentesDoHospede();
    }//GEN-LAST:event_btnRemDependenteActionPerformed

    private void txtPesquisarHospedesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarHospedesKeyReleased
        PesquisarHospedes();
    }//GEN-LAST:event_txtPesquisarHospedesKeyReleased

    private void txtPesquisarDependenteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarDependenteKeyReleased
        Pesquisar_All_Dependentes();
    }//GEN-LAST:event_txtPesquisarDependenteKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDependente;
    private javax.swing.JButton btnAddHospede;
    private javax.swing.JButton btnEditarDependente;
    private javax.swing.JButton btnEditarHospede;
    private javax.swing.JButton btnRemDependente;
    private javax.swing.JButton btnRemHospede;
    private javax.swing.JComboBox<String> cmbSexoDependente;
    private javax.swing.JComboBox<String> cmbSexoHospede;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPesquisarDependentes;
    private javax.swing.JLabel lblPesquisarHospedes;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JTable tabelaDependentes;
    private javax.swing.JTable tabelaHospedes;
    private javax.swing.JTextField txtCPFDependente;
    private javax.swing.JTextField txtCPFHospede;
    private com.toedter.calendar.JDateChooser txtDataDependente;
    private com.toedter.calendar.JDateChooser txtDataHospede;
    private javax.swing.JTextField txtEndHospede;
    private javax.swing.JTextField txtNomeDependente;
    private javax.swing.JTextField txtNomeHospede;
    private javax.swing.JTextField txtParDependente;
    private javax.swing.JTextField txtPesquisarDependente;
    private javax.swing.JTextField txtPesquisarHospedes;
    private javax.swing.JTextField txtTelHospede;
    // End of variables declaration//GEN-END:variables
}
