DROP TABLE USER_ROLES IF EXISTS;
DROP TABLE VOTES IF EXISTS;
DROP TABLE DISHES IF EXISTS;
DROP TABLE USERS IF EXISTS;
DROP TABLE MENUS IF EXISTS;
DROP TABLE RESTAURANTS IF EXISTS;

DROP SEQUENCE GLOBAL_SEQ IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE USERS
(
    id       INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX USERS_UNIQUE_EMAIL_IDX
    ON USERS (email);

CREATE TABLE USER_ROLES
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT USER_ROLES_IDX UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE RESTAURANTS
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX RESTAURANTS_UNIQUE_NAME_IDX
    ON RESTAURANTS (name);

CREATE TABLE MENUS
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER NOT NULL,
    date          DATE    NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);

CREATE TABLE DISHES
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    menu_id INTEGER      NOT NULL,
    name    VARCHAR(255) NOT NULL,
    price   VARCHAR(255) NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES MENUS (id) ON DELETE CASCADE
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
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    user_id       INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    date          DATE    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);