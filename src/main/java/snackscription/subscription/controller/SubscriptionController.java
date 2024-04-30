package snackscription.subscription.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/subscription")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController (SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Subscription> create(@RequestBody SubscriptionDTO subscriptionDTO){
        return new ResponseEntity<>(subscriptionService.save(subscriptionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SubscriptionDTO>> findAll(){
        return new ResponseEntity<>(subscriptionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> findById(@PathVariable String id){
        return new ResponseEntity<>(subscriptionService.findById(id), HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<Subscription> update(@RequestBody SubscriptionDTO subscriptionDTO){
        return new ResponseEntity<>(subscriptionService.update(subscriptionDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        subscriptionService.delete(id);
        return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
    }
}
