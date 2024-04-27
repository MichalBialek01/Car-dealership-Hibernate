package org.example.infrastructure.database.repository;

import org.example.business.dao.menagement.CarServiceRequestDAO;
import org.example.domain.CarServiceRequest;
import org.example.infrastructure.configuration.HibernateUtil;
import org.example.infrastructure.database.entity.CarServiceRequestEntity;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CarServiceRequestRepository implements CarServiceRequestDAO {
    @Override
    public Set<CarServiceRequestEntity> findActiveServiceRequestsByCarVin(String carVin) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            String hqlQuery = """
                   SELECT serviceRequest FROM CarServiceRequestEntity serviceRequest
                   WHERE
                   serviceRequest.car.vin = :vin
                   AND serviceRequest.completedDateTime IS NULL
                    """;

            List<CarServiceRequestEntity> carToServices = session.createQuery(hqlQuery, CarServiceRequestEntity.class)
                    .setParameter("vin", carVin)
                    .list();

            return new HashSet<>(carToServices);
        }
    }
}
