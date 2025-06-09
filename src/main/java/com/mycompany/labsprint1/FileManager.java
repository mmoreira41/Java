package com.mycompany.labsprint1;

import java.io.*;

public class FileManager {

    // Método para gravar uma lista de objetos em um arquivo
    public static void salvarObjeto(String fileName, Object objeto) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(objeto);
            System.out.println("Dados salvos com sucesso no arquivo: " + fileName);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    // Método para ler uma lista de objetos de um arquivo
    public static Object carregarObjeto(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
            return null;
        }
    }
}
