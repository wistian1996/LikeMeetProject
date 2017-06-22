package br.com.metting.www.likemeet.Control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.ceylonlabs.imageviewpopup.ImagePopup;


import java.io.File;
import java.net.UnknownHostException;

import br.com.metting.www.likemeet.Class.Evento;
import br.com.metting.www.likemeet.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by wisti on 11/06/2017.
 */

public class ImagemControl {


    private static ImagePopup imagePopup;

    public static void setImagem(String local, ImageView imageView, Context context) {
        Log.d(context.getClass().getSimpleName(), "Set Imagem");
        File extStore = Environment.getExternalStorageDirectory();
        String mPath = extStore.getAbsolutePath() + local;

        Glide.with(context)
                .load(Uri.fromFile(new File(mPath)))
                .centerCrop()
                .into(imageView);
    }

    public static void setImagemSemCorte(String local, final ImageView imageView, Context context) {
        Log.d(context.getClass().getSimpleName(), "Set Imagem");
        File extStore = Environment.getExternalStorageDirectory();
        String mPath = extStore.getAbsolutePath() + local;

        Glide.with(context)
                .load(Uri.fromFile(new File(mPath)))
                .fitCenter()
                .into(imageView);
    }

    public static void setImagemCircular(String local, ImageView imageView, Context context) {
        File extStore = Environment.getExternalStorageDirectory();
        String mPath = extStore.getAbsolutePath() + local;

        Glide.with(context)
                .load(Uri.fromFile(new File(mPath)))
                .centerCrop().transform(new CircleTransform(context))
                .into(imageView);
    }

    public static void carregarImagemMap(AQuery aQuery, ImageView imagemMapa, ProgressBar progressBarMapa, ImageOptions imageOptions, Evento e) {
        //utilizado para que eu possa fazer uma conexao externa com a internet
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //pegando imagem da internet
            String getMapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=14&size=560x240&markers=size:mid|color:red|"
                    + e.getLocal()
                    + "&sensor=false";
            //    url = new URL(getMapURL);
            aQuery.id(imagemMapa.getId()).progress(progressBarMapa).image(getMapURL, imageOptions);
        }
    }

    public static void carregarImagemComProgressEzoom(String local, Context c, final ImageView imageView, final ProgressBar progressBar) {
        File extStore = Environment.getExternalStorageDirectory();
        String mPath = extStore.getAbsolutePath() + local;


        Glide.with(c).load(Uri.fromFile(new File(mPath))).
                listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
                        photoViewAttacher.setZoomable(true);
                        photoViewAttacher.update();
                        return false;
                    }
                }).into(imageView);
    }

    public static void carregarImagemComProgress(String local, Context c, ImageView imageView, final ProgressBar progressBar) {

        File extStore = Environment.getExternalStorageDirectory();
        String mPath = extStore.getAbsolutePath() + local;

        Glide.with(c).load(Uri.fromFile(new File(mPath))).
                listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imageView);
    }
}
