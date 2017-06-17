package br.com.metting.www.likemeet.Activitys;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Class.PublicacaoImagem;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.Control.ImagemControl;
import br.com.metting.www.likemeet.R;


public class ActivityVerFoto extends AppCompatActivity {
    private PublicacaoImagem pb;
    private Usuario usuario;
    private Toolbar toolbar;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_foto);


        Bundle b = getIntent().getExtras();
        int value = 0; // or other values
        int x = 0;
        int y = 0;
        if (b != null) {
            value = b.getInt("idPublicacao");
            x = b.getInt("x");
            y = b.getInt("y");
            pb = Meet.getPublicacao(value);
            usuario = Meet.getUsuario(pb.getIdUsuario());
        }
        imageView = (ImageView) findViewById(R.id.imageViewPhotoShow);
        ImagemControl.setImagemSemCorte(pb.getURL(), imageView, this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(usuario.getNome());
        toolbar.setSubtitle(pb.getDataPublicacaoString());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = (TextView) findViewById(R.id.textViewDescricao);
        textView.setText(pb.getDescricao());

/*
        Animation anim = new ScaleAnimation(0.5f, 1, 0.5f, 1, x, y);
        anim.setDuration(2000); // duration = 300
        getWindow().getDecorView().findViewById(android.R.id.content).startAnimation(anim);
*/
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
