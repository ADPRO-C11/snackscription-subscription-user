package snackscription.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.repository.SubscriptionRepository;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> findAll(){
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription findById(String id){
        return null;
    }

    @Override
    public Subscription create(){
        return null;
    }

    @Override
    public Subscription edit(String id){
        return null;
    }
}
