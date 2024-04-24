package snackscription.subscription.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snackscription.subscription.enums.SubscriptionStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubscriptionTest {

    Subscription subscription;
    @BeforeEach
    void setUp(){
        this.subscription = new Subscription();
        this.subscription.setId("12345678910");
        this.subscription.setUniqueCode("MTH-12345678910");
        this.subscription.setUserId("12345678910");
        this.subscription.setSubscriptionBoxId("12345678910");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress("Jl. Doang Tapi Ga Jadian");
        shippingAddress.setCity("Jakarta Selatan");
        shippingAddress.setProvince("DKI Jakarta");
        shippingAddress.setPostalCode("123456");
        shippingAddress.setPhoneNumber("081234567890");

        this.subscription.setShippingAddress(shippingAddress);
        this.subscription.setStatus(SubscriptionStatus.PENDING.getValue());
        this.subscription.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testGetSubscriptionId(){
        assertEquals("12345678910", subscription.getId());
    }

    @Test
    void testGetUniqueCode(){
        assertEquals("MTH-12345678910", subscription.getUniqueCode());
    }

    @Test
    void testGetUserId(){
        assertEquals("12345678910", subscription.getUserId());
    }

    @Test
    void testGetSubscriptionBoxId(){
        assertEquals("12345678910", subscription.getSubscriptionBoxId());
    }

    @Test
    void testGetShippingAddress(){
        ShippingAddress shippingAddress = subscription.getShippingAddress();
        assertEquals("Jl. Doang Tapi Ga Jadian", shippingAddress.getAddress());
        assertEquals("Jakarta Selatan", shippingAddress.getCity());
        assertEquals("DKI Jakarta", shippingAddress.getProvince());
        assertEquals("123456", shippingAddress.getPostalCode());
        assertEquals("081234567890", shippingAddress.getPhoneNumber());
    }

    @Test
    void testGetStatus(){
        assertEquals(SubscriptionStatus.PENDING.getValue(), subscription.getStatus());
    }

    @Test
    void testDateCreated() {
        assertEquals(LocalDateTime.now(), subscription.getDateCreated());
    }

    @Test
    void testCreateInvalidSubscription() {
        assertThrows(IllegalArgumentException.class, () -> {
            Subscription subscription = new Subscription("SJW-12345678910",
                    this.subscription.getUserId(), this.subscription.getSubscriptionBoxId(), this.subscription.getShippingAddress(), this.subscription.getStatus());
        });
    }

    @Test
    void testSetInvalidStatus(){
        assertThrows(IllegalArgumentException.class, () -> subscription.setStatus("MEOW"));
    }
}