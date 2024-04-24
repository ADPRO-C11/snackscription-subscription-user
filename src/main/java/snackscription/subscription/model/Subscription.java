package snackscription.subscription.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import snackscription.subscription.enums.SubscriptionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public Subscription(){
        this.id = UUID.randomUUID().toString();
    }

    public Subscription(String uniqueCode, String userId, String subscriptionBoxId, ShippingAddress shippingAddress, String status){
        this.id = UUID.randomUUID().toString();

        if (!uniqueCode.startsWith("MTH-") && !uniqueCode.startsWith("QTR-") && !uniqueCode.startsWith("SAA-")) {
            throw new IllegalArgumentException("Invalid unique code");
        }

        this.uniqueCode = uniqueCode;
        this.userId = userId;
        this.subscriptionBoxId = subscriptionBoxId;
        this.shippingAddress = shippingAddress;
        this.setStatus(status);
    }

    public void setStatus(String status){
        if (SubscriptionStatus.contains(status)) {
            this.status = status;
        } else{
            throw new IllegalArgumentException("Invalid status");
        }
    }
}
