create table products (
    id          bigserial primary key,
    title       varchar(255),
    price       numeric(8, 2) not null
);

insert into products (title, price)
values ('Milk', 100),
       ('Bread', 80),
       ('Cheese', 90);

create table users (
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    address         varchar(255),
    phone           varchar(24),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles (
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles (
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email, address, phone)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com', 'street 1', '89054455444'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com', 'street 2', '89135554545');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

create table orders (
    id              bigserial primary key,
    user_id         bigint not null references users (id),
    total_price     numeric(8, 2) not null,
    address         varchar(255),
    phone           varchar(24),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table order_items (
    id                      bigserial primary key,
    product_id              bigint not null references products (id),
    order_id                bigint not null references orders (id),
    quantity                int not null,
    price_per_product       numeric(8, 2) not null,
    price                   numeric(8, 2) not null,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
)