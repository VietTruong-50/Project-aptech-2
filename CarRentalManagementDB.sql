CREATE DATABASE CarRentalManagement

USE CarRentalManagement

CREATE TABLE Users(
	id_user int identity(1,1) not null,
	user_name varchar (20) not null,
	password varchar(20) not null
	primary key (id_user)
);

INSERT INTO Users(user_name, password) VALUES ('viet', '123')

--CREATE Car
CREATE TABLE Car(
	id_car int identity(1,1) not null,
	car_name varchar (40) not null,
	manufacture nvarchar(40) not null,
	seats tinyint not null,
	rental_cost float not null,
	model varchar(50),
	car_status varchar(10) not null,
	cimage nvarchar(500),
	license_plates nchar(10),
	createdAt datetime,
	updatedAt datetime,
	primary key (id_car)
);

--CREATE Customers
CREATE TABLE Customers(
	id_customer int identity(1,1) not null,
	customer_name nvarchar(30) not null,
	idCard char(12) not null,
	phone char(10) not null,
	address nvarchar(80) not null,
	createdAt datetime,
	updatedAt datetime,
	primary key (id_customer)
);

INSERT INTO Customers( customer_name, idCard, phone, address, createdAt, updatedAt) VALUES
(N'Trương Quốc Việt', 102180153149, 0906143219, N'341 Phố Vọng, Hà Nội', '2022-07-30','2022-07-30'),
(N'Nguyễn Thị Hạnh', 103201723415, 0901233166, N'30 Trần Duy Hưng,  Hà Nội','2022-07-30','2022-07-30'),
(N'Nguyễn Vũ Long', 100298515054, 0903555333, N'11 Giải Phóng,  Hà Nội', '2022-07-30','2022-07-30')

--CREATE Staffs
CREATE TABLE Staffs(
	id_staff int identity(1,1) not null,
	staff_name nvarchar(30) not null,
	birth datetime not null,
	phone char(10) not null,
	createdAt datetime,
	updatedAt datetime,
	primary key (id_staff)
);

INSERT INTO Staffs( staff_name, birth, phone, createdAt, updatedAt) VALUES
( N'Vũ Đình Long',  '1997-09-15', 0903642221, '2022-07-31', '2022-07-31'),
( N'Hoàng Ngọc Thuỳ',  '1996-07-23', 0905146587, '2022-07-31', '2022-07-31'),
( N'Đặng Thuỳ Trâm', '1998-10-08', 0909991199, '2022-07-31', '2022-07-31')


--ADD COLUMN luong TO Staffs
--ALTER TABLE Staffs ADD salary AS (3000000+Staffs.number_of_contract*300000)
--GO

--CREATE Contract
CREATE TABLE Contract(
	id_contract int identity(1,1) not null,
	id_customer int not null,
	id_staff int not null,
	VAT int not null,
	deposit float(20),
	startDate datetime not null,
	endDate datetime not null,
	total_cost float null,
	createdAt datetime,
	updatedAt datetime,
	primary key (id_contract)
);



--CREATE ContractDetail
CREATE TABLE ContractDetail(
	id_contract_detail int  identity(1,1) not null,
	id_contract int not null,
	id_car int not null,
	primary key (id_contract_detail)
);


ALTER TABLE Contract 
ADD CONSTRAINT FK_Customers_Contract FOREIGN KEY (id_customer) REFERENCES Customers(id_customer);

ALTER TABLE ContractDetail 
ADD CONSTRAINT FK_Car_ContractDetail FOREIGN KEY (id_car) REFERENCES Car(id_car);

ALTER TABLE Contract 
ADD CONSTRAINT FK_Staffs_Contract FOREIGN KEY (id_staff) REFERENCES Staffs(id_staff);

ALTER TABLE ContractDetail
ADD CONSTRAINT FK_ContractDetail_Contract FOREIGN KEY (id_contract) REFERENCES Contract(id_contract);

CREATE VIEW CONTRACT_V AS
SELECT Contract.id_contract, Customers.id_customer, Staffs.id_staff , Customers.customer_name, 
Staffs.staff_name, Contract.total_cost, Contract.startDate, Contract.VAT, Contract.deposit,
Contract.endDate, Contract.createdAt, Contract.updatedAt
FROM Contract 
JOIN Customers ON Customers.id_customer = Contract.id_customer
JOIN Staffs ON Staffs.id_staff = Contract.id_staff

SELECT * FROM CONTRACT_V
DROP VIEW CONTRACT_V

SELECT (SELECT COUNT(*) FROM Contract WHERE id_staff = '1')*100/(SELECT COUNT(*) FROM Contract) AS percent_of_work

SELECT SUM(total_cost) AS cost_by FROM Contract WHERE DAY(startDate) = 15 GROUP BY DAY(startDate)
