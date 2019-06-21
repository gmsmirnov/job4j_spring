-- main tables

create table if not exists users (
	id serial primary key,
	login varchar(30) unique,
	password varchar(30)
);