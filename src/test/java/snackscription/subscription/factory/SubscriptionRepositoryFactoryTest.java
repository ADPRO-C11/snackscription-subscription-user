package snackscription.subscription.factory;

import org.junit.jupiter.api.Test;
import snackscription.subscription.repository.SubscriptionRepository;
import static org.junit.jupiter.api.Assertions.*;

class SubscriptionRepositoryFactoryTest {
    @Test
    void testCreate() {
        SubscriptionRepositoryFactory subscriptionRepositoryFactory = new SubscriptionRepositoryFactory();
        SubscriptionRepository subscriptionRepository = subscriptionRepositoryFactory.create();
        assertNotNull(subscriptionRepository);
        assertInstanceOf(SubscriptionRepository.class, subscriptionRepository);
    }
}
