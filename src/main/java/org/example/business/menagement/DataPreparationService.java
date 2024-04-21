package org.example.business.menagement;

import org.example.infrastructure.database.entity.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class DataPreparationService {

    public List<?> prepareInitData() {
        List< SalesmanEntity> salesman = InputDataCache.getInputData(
                Keys.InputDataGroup.INIT,
                Keys.Entity.SALESMAN,
                InputDataMapper::mapSalesman
        );

        List<MechanicEntity> mechanics = InputDataCache.getInputData(
                Keys.InputDataGroup.INIT,
                Keys.Entity.MECHANIC,
                InputDataMapper::mapMechanic
        );

        List<CarToBuyEntity> cars= InputDataCache.getInputData(
                Keys.InputDataGroup.INIT,
                Keys.Entity.CAR,
                InputDataMapper::mapCarToBuy
        );

        List<ServiceEntity> services = InputDataCache.getInputData(
                Keys.InputDataGroup.INIT,
                Keys.Entity.SERVICE,
                InputDataMapper::mapServices
        );
        List<PartEntity> parts = InputDataCache.getInputData(
                Keys.InputDataGroup.INIT,
                Keys.Entity.PART,
                InputDataMapper::mapParts
        );
        return Stream.of(salesman,mechanics,cars,services,parts)
                .flatMap(Collection::stream)
                .toList();
    }
}
