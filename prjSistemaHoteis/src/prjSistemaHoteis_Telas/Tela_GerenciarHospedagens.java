package prjSistemaHoteis_Telas;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import prjSistemaHoteis_Connection.Modulo_ConexaoMySQL;

public class Tela_GerenciarHospedagens extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void FinalizarContaStatus() {
        String sql = "update reserva set StatusReserva=? where CodReserva=?";
        try {
            pst= conexao.prepareStatement(sql);
            pst.setString(1, "Finalizada");
            pst.setString(2, lblCodHospedagem.getText());
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void FinalizarConta() {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a finalização da reserva ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            FinalizarContaStatus();
            String sql = "select CodReserva, CPFHospede, date_format(DataEntrada, '%d/%m/%Y') as Entrada, date_format(DataSaida, '%d/%m/%Y') as Saída, QTD_Diarias, valorDiaria, sum((QTD_Diarias*valorDiaria)) as TotalDiarias, TotalServiços, sum(((QTD_Diarias*valorDiaria)+TotalServiços)) as TotalFinal from (select R.CodReserva, H.CPFHospede, R.DataEntrada, R.DataSaida, HD.valorDiaria, datediff(R.DataSaida, R.DataEntrada)as QTD_Diarias, sum(HS.valorTotalServico) as TotalServiços from reserva as R, hospedagemdiaria as HD, hospedagemservico as HS, hospede as H where R.CPFHospede=H.CPFHospede and R.CodReserva=HD.CodReserva and R.CodReserva=? and HS.CodReserva=?) as Conta";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, lblCodHospedagem.getText());
                pst.setString(2, lblCodHospedagem.getText());
                rs = pst.executeQuery();

                JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
                JasperDesign jasperDesign = JRXmlLoader.load("C:\\Reports\\ContaReserva.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint Print = JasperFillManager.fillReport(jasperReport, null, jrRS);
                JasperViewer.viewReport(Print, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void SetarCamposGerenciarServico() {
        PesquisarServicos();
        cmbCodServico();
        cmbCodServico.setEnabled(false);
        txtDescricao.setEnabled(false);
        int setar = tabelaDiariaServicos.getSelectedRow();
        cmbCodServico.setSelectedItem(tabelaDiariaServicos.getModel().getValueAt(setar, 1).toString());
        txtValorTotalServ.setText(tabelaDiariaServicos.getModel().getValueAt(setar, 2).toString());
        txtDescricao.setText(tabelaDiariaServicos.getModel().getValueAt(setar, 3).toString());
        txtTipoServico.setText(tabelaDiariaServicos.getModel().getValueAt(setar, 4).toString());
        txtValorUnit.setText(tabelaDiariaServicos.getModel().getValueAt(setar, 5).toString());
        double qtd = (double) tabelaDiariaServicos.getModel().getValueAt(setar, 6);
        int qtd_f = (int) qtd;
        cmbQtdServ.setSelectedItem(Integer.toString(qtd_f));

    }

    public void CalcularValorFinalHospedagem() {
        double valorFinalDiaria = Double.parseDouble(lblValorTotalDiaria.getText());
        double valorTotalServicos;
        if (lblValorServFinal.getText() != null) {
            valorTotalServicos = Double.parseDouble(lblValorServFinal.getText());
        } else {
            lblValorServFinal.setText("0.00");
            valorTotalServicos = 0.00;
        }
        System.out.println(lblValorServFinal.getText());
        double valorTotal = valorFinalDiaria + valorTotalServicos;
        lblValorFinal.setText(Double.toString(valorTotal));
    }

    public void CalcularValorFinalServicos() {
        String sql = "select sum(valorTotalServico) as TotalServiços from hospedagemservico where CodReserva=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, lblCodHospedagem.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                lblValorServFinal.setText(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void RemoverServicoHospedagem() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esse serviço da hospedagem ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {

            int setar = tabelaDiariaServicos.getSelectedRow();
            String CodHospServ = tabelaDiariaServicos.getModel().getValueAt(setar, 0).toString();
            int CodHospServFinal = Integer.parseInt(CodHospServ);

            String sql = "delete from hospedagemservico where CodHospedagem=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setInt(1, CodHospServFinal);
                int removido = pst.executeUpdate();
                if (removido > 0) {
                    JOptionPane.showMessageDialog(null, "Serviço de hospedagem removido do sistema com sucesso.");
                    cmbCodServico.setSelectedItem(0);
                    txtValorTotalServ.setText(null);
                    txtDescricao.setText(null);
                    txtTipoServico.setText(null);
                    txtValorUnit.setText(null);
                    cmbQtdServ.setSelectedItem(0);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void EditarServicoHospedagem() {
        String sql = "update hospedagemservico set valorTotalServico=? where CodHospedagem=?";

        try {
            pst = conexao.prepareStatement(sql);

            pst.setDouble(1, Double.parseDouble(txtValorTotalServ.getText()));
            pst.setInt(2, Integer.parseInt(lblCodHS.getText()));
            int atualizado = pst.executeUpdate();
            if (atualizado > 0) {
                JOptionPane.showMessageDialog(null, "Serviço de hospedagem atualizado no sistema com sucesso.");
                cmbCodServico.setSelectedItem(0);
                txtValorTotalServ.setText(null);
                txtDescricao.setText(null);
                txtTipoServico.setText(null);
                txtValorUnit.setText(null);
                cmbQtdServ.setSelectedItem(0);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void AdicionarServicoHospedagem() {
        String sql = "insert into hospedagemservico (CodReserva, CodServico, valorTotalServico) values (?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, lblCodHospedagem.getText());
            pst.setString(2, cmbCodServico.getSelectedItem().toString());
            pst.setDouble(3, Double.parseDouble(txtValorTotalServ.getText()));
            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Serviço adicionado na hospedagem com sucesso.");
                cmbCodServico.setSelectedIndex(0);
                txtTipoServico.setText(null);
                cmbQtdServ.setSelectedIndex(0);
                txtDescricao.setText(null);
                txtValorUnit.setText(null);
                txtValorTotalServ.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void SetarCamposServicos() {
        int setar = tabelaListaServicos.getSelectedRow();
        cmbCodServico.setSelectedItem(tabelaListaServicos.getModel().getValueAt(setar, 0).toString());
        txtTipoServico.setText(tabelaListaServicos.getModel().getValueAt(setar, 1).toString());
        txtDescricao.setText(tabelaListaServicos.getModel().getValueAt(setar, 2).toString());
        txtValorUnit.setText(tabelaListaServicos.getModel().getValueAt(setar, 3).toString());
    }

    public void cmbCodServico() {
        String sql = "select CodServico from servico";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cmbCodServico.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void PesquisarServicos() {
        String sql = "select CodServico, TipoServico, Descricao, valorServico from servico";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tabelaListaServicos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void PesquisarServicosHospedagem() {
        String sql = "select HS.CodHospedagem, HS.CodServico, HS.valorTotalServico, S.Descricao, S.TipoServico, S.valorServico, HS.valorTotalServico/S.valorServico as QTD from hospedagemservico as HS, servico as S where HS.CodServico = S.CodServico and CodReserva=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, lblCodHospedagem.getText());
            rs = pst.executeQuery();
            tabelaDiariaServicos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void PesquisarHospedagens() {
        String sql = "select HD.CodReserva as CodReserva, R.CodQuarto as Quarto from hospedagemdiaria as HD, reserva as R where HD.CodReserva = R.CodReserva";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tabelaHospedagens.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void PesquisarDadosDiaria() {
        String sql = "select R.CodQuarto, H.CPFHospede, H.NomeHospede, date_format(R.DataEntrada, '%d/%m/%Y') as Entrada, date_format(R.DataSaida, '%d/%m/%Y') as Saída, Q.TipoQuarto, HD.valorDiaria, datediff(R.DataSaida, R.DataEntrada) as QTD from hospedagemdiaria as HD, reserva as R, quarto as Q, hospede as H where HD.CodReserva = R.CodReserva and R.CPFHospede=H.CPFHospede and R.CodReserva=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, lblCodHospedagem.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                lblNumQuarto.setText(rs.getString(1));
                lblCPFHospede.setText(rs.getString(2));
                lblNomeHospede.setText(rs.getString(3));
                lblDataEntrada.setText(rs.getString(4));
                lblDataSaida.setText(rs.getString(5));
                lblTipoQuarto.setText(rs.getString(6));
                lblValorDiaria.setText(rs.getString(7));
                double valorDiaria = Double.parseDouble(lblValorDiaria.getText()) * Integer.parseInt(rs.getString(8));
                lblValorTotalDiaria.setText(Double.toString(valorDiaria));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void SetarCampos_PropriedadesHospedagem() {
        int setar = tabelaHospedagens.getSelectedRow();
        lblCodHospedagem.setText(tabelaHospedagens.getModel().getValueAt(setar, 0).toString());
    }

    public void PesquisarHospedagensPorCodReserva() {
        String sql = "select HD.CodReserva as CodReserva, R.CodQuarto as Quarto from hospedagemdiaria as HD, reserva as R where HD.CodReserva like ? and R.CodReserva like ? and HD.CodReserva = R.CodReserva";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisarHospedagens.getText() + "%");
            pst.setString(2, txtPesquisarHospedagens.getText() + "%");
            rs = pst.executeQuery();
            tabelaHospedagens.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Tela_GerenciarHospedagens() {
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

        txtPesquisarHospedagens = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        PainelHospedagens = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaHospedagens = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        Servicos = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblCPFHospede = new javax.swing.JLabel();
        lblNomeHospede = new javax.swing.JLabel();
        lblCodHospedagem = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaDiariaServicos = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblValorFinal = new javax.swing.JLabel();
        lblNumQuarto = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblTipoQuarto = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblDataEntrada = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblValorDiaria = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblValorTotalDiaria = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblValorServFinal = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblDataSaida = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaListaServicos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cmbCodServico = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtValorTotalServ = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTipoServico = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        txtValorUnit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        cmbQtdServ = new javax.swing.JComboBox<>();
        lblCodHS = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Hospedagens");
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

        txtPesquisarHospedagens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarHospedagensKeyReleased(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prjSistemaHoteis_Icones/pesquisar.png"))); // NOI18N

        tabelaHospedagens.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaHospedagens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaHospedagensMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaHospedagens);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
        );

        PainelHospedagens.addTab("Hospedagens", jPanel1);

        jPanel2.setEnabled(false);

        jButton1.setText("Finalizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Servicos.setText("Gerenciar serviços");
        Servicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ServicosActionPerformed(evt);
            }
        });

        jLabel9.setText("Nome:");

        jLabel8.setText("CPF:");

        lblCPFHospede.setText("cpf");

        lblNomeHospede.setText("nome");

        lblCodHospedagem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCodHospedagem.setText("codHospedagem");

        tabelaDiariaServicos.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaDiariaServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaDiariaServicosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaDiariaServicos);

        jLabel13.setText("Quarto:");

        jLabel4.setText("Valor final:");

        lblValorFinal.setText("valorfinal");

        lblNumQuarto.setText("n_quarto");

        jLabel15.setText("TipoQuarto:");

        lblTipoQuarto.setText("tipoquarto");

        jLabel6.setText("Data entrada:");

        lblDataEntrada.setText("dataentrada");

        jLabel2.setText("Diária:");

        lblValorDiaria.setText("valordiaria");

        jLabel12.setText("Total:");

        lblValorTotalDiaria.setText("valortotal");

        jLabel5.setText("Serviços:");

        lblValorServFinal.setText("valorserv");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValorDiaria)
                .addGap(41, 41, 41)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValorTotalDiaria))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValorServFinal))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblValorDiaria)
                    .addComponent(jLabel12)
                    .addComponent(lblValorTotalDiaria))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblValorServFinal))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel17.setText("Data saída:");

        lblDataSaida.setText("datasaida");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCPFHospede)
                                            .addComponent(lblNomeHospede))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblNumQuarto)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addComponent(lblCodHospedagem))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(lblValorFinal)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTipoQuarto))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDataSaida)
                                    .addComponent(lblDataEntrada))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jButton1)
                .addGap(211, 211, 211)
                .addComponent(Servicos)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodHospedagem)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lblNomeHospede))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lblCPFHospede))))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblNumQuarto))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(lblTipoQuarto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lblDataEntrada))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(lblDataSaida)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblValorFinal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(Servicos))
                .addGap(21, 21, 21))
        );

        PainelHospedagens.addTab("Detalhes", jPanel2);

        jPanel3.setEnabled(false);

        tabelaListaServicos.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaListaServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaListaServicosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaListaServicos);

        jLabel3.setText("CódServiço:");

        cmbCodServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel16.setText("Tipo:");

        jLabel14.setText("Valor unit:");

        txtValorTotalServ.setEnabled(false);

        jLabel11.setText("Valor:");

        txtTipoServico.setEnabled(false);

        jLabel10.setText("Descrição:");

        txtValorUnit.setEnabled(false);

        jLabel7.setText("Qtd:");

        jButton4.setText("Adicionar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Editar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Remover");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        cmbQtdServ.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cmbQtdServ.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbQtdServItemStateChanged(evt);
            }
        });

        lblCodHS.setText("NumHospServ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbQtdServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cmbCodServico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(txtValorUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtValorTotalServ, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(45, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodHS)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(112, 112, 112)
                                .addComponent(jButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCodHS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbCodServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cmbQtdServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtValorUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txtValorTotalServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(50, 50, 50))
        );

        PainelHospedagens.addTab("Gerenciar serviços", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(PainelHospedagens)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPesquisarHospedagens, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(464, 464, 464)))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisarHospedagens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PainelHospedagens, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        setBounds(0, 0, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        PesquisarHospedagens();
        PesquisarServicosHospedagem();
        PainelHospedagens.setEnabledAt(1, false);
        PainelHospedagens.setEnabledAt(2, false);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tabelaHospedagensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaHospedagensMouseClicked
        PainelHospedagens.setEnabledAt(1, true);
        PainelHospedagens.setSelectedIndex(1);
        SetarCampos_PropriedadesHospedagem();
        //if(evt.getClickCount()==2){
        PesquisarDadosDiaria();
        //}
        CalcularValorFinalServicos();
        CalcularValorFinalHospedagem();
        PesquisarServicosHospedagem();
    }//GEN-LAST:event_tabelaHospedagensMouseClicked

    private void ServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ServicosActionPerformed
        PainelHospedagens.setEnabledAt(2, true);
        PainelHospedagens.setSelectedIndex(2);
        PesquisarServicos();
        cmbCodServico();

    }//GEN-LAST:event_ServicosActionPerformed

    private void tabelaListaServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaListaServicosMouseClicked
        SetarCamposServicos();
        String qtd = cmbQtdServ.getSelectedItem().toString();
        double valorTotal = Integer.parseInt(qtd) * Double.parseDouble(txtValorUnit.getText());
        txtValorTotalServ.setText(Double.toString(valorTotal));
    }//GEN-LAST:event_tabelaListaServicosMouseClicked

    private void cmbQtdServItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbQtdServItemStateChanged
        String qtd = cmbQtdServ.getSelectedItem().toString();
        double valorTotal = Integer.parseInt(qtd) * Double.parseDouble(txtValorUnit.getText());
        txtValorTotalServ.setText(Double.toString(valorTotal));
    }//GEN-LAST:event_cmbQtdServItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        AdicionarServicoHospedagem();
        CalcularValorFinalServicos();
        CalcularValorFinalHospedagem();
        PainelHospedagens.setSelectedIndex(1);
        PesquisarServicosHospedagem();
        PainelHospedagens.setEnabledAt(2, false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tabelaDiariaServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDiariaServicosMouseClicked
        int setar = tabelaDiariaServicos.getSelectedRow();
        String CodHospServ = tabelaDiariaServicos.getModel().getValueAt(setar, 0).toString();
        lblCodHS.setText(CodHospServ);
        PainelHospedagens.setSelectedIndex(2);
        PainelHospedagens.setEnabledAt(2, true);
        SetarCamposGerenciarServico();

    }//GEN-LAST:event_tabelaDiariaServicosMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        EditarServicoHospedagem();
        PesquisarServicosHospedagem();
        PainelHospedagens.setSelectedIndex(1);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        RemoverServicoHospedagem();
        PesquisarServicosHospedagem();
        PainelHospedagens.setSelectedIndex(1);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtPesquisarHospedagensKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarHospedagensKeyReleased
        //PesquisarHospedagensPorCodReserva();
    }//GEN-LAST:event_txtPesquisarHospedagensKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        FinalizarConta();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane PainelHospedagens;
    private javax.swing.JButton Servicos;
    private javax.swing.JComboBox<String> cmbCodServico;
    private javax.swing.JComboBox<String> cmbQtdServ;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCPFHospede;
    private javax.swing.JLabel lblCodHS;
    private javax.swing.JLabel lblCodHospedagem;
    private javax.swing.JLabel lblDataEntrada;
    private javax.swing.JLabel lblDataSaida;
    private javax.swing.JLabel lblNomeHospede;
    private javax.swing.JLabel lblNumQuarto;
    private javax.swing.JLabel lblTipoQuarto;
    private javax.swing.JLabel lblValorDiaria;
    private javax.swing.JLabel lblValorFinal;
    private javax.swing.JLabel lblValorServFinal;
    private javax.swing.JLabel lblValorTotalDiaria;
    private javax.swing.JTable tabelaDiariaServicos;
    private javax.swing.JTable tabelaHospedagens;
    private javax.swing.JTable tabelaListaServicos;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtPesquisarHospedagens;
    private javax.swing.JTextField txtTipoServico;
    private javax.swing.JTextField txtValorTotalServ;
    private javax.swing.JTextField txtValorUnit;
    // End of variables declaration//GEN-END:variables
}
