package br.com.metting.www.likemeet.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.metting.www.likemeet.Adapters.AmigosAdapter;
import br.com.metting.www.likemeet.Adapters.AmigosHorizontalAdapter;
import br.com.metting.www.likemeet.Class.Amigo;
import br.com.metting.www.likemeet.R;


public class AddAmigosEventoFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    View view;
    AmigosHorizontalAdapter amgHorizontaAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // recycler view amigos
        view = inflater.inflate(R.layout.fragment_add_amigos_evento, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerlista);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        AmigosAdapter amgAdapter = new AmigosAdapter(getActivity() , this);
        recyclerView.setAdapter(amgAdapter);
        //amigos selecionados
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerlista2);
        //  recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(llm2);
        amgHorizontaAdapter = new AmigosHorizontalAdapter(getActivity());
        recyclerView2.setAdapter(amgHorizontaAdapter);
        return view;
    }

    public AmigosHorizontalAdapter getAmgHorizontalAdapter() {
        return amgHorizontaAdapter;
    }


}
