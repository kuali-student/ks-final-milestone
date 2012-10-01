-----------------------------------------------
--- Duration Type Upgrades inculdes the ones that were originally configuired in R1 and the 2 new ones Minutes and Hours needed for appointment window slots
--- these were created using a google docs spreadsheet
--- https://docs.google.com/a/kuali.org/spreadsheet/ccc?key=0AuoZASu-9lUAdC1CeGpIcDR2c1ZhaU84NnJHcXE5dEE
-----------------------------------------------
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.TBD',null,'TBD','Ad hoc period required for negotiated sessions','Ad hoc period required for negotiated sessions',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.TwoYears',null,'Two Years','Four Year Program','Four Year Program',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.FourYears',null,'Four Years','Four Year Program','Four Year Program',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Month',null,'Month','1 month period','1 month period',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Week',null,'Week','1 week period','1 week period',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Day',null,'Day','1 day period','1 day period',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Hours',null,'Hours','1 hour period','1 hour period',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Minutes',null,'Minutes','1 minute period','1 minute period',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.HalfSemester',null,'Half Semester','Half semester (8 weeks)','Half semester (8 weeks)',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Mini-mester',null,'Mini-mester','Very Short 3 week mini-mester used for Grad in Summer','Very Short 3 week mini-mester used for Grad in Summer',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Period',null,'Period','Short one month term used for Winter Activities','Short one month term used for Winter Activities',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Semester',null,'Semester','Full 16 week semester used in fall and spring','Full 16 week semester used in fall and spring',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Quarter',null,'Quarter','Full 11 week quarter','Full 11 week quarter',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Session',null,'Session','Short 6 week sessions used in summer','Short 6 week sessions used in summer',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Term',null,'Term','12 week term used in the summer','12 week term used in the summer',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.atp.duration.Year',null,'Year','Full Year','Full Year',null, null, 'http://student.kuali.org/wsdl/atp/TimeAmountInfo', 0, to_date ('2012-03-01', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/