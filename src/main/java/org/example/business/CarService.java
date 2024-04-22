package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.CarDAO;
import org.example.infrastructure.database.entity.CarToBuyEntity;

import java.util.Optional;
@AllArgsConstructor
public class CarService {

    private final CarDAO carDAO;
    public CarToBuyEntity findCarToBuy(String vin) {
        Optional<CarToBuyEntity> carToBuyByVin = carDAO.findCarToBuyByVin(vin);
        if(carToBuyByVin.isEmpty()){
            throw new RuntimeException("Provided car with vin: [%s] doesn't exist".formatted(vin));
        }
        return carToBuyByVin.get();
    }
}
