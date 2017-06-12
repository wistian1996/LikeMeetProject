package br.com.metting.www.likemeet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.callback.ImageOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import br.com.metting.www.likemeet.Activitys.ActivityPerfil;
import br.com.metting.www.likemeet.Activitys.CadastroEventoActivity;
import br.com.metting.www.likemeet.Activitys.MainActivity;
import br.com.metting.www.likemeet.Activitys.VizualizarEventoActivity;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.HistoricoEventos;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.Fragments.Main.InfoEventoMapFragment;
import br.com.metting.www.likemeet.R;


/**
 * Created by wisti on 18/05/2017.
 */

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<HistoricoEventos> lHistorico;
    FragmentManager fm;
    Context c;

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

    @Override
    public void onBindViewHolder(HistoricoAdapter.MyViewHolder holder, final int position) {
        holder.idEvento = lHistorico.get(position).getIdEvento();
        holder.idUsuario = lHistorico.get(position).getIdUsuario();
        holder.tipoHistorico = lHistorico.get(position).getTipo();
        holder.dataHistorico = lHistorico.get(position).getData();

        final Evento e = Evento.getEvento(holder.idEvento);
        Usuario u = Meet.getUsuario(holder.idUsuario);


        // editando a descricao
        String descricao = "";

        if (lHistorico.get(position).getTipo().equals("criou")) {
            descricao = "<font color= #008e63>" + u.getNome() + "</font>"
                    + " criou o evento <font color=#008e63>" + e.getNome() + "</font>" + ".";
        }
        if (lHistorico.get(position).getTipo().equals("cancelou")) {
            descricao = "<font color=#008e63>" + u.getNome() + "</font>"
                    + " cancelou o evento <font color=#008e63>" + e.getNome() + "</font>" + ".";
        }
        if (lHistorico.get(position).getTipo().equals("foi")) {
            descricao = "<font color= #008e63>" + u.getNome() + "</font>"
                    + " participou do evento <font color=#008e63>" + e.getNome() + "</font>" + ".";
        }
        if (lHistorico.get(position).getTipo().equals("vai")) {
            descricao = "<font color= #008e63>" + u.getNome() + "</font>"
                    + " marcou presença no evento <font color=#008e63>" + e.getNome() + "</font>" + ".";
        }

        holder.descricao.setText(Html.fromHtml(descricao), TextView.BufferType.SPANNABLE);
        holder.endereco.setText(e.getEndereco());

        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        java.util.Date dataUtil = new java.util.Date(holder.dataHistorico.getTime());

        String data = formatter1.format(dataUtil);
        String hora = formatter2.format(dataUtil);

        holder.TextViewDataHistorico.setText(data + " às " + hora);
        Log.d(getClass().getSimpleName(), "Entrou BindView");


        holder.imageOptions = new ImageOptions();
        holder.imageOptions.memCache = true;
        holder.imageOptions.fileCache = true;
  //      holder.imageOptions.fallback = R.drawable.iconperfill;


        //utilizado para que eu possa fazer uma conexao externa com a internet
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //pegando imagem da internet
                String getMapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=18&size=560x240&markers=size:mid|color:red|"
                        + e.getLocal()
                        + "&sensor=false";
            //    url = new URL(getMapURL);
                holder.aQuery.id(holder.imagemMapa.getId()).progress(holder.progressBarMapa).image(getMapURL , holder.imageOptions);
                //  Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                // holder.aQuery.id(R.id.ImageViewMap).progress(R.layout.layout_progress_bar).image(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
                // holder.imagemMapa.setImageBitmap(bmp);
                Log.d(getClass().getSimpleName(), "TRY URL");
        }
// acoes para os botoes

        holder.cardViewMeusEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(getClass().getName(), "ON click meuEVENTO");
                Intent intent = new Intent(c, VizualizarEventoActivity.class);
                Bundle b = new Bundle();
                b.putInt("idEvento", e.getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                c.startActivity(intent);
            }
        });
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
        public RelativeLayout botaoEuVou;
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


        public MyViewHolder(final View itemView) {
            super(itemView);
            botaoEuVou = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutBotaoEuVou);
            descricao = (TextView) itemView.findViewById(R.id.textViewDescricao);
            TextViewDataHistorico = (TextView) itemView.findViewById(R.id.textViewDataHistorico);
            endereco = (TextView) itemView.findViewById(R.id.textViewEndereco);
            imagemMapa = (ImageView) itemView.findViewById(R.id.ImageViewMap);
            cardViewMeusEventos = (CardView) itemView.findViewById(R.id.card_view_meus_eventos);
            aQuery = new AQuery(itemView);
            progressBarMapa = (ProgressBar) itemView.findViewById(R.id.progressBarMapa);
        }

    }
    }
