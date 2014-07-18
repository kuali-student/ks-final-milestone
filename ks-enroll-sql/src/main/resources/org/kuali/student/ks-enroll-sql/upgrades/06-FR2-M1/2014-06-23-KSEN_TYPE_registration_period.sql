--  KSENROLL-13225
INSERT INTO KSEN_TYPE (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, NAME, OBJ_ID, REF_OBJECT_URI, SERVICE_URI, TYPE_KEY, VER_NBR)
  VALUES ('SYSTEMLOADER', TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ), 'Registration Open', 'Registration Open', TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), 'Registration Open', SYS_GUID(), 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 'http://student.kuali.org/wsdl/atp/AtpService', 'kuali.atp.milestone.registrationservicesopen', 0)
/
INSERT INTO KSEN_TYPE (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, NAME, OBJ_ID, REF_OBJECT_URI, SERVICE_URI, TYPE_KEY, VER_NBR)
  VALUES ('SYSTEMLOADER', TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ), 'Pre-Registration Period', 'Pre-Registration Period', TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), 'Pre-Registration Period', SYS_GUID(), 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 'http://student.kuali.org/wsdl/atp/AtpService', 'kuali.atp.milestone.preregistrationperiod', 0)
/
INSERT INTO KSEN_TYPE (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, NAME, OBJ_ID, REF_OBJECT_URI, SERVICE_URI, TYPE_KEY, VER_NBR)
  VALUES ('SYSTEMLOADER', TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ), 'Schedule Adjustment Period', 'Schedule Adjustment Period', TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), 'Schedule Adjustment Period', SYS_GUID(), 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 'http://student.kuali.org/wsdl/atp/AtpService', 'kuali.atp.milestone.scheduleadjustmentperiod', 0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.registrationservicesopen','kuali.milestone.type.group.registration',0,'kuali.atp.milestone.registrationservicesopen','kuali.type.type.relation.state.active','kuali.type.type.relation.type.group',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.preregistrationperiod','kuali.milestone.type.group.registration',0,'kuali.atp.milestone.preregistrationperiod','kuali.type.type.relation.state.active','kuali.type.type.relation.type.group',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.scheduleadjustmentperiod','kuali.milestone.type.group.registration',0,'kuali.atp.milestone.scheduleadjustmentperiod','kuali.type.type.relation.state.active','kuali.type.type.relation.type.group',0)
/

-- Fall 2012
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('9d6258bd-5bca-4529-9a3a-3b292f4d7b84', 0, 'admin', TIMESTAMP '2014-06-23 13:22:39.952', 'admin', TIMESTAMP '2014-06-23 13:22:39.952', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.registrationservicesopen', '', 'test', TIMESTAMP '2012-11-27 23:59:59.9', '1', '1', '0', '0', 'Regstration Open', '', TIMESTAMP '2012-03-01 00:00:00.0', 'f3c367be-70b0-4fa5-93d5-eff4590b2801')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('3405197c-91a8-4577-8958-1ad5bea46483', 0, 'admin', TIMESTAMP '2014-06-23 13:22:39.952', 'admin', TIMESTAMP '2014-06-23 13:22:39.952', 'kuali.atp.2012Fall', 'f3c367be-70b0-4fa5-93d5-eff4590b2801', '1d33f292-3906-4066-bd82-affe7d7071d1')
/
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('18817d0a-6c24-4202-a194-86f2d76e4b64', 0, 'admin', TIMESTAMP '2014-06-23 13:22:40.689', 'admin', TIMESTAMP '2014-06-23 13:22:40.689', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.preregistrationperiod', '', 'test', TIMESTAMP '2012-03-09 23:59:59.9', '1', '1', '0', '0', 'Pre-Registration Period', '', TIMESTAMP '2012-03-01 00:00:00.0', 'dc06e322-0a09-4a43-b324-b3e18b45f65f')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('ed91ca0a-69e6-4ff8-94a7-d923f37d073e', 0, 'admin', TIMESTAMP '2014-06-23 13:22:40.689', 'admin', TIMESTAMP '2014-06-23 13:22:40.689', 'kuali.atp.2012Fall', 'dc06e322-0a09-4a43-b324-b3e18b45f65f', 'fd37355c-6ff0-4d1a-873a-a9496302fb48')
/
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('9024755a-9f4b-4603-b5e6-d4910a9b456a', 0, 'admin', TIMESTAMP '2014-06-23 13:22:40.7', 'admin', TIMESTAMP '2014-06-23 13:22:40.7', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.scheduleadjustmentperiod', '', 'test', TIMESTAMP '2012-09-12 23:59:59.9', '1', '1', '0', '0', 'Schedule Adjustment Period', '', TIMESTAMP '2012-08-29 00:00:00.0', '65925287-d3bd-4e40-982b-301b3d1c6de8')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('8ceebacd-3671-4bc9-8964-488f9d8d4b26', 0, 'admin', TIMESTAMP '2014-06-23 13:22:40.7', 'admin', TIMESTAMP '2014-06-23 13:22:40.7', 'kuali.atp.2012Fall', '65925287-d3bd-4e40-982b-301b3d1c6de8', 'c27689ac-e251-470d-ad51-e80cfa5479f0')
/

-- Half Fall 1 2012
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('33c70bd4-9523-486e-98e6-c37a2d3fd805', 0, 'admin', TIMESTAMP '2014-06-23 13:26:30.218', 'admin', TIMESTAMP '2014-06-23 13:26:30.218', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.registrationservicesopen', '', 'test', TIMESTAMP '2012-11-27 23:59:59.9', '1', '1', '0', '0', 'Regstration Open', '', TIMESTAMP '2012-03-01 00:00:00.0', 'c0278202-2064-4999-abae-5bc64580f6cf')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('37169df7-c3b5-4da9-a2de-bb633486e154', 0, 'admin', TIMESTAMP '2014-06-23 13:26:30.218', 'admin', TIMESTAMP '2014-06-23 13:26:30.218', 'kuali.atp.2012HalfFall1', 'c0278202-2064-4999-abae-5bc64580f6cf', '26e0c7db-2a00-44a1-b904-2533d9aa545e')
/
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('393f6d9e-fc14-451c-afd2-437b1962f32d', 0, 'admin', TIMESTAMP '2014-06-23 13:26:30.229', 'admin', TIMESTAMP '2014-06-23 13:26:30.229', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.preregistrationperiod', '', 'test', TIMESTAMP '2012-03-09 23:59:59.9', '1', '1', '0', '0', 'Pre-Registration Period', '', TIMESTAMP '2012-03-01 00:00:00.0', '1b4c8924-c9d3-4a4f-863e-4a4267634ddf')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('c85c3b30-c5cb-40ba-828e-5c656c517733', 0, 'admin', TIMESTAMP '2014-06-23 13:26:30.229', 'admin', TIMESTAMP '2014-06-23 13:26:30.229', 'kuali.atp.2012HalfFall1', '1b4c8924-c9d3-4a4f-863e-4a4267634ddf', 'ea820fce-ce0c-40ad-9cb9-6885bde73b7b')
/
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('fe47ef56-1305-4a84-9d7f-6bef3d893b13', 0, 'admin', TIMESTAMP '2014-06-23 13:26:30.24', 'admin', TIMESTAMP '2014-06-23 13:26:30.24', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.scheduleadjustmentperiod', '', 'test', TIMESTAMP '2012-09-05 23:59:59.9', '1', '1', '0', '0', 'Schedule Adjustment Period', '', TIMESTAMP '2012-08-29 00:00:00.0', 'cb50c33c-1c1a-4329-9cfa-556234ae516c')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('09880d44-5123-4c66-802d-54ed8d0c1674', 0, 'admin', TIMESTAMP '2014-06-23 13:26:30.24', 'admin', TIMESTAMP '2014-06-23 13:26:30.24', 'kuali.atp.2012HalfFall1', 'cb50c33c-1c1a-4329-9cfa-556234ae516c', '452851ce-ae8c-4aaa-8586-7ac1c62ead4e')
/

-- Half Fall 2 2012
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('ec0a3be1-cd54-4dee-a6c9-17aa1dc23c12', 0, 'admin', TIMESTAMP '2014-06-23 13:29:15.584', 'admin', TIMESTAMP '2014-06-23 13:29:15.584', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.registrationservicesopen', '', 'test', TIMESTAMP '2012-11-27 23:59:59.9', '1', '1', '0', '0', 'Regstration Open', '', TIMESTAMP '2012-03-01 00:00:00.0', '36f85db1-d2f8-47a6-a36a-8a9fd1c71cbb')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('7558ccc7-8a4f-4016-9a2a-54ca84ba1141', 0, 'admin', TIMESTAMP '2014-06-23 13:29:15.584', 'admin', TIMESTAMP '2014-06-23 13:29:15.584', 'kuali.atp.2012HalfFall2', '36f85db1-d2f8-47a6-a36a-8a9fd1c71cbb', '1cb0c7cb-26f2-464f-bbce-b9de832c1836')
/
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('b8390006-26b6-439b-9014-755e72336885', 0, 'admin', TIMESTAMP '2014-06-23 13:29:15.597', 'admin', TIMESTAMP '2014-06-23 13:29:15.597', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.preregistrationperiod', '', 'test', TIMESTAMP '2012-11-27 23:59:59.9', '1', '1', '0', '0', 'Pre-Registration Period', '', TIMESTAMP '2012-03-10 00:00:00.0', 'd70eccb1-a0e1-4a56-bea2-f8c599178966')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('25a28cbc-b803-4ff5-9478-db9d47064219', 0, 'admin', TIMESTAMP '2014-06-23 13:29:15.597', 'admin', TIMESTAMP '2014-06-23 13:29:15.597', 'kuali.atp.2012HalfFall2', 'd70eccb1-a0e1-4a56-bea2-f8c599178966', '2462c0be-0dee-482f-949d-c25651668915')
/
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('0fb67e58-fa19-4978-898d-25e479b1e225', 0, 'admin', TIMESTAMP '2014-06-23 13:29:15.608', 'admin', TIMESTAMP '2014-06-23 13:29:15.608', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.scheduleadjustmentperiod', '', 'test', TIMESTAMP '2012-10-26 23:59:59.9', '1', '1', '0', '0', 'Schedule Adjustment Period', '', TIMESTAMP '2012-10-22 00:00:00.0', '4ccad224-f9a8-4c62-87b0-04517bbced51')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('62048703-e490-467f-b064-13e7e39d069f', 0, 'admin', TIMESTAMP '2014-06-23 13:29:15.608', 'admin', TIMESTAMP '2014-06-23 13:29:15.608', 'kuali.atp.2012HalfFall2', '4ccad224-f9a8-4c62-87b0-04517bbced51', '4471d788-1be0-48ee-be30-1249f133e6b0')
/

-- CHEM147 / AO A - add Half Fall 2 2012
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e9e5c62b-3b8d-44bd-b0b6-1b66b8df484c', 'kuali.attribute.nonstd.ts.indicator', '50cca1cc-8e5d-47a0-8bdc-c7d25eefe395', '0', '25bd80d0-252c-432f-a634-737357ec1836')
/
update KSEN_LUI set ATP_ID='kuali.atp.2012HalfFall2' where ID='50cca1cc-8e5d-47a0-8bdc-c7d25eefe395' and VER_NBR=1
/





