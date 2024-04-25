package snackscription.subscription.factory;

import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.model.Subscription;

public class SubscriptionFactory implements Factory<Subscription> {
    @Override
    public Subscription create(){
        return new Subscription();
    }

    public Subscription create(String type, String userId, String subscriptionBoxId, ShippingAddress shippingAddress){
        return new Subscription(type, userId, subscriptionBoxId, shippingAddress);
    }
}
