--Update Rule  Types Sequence

Update KRMS_TYP_RELN_T
   Set SeQ_NO = 7
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.agenda.type.course.creditConstraints')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.credit.repeatable')
/
 Update KRMS_TYP_RELN_T set SEQ_NO = 8
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.agenda.type.course.creditConstraints')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.credit.restriction')
/
--Update Sequence of Student Eligibility + Prereqs:
 Update KRMS_TYP_RELN_T set SEQ_NO = 0
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.success.compl.course')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 1
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.course.courseset.completed.all')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 2
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.course.courseset.completed.nof')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 3
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.credit.courseset.completed.nof')

/
 Update KRMS_TYP_RELN_T set SEQ_NO = 4
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.credits.courseset.completed.nof.org')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 5
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.credits.earned.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 6
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.cumulative.gpa.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 7
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.permission.instructor.required')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 8
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.permission.admin.org')
/
 Update KRMS_TYP_RELN_T set SEQ_NO = 9
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.admitted.to.program')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 10
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.admitted.to.program.org')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 11
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.admitted.to.program.campus')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 12
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.max.limit.courses.at.org.for.program')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 13
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.course.courseset.gpa.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 14
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.course.courseset.grade.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 15
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.nof.grade.min')

/
 Update KRMS_TYP_RELN_T set SEQ_NO = 16
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.duration.cumulative.gpa.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 17
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.memberof.population')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 18
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.credits.completed.nof')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 19
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.completed.nof')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 20
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.course.courseset.grade.max')
/
 Update KRMS_TYP_RELN_T set SEQ_NO = 21
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.notadmitted.to.program')
/
 Update KRMS_TYP_RELN_T set SEQ_NO = 22
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.max.limit.courses.at.org.duration.for.program')
/
 Update KRMS_TYP_RELN_T set SEQ_NO = 23
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.compl.course.as.of.term')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 24
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.compl.prior.to.term')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 25
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.compl.course.between.terms')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 26
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.freeform.text')
/
--Update Sequence of Corequisites:

 Update KRMS_TYP_RELN_T set SEQ_NO = 0
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.coreq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.course.enrolled')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 1
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.coreq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.enrolled.nof')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 2
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.coreq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.enrolled.all')

/
 Update KRMS_TYP_RELN_T set SEQ_NO = 3
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.coreq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.freeform.text')
/
--Update Sequence of Recommended Prep:

 Update KRMS_TYP_RELN_T set SEQ_NO = 0
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.success.compl.course')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 1
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.course.courseset.completed.all')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 2
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.course.courseset.completed.nof')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 3
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.credit.courseset.completed.nof')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 4
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.credits.courseset.completed.nof.org')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 5
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.credits.earned.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 6
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.cumulative.gpa.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 7
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.admitted.to.program')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 8
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.admitted.to.program.org')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 9
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.admitted.to.program.campus')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 10
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.course.courseset.gpa.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 11
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.course.courseset.grade.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 12
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.nof.grade.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 13
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.duration.cumulative.gpa.min')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 14
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.memberof.population')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 15
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.credits.completed.nof')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 16
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.completed.nof')
/


 Update KRMS_TYP_RELN_T set SEQ_NO = 17
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.compl.course.as.of.term')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 18
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.compl.prior.to.term')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 19
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.success.compl.course.between.terms')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 20
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.freeform.text')
/
--Update Sequence of Antrequisites:

 Update KRMS_TYP_RELN_T set SEQ_NO = 0
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.antireq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.course.notcompleted')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 1
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.antireq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.completed.none')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 2
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.antireq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.credits.completed.none')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 3
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.antireq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM =
               'kuali.krms.proposition.type.course.courseset.credits.completed.nof')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 4
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.antireq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.course.courseset.grade.max')
/

 Update KRMS_TYP_RELN_T set SEQ_NO = 5
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.antireq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.freeform.text')
/