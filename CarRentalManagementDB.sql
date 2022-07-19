CREATE DATABASE CarRentalManagement

USE CarRentalManagement

CREATE TABLE Users(
	id_user int not null,
	user_name varchar (20) not null,
	password varchar(20) not null
	primary key (id_user)
);

--CREATE Car
CREATE TABLE Car(
	id_car int identity(1,1) not null,
	car_name varchar (40) not null,
	manufacture nvarchar(40) not null,
	seats tinyint not null,
	rental_cost int not null,
	model varchar(50),
	car_status varchar(10) not null,
	cimage image,
	license_plates nchar(10),
	primary key (id_car)
);

--CREATE Customers
CREATE TABLE Customers(
	id_customer int identity(1,1) not null,
	customer_name nvarchar(30) not null,
	idCard char(12) not null,
	phone char(10) not null,
	address nvarchar(80) not null,
	primary key (id_customer)
);

--CREATE Staffs
CREATE TABLE Staffs(
	id_staff int identity(1,1) not null,
	staff_name nvarchar(30) not null,
	birth datetime not null,
	phone char(10) not null,
	number_of_contract int default 0,
	primary key (id_staff)
);

--ADD COLUMN luong TO Staffs
ALTER TABLE Staffs ADD salary AS (3000000+Staffs.number_of_contract*300000)
GO

--CREATE Contract
CREATE TABLE Contract(
	id_contract int not null,
	id_car int not null,
	id_customer int not null,
	id_staff int not null,
	total_cost int null,
	VAT int not null,
	startDate datetime not null,
	endDate datetime not null,
	deposit float(20),
	primary key (id_contract)
);

--CREATE ContractDetail
CREATE TABLE ContractDetail(
	id_contract_detail int not null,
	returnDate datetime default null,
	primary key (id_contract_detail)
);