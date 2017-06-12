package br.com.metting.www.likemeet.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.metting.www.likemeet.Activitys.MainActivity;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Fragments.Main.InfoEventoMapFragment;
import br.com.metting.www.likemeet.Fragments.Main.ProcurarEventosMeetFragment;
import br.com.metting.www.likemeet.R;

/**
 * Created by wisti on 06/03/2017.
 */

//  extends RecyclerView.Adapter<MeusEventosAdapter.MyViewHolder>
public class MeusEventosAdapter extends RecyclerView.Adapter<MeusEventosAdapter.MyViewHolder>{
    ArrayList<Evento> lEvento;
    private LayoutInflater mLayoutInflater;
    Context c;
    FragmentManager fm;

    public MeusEventosAdapter(Context c, ArrayList<Evento> lEvento , FragmentManager fm ) {

        this.mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.c = c;
        this.lEvento = lEvento;
        this.fm = fm;

    }

    @Override
    public MeusEventosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_view_meus_eventos, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MeusEventosAdapter.MyViewHolder holder, final int position) {
        // acoes para os botoes
        holder.id = lEvento.get(position).getId();
        holder.textViewDescricao.setText(lEvento.get(position).getDescricao());
        holder.textViewNome.setText(lEvento.get(position).getNome());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatHora = new SimpleDateFormat("HH:mm:ss");

        String data = dateFormat.format(lEvento.get(position).getDataEvento());
        String hora = dateFormatHora.format(lEvento.get(position).getDataEvento());
        holder.textViewData.setText(data);
        holder.textViewHorario.setText(hora);

        holder.cardViewMeusEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*
                // abrindo a tela de informacoes
                Fragment fragment = new InfoEventoMapFragment(lEvento.get(position), 1);

                android.support.v4.app.FragmentTransaction fragmentTrasaction =
                        ((MainActivity) c).getSupportFragmentManager().beginTransaction();
                fragmentTrasaction.replace(R.id.layoutPrincipal, fragment).addToBackStack(null).commit();
*/
                Fragment fragment = new InfoEventoMapFragment(lEvento.get(position), 1);
              fm.beginTransaction().addToBackStack(null).replace(R.id.layoutPrincipal , fragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (lEvento != null) {
            return lEvento.size();
        } else {
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public int id;
        public TextView textViewNome;
        public TextView textViewDescricao;
        public TextView textViewData;
        public TextView textViewHorario;
        public CardView cardViewMeusEventos;


        public MyViewHolder(final View itemView) {
            super(itemView);
            cardViewMeusEventos = (CardView) itemView.findViewById(R.id.card_view_meus_eventos);
            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            textViewDescricao = (TextView) itemView.findViewById(R.id.textViewDescricao);
            textViewData = (TextView) itemView.findViewById(R.id.textViewData);
            textViewHorario = (TextView) itemView.findViewById(R.id.textViewData);

        }

    }


}

