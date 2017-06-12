package br.com.metting.www.likemeet.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.metting.www.likemeet.Adapters.HistoricoAdapter;
import br.com.metting.www.likemeet.Class.HistoricoEventos;
import br.com.metting.www.likemeet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistoricoGeral extends Fragment {
    private RecyclerView recyclerView;
    private FragmentManager fm;
    private ArrayList<HistoricoEventos> listaHistoricos;

    public FragmentHistoricoGeral(FragmentManager fm, ArrayList<HistoricoEventos> listaHistoricos) {
        // Required empty public constructor
        this.listaHistoricos = listaHistoricos;
        this.fm = fm;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historico_geral, container, false);
        //iniciando a recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerlistaHistorico2);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        HistoricoAdapter adapter = new HistoricoAdapter(getActivity(), listaHistoricos, fm);
        //cache do recycler view
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        return view;
    }

}
