package snackscription.subscription.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.service.SubscriptionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SubscriptionControllerTest {

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionController subscriptionController;

    private SubscriptionDTO subscriptionDTO;
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId(UUID.randomUUID().toString());
        subscriptionDTO.setType("MONTHLY");
        subscriptionDTO.setUserId("user123");
        subscriptionDTO.setSubscriptionBoxId("box123");
        subscriptionDTO.setStatus("PENDING");

        subscription = new Subscription();
        subscription.setId(UUID.randomUUID().toString());
        subscription.setType("MONTHLY");
        subscription.setUserId("user123");
        subscription.setSubscriptionBoxId("box123");
        subscription.setStatus("PENDING");
    }

    @Test
    void testCreate() {
        when(subscriptionService.save(any(SubscriptionDTO.class)))
                .thenReturn(CompletableFuture.completedFuture(subscription));

        CompletableFuture<ResponseEntity<Subscription>> result = subscriptionController.create(subscriptionDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(subscription), result.join());
    }

    @Test
    void testFindAll() {
        List<SubscriptionDTO> subscriptionDTOList = List.of(subscriptionDTO);

        when(subscriptionService.findAll())
                .thenReturn(CompletableFuture.completedFuture(subscriptionDTOList));

        CompletableFuture<ResponseEntity<List<SubscriptionDTO>>> result = subscriptionController.findAll();

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(subscriptionDTOList), result.join());
    }

    @Test
    void testUpdate() {
        when(subscriptionService.findById(subscriptionDTO.getId()))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(subscriptionDTO)));
        when(subscriptionService.update(subscriptionDTO))
                .thenReturn(CompletableFuture.completedFuture(subscription));

        CompletableFuture<ResponseEntity<Subscription>> result = subscriptionController.update(subscriptionDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(subscription), result.join());
    }

    @Test
    void testFindById() {
        when(subscriptionService.findById(subscriptionDTO.getId()))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(subscriptionDTO)));

        CompletableFuture<ResponseEntity<SubscriptionDTO>> result = subscriptionController.findById(subscriptionDTO.getId());

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(subscriptionDTO), result.join());
    }

    @Test
    void testFindByIdInvalidId() {
        String invalidId = "invalid_id";

        CompletableFuture<ResponseEntity<SubscriptionDTO>> result = subscriptionController.findById(invalidId);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.badRequest().build(), result.join());
    }

    @Test
    void testDelete() {
        when(subscriptionService.delete(subscriptionDTO.getId()))
                .thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<ResponseEntity<String>> result = subscriptionController.delete(subscriptionDTO.getId());

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok("DELETE SUCCESS"), result.join());
    }
}
