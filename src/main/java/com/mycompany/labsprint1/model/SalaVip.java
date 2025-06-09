package com.mycompany.labsprint1.model;

import java.io.Serializable;
import java.util.List;

public class SalaVip extends Sala implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public double calcularCusto(double hora) {
        return 50 * hora * 1.30;
    }

    @Override
    public double calcularCancelamento(double custoTotal) {
        return custoTotal * 0.30;
    }

    @Override
    public double getValorHora() {
        return 50 * 1.30;
    }

    public String getTipo() {
        return "Vip";
    }

    public SalaVip() {
        super();
    }
}
