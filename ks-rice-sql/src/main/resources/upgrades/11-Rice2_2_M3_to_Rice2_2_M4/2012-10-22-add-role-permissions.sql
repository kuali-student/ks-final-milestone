--Open Create Course Route Status R 10065
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR,NM, desc_txt)
  VALUES ('Y','KS-SYS',sys_guid(),krim_perm_id_s.nextval,'10001',1,'Open Create Course Route Status R','Open Create Course Route Status R')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (attr_data_id,obj_id,ver_nbr,perm_id,kim_typ_id,kim_attr_defn_id,attr_val)
  VALUES (krim_attr_data_id_s.nextval,sys_guid(),1,krim_perm_id_s.currval,(select kim_typ_id from krim_typ_t where nm = 'Document Type and Route Node'),13,'kuali.proposal.type.course.create')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (attr_data_id,obj_id,ver_nbr,perm_id,kim_typ_id,kim_attr_defn_id,attr_val)
  VALUES (krim_attr_data_id_s.nextval,sys_guid(),1,krim_perm_id_s.currval,(select kim_typ_id from krim_typ_t where nm = 'Document Type and Route Node'),15,'R')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind) VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM Admin' and nmspc_cd = 'KS-SYS'),krim_perm_id_s.currval,'Y')
/

--Open Modify Course Route Status R 10066
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR,NM, desc_txt)
  VALUES ('Y','KS-SYS',sys_guid(),krim_perm_id_s.nextval,'10001',1,'Open Modify Course Route Status R','Open Modify Course Route Status R')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (attr_data_id,obj_id,ver_nbr,perm_id,kim_typ_id,kim_attr_defn_id,attr_val)
  VALUES (krim_attr_data_id_s.nextval,sys_guid(),1,krim_perm_id_s.currval,(select kim_typ_id from krim_typ_t where nm = 'Document Type and Route Node'),13,'kuali.proposal.type.course.modify')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (attr_data_id,obj_id,ver_nbr,perm_id,kim_typ_id,kim_attr_defn_id,attr_val)
  VALUES (krim_attr_data_id_s.nextval,sys_guid(),1,krim_perm_id_s.currval,(select kim_typ_id from krim_typ_t where nm = 'Document Type and Route Node'),15,'R')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind) VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM Admin' and nmspc_cd = 'KS-SYS'),krim_perm_id_s.currval,'Y')
/

--Open Course Retirement Route Status R 10067
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR,NM, desc_txt)
  VALUES ('Y','KS-SYS',sys_guid(),krim_perm_id_s.nextval,'10001',1,'Open Course Retirement Route Status R','Open Course Retirement Route Status R')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (attr_data_id,obj_id,ver_nbr,perm_id,kim_typ_id,kim_attr_defn_id,attr_val)
  VALUES (krim_attr_data_id_s.nextval,sys_guid(),1,krim_perm_id_s.currval,(select kim_typ_id from krim_typ_t where nm = 'Document Type and Route Node'),13,'kuali.proposal.type.course.retire')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (attr_data_id,obj_id,ver_nbr,perm_id,kim_typ_id,kim_attr_defn_id,attr_val)
  VALUES (krim_attr_data_id_s.nextval,sys_guid(),1,krim_perm_id_s.currval,(select kim_typ_id from krim_typ_t where nm = 'Document Type and Route Node'),15,'R')
/
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind) VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM Admin' and nmspc_cd = 'KS-SYS'),krim_perm_id_s.currval,'Y')
/

--krim_role_perm_t
INSERT INTO krim_role_perm_t (role_perm_id,obj_id,ver_nbr,role_id,perm_id,actv_ind) VALUES (krim_role_perm_id_s.nextval,sys_guid(),1,(select role_id from krim_role_t where role_nm = 'Kuali Student CM User' and nmspc_cd = 'KS-SYS'),(select perm_id from krim_perm_t where nm = 'Initiate Document' and nmspc_cd = 'KS-SYS'),'Y')
/


