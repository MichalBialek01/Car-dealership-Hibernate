package org.example.business.menagement;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.CarDealershipManagementDAO;

import java.util.List;

@AllArgsConstructor
public class CarDealershipManagementService {

private final CarDealershipManagementDAO carDealershipMenagementDAO;
private final DataPreparationService dataPreparationService;
    public void purge(){
        carDealershipMenagementDAO.purge();
    }
    public void init(){
            List<?> entities = dataPreparationService.prepareInitDat();
            carDealershipMenagementDAO.saveAll(entities);
    }
}
