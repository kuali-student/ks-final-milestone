
///////////////////////////////////////////////////////////////////////////
//States needed for LRC
///////////////////////////////////////////////////////////////////////////
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.values.group.state.draft','Draft','The result is just draft and cannot yet be used','kuali.result.values.group.process')
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.values.group.state.approved','Approved','The result has been approved to be used and awarded','kuali.result.values.group.process')
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.values.group.state.retired','The result has been retired and can still exist on records but can no longer be used','Retired','kuali.result.values.group.process')
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.scale.state.draft ','Draft','The result scale is just draft and cannot yet be used','kuali.result.scale.process')
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.scale.state.approved','Approved','The result scale has been approved to be used and awarded','kuali.result.scale.process')
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.scale.state.retired','Retired','The result scale has been retired and can still exist on records but can no longer be used','kuali.result.scale.process')
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.value.state.draft ','Draft','The result value is just draft and cannot yet be used','kuali.result.value.process')
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.value.state.approved','Approved','The result value has been approved to be used and awarded','kuali.result.value.process')
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.value.state.retired','Retired','The result value has been retired and can still exist on records but can no longer be used','kuali.result.value.process')

///////////////////////////////////////////////////////////////////////////
//LRC types
///////////////////////////////////////////////////////////////////////////
INSERT INTO KSEN_LRC_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.result.scale.grade.letter',1,'Standard A-F grading scale','A-F Grading Scale')
INSERT INTO KSEN_LRC_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.result.values.group.type.fixed',1,'A result grouping that includes just a single result value','Fixed')
INSERT INTO KSEN_LRC_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.result.values.group.type.range',1,'A grouping that indicates a range of numeric values with a specified increment. It is not necessary that all of the values within that range have been explicitly pre-defined','Range')
INSERT INTO KSEN_LRC_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.result.values.group.type.multiple',1,'Multiple explicitly named values from which one may be choosen','Multiple')
INSERT INTO KSEN_LRC_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.result.scale.type.grade',1,'The result of an assessment, typically of a course','Grade')
INSERT INTO KSEN_LRC_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.result.value.type.value',1,'The result value','Value')

///////////////////////////////////////////////////////////////////////////
//LRC descriptions
///////////////////////////////////////////////////////////////////////////
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-111', '<p>Standard A-F Grading used by most courses and how most courses are shown on the external transcript</p>', 'Standard A-F Grading used by most courses and how most courses are shown on the external transcript',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-221', '<p>Standard A-F grading scale</p>','Standard A-F grading scale',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-222', '<p>Excellent</p>','Excellent',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-223', '<p>Good</p>','Good',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-224', '<p>Average</p>','Average',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-225', '<p>Below Average</p>','Below Average',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-226', '<p>Failing</p>','Failing',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-227', '<p>Pass/Fail grading scale</p>','Pass/Fail grading scale',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-228', '<p>Pass/Fail grading used for courses that are only taught that way</p>','Pass/Fail grading used for courses that are only taught that way',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-229', '<p>Pass</p>','Pass',0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-230', '<p>Fail</p>','Fail',0)

///////////////////////////////////////////////////////////////////////////
//BEGIN - Entries for A-F Grading
///////////////////////////////////////////////////////////////////////////
INSERT INTO KSEN_LRC_RES_SCALE (ID,VER_NBR,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID) VALUES ('kuali.result.scale.grade.letter',1,'A-F Grading Scale','RICHTEXT-221','kuali.result.scale.state.approved','kuali.result.scale.type.grade')

INSERT INTO KSEN_LRC_RES_VAL_GRP(ID,NAME,RT_DESCR_ID,TYPE_ID,STATE_ID,RES_SCALE_ID) VALUES ('kuali.resultComponent.grade.letter','Standard A-F Grading','RICHTEXT-111','kuali.result.values.group.type.multiple','kuali.result.values.group.state.approved','kuali.result.scale.grade.letter')

INSERT INTO KSEN_LRC_RES_VALUE (ID,TYPE_ID,NAME,RT_DESCR_ID,VALUE,STATE_ID,RES_SCALE_ID) VALUES ('kuali.result.value.grade.letter.a','kuali.result.value.type.value','Excellent','RICHTEXT-222','A','kuali.result.value.state.approved','kuali.result.scale.grade.letter')
INSERT INTO KSEN_LRC_RES_VALUE (ID,TYPE_ID,NAME,RT_DESCR_ID,VALUE,STATE_ID,RES_SCALE_ID) VALUES ('kuali.result.value.grade.letter.b','kuali.result.value.type.value','Good','RICHTEXT-223','B','kuali.result.value.state.approved','kuali.result.scale.grade.letter')
INSERT INTO KSEN_LRC_RES_VALUE (ID,TYPE_ID,NAME,RT_DESCR_ID,VALUE,STATE_ID,RES_SCALE_ID) VALUES ('kuali.result.value.grade.letter.c','kuali.result.value.type.value','Average','RICHTEXT-224','C','kuali.result.value.state.approved','kuali.result.scale.grade.letter')
INSERT INTO KSEN_LRC_RES_VALUE (ID,TYPE_ID,NAME,RT_DESCR_ID,VALUE,STATE_ID,RES_SCALE_ID) VALUES ('kuali.result.value.grade.letter.d','kuali.result.value.type.value','Below Average','RICHTEXT-225','D','kuali.result.value.state.approved','kuali.result.scale.grade.letter')
INSERT INTO KSEN_LRC_RES_VALUE (ID,TYPE_ID,NAME,RT_DESCR_ID,VALUE,STATE_ID,RES_SCALE_ID) VALUES ('kuali.result.value.grade.letter.f','kuali.result.value.type.value','Failing','RICHTEXT-226','F','kuali.result.value.state.approved','kuali.result.scale.grade.letter')

INSERT INTO KSEN_LRC_RVGP_RV_RELTN (RES_VAL_GRP_ID,RES_VAL_ID) VALUES ('kuali.resultComponent.grade.letter','kuali.result.value.grade.letter.a')
INSERT INTO KSEN_LRC_RVGP_RV_RELTN (RES_VAL_GRP_ID,RES_VAL_ID) VALUES ('kuali.resultComponent.grade.letter','kuali.result.value.grade.letter.b')
INSERT INTO KSEN_LRC_RVGP_RV_RELTN (RES_VAL_GRP_ID,RES_VAL_ID) VALUES ('kuali.resultComponent.grade.letter','kuali.result.value.grade.letter.c')
INSERT INTO KSEN_LRC_RVGP_RV_RELTN (RES_VAL_GRP_ID,RES_VAL_ID) VALUES ('kuali.resultComponent.grade.letter','kuali.result.value.grade.letter.d')
INSERT INTO KSEN_LRC_RVGP_RV_RELTN (RES_VAL_GRP_ID,RES_VAL_ID) VALUES ('kuali.resultComponent.grade.letter','kuali.result.value.grade.letter.f')

//END - Entries for A-F Grading

///////////////////////////////////////////////////////////////////////////
//BEGIN - Entries for Pass/Fail Grading
///////////////////////////////////////////////////////////////////////////

INSERT INTO KSEN_LRC_RES_SCALE (ID,VER_NBR,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID) VALUES ('kuali.result.scale.grade.pf',1,'Pass/Fail Grading Scale','RICHTEXT-227','kuali.result.scale.state.approved','kuali.result.scale.type.grade')
INSERT INTO KSEN_LRC_RES_VAL_GRP(ID,NAME,RT_DESCR_ID,TYPE_ID,STATE_ID,RES_SCALE_ID) VALUES ('kuali.resultComponent.grade.passFail','Pass/Fail Grading','RICHTEXT-228','kuali.result.values.group.type.multiple','kuali.result.values.group.state.approved','kuali.result.scale.grade.letter')

INSERT INTO KSEN_LRC_RES_VALUE (ID,TYPE_ID,NAME,RT_DESCR_ID,VALUE,STATE_ID,RES_SCALE_ID) VALUES ('kuali.result.value.grade.pf.p','kuali.result.scale.type.grade','Pass','RICHTEXT-229','P','kuali.result.value.state.approved','kuali.result.scale.grade.pf')
INSERT INTO KSEN_LRC_RES_VALUE (ID,TYPE_ID,NAME,RT_DESCR_ID,VALUE,STATE_ID,RES_SCALE_ID) VALUES ('kuali.result.value.grade.pf.f','kuali.result.scale.type.grade','Fail','RICHTEXT-230','F','kuali.result.value.state.approved','kuali.result.scale.grade.pf')

INSERT INTO KSEN_LRC_RVGP_RV_RELTN (RES_VAL_GRP_ID,RES_VAL_ID) VALUES ('kuali.resultComponent.grade.passFail','kuali.result.value.grade.pf.p')
INSERT INTO KSEN_LRC_RVGP_RV_RELTN (RES_VAL_GRP_ID,RES_VAL_ID) VALUES ('kuali.resultComponent.grade.passFail','kuali.result.value.grade.pf.f')

//END - Entries for Pass/Fail Grading


-- INSERT INTO KSEN_LPR_RV_GRP_RELTN (LPR_ID,RV_GRP_ID) VALUES ('student3','kuali.resultComponent.grade.passFail')
-- INSERT INTO KSEN_LPR_RV_GRP_RELTN (LPR_ID,RV_GRP_ID) VALUES ('student1','kuali.resultComponent.grade.letter')
-- INSERT INTO KSEN_LPR_RV_GRP_RELTN (LPR_ID,RV_GRP_ID) VALUES ('student2','kuali.resultComponent.grade.letter')


--INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student2-grade-final', 'student2', 'student2 grade final', 'grade-final-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.final.grade.assigned', null, 0)