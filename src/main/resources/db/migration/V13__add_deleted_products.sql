create TABLE deleted_products (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    delete_date_time        TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),
    user_name               VARCHAR (50),
    product_name            VARCHAR (150),
    qty                     DOUBLE,
    summa                   DOUBLE       DEFAULT (0)
);