create table song
(
    id           serial primary key,
    title        varchar(255) not null,
    created      timestamp    not null,
    performer_id bigint references performer (id),
    album_id     bigint references album (id),
    playlist_id  bigint references playlist (id)
);
