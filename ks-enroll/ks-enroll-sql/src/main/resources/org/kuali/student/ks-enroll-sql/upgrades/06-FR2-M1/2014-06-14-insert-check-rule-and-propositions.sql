--Check Rule and proposition types.
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.type.check.name','KS-SYS','ruleTypeService','kuali.krms.type.check',0)
/

INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.custom.name','KS-SYS','customPropositionTypeService','kuali.krms.type.custom',0)
/

INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.requisites.name','KS-SYS','customPropositionTypeService','kuali.krms.type.requisites',0)
/

-- Is alive check rule and proposition: "kuali.rule.is.alive"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.is.alive','KS-SYS',null,'kuali.rule.is.alive','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check if person is alive.','S','kuali.prop.is.alive','kuali.rule.is.alive','kuali.krms.type.custom',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.is.alive' where RULE_ID like 'kuali.rule.is.alive'
/

-- Credit Load check rule and proposition: "kuali.rule.credit.load"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.credit.load','KS-SYS',null,'kuali.rule.credit.load','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check credit load.','S','kuali.prop.credit.load','kuali.rule.credit.load','kuali.krms.type.custom',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.credit.load' where RULE_ID like 'kuali.rule.credit.load'
/

-- Time Conflict Best Effort check rule and proposition: "kuali.rule.best.effort.time.conflict"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.best.effort.time.conflict','KS-SYS',null,'kuali.rule.best.effort.time.conflict','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check time conflict best effort.','S','kuali.prop.best.effort.time.conflict','kuali.rule.best.effort.time.conflict','kuali.krms.type.custom',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.best.effort.time.conflict' where RULE_ID like 'kuali.rule.best.effort.time.conflict'
/

-- Credit Limit check rule and proposition: "kuali.rule.credit.limit"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.credit.limit','KS-SYS',null,'kuali.rule.credit.limit','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check credit limit.','S','kuali.prop.credit.limit','kuali.rule.credit.limit','kuali.krms.type.custom',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.credit.limit' where RULE_ID like 'kuali.rule.credit.limit'
/

-- Minimum credit check rule and proposition: "kuali.rule.credit.minimum"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.credit.minimum','KS-SYS',null,'kuali.rule.credit.minimum','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check minimum credit.','S','kuali.prop.credit.minimum','kuali.rule.credit.minimum','kuali.krms.type.custom',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.credit.minimum' where RULE_ID like 'kuali.rule.credit.minimum'
/

-- Credit Load Best Effort check rule and proposition: "kuali.rule.best.effort.credit.load"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.best.effort.credit.load','KS-SYS',null,'kuali.rule.best.effort.credit.load','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check credit load best effort','S','kuali.prop.best.effort.credit.load','kuali.rule.best.effort.credit.load','kuali.krms.type.custom',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.best.effort.credit.load' where RULE_ID like 'kuali.rule.best.effort.credit.load'
/

-- Course Requisites check rule and proposition: "kuali.rule.course.requisites"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.course.requisites','KS-SYS',null,'kuali.rule.course.requisites','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check course requisites','S','kuali.prop.course.requisites','kuali.rule.course.requisites','kuali.krms.type.custom',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.course.requisites' where RULE_ID like 'kuali.rule.course.requisites'
/