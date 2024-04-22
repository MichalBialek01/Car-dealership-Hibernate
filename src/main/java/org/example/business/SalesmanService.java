package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.SalesmanDAO;
import org.example.infrastructure.database.entity.CarToBuyEntity;
import org.example.infrastructure.database.entity.SalesmanEntity;

import java.util.Optional;

@AllArgsConstructor
public class SalesmanService {
    private final SalesmanDAO salesmanDAO;

    public SalesmanEntity findSalesman(String pesel) {
        Optional<SalesmanEntity> salesman = salesmanDAO.findByPesel(pesel);
        if (salesman.isEmpty()) {
            throw new RuntimeException("Provided salesman with pesel: [%s] doesn't exist".formatted(pesel));
        }
        return salesman.get();
    }

}
