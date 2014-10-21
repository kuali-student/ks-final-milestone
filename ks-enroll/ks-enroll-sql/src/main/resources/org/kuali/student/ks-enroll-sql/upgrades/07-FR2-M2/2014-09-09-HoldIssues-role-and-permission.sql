--////////////////////////////////////////////////////////////////////////
-- Org Group type definition
--////////////////////////////////////////////////////////////////////////
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
  values ('KS-KRIM-ATTR-DEFN-1003', SYS_GUID(), 1, 'org.kuali.student.hold.authorization.orgId', 'Hold Authorization Org Id', 'Y',
    'KS-HLD', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/

insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
  values ('KS-KRIM-TYP-1011', SYS_GUID(), 1, 'KS Hold Org Authorization Group Type', '{http://student.kuali.org/kim}kimGroupTypeService', 'Y', 'KS-HLD')
/

insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
  values ('KS-KRIM-TYP-ATTR-1027', SYS_GUID(), 1, 'a',
    (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS Hold Org Authorization Group Type'),
    (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='org.kuali.student.hold.authorization.orgId' and NMSPC_CD='KS-HLD'), 'Y')
/

--////////////////////////////////////////////////////////////////////////
-- Hold Issue Role type definition
--////////////////////////////////////////////////////////////////////////
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
  values ('KS-KRIM-ATTR-DEFN-1004', SYS_GUID(), 1, 'org.kuali.student.hold.authorization.holdIssueIds', 'Hold Issue Authorization', 'Y',
    'KS-HLD', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/

insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
  values ('KS-KRIM-TYP-1012', SYS_GUID(), 1, 'KS Hold Issue Authorization Role Type', '{http://student.kuali.org/kim}kimGroupTypeService', 'Y', 'KS-HLD')
/

insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
  values ('KS-KRIM-TYP-ATTR-1028', SYS_GUID(), 1, 'a',
    (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS Hold Issue Authorization Role Type'),
    (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='org.kuali.student.hold.authorization.holdIssueIds' and NMSPC_CD='KS-HLD'), 'Y')
/

--////////////////////////////////////////////////////////////////////////
-- Insert apply hold permission, role and permission-role relationship.
--////////////////////////////////////////////////////////////////////////

insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
  values ('KS-KRIM-TYP-1013', SYS_GUID(), 1, 'KS Hold Issue Authorization Permission Type', '{http://student.kuali.org/kim}defaultPermissionTypeService', 'Y', 'KS-HLD')
/

insert into KRIM_PERM_TMPL_T (PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID, ACTV_IND)
  values ('KS-KRIM-PERM-TMPL-1015', SYS_GUID(), 1, 'KS-HLD', 'Manage Hold', 'Manage Hold',
    (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS Hold Issue Authorization Permission Type' AND NMSPC_CD='KS-HLD'), 'Y')
/

--////////////////////////////////////////////////////////////////////////
-- Insert apply hold permission, role and permission-role relationship.
--////////////////////////////////////////////////////////////////////////
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  values ('KS-KRIM-PERM-'||KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1,
    (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Manage Hold' and nmspc_cd = 'KS-HLD'),
    'KS-HLD', 'Allow Organizations to Apply Hold to Student', 'Perform Action to Apply Hold to Students', 'Y')
/

insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values ('KS-KRIM-PERM-ATTR-DATA-4005', SYS_GUID(), 1,
  (SELECT perm_id from krim_perm_t where nm = 'Allow Organizations to Apply Hold to Student' and nmspc_cd = 'KS-HLD'),
  (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'),
  (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'Apply Hold')
/

insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
  values ('KS-'||KRIM_ROLE_ID_S.NEXTVAL, SYS_GUID(), 1, 'Hold Apply Hold Role', 'KS-HLD',
    'Organizations that can Apply Hold to Students',
    (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS Hold Issue Authorization Role Type' AND NMSPC_CD='KS-HLD'),
    'Y', TO_DATE('01/01/2014', 'MM/DD/YYYY'))
/

insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values ('KS-KRIM-ROLE-PERM-'||KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1,
   (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Hold Apply Hold Role' and nmspc_cd = 'KS-HLD'),
   (SELECT perm_id from krim_perm_t where nm = 'Allow Organizations to Apply Hold to Student' and nmspc_cd = 'KS-HLD'),
   'Y')
/

--////////////////////////////////////////////////////////////////////////
-- Insert expire hold permission, role and permission-role relationship.
--////////////////////////////////////////////////////////////////////////
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  values ('KS-KRIM-PERM-'||KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1,
   (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Manage Hold' and nmspc_cd = 'KS-HLD'),
   'KS-HLD', 'Allow Organizations to Expire Applied Hold to Student', 'Perform Action to Expire Applied Hold to Students',
   'Y')
/

insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values ('KS-KRIM-PERM-ATTR-DATA-4006', SYS_GUID(), 1,
  (SELECT perm_id from krim_perm_t where nm = 'Allow Organizations to Expire Applied Hold to Student' and nmspc_cd = 'KS-HLD'),
  (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'),
  (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'Expire Hold')
/

insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
  values ('KS-'||KRIM_ROLE_ID_S.NEXTVAL, SYS_GUID(), 1, 'Hold Expire Applied Hold Role',
   'KS-HLD', 'Organizations that can Expire Applied Hold to Students',
   (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS Hold Issue Authorization Role Type' AND NMSPC_CD='KS-HLD'),
   'Y', TO_DATE('01/01/2014', 'MM/DD/YYYY'))
/

insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values ('KS-KRIM-ROLE-PERM-'||KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1,
   (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Hold Expire Applied Hold Role' and nmspc_cd = 'KS-HLD'),
   (SELECT perm_id from krim_perm_t where nm = 'Allow Organizations to Expire Applied Hold to Student' and nmspc_cd = 'KS-HLD'),
   'Y')
/

