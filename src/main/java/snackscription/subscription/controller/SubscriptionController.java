package snackscription.subscription.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.service.SubscriptionService;
import snackscription.subscription.utils.JWTUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/subscription")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final JWTUtils jwtUtils;

    public SubscriptionController(SubscriptionService subscriptionService, JWTUtils jwtUtils) {
        this.subscriptionService = subscriptionService;
        this.jwtUtils = jwtUtils;
    }

    private void validateToken(String token) throws IllegalAccessException {
        String jwt = token.replace("Bearer ", "");
        if (!jwtUtils.isTokenValid(jwt)) {
            throw new IllegalAccessException("You have no permission.");
        }
    }

    @GetMapping("")
    public ResponseEntity<String> main() {
        return ResponseEntity.ok("Snackscription - Subscription Management API");
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Subscription>> create(@RequestHeader(value = "Authorization") String token,
                                                                  @RequestBody SubscriptionDTO subscriptionDTO) throws IllegalAccessException {
        validateToken(token);
        return subscriptionService.save(subscriptionDTO)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<List<SubscriptionDTO>>> findAll(@RequestHeader(value = "Authorization") String token) throws IllegalAccessException {
        validateToken(token);
        return subscriptionService.findAll()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<SubscriptionDTO>> findById(@RequestHeader(value = "Authorization") String token,
                                                                       @PathVariable String id) throws IllegalAccessException {
        validateToken(token);
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }

        return subscriptionService.findById(id)
                .thenApply(optionalSubscription ->
                        optionalSubscription.map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build()));
    }

    @PatchMapping("/update")
    public CompletableFuture<ResponseEntity<Subscription>> update(@RequestHeader(value = "Authorization") String token,
                                                                  @RequestBody SubscriptionDTO subscriptionDTO) throws IllegalAccessException {
        validateToken(token);
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
    public CompletableFuture<ResponseEntity<String>> delete(@RequestHeader(value = "Authorization") String token,
                                                            @PathVariable String id) throws IllegalAccessException {
        validateToken(token);
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
