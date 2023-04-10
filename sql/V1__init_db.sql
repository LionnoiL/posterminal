CREATE TABLE client (
	client_guid VARCHAR (36) NOT NULL,
	client_name CHARACTER VARYING NOT NULL,
        PRIMARY KEY (
                client_guid
        )
);


CREATE TABLE users (
    user_guid       VARCHAR (36) NOT NULL,
    user_name     VARCHAR (50) DEFAULT NULL,
    user_password VARCHAR (60),
    active        boolean,
    interface_id  integer,
    PRIMARY KEY (
        user_guid
    )
);
