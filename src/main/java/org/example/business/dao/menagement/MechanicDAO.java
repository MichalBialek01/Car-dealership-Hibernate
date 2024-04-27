package org.example.business.dao.menagement;

import org.example.infrastructure.database.entity.MechanicEntity;
import org.example.infrastructure.database.entity.SalesmanEntity;

import java.util.Optional;

public interface MechanicDAO {
    Optional<MechanicEntity> findByPesel(String pesel);
}
