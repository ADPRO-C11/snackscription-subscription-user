package snackscription.subscription.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class ShippingAddress {
    String address;
    String city;
    String province;
    String postalCode;
    String phoneNumber;

    public ShippingAddress(String address, String city, String province, String postalCode, String phoneNumber){
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }
}