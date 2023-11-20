package cps510.carrentaldbms.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @Column(name = "CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private Set<RentalAgreement> rentalAgreements;

    @Column(name = "AGREEMENT_ID")
    private Long currentAgreement;

    @Column(name = "CREDENTIAL")
    private String Credential;
}
