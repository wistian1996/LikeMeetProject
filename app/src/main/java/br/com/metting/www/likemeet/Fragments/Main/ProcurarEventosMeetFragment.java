package br.com.metting.www.likemeet.Fragments.Main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Maps.MapsFragmentProcurarEventos;
import br.com.metting.www.likemeet.R;


public class ProcurarEventosMeetFragment extends Fragment {

    View view;
    private FragmentManager fragmentManager;
    private static SlidingUpPanelLayout slider;


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


        Fragment fr = new PrePesquisaFragment();
        FragmentManager fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.LayoutBaixoMap, fr);
        fragmentTransaction.commit();

        slider.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {

                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    slider.setTouchEnabled(false);
                    return;
                }
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slider.setTouchEnabled(true);
                }

            }
        });


        return view;
    }


    public static void abrirSlider() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                slider.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        }, 100);


    }

    public static void fecharSlider() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        }, 100);
    }

    public static SlidingUpPanelLayout getSlider() {
        return slider;
    }
}
