package org.example.business.menagement;

import org.example.domain.CarServiceProcessingRequest;
import org.example.domain.CarServiceRequest;
import org.example.infrastructure.database.entity.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataPreparationService {

    public List<?> prepareInitData() {
        List<SalesmanEntity> salesman = InputDataCache.getInputData(
                Keys.InputDataGroup.INIT,
                Keys.Entity.SALESMAN,
                InputDataMapper::mapSalesman
        );

        List<MechanicEntity> mechanics = InputDataCache.getInputData(
                Keys.InputDataGroup.INIT,
                Keys.Entity.MECHANIC,
                InputDataMapper::mapMechanic
        );

        List<CarToBuyEntity> cars = InputDataCache.getInputData(
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
        return Stream.of(salesman, mechanics, cars, services, parts)
                .flatMap(Collection::stream)
                .toList();
    }

    public List<Map<String, List<String>>> prepareFirstTimePurchaseData() {
        //List of records that contains 3 Map pairs where key is Entity, and value is
        return InputDataCache.getInputData(Keys.InputDataGroup.BUY_FIRST_TIME,
                line -> prepareMap(line));
    }

    public List<Map<String, List<String>>> prepareNextTimePurchaseData() {
        //List of records that contains 3 Map pairs where key is Entity, and value is
        return InputDataCache.getInputData(Keys.InputDataGroup.BUY_AGAIN,
                line -> prepareMap(line));
    }

    private Map<String, List<String>> prepareMap(String line) {
        //Spliting for "->" slices
        List<String> groupedValues = Arrays.stream(line.split("->")).map(element -> element.trim()).toList();
        return IntStream.iterate(0, previous -> previous + 2) // getting even numbers
                .boxed()
                .limit(3) //limited to 3
                .collect(Collectors.toMap(groupedValues::get,//Map's key's are on previous even provided numbers
                        iterator -> List.of(groupedValues.get(iterator + 1).split(";"))));
        //Get list of even numbers+1 and split them by  ";" sign
        //The result is map that include 3 maps

    }

    public CustomerEntity buildCustomerEntity(List<String> inputData, InvoiceEntity invoice) {
        return CustomerEntity
                .builder()
                .name(inputData.get(0))
                .surname(inputData.get(1))
                .phone(inputData.get(2))
                .email(inputData.get(3))
                .address(
                        AddressEntity
                                .builder()
                                .country(inputData.get(0))
                                .city(inputData.get(1))
                                .postalCode(inputData.get(2))
                                .address(inputData.get(3))
                                .build()
                )
                .invoices(Set.of(invoice))
                .build();
    }

    public List<CarServiceRequest> createServiceRequests() {
        return InputDataCache.getInputData(Keys.InputDataGroup.SERVICE_REQUEST, this::prepareMap)
                .stream()
                .map(this::createServiceRequest)
                .toList();

    }

    private CarServiceRequest createServiceRequest(Map<String, List<String>> inputData) {
        return CarServiceRequest
                .builder()
                .customer(createCustomer(inputData.get(Keys.Entity.CUSTOMER.toString())))
                .car(createCar(inputData.get(Keys.Entity.CAR.toString())))
                .customerComment(inputData.get(Keys.Constant.WHAT.toString()).get(0))
                .build();
    }

    private CarServiceRequest.Car createCar(List<String> inputData) {
        if (inputData.size() == 1) {
            return CarServiceRequest.Car
                    .builder()
                    .vin(inputData.get(0))
                    .build();
        }
        return CarServiceRequest.Car.builder()
                .vin(inputData.get(0))
                .brand(inputData.get(1))
                .model(inputData.get(2))
                .year(Integer.valueOf(inputData.get(3)))
                .build();
    }

    private CarServiceRequest.Customer createCustomer(List<String> inputData) {

        if (inputData.size() == 1) {
            return CarServiceRequest.Customer
                    .builder()
                    .email(inputData.get(0))
                    .build();
        }
        return CarServiceRequest.Customer
                .builder()
                .name(inputData.get(0))
                .surname(inputData.get(1))
                .phone(inputData.get(2))
                .email(inputData.get(3))
                .address(
                        CarServiceRequest.Address
                                .builder()
                                .country(inputData.get(4))
                                .city(inputData.get(5))
                                .postalCode(inputData.get(6))
                                .address(inputData.get(7))
                                .build()
                )
                .build();
    }

    public List<CarServiceProcessingRequest> prepareServiceRequestToProcess() {
        return InputDataCache.getInputData(Keys.InputDataGroup.DO_THE_SERVICE, this::prepareMap)
                .stream()
                .map(this::createCarServiceRequestToProcess)
                .toList();
    }

    private CarServiceProcessingRequest createCarServiceRequestToProcess(Map<String, List<String>> inputData) {
        List<String> what = inputData.get(Keys.Constant.WHAT.toString());
        return CarServiceProcessingRequest
                .builder()
                .mechnicPesel(inputData.get(Keys.Entity.MECHANIC.toString()).get(0))
                .carVin(inputData.get(Keys.Entity.CAR.toString()).get(0))
                .partSerialNumber(Optional.of(what.get(0)).filter(part -> !part.isBlank()).orElse(null))
                .partQuantity(Optional.ofNullable(what.get(1)).filter(value -> !value.isBlank()).map(Integer::parseInt).orElse(null))
                .serviceCode(what.get(2))
                .hours(Integer.parseInt(what.get(3)))
                .comment(what.get(4))
                .done(what.get(5))
                .build();
    }
}
