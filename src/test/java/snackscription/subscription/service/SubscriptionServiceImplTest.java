package snackscription.subscription.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.factory.SubscriptionFactory;
import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.repository.SubscriptionRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionFactory subscriptionFactory;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private Subscription subscription;
    private SubscriptionDTO subscriptionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId("1");
        subscriptionDTO.setType("MONTHLY");
        subscriptionDTO.setUserId("user123");
        subscriptionDTO.setSubscriptionBoxId("box123");
        subscriptionDTO.setShippingAddress(new ShippingAddress("Jl. Doang Tapi Ga Jadian",
                "Jakarta Selatan",
                "DKI Jakarta",
                "123456",
                "081234567890"));
        subscriptionDTO.setStatus("PENDING");

        subscription = new Subscription();
        subscription.setId("1");
        subscription.setType("MONTHLY");
        subscription.setUserId("user123");
        subscription.setSubscriptionBoxId("box123");
        subscription.setShippingAddress(new ShippingAddress("Jl. Doang Tapi Ga Jadian",
                "Jakarta Selatan",
                "DKI Jakarta",
                "123456",
                "081234567890"));
        subscription.setStatus("PENDING");

        when(subscriptionFactory.create(anyString(), anyString(), anyString(), any(ShippingAddress.class)))
                .thenReturn(subscription);
    }

    @Test
    void testSave(){
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
        Subscription result = subscriptionService.save(subscriptionDTO);

        assertNotNull(result);
        assertEquals(subscription, result);
    }

    @Test
    void testFindAll() {
        when(subscriptionRepository.findAll()).thenReturn(Collections.singletonList(subscription));

        List<SubscriptionDTO> result = subscriptionService.findAll();

        assertEquals(1, result.size());
        assertEquals(subscription.getId(), result.get(0).getId());
    }

    @Test
    void testFindById() {
        String id = "1";
        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(subscription));

        SubscriptionDTO result = subscriptionService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        String id = "nonexistent";

        when(subscriptionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.findById(id);
        });
    }

    @Test
    void testUpdate() {
        subscriptionDTO.setStatus("SUBSCRIBED");

        when(subscriptionRepository.findById(subscriptionDTO.getId())).thenReturn(Optional.of(subscription));
        when(subscriptionRepository.update(subscription)).thenReturn(subscription);

        Subscription result = subscriptionService.update(subscriptionDTO);

        assertNotNull(result);
        assertEquals(subscription, result);
        assertEquals(subscriptionDTO.getStatus(), result.getStatus());
    }

    @Test
    void testUpdateNotFound() {
        subscriptionDTO.setId("nonexistent");

        when(subscriptionRepository.findById(subscriptionDTO.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.update(subscriptionDTO);
        });
    }

    @Test
    void testDelete() {
        String id = "1";

        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(subscription));

        subscriptionService.delete(id);

        verify(subscriptionRepository, times(1)).delete(id);
    }

    @Test
    void testDelete_NotFound() {
        String id = "nonexistent";

        when(subscriptionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.delete(id);
        });
    }
}
