package br.com.metting.www.likemeet.Fragments.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.R;

public class infoEventoMap2Fragment extends Fragment {
    private View view;

    private TextView data;
    private TextView horario;
    private TextView entrada;
    private TextView idade;
    private TextView endereco;
    private TextView maxPessoas;
    private Evento evento;
    private TextView descricao;

    public infoEventoMap2Fragment(Evento evento) {
        this.evento = evento;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info_evento_map2, container, false);

        data = (TextView) view.findViewById(R.id.textViewInfoData);
        horario = (TextView) view.findViewById(R.id.textViewInfoHorario);
        entrada = (TextView) view.findViewById(R.id.textViewInfoEntrada);
        idade = (TextView) view.findViewById(R.id.textViewIdade);
        endereco = (TextView) view.findViewById(R.id.textViewEndereco);
        maxPessoas = (TextView) view.findViewById(R.id.textViewMaxPessoas);
        descricao = (TextView) view.findViewById(R.id.textViewDescricao);
        descricao.setText(evento.getDescricao());


        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(evento.getDataEvento());
        dataCalendar.add(Calendar.HOUR, evento.getDuracaoEvento()[0]);
        dataCalendar.add(Calendar.MINUTE, evento.getDuracaoEvento()[1]);

        SimpleDateFormat d = new SimpleDateFormat("dd/MM");
        SimpleDateFormat d2 = new SimpleDateFormat("HH:mm");
        String dataString = d.format(dataCalendar.getTime());
        String horaString = d2.format(dataCalendar.getTime());
        data.setText(evento.getDataString() + " Até: " + dataString + " às " + horaString);
        horario.setText(evento.getHoraString());


        if (evento.getValorEntrada() == 0) {
            entrada.setText("Entrada gratuita");
        } else {
            entrada.setText(String.valueOf(evento.getValorEntrada()));
        }
        if (evento.getIdadeMin() > 0) {
            idade.setText("Mínimo " + String.valueOf(evento.getIdadeMin()) + " anos");
        } else {
            idade.setText("Todas as idades");
        }

        endereco.setText(evento.getEndereco());

        if (evento.getQtdMax() > 0) {
            maxPessoas.setText("Máximo " + String.valueOf(evento.getQtdMax()) + " Pessoas");
        } else {
            maxPessoas.setText("Sem limites de participantes");
        }
        return view;
    }

}
