package org.example.customer;


import org.example.customer.dao.CustomerDao;
import org.example.customer.model.Customer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        CustomerDao customerDao = context.getBean(CustomerDao.class);


        Customer newCustomer = new Customer(null, "Rodion Plakhotniuk", "rodion@example.com", "8888-8888");
        customerDao.add(newCustomer);
        System.out.println("Клієнта додано");


        Customer found = customerDao.findById(1L);
        System.out.println("Знайдено клієнта: " + found);


        found.setEmail("rodion.new@example.com");
        customerDao.update(found);
        System.out.println("Клієнта оновлено");


        List<Customer> all = customerDao.findAll();
        System.out.println("Всі клієнти:");
        all.forEach(System.out::println);


        customerDao.delete(found.getId());
        System.out.println("Клієнта видалено");

        context.close();
    }
}
