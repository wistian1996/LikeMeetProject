package br.com.metting.www.likemeet.FirebaseUtils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import br.com.metting.www.likemeet.FirebaseModel.Evento;

/**
 * Created by wisti on 12/04/2017.
 */

public class FBEventoUtils {

    private DatabaseReference dbRefEvento;
    private FirebaseDatabase mDB;
    private String TAG;
    private onEventoChangeListener mOnEventoChangeListener;

    public FBEventoUtils() {
        this.mDB = FirebaseDatabase.getInstance();
        this.dbRefEvento = mDB.getReference("eventos");
        this.TAG = getClass().getSimpleName();
    }

    /**
     * Insere o evento ao banco de dados
     *
     * @param evento
     */
    public void inverirEvento(Evento evento) {
        String key = dbRefEvento.push().getKey();
        dbRefEvento.child(key).setValue(evento);
    }

    /**
     * Inicia o ouvinte do Firebase
     */
    public void iniciarListener() {
        dbRefEvento.addValueEventListener(valueEventEventoInsertListener);
    }

    /**
     * Para o ouvinte do Firebase
     */
    public void pararListener() {
        dbRefEvento.removeEventListener(valueEventEventoInsertListener);
    }

    /**
     * Seta o ouvinte das alterações de Eventos (inseridos, deletados e alterados)
     *
     * @param mOnEventoChangeListener
     */
    public void setOnEventoChangeListener(onEventoChangeListener mOnEventoChangeListener) {
        this.mOnEventoChangeListener = mOnEventoChangeListener;
    }

    /**
     * Ouvinte do Firebase
     */
    private ValueEventListener valueEventEventoInsertListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                GenericTypeIndicator<Map<String, Evento>> t = new GenericTypeIndicator<Map<String, Evento>>() {
                };
                Map<String, Evento> map = dataSnapshot.getValue(t);
                if (map != null) {
                    if (map.size() > 0) {
                        mOnEventoChangeListener.onEventoChange(map);
                    }
                }
            } catch (Exception e) {
                if (e != null)
                    Log.d(TAG, e.getMessage());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    /**
     * Interface que chama o método onEventoChange() para informar que os Eventos foram alterados, passando-os para o outro
     * lado da comunicação, ou seja, a classe que implementa esta interface
     */
    public interface onEventoChangeListener {
        void onEventoChange(Map<String, Evento> eventos);
    }
}
