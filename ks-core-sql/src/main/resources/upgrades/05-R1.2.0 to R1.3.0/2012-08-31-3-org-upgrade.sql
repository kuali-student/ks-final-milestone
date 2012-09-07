-- ###################
-- ##### INSERTS #####
-- ###################
INSERT INTO KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_DESCR, SHRT_NAME, ST, TYPE, OBJ_ID)
values ('222', 'TESTUSER', '01-JAN-00 12.00.00.000000 AM', 'admin', '01-JAN-00 12.00.00.000000 AM', 2, '01-JAN-00 12.00.00.000000 AM', '', '', 'Academic Affairs (College)', '', 'Academics', '', 'kuali.org.College', '5E7E3AACF7474BA9BF8667B6364531A3')
/
INSERT INTO KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_DESCR, SHRT_NAME, ST, TYPE, OBJ_ID)
values ('223', '', '01-JAN-00 12.00.00.000000 AM', 'admin', '01-JAN-00 12.00.00.000000 AM', 1, '01-JAN-00 12.00.00.000000 AM', '', '', 'Office of Undergraduate Studies (Department)', '', 'UndergraduateStudies', '', 'kuali.org.Department', 'c1069a5a-613c-4f38-b9ba-c465cc02136b')
/
INSERT INTO KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_DESCR, SHRT_NAME, ST, TYPE, OBJ_ID)
values ('224', '', '', '', '', 0, '01-JUL-10 12.00.00.000000 AM', '', '', 'Bioengineering Dept', '', 'Bioengineering', 'Active', 'kuali.org.Department', '572084a6-06e9-43ef-bd79-b7fe50c40fcc')
/
INSERT INTO KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_DESCR, SHRT_NAME, ST, TYPE, OBJ_ID)
values ('225', '', '', '', '', 0, '01-JUL-10 12.00.00.000000 AM', '', '', 'Bioengineering Dept Curriculum Committee', '', 'BioengineeringCOC', 'Active', 'kuali.org.COC', 'b3e8902a-6284-4a7d-b1e2-d6fe19719401')
/
INSERT INTO KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_DESCR, SHRT_NAME, ST, TYPE, OBJ_ID)
values ('226', '', '', '', '', 0, '01-JUL-10 12.00.00.000000 AM', '', '', 'Entomology Dept', '', 'Entomology', 'Active', 'kuali.org.Department', '64bf7d03-e592-42f2-aa2f-935937b93e05')
/
INSERT INTO KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LNG_DESCR, LNG_NAME, SHRT_DESCR, SHRT_NAME, ST, TYPE, OBJ_ID)
values ('227', '', '', '', '', 0, '01-JUL-10 12.00.00.000000 AM', '', '', 'Entomology Dept Curriculum Committee', '', 'EntomologyCOC', 'Active', 'kuali.org.COC', 'c12a6b9f-2f01-420c-8826-c9684db174b4')
/

--update relationships so new orgs are in correct hierarchy
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('ba7b62b4-f93f-4555-84ae-f5e8308b087b', null, null, 'admin', TIMESTAMP '2010-12-13 12:10:36', 3, TIMESTAMP '2009-12-31 03:00:00', null, null, '15', '222', 'kuali.org.Contain', '98771af9-0071-4c7e-9b0d-7a6b2039f60d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('ba7b62b4-f93f-4555-84ae-f5e8308b087c', null, null, 'admin', TIMESTAMP '2010-12-13 12:10:36', 3, TIMESTAMP '2009-12-31 03:00:00', null, null, '222', '223', 'kuali.org.Contain', '98771af9-0071-4c7e-9b0d-7a6b2039f60e')
/

-- delete circular relationship of alumni relation office to the alumni association
delete KSOR_ORG_ORG_RELTN
WHERE ORG = 14
AND RELATED_ORG = 13
AND TYPE = 'kuali.org.Administer'
/

-- add the department to COC chartering relationship
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR)
select a.eff_dt, SYS_GUID(),SYS_GUID (), a.org, a.related_org, 'kuali.org.Supervise', 'Active', 'nwright', sysdate, 'nwright',sysdate, 0
 from KSOR_ORG_ORG_RELTN A,
      KSOR_ORG B,
      KSOR_ORG C
where a.org = b.id
and a.type = 'kuali.org.CurriculumParent'
and c.type = 'kuali.org.COC'
and a.related_org = c.id
order by a.type, c.type, b.shrt_name, c.shrt_name
/

-- add the COC to parent COC relationship
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 174, 215,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 174, 155,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 141, 168,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 141, 143,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 141, 144,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 141, 169,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 141, 146,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 141, 145,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 141, 170,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 213,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 147,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 148,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 149,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 167,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 153,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 151,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 152,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 219,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 143, 221,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 144, 174,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 144, 157,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 144, 175,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 146, 158,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 146, 159,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 146, 161,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 146, 160,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 145, 202,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 145, 163,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 145, 162,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 145, 164,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 145, 165,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 145, 166,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 145, 205,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 145, 207,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 175, 211,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 175, 217,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 175, 150,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 175, 154,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,TYPE,ST, createid, createtime, UPDATEID,UPDATETIME,VER_NBR) values  (to_date ('20000101', 'YYYYMMDD'),sys_guid (), sys_guid (), 175, 156,'kuali.org.Parent2CurriculumChild','Active','nwright',sysdate,'nwright',sysdate,0)
/
INSERT INTO KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID)
values ('12', 'TESTUSER', '22-JAN-09 12.00.00.000000 AM', 'TESTUSER', '22-JAN-09 12.00.00.000000 AM', 0, '01-JAN-00 12.00.00.000000 AM', '', 'Active', '14', '13', 'kuali.org.Administer', '508D39C8C3F349D89C28C7F9B451F8A8')
/
INSERT INTO KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID)
values ('12c96088-90fa-40a1-9c5f-c978635abd88', '', '', 'admin', '02-AUG-11 12.00.00.000000 AM', 1, '31-DEC-99 12.00.00.000000 AM', '', '', '224', '225', 'kuali.org.CurriculumParent', '8f8ef13c-0508-4f7c-b7f2-104b8ebe5c2f')
/
INSERT INTO KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID)
values ('54496d35-5a9d-4dd3-8ff4-f780f318c599', '', '', 'admin', '02-AUG-11 12.00.00.000000 AM', 4, '31-DEC-99 12.00.00.000000 AM', '', '', '31', '224', 'kuali.org.Contain', 'f4748bb7-704a-450b-a5f4-d142f5fe3835')
/
INSERT INTO KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID)
values ('70d237f3-7ec3-444f-95e8-993749115454', 'admin', '03-AUG-11 12.00.00.000000 AM', 'admin', '03-AUG-11 12.00.00.000000 AM', 0, '', '', 'user6', '', '152', 'kuali.org.PersonRelation.Member', '887b3299-a73f-4eb2-b4f9-faff14e19acd')
/
INSERT INTO KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID)
values ('a51dcc09-9dbc-490f-869b-4187e1f25b26', 'admin', '03-AUG-11 12.00.00.000000 AM', 'admin', '03-AUG-11 12.00.00.000000 AM', 0, '', '', 'testuser10', '', '174', 'kuali.org.PersonRelation.Member', 'c8a68d02-7360-4402-bf1b-6b8473dea1c5')
/
INSERT INTO KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID)
values ('b08edadd-34d2-46ec-bbc8-1e8f3be36982', 'admin', '03-AUG-11 12.00.00.000000 AM', 'admin', '03-AUG-11 12.00.00.000000 AM', 0, '', '', 'testuser9', '', '174', 'kuali.org.PersonRelation.Chair', '3d313444-8cdd-4cc2-b9f0-49db6d390263')
/
INSERT INTO KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID)
values ('d86c8a4b-9e63-4697-a4f6-4ddeb6e03bc7', 'admin', '03-AUG-11 12.00.00.000000 AM', 'admin', '03-AUG-11 12.00.00.000000 AM', 0, '01-AUG-11 12.00.00.000000 AM', '', 'testuser7', '', '215', 'kuali.org.PersonRelation.Chair', '2bb6bdbb-655b-4d10-837e-375f7286027d')
/
INSERT INTO KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID)
values ('dcaac3cc-ed85-445a-a0ff-ee0e5341b3fe', 'admin', '03-AUG-11 12.00.00.000000 AM', 'admin', '03-AUG-11 12.00.00.000000 AM', 0, '', '', 'user5', '', '152', 'kuali.org.PersonRelation.Chair', 'c414382b-551b-4069-af22-26b526fcea8a')
/
INSERT INTO KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR)
values ('kuali.org.PersonRelation.CurriculumManager', 'Curriculum Manager', '01-JAN-00 12.00.00.000000 AM', '', 'Curriculum Manager', '3de6e68c-24bd-42c8-bb40-4a16673cef44', 0)
/
INSERT INTO KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID)
values ('12a24267-099d-4db3-a764-3db49fb02a34', 'admin', '02-AUG-11 12.00.00.000000 AM', 'admin', '02-AUG-11 12.00.00.000000 AM', 0, 'Member of the Entomology COC', '100', 1, '', null, 'Member of the Entomology COC', '227', 'kuali.org.PersonRelation.Member', 'e11564b0-183d-4267-9d3a-829c10ac7aee')
/
INSERT INTO KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID)
values ('22644949-b363-449d-aefd-e4003eb00dab', 'admin', '02-AUG-11 12.00.00.000000 AM', 'admin', '02-AUG-11 12.00.00.000000 AM', 0, 'Chair of the Entomology COC', '100', 1, '', null, 'Chair of the Entomology COC', '227', 'kuali.org.PersonRelation.Chair', '1cff7894-2ebc-4db4-a6f6-a42727f20c81')
/
INSERT INTO KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID)
values ('7bb96587-9e0b-4fed-b3f5-08a1e83cf347', 'admin', '02-AUG-11 12.00.00.000000 AM', 'admin', '02-AUG-11 12.00.00.000000 AM', 0, 'Member of Bioengineering COC', '100', 1, '', null, 'Member of Bioengineering COC', '225', 'kuali.org.PersonRelation.Member', '7e9824bb-6f99-47ea-b246-baf1240c15ca')
/
INSERT INTO KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID)
values ('c06cf5c8-d6fc-459c-aedb-e74aa9de451f', 'admin', '02-AUG-11 12.00.00.000000 AM', 'admin', '02-AUG-11 12.00.00.000000 AM', 0, 'Chair of Bioengineering COC', '100', 1, '', null, 'Chair of Bioengineering COC', '225', 'kuali.org.PersonRelation.Chair', '1115f9d0-1b5c-4d13-ae3c-2d71bea5bf99')
/
INSERT INTO KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID)
values ('cc473647-536d-4688-a33f-e7b00c118cd2', 'TESTUSER', '13-DEC-10 12.26.11.000000 PM', 'TESTUSER', '13-DEC-10 12.26.11.000000 PM', 0, 'Curriculum Manager', '100', 1, '', null, 'Curriculum Manager', '141', 'kuali.org.PersonRelation.CurriculumManager', '0fa77c54-b315-4b4a-8d98-dbe50504dd41')
/
INSERT INTO KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID)
values ('2f703cc6-2372-469d-ac3d-a66fa31ff9a0', 'admin', '03-AUG-11 12.00.00.000000 AM', 'admin', '03-AUG-11 12.00.00.000000 AM', 0, '01-AUG-11 12.00.00.000000 AM', '', 'user7', '', '143', 'kuali.org.PersonRelation.Chair', '12efb943-bf12-4bbe-aa42-82d8e8b2dbcc')
/
INSERT INTO KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID)
values ('10817a3c-ae5a-45ee-8dbc-aaa786029e2e', '', '', 'admin', '14-DEC-10 11.12.26.000000 AM', 2, '31-DEC-99 03.00.00.000000 AM', '', 'dev2', '', '141', 'kuali.org.PersonRelation.CurriculumManager', '61057ffa-eaf7-459a-989c-543ae517b833')
/
INSERT INTO KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID)
values ('40087561-7727-47a6-955f-f2bdff030499', 'admin', '03-AUG-11 12.00.00.000000 AM', 'admin', '03-AUG-11 12.00.00.000000 AM', 0, '01-AUG-11 12.00.00.000000 AM', '', 'user8', '', '143', 'kuali.org.PersonRelation.Member', '550d21ee-37ab-4f9f-a785-0b16903a1da5')
/
INSERT INTO KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID)
values ('4d8b8dc3-4734-4daa-a002-1375a9408dc3', 'admin', '03-AUG-11 12.00.00.000000 AM', 'admin', '03-AUG-11 12.00.00.000000 AM', 0, '01-AUG-11 12.00.00.000000 AM', '', 'testuser8', '', '215', 'kuali.org.PersonRelation.Member', 'ac8bb02e-ee42-4680-b35b-b6cda90032e7')
/

-- ###################
-- ##### UPDATES #####
-- ###################
UPDATE KSOR_ORG
SET TYPE = 'kuali.org.College'
WHERE Id = '26'
/

UPDATE KSOR_ORG_TYPE
SET OBJ_ID = 'BC40211A5D87FD41E040C60AF05B24C0'
WHERE TYPE_KEY = 'kuali.org.TestingService'
/

UPDATE KSOR_ORG_PERS_RELTN
SET ORG_PERS_RELTN_TYPE = 'kuali.org.PersonRelation.Chair',
PERS_ID = 'testuser2'
WHERE ID = '1'
/

UPDATE KSOR_ORG_PERS_RELTN
SET ORG_PERS_RELTN_TYPE = 'kuali.org.PersonRelation.Chair',
PERS_ID = 'testuser3',
ORG = '147'
WHERE ID = '3'
/
UPDATE KSOR_ORG_PERS_RELTN
SET ORG_PERS_RELTN_TYPE = 'kuali.org.PersonRelation.Chair',
PERS_ID = 'testuser1' 
WHERE ID = '6'
/

UPDATE KSOR_ORG_PERS_RELTN
SET ORG_PERS_RELTN_TYPE  = 'kuali.org.PersonRelation.Chair',
PERS_ID = 'testuser4'
WHERE ID = '4'
/