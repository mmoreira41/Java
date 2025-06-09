package com.mycompany.labsprint1.view;

import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.controller.ClienteController;
import javax.swing.*;
import java.awt.*;

public class CadastroClienteView extends JFrame {
    private JTextField nomeField;
    private JTextField cpfField;
    private JCheckBox corporativoCheckBox;
    private ClienteController controller;

    public CadastroClienteView() {
        super("Cadastro de Cliente");
        controller = new ClienteController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nomeField = new JTextField(20);
        panel.add(nomeField, gbc);

        // CPF
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cpfField = new JTextField(20);
        panel.add(cpfField, gbc);

        // Corporativo
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        corporativoCheckBox = new JCheckBox("Cliente Corporativo");
        panel.add(corporativoCheckBox, gbc);

        add(panel, BorderLayout.CENTER);

        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");

        salvarButton.addActionListener(e -> salvarCliente());
        cancelarButton.addActionListener(e -> dispose());

        panelBotoes.add(salvarButton);
        panelBotoes.add(cancelarButton);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void salvarCliente() {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        boolean corporativo = corporativoCheckBox.isSelected();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "O nome não pode ser vazio.",
                "Campo obrigatório",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this,
                "O CPF deve conter exatamente 11 dígitos numéricos.",
                "CPF inválido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Verificar duplicidade de CPF
        if (controller.buscarClientePorCPF(cpf) != null) {
            JOptionPane.showMessageDialog(this,
                "Já existe um cliente cadastrado com este CPF.",
                "CPF duplicado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Cliente cliente = new Cliente(nome, cpf, corporativo);
            controller.cadastrarCliente(cliente.getNome(), cliente.getCPF(), cliente.isCorporativo());
            JOptionPane.showMessageDialog(this,
                "Cliente cadastrado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao cadastrar cliente: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroClienteView().setVisible(true));
    }
} 