create table if not exists users
(
    id         serial,
    country    varchar(255),
    email      varchar(255),
    gender     varchar(255),
    is_visible boolean,
    name       varchar(255),
    pass_id    integer,
    primary key (id)
);

create table if not exists addresses
(
    id                 bigserial,
    address_has_active boolean,
    city               varchar(255),
    country            varchar(255),
    street             varchar(255),
    employee_id        integer,
    primary key (id),
    constraint fkq52xs50hfmptoxv42dbhk6jf5
        foreign key (employee_id) references users
);