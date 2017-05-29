package br.com.metting.www.likemeet.Activitys;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.metting.www.likemeet.Adapters.EventosProcurarAdapter;
import br.com.metting.www.likemeet.Adapters.HistoricoAdapter;
import br.com.metting.www.likemeet.Fragments.FragmentHistorico;
import br.com.metting.www.likemeet.R;

public class ActivityPerfil extends AppCompatActivity {

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


        Fragment fragment = new FragmentHistorico();
        android.support.v4.app.FragmentTransaction fragmentTrasaction =
               getSupportFragmentManager().beginTransaction();
        fragmentTrasaction.replace(R.id.nested_scrollViewHistorico, fragment);
        fragmentTrasaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
