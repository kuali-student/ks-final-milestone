TRUNCATE TABLE KSOR_ORG_ORG_RELTN_TYPE DROP STORAGE
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Administer','kuali.org.hierarchy.Main','is responsible for','is responsible for','Describes where one organization is administratively responsible for another organization that is not simply "part" of that organization and where the relationship cannot be said to be so strong that there is a "reporting" relationship','kuali.org.Administer')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Advisory','kuali.org.hierarchy.Main','is advised by','is advised by','Indicates that one organization provides advice to the leadership of another organization','kuali.org.Advisory')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Board','kuali.org.hierarchy.Main','has as its board of trustees','has as its board of trustees','Holds a Board of Trustee Relationship to the organization','kuali.org.Board')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Chartered','kuali.org.hierarchy.Main','charters','charters','Indicates that one organization exists because it is chartered or recognized or sponsored by another organization','kuali.org.Chartered')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Collaborate','kuali.org.hierarchy.Main','collaborates with','collaborates with','Peer Organizations Collaborating Together','kuali.org.Collaborate')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Curriculum Child','kuali.org.hierarchy.Curriculum','is parent of','is parent of','Indicates that one organization is the child of another organization in the curriculum Hierarchy','kuali.org.CurriculumChild')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Functional','kuali.org.hierarchy.Main','is functionally responsible to','is functionally responsible to','Functionally connected by not administratiavely Used for mapping matrix responsibilities','kuali.org.Functional')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'HQ','kuali.org.hierarchy.Main','has as its headquarters','has as its headquarters','The headquarters or administrative office for a larger division','kuali.org.HQ')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Part','kuali.org.hierarchy.Main','contains','contains','Indicates that one organization is simply a part of another larger organization','kuali.org.Part')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Report','kuali.org.hierarchy.Main','is reported to by','is reported to by','Describes where the head of one organization directly reports to the head of the higher level organization','kuali.org.Report')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Section','kuali.org.hierarchy.Main','has section','has section','Indicates that one organization is section of another larger organization','kuali.org.Section')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Sponsor','kuali.org.hierarchy.Main','is sponsored by','is sponsored by','Sponsors, creates or charters another group, typically a committee or sub-committee for a particular task.','kuali.org.Sponsor')
/
INSERT INTO KSOR_ORG_ORG_RELTN_TYPE (EFF_DT,EXPIR_DT,NAME,ORG_HIRCHY,REV_DESCR,REV_NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Subcommittee','kuali.org.hierarchy.Main','has as a subcommittee ','has as a subcommittee ','Indicates this is a subcommittee of another committee or organization','kuali.org.Subcommittee')
/
