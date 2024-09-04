-- #1 A bookstore owner is looking for a developer to design a system that can manage books in store, write the commands to create the tables in SQL syntax.
-- Note: the primary keys must be a long number and auto-incremental.
create table Authors (
  Id SERIAL PRIMARY KEY,
  First_name varchar(250),
  Last_name  varchar(250)
);
create table Genres (
  Id SERIAL PRIMARY KEY,
  Name varchar(250)
);

create table books (
  Id SERIAL PRIMARY KEY,
  Title varchar(250),
  Published_on date,
  Volume varchar (250),
  Edition varchar (250),
  Pages int,
  Author_Id references(Authors.Id),
  Genre_Id references(Genres.Id)
  );



-- #2.1 Employees db
create table Jobs (
Id BIGINT PRIMARY KEY AUT0_INCREMENT, Title varchar(250));

-- #2.2
create table Employees (
Id BIGINT PRIMARY KEY AUT0_INCREMENT,
Firstname varchar(250), 
Lastname varchar(250), 
Email varchar(250), 
contact_no varchar(90), 
salary float, 
joined_at, 
jobId references(Jobs.Id)
);

-- #2.3
Insert into Jobs(Title) values ("labandera");

-- #2.4
Insert into Employees(Firstname, Lastname, Email, contact_no, salary, joined_at, jobId)
values("Shai", "Shei", "mail@example.com", "0911234", 40000, 2015-07-21, 4)

-- #2.5 
select id concat(Firstname + " " + Lastname) as 'name' salary from Employees order by salary ASC

