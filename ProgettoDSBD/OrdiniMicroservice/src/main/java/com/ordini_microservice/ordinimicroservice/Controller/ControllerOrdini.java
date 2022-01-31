package com.ordini_microservice.ordinimicroservice.Controller;

import com.google.gson.Gson;
import com.ordini_microservice.ordinimicroservice.DataModel.OrderCreate;
import com.ordini_microservice.ordinimicroservice.DataModel.Ordine;
import com.ordini_microservice.ordinimicroservice.Exceptions.OrderNotFoundException;
import com.ordini_microservice.ordinimicroservice.Repository.OrdineRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ControllerOrdini {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private OrdineRepository repository;

    private String esito;

    Counter visitCounter;

    public ControllerOrdini(MeterRegistry registry) {
        visitCounter = Counter.builder("visit_counter")
                .description("Number of visits to the site")
                .register(registry);
    }


    @PostMapping(path="/ordine")
    public @ResponseBody String addOrdine(@RequestBody Ordine ordine) throws InterruptedException {

        visitCounter.increment();

        Ordine new_ordine = repository.save(ordine);
        OrderCreate order = new OrderCreate(new_ordine.get_id(), ordine.getPietanze(), "order_create");
        kafkaTemplate.send("order", "order_create", new Gson().toJson(order));

        System.out.println("ORDINE: " + new_ordine.get_id());

        Thread.sleep(10000);

        Ordine order_finale = repository.findOrdineBy_id(new_ordine.get_id());

        if(order_finale != null && order_finale.getStato().equals("order_confirmed")){

            order_finale.setStato("order_confirmed");

            repository.save(order_finale);

            esito = "Ordine creato correttamente";

        }else{

            repository.deleteById(new_ordine.get_id());

            esito = "Ordine fallito";

        }

        return esito;
    }


    @GetMapping(path="/ordine/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getOrdineByID(@PathVariable("id") ObjectId id){

        visitCounter.increment();

        Ordine ordine = repository.findBy_id(id);

        if (ordine != null) {
            return new ResponseEntity<Object>(ordine, HttpStatus.OK);
        }else{
            throw new OrderNotFoundException();
        }

    }

    @GetMapping(path="/ordine", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getAllOrdini(){

        visitCounter.increment();

        List<Ordine> ordine = repository.findAll();

        if (ordine.size() != 0) {
            return new ResponseEntity<Object>(ordine, HttpStatus.OK);
        }else{
            throw new OrderNotFoundException();
        }

    }

    @DeleteMapping(path="/ordine/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> deleteOrdine(@PathVariable("id") ObjectId id){

        visitCounter.increment();

        Ordine ordine = repository.findBy_id(id);

        if (ordine != null) {
            repository.deleteById(id);

            return new ResponseEntity<Object>("Cancellazione dell'ordine effettuata correttamente.", HttpStatus.OK);
        }else{
            throw new OrderNotFoundException();
        }

    }

}
