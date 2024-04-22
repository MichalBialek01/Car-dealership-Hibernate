package org.example.business.dao.menagement;

import org.example.infrastructure.database.entity.CarToBuyEntity;

import java.util.Optional;

public interface CarDAO {
    Optional<CarToBuyEntity> findCarToBuyByVin(String vin);
}
