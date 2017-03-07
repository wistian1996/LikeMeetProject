package br.com.metting.www.likemeet.Class;

import java.util.ArrayList;

/**
 * Created by wisti on 03/12/2016.
 */
public class Categoria {
    int id;
    String nome;
    public static ArrayList<Categoria> list;


    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;

    }

    public static ArrayList<Categoria> getLista() {
        Categoria c1 = new Categoria(1, "Festas");
        Categoria c2 = new Categoria(2, "Bares e boates");
        Categoria c3 = new Categoria(3, "Jogos");
        Categoria c4 = new Categoria(4, "Esportes");
        Categoria c5 = new Categoria(5, "Casuais");
        Categoria c6 = new Categoria(6, "Amigos");
        Categoria c7 = new Categoria(7, "Trabalho , Escola");

        list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        list.add(c6);
        list.add(c7);
        return list;
    }

    public static int getCateriaPorNome(String nome) {
        int id = 0;
        for (Categoria lista : list
                ) {
            if (nome.equals(lista.getNome())) id= lista.getId();
        }
        return id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
