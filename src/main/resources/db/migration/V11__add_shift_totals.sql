create TABLE shift_totals (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    doc_date                 VARCHAR(50),
    cash_start               DOUBLE DEFAULT (0),
    cash_end                 DOUBLE DEFAULT (0),
    summa_sale               DOUBLE DEFAULT (0),
    summa_return             DOUBLE DEFAULT (0),
    sale_cash                DOUBLE DEFAULT (0),
    sale_card                DOUBLE DEFAULT (0),
    money_in                 DOUBLE DEFAULT (0),
    money_out                DOUBLE DEFAULT (0)

);