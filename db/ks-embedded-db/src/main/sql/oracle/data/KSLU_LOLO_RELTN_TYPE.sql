TRUNCATE TABLE KSLU_LOLO_RELTN_TYPE DROP STORAGE
/
INSERT INTO KSLU_LOLO_RELTN_TYPE (DESCR,EFF_DT,EXPIR_DT,ID,NAME,REV_DESCR,REV_NAME)
  VALUES ('',TO_DATE( '20080101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20100101000000', 'YYYYMMDDHH24MISS' ),'kuali.lo.relation.type.inSupportOf','inSupportOf','','supports')
/
INSERT INTO KSLU_LOLO_RELTN_TYPE (DESCR,EFF_DT,EXPIR_DT,ID,NAME,REV_DESCR,REV_NAME)
  VALUES ('Parent-child relationship between a parent LO and sub LO. Currently used in the context of LOs that are related within a single CLU.',TO_DATE( '20080101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20100101000000', 'YYYYMMDDHH24MISS' ),'kuali.lo.relation.type.includes','includes','Child-parent relationship between a child LO and an LO that includes the child.  Currently used in the context of LOs that are related within a single CLU.','is-included-by')
/
