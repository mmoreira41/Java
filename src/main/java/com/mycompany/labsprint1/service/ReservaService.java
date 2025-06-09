package com.mycompany.labsprint1.service;

import com.mycompany.labsprint1.dao.ReservaDAO;
import com.mycompany.labsprint1.model.Reserva;
import com.mycompany.labsprint1.model.Cliente;
import com.mycompany.labsprint1.model.Sala;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservaService {
    private final ReservaDAO reservaDAO;

    public ReservaService() {
        this.reservaDAO = ReservaDAO.getInstance();
    }

    public Reserva criarReserva(LocalDate data, LocalTime horaInicio, LocalTime horaFim, Sala sala, Cliente cliente,
            double custo) {
        if (data == null || horaInicio == null || horaFim == null || sala == null || cliente == null || custo < 0) {
            throw new IllegalArgumentException("Todos os campos são obrigatórios");
        }

        if (horaInicio.isAfter(horaFim)) {
            throw new IllegalArgumentException("Hora de início deve ser anterior à hora de fim");
        }

        return new Reserva(data, horaInicio, horaFim, sala, cliente);

    }

    public boolean cancelarReserva(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser vazio");
        }

        Reserva reserva = buscarPorId(id);
        if (reserva != null) {
            reserva.excluirReserva();
            atualizar(reserva);
            return true;
        }
        return false;
    }

    public void salvar(Reserva reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não pode ser nula");
        }

        if (reserva.getCliente() == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }

        if (reserva.getSala() == null) {
            throw new IllegalArgumentException("Sala não pode ser nula");
        }

        if (reserva.getData() == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }

        if (reserva.getHoraInicio() == null) {
            throw new IllegalArgumentException("Hora de início não pode ser nula");
        }

        if (reserva.getHoraFim() == null) {
            throw new IllegalArgumentException("Hora de fim não pode ser nula");
        }

        if (reserva.getHoraInicio().isAfter(reserva.getHoraFim())) {
            throw new IllegalArgumentException("Hora de início deve ser anterior à hora de fim");
        }

        // Verifica conflito de horários
        if (existeConflitoHorario(reserva)) {
            throw new IllegalArgumentException("Já existe uma reserva para esta sala neste horário");
        }

        reservaDAO.salvar(reserva);
    }

    public List<Reserva> listarTodas() {
        return reservaDAO.listarTodas();
    }

    public Reserva buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser vazio");
        }
        return reservaDAO.buscarPorId(id);
    }

    public boolean excluir(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser vazio");
        }
        return reservaDAO.excluirReserva(id);
    }

    public void atualizar(Reserva reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não pode ser nula");
        }

        if (reserva.getCliente() == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }

        if (reserva.getSala() == null) {
            throw new IllegalArgumentException("Sala não pode ser nula");
        }

        if (reserva.getData() == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }

        if (reserva.getHoraInicio() == null) {
            throw new IllegalArgumentException("Hora de início não pode ser nula");
        }

        if (reserva.getHoraFim() == null) {
            throw new IllegalArgumentException("Hora de fim não pode ser nula");
        }

        if (reserva.getHoraInicio().isAfter(reserva.getHoraFim())) {
            throw new IllegalArgumentException("Hora de início deve ser anterior à hora de fim");
        }

        // Verifica conflito de horários (excluindo a própria reserva)
        if (existeConflitoHorario(reserva)) {
            throw new IllegalArgumentException("Já existe uma reserva para esta sala neste horário");
        }

        reservaDAO.atualizar(reserva);
    }

    public List<Reserva> buscarPorCliente(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }

        return reservaDAO.buscarPorCliente(cpf);
    }

    public List<Reserva> buscarPorSala(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código da sala não pode ser vazio");
        }

        return reservaDAO.listarTodas().stream()
                .filter(r -> r.getSala().getCodigo().equals(codigo))
                .toList();
    }

    public double calcularTotalArrecadado(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data inicial deve ser anterior à data final");
        }

        return reservaDAO.listarTodas().stream()
                .filter(r -> !r.getData().isBefore(dataInicio) && !r.getData().isAfter(dataFim))
                .mapToDouble(Reserva::getValorTotal)
                .sum();
    }

    public Map<String, Double> calcularTotalArrecadadoPorTipoSala(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data inicial deve ser anterior à data final");
        }

        return reservaDAO.listarTodas().stream()
                .filter(r -> !r.getData().isBefore(dataInicio) && !r.getData().isAfter(dataFim))
                .collect(Collectors.groupingBy(
                        r -> r.getSala().getClass().getSimpleName(),
                        Collectors.summingDouble(Reserva::getValorTotal)));
    }

    public double calcularFaturamentoPorTipoCliente(LocalDate dataInicio, LocalDate dataFim, boolean corporativo) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data inicial deve ser anterior à data final");
        }

        return reservaDAO.listarTodas().stream()
                .filter(r -> !r.getData().isBefore(dataInicio) && !r.getData().isAfter(dataFim))
                .filter(r -> r.getCliente().isCorporativo() == corporativo)
                .mapToDouble(Reserva::getValorTotal)
                .sum();
    }

    private boolean existeConflitoHorario(Reserva novaReserva) {
        return reservaDAO.listarTodas().stream()
                .filter(r -> r.getSala().getCodigo().equals(novaReserva.getSala().getCodigo()))
                .filter(r -> r.getData().equals(novaReserva.getData()))
                .filter(r -> !r.getId().equals(novaReserva.getId())) // Exclui a própria reserva no caso de atualização
                .anyMatch(r -> {
                    LocalTime inicioNova = novaReserva.getHoraInicio();
                    LocalTime fimNova = novaReserva.getHoraFim();
                    LocalTime inicioExistente = r.getHoraInicio();
                    LocalTime fimExistente = r.getHoraFim();

                    return (inicioNova.isBefore(fimExistente) && fimNova.isAfter(inicioExistente));
                });
    }

    // public void setReservas(List<Reserva> reservas) {
    // if (reservas == null) {
    // throw new IllegalArgumentException("Lista de reservas não pode ser nula");
    // }
    // reservaDAO.setReservas(reservas);
    // }
}
