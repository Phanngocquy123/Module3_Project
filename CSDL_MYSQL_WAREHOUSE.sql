create database warehouse;
use warehouse;

create table Product(
Product_id char(5) primary key,
Product_Name varchar(150) not null unique,
Manufacturer varchar(200) not null,
Created timestamp default current_timestamp,
Batch smallint not null, -- lô chứa sản phẩm
Quantity int not null default 0,
Product_Status bit default 1 -- 1: hoạt động, 0: không hoạt động
);
select * from Product;
insert into Product(Product_id, Product_Name, Manufacturer, Batch, Quantity) values
	('P001', 'Product1', 'Manufacturer1', 1, 0),
	('P002', 'Product2', 'Manufacturer2', 2, 0),
	('P003', 'Product3', 'Manufacturer3', 4, 0),
	('P004', 'Product4', 'Manufacturer1', 2, 0),
	('P005', 'Product5', 'Manufacturer2', 3, 0),
    ('P006', 'Product6', 'Manufacturer3', 1, 0),
	('P007', 'Product7', 'Manufacturer2', 2, 0),
	('P008', 'Product8', 'Manufacturer4', 6, 0),
	('P009', 'Product9', 'Manufacturer1', 2, 0),
    ('P010', 'Product10', 'Manufacturer1', 2, 0),
    ('P011', 'Product11', 'Manufacturer1', 5, 0),
	('P012', 'Product12', 'Manufacturer2', 1, 0)
;


create table Employee(
Emp_Id char(5) primary key,
Emp_Name varchar(100) not null unique,
Birth_Of_Date timestamp,
Email varchar(100) not null,
Phone varchar(100) not null,
Address text not null,
Emp_Status smallint not null -- 0: hoạt động, 1: nghỉ chế độ, 2: nghỉ việc
);
insert into Employee (Emp_Id, Emp_Name, Birth_Of_Date, Email, Phone, Address, Emp_Status) values 
('E001', 'John Doe', '1990-11-15 00:00:00', 'john.doe@example.com', '123456789', '123 Main St, City', 0),
('E002', 'Alice Smith', '1985-08-20 00:00:00', 'alice.smith@example.com', '987654321', '456 Elm St, City', 0),
('E003', 'Tom Hiddleston', '1978-03-10 00:00:00', 'bob.johnson@example.com', '456789123', '789 Oak St, City', 0),
('E004', 'Adelaide emp01', '2008-03-10 00:00:00', 'adelaide@example.com', '456789123', '789 Oak St, City', 2),
('E005', 'Martha Johnson', '2008-03-10 00:00:00', 'marthajohnson@example.com', '456789123', '789 Oak St, City', 0),
('E006', 'Bob Johnson 12', '1998-03-10 00:00:00', 'bobjohnson@example.com', '456789123', '789 Oak St, City', 1),
('E007', 'Genevieve', '1978-03-10 00:00:00', 'genevieve@example.com', '456789123', '789 Oak St, City', 2),
('E008', 'Xenia Myrna', '1978-03-10 00:00:00', 'xeniamyrna@example.com', '456789123', '789 Oak St, City', 1),
('E009', 'Bob Sigrid', '1978-03-10 00:00:00', 'bobsigrid@example.com', '456789123', '789 Oak St, City', 0),
('E010', 'mon Johnson', '1999-03-10 00:00:00', 'monjohnson@example.com', '456789123', '789 Oak St, City', 0),
('E011', 'jeri Johnson', '1996-10-10 00:00:00', 'jeriJohnson@example.com', '456789123', '789 Oak St, City', 1),
('E012', 'Bob emp01 mit', '1998-03-10 00:00:00', 'bobmit@example.com', '456789123', '789 Oak St, City', 1),
('E013', 'Cosima', '1978-03-10 00:00:00', 'cosiman@example.com', '456789123', '789 Oak St, City', 2)
;

select * from employee;


create table Account(
Acc_Id int primary key auto_increment,
User_name varchar(30) not null unique,
Password varchar(30) not null,
Permission bit default 1, -- quyền tài khoản 0: admin, 1: user
Emp_id char(5) not null unique, -- mã nhân viên
Acc_status bit default 1 -- 1: active, 0: block
);
insert into Account (User_name, Password, Permission, Emp_id, Acc_status) values 
('admin1', 'admin1', 0, 'E001', 1),
('user1', 'user1', 1, 'E002', 1),
('user2', 'user2', 1, 'E003', 1),
('user3', 'user3', 1, 'E004', 1),
('user4', 'user4', 1, 'E005', 1),
('user5', 'user5', 1, 'E006', 1),
('user6', 'user6', 1, 'E007', 1)
;
select * from Account;
SELECT Account.Acc_Id, Account.User_name, Employee.Emp_Name
FROM Account
JOIN Employee ON Account.Emp_id = Employee.Emp_Id;

create table Bill(
Bill_id bigint primary key auto_increment,
Bill_Code varchar(10) not null,
Bill_Type bit  not null, -- 1:nhập, 0: xuất
Emp_id_created char(5) not null, foreign key (Emp_id_created) references Employee (Emp_Id), -- mã nhân viên nhập/ xuất
Created timestamp default current_timestamp,
Emp_id_auth char(5) not null, foreign key (Emp_id_auth) references Employee (Emp_Id), -- mã nhân viên duyệt
Auth_date timestamp default current_timestamp, -- ngayduyet
Bill_Status smallint not null default 0 -- 0: tạo, 1: hủy, 2: duyệt
);

create table Bill_detail(
Bill_Detail_Id bigint primary key auto_increment,
Bill_Id bigint not null, foreign key (Bill_Id) references Bill (Bill_id), -- mã phiếu nhập/ xuất
Product_Id char(5) not null, foreign key (Product_Id) references Product (Product_id),
Quantity int not null check (Quantity > 0),
Price float not null check (Price > 0)
);

insert into Bill(Bill_Code, Bill_Type, Emp_id_created, Emp_id_auth, Bill_Status) values
('B001', 1, 'E001', 'E002', 0),
('B002', 0, 'E001', 'E003', 0),
('B003', 0, 'E002', 'E001', 0),
('B004', 1, 'E003', 'E001', 0),
('B005', 1, 'E002', 'E003', 0),
('B006', 0, 'E009', 'E002', 0),
('B007', 0, 'E010', 'E003', 0),
('B008', 1, 'E012', 'E001', 0),
('B009', 1, 'E002', 'E003', 0);
select * from bill;

insert into Bill_detail(Bill_Id, Product_Id, Quantity, Price) values
(1, 'P001', 20, 100.5),
(2, 'P002', 25, 120),
(3, 'P003', 50, 20),
(4, 'P004', 60, 115),
(5, 'P005', 10, 200.5),
(6, 'P006', 10, 100.5),
(7, 'P007', 15, 120),
(8, 'P008', 10, 20),
(9, 'P009', 30, 25)
;
select * from bill_detail;