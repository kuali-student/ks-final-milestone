

--org ORGID-1863972184 
--hold ACAD01 kuali.hold.issue.academic.probation

--create group
insert into krim_grp_t(actv_ind,grp_desc,grp_id,grp_nm,kim_typ_id,last_updt_dt,nmspc_cd,obj_id,ver_nbr)
VALUES('Y',null,'10010','Hold Functionaries for Ctr Crim Justice Technology','KS-KRIM-TYP-1011',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'KS-HLD',SYS_GUID(),'1')
/
--link group to org
insert into krim_grp_attr_data_t(attr_data_id,attr_val,grp_id,kim_attr_defn_id,kim_typ_id,obj_id,ver_nbr)
VALUES('10010','ORGID-1863972184','10010','KS-KRIM-ATTR-DEFN-1003','KS-KRIM-TYP-1011',SYS_GUID(),'1')
/
--link user to group
insert into krim_grp_mbr_t (actv_frm_dt,actv_to_dt,grp_id,grp_mbr_id,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,ver_nbr)
VALUES (null,null,'10010','1250',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'carol','P',SYS_GUID(),'1')
/
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10010','G',SYS_GUID(),'KS-10004','KS-KRIM-ROLE-MBR-10010','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10010','kuali.hold.issue.academic.probation','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10010','1')
/

--org ORGID-1869859059
--hold ACAD02 kuali.hold.issue.academically.ineligible

--create group
insert into krim_grp_t(actv_ind,grp_desc,grp_id,grp_nm,kim_typ_id,last_updt_dt,nmspc_cd,obj_id,ver_nbr)
VALUES('Y',null,'10011','Hold Functionaries for VA-MD Reg Col Vet Med','KS-KRIM-TYP-1011',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'KS-HLD',SYS_GUID(),'1')
/
--link group to org
insert into krim_grp_attr_data_t(attr_data_id,attr_val,grp_id,kim_attr_defn_id,kim_typ_id,obj_id,ver_nbr)
VALUES('10011','ORGID-1869859059','10011','KS-KRIM-ATTR-DEFN-1003','KS-KRIM-TYP-1011',SYS_GUID(),'1')
/
--link user to group
insert into krim_grp_mbr_t (actv_frm_dt,actv_to_dt,grp_id,grp_mbr_id,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,ver_nbr)
VALUES (null,null,'10011','1251',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'carol','P',SYS_GUID(),'1')
/
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10011','G',SYS_GUID(),'KS-10004','KS-KRIM-ROLE-MBR-10011','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10011','kuali.hold.issue.academically.ineligible','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10011','1')
/

--org ORGID-1872722953
--hold ACAD03 kuali.hold.issue.administratively.ineligible

--create group
insert into krim_grp_t(actv_ind,grp_desc,grp_id,grp_nm,kim_typ_id,last_updt_dt,nmspc_cd,obj_id,ver_nbr)
VALUES('Y',null,'10012','Hold Functionaries for Freshmen Connection-CLIS','KS-KRIM-TYP-1011',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'KS-HLD',SYS_GUID(),'1')
/
--link group to org
insert into krim_grp_attr_data_t(attr_data_id,attr_val,grp_id,kim_attr_defn_id,kim_typ_id,obj_id,ver_nbr)
VALUES('10012','ORGID-1863972184','10012','KS-KRIM-ATTR-DEFN-1003','KS-KRIM-TYP-1011',SYS_GUID(),'1')
/
--link user to group
insert into krim_grp_mbr_t (actv_frm_dt,actv_to_dt,grp_id,grp_mbr_id,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,ver_nbr)
VALUES (null,null,'10012','1252',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'carol','P',SYS_GUID(),'1')
/
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10012','G',SYS_GUID(),'KS-10004','KS-KRIM-ROLE-MBR-10012','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10012','kuali.hold.issue.administratively.ineligible','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10012','1')
/

--org ORGID-1872941743
--hold ACAD04 kuali.hold.issue.fundamental.english

--create group
insert into krim_grp_t(actv_ind,grp_desc,grp_id,grp_nm,kim_typ_id,last_updt_dt,nmspc_cd,obj_id,ver_nbr)
VALUES('Y',null,'10013','Hold Functionaries for AES-CMREC-Trfgrss Res Fac','KS-KRIM-TYP-1011',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'KS-HLD',SYS_GUID(),'1')
/
--link group to org
insert into krim_grp_attr_data_t(attr_data_id,attr_val,grp_id,kim_attr_defn_id,kim_typ_id,obj_id,ver_nbr)
VALUES('10013','ORGID-1872941743','10013','KS-KRIM-ATTR-DEFN-1003','KS-KRIM-TYP-1011',SYS_GUID(),'1')
/
--link user to group
insert into krim_grp_mbr_t (actv_frm_dt,actv_to_dt,grp_id,grp_mbr_id,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,ver_nbr)
VALUES (null,null,'10013','1253',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'carol','P',SYS_GUID(),'1')
/
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10013','G',SYS_GUID(),'KS-10004','KS-KRIM-ROLE-MBR-10013','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10013','kuali.hold.issue.fundamental.english','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10013','1')
/

--org ORGID-1873606557
--hold ACAD05 kuali.hold.issue.fundamental.math

--create group
insert into krim_grp_t(actv_ind,grp_desc,grp_id,grp_nm,kim_typ_id,last_updt_dt,nmspc_cd,obj_id,ver_nbr)
VALUES('Y',null,'10014','Hold Functionaries for UME-1890 Cecil','KS-KRIM-TYP-1011',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'KS-HLD',SYS_GUID(),'1')
/
--link group to org
insert into krim_grp_attr_data_t(attr_data_id,attr_val,grp_id,kim_attr_defn_id,kim_typ_id,obj_id,ver_nbr)
VALUES('10014','ORGID-1873606557','10014','KS-KRIM-ATTR-DEFN-1003','KS-KRIM-TYP-1011',SYS_GUID(),'1')
/
--link user to group
insert into krim_grp_mbr_t (actv_frm_dt,actv_to_dt,grp_id,grp_mbr_id,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,ver_nbr)
VALUES (null,null,'10014','1254',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'carol','P',SYS_GUID(),'1')
/
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10014','G',SYS_GUID(),'KS-10004','KS-KRIM-ROLE-MBR-10014','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10014','kuali.hold.issue.fundamental.math','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10014','1')
/


--link admin person to role
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin','P',SYS_GUID(),'KS-10004','KS-KRIM-ROLE-MBR-10015','1')
/


