package br.com.metting.www.likemeet.Fragments.CadastroEventos;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.metting.www.likemeet.R;


public class CalendarioEventoFragment extends Fragment {

    private CaldroidFragment caldroidFragment;
    private Date dataAnterior;
    private Date dataMarcada;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setLocale();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendario_evento, container, false);


        // fragmento calendário
        caldroidFragment = new CaldroidFragment();

        // ações que são chamada ao mexer no calendário
        CaldroidListener caldroidListener = new CaldroidListener() {
            // chamado a pressionar alguma data
            @Override
            public void onSelectDate(Date date, View view) {
                marcarCalendario(date);
            }

            // chamado quando ocorrer alteração de mês
           /* @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getContext(), text,
                        Toast.LENGTH_SHORT).show();
            }*/
        };

        // inicilização dos parâmetros do calendrário
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        caldroidFragment.setCaldroidListener(caldroidListener);
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        return view;
    }

    // permite realizar a marcação da data do calendário, passando como parâmetro o dia a ser marcado
    public void marcarCalendario(java.util.Date date) {
        ColorDrawable c = new ColorDrawable(getResources().getColor(R.color.colorAccent));
        //ColorDrawable c2 = new ColorDrawable(getResources().getColor(R.color.colorPrimary)) ;
        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -1);
        if (dataAtual != null  && date != null  && dataAtual.getTime().compareTo(date) > 0){
            Log.d("Entrou", "data atual maior que date");
            return;
        }

        if (dataAnterior != null)caldroidFragment.clearBackgroundDrawableForDate(dataAnterior);

        if(date != null) caldroidFragment.setBackgroundDrawableForDate(c, date);
        dataAnterior = date;
        dataMarcada = date;
        caldroidFragment.refreshView();
    }

    // seta a configuração padrão de linguagem
    public void setLocale() {
        Locale locale = new Locale("pt_BR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config,
                getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onResume() {
        super.onResume();
        marcarCalendario(dataMarcada);
    }

    public Date getDataMarcada() {
        return dataMarcada;
    }
}
