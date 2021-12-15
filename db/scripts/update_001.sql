create table brand(
    id serial primary key,
    name text
);

create table carBody(
    id serial primary key,
    name text
);

create table ad(
    id serial primary key,
    description text,
    brand_id int references brand(id),
    carBody_id int references carBody(id),
    photoUrl text
);