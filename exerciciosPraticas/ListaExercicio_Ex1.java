/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.exerciciosPraticas;

import java.util.Scanner;

/**
 *
 * @author Miguel Moreira
 */
public class ListaExercicio_Ex1 {   
    
    private static final int TAMANHO_ARRAY = 15;
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int[] vetorA = new int [TAMANHO_ARRAY];
        int[] vetorB = new int [TAMANHO_ARRAY];
        
        for(int i = 0; i<TAMANHO_ARRAY; i++){
           int posicao = i+1; 
           System.out.print("Insira o valor " + posicao + " na Array: ");
           vetorA[i] = s.nextInt();
           vetorB[i] = (int) FatorialNumero(vetorA[i]);
        }
               
        
        System.out.println("Array A Fatorada: ");
        for(int i = 0; i<TAMANHO_ARRAY; i++){
            System.out.println(vetorA[i]+"! = " + vetorB[i]);
        }
        
         s.close();
    }
    public static long FatorialNumero(int num){
        if(num < 0)return -1;
        if(num == 0 || num == 1) return 1;
          
        long fatorial = 1;
        for(int i = 2; i <= num; i++){
            fatorial *= i;
        }
            
        return fatorial;
        }
}
