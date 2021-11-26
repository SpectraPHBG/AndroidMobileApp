CREATE DATABASE AndroidCalculatorDB
USE AndroidCalculatorDB

CREATE TABLE CalculatorEquations(
ID INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
equation VARCHAR(100) NOT NULL,
x1 VARCHAR(70) NOT NULL,
x2 VARCHAR(70) NOT NULL
)

Alter table CalculatorEquations 
add appID integer not null