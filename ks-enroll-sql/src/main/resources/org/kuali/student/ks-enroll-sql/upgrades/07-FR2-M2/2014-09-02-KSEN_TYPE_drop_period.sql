--  KSENROLL-14626
INSERT INTO KSEN_TYPE (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, NAME, OBJ_ID, REF_OBJECT_URI, SERVICE_URI, TYPE_KEY, VER_NBR)
  VALUES ('SYSTEMLOADER', TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ), 'Drop Period', 'Drop Period', TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), 'Drop Period', SYS_GUID(), 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 'http://student.kuali.org/wsdl/atp/AtpService', 'kuali.atp.milestone.dropperiod', 0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.dropperiod','kuali.milestone.type.group.registration',0,'kuali.atp.milestone.dropperiod','kuali.type.type.relation.state.active','kuali.type.type.relation.type.group',0)
/

-- Fall 2012
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('9149c48f-c997-4dd6-a7f3-1052d52f10c0', 0, 'admin', TIMESTAMP '2014-09-02 20:35:39.567', 'admin', TIMESTAMP '2014-09-02 20:35:39.567', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.dropperiod', '', 'test', TIMESTAMP '2012-11-09 23:59:59.9', '1', '1', '0', '0', 'Drop Period', '', TIMESTAMP '2012-09-13 00:00:00.0', '13749e49-786c-4c11-a206-ccd6cff2c6da')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('c3317b17-f64c-4bc4-a653-497ec758b9fb', 0, 'admin', TIMESTAMP '2014-09-02 20:35:39.567', 'admin', TIMESTAMP '2014-09-02 20:35:39.567', 'kuali.atp.2012Fall', '13749e49-786c-4c11-a206-ccd6cff2c6da', 'f00d7360-6284-4204-b2f0-76578ea24a89')
/
insert into KSEN_ATP_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5a552af1-f486-43a5-bf21-8516d5609246', 'kuali.attribute.exam.period.exclude.sunday', 'a369c002-ce4d-4050-8394-9839c72a126e', '0', 'f2da9cb7-8702-429a-9bf7-cebe7dcc5e62')
/
insert into KSEN_ATP_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aac5e1cb-6b27-4c58-9c05-13e24174cc0a', 'kuali.attribute.exam.period.exclude.saturday', 'a369c002-ce4d-4050-8394-9839c72a126e', '0', 'f703ee36-f96d-462f-bf53-ab16e7ed2008')
/
insert into KSEN_ATP_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('70852477-6a43-4ca3-b509-d404ca0e6939', 'kuali.attribute.exam.period.exclude.sunday', 'dd286803-1bf2-429b-8ecb-0a22098b10a2', '0', '1aad2b4a-1d3f-4bdf-a4de-fe49ac58f684')
/
insert into KSEN_ATP_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c113c5ef-92b0-4d6f-aa65-aa04a7f67e1c', 'kuali.attribute.exam.period.exclude.saturday', 'dd286803-1bf2-429b-8ecb-0a22098b10a2', '0', '95b7d75c-1cbc-4a9f-b57d-722498bc7867')
/
insert into KSEN_ATP_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d4bb9b85-c011-406d-8383-42011eb8bea3', 'kuali.attribute.exam.period.exclude.saturday', 'kuali.atp.ExamPeriod.2013Spring', '0', '00d2bccd-ec46-4cbe-9f64-fb7f7a14477e')
/
insert into KSEN_ATP_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('21a82c2e-4d3b-45f5-bf9d-c070900f02be', 'kuali.attribute.exam.period.exclude.sunday', 'kuali.atp.ExamPeriod.2013Spring', '0', 'b52f162f-60e6-4fd2-bed5-d6218223cd24')
/

-- Half Fall 1 2012
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('3f2b5ae7-86b2-4586-88cd-af695a80d455', 0, 'admin', TIMESTAMP '2014-09-02 20:48:53.799', 'admin', TIMESTAMP '2014-09-02 20:48:53.799', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.dropperiod', '', 'test', TIMESTAMP '2012-10-04 23:59:59.9', '1', '1', '0', '0', 'Drop Period', '', TIMESTAMP '2012-09-06 00:00:00.0', '94522d30-79f6-4980-a9c3-52d1274e33f4')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('616e5a74-c6b7-4b8c-a6b2-ff4d7050d8ec', 0, 'admin', TIMESTAMP '2014-09-02 20:48:53.799', 'admin', TIMESTAMP '2014-09-02 20:48:53.799', 'kuali.atp.2012HalfFall1', '94522d30-79f6-4980-a9c3-52d1274e33f4', '698c93e1-1f1f-448d-ac50-36d59963a487')
/

-- Half Fall 2 2012
insert into KSEN_MSTONE (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MSTONE_STATE, MSTONE_TYPE, DESCR_FORMATTED, DESCR_PLAIN, END_DT, IS_ALL_DAY, IS_DATE_RANGE, IS_INSTRCT_DAY, IS_RELATIVE, NAME, RELATIVE_ANCHOR_MSTONE_ID, START_DT, ID) values ('b0deffbf-930f-4ff8-805c-3c918869ad0d', 0, 'admin', TIMESTAMP '2014-09-02 20:53:15.786', 'admin', TIMESTAMP '2014-09-02 20:53:15.786', 'kuali.milestone.state.Draft', 'kuali.atp.milestone.dropperiod', '', 'test', TIMESTAMP '2012-11-27 23:59:59.9', '1', '1', '0', '0', 'Drop Period', '', TIMESTAMP '2012-10-27 00:00:00.0', 'ff812ccd-97c5-4af8-b49e-ce7100815a0a')
/
insert into KSEN_ATPMSTONE_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, MSTONE_ID, ID) values ('8ad8d3d8-0878-41e7-ae91-d5dfe8693e56', 0, 'admin', TIMESTAMP '2014-09-02 20:53:15.786', 'admin', TIMESTAMP '2014-09-02 20:53:15.786', 'kuali.atp.2012HalfFall2', 'ff812ccd-97c5-4af8-b49e-ce7100815a0a', 'd2e956d1-a5ad-4592-a029-1fc875964e2d')
/