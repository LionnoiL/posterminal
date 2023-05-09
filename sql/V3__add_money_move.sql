create TABLE money_move (
    money_move_guid        VARCHAR (36) PRIMARY KEY,
    money_move_number      BIGINT       NOT NULL,
    money_move_date        TIMESTAMP    DEFAULT (CURRENT_TIMESTAMP),
    summa_doc              DOUBLE,
    move_type              VARCHAR(20)  DEFAULT ('MOVE_OUT'),
    upload                 BOOLEAN      DEFAULT (0),
    comment_doc            VARCHAR(150)
);