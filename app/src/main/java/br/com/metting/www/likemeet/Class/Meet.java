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

    private static ArrayList<Evento> listaEventos ;

    public Meet (){
        listaEventos = new ArrayList<>();

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
        Evento evento = new Evento(1, 1, "Quadra do melhores", "-10.937351,-37.082956", "Sem referencia", data3, duracaoEvento,0.0, 50, "Bora galera, bater um baba!!!!", 0, 4, 10);
        Evento evento3 = new Evento(3, 2, "Festeja fest", "-10.942671,-37.097429", "Sem referencia", data,duracaoEvento, 120.00, 50, "Festa particular!", 0, 1, 18);
        Evento evento4 = new Evento(4, 1, "Show ao vivo, Ludos pub", "-10.970726,-37.054052", "Sem referencia", data,duracaoEvento, 0.0, 50, "Bar com show ao vivo!", 0, 2, 18);
        Evento evento2 = new Evento(2, 1, "Truco dos paulista", "-10.970726,-37.054052", "Avenida São João Batista, 651 - 1º andar - Ponto Novo, Aracaju - SE, 49097-000", data,duracaoEvento, 0.0, 50, "Partida amistosa de truco com os meus brodinho Paulista , Marco , Geraldo , Paco e o piorzinho , leite porque o bicho é ruim mermo!!!!!!!", 0, 3, 12);

        Evento evento5 = new Evento(5, 5, "Feijão com tranqueira", "-10.991158,-37.051060", "Sem referencia", data,duracaoEvento, 0.0, 50, "Bar com show ao vivo!", 0, 2, 18);

        Evento evento6 = new Evento(6,1, "Cariri", "-10.994223,-37.053079", "Sem referencia", data,duracaoEvento, 0.0, 50, "Bar com show ao vivo!", 0, 2, 18);

        Evento evento7 = new Evento(7, 6, "SEMPESQ , UNIT", "-10.967741,-37.058704", "Sem referencia", data3,duracaoEvento, 0.0, 50, "13º SEMPESQ da UNIT!", 0, 5, 16);
        Evento evento8 = new Evento(8, 15, "TESTE , Testando", "-10.800000,-37.058704", "Sem referencia", data3,duracaoEvento, 0.0, 50, "Franklin Wistian!", 0, 6, 16);
        Evento evento9 = new Evento(9, 0, "TESTE , Testando", "-10.809544,-37.058704", "Sem referencia", data3,duracaoEvento, 0.0, 50, "Franklin Wistian!", 0, 5, 16);
        Evento evento10 = new Evento(10, 2, "TESTE , Testando", "-10.974512,-37.058704", "Sem referencia", data3,duracaoEvento, 0.0, 50, "Franklin Wistian!", 0, 5, 16);

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

    }

    public static ArrayList<Evento> getListaEventos() {
        return listaEventos;
    }


    public static void cadastrarEvento(int idUsuarioCadastrou, String nome, String local,
                                       String endereco, Date dataEvento,int[]duracaoEvento,
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

            }else{
                resultado = true;
            }
        }


        Evento evento = new Evento(id, idUsuarioCadastrou, nome, local, endereco, dataEvento, duracaoEvento,valorEntrada, qtdMax, descricao, privado, idCategoria, idadeMin);
        Log.d("Cadastro evento" , evento.toString());
        listaEventos.add(evento);
    }

}
