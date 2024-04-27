package org.example.business.dao.menagement;

import org.example.domain.CarServiceRequest;
import org.example.infrastructure.database.entity.CarServiceRequestEntity;

import java.util.Set;

public interface CarServiceRequestDAO {
    Set<CarServiceRequestEntity> findActiveServiceRequestsByCarVin(String carVin);
}
