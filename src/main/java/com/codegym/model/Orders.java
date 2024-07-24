package com.codegym.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double total;
    private boolean status;
    private boolean statusOrders;
    private boolean cancelStatus;
    private boolean foodTakeStatus;
    private boolean deliveryFoodStatus;
    private boolean doneDeliveryMoneyStatus;
    private String shippingAddress;

    @ManyToOne
    private User user;

    @ManyToOne
    private Shop shop;

    @ManyToOne
    private Delivery delivery;

    @ManyToOne
    private Coupon coupon;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "orders_detail",
            joinColumns = {
                    @JoinColumn(name = "order_id", referencedColumnName = "id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "food_id", referencedColumnName = "id")
            }
    )
    private Set<Food> foods;
}
