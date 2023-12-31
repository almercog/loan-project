drop table if exists employee cascade;

CREATE TABLE employee (
    id int NOT NULL,
    lastname varchar(255) NOT NULL,
    firstname varchar(255),
    age int,
    designation varchar(255),
    city varchar(255),
    PRIMARY KEY (id)
);

insert into employee(id,firstname,lastname,age,designation,city) values (1, 'john', 'bob', 55, 'AI Engineer', 'NewYork');
insert into employee(id,firstname,lastname,age,designation,city) values (2, 'Mike', 'Mores', 45, 'Document Writer', 'NewJersey');
insert into employee(id,firstname,lastname,age,designation,city) values (3, 'Iqbal', 'Kasim', 33, 'DB Engineer', 'Toronto');
insert into employee(id,firstname,lastname,age,designation,city) values (4, 'Shiva', 'Sai', 61, 'Developer', 'Ajax');
insert into employee(id,firstname,lastname,age,designation,city) values (5, 'Igor', 'van', 61, 'IT', 'Toronto');
insert into employee(id,firstname,lastname,age,designation,city) values (6, 'Anees', 'Begam', 45, 'Manager', 'NewYork');
insert into employee(id,firstname,lastname,age,designation,city) values (7, 'Tyler', 'Top', 44, 'Manager', 'NewJersey');