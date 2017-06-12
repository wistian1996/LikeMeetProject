package br.com.metting.www.likemeet.Fragments.CadastroEventos;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.metting.www.likemeet.Adapters.AmigosAdapter;
import br.com.metting.www.likemeet.R;


public class AddAmigosEventoFragment extends Fragment {
    RecyclerView recyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // recycler view amigos
        view = inflater.inflate(R.layout.fragment_add_amigos_evento, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerlista);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        AmigosAdapter amgAdapter = new AmigosAdapter(getActivity() , this);
        recyclerView.setAdapter(amgAdapter);
        return view;
    }



}
