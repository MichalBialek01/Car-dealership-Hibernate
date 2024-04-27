package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.ServiceDAO;
import org.example.infrastructure.database.entity.SalesmanEntity;
import org.example.infrastructure.database.entity.ServiceEntity;

import javax.xml.catalog.Catalog;
import java.util.Optional;

@AllArgsConstructor
public class ServiceCatalogService {
    private final ServiceDAO serviceDAO;

    public ServiceEntity findService(String serviceCode) {
        Optional<ServiceEntity> serviceEntity = serviceDAO.findByServiceCode(serviceCode);
        if (serviceEntity.isEmpty()) {
            throw new RuntimeException("Provided Service with serviceCode: [%s] doesn't exist".formatted(serviceCode));
        }
        return serviceEntity.get();
    }
}

