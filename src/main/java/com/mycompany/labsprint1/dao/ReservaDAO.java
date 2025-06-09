package com.mycompany.labsprint1.dao;

import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.SalaStandard;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private static ReservaDAO instance;

    private ReservaDAO() {
    }

    public static synchronized ReservaDAO getInstance() {
        if (instance == null) {
            instance = new ReservaDAO();
        }
        return instance;
    }

    public void salvar(Reserva reserva) {
        String sql = "INSERT INTO Reserva (data, horaInicio, horaFim, custo, codigoSala, cpfCliente) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(reserva.getData()));
            stmt.setTime(2, Time.valueOf(reserva.getHoraInicio()));
            stmt.setTime(3, Time.valueOf(reserva.getHoraFim()));
            stmt.setDouble(4, reserva.getCusto());
            stmt.setString(5, reserva.getSala().getCodigo());
            stmt.setString(6, reserva.getCliente().getCPF());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reserva> listarTodas() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT id, data, horaInicio, horaFim, custo, codigoSala, cpfCliente FROM Reserva";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Sala sala = new SalaStandard();
                sala.setCodigo(rs.getString("codigoSala"));

                Cliente cliente = ClienteDAO.getInstance().buscarPorCpf(rs.getString("cpfCliente"));

                Reserva reserva = new Reserva(
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("horaInicio").toLocalTime(),
                        rs.getTime("horaFim").toLocalTime(),
                        sala,
                        cliente);

                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    public boolean excluirReserva(String id) {
        if (id == null || id.isBlank())
            return false;

        int idNumerico = extrairIdNumerico(id);
        String sql = "DELETE FROM Reserva WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idNumerico);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Reserva buscarPorId(String idComPrefixo) {
        int id = extrairIdNumerico(idComPrefixo);
        String sql = "SELECT id, data, horaInicio, horaFim, custo, codigoSala, cpfCliente FROM Reserva WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Sala sala = new SalaStandard();
                sala.setCodigo(rs.getString("codigoSala"));

                Cliente cliente = ClienteDAO.getInstance().buscarPorCpf(rs.getString("cpfCliente"));

                Reserva reserva = new Reserva(
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("horaInicio").toLocalTime(),
                        rs.getTime("horaFim").toLocalTime(),
                        sala,
                        cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Reserva> buscarPorCliente(String cpf) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE cpfCliente = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sala sala = new SalaStandard();
                sala.setCodigo(rs.getString("codigoSala"));

                Cliente cliente = new Cliente(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getBoolean("tipocliente"));

                cliente.setCPF(rs.getString("cpfCliente"));

                Reserva reserva = new Reserva(
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("horaInicio").toLocalTime(),
                        rs.getTime("horaFim").toLocalTime(),
                        sala,
                        cliente);

                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    public void atualizar(Reserva reserva) {
        int idNumerico = extrairIdNumerico(reserva.getId());
        String sql = "UPDATE Reserva SET data = ?, horaInicio = ?, horaFim = ?, custo = ?, codigoSala = ?, cpfCliente = ? "
                +
                "WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(reserva.getData()));
            stmt.setTime(2, Time.valueOf(reserva.getHoraInicio()));
            stmt.setTime(3, Time.valueOf(reserva.getHoraFim()));
            stmt.setDouble(4, reserva.getCusto());
            stmt.setString(5, reserva.getSala().getCodigo());
            stmt.setString(6, reserva.getCliente().getCPF());
            stmt.setInt(7, idNumerico);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int extrairIdNumerico(String idComPrefixo) {
        try {
            return Integer.parseInt(idComPrefixo.replace("RES_", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido: " + idComPrefixo);
        }
    }
}
