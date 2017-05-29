package br.com.metting.www.likemeet.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import br.com.metting.www.likemeet.Adapters.HistoricoAdapter;
import br.com.metting.www.likemeet.Fragments.Main.PrePesquisaFragment;
import br.com.metting.www.likemeet.R;

public class FragmentHistorico extends Fragment {
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historico, container, false);
        Log.d(getClass().getName(), "Aberto Fragmento HistoricoEventos");

        //iniciando a recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerlistaHistorico);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        HistoricoAdapter adapter = new HistoricoAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

}
