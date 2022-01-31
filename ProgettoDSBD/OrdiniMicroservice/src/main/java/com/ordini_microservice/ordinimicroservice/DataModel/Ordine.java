package com.ordini_microservice.ordinimicroservice.DataModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "Ordine")
public class Ordine {
    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId _id;
    private String stato;
    private List<ObjectId> pietanze;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public List<ObjectId> getPietanze() {
        return pietanze;
    }

    public void setPietanze(List<ObjectId> pietanze) {
        this.pietanze = pietanze;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "_id=" + _id +
                ", stato='" + stato + '\'' +
                ", pietanze=" + pietanze +
                '}';
    }
}

