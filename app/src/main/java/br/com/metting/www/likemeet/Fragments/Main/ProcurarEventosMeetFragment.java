package br.com.metting.www.likemeet.Fragments.Main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import br.com.metting.www.likemeet.Activitys.MainActivity;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Maps.MapsFragmentProcurarEventos;
import br.com.metting.www.likemeet.R;

import static android.content.Context.LOCATION_SERVICE;


public class ProcurarEventosMeetFragment extends Fragment {

    View view;
    private FragmentManager fragmentManager;
    private static SlidingUpPanelLayout slider;
    public MapsFragmentProcurarEventos mapsFragmentProcurarEventos;
    private LocationManager locationManager;
    FragmentTransaction fragmentTransaction;
    public static ProgressDialog dialog;


    // The minimum distance to change updates in metters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 50; // 10
    // metters

    // The minimum time beetwen updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1

    // minute
    private void getMinhaLocalizacao() {
        Log.d(getClass().getName(), "getMinhaLocalizacao");

        if (!(ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)) {
            Log.d(getClass().getName(), "Permissao concedida");

            dialog = ProgressDialog.show(getContext(), "",
                    "Preparando o mapa, Aguarde um instante...", true);
            dialog.show();

            if (MainActivity.getLocal() != null) {
                Log.d(getClass().getName(), "location != null");
                abrirFragmentos();
                return;
            }
            locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_LOW);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String provider = locationManager.getBestProvider(criteria, false);

            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                Log.d(getClass().getName(), "location != null");
                MainActivity.setLocal(new LatLng(location.getLatitude(), location.getLongitude()));
                abrirFragmentos();
                return;
            }
            Log.d(getClass().getName(), "local == null");
            locationManager.requestSingleUpdate(criteria, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(getClass().getName(), "onLocationChanged");
                    MainActivity.setLocal(new LatLng(location.getLatitude(), location.getLongitude()));
                    Log.d(getClass().getName(), MainActivity.getLocal().toString());
                    abrirFragmentos();
                }

                @Override
                public void onProviderDisabled(String provider) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onProviderEnabled(String provider) {
                    // TODO Auto-generated method stub
                    Log.d(getClass().getSimpleName(), "Provider ativado");


                }

                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                    // TODO Auto-generated method stub
                    Log.d(getClass().getSimpleName(), "Provider alterado");


                }
            }, null);
        } else {
            Log.d(getClass().getName(), "Permissao nao concedida");
        }
    }

    private void abrirFragmentos() {
        try {
            if (getActivity() != null) {
                mapsFragmentProcurarEventos = new MapsFragmentProcurarEventos(MainActivity.getLocal());
                fragmentManager = getActivity().getSupportFragmentManager(); // getChildFragmentManager
                fragmentTransaction = fragmentManager.beginTransaction();
                //  fragmentTransaction.add(R.id.layoutMaps, mapsFragmentProcurarEventos, "MapsFragmentProcurarEventos");
                fragmentTransaction.replace(R.id.layoutMaps, mapsFragmentProcurarEventos);
                fragmentTransaction.commitAllowingStateLoss();

                Fragment fr = new PrePesquisaFragment();
                FragmentManager fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.LayoutBaixoMap, fr);
                fragmentTransaction.commit();
            } else {
                Log.d(getClass().getName(), " activity null em abrir fragmentos");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_procurar_eventos_meet, container, false);
        // opçoes do sliding layout
        slider = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);

        // para usar um fragmento dentro de um fragmento e necessario usar o getchildfragment manager
        getMinhaLocalizacao();

        slider.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                try {
                    if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        slider.setTouchEnabled(false);

                        // verifica qual fragment esta aberto no layout de baixo
                        Fragment f = getActivity().getSupportFragmentManager().findFragmentById(R.id.LayoutBaixoMap);
                        if (f instanceof ListaEventoFragment) {
                            ListaEventoFragment.imageViewSeta.setImageResource(R.drawable.ic_seta_baixo);
                            ListaEventoFragment.textViewExpandir.setText("Toque para fechar");
                            return;
                        }
                        return;
                    }
                    if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                        slider.setTouchEnabled(true);

                        // verifica qual fragment esta aberto no layout de baixo
                        Fragment f = getActivity().getSupportFragmentManager().findFragmentById(R.id.LayoutBaixoMap);
                        if (f instanceof ListaEventoFragment) {
                            ListaEventoFragment.imageViewSeta.setImageResource(R.drawable.ic_seta_cima);
                            ListaEventoFragment.textViewExpandir.setText("Toque para abrir");
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
