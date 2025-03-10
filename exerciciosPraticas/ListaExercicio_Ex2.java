/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exerciciosPraticas;

import static com.mycompany.exerciciosPraticas.ListaExercicio_Ex1.FatorialNumero;
import java.util.Scanner;

/**
 *
 * @author Miguel Moreira
 */
public class ListaExercicio_Ex2 {
    
    private static final int TAMANHO_ARRAY = 10;
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int[] VetorA = new int[TAMANHO_ARRAY];
        int[] ParImpar = new int[2];
        
        for(int i = 0; i<TAMANHO_ARRAY; i++){
          int posicao = i+1; 
          System.out.print("Insira o valor " + posicao + " na Array: ");
          VetorA[i] = s.nextInt();
          if((VetorA[i] % 2)== 0){
              ParImpar[0] +=1;
          }else{
              ParImpar[1] +=1;
          }
        }
        
        System.out.println("Da lista Inserida, tiveram: "+ ParImpar[0]+
                " numeros pares. E tiveram: "+ ParImpar[1]+ 
                " numeros impares");
        
        
        

    }
}
