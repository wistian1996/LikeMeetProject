package br.com.metting.www.likemeet.Fragments.Main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.metting.www.likemeet.Adapters.EventosProcurarAdapter;
import br.com.metting.www.likemeet.Adapters.ParticipantesEventoInfoAdapter;
import br.com.metting.www.likemeet.Class.Amigo;
import br.com.metting.www.likemeet.R;

public class listaParticipantesEventoInfoFragment extends Fragment {
   private View view;
   private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lista_participantes_evento_info, container, false);

        Amigo amg = new Amigo();
        ArrayList<Amigo> list = amg.getLista();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerlistaParticipantesEventoInfo);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        ParticipantesEventoInfoAdapter adapter = new ParticipantesEventoInfoAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);


        return view;
    }


}
