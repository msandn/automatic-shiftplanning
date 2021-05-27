//DROP TABLE SHIFT_SCHEDULE_SHIFTS;
//DROP TABLE SHIFT_SCHEDULE_EMPLOYEES;
//DROP TABLE shift;
//DROP TABLE SHIFT_SCHEDULE;
//DROP TABLE SHIFT_DOMAIN;
//DROP TABLE employee;


CREATE TABLE IF NOT EXISTS  employee(
                                      id        INTEGER  PRIMARY KEY,
                                      skill_ids ARRAY
);
