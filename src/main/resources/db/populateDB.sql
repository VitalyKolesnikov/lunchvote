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
       ('Admin', 'admin@gmail.com', '{noop}pass_admin');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('USER', 100003),
       ('ADMIN', 100003);

INSERT INTO RESTAURANTS (NAME)
VALUES ('KFC'),
       ('Burger King'),
       ('McDonald`s');

INSERT INTO MENUS (RESTAURANT_ID, DATE)
VALUES (100004, '2020-07-02'),
       (100005, '2020-07-02'),
       (100006, '2020-07-02');

INSERT INTO DISHES (NAME, PRICE)
VALUES ('Chicken wings', 300),
       ('French Fries', 150),
       ('Coca-cola', 90),
       ('Double Whopper', 300),
       ('Croissant', 120),
       ('Coffee', 70),
       ('Caesar Roll', 150),
       ('Chicken Nuggets', 150),
       ('Milkshake', 150);

INSERT INTO MENUS_DISHES (menu_id, dish_id)
VALUES (100007, 100010),
       (100007, 100011),
       (100007, 100012),
       (100008, 100013),
       (100008, 100014),
       (100008, 100015),
       (100009, 100016),
       (100009, 100017),
       (100009, 100018);

INSERT INTO VOTES (USER_ID, RESTAURANT_ID, DATE)
VALUES (100000, 100004, '2020-07-02 11:00:00'),
       (100001, 100005, '2020-07-02 11:25:00'),
       (100002, 100006, '2020-07-02 11:50:00');