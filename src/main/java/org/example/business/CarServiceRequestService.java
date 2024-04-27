package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.CarServiceRequestDAO;
import org.example.business.menagement.DataPreparationService;
import org.example.domain.CarServiceRequest;
import org.example.infrastructure.database.entity.CarServiceRequestEntity;
import org.example.infrastructure.database.entity.CarToBuyEntity;
import org.example.infrastructure.database.entity.CarToServiceEntity;
import org.example.infrastructure.database.entity.CustomerEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CarServiceRequestService {
    private final DataPreparationService dataPreparationService;
    private final CarService carService;
    private final CustomerService customerService;
    private final CarServiceRequestDAO carServiceRequestDAO;

    public void requestService() {
        // We distinguish 2 cases: 1 if car to service and client already exist is on database, and second if client and car are external guests.
        // Distinguish bases on if got car parameters  (vin&year&brand) aren't exisitng. if - true = this car doesn't exist

        Map<Boolean, List<CarServiceRequest>> serviceRequests = dataPreparationService.createServiceRequests()
                .stream().collect(Collectors.groupingBy(element -> element.getCar().shouldExistInCarToBuy()));


        serviceRequests.get(true).forEach(this::saveServiceRequestsForExistingCar);
        serviceRequests.get(false).forEach(this::saveServiceRequestForNewCar);

    }

    private void saveServiceRequestsForExistingCar(CarServiceRequest request) {
        CarToServiceEntity car = carService.findCarToService(request.getCar().getVin())
                .orElse(findInCarToBuyAndSaveInCarToService(request.getCar()));
        CustomerEntity customer = customerService.findCustomer(request.getCustomer().getEmail());

        CarServiceRequestEntity carServiceRequestEntity = buildCarServiceRequestEntity(request, car, customer);
        customer.addServiceRequest(carServiceRequestEntity);
        customerService.saveServiceRequest(customer);
    }

    private CarToServiceEntity findInCarToBuyAndSaveInCarToService(CarServiceRequest.Car car) {
        CarToBuyEntity carToBuy = carService.findCarToBuy(car.getVin());
        return carService.saveCarToService(carToBuy);
    }

    //Basing on above creating caToServiceEntity and connecting it with customer

    private CarServiceRequestEntity buildCarServiceRequestEntity(
            CarServiceRequest request,
            CarToServiceEntity car,
            CustomerEntity customer
    ) {
        OffsetDateTime when = OffsetDateTime.of(2027, 1, 10, 10, 2, 10, 0, ZoneOffset.UTC);
        return CarServiceRequestEntity.builder()
                .carServiceRequestNumber(generateCarServiceRequestNumber(when))
                .receivedDateTime(when)
                .customerComment(request.getCustomerComment())
                .customer(customer)
                .car(car)
                .build();
    }

    private String generateCarServiceRequestNumber(OffsetDateTime when) {
        return "%s.%s.%s-%s.%s.%s.%s".formatted(
                when.getYear(),
                when.getMonth().ordinal(),
                when.getDayOfMonth(),
                when.getHour(),
                when.getMinute(),
                when.getSecond(),
                randomInt(10, 100)
        );
    }
    private Object randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    private void saveServiceRequestForNewCar(CarServiceRequest request) {

        CarToServiceEntity car = carService.saveCarToService(request.getCar());
        CustomerEntity customer = customerService.saveCustomer(request.getCustomer());

        CarServiceRequestEntity carToServiceRequestEntity = buildCarServiceRequestEntity(request, car, customer);
        customer.addServiceRequest(carToServiceRequestEntity);
        customerService.saveServiceRequest(customer);
    }

    public CarServiceRequestEntity findAnyActiveRequest(String carVin) {
        Set<CarServiceRequestEntity> serviceRequests =  carServiceRequestDAO.findActiveServiceRequestsByCarVin(carVin);
         return serviceRequests.stream()
                 .findAny()
                 .orElseThrow( ()-> new RuntimeException("Could not find service request for car with VIN number: [%s] ".formatted(carVin)));
    }
}
