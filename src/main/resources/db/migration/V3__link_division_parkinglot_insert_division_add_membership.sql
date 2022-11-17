-- INSERT DIVISIONS INTO DIVISION
insert into division
values (nextval('division_seq'), 'NameTest1', 'OrignalNameTest1', 'TestDirectorName', null);

insert into division
values (nextval('division_seq'), 'NameTest2', 'OrignalNameTest2', 'TestDirectorName', null);

-- LINK DIVISION TO PARKING_LOT
alter table parking_lot
add column division_id integer references division;

-- CREATE COLUMN MEMBERSHIP_LEVEL IN MEMBER
alter table member
add column membership_level varchar(16) NOT NULL default 'BRONZE';