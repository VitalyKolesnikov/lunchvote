DELETE
FROM user_roles;
DELETE
FROM vote;
DELETE
FROM dish;
DELETE
FROM users;
DELETE
FROM menu;
DELETE
FROM restaurant;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User1', 'user1@gmail.com', '{noop}pass1'),
       ('User2', 'user2@gmail.com', '{noop}pass2'),
       ('User3', 'user3@gmail.com', '{noop}pass3'),
       ('User4', 'user4@gmail.com', '{noop}pass4'),
       ('User5', 'user5@gmail.com', '{noop}pass5'),
       ('User6', 'user6@gmail.com', '{noop}pass6'),
       ('Admin', 'admin@gmail.com', '{noop}pass_admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('USER', 100003),
       ('USER', 100004),
       ('USER', 100005),
       ('USER', 100006),
       ('ADMIN', 100006);

INSERT INTO restaurant (name)
VALUES ('KFC'),
       ('Burger King'),
       ('McDonald`s');

INSERT INTO menu (restaurant_id, menu_date)
VALUES (100007, now()),
       (100008, now()),
       (100009, now());


INSERT INTO dish (name, price, menu_id)
VALUES ('Chicken wings', 300, 100010),
       ('French Fries', 150, 100010),
       ('Coca-cola', 90, 100010),
       ('Double Whopper', 300, 100011),
       ('Croissant', 120, 100011),
       ('Coffee', 70, 100011),
       ('Caesar Roll', 150, 100012),
       ('Chicken Nuggets', 150, 100012),
       ('Milkshake', 150, 100012);

INSERT INTO vote (user_id, restaurant_id, vote_date)
VALUES (100000, 100009, '2020-08-27'),
       (100000, 100008, '2020-08-28'),
       (100000, 100007, '2020-08-29'),
       (100000, 100008, '2020-08-30'),
       (100000, 100007, '2020-08-31'),
       (100001, 100007, '2020-08-31'),
       (100002, 100007, '2020-08-31'),
       (100003, 100008, '2020-08-31'),
       (100004, 100008, '2020-08-31'),
       (100005, 100009, '2020-08-31'),
       (100000, 100007, now()),
       (100002, 100008, now()),
       (100003, 100009, now()),
       (100004, 100009, now()),
       (100005, 100009, now());