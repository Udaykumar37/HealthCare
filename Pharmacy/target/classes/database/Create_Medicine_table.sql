
use healthcare;


create table medicine(
	id bigint primary key auto_increment,
    name varchar(255) not null,
    price double not null,
    description varchar(255)
);

alter table medicine auto_increment = 1000;

