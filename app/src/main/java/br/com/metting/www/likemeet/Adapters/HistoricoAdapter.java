package br.com.metting.www.likemeet.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

import java.sql.Date;
import java.util.ArrayList;

import br.com.metting.www.likemeet.Activitys.ActivityPerfil;
import br.com.metting.www.likemeet.Activitys.VizualizarEventoActivity;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.HistoricoEventos;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Class.PublicacaoImagem;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.Control.DataControl;
import br.com.metting.www.likemeet.Control.ImagemControl;
import br.com.metting.www.likemeet.Control.PublicacaoControl;
import br.com.metting.www.likemeet.R;


/**
 * Created by wisti on 18/05/2017.
 */

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<HistoricoEventos> lHistorico;
    FragmentManager fm;
    Context c;
    private Usuario u;
    private Evento e;

    public HistoricoAdapter(Context c, ArrayList<HistoricoEventos> lHistorico, FragmentManager fm) {
        this.c = c;
        this.fm = fm;
        this.lHistorico = lHistorico;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public HistoricoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_view_historico, parent, false);
        HistoricoAdapter.MyViewHolder mvh = new HistoricoAdapter.MyViewHolder(v);
        return mvh;
    }

    private String setarDescricao(int position) {
        // editando a descricao
        String descricao = "";

        if (lHistorico.get(position).getTipo().equals("criou")) {
            descricao = u.getNome()
                    + " <font color=#008e63> criou </font> o evento <font color=#008e63> " + e.getNome() + "</font>" + ".";
        }
        if (lHistorico.get(position).getTipo().equals("cancelou")) {
            descricao = u.getNome()
                    + "<font color=#00FF0004> cancelou  </font> a participação no evento <font color=#008e63>" + e.getNome() + "</font>" + ".";
        }
        if (lHistorico.get(position).getTipo().equals("foi")) {
            descricao = u.getNome()
                    + " <font color=#000000> participou do evento </font> <font color=#008e63>" + e.getNome() + "</font>" + ".";
        }
        if (lHistorico.get(position).getTipo().equals("vai")) {
            descricao = u.getNome()
                    + " <font color=#000000> vai ao evento </font>  <font color=#008e63>" + e.getNome() + "</font>" + ".";
        }
        return descricao;
    }

    @Override
    public void onBindViewHolder(final HistoricoAdapter.MyViewHolder holder, final int position) {


        e = Evento.getEvento(lHistorico.get(position).getIdEvento());
        u = Meet.getUsuario(lHistorico.get(position).getIdUsuario());
        ArrayList<PublicacaoImagem> listaFotos = Meet.getListaPublicacoes(e.getId());

        holder.idEvento = e.getId();
        holder.idUsuario = u.getId();
        holder.tipoHistorico = lHistorico.get(position).getTipo();
        holder.dataHistorico = lHistorico.get(position).getData();
        holder.descricao.setText(Html.fromHtml(setarDescricao(position)), TextView.BufferType.SPANNABLE);
        holder.endereco.setText(e.getEndereco());

        //set imagem
        ImagemControl.setImagemCircular(u.getFoto(), holder.imageViewFotoPerfil, holder.view.getContext());

        holder.TextViewDataHistorico.setText(DataControl.getDataPublicacaoString(lHistorico.get(position).getData()));
        holder.imageOptions = new ImageOptions();
        holder.imageOptions.memCache = true;
        holder.imageOptions.memCache = true;
        holder.imageOptions.fileCache = true;
        //      holder.imageOptions.fallback = R.drawable.iconperfill;


        holder.textViewPublicacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFragmentoEvento(holder.view.getContext(), position);
            }
        });
        holder.imageViewFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, ActivityPerfil.class);
                Bundle b = new Bundle();
                //inserindo o ID do usuario que esta logado e passando id como parametro
                b.putInt("idUsuario", holder.idUsuario); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                c.startActivity(intent);
            }
        });


        holder.textViewPublicacoes.setText("Publicações (" + listaFotos.size() + ")");


        if (listaFotos.size() > 0) {
            setarImagens(holder.imageView1, holder.view.getContext(), listaFotos, 0);
            setarImagens(holder.imageView2, holder.view.getContext(), listaFotos, 1);
            setarImagens(holder.imageView3, holder.view.getContext(), listaFotos, 2);
        } else {
            holder.imageView1.setVisibility(View.GONE);
            holder.imageView2.setVisibility(View.GONE);
            holder.imageView3.setVisibility(View.GONE);
            holder.butonPublicacoes.setVisibility(View.GONE);
        }

        ImagemControl.carregarImagemMap(holder.aQuery, holder.imagemMapa, holder.progressBarMapa, holder.imageOptions, e);
        // acoes para os botoes

        holder.imagemMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Evento e = Evento.getEvento(lHistorico.get(position).getIdEvento());
                String[] latLng = e.getLocal().split(",");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + latLng[0] + ">,<" + latLng[1] + ">?q=<" + latLng[0] + ">,<" + latLng[1] + ">(" + e.getNome() + ")"));
                c.startActivity(intent);
            }
        });
        holder.cardViewMeusEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFragmentoEvento(holder.view.getContext(), position);
            }
        });
        holder.butonPublicacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFragmentoEvento(holder.view.getContext(), position);
            }
        });
    }

    private void abrirFragmentoEvento(Context c, int position) {
        Intent intent = new Intent(c, VizualizarEventoActivity.class);
        Bundle b = new Bundle();
        b.putInt("idEvento", lHistorico.get(position).getIdEvento());
        intent.putExtras(b); //Put your id to your next Intent
        c.startActivity(intent);
        ((Activity) c).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void setarImagens(final ImageView imageView, final Context c, final ArrayList<PublicacaoImagem> listaFotos, final int posicao) {

        if (listaFotos.size() > posicao && listaFotos.get(posicao) != null) {
            ImagemControl.setImagem(listaFotos.get(posicao).getURL(), imageView, c);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //obtendo o local da imagem na tela
                    int[] location = new int[2];
                    imageView.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    PublicacaoControl.carregarPublicacao(listaFotos.get(posicao).getId(), x, y, c);
                }
            });
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (lHistorico != null) {
            return lHistorico.size();
        } else {
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public int idEvento;
        public int idUsuario;
        public ImageView imagemMapa;
        public TextView descricao;
        public TextView endereco;
        public TextView TextViewDataHistorico;
        public Date dataHistorico;
        public String tipoHistorico;
        public CardView cardViewMeusEventos;
        public ImageOptions imageOptions;
        public AQuery aQuery;
        public ProgressBar progressBarMapa;
        public ImageView imageViewFotoPerfil;
        public ImageView imageView1;
        public ImageView imageView2;
        public ImageView imageView3;
        private TextView textViewPublicacoes;
        private Button butonPublicacoes;
        public View view;

        public MyViewHolder(final View itemView) {
            super(itemView);
            descricao = (TextView) itemView.findViewById(R.id.textViewDescricao);
            TextViewDataHistorico = (TextView) itemView.findViewById(R.id.textViewDataHistorico);
            textViewPublicacoes = (TextView) itemView.findViewById(R.id.textViewPublicacoes);
            endereco = (TextView) itemView.findViewById(R.id.textViewEndereco);
            imagemMapa = (ImageView) itemView.findViewById(R.id.ImageViewPublicacao);
            cardViewMeusEventos = (CardView) itemView.findViewById(R.id.card_view_meus_eventos);
            aQuery = new AQuery(itemView);
            butonPublicacoes = (Button) itemView.findViewById(R.id.ButtonPublicacoes);
            progressBarMapa = (ProgressBar) itemView.findViewById(R.id.progressBarMapa);
            imageViewFotoPerfil = (ImageView) itemView.findViewById(R.id.imageViewFotoPerfil);
            imageView1 = (ImageView) itemView.findViewById(R.id.imageView1);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);
            imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);
            this.view = itemView;
        }
    }
}
