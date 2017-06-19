package br.com.metting.www.likemeet.Control;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wisti on 18/06/2017.
 */

public class DataControl {

    public static String getDataPublicacaoString(Date data) {
        DateFormat df1 = new SimpleDateFormat("dd/MM");
        DateFormat df2 = new SimpleDateFormat("HH:mm");
        java.util.Date dt1 = new java.util.Date(data.getTime());


        Calendar dataPublicacao = Calendar.getInstance();
        dataPublicacao.setTime(dt1);
        dataPublicacao.set(Calendar.HOUR, 0);
        dataPublicacao.set(Calendar.MINUTE, 0);
        dataPublicacao.set(Calendar.SECOND, 0);
        dataPublicacao.set(Calendar.MILLISECOND, 0);

        Calendar atual = Calendar.getInstance();
        atual.set(Calendar.HOUR, 0);
        atual.set(Calendar.MINUTE, 0);
        atual.set(Calendar.SECOND, 0);
        atual.set(Calendar.MILLISECOND, 0);

        String dia ;

        if (dataPublicacao.getTime().getTime() == atual.getTime().getTime()) {
            dia = "hoje";
            Log.d("DataControl","dia = " + dia);
        } else {
            atual.add(Calendar.DAY_OF_MONTH, -1);
            if (dataPublicacao.getTime().getTime() == atual.getTime().getTime()) {
                dia = "Ontem";
                Log.d("DataControl","dia = " + dia);
            }else{
                dia = df1.format(dt1);
                Log.d("DataControl","dia = " + dia);
            }
        }
        String retorno = dia + " às " + df2.format(dt1);
        return retorno;

    }
}
