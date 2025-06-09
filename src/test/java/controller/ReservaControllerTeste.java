package com.mycompany.labsprint1;

import com.mycompany.labsprint1.controller.ReservaController;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.Cliente;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaControllerTeste {

    private ReservaController controller;
    private Sala sala;
    private Cliente cliente;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @BeforeEach
    public void setUp() {
        controller = new ReservaController();
        sala = new SalaStandard();
        sala.setCodigo("SALA_001");
        cliente = new Cliente("João Silva", "12345678901", true);
        dataInicio = LocalDateTime.now();
        dataFim = dataInicio.plusHours(2);
    }

    @Test
    public void testFazerReserva() {
        boolean resultado = controller.fazerReserva(dataInicio, dataFim, sala, cliente);
        assertTrue(resultado, "A reserva deveria ser feita com sucesso.");
        
        List<Reserva> reservas = controller.listarReservas();
        assertEquals(1, reservas.size(), "Deveria haver 1 reserva na lista.");
    }

    @Test
    public void testFazerReservaDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.fazerReserva(null, dataFim, sala, cliente);
        }, "Não deveria aceitar data de início nula");

        assertThrows(IllegalArgumentException.class, () -> {
            controller.fazerReserva(dataInicio, null, sala, cliente);
        }, "Não deveria aceitar data de fim nula");

        assertThrows(IllegalArgumentException.class, () -> {
            controller.fazerReserva(dataInicio, dataFim, null, cliente);
        }, "Não deveria aceitar sala nula");

        assertThrows(IllegalArgumentException.class, () -> {
            controller.fazerReserva(dataInicio, dataFim, sala, null);
        }, "Não deveria aceitar cliente nulo");
    }

    @Test
    public void testFazerReservaConflitoHorario() {
        controller.fazerReserva(dataInicio, dataFim, sala, cliente);
        
        assertThrows(IllegalArgumentException.class, () -> {
            controller.fazerReserva(dataInicio, dataFim, sala, cliente);
        }, "Não deveria permitir reserva com conflito de horário");
    }

    @Test
    public void testListarReservas() {
        controller.fazerReserva(dataInicio, dataFim, sala, cliente);
        controller.fazerReserva(dataInicio.plusDays(1), dataFim.plusDays(1), sala, cliente);
        
        List<Reserva> reservas = controller.listarReservas();
        assertEquals(2, reservas.size(), "Deveria haver 2 reservas na lista.");
    }

    @Test
    public void testCadastrarReserva() {
        LocalDate data = LocalDate.now();
        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFim = LocalTime.of(12, 0);
        
        controller.cadastrarReserva(cliente, sala, data, horaInicio, horaFim);
        
        List<Reserva> reservas = controller.listarReservas();
        assertEquals(1, reservas.size(), "Deveria haver 1 reserva na lista.");
    }

    @Test
    public void testCadastrarReservaDadosInvalidos() {
        LocalDate data = LocalDate.now();
        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFim = LocalTime.of(12, 0);
        
        assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastrarReserva(null, sala, data, horaInicio, horaFim);
        }, "Não deveria aceitar cliente nulo");

        assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastrarReserva(cliente, null, data, horaInicio, horaFim);
        }, "Não deveria aceitar sala nula");

        assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastrarReserva(cliente, sala, null, horaInicio, horaFim);
        }, "Não deveria aceitar data nula");

        assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastrarReserva(cliente, sala, data, null, horaFim);
        }, "Não deveria aceitar hora de início nula");

        assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastrarReserva(cliente, sala, data, horaInicio, null);
        }, "Não deveria aceitar hora de fim nula");
    }

    @Test
    public void testCadastrarReservaHorarioInvalido() {
        LocalDate data = LocalDate.now();
        LocalTime horaInicio = LocalTime.of(12, 0);
        LocalTime horaFim = LocalTime.of(10, 0);
        
        assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastrarReserva(cliente, sala, data, horaInicio, horaFim);
        }, "Não deveria aceitar hora de início posterior à hora de fim");
    }
} 