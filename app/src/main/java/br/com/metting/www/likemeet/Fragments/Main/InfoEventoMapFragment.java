package br.com.metting.www.likemeet.Fragments.Main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.metting.www.likemeet.Activitys.CadastroEventoActivity;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.R;

public class InfoEventoMapFragment extends Fragment implements OnMapReadyCallback {
    private Evento evento;
    // view pager do swep
    private ViewPager mViewPager;
    private TextView nome;
    private View view;
    private listaUsuariosFragment listaUsuariosFragment;
    private infoEventoMap2Fragment infoEventoMap2Fragment;
    private int fragmento; // se == 1 , esta abrindo pelo MeusEventosAdapter ou historico adapter
    private RelativeLayout relativeLayoutEditar;
    private RelativeLayout relativeLayoutInfo;
    private RelativeLayout relativeLayoutAddFoto;
    private RelativeLayout relativeLayoutBotaoEuVou;
    private RelativeLayout relativeLayoutCancelar;
    private RelativeLayout relativeLayoutApagar;
    private RelativeLayout relativeLayoutButtonInfo;
    private ImageView setaEsquerda;
    private ImageView setaDireita;
    private Dialog dialog;
    private TextView data;
    private TextView horario;
    private TextView entrada;
    private TextView idade;
    private TextView endereco;
    private TextView maxPessoas;
    private TextView descricao;
    private MapView mMap;
    private GoogleMap gMap;
    private Button buttonInfo;
    private Dialog mNoGpsDialog;
    Usuario u;

    public InfoEventoMapFragment(Evento evento) {
        this.evento = evento;
        fragmento = 0;
        u = u.getUsuario();
    }

    public InfoEventoMapFragment(Evento evento, int fragmento) {
        this.evento = evento;
        this.fragmento = fragmento;
        u = u.getUsuario();
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
        listaUsuariosFragment = new listaUsuariosFragment(evento.getListaPartipantes());
        infoEventoMap2Fragment = new infoEventoMap2Fragment(evento);
        nome = (TextView) view.findViewById(R.id.textViewNome);
        nome.setText(evento.getNome());
        relativeLayoutInfo = (RelativeLayout) view.findViewById(R.id.relativeLayoutFundoVerde);
        relativeLayoutEditar = (RelativeLayout) view.findViewById(R.id.relativeLayoutEditar);
        relativeLayoutAddFoto = (RelativeLayout) view.findViewById(R.id.RelativeLayoutAddFoto);
        relativeLayoutApagar = (RelativeLayout) view.findViewById(R.id.relativeLayoutApagar);
        relativeLayoutBotaoEuVou = (RelativeLayout) view.findViewById(R.id.relativeLayoutBotaoEuVou);
        relativeLayoutCancelar = (RelativeLayout) view.findViewById(R.id.relativeLayoutCancelar);
        relativeLayoutButtonInfo = (RelativeLayout) view.findViewById(R.id.relativeLayout_info_evento_botao);
        setaDireita = (ImageView) view.findViewById(R.id.imageViewSetaDireita);
        setaEsquerda = (ImageView) view.findViewById(R.id.imageViewSetaEsquerda);
        setaEsquerda.setVisibility(View.INVISIBLE);
        preencherInfoEvento();
        acoesbotoes();
        alterarBotoes();
        mViewPager = (ViewPager) view.findViewById(R.id.pager_info_evento);
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        //comentario da tab com nome . nao vai ser mais necessario
        // definindo uma tab para o page
        //  TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        //  tabLayout.addTab(tabLayout.newTab().setText("Evento"));
        //  tabLayout.addTab(tabLayout.newTab().setText("Participantes"));
        // tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // tabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        //   mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      /*  tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });*/
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setaDireita.setVisibility(View.VISIBLE);
                    setaEsquerda.setVisibility(View.INVISIBLE);

                    Snackbar.make(view, "Publicações, arraste para baixo para ver mais", Snackbar.LENGTH_SHORT)
                            .show();
                }
                if (position == 1) {
                    setaDireita.setVisibility(View.INVISIBLE);
                    setaEsquerda.setVisibility(View.VISIBLE);


                    Snackbar.make(view, "Participantes", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (fragmento == 0) ProcurarEventosMeetFragment.fecharSlider();
        return view;
    }

    private void acoesbotoes() {
        relativeLayoutButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        setaEsquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProcurarEventosMeetFragment.getSlider().getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
                    ProcurarEventosMeetFragment.abrirSlider();
                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            mViewPager.setCurrentItem(0);
                            setaDireita.setVisibility(View.VISIBLE);
                            setaEsquerda.setVisibility(View.INVISIBLE);
                        }
                    }, 200);
                } else {
                    mViewPager.setCurrentItem(0);
                    setaDireita.setVisibility(View.VISIBLE);
                    setaEsquerda.setVisibility(View.INVISIBLE);
                }
            }
        });
        setaDireita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProcurarEventosMeetFragment.getSlider().getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
                    ProcurarEventosMeetFragment.abrirSlider();
                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            mViewPager.setCurrentItem(1);
                            setaDireita.setVisibility(View.INVISIBLE);
                            setaEsquerda.setVisibility(View.VISIBLE);
                        }
                    }, 200);
                } else {
                    mViewPager.setCurrentItem(1);
                    setaDireita.setVisibility(View.INVISIBLE);
                    setaEsquerda.setVisibility(View.VISIBLE);
                }
            }
        });
        relativeLayoutEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getElementosIntent();
            }
        });
        relativeLayoutEditar.setOnClickListener(new View.OnClickListener() {
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
        relativeLayoutBotaoEuVou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // verificar a capacidade maxima de pessoas
                // verificar a data do evento se esta de acordo
                // verificar a idade minima para entrada do evento
                //nao fiz pra nao gastar tempo
                dialogConfirmacao();


            }
        });
    }

    private void entrarNoEvento() {
        alterarBotoes();
        if (ProcurarEventosMeetFragment.getSlider().getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
            ProcurarEventosMeetFragment.abrirSlider();
            evento.adicionarUsuario(Usuario.getUsuario());

            InfoEventoMapFragment inf = new InfoEventoMapFragment(evento);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.LayoutBaixoMap , inf).commit();

            Toast.makeText(getContext(),"Agora você é um participante" , Toast.LENGTH_LONG).show();
        }
    }

    DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    entrarNoEvento();
                    // lembrar de atualizar o historico de publicacoes
                case DialogInterface.BUTTON_NEGATIVE:
            }
        }
    };

    private void dialogConfirmacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        mNoGpsDialog = builder.setMessage("Participar do evento " + evento.getNome() + " ?")
                .setPositiveButton("Vou", dialogClickListener2).setNegativeButton("Não", dialogClickListener2)
                .create();
        mNoGpsDialog.show();
    }

    private void preencherInfoEvento() {

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_dialog_info_evento);
        dialog.setTitle("Informações do evento");


        data = (TextView) dialog.findViewById(R.id.textViewInfoData);
        horario = (TextView) dialog.findViewById(R.id.textViewInfoHorario);
        entrada = (TextView) dialog.findViewById(R.id.textViewInfoEntrada);
        idade = (TextView) dialog.findViewById(R.id.textViewIdade);
        endereco = (TextView) dialog.findViewById(R.id.textViewEndereco);
        maxPessoas = (TextView) dialog.findViewById(R.id.textViewMaxPessoas);
        descricao = (TextView) dialog.findViewById(R.id.textViewDescricao);
        descricao.setText(evento.getDescricao());

        mMap = (MapView) dialog.findViewById(R.id.mapViewLocalVizualizar);
        mMap.onCreate(null);
        mMap.onResume();
        mMap.getMapAsync(this);


        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(evento.getDataEvento());
        dataCalendar.add(Calendar.HOUR, evento.getDuracaoEvento()[0]);
        dataCalendar.add(Calendar.MINUTE, evento.getDuracaoEvento()[1]);

        SimpleDateFormat d = new SimpleDateFormat("dd/MM");
        SimpleDateFormat d2 = new SimpleDateFormat("HH:mm");
        String dataString = d.format(dataCalendar.getTime());
        String horaString = d2.format(dataCalendar.getTime());
        data.setText(evento.getDataString() + " Até: " + dataString + " às " + horaString);
        horario.setText(evento.getHoraString());


        if (evento.getValorEntrada() == 0) {
            entrada.setText("Entrada gratuita");
        } else {
            entrada.setText(String.valueOf(evento.getValorEntrada()));
        }
        if (evento.getIdadeMin() > 0) {
            idade.setText("Mínimo " + String.valueOf(evento.getIdadeMin()) + " anos");
        } else {
            idade.setText("Todas as idades");
        }

        endereco.setText(evento.getEndereco());

        if (evento.getQtdMax() > 0) {
            maxPessoas.setText("Máximo " + String.valueOf(evento.getQtdMax()) + " Pessoas");
        } else {
            maxPessoas.setText("Sem limites de participantes");
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        gMap = googleMap;
        gMap.getUiSettings().setZoomControlsEnabled(true);
        MarkerOptions marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        String[] latlong = evento.getLocal().split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        LatLng local2 = new LatLng(latitude, longitude);
        marker.position(local2);
        gMap.clear();
        gMap.addMarker(marker);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(local2, 15f));
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
                    return listaUsuariosFragment;

                default:
                    return null;
            }
        }

        public int getCount() {
            return 2;
        }
    }

    private void alterarBotoes() {
//VERIFICO SE O USUARIO ESTA NO EVENTO
        if (u.cadastradoEvento(evento.getId())) {
            relativeLayoutBotaoEuVou.setVisibility(View.GONE);
        } else {
            relativeLayoutAddFoto.setVisibility(View.GONE);
            relativeLayoutCancelar.setVisibility(View.GONE);
            relativeLayoutEditar.setVisibility(View.GONE);
            relativeLayoutApagar.setVisibility(View.GONE);
            return;
        }
        //VERIFICO SE O EVENTO É DO USUARIO QUE ESTA LOGADO SE FOR OS BOTOES PERMANEGEM VISIVEIS
        if (evento.getIdUsuarioCadastrou() != u.getId()) {
            relativeLayoutApagar.setVisibility(View.GONE);
            relativeLayoutEditar.setVisibility(View.GONE);

            return;
        }


    }
}
