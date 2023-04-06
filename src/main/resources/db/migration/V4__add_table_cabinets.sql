create table if not exists cabinets
(
    id         serial,
    capacity   integer,
    is_deleted boolean,
    name       varchar(255),
    primary key (id)
);