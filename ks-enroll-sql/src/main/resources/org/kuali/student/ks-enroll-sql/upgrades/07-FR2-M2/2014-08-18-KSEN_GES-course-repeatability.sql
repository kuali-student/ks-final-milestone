--  KSENROLL-14069: add GES parms and values for course repeatability

-- max repeatable parm
insert into KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME) VALUES ('kuali.ges.max.repeatable', 'Max Repeatable', 'Maximum number of times that a student can repeat a course', 'kuali.ges.parameter.type', 'kuali.ges.parameter.state.active', 'KUALI_DECIMAL', 0, 0, 'admin', TIMESTAMP '2014-08-18 15:39:05')
/

-- max repeatable value is 2 by default
insert into KSEN_GES_VALUE (GES_PARM_ID, GES_VALUE, GES_VALUE_STATE, GES_VALUE_TYPE, GES_VALUE_TYPE_ID, ID, PRIORITY, POPULATION_ID, VER_NBR, CREATEID, CREATETIME) VALUES ('kuali.ges.max.repeatable', '2', 'kuali.ges.value.state.active', 'kuali.ges.value.type', 'KUALI_DECIMAL', 'kuali.max.repeatable.default', 1, 'kuali.population.student.key.everyone', 0, 'admin', TIMESTAMP '2014-08-18 15:39:05')
/