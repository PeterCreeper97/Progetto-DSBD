package com.pietanze_microservice.pietanzemicroservice.Repository;

import com.pietanze_microservice.pietanzemicroservice.DataModel.Pietanza;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PietanzeRepository extends MongoRepository<Pietanza, ObjectId>{

    public Pietanza findByNome(String nome);

    public Pietanza findBy_id(ObjectId _id);

    public Pietanza findPietanzaBy_id(ObjectId _id);
}
