package com.pietanze_microservice.pietanzemicroservice.Kafka;

import com.google.gson.Gson;
import com.pietanze_microservice.pietanzemicroservice.DataModel.MenuCreate;
import com.pietanze_microservice.pietanzemicroservice.DataModel.OrderCreate;
import com.pietanze_microservice.pietanzemicroservice.DataModel.Pietanza;
import com.pietanze_microservice.pietanzemicroservice.Repository.PietanzeRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Order;
import java.util.List;

@Component
public class KafkaConsumer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private PietanzeRepository pietanzeRepository;

    @KafkaListener(topics = {"${KAFKA_TOPIC_ORDERS}","${KAFKA_TOPIC_MENU}"}, groupId = "${KAFKA_GROUP_ID}")
    public String listenValidation(ConsumerRecord<String, String> record) {
        if(record != null){
            System.out.println(record.key());
            if(record.key().equals("order_create")) {
                System.out.println("Messaggio ricevuto sul topic order con chiave order_create");
                OrderCreate order_create = new Gson().fromJson(record.value(), OrderCreate.class);

                System.out.println(order_create.toString());

                List<ObjectId> pietanze = order_create.getPietanze();

                for(int j = 0; j < pietanze.size(); j++) {
                    System.out.println(pietanze.get(j));
                }

                System.out.println("PRIMA DEL FOR");

                for(int i = 0; i < pietanze.size(); i++) {

                    System.out.println("DENTRO IL FOR");

                    Pietanza p = pietanzeRepository.findPietanzaBy_id(pietanze.get(i));
                    //System.out.println(p.toString());

                    if(p == null){

                        System.out.println("SONO DENTRO L IF");

                        order_create.setStato("order_failed");
                        kafkaTemplate.send("order", "order_failed", new Gson().toJson(order_create));
                        return "Ordine fallito.";
                    }
                }
                order_create.setStato("order_confirmed");
                kafkaTemplate.send("order", "order_confirmed", new Gson().toJson(order_create));
                return "Ordine confermato";
            }else if(record.key().equals("menu_create")){

                System.out.println("Messaggio ricevuto sul topic menu con chiave menu_create");
                MenuCreate menu_create = new Gson().fromJson(record.value(), MenuCreate.class);

                System.out.println(menu_create.toString());

                List<ObjectId> pietanze = menu_create.getPietanze();

                for(int j = 0; j < pietanze.size(); j++) {
                    System.out.println(pietanze.get(j));
                }

                System.out.println("PRIMA DEL FOR");

                for(int i = 0; i < pietanze.size(); i++) {

                    System.out.println("DENTRO IL FOR");

                    Pietanza p = pietanzeRepository.findPietanzaBy_id(pietanze.get(i));

                    if(p == null){

                        System.out.println("SONO DENTRO L IF");

                        menu_create.setStato("menu_not_valid");
                        kafkaTemplate.send("menu", "menu_not_valid", new Gson().toJson(menu_create));
                        return "Menu non valido.";
                    }
                }
                menu_create.setStato("menu_validate");
                kafkaTemplate.send("menu", "menu_validate", new Gson().toJson(menu_create));
                return "Menu convalidato";


            }


        }
        return null;
    }



}
