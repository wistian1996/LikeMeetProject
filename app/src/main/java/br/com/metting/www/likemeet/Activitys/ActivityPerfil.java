package br.com.metting.www.likemeet.Activitys;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.juliomarcos.ImageViewPopUpHelper;

import br.com.metting.www.likemeet.Control.ImagemControl;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.Fragments.Main.FragmentPerfilHistorico;
import br.com.metting.www.likemeet.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ActivityPerfil extends AppCompatActivity {
    private Usuario usuario;
    private ImageView imageViewPerfil;
    DisplayMetrics metrics;
    int width = 0, height = 0;
    View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

// / animacoes

        /*
        view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        mLoadAnimation.setDuration(1000);
        view.startAnimation(mLoadAnimation);
*/
/*
        Animation anim = new ScaleAnimation(0, 1, 0, 1, 50, 50);
        anim.setDuration(300); // duration = 300
       // getWindow().getDecorView().findViewById(android.R.id.content).startAnimation(anim);
        view = findViewById(android.R.id.content);
        view.startAnimation(anim);
*/
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


            ImagemControl.setImagem(usuario.getFoto(), imageViewPerfil, this);

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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
