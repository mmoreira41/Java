package com.mycompany.labsprint1;
import com.mycompany.labsprint1.dao.ClienteDAO;
import com.mycompany.labsprint1.model.Cliente;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.List;

public class ClienteDAOteste {

    private ClienteDAO clienteDAO;

    @BeforeEach
    public void setUp() {
        clienteDAO = new ClienteDAO();
    }

    @Test
    public void testSalvar() {
        Cliente cliente = new Cliente("12345678900", "Marcos", );
        boolean resultado = clienteDAO.salvar(cliente);
        assertTrue(resultado, "O cliente deveria ser salvo com sucesso.");
    }

    @Test
    public void testListarTodos() {
        clienteDAO.salvar(new Cliente("12345678900", "Marcos", "marcos@example.com"));
        clienteDAO.salvar(new Cliente("98765432100", "Ana", "ana@example.com"));

        List<Cliente> clientes = clienteDAO.listarTodos();
        assertEquals(2, clientes.size(), "Deveria haver 2 clientes na lista.");
    }

    @Test
    public void testBuscarPorCPF() {
        Cliente cliente = new Cliente("12345678900", "Marcos", "marcos@example.com");
        clienteDAO.salvar(cliente);

        Cliente encontrado = clienteDAO.buscarPorCPF("12345678900");
        assertNotNull(encontrado, "O cliente deveria ser encontrado.");
        assertEquals("Marcos", encontrado.getNome(), "O nome do cliente deveria ser 'Marcos'.");
    }

    @Test
    public void testExcluirCliente() {
        Cliente cliente = new Cliente("12345678900", "Marcos", "marcos@example.com");
        clienteDAO.salvar(cliente);

        boolean resultado = clienteDAO.excluirCliente("12345678900");
        assertTrue(resultado, "O cliente deveria ser excluído com sucesso.");

        Cliente excluido = clienteDAO.buscarPorCPF("12345678900");
        assertNull(excluido, "O cliente não deveria mais existir.");
    }
}