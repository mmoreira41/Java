package com.mycompany.labsprint1.service;

import com.mycompany.labsprint1.dao.SalaDAO;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.SalaStandard;
import com.mycompany.labsprint1.model.SalaPremium;
import com.mycompany.labsprint1.model.SalaVip;
import java.util.List;

public class SalaService {
    private final SalaDAO salaDAO;

    public SalaService() {
        this.salaDAO = SalaDAO.getInstance();
    }

    public Sala criarSala(String codigo, int capacidade, String tipo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código da sala não pode ser vazio");
        }

        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade da sala deve ser maior que zero");
        }

        if (tipo == null) {
            throw new IllegalArgumentException("Tipo da sala não pode ser nulo");
        }

        return switch (tipo.toLowerCase()) {
            case "standard" -> new SalaStandard();
            case "premium" -> new SalaPremium();
            case "vip" -> new SalaVip();
            default -> throw new IllegalArgumentException("Tipo de sala inválido: " + tipo);
        };
    }

    public void salvar(Sala sala) {
        if (sala == null) {
            throw new IllegalArgumentException("Sala não pode ser nula");
        }

        if (sala.getCodigo() == null || sala.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Código da sala não pode ser vazio");
        }

        if (sala.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Capacidade da sala deve ser maior que zero");
        }

        salaDAO.salvar(sala);
    }

    public List<Sala> listarTodas() {
        return salaDAO.listarTodas();
    }

    public Sala buscarPorId(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código da sala não pode ser vazio");
        }
        return salaDAO.buscarPorCodigo(codigo);
    }

    public boolean excluir(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código da sala não pode ser vazio");
        }
        return salaDAO.excluir(codigo);
    }

    public void atualizar(Sala sala) {
        if (sala == null) {
            throw new IllegalArgumentException("Sala não pode ser nula");
        }

        if (sala.getCodigo() == null || sala.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Código da sala não pode ser vazio");
        }

        if (sala.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Capacidade da sala deve ser maior que zero");
        }

        salaDAO.atualizar(sala);
    }

    // public void setSalas(List<Sala> salas) {
    // if (salas == null) {
    // throw new IllegalArgumentException("Lista de salas não pode ser nula");
    // }
    // salaDAO.setSalas(salas);
    // }
}