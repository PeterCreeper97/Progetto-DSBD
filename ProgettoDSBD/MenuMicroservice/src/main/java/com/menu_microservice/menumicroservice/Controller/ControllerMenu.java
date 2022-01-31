package com.menu_microservice.menumicroservice.Controller;


import com.google.gson.Gson;
import com.menu_microservice.menumicroservice.DataModel.Menu;
import com.menu_microservice.menumicroservice.DataModel.MenuCreate;
import com.menu_microservice.menumicroservice.Repository.MenuRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ControllerMenu {

    @Autowired
    private MenuRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String esito;

    Counter visitCounter;

    public ControllerMenu(MeterRegistry registry) {
        visitCounter = Counter.builder("visit_counter")
                .description("Number of visits to the site")
                .register(registry);
    }

    @PostMapping(path="/menu")
    public @ResponseBody String addMenu(@RequestBody Menu menu) throws InterruptedException {
        visitCounter.increment();

        System.out.println(menu.getNome());
        Menu menu1 = repository.findByNome(menu.getNome());

        if(menu1 == null){
            System.out.println( "STAMPA PRIMA DEL SAVE CONTROLLER :" + menu.toString());
            Menu new_menu = repository.save(menu);
            System.out.println( "STAMPA DOPO IL SAVE CONTROLLER :" + new_menu.toString());

            MenuCreate menu_create = new MenuCreate(new_menu.get_id(), menu.getPietanze(), "menu_create", menu.getNome());
            kafkaTemplate.send("menu", "menu_create", new Gson().toJson(menu_create));

            System.out.println("MENU: " + new_menu.get_id());

            Thread.sleep(10000);

            Menu menu_finale = repository.findMenuBy_id(new_menu.get_id());

            if (menu_finale != null && menu_finale.getStato().equals("menu_validate")) {

                menu_finale.setStato("menu_validate");

                repository.save(menu_finale);

                esito = "Menu creato correttamente.";

            } else {

                repository.deleteById(new_menu.get_id());

                esito = "Impossibile creare il menù, non tutte le pietanze inserite sono valide.";

            }

            return esito;
        }else{

            return "Impossibile creare un menù con il nome uguale ad uno già esistente.";

        }
    }


    @GetMapping(path="/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getAllMenu() {
        visitCounter.increment();

        List<Menu> menu = repository.findAll();

        if (menu.size() != 0) {
            return new ResponseEntity<Object>(menu, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Non sono presenti menù salvati.", HttpStatus.NOT_FOUND);
        }
    }
}
