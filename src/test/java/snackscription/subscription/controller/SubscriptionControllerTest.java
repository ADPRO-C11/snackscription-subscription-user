package snackscription.subscription.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.service.SubscriptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Test
    void testCreate() {
        SubscriptionDTO request = new SubscriptionDTO();
        Subscription response = new Subscription();

        when(subscriptionService.save(any(SubscriptionDTO.class))).thenReturn(response);

        ResponseEntity<Subscription> result = subscriptionController.create(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testCreateBadRequest() {
        SubscriptionDTO request = new SubscriptionDTO();

        when(subscriptionService.save(any(SubscriptionDTO.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<Subscription> result = subscriptionController.create(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testFindAll() {
        List<SubscriptionDTO> subscriptionList = new ArrayList<>();

        when(subscriptionService.findAll()).thenReturn(subscriptionList);

        ResponseEntity<List<SubscriptionDTO>> result = subscriptionController.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(subscriptionList, result.getBody());
    }

    @Test
    void testFindById() {
        String id = UUID.randomUUID().toString();
        SubscriptionDTO response = new SubscriptionDTO();

        when(subscriptionService.findById(id)).thenReturn(response);

        ResponseEntity<SubscriptionDTO> result = subscriptionController.findById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testFindByIdInvalidId() {
        String id = "invalidId";

        ResponseEntity<SubscriptionDTO> result = subscriptionController.findById(id);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testUpdate() {
        SubscriptionDTO request = new SubscriptionDTO();
        request.setId(UUID.randomUUID().toString());
        Subscription response = new Subscription();

        when(subscriptionService.update(any(SubscriptionDTO.class))).thenReturn(response);

        ResponseEntity<Subscription> result = subscriptionController.update(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testUpdateBadRequest() {
        SubscriptionDTO request = new SubscriptionDTO();
        request.setId("123");

        when(subscriptionService.update(any(SubscriptionDTO.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<Subscription> result = subscriptionController.update(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testDelete() {
        String id = UUID.randomUUID().toString();

        ResponseEntity<String> expectedResult = new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);

        doNothing().when(subscriptionService).delete(id);

        ResponseEntity<String> result = subscriptionController.delete(id);

        assertEquals(expectedResult, result);
    }

    @Test
    void testDeleteInvalidId() {
        String id = "invalidId";

        ResponseEntity<String> result = subscriptionController.delete(id);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testFindByIdNotFound() {
        String id = UUID.randomUUID().toString();

        when(subscriptionService.findById(id)).thenReturn(null);

        ResponseEntity<SubscriptionDTO> response = subscriptionController.findById(UUID.randomUUID().toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateNotFound() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId(UUID.randomUUID().toString());
        subscriptionDTO.setStatus("PENDING");

        doThrow(new IllegalArgumentException("Subscription not found"))
                .when(subscriptionService)
                .update(subscriptionDTO);

        when(subscriptionService.findById(UUID.randomUUID().toString())).thenReturn(null);

        ResponseEntity<Subscription> response = subscriptionController.update(subscriptionDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateNullId() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();

        ResponseEntity<Subscription> response = subscriptionController.update(subscriptionDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void testDeleteNotFound() {
        String id = UUID.randomUUID().toString();

        doThrow(new IllegalArgumentException("Subscription not found"))
                .when(subscriptionService)
                .delete(id);

        ResponseEntity<String> response = subscriptionController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
