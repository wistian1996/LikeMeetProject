package br.com.metting.www.likemeet.Fragments.CadastroEventos;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.R;


public class RelogioEventoFragment extends Fragment {

    private TimePicker timePickerRelogio;
    private View view;
    private int hora = 0;
    private int minuto = 0;
    private int[] duracaoEvento = new int[2];
    private EditText duracao;
    private Evento evento;

    public RelogioEventoFragment() {

    }

    public RelogioEventoFragment(Evento evento) {
        this.evento = evento;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_relogio_evento, container, false);
        timePickerRelogio = (TimePicker) view.findViewById(R.id.timePickerRelogio);
        duracao = (EditText) view.findViewById(R.id.editTextDuracao);

        duracao.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                verificarDuracao();
                return false;
            }
        });

        timePickerRelogio.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hora = i;
                minuto = i1;
            }
        });

        duracao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    verificarDuracao();
                }
            }
        });

        if (evento != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String dataFormatada = sdf.format(evento.getDataEvento());
            String[] horaMinuto;
            horaMinuto = dataFormatada.split(":");
            hora = Integer.parseInt(horaMinuto[0]);
            minuto = Integer.parseInt(horaMinuto[1]);
            preencherRelogio();

            duracaoEvento = evento.getDuracaoEvento();
            duracao.setText(String.valueOf(duracaoEvento[0] + ":" + duracaoEvento[1]));
            Log.d(getClass().getName(), evento.getDuracaoEvento()[0] + ":" + evento.getDuracaoEvento()[1]);
        }


        return view;
    }

    private boolean verificarDuracao() {
        try {
            String duracaoEventoString[] = duracao.getText().toString().split(":");
            Log.d(getClass().getName(), "horaString:" + duracaoEventoString[0] + "minutoString: " + duracaoEventoString[1]);
            duracaoEvento[0] = Integer.parseInt(duracaoEventoString[0]);
            duracaoEvento[1] = Integer.parseInt(duracaoEventoString[1]);
            Log.d(getClass().getName(), "Hora" + duracaoEvento[0] + "Minuto" + duracaoEvento[1]);

            if (duracaoEvento[0] == 0 && duracaoEvento[1] < 10) {
                duracaoEvento[1] = 00;
                duracaoEvento[0] = 00;
                duracao.setError("A duração do evento é de no mínimo 10 minutos!");
                return false;
            }

            if (duracaoEvento[0] > 72 | duracaoEvento[1] > 60) {
                duracaoEvento[0] = 00;
                duracaoEvento[1] = 00;
                duracao.setError("Campos incorretos,máximo 72:60");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            duracaoEvento[0] = 00;
            duracaoEvento[1] = 00;
            duracao.setError("Digite a duração no seguinte formato: '00:00'");
            return false;
        }
        return true;
    }

    private void preencherRelogio() {
        // se for api 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerRelogio.setHour(hora);
        } else {
            timePickerRelogio.setCurrentHour(hora);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerRelogio.setMinute(minuto);
        } else {
            timePickerRelogio.setCurrentMinute(minuto);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        preencherRelogio();
        duracao.setText(String.valueOf(duracaoEvento[0] + ":" + duracaoEvento[1]));
    }

    public int getHora() {

        // se for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hora = timePickerRelogio.getHour();
            Log.d(getClass().getName(), " hora = " + hora);
            return hora;
        } else {
            hora = timePickerRelogio.getCurrentHour();
            Log.d(getClass().getName(), " hora = " + hora);
            return hora;
        }

    }

    public int getMinutos() {
        // se for api 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            minuto = timePickerRelogio.getMinute();
            Log.d(getClass().getName(), " minuto = " + minuto);
            return minuto;
        } else {
            minuto = timePickerRelogio.getCurrentMinute();
            Log.d(getClass().getName(), " minuto = " + minuto);
            return minuto;
        }

    }

    public int[] getDuracaoEvento() {

        if (verificarDuracao() == false) {
            int[] retorno = new int[2];
            retorno[0] = -1;
            retorno[1] = -1;
            return retorno;
        }
        return duracaoEvento;
    }

    public String getDuracaoStr() {
        return String.valueOf(duracaoEvento[0] + ":" + duracaoEvento[1]);
    }

}
