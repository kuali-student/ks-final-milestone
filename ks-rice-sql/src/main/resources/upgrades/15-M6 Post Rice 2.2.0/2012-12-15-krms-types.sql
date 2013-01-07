
-- Angenda Types ******************************************************************************************************************************************* Angenda

--kuali.statement.type.course
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.agenda.type.course','KS-SYS','agendaTypeService','Y','0')
/

--kuali.statement.type.program
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.agenda.type.program','KS-SYS','agendaTypeService','Y','0')
/

--kuali.statement.type.course.enrollmentEligibility
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.agenda.type.course.enrollmentEligibility','KS-SYS','agendaTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.course'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.statement.type.course.creditConstraints
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.agenda.type.course.creditConstraints','KS-SYS','agendaTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.course'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/

--kuali.krms.agenda.type.schedule.eligibility
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.agenda.type.schedule.eligibility','KS-SYS','agendaTypeService','Y','0')
/
-- Rule Types ******************************************************************************************************************************************* Rules

--kuali.statement.type.course.academicReadiness.antireq
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.course.academicReadiness.antireq','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.course.enrollmentEligibility'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/

--kuali.statement.type.course.academicReadiness.coreq
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.course.academicReadiness.coreq','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.course.enrollmentEligibility'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/

--kuali.statement.type.course.academicReadiness.prereq
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.course.academicReadiness.prereq','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.course.enrollmentEligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.statement.type.course.recommendedPreparation
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.course.recommendedPreparation','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.course.enrollmentEligibility'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/

--kuali.statement.type.course.academicReadiness.studentEligibility  DON'T THINK THIS IS REQUIRED.
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.course.academicReadiness.studentEligibility','KS-SYS','ruleTypeService','Y','0')
/

--kuali.statement.type.course.academicReadiness.studentEligibilityPrereq THIS IS A DUPLICATE, SHOULD BE REMOVED.
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.course.enrollmentEligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/


--kuali.statement.type.course.credit.repeatable
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.course.credit.repeatable','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.course.creditConstraints'), krms_typ_s.currval, 'A', 8, 0, 'Y')
/

--kuali.statement.type.course.credit.restriction
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.course.credit.restriction','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.course.creditConstraints'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/

--kuali.statement.type.program.completion
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.program.completion','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.program'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/

--kuali.statement.type.program.entrance
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.program.entrance','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.program'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.statement.type.program.satisfactoryProgress
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.program.satisfactoryProgress','KS-SYS','ruleTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.program'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.rule.type.schedule.eligibility
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.rule.type.schedule.eligibility','KS-SYS','agendaTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.agenda.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

-- Proposition Types **************************************************************************************************************************************** Propositions

--kuali.reqComponent.type.course.completed
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.success.compl.course','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 13, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.recommendedPreparation'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 16, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 19, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.completed.all
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.success.course.courseset.completed.all','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 11, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 11, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.completed.max Note: This type is removed in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.completed.max','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 17, 0, 'Y')
--/

--kuali.reqComponent.type.course.courseset.completed.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.success.course.courseset.completed.nof','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 10, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 9, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.completed.none
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.courseset.completed.none','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.credit.restriction'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 10, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.credits.completed.max Note: Type not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.courseset.credits.completed.max','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 4, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 14, 0, 'Y')
--/

--kuali.reqComponent.type.course.courseset.credits.completed.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.courseset.credits.completed.nof','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 12, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 12, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.credits.completed.none
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.courseset.credits.completed.none','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 13, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.enrolled.all
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.courseset.enrolled.all','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.enrolled.nof
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.courseset.enrolled.nof','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.gpa.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.courseset.gpa.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 4, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.grade.max
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.courseset.grade.max','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 9, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 18, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.grade.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.courseset.grade.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 13, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 16, 0, 'Y')
/

--kuali.reqComponent.type.course.courseset.nof.grade.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.courseset.nof.grade.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 10, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 8, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 14, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 17, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/

--kuali.reqComponent.type.course.credits.min   Note: This type is not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.credits.min','KS-SYS','simplePropositionTypeService','Y','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
--/

--kuali.reqComponent.type.course.credits.repeat.max
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.credits.repeat.max','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.credit.repeatable'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.reqComponent.type.course.cumulative.gpa.min      Note: This type is not used in ENR
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.cumulative.gpa.min','KS-SYS','simplePropositionTypeService','Y','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
--/

--kuali.reqComponent.type.course.enrolled
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.enrolled','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.reqComponent.type.course.freeform.text
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.freeform.text','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.coreq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 0, 0, 'Y')
/

--kuali.reqComponent.type.course.notcompleted
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.notcompleted','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.credit.restriction'), krms_typ_s.currval, 'A', 1, 0, 'Y')
/

--kuali.reqComponent.type.course.org.credits.completed.min    Note: This is not used in ENR but CM ref is still using it.
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.course.org.credits.completed.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 12, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.recommendedPreparation'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 15, 0, 'Y')
/

--kuali.reqComponent.type.course.org.program.admitted
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.admitted.to.program.campus','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 11, 0, 'Y')
/

--kuali.reqComponent.type.course.permission.instructor.required
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.permission.instructor.required','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 8, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 13, 0, 'Y')
/

--kuali.reqComponent.type.course.permission.org.required
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.drop.org.permission.required','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 14, 0, 'Y')
/

--kuali.reqComponent.type.course.program.admitted   Not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibility'), krms_typ_s.currval, 'A', 4, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 10, 0, 'Y')
--/

--kuali.reqComponent.type.course.program.admitted.org.duration  Replaced by Schedule Eligibility Proposition Type
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 11, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 16, 0, 'Y')
--/

--kuali.reqComponent.type.course.program.notadmitted
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.notadmitted.to.program','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibility'), krms_typ_s.currval, 'A', 3, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 12, 0, 'Y')
/

--kuali.reqComponent.type.course.program.notadmitted.org.duration    Replaced by Schedule Eligibility Proposition Type
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.max.limit.courses.at.org.duration.for.program','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibility'), krms_typ_s.currval, 'A', 1, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 17, 0, 'Y')
--/

--kuali.reqComponent.type.course.test.score.max
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.test.score.max','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.antireq'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 15, 0, 'Y')
/

--kuali.reqComponent.type.course.test.score.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.test.score.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 9, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 15, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/

--kuali.reqComponent.type.program.admitted.credits  Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.admitted.credits','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 7, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 1, 0, 'Y')
--/

--kuali.reqComponent.type.program.candidate.status.duration  Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.candidate.status.duration','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 4, 0, 'Y')
--/

--kuali.reqComponent.type.program.completion.duration  Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.completion.duration','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 3, 0, 'Y')
--/

--kuali.reqComponent.type.program.completion.duration.afterentry     Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.completion.duration.afterentry','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 8, 0, 'Y')
--/

--kuali.reqComponent.type.program.courses.advisor         Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.courses.advisor','KS-SYS','simplePropositionTypeService','Y','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.reqComponent.type.program.courses.advisor'), krms_typ_s.currval, 'A', 0, 0, 'Y')
--/

--kuali.reqComponent.type.program.courses.theme      Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.courses.theme','KS-SYS','simplePropositionTypeService','Y','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 0, 0, 'Y')
--/

--kuali.reqComponent.type.program.credits.advisor       Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.credits.advisor','KS-SYS','simplePropositionTypeService','Y','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 0, 0, 'Y')
--/

--kuali.reqComponent.type.program.credits.max        Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.credits.max','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 2, 0, 'Y')
--/

--kuali.reqComponent.type.program.credits.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.credits.earned.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 5, 0, 'Y')
/

--kuali.reqComponent.type.program.credits.theme     Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.credits.theme','KS-SYS','simplePropositionTypeService','Y','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 0, 0, 'Y')
--/

--kuali.reqComponent.type.program.cumulative.gpa.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.cumulative.gpa.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 18, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/

--kuali.reqComponent.type.program.duration.gpa.min
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.duration.cumulative.gpa.min','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.satisfactoryProgress'), krms_typ_s.currval, 'A', 7, 0, 'Y')
/

--kuali.reqComponent.type.program.enrolled.credits.final        Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.enrolled.credits.final','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 7, 0, 'Y')
--/

--kuali.reqComponent.type.program.minor.admitted.classstanding      Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.minor.admitted.classstanding','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 8, 0, 'Y')
--/

--kuali.reqComponent.type.program.minors.nof        Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.minors.nof','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 9, 0, 'Y')
--/

--kuali.reqComponent.type.program.programset.completed.all      Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.programset.completed.all','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 2, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 3, 0, 'Y')
--/

--kuali.reqComponent.type.program.programset.completed.nof      Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.programset.completed.nof','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 1, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 1, 0, 'Y')
--/

--kuali.reqComponent.type.program.programset.coursecompleted.nof       Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.programset.coursecompleted.nof','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 3, 0, 'Y')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 4, 0, 'Y')
--/

--kuali.reqComponent.type.program.programset.notcompleted.nof      Note: not used in ENR.
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.programset.notcompleted.nof','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 2, 0, 'Y')
--/

--kuali.reqComponent.type.program.residence.credits.final
--insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.reqComponent.type.program.residence.credits.final','KS-SYS','simplePropositionTypeService','N','0')
--/
--insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.completion'), krms_typ_s.currval, 'A', 6, 0, 'Y')
--/

--Schedule Eligibility Proposition Types.
--kuali.krms.proposition.type.drop.min.credit.hours.due.to.attribute
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.drop.min.credit.hours.due.to.attribute','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.proposition.type.drop.min.credit.hours
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.drop.min.credit.hours','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.proposition.type.exceeds.minutes.overlap.allowed
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.exceeds.minutes.overlap.allowed','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.proposition.type.time.conflict.start.end
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.time.conflict.start.end','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.proposition.type.max.limit.courses.for.program
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.max.limit.courses.for.program','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.proposition.type.max.limit.credits.for.program
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.max.limit.credits.for.program','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.proposition.type.max.limit.courses.at.org.for.program
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.proposition.type.max.limit.courses.at.org.duration.for.program
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.max.limit.courses.at.org.duration.for.program','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.proposition.type.max.limit.courses.for.campus.duration
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.max.limit.courses.for.campus.duration','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/

--kuali.krms.proposition.type.max.limit.credits.for.campus.duration
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.max.limit.credits.for.campus.duration','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.schedule.eligibility'), krms_typ_s.currval, 'A', 2, 0, 'Y')
/


--New ENR Proposition Types
--kuali.krms.proposition.type.cant.add.to.activity.offering.due.to.state
--kuali.krms.proposition.type.no.repeat.course
--kuali.krms.proposition.type.no.repeat.courses
--kuali.krms.proposition.type.avail.seat
--kuali.krms.proposition.type.success.compl.course.as.of.term
--kuali.krms.proposition.type.success.compl.prior.to.term
--kuali.krms.proposition.type.success.compl.course.between.terms
--kuali.krms.proposition.type.success.compl.course.offer
--kuali.krms.proposition.type.notadmitted.to.program.in.class.standing
--kuali.krms.proposition.type.admitted.to.program.org
--kuali.krms.proposition.type.in.class.standing
--kuali.krms.proposition.type.greater.than.class.standing
--kuali.krms.proposition.type.less.than.class.standing
--kuali.krms.proposition.type.notin.class.standing
--kuali.krms.proposition.type.course.offering.enrolled
--kuali.krms.proposition.type.course.courseset.enrolled
--kuali.krms.proposition.type.no.repeat.course.nof

--kuali.krms.proposition.type.course.offering.drop.instructor.permission.required
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.course.offering.drop.instructor.permission.required','KS-SYS','simplePropositionTypeService','N','0')
/

--kuali.krms.proposition.type.test.score.between.values
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.test.score.between.values','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 9, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 15, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/
--kuali.krms.proposition.type.test.score
insert into krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr) values (krms_typ_s.nextval, 'kuali.krms.proposition.type.test.score','KS-SYS','simplePropositionTypeService','Y','0')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.prereq'), krms_typ_s.currval, 'A', 9, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'), krms_typ_s.currval, 'A', 15, 0, 'Y')
/
insert into krms_typ_reln_t (typ_reln_id, from_typ_id, to_typ_id, reln_typ, seq_no, ver_nbr, actv) VALUES (krms_typ_reln_s.nextval, (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.program.entrance'), krms_typ_s.currval, 'A', 6, 0, 'Y')
/








