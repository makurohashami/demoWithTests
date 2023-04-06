create table if not exists avatars
(
    id            serial,
    creation_date timestamp,
    img_name      varchar(255),
    is_expired    boolean,
    employee_id   integer,
    primary key (id),
    constraint fk2pw7fj4cxclpq7jt7689khd1o
        foreign key (employee_id) references users
);