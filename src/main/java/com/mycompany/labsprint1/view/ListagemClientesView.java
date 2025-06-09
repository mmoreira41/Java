package com.mycompany.labsprint1.view;

import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.controller.ClienteController;
import com.mycompany.labsprint1.LabSprint1;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListagemClientesView extends JFrame {
    private JTable tabelaClientes;
    private ClienteController controller;
    private JButton atualizarButton;
    private JButton excluirButton;
    private JButton cadastrarButton;
    private JCheckBox filtroAtivosCheckBox;

    public ListagemClientesView() {
        super("Listagem de Clientes");
        controller = new ClienteController();
        inicializarComponentes();
        carregarClientes();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tabelaClientes = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelControles = new JPanel(new BorderLayout());

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroAtivosCheckBox = new JCheckBox("Mostrar apenas clientes ativos");
        filtroAtivosCheckBox.addActionListener(e -> carregarClientes());
        panelFiltro.add(filtroAtivosCheckBox);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cadastrarButton = new JButton("Cadastrar Cliente");
        atualizarButton = new JButton("Atualizar");
        excluirButton = new JButton("Excluir Cliente");
        
        cadastrarButton.addActionListener(e -> abrirCadastroCliente());
        atualizarButton.addActionListener(e -> carregarClientes());
        excluirButton.addActionListener(e -> excluirClienteSelecionado());
        
        panelBotoes.add(cadastrarButton);
        panelBotoes.add(atualizarButton);
        panelBotoes.add(excluirButton);

        panelControles.add(panelFiltro, BorderLayout.WEST);
        panelControles.add(panelBotoes, BorderLayout.EAST);

        add(panelControles, BorderLayout.SOUTH);
    }

    private void abrirCadastroCliente() {
        CadastroClienteView cadastroView = new CadastroClienteView();
        cadastroView.setVisible(true);
        cadastroView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarClientes();
            }
        });
    }

    private void carregarClientes() {
        List<Cliente> clientes;
        if (filtroAtivosCheckBox.isSelected()) {
            clientes = controller.listarClientesAtivos();
        } else {
            clientes = controller.listarClientes();
        }
        
        String[] colunas = {"Nome", "CPF", "Tipo", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        for (Cliente c : clientes) {
            String status = c.isAtivo() ? "Ativo" : "Cancelado";
            String tipo = c.isCorporativo() ? "Corporativo" : "Individual";
            modelo.addRow(new Object[]{c.getNome(), c.getCPF(), tipo, status});
        }
        tabelaClientes.setModel(modelo);
    }

    private void excluirClienteSelecionado() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione um cliente para excluir.", 
                "Nenhum cliente selecionado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = (String) tabelaClientes.getValueAt(linhaSelecionada, 1);
        String nome = (String) tabelaClientes.getValueAt(linhaSelecionada, 0);

        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir o cliente " + nome + "?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                if (controller.excluirCliente(cpf)) {
                    LabSprint1.salvarDados();
                    JOptionPane.showMessageDialog(this,
                        "Cliente excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                    carregarClientes();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Erro ao excluir cliente. Cliente não encontrado.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir cliente: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListagemClientesView().setVisible(true));
    }
} 