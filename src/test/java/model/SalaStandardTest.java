package com.mycompany.labsprint1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class SalaStandardTest {
    
    private SalaStandard sala;
    private List<Recurso> recursos;
    
    @BeforeEach
    void setUp() {
        recursos = new ArrayList<>();
        recursos.add(new Recurso("Projetor"));
        sala = new SalaStandard();
        sala.setCodigo("SALA_001");
        sala.setLocal("Bloco A");
        sala.setCapacidade(30);
        sala.setRecursos(recursos);
    }
    
    @Test
    void testCriacaoSala() {
        assertNotNull(sala);
        assertEquals("SALA_001", sala.getCodigo());
        assertEquals("Bloco A", sala.getLocal());
        assertEquals(30, sala.getCapacidade());
        assertEquals(1, sala.getRecursos().size());
    }
    
    @Test
    void testCalcularCusto() {
        double custo = sala.calcularCusto(2.0); // 2 horas
        assertEquals(100.0, custo); // 50 * 2 = 100
    }
    
    @Test
    void testCalcularCancelamento() {
        double custoTotal = 100.0;
        double valorCancelamento = sala.calcularCancelamento(custoTotal);
        assertEquals(60.0, valorCancelamento); // 100 * 0.60 = 60
    }
    
    @Test
    void testGerarCodigo() {
        String codigo = Sala.gerarCodigo();
        assertNotNull(codigo);
        assertTrue(codigo.startsWith("SALA_"));
    }
} 