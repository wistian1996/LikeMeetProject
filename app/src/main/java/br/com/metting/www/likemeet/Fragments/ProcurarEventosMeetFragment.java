package br.com.metting.www.likemeet.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Maps.MapsFragmentProcurarEventos;
import br.com.metting.www.likemeet.R;


public class ProcurarEventosMeetFragment extends Fragment {

    View view;
    private FragmentManager fragmentManager;
    public static SlidingUpPanelLayout slider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_procurar_eventos_meet, container, false);
        // opçoes do sliding layout
        slider = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
         // para usar um fragmento dentro de um fragmento e necessario usar o getchildfragment manager
        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layoutMaps, new MapsFragmentProcurarEventos(), "MapsFragmentProcurarEventos");
        fragmentTransaction.commitAllowingStateLoss();


        Fragment fr = new ListaEventoFragment(Meet.getListaEventos());
        FragmentManager fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.LayoutBaixoMap, fr);
        fragmentTransaction.commit();

        return view;
    }

}
