create TABLE deleted_products (
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    delete_date_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_name               VARCHAR (50),
    product_name            VARCHAR (150),
    qty                     DOUBLE DEFAULT 0.0,
    summa                   DOUBLE       DEFAULT 0.0
);