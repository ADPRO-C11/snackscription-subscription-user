package snackscription.subscription.dto;

import lombok.*;
import snackscription.subscription.model.ShippingAddress;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SubscriptionDTO {
    String id;
    String uniqueCode;
    String type;
    String userId;
    String subscriptionBoxId;
    ShippingAddress shippingAddress;
    String status;
    LocalDateTime dateCreated;
}
