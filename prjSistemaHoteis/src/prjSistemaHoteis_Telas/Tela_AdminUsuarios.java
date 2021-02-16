package prjSistemaHoteis_Telas;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import prjSistemaHoteis_Connection.Modulo_ConexaoMySQL;

public class Tela_AdminUsuarios extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void LoginSenha_Check() {
        String TipoFuncionario = cmbTipoUser.getSelectedItem().toString();
        if (TipoFuncionario.equals("Administrador") || TipoFuncionario.equals("Gerente") || TipoFuncionario.equals("Recepção")) {
            txtUserLogin.setEnabled(true);
            txtUserPassword.setEnabled(true);
        } else {
            txtUserLogin.setEnabled(false);
            txtUserPassword.setEnabled(false);
        }
    }

    public void TiposDeFuncionarios() {
        String sql = "select CPFFuncionario from funcionario";

        try {
            //cmbSupervisor.removeItemAt(0);
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cmbSupervisor.addItem(rs.getString(1));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void SetarCampos() {
        int setar = tabelaUsuarios.getSelectedRow();
        txtUserID.setText(tabelaUsuarios.getModel().getValueAt(setar, 0).toString());
        txtUserNome.setText(tabelaUsuarios.getModel().getValueAt(setar, 1).toString());
        txtUserTelefone.setText(tabelaUsuarios.getModel().getValueAt(setar, 2).toString());
        txtUserEndereco.setText(tabelaUsuarios.getModel().getValueAt(setar, 3).toString());
        txtUserSalario.setText(tabelaUsuarios.getModel().getValueAt(setar, 4).toString());
        cmbTipoUser.setSelectedItem(tabelaUsuarios.getModel().getValueAt(setar, 5));
        cmbSexoUser.setSelectedItem(tabelaUsuarios.getModel().getValueAt(setar, 6).toString());
        txtUserLogin.setText(tabelaUsuarios.getModel().getValueAt(setar, 7).toString());
        txtUserPassword.setText(tabelaUsuarios.getModel().getValueAt(setar, 8).toString());
        try {
            txtUserDataNasc.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(tabelaUsuarios.getModel().getValueAt(setar, 9).toString()));

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        cmbSupervisor.setSelectedItem(tabelaUsuarios.getModel().getValueAt(setar, 10).toString());
        btnAddUser.setEnabled(false);
        txtUserID.setEnabled(false);

    }

    public void RemoverFuncionario() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esse funcionário do sistema ?", "Atenção !", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from funcionario where CPFFuncionario=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUserID.getText());
                int removido = pst.executeUpdate();
                if (removido > 0) {
                    JOptionPane.showMessageDialog(null, "Funcionário removido do sistema com sucesso.");
                    txtUserID.setText(null);
                    txtUserNome.setText(null);
                    txtUserTelefone.setText(null);
                    txtUserEndereco.setText(null);
                    txtUserSalario.setText(null);
                    cmbTipoUser.setSelectedIndex(0);
                    cmbSexoUser.setSelectedIndex(0);
                    txtUserLogin.setText(null);
                    txtUserPassword.setText(null);
                    txtUserDataNasc.setDate(null);
                    cmbSupervisor.setSelectedIndex(0);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

            }
        }

    }

    public void EditarFuncionario() {
        String sql = "update funcionario set NomeFuncionario=?, TelefoneFuncionario=?, EnderecoFuncionario=?, SalarioFuncionario=?, TipoFuncionario=?, SexoFuncionario=?, LoginUsuario=?, SenhaUsuario=?, DataNascimentoFuncionario=?, CPFSupervisor=? where CPFFuncionario=?";
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1, txtUserID.getText());
            pst.setString(1, txtUserNome.getText());
            pst.setString(2, txtUserTelefone.getText());
            pst.setString(3, txtUserEndereco.getText());
            pst.setString(4, txtUserSalario.getText());
            pst.setString(5, cmbTipoUser.getSelectedItem().toString());
            pst.setString(6, cmbSexoUser.getSelectedItem().toString());
            pst.setString(7, txtUserLogin.getText());
            pst.setString(8, txtUserPassword.getText());
            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataHospede = DataBaseDate.format(txtUserDataNasc.getDate());
            
            pst.setString(9, DataHospede);
            pst.setString(10, cmbSupervisor.getSelectedItem().toString());
            pst.setString(11, txtUserID.getText());

            if ((txtUserID.getText().isEmpty()) || (txtUserNome.getText().isEmpty()) || (txtUserTelefone.getText().isEmpty()) || (txtUserEndereco.getText().isEmpty()) || (txtUserSalario.getText().isEmpty()) || (txtUserDataNasc.getDate().toString().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
            } else {
                int atualizado = pst.executeUpdate();
                if (atualizado > 0) {
                    JOptionPane.showMessageDialog(null, "Funcionário atualizado no sistema com sucesso.");
                    txtUserID.setText(null);
                    txtUserNome.setText(null);
                    txtUserTelefone.setText(null);
                    txtUserEndereco.setText(null);
                    txtUserSalario.setText(null);
                    cmbTipoUser.setSelectedIndex(0);
                    cmbSexoUser.setSelectedIndex(0);
                    txtUserLogin.setText(null);
                    txtUserPassword.setText(null);
                    txtUserDataNasc.setDate(null);
                    cmbSupervisor.setSelectedIndex(0);
                }
            }
            txtUserID.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void AdicionarFuncionario() {
        String sql = "insert into funcionario (CPFFuncionario, NomeFuncionario, TelefoneFuncionario, EnderecoFuncionario, SalarioFuncionario, TipoFuncionario, SexoFuncionario, LoginUsuario, SenhaUsuario, DataNascimentoFuncionario, CPFSupervisor) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUserID.getText());
            pst.setString(2, txtUserNome.getText());
            pst.setString(3, txtUserTelefone.getText());
            pst.setString(4, txtUserEndereco.getText());
            pst.setString(5, txtUserSalario.getText());
            pst.setString(6, cmbTipoUser.getSelectedItem().toString());
            pst.setString(7, cmbSexoUser.getSelectedItem().toString());
            pst.setString(8, txtUserLogin.getText());
            pst.setString(9, txtUserPassword.getText());
            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataHospede = DataBaseDate.format(txtUserDataNasc.getDate());
            
            pst.setString(10, DataHospede);
            pst.setString(11, cmbSupervisor.getSelectedItem().toString());

            if ((txtUserID.getText().isEmpty()) || (txtUserNome.getText().isEmpty()) || (txtUserTelefone.getText().isEmpty()) || (txtUserEndereco.getText().isEmpty()) || (txtUserSalario.getText().isEmpty()) || (txtUserDataNasc.getDate().toString().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Funcionário cadastrado no sistema com sucesso.");
                    txtUserID.setText(null);
                    txtUserNome.setText(null);
                    txtUserTelefone.setText(null);
                    txtUserEndereco.setText(null);
                    txtUserSalario.setText(null);
                    cmbTipoUser.setSelectedIndex(0);
                    cmbSexoUser.setSelectedIndex(0);
                    txtUserLogin.setText(null);
                    txtUserPassword.setText(null);
                    txtUserDataNasc.setDate(null);
                    cmbSupervisor.setSelectedIndex(0);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void PesquisarUsuarios() {
        String sql = "select CPFFuncionario as CPF, NomeFuncionario as Nome, TelefoneFuncionario as Tel, EnderecoFuncionario as End, SalarioFuncionario as Salário, TipoFuncionario as Tipo, SexoFuncionario as Sexo, LoginUsuario as Login, SenhaUsuario as Senha, date_format(DataNascimentoFuncionario, '%d/%m/%Y') as Data, CPFSupervisor as Supervisor from funcionario where NomeFuncionario like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUserPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tabelaUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Tela_AdminUsuarios() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaUsuarios = new javax.swing.JTable();
        txtUserPesquisar = new javax.swing.JTextField();
        lblIconPesquisar = new javax.swing.JLabel();
        lblNomeUsuario = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        lblSenha = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        txtUserNome = new javax.swing.JTextField();
        txtUserPassword = new javax.swing.JTextField();
        txtUserLogin = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        btnAddUser = new javax.swing.JButton();
        btnUpdateUser = new javax.swing.JButton();
        btnRemUser = new javax.swing.JButton();
        cmbTipoUser = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUserTelefone = new javax.swing.JTextField();
        txtUserEndereco = new javax.swing.JTextField();
        cmbSexoUser = new javax.swing.JComboBox<>();
        txtUserSalario = new javax.swing.JTextField();
        cmbSupervisor = new javax.swing.JComboBox<>();
        lblSupervisor = new javax.swing.JLabel();
        txtUserID = new javax.swing.JTextField();
        txtUserDataNasc = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Administração - FUNCIONÁRIOS");
        setPreferredSize(new java.awt.Dimension(800, 600));
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

        tabelaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaUsuarios);

        txtUserPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUserPesquisarKeyReleased(evt);
            }
        });

        lblIconPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/pesquisar.png"))); // NOI18N

        lblNomeUsuario.setText("Nome:");

        lblLogin.setText("Login:");

        lblSenha.setText("Senha:");

        lblID.setText("CPF:");

        txtUserNome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserNomeMouseClicked(evt);
            }
        });

        txtUserPassword.setEnabled(false);

        txtUserLogin.setEnabled(false);

        lblTipo.setText("Tipo:");

        btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/plus.png"))); // NOI18N
        btnAddUser.setToolTipText("Adicionar funcionário");
        btnAddUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddUser.setMaximumSize(new java.awt.Dimension(64, 64));
        btnAddUser.setMinimumSize(new java.awt.Dimension(64, 64));
        btnAddUser.setPreferredSize(new java.awt.Dimension(64, 64));
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnUpdateUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/exchange.png"))); // NOI18N
        btnUpdateUser.setToolTipText("Editar funcionário");
        btnUpdateUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdateUser.setMaximumSize(new java.awt.Dimension(64, 64));
        btnUpdateUser.setMinimumSize(new java.awt.Dimension(64, 64));
        btnUpdateUser.setPreferredSize(new java.awt.Dimension(64, 64));
        btnUpdateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserActionPerformed(evt);
            }
        });

        btnRemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/x-button.png"))); // NOI18N
        btnRemUser.setToolTipText("Remover funcionário");
        btnRemUser.setBorder(null);
        btnRemUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemUser.setMaximumSize(new java.awt.Dimension(64, 64));
        btnRemUser.setMinimumSize(new java.awt.Dimension(64, 64));
        btnRemUser.setPreferredSize(new java.awt.Dimension(64, 64));
        btnRemUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemUserActionPerformed(evt);
            }
        });

        cmbTipoUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Administrador", "Limpeza", "Gerente", "Recepção", "Restaurante", "Serviços" }));
        cmbTipoUser.setSelectedItem("Limpeza");
        cmbTipoUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoUserActionPerformed(evt);
            }
        });

        jLabel1.setText("Telefone:");

        jLabel2.setText("Endereço:");

        jLabel3.setText("Salário:");

        jLabel4.setText("Sexo:");

        jLabel5.setText("Data nasc:");

        cmbSexoUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Masculino", "Feminino" }));

        cmbSupervisor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        lblSupervisor.setText("Supervisor:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtUserPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIconPesquisar)
                        .addContainerGap(256, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(231, 231, 231)
                        .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtUserDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cmbSexoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(21, 21, 21)
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtUserSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(22, 22, 22)
                                                .addComponent(lblSupervisor)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cmbSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtUserEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSenha)
                                    .addComponent(lblTipo)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblID)
                                        .addComponent(lblLogin))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtUserTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblNomeUsuario)
                                        .addGap(25, 25, 25)
                                        .addComponent(txtUserNome, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(66, 66, 66)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUserPassword)
                            .addComponent(cmbTipoUser, 0, 140, Short.MAX_VALUE)
                            .addComponent(txtUserID)
                            .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUserPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIconPesquisar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeUsuario)
                    .addComponent(txtUserNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblID)
                    .addComponent(txtUserID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUserLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblLogin)
                                .addComponent(txtUserTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)))
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(txtUserDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSenha)
                    .addComponent(jLabel2)
                    .addComponent(txtUserEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSexoUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtUserSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSupervisor)
                    .addComponent(cmbSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(lblTipo)
                    .addComponent(cmbTipoUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRemUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );

        setBounds(0, 0, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        AdicionarFuncionario();
        PesquisarUsuarios();
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void txtUserPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserPesquisarKeyReleased
        PesquisarUsuarios();
    }//GEN-LAST:event_txtUserPesquisarKeyReleased

    private void tabelaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaUsuariosMouseClicked
        SetarCampos();

    }//GEN-LAST:event_tabelaUsuariosMouseClicked

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        PesquisarUsuarios();
        TiposDeFuncionarios();

    }//GEN-LAST:event_formInternalFrameOpened

    private void btnUpdateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserActionPerformed
        EditarFuncionario();
        PesquisarUsuarios();
        btnAddUser.setEnabled(true);
    }//GEN-LAST:event_btnUpdateUserActionPerformed

    private void cmbTipoUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoUserActionPerformed
        LoginSenha_Check();
    }//GEN-LAST:event_cmbTipoUserActionPerformed

    private void btnRemUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemUserActionPerformed
        RemoverFuncionario();
        PesquisarUsuarios();
        btnAddUser.setEnabled(true);
    }//GEN-LAST:event_btnRemUserActionPerformed

    private void txtUserNomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserNomeMouseClicked

    }//GEN-LAST:event_txtUserNomeMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnRemUser;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JComboBox<String> cmbSexoUser;
    private javax.swing.JComboBox<String> cmbSupervisor;
    private javax.swing.JComboBox<String> cmbTipoUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIconPesquisar;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNomeUsuario;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblSupervisor;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JTable tabelaUsuarios;
    private com.toedter.calendar.JDateChooser txtUserDataNasc;
    private javax.swing.JTextField txtUserEndereco;
    private javax.swing.JTextField txtUserID;
    private javax.swing.JTextField txtUserLogin;
    private javax.swing.JTextField txtUserNome;
    private javax.swing.JTextField txtUserPassword;
    private javax.swing.JTextField txtUserPesquisar;
    private javax.swing.JTextField txtUserSalario;
    private javax.swing.JTextField txtUserTelefone;
    // End of variables declaration//GEN-END:variables
}
