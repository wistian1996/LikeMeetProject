package br.com.metting.www.likemeet.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.sql.Time;

import br.com.metting.www.likemeet.R;


public class RelogioEventoFragment extends Fragment {

    private TimePicker timePickerRelogio;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_relogio_evento, container, false);
        timePickerRelogio = (TimePicker) view.findViewById(R.id.timePickerRelogio);



        return view;

    }



public int getHora(){

    // se for API 23+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return timePickerRelogio.getHour();
    }else{
        return timePickerRelogio.getCurrentHour();
    }


}

 public int getMinutos(){
     // se for api 23+
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         return timePickerRelogio.getMinute();
     }else{
         return timePickerRelogio.getCurrentMinute();
     }

 }


}
