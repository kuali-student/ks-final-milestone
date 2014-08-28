--Apply Hold Role
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
     where nm = 'Apply Hold'
       and nmspc_cd = 'KS-HLD'),
   'KS-HLD',
   'Allow Organizations to Apply Hold to Student',
   'Perform Action to Apply Hold to Students',
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
   'Hold Apply Hold Role',
   'KS-HLD',
   'Organizations that can Apply Hold to Students',
   (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Default' AND NMSPC_CD='KUALI'),
   'Y',
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
     where ROLE_NM = 'Hold Apply Hold Role'
       and nmspc_cd = 'KS-HLD'),
   (SELECT perm_id
      from krim_perm_t
     where nm = 'Allow Organizations to Apply Hold to Student'
       and nmspc_cd = 'KS-HLD'),
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
     where nm = 'Allow Organizations to Apply Hold to Student'
       and nmspc_cd = 'KS-HLD'),
   (SELECT kim_typ_id
      from krim_typ_t
     where nm = 'Default'
       and nmspc_cd = 'KUALI'),
   (SELECT MIN(TO_NUMBER(kim_attr_defn_id))
      from krim_attr_defn_t
     where nm = 'viewId'
       and nmspc_cd = 'KR-KRAD'),
   'KS-HoldIssueMaintenanceView')
/


--Expire Applied Hold Role
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
     where nm = 'Expire Applied Hold'
       and nmspc_cd = 'KS-HLD'),
   'KS-HLD',
   'Allow Organizations to Expire Applied Hold to Student',
   'Perform Action to Expire Applied Hold to Students',
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
   'Hold Expire Applied Hold Role',
   'KS-HLD',
   'Organizations that can Expire Applied Hold to Students',
   (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Default' AND NMSPC_CD='KUALI'),
   'Y',
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
     where ROLE_NM = 'Hold Expire Applied Hold Role'
       and nmspc_cd = 'KS-HLD'),
   (SELECT perm_id
      from krim_perm_t
     where nm = 'Allow Organizations to Expire Applied Hold to Student'
       and nmspc_cd = 'KS-HLD'),
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
     where nm = 'Allow Organizations to Expire Applied Hold to Student'
       and nmspc_cd = 'KS-HLD'),
   (SELECT kim_typ_id
      from krim_typ_t
     where nm = 'Default'
       and nmspc_cd = 'KUALI'),
   (SELECT MIN(TO_NUMBER(kim_attr_defn_id))
      from krim_attr_defn_t
     where nm = 'viewId'
       and nmspc_cd = 'KR-KRAD'),
   'KS-HoldIssueMaintenanceView')
/

