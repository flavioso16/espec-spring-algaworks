alter table tb_order add code varchar(36) not null after id;
update tb_order set code = uuid();
alter table tb_order add constraint uk_tb_order_code unique(code);