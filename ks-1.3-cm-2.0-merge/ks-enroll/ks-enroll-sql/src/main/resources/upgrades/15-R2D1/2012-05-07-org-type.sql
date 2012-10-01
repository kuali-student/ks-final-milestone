-- ORG TYPES

INSERT INTO KSOR_ORG_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20120501000000', 'YYYYMMDDHH24MISS' ),'Campus','4ccc0e40-94ad-11e1-b0c4-0800200c9a6','Academic organizational unit representing a campus','kuali.org.type.campus',0)
/
INSERT INTO KSOR_ORG_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20120501000000', 'YYYYMMDDHH24MISS' ),'College','4ccc0e41-94ad-11e1-b0c4-0800200c9a7','Academic organizational unit representing a school or college','kuali.org.type.college',0)
/
INSERT INTO KSOR_ORG_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20120501000000', 'YYYYMMDDHH24MISS' ),'Academic Department','4ccc0e42-94ad-11e1-b0c4-0800200c9a9','Academic organizational unit representing a department','kuali.org.type.department',0)
/
INSERT INTO KSOR_ORG_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20120501000000', 'YYYYMMDDHH24MISS' ),'Academic Curriculum','74ccc0e43-94ad-11e1-b0c4-0800200c910','Academic organizational unit representing a curriculum','kuali.org.type.curriculum',0)
/

INSERT INTO KSOR_ORG_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20120501000000', 'YYYYMMDDHH24MISS' ),'University','4ccc0e42-94ad-11e1-b0c4-0800200d9a9','University','kuali.org.type.university',0)
/

-- Univeristy Org
INSERT INTO KSOR_ORG (CREATETIME,EFF_DT,ID,LNG_NAME,OBJ_ID,SHRT_NAME,ST,TYPE,LNG_DESCR,SHRT_DESCR,VER_NBR) VALUES (TO_DATE( '20120501000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '18610101000000', 'YYYYMMDDHH24MISS' ),'UW000','University of Washington','34c54990-9581-11e1-b0c4-0800200c9a67','UW','Active','kuali.org.type.university','University of Washington','UW',0)
/

-- ORG HEIRARCHY

INSERT INTO KSOR_ORG_HIRCHY (DESCR,EFF_DT,ID,NAME,OBJ_ID,ROOT_ORG,VER_NBR)
  VALUES ('Hierarchy used to manage organizational units associated with curriculum (course, course offerings, programs, program offerings).',TO_DATE( '20120501000000', 'YYYYMMDDHH24MISS' ),'kuali.org.hierarchy.academic.curriculum','Curriculum','2873acc0-9574-11e1-b0c4-0800200c9a66','UW000',0)
/

-- ORG HEIRARCHY JN ORG TYPE

INSERT INTO KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID,ORG_TYPE_ID)
  VALUES ('kuali.org.hierarchy.academic.curriculum','kuali.org.type.campus')
/
INSERT INTO KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID,ORG_TYPE_ID)
  VALUES ('kuali.org.hierarchy.academic.curriculum','kuali.org.type.college')
/
INSERT INTO KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID,ORG_TYPE_ID)
  VALUES ('kuali.org.hierarchy.academic.curriculum','kuali.org.type.department')
/
INSERT INTO KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID,ORG_TYPE_ID)
  VALUES ('kuali.org.hierarchy.academic.curriculum','kuali.org.type.curriculum')
/


-- ORG ORG RELATION TYPE

INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Child Organization','00b80ad0-9571-11e1-b0c4-0800200c9a6c','kuali.org.hierarchy.academic.curriculum','Indicates that this organization is the Parent of the organization','is Parent of','Indicates the organization is the child of a parent organization','kuali.org.relation.type.child2parent',0)
/