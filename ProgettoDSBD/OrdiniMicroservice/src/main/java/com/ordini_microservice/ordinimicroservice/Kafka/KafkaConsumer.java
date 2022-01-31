package com.ordini_microservice.ordinimicroservice.Kafka;


import com.google.gson.Gson;
import com.ordini_microservice.ordinimicroservice.DataModel.OrderCreate;
import com.ordini_microservice.ordinimicroservice.DataModel.Ordine;
import com.ordini_microservice.ordinimicroservice.Repository.OrdineRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    OrdineRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "${KAFKA_TOPIC_ORDERS}", groupId = "${KAFKA_GROUP_ID}")
    public void listenOrderValidation(ConsumerRecord<String, String> record) {
        if(record != null){

            if(record.key().equals("order_confirmed")) {

                OrderCreate ordine = new Gson().fromJson(record.value(), OrderCreate.class);

                Ordine order_confirmed = new Ordine();

                order_confirmed.set_id(ordine.getOrder_id());
                order_confirmed.setPietanze(ordine.getPietanze());
                order_confirmed.setStato("order_confirmed");

                System.out.println("KAFKA LISTENER ORDINI: " + ordine.toString());

                Ordine prova = repository.save(order_confirmed);

                System.out.println("KAFKA LISTENER ORDINI DOPO SAVE: " + prova.toString());

            }else{

                OrderCreate ordine = new Gson().fromJson(record.value(), OrderCreate.class);

                Ordine order_failed = new Ordine();

                order_failed.set_id(ordine.getOrder_id());
                order_failed.setPietanze(ordine.getPietanze());
                order_failed.setStato("order_failed");

                System.out.println("KAFKA LISTENER ORDINI: " + ordine.toString());

                Ordine prova = repository.save(order_failed);

                System.out.println("KAFKA LISTENER ORDINI DOPO SAVE: " + prova.toString());

            }

        }

    }

}
