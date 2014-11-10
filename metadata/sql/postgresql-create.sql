-- drop the existing database
drop database buptlabDB;

-- create the test user
create user test password 'test';

-- create the database
create database buptlabDB owner test;
