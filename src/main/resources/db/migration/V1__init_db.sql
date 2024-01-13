create TABLE users (
    user_guid     VARCHAR (36)  NOT NULL PRIMARY KEY,
    user_name     VARCHAR (50)  NOT NULL UNIQUE,
    user_password VARCHAR (60)  DEFAULT ('d41d8cd98f00b204e9800998ecf8427e'),
    active        boolean       DEFAULT 1,
    user_role     VARCHAR (20)
);

create TABLE organization (
    org_guid     VARCHAR (36) NOT NULL PRIMARY KEY,
    org_name     VARCHAR (150),
    code         VARCHAR (9),
    rro_name     VARCHAR (50),
    rro_token    VARCHAR (36),
    rro_active   boolean DEFAULT 1
);

create TABLE products (
    product_guid            VARCHAR (36)  NOT NULL PRIMARY KEY,
    product_name            VARCHAR (150) NOT NULL,
    product_code            VARCHAR (11),
    price                   DOUBLE NOT NULL,
    org_guid                VARCHAR (36),
    sku                     VARCHAR (9),
    no_discount             BOOLEAN         DEFAULT (1),
    uktved                  VARCHAR (20)    DEFAULT (''),
    tax_group               VARCHAR         DEFAULT (1),
    tax_rate                VARCHAR         DEFAULT (0),
    weight                  BOOLEAN         DEFAULT (0),
    need_excise             BOOLEAN         DEFAULT (0),
    unit_name               VARCHAR(10)     DEFAULT ('шт'),
    prostopay_product       BOOLEAN         DEFAULT (0),
    CONSTRAINT products_fk_organization FOREIGN KEY (org_guid) REFERENCES organization(org_guid)
);

CREATE INDEX idx_products_sku ON products (
    sku
);

create TABLE eans (
    ean_code        VARCHAR (40) NOT NULL,
    product_guid    VARCHAR (36) NOT NULL,
    CONSTRAINT eans_fk_products FOREIGN KEY (product_guid) REFERENCES products(product_guid),
    PRIMARY KEY (
        ean_code, product_guid
    )   
);

create TABLE cards (
    card_guid       VARCHAR (36)  NOT NULL PRIMARY KEY,
    code            VARCHAR (50)  NOT NULL,
    active          BOOLEAN,
    card_type       VARCHAR (20) DEFAULT('DISCOUNT'),
    client_name     VARCHAR (150),
    client_phone    VARCHAR (50),
    client_email    VARCHAR (50),
    discount        DOUBLE        DEFAULT (0),
    debt            DOUBLE        DEFAULT (0),
    debt_allowed    BOOLEAN       DEFAULT (0),
    max_debt        DOUBLE        DEFAULT (0),
    max_debt_day    INTEGER       DEFAULT (0)
);

create TABLE orders (
    order_guid                  VARCHAR (36) PRIMARY KEY,
    order_number                BIGINT       NOT NULL,
    order_date                  TIMESTAMP    DEFAULT (CURRENT_TIMESTAMP),
    card_guid                   VARCHAR (36),
    summa_doc                   DOUBLE,
    summa_doc_without_discount  DOUBLE,
    summa_pay                   DOUBLE,
    summa_to_pay                DOUBLE,
    summa_discount              DOUBLE,
    summa_round                 DOUBLE,
    doc_type                    VARCHAR (20)  DEFAULT ('ORDER'),
    pay_type                    VARCHAR (20) DEFAULT ('CASH'),
    upload                      BOOLEAN      DEFAULT (0),
    fiscal                      BOOLEAN      DEFAULT (0),
    internet                    BOOLEAN      DEFAULT (0),
    fiscal_print                BOOLEAN      DEFAULT (0),
    prn                         VARCHAR (50),
    auth_code                   VARCHAR (50),
    CONSTRAINT orders_fk_cards FOREIGN KEY (card_guid) REFERENCES cards(card_guid)
);


create TABLE orders_detail (
    orders_detail_guid      VARCHAR (36) PRIMARY KEY,
    order_guid              VARCHAR (36) NOT NULL,
    line_number             INT,
    product_guid            VARCHAR (36) NOT NULL,
    qty                     DOUBLE,
    price                   DOUBLE,
    summa_without_discount  DOUBLE       DEFAULT (0),
    summa_discount          DOUBLE       DEFAULT (0),
    summa                   DOUBLE       DEFAULT (0),
    excise                  VARCHAR (50),
    org_guid                VARCHAR (36),
    fiscal_print            BOOLEAN      DEFAULT (0),
    CONSTRAINT orders_detail_fk_orders FOREIGN KEY (order_guid) REFERENCES orders(order_guid),
    CONSTRAINT orders_detail_fk_productss FOREIGN KEY (product_guid) REFERENCES products(product_guid)
);

create TABLE quick_products (
    product_id VARCHAR (36),
    pos_id     INTEGER,
    color      VARCHAR      DEFAULT 'F0F0F0'
);
