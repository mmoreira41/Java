/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.MultMatrizHome;

/**
 *
 * @author 832593
 */
public class MultiplicarMatriz {
    
    public static int[][] MultiplicarMatriz2x2(int matriz1[][], int matriz2[][]){
        int linhas = matriz1.length;
        int colunas = matriz2[0].length;
        int[][] result = new int[linhas][colunas];
        
        for (int i = 0; i < linhas; i++){
            for( int j = 0; j < colunas; j++){
                 for( int k = 0; k < matriz1[0].length; k++){
                     result[i][j] += matriz1[i][k] * matriz2[k][j];
                }
            }
        }
        return result;
    }
    
}