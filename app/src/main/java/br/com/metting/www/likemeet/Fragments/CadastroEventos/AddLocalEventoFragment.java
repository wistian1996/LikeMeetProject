package br.com.metting.www.likemeet.Fragments.CadastroEventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.R;


public class AddLocalEventoFragment extends Fragment implements OnMapReadyCallback {
    private TextView editTextnome;
    private TextView editTextEndereco;
    private Button buttonLocal;
    Place infoLocal;
    private Evento evento;


    MapView mMap;
    GoogleMap gMap;

    // map Picker
    int PLACE_PICKER_REQUEST = 1;
    View view;

    public AddLocalEventoFragment() {

    }

    public AddLocalEventoFragment(Evento evento) {
        this.evento = evento;
    }

    public void chamarTelaMaps() {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(getActivity());
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_local_evento, container, false);
        editTextEndereco = (TextView) view.findViewById(R.id.editTextEndereco);
        editTextnome = (TextView) view.findViewById(R.id.editTextNome);
        buttonLocal = (Button) view.findViewById(R.id.buttonLocal);

        mMap = (MapView) view.findViewById(R.id.mapViewLocalAdd);
        mMap.onCreate(null);
        mMap.onResume();
        mMap.getMapAsync(this);
        mMap.setVisibility(View.INVISIBLE);


        buttonLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pasando a activity e o fragmento como parametro
                chamarTelaMaps();
            }
        });

        preencherInfo();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                //  String adress = String.format("Place: %s", place.getAddress());
                infoLocal = place;
                preencherInfo();
            }
        }
    }

    private void preencherInfo() {
        if (infoLocal != null) {
            marcarMapa(infoLocal.getLatLng());
            mMap.setVisibility(View.VISIBLE);
            if (infoLocal.getAddress() != null) {
                editTextEndereco.setText(String.format("%s", infoLocal.getAddress()));
                editTextEndereco.setVisibility(View.VISIBLE);
            } else {
                editTextEndereco.setVisibility(View.INVISIBLE);
            }
            if (infoLocal.getName() != null) {
                editTextnome.setText(String.format("%s", infoLocal.getName()));
                editTextnome.setVisibility(View.VISIBLE);
            } else {
                editTextnome.setVisibility(View.INVISIBLE);
            }
        } else {
            if (evento != null) {
                editTextEndereco.setText(evento.getEndereco());
                editTextEndereco.setVisibility(View.VISIBLE);
                editTextnome.setVisibility(View.INVISIBLE);
            }
        }
    }

    public String getNome() {
        return editTextnome.getText().toString();
    }

    public String getEndereco() {
        return editTextEndereco.getText().toString();
    }

    public String getLocal() {
        String lat = String.valueOf(infoLocal.getLatLng().latitude);
        String lng = String.valueOf(infoLocal.getLatLng().longitude);

        return lat + "," + lng;
    }

    public boolean verificarCampos() {
        if (editTextnome == null) return false;
        if (infoLocal == null) return false;
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        preencherInfo();
    }

    private void marcarMapa(LatLng local2) {
        MarkerOptions marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        marker.position(local2);
        mMap.setVisibility(View.VISIBLE);
        gMap.clear();
        gMap.addMarker(marker);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(local2, 15f));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //initialize the Google Maps Android API if features need to be used before obtaining a map
        MapsInitializer.initialize(getActivity());
        gMap = googleMap;
        gMap.getUiSettings().setZoomControlsEnabled(true);
        if (evento != null) {
            String[] latlong = evento.getLocal().split(",");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);
            LatLng local2 = new LatLng(latitude, longitude);
            marcarMapa(local2);
            return;
        }
        if (infoLocal != null) {
            marcarMapa(infoLocal.getLatLng());
        }
    }
}
