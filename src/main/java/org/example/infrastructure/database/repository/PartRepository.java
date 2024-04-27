package org.example.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.example.business.dao.menagement.PartDAO;
import org.example.infrastructure.configuration.HibernateUtil;
import org.example.infrastructure.database.entity.PartEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.Objects;
import java.util.Optional;

public class PartRepository implements PartDAO {
    @Override
    public Optional<PartEntity> findBySerialNumber(String serialNumber) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PartEntity> criteriaQuery = criteriaBuilder.createQuery(PartEntity.class);
            Root<PartEntity> root = criteriaQuery.from(PartEntity.class);

            ParameterExpression<String> parameter1 = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("serialNumber"), parameter1));

            Query<PartEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter1,serialNumber);
            try {
                PartEntity singleResult = query.getSingleResult();
                session.getTransaction().commit();
                return Optional.of(singleResult);
            } catch (Throwable ex) {
                return Optional.empty();
            }
        }
    }
}
