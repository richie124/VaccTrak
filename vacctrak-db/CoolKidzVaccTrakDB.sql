DROP DATABASE IF EXISTS CoolKidzVaccTrak;
CREATE DATABASE CoolKidzVaccTrak;
USE CoolKidzVaccTrak;

create table VaccineSites (
	ID int primary key auto_increment,
    VacCenter varchar(70) not null,
    Address varchar(100) not null,
    City varchar(100) not null,
    State varchar(10) not null,
    ZipCode varchar(10) not null,
    PhoneNumber varchar(15) not null,
    NumFirstVaccine int8, 
    NumSecondVaccine int8
);



