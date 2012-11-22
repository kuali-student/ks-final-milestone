--select ident.cd, clu.ver_nbr, st.id statementid, rq.id reqcomid, st.stmt_type_id, st.operator, rq.req_com_type_id, rul.nm, rul.rule_id ruleid, typ2.typ_id, rtyp.type_desc
--from ksst_ref_stmt_rel ref, ksst_stmt st,
--     kslu_clu clu, kslu_clu_ident ident, krms_typ_t typ2,
--     ksst_stmt_jn_req_com sjr, ksst_req_com rq,
--     krms_rule_t rul, krms_typ_t typ, ksst_req_com_type rtyp
--where st.id = ref.stmt_id
--  and ref.ref_obj_id = clu.id
--  and clu.offic_clu_id = ident.id
--  and ref.ref_obj_type_key = 'kuali.lu.type.CreditCourse'
--  and sjr.stmt_id = st.id
--  and typ2.nm(+) = rq.req_com_type_id
--  and sjr.req_com_id = rq.id
--  and rul.nm like ident.cd || '%'
--  and (rul.nm like '% v' || clu.ver_nbr || '%' or rul.nm not like '% v%')
--  and rul.typ_id = typ.typ_id
--  and rtyp.type_key = rq.req_com_type_id
--  and (st.stmt_type_id = typ.nm or (st.stmt_type_id = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'
--                               and typ.nm = 'kuali.statement.type.course.academicReadiness.prereq'))

--union

-- select ident.cd, clu.ver_nbr, st.id statementid, rq.id reqcomid, st.stmt_type_id, st.operator, rq.req_com_type_id, rul.nm, rul.rule_id ruleid, typ2.typ_id, rtyp.type_desc
--from ksst_ref_stmt_rel ref, ksst_stmt_jn_stmt stjn,
--     kslu_clu clu, kslu_clu_ident ident, ksst_stmt st,
--     ksst_stmt_jn_req_com sjr, ksst_req_com rq, krms_typ_t typ2,
--     krms_rule_t rul, krms_typ_t typ, ksst_req_com_type rtyp
--where stjn.stmt_id = ref.stmt_id
--  and ref.ref_obj_id = clu.id
--  and clu.offic_clu_id = ident.id
--  and ref.ref_obj_type_key = 'kuali.lu.type.CreditCourse'
--  and st.id = stjn.chld_stmt_id
--  and sjr.stmt_id = st.id
--  and typ2.nm(+) = rq.req_com_type_id
--  and sjr.req_com_id = rq.id
--  and rul.nm like ident.cd || '%'
--  and (rul.nm like '% v' || clu.ver_nbr || '%' or rul.nm not like '% v%')
--  and rul.typ_id = typ.typ_id
--  and rtyp.type_key = rq.req_com_type_id
--  and (st.stmt_type_id = typ.nm or (st.stmt_type_id = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'
--                               and typ.nm = 'kuali.statement.type.course.academicReadiness.prereq'))


--BSCI207 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI207 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--BSCI222 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI222 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--BSCI223 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI223 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--BSCI330 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI330 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--BSCI334 Prerequisites,AND,kuali.reqComponent.type.course.courseset.grade.min,Must have earned a minimum grade of <gradeType> <grade> in <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'BSCI334 Prerequisites'), 1, NULL)
/
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
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
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
--GEOG110 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG110 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG110 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG110 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG123 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG123 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG123 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG123 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG201 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG201 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG202 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG202 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG211 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG211 Corequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG212 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG212 Corequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG330 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG330 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG330 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG330 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG331 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG331 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG331 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG331 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG342 Preperation,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG342 Preperation'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG342 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG342 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG346 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG346 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG346 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG346 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG384 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG384 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG384 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG384 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG385 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG385 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG385 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG385 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG396 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG396 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG397 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG397 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG398 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG398 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG415 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG415 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG415 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG415 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG438 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG438 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG440 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG440 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG440 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG440 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG441 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG441 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG441 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG441 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG446 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG446 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG446 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG446 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG472 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG472 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG473 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG473 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG475 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG475 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG476 v6 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG476 v6 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG476 v6 Corequisites,AND,kuali.reqComponent.type.course.courseset.enrolled.nof,Must be concurrently enrolled in a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.enrolled.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG476 v6 Corequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG476 v8 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.all,Must have successfully completed all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG476 v8 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG496 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG496 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG603 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG603 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG603 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG603 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG604 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG604 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG604 Prerequisites,AND,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG604 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG615 Prerequisites,OR,kuali.reqComponent.type.course.courseset.completed.nof,Must have successfully completed a minimum of <n> courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed a minimum of <n> courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.nof'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG615 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG615 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG615 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG642 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG642 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--GEOG642 Prerequisites,OR,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'GEOG642 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MATH110 Antirequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH110 Antirequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MATH112 Antirequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH112 Antirequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MATH113 Antirequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH113 Antirequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MATH115 Antirequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH115 Antirequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MATH130 Credit Restriction,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH130 Credit Restriction'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MATH140 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH140 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MATH220 Prerequisites,AND,kuali.reqComponent.type.course.courseset.completed.none,Must not have successfully completed any courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have successfully completed any courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.completed.none'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MATH220 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC601 Prerequisites,OR,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.program.admitted'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC601 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC603 Prerequisites,OR,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.program.admitted'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC603 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC605 Prerequisites,AND,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.program.admitted'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC605 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC605 Prerequisites,OR,kuali.reqComponent.type.course.completed,Must have successfully completed <course>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have successfully completed <course>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.completed'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC605 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC605 Prerequisites,OR,kuali.reqComponent.type.course.permission.instructor.required,Permission of instructor required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of instructor required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.instructor.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC605 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC611 Corequisites,AND,kuali.reqComponent.type.course.enrolled,Must be concurrently enrolled in <course> 
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in <course> ', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.enrolled'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC611 Corequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC611 Prerequisites,AND,kuali.reqComponent.type.course.program.notadmitted,Must not have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must not have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.program.notadmitted'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC611 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC613 Corequisites,AND,kuali.reqComponent.type.course.courseset.enrolled.all,Must be concurrently enrolled in all courses from <courses>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must be concurrently enrolled in all courses from <courses>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.enrolled.all'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC613 Corequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC613 Prerequisites,AND,kuali.reqComponent.type.course.permission.org.required,Permission of <administering org> required
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Permission of <administering org> required', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.permission.org.required'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC613 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC613 Prerequisites,AND,kuali.reqComponent.type.course.program.admitted,Must have been admitted to the <program> program
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must have been admitted to the <program> program', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.program.admitted'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC613 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
--MUSC613 Prerequisites,AND,kuali.reqComponent.type.course.courseset.nof.grade.min,Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <gradeType> <grade>
insert into krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
values (krms_prop_s.nextval, 'Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <grade>', (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.course.courseset.nof.grade.min'), 'S', NULL, (Select rule_id from krms_rule_t where nm = 'MUSC613 Prerequisites'), 1, NULL)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'termId', 'T', 1, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, 'true', 'C', 2, 1)
/
insert into krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) values (krms_prop_parm_s.nextval, krms_prop_s.currval, '=', 'O', 3, 1)
/
