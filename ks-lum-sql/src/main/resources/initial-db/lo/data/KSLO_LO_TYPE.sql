TRUNCATE TABLE KSLO_LO_TYPE DROP STORAGE
/
INSERT INTO KSLO_LO_TYPE (DESCR,EFF_DT,ID,NAME,OBJ_ID,VER_NBR)
  VALUES ('LO governed by an organization external to department, e.g., the college at large, or a state or accrediting organization',TO_DATE( '20080101000000', 'YYYYMMDDHH24MISS' ),'kuali.lo.type.governed','Governed','602171427CC94F7FB0A55829AED3F082',0)
/
INSERT INTO KSLO_LO_TYPE (DESCR,EFF_DT,ID,NAME,OBJ_ID,VER_NBR)
  VALUES ('LO created in support of programs or courses, e.g., faculty-inspired additional LO for a course',TO_DATE( '20080101000000', 'YYYYMMDDHH24MISS' ),'kuali.lo.type.singleUse','Single use','77E6218ADA8A4C049F8D0A1F1DC94F99',0)
/
