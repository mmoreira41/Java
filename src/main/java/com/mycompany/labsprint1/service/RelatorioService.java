package com.mycompany.labsprint1.service;

import com.mycompany.labsprint1.dao.ClienteDAO;
import com.mycompany.labsprint1.dao.SalaDAO;
import com.mycompany.labsprint1.dao.ReservaDAO;
import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.Reserva;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RelatorioService {
    private final ClienteDAO clienteDAO;
    private final SalaDAO salaDAO;
    private final ReservaDAO reservaDAO;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public RelatorioService() {
        this.clienteDAO = ClienteDAO.getInstance();
        this.salaDAO = SalaDAO.getInstance();
        this.reservaDAO = ReservaDAO.getInstance();
    }

    // Relatórios de Clientes
    public List<String> gerarRelatorioClientesAtivos() {
        return clienteDAO.listarTodos().stream()
            .filter(Cliente::isAtivo)
            .map(cliente -> String.format("Nome: %s | CPF: %s | Tipo: %s",
                cliente.getNome(),
                cliente.getCPF(),
                cliente.isCorporativo() ? "Corporativo" : "Individual"))
            .collect(Collectors.toList());
    }

    public Map<String, Integer> gerarEstatisticasClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();
        Map<String, Integer> estatisticas = new HashMap<>();
        
        estatisticas.put("Total", clientes.size());
        estatisticas.put("Ativos", (int) clientes.stream().filter(Cliente::isAtivo).count());
        estatisticas.put("Corporativos", (int) clientes.stream().filter(Cliente::isCorporativo).count());
        estatisticas.put("Individuais", (int) clientes.stream().filter(c -> !c.isCorporativo()).count());
        
        return estatisticas;
    }

    // Relatórios de Salas
    public List<String> gerarRelatorioSalasDisponiveis() {
        return salaDAO.listarTodas().stream()
            .filter(Sala::isDisponivel)
            .map(sala -> String.format("Sala: %s | Capacidade: %d | Tipo: %s",
                sala.getCodigo(),
                sala.getCapacidade(),
                sala.getClass().getSimpleName()))
            .collect(Collectors.toList());
    }

    public Map<String, Object> gerarEstatisticasSalas() {
        List<Sala> salas = salaDAO.listarTodas();
        Map<String, Object> estatisticas = new HashMap<>();
        
        estatisticas.put("Total", salas.size());
        estatisticas.put("Disponíveis", salas.stream().filter(Sala::isDisponivel).count());
        estatisticas.put("Capacidade Total", salas.stream().mapToInt(Sala::getCapacidade).sum());
        estatisticas.put("Tipos", salas.stream()
            .collect(Collectors.groupingBy(
                sala -> sala.getClass().getSimpleName(),
                Collectors.counting()
            )));
        
        return estatisticas;
    }

    // Relatórios de Reservas
    public List<String> gerarRelatorioReservasAtivas() {
        return reservaDAO.listarTodas().stream()
            .filter(Reserva::isAtiva)
            .map(reserva -> String.format("Cliente: %s | Sala: %s | Data: %s %s-%s",
                reserva.getCliente().getNome(),
                reserva.getSala().getCodigo(),
                reserva.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                reserva.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")),
                reserva.getHoraFim().format(DateTimeFormatter.ofPattern("HH:mm"))))
            .collect(Collectors.toList());
    }

    public Map<String, Object> gerarEstatisticasReservas() {
        List<Reserva> reservas = reservaDAO.listarTodas();
        Map<String, Object> estatisticas = new HashMap<>();
        
        estatisticas.put("Total", reservas.size());
        estatisticas.put("Ativas", reservas.stream().filter(Reserva::isAtiva).count());
        estatisticas.put("Por Cliente", reservas.stream()
            .collect(Collectors.groupingBy(
                r -> r.getCliente().getCPF(),
                Collectors.counting()
            )));
        estatisticas.put("Por Sala", reservas.stream()
            .collect(Collectors.groupingBy(
                r -> r.getSala().getCodigo(),
                Collectors.counting()
            )));
        
        return estatisticas;
    }

    // Relatório Consolidado
    public Map<String, Object> gerarRelatorioConsolidado() {
        Map<String, Object> consolidado = new HashMap<>();
        
        consolidado.put("Clientes", gerarEstatisticasClientes());
        consolidado.put("Salas", gerarEstatisticasSalas());
        consolidado.put("Reservas", gerarEstatisticasReservas());
        
        return consolidado;
    }

    // Relatório de Ocupação por Período
    public List<String> gerarRelatorioOcupacaoPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return reservaDAO.listarTodas().stream()
            .filter(reserva -> !reserva.getData().isBefore(dataInicio) && 
                             !reserva.getData().isAfter(dataFim))
            .map(reserva -> String.format("Sala: %s | Data: %s %s-%s | Status: %s",
                reserva.getSala().getCodigo(),
                reserva.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                reserva.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")),
                reserva.getHoraFim().format(DateTimeFormatter.ofPattern("HH:mm")),
                reserva.isAtiva() ? "Ativa" : "Cancelada"))
            .collect(Collectors.toList());
    }
} 