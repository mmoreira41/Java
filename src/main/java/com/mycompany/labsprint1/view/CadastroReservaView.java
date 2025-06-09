package com.mycompany.labsprint1.view;

import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.controller.ClienteController;
import com.mycompany.labsprint1.controller.SalaController;
import com.mycompany.labsprint1.controller.ReservaController;
import com.mycompany.labsprint1.LabSprint1;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CadastroReservaView extends JFrame {
    private JComboBox<Cliente> cmbClientes;
    private JComboBox<Sala> cmbSalas;
    private JTextField txtData;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFim;
    private ReservaController reservaController;
    private ClienteController clienteController;
    private SalaController salaController;

    public CadastroReservaView() {
        super("Fazer Nova Reserva");
        this.reservaController = new ReservaController();
        this.clienteController = new ClienteController();
        this.salaController = new SalaController();
        inicializarComponentes();
        carregarDados();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; cmbClientes = new JComboBox<>(); panelFormulario.add(cmbClientes, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0; panelFormulario.add(new JLabel("Sala:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; cmbSalas = new JComboBox<>(); panelFormulario.add(cmbSalas, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0; panelFormulario.add(new JLabel("Data (dd/mm/aaaa):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; txtData = new JTextField(10); panelFormulario.add(txtData, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0; panelFormulario.add(new JLabel("Hora Início (HH:mm):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0; txtHoraInicio = new JTextField(5); panelFormulario.add(txtHoraInicio, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0; panelFormulario.add(new JLabel("Hora Fim (HH:mm):"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0; txtHoraFim = new JTextField(5); panelFormulario.add(txtHoraFim, gbc);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnReservar = new JButton("Fazer Reserva");
        JButton btnCancelar = new JButton("Cancelar");

        btnReservar.addActionListener(e -> fazerReserva());
        btnCancelar.addActionListener(e -> dispose());

        panelBotoes.add(btnReservar);
        panelBotoes.add(btnCancelar);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void carregarDados() {
        cmbClientes.addItem(null);
        for (Cliente cliente : clienteController.listarClientesAtivos()) {
            cmbClientes.addItem(cliente);
        }

        cmbSalas.addItem(null);
        for (Sala sala : salaController.listarSalas()) {
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

            reservaController.fazerReserva(data, horaInicio, horaFim, salaSelecionada.getCodigo(), clienteSelecionado.getCPF());
            LabSprint1.salvarDados();
            JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
             new CadastroReservaView().setVisible(true);
        });
    }
} 