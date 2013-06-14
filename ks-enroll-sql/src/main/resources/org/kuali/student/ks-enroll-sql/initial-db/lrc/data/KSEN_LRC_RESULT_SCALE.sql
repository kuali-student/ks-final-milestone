TRUNCATE TABLE KSEN_LRC_RESULT_SCALE DROP STORAGE
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Certificate of Advanced Graduate Studies','Certificate of Advanced Graduate Studies','kuali.result.scale.certificate.graduate','Graduate Certificate','kuali.result.scale.state.approved','kuali.result.scale.type.certificate',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Certification For example Nursing certificate','Certification For example Nursing certificate','kuali.result.scale.certification','Certification','kuali.result.scale.state.approved','kuali.result.scale.type.certification',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RANGE_INCREMENT,RANGE_MAX_VALUE,RANGE_MIN_VALUE,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Credit awarded for continuing education programs. Does not count towards any higher ed degree but may count towards certifications','Credit awarded for continuing education programs. Does not count towards any higher ed degree but may count towards certifications','kuali.result.scale.credit.ceu','Continuing Education Units','1','100','0','kuali.result.scale.state.approved','kuali.result.scale.type.credit',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RANGE_INCREMENT,RANGE_MAX_VALUE,RANGE_MIN_VALUE,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Regular Academic Credit','Regular Academic Credit','kuali.result.scale.credit.degree','Degree Credit','1','100','0','kuali.result.scale.state.approved','kuali.result.scale.type.credit',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RANGE_INCREMENT,RANGE_MAX_VALUE,RANGE_MIN_VALUE,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Credit associated with remedial credit that typically does not count towards a degree','Credit associated with remedial credit that typically does not count towards a degree','kuali.result.scale.credit.remedial','Remedial Credit','1','100','0','kuali.result.scale.state.approved','kuali.result.scale.type.credit',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Types of degrees awarded by the school','Types of degrees awarded by the school','kuali.result.scale.degree','Degree Types','kuali.result.scale.state.approved','kuali.result.scale.type.degree',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RANGE_INCREMENT,RANGE_MAX_VALUE,RANGE_MIN_VALUE,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'4.0 grade point average rounded to the nearest tenth','4.0 grade point average rounded to the nearest tenth','kuali.result.scale.gpa','Grade Point Average','0.1','4','0','kuali.result.scale.state.approved','kuali.result.scale.type.gpa',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RANGE_INCREMENT,RANGE_MAX_VALUE,RANGE_MIN_VALUE,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'4.0 grade point average rounded to the nearest hundredth, typically used for reporting to medical schools','4.0 grade point average rounded to the nearest hundredth, typically used for reporting to medical schools','kuali.result.scale.gpa.two.decimals','Grade Point Average to 2 Decimals','0.01','4','0','kuali.result.scale.state.approved','kuali.result.scale.type.gpa',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Grades used for administrative reasons -- not directly tied to academic evaluation','Grades used for administrative reasons -- not directly tied to academic evaluation','kuali.result.scale.grade.admin','Administrative Grades','kuali.result.scale.state.approved','kuali.result.scale.type.grade.admin',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Notation that simply indicates the work was completed','Notation that simply indicates the work was completed','kuali.result.scale.grade.completed','Completed Notation','kuali.result.scale.state.approved','kuali.result.scale.type.grade',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Standard A-F grading scale','Standard A-F grading scale','kuali.result.scale.grade.letter','Letter','kuali.result.scale.state.approved','kuali.result.scale.type.grade',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Standard A-F grading but with plus and minuses','Standard A-F grading but with plus and minuses','kuali.result.scale.grade.letter.plus.minus','A-F with Plus/Minus Grading Scale','kuali.result.scale.state.approved','kuali.result.scale.type.grade',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Indicates that a narravite description is required instead of a coded grade','Indicates that a narravite description is required instead of a coded grade','kuali.result.scale.grade.narrative','Narrative Grade','kuali.result.scale.state.approved','kuali.result.scale.type.grade',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RANGE_INCREMENT,RANGE_MAX_VALUE,RANGE_MIN_VALUE,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Percentage grading','Percentage grading','kuali.result.scale.grade.percentage','Percentage Grading Scale','1','100','0','kuali.result.scale.state.approved','kuali.result.scale.type.grade',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Pass/Fail grading scale','Pass/Fail grading scale','kuali.result.scale.grade.pf','Pass/Fail Grading Scale','kuali.result.scale.state.approved','kuali.result.scale.type.grade',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Pass/No Pass Grading scale (also called pass/no record because failing does not hurt your GPA)','Pass/No Pass Grading scale (also called pass/no record because failing does not hurt your GPA)','kuali.result.scale.grade.pnp','Pass/No Pass Grading Scale','kuali.result.scale.state.approved','kuali.result.scale.type.grade',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'No formal grade but a qualitative written assessment is typically recorded','No formal grade but a qualitative written assessment is typically recorded','kuali.result.scale.grade.review','Qualitative Review (Narrative)','kuali.result.scale.state.approved','kuali.result.scale.type.grade',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Used to flag students who are at risk of failing or otherwise not doing well in the course. Often used for midterm grading.','Used to flag students who are at risk of failing or otherwise not doing well in the course. Often used for midterm grading.','kuali.result.scale.grade.risk.flag','At Risk Flag','kuali.result.scale.state.approved','kuali.result.scale.type.grade',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'An honor is awarded periodically that identifies the top portion of the class by GPA','An honor is awarded periodically that identifies the top portion of the class by GPA','kuali.result.scale.honor.deans.list','Deans List','kuali.result.scale.state.approved','kuali.result.scale.type.honor',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'An honor awarded with the degree for example, Cum Laud, Magna Cum Laud and Summa Cum Laud','An honor awarded with the degree for example, Cum Laud, Magna Cum Laud and Summa Cum Laud','kuali.result.scale.honor.degree.latin','Latin Degree Honors','kuali.result.scale.state.approved','kuali.result.scale.type.honor',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Minor area of study','Minor area of study','kuali.result.scale.minor','Minor','kuali.result.scale.state.approved','kuali.result.scale.type.minor',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Indicates whether or not a requirement for a student has been completed','Indicates whether or not a requirement for a student has been completed','kuali.result.scale.requirement.completion','Requirement Completion Status','kuali.result.scale.state.approved','kuali.result.scale.type.requirement.completion',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Indicates the percentage of the requirement the student has completed','Indicates the percentage of the requirement the student has completed','kuali.result.scale.requirement.completion.percent','Requirement Completion Percent','kuali.result.scale.state.approved','kuali.result.scale.type.requirement.completion',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'Students Year of Study from undergrad through graduate school, i.e. freshman, sophomore, junior, etc','Students Year of Study from undergrad through graduate school, i.e. freshman, sophomore, junior, etc','kuali.result.scale.student.year','Student Year or Level','kuali.result.scale.state.approved','kuali.result.scale.type.student.year',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'The student year of graduate students','The student year of graduate students','kuali.result.scale.student.year.grad','Graduate Student Year','kuali.result.scale.state.approved','kuali.result.scale.type.student.year',0)
/
INSERT INTO KSEN_LRC_RESULT_SCALE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,RESULT_SCALE_STATE,RESULT_SCALE_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120622000000', 'YYYYMMDDHH24MISS' ),'The student year for lock step programs such as Law School','The student year for lock step programs such as Law School','kuali.result.scale.student.year.lockstep','Student Year for Lock Step Program','kuali.result.scale.state.approved','kuali.result.scale.type.student.year',0)
/
