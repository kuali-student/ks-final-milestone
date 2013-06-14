UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Last Day of Classes', DESCR_PLAIN = 'Last Day of Classes', NAME = 'Last Day of Classes' WHERE TYPE_KEY='kuali.atp.milestone.lastdayofclasses'
/
UPDATE KSEN_TYPE SET NAME = 'Instructional Period' WHERE TYPE_KEY = 'kuali.atp.milestone.InstructionalPeriod'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Junior Registration', DESCR_PLAIN = 'Junior Registration', NAME = 'Junior Registration' WHERE TYPE_KEY = 'kuali.atp.milestone.JuniorRegistration'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Freshmen Registration', DESCR_PLAIN = 'Freshmen Registration', NAME = 'Freshmen Registration' WHERE TYPE_KEY = 'kuali.atp.milestone.FreshmenRegistration'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'New Student Orientation', DESCR_PLAIN = 'New Student Orientation', NAME = 'New Student Orientation' WHERE TYPE_KEY = 'kuali.atp.milestone.NewStudentOrientation'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Advanced Registration Period', DESCR_PLAIN = 'Advanced Registration Period', NAME = 'Advanced Registration Period' WHERE TYPE_KEY = 'kuali.atp.milestone.AdvancedRegistrationPeriod'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Registration Begins for MBA', DESCR_PLAIN = 'Registration Begins for MBA', NAME = 'Registration Begins for MBA' WHERE TYPE_KEY = 'kuali.atp.milestone.RegistrationBeginsForMBA'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Senior Registration', DESCR_PLAIN = 'Senior Registration', NAME = 'Senior Registration' WHERE TYPE_KEY = 'kuali.atp.milestone.SeniorRegistration'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Sophomore Registration', DESCR_PLAIN = 'Sophomore Registration', NAME = 'Sophomore Registration' WHERE TYPE_KEY = 'kuali.atp.milestone.SophomoreRegistration'
/
UPDATE KSEN_TYPE SET NAME = 'Last Day to Drop with Clear Transcript' WHERE TYPE_KEY = 'kuali.atp.milestone.AddDropPeriod2'
/
UPDATE KSEN_TYPE SET NAME = 'Last Day to Drop with Annual Pass' WHERE TYPE_KEY = 'kuali.atp.milestone.AddDropPeriod3'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Last Day UG to Change Credits', DESCR_PLAIN = 'Last Day UG to Change Credits', NAME = 'Last Day UG to Change Credits' WHERE TYPE_KEY = 'kuali.atp.milestone.CourseSelectionPeriodEnd2'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Last Day UG to Drop without a "W"', DESCR_PLAIN = 'Last Day UG to Drop without a "W"', NAME = 'Last Day UG to Drop without a "W"' WHERE TYPE_KEY = 'kuali.atp.milestone.CourseSelectionPeriodEnd4'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Last Day GR to Drop', DESCR_PLAIN = 'Last Day GR to Drop', NAME = 'Last Day GR to Drop' WHERE TYPE_KEY = 'kuali.atp.milestone.DropDate2'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Last Day GR to Change Credits', DESCR_PLAIN = 'Last Day GR to Change Credits', NAME = 'Last Day GR to Change Credits' WHERE TYPE_KEY = 'kuali.atp.milestone.DropDate3'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Last Day UG to Drop with a "W"', DESCR_PLAIN = 'Last Day UG to Drop with a "W"', NAME = 'Last Day UG to Drop with a "W"' WHERE TYPE_KEY = 'kuali.atp.milestone.DropDate4'
/

-- KSENROLL-5663
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Rosh Hashanah', DESCR_PLAIN = 'Rosh Hashanah', NAME = 'Rosh Hashanah' WHERE TYPE_KEY = 'kuali.atp.milestone.RoshHashanah'
/
DELETE FROM KSEN_TYPETYPE_RELTN WHERE RELATED_TYPE_ID = 'kuali.atp.milestone.roshhashana'
/
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.atp.milestone.roshhashana'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Election Day', DESCR_PLAIN = 'Election Day', NAME = 'Election Day' WHERE TYPE_KEY = 'kuali.atp.milestone.ElectionDay'
/
DELETE FROM KSEN_TYPETYPE_RELTN WHERE RELATED_TYPE_ID = 'kuali.atp.milestone.electionday'
/
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.atp.milestone.electionday'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Martin Luther King Day', DESCR_PLAIN = 'Martin Luther King Day', NAME = 'Martin Luther King Day' WHERE TYPE_KEY = 'kuali.atp.milestone.MLKDay'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Spring Break', DESCR_PLAIN = 'Spring Break', NAME = 'Spring Break' WHERE TYPE_KEY = 'kuali.atp.milestone.SpringBreak'
/
UPDATE KSEN_TYPE SET DESCR_FORMATTED = 'Good Friday', DESCR_PLAIN = 'Good Friday', NAME = 'Good Friday' WHERE TYPE_KEY = 'kuali.atp.milestone.GoodFriday'
/
