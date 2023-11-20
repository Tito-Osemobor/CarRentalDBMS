package cps510.carrentaldbms.Entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "CAR")
@SequenceGenerator(name = "SEQ_CAR", sequenceName = "SEQ_CAR", allocationSize = 1)
public class Car {
    @Id
    @Column(name = "CAR_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAR")
    private Long id;

    @Column(name = "MAKE")
    private String make;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "MILEAGE")
    private String mileage;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PRICE")
    private Long price;
}
