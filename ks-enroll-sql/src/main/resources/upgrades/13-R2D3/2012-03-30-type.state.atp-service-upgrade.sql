-----------------------------------------------
--- ATP UPGRADES
-----------------------------------------------

UPDATE KSEN_ATP SET CREATEID='SYSTEMLOADER' WHERE CREATEID IS NULL
/

ALTER TABLE KSEN_ATP MODIFY CREATEID VARCHAR2(255) NOT NULL
/

UPDATE KSEN_ATP SET CREATETIME=TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS') WHERE CREATETIME IS NULL
/

ALTER TABLE KSEN_ATP MODIFY CREATETIME TIMESTAMP NOT NULL
/

ALTER TABLE KSEN_ATP MODIFY DESCR_PLAIN VARCHAR2(4000)
/

ALTER TABLE KSEN_ATP MODIFY VER_NBR NUMBER(19) NOT NULL
/

ALTER TABLE KSEN_ATP_ATTR RENAME COLUMN OWNER TO OWNER_ID
/

UPDATE KSEN_ATPATP_RELTN SET CREATEID='SYSTEMLOADER' WHERE CREATEID IS NULL
/

ALTER TABLE KSEN_ATPATP_RELTN MODIFY CREATEID VARCHAR2(255) NOT NULL
/

UPDATE KSEN_ATPATP_RELTN SET CREATETIME=TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS') WHERE CREATETIME IS NULL
/

ALTER TABLE KSEN_ATPATP_RELTN MODIFY CREATETIME TIMESTAMP NOT NULL
/

ALTER TABLE KSEN_ATPATP_RELTN MODIFY VER_NBR NUMBER(19) NOT NULL
/

ALTER TABLE KSEN_ATPATP_RELTN_ATTR RENAME COLUMN OWNER TO OWNER_ID
/

UPDATE KSEN_ATPMSTONE_RELTN SET CREATEID='SYSTEMLOADER' WHERE CREATEID IS NULL
/

ALTER TABLE KSEN_ATPMSTONE_RELTN MODIFY CREATEID VARCHAR2(255) NOT NULL
/

UPDATE KSEN_ATPMSTONE_RELTN SET CREATETIME=TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS') WHERE CREATETIME IS NULL
/

ALTER TABLE KSEN_ATPMSTONE_RELTN MODIFY CREATETIME TIMESTAMP NOT NULL
/

ALTER TABLE KSEN_ATPMSTONE_RELTN MODIFY VER_NBR NUMBER(19) NOT NULL
/

UPDATE KSEN_MSTONE SET CREATEID='SYSTEMLOADER' WHERE CREATEID IS NULL
/

ALTER TABLE KSEN_MSTONE MODIFY CREATEID VARCHAR2(255) NOT NULL
/

UPDATE KSEN_MSTONE SET CREATETIME=TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS') WHERE CREATETIME IS NULL
/

ALTER TABLE KSEN_MSTONE MODIFY CREATETIME TIMESTAMP NOT NULL
/

ALTER TABLE KSEN_MSTONE MODIFY DESCR_PLAIN VARCHAR2(4000)
/

ALTER TABLE KSEN_MSTONE MODIFY VER_NBR NUMBER(19) NOT NULL
/

ALTER TABLE KSEN_MSTONE_ATTR RENAME COLUMN OWNER TO OWNER_ID
/

-----------------------------------------------
--- STATE UPGRADES
-----------------------------------------------

UPDATE KSEN_STATE_PROCESS SET CREATEID='SYSTEMLOADER' WHERE CREATEID IS NULL
/

UPDATE KSEN_STATE_PROCESS SET CREATETIME=TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS') WHERE CREATETIME IS NULL
/

INSERT INTO KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME) (SELECT ID, OBJ_ID, NAME, DESCR, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME FROM KSEN_STATE_PROCESS WHERE ID LIKE 'kuali.atp.%'  OR ID LIKE 'kuali.milestone.%')
/

DELETE FROM KSEN_STATEPROCESS_RELTN WHERE PROCESS_KEY LIKE 'kuali.atp.%' OR ID LIKE 'kuali.milestone.%'
/

DELETE FROM KSEN_STATE_PROCESS WHERE ID LIKE 'kuali.atp.%' OR ID LIKE 'kuali.milestone.%'
/

INSERT INTO KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) (SELECT ID, OBJ_ID, NAME, DESCR, null, PROCESS_KEY, null, null, VER_NBR, TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS'), 'SYSTEMLOADER', TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS'), 'SYSTEMLOADER' FROM KSEN_COMM_STATE WHERE ID LIKE 'kuali.atp.%' OR ID LIKE 'kuali.milestone.%')
/

DELETE FROM KSEN_COMM_STATE WHERE ID LIKE 'kuali.atp.%' OR ID LIKE 'kuali.milestone.%'
/


-----------------------------------------------
--- DROPPING UNUSED TABLES
-----------------------------------------------

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATP_STATE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATP_STATE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATP_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATP_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPMSTONE_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPMSTONE_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/


DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPTYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPTYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

-----------------------------------------------
--- NEW ATP REFERENCE TYPES
-----------------------------------------------

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group', null, 'Group Type Type Relation','Types grouped together','Types grouped together', null, null, 'http://student.kuali.org/wsdl/type/TypeTypeRelationInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed', null, 'Allowed Type Type Relation','Type specifies which types can go together','Type specifies which types can go together', null, null, 'http://student.kuali.org/wsdl/type/TypeTypeRelationInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.milestone.type.group.holiday', null, 'Holiday Group','The list of milestone types that are allowed for holidays','The list of milestone types that are allowed for holidays', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.milestone.type.group.event', null, 'Event Group','The list of milestone types that are used for academic calendar events','The list of milestone types that are used for academic calendar events', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.milestone.type.group.instructional', null, 'Instructional','The list of milestone types that are used for Instructional Key Dates that are associated with a term','The list of milestone types that are used for Key Dates that are associated with a term', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.milestone.type.group.registration', null, 'Registration','The list of milestone types that are used for Registration Key Dates that are associated with a term','The list of milestone types that are used for Key Dates that are associated with a term', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.type.group.term', null, 'Term Group','The list of Terms that are associated with an academic calendar','The list of Terms that are associated with an academic calendar', null, null, 'http://student.kuali.org/wsdl/atp/AtpInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.milestone.type.group.appt.regperiods', null, 'Registration Periods for Registration Appointments', 'The list of milestone types that are used for Registration Key Dates in Registration Appointments', 'The list of milestone types that are used for Registration Key Dates in Registration Appointments',null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/
-----------------------------------------------
--- NEW CALENDAR TYPES
-----------------------------------------------

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.type.AcademicCalendar', null, 'Academic Calendar','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.type.HolidayCalendar', null, 'Holiday Calendar','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW TERM TYPES
-----------------------------------------------

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.type.Fall', null, 'Fall Term','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.type.Winter', null, 'Winter Term','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.type.Spring', null, 'Spring Term','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.type.Summer', null, 'Summer Term','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW HOLIDAYS
-----------------------------------------------

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.LaborDay', null, 'Labor Day','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.ColumbusDay', null, 'Columbus Day','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.VeteransDay', null, 'Veterans Day','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.VeteransDayObserved', null, 'Veterans Day observed','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.ThanksgivingBreak', null, 'Thanksgiving Break','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.Christmas', null, 'Christmas','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.ChristmasObserved', null, 'Christmas observed','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.NewYearsDay', null, 'New Year''s Day','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.NewYearsDayObserved', null, 'New Year''s Day observed','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.MLKDayObserved', null, 'Martin Luther King Day observed','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.PresidentsDay', null, 'President''s Day','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.MemorialDay', null, 'Memorial Day','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.IndependenceDay', null, 'Independence Day','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.IndependenceDayObserved', null, 'Independence Day observed','','', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW TERM KEY DATES
-----------------------------------------------

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.NewStudentConvocation', null, 'Freshmen Convocation','Ceremony to welcome freshmen','Ceremony to welcome freshmen', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.Homecoming', null, 'Homecoming Week','Week of activities welcome Alumni back to campus','Week of activities welcome Alumni back to campus', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.Commencement', null, 'Commencement - Seattle Campus','Day upon which the Seattle Campus conducts its University Graduation Ceremonies','Day upon which the Seattle Campus conducts its University Graduation Ceremonies', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.RegistrationOpen', null, 'Registration Services Open','Registration Services Start Date (first date student can start taking care of acknowledgements in advance of registration)','Registration Services Start Date (first date student can start taking care of acknowledgements in advance of registration)', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.RegistrationPeriod1', null, 'Registration Period 1','Priority Registration Period for Continuing Students','Priority Registration Period for Continuing Students', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.RegistrationPeriod2', null, 'Registration Period 2','Registration Period for Newly Admitted and Returning Former Students','Registration Period for Newly Admitted and Returning Former Students', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.RegistrationPeriod3', null, 'Registration Period 3','Registration Period open to all students for adds and changes.','Registration Period open to all students for adds and changes.', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.RegistrationPeriod4', null, 'Late Registration Period 1','Date of first late registration charge for quarter if not registered prior to the first day of the quarter','Date of first late registration charge for quarter if not registered prior to the first day of the quarter', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.RegistrationPeriod5', null, 'Late Registration Period 2','Date of second late registration charge for quarter if not registered prior to the fifteenth calendar day of the quarter','Date of second late registration charge for quarter if not registered prior to the fifteenth calendar day of the quarter', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.RegistrationPeriod6', null, 'Late Schedule Changes','Date that late change fee begins (eighth calendar day) for any add/drops/changes in registration.','Date that late change fee begins (eighth calendar day) for any add/drops/changes in registration.', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.AddDropPeriod1', null, 'Last Day to Add Classes','This is the last day a student may register in courses on the web for the quarter.','This is the last day a student may register in courses on the web for the quarter.', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.AddDropPeriod2', null, 'Last day to Drop with Clear Transcript','This is the last day a student may choose to drop a course for the quarter and not have it appear on the transcript.','This is the last day a student may choose to drop a course for the quarter and not have it appear on the transcript.', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.AddDropPeriod3', null, 'Last day to Drop with Annual Pass','The last day in the quarter an undergraduate student may choose to drop a course provided they have not already used their annual drop for the academic year.','The last day in the quarter an undergraduate student may choose to drop a course provided they have not already used their annual drop for the academic year.', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.InstructionalPeriod', null, 'Instructional period','Dates between which classes are held','Dates between which classes are held', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.FinalExamPeriod', null, 'Final Examination Period','Dates between which Final Exams are held','Dates between which Final Exams are held', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.milestone.GradingPeriod', null, 'Grading Period','Dates between which grades can be submitted','Dates between which grades can be submitted', null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW ATP TYPE TYPE RELATIONS  - TERM GROUPING
-----------------------------------------------

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Fall', null, 'kuali.type.type.relation.type.group','kuali.type.type.relation.state.active',null,null,'kuali.atp.type.group.term','kuali.atp.type.Fall',0,0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Winter', null, 'kuali.type.type.relation.type.group','kuali.type.type.relation.state.active',null,null,'kuali.atp.type.group.term','kuali.atp.type.Winter',1,0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Spring', null, 'kuali.type.type.relation.type.group','kuali.type.type.relation.state.active',null,null,'kuali.atp.type.group.term','kuali.atp.type.Spring',2,0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Summer', null, 'kuali.type.type.relation.type.group','kuali.type.type.relation.state.active',null,null,'kuali.atp.type.group.term','kuali.atp.type.Summer',3,0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW ATP TYPE TYPE RELATIONS - HOLIDAY GROUPING
-----------------------------------------------

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.LaborDay', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.LaborDay', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.ColumbusDay', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.ColumbusDay', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.VeteransDay', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.VeteransDay', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.VeteransDayObserved', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.VeteransDayObserved', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.ThanksgivingBreak', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.ThanksgivingBreak', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.Christmas', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.Christmas', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.ChristmasObserved', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.ChristmasObserved', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.NewYearsDay', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.NewYearsDay', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.NewYearsDayObserved', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.NewYearsDayObserved', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.MLKDayObserved', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.MLKDayObserved', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.PresidentsDay', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.PresidentsDay', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.MemorialDay', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.MemorialDay', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.IndependenceDay', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.IndependenceDay', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.holiday.kuali.atp.milestone.IndependenceDayObserved', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.holiday', 'kuali.atp.milestone.IndependenceDayObserved', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW ATP TYPE TYPE RELATIONS - EVENT GROUPING
-----------------------------------------------

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.event.kuali.atp.milestone.NewStudentConvocation', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.event', 'kuali.atp.milestone.NewStudentConvocation', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.event.kuali.atp.milestone.Homecoming', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.event', 'kuali.atp.milestone.Homecoming', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.event.kuali.atp.milestone.Commencement', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.event', 'kuali.atp.milestone.Commencement', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW ATP TYPE TYPE RELATIONS - REGISTRATION KEYDATE GROUPING
-----------------------------------------------

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.RegistrationOpen', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.RegistrationOpen', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.RegistrationPeriod1', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.RegistrationPeriod1', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.RegistrationPeriod2', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.RegistrationPeriod2', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.RegistrationPeriod3', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.RegistrationPeriod3', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.RegistrationPeriod4', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.RegistrationPeriod4', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.RegistrationPeriod5', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.RegistrationPeriod5', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.RegistrationPeriod6', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.RegistrationPeriod6', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.AddDropPeriod1', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.AddDropPeriod1', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.AddDropPeriod2', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.AddDropPeriod2', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.AddDropPeriod3', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.AddDropPeriod3', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW ATP TYPE TYPE RELATIONS - REGISTRATION PERIOD GROUPING
-----------------------------------------------

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.appt.regperiods.kuali.atp.milestone.RegistrationPeriod1', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.appt.regperiods', 'kuali.atp.milestone.RegistrationPeriod1', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.appt.regperiods.kuali.atp.milestone.RegistrationPeriod2', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.appt.regperiods', 'kuali.atp.milestone.RegistrationPeriod2', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.appt.regperiods.kuali.atp.milestone.RegistrationPeriod3', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.appt.regperiods', 'kuali.atp.milestone.RegistrationPeriod3', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.appt.regperiods.kuali.atp.milestone.RegistrationPeriod4', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.appt.regperiods', 'kuali.atp.milestone.RegistrationPeriod4', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.appt.regperiods.kuali.atp.milestone.RegistrationPeriod5', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.appt.regperiods', 'kuali.atp.milestone.RegistrationPeriod5', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.appt.regperiods.kuali.atp.milestone.RegistrationPeriod6', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.appt.regperiods', 'kuali.atp.milestone.RegistrationPeriod6', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/



-----------------------------------------------
--- NEW ATP TYPE TYPE RELATIONS - INSTRUCTIONAL KEYDATE GROUPING
-----------------------------------------------

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.instructional.kuali.atp.milestone.InstructionalPeriod', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.instructional', 'kuali.atp.milestone.InstructionalPeriod', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.instructional.kuali.atp.milestone.FinalExamPeriod', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.instructional', 'kuali.atp.milestone.FinalExamPeriod', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE,TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.group.kuali.milestone.type.group.instructional.kuali.atp.milestone.GradingPeriod', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.instructional', 'kuali.atp.milestone.GradingPeriod', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW ATP TYPE TYPE RELATIONS - ALLOWED TERMS FOR ACAL
-----------------------------------------------

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Fall', null, 'kuali.type.type.relation.type.allowed','kuali.type.type.relation.state.active',null,null,'kuali.atp.type.AcademicCalendar','kuali.atp.type.Fall',0,0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Winter', null, 'kuali.type.type.relation.type.allowed','kuali.type.type.relation.state.active',null,null,'kuali.atp.type.AcademicCalendar','kuali.atp.type.Winter',1,0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Spring', null, 'kuali.type.type.relation.type.allowed','kuali.type.type.relation.state.active',null,null,'kuali.atp.type.AcademicCalendar','kuali.atp.type.Spring',2,0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Summer', null, 'kuali.type.type.relation.type.allowed','kuali.type.type.relation.state.active',null,null,'kuali.atp.type.AcademicCalendar','kuali.atp.type.Summer',3,0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW ATP TYPE TYPE RELATIONS - ALLOWED KEYDATE GROUP FOR TERMS
-----------------------------------------------

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Fall.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active',null,null, 'kuali.atp.type.Fall', 'kuali.milestone.type.group.registration', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Winter.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active',null,null, 'kuali.atp.type.Winter', 'kuali.milestone.type.group.registration', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Spring.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active',null,null, 'kuali.atp.type.Spring', 'kuali.milestone.type.group.registration', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active',null,null, 'kuali.atp.type.Summer', 'kuali.milestone.type.group.registration', 0, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW ATP TYPE TYPE RELATIONS - ALLOWED CIRRICULUM GROUP FOR TERMS
-----------------------------------------------

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Fall.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active',null,null, 'kuali.atp.type.Fall', 'kuali.milestone.type.group.instructional', 1, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Winter.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active',null,null, 'kuali.atp.type.Winter', 'kuali.milestone.type.group.instructional', 1, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Spring.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active',null,null, 'kuali.atp.type.Spring', 'kuali.milestone.type.group.instructional', 1, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID,OBJ_ID,TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE,EFF_DT,EXPIR_DT,OWNER_TYPE_ID,RELATED_TYPE_ID,RANK,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active',null,null, 'kuali.atp.type.Summer', 'kuali.milestone.type.group.instructional', 1, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/
