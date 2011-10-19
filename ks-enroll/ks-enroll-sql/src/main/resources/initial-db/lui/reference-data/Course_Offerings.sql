-- CO Description
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('f73b065a-f813-404b-a425-310a7056c99f', 0, 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', '97c9ee38-fc04-4a06-95ff-fe7102581d0c')
/

-- CO Title
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('d498fb3a-7002-44f8-b2b9-69ee048fa989', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG100', 'GEOG', 'Introduction to Geography', '', '', '100', '', '', 'f87ccf94-dae8-4017-9b1a-c5f13b249d40')
/

-- CO using previous desc and title
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('85974cb7-7047-4793-820d-b1624dac7219', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', 'REFERENCECOURSEGEOG100', '97c9ee38-fc04-4a06-95ff-fe7102581d0c', '', '', 'kuali.lui.state.draft', 'kuali.lui.type.course.offering', '', '', '', 'f87ccf94-dae8-4017-9b1a-c5f13b249d40', '', 'deecef6d-48e7-4804-86ff-5b02e79af2cf')
/

-- Honors = false
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('27cc1f01-1472-402a-b79b-267ddd93101d', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'deecef6d-48e7-4804-86ff-5b02e79af2cf', 'kuali.lu.code.honorsOffering', 'false', '27f14484-3ab7-4cfb-941c-f115b4cfe656')
/

-- Final Grade Roster
insert into KSEN_LPR_ROSTER (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_DUR_TYP_KEY, CHECK_IN_REQ, RT_DESCR_ID, STATE_ID, TYPE_ID, MAX_CAPACITY, NAME, TM_QUANTITY, ID) values ('a73dbeb6-91a2-4644-b4dd-ad8643034e2f', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 0, '', 'kuali.lpr.roster.state.ready', 'kuali.lpr.roster.type.course.grade.final', 50, '', NULL, 'b56d6ccb-76da-4fb5-975b-f36cec4cdbfa')
/

-- New CO Description (intended for AO?)
insert into KSEN_LUI_RICH_TEXT (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('7ff2be15-c606-4a69-b6b0-5f34f4dd84e8', 0, 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', 'An introduction to the broad field of geography as it is applicable to the general education student. The course presents the basic rationale of variations in human occupancy of the earth and stresses geographic concepts relevant to understanding world, regional and local issues.', '43ded9c9-59d1-45a2-8dc0-e157692a6473')
/

-- New Honors = false (intended for AO?)
insert into KSEN_LUI_LUCD (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, LUI_ID, TYPE, VALUE, ID) values ('4583719e-e236-4abf-a566-4b997754e288', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', 'deecef6d-48e7-4804-86ff-5b02e79af2cf', 'kuali.lu.code.honorsOffering', 'false', '7f2629a1-e283-40bc-ab43-21afb123b8be')
/

-- New CO Title (intended for AO?)
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CD, DIVISION, LNG_NAME, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('12049987-e4bc-457d-9b3e-43d1ec775a18', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'GEOG100', 'GEOG', 'Introduction to Geography', '', '', '100', '', '', 'b1ef5c56-5723-4ebf-841c-ac2a53a6a2bc')
/

-- Assign Instructor for CO
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('0469ffcc-c508-45a3-8b8e-d76a9ae2ecab', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'deecef6d-48e7-4804-86ff-5b02e79af2cf', 'eric', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'b07d19a6-7ab2-467f-88a3-31f911d850a9')
/

-- Add Lecture AO
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('ef4294cd-fbec-448f-a37a-af13ec099691', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '2b7f8fee-f003-432c-9b56-9364adac1b00', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', '', '', '', 'a72c6f03-5466-4b42-803c-b871bfe48bb3')
/

-- Set Meeting time for Lecture AO
insert into KSEN_LUI_MTG_SCHE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, LUI_ID, SPACE_ID, TM_PRD, ID) values ('f7f591b0-4144-48d4-aff3-3d2ee2daf43e', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'a72c6f03-5466-4b42-803c-b871bfe48bb3', '', 'WE;1130,1230', 'eb36dae4-0205-4e69-97d3-5ffad142c743')
/

-- CO delivered by Lecture
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('18d1a445-ca39-4f0b-9db4-6b5d38682e18', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'a72c6f03-5466-4b42-803c-b871bfe48bb3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia', '', 'deecef6d-48e7-4804-86ff-5b02e79af2cf', 'ea120c84-47a6-4ab3-82a9-a259f0ea716d')
/

-- update CO
update KSEN_LUI set OBJ_ID='', VER_NBR=1, updateId='', updateTime='', ATP_ID='testTermId1', CLU_ID='REFERENCECOURSEGEOG100', RT_DESCR_ID='43ded9c9-59d1-45a2-8dc0-e157692a6473', EFF_DT='', EXP_DT='', STATE_ID='kuali.lui.state.offered', TYPE_ID='kuali.lui.type.course.offering', MAX_SEATS=45, MIN_SEATS='', NAME='', OFFIC_LUI_ID='b1ef5c56-5723-4ebf-841c-ac2a53a6a2bc', REF_URL='' where ID='deecef6d-48e7-4804-86ff-5b02e79af2cf' and VER_NBR=0
/

-- update New Honors = false (not sure what the purpose of this is)
update KSEN_LUI_LUCD set OBJ_ID='4583719e-e236-4abf-a566-4b997754e288', VER_NBR=1, updateId='', updateTime=to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), RT_DESCR_ID='', LUI_ID='deecef6d-48e7-4804-86ff-5b02e79af2cf', TYPE='kuali.lu.code.honorsOffering', VALUE='false' where ID='7f2629a1-e283-40bc-ab43-21afb123b8be' and VER_NBR=0
/

-- update New Title (not sure what the purpose of this is)
update KSEN_LUI_IDENT set OBJ_ID='12049987-e4bc-457d-9b3e-43d1ec775a18', VER_NBR=1, updateId='', updateTime=to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), CD='GEOG100', DIVISION='GEOG', LNG_NAME='Introduction to Geography', SHRT_NAME='', ST='', SUFX_CD='100', TYPE='', VARTN='' where ID='b1ef5c56-5723-4ebf-841c-ac2a53a6a2bc' and VER_NBR=0
/

-- Associate Roster with CO
insert into KSEN_LPRROSTER_LUI_RELTN (LPRROSTER_ID, LUI_ID) values ('b56d6ccb-76da-4fb5-975b-f36cec4cdbfa', 'deecef6d-48e7-4804-86ff-5b02e79af2cf')
/

-- Assign Instructor for Lecture AO
insert into KSEN_LPR (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, commitmentPercent, effectiveDate, expirationDate, luiId, personId, RELATION_STATE_ID, RELATION_TYPE_ID, ID) values ('8a502328-d67d-4f2e-be24-65c1c986e9d8', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'a72c6f03-5466-4b42-803c-b871bfe48bb3', 'eric', 'kuali.lpr.state.assigned', 'kuali.lpr.type.instructor.main', 'c8d14dab-a6f0-42f4-b41b-a811646e5837')
/

-- Create RegGroup
insert into KSEN_LUI (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, ATP_ID, CLU_ID, RT_DESCR_ID, EFF_DT, EXP_DT, STATE_ID, TYPE_ID, MAX_SEATS, MIN_SEATS, NAME, OFFIC_LUI_ID, REF_URL, ID) values ('f54b93df-a047-4df0-bfc2-8dab1461bd83', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), 'testTermId1', '', '', '', '', 'kuali.lui.state.offered', 'kuali.lui.type.registration.group', '', '', '', '', '', 'f574b8a7-01eb-4ee3-a66f-3dcf68c1d927')
/

-- Associate RegGroup with CO
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('9e319344-9b12-439f-b7cd-fe9e6796ea87', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'f574b8a7-01eb-4ee3-a66f-3dcf68c1d927', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'deecef6d-48e7-4804-86ff-5b02e79af2cf', 'ea6ba428-fe7f-4dbf-a960-dcfb38de9031')
/

-- Associate RegGroup with AOs
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, EFF_DT, EXP_DT, LUI_ID, STATE_ID, TYPE_ID, NAME, RELATED_LUI_ID, ID) values ('2d94a24c-1ec8-446a-a524-347d57033ee2', 0, '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', to_timestamp('19-Oct-11 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM'), '', '', '', 'f574b8a7-01eb-4ee3-a66f-3dcf68c1d927', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia', '', 'a72c6f03-5466-4b42-803c-b871bfe48bb3', '6482d6cb-b202-4e7d-b2f6-fa2537c01c54')
/

