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
	('P0001', 'Product1', 'Manufacturer1', 1, 100),
	('P0002', 'Product2', 'Manufacturer2', 2, 150),
	('P0003', 'Product3', 'Manufacturer3', 1, 200),
	('P0004', 'Product4', 'Manufacturer1', 2, 120),
	('P0005', 'Product5', 'Manufacturer2', 1, 180)
;
insert into Product(Product_id, Product_Name, Manufacturer, Batch, Quantity) values
	('P0006', 'Product6', 'Manufacturer3', 1, 100),
	('P0007', 'Product7', 'Manufacturer2', 2, 150),
	('P0008', 'Product8', 'Manufacturer4', 1, 200),
	('P0009', 'Product9', 'Manufacturer1', 2, 120),
    ('P0010', 'Product10', 'Manufacturer1', 2, 120),
    ('P0011', 'Product11', 'Manufacturer1', 2, 120),
	('P0012', 'Product12', 'Manufacturer2', 1, 180)
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
('EMP01', 'John Doe', '1990-11-15 00:00:00', 'john.doe@example.com', '123456789', '123 Main St, City', 0),
('EMP02', 'Alice Smith', '1985-08-20 00:00:00', 'alice.smith@example.com', '987654321', '456 Elm St, City', 1),
('EMP03', 'Tom Hiddleston', '1978-03-10 00:00:00', 'bob.johnson@example.com', '456789123', '789 Oak St, City', 2),
('EMP04', 'Adelaide emp01', '2008-03-10 00:00:00', 'adelaide@example.com', '456789123', '789 Oak St, City', 2),
('EMP05', 'Martha Johnson', '2008-03-10 00:00:00', 'marthajohnson@example.com', '456789123', '789 Oak St, City', 0),
('EMP06', 'Bob Johnson 12', '1998-03-10 00:00:00', 'bobjohnson@example.com', '456789123', '789 Oak St, City', 1),
('EMP07', 'Genevieve', '1978-03-10 00:00:00', 'genevieve@example.com', '456789123', '789 Oak St, City', 2),
('EMP08', 'Xenia Myrna', '1978-03-10 00:00:00', 'xeniamyrna@example.com', '456789123', '789 Oak St, City', 1),
('EMP09', 'Bob Sigrid', '1978-03-10 00:00:00', 'bobsigrid@example.com', '456789123', '789 Oak St, City', 0),
('EMP10', 'mon Johnson', '1999-03-10 00:00:00', 'monjohnson@example.com', '456789123', '789 Oak St, City', 2),
('EMP11', 'jeri Johnson', '1996-10-10 00:00:00', 'jeriJohnson@example.com', '456789123', '789 Oak St, City', 1),
('EMP12', 'Bob emp01 mit', '1998-03-10 00:00:00', 'bobmit@example.com', '456789123', '789 Oak St, City', 1),
('EMP13', 'Cosima', '1978-03-10 00:00:00', 'cosiman@example.com', '456789123', '789 Oak St, City', 2)
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
('admin1', 'admin123', 0, 'EMP01', 1),
('user1', 'user123', 1, 'EMP02', 1),
('user2', 'user456', 1, 'EMP03', 1)
;
select * from Account;
SELECT Account.Acc_Id, Account.User_name, Employee.Emp_Name
FROM Account
JOIN Employee ON Account.Emp_id = Employee.Emp_Id;

create table Bill(
Bill_id long primary key auto_increment,
Bill_Code varchar(10) not null,
Bill_Type bit  not null, -- 1:nhập, 0: xuất
Emp_id_created char(5) not null, foreign key (Emp_id_created) references Employee (Emp_Id), -- mã nhân viên nhập/ xuất
Created date default (curdate()),
Emp_id_auth char(5) not null, foreign key (Emp_id_auth) references Employee (Emp_Id), -- mã nhân viên duyệt
Auth_date date default current_timestamp, -- ngayduyet
Bill_Status smallint not null default 0 -- 0: tạo, 1: hủy, 2: duyệt
);

create table Bill_detail(
Bill_Detail_Id long primary key auto_increment,
Bill_Id long not null, foreign key (Bill_Id) references Bill (Bill_id), -- mã phiếu nhập/ xuất
Product_Id char(5) not null, foreign key (Product_Id) references Product (Product_id),
Quantity int not null check (Quantity > 0),
Price float not null check (Price > 0)
);