# Fake iTunes

This project has been an exercise in working with REST apis in Spring Boot.

Heroku link: REMEMBER TO PASTE THIS!


The project presents multiple endpoints:
A postman collection can be found in the postman folder, in root.

/api/customers :  returns all the customers in the database, with customer id, first and last name, country, 
zipcode, phone and email.

/api/customer/create : creates a new customer in the database.

/api/customer/update/{id} : updates a customer with the chosen id, in the database.

/api/customers/popular/country : returns a list og all countries with customers registered, 
sorted in descending order after the amount of customers per country.

/api/customers/total/spend : returns a list of all customer ids and total spend, sorted in order after total spend.

/api/customer/{id}/popular/genre : returns a customers favorite genre, in case of a tie it returns all of them.


The web app also features an index, and a search page made with thymeleaf, 
These can be found at / and /search?query=<query>. 
The index page shows a search field, that leads to the search page and 3 columns with random artists, tracks and genres.


The project has a docker container setup and ready to run.