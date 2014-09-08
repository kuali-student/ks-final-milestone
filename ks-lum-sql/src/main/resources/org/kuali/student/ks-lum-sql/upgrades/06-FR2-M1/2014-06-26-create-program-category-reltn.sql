-- Create example LO program Category. The source of this data is the Description of KSCM-2289
INSERT INTO KSLO_LO_CATEGORY (CREATEID,CREATETIME,ID,LO_CATEGORY_TYPE_ID,LO_REPO_ID,NAME,OBJ_ID,STATE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',TO_DATE( '20140626111111', 'YYYYMMDDHH24MISS' ),'8f701500-fd5d-11e3-a3ac-0800200c9a66','loCategoryType.accreditation','kuali.loRepository.key.singleUse','Certificate to Teach English',null,'active','admin',TO_DATE( '20140626111111', 'YYYYMMDDHH24MISS' ),0)
/
insert into KSLO_LO_JN_LOCATEGORY (ID, LOCATEGORY_ID, LO_ID, OBJ_ID, VER_NBR) values ('33db72a0-fd69-11e3-a3ac-0800200c9a66', '8f701500-fd5d-11e3-a3ac-0800200c9a66', 'f8b222c4-b632-42b5-9d0e-b1aaea5fe1cd', null, 0)
/
