drop table if exists currency_rate;
drop table if exists currency_operation;
drop table if exists user_db;

create table if not exists currency_rate(
  currency_code varchar(3) not null primary key,
  buy_rate decimal(10,5) not null,
  sell_rate decimal(10,5) not null
);

-- User table
create table if not exists user_db(
  username varchar(50) not null primary key,
  password varchar(256) not null,
  role varchar(25) not null
);

-- CurrencyOperation table
create table if not exists currency_operation(
  id int not null AUTO_INCREMENT primary key,
  currency_code varchar(3) not null,
  type_operation varchar(10) not null,
  amount decimal(10,5) not null,
  total_amount decimal(10,5) null null,
  username varchar(25) null null,
  status varchar(10) not null,
  date_operation date not null
)
