package com.mycompany.labsprint1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecursoTest {
    
    @Test
    void testCriacaoRecurso() {
        Recurso recurso = new Recurso("Projetor");
        assertNotNull(recurso);
        assertEquals("Projetor", recurso.getNome());
    }
    
    @Test
    void testSetNome() {
        Recurso recurso = new Recurso("Projetor");
        recurso.setNome("Computador");
        assertEquals("Computador", recurso.getNome());
    }
    
    @Test
    void testSetNomeNull() {
        Recurso recurso = new Recurso("Projetor");
        assertThrows(IllegalArgumentException.class, () -> {
            recurso.setNome(null);
        });
    }
    
    @Test
    void testSetNomeVazio() {
        Recurso recurso = new Recurso("Projetor");
        assertThrows(IllegalArgumentException.class, () -> {
            recurso.setNome("");
        });
    }
} 
@Test
void testConstrutorNomeNull() {
    assertThrows(IllegalArgumentException.class, () -> {
        new Recurso(null);
    });
}

@Test
void testConstrutorNomeVazio() {
    assertThrows(IllegalArgumentException.class, () -> {
        new Recurso("");
    });
}

@Test
void testGetNome() {
    Recurso recurso = new Recurso("Impressora");
    assertEquals("Impressora", recurso.getNome());
}