package com.mycompany.labsprint1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.SalaPremium;
import com.mycompany.labsprint1.model.SalaStandard;

public class LabSprint1Test {
    
    private LabSprint1 sistema;
    private Cliente cliente;
    private Sala sala;
    
    @BeforeEach
    void setUp() {
        sistema = new LabSprint1();
        cliente = new Cliente("João Silva", "12345678901", true);
        sala = new SalaStandard("SALA_001", "Bloco A", 30, null);
        sala.setCodigo("SALA_001");
        sala.setLocal("Bloco A");
        sala.setCapacidade(30);
    }
    
    @Test
    void testCriarCliente() {
        Cliente novoCliente = sistema.criarCliente("Maria Santos", "98765432100", false);
        assertNotNull(novoCliente);
        assertEquals("Maria Santos", novoCliente.getNome());
        assertEquals("98765432100", novoCliente.getCPF());
        assertFalse(novoCliente.verificarTipo("Corporativo"));
    }
    
    @Test
    void testCriarSalaStandard() {
        Sala novaSala = sistema.criarSalaStandard("SALA_002", "Bloco B", 40, null);
        assertNotNull(novaSala);
        assertEquals("SALA_002", novaSala.getCodigo());
        assertEquals("Bloco B", novaSala.getLocal());
        assertEquals(40, novaSala.getCapacidade());
    }
    
    @Test
    void testCriarSalaPremium() {
        Sala novaSala = sistema.criarSalaPremium("SALA_003", "Bloco C", 20, null);
        assertNotNull(novaSala);
        assertEquals("SALA_003", novaSala.getCodigo());
        assertEquals("Bloco C", novaSala.getLocal());
        assertEquals(20, novaSala.getCapacidade());
    }
    
    @Test
    void testCriarSalaVip() {
        Sala novaSala = sistema.criarSalaVip("SALA_004", "Bloco D", 10, null);
        assertNotNull(novaSala);
        assertEquals("SALA_004", novaSala.getCodigo());
        assertEquals("Bloco D", novaSala.getLocal());
        assertEquals(10, novaSala.getCapacidade());
    }
    
    @Test
    void testCriarReserva() {
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusHours(2);
        
        Reserva reserva = sistema.criarReserva(inicio, fim, sala, cliente, LocalDate.now());
        assertNotNull(reserva);
        assertEquals(sala, reserva.getSala());
        assertEquals(cliente, reserva.getCliente());
        assertEquals(100.0, reserva.getCusto());
    }
    
    @Test
    void testListarSalas() {
        sistema.criarSalaStandard("SALA_001", "Bloco A", 30, null);
        sistema.criarSalaPremium("SALA_002", "Bloco B", 20, null);
        
        List<Sala> salas = sistema.listarSalas();
        assertNotNull(salas);
        assertTrue(salas.size() >= 2);
    }
    
    @Test
    void testListarClientes() {
        sistema.criarCliente("Cliente 1", "11111111111", true);
        sistema.criarCliente("Cliente 2", "22222222222", false);
        
        List<Cliente> clientes = sistema.listarClientes();
        assertNotNull(clientes);
        assertTrue(clientes.size() >= 2);
    }
    
    @Test
    void testListarReservas() {
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusHours(2);
        
        sistema.criarReserva(inicio, fim, sala, cliente, LocalDate.now());
        
        List<Reserva> reservas = LabSprint1.listarReservas();
        assertNotNull(reservas);
        assertTrue(reservas.size() >= 1);
    }
} 