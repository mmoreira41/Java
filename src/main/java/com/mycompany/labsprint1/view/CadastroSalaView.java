package com.mycompany.labsprint1.view;

import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.SalaStandard;
import com.mycompany.labsprint1.model.SalaPremium;
import com.mycompany.labsprint1.model.SalaVip;
import com.mycompany.labsprint1.model.Recurso;
import com.mycompany.labsprint1.controller.SalaController;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CadastroSalaView extends JFrame {
    private JComboBox<String> tipoSalaCombo;
    private JTextField localField;
    private JSpinner capacidadeSpinner;
    private JList<String> recursosList;
    private DefaultListModel<String> recursosListModel;
    private JTextField novoRecursoField;
    private SalaController controller;

    public CadastroSalaView() {
        super("Cadastro de Sala");
        controller = new SalaController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tipo de Sala
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tipo de Sala:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] tipos = { "Standard", "Premium", "Vip" };
        tipoSalaCombo = new JComboBox<>(tipos);
        panel.add(tipoSalaCombo, gbc);

        // Local
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Local:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        localField = new JTextField(20);
        panel.add(localField, gbc);

        // Capacidade
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Capacidade:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
        capacidadeSpinner = new JSpinner(spinnerModel);
        panel.add(capacidadeSpinner, gbc);

        // Recursos
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Recursos:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        recursosListModel = new DefaultListModel<>();
        recursosList = new JList<>(recursosListModel);
        JScrollPane recursosScroll = new JScrollPane(recursosList);
        recursosScroll.setPreferredSize(new Dimension(200, 100));
        panel.add(recursosScroll, gbc);

        // Campo para novo recurso
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Novo Recurso:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        novoRecursoField = new JTextField(20);
        panel.add(novoRecursoField, gbc);

        // Botão para adicionar recurso
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        JButton adicionarRecursoButton = new JButton("Adicionar Recurso");
        adicionarRecursoButton.addActionListener(e -> adicionarRecurso());
        panel.add(adicionarRecursoButton, gbc);

        add(panel, BorderLayout.CENTER);

        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");

        salvarButton.addActionListener(e -> salvarSala());
        cancelarButton.addActionListener(e -> dispose());

        panelBotoes.add(salvarButton);
        panelBotoes.add(cancelarButton);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void adicionarRecurso() {
        String recurso = novoRecursoField.getText().trim();
        if (!recurso.isEmpty()) {
            recursosListModel.addElement(recurso);
            novoRecursoField.setText("");
        }
    }

    private void salvarSala() {
        String local = localField.getText().trim();
        int capacidade = (Integer) capacidadeSpinner.getValue();
        String tipo = (String) tipoSalaCombo.getSelectedItem();

        if (local.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha o local da sala.",
                    "Campo obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Recurso> recursos = new ArrayList<>();
            for (int i = 0; i < recursosListModel.size(); i++) {
                recursos.add(new Recurso(recursosListModel.getElementAt(i)));
            }

            Sala sala;
            String codigo = Sala.gerarCodigo();
            switch (tipo) {
                case "Standard":
                    sala = new SalaStandard();
                    break;
                case "Premium":
                    sala = new SalaPremium();
                    break;
                case "Vip":
                    sala = new SalaVip();
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de sala inválido");
            }

            controller.cadastrarSala(codigo, capacidade, tipo);
            JOptionPane.showMessageDialog(this,
                    "Sala cadastrada com sucesso!\nCódigo: " + codigo,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar sala: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroSalaView().setVisible(true));
    }
}