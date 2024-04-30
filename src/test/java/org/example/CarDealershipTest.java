package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.business.*;
import org.example.business.dao.menagement.*;
import org.example.business.menagement.CarDealershipManagementService;
import org.example.business.menagement.DataPreparationService;
import org.example.infrastructure.configuration.HibernateUtil;
import org.example.infrastructure.database.repository.*;
import org.junit.jupiter.api.*;

/*
    Test class to enforce defined program path.
    Typically test classes shouldn't be used in this way, but for this particular example it does.

 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealershipManagementService carDealershipManagementService;
    private CarPurchaseService carPurchaseService;
    private CarServiceRequestService carServiceRequestService;
    private CarServiceProcessingService carServiceProcessingService;
    private CarService carService;


    @BeforeEach
    void beforeEach() {
        CarDealershipManagementRepository carDealershipManagementDAO = new CarDealershipManagementRepository();
        CarDAO carDAO = new CarRepository();
        SalesmanDAO salesmanDAO = new SalesmanRepository();
        CustomerDAO customerDAO = new CustomerRepository();
        MechanicDAO mechanicDAO = new MechanicRepository();
        ServiceDAO serviceDAO = new ServiceRepository();
        PartDAO partDAO = new PartRepository();
        CarServiceRequestDAO carServiceRequestDAO = new CarServiceRequestRepository();
        DataPreparationService dataPreparationService = new DataPreparationService();
        ServiceCatalogService serviceCatalogService = new ServiceCatalogService(serviceDAO);
        PartCatalogService partCatalogService = new PartCatalogService(partDAO);
        CarService carService = new CarService(carDAO);
        ServiceRequestProcessingDAO serviceRequestProcessingDAO = new ServiceRequestProcessingRepository();
        CustomerService customerService = new CustomerService(customerDAO);
        SalesmanService salesmanService = new SalesmanService(salesmanDAO);
        MechanicService mechanicService = new MechanicService(mechanicDAO);

        this.carDealershipManagementService = new CarDealershipManagementService(
                carDealershipManagementDAO,
                dataPreparationService
        );
        this.carPurchaseService = new CarPurchaseService(
                dataPreparationService,
                customerService,
                carService,
                salesmanService
        );
        this.carServiceRequestService = new CarServiceRequestService(
                dataPreparationService,
                carService,
                customerService,
                carServiceRequestDAO
        );
        this.carServiceProcessingService = new CarServiceProcessingService(
                dataPreparationService,
                mechanicService,
                carService,
                carServiceRequestService,
                serviceCatalogService,
                partCatalogService,
                serviceRequestProcessingDAO
        );
        this.carService = new CarService(
                carDAO
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
        carServiceProcessingService.process();
    }

    @Test
    void printCarHistory() {
        log.info("### Running order 6");
        carService.printCarHistory("2C3CDYAG2DH731952");
    }


}
