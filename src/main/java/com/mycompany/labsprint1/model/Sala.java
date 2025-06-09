package com.mycompany.labsprint1.model;

import java.io.Serializable;
import java.util.List;

public abstract class Sala implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String local;
    private int capacidade;
    private List<Recurso> recursos;
    private int idTipoSala;

    public static String gerarCodigo() {
        return "SALA_" + System.currentTimeMillis();
    }

    public Sala() {
    }

    public abstract double calcularCusto(double hora);

    public abstract double calcularCancelamento(double valorTotal);

    public abstract double getValorHora();

    public abstract String getTipo();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public boolean isDisponivel() {
        return true; // Por padrão, todas as salas estão disponíveis
    }

    public int getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(int idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    @Override
    public String toString() {
        return this.codigo;
    }
}
