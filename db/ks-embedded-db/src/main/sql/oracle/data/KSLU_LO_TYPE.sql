TRUNCATE TABLE KSLU_LO_TYPE DROP STORAGE
/
INSERT INTO KSLU_LO_TYPE (DESCR,EFF_DT,EXPIR_DT,ID,NAME)
  VALUES ('LO governed by an organization external to department, e.g., the college at large, or a state or accrediting organization',TO_DATE( '20080101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20100101000000', 'YYYYMMDDHH24MISS' ),'kuali.lo.type.governed','Governed')
/
INSERT INTO KSLU_LO_TYPE (DESCR,EFF_DT,EXPIR_DT,ID,NAME)
  VALUES ('LO created in support of programs or courses, e.g., faculty-inspired additional LO for a course',TO_DATE( '20080101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20100101000000', 'YYYYMMDDHH24MISS' ),'kuali.lo.type.singleUse','Single use')
/
