package snackscription.subscription.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import snackscription.subscription.model.ShippingAddress;

import java.time.LocalDateTime;
class SubscriptionDTOTest {

    @Test
    void testIdAttribute() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        String id = "2";

        subscriptionDTO.setId(id);

        assertEquals(id, subscriptionDTO.getId());
    }

    @Test
    void testUniqueCodeAttribute() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        String uniqueCode = "QTR-0987654321ABCDEF";

        subscriptionDTO.setUniqueCode(uniqueCode);

        assertEquals(uniqueCode, subscriptionDTO.getUniqueCode());
    }

    @Test
    void testTypeAttribute() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        String type = "QUARTERLY";

        subscriptionDTO.setType(type);

        assertEquals(type, subscriptionDTO.getType());
    }

    @Test
    void testUserIdAttribute() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        String userId = "user456";

        subscriptionDTO.setUserId(userId);

        assertEquals(userId, subscriptionDTO.getUserId());
    }

    @Test
    void testSubscriptionBoxIdAttribute() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        String subscriptionBoxId = "box456";

        subscriptionDTO.setSubscriptionBoxId(subscriptionBoxId);

        assertEquals(subscriptionBoxId, subscriptionDTO.getSubscriptionBoxId());
    }

    @Test
    void testShippingAddressAttribute() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        ShippingAddress shippingAddress = new ShippingAddress(
                "Jl. Doang Tapi Ga Jadian",
                "Jakarta Selatan",
                "DKI Jakarta",
                "123456",
                "081234567890"
        );

        subscriptionDTO.setShippingAddress(shippingAddress);

        assertEquals(shippingAddress, subscriptionDTO.getShippingAddress());
    }

    @Test
    void testStatusAttribute() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        String status = "PENDING";

        subscriptionDTO.setStatus(status);

        assertEquals(status, subscriptionDTO.getStatus());
    }

    @Test
    void testDateCreatedAttribute() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        LocalDateTime dateCreated = LocalDateTime.now();

        subscriptionDTO.setDateCreated(dateCreated);

        assertEquals(dateCreated, subscriptionDTO.getDateCreated());
    }
}
