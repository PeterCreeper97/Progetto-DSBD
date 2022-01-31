package com.pietanze_microservice.pietanzemicroservice.DataModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*Pietanza: classe realizzata al fine di definire una struttura relativa al
nome e prezzo delle pietanze che saranno inserite all'interno del menu e che potranno essere
acquistate all'interno di un ordine*/

@Document(collection = "Pietanza")
public class Pietanza {

    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId _id;
    private String nome;
    private double prezzo;

    public ObjectId getPietanza_id() {
        return _id;
    }

    public Pietanza setPietanza_id(ObjectId pietanza_id) {
        this._id = pietanza_id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Pietanza{" +
                "pietanza_id=" + _id +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                '}';
    }
}

