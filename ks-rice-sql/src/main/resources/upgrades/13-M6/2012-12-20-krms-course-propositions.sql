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

--BSCI--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--BSCI207 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI207 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI207 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,cd3e4553-4372-45ad-ade6-ff3b33735870
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'cd3e4553-4372-45ad-ade6-ff3b33735870', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI222 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI222 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI222 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,6f7421b8-2fe6-4b42-90c8-cbe29dc503be
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '6f7421b8-2fe6-4b42-90c8-cbe29dc503be', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI223 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI223 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI223 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,28d48337-de87-498c-b417-866486e82eab
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Succesfully completed course')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '28d48337-de87-498c-b417-866486e82eab', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI330 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI330 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI330 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,9f7b733d-fb49-424f-bda5-01fadfdd30ab
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '9f7b733d-fb49-424f-bda5-01fadfdd30ab', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI334 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI334 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI334 Prerequisites')
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,b08de962-8ba4-4275-9596-e9ca172fa6da
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'b08de962-8ba4-4275-9596-e9ca172fa6da', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI335 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI335 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI335 Prerequisites')
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,ab355005-776f-4617-ad59-0cadc391c435
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'ab355005-776f-4617-ad59-0cadc391c435', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI337 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'BSCI337 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI337 Prerequisites')
/
--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI337 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI337 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI337 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI337 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI342 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'BSCI342 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI342 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI342 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI342 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI342 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI342 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,28d48337-de87-498c-b417-866486e82eab
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '28d48337-de87-498c-b417-866486e82eab', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI360 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI360 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI360 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,97ae3d8d-4522-47ff-a8fe-9f8abedb12ab
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '97ae3d8d-4522-47ff-a8fe-9f8abedb12ab', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI361 v2 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'BSCI361 v2 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI361 v2 Prerequisites')
/
--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI361 v2 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI361 v2 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI361 v2 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI361 v2 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,363841c1-cc36-4360-81c0-4f1f98fca3f3
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '363841c1-cc36-4360-81c0-4f1f98fca3f3', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI361 v6 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'BSCI361 v6 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI361 v6 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI361 v6 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI361 v6 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,a67606b4-b3f0-421f-a8c5-ba306385d1a6
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'a67606b4-b3f0-421f-a8c5-ba306385d1a6', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI361 v6 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI361 v6 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI362 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI362 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI362 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI363 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI363 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI363 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI366 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'BSCI366 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI366 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI366 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI366 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,fb5f3371-7c42-4087-a96e-bb9d6d7e7136
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'fb5f3371-7c42-4087-a96e-bb9d6d7e7136', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI366 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI366 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI370 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI370 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI370 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI373 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'BSCI373 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI373 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI373 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI373 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI373 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI373 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
--kuali.reqComponent.field.type.value.positive.integer,3
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCreditsFromOrganization' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number credits from courses in the organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '3', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI379 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI379 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI379 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI380 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI380 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI380 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,375ab6aa-b37e-4cb4-928f-b2ae928f3335
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '375ab6aa-b37e-4cb4-928f-b2ae928f3335', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI380 Prerequisites,OR,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI380 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI380 Prerequisites')
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,9630d314-4392-4d51-8de9-491f0fe09d3c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '9630d314-4392-4d51-8de9-491f0fe09d3c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI380 Prerequisites,OR,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI380 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,97550fcf-0a3a-4523-af35-34b39ec5b13f
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '97550fcf-0a3a-4523-af35-34b39ec5b13f', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI380 Preperation,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI380 Preperation'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI380 Preperation')
/
--kuali.reqComponent.field.type.course.clu.id,c4c52302-e533-4cb1-9cbd-1cd8bea58cf0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'c4c52302-e533-4cb1-9cbd-1cd8bea58cf0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI389 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI389 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI389 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI392 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI392 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI392 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI394 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'BSCI394 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI394 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI394 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI394 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,369bb607-c488-4323-ab73-9060a667d219
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '369bb607-c488-4323-ab73-9060a667d219', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI394 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI394 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,f69f7104-aa38-4613-9d4a-6cfa661f8c5f
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'f69f7104-aa38-4613-9d4a-6cfa661f8c5f', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI398 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI398 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI398 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI399 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI399 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI399 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI410 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI410 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI410 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,674ea61d-154a-4535-90db-dbca3392d023
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '674ea61d-154a-4535-90db-dbca3392d023', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI412 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI412 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI412 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,61edada9-2aa0-466f-a7ef-adfe549c2699
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '61edada9-2aa0-466f-a7ef-adfe549c2699', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI414 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI414 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI414 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,f7000cfc-3bf3-4fc8-9363-be39978b3758
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'f7000cfc-3bf3-4fc8-9363-be39978b3758', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI416 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI416 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI416 Prerequisites')
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,d8d93538-3239-4b1c-b953-7c6a9201adb2
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'd8d93538-3239-4b1c-b953-7c6a9201adb2', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI417 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI417 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI417 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,61409af7-8cfe-44ab-ae86-a6d5b6f89b59
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '61409af7-8cfe-44ab-ae86-a6d5b6f89b59', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI420 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI420 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI420 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,a36af94d-5151-4fe0-a6ed-50761e967387
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'a36af94d-5151-4fe0-a6ed-50761e967387', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI421 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI421 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI421 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,4d52420d-9429-4301-a03a-a493bff4f872
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '4d52420d-9429-4301-a03a-a493bff4f872', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI422 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI422 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI422 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,886db399-9b88-4786-9f88-23c82bd4a545
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '886db399-9b88-4786-9f88-23c82bd4a545', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI423 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI423 Corequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI423 Corequisites')
/
--kuali.reqComponent.field.type.course.clu.id,f27fb60c-e7a2-4e92-b3de-23c8e6c20e10
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'enrolledCourses' and nmspc_cd = 'KS-SYS'), 1, 'Concurrently enrolled in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'f27fb60c-e7a2-4e92-b3de-23c8e6c20e10', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI423 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI423 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI423 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,463779dd-eec4-42e5-b15a-987c263351c1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '463779dd-eec4-42e5-b15a-987c263351c1', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI424 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI424 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI424 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,3f2a1c00-974b-4755-8e25-f38743b1a7a3
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '3f2a1c00-974b-4755-8e25-f38743b1a7a3', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI425 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI425 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI425 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,3f2a1c00-974b-4755-8e25-f38743b1a7a3
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '3f2a1c00-974b-4755-8e25-f38743b1a7a3', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI426 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI426 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI426 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,595cf72b-7b98-463f-aa74-7e4770ee6da0
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '595cf72b-7b98-463f-aa74-7e4770ee6da0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI426 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI426 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,1b262ae8-9d1f-4758-a943-db4f89b66587
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '1b262ae8-9d1f-4758-a943-db4f89b66587', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI426 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI426 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,edb62cd5-91ec-4938-99d2-18c5c6a71225
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'edb62cd5-91ec-4938-99d2-18c5c6a71225', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI430 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI430 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI430 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,3d8ff082-1b98-4a6c-a309-726efa430521
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '3d8ff082-1b98-4a6c-a309-726efa430521', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI433 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'BSCI433 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI433 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI433 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI433 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,75fe6c37-54ff-4a11-9816-89c0b84d913d
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '75fe6c37-54ff-4a11-9816-89c0b84d913d', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI433 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI433 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI434 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'BSCI434 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI434 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI434 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI434 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI434 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI434 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,90362179-9736-40f8-85da-9f6928c880f5
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '90362179-9736-40f8-85da-9f6928c880f5', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI437 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'BSCI437 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI437 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI437 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI437 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI437 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI437 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,f7000cfc-3bf3-4fc8-9363-be39978b3758
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'f7000cfc-3bf3-4fc8-9363-be39978b3758', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI440 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'BSCI440 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI440 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI440 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI440 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI440 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI440 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,1b262ae8-9d1f-4758-a943-db4f89b66587
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '1b262ae8-9d1f-4758-a943-db4f89b66587', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI441 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI441 Corequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI441 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,06226b21-1a3e-4223-9782-8e840dbe56a5
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'enrolledCourses' and nmspc_cd = 'KS-SYS'), 1, 'Concurrently enrolled in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '06226b21-1a3e-4223-9782-8e840dbe56a5', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI442 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI442 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI442 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,2572469d-c39c-4cf8-b2f7-41a9bb75757f
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '2572469d-c39c-4cf8-b2f7-41a9bb75757f', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI443 v2 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'BSCI443 v2 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI443 v2 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.nof.grade.min,Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <gradeType> <grade>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <grade>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.nof.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI443 v2 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI443 v2 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,f3db0f35-3ccf-4bee-bf68-7820c66d0af1
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully complete a minimum of number courses from list with a minimum grade of type mark')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'f3db0f35-3ccf-4bee-bf68-7820c66d0af1', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI443 v2 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI443 v2 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,5001c013-f6f5-479b-a030-772004a281c0
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '5001c013-f6f5-479b-a030-772004a281c0', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI443 v6 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'BSCI443 v6 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI443 v6 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI443 v6 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI443 v6 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,c3b1b32d-a64c-4da1-af4b-2810a50eb4f2
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'c3b1b32d-a64c-4da1-af4b-2810a50eb4f2', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.nof.grade.min,Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <gradeType> <grade>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <grade>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.nof.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI443 v6 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI443 v6 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,733cb89b-d775-4352-98c4-aefe336bc1cf
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully complete a minimum of number courses from list with a minimum grade of type mark')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '733cb89b-d775-4352-98c4-aefe336bc1cf', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI446 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI446 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI446 Prerequisites')
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,b5640d5e-5a65-4539-8b89-76ae40f1d9ee
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'b5640d5e-5a65-4539-8b89-76ae40f1d9ee', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI447 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI447 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI447 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,eee56265-e4f0-4cfd-b094-debdd31cd373
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'eee56265-e4f0-4cfd-b094-debdd31cd373', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI451 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI451 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI451 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,1b262ae8-9d1f-4758-a943-db4f89b66587
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '1b262ae8-9d1f-4758-a943-db4f89b66587', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI453 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI453 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI453 Prerequisites')
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,1f65d236-a417-4696-999d-1c4738a11c21
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '1f65d236-a417-4696-999d-1c4738a11c21', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI454 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'BSCI454 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI454 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI454 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI454 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,eca730c0-ab39-427f-be35-ced7815047a3
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'eca730c0-ab39-427f-be35-ced7815047a3', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI454 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI454 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,b11c8680-1bfd-4246-9e5d-0836fc6624b8
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'b11c8680-1bfd-4246-9e5d-0836fc6624b8', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI460 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI460 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI460 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI461 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI461 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI461 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,b90d435a-2b9b-44d6-be43-e8ee8024e76b
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'b90d435a-2b9b-44d6-be43-e8ee8024e76b', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI462 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI462 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI462 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,37e36386-a349-4af3-ba47-b518d015ebe6
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '37e36386-a349-4af3-ba47-b518d015ebe6', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI463 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI463 Corequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI463 Corequisites')
/
--kuali.reqComponent.field.type.course.clu.id,e5841f88-648d-4fd2-9fda-96db754ede8d
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'enrolledCourses' and nmspc_cd = 'KS-SYS'), 1, 'Concurrently enrolled in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'e5841f88-648d-4fd2-9fda-96db754ede8d', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI464 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI464 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI464 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,b7dc29ce-0b30-4538-9f9f-86a5c36f3d72
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'b7dc29ce-0b30-4538-9f9f-86a5c36f3d72', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI465 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'BSCI465 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI465 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI465 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI465 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,038adb76-d322-478e-8ac1-ae04fb498896
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '038adb76-d322-478e-8ac1-ae04fb498896', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '1', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI465 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI465 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI467 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI467 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI467 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI471 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'BSCI471 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI471 Prerequisites')
/
--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI471 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI471 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,f7000cfc-3bf3-4fc8-9363-be39978b3758
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'f7000cfc-3bf3-4fc8-9363-be39978b3758', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI471 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI471 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI473 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI473 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI473 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,eb456fea-2e62-4abe-8cb9-7a5c8be321b7
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'eb456fea-2e62-4abe-8cb9-7a5c8be321b7', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI474 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI474 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI474 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,5b46b07d-fb95-491c-aafa-f1de6d49bdf8
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '5b46b07d-fb95-491c-aafa-f1de6d49bdf8', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI480 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI480 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI480 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI485 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI485 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI485 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,905d2ce9-e5be-41d1-962e-1ceb58aed08b
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '905d2ce9-e5be-41d1-962e-1ceb58aed08b', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI493 Prerequisites,OR,kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI493 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI493 Prerequisites')
/
--73bca644-79a5-4806-a785-cd538ccc07e9,kuali.reqComponent.field.type.org.id,65
--73bca644-79a5-4806-a785-cd538ccc07e9,kuali.reqComponent.field.type.value.positive.integer,4
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCreditsFromOrganization' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number credits from courses in the organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '4', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI493 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI493 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,ae8d95e0-6292-478f-af72-9c7f21afdd20
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'ae8d95e0-6292-478f-af72-9c7f21afdd20', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI493 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI493 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,28d48337-de87-498c-b417-866486e82eab
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '28d48337-de87-498c-b417-866486e82eab', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI494 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI494 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'BSCI494 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,67dcad60-bb8a-42b0-9ad6-e2dbb48b8981
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '67dcad60-bb8a-42b0-9ad6-e2dbb48b8981', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI494 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI494 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '65', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI494 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI494 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '485712a2-5559-44d5-9c5d-5c765910d5c0', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--CCJS188 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS188 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS188 Prerequisites')
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,6c03e5ed-0e16-4694-be6e-784b184627b4
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '6c03e5ed-0e16-4694-be6e-784b184627b4', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS200 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'CCJS200 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS200 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS200 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS200 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,3a9c67dc-fd39-44e3-b909-3d9aaf7918f9
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '3a9c67dc-fd39-44e3-b909-3d9aaf7918f9', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS200 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS200 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,36b87d43-96bb-4429-8c1e-ac21493b4858
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'gradeTypeForCourses' and nmspc_cd = 'KS-SYS'), 1, 'Earned a minimum grade of type mark in list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.grade.id', 'kuali.result.value.grade.letter.c', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '36b87d43-96bb-4429-8c1e-ac21493b4858', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS230 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS230 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS230 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS234 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS234 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS234 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,97b036d1-23d0-496f-b0ab-5da9f4b89391
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '97b036d1-23d0-496f-b0ab-5da9f4b89391', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS288 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS288 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS288 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,f5c72ab9-ec6d-4d56-a353-6bc1a22dbdc8
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'f5c72ab9-ec6d-4d56-a353-6bc1a22dbdc8', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS300 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'CCJS300 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS300 Prerequisites')
/
--kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS300 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS300 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,8fb23a72-2a34-46b4-8f1c-2cf78d2f4a7a
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCompletedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '8fb23a72-2a34-46b4-8f1c-2cf78d2f4a7a', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS300 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS300 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,0e9032c0-179a-431a-bf70-b18229770487
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '0e9032c0-179a-431a-bf70-b18229770487', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS310 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS310 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS310 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,60ba3f8e-d7fd-4d2e-86b0-78cfaacfae4c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '60ba3f8e-d7fd-4d2e-86b0-78cfaacfae4c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS320 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS320 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS320 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,399bbd4a-9cd5-4564-8935-fd566e263910
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '399bbd4a-9cd5-4564-8935-fd566e263910', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS330 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS330 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS330 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,5425dbe8-f94e-41ed-8e81-1b58ecb6d6ac
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '5425dbe8-f94e-41ed-8e81-1b58ecb6d6ac', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS331 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS331 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS331 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,43859020-90f7-4b97-a75f-4fdecfb04af8
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '43859020-90f7-4b97-a75f-4fdecfb04af8', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS332 Prerequisites,AND,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS332 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS332 Prerequisites')
/
--kuali.reqComponent.field.type.program.cluSet.id,2f8e6e28-380a-4cb6-9e59-5f85f5af1f19
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'admittedToProgram' and nmspc_cd = 'KS-SYS'), 1, 'Admitted to the list program')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.program.cluSet.id', '2f8e6e28-380a-4cb6-9e59-5f85f5af1f19', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS340 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS340 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS340 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS350 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS350 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS350 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,28d4f15c-c1b6-4ed9-a561-15dd9ab379d4
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '28d4f15c-c1b6-4ed9-a561-15dd9ab379d4', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS352 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS352 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS352 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS357 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'CCJS357 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS357 Prerequisites')
/
--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS357 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS357 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS357 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS357 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS359 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'CCJS359 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS359 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS359 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS359 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS359 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS359 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
--kuali.reqComponent.field.type.value.positive.integer,6
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCreditsFromOrganization' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number credits from courses in the organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '6', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS360 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS360 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS360 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,28d4f15c-c1b6-4ed9-a561-15dd9ab379d4
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '28d4f15c-c1b6-4ed9-a561-15dd9ab379d4', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS370 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS370 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS370 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS386 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS386 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS386 Prerequisites')
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS388 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS388 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS388 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,9eab576b-2636-4825-8f36-17b7def3b080
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '9eab576b-2636-4825-8f36-17b7def3b080', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS389 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS389 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS389 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,28d4f15c-c1b6-4ed9-a561-15dd9ab379d4
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', '28d4f15c-c1b6-4ed9-a561-15dd9ab379d4', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS398 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'CCJS398 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS398 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS398 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS398 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS398 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS398 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
--kuali.reqComponent.field.type.value.positive.integer,6
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCreditsFromOrganization' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number credits from courses in the organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '6', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS399 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'CCJS399 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS399 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS399 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS399 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
--kuali.reqComponent.field.type.value.positive.integer,12
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '12', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS399 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS399 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCreditsFromOrganization' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number credits from courses in the organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS400 v6 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'CCJS400 v6 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS400 v6 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS400 v6 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS400 v6 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS400 v6 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS400 v6 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,451706e8-6dfb-4638-b6ba-3b4f3469af9c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '451706e8-6dfb-4638-b6ba-3b4f3469af9c', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS400 v8 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'CCJS400 v8 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS400 v8 Prerequisites')
/
--kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS400 v8 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS400 v8 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.value.positive.integer,12
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'numberOfCreditsFromOrganization' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed a minimum of number credits from courses in the organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '12', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS400 v8 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS400 v8 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS432 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS432 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS432 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,1605dc0f-b763-4441-bfbf-bde70c3bbfae
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '1605dc0f-b763-4441-bfbf-bde70c3bbfae', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS444 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS444 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS444 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,95a4ef4d-8039-4d23-aa03-1c5b966f503a
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '95a4ef4d-8039-4d23-aa03-1c5b966f503a', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS451 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'CCJS451 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS451 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS451 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS451 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS451 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS451 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,18beb5ee-cc99-4d28-b0d0-56c5367dbf8e
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '18beb5ee-cc99-4d28-b0d0-56c5367dbf8e', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS452 Prerequisites,OR,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet 1 of the following', NULL, 'C', '|', (Select rule_id from krms_rule_t where nm = 'CCJS452 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS452 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS452 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS452 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS452 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS452 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.cluSet.id,f8dbb063-d026-4b1c-8b5a-ac7acc7571bc
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'f8dbb063-d026-4b1c-8b5a-ac7acc7571bc', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS453 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS453 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS453 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,d6d71b1b-9769-4c53-84f8-7932edb17872
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'd6d71b1b-9769-4c53-84f8-7932edb17872', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS454 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS454 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS454 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,77ca3e9e-ca86-43a4-b948-db265b290f58
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '77ca3e9e-ca86-43a4-b948-db265b290f58', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS455 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'CCJS455 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS455 Prerequisites')
/
--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS455 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS455 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,cee1942d-e11f-443f-b177-9542750c7579
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'cee1942d-e11f-443f-b177-9542750c7579', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS455 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS455 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS456 Prerequisites,AND,
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must meet all of the following', NULL, 'C', '&', (Select rule_id from krms_rule_t where nm = 'CCJS456 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS456 Prerequisites')
/
--kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.course.drop.org.permission.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS456 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS456 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.org.id,214   
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'adminOrganizationPermissionRequired' and nmspc_cd = 'KS-SYS'), 1, 'Permission of organization')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.org.id', '214', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS456 Prerequisites'), 1, NULL)
/
insert into krms_cmpnd_prop_props_t (cmpnd_prop_id, prop_id) values ((select prop_id from krms_prop_t where rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS456 Prerequisites') and dscrm_typ_cd = 'C'), krms_prop_s.currval)
/
--kuali.reqComponent.field.type.course.clu.id,fb0ed0b1-96c7-44e6-a649-871de27702e9
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'fb0ed0b1-96c7-44e6-a649-871de27702e9', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS457 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS457 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS457 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,24c1a647-7dcd-4707-837c-89dd2da26f84
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '24c1a647-7dcd-4707-837c-89dd2da26f84', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS461 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS461 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS461 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,7560ec21-2025-47b8-810d-50f2062f0a30
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '7560ec21-2025-47b8-810d-50f2062f0a30', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS462 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS462 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS462 Prerequisites')
/
--kuali.reqComponent.field.type.course.cluSet.id,e8ad4ea9-593e-48e0-8ca5-116679270817
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourses' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed all courses from list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'e8ad4ea9-593e-48e0-8ca5-116679270817', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS600 Prerequisites,AND,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS600 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS600 Prerequisites')
/
--kuali.reqComponent.field.type.program.cluSet.id,11770bd5-7df1-4a9a-8676-a44633ad96c4
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'admittedToProgram' and nmspc_cd = 'KS-SYS'), 1, 'Admitted to the list program')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.program.cluSet.id', '11770bd5-7df1-4a9a-8676-a44633ad96c4', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS653 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.krms.proposition.type.success.compl.course'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS653 Prerequisites'), 1, NULL)
/
update krms_rule_t SET prop_id = krms_prop_s.currval WHERE rule_id = (Select rule_id from krms_rule_t where nm = 'CCJS653 Prerequisites')
/
--kuali.reqComponent.field.type.course.clu.id,fb4e3445-743f-4d61-aa28-97aa4424356b
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) values (krms_term_s.nextval, (Select term_spec_id from krms_term_spec_t where nm = 'completedCourse' and nmspc_cd = 'KS-SYS'), 1, 'Successfully completed list')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) values (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.clu.id', 'fb4e3445-743f-4d61-aa28-97aa4424356b', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/