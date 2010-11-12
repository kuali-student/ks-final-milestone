TRUNCATE TABLE KSOR_ORG_ORG_RELTN_TYPE DROP STORAGE
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Administers','8052C081024B4EDFB225F96143EF6033','kuali.org.hierarchy.Main','Indicates that this organization is administered by another','is Administered by','Describes where one organization is administratively responsible for another organization that is not simply "part" of that organization and where the relationship cannot be said to be so strong that there is a "reporting" relationship','kuali.org.Administer',0)
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Advises','B4B60D00590B4068B36AB6EDC6C44A82','kuali.org.hierarchy.Main','Indicates that this organization receives advice from the leadership of another organization','is Advised by','Indicates that one organization provides advice to the leadership of another organization','kuali.org.Advisory',0)
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'is Board of','C8AD6A138C10463B8EC71E4B85F3AD90','kuali.org.hierarchy.Main','Indicates that one organization has at its Board (of Trustees) another organization','has Board of','Indicates that one organization if the Board (of Trustees) of another organization','kuali.org.Board',0)
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Chartered','0622B9EBC043443A9329EC561560FA1E','kuali.org.hierarchy.Main','Indicates that this organization exists because it is chartered or sponsored by another organization','is Chartered by','Indicates that this organization chartered or sponsored another organization','kuali.org.Charter',0)
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Collaborate','BDAD26F205DA4E0B858E7B0BA9940929','kuali.org.hierarchy.Main','collaborates with','collaborates with','Peer Organizations Collaborating Together','kuali.org.Collaborate',0)
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Contains','24580447305A4FDD80646A38FE3682D3','kuali.org.hierarchy.Main','Indicates that one organization is part of another organization','is Part of','Indicates that this organization contains another organization','kuali.org.Contain',0)
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'is Curriculum Parent of','6314186326A743C3B03350B63467E4EF','kuali.org.hierarchy.Curriculum','Indicates that one organization is the Child of another organization in the Curriculum Hierarchy','is Curriculum Child of','Indicates that one organization is the Parent of another organization in the Curriculum Hierarchy','kuali.org.CurriculumParent',0)
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Headquarters for','BDD2BE20009F4D14BDB1295BEE310503','kuali.org.hierarchy.Main','The administrative office for a larger division','has as its Headquarters','The headquarters of an administrative office for a larger division','kuali.org.HQ',0)
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'has Subcommittee of','E31F023B1587468C8CB9D959CDFA6452','kuali.org.hierarchy.Main','Indicates that this organization is a subcommittee of another','is Subcommittee of','Indicates this is organization has a subcommittee','kuali.org.Supercommittee',0)
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,NAME,OBJ_ID,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Supervises','2EC7AA7B781D48598A931BB9184D7CB2','kuali.org.hierarchy.Main','Indicates that the head of one organization is supervised by the head of another organization','is Supervised by','Indicates that the head of one organization has direct supervisory responsibility for the head of another organization','kuali.org.Supervise',0)
/
