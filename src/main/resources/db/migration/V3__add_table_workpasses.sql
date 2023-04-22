create table if not exists workpasses
(
    id           serial,
    access_level varchar(255),
    expire_date  timestamp,
    is_deleted   boolean,
    is_free      boolean,
    pass_status  varchar(255),
    uuid         varchar(255),
    prev_pass_id integer,
    delete_date  timestamp,
    primary key (id),
    constraint fk7rydv10wde3cksst2p8f1w4bc
        foreign key (prev_pass_id) references workpasses
);

ALTER TABLE users
ADD CONSTRAINT fkoi0gbc1clny8by7j6udah3kbf
FOREIGN KEY (pass_id)
REFERENCES workpasses (id);