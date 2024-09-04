
--1.
create type "STATUS" as ENUM ('ACTIVE', 'INACTIVE', 'DISABLED');


--2.
create table "ACCOUNTS" (
    "ID" UUID PRIMARY KEY,
    "EMAIL" VARCHAR(255),
    "STATUS" STATUS,
    "CREATED_AT" TIMESTAMP NOT NULL
);


--3.
create type "GENDER" as ENUM ('♂', '♀', '⚧');


--4.
create table "PROFILES" (
    "ACCOUNT_ID" UUID PRIMARY KEY REFERENCES ACCOUNTS("ID"),
    "FIRST_NAME" VARCHAR(255),
    "LAST_NAME" VARCHAR(255),
    "GENDER" "GENDER"
);


--5.
create table "ACCOUNT_AUTH_HISTORIES" (
    "ACCOUNT_ID" UUID REFERENCES ACCOUNTS(ID),
    "IS_SUCCESS" BOOLEAN,
    "ATTEMPTED_AT" TIMESTAMP WITHOUT TIME ZONE,
    PRIMARY KEY ("ACCOUNT_ID", "ATTEMPTED_AT")
);


--6.
select *
from "ACCOUNTS" a
join "PROFILES" p on a."ID" = p."ACCOUNT_ID";


--7.
select "ID", "EMAIL", "STATUS", "CREATED_AT", NULL AS "FIRST_NAME", NULL AS "LAST_NAME", NULL AS "GENDER"
from "ACCOUNTS"


UNION ALL

select "ACCOUNT_ID" AS "ID", NULL AS "EMAIL", NULL AS "STATUS", NULL AS "CREATED_AT", "FIRST_NAME", "LAST_NAME", "GENDER"
from "PROFILES";


--8.
select "GENDER", COUNT(*)
from "PROFILES"
group BY "GENDER";



--9.
select a."ID", a."EMAIL", a."STATUS", a."CREATED_AT", p."FIRST_NAME", p."LAST_NAME", p."GENDER"
from "ACCOUNTS" a
join "PROFILES" p on a."ID" = p."ACCOUNT_ID"
where a."CREATED_AT" between '2023-08-01' AND '2023-09-01';


--10.
select a."ID", a."EMAIL", a."STATUS", a."CREATED_AT", p."FIRST_NAME", p."LAST_NAME", p."GENDER", ah."IS_SUCCESS", ah."ATTEMPTED_AT"
from "ACCOUNTS" a
left join "PROFILES" p on a."ID" = p."ACCOUNT_ID"
left join "ACCOUNT_AUTH_HISTORIES" ah on a."ID" = ah."ACCOUNT_ID"
order by ah."ATTEMPTED_AT";



--11.
select a."EMAIL", p."FIRST_NAME", p."LAST_NAME", p."GENDER", ah."IS_SUCCESS", ah."ATTEMPTED_AT"
from "ACCOUNT_AUTH_HISTORIES" ah
join "ACCOUNTS" a on ah."ACCOUNT_ID" = a."ID"
join "PROFILES" p on a."ID" = p."ACCOUNT_ID"
where ah."IS_SUCCESS" = TRUE
and ah."ATTEMPTED_AT" between '2023-08-10' AND '2023-09-01';



--12.
select *
from "ACCOUNT_AUTH_HISTORIES"
order by "ATTEMPTED_AT"
limit 10;




