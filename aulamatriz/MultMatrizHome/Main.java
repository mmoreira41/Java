package com.mycompany.MultMatrizHome;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Digite o número de linhas das matrizes: ");
        int linhas = s.nextInt();

        System.out.print("Digite o número de colunas das matrizes: ");
        int colunas = s.nextInt();

        Matriz matriz1 = new Matriz(linhas, colunas);
        Matriz matriz2 = new Matriz(linhas, colunas);

        System.out.println("Digite os números da Primeira Matriz:");
        matriz1.lerDadosMatriz();

        System.out.println("Digite os números da Segunda Matriz:");
        matriz2.lerDadosMatriz();

        matriz1.imprimirMatriz("1");
        matriz2.imprimirMatriz("2");

        System.out.println("O resultado da multiplicação das matrizes é:");
        int[][] resultado = MultiplicarMatriz.MultiplicarMatriz2x2(matriz1.getMatriz(), matriz2.getMatriz());

        for (int i = 0; i < resultado.length; i++) {
            for (int j = 0; j < resultado[0].length; j++) {
                System.out.print(resultado[i][j] + " ");
            }
            System.out.println();
        }
    }
}
