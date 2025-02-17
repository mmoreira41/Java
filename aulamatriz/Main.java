/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aulamatriz;

import java.util.Scanner;

/**
 *
 * @author 832593
 */
public class Main {
  
    public static void main(String[] args) {
        Matriz matriz = new Matriz();
        
        Scanner s = new Scanner(System.in);
        
        System.out.print("Digite o numero de linhas: ");
        matriz.linha = s.nextInt();
        
        System.out.print("Digite o numero de coluna: ");
        matriz.coluna = s.nextInt();
       
        matriz.inicializarMatriz(matriz.linha, matriz.coluna);
        matriz.lerDadosMatriz();
        matriz.imprimirMatriz();
        
        System.out.print("Digite por quanto quer repetir a matriz: ");
        matriz.contador = s.nextInt();
        matriz.multiplicaCopiaMatrizPorConstante(matriz.contador);
        
    }
    
    
}
