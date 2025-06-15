package model;

public class Projeto {
 
    private Profissional Orientador;
    private equipe Equipe;
    private int notaFinal;

    // private String nome;
    // private String descricao;

    public Projeto(Profissional Orientador, equipe Equipe, int NotaFinal){
        this.Orientador = Orientador;
        this.Equipe = Equipe;
        this.notaFinal = notaFinal;
    }

}
