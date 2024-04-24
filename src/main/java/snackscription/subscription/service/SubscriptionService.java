package snackscription.subscription.service;

import snackscription.subscription.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> findAll();
    Subscription findById(String id);
    Subscription create();
    Subscription edit(String id);
}
