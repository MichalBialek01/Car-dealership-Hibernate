package org.example.business.dao.menagement;

import org.example.infrastructure.database.entity.CarServiceRequestEntity;
import org.example.infrastructure.database.entity.ServiceMechanicEntity;
import org.example.infrastructure.database.entity.ServicePartEntity;

public interface ServiceRequestProcessingDAO {
    void process(CarServiceRequestEntity carServiceRequest, ServiceMechanicEntity mechanicEntity, ServicePartEntity servicePartEntity);

    void process(CarServiceRequestEntity carServiceRequest, ServiceMechanicEntity mechanicEntity);
}
