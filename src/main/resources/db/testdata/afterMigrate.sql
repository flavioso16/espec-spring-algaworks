set foreign_key_checks = 0;

delete from tb_city;
delete from tb_kitchen;
delete from tb_state;
delete from tb_payment_type;
delete from tb_group;
delete from tb_group_permission;
delete from tb_permission;
delete from tb_produt;
delete from tb_address;
delete from tb_restaurant;
delete from tb_restaurant_payment_type;
delete from tb_user;
delete from tb_user_group;
delete from tb_restaurant_user_responsible;
delete from tb_order_item;
delete from tb_order;

set foreign_key_checks = 1;

alter table tb_city auto_increment = 1;
alter table tb_kitchen auto_increment = 1;
alter table tb_state auto_increment = 1;
alter table tb_payment_type auto_increment = 1;
alter table tb_group auto_increment = 1;
alter table tb_permission auto_increment = 1;
alter table tb_produt auto_increment = 1;
alter table tb_address auto_increment = 1;
alter table tb_restaurant auto_increment = 1;
alter table tb_user auto_increment = 1;
alter table tb_order auto_increment = 1;
alter table tb_order_item auto_increment = 1;

insert into tb_kitchen (id, name) values
(1, 'Tailandesa'),
(2, 'Indiana'),
(3, 'Argentina'),
(4, 'Brasileira');

insert into tb_state (id, name) values
(1, 'Minas Gerais'),
(2, 'São Paulo'),
(3, 'Ceará');

insert into tb_city (id, name, state_id) values
(1, 'Uberlândia', 1),
(2, 'Belo Horizonte', 1),
(3, 'São Paulo', 2),
(4, 'Campinas', 2),
(5, 'Fortaleza', 3);

insert into tb_address (id, postal_code, address, number, complement, district, city_id) values
(1, '38400-999', 'Rua João Pinheiro', '1000', null, 'Centro', 1),
(2, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Centro', 3),
(3, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 4);

insert into tb_restaurant (id, name, shipping_fee, kitchen_id, active, is_open, creation_date, update_date, address_id) values
(1, 'Thai Gourmet', 10, 1, true, true, utc_timestamp, utc_timestamp, 1),
(2, 'Thai Delivery', 9.50, 1, true, true, utc_timestamp, utc_timestamp, null),
(3, 'Tuk Tuk Comida Indiana', 15, 2, true, true, utc_timestamp, utc_timestamp, null),
(4, 'Java Steakhouse', 12, 3, true, true, utc_timestamp, utc_timestamp, null),
(5, 'Lanchonete do Tio Sam', 11, 4, true, true, utc_timestamp, utc_timestamp, null),
(6, 'Bar da Maria', 6, 4, true, true, utc_timestamp, utc_timestamp, null);

insert into tb_payment_type (id, description) values
(1, 'Cartão de crédito'),
(2, 'Cartão de débito'),
(3, 'Dinheiro');

insert into tb_permission (id, name, description) values
(1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas'),
(2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into tb_restaurant_payment_type (restaurant_id, payment_type_id) values
(1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into tb_produt (name, description, price, active, restaurant_id) values
('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1),
('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 0, 1),
('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2),
('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3),
('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3),
('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4),
('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4),
('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5),
('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into tb_group (name)
values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

insert into tb_group_permission (group_id, permission_id)
values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into tb_user (id, name, email, password, creation_date) values
(1, 'User One', 'user1@algafood.com', 'user1', utc_timestamp),
(2, 'User Two', 'user2@algafood.com', 'user2', utc_timestamp),
(3, 'User Three', 'user3@algafood.com', 'user3', utc_timestamp),
(4, 'User Four', 'user4@algafood.com', 'user4', utc_timestamp);

insert into tb_user_group(user_id, group_id) values (1, 1), (1, 2), (2, 2);

insert into tb_restaurant_user_responsible(user_id, restaurant_id) values (1, 1), (1, 2), (2, 2);

insert into tb_order (id, code, restaurant_id, client_id, payment_type_id, address_id,
    status, creation_date, subtotal, shipping_fee, total_value) values
(1, '9d5fa7b1-b75c-4e3b-99df-d18a68062661', 1, 1, 1, 1, 'CREATED', utc_timestamp, 298.90, 10, 308.90),
(2, '40e97a21-99b2-4aeb-89fc-fd704eb56519', 4, 1, 2, 1, 'CREATED', utc_timestamp, 79, 0, 79),
(3, '40e97a21-99b2-4aeb-89fc-dsadd2d2wd2w', 2, 2, 2, 1, 'CREATED', utc_timestamp, 79, 0, 79),
(4, '40e97a21-99b2-4aeb-89fc-f2f2f23f23f2', 4, 2, 2, 1, 'CREATED', utc_timestamp, 79, 0, 79);

insert into tb_order_item (id, order_id, product_id, amount, unit_price, total_price, description) values
(1, 1, 1, 1, 78.9, 78.9, null),
(2, 1, 2, 2, 110, 220, 'Menos picante, por favor'),
(3, 2, 6, 1, 79, 79, 'Ao ponto');



