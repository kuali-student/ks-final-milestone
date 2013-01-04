--INSERT KRIM TYP
--KS Permission       ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '1F81F05B5CF34104ACA44835D23F1B02', 1, 'KS Permission', 'permissionPermissionTypeService', 'Y', 'KS-SYS')
/

--Field Permission Type 10001   ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '6BB6E5FF4A84453EA1917E69FF0CF644', 1, 'Field Permission Type', null, 'Y', 'KS-SYS')
/
insert into KRIM_PERM_TMPL_T (PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID, ACTV_IND)
values (krim_perm_tmpl_id_s.nextval, 'DCBA154A16824CA4B4575E7501F213D7', 1, 'KS-SYS', 'Field Access', null, krim_typ_id_s.currval, 'Y')
/
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, 'B1D601FDB62E471787DAA28241FFA2C1', 1, 'dtoName', 'DTO Name', 'Y', 'KS-SYS', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, 'C70BC64405874384867C404AF5061607', 1, 'a', krim_typ_id_s.currval, krim_attr_defn_id_s.currval, 'Y')
/
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, 'FB19E7AA23314F0BACBD835A49051111', 1, 'dtoFieldKey', 'DTO Field Key', 'Y', 'KS-SYS', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, 'BD5707211D4840CF848C9AF1FEB00E93', 1, 'b', krim_typ_id_s.currval, krim_attr_defn_id_s.currval, 'Y')
/
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, '2786B2CE01F548988C3682844CDA73E4', 1, 'fieldAccessLevel', 'Field Access Level', 'Y', 'KS-SYS', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, '1FEB74F225594740879CABFD1DAEC8A6', 1, 'c', krim_typ_id_s.currval, krim_attr_defn_id_s.currval, 'Y')
/

--Derived Role: KS Non-Adhoc Action Request Role Type 10002 ****************************************************************************************************************************** TYPES
select krim_role_perm_id_s.nextval from dual
/
select krim_role_perm_id_s.nextval from dual
/
select krim_role_perm_id_s.nextval from dual
/
select krim_role_perm_id_s.nextval from dual
/
select krim_role_perm_id_s.nextval from dual
/
select krim_role_perm_id_s.nextval from dual
/
select krim_role_perm_id_s.nextval from dual
/
select krim_role_perm_id_s.nextval from dual
/
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, 'DA7BC49509DA446F89EA27EC3BBBBB33', 1, 'Derived Role: KS Non-Adhoc Action Request Role Type', 'ksNonAdhocActionRequestDerivedRoleTypeServiceImpl', 'Y', 'KS-SYS')
/
--Derived Role: Approve Request Recipient 10004
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '650D38DC8AAF403EB079E4E75C174916', 1, 'Derived Role: Approve Request Recipient', 'KS-SYS', null, krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '6A8575BDB5EF42BF8C08572EB47AC9FE', 1, krim_role_id_s.currval, 2911, 'Y')
/
--Derived Role: Acknowledge Request Recipient 10005
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '0B5ED576BAC04D0BAE39297C358FCEEA', 1, 'Derived Role: Acknowledge Request Recipient', 'KS-SYS', null, krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
--Derived Role: FYI Request Recipient 10006
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '65A4C495C9CB480A8F27B2F456412B17', 1, 'Derived Role: FYI Request Recipient', 'KS-SYS', null, krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/

--Derived Role: KS Route Log Role Type 10003 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, 'A5703EF5E89245BF8B9D53F2C92B52C0', 1, 'Derived Role: KS Route Log Role Type', 'ksRouteLogDerivedRoleTypeServiceImpl', 'Y', 'KS-SYS')
/
--Derived Role: Initiator or Reviewer 10007
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '4F385F234C024DDA8F0CA82B6DDB783F', 1, 'Derived Role: Initiator or Reviewer', 'KS-SYS', null, krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
--Derived Role: Router 10008
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '6F4E930FD9FC4AEDA1C3BE3CB33FF5F5', 1, 'Derived Role: Router', 'KS-SYS', null, krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/

--KS Use Screen 10016  **************************************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, 'BB5E34C754F7480E96D66EA4CEDAE1CC', 1, 'KS Use Screen', null, 'Y', 'KS-SYS')
/
insert into KRIM_PERM_TMPL_T (PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID, ACTV_IND)
values (krim_perm_tmpl_id_s.nextval, 'B1B7AF348A774BBAB697DC96484024E4', 1, 'KS-SYS', 'Use Screen', 'Used to create KS screen permissions', krim_typ_id_s.currval, 'Y')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, '04E2464825A94EC9BDCAD1E8B111A145', 1, 'a', krim_typ_id_s.currval, (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'Y')
/

--Derived Role: KS Document Editor Role Type  10004 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, 'E11230E220D44F5F941A6E1F8FA58B9F', 1, 'Derived Role: KS Document Editor Role Type', 'ksEditDocumentRoleTypeService', 'Y', 'KS-SYS')
/
--Derived Role: Document Editor 10009
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '98B9426DDC12414BA8CAE727C0054E7E', 1, 'Derived Role: Document Editor', 'KS-SYS', null, krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
--Add Collaborator Action 10009
insert into KRIM_PERM_TMPL_T (PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID, ACTV_IND)
values (krim_perm_tmpl_id_s.nextval, '2DB829B5913844329EE3807EEDF775A1', 1, 'KS-SYS', 'Add Collaborator Action', 'Used to define what actions a user can assign to collaborators', '1', 'Y')
/
--Add Collaborator with Acknowledge 10059
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'EDD9ADB13981434E8971DE96B67F3AD7', 1, krim_perm_tmpl_id_s.currval, 'KS-SYS', 'Add Collaborator with Acknowledge', null, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '81FA4B94929946099C9950FEB3645A96', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Authorizes users to remove adhoc reviewers to documents. 10062
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '45FCFDF311FA4A87B36763BFD5E00D75', 1, '66', 'KS-LUM', 'Remove Reviewers', 'Authorizes users to remove adhoc reviewers to documents.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'FC9B21E2669C40E5BBA576C0BFF01801', 1, krim_perm_id_s.currval, '3', '13', 'KualiStudentDocument')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '44625D91AD8A4904ADB51862DACBDBF2', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/

--Derived Role: KS Document Commenter Role Type  10005 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '6672DB350D2B4B10BC0576964C841EF7', 1, 'Derived Role: KS Document Commenter Role Type', 'ksCommentOnDocumentRoleTypeService', 'Y', 'KS-SYS')
/
--Derived Role: Document Commenter 10010
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '7EA6144D7F5F4319B7302980EEC8429A', 1, 'Derived Role: Document Commenter', 'KS-SYS', null, krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/

--Derived Role: Affiliation Role Type  10006 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '5770DDE11B57425EA4A1E9C680393AB7', 1, 'Derived Role: Affiliation Role Type', 'ksAffiliationService', 'Y', 'KS-SYS')
/
--Derived Role : Affiliation 10003
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '6A7A9ECA36004C5297CA338FCAA05F31', 1, 'Derived Role : Affiliation', 'KS-SYS', 'This role identifies users who are affiliates', krim_typ_id_s.currval, 'Y', to_date('16-05-2012 18:46:41', 'dd-mm-yyyy hh24:mi:ss'))
/

--College Type  10007 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, 'A33B989E890D4E0E957D46F4E7F393D0', 1, 'College Type', 'kimRoleTypeService', 'Y', 'KS-LUM')
/
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, '02C6842512DD437BA1F6754B218B6F02', 1, 'college', 'College', 'Y', 'KS-LUM', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, '78BF218F8BC345338BEFA760C76677FC', 1, 'a', krim_typ_id_s.currval, krim_attr_defn_id_s.currval, 'Y')
/

--Department Type 10008 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '8805C2ABC9B7424F9B6BC0D727D3587A', 1, 'Department Type', 'kimRoleTypeService', 'Y', 'KS-LUM')
/
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, '80F4E71A26F44605BB45693AE98BD02E', 1, 'department', 'Department', 'Y', 'KS-LUM', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, '8C7FA26E9CEB498BB6FDDC13EF058C93', 1, 'a', krim_typ_id_s.currval, krim_attr_defn_id_s.currval, 'Y')
/

--Division Type  10009 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, 'F35A0985ED2C4993B309746F83CFFD45', 1, 'Division Type', 'kimRoleTypeService', 'Y', 'KS-LUM')
/
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, 'FE0498D7750443B7A44E822B83507D63', 1, 'division', 'Division', 'Y', 'KS-LUM', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, '1B39BBA073AF4FDEAE98EF8B7E145043', 1, 'a', krim_typ_id_s.currval, krim_attr_defn_id_s.currval, 'Y')
/

--Derived Role: Org Admin Type  ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, 'A20E7508989E4C7CBC06B5238CFEFBFD', 1, 'Derived Role: Org Admin Type', 'ksOrgAdminRoleTypeService', 'Y', 'KS-SYS')
/

--Derived Role: Org Committee Type
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '74B9AA4080D340369E940F223A651CB0', 1, 'Derived Role: Org Committee Type', 'ksOrgCommitteeRoleTypeService', 'Y', 'KS-SYS')
/

--Derived Role: KS Adhoc Action Request Role Type 10012 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '41F8A3984A924E93A0EEBCB1567C769E', 1, 'Derived Role: KS Adhoc Action Request Role Type', 'ksAdhocActionRequestDerivedRoleTypeServiceImpl', 'Y', 'KS-SYS')
/
delete from KRIM_ROLE_T where ROLE_ID = '922'
/
--Adhoc Approve Request Recipient 10011
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, 'D37E230034BA4791A956F1B2E1868D33', 1, 'Adhoc Approve Request Recipient', 'KS-SYS', 'This role is used to adhoc add an user as an approve recepient', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
delete from KRIM_ROLE_T where ROLE_ID = '923'
/
--'Adhoc Acknowledge Request Recipient 10012
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, 'F526871D4D254163A81302CAD544522A', 1, 'Adhoc Acknowledge Request Recipient', 'KS-SYS', 'This role is used to adhoc add an user as a acknowledge request recepient', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
delete from KRIM_ROLE_T where ROLE_ID = '924'
/
--Adhoc FYI Request Recipient 10013
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '80C3A117311F4DE9A978BE957E82CFD7', 1, 'Adhoc FYI Request Recipient', 'KS-SYS', 'This role is used to adhoc add an user as a FYI request recepient', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/

--Adhoc Permissions Type  10013 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, 'F7E28DC5594D4F1F8AF1C6F81D93F8B5', 1, 'Adhoc Permissions Type', 'kimRoleTypeService', 'Y', 'KS-SYS')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, 'AD0EA2E8D43245FD8B67FAF006A264AA', 1, 'a', krim_typ_id_s.currval, '13', 'Y')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, '34BB9012B3264F2AA5F238EF018BC291', 1, 'b', krim_typ_id_s.currval, (select kim_attr_defn_id from krim_attr_defn_t where nm = 'dataId'), 'Y')
/
--Adhoc Permissions: Edit Document 10014
delete from KRIM_ROLE_T where ROLE_ID = '930'
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '838D7C8133CB468F9C06C5BFBD39519E', 1, 'Adhoc Permissions: Edit Document', 'KS-SYS', 'This role is for use with adding adhoc permissions for those who can edit a document.', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
--Adhoc Permissions: Comment on Document 10015
delete from KRIM_ROLE_T where ROLE_ID = '931'
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '822386F5E3FD4C93A2B6C695F1EC203F', 1, 'Adhoc Permissions: Comment on Document', 'KS-SYS', 'This role is for use with adding adhoc permissions for those who can comment on a document.', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/

--Derived Role: KS Completed Adhoc Action Request Role Type  10014 ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '64517DC4265D4F9A8DB4E4A1F3AE5899', 1, 'Derived Role: KS Completed Adhoc Action Request Role Type', 'ksAdhocDoneActionRequestDerivedRoleTypeServiceImpl', 'Y', 'KS-SYS')
/
--Completed Adhoc Approve Request Recipient 10019
delete from KRIM_ROLE_T where ROLE_ID = '932'
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '5A273700DDEF45DB86D9060F2A79E0F5', 1, 'Completed Adhoc Approve Request Recipient', 'KS-SYS', 'Role to find users who have an Adhoc Approve action request that had been active and is now complete.', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:55', 'dd-mm-yyyy hh24:mi:ss'))
/
--Completed Adhoc Acknowledge Request Recipient 10020
delete from KRIM_ROLE_T where ROLE_ID = '933'
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, 'CD3BED569F2740E18FBA023088C82CFD', 1, 'Completed Adhoc Acknowledge Request Recipient', 'KS-SYS', 'Role to find users who have an Adhoc Acknowledge action request that had been active and is now complete.', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:55', 'dd-mm-yyyy hh24:mi:ss'))
/
--Completed Adhoc FYI Request Recipient 10021
delete from KRIM_ROLE_T where ROLE_ID = '934'
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '28F89BDFF2F84DB99845E0E7458C5A17', 1, 'Completed Adhoc FYI Request Recipient', 'KS-SYS', 'Role to find users who have an Adhoc FYI action request that had been active and is now complete.', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:55', 'dd-mm-yyyy hh24:mi:ss'))
/

--Derived Role: KS Completed Non-Adhoc Action Request Role Type 10015  ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '25BB3A80078F4CCCB81E6733DDE21A5E', 1, 'Derived Role: KS Completed Non-Adhoc Action Request Role Type', 'ksNonAdhocDoneActionRequestDerivedRoleTypeServiceImpl', 'Y', 'KS-SYS')
/
--Completed Approve Request Recipient 10016
delete from KRIM_ROLE_T where ROLE_ID = '926'
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, 'D6AD0AE3961A4C29A8D18543C99EA3AD', 1, 'Completed Approve Request Recipient', 'KS-SYS', 'Role to find users who have an Approve action request that had been active and is now complete.', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:55', 'dd-mm-yyyy hh24:mi:ss'))
/
--Completed Acknowledge Request Recipient 10017
delete from KRIM_ROLE_T where ROLE_ID = '927'
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, 'AFFA8C68B26A4F509A759B3F933BC3E9', 1, 'Completed Acknowledge Request Recipient', 'KS-SYS', 'Role to find users who have an Acknowledge action request that had been active and is now complete.', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:55', 'dd-mm-yyyy hh24:mi:ss'))
/
--Completed FYI Request Recipient 10018
delete from KRIM_ROLE_T where ROLE_ID = '928'
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '9FFC5C4E15524E138B28CA376AC522D3', 1, 'Completed FYI Request Recipient', 'KS-SYS', 'Role to find users who have an FYI action request that had been active and is now complete.', krim_typ_id_s.currval, 'Y', to_date('09-05-2012 16:38:55', 'dd-mm-yyyy hh24:mi:ss'))
/

--Document Type and Route Node 10017   ****************************************************************************************************************************** TYPES
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
values (krim_typ_id_s.nextval, '7779A0C257B64A9A8D4AB628BF8B9FAE', 1, 'Document Type and Route Node', null, 'Y', 'KS-SYS')
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '760'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '761'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '762'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '763'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '851'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '852'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '853'
/
delete from KRIM_PERM_T where PERM_ID = '2900'
/
delete from KRIM_PERM_TMPL_T where PERM_TMPL_ID = '60'
/
--Open Document 10001
insert into KRIM_PERM_TMPL_T (PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID, ACTV_IND)
values (krim_perm_tmpl_id_s.nextval, '7F4307DC63294E1395A1F3BA0224A19B', 1, 'KS-SYS', 'Open Document', null, krim_typ_id_s.currval, 'Y')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, 'CCDC0A2CC0DF4C1983B5164B780C3975', 1, 'a', krim_typ_id_s.currval, '13', 'Y')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (krim_typ_attr_id_s.nextval, 'DE41064E12254586A9F8CF86E79E9ACD', 1, 'a', krim_typ_id_s.currval, '15', 'Y')
/

--Student System User Role 10000
delete from KRIM_ROLE_T where ROLE_ID = '899'
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, '35FDD79F13F94FFABC40A7EBA98846B2', 1, 'Student System User Role', 'KS-SYS', 'This role provides system user privileges in KS CM Application', '1', 'Y', to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, '2C94749F29164D08B765325A1BD8D013', krim_role_mbr_id_s.currval, '5', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
--Administer Routing for Document 10056
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'FE7E0DCE1FEB432988539F7B3F43CD44', 1, krim_role_id_s.currval, (select perm_id from krim_perm_t where nm = 'Administer Routing for Document' and nmspc_cd = 'KS-SYS'), 'Y')
/

--Kuali Student CM Admin 10001
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, 'BBD44C99C8074ACDAC550F90D605E456', 1, 'Kuali Student CM Admin', 'KS-SYS', 'This role provides adminstrative privileges to KS CM application.(eg. Admin Screens)', '1', 'Y', to_date('16-05-2012 18:46:41', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CMA_ROLE_MEMBER_28', krim_role_id_s.currval, '5', 'P', null, null, to_date('18-06-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CMA_ROLE_MEMBER_27', krim_role_id_s.currval, 'admin', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
--Assign Role 10000
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'F9D4A7F349A347029F6586AFB66449E3', 1, krim_role_id_s.currval, (select perm_id from krim_perm_t where nm = 'Assign Role' and nmspc_cd = 'KS-SYS'), 'Y')
/
--Allows users to access the Course modification screen 10057
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '5C22F2B6FFBD4B05AD568C0668ED8D0F', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Course Modify Screens', 'Allows users to access the Course modification screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '3C35C6B1E15044B8870A539F2ACE8FC6', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'cluModifyItem')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '205A01EFD38C4C0ABD5DF2D531A4F2ED', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Grant Permission 10001
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '8DD698813BE64194BA2C16E014CA7CA1', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Grant Responsibility 10002
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '435D5CB9A75B4702BE0EFEAC8469A9DE', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Populate Group 10003
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '4FB51064C722414286E1BF7E0E0DEAF6', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows the user to use the curriculum review checkbox 10004
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '802272BDA248410ABA357430652EEE4A', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Curriculum Review', 'Allows the user to use the curriculum review checkbox', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '05756B4EABFF480BB35AE9E4FAA25694', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useCurriculumReview')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '127D9797DE884887892DB6A70D18C7EB', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows the user to modify the clu without a version 10005
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '3E09FB90069B4BC9928633B832DBE95F', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Modify Clu Without Version Dialog', 'Allows the user to modify the clu without a version', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '2ED91BEA6D424C039F6C28FD30109638', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'cluModifyItem')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '3FAD9F19C1994C4B936A0B1F2358BB37', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allow user to create course admin proposal 10008
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '10154D15D2AB4069B42419F533F9CE80', 1, '10', 'KS-SYS', 'Create Course By Admin Proposal', 'Allow user to create course admin proposal', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '02378662DF354D94B5683A0810D6C586', 1, krim_perm_id_s.currval, '3', '13', 'kuali.proposal.type.course.create.admin')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'F736FB1EC9314929834347ECF931E607', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allow user to create modify course admin proposal 10009
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '24B0E0F9E21140649FA6BF1D395B3A10', 1, '10', 'KS-SYS', 'Modify Course By Admin Proposal', 'Allow user to create modify course admin proposal', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'C6CF19875A824352B6CCC48BD23B71E3', 1, krim_perm_id_s.currval, '3', '13', 'kuali.proposal.type.course.modify.admin')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '372612B0FED54BB09B5E832B6BB1556E', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allow user to create a program proposal 10010
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '3F6AB347B1614BDCBE3C862DB2293CBC', 1, '10', 'KS-SYS', 'Create Program By Proposal', 'Allow user to create a program proposal', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '1F5BE56694354AD19B1AF8016D3617D4', 1, krim_perm_id_s.currval, '3', '13', 'kuali.proposal.type.majorDiscipline.create')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '03CCDEEEDC744E259722F7093AD09E37', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allow user to mdoify a program by proposal 10011
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '4D19C283EABD431C814E571507A62D47', 1, '10', 'KS-SYS', 'Modify Program By Proposal', 'Allow user to mdoify a program by proposal', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '3795B796BAEF4489B9E4C6778617C858', 1, krim_perm_id_s.currval, '3', '13', 'kuali.proposal.type.majorDiscipline.modify')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '3FE9E60F09C4489B80105AA62B1BA14B', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to access the Create Course By Admin Proposal screen 10019
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'B5504669C3F64E6BB27F592E8170635F', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Create Course By Admin Proposal', 'Allows users to access the Create Course By Admin Proposal screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'C270DFD01DE74673AAE8C7A9AA2C8717', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useCreateCourseByAdminProposal')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '97391A97CFC9477BBC44572C21D249AE', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/

--Allows users to access the Create Program By Proposal screen 10020
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'C1CE6425F7194C68B89C927AE526E1B8', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Create Program By Proposal', 'Allows users to access the Create Program By Proposal screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '8E59377CC7FA45FCB827C1921B43FA5D', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useCreateProgramByProposal')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '8C25F9F01AE0489BBF7C72656FAB46B0', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to access the browse program catalog screen 10024
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'B35FB91B89BB40A0A0C1625DCF3DE081', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Browse Program Screen', 'Allows users to access the browse program catalog screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'EE2C3D8AEE98484999DDA0CC13C1C4ED', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useBrowseProgram')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '4DA5B3B2563346C1802BFF0DBD637DEE', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to access the "Find Academic Programs" screen 10025
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '008B936A81764252BA9A4519825F8FB0', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Find Program Screen', 'Allows users to access the "Find Academic Programs" screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '7C5E4E4162C543589AE3B03FAF0F98E3', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useFindProgramScreen')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'F05D4E781ACC48319071822F901B1499', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to access the "Find a Program Proposal" screen 10026
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'BF823AD34C0E486C801EE5A60642C533', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Find Program Proposal Screen', 'Allows users to access the "Find a Program Proposal" screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'A64F4536A22049EDA5E70B3F6B04E8E3', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useFindProgramProposalScreen')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '12F4F728C93D4227AB1A59CF4C0AC29A', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to access the "View Core Programs" screen 10027
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '39C235163D214999A70357ECFFC3E963', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use View Core Programs Screen', 'Allows users to access the "View Core Programs" screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '3DBC87257E144687BFDBDD9A290C5092', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useViewCoreProgramsScreen')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '6434F9D1C2D143B39BB9F62FF89B1D60', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to access the "View Credential Programs" screen 10028
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '27FD65A4ED874C54A15F254E2530D53B', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use View Credential Programs Screen', 'Allows users to access the "View Credential Programs" screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '3887772164A949D1A10FC04BCCD0DC2B', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useViewCredentialProgramsScreen')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '5E4BC145C2A94077878DB52977CE90D5', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/

--Allows users to access the View Course Set Management Screens 10029
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'E51CA4A558644F5584F8B6C8B339ABBB', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use View Course Set Management Screens', 'Allows users to access the View Course Set Management Screens', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'D7F9F79669814E84914365898870C8A2', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useViewCourseSetManagement')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'D2A3F48BA1CE421CA853536FC86EBB9A', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/

--Allows users to access the LO Category Screens screen 10030
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '10AFA3E4624142D789826DFE3F9349B1', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use LO Category Screens', 'Allows users to access the LO Category Screens screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '67BA6613425A46C1BC3AEF5880DB3074', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useLOCategory')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '727FBB1C6B4D45B2AE363E6548857EE6', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/

--Allows users to access the dependency analysis screen 10031
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '8EA060B26847443BBB62506BB65D8307', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Dependency Analysis Screen', 'Allows users to access the dependency analysis screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'A3B789B2C9834722B1BEB196FD98FB8F', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useDependencyAnalysis')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '953D3140F92643F1A45DA9B761AF318A', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows access to the Blanket Approval button on KS Documents. 10032
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '9746FD3AD65A4DD9B85A2E92780CC3A1', 1, '4', 'KS-SYS', 'Blanket Approve KS Document', 'Allows access to the Blanket Approval button on KS Documents.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '08F1F1D16C69499E8BB9FFD326FA3591', 1, krim_perm_id_s.currval, '3', '13', 'KualiStudentDocument')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '948130DE0BCA4AD69920F607779B17E9', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Blanket Approve 1', 'Blanket Approval at Route Status R. 10033
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'D6A5B26DDCF14E47A10D5FB705E307EA', 1, '67', 'KS-SYS', 'Blanket Approve 1', 'Blanket Approval at Route Status R.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'A76BE0DD38C7445389BF2D66DFCBB8DD', 1, krim_perm_id_s.currval, '8', '13', 'KualiStudentDocument')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '01214FB4EEF740068F8C2A8C5308B249', 1, krim_perm_id_s.currval, '8', '15', 'R')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '2CE525B62D514051AF01C99EA54A3784', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Blanket Approval at Route Status S. 10034
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'E358B96867D6436ABFEDF0F5DA004597', 1, '67', 'KS-SYS', 'Blanket Approve 2', 'Blanket Approval at Route Status S.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'BE1DFB2B66854A9ABB1BB9F6E2F68EDB', 1, krim_perm_id_s.currval, '8', '13', 'KualiStudentDocument')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '286AA06593F643CDB12605F31F6A53BA', 1, krim_perm_id_s.currval, '8', '15', 'S')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '0B8DE279A5D141628D2D013C2E8A4361', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows admins to have the choice of Admin retire or Retire by Proposal 10063
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '63B81024B62840BBB567AEB9B89FC7B5', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Retire Clu Admin Dialog', 'Allows admins to have the choice of Admin retire or Retire by Proposal', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '478DB7F346064F2AA5FAD26F31AA04A0', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'cluRetireItem')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '60B93DE0F1EE4313AE1FF8BE25566C33', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/

--Kuali Student CM User 10002
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, 'A6B4ECB431D642D687F5203F9AE072A3', 1, 'Kuali Student CM User', 'KS-SYS', 'General Kuali Student Curriculum Management User', '1', 'Y', to_date('16-05-2012 18:46:41', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_1', krim_role_id_s.currval, 'fred', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_2', krim_role_id_s.currval, 'fran', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_3', krim_role_id_s.currval, 'eric', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_4', krim_role_id_s.currval, 'edna', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_5', krim_role_id_s.currval, 'dev1', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_6', krim_role_id_s.currval, 'dev2', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_7', krim_role_id_s.currval, 'test1', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_8', krim_role_id_s.currval, 'test2', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_9', krim_role_id_s.currval, 'testuser1', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_10', krim_role_id_s.currval, 'testuser2', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_11', krim_role_id_s.currval, 'testuser3', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_12', krim_role_id_s.currval, 'testuser4', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_13', krim_role_id_s.currval, 'testuser5', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_14', krim_role_id_s.currval, 'testuser6', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_15', krim_role_id_s.currval, 'testuser7', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_16', krim_role_id_s.currval, 'testuser8', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_17', krim_role_id_s.currval, 'testuser9', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_18', krim_role_id_s.currval, 'user1', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_19', krim_role_id_s.currval, 'user2', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_20', krim_role_id_s.currval, 'user3', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_21', krim_role_id_s.currval, 'user4', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_22', krim_role_id_s.currval, 'user5', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_23', krim_role_id_s.currval, 'user6', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_24', krim_role_id_s.currval, 'user7', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_25', krim_role_id_s.currval, 'user8', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_26', krim_role_id_s.currval, 'admin', 'P', null, null, to_date('09-05-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, 'KS_SYS_KS_CM_ROLE_MEMBER_27', krim_role_id_s.currval, '5', 'P', null, null, to_date('18-06-2012 16:38:54', 'dd-mm-yyyy hh24:mi:ss'))
/
--Allows users to access the Create Course By Proposal screen 10018
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'A81E111228434863B2935C285C384EF8', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Create Course By Proposal', 'Allows users to access the Create Course By Proposal screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '7BA7D7D4B9C6430C98C18B3A0917FE22', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useCreateCourseByProposal')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '1940F1CDFCCE4341B4A2CF7653F44404', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to access the browse course catalog screen 10021
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '8D03DBDB36754CBC85D0AD8503F81022', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Browse Catalog Screen', 'Allows users to access the browse course catalog screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'C01E4BFA8ACC45E4B903159E7FE71F2F', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useBrowseCatalog')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '40E2A5F36512466CB1C180138FC41E7D', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to access the find course screen 10022
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '05FCF73BDBD149BDAC2163ED0E585BB7', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Find Course Screen', 'Allows users to access the find course screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '0C5EF8919847464C8D92BF7E12906566', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useFindCourse')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '497EBCD5A2A143F981910687776EF151', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to access the find course proposal screen 10023
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'EBC7F422C6B94C3B9520A907E40B0039', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Use Screen'), 'KS-SYS', 'Use Find Course Proposal Screen', 'Allows users to access the find course proposal screen', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '539940691A5A4CCEAFD704AA65A315E1', 1, krim_perm_id_s.currval, '1', (select kim_attr_defn_id from krim_attr_defn_t where nm = 'screenComponent'), 'useFindCourseProposal')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'F22EA9A4A6634CCFA3FF432150E96EB6', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to open Create Course Proposals with a Route Status of Final. 10035
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '1D296D97133F4B05AC8C3D9E89F96FE1', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Create Course Route Status F', 'Allows users to open Create Course Proposals with a Route Status of Final.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'E5818A173E3F464BA46AD27DD0502B8D', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.create')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '487947E38BFF41B7A4EA9FCD00ED8A03', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'F')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'E71682C020D046B08A6F96755C38E078', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to open Create Course Proposals with a Route Status of Cancelled. 10036
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'ABF70B00138749BFBC5EBD7E4FF86D18', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Create Course Route Status X', 'Allows users to open Create Course Proposals with a Route Status of Cancelled.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '87DC75C2EFA14464981061CAFA3C2553', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.create')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'DB7CF653AA3345B1BB08A3A58223F552', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'X')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '35224892C4FB4C4DA6CC6DD8DDE30870', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to open Create Course Proposals with a Route Status of Processed. 10037
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'F934059EDA8F434884BF2E5A08CD2025', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Create Course Route Status P', 'Allows users to open Create Course Proposals with a Route Status of Processed.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '46B34144DBB448E8A5F249DD4C4C6230', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.create')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'E2590E3E69BC4EECBF5A0F796CF62E4F', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'P')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '459751E3DDB64D4FB8E09B99BF3C1488', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to open Create Course Admin Proposals with a Route Status of Final. 10038
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'DE996CE9039541D290374C82545C1232', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Create Course Admin Route Status F', 'Allows users to open Create Course Admin Proposals with a Route Status of Final.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '944C00ABB25F45B3AFBADBC8F378DA77', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.create.admin')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'A3E5D42957A04EE4A4FBFBB1C07CCD98', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'F')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '64FC91200A444FDC8FF6EF4E47BCDDAC', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to open Create Course Admin Proposals with a Route Status of Cancelled. 10039
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '77C7A0136D0F4131B0AC31A3B7D1A7AB', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Create Course Admin Route Status X', 'Allows users to open Create Course Admin Proposals with a Route Status of Cancelled.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '49292DFD53E14D95B88FEA293081E269', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.create.admin')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '860A2872A58944D3BD1D88D8F2FA74DD', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'X')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '05F930FD26A44B2FA9E0DE4CC4749B91', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to open Create Course Admin Proposals with a Route Status of Processed. 10040
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'A727CA70B95A47D292329955C9D42CAE', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Create Course Admin Route Status P', 'Allows users to open Create Course Admin Proposals with a Route Status of Processed.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'A0483A0BF54E49209303680B683256EF', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.create.admin')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '0A2F4F99980F42FCB5698843AB271B22', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'P')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '681D1E7C445F4B8DB11C62913F81E4B4', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to open Course Modification Proposals with a Route Status of Final. 10041
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '3EA2EC03A4F844BE9B03476F7EA9B9C0', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Course Modification Route Status F', 'Allows users to open Course Modification Proposals with a Route Status of Final.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '509C184346CB449FB291FAFE423A521A', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.modify')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '700239C930D34BE29EFAABCBC5F3EB88', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'F')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '0548B73437A040FE99EF80F85898E823', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Allows users to open Course Modification Proposals with a Route Status of Cancelled. 10042
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '0A2128806D0045629CA1FCF7A3D0EECB', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Course Modification Route Status X', 'Allows users to open Course Modification Proposals with a Route Status of Cancelled.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '9753D618B5EE4A65A347CE8A44861551', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.modify')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '78154BDD668C449DA77C771F8AFCFFE6', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'X')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '2E48FF66C7B5461E8B7B5ACFDC335C2F', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Course Modification Route Status P 10043
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '88CA4D28AA24470DB075C5A3424F264C', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Course Modification Route Status P', 'Allows users to open Course Modification Proposals with a Route Status of Processed.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '6D2E4E1E3AA142EEA3298A5C7C4CCCFB', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.modify')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'C0182E9AACFD47F790D4A88469BE51DA', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'P')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '5331981CC8504835A15C0D4DE08B9F25', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Course Modification Admin Route Status F 10044
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '025063328EB54494AEF70075FE68D6CB', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Course Modification Admin Route Status F', 'Allows users to open Course Modification Admin Proposals with a Route Status of Final.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '5CB406BC21E74C82AC4F1387964186BC', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.modify.admin')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'A012F2B68F94492CB8B5AA394CFEB80A', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'F')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '952434D45A864FDE9C19F1585BAD6B41', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Course Modification Admin Route Status X 10045
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'CC1A893F62B8467E89251F8D609BEB82', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Course Modification Admin Route Status X', 'Allows users to open Course Modification Admin Proposals with a Route Status of Cancelled.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '59A87950C5344041A46E9967B0E35A7C', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.modify.admin')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'DC9D35CDAE4847EC83E895F4B95CE724', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'X')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '7ACFDB1168B64DC6B86461AEAB246694', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Course Modification Admin Route Status P 10046
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '672174C1193F4DD28C145E8D38DDF90F', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Course Modification Admin Route Status P', 'Allows users to open Course Modification Admin Proposals with a Route Status of Processed.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '286F06154734440280F5A4C2C2CFDBC9', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.modify.admin')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'C09775A2DAEF4A9FB3B8E059017F6434', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'P')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'EBA0A22A1CB04495B6A19F7690E9D3DD', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Program Creation Route Status F 10047
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'E26EB905306F4ED7B9091796CD3A3774', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Program Creation Route Status F', 'Allows users to open Program Creation Proposals with a Route Status of Final.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'A9F1C6E0BBE54B5986FAE636F48DEDE6', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.majorDiscipline.create')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'A7B8529BE84C4160B8E347A2E0F1EE75', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'F')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '96C53986AFEC4AD2875686D1AAB70ABF', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Program Creation Route Status X 10048
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'EC0DA085DB704BDEA1C9DA22DF346B5B', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Program Creation Route Status X', 'Allows users to open Program Creation Proposals with a Route Status of Cancelled.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'BD660161B456461F8C3EB8B2E10ECF53', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.majorDiscipline.create')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'B0D4C33E57CB478FBB7EB05F84C408CC', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'X')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'FD1C073DCB8F422BA76FD29EBEC63D59', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Program Creation Route Status P 10049
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'B184FB7EF191491BBFFB7C041B59A99F', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Program Creation Route Status P', 'Allows users to open Program Creation Proposals with a Route Status of Processed.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '3FAF1FC3562E479B9F7D4EF45C1F53E4', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.majorDiscipline.create')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '3C515C515B4245F1921C7B26E4F38304', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'P')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '8756BB3009BF496EA0003A1E64449E0F', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Program Modification Route Status F 10050
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '9AB3571470A642DC85CB8B8BCB206522', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Program Modification Route Status F', 'Allows users to open Program Modification Proposals with a Route Status of Final.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '7BA8D3D6ED5844D7BC3304063E62A87A', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.majorDiscipline.modify')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '59F1438D99694EB5854081B315AC661A', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'F')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '51674DFA83A942A58BBACDCEFD6B8886', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Program Modification Route Status X 10051
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '114E200336714ACFA10D08577B91685E', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Program Modification Route Status X', 'Allows users to open Program Modification Proposals with a Route Status of Cancelled.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '8B17F464CD8A4279B1831C5A2676BB9C', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.majorDiscipline.modify')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'B238E1B2876947B98E2AE51039B671F9', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'X')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'A43ABC3794884D47BDFD75FE6B323BB7', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Program Modification Route Status P 10052
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'DEE118747B16457E94928812A221D7E4', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Program Modification Route Status P', 'Allows users to open Program Modification Proposals with a Route Status of Processed.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'A3DA9E0B157441A987E4214EEAFA39BB', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.majorDiscipline.modify')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '7CC5D3018D1C4773928D81338E65F037', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'P')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'C11C14C837A74F079670356BC039C7BC', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Course Retirement Route Status F 10053
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '67DA8E8C7E5E4C8D8270A8687F30D366', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Course Retirement Route Status F', 'Allows users to open Course Retirement Proposals with a Route Status of Final.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'F4223FD60B864BBB97A00D3B4D527C14', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.retire')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '081B5CB1BC514DF5852DB57EF1BB2AAC', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'F')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '9F2FEC1A5D20406489EFB1A5614DF106', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Course Retirement Route Status X 10054
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'B402D72B9DB14BA09CFCBA890FF73AEF', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Course Retirement Route Status X', 'Allows users to open Course Retirement Proposals with a Route Status of Cancelled.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'EA31B34B2CA34984B50D51C8D19DD8FA', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.retire')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'D6ABE2F5B0C9436B8F8976DD05AFE766', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'X')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'A3EF143EC7EE4CA0BEE8A1C5C0EF3192', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
--Open Course Retirement Route Status P 10055
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '6DDB0A7AD7CF426A9E52792AC774254B', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-SYS', 'Open Course Retirement Route Status P', 'Allows users to open Course Retirement Proposals with a Route Status of Processed.', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '5B9B8D957EBD46308DEA8AB8CFC50C58', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '13', 'kuali.proposal.type.course.retire')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, '6BEAA4D4143945DCA6D678B8CE4D3E82', 1, krim_perm_id_s.currval, krim_typ_id_s.currval, '15', 'P')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '6702AABCAE8F449C9AB62C668058001C', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/
-- Allow user to retire course by proposal 10064
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '0C29276C87DC4A15A1C7BFC02FAC035C', 1, '10', 'KS-SYS', 'Retire Course By Proposal', 'Allow user to retire course by proposal', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'B031190B53644885A298C4C828DF8B79', 1, krim_perm_id_s.currval, '3', '13', 'kuali.proposal.type.course.retire')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '181314D488D14980B5E7C3898EE7C8B5', 1, krim_role_id_s.currval, krim_perm_id_s.currval, 'Y')
/

--******************************************************************************************************************PERMISSIONS WITH MULTIPLE ROLE/PERM LINKS
--Allow user to create course proposal 10006
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'E523C30B34854AE886E7C34E6E3434D0', 1, '10', 'KS-SYS', 'Create Course By Proposal', 'Allow user to create course proposal', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'B129DD73B1014057BEFDD1A2D16FFEDB', 1, krim_perm_id_s.currval, '3', '13', 'kuali.proposal.type.course.create')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'D23849A356174CE186C331D8FEF29BC9', 1, (select role_id from krim_role_t where role_nm = 'Kuali Student CM User'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'ABFB1B7A73A5424EBB8487301EACDD69', 1, (select role_id from krim_role_t where role_nm = 'Kuali Student CM Admin'), krim_perm_id_s.currval, 'Y')
/

--Allow user to create a modify course proposal 10007
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '49A25854FD6843A182839E66B8DF2A69', 1, '10', 'KS-SYS', 'Modify Course By Proposal', 'Allow user to create a modify course proposal', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, 'F03A8EF636B04554A7141D3DF5C0EA0B', 1, krim_perm_id_s.currval, '3', '13', 'kuali.proposal.type.course.modify')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'AB6F0C09E12E449785A80BC09AE2B717', 1, (select role_id from krim_role_t where role_nm = 'Kuali Student CM User'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'C31DD82104FC4CB6A68714F16774A830', 1, (select role_id from krim_role_t where role_nm = 'Kuali Student CM Admin'), krim_perm_id_s.currval, 'Y')
/

--Allows users with responsibility on workflow document to open document 10013
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '1F2D39C078104066B66C62B3AB5DD1E7', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Open Document'), 'KS-LUM', 'Open Document', 'Allows users with responsibility on workflow document to open document', 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '1BE76D8D3DCC46D6B0FCAE34080B479A', 1, (select role_id from krim_role_t where role_nm = 'Derived Role: Document Editor'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '7E69EFA7E11545D0972C7FD89BA7AC02', 1, (select role_id from krim_role_t where role_nm = 'Derived Role: Initiator or Reviewer'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'A4E1038195864F46BE4818A8360C3646', 1, (select role_id from krim_role_t where role_nm = 'Derived Role: Router'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'F0175BB98D694703B539DE2988993E35', 1, (select role_id from krim_role_t where role_nm = 'Derived Role: Document Commenter'), krim_perm_id_s.currval, 'Y')
/

--Comment on Document 10014
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '765'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '766'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '767'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '901'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '902'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '903'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '904'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '905'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '906'
/
delete from KRIM_PERM_T where PERM_ID = '3000'
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'B29BD9E4FA894685BD36E30AC50DE02D', 1, '61', 'KS-LUM', 'Comment on Document', null, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'AF8BB81D4003480F9B751CA47B5A62B5', 1, (select role_id from krim_role_t where role_nm = 'Derived Role: Document Editor'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '3BD2705BE6B44AA79F0CAAD5FCC65542', 1, (select role_id from krim_role_t where role_nm = 'Derived Role: Acknowledge Request Recipient'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '62FCD65CD5FB4E68B08AE6472ACFEAA2', 1, (select role_id from krim_role_t where role_nm = 'Derived Role: FYI Request Recipient'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '35C3713058794B2FB48C490109732168', 1, (select role_id from krim_role_t where role_nm = 'Completed Approve Request Recipient'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '642092E7524948ED8667CB49F6DBFAD7', 1, (select role_id from krim_role_t where role_nm = 'Completed Acknowledge Request Recipient'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '9C138270F34A478EBD96E8E7E903A0F0', 1, (select role_id from krim_role_t where role_nm = 'Completed FYI Request Recipient'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'C4DAE56C73EE4ED989DA4FD3314D2909', 1, (select role_id from krim_role_t where role_nm = 'Completed Adhoc Approve Request Recipient'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'BD7E3AA9BEB24872AE6C81F2A0E2F04A', 1, (select role_id from krim_role_t where role_nm = 'Completed Adhoc Acknowledge Request Recipient'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'FA5E6E1520AC48B99A8D4B6AE4B7097A', 1, (select role_id from krim_role_t where role_nm = 'Completed Adhoc Acknowledge Request Recipient'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'A764C36FF28C4656AC0B5BCF65F0B59E', 1, (select role_id from krim_role_t where role_nm = 'Completed Adhoc FYI Request Recipient'), krim_perm_id_s.currval, 'Y')
/

-- Edit Document 10015
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '768'
/
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID = '900'
/
delete from KRIM_PERM_T where PERM_ID = '3100'
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, 'C6DF560BD66E4624BBAB741308981FA1', 1, '62', 'KS-LUM', 'Edit Document', null, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '220A53AD18BB441DB96E4E58F8FDAC2F', 1, (select role_id from krim_role_t where role_nm = 'Derived Role: Approve Request Recipient'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'FC420DB843FC476FA37F53E670ABFD2E', 1, (select role_id from krim_role_t where role_nm = 'Adhoc Permissions: Edit Document'), krim_perm_id_s.currval, 'Y')
/

--Add Collaborator with FYI 10058
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '805880BAD13C4D20B80A2C1075B9762D', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Add Collaborator Action'), 'KS-SYS', 'Add Collaborator with FYI', null, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, 'F0612EDC9C184F9599A23E680B4B1035', 1, (select role_id from krim_role_t where role_nm = 'Kuali Student CM User'), krim_perm_id_s.currval, 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, '4B7E49B1D2A24165B02F7F00C325211C', 1, (select role_id from krim_role_t where role_nm = 'Derived Role: Document Editor'), krim_perm_id_s.currval, 'Y')
/

--***********************************************************************************************************************************************adhoc stuff
--Allows users to access the XML Ingester screen. 265
update krim_perm_attr_data_t set (attr_val) = ('org.kuali.rice.kew.batch.web.IngesterAction') where attr_data_id = '874'
/

--KRIM_ATTR_DEFN_T
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, '812E8CBF887D446EAE75F0A4B7FFF719', 1, 'dataId', 'Data Object Id', 'Y', 'KS-SYS', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, '9DEE3D5B1B064397A8198854E0608B6F', 1, 'org', 'Organization', 'Y', 'KS-SYS', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, 'C5B0169958674C52A8F6FA6A956BCB6A', 1, 'sectionId', 'Section Id', 'Y', 'KS-SYS', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM)
values (krim_attr_defn_id_s.nextval, '2A68FFF5C7CF4C9585B603DA7C1B0FD7', 1, 'ksReferenceTypeKey', 'Reference Type Key', 'Y', 'KS-SYS', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/

--Add Collaborator with Approve 10060
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, '335B2AE05108470E8CEE2CB0665922FB', 1, (select perm_tmpl_id from krim_perm_tmpl_t where nmspc_cd = 'KS-SYS' and nm = 'Add Collaborator Action'), 'KS-SYS', 'Add Collaborator with Approve', null, 'Y')
/

--Allows Save Document KS LUM Document 10061
update KRIM_PERM_T set (DESC_TXT) = ('Allows Save Document KS LUM Document') where perm_id = '2200'
/

--Authorizes users to withdraw a KS workflow document. 10017
update KRIM_PERM_T set (DESC_TXT) = ('Authorizes users to withdraw a KS workflow document') where perm_id = '3101'
/
