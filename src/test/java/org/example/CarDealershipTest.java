package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.business.menagement.CarDealershipManagementService;
import org.example.business.menagement.InputDataCache;
import org.example.infrastructure.configuration.HibernateUtil;
import org.example.infrastructure.database.repository.CarDealershipManagementRepository;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealershipManagementService carDealershipMenagementService;
    @BeforeEach
    void beforeEach(){
        this.carDealershipMenagementService= new CarDealershipManagementService(
                new CarDealershipManagementRepository()
        );
    }

    @AfterAll
    static void afterAll(){
        HibernateUtil.closeSessionFactory();
    }

    @Test
    @Order(1)
    void purge() {
        log.info("### Running order 1");
        carDealershipMenagementService.purge();
    }
    @Order(2)
    @Test
    void init() {
        log.info("### Running order 2");
    }
    @Order(3)
    @Test
    void purchase() {
        log.info("### Running order 3");
    }
    @Order(4)
    @Test
    void makeServiceRequest() {
        log.info("### Running order 4");
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
