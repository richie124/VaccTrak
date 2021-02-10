DROP DATABASE IF EXISTS CoolKidzVaccTrak;
CREATE DATABASE CoolKidzVaccTrak;
USE CoolKidzVaccTrak;

create table VaccineSites (
	ID int primary key auto_increment,
    VacCenter varchar(150) not null,
    Address varchar(100) not null,
    City varchar(100) not null,
    StateAbbreviation varchar(2) not null,
    ZipCode varchar(10) not null,
    PhoneNumber varchar(15),
    NumFirstVaccine int8, 
    NumSecondVaccine int8
);



