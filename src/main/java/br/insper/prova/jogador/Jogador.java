package br.insper.prova.jogador;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class Jogador {

    @Id
    private String id;
    private String nome;
    private Integer idade;
    private ArrayList<String> times;

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }

    public Jogador() {
    }


}
