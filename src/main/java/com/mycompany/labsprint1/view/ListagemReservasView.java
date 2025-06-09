package com.mycompany.labsprint1.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.controller.ReservaController;
import com.mycompany.labsprint1.LabSprint1;
import java.time.format.DateTimeFormatter;

public class ListagemReservasView extends JFrame {
    private JTable tabelaReservas;
    private DefaultTableModel modeloReservas;
    private ReservaController controller;
    private JTextField txtFiltroClienteCpf;
    private JTextField txtFiltroSalaCodigo;
    private JCheckBox filtroAtivasCheckBox;

    public ListagemReservasView() {
        super("Listagem de Reservas");
        controller = new ReservaController();
        inicializarComponentes();
        carregarReservas();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelControles = new JPanel(new BorderLayout());

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.add(new JLabel("Filtrar por Cliente (CPF):"));
        txtFiltroClienteCpf = new JTextField(10);
        panelFiltros.add(txtFiltroClienteCpf);
        panelFiltros.add(new JLabel("Filtrar por Sala (Código):"));
        txtFiltroSalaCodigo = new JTextField(10);
        panelFiltros.add(txtFiltroSalaCodigo);

        filtroAtivasCheckBox = new JCheckBox("Mostrar apenas reservas ativas");
        filtroAtivasCheckBox.addActionListener(e -> carregarReservas());
        panelFiltros.add(filtroAtivasCheckBox);

        JButton btnAplicarFiltro = new JButton("Aplicar Filtro");
        btnAplicarFiltro.addActionListener(e -> carregarReservas());
        panelFiltros.add(btnAplicarFiltro);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelarReserva = new JButton("Cancelar Reserva");
        btnCancelarReserva.addActionListener(e -> cancelarReservaSelecionada());
        panelBotoes.add(btnCancelarReserva);
        JButton btnAtualizarLista = new JButton("Atualizar Lista");
        btnAtualizarLista.addActionListener(e -> carregarReservas());
        panelBotoes.add(btnAtualizarLista);

        panelControles.add(panelFiltros, BorderLayout.WEST);
        panelControles.add(panelBotoes, BorderLayout.EAST);

        add(panelControles, BorderLayout.NORTH);

        String[] colunas = {"ID", "Cliente (CPF)", "Sala (Código)", "Data", "Hora Início", "Hora Fim", "Status"};
        modeloReservas = new DefaultTableModel(colunas, 0);
        tabelaReservas = new JTable(modeloReservas);
        JScrollPane scrollPaneReservas = new JScrollPane(tabelaReservas);
        add(scrollPaneReservas, BorderLayout.CENTER);
    }

    private void carregarReservas() {
        modeloReservas.setRowCount(0);
        List<Reserva> reservas = controller.listarTodas();
        
        String filtroClienteCpf = txtFiltroClienteCpf.getText().trim();
        String filtroSalaCodigo = txtFiltroSalaCodigo.getText().trim();
        boolean filtrarAtivas = filtroAtivasCheckBox.isSelected();

        for (Reserva reserva : reservas) {
            if (reserva.getCliente() == null || reserva.getSala() == null) {
                continue;
            }

            boolean passaFiltroCliente = filtroClienteCpf.isEmpty() || reserva.getCliente().getCPF().contains(filtroClienteCpf);
            boolean passaFiltroSala = filtroSalaCodigo.isEmpty() || reserva.getSala().getCodigo().contains(filtroSalaCodigo);
            boolean passaFiltroAtivas = !filtrarAtivas || reserva.isAtiva();

            if (passaFiltroCliente && passaFiltroSala && passaFiltroAtivas) {
                 modeloReservas.addRow(new Object[]{
                    reserva.getId(),
                    reserva.getCliente().getCPF() + " - " + reserva.getCliente().getNome(),
                    reserva.getSala().getCodigo() + " - " + reserva.getSala().getLocal(),
                    reserva.getData(),
                    reserva.getHoraInicio(),
                    reserva.getHoraFim(),
                    reserva.getStatus()
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
                    carregarReservas();
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
            new ListagemReservasView().setVisible(true);
        });
    }
}