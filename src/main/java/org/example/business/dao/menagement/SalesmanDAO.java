package org.example.business.dao.menagement;

import org.example.infrastructure.database.entity.SalesmanEntity;

import java.util.Optional;

public interface SalesmanDAO {
    Optional<SalesmanEntity> findByPesel(String pesel);
}
