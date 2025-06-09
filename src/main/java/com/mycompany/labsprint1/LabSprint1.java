package com.mycompany.labsprint1;

import com.mycompany.labsprint1.controller.ClienteController;
import com.mycompany.labsprint1.controller.SalaController;
import com.mycompany.labsprint1.dao.ClienteDAO;
import com.mycompany.labsprint1.dao.ReservaDAO;
import com.mycompany.labsprint1.dao.SalaDAO;
import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.controller.ReservaController;
import com.mycompany.labsprint1.view.MenuPrincipalView;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import java.util.List;

public class LabSprint1 {
    private static ClienteController clienteController = new ClienteController();
    private static SalaController salaController = new SalaController();
    private static ReservaController reservaController = new ReservaController();

    public static void main(String[] args) {
        carregarDados();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MenuPrincipalView view = new MenuPrincipalView();
            view.setVisible(true);
        });
    }

    public static void salvarDados() {
        FileManager.salvarObjeto("salas.dat", salaController.listarSalas());
        FileManager.salvarObjeto("clientes.dat", clienteController.listarClientes());
        FileManager.salvarObjeto("reservas.dat", reservaController.listarTodas());
    }

    public static void carregarDados() {
        List<Sala> salas = SalaDAO.getInstance().listarTodas();
        List<Cliente> clientes = ClienteDAO.getInstance().listarTodos();
        List<Reserva> reservas = ReservaDAO.getInstance().listarTodas();

        System.out.println("Salas: " + salas.size());
        System.out.println("Clientes: " + clientes.size());
        System.out.println("Reservas: " + reservas.size());
    }

    // public static void carregarDados() {
    // List<Sala> salasCarregadas = SalaDAO.getInstance().listarTodas();
    // salaController.setSalas(salasCarregadas);

    // List<Cliente> clientesCarregados = ClienteDAO.getInstance().listarTodos();
    // clienteController.setClientes(clientesCarregados);

    // List<Reserva> reservasCarregadas = ReservaDAO.getInstance().listarTodas();
    // reservaController.setReservas(reservasCarregadas);

    // System.out.println("Dados carregados do banco.");
    // }

}