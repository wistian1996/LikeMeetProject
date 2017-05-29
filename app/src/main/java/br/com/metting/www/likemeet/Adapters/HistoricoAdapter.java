package br.com.metting.www.likemeet.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import br.com.metting.www.likemeet.R;

/**
 * Created by wisti on 18/05/2017.
 */

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;

    public HistoricoAdapter(Context c) {
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public HistoricoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_view_historico, parent, false);
        HistoricoAdapter.MyViewHolder mvh = new HistoricoAdapter.MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(HistoricoAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        MapView mMap;
        GoogleMap gMap;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mMap = (MapView) itemView.findViewById(R.id.mapViewCardHistorico);
            mMap.onCreate(null);
            mMap.onResume();
            mMap.getMapAsync(this);}

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(itemView.getContext());
            gMap = googleMap;
        }
    }




}
