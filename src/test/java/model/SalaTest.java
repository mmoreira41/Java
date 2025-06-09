package com.mycompany.labsprint1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class SalaTest {
    
    private Sala sala;
    private List<Recurso> recursos;
    
    @BeforeEach
    void setUp() {
        recursos = new ArrayList<>();
        recursos.add(new Recurso("Projetor"));
        sala = new SalaStandard(); // Usando SalaStandard para testar métodos da classe base
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
    void testSetCodigo() {
        sala.setCodigo("SALA_002");
        assertEquals("SALA_002", sala.getCodigo());
    }
    
    @Test
    void testSetCodigoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            sala.setCodigo(null);
        });
    }
    
    @Test
    void testSetLocal() {
        sala.setLocal("Bloco B");
        assertEquals("Bloco B", sala.getLocal());
    }
    
    @Test
    void testSetLocalNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            sala.setLocal(null);
        });
    }
    
    @Test
    void testSetCapacidade() {
        sala.setCapacidade(40);
        assertEquals(40, sala.getCapacidade());
    }
    
    @Test
    void testSetCapacidadeInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            sala.setCapacidade(0);
        });
    }
    
    @Test
    void testSetRecursos() {
        List<Recurso> novosRecursos = new ArrayList<>();
        novosRecursos.add(new Recurso("Computador"));
        sala.setRecursos(novosRecursos);
        assertEquals(1, sala.getRecursos().size());
        assertEquals("Computador", sala.getRecursos().get(0).getNome());
    }
    
    @Test
    void testSetRecursosNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            sala.setRecursos(null);
        });
    }
} 