package org.example.cartapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Scope("prototype")
public class Cart {
    private final List<Product> items = new ArrayList<>();
    private final ProductRepository productRepository;

    @Autowired
    public Cart(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProductById(int id) {
        Optional<Product> optionalProduct = productRepository.getProductById(id);
        if (optionalProduct.isPresent()) {
            items.add(optionalProduct.get());
            System.out.println("Product '" + optionalProduct.get().getName() + "' added to cart.");
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }
    }

    public void removeProductById(int id) {
        boolean removed = items.removeIf(product -> product.getId() == id);
        if (removed) {
            System.out.println("Product with ID " + id + " removed from cart.");
        } else {
            System.out.println("Product with ID " + id + " was not in the cart.");
        }
    }

    public void showCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("--- Your Cart ---");
            items.forEach(System.out::println);
            System.out.println("-----------------");
        }
    }
}
