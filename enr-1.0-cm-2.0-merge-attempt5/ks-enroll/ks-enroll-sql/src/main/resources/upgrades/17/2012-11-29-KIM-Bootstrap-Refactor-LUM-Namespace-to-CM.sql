--Fix namespace typo
update KRIM_ROLE_T set KRIM_ROLE_T.NMSPC_CD='KS-SYS' where KRIM_ROLE_T.NMSPC_CD='KS_SYS'
/


--Rename KS-LUM namespace to KS-CM
update KRIM_ATTR_DEFN_T set NMSPC_CD = 'KS-CM' WHERE NMSPC_CD='KS-LUM'
/
update KRIM_PERM_T set NMSPC_CD = 'KS-CM' WHERE NMSPC_CD='KS-LUM'
/
update KRIM_ROLE_T set NMSPC_CD = 'KS-CM' WHERE NMSPC_CD='KS-LUM'
/
update KRIM_RSP_T set NMSPC_CD = 'KS-CM' WHERE NMSPC_CD='KS-LUM'
/
update KRIM_TYP_T set NMSPC_CD = 'KS-CM' WHERE NMSPC_CD='KS-LUM'
/


--Insert and delete old code with new code
insert into KRCR_NMSPC_T (NMSPC_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, APPL_ID) values ('KS-CM', 'F102F3FA08CF45CFAA404FBB89D831AB', 1, 'Kuali Student Learning Unit Management', 'Y', 'STUDENT')
/
delete from KRCR_NMSPC_T WHERE NMSPC_CD='KS-LUM'
/
