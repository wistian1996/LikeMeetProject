package br.com.metting.www.likemeet.Activitys;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.metting.www.likemeet.Class.Categoria;
import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.Fragments.Main.InfoEventoMapFragment;
import br.com.metting.www.likemeet.Fragments.Main.PrePesquisaFragment;
import br.com.metting.www.likemeet.R;

public class VizualizarEventoActivity extends AppCompatActivity {
    private Evento evento;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizar_evento);


        toolbar = (Toolbar) findViewById(R.id.toolbarEvento);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle b = getIntent().getExtras();
        int value; // or other values
        if (b != null) {
            value = b.getInt("idEvento");
            this.evento = Evento.getEvento(value);
            toolbar.setTitle("Vizualizar evento");
            toolbar.setSubtitle("Categoria: " + Categoria.getCategoria(evento.getIdCategoria()).getNome());
            Fragment fragment = new InfoEventoMapFragment(evento, 1);
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.RelativeLayoutVerEvento, fragment);
            fragmentTrasaction.commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
