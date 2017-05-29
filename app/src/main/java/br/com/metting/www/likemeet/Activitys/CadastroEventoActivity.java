package br.com.metting.www.likemeet.Activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

import br.com.metting.www.likemeet.Class.Categoria;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.FirebaseUtils.FBEventoUtils;
import br.com.metting.www.likemeet.Fragments.CadastroEventos.CalendarioEventoFragment;
import br.com.metting.www.likemeet.Fragments.CadastroEventos.AddAmigosEventoFragment;
import br.com.metting.www.likemeet.Fragments.CadastroEventos.AddLocalEventoFragment;
import br.com.metting.www.likemeet.Fragments.CadastroEventos.InfoEventoFragment;
import br.com.metting.www.likemeet.R;
import br.com.metting.www.likemeet.Fragments.CadastroEventos.RelogioEventoFragment;

public class CadastroEventoActivity extends AppCompatActivity {
    private ToggleButton toggleButtonCalendario;
    private ToggleButton toggleButtonRelogio;
    private ToggleButton toggleButtonGrupo;
    private ToggleButton toggleButtonMaps;
    private ToggleButton toggleButtonInfo;
    private Toolbar toolbar;
    private AddAmigosEventoFragment addAmigosEventoFragment;
    private AddLocalEventoFragment addLocalEventoFragment;
    private RelogioEventoFragment relogioEventoFragment;
    private CalendarioEventoFragment calendarioEventoFragment;
    private InfoEventoFragment infoEventoFragment;
    private Evento evento;

    // view pager do swep
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        infoEventoFragment = new InfoEventoFragment();
        calendarioEventoFragment = new CalendarioEventoFragment();
        relogioEventoFragment = new RelogioEventoFragment();
        addLocalEventoFragment = new AddLocalEventoFragment();
        addAmigosEventoFragment = new AddAmigosEventoFragment();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());


        // metodo do swep
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            // define o que fazer ao pasar o dedo trocando de tela
            public void onPageSelected(int position) {
                if (position == 0) {
                    acaoInfo();
                }
                if (position == 1) {
                    acaoCalendario();
                }
                if (position == 2) {
                    acaoRelogio();
                }
                if (position == 3) {
                    acaoLocalizacao();
                }
                if (position == 4) {
                    acaoAmigos();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Novo evento");
        toolbar.setSubtitle("Adicionar informações");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //botoes
        toggleButtonCalendario = (ToggleButton) findViewById(R.id.buttonCalendario);
        toggleButtonRelogio = (ToggleButton) findViewById(R.id.buttonRelogio);
        toggleButtonGrupo = (ToggleButton) findViewById(R.id.ButtonGrupo);
        toggleButtonInfo = (ToggleButton) findViewById(R.id.buttonInfo);
        toggleButtonMaps = (ToggleButton) findViewById(R.id.buttonMaps);

        toggleButtonInfo.setBackgroundResource(R.drawable.info_verde);

        toggleButtonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //utilizado para trocar de fragmento
                mViewPager.setCurrentItem(1);
            }
        });
        toggleButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        toggleButtonRelogio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });
        toggleButtonGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(4);
            }
        });
        toggleButtonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(3);
            }
        });

        //pegando a id do evento
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if (b != null) {
            value = b.getInt("idEvento");
        }
        if (value != -1) {
            evento = Evento.getEvento(value);
            infoEventoFragment = new InfoEventoFragment(evento);
            calendarioEventoFragment = new CalendarioEventoFragment(evento);
            relogioEventoFragment = new RelogioEventoFragment(evento);
            addLocalEventoFragment = new AddLocalEventoFragment(evento);
            addAmigosEventoFragment = new AddAmigosEventoFragment();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_cadastro, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void acaoInfo() {
        toolbar.setSubtitle("Adicionar informações");

        toggleButtonCalendario.setBackgroundResource(R.drawable.calendario_verde_preto);
        toggleButtonRelogio.setBackgroundResource(R.drawable.relogio_verde_preto);
        toggleButtonGrupo.setBackgroundResource(R.drawable.grupo_preto);
        toggleButtonInfo.setBackgroundResource(R.drawable.info_verde);
        toggleButtonMaps.setBackgroundResource(R.drawable.localizacao_verde_preto);
    }

    private void acaoCalendario() {
        toolbar.setSubtitle("Adicionar data");
        toggleButtonRelogio.setBackgroundResource(R.drawable.relogio_verde_preto);
        toggleButtonGrupo.setBackgroundResource(R.drawable.grupo_preto);
        toggleButtonInfo.setBackgroundResource(R.drawable.infor_verde_preto);
        toggleButtonMaps.setBackgroundResource(R.drawable.localizacao_verde_preto);
        toggleButtonCalendario.setBackgroundResource(R.drawable.calendario_verde);
    }

    private void acaoRelogio() {
        toolbar.setSubtitle("Adicionar horário");
        toggleButtonRelogio.setBackgroundResource(R.drawable.relogio_verde);
        toggleButtonCalendario.setBackgroundResource(R.drawable.calendario_verde_preto);
        toggleButtonGrupo.setBackgroundResource(R.drawable.grupo_preto);
        toggleButtonInfo.setBackgroundResource(R.drawable.infor_verde_preto);
        toggleButtonMaps.setBackgroundResource(R.drawable.localizacao_verde_preto);
    }

    private void acaoAmigos() {
        toolbar.setSubtitle("Adicionar amigos");
        toggleButtonCalendario.setBackgroundResource(R.drawable.calendario_verde_preto);
        toggleButtonRelogio.setBackgroundResource(R.drawable.relogio_verde_preto);
        toggleButtonGrupo.setBackgroundResource(R.drawable.grupo);
        toggleButtonInfo.setBackgroundResource(R.drawable.infor_verde_preto);
        toggleButtonMaps.setBackgroundResource(R.drawable.localizacao_verde_preto);
    }

    private void acaoLocalizacao() {
        toolbar.setSubtitle("Adicionar localização");
        toggleButtonCalendario.setBackgroundResource(R.drawable.calendario_verde_preto);
        toggleButtonRelogio.setBackgroundResource(R.drawable.relogio_verde_preto);
        toggleButtonGrupo.setBackgroundResource(R.drawable.grupo_preto);
        toggleButtonInfo.setBackgroundResource(R.drawable.infor_verde_preto);
        toggleButtonMaps.setBackgroundResource(R.drawable.localizacao_verde);
    }


    // define acao para o botao back do menu toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;

            case R.id.action_ok:
                //acao do botao send
                String nome = "";
                String descricao = "";
                String categoria = "";
                Double taxaEntrada = 0.0;
                int restricaoIdade = 0;
                int fluxoPessoas = 0;
                int privado = 0;
                int[] duracaoEvento;

                if (verificarCamposInfoEventoFragment()) {
                    nome = infoEventoFragment.getTextNome();
                    descricao = infoEventoFragment.getTextDescricao();
                    categoria = infoEventoFragment.getCategoria();

                    if (infoEventoFragment.getCheckBoxtaxaEntrada().isChecked()) {
                        taxaEntrada = Double.parseDouble(infoEventoFragment.getTextTaxaEntrada());
                    }
                    if (infoEventoFragment.getCheckBoxRestricaoIdade().isChecked()) {
                        restricaoIdade = Integer.parseInt(infoEventoFragment.getTextRestricaoIdade());
                    }

                    if (infoEventoFragment.getCheckBoxFluxoPessoas().isChecked()) {
                        fluxoPessoas = Integer.parseInt(infoEventoFragment.getTextFluxoPessoas());
                    }
                    if (infoEventoFragment.getCheckBoxPrivado().isChecked()) {
                        privado = 1;
                    }
                } else {
                    break;
                }


                Date dataSemHoras;
                Calendar dataCalendar;
                java.sql.Date data;
                if (verificarCamposCalendarioEventoFragment()) {
                    dataSemHoras = calendarioEventoFragment.getDataMarcada();
                    dataCalendar = Calendar.getInstance();
                    dataCalendar.setTime(dataSemHoras);
                    dataCalendar.set(Calendar.HOUR_OF_DAY, relogioEventoFragment.getHora());
                    dataCalendar.set(Calendar.MINUTE, relogioEventoFragment.getMinutos());
                    data = new java.sql.Date(dataCalendar.getTime().getTime());
                    duracaoEvento = relogioEventoFragment.getDuracaoEvento();
                } else {
                    break;
                }

                String endereco = "";
                String local = "";

                if (verificarCamposLocalEventoFragment()) {
                    endereco = addLocalEventoFragment.getNome() + " " + addLocalEventoFragment.getEndereco();
                    local = addLocalEventoFragment.getLocal();
                } else {
                    break;
                }
/*
                br.com.metting.www.likemeet.FirebaseModel.Evento evento = new br.com.metting.www.likemeet.FirebaseModel.Evento();
                br.com.metting.www.likemeet.FirebaseModel.Categoria catFirebase =
                        new br.com.metting.www.likemeet.FirebaseModel.Categoria();

                catFirebase.setId(99);
                catFirebase.setNome("Alex");
                evento.setCategoria(catFirebase);

                evento.setDataEvento(String.valueOf(data.getTime()));
                evento.setDescricao(descricao);
                evento.setDuracaoEvento(relogioEventoFragment.getDuracaoStr());
                evento.setEndereco(endereco);
                evento.setNome(nome);
                evento.setLocal(local);
                evento.setValorEntrada(taxaEntrada);
                evento.setQtdMax(fluxoPessoas);
                evento.setPrivado(Boolean.parseBoolean(privado + ""));
                evento.setIdadeMin(restricaoIdade);

                FBEventoUtils fbEventoUtils = new FBEventoUtils();
                fbEventoUtils.inverirEvento(evento);
*/
                Toast.makeText(getApplicationContext(), "Evento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();


                Meet.cadastrarEvento(1, nome, local, endereco, data, duracaoEvento, taxaEntrada,
                        fluxoPessoas, descricao, privado, Categoria.getCateriaPorNome(categoria), restricaoIdade);
                finish();
        }
        return true;
    }

    private boolean verificarCamposInfoEventoFragment() {
        boolean resultado = true;
        if (!infoEventoFragment.verificarFluxo()) resultado = false;
        Log.d("Verificar Campos", "" + resultado);
        if (!infoEventoFragment.verificarIdade()) resultado = false;
        Log.d("Verificar Campos", "" + resultado);
        if (!infoEventoFragment.verificarDescricao()) resultado = false;
        Log.d("Verificar Campos", "" + resultado);
        if (!infoEventoFragment.verificarTaxaDeEntrada()) resultado = false;
        Log.d("Verificar Campos", "" + resultado);
        if (!infoEventoFragment.verificarNome()) resultado = false;
        Log.d("Verificar Campos", "" + resultado);

        if (!resultado) {
            mViewPager.setCurrentItem(0);
            return false;
        } else {
            return true;
        }

    }

    private boolean verificarCamposLocalEventoFragment() {
        if (addLocalEventoFragment.verificarCampos()) {
            String nome = addLocalEventoFragment.getNome();
            String endereco = addLocalEventoFragment.getEndereco();

            if (nome.trim().equals("") || endereco.trim().equals("")) {
                Toast.makeText(this, "Adicione um local ao seu evento", Toast.LENGTH_SHORT).show();
                mViewPager.setCurrentItem(3);
                return false;
            } else {
                return true;
            }
        } else {
            Toast.makeText(this, "Adicione um local ao seu evento", Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(3);
            return false;
        }
    }

    private boolean verificarCamposCalendarioEventoFragment() {
        if (calendarioEventoFragment.getDataMarcada() == null) {
            Toast.makeText(this, "Selecione uma data para seu evento!", Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(1);
            return false;
        }
        try {
            int duracao[] = relogioEventoFragment.getDuracaoEvento();
            if (duracao[0] == -1) {
                mViewPager.setCurrentItem(2);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //  a partir daqui sao opçoes do menu swep
    /////////////////////////////////////////
    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {

            super(fm);

        }

        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:
                    return infoEventoFragment;
                case 1:
                    return calendarioEventoFragment;
                case 2:
                    return relogioEventoFragment;
                case 3:
                    return addLocalEventoFragment;
                case 4:
                    return addAmigosEventoFragment;


                default:
                    return null;
            }
        }

        public int getCount() {
            return 5;
        }
    }


}
