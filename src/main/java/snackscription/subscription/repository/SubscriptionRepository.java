package snackscription.subscription.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import snackscription.subscription.model.Subscription;

import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionRepository {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Subscription save(Subscription subscription) {
        return null;
    }

    @Transactional
    public List<Subscription> findAll(){
        return null;
    }

    @Transactional
    public Optional<Subscription> findById(String id){
        return null;
    }

    @Transactional
    public Subscription update(Subscription subscription){
        return null;
    }

    @Transactional
    public void delete(String id){
    }
}
