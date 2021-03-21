create table tb_restaurant_user_responsible (
	restaurant_id bigint not null,
	user_id bigint not null,
	primary key (restaurant_id, user_id)
) engine=InnoDB default charset=utf8;

alter table tb_restaurant_user_responsible add constraint fk_tb_restaurant_user_responsible_restaurant
foreign key (restaurant_id) references tb_restaurant (id);

alter table tb_restaurant_user_responsible add constraint fk_tb_restaurant_user_responsible_user
foreign key (user_id) references tb_user (id);
