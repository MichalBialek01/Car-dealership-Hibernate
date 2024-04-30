package org.example.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.example.business.dao.menagement.CarDAO;
import org.example.infrastructure.configuration.HibernateUtil;
import org.example.infrastructure.database.entity.CarHistoryEntity;
import org.example.infrastructure.database.entity.CarToBuyEntity;
import org.example.infrastructure.database.entity.CarToServiceEntity;
import org.example.infrastructure.database.entity.ServiceEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.Objects;
import java.util.Optional;

public class CarRepository implements CarDAO {
    @Override
    public Optional<CarToBuyEntity> findCarToBuyByVin(String vin) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            //            HQL:
//            String hql = "FROM car_to_buy WHERE vin = :vin";
//            List<CarToBuyEntity> carsByVin = session.createQuery(hql)
//                    .setParameter("vin",vin)
//                    .list();

//            Native query

//            String sql ="SELECT * FROM car_to_buy WHERE vin = :vin";
//            session.createNativeQuery(sql, CarToBuyEntity.class)
//                    .setParameter("vin",vin)
//                    .getResultList();

//            Criteria API
            session.beginTransaction();

            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToBuyEntity> criteriaQuery = criteriaBuilder.createQuery(CarToBuyEntity.class);
            Root<CarToBuyEntity> root = criteriaQuery.from(CarToBuyEntity.class);

            ParameterExpression<String> parameter1 = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), parameter1));

            Query<CarToBuyEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter1, vin);
            try {
                CarToBuyEntity singleResult = query.getSingleResult();
                session.getTransaction().commit();
                return Optional.of(singleResult);
            } catch (Throwable ex) {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<CarToServiceEntity> findCarToServiceByVin(String vin) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToServiceEntity> criteriaQuery = criteriaBuilder.createQuery(CarToServiceEntity.class);
            Root<CarToServiceEntity> root = criteriaQuery.from(CarToServiceEntity.class);

            ParameterExpression<String> parameter1 = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), parameter1));

            Query<CarToServiceEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter1, vin);
            try {
                CarToServiceEntity singleResult = query.getSingleResult();
                session.getTransaction().commit();
                return Optional.of(singleResult);
            } catch (Throwable ex) {
                return Optional.empty();
            }
        }

    }
    @Override
    public CarToServiceEntity saveCarToService(CarToServiceEntity entity) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public CarHistoryEntity findCarHistoryByVin(String vinNumber) {

        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();
//             //TODO - funcionality soon
//            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<CarToBuyEntity> criteriaQuery = criteriaBuilder.createQuery(CarToBuyEntity.class);
//            Root<CarToBuyEntity> root = criteriaQuery.from(CarToBuyEntity.class);
//
//            ParameterExpression<String> parameter1 = criteriaBuilder.parameter(String.class);
//            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vinNumber"), parameter1));
//
//            Query<CarToBuyEntity> query = session.createQuery(criteriaQuery);
//            query.setParameter(parameter1,vinNumber);
//            try {
//                CarToBuyEntity result = query.getSingleResult();
//                CarHistoryEntity
//                        .builder()
//                        .carVin(result.getVin())
//                        .serviceRequests(result.getCa)
//                        .build()
            session.getTransaction().commit();
            return CarHistoryEntity.builder().build();
        }
    }
}

