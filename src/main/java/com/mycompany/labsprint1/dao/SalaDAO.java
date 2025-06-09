package com.mycompany.labsprint1.dao;

import com.mycompany.labsprint1.model.Sala;
import com.mycompany.labsprint1.model.Recurso;
import com.mycompany.labsprint1.model.SalaStandard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {
    private static SalaDAO instance;

    private SalaDAO() {
    }

    public static synchronized SalaDAO getInstance() {
        if (instance == null) {
            instance = new SalaDAO();
        }
        return instance;
    }

    public void salvar(Sala sala) {
        String sql = "INSERT INTO sala (codigoSala, local, capacidade, idTipoSala) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getCodigo());
            stmt.setString(2, sala.getLocal());
            stmt.setInt(3, sala.getCapacidade());
            stmt.setInt(4, sala.getIdTipoSala()); // Ajuste conforme sua classe Sala

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sala> listarTodas() {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT codigoSala, local, capacidade, idTipoSala FROM sala";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sala sala = new SalaStandard();
                sala.setCodigo(rs.getString("codigoSala"));
                sala.setLocal(rs.getString("local"));
                sala.setCapacidade(rs.getInt("capacidade"));
                sala.setIdTipoSala(rs.getInt("idTipoSala")); // Certifique-se de que esse setter exista
                salas.add(sala);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salas;
    }

    public Sala buscarPorCodigo(String codigo) {
        String sql = "SELECT codigoSala, local, capacidade, idTipoSala FROM sala WHERE codigoSala = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Sala sala = new SalaStandard();
                sala.setCodigo(rs.getString("codigoSala"));
                sala.setLocal(rs.getString("local"));
                sala.setCapacidade(rs.getInt("capacidade"));
                sala.setIdTipoSala(rs.getInt("idTipoSala"));
                return sala;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void atualizar(Sala sala) {
        String sql = "UPDATE sala SET local = ?, capacidade = ?, idTipoSala = ? WHERE codigoSala = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getLocal());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setInt(3, sala.getIdTipoSala());
            stmt.setString(4, sala.getCodigo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean excluir(String codigo) {
        String sql = "DELETE FROM sala WHERE codigoSala = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
