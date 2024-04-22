package org.example.business.menagement;

import lombok.AllArgsConstructor;
import org.example.business.CarService;
import org.example.business.CustomerService;
import org.example.domain.CarServiceRequest;
import org.example.infrastructure.database.entity.CarServiceRequestEntity;
import org.example.infrastructure.database.entity.CarToServiceEntity;
import org.example.infrastructure.database.entity.CustomerEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CarServiceRequestService {
    private final DataPreparationService dataPreparationService;
    private final CarService carService;
    private final CustomerService customerService;

    public void requestService() {
        // We distinguish 2 cases: 1 if car to service and client already exist is on database, and second if client and car are external guests.
        // Distinguish bases on if getted car parameters  (vin&year&brand) aren't exisitng. if - true = this car doesn't exist

        Map<Boolean, List<CarServiceRequest>> serviceRequests = dataPreparationService.createServiceRequests()
                .stream().collect(Collectors.groupingBy(element -> element.getCar().shouldExisitInCarToBuy()));


        serviceRequests.get(true).forEach(this::saveServiceRequestForExisitngCar);
        serviceRequests.get(false).forEach(this::saveServiceRequestForNewCar);

    }

    private void saveServiceRequestForExisitngCar(CarServiceRequest carServiceRequest) {
        Optional<CarToServiceEntity> car = carService.findCarToService(carServiceRequest.getCar().getVin());
        CustomerEntity customer = customerService.findCustomer(carServiceRequest.getCustomer().getEmail());
        //Basing on above creating caToServiceEntity and connecting it with customer
        CarServiceRequestEntity carToServiceEntity = buildCarServiceRequestEntity(carServiceRequest, car, customer);
    }
//TODO -  32:50/8

    private CarServiceRequestEntity buildCarServiceRequestEntity(CarServiceRequest carServiceRequest, Optional<CarToServiceEntity> car, CustomerEntity customer) {
        return CarToServiceEntity
                .builder()
                .carServiceRequestNumber(generateCarServiceRequestNumber(when))
                .receivedDateTime(when)
                .completedDateTime(getCompleatedDT())
                .customerComment(customer.getCustommerComment())
                .customer()
                .car()
                .build()

    }

    private void saveServiceRequestForNewCar(CarServiceRequest carServiceRequest) {

    }
}
