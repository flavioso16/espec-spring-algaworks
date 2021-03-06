create table tb_order (
  id bigint not null auto_increment,
  subtotal decimal(10,2) not null,
  shipping_fee decimal(10,2) not null,
  total_value decimal(10,2) not null,
  status varchar(10) not null,
  creation_date datetime not null,
  confirmation_date datetime null,
  canceled_date datetime null,
  delivery_date datetime null,

  restaurant_id bigint not null,
  client_id bigint not null,
  payment_type_id bigint not null,
  address_id bigint(20),

  primary key (id),

  constraint fk_tb_order_tb_restaurant foreign key (restaurant_id) references tb_restaurant (id),
  constraint fk_tb_order_tb_user foreign key (client_id) references tb_user (id),
  constraint fk_tb_order_tb_payment_type foreign key (payment_type_id) references tb_payment_type (id)
) engine=InnoDB default charset=utf8;

create table tb_order_item (
  id bigint not null auto_increment,
  amount smallint(6) not null,
  unit_price decimal(10,2) not null,
  total_price decimal(10,2) not null,
  description varchar(255) null,
  order_id bigint not null,
  product_id bigint not null,
  
  primary key (id),
  unique key uk_tb_order_item_tb_produt (order_id, product_id),

  constraint fk_tb_order_item_tb_order foreign key (order_id) references tb_order (id),
  constraint fk_tb_order_item_tb_produt foreign key (product_id) references tb_produt (id)
) engine=InnoDB default charset=utf8;