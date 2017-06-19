package br.com.metting.www.likemeet.Fragments.Main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.metting.www.likemeet.Adapters.HistoricoImagensAdapter;
import br.com.metting.www.likemeet.Adapters.ParticipantesEventoInfoAdapter;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.R;

public class infoEventoMap2Fragment extends Fragment {
    private View view;
    private Evento evento;
    private RecyclerView recyclerView;

    public infoEventoMap2Fragment(Evento evento) {
        this.evento = evento;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info_evento_map2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_publicacoes_imagens);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setNestedScrollingEnabled(false);
        HistoricoImagensAdapter adapter = new HistoricoImagensAdapter(getActivity(), Meet.getListaPublicacoes(), getFragmentManager());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
