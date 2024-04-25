package snackscription.subscription.model;

import jakarta.persistence.*;
import lombok.Builder;
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

    @Column(name = "unique_code", unique = true)
    String uniqueCode;

    @Column(name = "type")
    String type;

    @Column(name = "user_id")
    String userId;

    @Column(name = "subscription_box_id")
    String subscriptionBoxId;

    @Embedded
    @Column(name = "shipping_address")
    ShippingAddress shippingAddress;

    @Column(name = "status")
    String status;

    @Column(name = "dateCreated")
    LocalDateTime dateCreated = LocalDateTime.now();

    public Subscription(){
        this.id = UUID.randomUUID().toString();
    }

    public Subscription(String type, String userId, String subscriptionBoxId, ShippingAddress shippingAddress){
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.setUniqueCode(type);
        this.userId = userId;
        this.subscriptionBoxId = subscriptionBoxId;
        this.shippingAddress = shippingAddress;
        this.setStatus(SubscriptionStatus.PENDING.getValue());
    }

    public void setUniqueCode(String type) {
        String prefix = switch (type) {
            case "Monthly" -> "MTH-";
            case "Quarterly" -> "QTR-";
            case "Semi-Annual" -> "SAA-";
            default -> throw new IllegalArgumentException("Invalid type");
        };
        String randomPart = UUID.randomUUID().toString();
        randomPart = randomPart.replace("-", "").toUpperCase().substring(0, 16);
        this.uniqueCode = prefix + randomPart;
    }

    public void setStatus(String status){
        if (SubscriptionStatus.contains(status)) {
            this.status = status;
        } else{
            throw new IllegalArgumentException("Invalid status");
        }
    }
}
