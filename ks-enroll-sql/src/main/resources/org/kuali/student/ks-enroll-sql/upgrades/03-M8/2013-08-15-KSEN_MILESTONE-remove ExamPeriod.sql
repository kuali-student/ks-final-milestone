-- KSENROLL-8689 FEP - Remove Final Exam Period from milestone type
DELETE from KSEN_MSTONE WHERE MSTONE_TYPE = 'kuali.atp.milestone.FinalExamPeriod'
/
DELETE from KSEN_TYPETYPE_RELTN WHERE RELATED_TYPE_ID = 'kuali.atp.milestone.FinalExamPeriod'
/
DELETE from KSEN_TYPE WHERE TYPE_KEY = 'kuali.atp.milestone.FinalExamPeriod'
/