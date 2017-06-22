package br.com.metting.www.likemeet.Class;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Array;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by wisti on 03/12/2016.
 */
public class Evento {

    private int id;
    private int idUsuarioCadastrou;
    private String Nome;
    private String local;
    private String endereco;
    private Date dataEvento;//(data em que o evento vai ocorrer)
    private int[] duracaoEvento; // duracao do evento
    private Double valorEntrada;//(caso haja valor de entrada a ser cobrado)
    private int qtdMax;//(quantidade maxima de pessoas)
    private String descricao;
    private int privado;//(se o evento for privado apenas o admin podera adicionar as pessoas ao evento)
    private int idCategoria;//(jogos,festa,bares,shows)
    private int idadeMin;// (idade minina restringida ao usuario)
    private ArrayList<Usuario> listaPartipantes;

    public Evento(int id, int idUsuarioCadastrou, String nome, String local, String endereco, Date dataEvento, int[] duracaoEvento, Double valorEntrada, int qtdMax, String descricao, int privado, int idCategoria, int idadeMin) {
        this.id = id;
        this.idUsuarioCadastrou = idUsuarioCadastrou;
        Nome = nome;
        this.local = local;
        this.endereco = endereco;
        this.dataEvento = dataEvento;
        this.valorEntrada = valorEntrada;
        this.qtdMax = qtdMax;
        this.descricao = descricao;
        this.privado = privado;
        this.idCategoria = idCategoria;
        this.idadeMin = idadeMin;
        this.duracaoEvento = duracaoEvento;
        this.listaPartipantes = Meet.getListaUsuarios();
    }

    public void setListaPartipantes(ArrayList<Usuario> listaPartipantes) {
        this.listaPartipantes = listaPartipantes;
    }

    public Evento() {

    }

    public void adicionarUsuario(Usuario usuario){
        listaPartipantes.add(usuario);
    }

    public static Evento getEvento(int id) {
        for (Evento list : Meet.getListaEventos()
                ) {
            if (list.getId() == id) {
                return list;
            }
        }
        return null;

    }

    public static ArrayList<Evento> getEventoCategoria(int idCategoria) {
        ArrayList<Evento> listaEventos = new ArrayList<>();
        for (Evento lista : Meet.getListaEventos()
                ) {
            if (lista.getIdCategoria() == idCategoria) {
                listaEventos.add(lista);
            }
        }
        return listaEventos;
    }

    public static ArrayList<Evento> getEvento(String nome) {
        ArrayList<Evento> listaEventos = new ArrayList<>();
        for (Evento lista : Meet.getListaEventos()
                ) {
            if (lista.getNome().toLowerCase().contains(nome.toLowerCase()) || lista.getDescricao().toLowerCase().contains(nome.toLowerCase())) {
                listaEventos.add(lista);
            }
        }
        return listaEventos;
    }

    public static ArrayList<Evento> getListaEventosLatLong(LatLng latLong) {
        ArrayList<Evento> list = new ArrayList<>();


        for (Evento lisEvento : Meet.getListaEventos()
                ) {
            String[] latlong = lisEvento.getLocal().split(",");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);
            LatLng location = new LatLng(latitude, longitude);

            if (location.equals(latLong)) {
                list.add(lisEvento);
            }
        }
        return list;
    }

    public String getDataString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String data = dateFormat.format(dataEvento);
        return data;
    }

    public String getHoraString() {

        SimpleDateFormat dateFormatHora = new SimpleDateFormat("HH:mm:ss");

        String hora = dateFormatHora.format(dataEvento);
        return hora;
    }

    @Override
    public String toString() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date data2 = new java.util.Date(dataEvento.getTime());

        String dataString = dateFormat.format(data2);


        return "id =" + id + "\n" +
                "id usuarioCadastrou: " + idUsuarioCadastrou + "\n" +
                "Nome: " + Nome + "\n" +
                "Local: " + local + "\n" +
                "Endereco: " + endereco + "\n" +
                "DataControl: " + dataString + "\n" +
                "valorEntrada: " + valorEntrada + "\n" +
                "qtdMax pessoas: " + qtdMax + "\n" +
                "Descricao: " + descricao + "\n" +
                "Privado: " + privado + "\n" +
                "idCategoria: " + idCategoria + "\n" +
                "idade Min: " + idadeMin;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Usuario> getListaPartipantes() {
        return listaPartipantes;
    }

    public int getIdUsuarioCadastrou() {
        return idUsuarioCadastrou;
    }

    public String getNome() {
        return Nome;
    }

    public String getLocal() {
        return local;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public String getEndereco() {
        return endereco;
    }

    public Double getValorEntrada() {
        return valorEntrada;
    }

    public int getQtdMax() {
        return qtdMax;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPrivado() {
        return privado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public int getIdadeMin() {
        return idadeMin;
    }

    public int[] getDuracaoEvento() {
        return duracaoEvento;
    }
}
