--
-- Copyright 2010 The Kuali Foundation Licensed under the
-- Educational Community License, Version 2.0 (the "License"); you may
-- not use this file except in compliance with the License. You may
-- obtain a copy of the License at
--
-- http://www.osedu.org/licenses/ECL-2.0
--
-- Unless required by applicable law or agreed to in writing,
-- software distributed under the License is distributed on an "AS IS"
-- BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
-- or implied. See the License for the specific language governing
-- permissions and limitations under the License.
--

-- --------------------------------------
-- |   Natural Language Configuration   |
-- --------------------------------------

--STATEMENT TYPES

-- REQUIREMENT TYPES

-- REQUIREMENT FIELD TYPES

-- REQ_COMP_TYPE -> REQ_COMP_FIELD_TYPE

-- REQ_COMPONENT_TYPE_NL_TEMPLATE

-- STMT_TYPE <-> REQ_COM_TYPE

-- STMT_TYPE <-> STMT_TYPE

-- KSST_NL_USAGE_TYPE

-- KSST_OBJECT_TYPE

-- KSST_OBJECT_SUB_TYPE

-- KSST_OBJ_TYP_JN_OBJ_SUB_TYP

-- KSST_REF_STMT_REL_TYPE

-- KSST_RSTMT_RTYP_JN_OSUB_TYP <-> KSST_OBJECT_SUB_TYPE

-- KSST_RSTMT_RTYP_JN_STMT_TYP <-> KSST_STMT_TYPE
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.completion' AND STMT_TYPE_ID = 'kuali.statement.type.program.completion';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.satisfactoryprogress' AND STMT_TYPE_ID = 'kuali.statement.type.program.satisfactoryProgress';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.entrance' AND STMT_TYPE_ID = 'kuali.statement.type.program.entrance';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.credit.recommendedpreparation' AND STMT_TYPE_ID = 'kuali.statement.type.course.recommendedPreparation';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.credit.restriction' AND STMT_TYPE_ID = 'kuali.statement.type.course.credit.restriction';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.credit.repeatable' AND STMT_TYPE_ID = 'kuali.statement.type.course.credit.repeatable';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.eligibilitiesandprerequisites' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.eligibilities' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibility';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.antirequisites' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.antireq';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.corequisites' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.coreq';
DELETE FROM KSST_RSTMT_RTYP_JN_STMT_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.prerequisites' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.completion' AND OBJ_SUB_TYPE_ID = 'program';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.satisfactoryprogress' AND OBJ_SUB_TYPE_ID = 'program';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.entrance' AND OBJ_SUB_TYPE_ID = 'program';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.credit.recommendedpreparation' AND OBJ_SUB_TYPE_ID = 'course';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.credit.restriction' AND OBJ_SUB_TYPE_ID = 'course';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.credit.repeatable' AND OBJ_SUB_TYPE_ID = 'course';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.eligibilitiesandprerequisites' AND OBJ_SUB_TYPE_ID = 'course';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.eligibilities' AND OBJ_SUB_TYPE_ID = 'course';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.antirequisites' AND OBJ_SUB_TYPE_ID = 'course';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.corequisites' AND OBJ_SUB_TYPE_ID = 'course';
DELETE FROM KSST_RSTMT_RTYP_JN_OSUB_TYP WHERE REF_STMT_REL_TYPE_ID = 'clu.prerequisites' AND OBJ_SUB_TYPE_ID = 'course';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.satisfactoryprogress';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.rule';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.prerequisites';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.entrance';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.eligibilitiesandprerequisites';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.eligibilities';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.credit.restriction';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.credit.repeatable';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.credit.recommendedpreparation';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.corequisites';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.completion';
DELETE FROM KSST_REF_STMT_REL_TYPE WHERE TYPE_KEY = 'clu.antirequisites';
DELETE FROM KSST_OBJ_TYP_JN_OBJ_SUB_TYP WHERE OBJ_TYPE_ID = 'clu' AND OBJ_SUB_TYPE_ID = 'program';
DELETE FROM KSST_OBJ_TYP_JN_OBJ_SUB_TYP WHERE OBJ_TYPE_ID = 'clu' AND OBJ_SUB_TYPE_ID = 'course';
DELETE FROM KSST_OBJECT_SUB_TYPE WHERE TYPE_KEY = 'program';
DELETE FROM KSST_OBJECT_SUB_TYPE WHERE TYPE_KEY = 'course';
DELETE FROM KSST_OBJECT_TYPE WHERE TYPE_KEY = 'clu';
DELETE FROM KSST_NL_USAGE_TYPE WHERE TYPE_KEY = 'KUALI.RULE';
DELETE FROM KSST_NL_USAGE_TYPE WHERE TYPE_KEY = 'KUALI.EXAMPLE';
DELETE FROM KSST_NL_USAGE_TYPE WHERE TYPE_KEY = 'KUALI.COMPOSITION';
DELETE FROM KSST_NL_USAGE_TYPE WHERE TYPE_KEY = 'KUALI.CATALOG';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.program.completion';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.program.satisfactoryProgress';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.program.entrance';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.creditConstraints' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.credit.repeatable';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.creditConstraints' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.credit.restriction';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.creditConstraints';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.enrollmentEligibility' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.antireq';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.enrollmentEligibility' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.recommendedPreparation';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.enrollmentEligibility' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.coreq';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.enrollmentEligibility' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq';
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course' AND CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.enrollmentEligibility';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.minors.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.completion.duration.afterentry';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.test.score.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.enrolled.credits.final';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.residence.credits.final';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.credits.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.gpa.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.programset.coursecompleted.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.programset.completed.all';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.completion' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.programset.completed.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.entrance' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.minor.admitted.classstanding';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.entrance' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.admitted.credits';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.entrance' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.test.score.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.entrance' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.gpa.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.entrance' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.programset.coursecompleted.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.entrance' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.programset.completed.all';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.entrance' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.programset.notcompleted.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.entrance' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.programset.completed.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.satisfactoryProgress' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.nof.grade.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.satisfactoryProgress' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.test.score.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.satisfactoryProgress' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.candidate.status.duration';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.satisfactoryProgress' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.completion.duration';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.satisfactoryProgress' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.credits.max';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.program.satisfactoryProgress' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.program.admitted.credits';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.credit.restriction' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.notcompleted';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.credit.restriction' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.none';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.recommendedPreparation' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.completed';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.recommendedPreparation' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.org.credits.completed.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.credit.repeatable' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.credits.repeat.max';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.org.credits.completed.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted.org.duration';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.nof.grade.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.test.score.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.permission.instructor.required';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.permission.org.required';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.max';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.gpa.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.all';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.completed';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.org.program.admitted';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted.org.duration';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibility' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibility' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibility' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.org.program.admitted';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibility' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted.org.duration';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.coreq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.enrolled';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.coreq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.enrolled.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.completed';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.org.credits.completed.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted.org.duration';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.nof.grade.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.test.score.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.permission.instructor.required';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.permission.org.required';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.max';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.gpa.min';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.nof';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.all';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.antireq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.notcompleted';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.antireq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.test.score.max';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.antireq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.none';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.antireq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.max';
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.antireq' AND REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.none';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '215';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '214';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '213';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '21';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '208';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '207';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '206';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '205';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '204';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '203';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '202';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2014';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2013';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2012';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2011';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2010';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '201';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2009';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2008';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2007';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2006';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2005';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2004';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2003';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2002';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2001';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '20';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '2';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '19';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '18';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '17';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '16';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '15';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '14';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '13';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '129';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '128';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '127';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '126';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '125';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '124';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '123';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '122';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '121';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '120';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '119';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '118';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '117';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '116';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '115';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '114';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '113';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '108';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '107';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '106';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '105';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '104';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '103';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '102';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1014';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1013';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1012';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1011';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1010';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '101';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1009';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1008';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1007';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1006';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1005';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1004';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1003';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1002';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1001';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '1';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '8';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '7';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '6';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '5';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '4';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3014';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3013';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3012';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3011';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3010';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3009';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3008';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3007';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3006';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3005';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3004';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3003';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3002';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3001';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '3';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '29';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '28';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '27';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '26';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '25';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '24';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '23';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '229';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '228';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '227';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '226';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '225';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '224';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '223';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '222';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '221';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '220';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '22';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '219';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '218';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '217';
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE ID = '216';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.minors.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.enrolled.credits.final' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.enrolled.credits.final' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.clu.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.residence.credits.final' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.residence.credits.final' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.clu.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.completion.duration.afterentry' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.durationType.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.completion.duration.afterentry' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.candidate.status.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.durationType.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.candidate.status.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.completion.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.durationType.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.completion.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.credits.max' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.credits.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.admitted.credits' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.programset.coursecompleted.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.programset.coursecompleted.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.programset.completed.all' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.programset.notcompleted.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.programset.completed.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.program.programset.completed.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.notcompleted' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.clu.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.enrolled' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.clu.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.completed' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.clu.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.org.credits.completed.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.org.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.org.credits.completed.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.credits.repeat.max' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.clu.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.clu.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.durationType.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.duration';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.org.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.notadmitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.durationType.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.duration';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.org.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.program.admitted.org.duration' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.nof.grade.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.gradeType.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.nof.grade.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.grade.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.nof.grade.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.nof.grade.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.test.score.max' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.test.score';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.test.score.max' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.test.score.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.test.score';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.test.score.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.permission.org.required' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.org.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.max' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.gradeType.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.max' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.grade.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.max' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.gradeType.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.grade.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.gpa.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.gpa';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.gpa.min' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.max' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.max' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.none' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.none' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.credits.completed.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.enrolled.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.enrolled.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.nof' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.all' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.none' AND REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.value.positive.integer';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.test.score';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.person.id';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.org.id';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.operator';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.gradeType.id';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.grade.id';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.gpa';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.durationType.id';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.duration';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.cluSet.id';
DELETE FROM KSST_REQ_COM_FIELD_TYPE WHERE ID = 'kuali.reqComponent.field.type.clu.id';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.residence.credits.final';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.programset.notcompleted.nof';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.programset.coursecompleted.nof';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.programset.completed.nof';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.programset.completed.all';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.minors.nof';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.minor.admitted.classstanding';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.enrolled.credits.final';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.credits.min';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.credits.max';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.completion.duration.afterentry';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.completion.duration';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.candidate.status.duration';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.program.admitted.credits';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.test.score.min';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.test.score.max';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.program.notadmitted.org.duration';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.program.notadmitted';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.program.admitted.org.duration';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.program.admitted';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.permission.org.required';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.permission.instructor.required';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.org.program.admitted';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.org.credits.completed.min';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.notcompleted';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.enrolled';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.credits.repeat.max';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.nof.grade.min';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.grade.min';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.grade.max';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.gpa.min';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.enrolled.nof';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.credits.completed.none';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.credits.completed.nof';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.credits.completed.max';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.completed.none';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.completed.nof';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.courseset.completed.all';
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY = 'kuali.reqComponent.type.course.completed';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.program.satisfactoryProgress';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.program.entrance';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.program.completion';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.program';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.recommendedPreparation';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.enrollmentEligibility';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.creditConstraints';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.credit.restriction';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.credit.repeatable';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.academicReadiness.studentEligibility';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.academicReadiness.prereq';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.academicReadiness.coreq';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course.academicReadiness.antireq';
DELETE FROM KSST_STMT_TYPE WHERE TYPE_KEY = 'kuali.statement.type.course';
