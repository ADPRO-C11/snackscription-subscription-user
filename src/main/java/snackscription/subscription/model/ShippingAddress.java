package snackscription.subscription.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ShippingAddress {
    String address;
    String city;
    String province;
    String postalCode;
    String phoneNumber;
}