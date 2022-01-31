package com.menu_microservice.menumicroservice.Kafka;


import com.google.gson.Gson;
import com.menu_microservice.menumicroservice.DataModel.Menu;
import com.menu_microservice.menumicroservice.DataModel.MenuCreate;
import com.menu_microservice.menumicroservice.Repository.MenuRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    MenuRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "${KAFKA_TOPIC_MENU}", groupId = "${KAFKA_GROUP_ID}")
    public void listenOrderValidation(ConsumerRecord<String, String> record) {
        if(record != null){

            if(record.key().equals("menu_validate")) {

                MenuCreate menu = new Gson().fromJson(record.value(), MenuCreate.class);

                Menu menu_validate = new Menu();

                menu_validate.set_id(menu.getMenu_id());
                menu_validate.setPietanze(menu.getPietanze());
                menu_validate.setNome(menu.getNome());
                menu_validate.setStato("menu_validate");

                System.out.println("KAFKA LISTENER MENU: " + menu.toString());

                Menu prova = repository.save(menu_validate);

                System.out.println("KAFKA LISTENER MENU DOPO SAVE: " + prova.toString());

            }else{

                MenuCreate menu = new Gson().fromJson(record.value(), MenuCreate.class);

                Menu menu_not_valid = new Menu();

                menu_not_valid.set_id(menu.getMenu_id());
                menu_not_valid.setPietanze(menu.getPietanze());
                menu_not_valid.setStato("menu_not_valid");

                System.out.println("KAFKA LISTENER MENU: " + menu.toString());

                Menu prova = repository.save(menu_not_valid);

                System.out.println("KAFKA LISTENER ORDINI DOPO SAVE: " + prova.toString());

            }

        }

    }

}
