--New Types
Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.agenda.type.final.exam','KS-SYS','agendaTypeService',0)
/
Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.agenda.type.final.exam.standard','KS-SYS','agendaTypeService',0)
/
Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.rule.type.final.exam.standard','KS-SYS','ruleTypeService',0)
/
Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.proposition.type.final.exam.timeslot','KS-SYS','simplePropositionTypeService',0)
/

Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.agenda.type.final.exam.common','KS-SYS','agendaTypeService',0)
/
Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.rule.type.final.exam.common','KS-SYS','ruleTypeService',0)
/
Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.proposition.type.final.exam.course','KS-SYS','simplePropositionTypeService',0)
/
Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.proposition.type.final.exam.courseset','KS-SYS','simplePropositionTypeService',0)
/
Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.action.type.requested.delivery.logistic','KS-SYS','actionTypeService',0)
/

Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.termresolver.type.final.exam','KS-SYS','termresolverTypeService',0)
/

--Type relations
Insert into KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) values (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.agenda.type.final.exam'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.agenda.type.final.exam.standard'),'A',1,0,'Y')
/
Insert into KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) values (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.agenda.type.final.exam'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.agenda.type.final.exam.common'),'A',2,0,'Y')
/

Insert into KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) values (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.agenda.type.final.exam.standard'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.standard'),'A',1,0,'Y')
/
Insert into KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) values (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.standard'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.final.exam.timeslot'),'A',1,0,'Y')
/
Insert into KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) values (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.standard'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.freeform.text'),'A',2,0,'Y')
/

Insert into KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) values (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.agenda.type.final.exam.common'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.common'),'A',1,0,'Y')
/
Insert into KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) values (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.common'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.final.exam.course'),'A',1,0,'Y')
/
Insert into KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) values (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.common'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.final.exam.courseset'),'A',2,0,'Y')
/
Insert into KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) values (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.common'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.freeform.text'),'A',3,0,'Y')
/