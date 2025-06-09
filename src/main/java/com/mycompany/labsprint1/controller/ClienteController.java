package com.mycompany.labsprint1.controller;

import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.service.ClienteService;
import java.util.List;

public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteService();
    }

    public boolean cadastrarCliente(String nome, String cpf, boolean corporativo) {
        try {
            clienteService.salvar(new Cliente(nome, cpf, corporativo));
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public Cliente buscarClientePorCPF(String cpf) {
        return clienteService.buscarPorCPF(cpf);
    }

    public List<Cliente> listarClientes() {
        return clienteService.listarTodos();
    }

    public List<Cliente> listarClientesAtivos() {
        return clienteService.listarAtivos();
    }

    public boolean excluirCliente(String cpf) {
        try {
            return clienteService.excluir(cpf);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    // public void setClientes(List<Cliente> clientes) {
    // if (clientes == null) {
    // throw new IllegalArgumentException("Lista de clientes não pode ser nula");
    // }
    // clienteService.setClientes(clientes);
    // }
}