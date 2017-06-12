package br.com.metting.www.likemeet.Activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import br.com.metting.www.likemeet.Control.ImagemControl;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.Fragments.Main.FragmentPerfilHistorico;
import br.com.metting.www.likemeet.R;

public class ActivityPerfil extends AppCompatActivity {
    private Usuario usuario;
    private ImageView imageViewPerfil;
    DisplayMetrics metrics;
    int width = 0, height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // carregando o usuario pela id
        Bundle b = getIntent().getExtras();
        int value; // or other values
        if (b != null) {
            value = b.getInt("idUsuario");
            this.usuario = Usuario.getUsuario(value);
            //alterando imagem do perfil

            //setando o image view de acordo com o tamanho do celular para o image view ficar quadrado
            metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            height = metrics.heightPixels;
            width = metrics.widthPixels;
            imageViewPerfil = (ImageView) findViewById(R.id.imageViewPerfil);
            imageViewPerfil.setMinimumHeight(width);
            ImagemControl.setImagem(usuario.getFoto(), imageViewPerfil);


            // alterando nome do perfil
            setTitle(usuario.getNome());


            Fragment fragment = new FragmentPerfilHistorico(getSupportFragmentManager(), usuario);
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.nested_scrollViewHistorico, fragment);
            fragmentTrasaction.commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
