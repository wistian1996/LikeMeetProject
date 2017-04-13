package br.com.metting.www.likemeet.FirebaseModel;

import br.com.metting.www.likemeet.Class.Usuario;

/**
 * Created by wisti on 12/04/2017.
 */

public class Evento {

    private int id;
    private Usuario usuario;
    private String nome;
    private String local;
    private String endereco;
    private String dataEvento;//(data em que o evento vai ocorrer)
    private String duracaoEvento; // duracao do evento
    private double valorEntrada;//(caso haja valor de entrada a ser cobrado)
    private int qtdMax;//(quantidade maxima de pessoas)
    private String descricao;
    private boolean privado;//(se o evento for privado apenas o admin podera adicionar as pessoas ao evento)
    private Categoria categoria;//(jogos,festa,bares,shows)
    private int idadeMin;// (idade minina restringida ao usuario)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getDuracaoEvento() {
        return duracaoEvento;
    }

    public void setDuracaoEvento(String duracaoEvento) {
        this.duracaoEvento = duracaoEvento;
    }

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public int getQtdMax() {
        return qtdMax;
    }

    public void setQtdMax(int qtdMax) {
        this.qtdMax = qtdMax;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPrivado() {
        return privado;
    }

    public void setPrivado(boolean privado) {
        this.privado = privado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getIdadeMin() {
        return idadeMin;
    }

    public void setIdadeMin(int idadeMin) {
        this.idadeMin = idadeMin;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", nome='" + nome + '\'' +
                ", local='" + local + '\'' +
                ", endereco='" + endereco + '\'' +
                ", dataEvento='" + dataEvento + '\'' +
                ", duracaoEvento='" + duracaoEvento + '\'' +
                ", valorEntrada=" + valorEntrada +
                ", qtdMax=" + qtdMax +
                ", descricao='" + descricao + '\'' +
                ", privado=" + privado +
                ", categoria=" + categoria +
                ", idadeMin=" + idadeMin +
                '}';
    }
}
