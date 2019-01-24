# Illumio_Assesment

All files related to coding challenged have been commited under this repository.

# Approach

I have extracted basic functionality, i.e accept_packet on Interface level, while data loading i.e rule loading is been done in implementation part and is part of the constructor.

Walkthrough of the logic for parsing the csv file >>
1. Initally check if the port contains "-" as delimiter, than check for "-" as delimiter in Ip address, if both are true, I will extract all posible combination in terms of Rule object.
2. if there is delimiter in port but not in ip address, we just have to extract combination of the port with fixed ip.
3. check for ip has delimter separately and extract rules on this term
4. Simple rule with no complexity.

USED FACADE DESIGN PATTERN

# Testing

Code is tested using Junit 4, with covering the bounday conditions and verfying the negative test cases such as Exceptions

# Optimization and Refinement

Whole rule exceution can be efficiently be done using Drools API, which can execute million of rules wrt to low latency.

I felt challenge of updating the rule sheet for the Drools, as in current scenario, rules needs to be feed on runtime, so data parsing and rules writing in excel sheet would leaded some time. Otherwise this is one of optimized approach for dong such rules excecution.

As of now Rule object was very much defined and constructor was solving the purose of intialization and data setting, but if data setting was each different fields, I would have used Builder pattern for object creation. 

# Which team

I am looking forward to join Platform team. 
