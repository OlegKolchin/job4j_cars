create table brand(
    id serial primary key,
    name text
);

create table carBody(
    id serial primary key,
    name text
);

create table car(
    id serial primary key,
    name text,
    brand_id int references brand(id),
    carBody_id int references carBody(id)
);

create table ad(
    id serial primary key,
    description text,
    photoUrl text,
    car_id int references car(id)
);