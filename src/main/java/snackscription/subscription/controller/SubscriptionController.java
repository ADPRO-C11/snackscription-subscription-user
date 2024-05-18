package snackscription.subscription.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.service.SubscriptionService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/subscription")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("")
    public ResponseEntity<String> main(){
        return ResponseEntity.ok("Snackscription - Subscription Management API");
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Subscription>> create(@RequestBody SubscriptionDTO subscriptionDTO) {
        return subscriptionService.save(subscriptionDTO)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<List<SubscriptionDTO>>> findAll() {
        return subscriptionService.findAll()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<SubscriptionDTO>> findById(@PathVariable String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }

        return subscriptionService.findById(id)
                .thenApply(optionalSubscription ->
                        optionalSubscription.map(subscription -> ResponseEntity.ok(subscription))
                                .orElse(ResponseEntity.notFound().build()));
    }

    @PatchMapping("/update")
    public CompletableFuture<ResponseEntity<Subscription>> update(@RequestBody SubscriptionDTO subscriptionDTO) {
        if (subscriptionDTO.getId() == null || subscriptionDTO.getId().isEmpty()) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }

        return subscriptionService.findById(subscriptionDTO.getId())
                .thenCompose(subscription -> {
                    if (subscription.isEmpty()) {
                        return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
                    } else {
                        return subscriptionService.update(subscriptionDTO)
                                .thenApply(ResponseEntity::ok);
                    }
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> delete(@PathVariable String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }

        return subscriptionService.delete(id)
                .thenApply(result -> ResponseEntity.ok("DELETE SUCCESS"))
                .exceptionally(ex -> ResponseEntity.notFound().build());
    }
}
