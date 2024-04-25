package snackscription.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snackscription.subscription.dto.SubscriptionDTO;

import snackscription.subscription.model.Subscription;
import snackscription.subscription.repository.SubscriptionRepository;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription save(SubscriptionDTO subscriptionDTO){
        return null;
    }

    @Override
    public List<SubscriptionDTO> findAll(){
       return null;
    }

    @Override
    public SubscriptionDTO findById(String id){
        return null;
    }

    @Override
    public Subscription update(SubscriptionDTO subscriptionDTO){
        return null;
    }

    @Override
    public void delete(String id){

    }
}
