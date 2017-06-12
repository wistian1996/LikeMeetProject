package br.com.metting.www.likemeet.Control;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by wisti on 11/06/2017.
 */

public class ImagemControl {

    public static void setImagem(String local, ImageView imageView) {
        // imagem da memoria
        File extStore = Environment.getExternalStorageDirectory();
        //String mPath = extStore.getAbsolutePath() + "/DCIM/100AVIARY/Clevia.jpg";
        String mPath = extStore.getAbsolutePath() + local;
        imageView.setImageURI(Uri.fromFile(new File(mPath)));
    }
}
