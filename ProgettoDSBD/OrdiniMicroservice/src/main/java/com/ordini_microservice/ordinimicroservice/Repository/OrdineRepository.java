package com.ordini_microservice.ordinimicroservice.Repository;


import com.ordini_microservice.ordinimicroservice.DataModel.Ordine;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdineRepository extends MongoRepository<Ordine, ObjectId>{

    public Ordine findBy_id(ObjectId _id);

    public Ordine findOrdineBy_id(ObjectId _id);

}
