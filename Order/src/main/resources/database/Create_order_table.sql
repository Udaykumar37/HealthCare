
use healthcare;


create table orders(
	id bigint primary key auto_increment,
    user_id integer not null,
    medicine_id integer not null,
    no_of_items integer not null
);

alter table order auto_increment = 3000;

