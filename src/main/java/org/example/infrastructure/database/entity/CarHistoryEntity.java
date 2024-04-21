package org.example.infrastructure.database.entity;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
public class CarHistoryEntity {

    String carVin;
    List<ServiceRequest> serviceRequests;

    public record ServiceRequest(
        String serviceRequestNumber,
        OffsetDateTime receivedDateTime,
        OffsetDateTime completedDateTime,
        String customerComment,
        List<Service> services,
        List<Part> parts
    ) {

        @Builder
        public ServiceRequest {
        }

        @Override
        public String toString() {
            return "ServiceRequest{" +
                "serviceRequestNumber='" + serviceRequestNumber + '\'' +
                ", receivedDateTime=" + receivedDateTime +
                ", completedDateTime=" + completedDateTime +
                ", customerComment='" + customerComment +
                '}';
        }
    }
    public record Service(
        String serviceCode,
        String description,
        BigDecimal price
    ) {

        @Builder
        public Service {
        }
    }

    public record Part(
        String serialNumber,
        String description,
        BigDecimal price
    ) {
        @Builder
        public Part {
        }
    }

}
