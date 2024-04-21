package org.example.business.menagement;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.CarDealershipManagementDAO;

import java.util.List;

@AllArgsConstructor
public class CarDealershipManagementService {

private final CarDealershipManagementDAO carDealershipManagementDAO;
private final DataPreparationService dataPreparationService;
    public void purge(){
        carDealershipManagementDAO.purge();
    }
    public void init(){
            List<?> entities = dataPreparationService.prepareInitData();
            carDealershipManagementDAO.saveAll(entities);
    }
}
