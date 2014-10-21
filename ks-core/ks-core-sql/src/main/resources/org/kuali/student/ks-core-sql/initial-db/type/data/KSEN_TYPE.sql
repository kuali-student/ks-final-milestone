TRUNCATE TABLE KSEN_TYPE DROP STORAGE
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'An appointment slot that specifies both the start and end times','An appointment slot that specifies both the start and end times','Closed slot','http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo','http://student.kuali.org/wsdl/appointment/AppointmentService','kuali.appointment.slot.type.closed',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'An appointment for registration','An appointment for registration','Registration appointment','http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo','http://student.kuali.org/wsdl/appointment/AppointmentService','kuali.appointment.type.registration',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'A window that does not specify rules but has one slot that is co-extensive with the window','A window that does not specify rules but has one slot that is co-extensive with the window','One Slot per Window','http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo','http://student.kuali.org/wsdl/appointment/AppointmentService','kuali.appointment.window.type.one.slot',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'A window that specifies interval and max_appts_per_slot to create appointment slots that will be assigned at most max_appts_per_slot. Specifying end date is optional (if not long enough, will get "not enough room" error.)','A window that specifies interval and max_appts_per_slot to create appointment slots that will be assigned at most max_appts_per_slot. Specifying end date is optional (if not long enough, will get "not enough room" error.)','Max Slotted Window','http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo','http://student.kuali.org/wsdl/appointment/AppointmentService','kuali.appointment.window.type.slotted.max',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'A window that specifies interval and end date (but not max_appts_per_slot) to create appointment slots that will be assigned appointments as uniformly as possible','A window that specifies interval and end date (but not max_appts_per_slot) to create appointment slots that will be assigned appointments as uniformly as possible','Uniform Slotted Window','http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo','http://student.kuali.org/wsdl/appointment/AppointmentService','kuali.appointment.window.type.slotted.uniform',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Advance Registration Period','Advance Registration Period',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Advance Registration Period','C9A3B848EC96470DA86E9487A0662E7F','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.DateRange.AdvanceRegistrationPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Fall Break','Fall Break',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Fall Break','C784C90C365545D1A973B67EC130FED1','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.DateRange.FallBreak',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Family Weekend','Family Weekend',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Family Weekend','E6B7A00F2FB7466A916909D40CB8CD1A','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.DateRange.FamilyWeekend',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Final Examinations','Final Examinations',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Final Exam Period','7C68763A12D742D195296C216B93DFA9','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.DateRange.FinalExamPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'New Student Orientation','New Student Orientation',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'New Student Orientation','95F54004E218409C944A6AF70267ED88','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.DateRange.NewStudentOrientation',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Proposal Period','Proposal Period',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Proposal Period','0920DC59D6764C4991845FA2979C0EE4','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.DateRange.ProposalPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Reading Days','Reading Days',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Reading Period','89042CEF245A45A8AA9C8FF046FC5A97','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.DateRange.ReadingPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Period within which proposal are grouped and reviewed together for approval','Period within which proposal are grouped and reviewed together for approval',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Review Period','A7658B4571E24684A8E8F96845AC72E0','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.DateRange.ReviewPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'1 day period','1 day period','Day','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Day',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Four Year Program','Four Year Program','Four Years','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.FourYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Half semester (8 weeks)','Half semester (8 weeks)','Half Semester','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.HalfSemester',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'1 hour period','1 hour period','Hours','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Hours',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Very Short 3 week mini-mester used for Grad in Summer','Very Short 3 week mini-mester used for Grad in Summer','Mini-mester','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Mini-mester',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'1 minute period','1 minute period','Minutes','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Minutes',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'1 month period','1 month period','Month','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Month',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Short one month term used for Winter Activities','Short one month term used for Winter Activities','Period','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Period',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Full 11 week quarter','Full 11 week quarter','Quarter','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Quarter',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Full 16 week semester used in fall and spring','Full 16 week semester used in fall and spring','Semester','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Semester',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Short 6 week sessions used in summer','Short 6 week sessions used in summer','Session','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Session',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Ad hoc period required for negotiated sessions','Ad hoc period required for negotiated sessions','TBD','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.TBD',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'12 week term used in the summer','12 week term used in the summer','Term','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Term',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Four Year Program','Four Year Program','Two Years','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.TwoYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'1 week period','1 week period','Week','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Week',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Full Year','Full Year','Year','http://student.kuali.org/wsdl/atp/TimeAmountInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.duration.Year',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'This is the last day a student may register in courses on the web for the quarter.','This is the last day a student may register in courses on the web for the quarter.','Last Day to Add Classes','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.AddDropPeriod1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'This is the last day a student may choose to drop a course for the quarter and not have it appear on the transcript.','This is the last day a student may choose to drop a course for the quarter and not have it appear on the transcript.','Last day to Drop with Clear Transcript','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.AddDropPeriod2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The last day in the quarter an undergraduate student may choose to drop a course provided they have not already used their annual drop for the academic year.','The last day in the quarter an undergraduate student may choose to drop a course provided they have not already used their annual drop for the academic year.','Last day to Drop with Annual Pass','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.AddDropPeriod3',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Early Admissions Application Due Date','Early Admissions Application Due Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Admissions Application Due Early Cycle','A58E956F8F784BB29C4DD6FF7B9ED4DB','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.AdmissionsApplicationDueEarlyCycle',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'AdvancedRegistrationPeriod','AdvancedRegistrationPeriod',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'AdvancedRegistrationPeriod','6FCEFCB59DEA43D18A3726A1F3071A98','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.AdvancedRegistrationPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Alumni Day','Alumni Day',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Alumni Day','870900CF9FCB40F38B83115E1B202940','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.AlumniDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Baccalaureate','Baccalaureate',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Baccalaureate','5A340649A10E45F7BF7FA77EB5284986','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Baccalaureate',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Packaging Process Begin Date','Packaging Process Begin Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Begin Packaging','D27F931D062E4A4F9301B988E010B6EC','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.BeginPackaging',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Christmas','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Christmas',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Christmas observed','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.ChristmasObserved',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Classes Begin Date','Classes Begin Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Classes Begin','3839C93114344FD3B2FA87E9CA9E6CA5','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.ClassesBegin',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Classes End Date','Classes End Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Classes End','0FA1AC57E12E4477A2EE1BDA10B617E6','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.ClassesEnd',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Columbus Day','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.ColumbusDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Day upon which the Seattle Campus conducts its University Graduation Ceremonies','Day upon which the Seattle Campus conducts its University Graduation Ceremonies','Commencement - Seattle Campus','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Commencement',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Coordinators Kickoff Meeting','Coordinators Kickoff Meeting',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Coordinators Kickoff Meeting','2B6EE819BFF24FC89610901E6089E6CE','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.CoordinatorsKickoffMeeting',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Course Selection Period ends','Course Selection Period ends',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Course Selection Period End','67D995B76DA748AFBE1BD432C4ECF713','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.CourseSelectionPeriodEnd',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('PATCH',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last Day to Add Course','Last Day to Add Course',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last Day to Add Course','5169AB66EAB24879AF29D355E3C296A3','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.CourseSelectionPeriodEnd1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('PATCH',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day UG to Change Credits','Last day UG to Change Credits',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day UG to Change Credits','DE9E05C8CBF345E4BA600E90D61A582D','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.CourseSelectionPeriodEnd2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('PATCH',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last Day UG to Change Grading Method','Last Day UG to Change Grading Method',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last Day UG to Change Grading Method','0BECA7B1A42A4A1A82F6FEF8ECA24D3C','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.CourseSelectionPeriodEnd3',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('PATCH',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day UG to Drop without a "W"','Last day UG to Drop without a "W"',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day UG to Drop without a "W"','34F824E9A2654D948F7D8F35B7CC0383','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.CourseSelectionPeriodEnd4',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Curriculum Committee Meeting','Curriculum Committee Meeting',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Curriculum Committee Meeting','714F3D3505DD405A86CBC11B34CDB53C','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.CurriculumCommitteeMeeting',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Funds Disbursement Date','Funds Disbursement Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Disburse Funds','C27AB47E6988486CB08F53751F765CCD','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.DisburseFunds',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Drop Period ends','Drop Period ends',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Drop Date','DF3C7C1983E44559A8399139DAF47459','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.DropDate',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('PATCH',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last Day GR to Change Grading Method','Last Day GR to Change Grading Method',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last Day GR to Change Grading Method','AA5CE67D2BA04E5A8916E94F85535D14','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.DropDate1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('PATCH',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day GR to Drop','Last day GR to Drop',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day GR to Drop','F2E505BE3C5446C2803FC3DE2CA9E8C4','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.DropDate2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('PATCH',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day GR to Change Credits','Last day GR to Change Credits',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day GR to Change Credits','7F2BADCE2E4C4D118388D7FEDD56A5D2','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.DropDate3',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('PATCH',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day UG to Drop with a "W"','Last day UG to Drop with a "W"',TO_DATE( '20121113104300', 'YYYYMMDDHH24MISS' ),'Last day UG to Drop with a "W"','0DD5A55DC2354B01B65D42FC0F5B4AE2','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.DropDate4',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Deadline to drop w/o "W"','Deadline to drop w/o "W"',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Drop Deadline Without Record','0FB23F33DEFD4A6A9876BAEA78FAE032','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.DropDeadlineWithoutRecord',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'ElectionDay','ElectionDay',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'ElectionDay','79C3703E98E34AB8AE432508D0944EC6','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.ElectionDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Dates between which Final Exams are held','Dates between which Final Exams are held','Final Examination Period','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.FinalExamPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Financial Aid Census Date','Financial Aid Census Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Financial Aid Census','38340FA50FF249D3B6B5B020931FF1BB','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.FinancialAidCensus',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'FreshmenRegistration','FreshmenRegistration',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'FreshmenRegistration','C7A49AB20B1F419D84C9D3A341A14149','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.FreshmenRegistration',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Bills Generation Date','Bills Generation Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Generate Bills','E2B59CF2812D4185833A7D329C17F4F5','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.GenerateBills',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'GoodFriday','GoodFriday',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'GoodFriday','6BBE807EBB564571B3949C31DBF04860','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.GoodFriday',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Grades Due ','Grades Due ',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Grades Due','C32E76F7B67B4E508AF84FAF163365B2','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.GradesDue',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Dates between which grades can be submitted','Dates between which grades can be submitted','Grading Period','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.GradingPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Deadline to apply for Graduation','Deadline to apply for Graduation',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Graduation Application Deadline','785BD6896C174C24BEF8059C9CED66C6','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.GraduationApplicationDeadline',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Week of activities welcome Alumni back to campus','Week of activities welcome Alumni back to campus','Homecoming Week','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Homecoming',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Independence Day','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.IndependenceDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Independence Day observed','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.IndependenceDayObserved',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Dates between which classes are held','Dates between which classes are held','Instructional period','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.InstructionalPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'JuniorRegistration','JuniorRegistration',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'JuniorRegistration','4DA04C7DC3C54329B9A8C38FC3FD8F81','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.JuniorRegistration',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Labor Day','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.LaborDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Deadline for Last Minute Proposals','Deadline for Last Minute Proposals',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Last Minute Proposals Deadline','6D16F3904FB84EC98AFC412568F38AFB','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.LastMinuteProposalsDeadline',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Begin Leave of Absence option','Begin Leave of Absence option',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Leave of Absense Begin','C553B869C6894C458CE6A9A56A8F919F','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.LeaveofAbsenseBegin',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'MLKDay','MLKDay',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'MLKDay','941575F3E8F1409D93C38C10F34FD957','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.MLKDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Martin Luther King Day observed','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.MLKDayObserved',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Satisfactory Progress Reports/Letters','Satisfactory Progress Reports/Letters',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Mail Progress Reports','DCAF67163ED94D9AA37D1A0CDED7178B','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.MailProgressReports',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Deadline for Proposals with Major Changes','Deadline for Proposals with Major Changes',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Major Changes Deadline','AA8A2FA1B4F44F4FA0CB6B78D528A59A','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.MajorChangesDeadline',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Memorial Day','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.MemorialDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Memorial Day Observed','Memorial Day Observed',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Memorial Day Observed','EC0814F98B1B433088D5176D4F618893','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.MemorialDayObserved',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Deadline for Proposals with Minor Changes','Deadline for Proposals with Minor Changes',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Minor Changes Deadline','BAB10682E5A246B1A3A8BC371CF20F64','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.MinorChangesDeadline',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Move-in Date for Some category of students','Move-in Date for Some category of students',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Move-in Date','11AE10621E7E408FAE1A87D5B680B832','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Move-inDate',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Ceremony to welcome freshmen','Ceremony to welcome freshmen','Freshmen Convocation','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.NewStudentConvocation',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'NewStudentOrientation','NewStudentOrientation',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'NewStudentOrientation','3B4D4900A69043D08466C4D091E88B1A','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.NewStudentOrientation',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'New Year''s Day','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.NewYearsDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'New Year''s Day observed','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.NewYearsDayObserved',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Payment Due Date','Payment Due Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Payment Due','29CEB5B47D4747E0A2C658BE082FCBB0','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.PaymentDue',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Grades Posted','Grades Posted',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Post Grades','02972740246649D998D42A5DBE1C0839','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.PostGrades',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Mid term Grades Posted','Mid term Grades Posted',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Post Grades Midterm','0ABE37AA97BE4BF5A53E1134CB15A5B0','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.PostGradesMidterm',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'President''s Day','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.PresidentsDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Refund Process Date','Refund Process Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Process Refunds','D1BB32CB13A648E7964248D811322D86','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.ProcessRefunds',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Approved Course Changes Published On-Line','Approved Course Changes Published On-Line',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Publish Changes On Line','90DDF9E3DB99494A8FFFB497EC82E700','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.PublishChangesOnLine',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Cancel w/ 100% refund Date','Cancel w/ 100% refund Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Refund 100','F9520F6BBF3542EFABFAADEEE65AF32E','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Refund100',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Cancel w/ 20% refund Date','Cancel w/ 20% refund Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Refund 20','D53D3FE7AF4E4A21B4D68E4F5C4B7EAE','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Refund20',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Cancel w/ 40% refund Date','Cancel w/ 40% refund Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Refund 40','22DB392C0F4148D59B058E7265103E86','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Refund40',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Cancel w/ 50% refund Date','Cancel w/ 50% refund Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Refund 50','975FFF9554444BFBB70B357381ABC9D6','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Refund50',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Cancel w/ 60% refund Date','Cancel w/ 60% refund Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Refund 60','5E5948C7607A425984003C6CC7C01094','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Refund60',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Cancel w/ 80% refund Date','Cancel w/ 80% refund Date',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Refund 80','F822058120AC482384F14AB2D3AA483A','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.Refund80',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Registration begins','Registration begins',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Registration Begins','8EECC40383FF4096ACA6FFF015FD7C10','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationBegins',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'RegistrationBeginsForMBA','RegistrationBeginsForMBA',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'RegistrationBeginsForMBA','12CCBFA9BD4143B6A5F2DAA7BA0DA4CB','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationBeginsForMBA',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Registration for non-degree seeking students','Registration for non-degree seeking students',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Registration Begins Non Degree','A70CAF9742894939B8C016E994F1D744','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationBeginsNonDegree',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Registration for Transfer Students','Registration for Transfer Students',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Registration Begins Transfer','AD09D1C702504E5DA1E6F8448459F532','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationBeginsTransfer',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'MBA Registration begins','MBA Registration begins',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Registration Begins for MBA','E7D00A7CA974407FA531520AFAF05D64','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationBeginsforMBA',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Registration Services Start Date (first date student can start taking care of acknowledgements in advance of registration)','Registration Services Start Date (first date student can start taking care of acknowledgements in advance of registration)','Registration Services Open','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationOpen',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Freshmen','Freshmen','Registration Period 1','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationPeriod1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Sophomores','Sophomores','Registration Period 2','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationPeriod2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Juniors','Juniors','Registration Period 3','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationPeriod3',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Seniors','Seniors','Registration Period 4','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationPeriod4',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Date of first late registration charge for quarter if not registered prior to the first day of the quarter','Date of first late registration charge for quarter if not registered prior to the first day of the quarter','Late Registration Period 1','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationPeriod5',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Date of second late registration charge for quarter if not registered prior to the fifteenth calendar day of the quarter','Date of second late registration charge for quarter if not registered prior to the fifteenth calendar day of the quarter','Late Registration Period 2','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationPeriod6',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Date that late change fee begins (eighth calendar day) for any add/drops/changes in registration.','Date that late change fee begins (eighth calendar day) for any add/drops/changes in registration.','Late Schedule Changes','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RegistrationPeriod7',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Begin Room Scheduling for Next Year','Begin Room Scheduling for Next Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Room Scheduling Begin','F0798B0701044F9080903EFA9EFF98A3','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RoomSchedulingBegin',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'RoshHashanah','RoshHashanah',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'RoshHashanah','FC2026744F3841B5A19286FD49E9DB7B','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.RoshHashanah',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'SeniorRegistration','SeniorRegistration',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'SeniorRegistration','D3375D735D434940BAB4CD76DDA1D075','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.SeniorRegistration',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'SophomoreRegistration','SophomoreRegistration',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'SophomoreRegistration','3D345E36F378432B83096BBF7DC67FAF','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.SophomoreRegistration',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'SpringBreak','SpringBreak',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'SpringBreak','E736B5C32B744CEBA7A840E699563324','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.SpringBreak',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Spring Break Begins','Spring Break Begins',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Spring Break Begins','1D6E63070F8F49E6A60D3A860381AC31','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.SpringBreakBegins',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Spring Break Classes Resume','Spring Break Classes Resume',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Spring Break Ends','D1D5AE0597E544A8B1407524A5931854','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.SpringBreakEnds',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Thanksgiving Break','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.ThanksgivingBreak',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Thanksgiving Break Begins','Thanksgiving Break Begins',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Thanksgiving Break Begin','6AE3F05A8E07417AB795B2668997948F','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.ThanksgivingBreakBegin',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMMSTONEUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Thanksgiving Break ends','Thanksgiving Break ends',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Thanksgiving Break End','5251075D8F374D2E82758B542DC0BCC9','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.ThanksgivingBreakEnd',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Veterans Day','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.VeteransDay',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Veterans Day observed','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.VeteransDayObserved',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120910000000', 'YYYYMMDDHH24MISS' ),'Election Day','Election Day','Election Day','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.electionday',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120801000000', 'YYYYMMDDHH24MISS' ),'End of First Week of Classes','End of First Week of Classes','End of First Week of Classes','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.endoffirstweekofclasses',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120801000000', 'YYYYMMDDHH24MISS' ),'First Day of Classes','First Day of Classes','First Day of Classes','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.firstdayofclasses',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('UMDATPLOADJOB',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'lastdayofclasses','lastdayofclasses',TO_DATE( '20121024191132', 'YYYYMMDDHH24MISS' ),'lastdayofclasses','2D56AC48BB1A48C58A1D3A3867D8D383','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.lastdayofclasses',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120801000000', 'YYYYMMDDHH24MISS' ),'Last Day of Registration','Last Day of Registration','Last Day of Registration','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.lastdayofregistration',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120801000000', 'YYYYMMDDHH24MISS' ),'Month prior to start of classes','Month Prior to Start of Classes','Month Prior to Start of Classes','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.monthpriortostartofclasses',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120910000000', 'YYYYMMDDHH24MISS' ),'Rosh Hashanah','Rosh Hashanah','Rosh Hashanah','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.milestone.roshhashana',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Academic Year','Academic Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'AY','6B7A1A1DBF534E5EBF130D6E824E9BE0','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.AY',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Every other Year Cycle','Every other Year Cycle',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Alternate Years Cycle','DC3D3CB5F9774F24BC4A80220C3107C6','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.AlternateYearsCycle',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Any Time','Any Time',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Any','5378F0807DE04202885E277898B8E43A','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Any',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Even Years','Even Years',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Even Years','18A96883F267479687B66624BE62F3BB','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.EvenYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Fiscal Year','Fiscal Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'FY','1AB2B105B19F413CAAA7840D7FAE2704','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.FY',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Fall','Fall',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Fall','81124CAA22494E7E8801DEC2CF4CC729','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Fall',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Fall & Spring','Fall & Spring',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Fall-Spring','54FCCF8127464FB3B0D86692EA4093ED','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Fall-Spring',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Half of Fall','1st Half of Fall',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Fall 1','B94E2526F3674C56B6CECB418B5C8E04','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Fall1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Half of Fall','2nd Half of Fall',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Fall 2','EC17FDC9B6C043C19B13D6B746BBE92B','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Fall2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Four Year Cycle','Four Year Cycle',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Four Year Cycle','5C7ED56FEBA5431D970368709CE6384A','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.FourYearCycle',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Odd Years','Odd Years',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Odd Years','30F3BDBE9CA34555BFCA9CF38A2CA0EA','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.OddYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Spring','Spring',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Spring','32E8FD6B6FE943D2A9ED0EF04C80A30B','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Spring',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Half of Spring','1st Half of Spring',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Spring 1','56DED993BC2646D4A38F5E1F1FEBAD52','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Spring1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Half of Spring','2nd Half of Spring',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Spring 2','30D0373A2E8B4891B67DA1A714413550','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Spring2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Spring Break','Spring Break',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Spring Break','AA7A0545E0894F9DA4BAE7A73C88D242','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.SpringBreak',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Summer','Summer',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer','04C6F97AF15745D380A647B11B7E9DF4','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Summer',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Half of Summer','1st Half of Summer',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer 1','B8D05E8399D643598BAB07F58177B452','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Summer1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1A of Summer','1A of Summer',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer 1A','4A8441A9923F48E7878A09BFC70CC8AC','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Summer1A',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1B of Summer','1B of Summer',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer 1B','3ACA1C48CE9D4A67B22E43874BF944C1','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Summer1B',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Half of Summer','2nd Half of Summer',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer 2','24A8AF1979CB4E368DE4AB14EC31A796','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Summer2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2C of Summer','2C of Summer',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer 2C','64E908EB7195493EBBE86CA3471115F6','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Summer2C',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2D of Summer','2D of Summer',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer 2D','F3000BD5AC68469D894E1D6F125B9B40','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Summer2D',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Term 1 of Year','Term 1 of Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Term 1','A092C89C59B149CCB866048E8E1C0976','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Term1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Term 2 of Year','Term 2 of Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Term 2','759FC9320B99441EAA4B26539CC0CEEB','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Term2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Winter Activity Period','Winter Activity Period',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Winter','9D339B23F75640DBBB25FF7835D233BC','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Winter',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Year 1 of Program','Year 1 of Program',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Year 1','DDDCAC4D7AC64E5588F22E3EF26B3F6D','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Year1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Year 2 of Program','Year 2 of Program',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Year 2','4E7CDD708BFD432D86F0E5F49B6A08FD','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Year2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Year 3 of Program','Year 3 of Program',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Year 3','E8412789C6A9457DB671F03C0DC9AB38','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Year3',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Year 4 of Program','Year 4 of Program',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Year 4','2938F51317E54001BC22AEBE0C02C5D3','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.season.Year4',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Full Academic Year','Full Academic Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'AY','760485DDB0E64D96A3E5F9783C716401','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.AY',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Academic Calendar','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.AcademicCalendar',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Ad hoc session','Ad hoc session',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Adhoc','B3B443C8D3D1490B9E7F1691CFC9628F','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Adhoc',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Even Numbered Years','Even Numbered Years',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Even Years','E705BB172D14441BAACBA0E7D260AA29','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.EvenYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Fiscal Year','Fiscal Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'FY','5C7BD755F7A24E2F9138C5CAECE2B5AE','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.FY',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Fall Term','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Fall',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Fall Even Years','Fall Even Years',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Fall Even Years','943DEB5A9E0141BEBD32C60F978F4D25','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.FallEvenYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Fall Odd Years','Fall Odd Years',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Fall Odd Years','8E933F06216E408CBC97E5D2C2DF6737','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.FallOddYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Freshman Year','Freshman Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Freshman Year','7729299721D14E55A0C063567781412A','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.FreshmanYear',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Term Freshman','1st Term Freshman',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Freshman Year Term 1','5AE4FCCC8F8B47B492390707CE6FB7F9','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.FreshmanYearTerm1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Term Freshman','2nd Term Freshman',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Freshman Year Term 2','C9C48C6B6B144907876378D0317392EB','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.FreshmanYearTerm2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Half Semester in Fall','1st Half Semester in Fall',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Half Fall 1','5B096FF5512D4BB9B6099C4FD299EC5B','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.HalfFall1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Half Semester in Fall','2nd Half Semester in Fall',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Half Fall 2','A8ABD3E0B2074F639544847441C0C824','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.HalfFall2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Half Semester in Spring','1st Half Semester in Spring',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Half Spring 1','6FB85A62E5EC49A98AA512E7C37ABA23','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.HalfSpring1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Half Semester in Spring','2nd Half Semester in Spring',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Half Spring 2','4C57528196D44353B726A4CF13BAD274','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.HalfSpring2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Holiday Calendar','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.HolidayCalendar',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Junior Year','Junior Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Junior Year','BAAF17D4DFA04465B1EDB3FC5A43CA46','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.JuniorYear',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Term Junior','1st Term Junior',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Junior Year Term 1','9F3E073E4D55415DA8386A2FD2A317F6','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.JuniorYearTerm1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Term Junior','2nd Term Junior',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Junior Year Term 2','4953CA9C447F4B049D51412A6E833D52','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.JuniorYearTerm2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Summer Mini-mester 1A','Summer Mini-mester 1A',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Mini-mester 1A','EA9E1D1CFA8F400980F59848F498AEDF','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Mini-mester1A',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Summer Mini-mester 1B','Summer Mini-mester 1B',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Mini-mester 1B','0E3CA9D453D844718DF63F66D28978B6','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Mini-mester1B',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Summer Mini-mester 2C','Summer Mini-mester 2C',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Mini-mester 2C','BBC049F98DFD4A5980A73013C3101566','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Mini-mester2C',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Summer Mini-mester 2D','Summer Mini-mester 2D',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Mini-mester 2D','0AB6E7FC659047BEAB62B54C361538C8','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Mini-mester2D',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Odd Numbered Years','Odd Numbered Years',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Odd Years','010DEB0398314E6197C260B869414D90','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.OddYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Senior Year','Senior Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Senior Year','207E7E23901C41BBAA41DA6A4893C8B5','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SeniorYear',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Term Senior','1st Term Senior',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Senior Year Term 1','3A0B7A1E8FF0431BB1913983AA760BB3','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SeniorYearTerm1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Term Senior','2nd Term Senior',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Senior Year Term 2','3CCEFCBE0F4841F790C50D51CCBD5EC9','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SeniorYearTerm2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Summer Session','1st Summer Session',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Session 1','903E7055A6AA4BA3B9E696ED38C5AD9F','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Session1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Summer Session','2nd Summer Session',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Session 2','87AAB22EA1E34938BFEC4ADE03131557','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Session2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Grad Summer Session','1st Grad Summer Session',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Session G1','C3B44CE699A44DA69DF9BA9316E06FB0','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SessionG1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Grad Summer Session','2nd Grad Summer Session',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Session G2','F8CF24BB92B8481BB7A8E09249308760','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SessionG2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Sophomore Year','Sophomore Year',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Sophomore Year','467744C4CE7042CE9ADF4DB83889BC7A','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SophomoreYear',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'1st Term Sophomore','1st Term Sophomore',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Sophomore Year Term 1','8872C5153A6640878DB887FAB58DCCC0','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SophomoreYearTerm1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'2nd Term Sophomore','2nd Term Sophomore',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Sophomore Year Term 2','F739E5D46BF04BC392A55943EFF1DF18','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SophomoreYearTerm2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Spring Term','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Spring',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Spring Break Experiential','Spring Break Experiential',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Spring Break','9DA283BEB36C42F9BD7E9F156C46AA69','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SpringBreak',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Spring Even Years','Spring Even Years',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Spring Even Years','626A93FF2507455F82D3E6F8CDB7B44C','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SpringEvenYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Spring Odd Years','Spring Odd Years',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Spring Odd Years','6C4507C51EB949F3BF63B528164CEAF9','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SpringOddYears',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Summer Term','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Summer',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120821000000', 'YYYYMMDDHH24MISS' ),'1st Summer Session','1st Summer Session',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer 1','903E7055A6AA4BA3B9E696ED38C5AD9F','http://student.kuali.org/wsdl/atp/MilestoneInfo','kuali.atp.type.Summer1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120821000000', 'YYYYMMDDHH24MISS' ),'2nd Summer Session','2nd Summer Session',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer 2','87AAB22EA1E34938BFEC4ADE03131557','http://student.kuali.org/wsdl/atp/MilestoneInfo','kuali.atp.type.Summer2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Summer Evening','Summer Evening',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Summer Eve','D0DF697193884F83BC461E832CE6A611','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.SummerEve',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMATPUPGRADE',TO_DATE( '20120912000000', 'YYYYMMDDHH24MISS' ),'Four Year Undergraduate','Four Year Undergraduate',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Undergrad Program','AA88B78ECC634693A3A5733B88FA6F57','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.UndergradProgram',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Winter Term','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.Winter',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The list of Terms that are associated with an academic calendar','The list of Terms that are associated with an academic calendar','Term Group','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.group.term',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'TRUE/FALSE flag indicating this couse should included in the end of term course evaluation process where students evaluate the course','TRUE/FALSE flag indicating this couse should included in the end of term course evaluation process where students evaluate the course','Course Evaluation Indicator','http://student.kuali.org/wsdl/common/AttributeInfo','kuali.attribute.course.evaluation.indicator',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'TRUE/FALSE flag indicating there is a final exam','TRUE/FALSE flag indicating there is a final exam','Final Exam Indicator','http://student.kuali.org/wsdl/common/AttributeInfo','kuali.attribute.final.exam.indicator',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Indicates the source of the funding STATE or SELF','Indicates the source of the funding STATE or SELF','Funding Source','http://student.kuali.org/wsdl/common/AttributeInfo','kuali.attribute.finding.source',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Captures the level at which grade rosters should be built, course offering or format offering or primary activity offering','Captures the level at which grade rosters should be built, course offering or format offering or primary activity offering','Grade Roster Level Type','http://student.kuali.org/wsdl/common/AttributeInfo','kuali.attribute.grade.roster.level.type.key',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'TRUE/FALSE flag indicates the enrollment is an estimate','TRUE/FALSE flag indicates the enrollment is an estimate','Max Enrollment i Estimate','http://student.kuali.org/wsdl/common/AttributeInfo','kuali.attribute.max.enrollment.is.estimate',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Dynamic attribute key used to store the the wait list level type key','Dynamic attribute key used to store the the wait list level type key','Wait List Level Type Key','http://student.kuali.org/wsdl/common/AttributeInfo','kuali.attribute.wait.list.level.type.key',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Indicates where fees are attached to CO or AO flag','Indicates where fees are attached to CO or AO flag','Where Fees Attached Flag','http://student.kuali.org/wsdl/common/AttributeInfo','kuali.attribute.where.fees.attached.flag',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Indicates that the issue has to do with academic progress issues','Academic Progress Issue','http://student.kuali.org/wsdl/type/HoldIssueInfo','http://student.kuali.org/wsdl/hold/HoldService','kuali.hold.issue.type.academic.progress',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Indicates that the issue has to do with academic advising issues','Academic Advising Issue','http://student.kuali.org/wsdl/type/HoldIssueInfo','http://student.kuali.org/wsdl/hold/HoldService','kuali.hold.issue.type.advising',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Indicates that the issue has to do with disciplinary issues','Disciplinary Issue','http://student.kuali.org/wsdl/type/HoldIssueInfo','http://student.kuali.org/wsdl/hold/HoldService','kuali.hold.issue.type.discipline',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Indicates that the issue has to do with financial issues, including non-payment of tuition charges','Financial Issue','http://student.kuali.org/wsdl/type/HoldIssueInfo','http://student.kuali.org/wsdl/hold/HoldService','kuali.hold.issue.type.financial',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Indicates that the issue is that materials borrowed from the library have not been returned or some fine has not been paid','Library Issue','http://student.kuali.org/wsdl/type/HoldIssueInfo','http://student.kuali.org/wsdl/hold/HoldService','kuali.hold.issue.type.library',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'This is a hold that applies to students','Student Hold','http://student.kuali.org/wsdl/type/HoldInfo','http://student.kuali.org/wsdl/hold/HoldService','kuali.hold.type.student',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Advisor to students in the program','Advisor to students in the program','Advisor','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.advisor','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Enrollee in the program','Enrollee in the program','Enrollee','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.enrollee','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Person who administers a test or examination','Person who administers a test or examination','Proctor','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.exam.proctor','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Supervisor','Supervisor','Supervisor','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.instructor.supervisor','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Roster Entry Automatic','Automatic Roster Entry','kuali.lpr.roster.entry.type.automatic','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.roster.entry.type.automatic','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Roster final type','Roster final type','kuali.lpr.roster.type.course.grade.final','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.roster.type.course.grade.final','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Add student to course registration group','Add','kuali.lpr.trans.item.type.add','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.trans.item.type.add','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Drop Type for lpr transactions','Drop','kuali.lpr.trans.item.type.drop','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.trans.item.type.drop','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Student registrant','Student registrant','kuali.lpr.trans.registrant','http://student.kuali.org/wsdl/lpr/LprTransactionInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.trans.registrant','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Register trans type','Register trans type','kuali.lpr.trans.type.register','http://student.kuali.org/wsdl/lpr/LprTransactionInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.trans.type.register','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Person who assists the main instructor but is still considered an "instructor"','Person who assists the main instructor but is still considered an "instructor"','Assistant Instructor','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.type.instructor.assistant','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Main instructor(s) responsible for course or section','Main instructor(s) responsible for course or section','Main Instructor','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.type.instructor.main','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Persons who support the course but not in any official teaching role','Persons who support the course but not in any official teaching role','Support instructor','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.type.instructor.support','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Registrant who is taking the course','Registrant who is taking the course','Registrant','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.type.registrant','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'Final Grade','Final Grade','kuali.lpr.type.roster.grade.final','http://student.kuali.org/wsdl/lpr/LprInfo','http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.type.roster.grade.final','TESTUSER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'An course offered for academic credit','An course offered for academic credit','Credit Course','http://student.kuali.org/wsdl/course/CourseInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.CreditCourse',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'A shell for course formats','A shell for course formats','Credit Course Format','http://student.kuali.org/wsdl/course/FormatInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.CreditCourseFormatShell',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The exchange of opinions or questions on course material, directed by the instructor.','The exchange of opinions or questions on course material, directed by the instructor.','Directed','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.Directed',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The exchange of opinions or questions on course material.','The exchange of opinions or questions on course material.','Discussion','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.Discussion',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Experiential Learning or Other','Experiential Learning or Other','Experiential Learning or Other','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.ExperientialLearningOROther',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Students doing homework, problem sets and reading assignments and writing','Students doing homework, problem sets and reading assignments and writing','Homework','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.Homework',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student working on projects in a defined laboratory space. Instructors are on-hand for students to ask questions and guidance.','Student working on projects in a defined laboratory space. Instructors are on-hand for students to ask questions and guidance.','Lab','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.Lab',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Instructor presentation of course materials.','Instructor presentation of course materials.','Lecture','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.Lecture',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Lecture or Seminar','Lecture or Seminar','Lecture or Seminar','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.LectureORSeminar',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Instructor or assistant walking through a learning practice.','Instructor or assistant walking through a learning practice.','Tutorial','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.Tutorial',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Web-based or technologically-mediated activities replacing standard discussion sections','Web-based or technologically-mediated activities replacing standard discussion sections','Web Discuss','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.WebDiscussion',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Instructor presentation of course materials via the web','Instructor presentation of course materials via the web','Web Lecture','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.WebLecture',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student works as a clerk for a working professional.','Student works as a clerk for a working professional.','Clerkship','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.clerkship',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student participates in both observing and executing health care delivery in a clinic setting.','Student participates in both observing and executing health care delivery in a clinic setting.','Clinic','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.clinic',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student attend a conference and optionally present their research results.','Student attend a conference and optionally present their research results.','Conference','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.conference',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student works independently with guidance from instructors.','Student works independently with guidance from instructors.','Independent Study','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.independentstudy',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student has supervised practical application of theory similar to an junior professional in a professional setting.','Student has supervised practical application of theory similar to an junior professional in a professional setting.','Practicum','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.practicum',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Discussion of course material, quizzes, tests and other exercises.','Discussion of course material, quizzes, tests and other exercises.','Quiz','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.quiz',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student participate in discussing specific topics in small groups with faculty.','Student participate in discussing specific topics in small groups with faculty.','Seminar','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.seminar',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student working on projects in a defined studio space. Instructors are on-hand for students to ask questions and guidance.','Student working on projects in a defined studio space. Instructors are on-hand for students to ask questions and guidance.','Studio','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.activity.studio',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'All of these types of activities: Lecture, Lab, Discussion, etc.','All of these types of activities: Lecture, Lab, Discussion, etc.','All Activity Types','http://student.kuali.org/wsdl/course/ActivityInfo','http://student.kuali.org/wsdl/clu/CluService','kuali.lu.type.grouping.activity',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120814000000', 'YYYYMMDDHH24MISS' ),'LuiLui relation beween course offering and format offering','LuiLui relation beween course offering and format offering','CO-to-FO relation','http://student.kuali.org/wsdl/lui/LuiLuiRelationInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.lui.relation.type.deliveredvia.co2fo',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120814000000', 'YYYYMMDDHH24MISS' ),'LuiLui relation beween format offering and activity offering','LuiLui relation beween format offering and activity offering','FO-to-AO relation','http://student.kuali.org/wsdl/lui/LuiLuiRelationInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.lui.relation.type.deliveredvia.fo2ao',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120814000000', 'YYYYMMDDHH24MISS' ),'LuiLui relation beween format offering and registration group','LuiLui relation beween format offering and registration group','FO-to-RG relation','http://student.kuali.org/wsdl/lui/LuiLuiRelationInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.lui.relation.type.deliveredvia.fo2rg',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120814000000', 'YYYYMMDDHH24MISS' ),'LuiLui relation beween registration group and activity offering','LuiLui relation beween registration group and activity offering','RG-to-AO relation','http://student.kuali.org/wsdl/lui/LuiLuiRelationInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.lui.relation.type.registeredforvia.rg2ao',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Experiential Learning or Other','Experiential Learning or Other','Experiential Learning or Other','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.ExperientialLearningOROther',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Lecture or Seminar','Lecture or Seminar','Lecture or Seminar','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.LectureORSeminar',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Physical activity and participation are incorporated into theýý class.','Physical activity and participation are incorporated into theýý class.','Activity','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.activity',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student works as a clerk for a working professional.','Student works as a clerk for a working professional.','Clerkship','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.clerkship',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student participates in both observing and executing health care delivery in a clinic setting.','Student participates in both observing and executing health care delivery in a clinic setting.','Clinic','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.clinic',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'A seminar led by different instructors','A seminar led by different instructors','Colloquium','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.colloquium',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Materials are available inýý electronic format for the student toýý explore on their own with no or minimalýýýý involvement by faculty','Materials are available inýý electronic format for the student toýý explore on their own with no or minimalýýýý involvement by faculty','Comp Based','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.compbased',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student attend a conference and optionally present their research results.','Student attend a conference and optionally present their research results.','Conference','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.conference',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Correspondence','Correspondence','Correspond','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.correspond',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student observation of an instructional display or performance','Student observation of an instructional display or performance','Demonstration','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.demonstration',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Provides students with a way to complete aý class orýý other area of focused studies.ý Directed study may be individualized method or in a group.','Provides students with a way to complete aý class orýý other area of focused studies.ý Directed study may be individualized method or in a group.','Directed','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.directed',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The exchange of opinions or questions on course material','The exchange of opinions or questions on course material','Discussion','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.discussion',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Instructional activity in non-classroom settings','Instructional activity in non-classroom settings','Field','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.field',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student''s doing homework, problem sets and reading assignments andýý writing','Student''s doing homework, problem sets and reading assignments andýý writing','Homework','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.homework',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student-initiated educational activity. Supervised special projectýý undertaken with the direction of anýýýý assigned faculty member.','Student-initiated educational activity. Supervised special projectýý undertaken with the direction of anýýýý assigned faculty member.','Independ','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.independ',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Individual activity inýý authentic non-academic settingýý arranged by instructor','Individual activity inýý authentic non-academic settingýý arranged by instructor','Internship','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.internship',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student working on projects in a defined laboratory space.ýýý Instructorsýý are on-hand for students to ask questions and guidance.','Student working on projects in a defined laboratory space.ýýý Instructorsýý are on-hand for students to ask questions and guidance.','Lab','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.lab',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Instructor presentation of course materials','Instructor presentation of course materials','Lecture','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.lecture',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student has supervised practical application of theory similar to an junior professional in a professional setting.','Student has supervised practical application of theory similar to an junior professional in a professional setting.','Practicum','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.practicum',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Individually scheduled classes, typically music.','Individually scheduled classes, typically music.','Private','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.private',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Discussion of course material, quizzes, tests and other exercises.','Discussion of course material, quizzes, tests and other exercises.','Quiz','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.quiz',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Oral review of course materialýý by students, often in small groups andýý often lead by TAs','Oral review of course materialýý by students, often in small groups andýý often lead by TAs','Recitation','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.recitation',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Research','Research','Research','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.research',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student-paced coverage,ýý usuallyýý with individualizedýý attention, of assigned course material','Student-paced coverage,ýý usuallyýý with individualizedýý attention, of assigned course material','Self Paced','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.selfpaced',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student participate in discussing specific topics in small groups with faculty.','Student participate in discussing specific topics in small groups with faculty.','Seminar','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.seminar',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student practice of studio skills and/or tasks','Student practice of studio skills and/or tasks','Studio','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.studio',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Supplementary (or remedial) individualized instruction, Extra meetings for the review or elaboration of course materials','Supplementary (or remedial) individualized instruction, Extra meetings for the review or elaboration of course materials','Tutorial','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.tutorial',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Often interactive instructionýý between campuses delivered throughýý closed-circuit TV cameras, screens andýýýý microphones. Students are able to interact as if they were in theýý sameýý classroom.','Often interactive instructionýý between campuses delivered throughýý closed-circuit TV cameras, screens andýýýý microphones. Students are able to interact as if they were in theýý sameýý classroom.','Video Conf','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.videoconf',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Web-based or technologically-mediated activitiesýý replacing standard discussion sections','Web-based or technologically-mediated activitiesýý replacing standard discussion sections','Web Discuss','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.webdiscussion',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Web-based orýý technologically-mediated activitiesýý replacing standard lectures','Web-based orýý technologically-mediated activitiesýý replacing standard lectures','Web Lecture','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.activity.offering.weblecture',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Used to collect tightly related course offerings into bundles that all have to be taken together','Used to collect tightly related course offerings into bundles that all have to be taken together','Course Bundle','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.course.bundle',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The offering of a course in a particular format for a term','The offering of a course in a particular format for a term','Course Format Offering','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.course.format.offering',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'An offering of a course for a particular term','An offering of a course for a particular term','Course Offering','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.course.offering',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'All of these types of activity offerings: Lecture, Lab, Discussion, etc.','All of these types of activity offerings: Lecture, Lab, Discussion, etc.','All Activity Offering Types','http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.grouping.activity',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120917000000', 'YYYYMMDDHH24MISS' ),'The collection of activity offerings of a single course that are grouped together for registration purposes','The collection of activity offerings of a single course that are grouped together for registration purposes','Registration Group','http://student.kuali.org/wsdl/lui/LuiInfo','http://student.kuali.org/wsdl/lui/LuiService','kuali.lui.type.registration.group',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The list of milestone types that are used for Registration Key Dates in Registration Appointments','The list of milestone types that are used for Registration Key Dates in Registration Appointments','Registration Periods for Registration Appointments','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.milestone.type.group.appt.regperiods',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The list of milestone types that are used for academic calendar events','The list of milestone types that are used for academic calendar events','Event Group','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.milestone.type.group.event',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The list of milestone types that are allowed for holidays','The list of milestone types that are allowed for holidays','Holiday Group','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.milestone.type.group.holiday',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The list of milestone types that are used for Key Dates that are associated with a term','The list of milestone types that are used for Instructional Key Dates that are associated with a term','Instructional','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.milestone.type.group.instructional',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The list of milestone types that are used for Key Dates that are associated with a term','The list of milestone types that are used for Registration Key Dates that are associated with a term','Registration','http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.milestone.type.group.registration',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120801000000', 'YYYYMMDDHH24MISS' ),'Grouping type for seatpool milestone types','Grouping type for seatpool milestone types','Grouping type for seatpool milestone types','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.milestone.type.group.seatpool',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Academic Administrative Support (Organization Type)','Academic Administrative Support (Organization Type)','Academic Administrative Support','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.AcademicAdministrativeSupport',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Academic Instructional Research Support (Organization Type)','Academic Instructional Research Support (Organization Type)','Academic Instructional Research Support','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.AcademicInstructionalResearchSupport',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Academic Outreach (Organization Type)','Academic Outreach (Organization Type)','Academic Outreach','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.AcademicOutreach',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Accrediting Body (Organization Type)','Accrediting Body (Organization Type)','Accrediting Body','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.AccreditingBody',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Advisory Group (Organization Type)','Advisory Group (Organization Type)','Advisory Group','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.AdvisoryGroup',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Campus (Organization Type)','Campus (Organization Type)','Campus','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.Campus',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Center (Organization Type)','Center (Organization Type)','Center','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.Center',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121204000000', 'YYYYMMDDHH24MISS' ),'College (Organization Type)','College (Organization Type)','College','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.College',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Committee (Organization Type)','Committee (Organization Type)','Committee','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.Committee',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121205000000', 'YYYYMMDDHH24MISS' ),'Department (Organization Type)','Department (Organization Type)','Department','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.Department',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Division (Organization Type)','Division (Organization Type)','Division','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.Division',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Institution (Organization Type)','Institution (Organization Type)','Institution','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.Institution',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Program (Organization Type)','Program (Organization Type)','Program','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.Program',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'School (Organization Type)','School (Organization Type)','School','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.School',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Senate (Organization Type)','Senate (Organization Type)','Senate','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.Senate',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'SubDepartment (Organization Type)','SubDepartment (Organization Type)','SubDepartment','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.SubDepartment',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'Testing Service (Organization Type)','Testing Service (Organization Type)','Testing Service','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.TestingService',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121220000000', 'YYYYMMDDHH24MISS' ),'University Support (Organization Type)','University Support (Organization Type)','University Support','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.UniversitySupport',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'A list of populations (childPopulationIds) and the reference population (referencePopulationId) must be supplied; results of the reference populations (e.g., all students) less the populations represented in the list (not in) determine the membership','A list of populations (childPopulationIds) and the reference population (referencePopulationId) must be supplied; results of the reference populations (e.g., all students) less the populations represented in the list (not in) determine the membership','Exclusion','http://student.kuali.org/wsdl/population/PopulationRuleInfo','http://student.kuali.org/wsdl/population/PopulationService','kuali.population.rule.type.exclusion',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'A list of populations (childPopulationIds) must be supplied; results of the intersection of the populations determine the membership','A list of populations (childPopulationIds) must be supplied; results of the intersection of the populations determine the membership','Intersection','http://student.kuali.org/wsdl/population/PopulationRuleInfo','http://student.kuali.org/wsdl/population/PopulationService','kuali.population.rule.type.intersection',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'Membership in the population is based entirely on someone manually inserting and removing people or groups (KIM) into and out of the population','Membership in the population is based entirely on someone manually inserting and removing people or groups (KIM) into and out of the population','Explicitly defined','http://student.kuali.org/wsdl/population/PopulationRuleInfo','http://student.kuali.org/wsdl/population/PopulationService','kuali.population.rule.type.person',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'An agenda id must be supplied which calculates the membership','An agenda id must be supplied which calculates the membership','Rule-based','http://student.kuali.org/wsdl/population/PopulationRuleInfo','http://student.kuali.org/wsdl/population/PopulationService','kuali.population.rule.type.rule',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'A searchCriteria must be supplied; results of the search determine the membership','A searchCriteria must be supplied; results of the search determine the membership','Search-based','http://student.kuali.org/wsdl/population/PopulationRuleInfo','http://student.kuali.org/wsdl/population/PopulationService','kuali.population.rule.type.search',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'A list of populations (childPopulationIds) must be supplied; results of the union of the populations determine the membership','A list of populations (childPopulationIds) must be supplied; results of the union of the populations determine the membership','Union','http://student.kuali.org/wsdl/population/PopulationRuleInfo','http://student.kuali.org/wsdl/population/PopulationService','kuali.population.rule.type.union',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'Membership in the population is comprised of people that are students','Membership in the population is comprised of people that are students','Student','http://student.kuali.org/wsdl/population/PopulationInfo','http://student.kuali.org/wsdl/population/PopulationService','kuali.population.type.student',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'A certification that a student completed the requirements for a particular area of study that results in a paper certificate being awarded','A certification that a student completed the requirements for a particular area of study that results in a paper certificate being awarded','Certificate','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.certificate',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Attestation that a student completed the requirements for a certification for a particular job or profession','Attestation that a student completed the requirements for a certification for a particular job or profession','Certification','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.certification',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Credit that is awarded for completion of a course','Credit that is awarded for completion of a course','Credit','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.credit',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Degree awarded for the completion of a particular program of study','Degree awarded for the completion of a particular program of study','Degree','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.degree',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Grade Point Average','Grade Point Average','GPA','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.gpa',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The result of an assessment, typically of a course','The result of an assessment, typically of a course','Grade','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.grade',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Grades that are not really the result of assessments but rather indicate administrative issue affecting the assignment of the grade.  For example an I for an incomplete grade','Grades that are not really the result of assessments but rather indicate administrative issue affecting the assignment of the grade.  For example an I for an incomplete grade','Admin Grade','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.grade.admin',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'An honor that is awarded to a student','An honor that is awarded to a student','Honor','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.honor',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Indicates the completion of a smaller area of study in conjunction with a degree','Indicates the completion of a smaller area of study in conjunction with a degree','Minor','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.minor',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Indicates the completion or lack thereof or the amount of progress towards the completion.  Often applied to requirements','Indicates the completion or lack thereof or the amount of progress towards the completion.  Often applied to requirements','Completion','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.requirement.completion',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Student''s Year of Study, often within a program i.e. Freshman, Sophomore, Junior, Senior','Student''s Year of Study, often within a program i.e. Freshman, Sophomore, Junior, Senior','Student Year','http://student.kuali.org/wsdl/lrc/ResultScaleInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.scale.type.student.year',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'A result value','A result value','Value','http://student.kuali.org/wsdl/lrc/ResultValueInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.value.type.value',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'A result grouping that includes just a single result value ','A result grouping that includes just a single result value ','Fixed','http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.values.group.type.fixed',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Multiple explicitly named values from which one may be choosen','Multiple explicitly named values from which one may be choosen','Multiple','http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.values.group.type.multiple',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'A grouping that indicates a range of numeric values with a specified increment.  It is not necessary that all of the values within that range have been explicitly pre-defined ','A grouping that indicates a range of numeric values with a specified increment.  It is not necessary that all of the values within that range have been explicitly pre-defined ','Range','http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.result.values.group.type.range',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMLRCUPGRADE',TO_DATE( '20120917000000', 'YYYYMMDDHH24MISS' ),'This records an awarded certificate','This records an awarded certificate',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Certificate','BFE500B4290445B3B0D09A14D1AFE040','http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.resultComponentType.certificate',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120821000000', 'YYYYMMDDHH24MISS' ),'This records a single fixed number of credits that are awarded if the student passes the course.','This records a single fixed number of credits that are awarded if the student passes the course.',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Fixed','7AECF0492B24496CA7FCDA083095E917','http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo','kuali.resultComponentType.credit.degree.fixed',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120821000000', 'YYYYMMDDHH24MISS' ),'This records a range of number of credits that can be awarded for this course.','This records a range of number of credits that can be awarded for this course.',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Variable','E3822A13EC1A42AF9305EEE1B3224FAB','http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo','kuali.resultComponentType.credit.degree.range',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMLRCUPGRADE',TO_DATE( '20120917000000', 'YYYYMMDDHH24MISS' ),'This records a degree is awarded if the student completes the program','This records a degree is awarded if the student completes the program',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Degree','0E192F1BA58149169AFB1C4A53546B64','http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.resultComponentType.degree',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMLRCUPGRADE',TO_DATE( '20120917000000', 'YYYYMMDDHH24MISS' ),'This records the overall GPA of a student in a program ','This records the overall GPA of a student in a program ',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Overall GPA ','A905E79D8BAC4A04A2610013490182AD','http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.resultComponentType.gpa',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,NAME,OBJ_ID,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('CMLRCUPGRADE',TO_DATE( '20120917000000', 'YYYYMMDDHH24MISS' ),'This records that a final grade is a result for this course','This records that a final grade is a result for this course',TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Final Grade','93923DA1E3874217B817D09C1BF3F20F','http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo','http://student.kuali.org/wsdl/lrc/LrcService','kuali.resultComponentType.grade.finalGrade',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'Building (Facility Type)','Building (Facility Type)','Building','http://student.kuali.org/wsdl/room/RoomInfo','http://student.kuali.org/wsdl/room/RoomService','kuali.room.facility.type.building',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'Partition (Facility Type))','Partition (Facility Type)','Partition','http://student.kuali.org/wsdl/room/RoomInfo','http://student.kuali.org/wsdl/room/RoomService','kuali.room.facility.type.partition',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'Building (Facility Type)','Region (Facility Type)','Region','http://student.kuali.org/wsdl/room/RoomInfo','http://student.kuali.org/wsdl/room/RoomService','kuali.room.facility.type.region',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'Scheduling (Org Room Responsibility Type)','Scheduling (Org Room Responsibility Type)','Scheduling','http://student.kuali.org/wsdl/room/RoomInfo','http://student.kuali.org/wsdl/room/RoomService','kuali.room.org.responsibility.scheduling',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'Classroom (Room Type)','Classroom (Room Type)','Classroom','http://student.kuali.org/wsdl/room/RoomInfo','http://student.kuali.org/wsdl/room/RoomService','kuali.room.type.classroom',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'General Classroom (Room Type)','General Classroom (Room Type)','General Classroom','http://student.kuali.org/wsdl/room/RoomInfo','http://student.kuali.org/wsdl/room/RoomService','kuali.room.type.classroom.general',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120813000000', 'YYYYMMDDHH24MISS' ),'Schedule Request','Schedule Request','Schedule Request','http://student.kuali.org/wsdl/scheduling/ScheduleRequestInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.schedule.request.type.schedule.request',1)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120904000000', 'YYYYMMDDHH24MISS' ),'Time slots that apply to scheduling classrooms for activity offerings','Time slots that apply to scheduling classrooms for activity offerings','Activity Offering','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120904000000', 'YYYYMMDDHH24MISS' ),'Time slots that apply to scheduling classrooms for activity offerings but do not have a day or time. These time slots indicate a TBA.','Time slots that apply to scheduling classrooms for activity offerings but do not have a day or time. These time slots indicate a TBA.','Activity Offering TBA','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering.tba',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The results of trying to reverse a rollover','The results of trying to reverse a rollover','Reverse of a Rollover','http://student.kuali.org/wsdl/courseOfferingSet/SocRolloverResultInfo','http://student.kuali.org/wsdl/courseOfferingSet/CourseOfferingSetService','kuali.soc.rollover.results.type.reverse',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'The results are of a rollover','The results are of a rollover','Rollover Results','http://student.kuali.org/wsdl/courseOfferingSet/SocRolloverResultInfo','http://student.kuali.org/wsdl/courseOfferingSet/CourseOfferingSetService','kuali.soc.rollover.results.type.rollover',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'SOC that holds all of a department''s course offerings for a term','SOC that holds all of a department''s course offerings for a term','Departmental','http://student.kuali.org/wsdl/courseOfferingSet/SocInfo','http://student.kuali.org/wsdl/courseOfferingSet/CourseOfferingSetService','kuali.soc.type.departmental',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Main SOC that holds all the course offerings for the specified term','Main SOC that holds all the course offerings for the specified term','Main','http://student.kuali.org/wsdl/courseOfferingSet/SocInfo','http://student.kuali.org/wsdl/courseOfferingSet/CourseOfferingSetService','kuali.soc.type.main',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'SOC that holds all the course offerings for a particular subject area','SOC that holds all the course offerings for a particular subject area','Subject Area','http://student.kuali.org/wsdl/courseOfferingSet/SocInfo','http://student.kuali.org/wsdl/courseOfferingSet/CourseOfferingSetService','kuali.soc.type.subject.area',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'SOC that holds all the course offerings who''s content is owned by a particular department','SOC that holds all the course offerings who''s content is owned by a particular department','Units Content Owner','http://student.kuali.org/wsdl/courseOfferingSet/SocInfo','http://student.kuali.org/wsdl/courseOfferingSet/CourseOfferingSetService','kuali.soc.type.units.content.owner',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'SOC that holds all the course offerings who''s deployment is controlled (owned) by a particular department','SOC that holds all the course offerings who''s deployment is controlled (owned) by a particular department','Units Deployment Owner','http://student.kuali.org/wsdl/courseOfferingSet/SocInfo','http://student.kuali.org/wsdl/courseOfferingSet/CourseOfferingSetService','kuali.soc.type.units.deployment.owner',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120910000000', 'YYYYMMDDHH24MISS' ),'State Change Type','State Change Type','State Change Type','http://student.kuali.org/wsdl/state/StateChangeInfo','http://student.kuali.org/wsdl/state/StateService','kuali.state.change.type',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120910000000', 'YYYYMMDDHH24MISS' ),'State constraint precondition Type','State constraint precondition Type','State constraint precondition Type','http://student.kuali.org/wsdl/state/StateConstraintInfo','http://student.kuali.org/wsdl/state/StateService','kuali.state.constraint.type.precondition',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120910000000', 'YYYYMMDDHH24MISS' ),'State constraint propagation Type','State constraint propagation Type','State constraint propagation Type','http://student.kuali.org/wsdl/state/StateConstraintInfo','http://student.kuali.org/wsdl/state/StateService','kuali.state.constraint.type.propagation',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120910000000', 'YYYYMMDDHH24MISS' ),'State Propagation Type','State Propagation Type','State Propagation Type','http://student.kuali.org/wsdl/state/StatePropagationInfo','http://student.kuali.org/wsdl/state/StateService','kuali.state.propagation.type',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Type specifies which types can go together','Type specifies which types can go together','Allowed Type Type Relation','http://student.kuali.org/wsdl/type/TypeTypeRelationInfo','http://student.kuali.org/wsdl/type/TypeService','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Types grouped together','Types grouped together','Group Type Type Relation','http://student.kuali.org/wsdl/type/TypeTypeRelationInfo','http://student.kuali.org/wsdl/type/TypeService','kuali.type.type.relation.type.group',0)
/
