package com.example.unitTests.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @OneToMany
    private List<Product> products = new ArrayList<>();

    @OneToOne
    private User user;

    public double getPrice() {
        return this.products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void removeProduct(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}

