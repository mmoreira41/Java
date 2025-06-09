package com.mycompany.labsprint1.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {

    public MenuPrincipalView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Reservas de Salas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnClientes = new JButton("Gerenciar Clientes");
        JButton btnSalas = new JButton("Gerenciar Salas");
        JButton btnReservas = new JButton("Listar Reservas");
        JButton btnFazerReserva = new JButton("Fazer Nova Reserva");
        JButton btnRelatorios = new JButton("Relatórios");

        btnClientes.addActionListener(e -> abrirGerenciamentoClientes());
        btnSalas.addActionListener(e -> abrirGerenciamentoSalas());
        btnReservas.addActionListener(e -> abrirListagemReservas());
        btnFazerReserva.addActionListener(e -> abrirCadastroReserva());
        btnRelatorios.addActionListener(e -> abrirRelatorios());

        panel.add(btnClientes);
        panel.add(btnSalas);
        panel.add(btnReservas);
        panel.add(btnFazerReserva);
        panel.add(btnRelatorios);

        add(panel);
    }

    private void abrirGerenciamentoClientes() {
        ListagemClientesView view = new ListagemClientesView();
        view.setVisible(true);
    }

    private void abrirGerenciamentoSalas() {
        ListagemSalasView view = new ListagemSalasView();
        view.setVisible(true);
    }

    private void abrirListagemReservas() {
        ListagemReservasView view = new ListagemReservasView();
        view.setVisible(true);
    }

    private void abrirCadastroReserva() {
        CadastroReservaView view = new CadastroReservaView();
        view.setVisible(true);
    }

    private void abrirRelatorios() {
        RelatorioView view = new RelatorioView();
        view.setVisible(true);
    }
}
