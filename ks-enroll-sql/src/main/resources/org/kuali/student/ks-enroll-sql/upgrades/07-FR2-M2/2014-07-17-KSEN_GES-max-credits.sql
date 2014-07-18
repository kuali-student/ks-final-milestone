--  KSENROLL-13602: add GES parms and values for max credit checks

-- credit limit parm
insert into KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME) VALUES ('kuali.ges.credit.limit', 'Credit Limit', 'Maximum number of credits that a student can register for in a given term', 'kuali.ges.parameter.type', 'kuali.ges.parameter.state.active', 'KUALI_DECIMAL', 0, 0, 'admin', TIMESTAMP '2014-07-17 10:57:03')
/
-- load calculation parm
insert into KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME) VALUES ('kuali.ges.load.calculation.for.credit.checks', 'Load Calculation for Credit Checks', 'The Load Calculation to use for credit limit checks', 'kuali.ges.parameter.type', 'kuali.ges.parameter.state.active', 'STRING', 0, 0, 'admin', TIMESTAMP '2014-07-17 10:57:03')
/

-- Max credits for Summer 1 atp type is 8
insert into KSEN_GES_VALUE (GES_PARM_ID, GES_VALUE, GES_VALUE_STATE, GES_VALUE_TYPE, GES_VALUE_TYPE_ID, ID, PRIORITY, POPULATION_ID, VER_NBR, CREATEID, CREATETIME) VALUES ('kuali.ges.credit.limit', '8', 'kuali.ges.value.state.active', 'kuali.ges.value.type', 'KUALI_DECIMAL', 'kuali.Summer1.max.credits', 1, 'kuali.population.student.key.everyone', 0, 'admin', TIMESTAMP '2014-07-17 10:57:03')
/
insert into KSEN_GES_VALUE_ATP_TYPE (ATP_TYPE, VALUE_ID) VALUES ('kuali.atp.type.Summer1', 'kuali.Summer1.max.credits')
/

-- Max credits for fall and spring atp types is 20
insert into KSEN_GES_VALUE (GES_PARM_ID, GES_VALUE, GES_VALUE_STATE, GES_VALUE_TYPE, GES_VALUE_TYPE_ID, ID, PRIORITY, POPULATION_ID, VER_NBR, CREATEID, CREATETIME) VALUES ('kuali.ges.credit.limit', '20', 'kuali.ges.value.state.active', 'kuali.ges.value.type', 'KUALI_DECIMAL', 'kuali.fall.spring.max.credits', 2, 'kuali.population.student.key.everyone', 0, 'admin', TIMESTAMP '2014-07-17 10:57:03')
/
insert into KSEN_GES_VALUE_ATP_TYPE (ATP_TYPE, VALUE_ID) VALUES ('kuali.atp.type.Fall', 'kuali.fall.spring.max.credits')
/
insert into KSEN_GES_VALUE_ATP_TYPE (ATP_TYPE, VALUE_ID) VALUES ('kuali.atp.type.Spring', 'kuali.fall.spring.max.credits')
/

-- load calculation value
insert into KSEN_GES_VALUE (GES_PARM_ID, GES_VALUE, GES_VALUE_STATE, GES_VALUE_TYPE, GES_VALUE_TYPE_ID, ID, PRIORITY, POPULATION_ID, VER_NBR, CREATEID, CREATETIME) VALUES ('kuali.ges.load.calculation.for.credit.checks', 'kuali.academic.record.calculation.type.load.credit.decimal', 'kuali.ges.value.state.active', 'kuali.ges.value.type', 'KUALI_DECIMAL', 'kuali.credit.load.calc', 1, 'kuali.population.student.key.everyone', 0, 'admin', TIMESTAMP '2014-07-17 10:57:03')
/
