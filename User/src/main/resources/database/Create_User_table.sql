
use healthcare;


create table user(
	id bigint primary key auto_increment,
    name varchar(255) not null,
    user_name varchar(255) not null,
    mobile_number varchar(20)
);

alter table user auto_increment = 5000;

