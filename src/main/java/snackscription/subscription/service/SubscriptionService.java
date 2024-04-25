package snackscription.subscription.service;

import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription save(SubscriptionDTO subscriptionDTO);
    List<SubscriptionDTO> findAll();
    SubscriptionDTO findById(String id);
    Subscription update(SubscriptionDTO subscriptionDTO);
    void delete(String id);
}
