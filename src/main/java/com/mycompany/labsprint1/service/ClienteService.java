package com.mycompany.labsprint1.service;

import com.mycompany.labsprint1.dao.ClienteDAO;
import com.mycompany.labsprint1.model.Cliente;
import java.util.List;

public class ClienteService {
    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = ClienteDAO.getInstance();
    }

    public Cliente criarCliente(String nome, String cpf, boolean corporativo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }

        if (buscarPorCPF(cpf) != null) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        return new Cliente(nome, cpf, corporativo);
    }

    public void salvar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (cliente.getCPF() == null || !cliente.getCPF().matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }

        clienteDAO.salvar(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }

    public List<Cliente> listarAtivos() {
        return clienteDAO.listarTodos().stream()
                .filter(Cliente::isAtivo)
                .toList();
    }

    public Cliente buscarPorCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }
        return clienteDAO.buscarPorCpf(cpf);
    }

    public boolean excluir(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }

        Cliente cliente = clienteDAO.buscarPorCpf(cpf);
        if (cliente != null) {
            cliente.setAtivo(false);
            clienteDAO.atualizar(cliente);
            return true;
        }
        return false;
    }

    public void atualizar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (cliente.getCPF() == null || !cliente.getCPF().matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }

        clienteDAO.atualizar(cliente);
    }

    // public void setClientes(List<Cliente> clientes) {
    // if (clientes == null) {
    // throw new IllegalArgumentException("Lista de clientes não pode ser nula");
    // }
    // clienteDAO.setClientes(clientes);
    // }
}