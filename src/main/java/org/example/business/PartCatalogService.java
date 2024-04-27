package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.PartDAO;
import org.example.infrastructure.database.entity.PartEntity;
import org.example.infrastructure.database.entity.ServiceEntity;

import java.util.Optional;

@AllArgsConstructor
public class PartCatalogService {
    private final PartDAO partDAO;

    public PartEntity findPart(String partSerialNumber) {
            Optional<PartEntity> partEntity = partDAO.findBySerialNumber(partSerialNumber);
            if (partEntity.isEmpty()) {
                throw new RuntimeException("Provided part with part serial number: [%s] doesn't exist".formatted(partSerialNumber));
            }
            return partEntity.get();
    }
}
