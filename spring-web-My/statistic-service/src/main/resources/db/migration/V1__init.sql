create table orderitemstat
(
    id           bigserial primary key,
    productid    bigint not null,
    producttitle varchar(255),
    quantity     int,
    date         timestamp default current_timestamp
);

insert into orderitemstat (productid, producttitle, quantity)
values (1,'Milk', 5),
       (2, 'Bread!', 80),
       (3, 'Cheese!', 40),
       (4, 'Cheese2!', 90),
       (5, 'Cheese3!', 25),
       (6, 'Cheese4', 5),
       (7, 'Cheese5', 10),
       (8, 'Cheese6', 9),
       (9, 'Cheese7!', 100),
       (10, 'Cheese8', 2),
       (11, 'Cheese9', 15);

create table cartitemstat
(
    id           bigserial primary key,
    productid    bigint not null,
    producttitle varchar(255),
    quantity     int,
    date         timestamp default current_timestamp
);

insert into cartitemstat (productid, producttitle, quantity)
values (1,'Milk', 5),
       (2, 'Bread!', 80),
       (3, 'Cheese!', 40),
       (4, 'Cheese2!', 90),
       (5, 'Cheese3!', 25),
       (6, 'Cheese4', 5),
       (7, 'Cheese5', 10),
       (8, 'Cheese6', 9),
       (9, 'Cheese7!', 100),
       (10, 'Cheese8', 2),
       (11, 'Cheese9', 15);










