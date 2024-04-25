package snackscription.subscription.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
        entityManager.persist(subscription);
        return subscription;
    }

    @Transactional
    public List<Subscription> findAll(){
        String jpql = "SELECT s FROM Subscription s";
        TypedQuery<Subscription> query = entityManager.createQuery(jpql, Subscription.class);
        return query.getResultList();
    }

    @Transactional
    public Optional<Subscription> findById(String id){
        Subscription subscription = entityManager.find(Subscription.class, id);
        return Optional.ofNullable(subscription);
    }

    @Transactional
    public Subscription update(Subscription subscription){
        return entityManager.merge(subscription);
    }

    @Transactional
    public void delete(String id){
        Subscription subscription = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
        entityManager.remove(subscription);
    }
}
