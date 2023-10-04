docker run --name postgres-db -e POSTGRES_PASSWORD=pwd -e POSTGRES_USER=ira -p 5432:5432 -d postgres

CREATE TABLE data
(
    id              SERIAL primary key,
    content         VARCHAR(50000),
    modification_date     DATE
);