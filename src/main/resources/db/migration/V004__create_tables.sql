create table payment_type (
	id bigint not null auto_increment,
	description varchar(60) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_group (
	id bigint not null auto_increment,
	name varchar(60) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table group_permission (
	group_id bigint not null,
	permission_id bigint not null,
	primary key (group_id, permission_id)
) engine=InnoDB default charset=utf8;

create table permission (
	id bigint not null auto_increment,
	description varchar(60) not null,
	name varchar(100) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table produt (
	id bigint not null auto_increment,
	restaurant_id bigint not null,
	name varchar(80) not null,
	description text not null,
	price decimal(10,2) not null,
	active tinyint(1) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table address (
	id bigint not null auto_increment,
	postal_code varchar(10) not null,
	address varchar(200) not null,
	number varchar(20) not null,
	complement varchar(100) not null,
	district varchar(100) not null,
	city_id bigint not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant (
	id bigint not null auto_increment,
	kitchen_id bigint not null,
	name varchar(80) not null,
	shipping_fee decimal(10,2) not null,
	update_date datetime not null,
	creation_date datetime not null,
	address_id bigint,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant_payment_type (
	restaurant_id bigint not null,
	payment_type_id bigint not null,
	primary key (restaurant_id, payment_type_id)
) engine=InnoDB default charset=utf8;

create table tb_user (
	id bigint not null auto_increment,
	name varchar(80) not null,
	email varchar(255) not null,
	password varchar(255) not null,
	creation_date datetime not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table user_group (
	user_id bigint not null,
	grup_id bigint not null,
	primary key (user_id, grup_id)
) engine=InnoDB default charset=utf8;

alter table group_permission add constraint fk_group_permission_permission
foreign key (permission_id) references permission (id);

alter table group_permission add constraint fk_group_permission_tb_group
foreign key (group_id) references tb_group (id);

alter table produt add constraint fk_produt_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint fk_restaurant_kitchen
foreign key (kitchen_id) references kitchen (id);

alter table restaurant add constraint fk_restaurant_address
foreign key (address_id) references address (id);

alter table restaurant_payment_type add constraint fk_restaurant_payment_type_payment_type
foreign key (payment_type_id) references payment_type (id);

alter table restaurant_payment_type add constraint fk_restaurant_payment_type_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table user_group add constraint fk_user_group_tb_group
foreign key (grup_id) references tb_group (id);

alter table user_group add constraint fk_user_group_tb_user
foreign key (user_id) references tb_user (id);