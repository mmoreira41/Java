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
public class ListaExercicio_Ex7 {
    public static void main(String[] args) {
         Scanner s = new Scanner(System.in);

        System.out.print("Digite o tamanho dos vetores: ");
        int N = s.nextInt();

        int[] vetorA = new int[N];
        int[] vetorB = new int[N];

        System.out.println("Digite os valores do vetor A:");
        for (int i = 0; i < N; i++) {
            vetorA[i] = s.nextInt();
        }

        // Lendo os valores do vetor B
        System.out.println("Digite os valores do vetor B:");
        for (int i = 0; i < N; i++) {
            vetorB[i] = s.nextInt();
        }

        int[] vetorResultado = somarVetoresInvertido(vetorA, vetorB);

        System.out.println("Vetor Resultado:");
        for (int num : vetorResultado) {
            System.out.print(num + " ");
        }

        s.close();
    }

    public static int[] somarVetoresInvertido(int[] vetorA, int[] vetorB) {
        int N = vetorA.length;
        int[] resultado = new int[N];

        for (int i = 0; i < N; i++) {
            resultado[N - 1 - i] = vetorA[i] + vetorB[i]; 
        }

        return resultado;
    }
}
    
