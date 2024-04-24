package snackscription.subscription.enums;

import lombok.Getter;

@Getter
public enum SubscriptionStatus {
    PENDING("PENDING"),
    REJECTED("REJECTED"),
    SUBSCRIBED("SUBSCRIBED");

    private final String value;

    private SubscriptionStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (SubscriptionStatus subscriptionStatus : SubscriptionStatus.values()) {
            if (subscriptionStatus.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}