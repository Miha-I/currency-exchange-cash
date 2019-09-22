insert into currency_rate values ('USD', 25.05, 28.15);
insert into currency_rate values ('EUR', 29.50, 31.10);
insert into currency_rate values ('RUR', 0.35, 0.45);

insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('EUR', 'buy', 100.00, 3110.00, 'user', 'new', '2019-09-17');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('RUR', 'sell', 250.00, 87.50, 'user', 'new', '2019-09-17');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('EUR', 'sell', 100.00, 2950.00, 'user', 'new', '2019-09-17');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('RUR', 'sell', 250.00, 87.50, 'user', 'new', '2019-09-17');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('RUR', 'buy', 50.00, 92.50, 'user', 'new', '2019-05-18');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('USD', 'sell', 250.00, 58.50, 'user', 'new', '2018-08-17');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('RUR', 'buy', 380.00, 245.50, 'user', 'new', '2018-01-13');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('RUR', 'buy', 10.00, 3658.50, 'user', 'new', '2018-12-25');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('USD', 'sell', 190.00, 236.50, 'user', 'new', '2017-04-17');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('RUR', 'sell', 26.00, 2364.50, 'user', 'new', '2017-04-17');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('USD', 'buy', 750.00, 214.50, 'user', 'new', '2017-03-17');
insert into currency_operation(currency_code, type_operation, amount, total_amount, username, status, date_operation)
values('RUR', 'sell', 18.00, 254.50, 'user', 'new', '2017-07-17');