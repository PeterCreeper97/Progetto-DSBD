package com.menu_microservice.menumicroservice.DataModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "Menu")
public class Menu {

    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId _id;
    private String nome;
    private List<ObjectId> pietanze;
    private String stato;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "_id=" + _id +
                ", nome='" + nome + '\'' +
                ", stato='" + stato + '\'' +
                '}';
    }
}

