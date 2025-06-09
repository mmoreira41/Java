package com.mycompany.labsprint1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class SalaPremiumTest {
    
    private SalaPremium sala;
    private List<Recurso> recursos;
    
    @BeforeEach
    void setUp() {
        recursos = new ArrayList<>();
        recursos.add(new Recurso("Projetor"));
        recursos.add(new Recurso("Ar Condicionado"));
        sala = new SalaPremium();
        sala.setCodigo("SALA_PREMIUM_001");
        sala.setLocal("Bloco B");
        sala.setCapacidade(40);
        sala.setRecursos(recursos);
    }
    
    @Test
    void testCriacaoSalaPremium() {
        assertNotNull(sala);
        assertEquals("SALA_PREMIUM_001", sala.getCodigo());
        assertEquals("Bloco B", sala.getLocal());
        assertEquals(40, sala.getCapacidade());
        assertEquals(2, sala.getRecursos().size());
    }
    
    @Test
    void testCalcularCusto() {
        double custo = sala.calcularCusto(2.0); // 2 horas
        assertEquals(115.0, custo); // 50 * 2 * 1.15 = 115
    }
    
    @Test
    void testCalcularCancelamento() {
        double custoTotal = 115.0;
        double valorCancelamento = sala.calcularCancelamento(custoTotal);
        assertEquals(46.0, valorCancelamento); // 115 * 0.40 = 46
    }
} 