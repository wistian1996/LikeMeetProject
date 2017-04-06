package br.com.metting.www.likemeet.Fragments.Main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.metting.www.likemeet.Adapters.EventosProcurarAdapter;
import br.com.metting.www.likemeet.Adapters.MeusEventosAdapter;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.R;

public class MeusEventosFragment extends Fragment {
    private View view;
    RecyclerView recyclerView;
    FragmentManager fm;

    public MeusEventosFragment(FragmentManager fm){
        this.fm = fm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meus_eventos, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerlistaMeusEventos);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        MeusEventosAdapter adapter = new MeusEventosAdapter(getActivity(), Usuario.getMeusEventos(1),fm);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        MeusEventosAdapter adapter = new MeusEventosAdapter(getActivity(), Usuario.getMeusEventos(1),fm);
        recyclerView.setAdapter(adapter);
        recyclerView.refreshDrawableState();


        super.onResume();
    }
}
