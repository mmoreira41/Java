package com.mycompany.labsprint1.view;

import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.controller.SalaController;
import com.mycompany.labsprint1.LabSprint1;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListagemSalasView extends JFrame {
    private JTable tabelaSalas;
    private SalaController controller;
    private JButton atualizarButton;
    private JButton excluirButton;
    private JButton cadastrarButton;

    public ListagemSalasView() {
        super("Listagem de Salas");
        controller = new SalaController();
        inicializarComponentes();
        carregarSalas();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tabelaSalas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaSalas);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cadastrarButton = new JButton("Cadastrar Sala");
        atualizarButton = new JButton("Atualizar");
        excluirButton = new JButton("Excluir Sala");
        
        cadastrarButton.addActionListener(e -> abrirCadastroSala());
        atualizarButton.addActionListener(e -> carregarSalas());
        excluirButton.addActionListener(e -> excluirSalaSelecionada());
        
        panelBotoes.add(cadastrarButton);
        panelBotoes.add(atualizarButton);
        panelBotoes.add(excluirButton);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void abrirCadastroSala() {
        CadastroSalaView cadastroView = new CadastroSalaView();
        cadastroView.setVisible(true);
        cadastroView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarSalas();
            }
        });
    }

    private void carregarSalas() {
        List<Sala> salas = controller.listarSalas();
        String[] colunas = {"Código", "Tipo", "Local", "Capacidade", "Recursos"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        for (Sala s : salas) {
            String recursos = String.join(", ", s.getRecursos().stream()
                .map(r -> r.getNome())
                .toArray(String[]::new));
            modelo.addRow(new Object[]{
                s.getCodigo(),
                s.getClass().getSimpleName(),
                s.getLocal(),
                s.getCapacidade(),
                recursos
            });
        }
        tabelaSalas.setModel(modelo);
    }

    private void excluirSalaSelecionada() {
        int linhaSelecionada = tabelaSalas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione uma sala para excluir.", 
                "Nenhuma sala selecionada", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = (String) tabelaSalas.getValueAt(linhaSelecionada, 0);
        String local = (String) tabelaSalas.getValueAt(linhaSelecionada, 2);

        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir a sala " + codigo + " (" + local + ")?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                if (controller.excluirSala(codigo)) {
                    LabSprint1.salvarDados();
                    JOptionPane.showMessageDialog(this,
                        "Sala excluída com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                    carregarSalas();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Erro ao excluir sala. Sala não encontrada.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir sala: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListagemSalasView().setVisible(true));
    }
} 