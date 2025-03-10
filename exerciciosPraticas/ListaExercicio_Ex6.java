    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exerciciosPraticas;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Miguel Moreira
 */
public class ListaExercicio_Ex6 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("digite quantos conjuntos de numeros deseja ordenar:");
        int N = s.nextInt();

        for (int i = 0; i < N; i++) {
            System.out.println("\nDigite os 3 numeros do conjunto " + (i + 1) + ":");
            int num1 = s.nextInt();
            int num2 = s.nextInt();
            int num3 = s.nextInt();

            ordenarEExibir(num1, num2, num3);
        }

        s.close();
    }

    public static void ordenarEExibir(int a, int b, int c) {
        int[] numeros = {a, b, c};
        Arrays.sort(numeros);

        System.out.println("numeros em ordem crescente: " + numeros[0] + ", " + numeros[1] + ", " + numeros[2]);
    }
}

