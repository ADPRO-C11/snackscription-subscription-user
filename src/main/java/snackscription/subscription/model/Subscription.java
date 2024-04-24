package snackscription.subscription.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Subscription {
    String id;
    String uniqueCode;
    String userId;
    String subscriptionBoxId;
    ShippingAddress shippingAddress;
    String status;
    LocalDateTime dateCreated;
}