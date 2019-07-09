drop database if exists superheros;
create database superheros;

use superheros;

create table hero (
id int primary key AUTO_INCREMENT,
name varchar(25),
description varchar(50),
superpower varchar(50)
);

create table location (
id int primary key AUTO_INCREMENT,
longitude decimal,
latitude decimal,
`name` varchar(25),
address varchar(75)
);

create table `organization` (
id int primary key AUTO_INCREMENT,
name varchar(50),
description varchar(100),
contact varchar(100) 
);

create table sighting (
id int primary key AUTO_INCREMENT,
`date` Date, 
locationid int,
foreign key(locationid) references location(id)
);

create table sighting_hero (
sightingid int,
heroid int,
primary key(sightingid, heroid),
foreign key(sightingid) references sighting(id),
foreign key(heroid) references hero(id)
);

create table organization_hero (
organizationid int,
heroid int,
primary key(organizationid, heroid),
foreign key(organizationid) references `organization`(id),
foreign key(heroid) references hero(id)
);
