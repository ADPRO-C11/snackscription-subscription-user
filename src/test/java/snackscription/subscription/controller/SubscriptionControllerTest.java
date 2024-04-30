package snackscription.subscription.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.service.SubscriptionService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SubscriptionControllerTest {

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionController subscriptionController;

    public SubscriptionControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        List<SubscriptionDTO> subscriptionDTOList = Collections.emptyList();

        when(subscriptionService.findAll()).thenReturn(subscriptionDTOList);

        ResponseEntity<List<SubscriptionDTO>> responseEntity = subscriptionController.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(subscriptionDTOList, responseEntity.getBody());
    }

    @Test
    void testFindAll() {
        List<SubscriptionDTO> subscriptionDTOList = Collections.emptyList();

        when(subscriptionService.findAll()).thenReturn(subscriptionDTOList);

        ResponseEntity<List<SubscriptionDTO>> responseEntity = subscriptionController.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(subscriptionDTOList, responseEntity.getBody());
    }

    @Test
    void testFindById() {
        String id = "1";
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();

        when(subscriptionService.findById(id)).thenReturn(subscriptionDTO);

        ResponseEntity<SubscriptionDTO> responseEntity = subscriptionController.findById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(subscriptionDTO, responseEntity.getBody());
    }

    @Test
    void testUpdate() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        Subscription subscription = new Subscription();

        when(subscriptionService.update(any())).thenReturn(subscription);

        ResponseEntity<Subscription> responseEntity = subscriptionController.update(subscriptionDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(subscription, responseEntity.getBody());
    }

    @Test
    void testDelete() {
        String id = "1";

        ResponseEntity<String> responseEntity = subscriptionController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("DELETE SUCCESS", responseEntity.getBody());
        verify(subscriptionService, times(1)).delete(id);
    }
}
