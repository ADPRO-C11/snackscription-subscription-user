package snackscription.subscription.factory;

import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.model.Subscription;

public class SubscriptionFactory implements Factory<Subscription> {
    @Override
    public Subscription create(){
        return new Subscription();
    }

    public Subscription create(String uniqueCode, String userId, String subscriptionBoxId, ShippingAddress shippingAddress, String status){
        return new Subscription(uniqueCode, userId, subscriptionBoxId, shippingAddress, status);
    }
}
