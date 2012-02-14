-- ---------------------------------------------------
--                   GEOG100 (Spring 2011)
-- ---------------------------------------------------
-- CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('EF76F01208BB4548ACBA56DB4AAF63161', 0, 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011DESCR')
/

-- CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('EF76F01208BB4548ACBA56DB4AAF63162', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG100FALL2010', 'GEOG', 'Introduction to Geography', '', '', '100', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011OFFICIALID')
/

-- CO using previous desc and title
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('EF76F01208BB4548ACBA56DB4AAF63163', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'Spring2011', 'REFERENCECOURSEGEOG100', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011DESCR', '', '', 'kuali.lui.state.draft', 'kuali.lui.type.course.offering', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011OFFICIALID', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011')
/

-- Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('EF76F01208BB4548ACBA56DB4AAF63164', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG100SPRING2011HONORS')
/

-- Final Grade Roster
insert into KSEN_LPR_ROSTER (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_DUR_TYP_KEY, CHECK_IN_REQ, RT_DESCR_ID, STATE_ID, TYPE_ID, MAX_CAPACITY, NAME, TM_QUANTITY, ID) values ('EF76F01208BB4548ACBA56DB4AAF63165', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 0, '', 'kuali.lpr.roster.state.ready', 'kuali.lpr.roster.type.course.grade.final', 60, '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011GRADINGROSTER')
/

-- New CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('EF76F01208BB4548ACBA56DB4AAF63166', 0, 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011DESCR2')
/

-- New Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('EF76F01208BB4548ACBA56DB4AAF63167', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG100SPRING2011HONORS2')
/

-- New CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('EF76F01208BB4548ACBA56DB4AAF63168', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG100FALL2010', 'GEOG', 'Introduction to Geography', '', '', '100', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011OFFICIALID2')
/

-- Assign Instructor for CO
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF63169', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011INSTRUCTOR')
/

-- update CO
update KSEN_LUI set OBJ_ID='', VER_NBR=1, updateId='', updateTime='', ATP_ID='Spring2011', CLU_ID='REFERENCECOURSEGEOG100', RT_DESCR_ID='REFERENCECOURSEOFFERINGGEOG100SPRING2011DESCR2', EFF_DT='', EXP_DT='', STATE_ID='kuali.lui.state.offered', TYPE_ID='kuali.lui.type.course.offering', MAX_SEATS=60, MIN_SEATS='', NAME='', OFFIC_LUI_ID='REFERENCECOURSEOFFERINGGEOG100SPRING2011OFFICIALID2', REF_URL='' where ID='REFERENCECOURSEOFFERINGGEOG100SPRING2011' and VER_NBR=0
/

-- update New Honors = false
update KSEN_LUI_LUCD set OBJ_ID='EF76F01208BB4548ACBA56DB4AAF631617', VER_NBR=1, updateId='', updateTime=to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), RT_DESCR_ID='', LUI_ID='REFERENCECOURSEOFFERINGGEOG100SPRING2011', TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='REFERENCECOURSEOFFERINGGEOG100SPRING2011HONORS2' and VER_NBR=0
/

-- update New Title
update KSEN_LUI_IDENT set OBJ_ID='EF76F01208BB4548ACBA56DB4AAF631618', VER_NBR=1, updateId='', updateTime=to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), CD='GEOG100FALL2010', DIVISION='GEOG', LNG_NAME='Introduction to Geography', SHRT_NAME='', ST='', SUFX_CD='100', TYPE='', VARTN='' where ID='REFERENCECOURSEOFFERINGGEOG100SPRING2011OFFICIALID2' and VER_NBR=0
/

-- Associate Roster with CO
insert into KSEN_LPRROSTER_LUI_RELTN (LPRROSTER_ID, LUI_ID) values ('REFERENCECOURSEOFFERINGGEOG100SPRING2011GRADINGROSTER', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011')
/

-- Add Lecture AO1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('EF76F01208BB4548ACBA56DB4AAF631610', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'Spring2011', '2b7f8fee-f003-432c-9b56-9364adac1b00', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY1')
/

-- Set Meeting time for Lecture AO1
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('EF76F01208BB4548ACBA56DB4AAF631611', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY1', '', 'MO,WE;1300,1400', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011MTGSCHED1')
/

-- CO delivered by Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631612', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY1RELTN')
/

-- Assign Instructor for Lecture AO1
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631613', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY1', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY1INSTRUCTOR')
/

-- Add DISCUSSION AO2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('EF76F01208BB4548ACBA56DB4AAF631619', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'Spring2011', '2b7f8fee-f003-432c-9b56-9364adac1b00', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY2')
/

-- Set Meeting time for DISCUSSION AO2
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('EF76F01208BB4548ACBA56DB4AAF631620', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY2', '', 'TU,TH;1300,1400', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011MTGSCHED2')
/

-- CO delivered by DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631621', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY2RELTN')
/

-- Assign Instructor for DISCUSSION AO2
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631622', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY2', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY2INSTRUCTOR')
/

-- Add DISCUSSION AO3
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('EF76F01208BB4548ACBA56DB4AAF631623', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'Spring2011', '2b7f8fee-f003-432c-9b56-9364adac1b00', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY3')
/

-- Set Meeting time for DISCUSSION AO3
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('EF76F01208BB4548ACBA56DB4AAF631624', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY3', '', 'TU,TH;1500,1600', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011MTGSCHED3')
/

-- CO delivered by DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631625', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY3RELTN')
/

-- Assign Instructor for DISCUSSION AO3
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631626', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY3', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY3INSTRUCTOR')
/

-- Create RegGroup1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('EF76F01208BB4548ACBA56DB4AAF631614', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'Spring2011', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP1')
/

-- Associate RegGroup1 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631615', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP1CORLTN')
/

-- Associate RegGroup1 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631616', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP1AO1RLTN')
/

-- Associate RegGroup1 with DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631627', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY2', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP1AO2RLTN')
/

-- Create RegGroup2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('EF76F01208BB4548ACBA56DB4AAF631628', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'Spring2011', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP2')
/

-- Associate RegGroup2 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631629', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP2CORLTN')
/

-- Associate RegGroup2 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631630', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP2AO1RLTN')
/

-- Associate RegGroup2 with DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('EF76F01208BB4548ACBA56DB4AAF631631', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011ACTIVITY3', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011REGGROUP1AO3RLTN')
/

-- Set Grading Method on CO
INSERT INTO KSEN_LUI_RV_GRP_RELTN (ID, LUI_ID, RV_GRP_ID) VALUES ('50D39EC5276B47C49899107D684629EB0', 'REFERENCECOURSEOFFERINGGEOG100SPRING2011', 'kuali.resultComponent.grade.letter')
/

-- ---------------------------------------------------
--                   GEOG100 (Fall 2011)
-- ---------------------------------------------------
-- CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB1', 0, 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'REFERENCECOURSEOFFERINGGEOG100DESCR')
/

-- CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB2', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG100', 'GEOG', 'Introduction to Geography', '', '', '100', '', '', 'REFERENCECOURSEOFFERINGGEOG100OFFICIALID')
/

-- CO using previous desc and title
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB3', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', 'REFERENCECOURSEGEOG100', 'REFERENCECOURSEOFFERINGGEOG100DESCR', '', '', 'kuali.lui.state.draft', 'kuali.lui.type.course.offering', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100OFFICIALID', '', 'REFERENCECOURSEOFFERINGGEOG100')
/

-- Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB4', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG100', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG100HONORS')
/

-- Final Grade Roster
insert into KSEN_LPR_ROSTER (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_DUR_TYP_KEY, CHECK_IN_REQ, RT_DESCR_ID, STATE_ID, TYPE_ID, MAX_CAPACITY, NAME, TM_QUANTITY, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB5', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 0, '', 'kuali.lpr.roster.state.ready', 'kuali.lpr.roster.type.course.grade.final', 60, '', '', 'REFERENCECOURSEOFFERINGGEOG100GRADINGROSTER')
/

-- New CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB6', 0, 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'REFERENCECOURSEOFFERINGGEOG100DESCR2')
/

-- New Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB7', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG100', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG100HONORS2')
/

-- New CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB8', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG100', 'GEOG', 'Introduction to Geography', '', '', '100', '', '', 'REFERENCECOURSEOFFERINGGEOG100OFFICIALID2')
/

-- Assign Instructor for CO
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB9', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG100INSTRUCTOR')
/

-- update CO
update KSEN_LUI set OBJ_ID='', VER_NBR=1, updateId='', updateTime='', ATP_ID='testTermId1', CLU_ID='REFERENCECOURSEGEOG100', RT_DESCR_ID='REFERENCECOURSEOFFERINGGEOG100DESCR2', EFF_DT='', EXP_DT='', STATE_ID='kuali.lui.state.offered', TYPE_ID='kuali.lui.type.course.offering', MAX_SEATS=60, MIN_SEATS='', NAME='', OFFIC_LUI_ID='REFERENCECOURSEOFFERINGGEOG100OFFICIALID2', REF_URL='' where ID='REFERENCECOURSEOFFERINGGEOG100' and VER_NBR=0
/

-- update New Honors = false
update KSEN_LUI_LUCD set OBJ_ID='2FBF60BD85E448A6AB8A333A6D9A62AB17', VER_NBR=1, updateId='', updateTime=to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), RT_DESCR_ID='', LUI_ID='REFERENCECOURSEOFFERINGGEOG100', TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='REFERENCECOURSEOFFERINGGEOG100HONORS2' and VER_NBR=0
/

-- update New Title
update KSEN_LUI_IDENT set OBJ_ID='2FBF60BD85E448A6AB8A333A6D9A62AB18', VER_NBR=1, updateId='', updateTime=to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), CD='GEOG100', DIVISION='GEOG', LNG_NAME='Introduction to Geography', SHRT_NAME='', ST='', SUFX_CD='100', TYPE='', VARTN='' where ID='REFERENCECOURSEOFFERINGGEOG100OFFICIALID2' and VER_NBR=0
/

-- Associate Roster with CO
insert into KSEN_LPRROSTER_LUI_RELTN (LPRROSTER_ID, LUI_ID) values ('REFERENCECOURSEOFFERINGGEOG100GRADINGROSTER', 'REFERENCECOURSEOFFERINGGEOG100')
/

-- Add Lecture AO1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB10', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '2b7f8fee-f003-432c-9b56-9364adac1b00', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY1')
/

-- Set Meeting time for Lecture AO1
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB11', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY1', '', 'MO,WE;1300,1400', 'REFERENCECOURSEOFFERINGGEOG100MTGSCHED1')
/

-- CO delivered by Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB12', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG100', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY1RELTN')
/

-- Assign Instructor for Lecture AO1
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB13', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY1', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY1INSTRUCTOR')
/

-- Add DISCUSSION AO2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB19', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '2b7f8fee-f003-432c-9b56-9364adac1b00', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY2')
/

-- Set Meeting time for DISCUSSION AO2
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB20', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY2', '', 'TU,TH;1300,1400', 'REFERENCECOURSEOFFERINGGEOG100MTGSCHED2')
/

-- CO delivered by DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB21', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG100', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY2RELTN')
/

-- Assign Instructor for DISCUSSION AO2
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB22', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY2', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY2INSTRUCTOR')
/

-- Add DISCUSSION AO3
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB23', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '2b7f8fee-f003-432c-9b56-9364adac1b00', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY3')
/

-- Set Meeting time for DISCUSSION AO3
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB24', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY3', '', 'TU,TH;1500,1600', 'REFERENCECOURSEOFFERINGGEOG100MTGSCHED3')
/

-- CO delivered by DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB25', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG100', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY3RELTN')
/

-- Assign Instructor for DISCUSSION AO3
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB26', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY3', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY3INSTRUCTOR')
/

-- Create RegGroup1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB14', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP1')
/

-- Associate RegGroup1 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB15', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP1CORLTN')
/

-- Associate RegGroup1 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB16', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP1AO1RLTN')
/

-- Associate RegGroup1 with DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB27', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY2', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP1AO2RLTN')
/

-- Create RegGroup2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB28', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP2')
/

-- Associate RegGroup2 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB29', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP2CORLTN')
/

-- Associate RegGroup2 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB30', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP2AO1RLTN')
/

-- Associate RegGroup2 with DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2FBF60BD85E448A6AB8A333A6D9A62AB31', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG100ACTIVITY3', 'REFERENCECOURSEOFFERINGGEOG100REGGROUP1AO3RLTN')
/

-- Set Grading Method on CO
INSERT INTO KSEN_LUI_RV_GRP_RELTN (ID, LUI_ID, RV_GRP_ID) VALUES ('50D39EC5276B47C49899107D684629EB1', 'REFERENCECOURSEOFFERINGGEOG100', 'kuali.resultComponent.grade.letter')
/

-- ---------------------------------------------------
--                   GEOG123
-- ---------------------------------------------------
-- CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('BF0242C0827B41138BAF7A4CBECFD4311', 0, 'A unique experience in integrating physical, chemical, geological, and biological sciences with geographical, economic, sociological, and political knowledge skills toward a better understanding of global change.  Review of environmental science relating to weather and climate change, acid precipitation, ozone holes, global warming, and impacts on biology, agriculture, and human behavior.  Study of the natural, long- term variability of the global environment, and what influence mankind may have in perturbing it from its natural evolution.  Concepts of how physical, biological, and human behavioral systems interact, and the  repercussions which may follow human endeavors. The manner in which to approach decision and policy making related to global change.', 'A unique experience in integrating physical, chemical, geological, and biological sciences with geographical, economic, sociological, and political knowledge skills toward a better understanding of global change.  Review of environmental science relating to weather and climate change, acid precipitation, ozone holes, global warming, and impacts on biology, agriculture, and human behavior.  Study of the natural, long- term variability of the global environment, and what influence mankind may have in perturbing it from its natural evolution.  Concepts of how physical, biological, and human behavioral systems interact, and the  repercussions which may follow human endeavors. The manner in which to approach decision and policy making related to global change.', 'REFERENCECOURSEOFFERINGGEOG123DESCR')
/

-- CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('BF0242C0827B41138BAF7A4CBECFD4312', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG123', 'GEOG', 'Causes and Implications of Global Change', '', '', '123', '', '', 'REFERENCECOURSEOFFERINGGEOG123OFFICIALID')
/

-- CO using previous desc and title
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('BF0242C0827B41138BAF7A4CBECFD4313', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', 'REFERENCECOURSEGEOG123', 'REFERENCECOURSEOFFERINGGEOG123DESCR', '', '', 'kuali.lui.state.draft', 'kuali.lui.type.course.offering', '', '', '', 'REFERENCECOURSEOFFERINGGEOG123OFFICIALID', '', 'REFERENCECOURSEOFFERINGGEOG123')
/

-- Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('BF0242C0827B41138BAF7A4CBECFD4314', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG123', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG123HONORS')
/

-- Final Grade Roster
insert into KSEN_LPR_ROSTER (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_DUR_TYP_KEY, CHECK_IN_REQ, RT_DESCR_ID, STATE_ID, TYPE_ID, MAX_CAPACITY, NAME, TM_QUANTITY, ID) values ('BF0242C0827B41138BAF7A4CBECFD4315', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 0, '', 'kuali.lpr.roster.state.ready', 'kuali.lpr.roster.type.course.grade.final', 50, '', '', 'REFERENCECOURSEOFFERINGGEOG123GRADINGROSTER')
/

-- New CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('BF0242C0827B41138BAF7A4CBECFD4316', 0, 'A unique experience in integrating physical, chemical, geological, and biological sciences with geographical, economic, sociological, and political knowledge skills toward a better understanding of global change.  Review of environmental science relating to weather and climate change, acid precipitation, ozone holes, global warming, and impacts on biology, agriculture, and human behavior.  Study of the natural, long- term variability of the global environment, and what influence mankind may have in perturbing it from its natural evolution.  Concepts of how physical, biological, and human behavioral systems interact, and the  repercussions which may follow human endeavors. The manner in which to approach decision and policy making related to global change.', 'A unique experience in integrating physical, chemical, geological, and biological sciences with geographical, economic, sociological, and political knowledge skills toward a better understanding of global change.  Review of environmental science relating to weather and climate change, acid precipitation, ozone holes, global warming, and impacts on biology, agriculture, and human behavior.  Study of the natural, long- term variability of the global environment, and what influence mankind may have in perturbing it from its natural evolution.  Concepts of how physical, biological, and human behavioral systems interact, and the  repercussions which may follow human endeavors. The manner in which to approach decision and policy making related to global change.', 'REFERENCECOURSEOFFERINGGEOG123DESCR2')
/

-- New Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('BF0242C0827B41138BAF7A4CBECFD4317', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG123', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG123HONORS2')
/

-- New CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('BF0242C0827B41138BAF7A4CBECFD4318', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG123', 'GEOG', 'Causes and Implications of Global Change', '', '', '123', '', '', 'REFERENCECOURSEOFFERINGGEOG123OFFICIALID2')
/

-- Assign Instructor for CO
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD4319', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123', 'eric', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG123INSTRUCTOR')
/

-- update CO
update KSEN_LUI set OBJ_ID='', VER_NBR=1, updateId='', updateTime='', ATP_ID='testTermId1', CLU_ID='REFERENCECOURSEGEOG123', RT_DESCR_ID='REFERENCECOURSEOFFERINGGEOG123DESCR2', EFF_DT='', EXP_DT='', STATE_ID='kuali.lui.state.offered', TYPE_ID='kuali.lui.type.course.offering', MAX_SEATS=50, MIN_SEATS='', NAME='', OFFIC_LUI_ID='REFERENCECOURSEOFFERINGGEOG123OFFICIALID2', REF_URL='' where ID='REFERENCECOURSEOFFERINGGEOG123' and VER_NBR=0
/

-- update New Honors = false
update KSEN_LUI_LUCD set OBJ_ID='BF0242C0827B41138BAF7A4CBECFD43117', VER_NBR=1, updateId='', updateTime=to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), RT_DESCR_ID='', LUI_ID='REFERENCECOURSEOFFERINGGEOG123', TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='REFERENCECOURSEOFFERINGGEOG123HONORS2' and VER_NBR=0
/

-- update New Title
update KSEN_LUI_IDENT set OBJ_ID='BF0242C0827B41138BAF7A4CBECFD43118', VER_NBR=1, updateId='', updateTime=to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), CD='GEOG123', DIVISION='GEOG', LNG_NAME='Causes and Implications of Global Change', SHRT_NAME='', ST='', SUFX_CD='123', TYPE='', VARTN='' where ID='REFERENCECOURSEOFFERINGGEOG123OFFICIALID2' and VER_NBR=0
/

-- Associate Roster with CO
insert into KSEN_LPRROSTER_LUI_RELTN (LPRROSTER_ID, LUI_ID) values ('REFERENCECOURSEOFFERINGGEOG123GRADINGROSTER', 'REFERENCECOURSEOFFERINGGEOG123')
/

-- Add Lecture AO1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('BF0242C0827B41138BAF7A4CBECFD43110', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '026cfeb6-78b1-4d3c-b528-d6d04d0fff57', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY1')
/

-- Set Meeting time for Lecture AO1
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('BF0242C0827B41138BAF7A4CBECFD43111', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY1', '', 'TU,WE;0900,1030', 'REFERENCECOURSEOFFERINGGEOG123MTGSCHED1')
/

-- CO delivered by Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43112', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG123', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY1RELTN')
/

-- Assign Instructor for Lecture AO1
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43113', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY1', 'eric', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY1INSTRUCTOR')
/

-- Add DISCUSSION AO2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('BF0242C0827B41138BAF7A4CBECFD43119', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '026cfeb6-78b1-4d3c-b528-d6d04d0fff57', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY2')
/

-- Set Meeting time for DISCUSSION AO2
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('BF0242C0827B41138BAF7A4CBECFD43120', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY2', '', 'TU,TH;1300,1400', 'REFERENCECOURSEOFFERINGGEOG123MTGSCHED2')
/

-- CO delivered by DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43121', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG123', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY2RELTN')
/

-- Assign Instructor for DISCUSSION AO2
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43122', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY2', 'eric', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY2INSTRUCTOR')
/

-- Add DISCUSSION AO3
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('BF0242C0827B41138BAF7A4CBECFD43123', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '026cfeb6-78b1-4d3c-b528-d6d04d0fff57', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY3')
/

-- Set Meeting time for DISCUSSION AO3
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('BF0242C0827B41138BAF7A4CBECFD43124', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY3', '', 'TU,WE;1200,1300', 'REFERENCECOURSEOFFERINGGEOG123MTGSCHED3')
/

-- CO delivered by DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43125', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG123', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY3RELTN')
/

-- Assign Instructor for DISCUSSION AO3
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43126', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY3', 'eric', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY3INSTRUCTOR')
/

-- Create RegGroup1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('BF0242C0827B41138BAF7A4CBECFD43114', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', 30, '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP1')
/

-- Associate RegGroup1 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43115', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG123', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP1CORLTN')
/

-- Associate RegGroup1 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43116', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP1AO1RLTN')
/

-- Associate RegGroup1 with DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43127', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY2', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP1AO2RLTN')
/

-- Create RegGroup2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('BF0242C0827B41138BAF7A4CBECFD43128', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', 20, '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP2')
/

-- Associate RegGroup2 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43129', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG123', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP2CORLTN')
/

-- Associate RegGroup2 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43130', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP2AO1RLTN')
/

-- Associate RegGroup2 with DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('BF0242C0827B41138BAF7A4CBECFD43131', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG123ACTIVITY3', 'REFERENCECOURSEOFFERINGGEOG123REGGROUP1AO3RLTN')
/

-- Set Grading Method on CO
INSERT INTO KSEN_LUI_RV_GRP_RELTN (ID, LUI_ID, RV_GRP_ID) VALUES ('50D39EC5276B47C49899107D684629EB2', 'REFERENCECOURSEOFFERINGGEOG123', 'kuali.resultComponent.grade.letter')
/

-- ---------------------------------------------------
--                   GEOG130
-- ---------------------------------------------------
-- CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('AFC579834097866CE040F50A8B7D25701', 0, 'An introduction to the geographic characteristics of the development problems and prospects of developing countries. Spatial distribution of poverty, employment, migration and urban growth, agricultural productivity, rural development, policies and international trade. Portraits of selected developing countries.', 'An introduction to the geographic characteristics of the development problems and prospects of developing countries. Spatial distribution of poverty, employment, migration and urban growth, agricultural productivity, rural development, policies and international trade. Portraits of selected developing countries.', 'REFERENCECOURSEOFFERINGGEOG130DESCR')
/

-- CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('AFC579834097866CE040F50A8B7D25702', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG130', 'GEOG', 'Developing Countries', '', '', '130', '', '', 'REFERENCECOURSEOFFERINGGEOG130OFFICIALID')
/

-- CO using previous desc and title
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('AFC579834097866CE040F50A8B7D25703', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', 'REFERENCECOURSEGEOG130', 'REFERENCECOURSEOFFERINGGEOG130DESCR', '', '', 'kuali.lui.state.draft', 'kuali.lui.type.course.offering', '', '', '', 'REFERENCECOURSEOFFERINGGEOG130OFFICIALID', '', 'REFERENCECOURSEOFFERINGGEOG130')
/

-- Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('AFC579834097866CE040F50A8B7D25704', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG130', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG130HONORS')
/

-- Final Grade Roster
insert into KSEN_LPR_ROSTER (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_DUR_TYP_KEY, CHECK_IN_REQ, RT_DESCR_ID, STATE_ID, TYPE_ID, MAX_CAPACITY, NAME, TM_QUANTITY, ID) values ('AFC579834097866CE040F50A8B7D25705', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 0, '', 'kuali.lpr.roster.state.ready', 'kuali.lpr.roster.type.course.grade.final', 50, '', '', 'REFERENCECOURSEOFFERINGGEOG130GRADINGROSTER')
/

-- New CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('AFC579834097866CE040F50A8B7D25706', 0, 'An introduction to the geographic characteristics of the development problems and prospects of developing countries. Spatial distribution of poverty, employment, migration and urban growth, agricultural productivity, rural development, policies and international trade. Portraits of selected developing countries.', 'An introduction to the geographic characteristics of the development problems and prospects of developing countries. Spatial distribution of poverty, employment, migration and urban growth, agricultural productivity, rural development, policies and international trade. Portraits of selected developing countries.', 'REFERENCECOURSEOFFERINGGEOG130DESCR2')
/

-- New Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('AFC579834097866CE040F50A8B7D25707', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG130', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG130HONORS2')
/

-- New CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('AFC579834097866CE040F50A8B7D25708', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG130', 'GEOG', 'Developing Countries', '', '', '130', '', '', 'REFERENCECOURSEOFFERINGGEOG130OFFICIALID2')
/

-- Assign Instructor for CO
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('AFC579834097866CE040F50A8B7D25709', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG130INSTRUCTOR')
/

-- update CO
update KSEN_LUI set OBJ_ID='', VER_NBR=1, updateId='', updateTime='', ATP_ID='testTermId1', CLU_ID='REFERENCECOURSEGEOG130', RT_DESCR_ID='REFERENCECOURSEOFFERINGGEOG130DESCR2', EFF_DT='', EXP_DT='', STATE_ID='kuali.lui.state.offered', TYPE_ID='kuali.lui.type.course.offering', MAX_SEATS=25, MIN_SEATS='', NAME='', OFFIC_LUI_ID='REFERENCECOURSEOFFERINGGEOG130OFFICIALID2', REF_URL='' where ID='REFERENCECOURSEOFFERINGGEOG130' and VER_NBR=0
/

-- update New Honors = false
update KSEN_LUI_LUCD set OBJ_ID='AFC579834097866CE040F50A8B7D257017', VER_NBR=1, updateId='', updateTime=to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), RT_DESCR_ID='', LUI_ID='REFERENCECOURSEOFFERINGGEOG130', TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='REFERENCECOURSEOFFERINGGEOG130HONORS2' and VER_NBR=0
/

-- update New Title
update KSEN_LUI_IDENT set OBJ_ID='AFC579834097866CE040F50A8B7D257018', VER_NBR=1, updateId='', updateTime=to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), CD='GEOG130', DIVISION='GEOG', LNG_NAME='Developing Countries', SHRT_NAME='', ST='', SUFX_CD='130', TYPE='', VARTN='' where ID='REFERENCECOURSEOFFERINGGEOG130OFFICIALID2' and VER_NBR=0
/

-- Associate Roster with CO
insert into KSEN_LPRROSTER_LUI_RELTN (LPRROSTER_ID, LUI_ID) values ('REFERENCECOURSEOFFERINGGEOG130GRADINGROSTER', 'REFERENCECOURSEOFFERINGGEOG130')
/

-- Add Lecture AO1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('AFC579834097866CE040F50A8B7D257010', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '7254b882-f291-4257-aad8-56c9bbf3a7ec', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY1')
/

-- Set Meeting time for Lecture AO1
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('AFC579834097866CE040F50A8B7D257011', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY1', '', 'TU,TH;1300,1450', 'REFERENCECOURSEOFFERINGGEOG130MTGSCHED1')
/

-- CO delivered by Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('AFC579834097866CE040F50A8B7D257012', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG130', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY1RELTN')
/

-- Assign Instructor for Lecture AO1
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('AFC579834097866CE040F50A8B7D257013', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY1', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY1INSTRUCTOR')
/

-- Add DISCUSSION AO2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('AFC579834097866CE040F50A8B7D257019', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '7254b882-f291-4257-aad8-56c9bbf3a7ec', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY2')
/

-- Set Meeting time for DISCUSSION AO2
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('AFC579834097866CE040F50A8B7D257020', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY2', '', 'MO,WE,FR;1030,1130', 'REFERENCECOURSEOFFERINGGEOG130MTGSCHED2')
/

-- CO delivered by DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('AFC579834097866CE040F50A8B7D257021', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG130', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY2RELTN')
/

-- Assign Instructor for DISCUSSION AO2
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('AFC579834097866CE040F50A8B7D257022', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY2', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY2INSTRUCTOR')
/

-- Add DISCUSSION AO3
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('AFC579834097866CE040F50A8B7D257023', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '7254b882-f291-4257-aad8-56c9bbf3a7ec', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY3')
/

-- Set Meeting time for DISCUSSION AO3
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('AFC579834097866CE040F50A8B7D257024', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY3', '', 'MO,WE,FR;0930,1030', 'REFERENCECOURSEOFFERINGGEOG130MTGSCHED3')
/

-- CO delivered by DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('AFC579834097866CE040F50A8B7D257025', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG130', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY3RELTN')
/

-- Assign Instructor for DISCUSSION AO3
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('AFC579834097866CE040F50A8B7D257026', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY3', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY3INSTRUCTOR')
/

-- Create RegGroup1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('AFC579834097866CE040F50A8B7D257014', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP1')
/

-- Associate RegGroup1 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('AFC579834097866CE040F50A8B7D257015', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG130', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP1CORLTN')
/

-- Associate RegGroup1 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('AFC579834097866CE040F50A8B7D257016', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP1AO1RLTN')
/

-- Associate RegGroup1 with DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('AFC579834097866CE040F50A8B7D257027', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY2', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP1AO2RLTN')
/

-- Create RegGroup2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('AFC579834097866CE040F50A8B7D257028', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP2')
/

-- Associate RegGroup2 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('AFC579834097866CE040F50A8B7D257029', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG130', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP2CORLTN')
/

-- Associate RegGroup2 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('AFC579834097866CE040F50A8B7D257030', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP2AO1RLTN')
/

-- Associate RegGroup2 with DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('AFC579834097866CE040F50A8B7D257031', 0, '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 1.39.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG130ACTIVITY3', 'REFERENCECOURSEOFFERINGGEOG130REGGROUP1AO3RLTN')
/

-- Set Grading Method on CO
INSERT INTO KSEN_LUI_RV_GRP_RELTN (ID, LUI_ID, RV_GRP_ID) VALUES ('50D39EC5276B47C49899107D684629EB3', 'REFERENCECOURSEOFFERINGGEOG130', 'kuali.resultComponent.grade.letter')
/

-- ---------------------------------------------------
--                   GEOG201
-- ---------------------------------------------------
-- CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('F85B5A4A880546D9825F5FD5417FC1951', 0, 'A systematic introduction to the processes and associated forms of the atmosphere and earth''s surfaces emphasizing the interaction between climatology, hydrology and geomorphology.', 'A systematic introduction to the processes and associated forms of the atmosphere and earth''s surfaces emphasizing the interaction between climatology, hydrology and geomorphology.', 'REFERENCECOURSEOFFERINGGEOG201DESCR')
/

-- CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('F85B5A4A880546D9825F5FD5417FC1952', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG201', 'GEOG', 'Geography of Environmental Systems', '', '', '201', '', '', 'REFERENCECOURSEOFFERINGGEOG201OFFICIALID')
/

-- CO using previous desc and title
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('F85B5A4A880546D9825F5FD5417FC1953', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', 'REFERENCECOURSEGEOG201', 'REFERENCECOURSEOFFERINGGEOG201DESCR', '', '', 'kuali.lui.state.draft', 'kuali.lui.type.course.offering', '', '', '', 'REFERENCECOURSEOFFERINGGEOG201OFFICIALID', '', 'REFERENCECOURSEOFFERINGGEOG201')
/

-- Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('F85B5A4A880546D9825F5FD5417FC1954', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG201', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG201HONORS')
/

-- Final Grade Roster
insert into KSEN_LPR_ROSTER (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_DUR_TYP_KEY, CHECK_IN_REQ, RT_DESCR_ID, STATE_ID, TYPE_ID, MAX_CAPACITY, NAME, TM_QUANTITY, ID) values ('F85B5A4A880546D9825F5FD5417FC1955', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 0, '', 'kuali.lpr.roster.state.ready', 'kuali.lpr.roster.type.course.grade.final', 80, '', '', 'REFERENCECOURSEOFFERINGGEOG201GRADINGROSTER')
/

-- New CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('F85B5A4A880546D9825F5FD5417FC1956', 0, 'A systematic introduction to the processes and associated forms of the atmosphere and earth''s surfaces emphasizing the interaction between climatology, hydrology and geomorphology.', 'A systematic introduction to the processes and associated forms of the atmosphere and earth''s surfaces emphasizing the interaction between climatology, hydrology and geomorphology.', 'REFERENCECOURSEOFFERINGGEOG201DESCR2')
/

-- New Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('F85B5A4A880546D9825F5FD5417FC1957', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'REFERENCECOURSEOFFERINGGEOG201', 'kuali.lu.code.honorsOffering', 0, 'REFERENCECOURSEOFFERINGGEOG201HONORS2')
/

-- New CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('F85B5A4A880546D9825F5FD5417FC1958', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG201', 'GEOG', 'Geography of Environmental Systems', '', '', '201', '', '', 'REFERENCECOURSEOFFERINGGEOG201OFFICIALID2')
/

-- Assign Instructor for CO
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC1959', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG201INSTRUCTOR')
/

-- update CO
update KSEN_LUI set OBJ_ID='', VER_NBR=1, updateId='', updateTime='', ATP_ID='testTermId1', CLU_ID='REFERENCECOURSEGEOG201', RT_DESCR_ID='REFERENCECOURSEOFFERINGGEOG201DESCR2', EFF_DT='', EXP_DT='', STATE_ID='kuali.lui.state.offered', TYPE_ID='kuali.lui.type.course.offering', MAX_SEATS=80, MIN_SEATS='', NAME='', OFFIC_LUI_ID='REFERENCECOURSEOFFERINGGEOG201OFFICIALID2', REF_URL='' where ID='REFERENCECOURSEOFFERINGGEOG201' and VER_NBR=0
/

-- update New Honors = false
update KSEN_LUI_LUCD set OBJ_ID='F85B5A4A880546D9825F5FD5417FC19517', VER_NBR=1, updateId='', updateTime=to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), RT_DESCR_ID='', LUI_ID='REFERENCECOURSEOFFERINGGEOG201', TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='REFERENCECOURSEOFFERINGGEOG201HONORS2' and VER_NBR=0
/

-- update New Title
update KSEN_LUI_IDENT set OBJ_ID='F85B5A4A880546D9825F5FD5417FC19518', VER_NBR=1, updateId='', updateTime=to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), CD='GEOG201', DIVISION='GEOG', LNG_NAME='Geography of Environmental Systems', SHRT_NAME='', ST='', SUFX_CD='201', TYPE='', VARTN='' where ID='REFERENCECOURSEOFFERINGGEOG201OFFICIALID2' and VER_NBR=0
/

-- Associate Roster with CO
insert into KSEN_LPRROSTER_LUI_RELTN (LPRROSTER_ID, LUI_ID) values ('REFERENCECOURSEOFFERINGGEOG201GRADINGROSTER', 'REFERENCECOURSEOFFERINGGEOG201')
/

-- Add Lecture AO1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('F85B5A4A880546D9825F5FD5417FC19510', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', 'c744103f-adc9-40e4-8b09-be69fd0f9ac8', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY1')
/

-- Set Meeting time for Lecture AO1
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('F85B5A4A880546D9825F5FD5417FC19511', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY1', '', 'MO,WE;1100,1200', 'REFERENCECOURSEOFFERINGGEOG201MTGSCHED1')
/

-- CO delivered by Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19512', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG201', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY1RELTN')
/

-- Assign Instructor for Lecture AO1
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19513', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY1', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY1INSTRUCTOR')
/

-- Add DISCUSSION AO2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('F85B5A4A880546D9825F5FD5417FC19519', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', 'c744103f-adc9-40e4-8b09-be69fd0f9ac8', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY2')
/

-- Set Meeting time for DISCUSSION AO2
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('F85B5A4A880546D9825F5FD5417FC19520', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY2', '', 'TU,TH;1300,1400', 'REFERENCECOURSEOFFERINGGEOG201MTGSCHED2')
/

-- CO delivered by DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19521', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG201', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY2RELTN')
/

-- Assign Instructor for DISCUSSION AO2
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19522', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY2', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY2INSTRUCTOR')
/

-- Add DISCUSSION AO3
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('F85B5A4A880546D9825F5FD5417FC19523', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', 'c744103f-adc9-40e4-8b09-be69fd0f9ac8', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lab', '', '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY3')
/

-- Set Meeting time for DISCUSSION AO3
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('F85B5A4A880546D9825F5FD5417FC19524', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY3', '', 'TU,TH;1500,1600', 'REFERENCECOURSEOFFERINGGEOG201MTGSCHED3')
/

-- CO delivered by DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19525', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'REFERENCECOURSEOFFERINGGEOG201', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY3RELTN')
/

-- Assign Instructor for DISCUSSION AO3
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19526', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY3', 'fran', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY3INSTRUCTOR')
/

-- Create RegGroup1
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('F85B5A4A880546D9825F5FD5417FC19514', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', 40, '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP1')
/

-- Associate RegGroup1 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19515', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG201', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP1CORLTN')
/

-- Associate RegGroup1 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19516', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP1AO1RLTN')
/

-- Associate RegGroup1 with DISCUSSION AO2
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19527', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY2', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP1AO2RLTN')
/

-- Create RegGroup2
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('F85B5A4A880546D9825F5FD5417FC19528', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', 40, '', '', '', '', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP2')
/

-- Associate RegGroup2 with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19529', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG201', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP2CORLTN')
/

-- Associate RegGroup2 with Lecture AO1
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19530', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY1', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP2AO1RLTN')
/

-- Associate RegGroup2 with DISCUSSION AO3
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('F85B5A4A880546D9825F5FD5417FC19531', 0, '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('21-Oct-11 3.35.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'REFERENCECOURSEOFFERINGGEOG201ACTIVITY3', 'REFERENCECOURSEOFFERINGGEOG201REGGROUP1AO3RLTN')
/

-- Set Grading Method on CO
INSERT INTO KSEN_LUI_RV_GRP_RELTN (ID, LUI_ID, RV_GRP_ID) VALUES ('50D39EC5276B47C49899107D684629EB4', 'REFERENCECOURSEOFFERINGGEOG201', 'kuali.resultComponent.grade.letter')
/
