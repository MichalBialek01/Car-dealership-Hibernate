package org.example.business.dao.menagement;

import org.example.infrastructure.database.entity.PartEntity;

import java.util.Optional;

public interface PartDAO {
    Optional<PartEntity> findBySerialNumber(String serialNumber);
}
