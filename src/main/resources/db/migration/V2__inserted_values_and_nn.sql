-- ALTER TABLES SO FIELD ARE NOT NULL
alter table division
    alter column original_name set not null;
alter table division
    alter column director set not null;

alter table contact_person
    alter column street_name set not null;
alter table contact_person
    alter column street_number set not null;

alter table parking_lot
    alter column street_name set not null;
alter table parking_lot
    alter column street_number set not null;

alter table member
    alter column street_name set not null;
alter table member
    alter column street_number set not null;
alter table member
    alter column telephone_number set not null;
alter table member
    alter column email set not null;

-- INSERT VALUES INTO POSTAL_CODE
insert into postal_code
values ('1111', 'city');

insert into postal_code
values ('3020', 'Herent');

insert into postal_code
values ('3018', 'Wijgmaal');

insert into postal_code
values ('3000', 'Leuven');

insert into postal_code
values ('3010', 'Kessel-Lo');

insert into postal_code
values ('9000', 'Ghent');

insert into postal_code
values ('1000', 'Bruxelles');

insert into postal_code
values ('2000', 'Antwerp');

insert into postal_code
values ('2018', 'Berchem');

-- INSERT VALUES INTO CONTACT_PERSON
insert into contact_person
values (nextval('contact_person_seq'), 'testContact', 'contact@parkshark.com', '012 34 56 78', null, 'some street', '1', '1111');
