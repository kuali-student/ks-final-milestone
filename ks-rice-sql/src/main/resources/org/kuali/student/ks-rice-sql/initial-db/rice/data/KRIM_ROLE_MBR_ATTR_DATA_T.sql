TRUNCATE TABLE KRIM_ROLE_MBR_ATTR_DATA_T DROP STORAGE
/
-- Note added by Bonnie
-- We should pull out the authz setup out of ks-rice-sql.
-- Below is the reference implementation.
-- After switching to UMD reference data, 1000ENGL is not the correct value
-- it is changed to 2677933260. I'll make the update script to fix this problem
-- under ks-enroll-sql
INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,ROLE_MBR_ID,VER_NBR)
  VALUES ('10097','1000ENGL','114','114','D2F07FCB2ECCFFC9E040760A4A45207E','10036',1)
/
INSERT INTO KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,ROLE_MBR_ID,VER_NBR)
  VALUES ('10098','N','117','114','D2F07FCB2ECDFFC9E040760A4A45207E','10036',1)
/
