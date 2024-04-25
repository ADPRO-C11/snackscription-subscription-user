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
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController (SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Subscription> create(@RequestBody SubscriptionDTO subscriptionDTO){
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<List<SubscriptionDTO>> findAll(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> findById(@PathVariable String id){
        return null;
    }

    @PatchMapping("/update")
    public ResponseEntity<Subscription> update(@RequestBody SubscriptionDTO subscriptionDTO){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return null;
    }
}
