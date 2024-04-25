package snackscription.subscription.dto;

import org.springframework.stereotype.Component;
import snackscription.subscription.factory.SubscriptionFactory;
import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.model.Subscription;

import java.util.Optional;

@Component
public class DTOMapper {

    public static SubscriptionDTO convertModelToDto(Subscription subscription){
        return new SubscriptionDTO(
                subscription.getId(),
                subscription.getUniqueCode(),
                subscription.getType(),
                subscription.getSubscriptionBoxId(),
                subscription.getUserId(),
                subscription.getShippingAddress(),
                subscription.getStatus(),
                subscription.getDateCreated()
        );
    }

    public static Subscription convertDTOtoModel(SubscriptionDTO subscriptionDTO){
        String type = subscriptionDTO.getType();
        String userId = subscriptionDTO.getUserId();
        String subscriptionBoxId = subscriptionDTO.getSubscriptionBoxId();
        ShippingAddress shippingAddress = subscriptionDTO.getShippingAddress();
        return new SubscriptionFactory().create(type, userId, subscriptionBoxId, shippingAddress);
    }

    public static Subscription updateSubscription(Subscription subscription, SubscriptionDTO subscriptionDTO) {
        Optional.ofNullable(subscriptionDTO.getShippingAddress()).ifPresent(subscription::setShippingAddress);
        Optional.ofNullable(subscriptionDTO.getStatus()).ifPresent(subscription::setStatus);
        return subscription;
    }
}
