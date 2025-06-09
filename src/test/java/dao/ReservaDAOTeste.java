package com.mycompany.labsprint1;

import com.mycompany.labsprint1.dao.ReservaDAO;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.Cliente;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.List;

public class ReservaDAOTeste {

    private ReservaDAO reservaDAO;
    private Sala sala;
    private Cliente cliente;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @BeforeEach
    public void setUp() {
        reservaDAO = new ReservaDAO();
        sala = new SalaStandard();
        sala.setCodigo("SALA_001");
        cliente = new Cliente("João Silva", "12345678901", true);
        dataInicio = LocalDateTime.now();
        dataFim = dataInicio.plusHours(2);
    }

    @Test
    public void testSalvar() {
        Reserva reserva = new Reserva(dataInicio, dataFim, sala, cliente, dataInicio.toLocalDate());
        reservaDAO.salvar(reserva);
        List<Reserva> reservas = reservaDAO.listarTodas();
        assertEquals(1, reservas.size(), "Deveria haver 1 reserva na lista.");
    }

    @Test
    public void testListarTodas() {
        Reserva reserva1 = new Reserva(dataInicio, dataFim, sala, cliente, dataInicio.toLocalDate());
        Reserva reserva2 = new Reserva(dataInicio.plusDays(1), dataFim.plusDays(1), sala, cliente, dataInicio.plusDays(1).toLocalDate());
        
        reservaDAO.salvar(reserva1);
        reservaDAO.salvar(reserva2);

        List<Reserva> reservas = reservaDAO.listarTodas();
        assertEquals(2, reservas.size(), "Deveria haver 2 reservas na lista.");
    }

    @Test
    public void testBuscarPorId() {
        Reserva reserva = new Reserva(dataInicio, dataFim, sala, cliente, dataInicio.toLocalDate());
        reservaDAO.salvar(reserva);

        Reserva encontrada = reservaDAO.buscarPorId(reserva.getId());
        assertNotNull(encontrada, "A reserva deveria ser encontrada.");
        assertEquals(reserva.getId(), encontrada.getId(), "O ID da reserva deveria corresponder.");
    }

    @Test
    public void testExcluirReserva() {
        Reserva reserva = new Reserva(dataInicio, dataFim, sala, cliente, dataInicio.toLocalDate());
        reservaDAO.salvar(reserva);

        boolean resultado = reservaDAO.excluirReserva(reserva.getId());
        assertTrue(resultado, "A reserva deveria ser excluída com sucesso.");

        Reserva excluida = reservaDAO.buscarPorId(reserva.getId());
        assertNull(excluida, "A reserva não deveria mais existir.");
    }

    @Test
    public void testExcluirReservaInexistente() {
        boolean resultado = reservaDAO.excluirReserva("ID_INEXISTENTE");
        assertFalse(resultado, "Não deveria ser possível excluir uma reserva inexistente.");
    }

    @Test
    public void testBuscarPorIdInexistente() {
        Reserva encontrada = reservaDAO.buscarPorId("ID_INEXISTENTE");
        assertNull(encontrada, "Não deveria encontrar uma reserva com ID inexistente.");
    }
} 