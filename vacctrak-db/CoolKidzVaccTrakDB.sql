DROP DATABASE IF EXISTS CoolKidzVaccTrak;
CREATE DATABASE CoolKidzVaccTrak;
USE CoolKidzVaccTrak;

create table VaccineSites (
	VacCenterId int primary key auto_increment,
    VacCenter varchar(150) not null,
    Address varchar(100) not null,
    City varchar(100) not null,
    StateAbbreviation varchar(2) not null,
    ZipCode varchar(10) not null,
    PhoneNumber varchar(15) DEFAULT 'N/A',
    NumFirstVaccine int8, 
    NumSecondVaccine int8,
    Latitude varchar(30),
    Longitude varchar(30)
);

create table Users (
	UserId int primary key auto_increment,
	UserName varchar(70) not null
);

create table Permissions(
	PermId int primary key auto_increment,
    UserId int, 
    VacCenterId int, 
    FOREIGN KEY(UserId) REFERENCES Users(UserId),
    FOREIGN KEY(VacCenterId) REFERENCES VaccineSites(VacCenterId)
);