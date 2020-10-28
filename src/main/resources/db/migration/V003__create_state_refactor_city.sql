create table tb_state (
	id bigint not null auto_increment,
	name varchar(80) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

insert into tb_state (name) select distinct name_state from tb_city;

alter table tb_city add column state_id bigint not null;

update tb_city c set c.state_id = (select s.id from tb_state s where s.name = c.name_state);

alter table tb_city add constraint fk_tb_city_state
foreign key (state_id) references tb_state (id);

alter table tb_city drop column name_state;

alter table tb_city change name_city name varchar(80) not null;