package com.mycompany.MultMatrizHome;

import java.util.Scanner;

public class Matriz {
    private int[][] matriz;
    private int linha;
    private int coluna;

    public Matriz(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        this.matriz = new int[linha][coluna];
    }

    public void lerDadosMatriz() {
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                matriz[i][j] = s.nextInt();
            }
        }
    }

    public void imprimirMatriz(String nome) {
        System.out.println("Matriz " + nome + ":");
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] getMatriz() {
        return matriz;
    }
}
