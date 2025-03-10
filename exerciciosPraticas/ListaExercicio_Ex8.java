/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exerciciosPraticas;

/**
 *
 * @author Miguel Moreira
 */
public class ListaExercicio_Ex8 {
    public static void main(String[] args) {
         int[][] matriz = {
            {5, 10, 7, 8},
            {13, 4, 1, 3},
            {9, 2, 5, 6},
            {10, 7, 4, 9}
        };

        exibirMatrizTransposta(matriz);

        int somaQuadrados = somaQuadradosDiagonalSecundaria(matriz);
        System.out.println("\nSoma dos quadrados da diagonal secundária: " + somaQuadrados);

        int C = 2; 
        multiplicarMatrizPorConstante(matriz, C);

        trocarLinhaComColuna(matriz, 2);
        System.out.println("\nMatriz após troca da 3ª linha com a 3ª coluna:");
        imprimirMatriz(matriz);
    }

    public static void exibirMatrizTransposta(int[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;

        System.out.println("Matriz Transposta:");
        for (int j = 0; j < colunas; j++) {
            for (int i = 0; i < linhas; i++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int somaQuadradosDiagonalSecundaria(int[][] matriz) {
        int N = matriz.length;
        int soma = 0;

        for (int i = 0; i < N; i++) {
            soma += Math.pow(matriz[i][N - 1 - i], 2);
        }

        return soma;
    }

    public static void multiplicarMatrizPorConstante(int[][] matriz, int C) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;

        System.out.println("\nMatriz multiplicada por " + C + ":");
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print((matriz[i][j] * C) + " ");
            }
            System.out.println();
        }
    }

    public static void trocarLinhaComColuna(int[][] matriz, int index) {
        int N = matriz.length;

        for (int i = 0; i < N; i++) {
            int temp = matriz[index][i]; 
            matriz[index][i] = matriz[i][index]; 
            matriz[i][index] = temp; 
        }
    }

    public static void imprimirMatriz(int[][] matriz) {
        for (int[] linha : matriz) {
            for (int elemento : linha) {
                System.out.print(elemento + " ");
            }
            System.out.println();
        }
    }
}
