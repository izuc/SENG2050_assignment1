SENG2050 Assignment 1
Student: Lance Baker (c3128034)

Theatre.java
The Theatre class is a servlet which presents the main functionality to the website. 
It is a singular design enabling for the theatre room, the booking form, and the seat 
details viewing area to be within the one servlet; which is then singled out based 
on the parameters received. This enables for the servlet to present maximum functionality, 
without having to create separate pages for each of the other areas.

Seat.java
The Seat class is a regular Java class that is used to store the customer information 
based on the desired seating. The objects once created is then stored in a static 
HashMap within the Theatre servlet. The HashMap persists in memory until tomcat is shutdown.

The Java files are located within WEB-INF\classes.
