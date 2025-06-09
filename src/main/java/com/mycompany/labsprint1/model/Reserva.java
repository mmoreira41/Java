package com.mycompany.labsprint1.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Sala sala;
    private Cliente cliente;
    private double custo;
    private boolean ativa;

    public Reserva(LocalDate data, LocalTime horaInicio, LocalTime horaFim,
            Sala sala, Cliente cliente) {
        this.id = "RES_" + System.currentTimeMillis();
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.sala = sala;
        this.cliente = cliente;
        this.ativa = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getCusto() {
        return custo;
    }

    public double getValorTotal() {
        return this.custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void excluirReserva() {
        System.out.println("Reserva da sala " + this.sala.getCodigo() +
                " para o Cliente " + this.cliente.getNome() + " foi marcada como cancelada.");
        this.ativa = false;
    }
}
