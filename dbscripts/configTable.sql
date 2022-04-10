CREATE TABLE configuration (
  ID int,
  NAME VARCHAR(64),
  EXPRESSION VARCHAR(64),
  PRIMARY KEY (ID)
);

insert into configuration(ID, NAME, EXPRESSION)
values(1, 'taskSchedule','0 30 18 ? * *');

insert into configuration(ID, NAME, EXPRESSION)
values(2, 'taskSchedule','0 */5 * ? * *');

insert into configuration(ID, NAME, EXPRESSION)
values(3, 'reportSchedule','0 */10 * ? * *');

commit;