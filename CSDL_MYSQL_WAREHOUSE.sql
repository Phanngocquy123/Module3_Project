create database warehouse;
use warehouse;

create table Product(
Product_id char(5) primary key,
Prodcut_Name varchar(150) not null unique,
Manufacturer varchar(200) not null,
Created date default (curdate()),
Batch smallint not null, -- lô chứa sản phẩm
Quantity int not null default 0,
Product_Status bit default 1 -- 1: hoạt động, 0: không hoạt động
);

create table Employee(
Emp_Id char(5) primary key,
Emp_Name varchar(100) not null unique,
Birth_Of_Date date,
Email varchar(100) not null,
Phone varchar(100) not null,
Address text not null,
Emp_Status smallint not null -- 0: hoạt động, 1: nghỉ chế độ, 2: nghỉ việc
);

create table Account(
Acc_Id int primary key auto_increment,
User_name varchar(30) not null unique,
Password varchar(30) not null,
Permission bit default 1, -- quyền tài khoản 0: admin, 1: user
Emp_id char(5) not null unique, -- mã nhân viên
Acc_status bit default 1 -- 1: active, 0: block
);

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