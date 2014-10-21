--KSENROLL-13925
update KSEN_TYPE set TYPE_KEY = 'kuali.hold.holdissue.type.advising'
where TYPE_KEY = 'kuali.hold.issue.type.advising'
/
update KSEN_TYPE set TYPE_KEY = 'kuali.hold.holdissue.type.financial'
where TYPE_KEY = 'kuali.hold.issue.type.financial'
/
update KSEN_TYPE set TYPE_KEY = 'kuali.hold.holdissue.type.admissions'
where TYPE_KEY = 'kuali.hold.issue.type.admissions'
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
VALUES ('SYSTEMLOADER',TO_DATE( '20141001', 'YYYYMMDD' ),
        'Indicates an academic issue. Can include discipline issues, greek issues, etc.',
        'Indicates an academic issue. Can include discipline issues, greek issues, etc.',
        'Dean of Students','http://student.kuali.org/wsdl/type/HoldIssueInfo',
        'http://student.kuali.org/wsdl/hold/HoldService','kuali.hold.holdissue.type.deanofstudents',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
VALUES ('SYSTEMLOADER',TO_DATE( '20141001', 'YYYYMMDD' ),
        'Can indicate an issue with the student record.',
        'Can indicate an issue with the student record.',
        'Student Record','http://student.kuali.org/wsdl/type/HoldIssueInfo',
        'http://student.kuali.org/wsdl/hold/HoldService','kuali.hold.holdissue.type.studentrecord',0)
/
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.hold.issue.type.academic.progress'
/
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.hold.issue.type.discipline'
/
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.hold.issue.type.library'
/
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.hold.issue.type.medical.immunization'
/
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.hold.issue.type.international.students'
/
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.hold.issue.type.financial.aid'
/
update KSEN_HOLD_ISSUE set NAME = 'Dean of Students', HOLD_CD = 'DISP02', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.deanofstudents'
where HOLD_CD = 'ACAD01' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.academic.progress'
/
update KSEN_HOLD_ISSUE set NAME = 'Student Record', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.studentrecord'
where HOLD_CD = 'ACAD02' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.academic.progress'
/
update KSEN_HOLD_ISSUE set NAME = 'Dean of Students', HOLD_CD = 'DISP03', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.deanofstudents'
where HOLD_CD = 'ACAD03' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.academic.progress'
/
update KSEN_HOLD_ISSUE set NAME = 'Admissions Issues', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.admissions'
where HOLD_CD = 'ADMIS03' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.admissions'
/
update KSEN_HOLD_ISSUE set NAME = 'Student Record', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.studentrecord'
where HOLD_CD = 'ACAD06' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.academic.progress'
/
update KSEN_HOLD_ISSUE set NAME = 'Financial Issue', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.financial'
where HOLD_CD = 'FIN02' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.financial.aid'
/
update KSEN_HOLD_ISSUE set NAME = 'Financial Issue', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.financial'
where HOLD_CD = 'FIN01' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.financial'
/
update KSEN_HOLD_ISSUE set NAME = 'Student Record', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.studentrecord'
where HOLD_CD = 'ACAD04' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.academic.progress'
/
update KSEN_HOLD_ISSUE set NAME = 'Student Record', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.studentrecord'
where HOLD_CD = 'ACAD05' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.academic.progress'
/
update KSEN_HOLD_ISSUE set NAME = 'Admissions Issues', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.admissions'
where HOLD_CD = 'ADMIS02' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.international.students'
/
update KSEN_HOLD_ISSUE set NAME = 'Dean of Students', HOLD_CD = 'DISP04', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.deanofstudents'
where HOLD_CD = 'DISP01' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.discipline'
/
update KSEN_HOLD_ISSUE set NAME = 'Admissions Issues', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.admissions'
where HOLD_CD = 'ADMIS02' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.international.students'
/
update KSEN_HOLD_ISSUE set NAME = 'Student Record', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.studentrecord'
where HOLD_CD = 'ACAD08' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.academic.progress'
/
update KSEN_HOLD_ISSUE set NAME = 'Academic Advising Issue', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.advising'
where HOLD_CD = 'ADV01' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.advising'
/
update KSEN_HOLD_ISSUE set NAME = 'Admissions Issues', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.admissions'
where HOLD_CD = 'ADMIS01' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.admissions'
/
update KSEN_HOLD_ISSUE set NAME = 'Student Record', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.studentrecord'
where HOLD_CD = 'MED01' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.medical.immunization'
/
update KSEN_HOLD_ISSUE set NAME = 'Student Record', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.studentrecord'
where HOLD_CD = 'ACAD07' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.academic.progress'
/
update KSEN_HOLD_ISSUE set NAME = 'Academic Advising Issue', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.advising'
where HOLD_CD = 'ADV02' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.advising'
/
update KSEN_HOLD_ISSUE set NAME = 'Student Record', HOLD_ISSUE_TYPE = 'kuali.hold.holdissue.type.studentrecord'
where HOLD_CD = 'ACAD09' and HOLD_ISSUE_TYPE = 'kuali.hold.issue.type.academic.progress'
/
