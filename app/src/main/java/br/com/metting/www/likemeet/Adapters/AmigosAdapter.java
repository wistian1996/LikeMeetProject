package br.com.metting.www.likemeet.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.com.metting.www.likemeet.Class.Amigo;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Fragments.AddAmigosEventoFragment;
import br.com.metting.www.likemeet.R;

/**
 * Created by wisti on 03/12/2016.
 */
public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.MyViewHolder> {
    ArrayList<Amigo> list;
    ArrayList<Amigo> listAdd;
    Amigo control = new Amigo();
    private LayoutInflater mLayoutInflater;
    AddAmigosEventoFragment fragment;


    public AmigosAdapter(Context c, AddAmigosEventoFragment fragment) {
        this.fragment = fragment;
        Amigo control = new Amigo();
        list = control.getLista();
        listAdd = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public AmigosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_view_lista_amigos_add, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(final AmigosAdapter.MyViewHolder holder, int position) {
        // acoes para os botoes
        holder.textViewNome.setText(list.get(position).getNome());
        holder.textViewTel.setText(list.get(position).getTel());
        holder.setId(list.get(position).getId());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fragment.getAmgHorizontalAdapter().atualizar(holder.getId());
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.getAmgHorizontalAdapter().atualizar(holder.getId());
                if (holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);
                }else{
                    holder.checkBox.setChecked(true);
                }
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



        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
