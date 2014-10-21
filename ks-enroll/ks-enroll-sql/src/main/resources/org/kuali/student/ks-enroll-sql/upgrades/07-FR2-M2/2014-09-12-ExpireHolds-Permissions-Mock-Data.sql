--Expire Hold 
--link admin person to role
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin','P',SYS_GUID(),'KS-10005','KS-KRIM-ROLE-MBR-10016','1')
/


--hold ACAD01 kuali.hold.issue.academic.probation
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10010','G',SYS_GUID(),'KS-10005','KS-KRIM-ROLE-MBR-10017','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10015','kuali.hold.issue.academic.probation','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10017','1')
/

--hold ACAD02 kuali.hold.issue.academically.ineligible
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10011','G',SYS_GUID(),'KS-10005','KS-KRIM-ROLE-MBR-10018','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10016','kuali.hold.issue.academically.ineligible','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10018','1')
/



--hold ACAD03 kuali.hold.issue.administratively.ineligible
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10012','G',SYS_GUID(),'KS-10005','KS-KRIM-ROLE-MBR-10019','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10017','kuali.hold.issue.administratively.ineligible','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10019','1')
/

--hold ACAD04 kuali.hold.issue.fundamental.english
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10013','G',SYS_GUID(),'KS-10005','KS-KRIM-ROLE-MBR-10020','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10018','kuali.hold.issue.fundamental.english','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10020','1')
/

--hold ACAD05 kuali.hold.issue.fundamental.math
--link role to group
insert into krim_role_mbr_t(actv_frm_dt,actv_to_dt,last_updt_dt,mbr_id,mbr_typ_cd,obj_id,role_id,role_mbr_id,ver_nbr)
VALUES(null,null,TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'10014','G',SYS_GUID(),'KS-10005','KS-KRIM-ROLE-MBR-10021','1')
/
--link role to hold
insert into krim_role_mbr_attr_data_t(attr_data_id,attr_val,kim_attr_defn_id,kim_typ_id,obj_id,role_mbr_id,ver_nbr)
VALUES('KS-KRIM-ROLE-MBR-ATTR-DATA-10019','kuali.hold.issue.fundamental.math','KS-KRIM-ATTR-DEFN-1004','KS-KRIM-TYP-1012',SYS_GUID(),'KS-KRIM-ROLE-MBR-10021','1')
/
