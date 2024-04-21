package org.example.infrastructure.configuration;

import lombok.extern.slf4j.Slf4j;
import org.example.infrastructure.database.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.Map;

@Slf4j
public class HibernateUtil {

    private static final Map<String, Object> HIBERNATE_SETTINGS = Map.ofEntries(
            Map.entry(Environment.DRIVER, "org.postgresql.Driver"),
            Map.entry(Environment.URL, "jdbc:postgresql://localhost:5432/car_dealership"),
            Map.entry(Environment.USER, "postgres"),
            Map.entry(Environment.PASS, "postgres"),
            Map.entry(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect"),
            Map.entry(Environment.CONNECTION_PROVIDER, "org.hibernate.hikaricp.internal.HikariCPConnectionProvider"),
            Map.entry(Environment.HBM2DDL_AUTO, "validate"),
            Map.entry(Environment.SHOW_SQL, true),
            Map.entry(Environment.FORMAT_SQL, false)
    );

    private static final Map<String, Object> HIKARI_CP_SETTINGS = Map.ofEntries(
            Map.entry("hibernate.hikari.connectionTimeout", "20000"),
            Map.entry("hibernate.hikari.minimumIdle", "10"),
            Map.entry("hibernate.hikari.maximumPoolSize", "20"),
            Map.entry("hibernate.hikari.idleTimeout", "300000")
    );
    private static final SessionFactory sessionFactory = loadSessionFactory();

    private static SessionFactory loadSessionFactory() {
        try {
            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(HIBERNATE_SETTINGS)
                    .applySettings(HIKARI_CP_SETTINGS)
                    .build();
            Metadata metadata = new MetadataSources(standardServiceRegistry)
                    .addAnnotatedClass(AddressEntity.class)
                    .addAnnotatedClass(CarServiceRequestEntity.class)
                    .addAnnotatedClass(CarToBuyEntity.class)
                    .addAnnotatedClass(CarToServiceEntity.class)
                    .addAnnotatedClass(CustomerEntity.class)
                    .addAnnotatedClass(InvoiceEntity.class)
                    .addAnnotatedClass(MechanicEntity.class)
                    .addAnnotatedClass(PartEntity.class)
                    .addAnnotatedClass(SalesmanEntity.class)
                    .addAnnotatedClass(ServiceEntity.class)
                    .addAnnotatedClass(ServiceMechanicEntity.class)
                    .addAnnotatedClass(ServicePartEntity.class)
                    .getMetadataBuilder()
                    .build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void closeSessionFactory() {
        try {
            sessionFactory.close();
        } catch (Throwable ex) {
            log.error("Exception while closing session factory", ex);
        }
    }

    public static Session getSession() {
        try {
            return sessionFactory.openSession();
        } catch (Throwable ex) {
            log.error("Exception while opening session", ex);
        }
        return null;
    }
}
