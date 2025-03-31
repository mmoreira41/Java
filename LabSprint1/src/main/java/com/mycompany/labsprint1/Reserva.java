/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.labsprint1;

import java.time.LocalDateTime;

/**
 *
 * @author Miguel Moreira
 */
public class Reserva {
    private String tipo;
    private LocalDateTime dataInicio;
    private LocalDateTime horaFim;
    private LocalDateTime Data;
    private double custo;
    private Sala sala;
    private Cliente cliente;
    
    
    public boolean verificarDisponibilidade(){
        return false;
    }
    public double calcularValor(){
        return custo;
    }
}
