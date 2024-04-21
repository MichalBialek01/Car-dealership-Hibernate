package org.example.business.menagement;

import lombok.experimental.UtilityClass;
import org.example.infrastructure.database.entity.*;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class InputDataMapper {
    public static SalesmanEntity mapSalesman(String line) {
        List<String> splitedData = DataManipulationUtil.lineSplit(line);
        return SalesmanEntity
                .builder()
                .name(splitedData.get(0))
                .surname(splitedData.get(1))
                .pesel(splitedData.get(2))
                .build();
    }

    public static MechanicEntity mapMechanic(String line) {
        List<String> splitedData = DataManipulationUtil.lineSplit(line);
        return MechanicEntity
                .builder()
                .name(splitedData.get(0))
                .surname(splitedData.get(1))
                .pesel(splitedData.get(2))
                .build();
    }

    public static CarToBuyEntity mapCarToBuy(String line) {
        List<String> splitedData = DataManipulationUtil.lineSplit(line);
        return CarToBuyEntity
                .builder()
                .vin(splitedData.get(0))
                .brand(splitedData.get(1))
                .model(splitedData.get(2))
                .year(Integer.valueOf(splitedData.get(3)))
                .color(splitedData.get(4))
                .price(new BigDecimal(splitedData.get(5)))
                .build();
    }

    public static ServiceEntity mapServices(String line) {
        List<String> splitedData = DataManipulationUtil.lineSplit(line);
        return ServiceEntity
                .builder()
                .serviceCode(splitedData.get(0))
                .description(splitedData.get(1))
                .price(new BigDecimal(splitedData.get(2)))
                .build();
    }

    public static PartEntity mapParts(String line) {
        List<String> splitedData = DataManipulationUtil.lineSplit(line);
        return PartEntity
                .builder()
                .serialNumber(splitedData.get(0))
                .description(splitedData.get(1))
                .price(new BigDecimal(splitedData.get(2)))
                .build();
    }
}
