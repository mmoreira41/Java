package com.mycompany.labsprint1.model;
import java.io.Serializable;

public class Recurso implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;

    public Recurso(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }
}
