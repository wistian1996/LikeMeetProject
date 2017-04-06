package br.com.metting.www.likemeet.Fragments.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import br.com.metting.www.likemeet.Activitys.MainActivity;
import br.com.metting.www.likemeet.Class.Categoria;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Maps.MapsFragmentProcurarEventos;
import br.com.metting.www.likemeet.R;

public class PrePesquisaFragment extends Fragment {

    View view;
    ImageView esportes;
    ImageView bares;
    ImageView profissional;
    ImageView jogos;
    ImageView festas;
    ImageView cultural;
    ImageView feiras;

    int categoria = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pre_pesquisa, container, false);


        esportes = (ImageView) view.findViewById(R.id.imageViewEsportes);
        bares = (ImageView) view.findViewById(R.id.imageViewBar);
        profissional = (ImageView) view.findViewById(R.id.imageViewProfissional);
        jogos = (ImageView) view.findViewById(R.id.imageViewJogo);
        festas = (ImageView) view.findViewById(R.id.imageViewFesta);
        cultural = (ImageView) view.findViewById(R.id.imageEncontrarAmigos);
        feiras = (ImageView) view.findViewById(R.id.imageViewFeira);

        esportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoria = 3;
                abrirLista();
            }
        });
        bares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoria = 6;
                abrirLista();
            }
        });
        profissional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoria = 1;
                abrirLista();
            }
        });
        jogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoria = 4;
                abrirLista();
            }
        });

        festas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoria = 0;
                abrirLista();
            }
        });
        cultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoria = 2;
                abrirLista();
            }
        });
        feiras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoria = 5;
                abrirLista();
            }
        });

        return view;
    }

    private void abrirLista() {
        ProcurarEventosMeetFragment.fecharSlider();
        Fragment fragment = new ListaEventoFragment(Evento.getEventoCategoria(categoria));
        MapsFragmentProcurarEventos.marcarPontos(Evento.getEventoCategoria(categoria));
        Categoria c = Categoria.getCategoria(categoria);
        MainActivity.toolbar.setSubtitle("Categoria: " + c.getNome());

        android.support.v4.app.FragmentTransaction fragmentTrasaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
        fragmentTrasaction.commit();


    }
}
