package com.mycompany.labsprint1.controller;

import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.service.ReservaService;
import com.mycompany.labsprint1.service.ClienteService;
import com.mycompany.labsprint1.service.SalaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaController {
    private final ReservaService reservaService;
    private final ClienteService clienteService;
    private final SalaService salaService;

    public ReservaController() {
        this.reservaService = new ReservaService();
        this.clienteService = new ClienteService();
        this.salaService = new SalaService();
    }

    public Reserva fazerReserva(LocalDate data, LocalTime horaInicio, LocalTime horaFim, Sala sala, Cliente cliente,
            double custo) {
        if (data == null || horaInicio == null || horaFim == null || sala == null || cliente == null || custo < 0) {
            throw new IllegalArgumentException("Todos os campos são obrigatórios");
        }

        Sala sala = salaService.buscarPorId(codigoSala);
        if (sala == null) {
            throw new IllegalArgumentException("Sala não encontrada");
        }

        Cliente cliente = clienteService.buscarPorCPF(cpfCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        String id = "RES_" + System.currentTimeMillis();
        return new Reserva(id, data, horaInicio, horaFim, sala, cliente, custo);
        reservaService.salvar(reserva);
        return reserva;
    }

    public List<Reserva> buscarReservasPorCliente(String cpf) {
        return reservaService.buscarPorCliente(cpf);
    }

    public List<Reserva> buscarReservasPorSala(String codigo) {
        return reservaService.buscarPorSala(codigo);
    }

    public boolean cancelarReserva(String id) {
        return reservaService.cancelarReserva(id);
    }

    public List<Reserva> listarTodas() {
        return reservaService.listarTodas();
    }

    public Reserva buscarPorId(String id) {
        return reservaService.buscarPorId(id);
    }

    // public void setReservas(List<Reserva> reservas) {
    // if (reservas == null) {
    // throw new IllegalArgumentException("Lista de reservas não pode ser nula");
    // }
    // reservaService.setReservas(reservas);
    // }
}
