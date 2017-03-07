package br.com.metting.www.likemeet.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import br.com.metting.www.likemeet.R;


public class AddLocalEventoFragment extends Fragment {
    private EditText editTextnome;
    private EditText editTextEndereco;
    private Button buttonLocal;
    Place infoLocal;

    // map Picker
    int PLACE_PICKER_REQUEST = 1;
    View view;

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
        editTextEndereco = (EditText) view.findViewById(R.id.editTextEndereco);
        editTextnome = (EditText) view.findViewById(R.id.editTextNome);
        buttonLocal = (Button) view.findViewById(R.id.buttonLocal);

        buttonLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pasando a activity e o fragmento como parametro
                chamarTelaMaps();
            }
        });


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

                if (infoLocal != null) {
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
                }
            }
        }
    }

    public String getNome(){
        return editTextnome.getText().toString();
    }
    public String getEndereco(){
        return editTextEndereco.getText().toString();
    }

    public String getLocal(){
        String lat = String.valueOf( infoLocal.getLatLng().latitude);
        String lng = String.valueOf(infoLocal.getLatLng().longitude);

        return lat+","+lng;
    }
    public boolean verificarCampos(){
        if (editTextnome == null) return false;
        if (infoLocal == null) return false;
        return true;
    }

}
