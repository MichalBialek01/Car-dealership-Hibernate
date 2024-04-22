package org.example.infrastructure.database.repository;

import org.example.business.dao.menagement.SalesmanDAO;
import org.example.infrastructure.configuration.HibernateUtil;
import org.example.infrastructure.database.entity.SalesmanEntity;
import org.hibernate.Session;

import java.util.Objects;
import java.util.Optional;

public class SalesmanRepository implements SalesmanDAO {

    @Override
    public Optional<SalesmanEntity> findByPesel(String pesel) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            String hqlQuery = "SELECT salesman FROM SalesmanEntity salesman WHERE salesman.pesel = :pesel";
            Optional<SalesmanEntity> queryResult = session.createQuery(hqlQuery, SalesmanEntity.class)
                    .setParameter("pesel", pesel)
                    .uniqueResultOptional();

            session.getTransaction().commit();
            return queryResult;

            /*

            By criteriaQuery:

              HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<SalesmanEntity> criteriaQuery = criteriaBuilder.createQuery(SalesmanEntity.class);
            Root<SalesmanEntity> root = criteriaQuery.from(SalesmanEntity.class);

            ParameterExpression<String> parameter1 = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("pesel"), parameter1));

            Query<SalesmanEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter1, pesel);
            try {
                SalesmanEntity singleResult = query.getSingleResult();
                session.getTransaction().commit();
                return Optional.of(singleResult);
            } catch (Throwable ex) {
                return Optional.empty();
            }
             */

        }
    }

}
