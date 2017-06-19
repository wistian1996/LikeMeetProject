package br.com.metting.www.likemeet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

import java.util.ArrayList;

import br.com.metting.www.likemeet.Activitys.ActivityPerfil;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.PublicacaoImagem;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.Control.DataControl;
import br.com.metting.www.likemeet.Control.ImagemControl;
import br.com.metting.www.likemeet.Control.PublicacaoControl;
import br.com.metting.www.likemeet.R;


/**
 * Created by wisti on 18/05/2017.
 */

public class HistoricoImagensAdapter extends RecyclerView.Adapter<HistoricoImagensAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<PublicacaoImagem> listaPublicacao;
    FragmentManager fm;
    Context c;
    private Usuario u;
    private Evento e;

    public HistoricoImagensAdapter(Context c, ArrayList<PublicacaoImagem> lPublicacao, FragmentManager fm) {
        this.c = c;
        this.fm = fm;
        this.listaPublicacao = lPublicacao;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public HistoricoImagensAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_view_historico_imagens_publicadas, parent, false);
        HistoricoImagensAdapter.MyViewHolder mvh = new HistoricoImagensAdapter.MyViewHolder(v);
        return mvh;
    }


    @Override
    public void onBindViewHolder(final HistoricoImagensAdapter.MyViewHolder holder, final int position) {
        final Usuario u = Usuario.getUsuario(listaPublicacao.get(position).getIdUsuario());

        ImagemControl.setImagemCircular(u.getFoto(), holder.imageViewFotoPerfil, c);
        ImagemControl.carregarImagemComProgress(listaPublicacao.get(position).getURL(), c, holder.imageViewPublicacao, holder.progressBarImagem);
        holder.descricao.setText(listaPublicacao.get(position).getDescricao());
        holder.textViewQtdVizualizacao.setText(listaPublicacao.get(position).getQtdVizualizacoes() + " vizualizações");
        holder.textViewDataPublicacao.setText(DataControl.getDataPublicacaoString(listaPublicacao.get(position).getDataPublicacao()));
        holder.textViewNome.setText(u.getNome());
        holder.imageViewPublicacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicacaoControl.carregarPublicacao(listaPublicacao.get(position).getId(), 0, 0, c);
            }
        });

        holder.imageViewFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, ActivityPerfil.class);
                Bundle b = new Bundle();
                //inserindo o ID do usuario que esta logado e passando id como parametro
                b.putInt("idUsuario", u.getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listaPublicacao != null) {
            return listaPublicacao.size();
        } else {
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView descricao;
        public TextView textViewDataPublicacao;
        public TextView textViewQtdVizualizacao;
        public TextView textViewNome;
        public ProgressBar progressBarImagem;
        public ImageView imageViewFotoPerfil;
        public ImageView imageViewPublicacao;
        public View view;
        public AQuery aQuery;
        public ImageOptions imageOptions;
        public CardView cardViewHistoricoImagem;


        public MyViewHolder(final View itemView) {
            super(itemView);
            descricao = (TextView) itemView.findViewById(R.id.textViewDescricao);
            textViewDataPublicacao = (TextView) itemView.findViewById(R.id.textViewDataPublicacao);
            textViewQtdVizualizacao = (TextView) itemView.findViewById(R.id.textViewQtdVizualizacoes);
            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            imageViewFotoPerfil = (ImageView) itemView.findViewById(R.id.imageViewFotoPerfil);
            imageViewPublicacao = (ImageView) itemView.findViewById(R.id.ImageViewPublicacao);
            aQuery = new AQuery(itemView);
            progressBarImagem = (ProgressBar) itemView.findViewById(R.id.progressBarImagem);
            this.view = itemView;
            cardViewHistoricoImagem = (CardView) itemView.findViewById(R.id.card_view_historico_imagem);

        }

    }
}
