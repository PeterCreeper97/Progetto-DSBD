package com.menu_microservice.menumicroservice.Controller;


import com.menu_microservice.menumicroservice.DataModel.Menu;
import com.menu_microservice.menumicroservice.Repository.MenuRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class ControllerMenu {

    @Autowired
    private MenuRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    Counter visitCounter;

    public ControllerMenu(MeterRegistry registry) {
        visitCounter = Counter.builder("visit_counter")
                .description("Number of visits to the site")
                .register(registry);
    }

    @PostMapping(path="/menu")
    public @ResponseBody String addMenu(@RequestBody Menu menu){
        visitCounter.increment();
        repository.save(menu);
        return "Menu creato.";
    }


    @GetMapping(path="/menu/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getMenuByNome(@PathVariable("nome") String nome){
        visitCounter.increment();
        Menu menu = repository.findByNome(nome);
        /*if (order != null && (userId.equals("0") || order.getUserId().equals(userId))) {
            return new ResponseEntity<Object>(order, HttpStatus.OK);
        }else{
            throw new OrderNotFoundException();
        }*/

        return new ResponseEntity<Object>(menu, HttpStatus.OK);
    }




/*
    @GetMapping(path="/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getOrderById(@PathVariable("id") ObjectId id,
                                        @RequestHeader("X-User-ID") String userId){

            TotalOrder order = repository.findBy_id(id);
            if (order != null && (userId.equals("0") || order.getUserId().equals(userId))) {
                return new ResponseEntity<Object>(order, HttpStatus.OK);
            }else{
                throw new OrderNotFoundException();
            }
    }

    @GetMapping(path="/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getOrdersbyIdUser(@RequestHeader("X-User-ID") String userId,
                                                                  @RequestParam(required = false) Integer per_page,
                                                                  @RequestParam(required = false) Integer page){
        List<TotalOrder> orders = repository.findByUserId(userId);
        if(orders.size() != 0) {

            switch (userId) {
                case "0":
                    if (page != null && per_page != null) {
                        orders = paginationService.getAllOrders(page, per_page);
                    } else {
                        orders = repository.findAll();
                    }
                    break;
                default:
                    if (page != null && per_page != null) {
                        orders = paginationService.getOrdersByUserID(page, per_page, userId);
                    } else {
                        //nel caso id != 0 e senza pagination
                        orders = repository.findByUserId(userId);
                    }
                    break;
            }

            return new ResponseEntity<Object>(orders, HttpStatus.OK);

        }else{
            throw new OrderNotFoundException();
        }

    }

    @GetMapping(path="/ping")
    public @ResponseBody
    ResponseEntity<Object> ping(){
            try {
                Document answer = mongoTemplate.getDb().runCommand(new BasicDBObject("ping", "1"));
                return new ResponseEntity<Object>(new StatusMicroservice("up", "up"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<Object>(new StatusMicroservice("up", "down"), HttpStatus.OK);
            }
    }

    @GetMapping(path="/ping1")
    public @ResponseBody String pingKub(){
        return "Pong";
    }
*/
}
