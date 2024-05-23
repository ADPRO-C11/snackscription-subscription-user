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
import snackscription.subscription.utils.JWTUtils;

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

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private SubscriptionController subscriptionController;

    private SubscriptionDTO subscriptionDTO;
    private Subscription subscription;
    private final String validToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyMTIzIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MTYyMzkwMjIsImV4cCI6MTcxNjMyNTQyMn0.5wT-7Q_Oc2by1b2q6xR6g-eOdAG_RUVH5HbyCbn88mE";

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

        when(jwtUtils.isTokenValid(validToken)).thenReturn(true);
    }

    @Test
    void testCreate() throws IllegalAccessException {
        when(subscriptionService.save(any(SubscriptionDTO.class)))
                .thenReturn(CompletableFuture.completedFuture(subscription));

        CompletableFuture<ResponseEntity<Subscription>> result = subscriptionController.create(validToken, subscriptionDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(subscription), result.join());
    }

    @Test
    void testFindAll() throws IllegalAccessException {
        List<SubscriptionDTO> subscriptionDTOList = List.of(subscriptionDTO);

        when(subscriptionService.findAll())
                .thenReturn(CompletableFuture.completedFuture(subscriptionDTOList));

        CompletableFuture<ResponseEntity<List<SubscriptionDTO>>> result = subscriptionController.findAll(validToken);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(subscriptionDTOList), result.join());
    }

    @Test
    void testUpdate() throws IllegalAccessException {
        when(subscriptionService.findById(subscriptionDTO.getId()))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(subscriptionDTO)));
        when(subscriptionService.update(subscriptionDTO))
                .thenReturn(CompletableFuture.completedFuture(subscription));

        CompletableFuture<ResponseEntity<Subscription>> result = subscriptionController.update(validToken, subscriptionDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(subscription), result.join());
    }

    @Test
    void testFindById() throws IllegalAccessException {
        when(subscriptionService.findById(subscriptionDTO.getId()))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(subscriptionDTO)));

        CompletableFuture<ResponseEntity<SubscriptionDTO>> result = subscriptionController.findById(validToken, subscriptionDTO.getId());

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(subscriptionDTO), result.join());
    }

    @Test
    void testFindByIdInvalidId() throws IllegalAccessException {
        String invalidId = "invalid_id";

        CompletableFuture<ResponseEntity<SubscriptionDTO>> result = subscriptionController.findById(validToken, invalidId);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.badRequest().build(), result.join());
    }

    @Test
    void testDelete() throws IllegalAccessException {
        when(subscriptionService.delete(subscriptionDTO.getId()))
                .thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<ResponseEntity<String>> result = subscriptionController.delete(validToken, subscriptionDTO.getId());

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok("DELETE SUCCESS"), result.join());
    }

    @Test
    void testUpdate_InvalidId() throws IllegalAccessException {
        SubscriptionDTO invalidSubscriptionDTO = new SubscriptionDTO();
        CompletableFuture<ResponseEntity<Subscription>> expectedResult = CompletableFuture.completedFuture(ResponseEntity.badRequest().build());

        CompletableFuture<ResponseEntity<Subscription>> result = subscriptionController.update(validToken, invalidSubscriptionDTO);

        assertTrue(result.isDone());
        assertEquals(expectedResult.join(), result.join());
    }

    @Test
    void testUpdate_SubscriptionNotFound() throws IllegalAccessException {
        SubscriptionDTO invalidSubscriptionDTO = new SubscriptionDTO();
        invalidSubscriptionDTO.setId(UUID.randomUUID().toString());
        CompletableFuture<ResponseEntity<Subscription>> expectedResult = CompletableFuture.completedFuture(ResponseEntity.notFound().build());

        when(subscriptionService.findById(invalidSubscriptionDTO.getId())).thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        CompletableFuture<ResponseEntity<Subscription>> result = subscriptionController.update(validToken, invalidSubscriptionDTO);

        assertTrue(result.isDone());
        assertEquals(expectedResult.join(), result.join());
    }

    @Test
    void testDelete_InvalidId() throws IllegalAccessException {
        CompletableFuture<ResponseEntity<String>> expectedResult = CompletableFuture.completedFuture(ResponseEntity.badRequest().build());

        CompletableFuture<ResponseEntity<String>> result = subscriptionController.delete(validToken, "invalid_id");

        assertTrue(result.isDone());
        assertEquals(expectedResult.join(), result.join());
    }

    @Test
    void testDelete_SubscriptionNotFound() throws IllegalAccessException {
        String invalidId = UUID.randomUUID().toString();
        CompletableFuture<ResponseEntity<String>> expectedResult = CompletableFuture.completedFuture(ResponseEntity.notFound().build());

        when(subscriptionService.delete(invalidId)).thenReturn(CompletableFuture.failedFuture(new IllegalArgumentException()));

        CompletableFuture<ResponseEntity<String>> result = subscriptionController.delete(validToken, invalidId);

        assertTrue(result.isDone());
        assertEquals(expectedResult.join(), result.join());
    }

    @Test
    void testMain() {
        ResponseEntity<String> response = subscriptionController.main();
        assertEquals("Snackscription - Subscription Management API", response.getBody());
    }

    @Test
    void testCreateExceptionHandler() throws IllegalAccessException {
        SubscriptionDTO invalidSubscriptionDTO = new SubscriptionDTO();
        CompletableFuture<ResponseEntity<Subscription>> expectedResult = CompletableFuture.completedFuture(ResponseEntity.badRequest().build());

        CompletableFuture<Subscription> failedFuture = CompletableFuture.failedFuture(new IllegalArgumentException("Invalid Request"));
        when(subscriptionService.save(invalidSubscriptionDTO)).thenReturn(failedFuture);

        CompletableFuture<ResponseEntity<Subscription>> result = subscriptionController.create(validToken, invalidSubscriptionDTO);

        assertTrue(result.isDone());
        assertEquals(expectedResult.join(), result.join());
    }
}
