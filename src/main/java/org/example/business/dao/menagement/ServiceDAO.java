package org.example.business.dao.menagement;

import org.example.infrastructure.database.entity.ServiceEntity;

import java.util.Optional;

public interface ServiceDAO {
    Optional<ServiceEntity> findByServiceCode(String serviceCode);

}
