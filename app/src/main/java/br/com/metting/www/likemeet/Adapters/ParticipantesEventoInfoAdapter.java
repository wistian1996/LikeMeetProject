package br.com.metting.www.likemeet.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.metting.www.likemeet.Class.Amigo;
import br.com.metting.www.likemeet.R;

/**
 * Created by wisti on 03/12/2016.
 */
public class ParticipantesEventoInfoAdapter extends RecyclerView.Adapter<ParticipantesEventoInfoAdapter.MyViewHolder> {
    ArrayList<Amigo> list;
    ArrayList<Amigo> listAdd;
    Amigo control = new Amigo();
    private LayoutInflater mLayoutInflater;


    public ParticipantesEventoInfoAdapter(Context c, ArrayList<Amigo> list) {
        this.list = list;
        listAdd = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ParticipantesEventoInfoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_view_lista_amigos_add, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(final ParticipantesEventoInfoAdapter.MyViewHolder holder, int position) {
        // acoes para os botoes
        holder.textViewNome.setText(list.get(position).getNome());
        holder.textViewTel.setText(list.get(position).getTel());
        holder.setId(list.get(position).getId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public int id;
        public TextView textViewNome;
        public TextView textViewTel;
        private ImageView imageViewPerfil;
        private CardView cardView;
        private CheckBox checkBox;

        public MyViewHolder(final View itemView) {

            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            textViewTel = (TextView) itemView.findViewById(R.id.textViewTel);
            imageViewPerfil = (ImageView) itemView.findViewById(R.id.imageViewFoto);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

            checkBox.setVisibility(View.INVISIBLE);

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
