package com.menu_microservice.menumicroservice.DataModel;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;

public class MenuCreate implements Serializable {

    private ObjectId menu_id;
    private List<ObjectId> pietanze;
    private String stato;
    private String nome;

    public MenuCreate(ObjectId menu_id, List<ObjectId> pietanze, String stato, String nome) {
        this.menu_id = menu_id;
        this.pietanze = pietanze;
        this.stato = stato;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ObjectId getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(ObjectId setMenu_id) {
        this.menu_id = menu_id;
    }

    public List<ObjectId> getPietanze() {
        return pietanze;
    }

    public void setPietanze(List<ObjectId> pietanze) {
        this.pietanze = pietanze;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
