package com.mycompany.labsprint1.controller;

import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.service.ReservaService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RelatorioController {
    private final ReservaService reservaService;
    
    public RelatorioController() {
        this.reservaService = new ReservaService();
    }
    
    public double calcularTotalArrecadado(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }
        
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data inicial deve ser anterior à data final");
        }
        
        return reservaService.calcularTotalArrecadado(dataInicio, dataFim);
    }
    
    public Map<String, Double> calcularTotalArrecadadoPorTipoSala(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }
        
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data inicial deve ser anterior à data final");
        }
        
        return reservaService.calcularTotalArrecadadoPorTipoSala(dataInicio, dataFim);
    }
    
    public double calcularFaturamentoPorTipoCliente(LocalDate dataInicio, LocalDate dataFim, boolean corporativo) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }
        
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data inicial deve ser anterior à data final");
        }
        
        return reservaService.calcularFaturamentoPorTipoCliente(dataInicio, dataFim, corporativo);
    }
    
    public List<Reserva> buscarReservasPorCliente(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        
        return reservaService.buscarPorCliente(cpf);
    }
} 