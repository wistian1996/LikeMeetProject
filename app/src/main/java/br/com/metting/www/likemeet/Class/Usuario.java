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
    String status;
    String tel;
    String email;
    String endereco;
    Date nasc;
    String foto;
    int ativo;
    int qtdEventosCadastrados;
    String codigoAtivo;
    int vip;// (define se o usuario é um usuario vip ou nao)

    public Usuario(int id, String nome, String status, Date nasc, String tel , String email, String endereco, String foto, int ativo, int qtdEventosCadastrados, String codigoAtivo, int vip) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.endereco = endereco;
        this.nasc = nasc;
        this.tel = tel;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public static Usuario getUsuario() {
        return Meet.getUsuario(1);
    }

    public static Usuario getUsuario(int id) {
        for (Usuario lista : Meet.getListaUsuarios()
                ) {
            if (lista.getId() == id) {
                return lista;
            }
        }
        return null;
    }


    public String getEndereco() {
        return endereco;
    }

    public String getStatus() {
        return status;
    }

    public static ArrayList<Evento> getEventosUsuario(int id) {

        ArrayList<Evento> lista = new ArrayList<>();

        // lista de eventos que o usuario cadastrou
        for (Evento listaEvento : Meet.getListaEventos()
                ) {
            if (listaEvento.getIdUsuarioCadastrou() == id) {
                lista.add(listaEvento);
            }

        }
        return lista;
    }


    public static ArrayList<Evento> getMeusEventos(int id) {
        ArrayList<Evento> lista = new ArrayList<>();
        for (Evento listaEvento : Meet.getListaEventos()
                ) {
            if (listaEvento.getIdUsuarioCadastrou() == id) {
                lista.add(listaEvento);
            }
        }

        lista.add(Evento.getEvento(8));
        lista.add(Evento.getEvento(7));
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
