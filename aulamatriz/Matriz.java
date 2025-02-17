/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.aulamatriz;

import java.util.Scanner;

/**
 *
 * @author 832593
 */
public class Matriz{

    int linha;
    int coluna;
    int[][] matriz;
    int contador;
    
    
    public void inicializarMatriz(int linha, int coluna){   
        this.linha = linha;
        this.coluna = coluna;
        
        matriz = new int[linha][coluna];
    }
    
    public int[][] multiplicaCopiaMatrizPorConstante(int c){
        
        this.contador = c;
        System.out.println("Copia da Matriz Multiplicada:");
        
        int[][] result = new int[linha][coluna];        
        for(int i = 0; i <linha; i++){
            for(int j = 0; j <coluna; j++){
                System.out.print(matriz[i][j]*c + " ");
            }
            System.out.println(" ");
        }
        return result;
    }
    
    
    public void lerDadosMatriz(){
        Scanner s = new Scanner(System.in);
        
        for(int i = 0; i <linha; i++){
            for(int j = 0; j <coluna; j++){
                matriz[i][j] = s.nextInt(); 
            }
        }
    }
    
   public void imprimirMatriz(){
       System.out.println("Matriz Dada:");
       for(int i = 0; i <linha; i++){
            for(int j = 0; j <coluna; j++){
                System.out.print(matriz[i][j] + " "); 
            }
            System.out.println(" ");
        }
   }
   
   public static void main(String[] args) {
       
   }

}
