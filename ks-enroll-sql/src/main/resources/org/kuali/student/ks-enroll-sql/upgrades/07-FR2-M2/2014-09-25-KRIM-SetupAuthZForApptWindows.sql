--KSENROLL-14989 Put authorization on Manage Registration Windows and Appointments link

--Create a view permission for view id registrationWindowsManagementView
insert into KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
values ('Y', 'Allows the user to Open Views for Appointment Window', 'Open Views for Appointment Window', 'KS-ENR', 'E2F17FCB1E7EFFA9E040760A4A45207E', 'KS-KRIM-PERM-4004', 'KS-KRIM-PERM-TMPL-1000', 1)
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, ATTR_VAL, KIM_ATTR_DEFN_ID, KIM_TYP_ID, OBJ_ID, PERM_ID, VER_NBR)
values ('KS-KRIM-PERM-ATTR-DATA-4007', 'registrationWindowsManagementView', '47', '1', '1488072BB63E6C71E050440A9B9A1ED2', 'KS-KRIM-PERM-4004', 1)
/


--KSENROLL-15094 Add new role specifically for Appt Windows w/ martha & admin in it

--Create the new role
insert into KRIM_ROLE_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, LAST_UPDT_DT, NMSPC_CD, OBJ_ID, ROLE_ID, ROLE_NM, VER_NBR)
values ('Y', 'KS Administrator with global abilities Appointment Windows Permission', 1, SYSDATE, 'KS-ENR', '0d71c654-667f-4c3c-b9e0-af52b5e4d08f', 'KS-10008', 'Appointment Windows Personnel Role', 1)
/


--Add 'martha' & 'admin' to the new role
insert into KRIM_ROLE_MBR_T (LAST_UPDT_DT, MBR_ID, MBR_TYP_CD, OBJ_ID, ROLE_ID, ROLE_MBR_ID, VER_NBR)
values (SYSDATE, 'admin', 'P', '06ba8d9e-a2ca-449e-bceb-17b9d3b12913', 'KS-10008', 'KS-10014', 1)
/

insert into KRIM_ROLE_MBR_T (LAST_UPDT_DT, MBR_ID, MBR_TYP_CD, OBJ_ID, ROLE_ID, ROLE_MBR_ID, VER_NBR)
values (SYSDATE, 'martha', 'P', 'b0cbf342-2dc9-444e-a92f-f9bb5054a9da', 'KS-10008', 'KS-10015', 1)
/


--Tie the permission to the new role
insert into KRIM_ROLE_PERM_T (ACTV_IND, OBJ_ID, PERM_ID, ROLE_ID, ROLE_PERM_ID, VER_NBR)
values ('Y', 'faa57c50-83cd-423c-ab56-fdc7ad4b0c09', 'KS-KRIM-PERM-4004', 'KS-10008', 'KS-KRIM-ROLE-PERM-4006', 1)
/
