DELETE
FROM USER_ROLES;
DELETE
FROM VOTES;
DELETE
FROM DISHES;
DELETE
FROM USERS;
DELETE
FROM MENUS;
DELETE
FROM RESTAURANTS;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User1', 'user1@gmail.com', '{noop}pass1'),
       ('User2', 'user2@gmail.com', '{noop}pass2'),
       ('User3', 'user3@gmail.com', '{noop}pass3'),
       ('User4', 'user4@gmail.com', '{noop}pass4'),
       ('User5', 'user5@gmail.com', '{noop}pass5'),
       ('User6', 'user6@gmail.com', '{noop}pass6'),
       ('Admin', 'admin@gmail.com', '{noop}pass_admin');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('USER', 100003),
       ('USER', 100004),
       ('USER', 100005),
       ('USER', 100006),
       ('ADMIN', 100006);

INSERT INTO RESTAURANTS (NAME)
VALUES ('KFC'),
       ('Burger King'),
       ('McDonald`s');

INSERT INTO MENUS (RESTAURANT_ID, DATE)
VALUES (100007, '2020-08-31'),
       (100008, '2020-08-31'),
       (100009, '2020-08-31');


INSERT INTO DISHES (NAME, PRICE, MENU_ID)
VALUES ('Chicken wings', 300, 100010),
       ('French Fries', 150, 100010),
       ('Coca-cola', 90, 100010),
       ('Double Whopper', 300, 100011),
       ('Croissant', 120, 100011),
       ('Coffee', 70, 100011),
       ('Caesar Roll', 150, 100012),
       ('Chicken Nuggets', 150, 100012),
       ('Milkshake', 150, 100012);

INSERT INTO VOTES (USER_ID, RESTAURANT_ID, DATE)
VALUES (100000, 100009, '2020-08-27'),
       (100000, 100007, '2020-08-29'),
       (100000, 100007, '2020-08-31'),
       (100001, 100008, '2020-08-31'),
       (100002, 100008, '2020-08-31'),
       (100003, 100009, '2020-08-31'),
       (100004, 100009, '2020-08-31'),
       (100005, 100009, '2020-08-31');