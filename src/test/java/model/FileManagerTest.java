package com.mycompany.labsprint1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileManagerTest {
    
    @Test
    void testSalvarDados() throws IOException {
        String dados = "Teste de dados";
        String nomeArquivo = "teste_salvar.txt";
        
        FileManager.salvarObjeto(dados, nomeArquivo);
        
        File arquivo = new File(nomeArquivo);
        assertTrue(arquivo.exists());
        
        List<String> linhas = Files.readAllLines(Path.of(nomeArquivo));
        assertEquals(1, linhas.size());
        assertEquals(dados, linhas.get(0));
        
        // Limpeza
        arquivo.delete();
    }
    
    @Test
    void testCarregarDados() throws IOException {
        String dados = "Teste de dados";
        String nomeArquivo = "teste_carregar.txt";
        
        // Criar arquivo de teste
        Files.writeString(Path.of(nomeArquivo), dados);
        
        String dadosCarregados = (String) FileManager.carregarObjeto(nomeArquivo);
        assertEquals(dados, dadosCarregados);
        
        // Limpeza
        new File(nomeArquivo).delete();
    }
    
    @Test
    void testCarregarDadosArquivoInexistente() {
        String nomeArquivo = "arquivo_inexistente.txt";
        assertThrows(IOException.class, () -> {
            FileManager.carregarObjeto(nomeArquivo);
        });
    }
    
    @Test
    void testSalvarDadosNomeArquivoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            FileManager.salvarObjeto("dados", null);
        });
    }
    
    @Test
    void testCarregarDadosNomeArquivoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            FileManager.carregarObjeto(null);
        });
    }
} 