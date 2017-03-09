package br.com.metting.www.likemeet.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.metting.www.likemeet.Activitys.MainActivity;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Fragments.InfoEventoMapFragment;
import br.com.metting.www.likemeet.Fragments.ProcurarEventosMeetFragment;
import br.com.metting.www.likemeet.R;

/**
 * Created by wisti on 18/12/2016.
 */
public class EventosProcurarAdapter extends RecyclerView.Adapter<EventosProcurarAdapter.MyViewHolder> {
    ArrayList<Evento> list;
    private LayoutInflater mLayoutInflater;
    Context c;

    public EventosProcurarAdapter(Context c, ArrayList<Evento> list) {
        this.mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.c = c;
    }

    @Override
    public EventosProcurarAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_view_lista_eventos_procurar, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }

    }

    @Override
    public void onBindViewHolder(EventosProcurarAdapter.MyViewHolder holder, final int position) {
        // acoes para os botoes
        holder.id = list.get(position).getId();
        holder.textViewDescricao.setText(list.get(position).getDescricao());
        holder.textViewNome.setText(list.get(position).getNome());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatHora = new SimpleDateFormat("HH:mm:ss");
        String data = dateFormat.format(list.get(position).getDataEvento());
        String hora = dateFormatHora.format(list.get(position).getDataEvento());



        //acao ao clicar no card view
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!ProcurarEventosMeetFragment.slider.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {


                    // mostrando a janela de informacoes do marker
                    String[] latlong = list.get(position).getLocal().split(",");
                    double latitude = Double.parseDouble(latlong[0]);
                    double longitude = Double.parseDouble(latlong[1]);
                    LatLng local = new LatLng(latitude, longitude);


                    // abrindo a tela de informacoes
                    Fragment fragment = new InfoEventoMapFragment(list.get(position));

                    android.support.v4.app.FragmentTransaction fragmentTrasaction =
                            ((MainActivity) c).getSupportFragmentManager().beginTransaction();
                    fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
                    fragmentTrasaction.commit();


                    // expandindo o fragmento
                    ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

                } else {

                    ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }


            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public int id;
        public TextView textViewNome;
        public TextView textViewDescricao;
        public CardView cardView;

        public MyViewHolder(final View itemView) {

            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_evento);
            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            textViewDescricao = (TextView) itemView.findViewById(R.id.textViewDescricao);




        }

    }

}


