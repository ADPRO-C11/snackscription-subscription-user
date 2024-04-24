package snackscription.subscription.repository;

import org.springframework.stereotype.Repository;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.service.SubscriptionService;

import java.util.List;

@Repository
public class SubscriptionRepository {
    private List<Subscription> subscriptionData;

    public List<Subscription> findAll(){
        return subscriptionData;
    }

    public Subscription findById(String id){
        return null;
    }

    public Subscription create(){
        return null;
    }

    public Subscription edit(String id){
        return null;
    }
}
