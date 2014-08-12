
insert into KSEN_POPULATION_RULE (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, ID, NAME, OBJ_ID, POPULATION_RULE_STATE, POPULATION_RULE_TYPE, REF_POPULATION_ID, SUPPORTS_GET_MBR_IND, UPDATEID, UPDATETIME, VARIES_BY_TIME_IND, VER_NBR) values ('SYSTEM', TIMESTAMP '2014-07-09 16:48:38', 'People who should be included in a registration is open check', 'People who should be included in a registration is open check', 'kuali.population.rule.key.regdatecheck', 'RegistrationDateCheckRule', '64626a67-c3ff-49dc-9589-10224e5a9811', 'kuali.population.population.rule.state.active', 'kuali.population.rule.type.person', null, 1, 'SYSTEM', TIMESTAMP '2014-07-09 16:48:38', 0, 0)
/

insert into KSEN_POPULATION (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, ID, NAME, OBJ_ID, POPULATION_RULE_ID, POPULATION_STATE, POPULATION_TYPE, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEM', TIMESTAMP '2014-07-09 16:51:00', 'People who should be included in a registration is open check', 'People who should be included in a registration is open check', 'kuali.population.student.key.regdatecheck', 'RegistrationDateCheck', '589f96e9-605c-4ec1-83c9-29c4984e4d2c', 'kuali.population.rule.key.regdatecheck', 'kuali.population.population.state.active', 'kuali.population.type.student', 'SYSTEM', TIMESTAMP '2014-07-09 16:51:00', 1)
/

insert into KSEN_POPULATION_RULE_PERS (PERSON_ID, POPULATION_RULE_ID) values ('e.sonalik', 'kuali.population.rule.key.regdatecheck')
/
insert into KSEN_POPULATION_RULE_PERS (PERSON_ID, POPULATION_RULE_ID) values ('e.soob', 'kuali.population.rule.key.regdatecheck')
/
insert into KSEN_POPULATION_RULE_PERS (PERSON_ID, POPULATION_RULE_ID) values ('e.stevenj', 'kuali.population.rule.key.regdatecheck')
/
insert into KSEN_POPULATION_RULE_PERS (PERSON_ID, POPULATION_RULE_ID) values ('e.sunilak', 'kuali.population.rule.key.regdatecheck')
/
