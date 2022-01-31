package com.pietanze_microservice.pietanzemicroservice.DataModel;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;

public class OrderCreate implements Serializable {

    private ObjectId order_id;
    private List<ObjectId> pietanze;
    private String stato;

    public OrderCreate(ObjectId order_id, List<ObjectId> pietanze, String stato) {
        this.order_id = order_id;
        this.pietanze = pietanze;
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "OrderCreate{" +
                "order_id=" + order_id +
                ", pietanze=" + pietanze +
                ", stato='" + stato + '\'' +
                '}';
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public ObjectId getOrder_id() {
        return order_id;
    }

    public void setOrder_id(ObjectId order_id) {
        this.order_id = order_id;
    }

    public List<ObjectId> getPietanze() {
        return pietanze;
    }

    public void setPietanze(List<ObjectId> pietanze) {
        this.pietanze = pietanze;
    }


}
