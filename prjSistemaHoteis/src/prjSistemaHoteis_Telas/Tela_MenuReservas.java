package prjSistemaHoteis_Telas;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import prjSistemaHoteis_Connection.Modulo_ConexaoMySQL;

public class Tela_MenuReservas extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void RemoverHospedagens() {
        String sql = "delete from hospedagemdiaria where CodReserva=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCodReserva.getText());
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void RemoverServicosHospedagens(){
        String sql = "delete from hospedagemservico where CodReserva=?";
         try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCodReserva.getText());
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void TipoQuarto() {
        String sql = "select TipoQuarto from quarto where CodQuarto=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cmbCodQuarto.getSelectedItem().toString());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtTipoQuarto.setText(rs.getString(1));
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void AdicionarDiaria() {

        String sqlHospDiaria = "insert into hospedagemdiaria (CodReserva, valorDiaria) values (?, ?)";
        double valorDiaria = 0.00;
        try {

            pst = conexao.prepareStatement(sqlHospDiaria);
            pst.setString(1, txtCodReserva.getText());
            if (txtTipoQuarto.getText().equals("Single")) {
                valorDiaria = 100.00;

            } else if (txtTipoQuarto.getText().equals("Double")) {
                valorDiaria = 170.00;

            } else if (txtTipoQuarto.getText().equals("Família")) {
                valorDiaria = 250.00;

            } else if (txtTipoQuarto.getText().equals("Luxo")) {
                valorDiaria = 350.00;

            }
            pst.setDouble(2, valorDiaria);
            pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void SetarCampos() {
        int setar = tabelaReservas.getSelectedRow();
        txtCodReserva.setText(tabelaReservas.getModel().getValueAt(setar, 0).toString());
        cmbCodQuarto.setSelectedItem(tabelaReservas.getModel().getValueAt(setar, 1).toString());
        TipoQuarto();
        txtCPFFuncionario.setText(tabelaReservas.getModel().getValueAt(setar, 2).toString());
        txtCPFHospede.setText(tabelaReservas.getModel().getValueAt(setar, 3).toString());
        txtNomeHospede.setText(tabelaReservas.getModel().getValueAt(setar, 4).toString());
        txtEndHospede.setText(tabelaReservas.getModel().getValueAt(setar, 5).toString());
        txtTelefoneHospede.setText(tabelaReservas.getModel().getValueAt(setar, 6).toString());
        cmbSexoHospede.setSelectedItem(tabelaReservas.getModel().getValueAt(setar, 7).toString());

        try {
            txtDataHospede.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(tabelaReservas.getModel().getValueAt(setar, 8).toString()));
            txtDataEntrada.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(tabelaReservas.getModel().getValueAt(setar, 9).toString()));
            txtDataSaida.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(tabelaReservas.getModel().getValueAt(setar, 10).toString()));
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        txtCPFHospede.setEnabled(false);
        txtCodReserva.setEnabled(false);
        btnAddReserva.setEnabled(false);
    }

    public void PesquisarReservas() {
        String sql = "select distinct R.CodReserva, R.CodQuarto, R.CPFFuncionario, H.CPFHospede, H.NomeHospede as Nome, H.EnderecoHospede as Endereço, H.TelefoneHospede as Telefone, H.SexoHospede as Sexo, date_format(H.DataNascHospede, '%d/%m/%Y') as DataNasc, date_format(R.DataEntrada, '%d/%m/%Y') as Entrada, date_format(R.DataSaida, '%d/%m/%Y') as Saída, StatusReserva as Status from reserva as R, hospede as H where R.CPFHospede = H.CPFHospede";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tabelaReservas.setModel(DbUtils.resultSetToTableModel(rs));
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void PesquisarReservasPorNomeHospede() {
        String sql = "select R.CodReserva, R.CodQuarto, R.CPFFuncionario, H.CPFHospede, H.NomeHospede as Nome, H.EnderecoHospede as Endereço, H.TelefoneHospede as Telefone, H.SexoHospede as Sexo, date_format(H.DataNascHospede, '%d/%m/%Y') as DataNasc, date_format(R.DataEntrada, '%d/%m/%Y') as Entrada, date_format(R.DataSaida, '%d/%m/%Y') as Saída, StatusReserva as Status from reserva as R, hospede as H where H.NomeHospede like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisarReserva.getText() + "%");
            rs = pst.executeQuery();
            tabelaReservas.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void RemoverDependentes() {

        String sql = "delete from dependente where CPFHospede=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCPFHospede.getText());
            int removido = pst.executeUpdate();
            if (removido > 0) {
                JOptionPane.showMessageDialog(null, "Reserva, hóspede e dependentes apagados com sucesso.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Remover_Hospede_Titular() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover essa reserva do sistema ?", "Atenção !", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from hospede where CPFHospede=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCPFHospede.getText());
                int removido = pst.executeUpdate();
                if (removido > 0) {
                    txtNomeHospede.setText(null);
                    txtEndHospede.setText(null);
                    txtTelefoneHospede.setText(null);
                    txtDataHospede.setDate(null);
                    txtCPFHospede.setText(null);
                    cmbSexoHospede.setSelectedItem(0);
                    txtCPFHospede.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void Remover_Reserva() {

        String sql = "delete from reserva where CodReserva=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCodReserva.getText());
            int removido = pst.executeUpdate();
            if (removido > 0) {
                txtCodReserva.setText(null);
                cmbCodQuarto.setSelectedIndex(0);
                txtDataEntrada.setDate(null);
                txtDataSaida.setDate(null);
                txtCodReserva.setEnabled(true);
                txtCPFFuncionario.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Editar_Hospede_Titular() {
        String sqlhospede = "update hospede set NomeHospede=?, EnderecoHospede=?, TelefoneHospede=?, SexoHospede=?, DataNascHospede=? where CPFHospede=?";

        try {
            pst = conexao.prepareStatement(sqlhospede);

            pst.setString(1, txtNomeHospede.getText());
            pst.setString(2, txtEndHospede.getText());
            pst.setString(3, txtTelefoneHospede.getText());
            pst.setString(4, cmbSexoHospede.getSelectedItem().toString());

            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataHospede = DataBaseDate.format(txtDataHospede.getDate());

            pst.setString(5, DataHospede);
            pst.setString(6, txtCPFHospede.getText());

            if ((txtNomeHospede.getText().isEmpty()) || (txtEndHospede.getText().isEmpty()) || (txtTelefoneHospede.getText().isEmpty()) || (txtDataHospede.getDate().toString().isEmpty()) || (txtCPFHospede.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos do hospede.");
            } else {
                int atualizado = pst.executeUpdate();
                if (atualizado > 0) {
                    //JOptionPane.showMessageDialog(null, "Informações alteradas no sistema com sucesso.");
                    txtNomeHospede.setText(null);
                    txtEndHospede.setText(null);
                    txtTelefoneHospede.setText(null);
                    txtDataHospede.setDate(null);
                    txtCPFHospede.setText(null);
                    cmbSexoHospede.setSelectedItem(0);
                    txtCPFHospede.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Editar_Reserva() {
        String sql = "update reserva set CodQuarto=?, DataEntrada=?, DataSaida=?, StatusReserva=? where CodReserva=?";
        try {

            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataEntradaFormatada = DataBaseDate.format(txtDataEntrada.getDate());
            String DataSaidaFormatada = DataBaseDate.format(txtDataSaida.getDate());

            pst = conexao.prepareStatement(sql);
            pst.setString(1, cmbCodQuarto.getSelectedItem().toString());
            pst.setString(2, DataEntradaFormatada);
            pst.setString(3, DataSaidaFormatada);
            pst.setString(4, cmbStatusReserva.getSelectedItem().toString());
            pst.setString(5, txtCodReserva.getText());

            if ((cmbCodQuarto.getSelectedItem().toString().isEmpty()) || (txtDataEntrada.getDate().toString().isEmpty()) || (txtDataSaida.getDate().toString().isEmpty()) || (txtCodReserva.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos da reserva.");
            } else {
                int atualizado = pst.executeUpdate();
                if (atualizado > 0) {
                    JOptionPane.showMessageDialog(null, "Reserva atualizada no sistema com sucesso.");
                    cmbCodQuarto.setSelectedIndex(0);
                    txtDataEntrada.setDate(null);
                    txtDataSaida.setDate(null);
                    txtCodReserva.setText(null);
                    txtCPFFuncionario.setText(null);
                    txtCodReserva.setEnabled(true);
                    btnAddReserva.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problema");

        }
    }

    public void Adicionar_Hospede_Titular() {
        String sqlhospede = "insert into hospede (CPFHospede, NomeHospede, TelefoneHospede, EnderecoHospede, SexoHospede, DataNascHospede) values (?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sqlhospede);
            pst.setString(1, txtCPFHospede.getText());
            pst.setString(2, txtNomeHospede.getText());
            pst.setString(3, txtTelefoneHospede.getText());
            pst.setString(4, txtEndHospede.getText());
            pst.setString(5, cmbSexoHospede.getSelectedItem().toString());
            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataHospede = DataBaseDate.format(txtDataHospede.getDate());

            pst.setString(6, DataHospede);

            if ((txtCPFHospede.getText().isEmpty()) || (txtCPFHospede.getText().isEmpty()) || (txtTelefoneHospede.getText().isEmpty()) || (txtEndHospede.getText().isEmpty()) || (cmbSexoHospede.getSelectedItem().toString().isEmpty()) || (txtDataHospede.getDate().toString().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos do hospede.");
            } else {
                pst.executeUpdate();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void AdicionarReservas() {
        String sqlreserva = "insert into reserva (CodReserva, CPFHospede, CPFFuncionario, CodQuarto, DataEntrada, DataSaida, HoraEntrada, HoraSaida, StatusReserva, DataHoraAddReserva) values (?, ?, ?, ?, ?, ?, '13:00', '12:00', ?, NOW())";
        try {
            pst = conexao.prepareStatement(sqlreserva);
            pst.setString(1, txtCodReserva.getText());
            pst.setString(2, txtCPFHospede.getText());
            pst.setString(3, txtCPFFuncionario.getText());
            pst.setString(4, cmbCodQuarto.getSelectedItem().toString());

            SimpleDateFormat DataBaseDate = new SimpleDateFormat("YYYY-MM-dd");
            String DataEntradaFormatada = DataBaseDate.format(txtDataEntrada.getDate());
            String DataSaidaCompleta = DataBaseDate.format(txtDataSaida.getDate());
            pst.setString(5, DataEntradaFormatada);
            pst.setString(6, DataSaidaCompleta);
            pst.setString(7, cmbStatusReserva.getSelectedItem().toString());
            
            if ((txtCodReserva.getText().isEmpty()) || (txtCPFHospede.getText().isEmpty()) || (txtCPFFuncionario.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos da reserva e hospede.");
            } else {
                int radicionada = pst.executeUpdate();
                if (radicionada > 0) {
                    JOptionPane.showMessageDialog(null, "Reserva cadastrada no sistema com sucesso.");
                    AdicionarDiaria();
                    txtCodReserva.setText(null);
                    txtCPFHospede.setText(null);
                    txtCPFFuncionario.setText(null);
                    txtNomeHospede.setText(null);
                    txtTelefoneHospede.setText(null);
                    txtEndHospede.setText(null);
                    txtDataHospede.setDate(null);
                    txtDataEntrada.setDate(null);
                    txtDataSaida.setDate(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String CPFFuncionario(String login) {
        String sql = "select CPFFuncionario from funcionario where LoginUsuario=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, login);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtCPFFuncionario.setText(rs.getString(1));

                return login;
            } else if (!rs.next()) {
                return null;
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return "fim";
    }

    public void CPFConsultaEditarRemover() {
        String sql = "select CPFFuncionario from funcionario where LoginUsuario=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, lblLoginTeste.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtCPFFuncionario.setText(rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void cmbQuartos() {
        String sql = "select CodQuarto, TipoQuarto from quarto where StatusQuarto='Disponível'";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cmbCodQuarto.addItem(rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Tela_MenuReservas() {
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

        btnAddReserva = new javax.swing.JButton();
        btnEditarReserva = new javax.swing.JButton();
        btnRemoverReserva = new javax.swing.JButton();
        lblPesquisarReserva = new javax.swing.JLabel();
        txtPesquisarReserva = new javax.swing.JTextField();
        lblCodReserva = new javax.swing.JLabel();
        txtCodReserva = new javax.swing.JTextField();
        lblCPFHospede = new javax.swing.JLabel();
        txtCPFHospede = new javax.swing.JTextField();
        lblCPFFuncionario = new javax.swing.JLabel();
        txtCPFFuncionario = new javax.swing.JTextField();
        lblNomeHospede = new javax.swing.JLabel();
        lblTelHospede = new javax.swing.JLabel();
        lblEnderecoHospede = new javax.swing.JLabel();
        lblDataNascHospede = new javax.swing.JLabel();
        txtNomeHospede = new javax.swing.JTextField();
        txtTelefoneHospede = new javax.swing.JTextField();
        txtEndHospede = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaReservas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbSexoHospede = new javax.swing.JComboBox<>();
        cmbCodQuarto = new javax.swing.JComboBox<>();
        txtDataEntrada = new com.toedter.calendar.JDateChooser();
        txtDataSaida = new com.toedter.calendar.JDateChooser();
        lblLoginTeste = new javax.swing.JLabel();
        txtDataHospede = new com.toedter.calendar.JDateChooser();
        txtTipoQuarto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbStatusReserva = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Gerenciar reservas");
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

        btnAddReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/plus.png"))); // NOI18N
        btnAddReserva.setToolTipText("Adicionar reserva");
        btnAddReserva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddReserva.setMaximumSize(new java.awt.Dimension(64, 64));
        btnAddReserva.setPreferredSize(new java.awt.Dimension(64, 64));
        btnAddReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddReservaActionPerformed(evt);
            }
        });

        btnEditarReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/exchange.png"))); // NOI18N
        btnEditarReserva.setToolTipText("Editar reserva");
        btnEditarReserva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarReserva.setPreferredSize(new java.awt.Dimension(64, 64));
        btnEditarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarReservaActionPerformed(evt);
            }
        });

        btnRemoverReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/x-button.png"))); // NOI18N
        btnRemoverReserva.setToolTipText("Remover reserva");
        btnRemoverReserva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoverReserva.setPreferredSize(new java.awt.Dimension(64, 64));
        btnRemoverReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverReservaActionPerformed(evt);
            }
        });

        lblPesquisarReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/pesquisar.png"))); // NOI18N

        txtPesquisarReserva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPesquisarReservaMouseClicked(evt);
            }
        });
        txtPesquisarReserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarReservaKeyReleased(evt);
            }
        });

        lblCodReserva.setText("Cód.Reserva:");

        lblCPFHospede.setText("CPF:");

        lblCPFFuncionario.setText("Feita por:");

        txtCPFFuncionario.setEnabled(false);

        lblNomeHospede.setText("Nome:");

        lblTelHospede.setText("Tel:");

        lblEnderecoHospede.setText("Endereço: ");

        lblDataNascHospede.setText("Data Nasc:");

        tabelaReservas.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaReservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaReservasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaReservas);

        jLabel2.setText("Cód.Quarto:");

        jLabel3.setText("Entrada:");

        jLabel4.setText("Saída:");

        jLabel1.setText("Sexo:");

        cmbSexoHospede.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Masculino", "Feminino" }));

        cmbCodQuarto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cmbCodQuarto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCodQuartoItemStateChanged(evt);
            }
        });

        txtDataEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDataEntradaMouseClicked(evt);
            }
        });

        lblLoginTeste.setForeground(new java.awt.Color(255, 255, 255));
        lblLoginTeste.setText("jLabel5");

        txtTipoQuarto.setEnabled(false);

        jLabel5.setText("Status:");

        cmbStatusReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Iniciada", "Finalizada" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblLoginTeste))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPesquisarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(lblPesquisarReserva))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblEnderecoHospede)
                                    .addGap(19, 19, 19)
                                    .addComponent(txtEndHospede))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnAddReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(259, 259, 259)
                                            .addComponent(btnEditarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblDataNascHospede)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtDataHospede, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cmbSexoHospede, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTelHospede)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtTelefoneHospede, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(btnRemoverReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbStatusReserva, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblCodReserva)
                                .addComponent(lblCPFFuncionario)
                                .addComponent(lblNomeHospede))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCPFFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                        .addComponent(txtCodReserva))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel2))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGap(56, 56, 56)
                                            .addComponent(lblCPFHospede)))
                                    .addGap(10, 10, 10)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCPFHospede)
                                        .addComponent(cmbCodQuarto, 0, 115, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtTipoQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3))
                                        .addComponent(jLabel4))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtDataSaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(txtNomeHospede, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPesquisarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPesquisarReserva))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCodReserva)
                            .addComponent(txtCodReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(cmbCodQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCPFFuncionario)
                            .addComponent(txtCPFFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCPFHospede)
                            .addComponent(txtCPFHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtDataSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeHospede)
                    .addComponent(txtNomeHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEnderecoHospede)
                    .addComponent(txtEndHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cmbStatusReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDataNascHospede)
                        .addComponent(lblTelHospede)
                        .addComponent(txtTelefoneHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(cmbSexoHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDataHospede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRemoverReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(lblLoginTeste))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        PesquisarReservas();
        String login = JOptionPane.showInputDialog(null, "Digite o seu login: ");
        lblLoginTeste.setText(login);
        if (CPFFuncionario(login) == null) {
            this.dispose();
            JOptionPane.showMessageDialog(null, "Login inválido ou operação cancelada.");
        }
        cmbQuartos();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnAddReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddReservaActionPerformed

        Adicionar_Hospede_Titular();
        AdicionarReservas();
        PesquisarReservas();
        CPFFuncionario(lblLoginTeste.getText());
    }//GEN-LAST:event_btnAddReservaActionPerformed

    private void tabelaReservasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaReservasMouseClicked
        SetarCampos();
    }//GEN-LAST:event_tabelaReservasMouseClicked

    private void btnEditarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarReservaActionPerformed
        Editar_Hospede_Titular();
        Editar_Reserva();
        PesquisarReservas();
        CPFFuncionario(lblLoginTeste.getText());
    }//GEN-LAST:event_btnEditarReservaActionPerformed

    private void txtPesquisarReservaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarReservaKeyReleased
        //PesquisarReservasPorNomeHospede();
    }//GEN-LAST:event_txtPesquisarReservaKeyReleased

    private void btnRemoverReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverReservaActionPerformed
        RemoverHospedagens();
        RemoverServicosHospedagens();
        Remover_Reserva();
        Remover_Hospede_Titular();
        //RemoverDependentes();
        PesquisarReservas();
        CPFFuncionario(lblLoginTeste.getText());
        btnAddReserva.setEnabled(true);
    }//GEN-LAST:event_btnRemoverReservaActionPerformed

    private void txtDataEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataEntradaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataEntradaMouseClicked

    private void txtPesquisarReservaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesquisarReservaMouseClicked
        //PesquisarReservas();
    }//GEN-LAST:event_txtPesquisarReservaMouseClicked

    private void cmbCodQuartoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCodQuartoItemStateChanged
        TipoQuarto();
    }//GEN-LAST:event_cmbCodQuartoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddReserva;
    private javax.swing.JButton btnEditarReserva;
    private javax.swing.JButton btnRemoverReserva;
    private javax.swing.JComboBox<String> cmbCodQuarto;
    private javax.swing.JComboBox<String> cmbSexoHospede;
    private javax.swing.JComboBox<String> cmbStatusReserva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCPFFuncionario;
    private javax.swing.JLabel lblCPFHospede;
    private javax.swing.JLabel lblCodReserva;
    private javax.swing.JLabel lblDataNascHospede;
    private javax.swing.JLabel lblEnderecoHospede;
    private javax.swing.JLabel lblLoginTeste;
    private javax.swing.JLabel lblNomeHospede;
    private javax.swing.JLabel lblPesquisarReserva;
    private javax.swing.JLabel lblTelHospede;
    private javax.swing.JTable tabelaReservas;
    public javax.swing.JTextField txtCPFFuncionario;
    private javax.swing.JTextField txtCPFHospede;
    private javax.swing.JTextField txtCodReserva;
    private com.toedter.calendar.JDateChooser txtDataEntrada;
    private com.toedter.calendar.JDateChooser txtDataHospede;
    private com.toedter.calendar.JDateChooser txtDataSaida;
    private javax.swing.JTextField txtEndHospede;
    private javax.swing.JTextField txtNomeHospede;
    private javax.swing.JTextField txtPesquisarReserva;
    private javax.swing.JTextField txtTelefoneHospede;
    private javax.swing.JTextField txtTipoQuarto;
    // End of variables declaration//GEN-END:variables

}
