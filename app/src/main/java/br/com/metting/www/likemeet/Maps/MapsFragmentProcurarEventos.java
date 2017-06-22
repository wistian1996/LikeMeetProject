package br.com.metting.www.likemeet.Maps;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import br.com.metting.www.likemeet.Activitys.MainActivity;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Fragments.Main.InfoEventoMapFragment;
import br.com.metting.www.likemeet.Fragments.Main.ListaEventoFragment;
import br.com.metting.www.likemeet.Fragments.Main.PrePesquisaFragment;
import br.com.metting.www.likemeet.Fragments.Main.ProcurarEventosMeetFragment;
import br.com.metting.www.likemeet.R;

public class MapsFragmentProcurarEventos extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback {

    private static GoogleMap mMap;
    private LatLng local;
    private static ArrayList<Marker> listaMarker = new ArrayList<>();
    private String TAG;

    public MapsFragmentProcurarEventos(LatLng local) {
        this.local = local;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MainActivity.toolbar.setSubtitle("Preparando mapa...");
        TAG = getClass().getSimpleName();
        getMapAsync(this);
    }

    //executar alguma acao ao clicar em alguma area do meu mapa
    //sera executado quando o mapa estiver pronto
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d(getClass().getSimpleName(), "Mapa pronto");
        try {
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);
            marcarPontos(Meet.getListaEventos());
            mMap.setOnMapLoadedCallback(this);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(local, 10f), 1000, null);

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    ProcurarEventosMeetFragment.abrirSlider();
                }
            });

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    chamarFragmentListaEventos(null);
                }
            });


        } catch (SecurityException e) {
            Log.d(getClass().getName(), e.getMessage());
            e.printStackTrace();
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {// Inflate the layout for this fragment

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14f), 1500, null);
                chamarFragmentListaEventos(Evento.getListaEventosLatLong(marker.getPosition()));
                //Build camera position
                marker.showInfoWindow();
                // Se ela retorna falso, então o comportamento padrão irá ocorrer em adição ao seu comportamento personalizado
                return true;
            }
        });

        MainActivity.toolbar.setSubtitle("Procurar novos eventos");
        ProcurarEventosMeetFragment.dialog.cancel();
        // criarRaio();
    }

    private void abrirFragmentoCategorias() {
        try {
            Fragment fragment = new PrePesquisaFragment();
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
            fragmentTrasaction.commit();
            MapsFragmentProcurarEventos.marcarPontos(Meet.getListaEventos());
            MainActivity.toolbar.setSubtitle("Encontrar novos eventos");
            ProcurarEventosMeetFragment.fecharSlider();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(getClass().getSimpleName(), "Error abrir fragmento categorias");
        }


    }

    private void chamarFragmentListaEventos(final ArrayList<Evento> lista) {
        // se a lista  for nula , retornara todos eventos
        if (lista == null) {
            // configurando o sliderUp
            abrirFragmentoCategorias();
            return;
        } else {
            // ser for size 1 quer diser que so ha 1 evento naquela localidade , entao abrimos a tela de ir para o evento diretamente
            if (lista.size() == 1) {

                Evento control = new Evento();
                Fragment fragment = new InfoEventoMapFragment(control.getEvento(lista.get(0).getId()));
                android.support.v4.app.FragmentTransaction fragmentTrasaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
                fragmentTrasaction.commit();

                return;

            }
            Fragment fragment = new ListaEventoFragment(lista);
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
            fragmentTrasaction.commit();


            // slider Up
            ProcurarEventosMeetFragment.abrirSlider();
        }


    }

    public static void marcarPontos(ArrayList<Evento> listaEventos) {

        MarkerOptions marker = new MarkerOptions();
        Marker m = null;
        listaMarker.clear();
        mMap.clear();
        //    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marcar_map));

        for (Evento listaEvento : listaEventos) {
            String[] latlong = listaEvento.getLocal().split(",");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);

            ArrayList<Evento> list = Evento.getListaEventosLatLong(new LatLng(latitude, longitude));
            //quer diser que há varios eventos na mesma localizacao entao marca de amarelo
            if (list.size() > 1) {
                LatLng location = new LatLng(latitude, longitude);
                marker.position(location);
                marker.title("Varios eventos");
                marker.snippet("Há varios eventos por aqui!");
                marker.alpha(0.8f);
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                m = mMap.addMarker(marker);
            } else {
                Date data = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String data1 = dateFormat.format(data);
                String data2 = dateFormat.format(listaEvento.getDataEvento());
                if (data1.equals(data2)) {
                    // quer diser que o evento é naquele dia entao o marcador é verde
                    LatLng location = new LatLng(latitude, longitude);
                    marker.position(location);
                    marker.title(listaEvento.getNome());
                    marker.snippet(listaEvento.getDescricao());
                    marker.alpha(0.8f);
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                    m = mMap.addMarker(marker);
                }
                if (!data1.equals(data2)) {
                    // quer diser que o evento nao é naquele dia entao o marcador é azul
                    LatLng location = new LatLng(latitude, longitude);
                    marker.position(location);
                    marker.title(listaEvento.getNome());
                    marker.snippet(listaEvento.getDescricao());
                    marker.alpha(0.8f);
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                    m = mMap.addMarker(marker);

                }
            }
            // adicionando  o marcador a lista de marcacoes
            if (m != null) {
                listaMarker.add(m);
            }

        }


    }

    public static void descarmarMarker() {

        for (Marker lista : listaMarker
                ) {
            if (lista.isInfoWindowShown()) {
                lista.hideInfoWindow();
                break;
            }
        }
    }

    public static void marcarMarker(LatLng latLng) {
        for (Marker lista : listaMarker
                ) {
            if (latLng.toString().equals(lista.getPosition().toString())) {
                Log.d("local", "local1: " + latLng.toString());
                Log.d("local", "local2: " + lista.getPosition().toString());
                lista.showInfoWindow();
                //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10), 1500, null);
                return;
            }
        }
    }

    public static GoogleMap getmMap() {
        return mMap;
    }

    public static ArrayList<Marker> getListaMarker() {
        return listaMarker;
    }


    @Override
    public void onMapLoaded() {
        Log.d(getClass().getSimpleName(), "Mapa carregado");

    }

}
