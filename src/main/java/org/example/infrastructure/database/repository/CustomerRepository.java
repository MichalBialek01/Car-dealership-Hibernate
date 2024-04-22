package org.example.infrastructure.database.repository;

import org.example.business.dao.menagement.CustomerDAO;
import org.example.infrastructure.configuration.HibernateUtil;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.hibernate.Session;

import java.util.Objects;
import java.util.Optional;

public class CustomerRepository implements CustomerDAO {
    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            String hqlQuery = "SELECT customer FROM CustomerEntity customer WHERE customer.email = :email";
            Optional<CustomerEntity> queryResult = session.createQuery(hqlQuery, CustomerEntity.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return queryResult;
        }
    }


    @Override
    public void issueInvoice(CustomerEntity customer) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            if (Objects.isNull(customer.getCustomerId())) {
                session.persist(customer);
            }
            customer.getInvoices().stream()
                    .filter(invoice -> Objects.isNull(invoice.getInvoiceId()))
                    .forEach(invoice -> {
                        invoice.setCustomer(customer);
                        session.persist(invoice);
                    });

            session.getTransaction().commit();
        }
    }
}

