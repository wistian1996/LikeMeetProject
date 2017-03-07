package br.com.metting.www.likemeet.Class;

import java.util.ArrayList;

/**
 * Created by wisti on 03/12/2016.
 */
public class Amigo {
    int id;
    String nome;
    String tel;

    public Amigo(int id, String nome, String tel) {
        this.id = id;
        this.nome = nome;
        this.tel = tel;
    }

    public Amigo() {

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTel() {
        return tel;
    }


    public ArrayList<Amigo> getLista() {
        Amigo a2 = new Amigo(1, "Franklin Wistian", "75888445224");
        Amigo a3 = new Amigo(2, "Clevia lima", "95874521565");
        Amigo a4 = new Amigo(3, "Alexandre Andrade", "7854589655");
        Amigo a5 = new Amigo(4, "Elder Sales", "9854215698");
        Amigo a6 = new Amigo(5, "Like a boss", "1234567898");
        Amigo a7 = new Amigo(6, "Josete", "7599854145");

        ArrayList<Amigo> list = new ArrayList<>();
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(a7);
        return list;

    }

    public Amigo getAmigoTel(String tel){
        for (Amigo listaAmigo: getLista()) {
            if (listaAmigo.getTel().equals(tel)){
                return listaAmigo;
            }
        }
        return null;
    }

    public  Amigo getAmigoId(int id){
        for (Amigo listaAmigo: getLista()) {
            if (listaAmigo.getId() == id){
                return listaAmigo;
            }
        }
        return null;
    }
}

