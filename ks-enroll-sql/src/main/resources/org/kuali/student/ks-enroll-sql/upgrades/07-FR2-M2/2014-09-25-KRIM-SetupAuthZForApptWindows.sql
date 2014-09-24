--KSENROLL-14989 Put authorization on Manage Registration Windows and Appointments link

--Create a view permission for view id registrationWindowsManagementView
insert into KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
values ('Y', 'Allows the user to Open Views for Appointment Window', 'Open Views for Appointment Window', 'KS-ENR', 'E2F17FCB1E7EFFA9E040760A4A45207E', 'KS-KRIM-PERM-4004', 'KS-KRIM-PERM-TMPL-1000', 1)
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, ATTR_VAL, KIM_ATTR_DEFN_ID, KIM_TYP_ID, OBJ_ID, PERM_ID, VER_NBR)
values ('KS-KRIM-PERM-ATTR-DATA-4007', 'registrationWindowsManagementView', '47', '1', '1488072BB63E6C71E050440A9B9A1ED2', 'KS-KRIM-PERM-4004', 1)
/

--Tie the permission to the 'Central Registration Personnel Role'
insert into KRIM_ROLE_PERM_T (ACTV_IND, OBJ_ID, PERM_ID, ROLE_ID, ROLE_PERM_ID, VER_NBR)
values ('Y', '1477072DD1386C71E050440A9B9A3CE3', 'KS-KRIM-PERM-4004', 'KS-10003', 'KS-KRIM-ROLE-PERM-4005', 1)
/
