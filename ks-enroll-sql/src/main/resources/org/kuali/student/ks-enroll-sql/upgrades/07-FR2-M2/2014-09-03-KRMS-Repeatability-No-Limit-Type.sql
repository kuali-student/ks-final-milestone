-- KSENROLL-14515 add a new proposition type for repeatability to simply say "no repeatability rule"
insert into KRMS_TYP_T (ACTV, NM, NMSPC_CD, SRVC_NM, TYP_ID, VER_NBR)
  values ('Y', 'kuali.krms.proposition.type.repeatability.no.limit', 'KS-SYS', 'simplePropositionTypeService', 'kuali.krms.prop.type.repeat.no.limit', 0)
/
insert into KRMS_TYP_RELN_T (ACTV, FROM_TYP_ID, RELN_TYP, SEQ_NO, TO_TYP_ID, TYP_RELN_ID, VER_NBR)
  values ('Y', '10011', 'A', 1, 'kuali.krms.prop.type.repeat.no.limit', 'KS-KRMS-TYP-RELN-55767', 0)
/

insert into KRMS_NL_TMPL_T (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID, VER_NBR)
  values ('en', 'KS-KRMS-NL-TMPL-55765', 'KS-KRMS-NL-USAGE-1001', 'No Repeatable Limit', 'kuali.krms.prop.type.repeat.no.limit', 0)
/
insert into KRMS_NL_TMPL_T (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID, VER_NBR)
  values ('en', 'KS-KRMS-NL-TMPL-55766', 'KS-KRMS-NL-USAGE-1003', 'No Repeatable Limit', 'kuali.krms.prop.type.repeat.no.limit', 0)
/
insert into KRMS_NL_TMPL_T (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID, VER_NBR)
  values ('en', 'KS-KRMS-NL-TMPL-55767', 'KS-KRMS-NL-USAGE-1000', 'No Repeatable Limit', 'kuali.krms.prop.type.repeat.no.limit', 0)
/
insert into KRMS_NL_TMPL_T (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID, VER_NBR)
  values ('en', 'KS-KRMS-NL-TMPL-55768', 'KS-KRMS-NL-USAGE-1005', 'No Repeatable Limit', 'kuali.krms.prop.type.repeat.no.limit', 0)
/
insert into KRMS_NL_TMPL_T (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID, VER_NBR)
  values ('en', 'KS-KRMS-NL-TMPL-55769', 'KS-KRMS-NL-USAGE-1002', 'No Repeatable Limit', 'kuali.krms.prop.type.repeat.no.limit', 0)
/
insert into KRMS_NL_TMPL_T (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID, VER_NBR)
  values ('en', 'KS-KRMS-NL-TMPL-55770', 'KS-KRMS-NL-USAGE-1004', 'No Repeatable Limit', 'kuali.krms.prop.type.repeat.no.limit', 0)
/

insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  values ('Y', 'No Limit On Course Repeatability', 'NoLimitOnCourseRepeatability', 'KS-SYS', 'KS-KRMS-TERM-SPEC-55768', 'java.lang.Boolean', 1)
/

insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  values ('Y', 'NoLimitOnCourseRepeatability', 'KS-SYS', 'KS-KRMS-TERM-SPEC-55768', 'KS-KRMS-TERM-RSLVR-55769', 'KS-KRMS-TYP-55758', 0)
/