package br.com.metting.www.likemeet.Maps;


import android.app.ProgressDialog;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import br.com.metting.www.likemeet.Activitys.MainActivity;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.FirebaseUtils.FBEventoUtils;
import br.com.metting.www.likemeet.Fragments.Main.InfoEventoMapFragment;
import br.com.metting.www.likemeet.Fragments.Main.ListaEventoFragment;
import br.com.metting.www.likemeet.Fragments.Main.PrePesquisaFragment;
import br.com.metting.www.likemeet.Fragments.Main.ProcurarEventosMeetFragment;
import br.com.metting.www.likemeet.R;

public class MapsFragmentProcurarEventos extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLoadedCallback {

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
    public void onMapReady(GoogleMap googleMap) {
Log.d(getClass().getSimpleName(), "Mapa pronto");
        try {
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);
            marcarPontos(Meet.getListaEventos());

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(local, 10f));

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    ArrayList<Evento> list = Evento.getListaEventosLatLong(marker.getPosition());
                    if (list.size() == 1) {
                        Fragment fragment = new InfoEventoMapFragment(list.get(0));

                        android.support.v4.app.FragmentTransaction fragmentTrasaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
                        fragmentTrasaction.commit();
                    }

                    ProcurarEventosMeetFragment.abrirSlider();
                }
            });
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {// Inflate the layout for this fragment
                    chamarFragmentListaEventos(Evento.getListaEventosLatLong(marker.getPosition()));
                    // Se ela retorna falso, então o comportamento padrão irá ocorrer em adição ao seu comportamento personalizado
                    return false;
                }
            });

            mMap.setOnMapLoadedCallback(this);

        } catch (SecurityException e) {
            Log.d(getClass().getName(), e.getMessage());
            e.printStackTrace();
        }

        MainActivity.toolbar.setSubtitle("Procurar novos eventos");


        ProcurarEventosMeetFragment.dialog.cancel();
        // criarRaio();
    }

    private void abrirFragmentoCategorias() {
        Fragment fragment = new PrePesquisaFragment();
        android.support.v4.app.FragmentTransaction fragmentTrasaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
        fragmentTrasaction.commit();
        MapsFragmentProcurarEventos.marcarPontos(Meet.getListaEventos());
        MainActivity.toolbar.setSubtitle("Encontrar novos eventos");
        ProcurarEventosMeetFragment.fecharSlider();

    }

    private void chamarFragmentListaEventos(ArrayList<Evento> lista) {

        // se a lista nao for nula , retornara todos eventos
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




/*
    private void criarRaio() {

        int raio = 15000;
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(getMinhaLocalizacao())
                .radius(raio)
                .strokeColor(Color.BLACK)
                .fillColor(Color.TRANSPARENT));
        // move a camera em um certo raio de distancia

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getMinhaLocalizacao(), 10));
    }
*/

    @Override
    public void onMapClick(LatLng latLng) {
     abrirFragmentoCategorias();
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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                return;
            }
        }
    }

    public static void Remarcar() {
        mMap.clear();
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
