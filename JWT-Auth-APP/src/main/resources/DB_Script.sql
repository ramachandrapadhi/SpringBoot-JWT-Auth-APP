create database user_auth;

create table Users (
user_id varchar(255) not null, 
user_name varchar(255), 
first_name varchar(255), 
last_name varchar(255),
user_email varchar(255), 
password varchar(255), 
created_at datetime(6),
updated_at datetime(6), 
created_by varchar(255), 
updated_by varchar(255), 
primary key (user_id)) 
engine=InnoDB
