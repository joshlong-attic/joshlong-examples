--
-- CRM DDL
--

-- basic table definitions
CREATE TABLE product (
  id SERIAL,
  description VARCHAR(255),
  name VARCHAR(255) NOT NULL,
  price DOUBLE PRECISION NOT NULL
);


CREATE TABLE customer (
  id serial,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL
);

CREATE TABLE purchase (
  id serial ,
  total DOUBLE PRECISION NOT NULL,
  customer_id BIGINT NOT NULL
);


CREATE TABLE line_item (
  id serial,
  product_id BIGINT NOT NULL,
  purchase_id BIGINT NOT NULL
);

-- sets up the foreign keys
alter table line_item add foreign key (product_id) references product(id);

alter table line_item add foreign key (purchase_id ) references purchase (id);

alter table purchase add foreign key(customer_id ) references customer (id);
