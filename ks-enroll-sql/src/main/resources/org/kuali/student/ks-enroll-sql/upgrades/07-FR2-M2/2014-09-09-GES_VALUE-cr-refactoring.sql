-- KSRENROLL-14781 -- refactor credit load and max repeatability for GES service changes

update KSEN_GES_VALUE
  set atp_type_key = 'kuali.atp.type.Summer1',
      obj_id = '02A7ADFE-9107-2C29-E050-007F010130E0'
  where id = 'kuali.Summer1.max.credits'
/

update KSEN_GES_VALUE
  set atp_type_key = 'kuali.atp.type.Fall',
      obj_id = '02A7ADFE-9108-2C29-E050-007F010130E0',
      id = 'kuali.fall.max.credits'
  where id = 'kuali.fall.spring.max.credits'
/

insert into KSEN_GES_VALUE (GES_PARM_ID, GES_VALUE, GES_VALUE_STATE, GES_VALUE_TYPE, GES_VALUE_TYPE_ID, ID, PRIORITY,
                            POPULATION_ID, VER_NBR, ATP_TYPE_KEY, OBJ_ID, CREATEID, CREATETIME)
  VALUES ('kuali.ges.credit.limit', '20', 'kuali.ges.value.state.active', 'kuali.ges.value.type', 'KUALI_DECIMAL',
          'kuali.spring.max.credits', 2, 'kuali.population.student.key.everyone', 0, 'kuali.atp.type.Spring',
          '02A7ADFE-910B-2C29-E050-007F010130E0', 'admin', TO_DATE('2014-09-09', 'YYYY-MM-DD'))
/