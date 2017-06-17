package br.com.metting.www.likemeet.Class;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by wisti on 12/06/2017.
 */


public class PublicacaoImagem {
    private int id;
    private int idEvento;
    private int idUsuario;
    private int qtdVizualizacoes;
    private String descricao;
    private String URL;
    private Date dataPublicacao;

    public PublicacaoImagem(int id, int idEvento, int idUsuario, String descricao, String URL, Date dataPublicacao , int qtdVizualizacoes) {
        this.id = id;
        this.idEvento = idEvento;
        this.descricao = descricao;
        this.idUsuario = idUsuario;
        this.URL = URL;
        this.dataPublicacao = dataPublicacao;
        this.qtdVizualizacoes = qtdVizualizacoes;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public String getDataPublicacaoString() {
        DateFormat df = new SimpleDateFormat("dd/MM às HH:mm");
        java.util.Date dt = new java.util.Date(dataPublicacao.getTime());
        return df.format(dt);

    }

    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getQtdVizualizacoes() {
        return qtdVizualizacoes;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getURL() {
        return URL;
    }

}
