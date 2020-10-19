DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description , calories)
VALUES (100001,'2020-06-09 06:00:00', 'Админ завтрак', 300),
       (100001,'2020-06-09 20:00:00', 'Админ ужин', 1000),
       (100000,'2020-09-30 08:00:00', 'Завтрак', 1000),
       (100000,'2020-09-30 13:00:00', 'Обед', 500),
       (100000,'2020-09-30 20:00:00', 'Ужин', 2500);