package br.com.metting.www.likemeet.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.metting.www.likemeet.Activitys.ActivityPerfil;
import br.com.metting.www.likemeet.Control.ImagemControl;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.R;

/**
 * Created by wisti on 03/12/2016.
 */
public class ParticipantesEventoInfoAdapter extends RecyclerView.Adapter<ParticipantesEventoInfoAdapter.MyViewHolder> {
    ArrayList<Usuario> list;
    private LayoutInflater mLayoutInflater;


    public ParticipantesEventoInfoAdapter(Context c, ArrayList<Usuario> list) {
        this.list = list;
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
        holder.setId(list.get(position).getId());

        //setando imagem do perfil
        ImagemControl.setImagemCircular(list.get(position).getFoto(), holder.imageViewPerfil , holder.view.getContext());

        holder.textViewStatus.setText(list.get(position).getStatus());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.view.getContext(), ActivityPerfil.class);
                Bundle b = new Bundle();
                b.putInt("idUsuario", holder.getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                holder.view.getContext().startActivity(intent);
                ((Activity) holder.view.getContext()).overridePendingTransition(R.animator.zoom_in, R.anim.fade_out);

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
        private ImageView imageViewPerfil;
        private CardView cardView;
        private CheckBox checkBox;
        private TextView textViewStatus;
        private View view;

        public MyViewHolder(final View itemView) {

            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            imageViewPerfil = (ImageView) itemView.findViewById(R.id.imageViewFoto);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            textViewStatus = (TextView) itemView.findViewById(R.id.textViewStatus);
            this.view = itemView;


            checkBox.setVisibility(View.GONE);

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
