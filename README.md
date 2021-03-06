revoter: REstaurant VOTE system

TOOLS & TECHNOLOGIES USED
=========================
Java 1.8, Eclipse, MySQL/HSQLDB, Hibernate/Hibernate Validation, Spring 4, Spring Security 4, Maven 3.3, Tomcat 7, cURL, Slf4j, Swagger


HOW TO USE/TEST
===============
1. Get all the sources from "https://github.com/rvoGH01/revoter"
2. Tune "src/main/resources/db.properties" in order to get valid DB connection
3. Execute "mvn clean install" to build the project
4. Deploy "revoter.war" in Tomcat. After deployment is done you should have a new database ("revoter") created. It should contain a set of default users in "users" table. If "users" table is empty, you can add them manually by executing the following SQL: "src/main/resources/initial_data.sql". 
5. Test using cURL commands mentioned below.


DATABASE DESCRIPTION
====================
Database contains the following tables: 
restaurant(restaurant_id, name) - contains restaurant info;
dish(dish_id, name, price, restaurant_id) - contains a dish info for a specific restaurant
vote(vote_id, restaurant_id, user_id) - contains votes for a specific restaurant made by a user
users(user_id, admin, first_name, last_name, username, password) - contains user information


API OVERVIEW
============

Swagger
-------
    http://localhost:8899/revoter/swagger/index.html
	
Resource Endpoint                                     | HTTP method | Description

vote related actions

    revoter/api/restourants/{restourantId}/votes          | GET         | Get all votes for restaurant
    revoter/api/restourants/{restourantId}/votes          | POST        | Create a new vote
    revoter/api/restourants/{restourantId}/votes/{voteId} | GET         | Get specific vote for restaurant

restaurant related actions

    revoter/api/restourants                               | POST        | Create/Add a new restaurant
    revoter/api/restourants                               | GET         | Get all available restaurants
    revoter/api/restourants/{restourantId}                | GET         | Get specific restaurant
    revoter/api/restourants/{restourantId}                | PUT         | Update restaurant (add dish for a restaurant)
    revoter/api/restourants/{restourantId}                | DELETE      | Delete restaurant

get result

    revoter/api/restaurants/computeresult                 | GET         | Get voting result


cURL TEST COMMANDS
==================

Get Restaurant(s)
-----------------
    curl -v http://localhost:8899/revoter/api/restaurants
    curl -u user2:222 http://localhost:8899/revoter/api/restaurants
    curl -vu admin:admin http://localhost:8899/revoter/api/restaurants

Add Restaurant (1 dish only)
----------------------------
    curl -u admin:admin -X POST http://localhost:8899/revoter/api/restaurants -H "Content-Type:application/json" -d "{\"name\": \"Atlas\",\"dishes\":[{\"name\":\"Potato\",\"price\":45.50}]}"

Add Restaurant (2 dishes)
----------------------------
    curl -v -X POST http://localhost:8899/revoter/api/restaurants -H "Content-Type:application/json" -d "{\"name\": \"Atlas\",\"dishes\":[{\"name\":\"Potato\",\"price\":45.50},{\"name\":\"Meat\",\"price\":24.25}]}"
    curl -u user3:333 -X POST http://localhost:8899/revoter/api/restaurants -H "Content-Type:application/json" -d "{\"name\": \"Atlas\",\"dishes\":[{\"name\":\"Potato\",\"price\":45.50},{\"name\":\"Meat\",\"price\":24.25}]}"
    curl -u admin:admin -X POST http://localhost:8899/revoter/api/restaurants -H "Content-Type:application/json" -d "{\"name\": \"Atlas\",\"dishes\":[{\"name\":\"Potato\",\"price\":45.50},{\"name\":\"Meat\",\"price\":24.25}]}"

Delete Restaurant
-----------------
    curl -i -X DELETE http://localhost:8899/revoter/api/restaurants/1
    curl -i -u user1:111 -X DELETE http://localhost:8899/revoter/api/restaurants/1
    curl -i -u admin:admin -X DELETE http://localhost:8899/revoter/api/restaurants/1

Vote Restaurant
---------------
    curl -u user3:333 -X POST http://localhost:8899/revoter/api/restaurants/1/votes -H "Content-Type:application/json" -d "{\"restaurant\": {\"id\": 1, \"name\": \"any\"}, \"user\": {\"id\":4, \"username\":\"user3\", \"password\":\"any\", \"firstName\":\"first3\", \"lastName\":\"last3\", \"admin\":\"false\"}}"
    curl -i -u admin:admin -X POST http://localhost:8899/revoter/api/restaurants/24/votes -H "Content-Type:application/json" -d "{\"restaurant\": {\"id\":24, \"name\": \"any\"}, \"user\": {\"id\":2, \"username\":\"admin\", \"password\":\"any\", \"firstName\":\"Admin\", \"lastName\":\"Admin\", \"admin\":\"true\"}}"

Get Votes for Restaurant
------------------------
    curl -v http://localhost:8899/revoter/api/restaurants/1/votes
    curl -u user3:333 http://localhost:8899/revoter/api/restaurants/1/votes
    curl -vu admin:admin http://localhost:8899/revoter/api/restaurants/1/votes
    
Get voting result
-----------------
    curl -v http://localhost:8899/revoter/api/restaurants/computeresult