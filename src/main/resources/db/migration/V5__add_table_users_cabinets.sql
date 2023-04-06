create table if not exists users_cabinets
(
    cabinet_id  integer not null,
    employee_id integer not null,
    is_active   boolean,
    primary key (cabinet_id, employee_id),
    constraint fk2j8mg5f3cxms4fjdmdq6ba9ma
        foreign key (cabinet_id) references cabinets,
    constraint fkr3n130uuqr0j68qbjmhnhm1g4
        foreign key (employee_id) references users
);