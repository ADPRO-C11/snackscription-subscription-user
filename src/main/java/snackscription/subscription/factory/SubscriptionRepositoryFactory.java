package snackscription.subscription.factory;

import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.repository.SubscriptionRepository;

public class SubscriptionRepositoryFactory implements Factory<SubscriptionRepository> {
    @Override
    public SubscriptionRepository create(){
        return new SubscriptionRepository();
    }

    @Override
    public SubscriptionRepository create(String uniqueCode, String userId, String subscriptionBoxId, ShippingAddress shippingAddress, String status){
        throw new UnsupportedOperationException();
    }
}
