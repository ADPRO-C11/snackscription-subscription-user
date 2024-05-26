package snackscription.subscription.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snackscription.subscription.factory.SubscriptionFactory;
import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.model.Subscription;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private SubscriptionRepository subscriptionRepository;

    private final SubscriptionFactory subscriptionFactory = new SubscriptionFactory();

    @Test
    void testSave() {
        Subscription subscription = subscriptionFactory.create("MONTHLY", "userId", "boxId", new ShippingAddress());
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        assertEquals(subscription, savedSubscription);
        verify(entityManager, times(1)).persist(subscription);
    }

    @Test
    void testFindAll() {
        Subscription subscription1 = subscriptionFactory.create();
        Subscription subscription2 = subscriptionFactory.create();

        TypedQuery<Subscription> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT s FROM Subscription s", Subscription.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(subscription1, subscription2));

        List<Subscription> subscriptions = subscriptionRepository.findAll();

        assertEquals(2, subscriptions.size());
        verify(entityManager, times(1)).createQuery("SELECT s FROM Subscription s", Subscription.class);
        verify(query, times(1)).getResultList();
    }

    @Test
    void testFindById() {
        Subscription subscription = subscriptionFactory.create();
        subscription.setId("1");

        when(entityManager.find(Subscription.class, "1")).thenReturn(subscription);

        Optional<Subscription> optionalSubscription = subscriptionRepository.findById("1");

        assertEquals(Optional.of(subscription), optionalSubscription);
        verify(entityManager, times(1)).find(Subscription.class, "1");
    }

    @Test
    void testFindByIdSubscriptionNotFound() {
        when(entityManager.find(Subscription.class, "nonexistentId")).thenReturn(null);

        assertNull(subscriptionRepository.findById("nonexistentId").orElse(null));

        verify(entityManager, times(1)).find(Subscription.class, "nonexistentId");
    }

    @Test
    void testUpdate() {
        Subscription subscription = subscriptionFactory.create();

        when(entityManager.merge(subscription)).thenReturn(subscription);

        Subscription updatedSubscription = subscriptionRepository.update(subscription);

        assertEquals(subscription, updatedSubscription);
        verify(entityManager, times(1)).merge(subscription);
    }

    @Test
    void testDelete() {
        Subscription subscription = new Subscription();
        subscription.setId("1");

        when(entityManager.find(Subscription.class, "1")).thenReturn(subscription);
        doNothing().when(entityManager).remove(subscription);

        subscriptionRepository.delete("1");

        verify(entityManager, times(1)).find(Subscription.class, "1");
        verify(entityManager, times(1)).remove(subscription);
    }

    @Test
    void testDeleteSubscriptionNotFound() {
        when(entityManager.find(Subscription.class, "1")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> subscriptionRepository.delete("1"));

        verify(entityManager, times(1)).find(Subscription.class, "1");
        verify(entityManager, never()).remove(any());
    }

    @Test
    void testFindByUser() {
        String userId = "user123";
        Subscription subscription1 = subscriptionFactory.create();
        Subscription subscription2 = subscriptionFactory.create();

        TypedQuery<Subscription> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT s FROM Subscription s WHERE s.userId = :userId", Subscription.class)).thenReturn(query);
        when(query.setParameter("userId", userId)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(subscription1, subscription2));

        List<Subscription> subscriptions = subscriptionRepository.findByUser(userId);

        assertEquals(2, subscriptions.size());
        verify(entityManager, times(1)).createQuery("SELECT s FROM Subscription s WHERE s.userId = :userId", Subscription.class);
        verify(query, times(1)).setParameter("userId", userId);
        verify(query, times(1)).getResultList();
    }
}
