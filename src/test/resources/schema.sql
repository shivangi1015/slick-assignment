DROP TABLE IF EXISTS experienced_employee;

CREATE TABLE IF NOT EXISTS experienced_employee(id int PRIMARY KEY,name varchar(200),experience double);

DROP TABLE IF EXISTS new_project_table;

CREATE TABLE IF NOT EXISTS new_project_table(emp_id int ,pid int PRIMARY KEY, name varchar(200),FOREIGN KEY(emp_id) REFERENCES
                   experienced_employee(id));

