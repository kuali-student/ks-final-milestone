-- delete circular relationship of alumni relation office to the alumni association
delete KSOR_ORG_ORG_RELTN
WHERE ORG = 14
AND RELATED_ORG = 13
AND TYPE = 'kuali.org.Administer'
/
-- insert new org org relation type that makes the parent the first one defined in the releation
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),
'is Curriculum Parent of',
SYS_GUID(),
'kuali.org.hierarchy.Curriculum',
'Indicates that this organization is the Child of another organization in the Curriculum Hierarchy',
'is Curriculum Child of',
'Indicates that this organization is the Parent of another organization in the Curriculum Hierarchy',
'kuali.org.Parent2CurriculumChild',0)
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