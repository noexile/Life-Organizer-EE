CREATE SCHEMA `lo` DEFAULT CHARACTER SET utf8; #new schema Life Organizer
DROP SCHEMA `lo`;

CREATE TABLE lo.users (
user_id int PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(25) UNIQUE NOT NULL,
pass VARCHAR(25) NOT NULL,
email VARCHAR(25) UNIQUE NOT NULL
);

CREATE TABLE lo.payment_events (
pe_id int PRIMARY KEY AUTO_INCREMENT,
user_id int NOT NULL,
pe_name VARCHAR(25) NOT NULL,
description VARCHAR(255), 
ammount DOUBLE PRECISION UNSIGNED,
is_payed BOOLEAN NOT NULL,
for_date DATE NOT NULL,
for_time TIME NOT NULL,
FOREIGN KEY (user_id) REFERENCES lo.users(user_id)
);

CREATE TABLE lo.todos (
pe_id int PRIMARY KEY AUTO_INCREMENT,
user_id int NOT NULL,
pe_name VARCHAR(25) NOT NULL,
description VARCHAR(255),
FOREIGN KEY (user_id) REFERENCES lo.users(user_id)
);

CREATE TABLE lo.notification_events (
ne_id int PRIMARY KEY AUTO_INCREMENT,
user_id int NOT NULL,
ne_name VARCHAR(25) NOT NULL,
description VARCHAR(255), 
for_date DATE NOT NULL,
FOREIGN KEY (user_id) REFERENCES lo.users(user_id)
);

CREATE TABLE lo.shopping_lists (
pe_id int PRIMARY KEY AUTO_INCREMENT,
list_id int NOT NULL UNIQUE,
list_name VARCHAR(25) NOT NULL,
user_id int NOT NULL,
FOREIGN KEY (user_id) REFERENCES lo.users(user_id)
);

CREATE TABLE lo.shopping_entries (
pe_id int PRIMARY KEY AUTO_INCREMENT,
item_name VARCHAR(100) NOT NULL,
item_value DOUBLE PRECISION,
list_id int NOT NULL,
FOREIGN KEY (list_id) REFERENCES lo.shopping_lists(list_id)
);