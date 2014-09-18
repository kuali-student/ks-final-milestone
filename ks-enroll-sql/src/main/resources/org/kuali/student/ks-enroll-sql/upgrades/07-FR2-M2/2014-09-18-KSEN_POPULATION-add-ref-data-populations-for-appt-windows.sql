--KSENROLL-14898 added some ref data populations with actual students so we can test appointment windows
insert into KSEN_POPULATION_RULE (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, ID, NAME, OBJ_ID, POPULATION_RULE_STATE, POPULATION_RULE_TYPE, REF_POPULATION_ID, SUPPORTS_GET_MBR_IND, UPDATEID, UPDATETIME, VARIES_BY_TIME_IND, VER_NBR)
        values ('SYSTEM', TIMESTAMP '2014-07-09 12:48:38', 'Static Special Population', 'Static Special Population', 'kuali.population.rule.key.static.special', 'StaticSpecialPopulationRule', '64626a67-c3ff-49dc-9589-10224e5a9812', 'kuali.population.population.rule.state.active', 'kuali.population.rule.type.person', null, 1, 'SYSTEM', TIMESTAMP '2014-07-09 12:48:38', 0, 0)
/
insert into KSEN_POPULATION_RULE (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, ID, NAME, OBJ_ID, POPULATION_RULE_STATE, POPULATION_RULE_TYPE, REF_POPULATION_ID, SUPPORTS_GET_MBR_IND, UPDATEID, UPDATETIME, VARIES_BY_TIME_IND, VER_NBR)
        values ('SYSTEM', TIMESTAMP '2014-07-09 12:48:38', 'Static Freshmen/Sophomore Population', 'Static Freshmen/Sophomore Population', 'kuali.population.rule.key.static.sofresh', 'StaticFreshmen/SophomorePopulationRule', '64626a67-c3ff-49dc-9589-10224e5a9813', 'kuali.population.population.rule.state.active', 'kuali.population.rule.type.person', null, 1, 'SYSTEM', TIMESTAMP '2014-07-09 12:48:38', 0, 0)
/
insert into KSEN_POPULATION_RULE (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, ID, NAME, OBJ_ID, POPULATION_RULE_STATE, POPULATION_RULE_TYPE, REF_POPULATION_ID, SUPPORTS_GET_MBR_IND, UPDATEID, UPDATETIME, VARIES_BY_TIME_IND, VER_NBR)
        values ('SYSTEM', TIMESTAMP '2014-07-09 12:48:38', 'Static Junior/Senior Population', 'Static Junior/Senior Population', 'kuali.population.rule.key.static.juniorsenior', 'StaticJunior/SeniorPopulationRule', '64626a67-c3ff-49dc-9589-10224e5a9814', 'kuali.population.population.rule.state.active', 'kuali.population.rule.type.person', null, 1, 'SYSTEM', TIMESTAMP '2014-07-09 12:48:38', 0, 0)
/

insert into KSEN_POPULATION (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, ID, NAME, OBJ_ID, POPULATION_RULE_ID, POPULATION_STATE, POPULATION_TYPE, UPDATEID, UPDATETIME, VER_NBR)
        values ('SYSTEM', TIMESTAMP '2014-07-09 12:51:00', 'Static Special Population', 'Static Special Population', 'kuali.population.student.key.static.special', 'Static Special Population', '589f96e9-605c-4ec1-83c9-29c4984e5d2c', 'kuali.population.rule.key.static.special', 'kuali.population.population.state.active', 'kuali.population.type.student', 'SYSTEM', TIMESTAMP '2014-07-09 12:51:00', 1)
/
insert into KSEN_POPULATION (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, ID, NAME, OBJ_ID, POPULATION_RULE_ID, POPULATION_STATE, POPULATION_TYPE, UPDATEID, UPDATETIME, VER_NBR)
        values ('SYSTEM', TIMESTAMP '2014-07-09 12:51:00', 'Static Freshmen/Sophomore Population', 'Static Freshmen/Sophomore Population', 'kuali.population.student.key.static.sofresh', 'Static Freshmen/Sophomore Population', '589f96e9-605c-4ec1-83c9-29c4984e6d2c', 'kuali.population.rule.key.static.sofresh', 'kuali.population.population.state.active', 'kuali.population.type.student', 'SYSTEM', TIMESTAMP '2014-07-09 12:51:00', 1)
/
insert into KSEN_POPULATION (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, ID, NAME, OBJ_ID, POPULATION_RULE_ID, POPULATION_STATE, POPULATION_TYPE, UPDATEID, UPDATETIME, VER_NBR)
        values ('SYSTEM', TIMESTAMP '2014-07-09 12:51:00', 'Static Junior/Senior Population', 'Static Junior/Senior Population', 'kuali.population.student.key.static.juniorsenior', 'Static Junior/Senior Population', '589f96e9-605c-4ec1-83c9-29c4984e7d2c', 'kuali.population.rule.key.static.juniorsenior', 'kuali.population.population.state.active', 'kuali.population.type.student', 'SYSTEM', TIMESTAMP '2014-07-09 12:51:00', 1)
/

insert into KSEN_POPULATION_RULE_PERS (PERSON_ID, POPULATION_RULE_ID)
        select ea.ENTITY_ID, 'kuali.population.rule.key.static.special' from KRIM_ENTITY_AFLTN_T ea, KRIM_PRNCPL_T p where ea.AFLTN_TYP_CD='STDNT' and p.ENTITY_ID = ea.ENTITY_ID and p.PRNCPL_ID LIKE 'S.%'
/

insert into KSEN_POPULATION_RULE_PERS (PERSON_ID, POPULATION_RULE_ID)
        select ea.ENTITY_ID, 'kuali.population.rule.key.static.sofresh' from KRIM_ENTITY_AFLTN_T ea, KRIM_PRNCPL_T p where ea.AFLTN_TYP_CD='STDNT' and p.ENTITY_ID = ea.ENTITY_ID and p.PRNCPL_ID LIKE 'M.%'
/

insert into KSEN_POPULATION_RULE_PERS (PERSON_ID, POPULATION_RULE_ID)
        select ea.ENTITY_ID, 'kuali.population.rule.key.static.juniorsenior' from KRIM_ENTITY_AFLTN_T ea, KRIM_PRNCPL_T p where ea.AFLTN_TYP_CD='STDNT' and p.ENTITY_ID = ea.ENTITY_ID and p.PRNCPL_ID LIKE 'C.%'
/
