Lunchvote Graduation Project 
===============================
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/fee1d70912884e5da9f147a5424449d0)](https://www.codacy.com/manual/VitalyKolesnikov/lunchvote?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=VitalyKolesnikov/lunchvote&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/VitalyKolesnikov/lunchvote.svg?branch=master)](https://travis-ci.org/VitalyKolesnikov/lunchvote)

## Task

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.

## REST API

> All requests require basic authorization.

### Restaurants (access for admins only)

#### Get all

    curl -s http://localhost:8080/lunchvote/rest/admin/restaurants -u admin@gmail.com:pass_admin
    
#### Get by ID

    curl -s http://localhost:8080/lunchvote/rest/admin/restaurants/100007 -u admin@gmail.com:pass_admin

#### Get by name

    curl -s http://localhost:8080/lunchvote/rest/admin/restaurants/by?name=KFC -u admin@gmail.com:pass_admin
    
#### Create

    curl -s -X POST -d '{"name":"BlackStar Burgers"}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/restaurants -u admin@gmail.com:pass_admin
    
#### Update

    curl -s -X PUT -d '{"name":"Kentucky Fried Chicken"}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/restaurants/100007 -u admin@gmail.com:pass_admin
    
#### Delete

    curl -s -X DELETE http://localhost:8080/lunchvote/rest/admin/restaurants/100007 -u admin@gmail.com:pass_admin
    
### Dishes (access for admins only)

#### Get by ID

    curl -s http://localhost:8080/lunchvote/rest/admin/dishes/100016 -u admin@gmail.com:pass_admin

#### Create

    curl -s -X POST -d '{"name": "Shrimp roll", "price": 300, "menuId": 100010}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/dishes -u admin@gmail.com:pass_admin
    
#### Update

    curl -s -X PUT -d '{"name": "Coca-cola light", "price": 95, "menuId": 100010}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/dishes/100015 -u admin@gmail.com:pass_admin
    
#### Delete

    curl -s -X DELETE http://localhost:8080/lunchvote/rest/admin/dishes/100018 -u admin@gmail.com:pass_admin
    
### Menus (access for admins only)

#### Get by ID

    curl -s http://localhost:8080/lunchvote/rest/admin/menus/100010 -u admin@gmail.com:pass_admin
    
#### Get by date

    curl -s http://localhost:8080/lunchvote/rest/admin/menus/filter?date=2020-08-28 -u admin@gmail.com:pass_admin

#### Create

    curl -s -X POST -d '{"restaurantId":100007, "date":"2020-08-29"}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/menus -u admin@gmail.com:pass_admin
    
#### Update

    curl -s -X PUT -d '{"restaurantId":100007, "date":"2020-08-30"}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/menus/100010 -u admin@gmail.com:pass_admin
    
#### Delete

    curl -s -X DELETE http://localhost:8080/lunchvote/rest/admin/menus/100011 -u admin@gmail.com:pass_admin

### Menus (access for all authorized users)

#### Get todays menu

    curl -s http://localhost:8080/lunchvote/rest/menus -u user1@gmail.com:pass1
    
### Users (access for admins only)

#### Get all

    curl -s http://localhost:8080/lunchvote/rest/admin/users -u admin@gmail.com:pass_admin
    
#### Get by ID

    curl -s http://localhost:8080/lunchvote/rest/admin/users/100000 -u admin@gmail.com:pass_admin

#### Get by email

    curl -s http://localhost:8080/lunchvote/rest/admin/users/by?email=user2@gmail.com -u admin@gmail.com:pass_admin
    
#### Create

    curl -s -X POST -d '{"name": "User7", "email": "user7@gmail.com", "password": "{noop}pass7", "roles": ["USER"]}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/users -u admin@gmail.com:pass_admin
    
#### Update

    curl -s -X PUT -d '{"name": "User33", "email": "user33@gmail.com", "password": "pass33", "roles": ["USER"]}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/users/100002 -u admin@gmail.com:pass_admin
    
#### Delete

    curl -s -X DELETE http://localhost:8080/lunchvote/rest/admin/users/100001 -u admin@gmail.com:pass_admin
    
### Votes (access for admins only)

#### Get vote result by date

    curl -s http://localhost:8080/lunchvote/rest/admin/votes/result?date=2020-08-28 -u admin@gmail.com:pass_admin

#### Get by date

    curl -s http://localhost:8080/lunchvote/rest/admin/votes/filter?date=2020-08-28 -u admin@gmail.com:pass_admin
    
### Votes (access for all authorized users)

#### Get vote history

    curl -s http://localhost:8080/lunchvote/rest/votes -u user1@gmail.com:pass1

#### Vote for restaurant

    curl -s -X POST http://localhost:8080/lunchvote/rest/votes?restaurantId=100009 -u user1@gmail.com:pass1