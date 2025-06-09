package com.mycompany.labsprint1;

import com.mycompany.labsprint1.controller.RelatorioController;
import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.SalaPremium;
import com.mycompany.labsprint1.model.SalaStandard;
import com.mycompany.labsprint1.model.SalaVip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RelatorioControllerTeste {
    private RelatorioController controller;
    private Cliente cliente1;
    private Cliente cliente2;
    private Sala sala1;
    private Sala sala2;
    private Sala sala3;
    private Reserva reserva1;
    private Reserva reserva2;
    private Reserva reserva3;
    private LocalDate data1;
    private LocalDate data2;
    private LocalDate data3;

    @BeforeEach
    void setUp() {
        controller = new RelatorioController();
        
        // Criar clientes
        cliente1 = new Cliente("João Silva", "123.456.789-00", false);
        cliente2 = new Cliente("Empresa XYZ", "987.654.321-00", true);
        
        // Criar salas
        sala1 = new SalaStandard("S001", "Sala 1", 100.0);
        sala2 = new SalaPremium("S002", "Sala 2", 200.0);
        sala3 = new SalaVip("S003", "Sala 3", 300.0);
        
        // Criar datas
        data1 = LocalDate.now().minusDays(2);
        data2 = LocalDate.now().minusDays(1);
        data3 = LocalDate.now();
        
        // Criar reservas
        reserva1 = new Reserva(cliente1, sala1, data1, LocalTime.of(9, 0), LocalTime.of(11, 0));
        reserva2 = new Reserva(cliente2, sala2, data2, LocalTime.of(14, 0), LocalTime.of(16, 0));
        reserva3 = new Reserva(cliente1, sala3, data3, LocalTime.of(10, 0), LocalTime.of(12, 0));
    }

    @Test
    void testCalcularTotalArrecadado() {
        // Adicionar reservas ao sistema
        controller.listarReservas().add(reserva1);
        controller.listarReservas().add(reserva2);
        controller.listarReservas().add(reserva3);

        LocalDate inicio = data1;
        LocalDate fim = data3;

        double total = controller.calcularTotalArrecadado(inicio, fim);
        
        // Verificar se o total está correto (2 horas * valor de cada sala)
        double esperado = (2 * sala1.getValorHora()) + (2 * sala2.getValorHora()) + (2 * sala3.getValorHora());
        assertEquals(esperado, total, 0.01);
    }

    @Test
    void testCalcularTotalArrecadadoPorTipoSala() {
        // Adicionar reservas ao sistema
        controller.listarReservas().add(reserva1);
        controller.listarReservas().add(reserva2);
        controller.listarReservas().add(reserva3);

        LocalDate inicio = data1;
        LocalDate fim = data3;

        Map<String, Double> totalPorTipo = controller.calcularTotalArrecadadoPorTipoSala(inicio, fim);
        
        // Verificar se os totais por tipo estão corretos
        assertEquals(2 * sala1.getValorHora(), totalPorTipo.get("SalaStandard"), 0.01);
        assertEquals(2 * sala2.getValorHora(), totalPorTipo.get("SalaPremium"), 0.01);
        assertEquals(2 * sala3.getValorHora(), totalPorTipo.get("SalaVip"), 0.01);
    }

    @Test
    void testCalcularFaturamentoPorTipoCliente() {
        // Adicionar reservas ao sistema
        controller.listarReservas().add(reserva1);
        controller.listarReservas().add(reserva2);
        controller.listarReservas().add(reserva3);

        LocalDate inicio = data1;
        LocalDate fim = data3;

        double faturamentoCorporativo = controller.calcularFaturamentoPorTipoCliente(inicio, fim, true);
        double faturamentoIndividual = controller.calcularFaturamentoPorTipoCliente(inicio, fim, false);
        
        // Verificar se os faturamentos por tipo de cliente estão corretos
        assertEquals(2 * sala2.getValorHora(), faturamentoCorporativo, 0.01);
        assertEquals((2 * sala1.getValorHora()) + (2 * sala3.getValorHora()), faturamentoIndividual, 0.01);
    }

    @Test
    void testCalcularOcupacaoSalas() {
        // Adicionar salas e reservas ao sistema
        controller.listarSalas().add(sala1);
        controller.listarSalas().add(sala2);
        controller.listarSalas().add(sala3);
        controller.listarReservas().add(reserva1);
        controller.listarReservas().add(reserva2);
        controller.listarReservas().add(reserva3);

        LocalDate inicio = data1;
        LocalDate fim = data3;

        Map<Sala, Double> ocupacao = controller.calcularOcupacaoSalas(inicio, fim);
        
        // Verificar se a ocupação está sendo calculada corretamente
        assertTrue(ocupacao.get(sala1) > 0);
        assertTrue(ocupacao.get(sala2) > 0);
        assertTrue(ocupacao.get(sala3) > 0);
    }

    @Test
    void testBuscarClientesPorCPF() {
        // Adicionar clientes ao sistema
        controller.listarClientes().add(cliente1);
        controller.listarClientes().add(cliente2);

        List<Cliente> resultado = controller.buscarClientesPorCPF("123.456.789-00");
        
        // Verificar se a busca está funcionando corretamente
        assertEquals(1, resultado.size());
        assertEquals(cliente1, resultado.get(0));
    }

    @Test
    void testBuscarReservasPorCliente() {
        // Adicionar reservas ao sistema
        controller.listarReservas().add(reserva1);
        controller.listarReservas().add(reserva2);
        controller.listarReservas().add(reserva3);

        List<Reserva> resultado = controller.buscarReservasPorCliente("123.456.789-00");
        
        // Verificar se a busca está funcionando corretamente
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(reserva1));
        assertTrue(resultado.contains(reserva3));
    }

    @Test
    void testMediaHorasPorCliente() {
        // Adicionar reservas ao sistema
        controller.listarReservas().add(reserva1);
        controller.listarReservas().add(reserva2);
        controller.listarReservas().add(reserva3);

        double media = controller.mediaHorasPorCliente();
        
        // Verificar se a média está sendo calculada corretamente
        // Cada reserva tem 2 horas, então a média deve ser 2
        assertEquals(2.0, media, 0.01);
    }
} 