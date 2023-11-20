package cps510.carrentaldbms.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "RENTAL_AGREEMENT")
public class RentalAgreement {
    @Id
    @Column(name = "AGREEMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    @JsonBackReference
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    @JsonBackReference
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_ID", referencedColumnName = "CAR_ID")
    @JsonBackReference
    private Car car;

    @Column(name = "DATE_TAKEN")
    private Timestamp dateTaken;

    @Column(name = "RETURN_DATE")
    private Date returnDate;

    @Column(name = "TOTAL_PRICE")
    private String totalPrice;
}
