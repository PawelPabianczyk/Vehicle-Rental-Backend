INSERT INTO categories(id, name)
VALUES (1, 'SEDAN'),
       (2, 'COUPE'),
       (3, 'SPORTS'),
       (4, 'HATCHBACK'),
       (5, 'SUV');
INSERT INTO roles(id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_MANAGER'),
       (3, 'ROLE_REGULAR'),
       (4, 'ROLE_USER');
INSERT INTO users(id, username, email, password, is_active)
VALUES (1, 'admin', 'admina@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (2, 'manager', 'manager@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (3, 'regular', 'regular@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true);
INSERT INTO user_roles(user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 3);
INSERT INTO personal_information(id, first_name, last_name, birthdate, address, city, country, phone, user_id,
                                 is_active)
VALUES (1, 'Jan', 'Kowalski', '1990-01-01', 'Urzednicza 3', 'Krakow', 'Polska', '123456789', 1, true),
       (2, 'Adam', 'Nowak', '1991-01-01', 'Urzednicza 4', 'Krakow', 'Polska', '223456789', 2, true),
       (3, 'Piotr', 'Piotrowski', '1992-01-01', 'Urzednicza 5', 'Krakow', 'Polska', '323456789', 3, true);
insert into jobs (ID, IS_ACTIVE, SALARY, TITLE)
values (1, true, 8000, 'ADMIN'),
       (2, true, 10000, 'MANAGER'),
       (3, true, 3000, 'REGULAR');
insert into employees (ID, BONUS, IS_ACTIVE, BOSS_ID, JOB_ID, USER_ID)
values (1, 1000, true, 1, 1, 1),
       (2, 2000, true, 1, 2, 2),
       (3, 800, true, 1, 3, 3);