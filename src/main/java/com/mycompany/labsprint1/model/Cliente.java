package com.mycompany.labsprint1.model;
import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;
    private boolean corporativo;
    private boolean ativo;

    public Cliente(String nome, String cpf, boolean corporativo) {
        this.nome = nome;
        this.cpf = cpf;
        this.corporativo = corporativo;
        this.ativo = true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        if (cpf != null && cpf.matches("\\d{11}")) {
            this.cpf = cpf;
        } else {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }
    }

    public boolean isCorporativo() {
        return corporativo;
    }

    public void setCorporativo(boolean corporativo) {
        this.corporativo = corporativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getTipo() {
        return corporativo ? "Corporativo" : "Individual";
    }

    public String getStatus() {
        return ativo ? "Ativo" : "Cancelado";
    }

    public boolean verificarTipo(String tipo) {
        if (tipo == null) {
            return false;
        }
        return tipo.equalsIgnoreCase(getTipo());
    }

    @Override
    public String toString() {
        return nome + " (" + getStatus() + ")";
    }
}
