--
-- Copyright 2005-2012 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

--KIM permissions for KS KRMS
insert into krim_perm_tmpl_t (perm_tmpl_id, nm, nmspc_cd, desc_txt, kim_typ_id, actv_ind, ver_nbr, obj_id)
values (krim_perm_tmpl_id_s.nextval,'KRMS Agenda Permission','KS-SYS','View/Maintain Agenda',
        (select kim_typ_id from krim_typ_t where nm = 'Namespace' and nmspc_cd = 'KR-NS'),
        'Y',1,sys_guid())
/

insert into krim_perm_t (perm_id, perm_tmpl_id, nmspc_cd, nm, desc_txt, actv_ind, ver_nbr, obj_id)
values (krim_perm_id_s.nextval,
        (select perm_tmpl_id from krim_perm_tmpl_t where nm = 'KRMS Agenda Permission' and nmspc_cd = 'KS-SYS'),
        'KS-SYS','Maintain KRMS Agenda','Allows creation and modification of agendas via the agenda editor','Y',1,sys_guid())
/

-- KIM roles
insert into krim_role_t
(role_id, role_nm, nmspc_cd, desc_txt, kim_typ_id, actv_ind, last_updt_dt, obj_id)
values (krim_role_id_s.nextval,
        'Kuali Rules Management System Administrator', 'KS-SYS',
        'This role maintains KRMS agendas and rules.',
        (select kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'),
        'Y', current_date, sys_guid())
/

insert into krim_role_mbr_t
(role_mbr_id, role_id, mbr_id, mbr_typ_cd, last_updt_dt, ver_nbr, obj_id)
values (krim_role_mbr_id_s.nextval,
        (select role_id from krim_role_t where role_nm = 'Kuali Rules Management System Administrator' and nmspc_cd = 'KS-SYS'),
        (select prncpl_id from krim_prncpl_t where prncpl_nm = 'admin'),
        'P', current_date, 1, sys_guid())
/

insert into krim_role_perm_t
(role_perm_id, role_id, perm_id, actv_ind, ver_nbr, obj_id)
values (krim_role_perm_id_s.nextval,
        (select role_id from krim_role_t where role_nm = 'Kuali Rules Management System Administrator' and nmspc_cd = 'KS-SYS'),
        (select perm_id from krim_perm_t where nm = 'Maintain KRMS Agenda' and nmspc_cd = 'KS-SYS'),
        'Y', 1, sys_guid())
/

---- If you should want to clean out your KRMS tables:
delete from  krms_cntxt_vld_rule_typ_t
/
delete from  krms_cntxt_vld_func_t
/
delete from  krms_term_spec_ctgry_t
/
delete from  krms_func_ctgry_t
/
delete from  krms_ctgry_t
/
delete from  krms_func_parm_t
/
delete from  krms_func_t
/
delete from  krms_term_parm_t
/
delete from  krms_term_rslvr_parm_spec_t
/
delete from  krms_term_t
/
delete from  krms_cntxt_vld_term_spec_t
/
delete from  krms_term_rslvr_input_spec_t
/
delete from  krms_term_rslvr_attr_t
/
delete from  krms_term_rslvr_t
/
delete from  krms_term_spec_t
/
delete from  krms_prop_parm_t
/
delete from  krms_cmpnd_prop_props_t
/
delete from  krms_agenda_attr_t
/
delete from  krms_cntxt_vld_actn_typ_t
/
delete from  krms_cntxt_vld_agenda_typ_t
/
delete from  krms_cntxt_attr_t
/
delete from  krms_rule_attr_t
/
update krms_agenda_itm_t set when_true=null
/
update krms_agenda_itm_t set when_false=null
/
update krms_agenda_itm_t set always=null
/
delete from  krms_agenda_itm_t
/
delete from  krms_actn_attr_t
/
delete from  krms_actn_t
/
delete from  krms_typ_attr_t
/
delete from  krms_attr_defn_t
/
delete from  krms_agenda_t
/
update krms_rule_t set prop_id=null
/
delete from  krms_prop_t
/
delete from  krms_rule_t
/
delete from  krms_typ_reln_t
/
delete from  krms_typ_t
/
delete from  krms_cntxt_t
/

