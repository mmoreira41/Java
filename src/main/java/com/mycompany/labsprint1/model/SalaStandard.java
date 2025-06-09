package com.mycompany.labsprint1.model;

import java.io.Serializable;
import java.util.List;

public class SalaStandard extends Sala implements Serializable {
    private static final long serialVersionUID = 1L;

    public SalaStandard() {
        super();
    }

    @Override
    public double calcularCusto(double hora) {
        return 50 * hora;
    }

    @Override
    public double calcularCancelamento(double custoTotal) {
        return custoTotal * 0.60;
    }

    @Override
    public double getValorHora() {
        return 50;
    }

    public String getTipo() {
        return "Standard";
    }
}
