package snackscription.subscription.factory;

import snackscription.subscription.model.ShippingAddress;

public interface Factory <T> {
    T create();
    T create(String type, String userId, String subscriptionBoxId, ShippingAddress shippingAddress);
}
