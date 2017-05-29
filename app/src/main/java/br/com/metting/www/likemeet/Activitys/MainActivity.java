package br.com.metting.www.likemeet.Activitys;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;


import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.FirebaseUtils.FBEventoUtils;
import br.com.metting.www.likemeet.Fragments.CadastroEventos.CalendarioEventoFragment;
import br.com.metting.www.likemeet.Fragments.CadastroEventos.InfoEventoFragment;
import br.com.metting.www.likemeet.Fragments.Main.InfoEventoMapFragment;
import br.com.metting.www.likemeet.Fragments.Main.ListaEventoFragment;
import br.com.metting.www.likemeet.Fragments.Main.MeusEventosFragment;
import br.com.metting.www.likemeet.Fragments.Main.ProcurarEventosMeetFragment;
import br.com.metting.www.likemeet.Fragments.Main.PrePesquisaFragment;
import br.com.metting.www.likemeet.Maps.MapsFragmentProcurarEventos;
import br.com.metting.www.likemeet.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Toolbar toolbar;
    private FloatingActionButton fab;
    private ProcurarEventosMeetFragment fragmentoListaEventos;
    private static SearchView searchView;
    private ProcurarEventosMeetFragment procurarEventosMeetFragment;
    private MeusEventosFragment meusEventosFragment;
    private CalendarioEventoFragment calendarioEventoFragment;
    private Dialog mNoGpsDialog;
    private static LatLng local;
    private DrawerLayout drawerLayout;
    private RelativeLayout layout_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Meet m = new Meet();
        createNoGpsDialog();
        fragmentoListaEventos = new ProcurarEventosMeetFragment();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Encontrar novos eventos");
        fab = (FloatingActionButton) findViewById(R.id.fab);

        LocationManager locationManager = (LocationManager) this.getSystemService(getApplicationContext().LOCATION_SERVICE);

        // verifica se o GPS do dispositivo está ativo
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d(getClass().getSimpleName(), "GPS ativado");
            procurarEventosMeetFragment = new ProcurarEventosMeetFragment();
            trocarFragmento(procurarEventosMeetFragment);
        } else {
            Log.d(getClass().getSimpleName(), "GPS Não ativado");
        }

        meusEventosFragment = new MeusEventosFragment(getSupportFragmentManager());
        calendarioEventoFragment = new CalendarioEventoFragment();

//        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
//        tx.replace(R.id.layoutPrincipal, fragmentoListaEventos);
//        tx.commit();

        fab.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
                                       startActivity(intent);
                                   }
                               }
        );

        setupDrawerView();

        //check se a permissao de local ja foi concedida
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    private void setupDrawerView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // como trocar a tintura do menu  no navigation drawer
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},// unchecked state
                new int[]{android.R.attr.state_checked}, // checked state
        };
        int[] colors = new int[]{
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                ContextCompat.getColor(this, R.color.colorAccent)
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);
        navigationView.setItemTextColor(colorStateList);
        navigationView.setItemIconTintList(colorStateList);
    }

    public static SearchView getSearchView() {
        return searchView;
    }

    public static LatLng getLocal() {
        return local;
    }

    public static void setLocal(LatLng local) {
        MainActivity.local = local;
    }

    // verifica a resposta da requisicao de acesso a localizacao
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    this.finish();
                }
                return;
            }
        }
    }

    // chama o fragment de categorias (posicionado na parte inferior do FragmentEventos
    private void abrirFragmentoCategorias() {
        ProcurarEventosMeetFragment.fecharSlider();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Fragment fragment = new PrePesquisaFragment();
                android.support.v4.app.FragmentTransaction fragmentTrasaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
                fragmentTrasaction.commit();
                MapsFragmentProcurarEventos.marcarPontos(Meet.getListaEventos());
                toolbar.setSubtitle("Encontrar novos eventos");
            }
        }, 100);
    }

    @Override
    public void onBackPressed() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        // caso haja algum framento iniciado com addtobackstack antes do replace , ele é inserido na pilha
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            Log.d(getClass().getName(), "pilha:" + getSupportFragmentManager().getBackStackEntryCount());
            return;
        }

        //caso search esteja aberto
        if (!searchView.isIconified()) {
            searchView.onActionViewCollapsed();
            //caso slider esteja aberto
            abrirFragmentoCategorias();
            return;
        }

        //caso slider esteja aberto
        if (!ProcurarEventosMeetFragment.getSlider().getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
            ProcurarEventosMeetFragment.fecharSlider();
            return;
        }
        // verifica qual fragment esta aberto no layout de baixo
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.LayoutBaixoMap);
        if (f instanceof ListaEventoFragment || f instanceof InfoEventoMapFragment) {
            abrirFragmentoCategorias();
            return;
        }

        // Se vier null ou length == 0
        DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();

                    case DialogInterface.BUTTON_NEGATIVE:

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mNoGpsDialog = builder.setMessage("Deseja finalizar a aplicação?")
                .setPositiveButton("Sim", dialogClickListener2).setNegativeButton("Não", dialogClickListener2)
                .create();

        mNoGpsDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//set layout perfil
        layout_perfil = (RelativeLayout) findViewById(R.id.layout_perfil);
        layout_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityPerfil.class);
                startActivity(intent);
            }
        });

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            // AÇAO AO APERTAR O BOTAO PESQUISAR
            public void onClick(View view) {
                //"aguardando o teclado abrir primeiro"
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        procurarEventosMeetFragment.abrirSlider();
                        MapsFragmentProcurarEventos.marcarPontos(Meet.getListaEventos());
                    }
                }, 300);

                Fragment fragment = new ListaEventoFragment(Evento.getEvento(""));
                android.support.v4.app.FragmentTransaction fragmentTrasaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
                fragmentTrasaction.commit();
                MapsFragmentProcurarEventos.descarmarMarker();
                toolbar.setSubtitle("Encontrar novos eventos");
                searchView.requestFocus();
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.LayoutBaixoMap);
                if (f instanceof ListaEventoFragment) {
                    abrirFragmentoCategorias();
                }

                if (!ProcurarEventosMeetFragment.getSlider().getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
                    ProcurarEventosMeetFragment.fecharSlider();
                }
                toolbar.setSubtitle("Encontrar novos eventos");

                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //acao ao selecionar o botao pesquisar
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procurarEventosMeetFragment.abrirSlider();

                Fragment fragment = new ListaEventoFragment(Evento.getEvento(newText));
                android.support.v4.app.FragmentTransaction fragmentTrasaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
                fragmentTrasaction.commit();
                MapsFragmentProcurarEventos.descarmarMarker();

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     /*
        switch (item.getItemId()) {
            case R.id.action_atualizar:
                //acao do botao atualizar
                break;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    // verifica se o gps esta ligado
    private void createNoGpsDialog() {
        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.length() != 0) {
            return;
        }


        //   Se vier null ou length == 0
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(callGPSSettingIntent, 100);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        finish();
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mNoGpsDialog = builder.setMessage("Para encontrar eventos é preciso que sua localização esteja ativada.")
                .setPositiveButton("Configurações", dialogClickListener).setNegativeButton("Sair", dialogClickListener)
                .create();

        mNoGpsDialog.show();
        mNoGpsDialog.setCancelable(false);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_meet) {

            toolbar.setSubtitle("Encontrar novos eventos");
            fab.setVisibility(View.INVISIBLE);
            trocarFragmento(procurarEventosMeetFragment);
            searchView.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_meus_eventos) {
            fab.setVisibility(View.VISIBLE);
            toolbar.setSubtitle("Meus eventos");
            searchView.setVisibility(View.INVISIBLE);

            trocarFragmento(meusEventosFragment);

        } else if (id == R.id.nav_config) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_chat) {
            fab.setVisibility(View.INVISIBLE);
            toolbar.setSubtitle("Chat");
            searchView.setVisibility(View.INVISIBLE);

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void trocarFragmento(final Fragment fragment) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                android.support.v4.app.FragmentTransaction fragmentTrasaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTrasaction.replace(R.id.layoutPrincipal, fragment);
                fragmentTrasaction.commit();
            }
        }, 200);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100) {
                LocationManager locationManager = (LocationManager) this.getSystemService(getApplicationContext().LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    mNoGpsDialog.cancel();
                    procurarEventosMeetFragment = new ProcurarEventosMeetFragment();
                    trocarFragmento(procurarEventosMeetFragment);
                } else {
                    mNoGpsDialog.show();
                }
            }
        } catch (Exception e) {
            Log.d(getClass().getName(), e.getMessage());
        }

    }


}
