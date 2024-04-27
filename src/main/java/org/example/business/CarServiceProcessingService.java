package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.ServiceRequestProcessingDAO;
import org.example.business.menagement.DataPreparationService;
import org.example.business.menagement.Keys;
import org.example.domain.CarServiceProcessingRequest;
import org.example.infrastructure.database.entity.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class CarServiceProcessingService {
    private final DataPreparationService dataPreparationService;
    private final MechanicService mechanicService;
    private final CarService carService;
    private final CarServiceRequestService carToServiceRequestService;
    private final ServiceCatalogService catalogService;
    private final PartCatalogService partCatalogService;
    private final ServiceRequestProcessingDAO serviceRequestProcessingDAO;
    public void process() {
        List<CarServiceProcessingRequest> toProcess = dataPreparationService.prepareServiceRequestToProcess();
        toProcess.forEach(this::processRequest);
    }

    private void processRequest(CarServiceProcessingRequest request) {
        MechanicEntity mechanic = mechanicService.findMechanic(request.getMechnicPesel());
        //Checking that carToServiceEntity is existing
        CarToServiceEntity carToServiceEntity = carService.findCarToService(request.getCarVin()).orElseThrow();
        CarServiceRequestEntity carServiceRequest =  carToServiceRequestService.findAnyActiveRequest(request.getCarVin());

        ServiceEntity service = catalogService.findService(request.getServiceCode());

        ServiceMechanicEntity mechanicEntity = buildServiceMechanicEntity(request,mechanic,carServiceRequest,service);

        if(Keys.Constant.FINISHED.toString().equals(request.getDone())){
            carServiceRequest.setCompletedDateTime(OffsetDateTime.now());
        }

        //Case if no parts are required
        if(Objects.isNull(request.getPartSerialNumber()) || Objects.isNull(request.getPartQuantity())){
            serviceRequestProcessingDAO.process(carServiceRequest,mechanicEntity);
        }else {
            PartEntity part = partCatalogService.findPart(request.getPartSerialNumber());
            ServicePartEntity servicePartEntity = buildServicePartEntity(request,carServiceRequest,part);
            serviceRequestProcessingDAO.process(carServiceRequest,mechanicEntity,servicePartEntity);
        }

    }

    private ServicePartEntity buildServicePartEntity(
            CarServiceProcessingRequest request,
            CarServiceRequestEntity carServiceRequest,
            PartEntity part) {

        return ServicePartEntity
                .builder()
                .quantity(request.getPartQuantity())
                .carServiceRequest(carServiceRequest)
                .part(part)
                .build();
    }

    private ServiceMechanicEntity buildServiceMechanicEntity(
            CarServiceProcessingRequest request,
            MechanicEntity mechanic,
            CarServiceRequestEntity carServiceRequest,
            ServiceEntity service) {

        return ServiceMechanicEntity.builder()
                .hours(request.getHours())
                .comment(request.getComment())
                .carServiceRequest(carServiceRequest)
                .service(service)
                .mechanic(mechanic)
                .build();
    }
}
