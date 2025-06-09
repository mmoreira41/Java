package com.mycompany.labsprint1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mycompany.labsprint1.model.Cliente;
public class ClienteTest {
    
    @Test
    void testCriacaoCliente() {
        Cliente cliente = new Cliente("João Silva", "12345678901", true);
        assertNotNull(cliente);
        assertEquals("João Silva", cliente.getNome());
        assertEquals("12345678901", cliente.getCPF());
        assertTrue(cliente.verificarTipo("Corporativo"));
    }
    
    @Test
    void testCriacaoClienteDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(null, "12345678901", true);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Cliente("", "12345678901", true);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Cliente("João Silva", null, true);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Cliente("João Silva", "", true);
        });
    }
    @Test
    void testToString() {
        Cliente cliente = new Cliente("João Silva", "12345678901", true);
        assertEquals("João Silva", cliente.toString());
    }

    @Test
    void testGetTipo() {
        Cliente clienteCorporativo = new Cliente("João Silva", "12345678901", true);
        assertEquals("Corporativo", clienteCorporativo.getTipo());

        Cliente clienteIndividual = new Cliente("Maria Santos", "98765432100", false);
        assertEquals("Individual", clienteIndividual.getTipo());
    }

    @Test
    void testSetNomeTrimmed() {
        Cliente cliente = new Cliente("João Silva", "12345678901", true);
        cliente.setNome("  Maria Santos  ");
        assertEquals("Maria Santos", cliente.getNome().trim());
    }

    @Test
    void testSetCPFWithSpaces() {
        Cliente cliente = new Cliente("João Silva", "12345678901", true);
        assertThrows(IllegalArgumentException.class, () -> {
            cliente.setCPF(" 12345678901 ");
        });
        cliente.setCPF("98765432100");
        assertEquals("98765432100", cliente.getCPF());
    }
    
    @Test
    void testSetCPFInvalido() {
        Cliente cliente = new Cliente("João Silva", "12345678901", true);
        
        assertThrows(IllegalArgumentException.class, () -> {
            cliente.setCPF("123"); // CPF muito curto
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            cliente.setCPF("123456789012"); // CPF muito longo
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            cliente.setCPF("1234567890a"); // CPF com caracteres não numéricos
        });
    }
    
    @Test
    void testSetCPFNull() {
        Cliente cliente = new Cliente("João Silva", "12345678901", true);
        assertThrows(IllegalArgumentException.class, () -> {
            cliente.setCPF(null);
        });
    }
    
    @Test
    void testSetTipoCorporativo() {
        Cliente cliente = new Cliente("João Silva", "12345678901", true);
        cliente.setCorporativo(false);
        assertFalse(cliente.verificarTipo("Corporativo"));
        
        cliente.setCorporativo(true);
        assertTrue(cliente.verificarTipo("Corporativo"));
    }
    
    @Test
    void testEquals() {
        Cliente cliente1 = new Cliente("João Silva", "12345678901", true);
        Cliente cliente2 = new Cliente("João Silva", "12345678901", true);
        Cliente cliente3 = new Cliente("Maria Santos", "98765432100", false);
        
        assertEquals(cliente1, cliente2);
        assertNotEquals(cliente1, cliente3);
    }
    
    @Test
    void testHashCode() {
        Cliente cliente1 = new Cliente("João Silva", "12345678901", true);
        Cliente cliente2 = new Cliente("João Silva", "12345678901", true);
        Cliente cliente3 = new Cliente("Maria Santos", "98765432100", false);
        
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
        assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
    }
} 