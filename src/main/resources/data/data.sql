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
       (3, 'regular', 'regular@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (4, 'user4', 'user4@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (5, 'user5', 'user5@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (6, 'user6', 'user6@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (7, 'user7', 'user7@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (8, 'user8', 'user8@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (9, 'user9', 'user9@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (10, 'user10', 'user10@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (11, 'user11', 'user11@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true),
       (12, 'user12', 'user12@mail.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', true);
INSERT INTO user_roles(user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 3),
       (4, 4),
       (5, 4),
       (6, 4),
       (7, 4),
       (8, 4),
       (9, 4),
       (10, 4),
       (11, 4),
       (12, 4);
INSERT INTO personal_information(id, first_name, last_name, birthdate, address, city, country, phone, user_id,
                                 is_active)
VALUES (1, 'Jan', 'Kowalski', '1990-01-01', 'Urzednicza 3', 'Krakow', 'Polska', '123456789', 1, true),
       (2, 'Adam', 'Nowak', '1991-01-01', 'Urzednicza 4', 'Krakow', 'Polska', '223456789', 2, true),
       (3, 'Piotr', 'Piotrowski', '1992-01-01', 'Urzednicza 5', 'Krakow', 'Polska', '323456789', 3, true),
       (4, 'imie4', 'nazwisko4', '1992-01-01', 'ulica 4', 'miasto', 'kraj', '343456789', 4, true),
       (5, 'imie5', 'nazwisko5', '1992-01-01', 'ulica 5', 'miasto', 'kraj', '353456789', 5, true),
       (6, 'imie6', 'nazwisko6', '1992-01-01', 'ulica 6', 'miasto', 'kraj', '363456789', 6, true),
       (7, 'imie7', 'nazwisko7', '1992-07-01', 'ulica 7', 'miasto', 'kraj', '373456789', 7, true),
       (8, 'imie8', 'nazwisko8', '1992-08-01', 'ulica 8', 'miasto', 'kraj', '383456789', 8, true),
       (9, 'imie9', 'nazwisko9', '1992-09-01', 'ulica 9', 'miasto', 'kraj', '393456789', 9, true),
       (10, 'imie10', 'nazwisko10', '1992-10-01', 'ulica 10', 'miasto', 'kraj', '103456789', 10, false),
       (11, 'imie11', 'nazwisko11', '1992-11-01', 'ulica 11', 'miasto', 'kraj', '113456789', 11, false),
       (12, 'imie12', 'nazwisko12', '1992-12-01', 'ulica 12', 'miasto', 'kraj', '121456789', 12, false);
INSERT INTO customers(id, driving_license_number, is_verified, user_id, is_active)
VALUES (4, '44444', true, 4, true),
       (5, '55555', true, 5, true),
       (6, '66666', true, 6, true),
       (7, '77777', false, 7, true),
       (8, '88888', false, 8, true),
       (9, '99999', false, 9, true),
       (10, '10101', false, 10, false),
       (11, '11111', false, 11, false),
       (12, '12121', false, 12, false);