CREATE TABLE BANK_ACCOUNT (
  BANK_ACCOUNT_ID int NOT NULL,
  BANK_NAME varchar(64) DEFAULT NULL,
  ACCOUNT_TYPE varchar(64) DEFAULT NULL,
  employee_id int DEFAULT NULL,
  PRIMARY KEY (BANK_ACCOUNT_ID),
  KEY fk_employee (employee_id),
  CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee(EMPLOYEE_ID)
);

insert into BANK_ACCOUNT(BANK_ACCOUNT_ID, BANK_NAME, ACCOUNT_TYPE, EMPLOYEE_ID)
values(1, 'Kotak Bank','Savings');

insert into BANK_ACCOUNT(BANK_ACCOUNT_ID, BANK_NAME, ACCOUNT_TYPE, EMPLOYEE_ID)
values(2, 'HDFC Bank','Current');

insert into BANK_ACCOUNT(BANK_ACCOUNT_ID, BANK_NAME, ACCOUNT_TYPE, EMPLOYEE_ID)
values(3, 'ICICI Bank','Savings');

insert into BANK_ACCOUNT(BANK_ACCOUNT_ID, BANK_NAME, ACCOUNT_TYPE, EMPLOYEE_ID)
values(4, 'Idbi Bank','Current');

insert into BANK_ACCOUNT(BANK_ACCOUNT_ID, BANK_NAME, ACCOUNT_TYPE, EMPLOYEE_ID)
values(5, 'Kotak Bank','Savings', (select employee_id from employee where employee_name='Soham'));

insert into BANK_ACCOUNT(BANK_ACCOUNT_ID, BANK_NAME, ACCOUNT_TYPE, EMPLOYEE_ID)
values(6, 'HDFC Bank','Current', (select employee_id from employee where employee_name='Soham'));

insert into BANK_ACCOUNT(BANK_ACCOUNT_ID, BANK_NAME, ACCOUNT_TYPE, EMPLOYEE_ID)
values(7, 'ICICI Bank','Savings', (select employee_id from employee where employee_name='Sagar'));

insert into BANK_ACCOUNT(BANK_ACCOUNT_ID, BANK_NAME, ACCOUNT_TYPE, EMPLOYEE_ID)
values(8, 'Idbi Bank','Current', (select employee_id from employee where employee_name='Shyam'));

insert into BANK_ACCOUNT(BANK_ACCOUNT_ID, BANK_NAME, ACCOUNT_TYPE, EMPLOYEE_ID)
values(9, 'DCB Bank','Current', (select employee_id from employee where employee_name='Soham'));

COMMIT;