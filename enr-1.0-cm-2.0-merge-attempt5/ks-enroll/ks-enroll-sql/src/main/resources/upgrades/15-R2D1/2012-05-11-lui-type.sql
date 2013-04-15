-----------------------------------------------
--- CO and AO Types
-----------------------------------------------

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.course.bundle', null, 'Course Bundle','Used to collect tightly related course offerings into bundles that all have to be taken together','Used to collect tightly related course offerings into bundles that all have to be taken together', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.course.offering', null, 'Course Offering','An offering of a course for a particular term','An offering of a course for a particular term', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.course.format.offering', null, 'Course Format Offering','The offering of a course in a particular format for a term','The offering of a course in a particular format for a term', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.lecture', null, 'Lecture','Instructor presentation of course materials','Instructor presentation of course materials', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.lab', null, 'Lab','Student working on projects in a defined laboratory space.    Instructors   are on-hand for students to ask questions and guidance.','Student working on projects in a defined laboratory space.    Instructors   are on-hand for students to ask questions and guidance.', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.discussion', null, 'Discussion','The exchange of opinions or questions on course material','The exchange of opinions or questions on course material', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.tutorial', null, 'Tutorial','Supplementary (or remedial) individualized instruction, Extra meetings for the review or elaboration of course materials','Supplementary (or remedial) individualized instruction, Extra meetings for the review or elaboration of course materials', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.weblecture', null, 'Web Lecture','Web-based or   technologically-mediated activities   replacing standard lectures','Web-based or   technologically-mediated activities   replacing standard lectures', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.webdiscussion', null, 'Web Discuss','Web-based or technologically-mediated activities   replacing standard discussion sections','Web-based or technologically-mediated activities   replacing standard discussion sections', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.directed', null, 'Directed','Provides students with a way to complete a  class or   other area of focused studies.  Directed study may be individualized method or in a group.','Provides students with a way to complete a  class or   other area of focused studies.  Directed study may be individualized method or in a group.', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.studio', null, 'Studio','Student practice of studio skills and/or tasks','Student practice of studio skills and/or tasks', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.correspond', null, 'Correspond','Correspondence','Correspondence', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.activity', null, 'Activity','Physical activity and participation are incorporated into the   class.','Physical activity and participation are incorporated into the   class.', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.colloquium', null, 'Colloquium','A seminar led by different instructors','A seminar led by different instructors', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.demonstration', null, 'Demonstration','Student observation of an instructional display or performance','Student observation of an instructional display or performance', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.field', null, 'Field','Instructional activity in non-classroom settings','Instructional activity in non-classroom settings', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.homework', null, 'Homework','Student''s doing homework, problem sets and reading assignments and   writing','Student''s doing homework, problem sets and reading assignments and   writing', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.independ', null, 'Independ','Student-initiated educational activity. Supervised special project   undertaken with the direction of an     assigned faculty member.','Student-initiated educational activity. Supervised special project   undertaken with the direction of an     assigned faculty member.', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.internship', null, 'Internship','Individual activity in   authentic non-academic setting   arranged by instructor','Individual activity in   authentic non-academic setting   arranged by instructor', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.private', null, 'Private','Individually scheduled classes, typically music.','Individually scheduled classes, typically music.', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.recitation', null, 'Recitation','Oral review of course material   by students, often in small groups and   often lead by TAs','Oral review of course material   by students, often in small groups and   often lead by TAs', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.research', null, 'Research','Research','Research', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.selfpaced', null, 'Self Paced','Student-paced coverage,   usually   with individualized   attention, of assigned course material','Student-paced coverage,   usually   with individualized   attention, of assigned course material', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.compbased', null, 'Comp Based','Materials are available in   electronic format for the student to   explore on their own with no or minimal     involvement by faculty','Materials are available in   electronic format for the student to   explore on their own with no or minimal     involvement by faculty', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.lui.type.activity.offering.videoconf', null, 'Video Conf','Often interactive instruction   between campuses delivered through   closed-circuit TV cameras, screens and     microphones. Students are able to interact as if they were in the   same   classroom.','Often interactive instruction   between campuses delivered through   closed-circuit TV cameras, screens and     microphones. Students are able to interact as if they were in the   same   classroom.', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- Course Set Types
-----------------------------------------------

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.soc.type.main', null, 'Main','Main SOC that holds all the course offerings for the specified term','Main SOC that holds all the course offerings for the specified term', null, null, 'http://student.kuali.org/wsdl/courseOfferingSet/SocInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.soc.type.departmental', null, 'Departmental','SOC that holds all of a department''s course offerings for a term','SOC that holds all of a department''s course offerings for a term', null, null, 'http://student.kuali.org/wsdl/courseOfferingSet/SocInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.soc.type.subject.area', null, 'Subject Area','SOC that holds all the course offerings for a particular subject area','SOC that holds all the course offerings for a particular subject area', null, null, 'http://student.kuali.org/wsdl/courseOfferingSet/SocInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.soc.type.units.content.owner', null, 'Units Content Owner','SOC that holds all the course offerings who''s content is owned by a particular department','SOC that holds all the course offerings who''s content is owned by a particular department', null, null, 'http://student.kuali.org/wsdl/courseOfferingSet/SocInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.soc.type.units.deployment.owner', null, 'Units Deployment Owner','SOC that holds all the course offerings who''s deployment is controlled (owned) by a particular department','SOC that holds all the course offerings who''s deployment is controlled (owned) by a particular department', null, null, 'http://student.kuali.org/wsdl/courseOfferingSet/SocInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.soc.rollover.results.type.rollover', null, 'Rollover Results','The results are of a rollover','The results are of a rollover', null, null, 'http://student.kuali.org/wsdl/courseOfferingSet/SocRolloverResultInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.soc.rollover.results.type.reverse', null, 'Reverse of a Rollover','The results of trying to reverse a rollover','The results of trying to reverse a rollover', null, null, 'http://student.kuali.org/wsdl/courseOfferingSet/SocRolloverResultInfo', 0, to_date('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null,null)
/

