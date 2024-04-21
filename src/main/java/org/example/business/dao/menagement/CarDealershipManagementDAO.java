package org.example.business.dao.menagement;

import java.util.List;

public interface CarDealershipManagementDAO {
    void purge();


    void saveAll(List<?> entities);
}
