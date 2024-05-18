package snackscription.subscription.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snackscription.subscription.dto.DTOMapper;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionDTO subscriptionDTO;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Mock
    private Subscription subscription;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void testSave() {
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(CompletableFuture.completedFuture(subscription).resultNow());

        CompletableFuture<Subscription> result = subscriptionService.save(subscriptionDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertSame(subscription, result.join());
    }

    @Test
    void testFindAll() {
        List<Subscription> subscriptions = List.of(subscription);
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        CompletableFuture<List<SubscriptionDTO>> result = subscriptionService.findAll();

        assertNotNull(result);
        assertTrue(result.isDone());
        assertFalse(result.join().isEmpty());
    }

    @Test
    void testFindById() {
        String id = "1";
        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(subscription));

        CompletableFuture<Optional<SubscriptionDTO>> result = subscriptionService.findById(id);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertTrue(result.join().isPresent());
    }

    @Test
    void testUpdate() {
        when(subscriptionRepository.findById(anyString())).thenReturn(Optional.of(subscription));
        when(subscriptionRepository.update(any(Subscription.class))).thenReturn(CompletableFuture.completedFuture(subscription).resultNow());

        CompletableFuture<Subscription> result = subscriptionService.update(subscriptionDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertSame(subscription, result.join());
    }

    @Test
    void testDelete() {
        String id = "1";
        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(subscription));

        CompletableFuture<Void> result = subscriptionService.delete(id);

        assertNotNull(result);
        assertTrue(result.isDone());
    }

    @Test
    void findById_NullOrEmptyId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, this::findByIdWithNullOrEmptyId);
    }

    private void findByIdWithNullOrEmptyId() {
        subscriptionService.findById(null).join();
        subscriptionService.findById("").join();
    }

    @Test
    void update_NullSubscriptionDTO_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, this::updateWithNullSubscriptionDTO);
    }

    private void updateWithNullSubscriptionDTO() {
        subscriptionService.update(null).join();
    }

    @Test
    void update_NonExistentSubscription_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, this::updateNonExistentSubscription);
    }

    private void updateNonExistentSubscription() {
        SubscriptionDTO nonExistentSubscriptionDTO = new SubscriptionDTO();
        nonExistentSubscriptionDTO.setId("nonExistentId");
        when(subscriptionRepository.findById(nonExistentSubscriptionDTO.getId())).thenReturn(Optional.empty());
        subscriptionService.update(nonExistentSubscriptionDTO).join();
    }

    @Test
    void delete_NullOrEmptyId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, this::deleteWithNullOrEmptyId);
    }

    private void deleteWithNullOrEmptyId() {
        subscriptionService.delete(null).join();
        subscriptionService.delete(" ").join();
    }

    @Test
    void delete_NonExistentSubscription_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, this::deleteNonExistentSubscription);
    }

    private void deleteNonExistentSubscription() {
        String nonExistentId = "nonExistentId";
        when(subscriptionRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        subscriptionService.delete(nonExistentId).join();
    }

    @Test
    void testInvalidSave() {
        subscriptionDTO = new SubscriptionDTO();
        assertThrows(
                IllegalArgumentException.class, this::createInvalidSave
        );
    }

    private void createInvalidSave(){
        subscriptionDTO = new SubscriptionDTO();
        subscriptionService.save(subscriptionDTO);
    }
}
