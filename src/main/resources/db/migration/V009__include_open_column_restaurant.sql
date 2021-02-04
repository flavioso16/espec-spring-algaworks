alter table tb_restaurant add is_open tinyint(1) not null;
update tb_restaurant set is_open = false;