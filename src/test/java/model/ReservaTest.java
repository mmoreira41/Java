package com.mycompany.labsprint1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaTest {
    
    private Sala sala;
    private Cliente cliente;
    private Reserva reserva;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    
    @BeforeEach
    void setUp() {
        List<Recurso> recursos = new ArrayList<>();
        recursos.add(new Recurso("Projetor"));
        sala = new SalaStandard();
        sala.setCodigo("SALA_001");
        sala.setLocal("Bloco A");
        sala.setCapacidade(30);
        sala.setRecursos(recursos);
        
        cliente = new Cliente("João Silva", "12345678901", true);
        
        dataInicio = LocalDateTime.now();
        dataFim = dataInicio.plusHours(2);
        
        reserva = new Reserva("Standard", dataInicio, dataFim, 100.0, sala, cliente);
    }
    
    @Test
    void testCriacaoReserva() {
        assertNotNull(reserva);
        assertEquals(sala, reserva.getSala());
        assertEquals(cliente, reserva.getCliente());
        assertEquals(100.0, reserva.getCusto());
        assertEquals("Standard", reserva.getTipo());
        assertEquals(dataInicio, reserva.getDataInicio());
        assertEquals(dataFim, reserva.getDataFim());
    }
    
    @Test
    void testCriacaoReservaDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reserva(null, dataInicio, dataFim, 100.0, sala, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Reserva("Standard", null, dataFim, 100.0, sala, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Reserva("Standard", dataInicio, null, 100.0, sala, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Reserva("Standard", dataInicio, dataFim, -100.0, sala, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Reserva("Standard", dataInicio, dataFim, 100.0, null, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Reserva("Standard", dataInicio, dataFim, 100.0, sala, null);
        });
    }
    
    @Test
    void testVerificarDisponibilidade() {
        LocalDate data = LocalDate.now();
        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFim = LocalTime.of(12, 0);
        
        assertDoesNotThrow(() -> {
            reserva.verificarDisponibilidade(sala, data, horaInicio, horaFim);
        });
    }
    
    @Test
    void testVerificarDisponibilidadeDadosInvalidos() {
        LocalDate data = LocalDate.now();
        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFim = LocalTime.of(12, 0);
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.verificarDisponibilidade(null, data, horaInicio, horaFim);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.verificarDisponibilidade(sala, null, horaInicio, horaFim);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.verificarDisponibilidade(sala, data, null, horaFim);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.verificarDisponibilidade(sala, data, horaInicio, null);
        });
    }
    
    @Test
    void testCalcularCusto() {
        assertDoesNotThrow(() -> {
            reserva.calcularCusto(50.0, "Corporativo", cliente);
        });
    }
    
    @Test
    void testCalcularCustoDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.calcularCusto(-50.0, "Corporativo", cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.calcularCusto(50.0, null, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.calcularCusto(50.0, "Corporativo", null);
        });
    }
    
    @Test
    void testReservarSala() {
        LocalDate data = LocalDate.now();
        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFim = LocalTime.of(12, 0);
        
        assertDoesNotThrow(() -> {
            reserva.reservarSala(sala, data, horaInicio, horaFim, cliente);
        });
    }
    
    @Test
    void testReservarSalaDadosInvalidos() {
        LocalDate data = LocalDate.now();
        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFim = LocalTime.of(12, 0);
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.reservarSala(null, data, horaInicio, horaFim, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.reservarSala(sala, null, horaInicio, horaFim, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.reservarSala(sala, data, null, horaFim, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.reservarSala(sala, data, horaInicio, null, cliente);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            reserva.reservarSala(sala, data, horaInicio, horaFim, null);
        });
    }
} 