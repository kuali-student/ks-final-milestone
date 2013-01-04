--select rq.*, krms.*
--  from ksst_ref_stmt_rel sref, ksst_stmt_jn_req_com str, ksst_stmt st,
--       ksst_req_com rq, ksst_rc_jn_rc_field rjf, ksst_req_com_field rqf,
--       (select distinct rul.nm rul, prop.desc_txt des, ref.ref_obj_id refid, proptyp.nm proptype, rultyp.nm rultype
--          from krms_ref_obj_krms_obj_t ref, krms_agenda_t age, krms_agenda_itm_t ar,
--               krms_rule_t rul, krms_prop_t prop, krms_typ_t proptyp, krms_typ_t rultyp
--         where ref.krms_obj_id = age.agenda_id
--           and age.agenda_id = ar.agenda_id
--           and ar.rule_id = rul.rule_id
--           and rul.typ_id = rultyp.typ_id
--           and rul.rule_id = prop.rule_id
--           and prop.typ_id = proptyp.typ_id) krms
--where (sref.stmt_id = (select sjs.stmt_id from ksst_stmt_jn_stmt sjs where sjs.chld_stmt_id = str.stmt_id)
--    or sref.stmt_id = str.stmt_id)
--   and sref.stmt_id = st.id
--   and str.req_com_id = rq.id
--   and sref.ref_obj_type_key = 'kuali.lu.type.CreditCourse'
--   and rq.id = rjf.req_com_id
--   and rjf.req_com_field_id = rqf.id
--   and krms.refid = sref.ref_obj_id
--   and rq.req_com_type_id = krms.proptype
--   and (st.stmt_type_id = krms.rultype
--    or (st.stmt_type_id = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' and krms.rultype = 'kuali.statement.type.course.academicReadiness.prereq'))

--GEOG--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--GEOG110 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG110 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG110 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG110 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG110 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG110 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG110 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,cfff5b07-4dd4-4ad8-8295-1bed0bd6648e
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'cfff5b07-4dd4-4ad8-8295-1bed0bd6648e', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG123 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG123 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG123 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG123 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG123 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,bd2745e8-12ec-48a0-b701-d9696e6c406e
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number of courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'bd2745e8-12ec-48a0-b701-d9696e6c406e', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG123 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG123 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG201 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG201 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG201 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,REFERENCECOURSEGEOG100
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'REFERENCECOURSEGEOG100', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG202 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG202 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG202 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,REFERENCECOURSEGEOG123
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'REFERENCECOURSEGEOG123', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG211 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG211 Corequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG211 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,44f6025c-d06b-4421-8591-6380f7cda128
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'enrolledCourses' and nmspc_cd = 'KS-SYS'), 1, 'Concurrently enrolled in list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '44f6025c-d06b-4421-8591-6380f7cda128', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG212 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG212 Corequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG212 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,47221f9f-0d26-4423-992b-91e7139d522b
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'enrolledCourses' and nmspc_cd = 'KS-SYS'), 1, 'Concurrently enrolled in list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '47221f9f-0d26-4423-992b-91e7139d522b', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG330 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG330 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG330 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG330 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG330 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG330 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG330 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,32406782-4950-4005-959b-709fdeab44a0
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '32406782-4950-4005-959b-709fdeab44a0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG331 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG331 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG331 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG331 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG331 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG331 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG331 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,221f04d1-1807-4bf4-a006-bbac909c5695
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '221f04d1-1807-4bf4-a006-bbac909c5695', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG342 Preperation,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG342 Preperation'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG342 Preperation')
/
--kuali.reqComponent.field.type.course.clu.id,4ceadf8f-737e-4cf6-8a38-a4cbd5eddc3c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '4ceadf8f-737e-4cf6-8a38-a4cbd5eddc3c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG342 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG342 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG342 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,44f6025c-d06b-4421-8591-6380f7cda128
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '44f6025c-d06b-4421-8591-6380f7cda128', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG346 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'GEOG346 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG346 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG346 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG346 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,59bb6da9-e6dc-4ff8-82ea-3c398ea08f08
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '59bb6da9-e6dc-4ff8-82ea-3c398ea08f08', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG346 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG346 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,79763b0a-7a4d-4d0d-9d4d-8487afb80a93
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '79763b0a-7a4d-4d0d-9d4d-8487afb80a93', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG384 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'GEOG384 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG384 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG384 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG384 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,5ffc8854-2a28-401e-af53-cc065fa5b08e
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '5ffc8854-2a28-401e-af53-cc065fa5b08e', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG384 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG384 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,f452e269-389a-46e1-9849-0154ccce9d3c
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of nuber courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'f452e269-389a-46e1-9849-0154ccce9d3c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG385 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'GEOG385 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG385 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG385 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG385 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,19a9f491-1b54-4c8f-ae35-2aaaeab011a2
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '19a9f491-1b54-4c8f-ae35-2aaaeab011a2', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG385 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG385 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,a0b65f11-8816-4d13-a8c1-13029bd54ec4
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'a0b65f11-8816-4d13-a8c1-13029bd54ec4', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG396 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG396 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG396 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG397 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG397 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG397 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,779dfa99-bf93-4fd4-9900-5b523252f4cf
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '779dfa99-bf93-4fd4-9900-5b523252f4cf', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG398 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG398 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG398 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG415 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG415 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG415 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG415 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG415 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,af0070a0-0a1d-45f6-9853-d9eaeae7a020
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'af0070a0-0a1d-45f6-9853-d9eaeae7a020', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG415 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG415 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG438 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG438 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG438 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG440 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG440 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG440 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG440 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG440 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG440 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG440 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,1ada78f2-e91c-4c74-9314-bf9fa3e16148
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '1ada78f2-e91c-4c74-9314-bf9fa3e16148', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG441 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG441 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG441 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG441 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG441 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG441 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG441 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,f1e5da96-6694-497c-acb5-ac7e94dded39
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'f1e5da96-6694-497c-acb5-ac7e94dded39', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG446 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG446 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG446 Prerequisites')
/
--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG446 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG446 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,8df91968-9e50-4790-859c-996827130c8c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '8df91968-9e50-4790-859c-996827130c8c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG446 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG446 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG472 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG472 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG472 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,33830275-3bfd-43e9-a5f6-4cc198edb4d6
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '33830275-3bfd-43e9-a5f6-4cc198edb4d6', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG473 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG473 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG473 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,7a2da288-609f-488f-903f-489040a68325
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '7a2da288-609f-488f-903f-489040a68325', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG475 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG475 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG475 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,5bcaf87e-d25d-49a0-9b52-9672796ff4b2
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '5bcaf87e-d25d-49a0-9b52-9672796ff4b2', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG476 v6 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG476 v6 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG476 v6 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,eb7662a1-6436-4bb2-925a-1a968eb0ee3f
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'eb7662a1-6436-4bb2-925a-1a968eb0ee3f', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG476 v6 Corequisites,AND,kuali.reqComponent.type.course.courseset.enrolled.nof,Must be concurrently enrolled in a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.enrolled.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG476 v6 Corequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG476 v6 Corequisites')
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,1ea8dd3b-669c-4ab6-948d-dfb511f89815
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'enrolledCourses' and nmspc_cd = 'KS-SYS'), 1, 'Concurrently enrolled in a minimum of number courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '1ea8dd3b-669c-4ab6-948d-dfb511f89815', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG476 v8 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG476 v8 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG476 v8 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,356b8c34-771f-4000-98b6-1a579775074d
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '356b8c34-771f-4000-98b6-1a579775074d', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG496 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG496 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG496 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,29
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '29', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG603 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'GEOG603 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG603 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG603 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG603 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG603 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG603 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,13789ff2-119b-413a-9647-0b07c5781a89
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '13789ff2-119b-413a-9647-0b07c5781a89', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG604 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'GEOG604 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG604 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG604 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG604 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG604 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG604 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,13789ff2-119b-413a-9647-0b07c5781a89
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '13789ff2-119b-413a-9647-0b07c5781a89', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG615 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG615 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG615 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG615 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG615 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,2afeee88-a6ad-4518-ba69-cd9c797c7639
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '2afeee88-a6ad-4518-ba69-cd9c797c7639', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG615 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG615 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--GEOG642 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'GEOG642 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG642 Prerequisites')
/
--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG642 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG642 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,d3b84c5d-0867-45d2-81f8-9d664b5d46f1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'd3b84c5d-0867-45d2-81f8-9d664b5d46f1', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG642 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'GEOG642 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,53
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '53', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MATH--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--MATH110 Antirequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH110 Antirequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MATH110 Antirequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,750ae9d6-f65a-4af1-b101-a79f81338c45
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Not successfully completed any courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '750ae9d6-f65a-4af1-b101-a79f81338c45', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MATH112 Antirequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH112 Antirequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MATH112 Antirequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,6037ecc6-3746-4aee-9c0b-a198d6f0684d
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Not successfully completed any courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '6037ecc6-3746-4aee-9c0b-a198d6f0684d', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MATH113 Antirequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH113 Antirequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MATH113 Antirequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,00ad0bf1-6891-4408-8700-d0c10759d47a
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Not successfully completed any courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '00ad0bf1-6891-4408-8700-d0c10759d47a', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MATH115 Antirequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH115 Antirequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MATH115 Antirequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,08cae8cc-86af-41b5-a332-5e3b9d3cf19d
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Not successfully completed any courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '08cae8cc-86af-41b5-a332-5e3b9d3cf19d', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MATH130 Credit Restriction,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH130 Credit Restriction'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MATH130 Credit Restriction')
/
--kuali.reqComponent.field.type.course.cluSet.id,12ff806e-938c-4f2a-977b-cb2dabea78f7
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Not successfully completed any courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '12ff806e-938c-4f2a-977b-cb2dabea78f7', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MATH140 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH140 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MATH140 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,bb33b0a4-289e-4589-b63e-e08186781c36
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Not successfully completed any courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'bb33b0a4-289e-4589-b63e-e08186781c36', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MATH220 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH220 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MATH220 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,6a7bfd53-54df-4c0c-ba89-f81d539d5cda
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Not successfully completed any courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '6a7bfd53-54df-4c0c-ba89-f81d539d5cda', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--MUSC601 Prerequisites,OR,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC601 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MUSC601 Prerequisites')
/
--kuali.reqComponent.field.type.program.cluSet.id,5da8869d-c1cd-4b13-a3e3-9abb63bfa857
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'admittedToProgram' and nmspc_cd = 'KS-SYS'), 1, 'Admitted to the list program.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.program.cluSet.id', '5da8869d-c1cd-4b13-a3e3-9abb63bfa857', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC603 Prerequisites,OR,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC603 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MUSC603 Prerequisites')
/
--kuali.reqComponent.field.type.program.cluSet.id,621b3b43-cf72-40da-9173-4e2444df4b3d
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'admittedToProgram' and nmspc_cd = 'KS-SYS'), 1, 'Admitted to the list program.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.program.cluSet.id', '621b3b43-cf72-40da-9173-4e2444df4b3d', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC605 Prerequisites,AND,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC605 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MUSC605 Prerequisites')
/
--kuali.reqComponent.field.type.program.cluSet.id,a1c7362a-e6b6-408b-8d34-709db7541167
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'admittedToProgram' and nmspc_cd = 'KS-SYS'), 1, 'Admitted to the list program.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.program.cluSet.id', 'a1c7362a-e6b6-408b-8d34-709db7541167', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC605 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC605 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,7f06d0a7-157e-4925-a67f-33964999e79b
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '7f06d0a7-157e-4925-a67f-33964999e79b', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC605 Prerequisites,OR,kuali.reqComponent.type.course.permission.instructor.required,Permission of instructor required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of instructor required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.permission.instructor.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC605 Prerequisites'), 1, NULL)
/
-- TODO No matching term specification
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Permission of instructor.')
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC611 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC611 Corequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MUSC611 Corequisites')
/
--kuali.reqComponent.field.type.course.clu.id,c091a996-6c66-465a-88f1-9c0deeafc7e1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'enrolledCourses' and nmspc_cd = 'KS-SYS'), 1, 'Concurrently enrolled in list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'c091a996-6c66-465a-88f1-9c0deeafc7e1', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC611 Prerequisites,AND,kuali.reqComponent.type.course.program.notadmitted,Must not have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.notadmitted.to.program'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC611 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MUSC611 Prerequisites')
/
--kuali.reqComponent.field.type.program.cluSet.id,2913ff5d-936d-4cc5-be4d-4d35b20dbfb3
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'admittedToProgram' and nmspc_cd = 'KS-SYS'), 1, 'Not admitted to the list program.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.program.cluSet.id', '2913ff5d-936d-4cc5-be4d-4d35b20dbfb3', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC613 Corequisites,AND,kuali.reqComponent.type.course.courseset.enrolled.all,Must be concurrently enrolled in all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.enrolled.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC613 Corequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MUSC613 Corequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,acc8feed-0e06-471e-9f57-923a82644634
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'enrolledCourses' and nmspc_cd = 'KS-SYS'), 1, 'Concurrently enrolled in all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'acc8feed-0e06-471e-9f57-923a82644634', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC613 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC613 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'MUSC613 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,55
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '55', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC613 Prerequisites,AND,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC613 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.program.cluSet.id,7a7ae03e-9b11-4a77-af94-bb3744e11294
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'admittedToProgram' and nmspc_cd = 'KS-SYS'), 1, 'Admitted to the list program.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.program.cluSet.id', '7a7ae03e-9b11-4a77-af94-bb3744e11294', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--MUSC613 Prerequisites,AND,kuali.reqComponent.type.course.courseset.nof.grade.min,Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <gradeType> <grade>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <grade>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.nof.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC613 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.b
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,50342ded-2fd1-4cf0-81d4-cd46df8e336a
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully complete a minimum of number courses from list with a minimum grade of type mark.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '50342ded-2fd1-4cf0-81d4-cd46df8e336a', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.b', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_prop_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/