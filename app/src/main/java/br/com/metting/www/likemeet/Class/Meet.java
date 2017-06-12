package br.com.metting.www.likemeet.Class;

import android.util.Log;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by wisti on 03/03/2017.
 */
public class Meet {

    private static ArrayList<Evento> listaEventos;
    private static ArrayList<HistoricoEventos> historicoEventos;
    private static ArrayList<Usuario> listaUsuarios;

    public Meet() {
        listaEventos = new ArrayList<>();
        historicoEventos = new ArrayList<>();
        listaUsuarios = new ArrayList<>();

        int[] duracaoEvento = new int[2];

        duracaoEvento[0] = 24;
        duracaoEvento[1] = 00;

        java.util.Date data1 = new java.util.Date();
        Date data = new Date(data1.getTime());
        java.util.Date data2 = new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(data2);
        c.add(Calendar.DATE, +1);
        data2 = c.getTime();
        Date data3 = new Date(data2.getTime());
        Evento evento = new Evento(1, 1, "Quadra do melhores", "-10.937351,-37.082956", "Sem referencia", data3, duracaoEvento, 0.0, 50, "Bora galera, bater um baba!!!!", 0, 4, 10);
        Evento evento3 = new Evento(3, 2, "Festeja fest", "-10.942671,-37.097429", "Sem referencia", data, duracaoEvento, 120.00, 50, "Festa particular!", 0, 1, 18);
        Evento evento4 = new Evento(4, 1, "Show ao vivo, Ludos pub", "-10.970726,-37.054052", "Sem referencia", data, duracaoEvento, 0.0, 50, "Bar com show ao vivo!", 0, 2, 18);
        Evento evento2 = new Evento(2, 1, "Truco dos paulista", "-10.970726,-37.054052", "Avenida São João Batista, 651 - 1º andar - Ponto Novo, Aracaju - SE, 49097-000", data, duracaoEvento, 0.0, 50, "Partida amistosa de truco com os meus brodinho Paulista , Marco , Geraldo , Paco e o piorzinho , leite porque o bicho é ruim mermo!!!!!!!", 0, 3, 12);

        Evento evento5 = new Evento(5, 5, "Feijão com tranqueira", "-10.991158,-37.051060", "Sem referencia", data, duracaoEvento, 0.0, 50, "Bar com show ao vivo!", 0, 2, 18);

        Evento evento6 = new Evento(6, 1, "Cariri", "-10.994223,-37.053079", "Sem referencia", data, duracaoEvento, 0.0, 50, "Bar com show ao vivo!", 0, 2, 18);

        Evento evento7 = new Evento(7, 6, "SEMPESQ , UNIT", "-10.967741,-37.058704", "Sem referencia", data3, duracaoEvento, 0.0, 50, "13º SEMPESQ da UNIT!", 0, 5, 16);
        Evento evento8 = new Evento(8, 15, "TESTE , Testando", "-10.800000,-37.058704", "Sem referencia", data3, duracaoEvento, 0.0, 50, "Franklin Wistian!", 0, 6, 16);
        Evento evento9 = new Evento(9, 0, "TESTE , Testando", "-10.809544,-37.058704", "Sem referencia", data3, duracaoEvento, 0.0, 50, "Franklin Wistian!", 0, 5, 16);
        Evento evento10 = new Evento(10, 2, "TESTE , Testando", "-10.974512,-37.058704", "Sem referencia", data3, duracaoEvento, 0.0, 50, "Franklin Wistian!", 0, 5, 16);

        listaEventos.add(evento3);
        listaEventos.add(evento4);
        listaEventos.add(evento2);
        listaEventos.add(evento);
        listaEventos.add(evento5);
        listaEventos.add(evento6);
        listaEventos.add(evento7);
        listaEventos.add(evento8);
        listaEventos.add(evento9);
        listaEventos.add(evento10);

        Usuario u0 = new Usuario(1, "Franklin Wistian","A vingança é plena mata a alma e a envenena",  data, "7999845412","franklinwistian@jzone.com.br", "RioReal - SE", "/DCIM/100AVIARY/Franklin.jpg", 1, 0, "", 1);
        Usuario u1 = new Usuario(2, "Alexandre andrade","Eu sou da NASA", data, "7999845412","", "Aracaju - SE", "/DCIM/100AVIARY/Alexandre.jpg", 1, 0, "", 1);
        Usuario u2 = new Usuario(3, "Negan sales","Minha nossa, eu nunca estive tao errado em toda minha vida!", data, "7999845412","", "Itabaianinha - SE", "/DCIM/100AVIARY/Elder.jpg", 1, 0, "", 1);
        Usuario u3 = new Usuario(4, "Carlos Henrique","Likeaboss", data, "7999845412","", "Itabaiana - SE", "/DCIM/100AVIARY/Likeaboss.jpg", 1, 0, "", 1);
        Usuario u4 = new Usuario(5, "Luiz Felipe","Sou blessed", data, "7999845412","", "Aracaju - BA", "/DCIM/100AVIARY/Felipe.jpg", 1, 0, "", 1);
        Usuario u5 = new Usuario(6, "Clevia","Clevia s2 Wistian", data, "79998305214","", "RioReal - BA", "/DCIM/100AVIARY/Clevia.jpg", 1, 0, "", 1);
        listaUsuarios.add(u1);
        listaUsuarios.add(u2);
        listaUsuarios.add(u3);
        listaUsuarios.add(u4);
        listaUsuarios.add(u0);
        listaUsuarios.add(u5);

        HistoricoEventos h1 = new HistoricoEventos(2, 2, data, "foi");
        HistoricoEventos h2 = new HistoricoEventos(4, 3, data, "vai");
        HistoricoEventos h3 = new HistoricoEventos(5, 4, data, "cancelou");
        HistoricoEventos h4 = new HistoricoEventos(6, 1, data, "criou");
        HistoricoEventos h5 = new HistoricoEventos(7, 1, data, "cancelou");
        HistoricoEventos h6 = new HistoricoEventos(8, 1, data, "criou");
        HistoricoEventos h7 = new HistoricoEventos(9, 1, data, "vai");
        HistoricoEventos h8 = new HistoricoEventos(10, 1, data, "foi");
        HistoricoEventos h9 = new HistoricoEventos(10, 1, data, "foi");
        HistoricoEventos h10 = new HistoricoEventos(10, 1, data, "cancelou");
        HistoricoEventos h11 = new HistoricoEventos(10, 1, data, "criou");
        HistoricoEventos h12 = new HistoricoEventos(2, 1, data, "foi");
        HistoricoEventos h13 = new HistoricoEventos(2, 1, data, "foi");
        HistoricoEventos h14 = new HistoricoEventos(2, 1, data, "foi");
        HistoricoEventos h15 = new HistoricoEventos(2, 1, data, "foi");
        HistoricoEventos h16 = new HistoricoEventos(2, 1, data, "foi");
        HistoricoEventos h17 = new HistoricoEventos(2, 1, data, "foi");
        HistoricoEventos h18 = new HistoricoEventos(2, 1, data, "foi");
        historicoEventos.add(h1);
        historicoEventos.add(h2);
        historicoEventos.add(h3);
        historicoEventos.add(h4);
        historicoEventos.add(h5);
        historicoEventos.add(h6);
        historicoEventos.add(h7);
        historicoEventos.add(h8);
        historicoEventos.add(h9);
        historicoEventos.add(h10);
        historicoEventos.add(h11);
        historicoEventos.add(h12);
        historicoEventos.add(h13);
        historicoEventos.add(h14);
        historicoEventos.add(h15);
        historicoEventos.add(h16);
        historicoEventos.add(h17);
        historicoEventos.add(h18);


    }

    public static ArrayList<Evento> getListaEventos() {
        return listaEventos;
    }

    public static ArrayList<HistoricoEventos> getHistoricoEventos() {
        return historicoEventos;
    }


    public static ArrayList<HistoricoEventos> getHistoricoEventos(int id) {
        ArrayList<HistoricoEventos> listaH = new ArrayList<>();
        for (HistoricoEventos l : historicoEventos
                ) {
            if (l.getIdUsuario() == id) {
                listaH.add(l);
            }
        }
        return listaH;
    }


    public static ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public static Usuario getUsuario(int id) {
        for (Usuario lista : listaUsuarios
                ) {
            if (lista.getId() == id) {
                return lista;
            }
        }
        return null;
    }

    public static void cadastrarEvento(int idUsuarioCadastrou, String nome, String local,
                                       String endereco, Date dataEvento, int[] duracaoEvento,
                                       Double valorEntrada, int qtdMax,
                                       String descricao, int privado, int idCategoria, int idadeMin) {
        //local = latlng
        // nome , descricao , endereco , local , idusuariocadastrou, dataevento sao NOTNULL


        // criando um ID diferente
        Random aleatorio = new Random(1000);
        int id = 0;
        boolean resultado = false;

        while (resultado == false) {
            id = aleatorio.nextInt();
            if (listaEventos != null) {
                for (Evento listaE : listaEventos
                        ) {
                    if (id == listaE.getId()) {
                        break;
                    }
                }
                resultado = true;

            } else {
                resultado = true;
            }
        }


        Evento evento = new Evento(id, idUsuarioCadastrou, nome, local, endereco, dataEvento, duracaoEvento, valorEntrada, qtdMax, descricao, privado, idCategoria, idadeMin);
        Log.d("Cadastro evento", evento.toString());
        listaEventos.add(evento);
    }

}
