package br.com.metting.www.likemeet.Maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Fragments.InfoEventoMapFragment;
import br.com.metting.www.likemeet.Fragments.ListaEventoFragment;
import br.com.metting.www.likemeet.Fragments.ProcurarEventosMeetFragment;
import br.com.metting.www.likemeet.R;

public class MapsFragmentProcurarEventos extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final String TAG = "MapsFragmentProcurarEventos";
    private static final int MY_PERMISSION_LOCATION = 128;
    private LatLng local;
    private static ArrayList<Marker> listaMarker = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {

        getMapAsync(this);
        super.onCreate(savedInstanceState);


    }

    //executar alguma acao ao clicar em alguma area do meu mapa
    //sera executado quando o mapa estiver pronto
    @Override
    public void onMapReady(GoogleMap googleMap) {
        local = getMinhaLocalizacao();
        try {
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);
            marcarPontos();


            if (local != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 11));
            }

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

                    ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
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

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    // se a lista for null entao ele retorna todos eventos
                    chamarFragmentListaEventos(null);
                }
            });
        } catch (SecurityException e) {
            Log.d(getClass().getName(), e.getMessage());
            e.printStackTrace();
        }

        // criarRaio();
    }

    private void chamarFragmentListaEventos(ArrayList<Evento> lista) {

        // se a lista nao for nula , retornara todos eventos
        if (lista == null) {
            Fragment fragment = new ListaEventoFragment();
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
            fragmentTrasaction.commit();
            // configurando o sliderUp

            ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            ProcurarEventosMeetFragment.slider.refreshDrawableState();
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


                //Configurando o sliderUp
                ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                ProcurarEventosMeetFragment.slider.refreshDrawableState();
                return;

            }

            Fragment fragment = new ListaEventoFragment(lista);
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
            fragmentTrasaction.commit();

            //Configurado o slider Up
            ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            ProcurarEventosMeetFragment.slider.refreshDrawableState();
        }

    }

    private void marcarPontos() {

        MarkerOptions marker = new MarkerOptions();
        Marker m = null;
        //    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marcar_map));

        for (Evento listaEvento : Meet.getListaEventos()) {
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


    private LatLng getMinhaLocalizacao() {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);


        if (!(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            // Location location = locationManager.getLastKnownLocation(provider);

            LatLng local = null;
            if (location != null) {
                local = new LatLng(location.getLatitude(), location.getLongitude());
            } else {
                Toast.makeText(getActivity(), "Falha ao tentar acessar sua localização", Toast.LENGTH_LONG);
            }
            return local;
        }
        //fim da tag
        return null;
    }


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


    @Override
    public void onMapClick(LatLng latLng) {

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
}
