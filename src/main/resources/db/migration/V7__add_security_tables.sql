create table if not exists users_auth (
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled boolean not null
);


create table if not exists authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username)
    references users_auth(username)
);

create unique index if not exists ix_auth_username on authorities (username,authority);

insert into users_auth(username, password, enabled) values ('user', '$2a$10$zOMpk1q3ekVkSkfKgn0oiuOOjXJlefuQEtJZcIohje5essGXynBfi', true);
insert into users_auth(username, password, enabled) values ('admin', '$2a$10$zOMpk1q3ekVkSkfKgn0oiuOOjXJlefuQEtJZcIohje5essGXynBfi', true);

insert into authorities(username, authority) values ('user', 'USER');
insert into authorities(username, authority) values ('admin', 'USER');
insert into authorities(username, authority) values ('admin', 'ADMIN');