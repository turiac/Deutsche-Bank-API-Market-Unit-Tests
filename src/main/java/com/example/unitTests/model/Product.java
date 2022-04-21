package com.example.unitTests.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    private String name;
    private double price;
    private int quantity;

    @ManyToOne
    Cart cart;

    @ManyToOne
    private WishList wishList;

    @ManyToOne
    private Order order;
}
