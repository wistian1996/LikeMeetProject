package br.com.metting.www.likemeet.Class;

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

}
