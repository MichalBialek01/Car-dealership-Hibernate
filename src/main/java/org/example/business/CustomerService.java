package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.CustomerDAO;
import org.example.domain.CarServiceRequest;
import org.example.infrastructure.database.entity.AddressEntity;
import org.example.infrastructure.database.entity.CustomerEntity;

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

    public void saveServiceRequest(CustomerEntity customer) {
        customerDAO.saveServiceRequest(customer);
    }

    public CustomerEntity saveCustomer(CarServiceRequest.Customer customer) {
        CustomerEntity entity = CustomerEntity.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .address(
                        AddressEntity
                                .builder()
                                .country(customer.getAddress().getCountry())
                                .city(customer.getAddress().getCity())
                                .postalCode(customer.getAddress().getPostalCode())
                                .address(customer.getAddress().getAddress())
                                .build())
                .build();
        return customerDAO.saveCustomer(entity);
    }
}

