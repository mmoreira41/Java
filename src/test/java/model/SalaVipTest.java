package com.mycompany.labsprint1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class SalaVipTest {
    
    private SalaVip sala;
    private List<Recurso> recursos;
    
    @BeforeEach
    void setUp() {
        recursos = new ArrayList<>();
        recursos.add(new Recurso("Projetor"));
        recursos.add(new Recurso("Ar Condicionado"));
        recursos.add(new Recurso("Coffee Break"));
        sala = new SalaVip();
        sala.setCodigo("SALA_VIP_001");
        sala.setLocal("Bloco C");
        sala.setCapacidade(20);
        sala.setRecursos(recursos);
    }
    
    @Test
    void testCriacaoSalaVip() {
        assertNotNull(sala);
        assertEquals("SALA_VIP_001", sala.getCodigo());
        assertEquals("Bloco C", sala.getLocal());
        assertEquals(20, sala.getCapacidade());
        assertEquals(3, sala.getRecursos().size());
    }
    
    @Test
    void testCalcularCusto() {
        double custo = sala.calcularCusto(2.0); // 2 horas
        assertEquals(130.0, custo); // 50 * 2 * 1.30 = 130
    }
    
    @Test
    void testCalcularCancelamento() {
        double custoTotal = 130.0;
        double valorCancelamento = sala.calcularCancelamento(custoTotal);
        assertEquals(39.0, valorCancelamento); // 130 * 0.30 = 39
    }
} 