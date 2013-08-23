TRUNCATE TABLE KSLO_LO_RELTN_TYPE DROP STORAGE
/
INSERT INTO KSLO_LO_RELTN_TYPE (EFF_DT,ID,NAME,OBJ_ID,REV_NAME,VER_NBR)
  VALUES (TO_DATE( '20080101000000', 'YYYYMMDDHH24MISS' ),'kuali.lo.relation.type.inSupportOf','inSupportOf','6DFADEAABF6140689F8B6A10DFCFDAFF','supports',0)
/
INSERT INTO KSLO_LO_RELTN_TYPE (DESCR,EFF_DT,ID,NAME,OBJ_ID,REV_DESCR,REV_NAME,VER_NBR)
  VALUES ('Parent-child relationship between a parent LO and sub LO. Currently used in the context of LOs that are related within a single CLU.',TO_DATE( '20080101000000', 'YYYYMMDDHH24MISS' ),'kuali.lo.relation.type.includes','includes','543596F0443D427CB8A3ADC65E68949C','Child-parent relationship between a child LO and an LO that includes the child.  Currently used in the context of LOs that are related within a single CLU.','is-included-by',0)
/
