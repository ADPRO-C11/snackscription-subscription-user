package snackscription.subscription.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import snackscription.subscription.enums.SubscriptionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "subscription")
public class Subscription {
    @Id
    String id;

    @Column(name = "unique_code", nullable = false, unique = true)
    String uniqueCode;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "subscription_box_id", nullable = false)
    String subscriptionBoxId;

    @Embedded
    @Column(name = "shipping_address", nullable = false)
    ShippingAddress shippingAddress;

    @Column(name = "status", nullable = false)
    String status;

    @Column(name = "dateCreated", nullable = false)
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
