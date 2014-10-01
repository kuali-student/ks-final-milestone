/*KSENROLL-1585
As a  Central Registration Personnel I want to  be the appropriate Role to
have access to Apply Holds to students  so that  not any person that have access
to the system*/
--////////////////////////////////////////////////////////////////////////
--Insert a permission --krim_perm_t
--////////////////////////////////////////////////////////////////////////
insert into KRIM_PERM_T
  (PERM_ID,
   OBJ_ID,
   VER_NBR,
   PERM_TMPL_ID,
   NMSPC_CD,
   NM,
   DESC_TXT,
   ACTV_IND)
values
  ('KS-KRIM-PERM-'||KRIM_PERM_ID_S.NEXTVAL,
   SYS_GUID(),
   1,
   (SELECT perm_tmpl_id
      FROM krim_perm_tmpl_t
     where nm = 'Open View'
       and nmspc_cd = 'KS-ENR'),
   'KS-ENR',
   'Apply Holds Personnel Permission',
   'Allows the user to Apply Holds to Students',
   'Y')
/
--////////////////////////////////////////////////////////////////////////
--Insert a Role --krim_role_t
--////////////////////////////////////////////////////////////////////////
insert into KRIM_ROLE_T
  (ROLE_ID,
   OBJ_ID,
   VER_NBR,
   ROLE_NM,
   NMSPC_CD,
   DESC_TXT,
   KIM_TYP_ID,
   ACTV_IND,
   LAST_UPDT_DT)
values
  ('KS-'||KRIM_ROLE_ID_S.NEXTVAL,
   SYS_GUID(),
   1,
   'Apply Holds Personnel Role',
   'KS-ENR',
   'KS Administrator with global abilities Apply Holds Personnel Permission',
   (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Default' AND NMSPC_CD='KUALI'),
   'Y',
   TO_DATE('01/01/2014', 'MM/DD/YYYY'))
/
--////////////////////////////////////////////////////////////////////////
--Insert a role member --Admin
--////////////////////////////////////////////////////////////////////////
insert into KRIM_ROLE_MBR_T
  (ROLE_MBR_ID,
   VER_NBR,
   OBJ_ID,
   ROLE_ID,
   MBR_ID,
   MBR_TYP_CD,
   ACTV_FRM_DT,
   ACTV_TO_DT,
   LAST_UPDT_DT)
values
  ('KS-'||KRIM_ROLE_MBR_ID_S.NEXTVAL,
   1,
   SYS_GUID(),
   (SELECT ROLE_ID
      FROM KRIM_ROLE_T
     where ROLE_NM = 'Apply Holds Personnel Role'
       and nmspc_cd = 'KS-ENR'),
   'admin',
   'P',
   '',
   '',
   TO_DATE('01/01/2014', 'MM/DD/YYYY'))
/
--////////////////////////////////////////////////////////////////////////
--Insert a role member --Martha
--////////////////////////////////////////////////////////////////////////
insert into KRIM_ROLE_MBR_T
  (ROLE_MBR_ID,
   VER_NBR,
   OBJ_ID,
   ROLE_ID,
   MBR_ID,
   MBR_TYP_CD,
   ACTV_FRM_DT,
   ACTV_TO_DT,
   LAST_UPDT_DT)
values
  ('KS-'||KRIM_ROLE_MBR_ID_S.NEXTVAL,
   1,
   SYS_GUID(),
   (SELECT ROLE_ID
      FROM KRIM_ROLE_T
     where ROLE_NM = 'Apply Holds Personnel Role'
       and nmspc_cd = 'KS-ENR'),
   'martha',
   'P',
   '',
   '',
   TO_DATE('01/01/2014', 'MM/DD/YYYY'))
/
--////////////////////////////////////////////////////////////////////////
--Insert a role member --Carol
--////////////////////////////////////////////////////////////////////////
insert into KRIM_ROLE_MBR_T
  (ROLE_MBR_ID,
   VER_NBR,
   OBJ_ID,
   ROLE_ID,
   MBR_ID,
   MBR_TYP_CD,
   ACTV_FRM_DT,
   ACTV_TO_DT,
   LAST_UPDT_DT)
values
  ('KS-'||KRIM_ROLE_MBR_ID_S.NEXTVAL,
   1,
   SYS_GUID(),
   (SELECT ROLE_ID
      FROM KRIM_ROLE_T
     where ROLE_NM = 'Apply Holds Personnel Role'
       and nmspc_cd = 'KS-ENR'),
   'carol',
   'P',
   '',
   '',
   TO_DATE('01/01/2014', 'MM/DD/YYYY'))
/
--////////////////////////////////////////////////////////////////////////
-- Role = Permision --krim_role_perm_t
--////////////////////////////////////////////////////////////////////////
insert into KRIM_ROLE_PERM_T
  (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values
  ('KS-KRIM-ROLE-PERM-'||KRIM_ROLE_PERM_ID_S.NEXTVAL,
   SYS_GUID(),
   1,
   (SELECT ROLE_ID
      FROM KRIM_ROLE_T
     where ROLE_NM = 'Apply Holds Personnel Role'
       and nmspc_cd = 'KS-ENR'),
   (SELECT perm_id
      from krim_perm_t
     where nm = 'Apply Holds Personnel Permission'
       and nmspc_cd = 'KS-ENR'),
   'Y')
/
--////////////////////////////////////////////////////////////////////////
--permission = atribute --krim_perm_attr_data_t
--////////////////////////////////////////////////////////////////////////
insert into KRIM_PERM_ATTR_DATA_T
  (ATTR_DATA_ID,
   OBJ_ID,
   VER_NBR,
   PERM_ID,
   KIM_TYP_ID,
   KIM_ATTR_DEFN_ID,
   ATTR_VAL)
values
  ('KS-KRIM-PERM-ATTR-DATA-'||KRIM_ATTR_DATA_ID_S.NEXTVAL,
   SYS_GUID(),
   1,
   (SELECT perm_id
      from krim_perm_t
     where nm = 'Apply Holds Personnel Permission'
       and nmspc_cd = 'KS-ENR'),
   (SELECT kim_typ_id
      from krim_typ_t
     where nm = 'Default'
       and nmspc_cd = 'KUALI'),
   (SELECT MIN(TO_NUMBER(kim_attr_defn_id))
      from krim_attr_defn_t
     where nm = 'viewId'
       and nmspc_cd = 'KR-KRAD'),
   'KS-AppliedHoldManagementView')
/