package com.codegym.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String image;
    private String phone;
    private String email;
    private String opening_time;
    private String closing_time;
    private Double revenue;
    private boolean status;

    @OneToOne
    private User user;

}
