INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) VALUES ( KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NM = 'Edit Field' AND NMSPC_CD = 'KS-ENR'), 'KS-ENR', 'Edit Field for Non-Standard Time-Slot Approval Flag', 'Allows the user to Edit Field for Non-Standard Time-Slot Approval Flag', 'Y')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) VALUES ( KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Edit Field for Non-Standard Time-Slot Approval Flag' AND NMSPC_CD = 'KS-ENR'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM = 'Default' AND NMSPC_CD = 'KUALI'), (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'fieldId' AND NMSPC_CD = 'KR-KRAD'), 'isApprovedForNonStandardTimeSlots')
/
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) VALUES (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'KS Schedule Coordinator' AND NMSPC_CD = 'KS-ENR'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Edit Field for Non-Standard Time-Slot Approval Flag' AND NMSPC_CD = 'KS-ENR'), 'Y')
/