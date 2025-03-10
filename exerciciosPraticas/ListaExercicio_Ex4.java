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
public class ListaExercicio_Ex4 {
    
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);   
        
        System.out.print("Digite o numero de alunos: ");
        int numAlunos = s.nextInt();
        
        for(int i = 0; i < numAlunos; i++){
            System.out.println("Digite as notas do aluno "+ (i+1)+" :");
            System.out.print("Nota 1:");
            int nota1 = s.nextInt();
            System.out.print("Nota 2:");
            int nota2 = s.nextInt();
            System.out.print("Nota 3:");
            int nota3 = s.nextInt();
            
             System.out.println("Digite 'A' para calcular a media aritmetica ou 'P' para ponderada das notas do aluno: ");
            char letra = s.next().charAt(0);
            
            Aluno aluno = new Aluno(nota1, nota2, nota3);
            
            double media = aluno.CalcularMedia(letra);
            
            System.out.println("O Aluno "+ (i+1)+" tem a media de: "+ media);
        }
        

       
        s.close();

        
    }
    
    static class Aluno{
        private int id; 
        private int nota1;
        private int nota2;
        private int nota3;
        
        public Aluno(int n1, int n2, int n3){
            this.nota1 = n1;
            this.nota2 = n2;
            this.nota3 = n3;
        }
        
        public int getNota1(){
            return nota1;
        }
        public int getNota2(){
            return nota2;
        }
        public int getNota3(){
            return nota3;
        }
        
        public double CalcularMedia(char letra){
    
        double media;
        
        if(letra == 'A'){
            media = ((nota1 + nota2 + nota3)/3.0);
        }
        else if(letra == 'P'){
            media = (((nota1*5)+(nota2*3)+(nota3*2))/10.0);
        }
        else{
            System.out.println("Opção inválida! Digite entre 'A' ou 'P'.");
            return -1;
        }
        
        return media;
        }
    
    
    }   
}
