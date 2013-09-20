Insert into KSLU_LUTYPE (DLVR_MTHD,EFF_DT,EXPIR_DT,INSTR_FRMT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
values (null,to_timestamp('31/DEC/99 19:00:00.000000000','DD/MON/RR HH24:MI:SS.FF'),null,null,'Exam',SYS_GUID(),'A generic canonical Exam.','kuali.lu.type.exam',0)
/

Insert into KSLU_LUTYPE (DLVR_MTHD,EFF_DT,EXPIR_DT,INSTR_FRMT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
values (null,to_timestamp('31/DEC/99 19:00:00.000000000','DD/MON/RR HH24:MI:SS.FF'),null,null,'Exam',SYS_GUID(),'A canonical exam that will be used to instantiate final exam offerings.','kuali.lu.type.exam.final',0)
/