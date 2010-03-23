TRUNCATE TABLE KSST_REQ_COM_TYPE_NL_TMPL DROP STORAGE
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('1','en','KUALI.CATALOG','kuali.reqCompType.courseList.none','Student must have completed none of $cluSet.getCluSetAsCode()')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('10','en','KUALI.CATALOG','kuali.reqCompType.programList.enroll.none','Not for students in $cluSet.getCluSetAsCode()')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('1000','de','KUALI.CATALOG','kuali.reqCompType.courseList.nof','Student muss abgeschlossen $fields.get("reqCompFieldType.requiredCount") von $cluSet.getCluSetAsShortName()')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('101','en','KUALI.EXAMPLE','kuali.reqCompType.courseList.none','Student must have completed none of MATH100, MATH101')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('102','en','KUALI.EXAMPLE','kuali.reqCompType.courseList.all','Student must have completed all of MATH100, MATH101, MATH102')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('103','en','KUALI.EXAMPLE','kuali.reqCompType.courseList.1of2','Student must have completed MATH100 or MATH101')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('104','en','KUALI.EXAMPLE','kuali.reqCompType.courseList.nof','Student must have completed 2 of MATH100, MATH101, MATH102')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('105','en','KUALI.EXAMPLE','kuali.reqCompType.gradecheck','Student needs a minimum GPA of 2.5')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('106','en','KUALI.EXAMPLE','kuali.reqCompType.grdCondCourseList','Students must take 3 credits from ENGL 100, ENGL 102 or ENGL 104')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('107','en','KUALI.EXAMPLE','kuali.reqCompType.courseList.coreq.all','Student must be enrolled in all of MATH100, MATH101')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('108','en','KUALI.EXAMPLE','kuali.reqCompType.courseList.coreq.oneof','Student must be enrolled in one of MATH100, MATH101, MATH102')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('109','en','KUALI.EXAMPLE','kuali.reqCompType.programList.enroll.oneof','Students must be in one of the following program(s): Creative Writing Major')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('110','en','KUALI.EXAMPLE','kuali.reqCompType.programList.enroll.none','Not for students in a Music Major or Minor')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('2','en','KUALI.CATALOG','kuali.reqCompType.courseList.all','Student must have completed all of $cluSet.getCluSetAsCode()')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('201','en','KUALI.COMPOSITION','kuali.reqCompType.courseList.none','<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('202','en','KUALI.COMPOSITION','kuali.reqCompType.courseList.all','<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('203','en','KUALI.COMPOSITION','kuali.reqCompType.courseList.1of2','<reqCompFieldType=reqCompFieldType.clu;reqCompFieldLabel=Course 1> or <reqCompFieldType=reqCompFieldType.clu;reqCompFieldLabel=Course 2>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('204','en','KUALI.COMPOSITION','kuali.reqCompType.courseList.nof','<reqCompFieldType=reqCompFieldType.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('205','en','KUALI.COMPOSITION','kuali.reqCompType.gradecheck','<reqCompFieldType=reqCompFieldType.gpa;reqCompFieldLabel=GPA>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('206','en','KUALI.COMPOSITION','kuali.reqCompType.grdCondCourseList','<reqCompFieldType=reqCompFieldType.totalCredits;reqCompFieldLabel=Credits> from <reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('207','en','KUALI.COMPOSITION','kuali.reqCompType.courseList.coreq.all','<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('208','en','KUALI.COMPOSITION','kuali.reqCompType.courseList.coreq.oneof','<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('209','en','KUALI.COMPOSITION','kuali.reqCompType.programList.enroll.oneof','<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Programs>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('210','en','KUALI.COMPOSITION','kuali.reqCompType.programList.enroll.none','<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Programs>')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('3','en','KUALI.CATALOG','kuali.reqCompType.courseList.1of2','Student must have completed $cluSet.getCluAsCode(0) or $cluSet.getCluAsCode(1)')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('4','en','KUALI.CATALOG','kuali.reqCompType.courseList.nof','Student must have completed $expectedValue of $cluSet.getCluSetAsCode()')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('5','en','KUALI.CATALOG','kuali.reqCompType.gradecheck','Student needs a minimum GPA of $gpa')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('6','en','KUALI.CATALOG','kuali.reqCompType.grdCondCourseList','Students must take $totalCredits credits from $cluSet.getCluSetAsCode()')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('7','en','KUALI.CATALOG','kuali.reqCompType.courseList.coreq.all','Student must be enrolled in all of $cluSet.getCluSetAsCode()')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('8','en','KUALI.CATALOG','kuali.reqCompType.courseList.coreq.oneof','Student must be enrolled in one of of $cluSet.getCluSetAsCode()')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('9','en','KUALI.CATALOG','kuali.reqCompType.programList.enroll.oneof','Restricted to students in the $cluSet.getCluSetAsCode() only')
/
