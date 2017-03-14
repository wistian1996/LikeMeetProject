package br.com.metting.www.likemeet.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.metting.www.likemeet.Activitys.MainActivity;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Fragments.Main.InfoEventoMapFragment;
import br.com.metting.www.likemeet.Fragments.Main.ProcurarEventosMeetFragment;
import br.com.metting.www.likemeet.Maps.MapsFragmentProcurarEventos;
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
        holder.data = list.get(position).getDataEvento();
        holder.textViewNome.setText(list.get(position).getNome());
        holder.textViewDescricao.setText(list.get(position).getDescricao());
        //acao ao clicar no card view

        Date data = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String data1 = dateFormat.format(data);
        String data2 = dateFormat.format(list.get(position).getDataEvento());
        if (data1.equals(data2)) {
            // quer diser que o evento e hoje
            holder.imageView.setImageResource(R.drawable.ic_vector_marcador_verde);
        } else {
            // o evento e outro dia
            holder.imageView.setImageResource(R.drawable.ic_vector_marcador_azul);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("adapter", "action");
                MainActivity.getSearchView().onActionViewCollapsed();

                //handler da uma pausa no sistema por 0.3 segundos para que de tempo de o teclado sumir antes que desca o slider .
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (!ProcurarEventosMeetFragment.getSlider().getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {

                            // mostrando a janela de informacoes do marker
                            String[] latlong = list.get(position).getLocal().split(",");
                            double latitude = Double.parseDouble(latlong[0]);
                            double longitude = Double.parseDouble(latlong[1]);
                            LatLng local = new LatLng(latitude, longitude);

                            MapsFragmentProcurarEventos.marcarMarker(local);


                            // abrindo a tela de informacoes
                            Fragment fragment = new InfoEventoMapFragment(list.get(position));

                            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                                    ((MainActivity) c).getSupportFragmentManager().beginTransaction();
                            fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
                            fragmentTrasaction.commit();
                            ProcurarEventosMeetFragment.fecharSlider();


                        } else {
                            ProcurarEventosMeetFragment.abrirSlider();
                        }
                    }
                }, 300);
                //verifico se  o sliderrup esta expanded
            }
        });


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public int id;
        public Date data;
        public TextView textViewNome;
        public TextView textViewDescricao;
        public CardView cardView;
        public ImageView imageView;

        public MyViewHolder(final View itemView) {

            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_evento);
            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            textViewDescricao = (TextView) itemView.findViewById(R.id.textViewDescricao);
            imageView = (ImageView) itemView.findViewById(R.id.imageView7);


        }

    }

}


