--KSENROLL-12748
--create duplicate orgs with new orgid's
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  select 'ORGID-'||ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE
  from KSOR_ORG where ID NOT LIKE 'ORGID-%'
/
--update relation tables
update ksor_org_hirchy set root_org = 'ORGID-'||root_org where root_org NOT LIKE 'ORGID-%'
/
update KSOR_ORG_ORG_RELTN set org = 'ORGID-'||org where org NOT LIKE 'ORGID-%'
/
update KSOR_ORG_ORG_RELTN set related_org = 'ORGID-'||related_org where related_org NOT LIKE 'ORGID-%'
/
update KSOR_ORG_PERS_RELTN set org = 'ORGID-'||org where org NOT LIKE 'ORGID-%'
/
update KSOR_ORG_JN_ORG_PERS_REL_TYPE set org_id = 'ORGID-'||org_id where org_id NOT LIKE 'ORGID-%'
/
update KSOR_ORG_POS_RESTR set org = 'ORGID-'||org where org NOT LIKE 'ORGID-%'
/
--now delete orgs with old orgid's
delete KSOR_ORG where ID NOT LIKE 'ORGID-%'
/
--fix currupt insert value
update KRMS_TERM_PARM_T set val = '1273172872' where val = 'KS-KRMS-TERM-1273172872'
/
--now update orgid's
update KRMS_TERM_PARM_T set val = 'ORGID-'||val where nm = 'kuali.term.parameter.type.org.id' and val NOT LIKE 'ORGID-%'
/
update KSLU_CLU_ADMIN_ORG set org_id = 'ORGID-'||org_id where org_id NOT LIKE 'ORGID-%'
/
update KSEN_LUI_UNITS_CONT_OWNER set org_id = 'ORGID-'||org_id where org_id NOT LIKE 'ORGID-%'
/
update KSEN_LUI_UNITS_DEPLOYMENT set org_id = 'ORGID-'||org_id where org_id NOT LIKE 'ORGID-%'
/
update KSSC_SUBJ_CD_JN_ORG set org_id = 'ORGID-'||org_id where org_id NOT LIKE 'ORGID-%'
/