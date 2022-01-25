package com.menu_microservice.menumicroservice.DataModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "Menu")
public class Menu {

    private ObjectId menu_id;
    private String nome;
    private List<ObjectId> pietanze;

    public ObjectId getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(ObjectId menu_id) {
        this.menu_id = menu_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ObjectId> getPietanze() {
        return pietanze;
    }

    public void setPietanze(List<ObjectId> pietanze) {
        this.pietanze = pietanze;
    }
}

