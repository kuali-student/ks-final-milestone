--  INSERTING into KSEN_ATP
Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('testAtpId1',null,0,null,null,null,null, to_timestamp('09-SEP-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('08-SEP-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), '2011-2012 Academic Year', 'kuali.atp.state.Draft','kuali.atp.type.AcademicCalendar','RICHTEXT-101')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('testAtpId2',null,0,null,null,null,null, to_timestamp('09-SEP-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('08-SEP-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), '2011-2012 Campus Calendar', 'kuali.atp.state.Draft','kuali.atp.type.CampusCalendar','RICHTEXT-102')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('testDeleteAtpId1',null,0,null,null,null,null, to_timestamp('01-JAN-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-DEC-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testDeleteAtp1', 'kuali.atp.state.Draft','kuali.atp.type.CampusCalendar','RICHTEXT-103')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('testDeleteAtpId2',null,0,null,null,null,null, to_timestamp('01-JAN-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-DEC-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testDeleteAtp2', 'kuali.atp.state.Draft','kuali.atp.type.CampusCalendar','RICHTEXT-104')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('termRelationTestingTerm1',null,0,null,null,null,null, to_timestamp('01-SEP-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-DEC-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testingTerm1', 'kuali.atp.state.Draft','kuali.atp.type.Fall','RICHTEXT-TRT-1')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('termRelationTestingTerm2',null,0,null,null,null,null, to_timestamp('01-JAN-01 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-MAY-01 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testingTerm2', 'kuali.atp.state.Draft','kuali.atp.type.Spring','RICHTEXT-TRT-2')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('termRelationTestingTerm3',null,0,null,null,null,null, to_timestamp('01-SEP-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-DEC-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testingTerm3', 'kuali.atp.state.Official','kuali.atp.type.Fall','RICHTEXT-TRT-7')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('termRelationTestingTerm4',null,0,null,null,null,null, to_timestamp('01-JAN-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-MAY-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testingTerm4', 'kuali.atp.state.Draft','kuali.atp.type.HalfFall1','RICHTEXT-TRT-8')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('termRelationTestingTermDelete',null,0,null,null,null,null, to_timestamp('01-JAN-31 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-MAY-31 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testingTermDelete', 'kuali.atp.state.Draft','kuali.atp.type.HalfFall1','RICHTEXT-TRT-9')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('termRelationTestingTerm5',null,0,null,null,null,null, to_timestamp('01-SEP-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-DEC-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testingTerm3', 'kuali.atp.state.Official','kuali.atp.type.Fall','RICHTEXT-TRT-10')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('termRelationTestingTerm6',null,0,null,null,null,null, to_timestamp('01-JAN-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-MAY-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testingTerm4', 'kuali.atp.state.Draft','kuali.atp.type.HalfFall2','RICHTEXT-TRT-11')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('termRelationTestingAcal1',null,0,null,null,null,null, to_timestamp('01-SEP-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('01-JUN-01 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testingAcal1', 'kuali.atp.state.Draft','kuali.atp.type.AcademicCalendar','RICHTEXT-TRT-3')
/

Insert into KSEN_ATP (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,START_DT,END_DT,NAME,ATP_STATE_ID,ATP_TYPE_ID,RT_DESCR_ID) values ('termRelationTestingAcal2',null,0,null,null,null,null, to_timestamp('01-SEP-01 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('01-JUN-02 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 'testingAcal2', 'kuali.atp.state.Draft','kuali.atp.type.AcademicCalendar','RICHTEXT-TRT-4')
/

