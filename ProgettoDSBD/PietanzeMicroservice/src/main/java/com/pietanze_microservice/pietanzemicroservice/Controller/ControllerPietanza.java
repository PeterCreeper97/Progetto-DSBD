package com.pietanze_microservice.pietanzemicroservice.Controller;


import com.pietanze_microservice.pietanzemicroservice.DataModel.Pietanza;
import com.pietanze_microservice.pietanzemicroservice.Exceptions.PietanzaNotFoundException;
import com.pietanze_microservice.pietanzemicroservice.Repository.PietanzeRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(path="/pietanze")
public class ControllerPietanza {

    @Autowired
    private PietanzeRepository repository;

    Counter visitCounter;

    public ControllerPietanza(MeterRegistry registry) {
        visitCounter = Counter.builder("visit_counter")
                .description("Number of visits to the site")
                .register(registry);
    }

    @PostMapping(path="/")
    public @ResponseBody ResponseEntity<Object> addPietanza(@RequestBody Pietanza pietanza){
        visitCounter.increment();

        Pietanza pietanza1 = repository.findByNome(pietanza.getNome());

        if(pietanza1 == null){
            repository.save(pietanza);
            return new ResponseEntity<Object>("Pietanza inserita correttamente.", HttpStatus.OK);
        }else{
            return new ResponseEntity<Object>("Impossibile inserire due pietanze con lo stesso nome", HttpStatus.OK);
        }

    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody
    ResponseEntity<Object> deletePietanza(@PathVariable ObjectId id){
        visitCounter.increment();

        Pietanza pietanza = repository.findPietanzaBy_id(id);

        if (pietanza != null) {
            repository.delete(pietanza);
            return new ResponseEntity<Object>("Pietanza cancellata correttamente.", HttpStatus.OK);
        }else{
            throw new PietanzaNotFoundException();
        }

    }

    @GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getAllPietanze(){
        visitCounter.increment();

        List<Pietanza> pietanza = repository.findAll();

        if (pietanza.size() != 0) {
            return new ResponseEntity<Object>(pietanza, HttpStatus.OK);
        }else{
            throw new PietanzaNotFoundException();
        }

    }

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getPietanzaById(@PathVariable ObjectId id){
        visitCounter.increment();

        Pietanza pietanza = repository.findPietanzaBy_id(id);

        if (pietanza != null) {
            return new ResponseEntity<Object>(pietanza, HttpStatus.OK);
        }else{
            throw new PietanzaNotFoundException();
        }

    }

}
