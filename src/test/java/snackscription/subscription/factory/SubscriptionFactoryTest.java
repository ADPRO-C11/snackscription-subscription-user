package snackscription.subscription.factory;

import org.junit.jupiter.api.Test;
import snackscription.subscription.enums.SubscriptionStatus;
import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.model.Subscription;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubscriptionFactoryTest {
    @Test
    void testCreateSubscription(){
        SubscriptionFactory subscriptionFactory = new SubscriptionFactory();
        Subscription subscription = subscriptionFactory.create();
        assertNotNull(subscription.getId());
    }

    @Test
    void testCreateSubscriptionComplete(){
        SubscriptionFactory subscriptionFactory = new SubscriptionFactory();

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress("Jl. Doang Tapi Ga Jadian");
        shippingAddress.setCity("Jakarta Selatan");
        shippingAddress.setProvince("DKI Jakarta");
        shippingAddress.setPostalCode("123456");
        shippingAddress.setPhoneNumber("081234567890");

        Subscription subscription = subscriptionFactory.create(
                "Monthly",
                "12345678910",
                "12345678910",
                shippingAddress
        );
        assertNotNull(subscription.getId());
        assertNotNull(subscription.getUniqueCode());
        assertEquals("12345678910", subscription.getUserId());
        assertEquals("12345678910", subscription.getSubscriptionBoxId());
        assertEquals(SubscriptionStatus.PENDING.getValue(), subscription.getStatus());

        ShippingAddress subscriptionShippingAddress = subscription.getShippingAddress();
        assertEquals("Jl. Doang Tapi Ga Jadian", subscriptionShippingAddress.getAddress());
        assertEquals("Jakarta Selatan", subscriptionShippingAddress.getCity());
        assertEquals("DKI Jakarta", subscriptionShippingAddress.getProvince());
        assertEquals("123456", subscriptionShippingAddress.getPostalCode());
        assertEquals("081234567890", subscriptionShippingAddress.getPhoneNumber());
    }
}
