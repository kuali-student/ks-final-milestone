DECLARE
   var_offic_clu_id varchar2(36);
   var_expDate timestamp(6);
   var_curVerEndDate timestamp(6);
BEGIN

  select TIMESTAMP '2014-06-01 00:00:00.000' into var_expDate from dual;
  select TIMESTAMP '2014-02-01 00:00:00.000' into var_curVerEndDate from dual;

  select ID into var_offic_clu_id from KSLU_CLU_IDENT where CD = 'WMST628';

  -- Supersede a version of WMST628
  UPDATE KSLU_CLU_IDENT set ST = 'Superseded', VER_NBR = VER_NBR+1 where id = var_offic_clu_id;

  UPDATE KSLU_CLU set ST = 'Superseded', CURR_VER_END=var_curVerEndDate, EXPIR_DT=var_expDate, LAST_ATP='kuali.atp.2014Spring', VER_NBR = VER_NBR+1
    WHERE OFFIC_CLU_ID = var_offic_clu_id;

END;
/

-- Create a new version of WMST628
DECLARE
  var_clu_id1 varchar2(36);
  var_clu_id2 varchar2(36);
  var_clu_id3 varchar2(36);
  var_acct_id varchar2(36);
  var_fee_id varchar2(36);
  var_rslt_opt_id1 varchar2(36);
  var_rslt_opt_id2 varchar2(36);
  var_rslt_id1 varchar2(36);
  var_rslt_id2 varchar2(36);
  var_effDate timestamp(6);
  var_curVerStDate timestamp(6);
  var_updateDate timestamp(6);
  var_descr_id1 varchar2(36);
  var_descr_id2 varchar2(36);
  var_descr_id3 varchar2(36);
  var_clu_ident_id varchar2(36);
  var_verIndId_for_clu varchar2(36):='2e0b2d60-12e0-48ca-abfa-eb0e41f88c77';
  var_newVerIndId_for_clu2 varchar2(36):='c41bc98c-f032-4b66-b01e-d8991a3dbe75';
  var_newVerIndId_for_clu3 varchar2(36):='5269eb53-c598-45ec-aecc-1864a1c7d236';

BEGIN

select CONCAT('KS-CLU-', 'refdata-0') into var_clu_id1 from dual;
select CONCAT('KS-CLU-', 'refdata-1') into var_clu_id2 from dual;
select CONCAT('KS-CLU-', 'refdata-2') into var_clu_id3 from dual;
select CONCAT('KS-CLU-ACCT-', 'refdata-3') into var_acct_id from dual;
select CONCAT('KS-CLU-FEE-', 'refdata-4') into var_fee_id from dual;
select CONCAT('KS-CLU-RSLT-', 'refdata-5') into var_rslt_id1 from dual;
select CONCAT('KS-CLU-RSLT-', 'refdata-6') into var_rslt_id2 from dual;
select CONCAT('KS-RSLT-OPT-', 'refdata-7') into var_rslt_opt_id1 from dual;
select CONCAT('KS-RSLT-OPT-', 'refdata-8') into var_rslt_opt_id2 from dual;

select TIMESTAMP '2014-06-02 00:00:00.000' into var_effDate from dual;
select TIMESTAMP '2014-02-01 00:00:00.000' into var_curVerStDate from dual;
select TIMESTAMP '2014-02-01 00:00:00.000' into var_updateDate from dual;

select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-9') into var_descr_id1 from dual;
select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-10') into var_descr_id2 from dual;
select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-11') into var_descr_id3 from dual;
select CONCAT('KS-CLU-IDENT-', 'refdata-12') into var_clu_ident_id from dual;


INSERT INTO KSLU_CLU_IDENT (CD,DIVISION,ID,LNG_NAME,LVL,OBJ_ID,SHRT_NAME,ST,SUFX_CD,TYPE,VER_NBR)
  VALUES ('WMST628','WMST',var_clu_ident_id,'Test New Version','600',sys_guid(),'Test New Version','Active','628','kuali.lu.type.CreditCourse.identifier.official',0);

INSERT INTO KSLU_CLU_FEE (CREATEID,CREATETIME,ID,OBJ_ID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_fee_id,sys_guid(),var_updateDate,0);

INSERT INTO KSLU_CLU_ACCT (ID,OBJ_ID,VER_NBR)
  VALUES (var_acct_id,sys_guid(),0);

INSERT INTO KSLU_RICH_TEXT_T (FORMATTED,ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES ('Test New Version',var_descr_id1,sys_guid(),'Test New Version',0);

INSERT INTO KSLU_RICH_TEXT_T (ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES (var_descr_id2,sys_guid(),'Grading option',0);

INSERT INTO KSLU_RICH_TEXT_T (ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES (var_descr_id3,sys_guid(),'Grading options',0);

INSERT INTO KSLU_CLU (ACCT_ID,CAN_CREATE_LUI,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,EFF_DT,EXP_FIRST_ATP,FEE_ID,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,OFFIC_CLU_ID,RT_DESCR_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (var_acct_id,0,'admin',var_updateDate,var_curVerStDate,0,0,var_effDate,'kuali.atp.2014Summer1',var_fee_id,0,var_clu_id1,0,0,'kuali.lu.type.CreditCourse',sys_guid(),var_clu_ident_id,var_descr_id1,2,'Active',var_updateDate,var_verIndId_for_clu,0);

INSERT INTO KSLU_CLU (CAN_CREATE_LUI,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (0,'admin',var_updateDate,var_curVerStDate,0,0,0,var_clu_id2,0,0,'kuali.lu.type.CreditCourseFormatShell',sys_guid(),1,'Active',var_updateDate,var_newVerIndId_for_clu2,0);

INSERT INTO KSLU_CLU (CAN_CREATE_LUI,CLU_INTSTY_QTY,CLU_INTSTY_TYPE,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (0,'3','kuali.atp.duration.Week','admin',var_updateDate,var_curVerStDate,0,0,0,var_clu_id3,0,0,'kuali.lu.type.activity.Lecture',sys_guid(),1,'Active',var_updateDate,var_newVerIndId_for_clu3,0);

INSERT INTO KSLU_CLUCLU_RELTN (CLU_ID,CLU_RELTN_REQ,CREATEID,CREATETIME,ID,LU_RELTN_TYPE_ID,OBJ_ID,RELATED_CLU_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,1,'admin',var_updateDate,CONCAT('KS-CLUCLU-RELTN-', 'refdata-13'),'luLuRelationType.hasCourseFormat',sys_guid(),var_clu_id2,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLUCLU_RELTN (CLU_ID,CLU_RELTN_REQ,CREATEID,CREATETIME,ID,LU_RELTN_TYPE_ID,OBJ_ID,RELATED_CLU_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id2,1,'admin',var_updateDate,CONCAT('KS-CLUCLU-RELTN-', 'refdata-14'),'luLuRelationType.contains',sys_guid(),var_clu_id3,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLU_RSLT (CLU_ID,CREATEID,CREATETIME,ID,OBJ_ID,ST,TYPE_KEY_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,'admin',var_updateDate,var_rslt_id2,sys_guid(),'Active','kuali.resultType.creditCourseResult','admin',var_updateDate,0);

INSERT INTO KSLU_CLU_RSLT (CLU_ID,CREATEID,CREATETIME,EFF_DT,ID,OBJ_ID,RT_DESCR_ID,ST,TYPE_KEY_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,'admin',var_updateDate,var_effDate,var_rslt_id1,sys_guid(),var_descr_id3,'Active','kuali.resultType.gradeCourseResult','admin',var_updateDate,0);

INSERT INTO KSLU_RSLT_OPT (CREATEID,CREATETIME,ID,OBJ_ID,RES_COMP_ID,RT_DESCR_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_rslt_opt_id1,sys_guid(),'kuali.resultComponent.grade.satisfactory',var_descr_id2,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_RSLT_OPT (CREATEID,CREATETIME,ID,OBJ_ID,RES_COMP_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_rslt_opt_id2,sys_guid(),'kuali.creditType.credit.degree.3.0','Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLURES_JN_RESOPT (CLU_RES_ID,RES_OPT_ID)
  VALUES (var_rslt_id2,var_rslt_opt_id2);

INSERT INTO KSLU_CLURES_JN_RESOPT (CLU_RES_ID,RES_OPT_ID)
  VALUES (var_rslt_id1,var_rslt_opt_id1);

INSERT INTO KSLU_CLU_ADMIN_ORG (CLU_ID,ID,IS_PR,OBJ_ID,ORG_ID,TYPE,VER_NBR)
  VALUES (var_clu_id1,CONCAT('KS-CLU-ADMIN_ORG-', 'refdata-15'),0,sys_guid(),'ORGID-4014660630','kuali.adminOrg.type.CurriculumOversight',0);

INSERT INTO KSLU_CLU_ATP_TYPE_KEY (ATP_TYPE_KEY,CLU_ID,ID,OBJ_ID,VER_NBR)
  VALUES ('kuali.atp.season.Any',var_clu_id1,CONCAT('KS-CLU-ATP-TYPE-KEY-', 'refdata-16'),sys_guid(),0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('audit','false',CONCAT('KS-CLU-ATTR-', 'refdata-17'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('repeatableNumCredits','12',CONCAT('KS-CLU-ATTR-', 'refdata-18'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('passFail','false',CONCAT('KS-CLU-ATTR-', 'refdata-19'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('semesterType','semesterType.standard',CONCAT('KS-CLU-ATTR-', 'refdata-20'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('repeatableIfContentDiffers','true',CONCAT('KS-CLU-ATTR-', 'refdata-21'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTypeExplanation','.',CONCAT('KS-CLU-ATTR-', 'refdata-22'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTotalContactHours','15',CONCAT('KS-CLU-ATTR-', 'refdata-23'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('finalExamStatus','STD',CONCAT('KS-CLU-ATTR-', 'refdata-24'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTotalCredits','1',CONCAT('KS-CLU-ATTR-', 'refdata-25'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_JN_CAMP_LOC (CAMP_LOC,CLU_ID,ID,OBJ_ID,VER_NBR)
  VALUES ('NO',var_clu_id1,CONCAT('KS-CLU-JN-CAMP-LOC-', 'refdata-26'),sys_guid(),0);

end;
/

-- Supersede WMST621
DECLARE
   var_offic_clu_id varchar2(36);
   var_expDate timestamp(6);
   var_curVerEndDate timestamp(6);
BEGIN

  select TIMESTAMP '2014-06-01 00:00:00.000' into var_expDate from dual;
  select TIMESTAMP '2014-02-01 00:00:00.000' into var_curVerEndDate from dual;

  select ID into var_offic_clu_id from KSLU_CLU_IDENT where CD = 'WMST621' and ST = 'Active';

  -- Supersede a version of WMST621
  UPDATE KSLU_CLU_IDENT set ST = 'Superseded', VER_NBR = VER_NBR+1 where id = var_offic_clu_id;

  UPDATE KSLU_CLU set ST = 'Superseded', CURR_VER_END=var_curVerEndDate, EXPIR_DT=var_expDate, LAST_ATP='kuali.atp.2014Spring', VER_NBR = VER_NBR+1
    WHERE OFFIC_CLU_ID = var_offic_clu_id;

END;
/

-- Temp table to hold some old --> new id mappings
CREATE TABLE TEMP_MAP_T
(
      ID_TYPE varchar2(20) not null,
      OLD_ID VARCHAR2(40) not null,
      NEW_ID VARCHAR2(40) not null
)
/

-- Create a new version of WMST621
DECLARE
  var_clu_id1 varchar2(36);
  var_clu_id2 varchar2(36);
  var_clu_id3 varchar2(36);
  var_acct_id varchar2(36);
  var_fee_id varchar2(36);
  var_rslt_opt_id1 varchar2(36);
  var_rslt_opt_id2 varchar2(36);
  var_rslt_id1 varchar2(36);
  var_rslt_id2 varchar2(36);
  var_effDate timestamp(6);
  var_curVerStDate timestamp(6);
  var_updateDate timestamp(6);
  var_descr_id1 varchar2(36);
  var_descr_id2 varchar2(36);
  var_descr_id3 varchar2(36);
  var_clu_ident_id varchar2(36);
  var_verIndId_for_clu varchar2(36):='f7a2bcf6-4d1e-457f-922e-109facc627f7';
  var_oldClu varchar2(36):='fea7fa91-1aa6-4744-9e94-973b463c2622';
  var_newVerIndId_for_clu2 varchar2(36):='1b72a941-664c-4022-8edf-140f0514171a';
  var_newVerIndId_for_clu3 varchar2(36):='dd46f3e4-ce00-4d9c-80e6-91bc83510e23';
  var_oldAgendaId varchar2(36);

BEGIN

select CONCAT('KS-CLU-', 'refdata-27') into var_clu_id1 from dual;
select CONCAT('KS-CLU-', 'refdata-28') into var_clu_id2 from dual;
select CONCAT('KS-CLU-', 'refdata-29') into var_clu_id3 from dual;
select CONCAT('KS-CLU-ACCT-', 'refdata-30') into var_acct_id from dual;
select CONCAT('KS-CLU-FEE-', 'refdata-31') into var_fee_id from dual;
select CONCAT('KS-CLU-RSLT-', 'refdata-32') into var_rslt_id1 from dual;
select CONCAT('KS-CLU-RSLT-', 'refdata-33') into var_rslt_id2 from dual;
select CONCAT('KS-RSLT-OPT-', 'refdata-34') into var_rslt_opt_id1 from dual;
select CONCAT('KS-RSLT-OPT-', 'refdata-35') into var_rslt_opt_id2 from dual;

select TIMESTAMP '2014-06-02 00:00:00.000' into var_effDate from dual;
select TIMESTAMP '2014-02-01 00:00:00.000' into var_curVerStDate from dual;
select TIMESTAMP '2014-02-01 00:00:00.000' into var_updateDate from dual;

select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-36') into var_descr_id1 from dual;
select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-37') into var_descr_id2 from dual;
select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-38') into var_descr_id3 from dual;
select CONCAT('KS-CLU-IDENT-', 'refdata-39') into var_clu_ident_id from dual;


INSERT INTO KSLU_CLU_IDENT (CD,DIVISION,ID,LNG_NAME,LVL,OBJ_ID,SHRT_NAME,ST,SUFX_CD,TYPE,VER_NBR)
  VALUES ('WMST621','WMST',var_clu_ident_id,'Test New Version','600',sys_guid(),'Test New Version','Active','621','kuali.lu.type.CreditCourse.identifier.official',0);

INSERT INTO KSLU_CLU_FEE (CREATEID,CREATETIME,ID,OBJ_ID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_fee_id,sys_guid(),var_updateDate,0);

INSERT INTO KSLU_CLU_ACCT (ID,OBJ_ID,VER_NBR)
  VALUES (var_acct_id,sys_guid(),0);

INSERT INTO KSLU_RICH_TEXT_T (FORMATTED,ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES ('Test New Version',var_descr_id1,sys_guid(),'Test New Version',0);

INSERT INTO KSLU_RICH_TEXT_T (ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES (var_descr_id2,sys_guid(),'Grading option',0);

INSERT INTO KSLU_RICH_TEXT_T (ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES (var_descr_id3,sys_guid(),'Grading options',0);

INSERT INTO KSLU_CLU (ACCT_ID,CAN_CREATE_LUI,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,EFF_DT,EXP_FIRST_ATP,FEE_ID,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,OFFIC_CLU_ID,RT_DESCR_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (var_acct_id,0,'admin',var_updateDate,var_curVerStDate,0,0,var_effDate,'kuali.atp.2014Summer1',var_fee_id,0,var_clu_id1,0,0,'kuali.lu.type.CreditCourse',sys_guid(),var_clu_ident_id,var_descr_id1,3,'Active',var_updateDate,var_verIndId_for_clu,0);

INSERT INTO KSLU_CLU (CAN_CREATE_LUI,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (0,'admin',var_updateDate,var_curVerStDate,0,0,0,var_clu_id2,0,0,'kuali.lu.type.CreditCourseFormatShell',sys_guid(),1,'Active',var_updateDate,var_newVerIndId_for_clu2,0);

INSERT INTO KSLU_CLU (CAN_CREATE_LUI,CLU_INTSTY_QTY,CLU_INTSTY_TYPE,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (0,'3','kuali.atp.duration.Week','admin',var_updateDate,var_curVerStDate,0,0,0,var_clu_id3,0,0,'kuali.lu.type.activity.Lecture',sys_guid(),1,'Active',var_updateDate,var_newVerIndId_for_clu3,0);

INSERT INTO KSLU_CLUCLU_RELTN (CLU_ID,CLU_RELTN_REQ,CREATEID,CREATETIME,ID,LU_RELTN_TYPE_ID,OBJ_ID,RELATED_CLU_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,1,'admin',var_updateDate,CONCAT('KS-CLUCLU-RELTN-', 'refdata-40'),'luLuRelationType.hasCourseFormat',sys_guid(),var_clu_id2,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLUCLU_RELTN (CLU_ID,CLU_RELTN_REQ,CREATEID,CREATETIME,ID,LU_RELTN_TYPE_ID,OBJ_ID,RELATED_CLU_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id2,1,'admin',var_updateDate,CONCAT('KS-CLUCLU-RELTN-', 'refdata-41'),'luLuRelationType.contains',sys_guid(),var_clu_id3,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLU_RSLT (CLU_ID,CREATEID,CREATETIME,ID,OBJ_ID,ST,TYPE_KEY_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,'admin',var_updateDate,var_rslt_id2,sys_guid(),'Active','kuali.resultType.creditCourseResult','admin',var_updateDate,0);

INSERT INTO KSLU_CLU_RSLT (CLU_ID,CREATEID,CREATETIME,EFF_DT,ID,OBJ_ID,RT_DESCR_ID,ST,TYPE_KEY_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,'admin',var_updateDate,var_effDate,var_rslt_id1,sys_guid(),var_descr_id3,'Active','kuali.resultType.gradeCourseResult','admin',var_updateDate,0);

INSERT INTO KSLU_RSLT_OPT (CREATEID,CREATETIME,ID,OBJ_ID,RES_COMP_ID,RT_DESCR_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_rslt_opt_id1,sys_guid(),'kuali.resultComponent.grade.satisfactory',var_descr_id2,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_RSLT_OPT (CREATEID,CREATETIME,ID,OBJ_ID,RES_COMP_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_rslt_opt_id2,sys_guid(),'kuali.creditType.credit.degree.3.0','Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLURES_JN_RESOPT (CLU_RES_ID,RES_OPT_ID)
  VALUES (var_rslt_id2,var_rslt_opt_id2);

INSERT INTO KSLU_CLURES_JN_RESOPT (CLU_RES_ID,RES_OPT_ID)
  VALUES (var_rslt_id1,var_rslt_opt_id1);

INSERT INTO KSLU_CLU_ADMIN_ORG (CLU_ID,ID,IS_PR,OBJ_ID,ORG_ID,TYPE,VER_NBR)
  VALUES (var_clu_id1,CONCAT('KS-CLU-ADMIN-ORG-', 'refdata-42'),0,sys_guid(),'ORGID-4014660630','kuali.adminOrg.type.CurriculumOversight',0);

INSERT INTO KSLU_CLU_ATP_TYPE_KEY (ATP_TYPE_KEY,CLU_ID,ID,OBJ_ID,VER_NBR)
  VALUES ('kuali.atp.season.Any',var_clu_id1,CONCAT('KS-CLU-ATP-TYPE-KEY-', 'refdata-43'),sys_guid(),0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('audit','false',CONCAT('KS-CLU-ATTR-', 'refdata-44'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('repeatableNumCredits','12',CONCAT('KS-CLU-ATTR-', 'refdata-45'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('passFail','false',CONCAT('KS-CLU-ATTR-', 'refdata-46'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('semesterType','semesterType.standard',CONCAT('KS-CLU-ATTR-', 'refdata-47'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('repeatableIfContentDiffers','true',CONCAT('KS-CLU-ATTR-', 'refdata-48'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTypeExplanation','.',CONCAT('KS-CLU-ATTR-', 'refdata-49'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTotalContactHours','15',CONCAT('KS-CLU-ATTR-', 'refdata-50'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('finalExamStatus','STD',CONCAT('KS-CLU-ATTR-', 'refdata-51'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTotalCredits','1',CONCAT('KS-CLU-ATTR-', 'refdata-52'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_JN_CAMP_LOC (CAMP_LOC,CLU_ID,ID,OBJ_ID,VER_NBR)
  VALUES ('NO',var_clu_id1,CONCAT('KS-CLU-JN-CAMP-LOC-', 'refdata-53'),sys_guid(),0);

-- Copy over the requisite info

INSERT INTO TEMP_MAP_T (ID_TYPE, OLD_ID, NEW_ID)
  select 'REF-OBJ-KRMS-OBJ', REF_OBJ_KRMS_OBJ_ID, CONCAT('KS-KRMS-REF-OBJ-KRMS-OBJ-', 'refdata-54')
  from KRMS_REF_OBJ_KRMS_OBJ_T where REF_OBJ_ID = var_oldClu;

INSERT INTO TEMP_MAP_T (ID_TYPE, OLD_ID, NEW_ID)
  select 'AGENDA', KRMS_OBJ_ID, CONCAT('KS-KRMS-AGENDA-', 'refdata-55')
    from KRMS_REF_OBJ_KRMS_OBJ_T where REF_OBJ_ID = var_oldClu;

INSERT INTO TEMP_MAP_T (ID_TYPE, OLD_ID, NEW_ID)
  SELECT 'AGENDA-ITM', AGENDA_ITM_ID, CONCAT('KS-KRMS-AGENDA-ITM-', 'refdata-56')
  FROM KRMS_AGENDA_ITM_T WHERE AGENDA_ID in (SELECT OLD_ID from TEMP_MAP_T where ID_TYPE = 'AGENDA');

INSERT INTO TEMP_MAP_T (ID_TYPE, OLD_ID, NEW_ID)
  SELECT 'RULE', RULE_ID, CONCAT('KS-KRMS-RULE-', 'refdata-57')
  FROM KRMS_AGENDA_ITM_T WHERE AGENDA_ID in (SELECT OLD_ID from TEMP_MAP_T where ID_TYPE = 'AGENDA');

INSERT INTO TEMP_MAP_T (ID_TYPE, OLD_ID, NEW_ID)
  SELECT 'PROP', PROP_ID, CONCAT('KS-KRMS-PROP-', 'refdata-58')
  FROM KRMS_PROP_T WHERE RULE_ID in (SELECT OLD_ID from TEMP_MAP_T where ID_TYPE = 'RULE');

INSERT INTO TEMP_MAP_T (ID_TYPE, OLD_ID, NEW_ID)
  SELECT 'PROP-PARM', PROP_PARM_ID, CONCAT('KS-KRMS-PROP-PARM-', 'refdata-59')
  FROM KRMS_PROP_PARM_T WHERE PROP_ID in (SELECT OLD_ID from TEMP_MAP_T where ID_TYPE = 'PROP');

INSERT INTO TEMP_MAP_T (ID_TYPE, OLD_ID, NEW_ID)
  SELECT 'TERM', PARM_VAL, CONCAT('KS-KRMS-TERM-', 'refdata-60')
  FROM KRMS_PROP_PARM_T WHERE PROP_ID in (SELECT OLD_ID from TEMP_MAP_T where ID_TYPE = 'PROP') and PARM_TYP_CD='T';

INSERT INTO TEMP_MAP_T (ID_TYPE, OLD_ID, NEW_ID)
  SELECT 'TERM-PARM', TERM_PARM_ID, CONCAT('KS-KRMS-TERM-PARM-', 'refdata-61')
  FROM KRMS_TERM_PARM_T WHERE TERM_ID in (SELECT OLD_ID from TEMP_MAP_T where ID_TYPE = 'TERM');


-- actual inserts now
INSERT INTO KRMS_RULE_T(ACTV, DESC_TXT, NM, NMSPC_CD, PROP_ID, RULE_ID, TYP_ID, VER_NBR)
  SELECT ACTV, DESC_TXT, REPLACE(NM, var_oldClu, var_clu_id1), NMSPC_CD, PROP_ID, t.new_id, TYP_ID, 0
  FROM TEMP_MAP_T t, KRMS_RULE_T r WHERE t.ID_TYPE = 'RULE' and t.OLD_ID = r.rule_id;

INSERT INTO KRMS_PROP_T(CMPND_OP_CD, CMPND_SEQ_NO, DESC_TXT, DSCRM_TYP_CD, PROP_ID, RULE_ID, TYP_ID, VER_NBR)
SELECT CMPND_OP_CD, CMPND_SEQ_NO, DESC_TXT, DSCRM_TYP_CD, t.new_id, (select new_id from temp_map_t where old_id = RULE_id), TYP_ID, 0
FROM TEMP_MAP_T t, KRMS_PROP_T p WHERE t.ID_TYPE = 'PROP' and t.OLD_ID = p.prop_id;

-- update the prop_id now that it has been created
UPDATE KRMS_RULE_T set PROP_ID = (select new_id from temp_map_t where old_id = PROP_ID)
where RULE_ID = (select new_id from temp_map_t where new_id = rule_id);

INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
select PARM_TYP_CD, PARM_VAL, (select new_id from temp_map_t where old_id = prop_id), t.new_id, SEQ_NO, 0
FROM TEMP_MAP_T t, KRMS_PROP_PARM_T p WHERE t.ID_TYPE = 'PROP-PARM' and t.OLD_ID = p.prop_parm_id;

INSERT INTO KRMS_TERM_T(DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  SELECT DESC_TXT, t.new_id, TERM_SPEC_ID, 0
  FROM TEMP_MAP_T t, KRMS_TERM_T p WHERE t.ID_TYPE = 'TERM' and t.OLD_ID = p.term_id;

INSERT INTO KRMS_TERM_PARM_T(NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
  SELECT NM, (select new_id from temp_map_t where old_id = term_id), t.new_id, VAL, 0
  FROM TEMP_MAP_T t, KRMS_TERM_PARM_T p WHERE t.ID_TYPE = 'TERM-PARM' and t.OLD_ID = p.term_parm_id;

UPDATE KRMS_PROP_PARM_T set PARM_VAL = (select new_id from temp_map_t where old_id = PARM_VAL)
  WHERE PROP_PARM_ID = (select new_id from temp_map_t where new_id = PROP_PARM_ID) AND PARM_VAL = (select old_id from temp_map_t where old_id = PARM_VAL);

INSERT INTO KRMS_REF_OBJ_KRMS_OBJ_T(ACTV, COLLECTION_NM, KRMS_DSCR_TYP, KRMS_OBJ_ID, NMSPC_CD, REF_DSCR_TYP, REF_OBJ_ID, REF_OBJ_KRMS_OBJ_ID, VER_NBR)
  SELECT ACTV, COLLECTION_NM, KRMS_DSCR_TYP, ag.NEW_ID, NMSPC_CD, REF_DSCR_TYP, var_clu_id1, t.NEW_ID, 0
  from TEMP_MAP_T t, KRMS_REF_OBJ_KRMS_OBJ_T k, TEMP_MAP_T ag where t.ID_TYPE = 'REF-OBJ-KRMS-OBJ' and t.OLD_ID = k.REF_OBJ_KRMS_OBJ_ID and ag.ID_TYPE='AGENDA';

INSERT INTO KRMS_AGENDA_T(ACTV, AGENDA_ID, CNTXT_ID, INIT_AGENDA_ITM_ID, NM, TYP_ID, VER_NBR)
  SELECT ACTV, t.NEW_ID, CNTXT_ID, INIT_AGENDA_ITM_ID, REPLACE(NM, var_oldClu, var_clu_id1), TYP_ID, 0
  from TEMP_MAP_T t, KRMS_AGENDA_T a, TEMP_MAP_T it where t.ID_TYPE = 'AGENDA' and t.OLD_ID = a.AGENDA_ID and it.ID_TYPE='AGENDA-ITM';

INSERT INTO KRMS_AGENDA_ITM_T(AGENDA_ID, AGENDA_ITM_ID, ALWAYS, RULE_ID, SUB_AGENDA_ID, VER_NBR, WHEN_FALSE, WHEN_TRUE)
  SELECT (select new_id from temp_map_t where ID_TYPE = 'AGENDA' and OLD_ID = AGENDA_ID), t.new_id, ALWAYS, (select new_id from temp_map_t where ID_TYPE = 'RULE' and OLD_ID = RULE_ID), SUB_AGENDA_ID, 0, WHEN_FALSE, WHEN_TRUE
  from TEMP_MAP_T t, KRMS_AGENDA_ITM_T a where t.ID_TYPE = 'AGENDA-ITM' and t.OLD_ID = a.AGENDA_ITM_ID;

-- update the init_agenda_itm_id now that it has been created
update KRMS_AGENDA_T set init_agenda_itm_id = (select new_id from temp_map_t where old_id = init_agenda_itm_id)
where agenda_id = (select new_id from temp_map_t where new_id = agenda_id);

end;
/

-- cleanup temp table
DROP TABLE TEMP_MAP_T
/

-- Create a new version of WMST998
DECLARE
  var_clu_id1 varchar2(36);
  var_clu_id2 varchar2(36);
  var_clu_id3 varchar2(36);
  var_acct_id varchar2(36);
  var_fee_id varchar2(36);
  var_rslt_opt_id1 varchar2(36);
  var_rslt_opt_id2 varchar2(36);
  var_rslt_id1 varchar2(36);
  var_rslt_id2 varchar2(36);
  var_effDate timestamp(6);
  var_curVerStDate timestamp(6);
  var_updateDate timestamp(6);
  var_descr_id1 varchar2(36);
  var_descr_id2 varchar2(36);
  var_descr_id3 varchar2(36);
  var_clu_ident_id varchar2(36);
  var_verIndId_for_clu varchar2(36):='3f37f9c3-ba40-4319-8fd1-363a47202bb3';
  var_newVerIndId_for_clu2 varchar2(36):='0e26e9bb-23cf-4e8b-a2d8-b96aa8138847';
  var_newVerIndId_for_clu3 varchar2(36):='e228e00b-696b-43f3-8722-bf303ed16a86';

BEGIN

select CONCAT('KS-CLU-', 'refdata-62') into var_clu_id1 from dual;
select CONCAT('KS-CLU-', 'refdata-63') into var_clu_id2 from dual;
select CONCAT('KS-CLU-', 'refdata-64') into var_clu_id3 from dual;
select CONCAT('KS-CLU-ACCT-','refdata-65') into var_acct_id from dual;
select CONCAT('KS-CLU-FEE-', 'refdata-66') into var_fee_id from dual;
select CONCAT('KS-CLU-RSLT-','refdata-67') into var_rslt_id1 from dual;
select CONCAT('KS-CLU-RSLT-','refdata-68') into var_rslt_id2 from dual;
select CONCAT('KS-RSLT-OPT-','refdata-69') into var_rslt_opt_id1 from dual;
select CONCAT('KS-RSLT-OPT-','refdata-70') into var_rslt_opt_id2 from dual;

select TIMESTAMP '2014-06-02 00:00:00.000' into var_effDate from dual;
select TIMESTAMP '2014-02-01 00:00:00.000' into var_curVerStDate from dual;
select TIMESTAMP '2014-02-01 00:00:00.000' into var_updateDate from dual;

select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-71') into var_descr_id1 from dual;
select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-72') into var_descr_id2 from dual;
select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-73') into var_descr_id3 from dual;
select CONCAT('KS-CLU-IDENT-', 'refdata-74') into var_clu_ident_id from dual;


INSERT INTO KSLU_CLU_IDENT (CD,DIVISION,ID,LNG_NAME,LVL,OBJ_ID,SHRT_NAME,ST,SUFX_CD,TYPE,VER_NBR)
  VALUES ('WMST998','WMST',var_clu_ident_id,'Test New Course','900',sys_guid(),'Test New Course','Active','998','kuali.lu.type.CreditCourse.identifier.official',0);

INSERT INTO KSLU_CLU_FEE (CREATEID,CREATETIME,ID,OBJ_ID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_fee_id,sys_guid(),var_updateDate,0);

INSERT INTO KSLU_CLU_ACCT (ID,OBJ_ID,VER_NBR)
  VALUES (var_acct_id,sys_guid(),0);

INSERT INTO KSLU_RICH_TEXT_T (FORMATTED,ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES ('Test New Course',var_descr_id1,sys_guid(),'Test New Course',0);

INSERT INTO KSLU_RICH_TEXT_T (ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES (var_descr_id2,sys_guid(),'Grading option',0);

INSERT INTO KSLU_RICH_TEXT_T (ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES (var_descr_id3,sys_guid(),'Grading options',0);

INSERT INTO KSLU_CLU (ACCT_ID,CAN_CREATE_LUI,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,EFF_DT,EXP_FIRST_ATP,FEE_ID,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,OFFIC_CLU_ID,RT_DESCR_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (var_acct_id,0,'admin',var_updateDate,var_curVerStDate,0,0,var_effDate,'kuali.atp.2014Summer1',var_fee_id,0,var_clu_id1,0,0,'kuali.lu.type.CreditCourse',sys_guid(),var_clu_ident_id,var_descr_id1,1,'Active',var_updateDate,var_verIndId_for_clu,0);

INSERT INTO KSLU_CLU (CAN_CREATE_LUI,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (0,'admin',var_updateDate,var_curVerStDate,0,0,0,var_clu_id2,0,0,'kuali.lu.type.CreditCourseFormatShell',sys_guid(),1,'Active',var_updateDate,var_newVerIndId_for_clu2,0);

INSERT INTO KSLU_CLU (CAN_CREATE_LUI,CLU_INTSTY_QTY,CLU_INTSTY_TYPE,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (0,'3','kuali.atp.duration.Week','admin',var_updateDate,var_curVerStDate,0,0,0,var_clu_id3,0,0,'kuali.lu.type.activity.Lecture',sys_guid(),1,'Active',var_updateDate,var_newVerIndId_for_clu3,0);

INSERT INTO KSLU_CLUCLU_RELTN (CLU_ID,CLU_RELTN_REQ,CREATEID,CREATETIME,ID,LU_RELTN_TYPE_ID,OBJ_ID,RELATED_CLU_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,1,'admin',var_updateDate,CONCAT('KS-CLUCLU-RELTN-', 'refdata-75'),'luLuRelationType.hasCourseFormat',sys_guid(),var_clu_id2,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLUCLU_RELTN (CLU_ID,CLU_RELTN_REQ,CREATEID,CREATETIME,ID,LU_RELTN_TYPE_ID,OBJ_ID,RELATED_CLU_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id2,1,'admin',var_updateDate,CONCAT('KS-CLUCLU-RELTN-', 'refdata-76'),'luLuRelationType.contains',sys_guid(),var_clu_id3,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLU_RSLT (CLU_ID,CREATEID,CREATETIME,ID,OBJ_ID,ST,TYPE_KEY_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,'admin',var_updateDate,var_rslt_id2,sys_guid(),'Active','kuali.resultType.creditCourseResult','admin',var_updateDate,0);

INSERT INTO KSLU_CLU_RSLT (CLU_ID,CREATEID,CREATETIME,EFF_DT,ID,OBJ_ID,RT_DESCR_ID,ST,TYPE_KEY_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,'admin',var_updateDate,var_effDate,var_rslt_id1,sys_guid(),var_descr_id3,'Active','kuali.resultType.gradeCourseResult','admin',var_updateDate,0);

INSERT INTO KSLU_RSLT_OPT (CREATEID,CREATETIME,ID,OBJ_ID,RES_COMP_ID,RT_DESCR_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_rslt_opt_id1,sys_guid(),'kuali.resultComponent.grade.satisfactory',var_descr_id2,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_RSLT_OPT (CREATEID,CREATETIME,ID,OBJ_ID,RES_COMP_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_rslt_opt_id2,sys_guid(),'kuali.creditType.credit.degree.3.0','Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLURES_JN_RESOPT (CLU_RES_ID,RES_OPT_ID)
  VALUES (var_rslt_id2,var_rslt_opt_id2);

INSERT INTO KSLU_CLURES_JN_RESOPT (CLU_RES_ID,RES_OPT_ID)
  VALUES (var_rslt_id1,var_rslt_opt_id1);

INSERT INTO KSLU_CLU_ADMIN_ORG (CLU_ID,ID,IS_PR,OBJ_ID,ORG_ID,TYPE,VER_NBR)
  VALUES (var_clu_id1,CONCAT('KS-CLU-ADMIN-ORG-', 'refdata-77'),0,sys_guid(),'ORGID-4014660630','kuali.adminOrg.type.CurriculumOversight',0);

INSERT INTO KSLU_CLU_ATP_TYPE_KEY (ATP_TYPE_KEY,CLU_ID,ID,OBJ_ID,VER_NBR)
  VALUES ('kuali.atp.season.Any',var_clu_id1,CONCAT('KS-CLU-ATP-TYPE-KEY-', 'refdata-78'),sys_guid(),0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('audit','false',CONCAT('KS-CLU-ATTR-', 'refdata-79'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('repeatableNumCredits','12',CONCAT('KS-CLU-ATTR-', 'refdata-80'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('passFail','false',CONCAT('KS-CLU-ATTR-', 'refdata-81'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('semesterType','semesterType.standard',CONCAT('KS-CLU-ATTR-', 'refdata-82'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('repeatableIfContentDiffers','true',CONCAT('KS-CLU-ATTR-', 'refdata-83'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTypeExplanation','.',CONCAT('KS-CLU-ATTR-', 'refdata-84'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTotalContactHours','15',CONCAT('KS-CLU-ATTR-', 'refdata-85'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('finalExamStatus','STD',CONCAT('KS-CLU-ATTR-', 'refdata-86'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTotalCredits','1',CONCAT('KS-CLU-ATTR-', 'refdata-87'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_JN_CAMP_LOC (CAMP_LOC,CLU_ID,ID,OBJ_ID,VER_NBR)
  VALUES ('NO',var_clu_id1,CONCAT('KS-CLU-JN-CAMP-LOC-', 'refdata-88'),sys_guid(),0);

end;
/

-- Create a new version of WMST999
DECLARE
  var_clu_id1 varchar2(36);
  var_clu_id2 varchar2(36);
  var_clu_id3 varchar2(36);
  var_acct_id varchar2(36);
  var_fee_id varchar2(36);
  var_rslt_opt_id1 varchar2(36);
  var_rslt_opt_id2 varchar2(36);
  var_rslt_id1 varchar2(36);
  var_rslt_id2 varchar2(36);
  var_effDate timestamp(6);
  var_curVerStDate timestamp(6);
  var_updateDate timestamp(6);
  var_descr_id1 varchar2(36);
  var_descr_id2 varchar2(36);
  var_descr_id3 varchar2(36);
  var_clu_ident_id varchar2(36);
  var_verIndId_for_clu varchar2(36):='94e19d70-38c5-4364-9e18-087c9b3e3361';
  var_newVerIndId_for_clu2 varchar2(36):='a3c0acc7-cd7e-4b94-9532-d921d1cce8fe';
  var_newVerIndId_for_clu3 varchar2(36):='56b449ee-b70e-4ada-9636-82d120c7e1a7';

BEGIN

select CONCAT('KS-CLU-', 'refdata-89') into var_clu_id1 from dual;
select CONCAT('KS-CLU-', 'refdata-90') into var_clu_id2 from dual;
select CONCAT('KS-CLU-', 'refdata-91') into var_clu_id3 from dual;
select CONCAT('KS-CLU-ACCT-','refdata-92') into var_acct_id from dual;
select CONCAT('KS-CLU-FEE-', 'refdata-93') into var_fee_id from dual;
select CONCAT('KS-CLU-RSLT-','refdata-94') into var_rslt_id1 from dual;
select CONCAT('KS-CLU-RSLT-','refdata-95') into var_rslt_id2 from dual;
select CONCAT('KS-RSLT-OPT-','refdata-96') into var_rslt_opt_id1 from dual;
select CONCAT('KS-RSLT-OPT-','refdata-97') into var_rslt_opt_id2 from dual;

select TIMESTAMP '2014-09-02 00:00:00.000' into var_effDate from dual;
select TIMESTAMP '2014-02-01 00:00:00.000' into var_curVerStDate from dual;
select TIMESTAMP '2014-02-01 00:00:00.000' into var_updateDate from dual;

select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-98') into var_descr_id1 from dual;
select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-99') into var_descr_id2 from dual;
select CONCAT('KS-CLU-RICH-TEXT-', 'refdata-100') into var_descr_id3 from dual;
select CONCAT('KS-CLU-IDENT-', 'refdata-101') into var_clu_ident_id from dual;


INSERT INTO KSLU_CLU_IDENT (CD,DIVISION,ID,LNG_NAME,LVL,OBJ_ID,SHRT_NAME,ST,SUFX_CD,TYPE,VER_NBR)
  VALUES ('WMST999','WMST',var_clu_ident_id,'Test New Course','900',sys_guid(),'Test New Course','Active','999','kuali.lu.type.CreditCourse.identifier.official',0);

INSERT INTO KSLU_CLU_FEE (CREATEID,CREATETIME,ID,OBJ_ID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_fee_id,sys_guid(),var_updateDate,0);

INSERT INTO KSLU_CLU_ACCT (ID,OBJ_ID,VER_NBR)
  VALUES (var_acct_id,sys_guid(),0);

INSERT INTO KSLU_RICH_TEXT_T (FORMATTED,ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES ('Test New Course',var_descr_id1,sys_guid(),'Test New Course',0);

INSERT INTO KSLU_RICH_TEXT_T (ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES (var_descr_id2,sys_guid(),'Grading option',0);

INSERT INTO KSLU_RICH_TEXT_T (ID,OBJ_ID,PLAIN,VER_NBR)
  VALUES (var_descr_id3,sys_guid(),'Grading options',0);

INSERT INTO KSLU_CLU (ACCT_ID,CAN_CREATE_LUI,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,EFF_DT,EXP_FIRST_ATP,FEE_ID,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,OFFIC_CLU_ID,RT_DESCR_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (var_acct_id,0,'admin',var_updateDate,var_curVerStDate,0,0,var_effDate,'kuali.atp.2014Fall',var_fee_id,0,var_clu_id1,0,0,'kuali.lu.type.CreditCourse',sys_guid(),var_clu_ident_id,var_descr_id1,1,'Active',var_updateDate,var_verIndId_for_clu,0);

INSERT INTO KSLU_CLU (CAN_CREATE_LUI,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (0,'admin',var_updateDate,var_curVerStDate,0,0,0,var_clu_id2,0,0,'kuali.lu.type.CreditCourseFormatShell',sys_guid(),1,'Active',var_updateDate,var_newVerIndId_for_clu2,0);

INSERT INTO KSLU_CLU (CAN_CREATE_LUI,CLU_INTSTY_QTY,CLU_INTSTY_TYPE,CREATEID,CREATETIME,CURR_VER_START,DEF_ENRL_EST,DEF_MAX_ENRL,HAS_EARLY_DROP_DEDLN,ID,IS_ENRL,IS_HAZR_DISBLD_STU,LUTYPE_ID,OBJ_ID,SEQ_NUM,ST,UPDATETIME,VER_IND_ID,VER_NBR)
  VALUES (0,'3','kuali.atp.duration.Week','admin',var_updateDate,var_curVerStDate,0,0,0,var_clu_id3,0,0,'kuali.lu.type.activity.Lecture',sys_guid(),1,'Active',var_updateDate,var_newVerIndId_for_clu3,0);

INSERT INTO KSLU_CLUCLU_RELTN (CLU_ID,CLU_RELTN_REQ,CREATEID,CREATETIME,ID,LU_RELTN_TYPE_ID,OBJ_ID,RELATED_CLU_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,1,'admin',var_updateDate,CONCAT('KS-CLUCLU-RELTN-', 'refdata-102'),'luLuRelationType.hasCourseFormat',sys_guid(),var_clu_id2,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLUCLU_RELTN (CLU_ID,CLU_RELTN_REQ,CREATEID,CREATETIME,ID,LU_RELTN_TYPE_ID,OBJ_ID,RELATED_CLU_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id2,1,'admin',var_updateDate,CONCAT('KS-CLUCLU-RELTN-', 'refdata-103'),'luLuRelationType.contains',sys_guid(),var_clu_id3,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLU_RSLT (CLU_ID,CREATEID,CREATETIME,ID,OBJ_ID,ST,TYPE_KEY_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,'admin',var_updateDate,var_rslt_id2,sys_guid(),'Active','kuali.resultType.creditCourseResult','admin',var_updateDate,0);

INSERT INTO KSLU_CLU_RSLT (CLU_ID,CREATEID,CREATETIME,EFF_DT,ID,OBJ_ID,RT_DESCR_ID,ST,TYPE_KEY_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES (var_clu_id1,'admin',var_updateDate,var_effDate,var_rslt_id1,sys_guid(),var_descr_id3,'Active','kuali.resultType.gradeCourseResult','admin',var_updateDate,0);

INSERT INTO KSLU_RSLT_OPT (CREATEID,CREATETIME,ID,OBJ_ID,RES_COMP_ID,RT_DESCR_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_rslt_opt_id1,sys_guid(),'kuali.resultComponent.grade.satisfactory',var_descr_id2,'Active','admin',var_updateDate,0);

INSERT INTO KSLU_RSLT_OPT (CREATEID,CREATETIME,ID,OBJ_ID,RES_COMP_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',var_updateDate,var_rslt_opt_id2,sys_guid(),'kuali.creditType.credit.degree.3.0','Active','admin',var_updateDate,0);

INSERT INTO KSLU_CLURES_JN_RESOPT (CLU_RES_ID,RES_OPT_ID)
  VALUES (var_rslt_id2,var_rslt_opt_id2);

INSERT INTO KSLU_CLURES_JN_RESOPT (CLU_RES_ID,RES_OPT_ID)
  VALUES (var_rslt_id1,var_rslt_opt_id1);

INSERT INTO KSLU_CLU_ADMIN_ORG (CLU_ID,ID,IS_PR,OBJ_ID,ORG_ID,TYPE,VER_NBR)
  VALUES (var_clu_id1,CONCAT('KS-CLU-ADMIN-ORG-', 'refdata-104'),0,sys_guid(),'ORGID-4014660630','kuali.adminOrg.type.CurriculumOversight',0);

INSERT INTO KSLU_CLU_ATP_TYPE_KEY (ATP_TYPE_KEY,CLU_ID,ID,OBJ_ID,VER_NBR)
  VALUES ('kuali.atp.season.Any',var_clu_id1,CONCAT('KS-CLU-ATP-TYP-KEY-', 'refdata-105'),sys_guid(),0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('audit','false',CONCAT('KS-CLU-ATTR-', 'refdata-106'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('repeatableNumCredits','12',CONCAT('KS-CLU-ATTR-', 'refdata-107'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('passFail','false',CONCAT('KS-CLU-ATTR-', 'refdata-108'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('semesterType','semesterType.standard',CONCAT('KS-CLU-ATTR-', 'refdata-109'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('repeatableIfContentDiffers','true',CONCAT('KS-CLU-ATTR-', 'refdata-110'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTypeExplanation','.',CONCAT('KS-CLU-ATTR-', 'refdata-111'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTotalContactHours','15',CONCAT('KS-CLU-ATTR-', 'refdata-112'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('finalExamStatus','STD',CONCAT('KS-CLU-ATTR-', 'refdata-113'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_ATTR (ATTR_NAME,ATTR_VALUE,ID,OBJ_ID,OWNER,VER_NBR)
  VALUES ('activityTotalCredits','1',CONCAT('KS-CLU-ATTR-', 'refdata-114'),sys_guid(),var_clu_id1,0);

INSERT INTO KSLU_CLU_JN_CAMP_LOC (CAMP_LOC,CLU_ID,ID,OBJ_ID,VER_NBR)
  VALUES ('NO',var_clu_id1,CONCAT('KS-CLU-JN-CAMP-LOC-', 'refdata-115'),sys_guid(),0);

end;
/