create TABLE app_options (
    options_name       VARCHAR (50) PRIMARY KEY,
    options_value      VARCHAR (250)
);

insert into app_options (options_name, options_value)
values
('number_message_received', '0'),
('number_message_taken', '0');