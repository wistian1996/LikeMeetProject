package br.com.metting.www.likemeet.Control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import br.com.metting.www.likemeet.Activitys.ActivityVerFoto;
import br.com.metting.www.likemeet.R;

/**
 * Created by wisti on 16/06/2017.
 */

public class PublicacaoControl {

    public static void carregarPublicacao(int idPublicacao, int x, int y, Context c) {
        ActivityVerFoto activityVerFoto = new ActivityVerFoto();
        Intent intent = new Intent(c, activityVerFoto.getClass());
        Bundle b = new Bundle();
        //inserindo o ID do usuario que esta logado e passando id como parametro
        b.putInt("idPublicacao", idPublicacao);
        b.putInt("x", x);
        b.putInt("y", y);
        //Your id
        intent.putExtras(b); //Put your id to your next Intent
        c.startActivity(intent);
        ((Activity) c).overridePendingTransition(R.animator.zoom_in, R.anim.fade_out);
    }
}
