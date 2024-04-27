package org.example.business.dao.menagement;

import org.example.infrastructure.database.entity.CustomerEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerDAO {
    Optional<CustomerEntity> findByEmail(String email);

    void issueInvoice(CustomerEntity customer);

    void saveServiceRequest(CustomerEntity customer);

    CustomerEntity saveCustomer(CustomerEntity entity);
}
