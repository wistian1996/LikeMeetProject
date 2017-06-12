package br.com.metting.www.likemeet.Class;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by wisti on 22/05/2017.
 */

public class HistoricoEventos {
    private int idEvento;
    private int idUsuario;
    private String tipo; // criou , cancelou , foi , vai
    private Date data; // data hora e minuto do historico


    public HistoricoEventos(int idEvento, int idUsuario, Date data, String tipo) {
        this.idEvento = idEvento;
        this.idUsuario = idUsuario;
        this.data = data;
        this.tipo = tipo;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getData() {
        return data;
    }

    public static HistoricoEventos getEvento(int id) {
        for (HistoricoEventos lista2 : Meet.getHistoricoEventos()
                ) {
            if (lista2.getIdEvento() == id) {
                return lista2;
            }
        }
        return null;
    }
}
