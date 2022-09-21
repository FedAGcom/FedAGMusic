insert into users (email, password, first_name, last_name, role, created)
values ('user@mail.ru', '$2a$12$XSRvPpzZKWDYgYyI3M5drOMtLonDwZB5iGaSq4fwkixhaSGo8DJry', 'User', 'Userov',
        'ROLE_USER', '2022-09-12 13:05:08.273266'),
       ('admin@mail.ru', '$2a$12$IZkZmyzdIlW2g0yJS7tjiu8R5CfdrKAeeyf6hgjVjLQK9EJjxqIqq', 'Admin', 'Adminov',
        'ROLE_ADMIN', '2022-09-12 13:05:31.679726');

INSERT INTO performer (name, description)
VALUES ('perforqqmer23', 'description message'),
       ('Mihail Krug', 'description Mihail Krug'),
       ('Michael Jackson', 'description Michael Jackson'),
       ('performer1', 'description message');


INSERT INTO album (title, created, performer_id)
VALUES ('album west', '2022-09-12 17:33:22.000000', 1),
       ('album best', '2022-09-05 17:34:35.000000', 3);

INSERT INTO playlist (title, created, users_id)
VALUES ('pop', '2022-09-12 17:37:54.000000', 1),
       ('favorite', '2022-09-12 17:37:03.000000', 2);

insert into song (title, created, performer_id, album_id, playlist_id)
values ('song1', '2022-09-12 17:40:57.000000', 1, 2, 1),
       ('song2', '2022-09-12 17:41:20.000000', 3, 1, 2);




