package org.example.cartapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Scanner scanner = new Scanner(System.in);

        try {
            runApplication(context, scanner);
        } finally {
            context.close();
            scanner.close();
        }
    }

    private static void runApplication(AnnotationConfigApplicationContext context, Scanner scanner) {
        ProductRepository productRepository = context.getBean(ProductRepository.class);

        System.out.println("Welcome to the Console Cart Application!");
        Cart cart = null;

        while (true) {
            if (cart == null) {
                cart = context.getBean(Cart.class);
            }

            System.out.println("\nAvailable products:");
            productRepository.getAllProducts().forEach(System.out::println);

            System.out.println("\nChoose an action:");
            System.out.println("1. Add product to cart");
            System.out.println("2. Remove product from cart");
            System.out.println("3. View cart");
            System.out.println("4. Get a new cart");
            System.out.println("5. Exit");
            System.out.print("Your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter product ID to add: ");
                    try {
                        int addId = Integer.parseInt(scanner.nextLine());
                        cart.addProductById(addId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid ID.");
                    }
                    break;
                case 2:
                    System.out.print("Enter product ID to remove: ");
                    try {
                        int removeId = Integer.parseInt(scanner.nextLine());
                        cart.removeProductById(removeId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid ID.");
                    }
                    break;
                case 3:
                    cart.showCart();
                    break;
                case 4:
                    cart = context.getBean(Cart.class);
                    System.out.println("A new cart has been created.");
                    break;
                case 5:
                    System.out.println("Exiting application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
