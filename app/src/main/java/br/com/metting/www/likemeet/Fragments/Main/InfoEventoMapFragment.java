package br.com.metting.www.likemeet.Fragments.Main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import br.com.metting.www.likemeet.Activitys.CadastroEventoActivity;
import br.com.metting.www.likemeet.Activitys.MainActivity;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.Maps.MapsFragmentProcurarEventos;
import br.com.metting.www.likemeet.R;

public class InfoEventoMapFragment extends Fragment {
    private Evento evento;
    // view pager do swep
    private ViewPager mViewPager;
    private TextView nome;
    private View view;
    private RelativeLayout relativeLayoutInfo;
    private listaParticipantesEventoInfoFragment listaParticipantesEventoInfoFragment;
    private infoEventoMap2Fragment infoEventoMap2Fragment;
    private int fragmento; // se == 1 , esta abrindo pelo MeusEventosAdapter ou historico adapter
    private TextView textViewEuvou;
    private Button buttonEuVou;
    private RelativeLayout relativeLayoutEditar;
    private Button buttonEditar;

    public InfoEventoMapFragment(Evento evento) {
        this.evento = evento;
        fragmento = 0;
    }


    public InfoEventoMapFragment(Evento evento, int fragmento) {
        this.evento = evento;
        this.fragmento = fragmento;
    }


    private void getElementosIntent() {
        Intent intent = new Intent(getActivity(), CadastroEventoActivity.class);

        Bundle b = new Bundle();
        b.putInt("idEvento", evento.getId()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_info_evento_map, container, false);

        // Inflate the layout for this fragment
        listaParticipantesEventoInfoFragment = new listaParticipantesEventoInfoFragment();
        infoEventoMap2Fragment = new infoEventoMap2Fragment(evento);


        nome = (TextView) view.findViewById(R.id.textViewNome);
        nome.setText(evento.getNome());
        relativeLayoutInfo = (RelativeLayout) view.findViewById(R.id.relativeLayoutFundoVerde);
        textViewEuvou = (TextView) view.findViewById(R.id.textViewEuVou);
        buttonEuVou = (Button) view.findViewById(R.id.buttonEuVou);
        relativeLayoutEditar = (RelativeLayout) view.findViewById(R.id.relativeLayoutEditar);
        buttonEditar = (Button) view.findViewById(R.id.buttonEditar);
        alterarBotoes();

        relativeLayoutEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getElementosIntent();
            }
        });
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getElementosIntent();
            }
        });


        if (fragmento == 0) {
            relativeLayoutInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ProcurarEventosMeetFragment.getSlider().getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
                        ProcurarEventosMeetFragment.fecharSlider();
                    } else {
                        ProcurarEventosMeetFragment.abrirSlider();
                    }

                }
            });
        }


        // definindo uma tab para o page
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Informações"));
        tabLayout.addTab(tabLayout.newTab().setText("Participantes"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));



        mViewPager = (ViewPager) view.findViewById(R.id.pager_info_evento);

        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (fragmento == 0) ProcurarEventosMeetFragment.fecharSlider();
        return view;
    }

    public class PagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {

            super(fm);

        }

        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:
                    return infoEventoMap2Fragment;
                case 1:
                    return listaParticipantesEventoInfoFragment;

                default:
                    return null;
            }
        }

        public int getCount() {
            return 2;
        }
    }

    private void alterarBotoes() {
        if (evento.getIdUsuarioCadastrou() == Usuario.getUsuario().getId()) {
            buttonEuVou.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete_sweep_black_24dp));
            textViewEuvou.setText("Apagar");
            relativeLayoutEditar.setVisibility(View.VISIBLE);
            return;
        } else {
            relativeLayoutEditar.setVisibility(View.INVISIBLE);
        }
        for (Evento lEvento : Usuario.getMeusEventos(1)
                ) {
            if (evento.getId() == lEvento.getId()) {
                buttonEuVou.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_cancel_black_24dp));
                textViewEuvou.setText("Desistir!");
                return;
            }
        }


    }
}
