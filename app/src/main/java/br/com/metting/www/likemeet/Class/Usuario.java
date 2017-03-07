package br.com.metting.www.likemeet.Class;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by wisti on 03/12/2016.
 */
public class Usuario {


    int id;
    String nome;
    String tel;
    Date nasc;
    String foto;
    int ativo;
    int qtdEventosCadastrados;
    String codigoAtivo;
    int vip;// (define se o usuario é um usuario vip ou nao)

    public Usuario(int id, String nome, Date nasc, String tel, String foto, int ativo, int qtdEventosCadastrados, String codigoAtivo, int vip) {
        this.id = id;
        this.nome = nome;
        this.nasc = nasc;
        this.tel = tel;
        this.foto = foto;
        this.ativo = ativo;
        this.qtdEventosCadastrados = qtdEventosCadastrados;
        this.codigoAtivo = codigoAtivo;
        this.vip = vip;
    }

    public Usuario(String nome, String tel) {
        this.nome = nome;
        this.tel = tel;

    }

    public Usuario getUsuario() {
        java.util.Date data = new java.util.Date();
        Date nive = new Date(data.getTime());
        Usuario u = new Usuario(1, "Franklin Wistian", nive, "75999843408", "", 1, 0, "", 1);
        return u;
    }

    public static ArrayList<Evento> getEventosUsuario(int id) {
        ArrayList<Evento> lista = new ArrayList<>();
        for (Evento listaEvento : Meet.getListaEventos()
                ) {
            if (listaEvento.getIdUsuarioCadastrou() == id) {
                lista.add(listaEvento);
            }
        }
        return lista;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTel() {
        return tel;
    }

    public Date getNasc() {
        return nasc;
    }

    public String getFoto() {
        return foto;
    }

    public int getAtivo() {
        return ativo;
    }

    public int getQtdEventosCadastrados() {
        return qtdEventosCadastrados;
    }

    public String getCodigoAtivo() {
        return codigoAtivo;
    }

    public int getVip() {
        return vip;
    }
}
