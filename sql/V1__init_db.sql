CREATE TABLE clients (
	client_guid VARCHAR (36) NOT NULL PRIMARY KEY,
	client_name CHARACTER VARYING NOT NULL
);

CREATE TABLE users (
    user_guid     VARCHAR (36) NOT NULL PRIMARY KEY,
    user_name     VARCHAR (50) NOT NULL UNIQUE,
    user_password VARCHAR (60) DEFAULT ('d41d8cd98f00b204e9800998ecf8427e'),
    active        boolean DEFAULT 1,
    user_role     VARCHAR (20),
    PRIMARY KEY (
        user_guid
    )
);

CREATE TABLE organization (
    org_guid VARCHAR (36) NOT NULL PRIMARY KEY,
    org_name VARCHAR (150),
    code     VARCHAR (9) 
);

CREATE TABLE products (
    product_guid   VARCHAR (36) NOT NULL PRIMARY KEY,
    product_name   VARCHAR (150) NOT NULL,
    product_code   VARCHAR (11),
    price          DOUBLE NOT NULL,
    org_guid       VARCHAR (36),
    sku            VARCHAR (9),
    no_discount    BOOLEAN       DEFAULT (1),
    uktved         VARCHAR (20)  DEFAULT (''),
    tax_group        VARCHAR       DEFAULT (1),
    tax_rate       VARCHAR       DEFAULT (0),
    weight         BOOLEAN       DEFAULT (0),
    CONSTRAINT products_fk_organization FOREIGN KEY (org_guid) REFERENCES organization(org_guid)
);

CREATE TABLE eans (
    ean_code   VARCHAR (40) NOT NULL,
    product_guid VARCHAR (36) NOT NULL,
    CONSTRAINT eans_fk_products FOREIGN KEY (product_guid) REFERENCES products(product_guid),
    PRIMARY KEY (
        ean_code, product_guid
    )   
);

CREATE TABLE cards (
    card_guid    VARCHAR (36)  NOT NULL PRIMARY KEY,
    code         VARCHAR (50)  NOT NULL,
    active       BOOLEAN,
    card_type    VARCHAR (20) DEFAULT('DISCOUNT'),
    client_guid  VARCHAR (36),
    client_name  VARCHAR (150),
    client_phone VARCHAR (50),
    client_email VARCHAR (50),
    discount     DOUBLE        DEFAULT (0),
    debt         DOUBLE        DEFAULT (0),
    debt_allowed BOOLEAN       DEFAULT (0),
    max_debt     DOUBLE        DEFAULT (0),
    max_debt_day INTEGER       DEFAULT (0),
    CONSTRAINT cards_fk_clients FOREIGN KEY (client_guid) REFERENCES clients(client_guid)
);

CREATE TABLE orders (
    order_guid   VARCHAR (36) PRIMARY KEY,
    order_date   TIMESTAMP    DEFAULT (CURRENT_TIMESTAMP),
    card_guid    VARCHAR (36),
    summa_doc    DOUBLE,
    summa_pay    DOUBLE,
    doc_type     VARCHAR(20)  DEFAULT ('ORDER'),
    pay_type     VARCHAR (20) DEFAULT ('CASH'),
    upload       BOOLEAN      DEFAULT (0),
    fiscal       BOOLEAN      DEFAULT (0),
    internet     BOOLEAN      DEFAULT (0),
    fiscal_print BOOLEAN      DEFAULT (0),
    CONSTRAINT orders_fk_cards FOREIGN KEY (card_guid) REFERENCES cards(card_guid)
);
