create table brand(
    id serial primary key,
    name text
);

create table body(
    id serial primary key,
    name text
);

create table car(
    id serial primary key,
    name text,
    brand_id int references brand(id),
    body_id int references body(id)
);

create table ad(
    id serial primary key,
    description text,
    photoUrl text,
    created timestamp with time zone,
    car_id int references car(id)
);