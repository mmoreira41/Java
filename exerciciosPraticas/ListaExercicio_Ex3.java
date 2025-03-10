/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exerciciosPraticas;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import javax.swing.text.DateFormatter;

/**
 *
 * @author Miguel Moreira
 */
public class ListaExercicio_Ex3 {
    
    private static final int NUM_FUNCIONARIOS = 10;
    
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        Funcionario[] funcionarios = new Funcionario[NUM_FUNCIONARIOS];
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for(int i = 0; i<NUM_FUNCIONARIOS; i++){
            int numFunc = i+1;
            System.out.print("ola Funcionario "+ numFunc + ". Digite o seu nome: ");
            String nome = s.next();
            System.out.print("Bom, agora seu sexo (F/M): ");
            String sexo = s.next();
            
            LocalDate dataNascimento = null;
            while (dataNascimento == null) {
                System.out.print("Bom, agora sua Data de nascimento (dd/mm/aaaa): ");
                String dataNasc = s.next();
                try {
                    dataNascimento = LocalDate.parse(dataNasc, formatter);
                } catch (Exception e) {
                    System.out.println("Formato inválido. Use dd/MM/yyyy.");
                }
                System.out.println("");
            }

            funcionarios[i] = new Funcionario(nome,sexo,dataNascimento);  
 
        }
        
        System.out.println("Tivemos cadastrados: "+ NUM_FUNCIONARIOS + " funcionarios");
        
        int qntFuncFem = 0;
        int qntFuncMasc = 0;
        
        for(int i = 0; i<NUM_FUNCIONARIOS; i++){
            if(funcionarios[i].sexo.equals("F")){
                qntFuncFem++;
            }else if(funcionarios[i].sexo.equals("M")){
                qntFuncMasc++;
            }
           
        }
         double PercentualFem = ((double)qntFuncFem/NUM_FUNCIONARIOS)*100;
         double PercentualMasc = ((double)qntFuncMasc/NUM_FUNCIONARIOS)*100;

         System.out.println("Tivemos "+ qntFuncFem +" do sexo Feminino ("+ String.format("%.2f", PercentualFem) +"%) \n E "+ qntFuncMasc + " do sexo Masculino ("+ String.format("%.2f", PercentualMasc) +"%).");
         
         
        int somaIdade = 0;
        int idadeMediaFem = 0;
        int idadeMediaMasc = 0;

        for(int i=0; i<=NUM_FUNCIONARIOS; i++){
            somaIdade+=funcionarios[i].getIdade();
        }
        
        
        
        System.out.println(); 
         
         s.close();
    }
    static class Funcionario{ 
        
        private String nome;
        private String sexo;
        private LocalDate dataNasc;
        
        
        public Funcionario(String nome, String sexo, LocalDate dataNasc){
            this.nome = nome;
            this.sexo = sexo;
            this.dataNasc = dataNasc;
        }
        
        public String getNome(){
            return nome;
        }
        
        public String getSexo(){
            return sexo;
        }
        
        public int getIdade(){
            return LocalDate.now().getYear() - dataNasc.getYear();
        }
        
        
       
    }
}
