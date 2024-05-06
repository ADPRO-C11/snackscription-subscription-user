package snackscription.subscription.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snackscription.subscription.enums.SubscriptionStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {

    Subscription subscription;
    @BeforeEach
    void setUp(){
        this.subscription = new Subscription();
        this.subscription.setId("1234567890");
        this.subscription.setType("MONTHLY");
        this.subscription.setUniqueCode("MONTHLY");
        this.subscription.setUserId("12345678910");
        this.subscription.setSubscriptionBoxId("12345678910");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress("Jl. Doang Tapi Ga Jadian");
        shippingAddress.setCity("Jakarta Selatan");
        shippingAddress.setProvince("DKI Jakarta");
        shippingAddress.setPostalCode("123456");
        shippingAddress.setPhoneNumber("081234567890");

        this.subscription.setStatus(SubscriptionStatus.PENDING.getValue());
        this.subscription.setShippingAddress(shippingAddress);
        this.subscription.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testGetSubscriptionId(){
        assertEquals("1234567890", subscription.getId());
    }

    @Test
    void testGetUniqueCode(){
        assertTrue(subscription.getUniqueCode().startsWith("MTH-"));
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
        assertNotNull(subscription.getDateCreated());
    }

    @Test
    void testCreateInvalidSubscription() {
        assertThrows(IllegalArgumentException.class, () -> {
            Subscription subscription = new Subscription("YEARLY",
                    this.subscription.getUserId(), this.subscription.getSubscriptionBoxId(), this.subscription.getShippingAddress());
        });
    }

    @Test
    void testSetUniqueCodeMonthly() {
        subscription.setUniqueCode("MONTHLY");
        assertEquals("MTH-", subscription.getUniqueCode().substring(0, 4));
    }

    @Test
    void testSetUniqueCodeQuarterly() {
        subscription.setUniqueCode("QUARTERLY");
        assertEquals("QTR-", subscription.getUniqueCode().substring(0, 4));
    }

    @Test
    void testSetUniqueCodeSemiAnnual() {
        subscription.setUniqueCode("SEMI-ANNUAL");
        assertEquals("SAA-", subscription.getUniqueCode().substring(0, 4));
    }

    @Test
    void testSetInvalidStatus(){
        assertThrows(IllegalArgumentException.class, () -> subscription.setStatus("MEOW"));
    }
}