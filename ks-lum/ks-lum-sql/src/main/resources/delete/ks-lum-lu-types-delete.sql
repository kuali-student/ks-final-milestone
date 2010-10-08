



DELETE FROM KSLU_RSLT_USG_TYPE WHERE TYPE_KEY = 'lrType.midtermGrade';
DELETE FROM KSLU_RSLT_USG_TYPE WHERE TYPE_KEY = 'lrType.finalGrade';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.CoreProgram';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.Variation';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.MajorDiscipline';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.credential.Baccalaureate';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'luType.shell.program';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'luType.shell.course';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.activity.WebLecture';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.activity.Tutorial';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.activity.Lecture';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.activity.Lab';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.activity.Discussion';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.activity.Directed';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.NonCreditCourse';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.CreditCourseFormatShell';
DELETE FROM KSLU_LUTYPE WHERE TYPE_KEY = 'kuali.lu.type.CreditCourse';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'kuali.lu.lu.relation.type.hasProgramRequirement';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'kuali.lu.lu.relation.type.hasMajorProgram';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'kuali.lu.lu.relation.type.hasVariationProgram';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'kuali.lu.lu.relation.type.hasCoreProgram';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'luLuRelationType.hasCourseFormat';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'luLuRelationType.contains';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'luLuRelationType.colocated';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'luLuRelationType.alias';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'kuali.lu.relation.type.copyOfClu';
DELETE FROM KSLU_LULU_RELTN_TYPE WHERE ID = 'kuali.lu.relation.type.co-located';
DELETE FROM KSLU_CLU_RSLT_TYP WHERE TYPE_KEY = 'kuali.resultType.degree';
DELETE FROM KSLU_CLU_RSLT_TYP WHERE TYPE_KEY = 'kuali.resultType.certificate';
DELETE FROM KSLU_CLU_RSLT_TYP WHERE TYPE_KEY = 'kuali.resultType.gradeCourseResult';
DELETE FROM KSLU_CLU_RSLT_TYP WHERE TYPE_KEY = 'kuali.resultType.creditCourseResult';
DELETE FROM KSLU_CLU_LO_RELTN_TYPE WHERE TYPE_KEY = 'cluLoType.default';
