
-- Angenda Types ******************************************************************************************************************************************* Angenda

--kuali.statement.type.course
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course','KS-SYS','agendaTypeService','N','0')
/

--kuali.statement.type.program
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.program','KS-SYS','agendaTypeService','N','0')
/

--kuali.statement.type.course.enrollmentEligibility
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.enrollmentEligibility','KS-SYS','agendaTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.statement.type.course.creditConstraints
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.creditConstraints','KS-SYS','agendaTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/

-- Rule Types ******************************************************************************************************************************************* Rules

--kuali.statement.type.course.academicReadiness.antireq
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.academicReadiness.antireq','KS-SYS','ruleTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/

--kuali.statement.type.course.academicReadiness.coreq
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.academicReadiness.coreq','KS-SYS','ruleTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/

--kuali.statement.type.course.academicReadiness.prereq
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.academicReadiness.prereq','KS-SYS','ruleTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.statement.type.course.recommendedPreparation
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.recommendedPreparation','KS-SYS','ruleTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.enrollmentEligibility'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/

--kuali.statement.type.course.academicReadiness.studentEligibility
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.academicReadiness.studentEligibility','KS-SYS','ruleTypeService','N','0')
/

--kuali.statement.type.course.academicReadiness.studentEligibilityPrereq
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq','KS-SYS','ruleTypeService','N','0')
/


--kuali.statement.type.course.credit.repeatable
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.credit.repeatable','KS-SYS','ruleTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.creditConstraints'), krms_typ_s.currval, 'A', 8, 0, 'Y')
/

--kuali.statement.type.course.credit.restriction
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.course.credit.restriction','KS-SYS','ruleTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.creditConstraints'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/

--kuali.statement.type.program.completion
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.program.completion','KS-SYS','ruleTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/

--kuali.statement.type.program.entrance
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.program.entrance','KS-SYS','ruleTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.statement.type.program.satisfactoryProgress
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.statement.type.program.satisfactoryProgress','KS-SYS','ruleTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

-- Proposition Types **************************************************************************************************************************************** Propositions

--kuali.reqComponent.type.course.completed
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.completed','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 13, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.recommendedPreparation'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 16, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 19, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.completed.all
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.completed.all','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 11, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 11, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.completed.max
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.completed.max','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 17, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.completed.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.completed.nof','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 10, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 9, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.completed.none
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.completed.none','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.credit.restriction'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 10, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.credits.completed.max
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.credits.completed.max','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 14, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.credits.completed.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.credits.completed.nof','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 12, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 12, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.credits.completed.none
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.credits.completed.none','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 13, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.enrolled.all
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.enrolled.all','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.enrolled.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.enrolled.nof','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.gpa.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.gpa.min','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.grade.max
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.grade.max','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 9, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 18, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.grade.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.grade.min','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 13, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 16, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.nof.grade.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.nof.grade.min','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 10, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 8, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 14, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 17, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/

--kuali.reqComponent.type.course.credits.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.credits.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/

--kuali.reqComponent.type.course.credits.repeat.max
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.credits.repeat.max','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.credit.repeatable'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.reqComponent.type.course.cumulative.gpa.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.cumulative.gpa.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/

--kuali.reqComponent.type.course.enrolled
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.enrolled','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.reqComponent.type.course.freeform.text
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.freeform.text','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/

--kuali.reqComponent.type.course.notcompleted
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.notcompleted','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.credit.restriction'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.reqComponent.type.course.org.credits.completed.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.org.credits.completed.min','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 12, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.recommendedPreparation'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 15, 0, 'Y')
/

--kuali.reqComponent.type.course.org.program.admitted
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.org.program.admitted','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 11, 0, 'Y')
/

--kuali.reqComponent.type.course.permission.instructor.required
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.permission.instructor.required','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 8, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 13, 0, 'Y')
/

--kuali.reqComponent.type.course.permission.org.required
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.permission.org.required','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 14, 0, 'Y')
/

--kuali.reqComponent.type.course.program.admitted
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.program.admitted','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibility'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 10, 0, 'Y')
/

--kuali.reqComponent.type.course.program.admitted.org.duration
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.program.admitted.org.duration','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 11, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 16, 0, 'Y')
/

--kuali.reqComponent.type.course.program.notadmitted
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.program.notadmitted','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibility'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 12, 0, 'Y')
/

--kuali.reqComponent.type.course.program.notadmitted.org.duration
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.program.notadmitted.org.duration','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibility'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 17, 0, 'Y')
/

--kuali.reqComponent.type.course.test.score.max
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.test.score.max','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 15, 0, 'Y')
/

--kuali.reqComponent.type.course.test.score.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.test.score.min','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 9, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 15, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/

--kuali.reqComponent.type.program.admitted.credits
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.admitted.credits','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.reqComponent.type.program.candidate.status.duration
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.candidate.status.duration','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/

--kuali.reqComponent.type.program.completion.duration
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.completion.duration','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/

--kuali.reqComponent.type.program.completion.duration.afterentry
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.completion.duration.afterentry','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 8, 0, 'Y')
/

--kuali.reqComponent.type.program.courses.advisor
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.courses.advisor','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.program.courses.advisor'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/

--kuali.reqComponent.type.program.courses.theme
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.courses.theme','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/

--kuali.reqComponent.type.program.credits.advisor
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.credits.advisor','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/

--kuali.reqComponent.type.program.credits.max
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.credits.max','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.reqComponent.type.program.credits.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.credits.min','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/

--kuali.reqComponent.type.program.credits.theme
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.credits.theme','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/

--kuali.reqComponent.type.program.cumulative.gpa.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.cumulative.gpa.min','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 18, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/

--kuali.reqComponent.type.program.duration.gpa.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.duration.gpa.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/

--kuali.reqComponent.type.program.enrolled.credits.final
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.enrolled.credits.final','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/

--kuali.reqComponent.type.program.minor.admitted.classstanding
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.minor.admitted.classstanding','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 8, 0, 'Y')
/

--kuali.reqComponent.type.program.minors.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.minors.nof','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 9, 0, 'Y')
/

--kuali.reqComponent.type.program.programset.completed.all
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.programset.completed.all','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/

--kuali.reqComponent.type.program.programset.completed.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.programset.completed.nof','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.reqComponent.type.program.programset.coursecompleted.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.programset.coursecompleted.nof','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/

--kuali.reqComponent.type.program.programset.notcompleted.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.programset.notcompleted.nof','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.entrance'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.reqComponent.type.program.residence.credits.final
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.residence.credits.final','KS-SYS','simplePropositionTypeService','N','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.statement.type.program.completion'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/








