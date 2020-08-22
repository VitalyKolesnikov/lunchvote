DROP VIEW IF EXISTS DISHES_VIEW;
DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS VOTES;
DROP TABLE IF EXISTS MENUS_DISHES;
DROP TABLE IF EXISTS DISHES;
DROP TABLE IF EXISTS MENUS;
DROP TABLE IF EXISTS RESTAURANTS;
DROP TABLE IF EXISTS USERS;

DROP SEQUENCE IF EXISTS global_seq;
CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE USERS
(
    id    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name  VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);
CREATE UNIQUE INDEX USERS_UNIQUE_EMAIL_IDX
    ON USERS (email);

CREATE TABLE USER_ROLES
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT USER_ROLES_IDX UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE RESTAURANTS
(
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name VARCHAR NOT NULL
);

CREATE TABLE MENUS
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaurant_id INTEGER NOT NULL,
    date          DATE    NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);

CREATE TABLE DISHES
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name    VARCHAR NOT NULL,
    price   VARCHAR NOT NULL
);

CREATE TABLE MENUS_DISHES
(
    menu_id INTEGER NOT NULL,
    dish_id INTEGER NOT NULL,
    CONSTRAINT MENUS_DISHES_IDX UNIQUE (menu_id, dish_id),
    FOREIGN KEY (menu_id) REFERENCES MENUS (id) ON DELETE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES DISHES (id) ON DELETE CASCADE
);

CREATE TABLE VOTES
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id       INTEGER   NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    date_time     TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);