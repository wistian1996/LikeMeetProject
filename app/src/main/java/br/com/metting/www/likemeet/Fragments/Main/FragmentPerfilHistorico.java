package br.com.metting.www.likemeet.Fragments.Main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;

import br.com.metting.www.likemeet.Adapters.HistoricoAdapter;
import br.com.metting.www.likemeet.Class.HistoricoEventos;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.Fragments.Main.PrePesquisaFragment;
import br.com.metting.www.likemeet.R;

public class FragmentPerfilHistorico extends Fragment {
    private RecyclerView recyclerView;
    Usuario usuario;
    private FragmentManager fm;
    private ArrayList<HistoricoEventos> listaHistoricos;
    private TextView textViewStatus;
    private TextView textViewIdadeCidade;

    public FragmentPerfilHistorico(FragmentManager fm, Usuario usuario) {
        this.fm = fm;
        this.usuario = usuario;
        //pegando os eventos relacionados ao historico do usuario
        listaHistoricos = Meet.getHistoricoEventos(usuario.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
        textViewIdadeCidade = (TextView) view.findViewById(R.id.textViewIdadeCidade);

        textViewIdadeCidade.setText(usuario.getEndereco());
        textViewStatus.setText("'" + usuario.getStatus() + "'");



        //iniciando a recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerlistaHistorico);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        HistoricoAdapter adapter = new HistoricoAdapter(getActivity(), listaHistoricos, fm);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
