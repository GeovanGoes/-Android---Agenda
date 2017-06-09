package br.com.alura.agenda.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by root on 21/04/17.
 * Model que representa um aluno
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aluno implements Serializable {

    /**
     * UUID do usuario
     * */
    @JsonProperty("id")
    private String id;

    /**
     * Nome
     * */
    private String nome;

    /**
     * Endereço
     * */
    private String endereco;

    /**
     * Telefone
     * */
    private String telefone;

    /**
     * Site
     * */
    private String site;

    /**
     *
     * */
    private Double nota;

    /**
     *
     * */
    private String caminhoFoto;

    private int desativado;

    private int sincronizado;

    public Aluno() {
    }

    public Aluno(String id, String nome, String endereco, String telefone, String site, Double nota, String caminhoFoto) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.site = site;
        this.nota = nota;
        this.caminhoFoto = caminhoFoto;
    }

    public void sincroniza(){
        this.sincronizado = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public int getDesativado() {
        return desativado;
    }

    public void setDesativado(int desativado) {
        this.desativado = desativado;
    }

    public boolean estaDesativado(){
        return desativado == 1;
    }

    public int getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.nome;
    }

    public void desincroninza() {
        this.sincronizado = 0;
    }

    public void desativar() {
        this.desativado = 1;
        desincroninza();
    }
}
