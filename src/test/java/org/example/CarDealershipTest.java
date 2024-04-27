package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.business.*;
import org.example.business.dao.menagement.CarDAO;
import org.example.business.dao.menagement.CustomerDAO;
import org.example.business.dao.menagement.SalesmanDAO;
import org.example.business.menagement.CarDealershipManagementService;
import org.example.business.menagement.DataPreparationService;
import org.example.infrastructure.configuration.HibernateUtil;
import org.example.infrastructure.database.repository.CarDealershipManagementRepository;
import org.example.infrastructure.database.repository.CarRepository;
import org.example.infrastructure.database.repository.CustomerRepository;
import org.example.infrastructure.database.repository.SalesmanRepository;
import org.junit.jupiter.api.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealershipManagementService carDealershipManagementService;
    private CarPurchaseService carPurchaseService;
    private CarServiceRequestService carServiceRequestService;
    CarDAO carDAO = new CarRepository();
    SalesmanDAO salesmanDAO = new SalesmanRepository();
    CustomerDAO customerDAO = new CustomerRepository();
    DataPreparationService dataPreparationService = new DataPreparationService();

    @BeforeEach
    void beforeEach() {
        this.carDealershipManagementService = new CarDealershipManagementService(
                new CarDealershipManagementRepository(),
                dataPreparationService
        );
        CarService carService = new CarService(carDAO);
        CustomerService customerService = new CustomerService(customerDAO);
        this.carPurchaseService = new CarPurchaseService(
                dataPreparationService,
                customerService,
                carService,
                new SalesmanService(salesmanDAO)

        );
        this.carServiceRequestService = new CarServiceRequestService(
                dataPreparationService,
                carService
                ,customerService
        );
    }

    @AfterAll
    static void afterAll() {
        HibernateUtil.closeSessionFactory();
    }

    @Test
    @Order(1)
    void purge() {
        log.info("### Running order 1");
        carDealershipManagementService.purge();
    }

    @Test
    @Order(2)
    void init() {
        log.info("### Running order 2");
        carDealershipManagementService.init();
    }
    @Test
    @Order(3)
    void purchase() {
        log.info("### Running order 3");
        carPurchaseService.purchase();
    }

    @Order(4)
    @Test
    void makeServiceRequest() {
        log.info("### Running order 4");
        carServiceRequestService.requestService();
    }

    @Order(5)
    @Test
    void processServiceRequests() {
        log.info("### Running order 5");
    }

    @Test
    void printCarHistory() {
        log.info("### Running order 6");
    }


}
