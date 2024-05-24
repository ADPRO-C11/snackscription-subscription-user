package snackscription.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import snackscription.subscription.dto.DTOMapper;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Async
    public CompletableFuture<Subscription> save(SubscriptionDTO subscriptionDTO) {
        try{
            Subscription subscription = DTOMapper.convertDTOtoModel(subscriptionDTO);
            return CompletableFuture.completedFuture(subscriptionRepository.save(subscription));
        } catch (Exception e){
            throw new IllegalArgumentException("Invalid Request", e);
        }
    }

    @Override
    @Async
    public CompletableFuture<List<SubscriptionDTO>> findAll() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<SubscriptionDTO> dtos = subscriptions.stream()
                .map(DTOMapper::convertModelToDto)
                .toList();
        return CompletableFuture.completedFuture(dtos);
    }
    @Override
    @Async
    public CompletableFuture<Optional<SubscriptionDTO>> findById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return subscriptionRepository.findById(id)
                .map(subscription -> CompletableFuture.completedFuture(Optional.of(DTOMapper.convertModelToDto(subscription))))
                .orElse(CompletableFuture.completedFuture(Optional.empty()));
    }

    @Override
    @Async
    public CompletableFuture<List<SubscriptionDTO>> findByUser(String userId){
        List<Subscription> subscriptions = subscriptionRepository.findByUser(userId);
        List<SubscriptionDTO> dtos = subscriptions.stream()
                .map(DTOMapper::convertModelToDto)
                .toList();
        return CompletableFuture.completedFuture(dtos);
    }

    @Override
    @Async
    public CompletableFuture<Subscription> update(SubscriptionDTO subscriptionDTO) {
        if (subscriptionDTO == null) {
            throw new IllegalArgumentException("Subscription cannot be null");
        }

        return subscriptionRepository.findById(subscriptionDTO.getId())
                .map(subscription -> {
                    DTOMapper.updateSubscription(subscription, subscriptionDTO);
                    return CompletableFuture.completedFuture(subscriptionRepository.update(subscription));
                })
                .orElseThrow(() -> new IllegalArgumentException("Subscription isn't found"));
    }

    @Override
    @Async
    public CompletableFuture<Void> delete(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (subscriptionRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Subscription not found");
        }
        subscriptionRepository.delete(id);
        return CompletableFuture.completedFuture(null);
    }
}
