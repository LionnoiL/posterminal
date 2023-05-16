create TABLE shift_results (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    doc_date                 TIMESTAMP     DEFAULT (CURRENT_TIMESTAMP),
    user_guid                VARCHAR (36),
    summa_order_cash         DOUBLE,
    summa_return_cash        DOUBLE,
    summa_cash               DOUBLE,
    summa_order_card         DOUBLE,
    summa_card               DOUBLE,
    summa_money_move_in      DOUBLE,
    ssumma_money_move_out    DOUBLE,
    summa_sale               DOUBLE,
    summa_return             DOUBLE
);