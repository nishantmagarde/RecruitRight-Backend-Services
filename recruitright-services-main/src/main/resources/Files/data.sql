create table user(userId varchar(50) primary key, userType varchar(20), firstName varchar(20), lastName varchar(20), contact varchar(20), password varchar(20));
create table session(userId varchar(50), sessionId varchar(100) primary key, foreign key(userId) references user(userId));
create table userprofiles(userId varchar(50) primary key, name varchar(50), contact varchar(20), resume longblob, status varchar(20) default "active",uploader varchar(50));
create table requirements(reqId int primary key auto_increment, userId varchar(50), isu varchar(50), subIsu varchar(50), projectName varchar(100), jobRole varchar(50), jobRoleType varchar(50), techStack varchar(1000), experience double, status varchar(50) default "active",foreign key(userId) references user(userId));
alter table requirements auto_increment=101; 
create table profilescores(reqId int, userId varchar(50), profileScore double, status varchar(20),foreign key(reqId) references requirements(reqId));
create table feedback(reqId int,userId varchar(50), name varchar(50), contact varchar(20), profileScore double, status varchar(20), remarks varchar(1000), foreign key(reqId) references requirements(reqId));

