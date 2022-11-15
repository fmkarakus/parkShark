create table division
(
    id            integer primary key,
    name          varchar(32) unique not null,
    original_name varchar(32),
    director      varchar(32),
    parent_id     integer references division
);

create table postal_code
(
    postal_code varchar(16) primary key,
    label       varchar(32) not null unique
);

create table contact_person
(
    id                  integer primary key,
    name                varchar(64) not null,
    email               varchar(64) not null,
    mobile_phone_number varchar(16),
    telephone_number    varchar(16),
    street_name         varchar(32),
    street_number       varchar(8),
    postal_code         varchar(16) references postal_code
);

create table parking_lot
(
    id                integer primary key,
    name              varchar(64)      not null,
    category          varchar(32)      not null,
    max_capacity      integer          not null,
    price_per_hour    double precision not null,
    contact_person_id integer references contact_person,
    street_name       varchar(32),
    street_number     varchar(8),
    postal_code       varchar(16) references postal_code
);

create table member
(
    id                    integer primary key,
    first_name            varchar(64) not null,
    last_name             varchar(64) not null,
    street_name           varchar(32),
    street_number         varchar(8),
    postal_code           varchar(16) references postal_code,
    telephone_number      varchar(16),
    email                 varchar(64) not null,
    license_plate_number  varchar(16) not null,
    license_plate_country varchar(32) not null,
    registration_date     date        not null,
    role                  varchar(16) not null
);