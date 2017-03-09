package br.com.metting.www.likemeet.Activitys;

import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Fragments.CadastroEventos.CalendarioEventoFragment;
import br.com.metting.www.likemeet.Fragments.ListaEventoFragment;
import br.com.metting.www.likemeet.Fragments.MeusEventosFragment;
import br.com.metting.www.likemeet.Fragments.ProcurarEventosMeetFragment;
import br.com.metting.www.likemeet.Maps.MapsFragmentProcurarEventos;
import br.com.metting.www.likemeet.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private Fragment fragment;
    private FloatingActionButton fab;
    private ProcurarEventosMeetFragment fragmentoListaEventos;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Meet m = new Meet();
        createNoGpsDialog();
        fragmentoListaEventos = new ProcurarEventosMeetFragment();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.layoutPrincipal, fragmentoListaEventos);
        tx.commit();

        fab.setVisibility(View.INVISIBLE);


        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                                       Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
                                       startActivity(intent);

                                   }
                               }

        );


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


        //check se a permissao de local ja foi concedida
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


    }

    // verifica a resposta da requisicao de acesso a localizacao
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    this.finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    DrawerLayout drawerLayout;

    @Override
    public void onBackPressed() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {

            //caso slider esteja aberto
            if (!ProcurarEventosMeetFragment.slider.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
                ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                return;
            }
            //caso search esteja aberto
            if (!searchView.isIconified()) {
                searchView.setIconified(true);
                return;
            }

            //   Se vier null ou length == 0
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
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
            Dialog mNoGpsDialog = builder.setMessage("Deseja finalizar a aplicação?")
                    .setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener)
                    .create();

            mNoGpsDialog.show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            // AÇAO AO APERTAR O BOTAO PESQUISAR
            public void onClick(View view) {

                Fragment fragment = new ListaEventoFragment(Meet.getListaEventos());
                android.support.v4.app.FragmentTransaction fragmentTrasaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTrasaction.replace(R.id.LayoutBaixoMap, fragment);
                fragmentTrasaction.commit();
                MapsFragmentProcurarEventos.descarmarMarker();

                if (!ProcurarEventosMeetFragment.slider.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
                    ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    Log.d("Main", "SLIDDER EXPANDED");
                }

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("Main", "SLIDDER COLLAPSED");
                if (!ProcurarEventosMeetFragment.slider.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
                    ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
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
                // utlizar autocomplete
                if (!ProcurarEventosMeetFragment.slider.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
                    Log.d("Main", "SLIDDER EXPANDED");
                    ProcurarEventosMeetFragment.slider.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ok:
                Toast.makeText(this, "sei la ", Toast.LENGTH_LONG);
                break;
        }
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
                        startActivity(callGPSSettingIntent);
                        finish();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Dialog mNoGpsDialog = builder.setMessage("Para encontrar eventos é preciso que sua localização esteja ativada.")
                .setPositiveButton("Configurações", dialogClickListener)
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
            fragment = new ProcurarEventosMeetFragment();
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.layoutPrincipal, fragment);
            fragmentTrasaction.commit();

        } else if (id == R.id.nav_meus_eventos) {
            fab.setVisibility(View.VISIBLE);
            toolbar.setSubtitle("Meus eventos");
            fragment = new MeusEventosFragment();
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.layoutPrincipal, fragment);
            fragmentTrasaction.commit();

        } else if (id == R.id.nav_agenda) {
            toolbar.setSubtitle("Minha agenda");
            fab.setVisibility(View.VISIBLE);
            fragment = new CalendarioEventoFragment();
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.layoutPrincipal, fragment);
            fragmentTrasaction.commit();

        } else if (id == R.id.nav_chat) {
            fab.setVisibility(View.INVISIBLE);
            toolbar.setSubtitle("Chat");

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
