package com.mycompany.labsprint1.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;
import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.controller.ReservaController;
import com.mycompany.labsprint1.LabSprint1;

public class ReservasView extends JFrame {
    private JComboBox<Cliente> cmbClientes;
    private JComboBox<Sala> cmbSalas;
    private JTextField txtData;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFim;
    private JTable tabelaReservas;
    private DefaultTableModel modeloReservas;
    private ReservaController controller;
    private JTextField txtFiltroClienteCpf;
    private JTextField txtFiltroSalaCodigo;

    public ReservasView(List<Cliente> clientes, List<Sala> salas) {
        super("Gerenciamento de Reservas");
        this.controller = new ReservaController();
        inicializarComponentes();
        carregarDados(clientes, salas);
        carregarReservas(); // Carregar reservas na inicialização
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel para fazer nova reserva (formulário)
        JPanel panelFazerReserva = new JPanel(new GridBagLayout());
        panelFazerReserva.setBorder(BorderFactory.createTitledBorder("Fazer Nova Reserva"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelFazerReserva.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; cmbClientes = new JComboBox<>(); panelFazerReserva.add(cmbClientes, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0; panelFazerReserva.add(new JLabel("Sala:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; cmbSalas = new JComboBox<>(); panelFazerReserva.add(cmbSalas, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0; panelFazerReserva.add(new JLabel("Data (dd/mm/aaaa):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; txtData = new JTextField(10); panelFazerReserva.add(txtData, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0; panelFazerReserva.add(new JLabel("Hora Início (HH:mm):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0; txtHoraInicio = new JTextField(5); panelFazerReserva.add(txtHoraInicio, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0; panelFazerReserva.add(new JLabel("Hora Fim (HH:mm):"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0; txtHoraFim = new JTextField(5); panelFazerReserva.add(txtHoraFim, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        JButton btnReservar = new JButton("Fazer Reserva");
        btnReservar.addActionListener(e -> fazerReserva());
        panelFazerReserva.add(btnReservar, gbc);

        // Painel para listagem de reservas (tabela)
        JPanel panelListagemReserva = new JPanel(new BorderLayout(5, 5));
        panelListagemReserva.setBorder(BorderFactory.createTitledBorder("Reservas Existentes"));

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.add(new JLabel("Filtrar por Cliente (CPF):"));
        txtFiltroClienteCpf = new JTextField(10);
        panelFiltros.add(txtFiltroClienteCpf);
        panelFiltros.add(new JLabel("Filtrar por Sala (Código):"));
        txtFiltroSalaCodigo = new JTextField(10);
        panelFiltros.add(txtFiltroSalaCodigo);
        JButton btnAplicarFiltro = new JButton("Aplicar Filtro");
        btnAplicarFiltro.addActionListener(e -> carregarReservas());
        panelFiltros.add(btnAplicarFiltro);

        String[] colunas = {"ID", "Cliente (CPF)", "Sala (Código)", "Data", "Hora Início", "Hora Fim"};
        modeloReservas = new DefaultTableModel(colunas, 0);
        tabelaReservas = new JTable(modeloReservas);
        JScrollPane scrollPaneReservas = new JScrollPane(tabelaReservas);

        JPanel panelBotoesListagem = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelarReserva = new JButton("Cancelar Reserva");
        btnCancelarReserva.addActionListener(e -> cancelarReservaSelecionada());
        panelBotoesListagem.add(btnCancelarReserva);
        JButton btnAtualizarLista = new JButton("Atualizar Lista");
        btnAtualizarLista.addActionListener(e -> carregarReservas());
        panelBotoesListagem.add(btnAtualizarLista);

        panelListagemReserva.add(panelFiltros, BorderLayout.NORTH);
        panelListagemReserva.add(scrollPaneReservas, BorderLayout.CENTER);
        panelListagemReserva.add(panelBotoesListagem, BorderLayout.SOUTH);

        // Divide a tela em duas partes: fazer reserva e listagem
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFazerReserva, panelListagemReserva);
        splitPane.setResizeWeight(0.3); // Define a proporção inicial
        add(splitPane, BorderLayout.CENTER);
    }

    private void carregarDados(List<Cliente> clientes, List<Sala> salas) {
        cmbClientes.addItem(null);
        for (Cliente cliente : clientes) {
            if (cliente.isAtivo()) {
                cmbClientes.addItem(cliente);
            }
        }

        cmbSalas.addItem(null);
        for (Sala sala : salas) {
            cmbSalas.addItem(sala);
        }
    }

    private void fazerReserva() {
        Cliente clienteSelecionado = (Cliente) cmbClientes.getSelectedItem();
        Sala salaSelecionada = (Sala) cmbSalas.getSelectedItem();
        String dataStr = txtData.getText();
        String horaInicioStr = txtHoraInicio.getText();
        String horaFimStr = txtHoraFim.getText();

        if (clienteSelecionado == null || salaSelecionada == null ||
            dataStr.isEmpty() || horaInicioStr.isEmpty() || horaFimStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!");
            return;
        }

        try {
            DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate data = LocalDate.parse(dataStr, dataFormatter);
            LocalTime horaInicio = LocalTime.parse(horaInicioStr, horaFormatter);
            LocalTime horaFim = LocalTime.parse(horaFimStr, horaFormatter);

            controller.fazerReserva(data, horaInicio, horaFim, salaSelecionada.getCodigo(), clienteSelecionado.getCPF());
            LabSprint1.salvarDados();
            JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarReservas(); // Atualiza a lista após fazer a reserva
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data ou hora inválido. Use dd/mm/aaaa para data e HH:mm para hora.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao fazer reserva: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        cmbClientes.setSelectedIndex(0);
        cmbSalas.setSelectedIndex(0);
        txtData.setText("");
        txtHoraInicio.setText("");
        txtHoraFim.setText("");
    }

    private void carregarReservas() {
        modeloReservas.setRowCount(0); // Limpa a tabela antes de carregar
        List<Reserva> reservas = controller.listarTodas();
        
        String filtroClienteCpf = txtFiltroClienteCpf.getText().trim();
        String filtroSalaCodigo = txtFiltroSalaCodigo.getText().trim();

        for (Reserva reserva : reservas) {
            boolean passaFiltroCliente = filtroClienteCpf.isEmpty() || reserva.getCliente().getCPF().contains(filtroClienteCpf);
            boolean passaFiltroSala = filtroSalaCodigo.isEmpty() || reserva.getSala().getCodigo().contains(filtroSalaCodigo);

            if (passaFiltroCliente && passaFiltroSala) {
                 modeloReservas.addRow(new Object[]{
                    reserva.getId(),
                    reserva.getCliente().getCPF() + " - " + reserva.getCliente().getNome(),
                    reserva.getSala().getCodigo() + " - " + reserva.getSala().getLocal(),
                    reserva.getData(),
                    reserva.getHoraInicio(),
                    reserva.getHoraFim()
                });
            }
        }
    }
    
    private void cancelarReservaSelecionada() {
        int linhaSelecionada = tabelaReservas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione uma reserva para cancelar.", 
                "Nenhuma reserva selecionada", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idReserva = (String) tabelaReservas.getValueAt(linhaSelecionada, 0);

        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja cancelar a reserva com ID " + idReserva + "?",
            "Confirmar cancelamento",
            JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                if (controller.cancelarReserva(idReserva)) {
                    LabSprint1.salvarDados();
                    JOptionPane.showMessageDialog(this,
                        "Reserva cancelada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                    carregarReservas(); // Atualiza a lista após cancelar
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Erro ao cancelar reserva. Reserva não encontrada.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao cancelar reserva: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            List<Cliente> clientesExemplo = new ArrayList<>();
            clientesExemplo.add(new Cliente("Cliente Teste", "12345678901", false));

            List<Sala> salasExemplo = new ArrayList<>();

            new ReservasView(clientesExemplo, salasExemplo).setVisible(true);
        });
    }
}