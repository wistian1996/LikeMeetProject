package br.com.metting.www.likemeet.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.R;

/**
 * Created by wisti on 06/03/2017.
 */
public class MeusEventosAdapter extends RecyclerView.Adapter<MeusEventosAdapter.MyViewHolder> {
    ArrayList<Evento> lEvento;
    private LayoutInflater mLayoutInflater;
    Context c;

    public MeusEventosAdapter(Context c, ArrayList<Evento> lEvento) {

        this.mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.c = c;
        this.lEvento = lEvento;
    }

    @Override
    public MeusEventosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_view_meus_eventos, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MeusEventosAdapter.MyViewHolder holder, int position) {
        // acoes para os botoes
        holder.id = lEvento.get(position).getId();
        holder.textViewDescricao.setText(lEvento.get(position).getDescricao());
        holder.textViewNome.setText(lEvento.get(position).getNome());
        holder.textViewFluxoMax.setText(String.valueOf(lEvento.get(position).getQtdMax()));
        holder.textViewEntrada.setText(String.valueOf(lEvento.get(position).getValorEntrada()));


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatHora = new SimpleDateFormat("HH:mm:ss");

        String data = dateFormat.format(lEvento.get(position).getDataEvento());
        String hora = dateFormatHora.format(lEvento.get(position).getDataEvento());
        holder.textViewData.setText(data);
        holder.textViewHorario.setText(hora);
        String taxaEntrada;



        if (lEvento.get(position).getValorEntrada() != 0.0) {
            taxaEntrada = String.valueOf(lEvento.get(position).getValorEntrada()) + " R$";

        } else {
            taxaEntrada = "Gratuita";
        }
        holder.textViewEntrada.setText(taxaEntrada);


    }

    @Override
    public int getItemCount() {
        if (lEvento != null) {
            return lEvento.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public int id;
        public TextView textViewNome;
        public TextView textViewEntrada;
        public TextView textViewDescricao;
        public TextView textViewData;
        public TextView textViewIdadeMinima;
        public TextView textViewFluxoMax;
        public TextView textViewHorario;
        public TextView textViewCategoria;


        public MyViewHolder(final View itemView) {

            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            textViewDescricao = (TextView) itemView.findViewById(R.id.textViewDescricao);
            textViewData = (TextView) itemView.findViewById(R.id.textViewData);
            textViewHorario = (TextView) itemView.findViewById(R.id.textViewHorario);
            textViewEntrada = (TextView) itemView.findViewById(R.id.textViewEntrada);
            textViewIdadeMinima = (TextView) itemView.findViewById(R.id.textViewIdadeMinima);
            textViewFluxoMax = (TextView) itemView.findViewById(R.id.textViewFluxoMax);
            textViewCategoria = (TextView) itemView.findViewById(R.id.textViewCategoria);


        }


    }

}

