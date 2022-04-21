package com.example.unitTests.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    private Date createdDate;

    @OneToOne
    User user;

    @OneToMany
    private List<Product> products;
}
