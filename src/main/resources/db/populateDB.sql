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

INSERT INTO USERS (NAME, EMAIL)
VALUES ('User1', 'user1@gmail.com'),
       ('User2', 'user2@gmail.com'),
       ('User3', 'user3@gmail.com'),
       ('Admin', 'admin@gmail.com');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('ADMIN', 100003);

INSERT INTO RESTAURANTS (NAME)
VALUES ('KFC'),
       ('Burger King'),
       ('McDonald`s');

INSERT INTO MENUS (RESTAURANT_ID, DATE)
VALUES (100004, '2020-07-02'),
       (100005, '2020-07-02'),
       (100006, '2020-07-02');

INSERT INTO DISHES (MENU_ID, NAME, PRICE)
VALUES (100007, 'Chicken wings', 300),
       (100007, 'French Fries', 150),
       (100007, 'Coca-cola', 90),
       (100008, 'Double Whopper', 300),
       (100008, 'Croissant', 120),
       (100008, 'Coffee', 70),
       (100009, 'Caesar Roll', 150),
       (100009, 'Chicken Nuggets', 150),
       (100009, 'Milkshake', 150);

INSERT INTO VOTES (USER_ID, RESTAURANT_ID, DATE_TIME)
VALUES (100000, 100004, '2020-07-02 11:00:00'),
       (100001, 100005, '2020-07-02 11:25:00'),
       (100002, 100006, '2020-07-02 11:50:00');