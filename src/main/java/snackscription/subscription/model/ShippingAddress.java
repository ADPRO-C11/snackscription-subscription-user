package snackscription.subscription.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingAddress {
    String address;
    String city;
    String province;
    String postalCode;
    String phoneNumber;
}