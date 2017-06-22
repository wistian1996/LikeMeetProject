package br.com.metting.www.likemeet;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import br.com.metting.www.likemeet.Class.Meet;
import br.com.metting.www.likemeet.Class.Usuario;
import br.com.metting.www.likemeet.Fragments.Main.listaUsuariosFragment;

public class ActivityUsuarios extends AppCompatActivity {
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle b = getIntent().getExtras();
        int value; // or other values
        int value2;
        if (b != null) {
            value = b.getInt("idUsuario");
            value2 = b.getInt("Tela");

            this.usuario = Usuario.getUsuario(value);
            //alterando imagem do perfil

            if (value2 == 0){
                toolbar.setSubtitle("Seguindo");
                Log.d(getClass().getSimpleName(),"VALUE 0");
            }
            if (value2 == 1){
                toolbar.setSubtitle("Seguidores");
                Log.d(getClass().getSimpleName(),"VALUE 1");
            }


            Fragment fragment = new listaUsuariosFragment(Meet.getListaUsuarios());
            android.support.v4.app.FragmentTransaction fragmentTrasaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTrasaction.replace(R.id.RelativeLayoutUsuarios, fragment);
            fragmentTrasaction.commit();
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
