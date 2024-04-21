package org.example.business.menagement;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.CarDealershipManagementDAO;

@AllArgsConstructor
public class CarDealershipManagementService {

private final CarDealershipManagementDAO carDealershipMenagementDAO;
    public void purge(){
        carDealershipMenagementDAO.purge();
    }

}
