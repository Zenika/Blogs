DROP TABLE contacts IF EXISTS;
CREATE TABLE contacts(
    id INT IDENTITY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    phonenumber VARCHAR(255)
);
