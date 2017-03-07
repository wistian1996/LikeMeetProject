package br.com.metting.www.likemeet.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.metting.www.likemeet.Class.Amigo;
import br.com.metting.www.likemeet.R;

/**
 * Created by wisti on 04/12/2016.
 */
public class AmigosHorizontalAdapter extends RecyclerView.Adapter<AmigosHorizontalAdapter.MyViewHolder> {

    ArrayList<Amigo> list;
    private LayoutInflater mLayoutInflater;
    Context c;

    public AmigosHorizontalAdapter(Context c) {
        this.c = c;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = new ArrayList<>();
    }

    @Override
    public AmigosHorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_view_amigos_selecionados, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final AmigosHorizontalAdapter.MyViewHolder holder, int position) {
        holder.textViewNome.setText(list.get(position).getNome());
        holder.setId(list.get(position).getId());

    }

    public void atualizar(int id){

        Amigo control = new Amigo();
        for (Amigo listAmigos: list
             ) {
            if (listAmigos.getId() == id){
                remove(id);
                return;
            }
        }
        adicionar(control.getAmigoId(id));
    }

    public void adicionar(Amigo amigo) {
        list.add(amigo);
        notifyDataSetChanged();
        notifyDataSetChanged();
    }

    public void remove(int id) {
        for (Amigo listaAmigo : list) {
            if (listaAmigo.getId() == id) {
                list.remove(listaAmigo);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }

    }

    public void setList(ArrayList<Amigo> list) {
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public int id;
        public TextView textViewNome;
        public TextView textViewTel;
        public CardView cardView;

        public MyViewHolder(View itemView) {

            super(itemView);
            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
