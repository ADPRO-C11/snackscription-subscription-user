package snackscription.subscription.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.repository.SubscriptionRepository;
import snackscription.subscription.dto.DTOMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private Subscription subscription1;
    private SubscriptionDTO subscriptionDTO1;
    private List<Subscription> subscriptions;
    private final String userId = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        subscription1 = new Subscription();
        subscription1.setId("1");
        subscription1.setType("MONTHLY");
        subscription1.setUserId(userId);
        subscription1.setSubscriptionBoxId("box123");
        subscription1.setShippingAddress(new ShippingAddress("Jl. Doang Tapi Ga Jadian",
                "Jakarta Selatan",
                "DKI Jakarta",
                "123456",
                "081234567890"));
        subscription1.setStatus("PENDING");

        Subscription subscription2 = new Subscription();
        subscription2.setId("2");
        subscription2.setType("QUARTERLY");
        subscription2.setUserId(userId);
        subscription2.setSubscriptionBoxId("box456");
        subscription2.setShippingAddress(new ShippingAddress("Jl. Jadian Tapi Ga Doang",
                "Jakarta Utara",
                "DKI Jakarta",
                "654321",
                "089876543210"));
        subscription2.setStatus("SUBSCRIBED");

        subscriptionDTO1 = DTOMapper.convertModelToDto(subscription1);
        SubscriptionDTO subscriptionDTO2 = DTOMapper.convertModelToDto(subscription2);

        subscriptions = Arrays.asList(subscription1, subscription2);
    }

    @Test
    void testSave() {
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription1);

        CompletableFuture<Subscription> result = subscriptionService.save(subscriptionDTO1);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(subscription1, result.join());
    }

    @Test
    void testFindAll() {
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        CompletableFuture<List<SubscriptionDTO>> result = subscriptionService.findAll();

        assertNotNull(result);
        assertTrue(result.isDone());
        assertNotNull(result.join());
    }

    @Test
    void testFindById() {
        String id = "1";
        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(subscription1));

        CompletableFuture<Optional<SubscriptionDTO>> result = subscriptionService.findById(id);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertTrue(result.join().isPresent());
        assertNotNull(result.join());
    }

    @Test
    void testUpdate() {
        when(subscriptionRepository.findById(anyString())).thenReturn(Optional.of(subscription1));
        when(subscriptionRepository.update(any(Subscription.class))).thenReturn(subscription1);

        CompletableFuture<Subscription> result = subscriptionService.update(subscriptionDTO1);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(subscription1, result.join());
    }

    @Test
    void testDelete() {
        String id = "1";
        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(subscription1));
        doNothing().when(subscriptionRepository).delete(anyString());

        CompletableFuture<Void> result = subscriptionService.delete(id);

        assertNotNull(result);
        assertTrue(result.isDone());
        verify(subscriptionRepository, times(1)).delete(id);
    }

    @Test
    void findById_NullOrEmptyId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.findById(null).join());
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.findById("").join());
    }

    @Test
    void update_NullSubscriptionDTO_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.update(null).join());
    }

    @Test
    void update_NonExistentSubscription_ThrowsIllegalArgumentException() {
        SubscriptionDTO nonExistentSubscriptionDTO = new SubscriptionDTO();
        nonExistentSubscriptionDTO.setId("nonExistentId");
        when(subscriptionRepository.findById(nonExistentSubscriptionDTO.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> subscriptionService.update(nonExistentSubscriptionDTO).join());
    }

    @Test
    void delete_NullOrEmptyId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.delete(null).join());
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.delete(" ").join());
    }

    @Test
    void delete_NonExistentSubscription_ThrowsIllegalArgumentException() {
        String nonExistentId = "nonExistentId";
        when(subscriptionRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> subscriptionService.delete(nonExistentId).join());
    }

    @Test
    void testFindByUser() {
        when(subscriptionRepository.findByUser(userId)).thenReturn(subscriptions);

        CompletableFuture<List<SubscriptionDTO>> result = subscriptionService.findByUser(userId);

        assertNotNull(result);
        assertNotNull(result.join());
        verify(subscriptionRepository, times(1)).findByUser(userId);
    }
}
