create TABLE shift_results (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    doc_date                 TIMESTAMP     DEFAULT (CURRENT_TIMESTAMP),
    user_guid                VARCHAR (36),
    summa_order_cash         DOUBLE DEFAULT (0),
    summa_return_cash        DOUBLE DEFAULT (0),
    summa_order_card         DOUBLE DEFAULT (0),
    summa_money_move_in      DOUBLE DEFAULT (0),
    summa_money_move_out     DOUBLE DEFAULT (0),
    summa_safe               DOUBLE DEFAULT (0),
    summa_sale               DOUBLE DEFAULT (0),
    summa_return             DOUBLE DEFAULT (0)
);