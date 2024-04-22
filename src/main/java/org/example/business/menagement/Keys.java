package org.example.business.menagement;

public interface Keys {
    enum InputDataGroup{
        INIT,
        BUY_FIRST_TIME,
        SERVICE_REQUEST,
        BUY_AGAIN
    }
    enum Entity{
         SALESMAN,
        CUSTOMER,
        MECHANIC,
        CAR,
        SERVICE,
        PART
    }
    enum Constant{
        WHAT
    }
}
