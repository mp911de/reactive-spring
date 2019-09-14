CREATE TABLE person (id int auto_increment PRIMARY KEY, firstname varchar(255), lastname varchar(255));
CREATE TABLE person_event (id int auto_increment PRIMARY KEY, person_id int, event_type varchar(255), event_time timestamp);
