package snackscription.subscription.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.service.SubscriptionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subscription")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController (SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Subscription> create(@RequestBody SubscriptionDTO subscriptionDTO){
        Subscription subscription;
        try {
            subscription = subscriptionService.save(subscriptionDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(subscription);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SubscriptionDTO>> findAll(){
        return new ResponseEntity<>(subscriptionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> findById(@PathVariable String id){
        try {
            UUID.fromString(id);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        SubscriptionDTO subscription;
        try {
            subscription = subscriptionService.findById(id);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscription);
    }

    @PatchMapping("/update")
    public ResponseEntity<Subscription> update(@RequestBody SubscriptionDTO subscriptionDTO){
        Subscription subscription;

        if(subscriptionDTO.getId() == null || subscriptionDTO.getId().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try{
            subscriptionService.findById(subscriptionDTO.getId());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        try {
            subscription = subscriptionService.update(subscriptionDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(subscription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        try {
            UUID.fromString(id);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        try {
            subscriptionService.delete(id);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
    }
}
