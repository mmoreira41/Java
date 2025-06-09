package com.mycompany.labsprint1.controller;

import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.service.SalaService;
import java.util.List;

public class SalaController {
    private final SalaService salaService;

    public SalaController() {
        this.salaService = new SalaService();
    }

    public boolean cadastrarSala(String codigo, int capacidade, String tipo) {
        try {
            salaService.salvar(salaService.criarSala(codigo, capacidade, tipo));
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar sala: " + e.getMessage());
        }
    }

    public Sala buscarSalaPorCodigo(String codigo) {
        return salaService.buscarPorId(codigo);
    }

    public List<Sala> listarSalas() {
        return salaService.listarTodas();
    }

    public boolean excluirSala(String codigo) {
        try {
            return salaService.excluir(codigo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir sala: " + e.getMessage());
        }
    }

    // public void setSalas(List<Sala> salas) {
    // if (salas == null) {
    // throw new IllegalArgumentException("Lista de salas não pode ser nula");
    // }
    // salaService.setSalas(salas);
    // }
}