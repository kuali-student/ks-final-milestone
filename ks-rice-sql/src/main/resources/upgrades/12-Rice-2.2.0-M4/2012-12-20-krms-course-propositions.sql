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
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI207 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,cd3e4553-4372-45ad-ade6-ff3b33735870
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) VALUES (krms_term_s.nextval, 'termSpecId', 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) VALUES (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', 'cd3e4553-4372-45ad-ade6-ff3b33735870', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI222 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI222 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,6f7421b8-2fe6-4b42-90c8-cbe29dc503be
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) VALUES (krms_term_s.nextval, 'termSpecId', 1, 'Successfully completed all courses from list.')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) VALUES (krms_term_parm_s.nextval, krms_term_s.currval, 'kuali.reqComponent.field.type.course.cluSet.id', '6f7421b8-2fe6-4b42-90c8-cbe29dc503be', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI223 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI223 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,28d48337-de87-498c-b417-866486e82eab
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) VALUES (krms_term_s.nextval, 'termSpecId', 1, '????')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) VALUES (krms_term_parm_s.nextval, krms_term_s.currval, '??????', '?????', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI330 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI330 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,9f7b733d-fb49-424f-bda5-01fadfdd30ab
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
insert into krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) VALUES (krms_term_s.nextval, 'termSpecId', 1, '????')
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) VALUES (krms_term_parm_s.nextval, krms_term_s.currval, '??????', '?????', 1)
/
insert into krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) VALUES (krms_term_parm_s.nextval, krms_term_s.currval, '??????', '?????', 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, krms_term_s.currval, 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI334 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI334 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,b08de962-8ba4-4275-9596-e9ca172fa6da
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI335 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI335 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,ab355005-776f-4617-ad59-0cadc391c435
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI337 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI337 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI337 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI337 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI342 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI342 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI342 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI342 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,28d48337-de87-498c-b417-866486e82eab
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI360 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI360 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,97ae3d8d-4522-47ff-a8fe-9f8abedb12ab
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI361 v2 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI361 v2 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI361 v2 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI361 v2 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,363841c1-cc36-4360-81c0-4f1f98fca3f3
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI361 v6 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI361 v6 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,a67606b4-b3f0-421f-a8c5-ba306385d1a6
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI361 v6 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI361 v6 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI362 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI362 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI363 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI363 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI366 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI366 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,fb5f3371-7c42-4087-a96e-bb9d6d7e7136
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI366 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI366 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI370 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI370 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI373 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI373 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI373 Prerequisites,AND,kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI373 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
--kuali.reqComponent.field.type.value.positive.integer,3
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI379 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI379 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI380 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI380 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,375ab6aa-b37e-4cb4-928f-b2ae928f3335
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI380 Prerequisites,OR,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI380 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,9630d314-4392-4d51-8de9-491f0fe09d3c
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI380 Prerequisites,OR,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI380 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,97550fcf-0a3a-4523-af35-34b39ec5b13f
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI380 Preperation,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI380 Preperation'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,c4c52302-e533-4cb1-9cbd-1cd8bea58cf0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI389 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI389 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI392 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI392 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI394 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI394 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,369bb607-c488-4323-ab73-9060a667d219
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI394 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI394 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,f69f7104-aa38-4613-9d4a-6cfa661f8c5f
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI398 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI398 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI399 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI399 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI410 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI410 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,674ea61d-154a-4535-90db-dbca3392d023
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI412 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI412 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,61edada9-2aa0-466f-a7ef-adfe549c2699
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI414 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI414 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,f7000cfc-3bf3-4fc8-9363-be39978b3758
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI416 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI416 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,d8d93538-3239-4b1c-b953-7c6a9201adb2
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI417 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI417 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,61409af7-8cfe-44ab-ae86-a6d5b6f89b59
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI420 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI420 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,a36af94d-5151-4fe0-a6ed-50761e967387
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI421 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI421 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,4d52420d-9429-4301-a03a-a493bff4f872
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI422 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI422 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,886db399-9b88-4786-9f88-23c82bd4a545
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI423 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI423 Corequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,f27fb60c-e7a2-4e92-b3de-23c8e6c20e10
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI423 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI423 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,463779dd-eec4-42e5-b15a-987c263351c1
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI424 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI424 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,3f2a1c00-974b-4755-8e25-f38743b1a7a3
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI425 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI425 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,3f2a1c00-974b-4755-8e25-f38743b1a7a3
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI426 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI426 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,595cf72b-7b98-463f-aa74-7e4770ee6da0
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI426 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI426 Prerequisites'), 1, NULL)
/
--BSCI426 Prerequisites,Must have successfully completed <course>,df041df4-059c-4abb-b8e7-4bab81a21e48,kuali.reqComponent.field.type.course.clu.id,1b262ae8-9d1f-4758-a943-db4f89b66587
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI426 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI426 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,edb62cd5-91ec-4938-99d2-18c5c6a71225
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI430 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI430 Prerequisites'), 1, NULL)
/
--BSCI430 Prerequisites,Must have successfully completed all courses from <courses>,0da24a86-fae3-47ed-861c-8cfa41b6e2f0,kuali.reqComponent.field.type.course.cluSet.id,3d8ff082-1b98-4a6c-a309-726efa430521
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI433 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI433 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI433 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI433 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,75fe6c37-54ff-4a11-9816-89c0b84d913d
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI434 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI434 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI434 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI434 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,90362179-9736-40f8-85da-9f6928c880f5
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI437 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI437 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI437 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI437 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,f7000cfc-3bf3-4fc8-9363-be39978b3758
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI440 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI440 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI440 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI440 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,1b262ae8-9d1f-4758-a943-db4f89b66587
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI441 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI441 Corequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,06226b21-1a3e-4223-9782-8e840dbe56a5
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI442 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI442 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,2572469d-c39c-4cf8-b2f7-41a9bb75757f
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI443 v2 Prerequisites,AND,kuali.reqComponent.type.course.courseset.nof.grade.min,Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <gradeType> <grade>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <grade>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.nof.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI443 v2 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,f3db0f35-3ccf-4bee-bf68-7820c66d0af1
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI443 v2 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI443 v2 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,5001c013-f6f5-479b-a030-772004a281c0
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI443 v6 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI443 v6 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,c3b1b32d-a64c-4da1-af4b-2810a50eb4f2
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI443 v6 Prerequisites,AND,kuali.reqComponent.type.course.courseset.nof.grade.min,Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <gradeType> <grade>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <grade>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.nof.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI443 v6 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,733cb89b-d775-4352-98c4-aefe336bc1cf
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.value.positive.integer,1
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI446 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI446 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,b5640d5e-5a65-4539-8b89-76ae40f1d9ee
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI447 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI447 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,eee56265-e4f0-4cfd-b094-debdd31cd373
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI451 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI451 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,1b262ae8-9d1f-4758-a943-db4f89b66587
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI453 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI453 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--kuali.reqComponent.field.type.course.cluSet.id,1f65d236-a417-4696-999d-1c4738a11c21
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI454 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI454 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,eca730c0-ab39-427f-be35-ced7815047a3

insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI454 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI454 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,b11c8680-1bfd-4246-9e5d-0836fc6624b8
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI460 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI460 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI461 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI461 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,b90d435a-2b9b-44d6-be43-e8ee8024e76b
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI462 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI462 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,37e36386-a349-4af3-ba47-b518d015ebe6
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI463 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI463 Corequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,e5841f88-648d-4fd2-9fda-96db754ede8d
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI464 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI464 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.cluSet.id,b7dc29ce-0b30-4538-9f9f-86a5c36f3d72
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI465 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI465 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.value.positive.integer,1
--kuali.reqComponent.field.type.course.cluSet.id,038adb76-d322-478e-8ac1-ae04fb498896
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI465 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI465 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI467 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI467 Prerequisites'), 1, NULL)
/
--kuali.reqComponent.field.type.org.id,65
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI471 Prerequisites,Must have successfully completed <course>,88425afc-1a66-4c71-80f4-30693f653488,kuali.reqComponent.field.type.course.clu.id,f7000cfc-3bf3-4fc8-9363-be39978b3758
--BSCI471 Prerequisites,Permission of <administering org> required,d1ebba64-4f54-4241-aad7-ec4d18b42e2e,kuali.reqComponent.field.type.org.id,65
--BSCI473 Prerequisites,Must have successfully completed <course>,d46df6e6-3405-4366-85ed-0a921394a84a,kuali.reqComponent.field.type.course.clu.id,eb456fea-2e62-4abe-8cb9-7a5c8be321b7
--BSCI474 Prerequisites,Must have successfully completed all courses from <courses>,45e0b2c7-52f2-4d83-afd5-13bb4e8a2bc1,kuali.reqComponent.field.type.course.cluSet.id,5b46b07d-fb95-491c-aafa-f1de6d49bdf8
--BSCI480 Prerequisites,Permission of <administering org> required,5fd26f88-7ab3-4e98-a1ce-cc819a350512,kuali.reqComponent.field.type.org.id,65
--BSCI485 Prerequisites,Must have successfully completed all courses from <courses>,008f0616-4b04-4ff5-8e7a-369c700f89dc,kuali.reqComponent.field.type.course.cluSet.id,905d2ce9-e5be-41d1-962e-1ceb58aed08b

--BSCI471 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI471 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI471 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI471 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI473 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI473 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI474 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI474 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI480 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI480 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI485 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI485 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI493 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,73bca644-79a5-4806-a785-cd538ccc07e9,kuali.reqComponent.field.type.org.id,65
--BSCI493 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,73bca644-79a5-4806-a785-cd538ccc07e9,kuali.reqComponent.field.type.value.positive.integer,4
--BSCI493 Prerequisites,Must have successfully completed <course>,b4dc7979-4045-435a-b609-0b8aae25d62d,kuali.reqComponent.field.type.course.clu.id,28d48337-de87-498c-b417-866486e82eab
--BSCI493 Prerequisites,Must have successfully completed all courses from <courses>,b8887937-c083-4e1b-98fa-f06fa372a0f3,kuali.reqComponent.field.type.course.cluSet.id,ae8d95e0-6292-478f-af72-9c7f21afdd20

--BSCI493 Prerequisites,OR,kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI493 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI493 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI493 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI493 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI493 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI494 Prerequisites,Must have successfully completed a minimum of <n> courses from <courses>,3062e31a-ce82-488e-8520-cbe7a0e4e496,kuali.reqComponent.field.type.course.cluSet.id,67dcad60-bb8a-42b0-9ad6-e2dbb48b8981
--BSCI494 Prerequisites,Must have successfully completed a minimum of <n> courses from <courses>,3062e31a-ce82-488e-8520-cbe7a0e4e496,kuali.reqComponent.field.type.value.positive.integer,1
--BSCI494 Prerequisites,Must have successfully completed <course>,d8ad78fc-2389-4b52-bd57-7fa67bbbe664,kuali.reqComponent.field.type.course.clu.id,485712a2-5559-44d5-9c5d-5c765910d5c0
--BSCI494 Prerequisites,Permission of <administering org> required,f3144a03-1d60-4f0c-bc0c-219ecf5b887d,kuali.reqComponent.field.type.org.id,65

--BSCI494 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI494 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI494 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI494 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--BSCI494 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI494 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--CCJS188 Prerequisites,Must have successfully completed a minimum of <n> courses from <courses>,e65ae39d-4dcb-4c21-9c94-0d10cc6f756f,kuali.reqComponent.field.type.value.positive.integer,1
--CCJS188 Prerequisites,Must have successfully completed a minimum of <n> courses from <courses>,e65ae39d-4dcb-4c21-9c94-0d10cc6f756f,kuali.reqComponent.field.type.course.cluSet.id,6c03e5ed-0e16-4694-be6e-784b184627b4
--CCJS200 Prerequisites,Must have successfully completed all courses from <courses>,d54322ce-3a1c-4a2b-a7b0-76e9c758ff8a,kuali.reqComponent.field.type.course.cluSet.id,3a9c67dc-fd39-44e3-b909-3d9aaf7918f9
--CCJS200 Prerequisites,Must have earned a minimum grade of <gradeType> <grade> in <courses>,e787c77b-1e16-4340-a822-31d431d1c3ba,kuali.reqComponent.field.type.grade.id,kuali.result.value.grade.letter.c
--CCJS200 Prerequisites,Must have earned a minimum grade of <gradeType> <grade> in <courses>,e787c77b-1e16-4340-a822-31d431d1c3ba,kuali.reqComponent.field.type.course.cluSet.id,36b87d43-96bb-4429-8c1e-ac21493b4858
--CCJS230 Prerequisites,Must have successfully completed <course>,77c26eb9-7bf7-471e-b7eb-d057f482b67a,kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
--CCJS234 Prerequisites,Must have successfully completed all courses from <courses>,2067635f-e985-41d8-920f-8d1aa242099c,kuali.reqComponent.field.type.course.cluSet.id,97b036d1-23d0-496f-b0ab-5da9f4b89391
--CCJS288 Prerequisites,Must have successfully completed all courses from <courses>,153e91a4-d81c-4c19-9e9c-b51c27e902c5,kuali.reqComponent.field.type.course.cluSet.id,f5c72ab9-ec6d-4d56-a353-6bc1a22dbdc8

--CCJS188 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS188 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS200 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS200 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS200 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS200 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS230 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS230 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS234 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS234 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS288 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS288 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS300 Prerequisites,Must have successfully completed a minimum of <n> courses from <courses>,24c0d53d-2e98-48a4-a1b1-2c34ea178aad,kuali.reqComponent.field.type.value.positive.integer,1
--CCJS300 Prerequisites,Must have successfully completed a minimum of <n> courses from <courses>,24c0d53d-2e98-48a4-a1b1-2c34ea178aad,kuali.reqComponent.field.type.course.cluSet.id,8fb23a72-2a34-46b4-8f1c-2cf78d2f4a7a
--CCJS300 Prerequisites,Must have successfully completed all courses from <courses>,6557c09d-b2e6-4ec0-b869-f7ed9d4ea3dd,kuali.reqComponent.field.type.course.cluSet.id,0e9032c0-179a-431a-bf70-b18229770487
--CCJS310 Prerequisites,Must have successfully completed all courses from <courses>,3382ef72-bb1e-441d-92ca-f5c6aba6f29c,kuali.reqComponent.field.type.course.cluSet.id,60ba3f8e-d7fd-4d2e-86b0-78cfaacfae4c
--CCJS320 Prerequisites,Must have successfully completed all courses from <courses>,e800d452-4fd3-4d7e-94c9-dc0c690b7c2f,kuali.reqComponent.field.type.course.cluSet.id,399bbd4a-9cd5-4564-8935-fd566e263910
--CCJS330 Prerequisites,Must have successfully completed all courses from <courses>,3969bf66-e014-4860-93a1-894b21e1fcfe,kuali.reqComponent.field.type.course.cluSet.id,5425dbe8-f94e-41ed-8e81-1b58ecb6d6ac

--CCJS300 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS300 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS300 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS300 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS310 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS310 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS320 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS320 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS330 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS330 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS331 Prerequisites,Must have successfully completed all courses from <courses>,cd2052a2-0c2f-4bc0-a1cf-dadf24259628,kuali.reqComponent.field.type.course.cluSet.id,43859020-90f7-4b97-a75f-4fdecfb04af8
--CCJS332 Prerequisites,Must have been admitted to the <program> program,c6ea0f3b-23a3-41ff-8069-af0e5c289626,kuali.reqComponent.field.type.program.cluSet.id,2f8e6e28-380a-4cb6-9e59-5f85f5af1f19
--CCJS340 Prerequisites,Must have successfully completed <course>,f092c793-6ddb-443c-9af0-c8fe4af34f24,kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
--CCJS350 Prerequisites,Must have successfully completed <course>,d5562fa1-7bc2-43c5-a7b9-38047722b7b5,kuali.reqComponent.field.type.course.clu.id,28d4f15c-c1b6-4ed9-a561-15dd9ab379d4
--CCJS352 Prerequisites,Must have successfully completed <course>,166f19ea-461f-4a75-a22f-141a26e498c8,kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
--CCJS357 Prerequisites,Must have successfully completed <course>,64938856-84e9-43c1-9b30-719132d565c0,kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
--CCJS357 Prerequisites,Permission of <administering org> required,f933f477-415c-4e90-9c16-e3590355ea2e,kuali.reqComponent.field.type.org.id,214

--CCJS331 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS331 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS332 Prerequisites,AND,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.program.admitted'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS332 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS340 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS340 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS350 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS350 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS352 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS352 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS357 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS357 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS357 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS357 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS359 Prerequisites,Permission of <administering org> required,31356cad-a2e0-4b92-a7c7-0b6aaa4fbdc2,kuali.reqComponent.field.type.org.id,214
--CCJS359 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,3f6ca9c0-f8ba-40ba-8044-fb5b2301d2b6,kuali.reqComponent.field.type.org.id,214
--CCJS359 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,3f6ca9c0-f8ba-40ba-8044-fb5b2301d2b6,kuali.reqComponent.field.type.value.positive.integer,6
--CCJS360 Prerequisites,Must have successfully completed <course>,7bbb5fa4-38fd-48b9-b8df-674d5fcd72f3,kuali.reqComponent.field.type.course.clu.id,28d4f15c-c1b6-4ed9-a561-15dd9ab379d4
--CCJS370 Prerequisites,Must have successfully completed <course>,ad6c9261-eeaf-4296-9929-29291249b971,kuali.reqComponent.field.type.course.clu.id,5e40d6f9-0c5e-4b9b-b27f-a01e5d7da4c8
--CCJS386 Prerequisites,Permission of <administering org> required,8bd94c9b-33fd-452e-94a0-65263461c07e,kuali.reqComponent.field.type.org.id,214
--CCJS388 Prerequisites,Must have successfully completed all courses from <courses>,becd8ea3-adfe-40ae-a7ff-213c1c9ab0be,kuali.reqComponent.field.type.course.cluSet.id,9eab576b-2636-4825-8f36-17b7def3b080
--CCJS389 Prerequisites,Must have successfully completed <course>,7792f72a-a0ea-4767-a818-4b699f8a97b8,kuali.reqComponent.field.type.course.clu.id,28d4f15c-c1b6-4ed9-a561-15dd9ab379d4

--CCJS359 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS359 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS359 Prerequisites,AND,kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS359 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS360 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS360 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS370 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS370 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS386 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS386 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS388 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS388 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS389 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS389 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS398 Prerequisites,Permission of <administering org> required,10fad561-d43c-4d85-a272-8162a4ba0f22,kuali.reqComponent.field.type.org.id,214
--CCJS398 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,577568ee-49ce-4d8c-a888-86109413fbfe,kuali.reqComponent.field.type.org.id,214
--CCJS398 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,577568ee-49ce-4d8c-a888-86109413fbfe,kuali.reqComponent.field.type.value.positive.integer,6
--CCJS399 Prerequisites,Permission of <administering org> required,becf7366-bb3d-4683-a293-0041fe73a1c6,kuali.reqComponent.field.type.org.id,214
--CCJS399 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,fa109fef-80d8-4a7b-8bc4-c1d9c975cfde,kuali.reqComponent.field.type.org.id,214
--CCJS399 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,fa109fef-80d8-4a7b-8bc4-c1d9c975cfde,kuali.reqComponent.field.type.value.positive.integer,12

--CCJS398 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS398 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS398 Prerequisites,AND,kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS398 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS399 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS399 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS399 Prerequisites,AND,kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS399 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS400 v6 Prerequisites,Permission of <administering org> required,c9d72ad1-1036-42f0-89c7-ac172e50676f,kuali.reqComponent.field.type.org.id,214
--CCJS400 v6 Prerequisites,Must have successfully completed all courses from <courses>,ca8a4929-210c-4c10-8df0-e396552236d1,kuali.reqComponent.field.type.course.cluSet.id,451706e8-6dfb-4638-b6ba-3b4f3469af9c
--CCJS400 v8 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,0fcffd3f-5556-4a0a-84c9-f61f86caee4b,kuali.reqComponent.field.type.value.positive.integer,12
--CCJS400 v8 Prerequisites,Must have successfully completed a minimum of <n> credits from courses in the <org>,0fcffd3f-5556-4a0a-84c9-f61f86caee4b,kuali.reqComponent.field.type.org.id,214
--CCJS400 v8 Prerequisites,Permission of <administering org> required,56e935b2-36ad-45e5-a3dc-95d140c44fa3,kuali.reqComponent.field.type.org.id,214
--CCJS432 Prerequisites,Must have successfully completed all courses from <courses>,0c8191a3-db93-4bc9-8833-d90e49f1766e,kuali.reqComponent.field.type.course.cluSet.id,1605dc0f-b763-4441-bfbf-bde70c3bbfae
--CCJS444 Prerequisites,Must have successfully completed all courses from <courses>,f52108fc-d76a-4bf1-9d4f-6b5aaadc5b1c,kuali.reqComponent.field.type.course.cluSet.id,95a4ef4d-8039-4d23-aa03-1c5b966f503a
--CCJS451 Prerequisites,Permission of <administering org> required,06735685-df18-447b-9259-84563339376b,kuali.reqComponent.field.type.org.id,214
--CCJS451 Prerequisites,Must have successfully completed all courses from <courses>,44a558c1-cf8f-4688-82ee-fa8ec2a61cc0,kuali.reqComponent.field.type.course.cluSet.id,18beb5ee-cc99-4d28-b0d0-56c5367dbf8e

--CCJS400 v6 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS400 v6 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS400 v6 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS400 v6 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS400 v8 Prerequisites,AND,kuali.reqComponent.type.course.org.credits.completed.min,Must have successfully completed a minimum of <n> credits from courses in the <org>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> credits from courses in the <org>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.org.credits.completed.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS400 v8 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS400 v8 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS400 v8 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS432 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS432 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS444 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS444 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS451 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS451 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS451 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS451 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS452 Prerequisites,Permission of <administering org> required,9c82a713-515f-410b-b10e-59ff86844b6d,kuali.reqComponent.field.type.org.id,214
--CCJS452 Prerequisites,Must have successfully completed all courses from <courses>,b0823ece-d2d0-4430-a615-df5cfe0de638,kuali.reqComponent.field.type.course.cluSet.id,f8dbb063-d026-4b1c-8b5a-ac7acc7571bc
--CCJS453 Prerequisites,Must have successfully completed all courses from <courses>,b54fd46b-29e4-4dd9-ad35-9eed17927990,kuali.reqComponent.field.type.course.cluSet.id,d6d71b1b-9769-4c53-84f8-7932edb17872
--CCJS454 Prerequisites,Must have successfully completed all courses from <courses>,ec3130e9-fcca-46b7-88da-fef6f831022a,kuali.reqComponent.field.type.course.cluSet.id,77ca3e9e-ca86-43a4-b948-db265b290f58
--CCJS455 Prerequisites,Must have successfully completed <course>,894c8b85-741e-4cb2-bc52-3c2cd19c677f,kuali.reqComponent.field.type.course.clu.id,cee1942d-e11f-443f-b177-9542750c7579
--CCJS455 Prerequisites,Permission of <administering org> required,ecf70e71-440f-4fd8-bda2-73c886642cf1,kuali.reqComponent.field.type.org.id,214

--CCJS452 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS452 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS452 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS452 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS453 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS453 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS454 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS454 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS455 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS455 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS455 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS455 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS456 Prerequisites,Permission of <administering org> required,bf05483e-b711-48dc-b258-8944f2abe5d1,kuali.reqComponent.field.type.org.id,214
--CCJS456 Prerequisites,Must have successfully completed <course>,fa1cc1c2-345b-417a-adee-d6c90f7116a0,kuali.reqComponent.field.type.course.clu.id,fb0ed0b1-96c7-44e6-a649-871de27702e9
--CCJS457 Prerequisites,Must have successfully completed all courses from <courses>,0967dbb7-7528-4ea5-acb1-79d7d9afa2ac,kuali.reqComponent.field.type.course.cluSet.id,24c1a647-7dcd-4707-837c-89dd2da26f84
--CCJS461 Prerequisites,Must have successfully completed all courses from <courses>,b88a4de8-3557-4f7f-a872-7fb562ff29b7,kuali.reqComponent.field.type.course.cluSet.id,7560ec21-2025-47b8-810d-50f2062f0a30
--CCJS462 Prerequisites,Must have successfully completed all courses from <courses>,27a697ea-90cd-4b7d-a7a1-e0948c8f036f,kuali.reqComponent.field.type.course.cluSet.id,e8ad4ea9-593e-48e0-8ca5-116679270817
--CCJS600 Prerequisites,Must have been admitted to the <program> program,fefac7cc-ac65-4117-aac1-f542f8f3faeb,kuali.reqComponent.field.type.program.cluSet.id,11770bd5-7df1-4a9a-8676-a44633ad96c4
--CCJS653 Prerequisites,Must have successfully completed <course>,4524bd84-37d5-47f0-bb11-4204aca64acc,kuali.reqComponent.field.type.course.clu.id,fb4e3445-743f-4d61-aa28-97aa4424356b

--CCJS456 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS456 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS456 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS456 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS457 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS457 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS461 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS461 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS462 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS462 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS600 Prerequisites,AND,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.program.admitted'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS600 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/

--CCJS653 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'CCJS653 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/