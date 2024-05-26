package snackscription.subscription.service;

import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface SubscriptionService {
    CompletableFuture<Subscription> save(SubscriptionDTO subscriptionDTO);
    CompletableFuture<List<SubscriptionDTO>> findAll();
    CompletableFuture<List<SubscriptionDTO>> findByUser(String id);
    CompletableFuture<Optional<SubscriptionDTO>> findById(String id);
    CompletableFuture<Subscription> update(SubscriptionDTO subscriptionDTO);
    CompletableFuture<Void> delete(String id);
}
