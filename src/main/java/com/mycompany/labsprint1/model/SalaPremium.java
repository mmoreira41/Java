package com.mycompany.labsprint1.model;

import java.io.Serializable;
import java.util.List;

public class SalaPremium extends Sala implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public double calcularCusto(double hora) {
        return 50 * hora * 1.15;
    }

    @Override
    public double calcularCancelamento(double custoTotal) {
        return custoTotal * 0.40;
    }

    @Override
    public double getValorHora() {
        return 50 * 1.15;
    }

    public String getTipo() {
        return "Premium";
    }

    public SalaPremium() {
        super();
    }
}
