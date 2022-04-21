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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    private String email;

    @OneToOne
    private WishList wishList;

    @OneToOne
    private Cart cart;

    @OneToOne
    private Order order;
}
