﻿CREATE DATABASE CarRentalManagement

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
	car_status varchar(20) not null,
	cimage nvarchar(250),
	license_plates nchar(30),
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
(N'Trương Quốc Việt', 102180153149, '0906143219', N'341 Phố Vọng, Hà Nội', '2022-07-30','2022-07-30'),
(N'Nguyễn Thị Hạnh', 103201723415, '0901233166', N'30 Trần Duy Hưng,  Hà Nội','2022-07-30','2022-07-30'),
(N'Nguyễn Vũ Long', 100298515054, '0903555333', N'11 Giải Phóng,  Hà Nội', '2022-07-30','2022-07-30')

--CREATE Staffs
CREATE TABLE Staffs(
	id_staff int identity(1,1) not null,
	staff_name nvarchar(30) not null,
	birth datetime not null,
	phone char(10) not null,
	idCard char(12) not null,
	number_of_contract int default 0,
	createdAt datetime,
	updatedAt datetime,
	primary key (id_staff)
);

drop table Staffs

INSERT INTO Staffs( staff_name, birth, phone, idCard, createdAt, updatedAt) VALUES
( N'Vũ Đình Long',  '1997-09-15', '0903642221', '102180153149', '2022-07-31', '2022-07-31'),
( N'Hoàng Ngọc Thuỳ',  '1996-07-23', '0905146587', '102180153149', '2022-07-31', '2022-07-31'),
( N'Đặng Thuỳ Trâm', '1998-10-08', '0909991199', '102180153149', '2022-07-31', '2022-07-31')


--ADD COLUMN luong TO Staffs
--ALTER TABLE Staffs ADD salary AS (3000000+Staffs.number_of_contract*300000)
--GO

--CREATE Contract
CREATE TABLE Contract(
	id_contract int identity(1,1) not null,
	id_customer int not null,
	id_staff int not null,
	VAT int not null,
	deposit float,
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

SELECT Customers.id_customer, Customers.customer_name, Customers.address, Customers.phone, Customers.idCard, Customers.createdAt, Customers.updatedAt
FROM Customers 
LEFT JOIN Contract ON Customers.id_customer = Contract.id_customer
WHERE Contract.id_contract IS NULL

SELECT COUNT(*) AS total_contract FROM Contract
SELECT COUNT(*) AS nb_Contract FROM Contract JOIN Staffs ON Contract.id_staff = Staffs.id_staff WHERE Staffs.id_staff = 1 AND Staffs.role = 'Nhân viên' GROUP BY Contract.id_staff

SELECT * FROM Car JOIN ContractDetail ON Car.id_car= ContractDetail.id_car WHERE (SELECT *
FROM Contract
JOIN Customers ON Customers.id_customer = Contract.id_customer
WHERE customer_name = 'Huyen' )

SELECT SUM(total_cost) AS cost_by FROM Contract WHERE DAY(startDate) = 9 AND MONTH(startDate) = 3 GROUP BY DAY(startDate)


CREATE VIEW CONTRACT_UNSIGNED_V AS
SELECT Contract.id_contract, Customers.id_customer, Staffs.id_staff , dbo.fChuyenCoDauThanhKhongDau(Customers.customer_name) AS 'customer_name', 
dbo.fChuyenCoDauThanhKhongDau(Staffs.staff_name) AS 'staff_name', Contract.total_cost, Contract.startDate, Contract.VAT, Contract.deposit,
Contract.endDate, Contract.createdAt, Contract.updatedAt
FROM Contract 
JOIN Customers ON Customers.id_customer = Contract.id_customer
JOIN Staffs ON Staffs.id_staff = Contract.id_staff

SELECT * FROM Car WHERE car_status = '' AND seats = 4

CREATE FUNCTION [dbo].[fChuyenCoDauThanhKhongDau](@inputVar NVARCHAR(MAX) )
RETURNS NVARCHAR(MAX)
AS
BEGIN    
    IF (@inputVar IS NULL OR @inputVar = '')  RETURN ''
   
    DECLARE @RT NVARCHAR(MAX)
    DECLARE @SIGN_CHARS NCHAR(256)
    DECLARE @UNSIGN_CHARS NCHAR (256)
 
    SET @SIGN_CHARS = N'ăâđêôơưàảãạáằẳẵặắầẩẫậấèẻẽẹéềểễệếìỉĩịíòỏõọóồổỗộốờởỡợớùủũụúừửữựứỳỷỹỵýĂÂĐÊÔƠƯÀẢÃẠÁẰẲẴẶẮẦẨẪẬẤÈẺẼẸÉỀỂỄỆẾÌỈĨỊÍÒỎÕỌÓỒỔỖỘỐỜỞỠỢỚÙỦŨỤÚỪỬỮỰỨỲỶỸỴÝ' + NCHAR(272) + NCHAR(208)
    SET @UNSIGN_CHARS = N'aadeoouaaaaaaaaaaaaaaaeeeeeeeeeeiiiiiooooooooooooooouuuuuuuuuuyyyyyAADEOOUAAAAAAAAAAAAAAAEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOUUUUUUUUUUYYYYYDD'
 
    DECLARE @COUNTER int
    DECLARE @COUNTER1 int
   
    SET @COUNTER = 1
    WHILE (@COUNTER <= LEN(@inputVar))
    BEGIN  
        SET @COUNTER1 = 1
        WHILE (@COUNTER1 <= LEN(@SIGN_CHARS) + 1)
        BEGIN
            IF UNICODE(SUBSTRING(@SIGN_CHARS, @COUNTER1,1)) = UNICODE(SUBSTRING(@inputVar,@COUNTER ,1))
            BEGIN          
                IF @COUNTER = 1
                    SET @inputVar = SUBSTRING(@UNSIGN_CHARS, @COUNTER1,1) + SUBSTRING(@inputVar, @COUNTER+1,LEN(@inputVar)-1)      
                ELSE
                    SET @inputVar = SUBSTRING(@inputVar, 1, @COUNTER-1) +SUBSTRING(@UNSIGN_CHARS, @COUNTER1,1) + SUBSTRING(@inputVar, @COUNTER+1,LEN(@inputVar)- @COUNTER)
                BREAK
            END
            SET @COUNTER1 = @COUNTER1 +1
        END
        SET @COUNTER = @COUNTER +1
    END
    -- SET @inputVar = replace(@inputVar,' ','-')
    RETURN @inputVar
END