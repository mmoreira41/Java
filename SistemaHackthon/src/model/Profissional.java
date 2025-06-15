package model;

public class Profissional extends Pessoa{
    private String area;

    public Profissional(String nome, String area){
        super(nome);
        this.area = area;
    }

    public String getArea() {
        return area;
    }
    
}
