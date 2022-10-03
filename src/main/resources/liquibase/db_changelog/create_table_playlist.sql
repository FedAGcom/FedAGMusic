create table playlist
(
    id       serial primary key,
    title    varchar(255) not null,
    created  timestamp    not null,
    users_id bigint references users (id)
);
