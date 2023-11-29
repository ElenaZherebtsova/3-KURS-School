CREATE TABLE users (
id      INTEGER PRIMARYKEY,
name    VARCHAR,
age     INTEGER,
driver_license BOOLEAN,
car_id  INTEGER REFERENCES cars(id)
);

CREATE TABLE cars (
id      INTEGER,
brand   VARCHAR,
MODEL   VARCHAR,
cost    INTEGER
);