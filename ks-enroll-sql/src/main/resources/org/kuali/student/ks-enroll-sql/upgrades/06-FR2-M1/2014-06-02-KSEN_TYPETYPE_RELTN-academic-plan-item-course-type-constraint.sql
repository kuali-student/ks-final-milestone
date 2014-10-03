-- (KSAP-1212) Setup Item Object Reference types allowed (for an Item on an Academic Plan)
INSERT INTO KSEN_TYPETYPE_RELTN
(ID, OBJ_ID,
  TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT,
  OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
  VALUES ('kuali.type.type.relation.type.allowed.kuali.academicplan.type.planitem.kuali.lu.type.CreditCourse', null,
  'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null,
  'kuali.academicplan.type.planitem', 'kuali.lu.type.CreditCourse', 1, 0, TIMESTAMP '2013-08-09 00:00:00', 'SYSTEMLOADER', null, null)
/
