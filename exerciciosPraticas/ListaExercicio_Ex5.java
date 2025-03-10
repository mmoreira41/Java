/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exerciciosPraticas;

import java.util.Scanner;

/**
 *
 * @author Miguel Moreira
 */
public class ListaExercicio_Ex5 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        double somaSalarios = 0;
        int quantidadePessoas = 0;

        while (true) { // Loop infinito até o usuário decidir parar
            System.out.print("Digite o salario (ou -1 para sair): ");
            double salario = s.nextDouble();

            if (salario == -1) { 
                break;
            }

            System.out.print("Digite o nnmero de filhos: ");
            int filhos = s.nextInt(); 
            
            somaSalarios += salario;
            quantidadePessoas++;
        }

        if (quantidadePessoas > 0) {
            double mediaSalario = somaSalarios / quantidadePessoas;
            System.out.println("Media salarial da populacao("+ quantidadePessoas +"): " + mediaSalario);
        } else {
            System.out.println("Erro!");
        }

        s.close();

    }
}
