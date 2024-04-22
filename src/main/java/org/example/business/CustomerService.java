package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.CustomerDAO;
import org.example.infrastructure.database.entity.CustomerEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    public void issueInvoice(CustomerEntity customer) {
        customerDAO.issueInvoice(customer);
    }

    public CustomerEntity findCustomer(String email) {
        Optional<CustomerEntity> customer = customerDAO.findByEmail(email);
        if (email.isEmpty()) {
            throw new RuntimeException("Provided customer with email: [%s] doesn't exist".formatted(customer));
        }
        return customer.get();
    }
}

