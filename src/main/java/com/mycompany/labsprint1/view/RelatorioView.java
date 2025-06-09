package com.mycompany.labsprint1.view;

import com.mycompany.labsprint1.controller.RelatorioController;
import com.mycompany.labsprint1.controller.ClienteController;
import com.mycompany.labsprint1.controller.ReservaController;
import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.model.Sala;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RelatorioView extends JFrame {
    private RelatorioController controller;
    private ClienteController clienteController;
    private ReservaController reservaController;
    private JTextArea areaRelatorioGeral;
    private JTable tabelaResultados;
    private DefaultTableModel modeloResultados;
    private JTextField dataInicioField;
    private JTextField dataFimField;
    private JTextField cpfClienteField;
    private JTextField codigoSalaField;
    private CardLayout cardLayoutResultados;
    private JPanel panelResultados;

    public RelatorioView() {
        super("Relatórios do Sistema");
        this.controller = new RelatorioController();
        this.clienteController = new ClienteController();
        this.reservaController = new ReservaController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel de Controles (Filtros e Botões de Relatório)
        JPanel panelControles = new JPanel(new BorderLayout(10, 10));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de Filtros
        JPanel panelFiltros = new JPanel(new GridLayout(2, 2, 10, 5));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        // Filtros de Data
        JPanel panelFiltrosData = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltrosData.add(new JLabel("Data Início (dd/MM/yyyy):"));
        dataInicioField = new JTextField(10);
        panelFiltrosData.add(dataInicioField);
        panelFiltrosData.add(new JLabel("Data Fim (dd/MM/yyyy):"));
        dataFimField = new JTextField(10);
        panelFiltrosData.add(dataFimField);

        panelFiltros.add(panelFiltrosData);

        // Painel de Botões de Relatório
        JPanel panelBotoesRelatorio = new JPanel(new GridLayout(3, 2, 10, 10));
        panelBotoesRelatorio.setBorder(BorderFactory.createTitledBorder("Relatórios"));

        // Relatórios de Clientes
        JButton btnClientesAtivos = new JButton("Clientes Ativos");
        JButton btnClientesFrequentes = new JButton("Clientes Mais Frequentes");
        btnClientesAtivos.addActionListener(e -> gerarRelatorioClientesAtivos());
        btnClientesFrequentes.addActionListener(e -> gerarRelatorioClientesFrequentes());

        // Relatórios de Salas
        JButton btnSalasOcupacao = new JButton("Taxa de Cancelamento");
        JButton btnSalasFaturamento = new JButton("Faturamento por Sala");
        btnSalasOcupacao.addActionListener(e -> gerarRelatorioTaxaCancelamento());
        btnSalasFaturamento.addActionListener(e -> gerarRelatorioFaturamentoSalas());

        // Relatórios de Reservas
        JButton btnReservasPeriodo = new JButton("Reservas por Período");
        JButton btnFaturamentoTotal = new JButton("Faturamento Total");
        btnReservasPeriodo.addActionListener(e -> gerarRelatorioReservasPeriodo());
        btnFaturamentoTotal.addActionListener(e -> gerarRelatorioFaturamentoTotal());

        // Adiciona botões aos painéis
        JPanel panelClientes = new JPanel(new GridLayout(2, 1, 5, 5));
        panelClientes.setBorder(BorderFactory.createTitledBorder("Clientes"));
        panelClientes.add(btnClientesAtivos);
        panelClientes.add(btnClientesFrequentes);

        JPanel panelSalas = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSalas.setBorder(BorderFactory.createTitledBorder("Salas"));
        panelSalas.add(btnSalasOcupacao);
        panelSalas.add(btnSalasFaturamento);

        JPanel panelReservas = new JPanel(new GridLayout(2, 1, 5, 5));
        panelReservas.setBorder(BorderFactory.createTitledBorder("Reservas"));
        panelReservas.add(btnReservasPeriodo);
        panelReservas.add(btnFaturamentoTotal);

        panelBotoesRelatorio.add(panelClientes);
        panelBotoesRelatorio.add(panelSalas);
        panelBotoesRelatorio.add(panelReservas);

        panelControles.add(panelFiltros, BorderLayout.NORTH);
        panelControles.add(panelBotoesRelatorio, BorderLayout.CENTER);

        add(panelControles, BorderLayout.NORTH);

        // Painel para exibir os resultados
        cardLayoutResultados = new CardLayout();
        panelResultados = new JPanel(cardLayoutResultados);

        // Área de texto para relatórios gerais
        areaRelatorioGeral = new JTextArea();
        areaRelatorioGeral.setEditable(false);
        areaRelatorioGeral.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPaneRelatorioGeral = new JScrollPane(areaRelatorioGeral);
        panelResultados.add(scrollPaneRelatorioGeral, "Geral");

        // Tabela para resultados tabulares
        String[] colunas = {"Coluna 1", "Coluna 2", "Coluna 3"};
        modeloResultados = new DefaultTableModel(colunas, 0);
        tabelaResultados = new JTable(modeloResultados);
        JScrollPane scrollPaneTabela = new JScrollPane(tabelaResultados);
        panelResultados.add(scrollPaneTabela, "Tabela");

        add(panelResultados, BorderLayout.CENTER);
    }

    private void gerarRelatorioClientesAtivos() {
        List<Cliente> clientes = clienteController.listarClientes().stream()
                .filter(Cliente::isAtivo)
                .collect(Collectors.toList());

        modeloResultados.setColumnIdentifiers(new String[]{"Nome", "CPF", "Tipo", "Status"});
        modeloResultados.setRowCount(0);

        for (Cliente c : clientes) {
            modeloResultados.addRow(new Object[]{
                c.getNome(),
                c.getCPF(),
                c.isCorporativo() ? "Corporativo" : "Individual",
                "Ativo"
            });
        }

        cardLayoutResultados.show(panelResultados, "Tabela");
    }

    private void gerarRelatorioClientesFrequentes() {
        Map<Cliente, Long> clientesFrequentes = reservaController.listarTodas().stream()
                .collect(Collectors.groupingBy(
                        Reserva::getCliente,
                        Collectors.counting()
                ));

        modeloResultados.setColumnIdentifiers(new String[]{"Nome", "CPF", "Tipo", "Total de Reservas"});
        modeloResultados.setRowCount(0);

        clientesFrequentes.entrySet().stream()
                .sorted(Map.Entry.<Cliente, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    Cliente c = entry.getKey();
                    modeloResultados.addRow(new Object[]{
                        c.getNome(),
                        c.getCPF(),
                        c.isCorporativo() ? "Corporativo" : "Individual",
                        entry.getValue()
                    });
                });

        cardLayoutResultados.show(panelResultados, "Tabela");
    }

    private void gerarRelatorioTaxaCancelamento() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataInicio = LocalDate.parse(dataInicioField.getText(), formatter);
            LocalDate dataFim = dataFimField.getText().isEmpty() ? 
                    LocalDate.now() : 
                    LocalDate.parse(dataFimField.getText(), formatter);

            List<Reserva> reservas = reservaController.listarTodas().stream()
                    .filter(r -> !r.getData().isBefore(dataInicio) && !r.getData().isAfter(dataFim))
                    .collect(Collectors.toList());

            Map<String, Long> cancelamentosPorTipo = reservas.stream()
                    .filter(r -> !r.isAtiva())
                    .collect(Collectors.groupingBy(
                            r -> r.getSala().getClass().getSimpleName(),
                            Collectors.counting()
                    ));

            modeloResultados.setColumnIdentifiers(new String[]{
                "Tipo de Sala", 
                "Total de Cancelamentos",
                "Taxa de Cancelamento (%)"
            });
            modeloResultados.setRowCount(0);

            Map<String, Long> totalReservasPorTipo = reservas.stream()
                    .collect(Collectors.groupingBy(
                            r -> r.getSala().getClass().getSimpleName(),
                            Collectors.counting()
                    ));

            cancelamentosPorTipo.forEach((tipo, cancelamentos) -> {
                long totalReservas = totalReservasPorTipo.getOrDefault(tipo, 0L);
                double taxaCancelamento = totalReservas > 0 ? 
                    (double) cancelamentos / totalReservas * 100 : 0;

                modeloResultados.addRow(new Object[]{
                    tipo,
                    cancelamentos,
                    String.format("%.2f", taxaCancelamento)
                });
            });

            cardLayoutResultados.show(panelResultados, "Tabela");

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira as datas no formato dd/MM/yyyy",
                "Erro de Formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarRelatorioFaturamentoSalas() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataInicio = LocalDate.parse(dataInicioField.getText(), formatter);
            LocalDate dataFim = dataFimField.getText().isEmpty() ? 
                    LocalDate.now() : 
                    LocalDate.parse(dataFimField.getText(), formatter);

            Map<String, Double> faturamentoPorTipoSala = controller.calcularTotalArrecadadoPorTipoSala(dataInicio, dataFim);

            modeloResultados.setColumnIdentifiers(new String[]{"Tipo de Sala", "Faturamento Total (R$)"});
            modeloResultados.setRowCount(0);

            faturamentoPorTipoSala.forEach((tipo, faturamento) -> {
                modeloResultados.addRow(new Object[]{
                    tipo,
                    String.format("%.2f", faturamento)
                });
            });

            cardLayoutResultados.show(panelResultados, "Tabela");

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira as datas no formato dd/MM/yyyy",
                "Erro de Formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarRelatorioReservasPeriodo() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataInicio = null;
            LocalDate dataFim = null;
            String dataInicioStr = dataInicioField.getText().trim();
            String dataFimStr = dataFimField.getText().trim();

            if (!dataInicioStr.isEmpty()) {
                dataInicio = LocalDate.parse(dataInicioStr, formatter);
            }
            if (!dataFimStr.isEmpty()) {
                dataFim = LocalDate.parse(dataFimStr, formatter);
            }

            List<Reserva> reservas;
            if (dataInicio != null && dataFim != null) {
                final LocalDate finalDataInicio = dataInicio;
                final LocalDate finalDataFim = dataFim;
                reservas = reservaController.listarTodas().stream()
                    .filter(r -> !r.getData().isBefore(finalDataInicio) && !r.getData().isAfter(finalDataFim))
                    .collect(Collectors.toList());
             } else {
                 reservas = reservaController.listarTodas();
             }

            modeloResultados.setColumnIdentifiers(new String[]{"ID", "Cliente", "Sala", "Data", "Horário", "Valor"});
            modeloResultados.setRowCount(0);

            for (Reserva r : reservas) {
                modeloResultados.addRow(new Object[]{
                    r.getId(),
                    r.getCliente().getNome(),
                    r.getSala().getCodigo(),
                    r.getData().format(formatter),
                    r.getHoraInicio() + " - " + r.getHoraFim(),
                    String.format("R$ %.2f", r.getValorTotal())
                });
            }

            cardLayoutResultados.show(panelResultados, "Tabela");

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira as datas no formato dd/MM/yyyy",
                "Erro de Formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarRelatorioFaturamentoTotal() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataInicio = LocalDate.parse(dataInicioField.getText(), formatter);
            LocalDate dataFim = dataFimField.getText().isEmpty() ? 
                    LocalDate.now() : 
                    LocalDate.parse(dataFimField.getText(), formatter);

            double faturamentoTotal = controller.calcularTotalArrecadado(dataInicio, dataFim);
            double faturamentoCorporativo = controller.calcularFaturamentoPorTipoCliente(dataInicio, dataFim, true);
            double faturamentoIndividual = controller.calcularFaturamentoPorTipoCliente(dataInicio, dataFim, false);

            areaRelatorioGeral.setText(String.format(
                "Relatório de Faturamento Total\n" +
                "Período: %s a %s\n\n" +
                "Faturamento Total: R$ %.2f\n" +
                "Faturamento Corporativo: R$ %.2f\n" +
                "Faturamento Individual: R$ %.2f",
                dataInicio.format(formatter),
                dataFim.format(formatter),
                faturamentoTotal,
                faturamentoCorporativo,
                faturamentoIndividual
            ));

            cardLayoutResultados.show(panelResultados, "Geral");

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira as datas no formato dd/MM/yyyy",
                "Erro de Formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RelatorioView().setVisible(true));
    }
}
