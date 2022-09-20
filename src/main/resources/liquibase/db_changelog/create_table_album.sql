create table album
(
    id           serial primary key,
    title        varchar(255) not null,
    created      timestamp    not null,
    performer_id bigint references performer (id)
);
