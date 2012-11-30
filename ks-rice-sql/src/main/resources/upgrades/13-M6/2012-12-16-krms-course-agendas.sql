--select distinct ref.ref_obj_id, ref.ref_obj_type_key, ident.cd,
--       (select frt.nm
--          from krms_typ_reln_t tr, krms_typ_t tot, krms_typ_t frt
--         where tr.to_typ_id = tot.typ_id
--           and tr.from_typ_id = frt.typ_id
--           and tot.nm = st.stmt_type_id) agendatypekey
--from ksst_ref_stmt_rel ref, ksst_stmt st,
--     kslu_clu clu, kslu_clu_ident ident
--where st.id = ref.stmt_id
--  and ref.ref_obj_id = clu.id
--  and clu.offic_clu_id = ident.id
--  and ref.ref_obj_type_key = 'kuali.lu.type.CreditCourse'

insert into krms_cntxt_t (cntxt_id, nmspc_cd, nm, typ_id, actv, ver_nbr, desc_txt) values (krms_cntxt_s.nextval, 'KS-SYS', 'Course Requirements', 'T1004', 'Y', 0, 'Course Requirements')
/

--REFERENCECOURSEMATH220 MATH220
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MATH220 Credit Constraints', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.creditConstraints'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMATH220', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MATH220 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.credit.restriction'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMATH140 MATH140
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MATH140 Credit Constraints', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.creditConstraints'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMATH140', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MATH140 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.credit.restriction'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMATH130 MATH130
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MATH130 Credit Constraints', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.creditConstraints'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMATH130', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MATH130 Credit Restriction', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.credit.restriction'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/


--49d4e580-cfca-44d6-bff8-de0075c3d369 CCJS386
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS386 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '49d4e580-cfca-44d6-bff8-de0075c3d369', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS386 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMATH113 MATH113
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MATH113 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMATH113', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MATH113 Antirequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.antireq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--128c9d81-e9ce-46c3-b5ab-eb8525b13311 GEOG472
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG472 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '128c9d81-e9ce-46c3-b5ab-eb8525b13311', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG472 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--5bc718b4-b25b-4a26-80c2-938570174b79 CCJS330
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS330 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '5bc718b4-b25b-4a26-80c2-938570174b79', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS330 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--e394b51b-82f9-41c1-b7d3-95252dc6b317 BSCI223
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI223 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'e394b51b-82f9-41c1-b7d3-95252dc6b317', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI223 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMUSC605 MUSC605
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MUSC605 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMUSC605', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MUSC605 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--cf6dcc35-e229-40e3-bd42-3ff05e07c64a CCJS400
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS400 v8 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'cf6dcc35-e229-40e3-bd42-3ff05e07c64a', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS400 v8 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--078f620e-5402-4aff-a7da-fd3394c8a2ee BSCI441
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI441 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '078f620e-5402-4aff-a7da-fd3394c8a2ee', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI441 Corequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--ad7acd2a-a4e1-49ae-afa5-d4eac144d34d BSCI398
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI398 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'ad7acd2a-a4e1-49ae-afa5-d4eac144d34d', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI398 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--ee709b82-f6d0-47ce-885b-a3dba225ba68 GEOG212
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG212 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'ee709b82-f6d0-47ce-885b-a3dba225ba68', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG212 Corequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--cc6a0a30-3e34-4cdf-8c25-4d8ab6b42652 GEOG331
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG331 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'cc6a0a30-3e34-4cdf-8c25-4d8ab6b42652', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG331 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--69a43341-550a-465e-a62c-a4e3d6356156 BSCI467
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI467 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '69a43341-550a-465e-a62c-a4e3d6356156', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI467 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--88c6d275-a86f-4189-87d9-bee0f8e10b62 CCJS398
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS398 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '88c6d275-a86f-4189-87d9-bee0f8e10b62', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS398 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--b8debaf9-c924-4dae-be44-82c129a48699 BSCI379
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI379 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'b8debaf9-c924-4dae-be44-82c129a48699', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI379 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--7a32aaff-3567-47cf-a604-ee8e74c5f6df CCJS462
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS462 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '7a32aaff-3567-47cf-a604-ee8e74c5f6df', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS462 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--d6018081-c944-409a-94a8-cb8c6950b666 GEOG330
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG330 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'd6018081-c944-409a-94a8-cb8c6950b666', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG330 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--14dd1594-9007-4282-b08d-54d1c73f0e6e BSCI399
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI399 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '14dd1594-9007-4282-b08d-54d1c73f0e6e', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI399 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--d16b7ba0-5476-46e4-83b8-38842b3e0178 BSCI342
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI342 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'd16b7ba0-5476-46e4-83b8-38842b3e0178', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI342 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--a0be6df6-4458-44c7-8109-d62fec0a6110 BSCI414
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI414 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'a0be6df6-4458-44c7-8109-d62fec0a6110', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI414 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMATH110 MATH110
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MATH110 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMATH110', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MATH110 Antirequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.antireq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--6c3b2706-0bc8-4f7e-97c7-f7054fa4381b BSCI443
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI443 v6 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '6c3b2706-0bc8-4f7e-97c7-f7054fa4381b', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI443 v6 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--6c02ee86-4252-45b6-a1ea-cda29edc1db1 BSCI360
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI360 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '6c02ee86-4252-45b6-a1ea-cda29edc1db1', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI360 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--ab657e93-a165-4508-837a-3974382ee803 GEOG440
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG440 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'ab657e93-a165-4508-837a-3974382ee803', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG440 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--3f0a6021-736b-4aa6-9047-fd429ef488c0 BSCI451
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI451 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '3f0a6021-736b-4aa6-9047-fd429ef488c0', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI451 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--07b49073-828f-4bdc-936d-69a599c59525 BSCI480
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI480 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '07b49073-828f-4bdc-936d-69a599c59525', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI480 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--870d3754-2e3b-4efd-a650-fbf306bf63ea CCJS350
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS350 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '870d3754-2e3b-4efd-a650-fbf306bf63ea', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS350 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--715ad4bc-f86b-425a-953a-d8cedc8356ab BSCI366
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI366 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '715ad4bc-f86b-425a-953a-d8cedc8356ab', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI366 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--dff6c4dc-40c2-4957-89fc-3984dd9196ef CCJS452
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS452 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'dff6c4dc-40c2-4957-89fc-3984dd9196ef', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS452 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--e6b79746-a475-4cdd-a1eb-6c084214118e BSCI485
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI485 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'e6b79746-a475-4cdd-a1eb-6c084214118e', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI485 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--b6e0b88e-b61e-4b5c-a388-7f31cea7964a CCJS388
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS388 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'b6e0b88e-b61e-4b5c-a388-7f31cea7964a', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS388 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--f778faaf-d888-4ea3-9b7c-82cab80706b7 CCJS444
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS444 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'f778faaf-d888-4ea3-9b7c-82cab80706b7', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS444 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--61b5a981-0f8f-4c56-9ebd-687b18b8f825 BSCI421
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI421 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '61b5a981-0f8f-4c56-9ebd-687b18b8f825', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI421 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--e3c70078-a738-4da6-b6c1-fb06984e0183 BSCI453
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI453 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'e3c70078-a738-4da6-b6c1-fb06984e0183', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI453 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--11b1559a-0969-4d78-a3c6-3377fdb4b8ba GEOG415
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG415 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '11b1559a-0969-4d78-a3c6-3377fdb4b8ba', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG415 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--4eaae9a4-e761-4de2-89e6-cf56b3e6fb52 BSCI392
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI392 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '4eaae9a4-e761-4de2-89e6-cf56b3e6fb52', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI392 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--21f7a0b2-abbc-4f77-8bf2-a4fde41c97ca CCJS320
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS320 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '21f7a0b2-abbc-4f77-8bf2-a4fde41c97ca', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS320 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--7b801d7b-3d20-44b8-8577-644beff4d59b BSCI474
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI474 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '7b801d7b-3d20-44b8-8577-644beff4d59b', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI474 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--e24ad900-7f9e-4375-8035-0a884a78ddd6 BSCI454
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI454 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'e24ad900-7f9e-4375-8035-0a884a78ddd6', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI454 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--fe0934c3-b16f-47b6-97f2-54b3ea7188de BSCI434
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI434 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'fe0934c3-b16f-47b6-97f2-54b3ea7188de', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI434 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--04cc1956-e0aa-420e-80ab-03122854467c BSCI447
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI447 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '04cc1956-e0aa-420e-80ab-03122854467c', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI447 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--bc013c37-6661-4747-818a-a4833c7c55d4 BSCI337
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI337 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'bc013c37-6661-4747-818a-a4833c7c55d4', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI337 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--6b2aaac5-b84b-483e-8ee0-a9ce7e7b1dc6 BSCI424
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI424 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '6b2aaac5-b84b-483e-8ee0-a9ce7e7b1dc6', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI424 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--7fe00533-8c50-47f9-8b62-cb14032d2216 BSCI394
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI394 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '7fe00533-8c50-47f9-8b62-cb14032d2216', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI394 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--99b51dbc-ef8b-4174-b18d-7b311676cb1f BSCI494
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI494 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '99b51dbc-ef8b-4174-b18d-7b311676cb1f', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI494 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMUSC611 MUSC611
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MUSC611 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMUSC611', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MUSC611 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MUSC611 Corequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
--REFERENCECOURSEGEOG604 GEOG604
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG604 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEGEOG604', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG604 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--f1b21adc-2544-4ba7-b1a1-c920ad4a9834 BSCI460
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI460 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'f1b21adc-2544-4ba7-b1a1-c920ad4a9834', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI460 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--98da88ad-02bb-4858-bf2c-d376d0f527ba GEOG475
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG475 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '98da88ad-02bb-4858-bf2c-d376d0f527ba', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG475 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--718cce39-28c4-4893-b832-d7a08d214b38 CCJS188
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS188 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '718cce39-28c4-4893-b832-d7a08d214b38', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS188 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--cbcc4805-7a3f-4d20-b511-2d5ecae60e18 BSCI446
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI446 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'cbcc4805-7a3f-4d20-b511-2d5ecae60e18', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI446 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--1c5ee205-f677-46fc-9c11-c376ea59fc84 BSCI334
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI334 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '1c5ee205-f677-46fc-9c11-c376ea59fc84', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI334 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--07a69fa9-6d3c-41ea-9eb0-5b6bc48a6d5a BSCI417
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI417 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '07a69fa9-6d3c-41ea-9eb0-5b6bc48a6d5a', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI417 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--be7d4e50-cb83-47f4-86ef-39f1d29d9812 BSCI471
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI471 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'be7d4e50-cb83-47f4-86ef-39f1d29d9812', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI471 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEGEOG202 GEOG202
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG202 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEGEOG202', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG202 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--02982391-df69-4c95-b6fa-702a85406c53 BSCI423
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI423 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '02982391-df69-4c95-b6fa-702a85406c53', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI423 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI423 Corequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
--4eda4440-bbb6-4f95-8af6-dacc63ffb0a6 BSCI389
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI389 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '4eda4440-bbb6-4f95-8af6-dacc63ffb0a6', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI389 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--79cc9828-82af-41c2-8f26-cb56eddcdb27 BSCI473
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI473 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '79cc9828-82af-41c2-8f26-cb56eddcdb27', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI473 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEGEOG615 GEOG615
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG615 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEGEOG615', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG615 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--be801908-0b5e-4531-8019-084c2f4fabb8 CCJS451
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS451 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'be801908-0b5e-4531-8019-084c2f4fabb8', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS451 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--faf3fef9-a881-4b9e-8bbc-5b6f43b74ef0 GEOG123
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG123 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'faf3fef9-a881-4b9e-8bbc-5b6f43b74ef0', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG123 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--a777d62d-2ded-4868-9235-4fe4733d0cc6 GEOG385
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG385 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'a777d62d-2ded-4868-9235-4fe4733d0cc6', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG385 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--aefb4696-2dae-4b9e-b494-99db7bfe71d9 BSCI380
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI380 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'aefb4696-2dae-4b9e-b494-99db7bfe71d9', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI380 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI380 Preperation', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.recommendedPreparation'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
--ae909038-8e08-4f78-b914-46a0e66de65d BSCI361
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI361 v6 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'ae909038-8e08-4f78-b914-46a0e66de65d', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI361 v6 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSECCJS653 CCJS653
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS653 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSECCJS653', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS653 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEGEOG201 GEOG201
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG201 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEGEOG201', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG201 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--60198df5-41f0-4e96-b7a8-ee7d8f99b662 BSCI412
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI412 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '60198df5-41f0-4e96-b7a8-ee7d8f99b662', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI412 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--dc5bda00-c906-418b-9bbd-3228b2d03fc5 CCJS457
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS457 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'dc5bda00-c906-418b-9bbd-3228b2d03fc5', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS457 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--5c5182dc-be81-48b1-909c-497bcf083d5a CCJS352
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS352 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '5c5182dc-be81-48b1-909c-497bcf083d5a', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS352 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--5679ca34-115b-4a6e-88e7-c273e0d42809 GEOG342
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG342 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '5679ca34-115b-4a6e-88e7-c273e0d42809', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG342 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG342 Preperation', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.recommendedPreparation'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
--45b77997-8d0f-4b0d-92a0-084883a159c1 GEOG346
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG346 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '45b77997-8d0f-4b0d-92a0-084883a159c1', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG346 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--76cb88f5-1ff1-433c-9411-e69a395931b5 CCJS200
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS200 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '76cb88f5-1ff1-433c-9411-e69a395931b5', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS200 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--20b70e39-3d4c-48ce-80e3-cc200d5c90fd BSCI465
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI465 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '20b70e39-3d4c-48ce-80e3-cc200d5c90fd', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI465 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--c207efa1-b9f2-4c51-9b77-d42e8972faa3 BSCI430
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI430 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'c207efa1-b9f2-4c51-9b77-d42e8972faa3', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI430 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--b71ebf07-0f8b-4fe9-8f07-b7ad855c32d2 BSCI420
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI420 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'b71ebf07-0f8b-4fe9-8f07-b7ad855c32d2', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI420 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--6c288a2e-96b0-4d99-9a77-a6cfa939ab8b CCJS357
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS357 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '6c288a2e-96b0-4d99-9a77-a6cfa939ab8b', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS357 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--642b5fff-5953-4730-bd24-86047fb49389 GEOG496
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG496 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '642b5fff-5953-4730-bd24-86047fb49389', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG496 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--d23fbc61-bef2-47f6-9f5c-5937439ba2e7 GEOG397
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG397 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'd23fbc61-bef2-47f6-9f5c-5937439ba2e7', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG397 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--c01e4ab0-ca25-49ec-8df5-3c5cad74ad73 GEOG446
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG446 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'c01e4ab0-ca25-49ec-8df5-3c5cad74ad73', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG446 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--566276a8-43b0-42a3-8cb8-2dfd212bc90f BSCI462
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI462 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '566276a8-43b0-42a3-8cb8-2dfd212bc90f', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI462 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--5ab92567-4e71-4f80-8f9d-776ff3a84a29 BSCI463
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI463 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '5ab92567-4e71-4f80-8f9d-776ff3a84a29', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI463 Corequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMUSC613 MUSC613
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MUSC613 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMUSC613', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MUSC613 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MUSC613 Corequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
--REFERENCECOURSEGEOG642 GEOG642
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG642 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEGEOG642', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG642 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--3052512f-1bce-4034-9745-a8a98cb39652 BSCI363
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI363 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '3052512f-1bce-4034-9745-a8a98cb39652', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI363 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--84bf3cad-50c3-4647-a0d4-195272cd810b BSCI440
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI440 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '84bf3cad-50c3-4647-a0d4-195272cd810b', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI440 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--53bb510d-9cf0-4127-9f7a-343d69e95718 BSCI442
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI442 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '53bb510d-9cf0-4127-9f7a-343d69e95718', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI442 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--8a691610-1855-40f8-90eb-eb93db18c214 BSCI330
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI330 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '8a691610-1855-40f8-90eb-eb93db18c214', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI330 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--8f660b37-a474-4cb8-aec6-695fe39f7059 BSCI422
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI422 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '8f660b37-a474-4cb8-aec6-695fe39f7059', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI422 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--1c89b408-73ff-456a-a4e6-9d993a3d7ca9 BSCI493
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI493 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '1c89b408-73ff-456a-a4e6-9d993a3d7ca9', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI493 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--9ceee7cd-d076-43de-b6ab-6b4444d149c1 GEOG398
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG398 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '9ceee7cd-d076-43de-b6ab-6b4444d149c1', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG398 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--f0c4852d-08fd-4421-b69f-9d6c44a38d62 BSCI426
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI426 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'f0c4852d-08fd-4421-b69f-9d6c44a38d62', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI426 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--5c45c9fc-04df-4bfd-a454-d6eeea9df02f CCJS453
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS453 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '5c45c9fc-04df-4bfd-a454-d6eeea9df02f', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS453 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--93d57cba-9a73-406c-adc6-812765bcd5fc BSCI361
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI361 v2 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '93d57cba-9a73-406c-adc6-812765bcd5fc', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI361 v2 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--72b8e408-5d76-4822-9c06-4ebbdbc86df1 BSCI461
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI461 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '72b8e408-5d76-4822-9c06-4ebbdbc86df1', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI461 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--daf3478c-ecd0-4ebb-b031-bc0cf1192721 BSCI362
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI362 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'daf3478c-ecd0-4ebb-b031-bc0cf1192721', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI362 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--2c637d66-d6a6-4bc0-b92a-b0ec591f823a CCJS300
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS300 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '2c637d66-d6a6-4bc0-b92a-b0ec591f823a', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS300 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--471bff41-f3cd-4071-b7a7-eb5bc1b83a8c CCJS389
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS389 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '471bff41-f3cd-4071-b7a7-eb5bc1b83a8c', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS389 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--7d1c9098-5235-4b9a-8c60-e2438589e11f CCJS370
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS370 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '7d1c9098-5235-4b9a-8c60-e2438589e11f', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS370 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--6851f530-c9a3-4f78-a2c5-502f0be8afe8 CCJS432
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS432 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '6851f530-c9a3-4f78-a2c5-502f0be8afe8', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS432 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMUSC603 MUSC603
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MUSC603 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMUSC603', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MUSC603 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSECCJS600 CCJS600
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS600 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSECCJS600', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS600 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--a2c7bba8-9264-4523-81de-a0480d6e14a2 CCJS332
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS332 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'a2c7bba8-9264-4523-81de-a0480d6e14a2', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS332 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--6341106c-e865-48af-9406-538f4ffefd39 GEOG396
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG396 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '6341106c-e865-48af-9406-538f4ffefd39', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG396 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--983f43e1-a35e-4c1d-83b1-2dc1a5302693 CCJS461
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS461 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '983f43e1-a35e-4c1d-83b1-2dc1a5302693', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS461 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--bf1d68db-4967-45f4-9362-90e0458e8df7 BSCI222
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI222 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'bf1d68db-4967-45f4-9362-90e0458e8df7', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI222 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--6c6221d2-ac49-4e98-9180-baffda0829a4 GEOG211
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG211 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '6c6221d2-ac49-4e98-9180-baffda0829a4', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG211 Corequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--daead631-461f-474a-8cbc-e1d9d72b2f19 CCJS288
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS288 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'daead631-461f-474a-8cbc-e1d9d72b2f19', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS288 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--22906052-4016-4d18-b26f-55fd614a651b CCJS310
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS310 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '22906052-4016-4d18-b26f-55fd614a651b', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS310 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--f4457b4f-65af-497f-bf41-bed5cab7aae4 CCJS234
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS234 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'f4457b4f-65af-497f-bf41-bed5cab7aae4', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS234 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--529c9fb5-801c-4bf8-a4f6-a5bd2d1f85e0 BSCI373
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI373 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '529c9fb5-801c-4bf8-a4f6-a5bd2d1f85e0', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI373 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--8080645e-475c-4d3a-bea1-8a0dff33530e CCJS456
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS456 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '8080645e-475c-4d3a-bea1-8a0dff33530e', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS456 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--d1f8c7ff-cb22-48e7-af22-9ac2d7c08b79 BSCI416
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI416 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'd1f8c7ff-cb22-48e7-af22-9ac2d7c08b79', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI416 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--ab7c3bfb-3c05-4894-b203-bcfcd9c1e08e BSCI370
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI370 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'ab7c3bfb-3c05-4894-b203-bcfcd9c1e08e', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI370 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--55f906b8-8eb6-42af-9342-75a660e48d8b BSCI464
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI464 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '55f906b8-8eb6-42af-9342-75a660e48d8b', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI464 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--7740bb42-fd33-48cf-bbb0-854c4f8d673c GEOG476
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG476 v6 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '7740bb42-fd33-48cf-bbb0-854c4f8d673c', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG476 v6 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG476 v6 Corequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
--484205ed-07c9-4d98-a4b0-cc2629641e88 CCJS399
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS399 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '484205ed-07c9-4d98-a4b0-cc2629641e88', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS399 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--58d21bde-7afd-4230-b6f9-bdeb6a4adec4 BSCI443
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI443 v2 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '58d21bde-7afd-4230-b6f9-bdeb6a4adec4', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI443 v2 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--2ae07032-21db-4d1c-93f9-de988f9ebc58 GEOG441
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG441 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '2ae07032-21db-4d1c-93f9-de988f9ebc58', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG441 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--42a53970-3099-464c-b3c7-2463279f2bff CCJS400
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS400 v6 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '42a53970-3099-464c-b3c7-2463279f2bff', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS400 v6 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMUSC601 MUSC601
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MUSC601 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMUSC601', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MUSC601 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--e95ceaba-b059-476a-8239-be4eb458639a CCJS454
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS454 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'e95ceaba-b059-476a-8239-be4eb458639a', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS454 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--d23a7edb-3689-4e7e-842f-c72218371210 CCJS360
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS360 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'd23a7edb-3689-4e7e-842f-c72218371210', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS360 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEGEOG603 GEOG603
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG603 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEGEOG603', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG603 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--1c0004fd-c7b0-4067-8ecb-578a2eab95c2 BSCI433
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI433 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '1c0004fd-c7b0-4067-8ecb-578a2eab95c2', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI433 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--17ab080d-7796-4f86-b368-3a51fdf57c9b BSCI410
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI410 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '17ab080d-7796-4f86-b368-3a51fdf57c9b', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI410 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--692989c2-f15f-4162-ad4c-f2cc1bdd8287 BSCI207
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI207 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '692989c2-f15f-4162-ad4c-f2cc1bdd8287', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI207 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--5181b2b9-b3ae-41a4-8b88-c101044d9153 GEOG476
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG476 v8 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '5181b2b9-b3ae-41a4-8b88-c101044d9153', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG476 v8 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--dd630096-5a36-4014-967c-5a7c3f587e76 CCJS331
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS331 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'dd630096-5a36-4014-967c-5a7c3f587e76', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS331 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--29c88a70-47e6-4eae-9658-fdc7dc795b8a GEOG110
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG110 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '29c88a70-47e6-4eae-9658-fdc7dc795b8a', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG110 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--5e918070-b74b-48e7-8662-0c1f29de03e7 CCJS340
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS340 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '5e918070-b74b-48e7-8662-0c1f29de03e7', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS340 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--167719bd-f7d9-4696-8348-bd9b5a8ad775 CCJS230
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS230 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '167719bd-f7d9-4696-8348-bd9b5a8ad775', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS230 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--6196c101-ccd9-4557-8b11-ea1020e412d3 BSCI335
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI335 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '6196c101-ccd9-4557-8b11-ea1020e412d3', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI335 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--2c677952-ecc7-4a1d-bd5b-025b11e9b3a5 GEOG384
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG384 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '2c677952-ecc7-4a1d-bd5b-025b11e9b3a5', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG384 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--d404d8fb-23be-49d1-af24-5ec822e41ef3 GEOG473
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG473 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'd404d8fb-23be-49d1-af24-5ec822e41ef3', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG473 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMATH112 MATH112
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MATH112 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMATH112', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MATH112 Antirequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.antireq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--dc4eb85d-f3fb-4b26-8bdf-17aebf229b12 GEOG438
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'GEOG438 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'dc4eb85d-f3fb-4b26-8bdf-17aebf229b12', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'GEOG438 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--55354426-719f-4e0f-a8a7-fdf3b599b87c BSCI437
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI437 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '55354426-719f-4e0f-a8a7-fdf3b599b87c', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI437 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--11aec214-1dad-4d9e-a1f1-e6791f164e1a CCJS455
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS455 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '11aec214-1dad-4d9e-a1f1-e6791f164e1a', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS455 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--9cddad26-4a99-4aa5-94d0-89162df6f14c CCJS359
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'CCJS359 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', '9cddad26-4a99-4aa5-94d0-89162df6f14c', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'CCJS359 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
 values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--REFERENCECOURSEMATH115 MATH115
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'MATH115 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'REFERENCECOURSEMATH115', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'MATH115 Antirequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.antireq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
--f4786b0f-51cf-4e55-87f8-8967fb859b9d	BSCI425
insert into krms_agenda_t (agenda_id, nm, cntxt_id, init_agenda_itm_id, typ_id, actv, ver_nbr)
values (krms_agenda_s.nextval, 'BSCI425 Enrollment Eligibility', krms_cntxt_s.currval, null, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), 'Y', 0)
/
insert into krms_ref_obj_krms_obj_t r (r.ref_obj_krms_obj_id, r.collection_nm, r.krms_obj_id, r.krms_dscr_typ, r.ref_obj_id, r.ref_dscr_typ, r.nmspc_cd, r.actv, r.ver_nbr)
values (krms_ref_obj_krms_obj_s.nextval, 'Course', krms_agenda_s.currval, 'Agenda', 'f4786b0f-51cf-4e55-87f8-8967fb859b9d', 'kuali.lu.type.CreditCourse', 'KS-SYS', 'Y', 0)
/
insert into krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
values (krms_rule_s.nextval, 'KS-SYS', 'BSCI425 Prerequisites', (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), null, 'Y', 0, null)
/
insert into krms_agenda_itm_t (agenda_itm_id, rule_id, sub_agenda_id, agenda_id, ver_nbr, when_true, when_false, always)
values (krms_agenda_itm_s.nextval, krms_rule_s.currval, NULL, krms_agenda_s.currval, 1, NULL, NULL, NULL)
/
update krms_agenda_t SET init_agenda_itm_id = krms_agenda_itm_s.currval  WHERE agenda_id = (select max(agenda_id) from krms_agenda_t where agenda_id not like 'T%')
/
