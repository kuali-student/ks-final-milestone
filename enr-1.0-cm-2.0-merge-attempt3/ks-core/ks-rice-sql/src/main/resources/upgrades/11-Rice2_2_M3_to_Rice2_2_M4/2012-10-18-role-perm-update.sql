INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind)
VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Modify Program By Proposal' and nmspc_cd = 'KS-SYS'),'Y')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind)
VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Use Browse Program Screen' and nmspc_cd = 'KS-SYS'),'Y')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind)
VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Use Find Program Screen' and nmspc_cd = 'KS-SYS'),'Y')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind)
VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Use Find Program Proposal Screen' and nmspc_cd = 'KS-SYS'),'Y')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind)
VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Use View Core Programs Screen' and nmspc_cd = 'KS-SYS'),'Y')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind)
VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Use View Credential Programs Screen' and nmspc_cd = 'KS-SYS'),'Y')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind)
VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Use View Course Set Management Screens' and nmspc_cd = 'KS-SYS'),'Y')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind)
VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Use LO Category Screens' and nmspc_cd = 'KS-SYS'),'Y')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind)
VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Use Dependency Analysis Screen' and nmspc_cd = 'KS-SYS'),'Y')
/