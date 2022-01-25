package com.menu_microservice.menumicroservice.Repository;

import com.menu_microservice.menumicroservice.DataModel.Menu;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuRepository extends MongoRepository<Menu, ObjectId>{


    public Menu findByNome(String nome);
}
