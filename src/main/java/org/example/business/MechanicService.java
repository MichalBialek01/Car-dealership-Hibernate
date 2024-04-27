package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.menagement.MechanicDAO;
import org.example.infrastructure.database.entity.MechanicEntity;

import java.util.Optional;
@AllArgsConstructor
public class MechanicService {

    private final MechanicDAO mechanicDAO ;

    public MechanicEntity findMechanic(String pesel) {
        Optional<MechanicEntity> mechanic = mechanicDAO.findByPesel(pesel);
        if (mechanic.isEmpty()) {
            throw new RuntimeException("Provided mechanic with pesel: [%s] doesn't exist".formatted(pesel));
        }
        return mechanic.get();
    }
}
