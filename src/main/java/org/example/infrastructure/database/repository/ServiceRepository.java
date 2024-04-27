package org.example.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.example.business.dao.menagement.ServiceDAO;
import org.example.infrastructure.configuration.HibernateUtil;
import org.example.infrastructure.database.entity.CarToBuyEntity;
import org.example.infrastructure.database.entity.ServiceEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.Objects;
import java.util.Optional;

public class ServiceRepository implements ServiceDAO {
    @Override
    public Optional<ServiceEntity> findByServiceCode(String serviceCode) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ServiceEntity> criteriaQuery = criteriaBuilder.createQuery(ServiceEntity.class);
            Root<ServiceEntity> root = criteriaQuery.from(ServiceEntity.class);

            ParameterExpression<String> parameter1 = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("serviceCode"), parameter1));

            Query<ServiceEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter1,serviceCode);
            try {
                ServiceEntity singleResult = query.getSingleResult();
                session.getTransaction().commit();
                return Optional.of(singleResult);
            } catch (Throwable ex) {
                return Optional.empty();
            }
        }
    }
}
