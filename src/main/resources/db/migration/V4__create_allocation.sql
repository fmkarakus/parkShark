-- ADD COLUMN AVAILABLE_CAPACITY TO PARKING_LOT
alter table parking_lot
add column available_capacity integer not null default 0;

-- CREATE TABLE ALLOCATION
create table allocation(
    id integer primary key,
    member_id integer references member,
    license_plate_number varchar(16) not null ,
    parking_lot_id integer references parking_lot,
    starting_time date not null,
    stopping_time date,
    status varchar(16) not null
);