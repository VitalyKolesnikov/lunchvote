Lunchvote Graduation Project 
===============================
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/fee1d70912884e5da9f147a5424449d0)](https://www.codacy.com/manual/VitalyKolesnikov/lunchvote?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=VitalyKolesnikov/lunchvote&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/VitalyKolesnikov/lunchvote.svg?branch=master)](https://travis-ci.org/VitalyKolesnikov/lunchvote)

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

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

# REST API

## Restaurants (access for admins only)

###Get all

GET /rest/admin/restaurants

    curl -s http://localhost:8080/lunchvote/rest/admin/restaurants -u admin@gmail.com:pass_admin
    
###Get by ID

GET /rest/admin/restaurants/{id}

    curl -s http://localhost:8080/lunchvote/rest/admin/restaurants/100004 -u admin@gmail.com:pass_admin

###Get by name

GET /rest/admin/restaurants/by?name={name}

    curl -s http://localhost:8080/lunchvote/rest/admin/restaurants/by?name=KFC -u admin@gmail.com:pass_admin
    
###Create new

POST /rest/admin/restaurants

Body: {"name": "{name}"}

    curl -s -X POST -d '{"name":"BlackStar Burgers"}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/restaurants -u admin@gmail.com:pass_admin
    
###Update

PUT /rest/admin/restaurants/{id}

Body: {"name": "{name}"}

    curl -s -X PUT -d '{"name":"Kentucky Fried Chicken"}' -H 'Content-Type:application/json' http://localhost:8080/lunchvote/rest/admin/restaurants/100004 -u admin@gmail.com:pass_admin
    
###Delete

DELETE /rest/admin/restaurants/{id}

    curl -s -X DELETE http://localhost:8080/lunchvote/rest/admin/restaurants/100004 -u admin@gmail.com:pass_admin