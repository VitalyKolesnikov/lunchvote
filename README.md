Lunchvote Graduation Project 
===============================

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/dc4fb02c1db649998ca9ca5f7d7f6b91)](https://app.codacy.com/manual/VitalyKolesnikov/topjava?utm_source=github.com&utm_medium=referral&utm_content=VitalyKolesnikov/topjava&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.org/VitalyKolesnikov/topjava.svg?branch=master)](https://travis-ci.org/VitalyKolesnikov/topjava)

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