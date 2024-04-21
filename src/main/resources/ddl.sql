DROP TABLE IF EXISTS service_mechanic CASCADE;
DROP TABLE IF EXISTS service_part CASCADE;
DROP TABLE IF EXISTS car_service_request CASCADE;
DROP TABLE IF EXISTS invoice CASCADE;
DROP TABLE IF EXISTS mechanic CASCADE;
DROP TABLE IF EXISTS part CASCADE;
DROP TABLE IF EXISTS service CASCADE;
DROP TABLE IF EXISTS car_to_service CASCADE;
DROP TABLE IF EXISTS car_to_buy CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS salesman CASCADE;

CREATE TABLE salesman
(
    salesman_id SERIAL      NOT NULL,
    name        VARCHAR(32) NOT NULL,
    surname     VARCHAR(32) NOT NULL,
    pesel       VARCHAR(32) NOT NULL,
    PRIMARY KEY (salesman_id)
);

CREATE TABLE address
(
    address_id  SERIAL      NOT NULL,
    country     VARCHAR(32) NOT NULL,
    city        VARCHAR(32) NOT NULL,
    postal_code VARCHAR(32) NOT NULL,
    address     VARCHAR(32) NOT NULL,
    PRIMARY KEY (address_id)
);

CREATE TABLE customer
(
    customer_id SERIAL      NOT NULL,
    name        VARCHAR(32) NOT NULL,
    surname     VARCHAR(32) NOT NULL,
    phone       VARCHAR(32) NOT NULL,
    email       VARCHAR(32) NOT NULL,
    address_id  INT         NOT NULL,
    PRIMARY KEY (customer_id),
    UNIQUE (email),
    CONSTRAINT fk_customer_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);

CREATE TABLE car_to_buy
(
    car_to_buy_id   SERIAL          NOT NULL,
    vin             VARCHAR(20)     NOT NULL,
    brand           VARCHAR(32)     NOT NULL,
    model           VARCHAR(32)     NOT NULL,
    year            SMALLINT        NOT NULL,
    color           VARCHAR(32),
    price           NUMERIC(19, 2)  NOT NULL,
    PRIMARY KEY (car_to_buy_id),
    UNIQUE (vin)
);

CREATE TABLE car_to_service
(
    car_to_service_id SERIAL    NOT NULL,
    vin    VARCHAR(20)          NOT NULL,
    brand  VARCHAR(32)          NOT NULL,
    model  VARCHAR(32)          NOT NULL,
    year   SMALLINT             NOT NULL,
    PRIMARY KEY (car_to_service_id),
    UNIQUE (vin)
);

CREATE TABLE service
(
    service_id   SERIAL         NOT NULL,
    service_code VARCHAR(32)    NOT NULL,
    description  VARCHAR(64)    NOT NULL,
    price        NUMERIC(19, 2) NOT NULL,
    PRIMARY KEY (service_id)
);

CREATE TABLE part
(
    part_id       SERIAL            NOT NULL,
    serial_number VARCHAR(32)       NOT NULL,
    description   VARCHAR(64)       NOT NULL,
    price         NUMERIC(19, 2)    NOT NULL,
    PRIMARY KEY (part_id),
    UNIQUE (serial_number)
);

CREATE TABLE mechanic
(
    mechanic_id SERIAL      NOT NULL,
    name        VARCHAR(32) NOT NULL,
    surname     VARCHAR(32) NOT NULL,
    pesel       VARCHAR(32) NOT NULL,
    PRIMARY KEY (mechanic_id)
);

CREATE TABLE invoice
(
    invoice_id     SERIAL                   NOT NULL,
    invoice_number VARCHAR(64)              NOT NULL,
    date_time      TIMESTAMP WITH TIME ZONE NOT NULL,
    car_to_buy_id  INT                      NOT NULL,
    customer_id    INT                      NOT NULL,
    salesman_id    INT                      NOT NULL,
    PRIMARY KEY (invoice_id),
    UNIQUE (invoice_number),
    CONSTRAINT fk_invoice_car
        FOREIGN KEY (car_to_buy_id)
            REFERENCES car_to_buy (car_to_buy_id),
    CONSTRAINT fk_invoice_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_invoice_salesman
        FOREIGN KEY (salesman_id)
            REFERENCES salesman (salesman_id)
);

CREATE TABLE car_service_request
(
    car_service_request_id      SERIAL                      NOT NULL,
    car_service_request_number  VARCHAR(32)                 NOT NULL,
    received_date_time          TIMESTAMP WITH TIME ZONE    NOT NULL,
    completed_date_time         TIMESTAMP WITH TIME ZONE,
    customer_comment            TEXT,
    customer_id                 INT                         NOT NULL,
    car_to_service_id           INT                         NOT NULL,
    PRIMARY KEY (car_service_request_id),
    UNIQUE (car_service_request_number),
    CONSTRAINT fk_car_service_request_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_car_service_request_car
        FOREIGN KEY (car_to_service_id)
            REFERENCES car_to_service (car_to_service_id)
);

CREATE TABLE service_part
(
    service_part_id         SERIAL   NOT NULL,
    quantity                INT      NOT NULL,
    car_service_request_id  INT      NOT NULL,
    part_id                 INT      NOT NULL,
    PRIMARY KEY (service_part_id),
    CONSTRAINT fk_service_part_service_request
        FOREIGN KEY (car_service_request_id)
            REFERENCES car_service_request (car_service_request_id),
    CONSTRAINT fk_service_part_part
        FOREIGN KEY (part_id)
            REFERENCES part (part_id)
);

CREATE TABLE service_mechanic
(
    service_mechanic_id SERIAL          NOT NULL,
    hours                   INT         NOT NULL,
    comment                 VARCHAR(32) NOT NULL,
    car_service_request_id  INT         NOT NULL,
    mechanic_id             INT         NOT NULL,
    service_id              INT         NOT NULL,
    PRIMARY KEY (service_mechanic_id),
    CONSTRAINT fk_service_mechanic_car_service_request
        FOREIGN KEY (car_service_request_id)
            REFERENCES car_service_request (car_service_request_id),
    CONSTRAINT fk_service_mechanic_mechanic
        FOREIGN KEY (mechanic_id)
            REFERENCES mechanic (mechanic_id),
    CONSTRAINT fk_service_mechanic_service
        FOREIGN KEY (service_id)
            REFERENCES service (service_id)
);

