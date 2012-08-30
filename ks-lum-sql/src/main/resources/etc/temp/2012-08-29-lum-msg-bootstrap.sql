-- Program cross field validation warning messages

insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('ebd58a7d-2dfc-45ce-bb72-f5c270bbfe1f', 'validation.majorEndTermRequiresVariationEndTerm', 'en', 'validation', 'The value in this field must be equal to or greater than the values in the corresponding fields in the associated specializations in order to submit this application.', SYS_GUID(), 3)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('673088c1-511b-48eb-aff0-797545321164', 'validation.variationStartTerm', 'en', 'validation', 'The value in this field must equal to or greater than the values in the corresponding fields in the parent major in order to submit this modification. Either change the value in this field or change the corresponding fields in the parent major.', SYS_GUID(), 3)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('d818d3ed-2c39-4454-991c-c28350229e25', 'validation.variation', 'en', 'validation', 'Items in this specialization conflict with values in items in the parent major. Click on the specialization name to modify and resolve the conflict. You may also change the value in the parent major.', SYS_GUID(), 4)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('b7f303e7-215b-4155-b637-db414a3cfffa', 'validation.majorEndTermRequiresVariationStartTerm', 'en', 'validation', 'The value in this field must be equal to or less than the values in the corresponding fields in the associated specializations in order to submit this application.', SYS_GUID(), 3)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('3fb52d13-39cb-42ce-b4b4-352ba3654cf9', 'validation.variationEndTerm', 'en', 'validation', 'The value in this field must equal to or earlier than the values in the corresponding fields in the parent major in order to submit this modification. Either change the value in this field or change the corresponding fields in the parent major.', SYS_GUID(), 5)
/

insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('3c3da6ea-5678-41e0-919f-ddc093c8843f', 'validation.course.subjectAreaUsage.all', 'en', 'validation', 'All organizations must be added for the given subject area.', '8530c759-d973-44d0-8ef9-8c22da69e4d6', 5)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('70e2915c-fc38-4171-8af9-7ce8579f9da3', 'validation.course.subjectAreaUsage.one', 'en', 'validation', 'Only one organization can be added for the given subject area.', '25d9395c-07df-44bb-9b9e-c09e61539d83', 5)
/

insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('9856cfca-9a7a-4af5-b544-280a74ffff21', 'modifyProgramNoVersion', 'en', 'program', 'Modify this Version', 'SYSGUID()', 2)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('c1866017-0225-4093-a141-f363fc2e9886', 'modifyProgramSubTitle', 'en', 'program', 'What kind of modification?', 'SYSGUID()', 2)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('9ae7d731-51ce-49f6-a0bc-0ee33d151241', 'modifyProgramWithVersion', 'en', 'program', 'Modify with a new version', 'SYSGUID()', 2)
/

insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('cde9a619-4d45-46e9-8b02-4b945700754c', 'courseLastTermOffered', 'en', 'course', 'Last Term Offered', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('2af23e4d-11af-4c7f-becb-51beecfd257c', 'courseLastTermOffered-instruct', 'en', 'course', 'Select the last term this course was or will be offered, if known.', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('e6c5c30c-9a4a-4467-8323-03c248bfde4d', 'coursePublicationYear', 'en', 'course', 'Last Course Catalog Publication Year', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('01bcbc8f-2270-4b2a-9237-5664295e6158', 'coursePublicationYear-instruct', 'en', 'course', 'Last academic year for which this course will be published in the course catalog.', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('435cefaf-45a2-4556-948b-752d995b9fda', 'courseRetireRationale', 'en', 'course', 'Rationale for Retirement', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('93621a65-bb35-4532-912c-aa28814c39b8', 'courseSpecialCircumstances', 'en', 'course', 'Special Circumstances', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('bf6713d4-793f-41ad-8c7a-b748b6bae8d6', 'courseSpecialCircumstances-instruct', 'en', 'course', 'Information about how the course should be treated (i.e. transferability, usage to meet major requirements, etc.) after it is retired.', SYS_GUID(), 2)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('01d44d37-ddbe-48ec-aed3-2a34076fbe45', 'retirement', 'en', 'course', 'Retirement Information', SYS_GUID(), 1)
/

insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('78858cc2-cf6d-48c8-855c-102a7bef985e', 'showingRequiredFields', 'en', 'common', 'Showing only required fields.	    ', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('c4ae799b-17b5-4d82-be47-22f5a3a74d57', 'showingAllFields', 'en', 'common', 'Showing required and optional fields.	     ', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('fa234008-491f-4337-91ca-4720e1110dd4', 'showAllFields', 'en', 'common', 'Show all fields', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('a8dea7aa-276b-4b72-8101-9a63e2621227', 'showRequiredFields', 'en', 'common', 'Show only required fields', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('47b901d4-cd65-4e6b-920d-f96a916f4eb1', 'requiredFields', 'en', 'common', 'No required fields.	     ', SYS_GUID(), 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('0aa5f3f7-30c9-48ae-ba7d-32ee3e3bc091', 'allFields', 'en', 'common', 'Show all fields', SYS_GUID(), 1)
/

insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('947f82fc-cab9-4c23-b2e4-688d54468aa8', 'previousEndTerm', 'en', 'course', 'Currently Active Version''s End Term', '4703ebbf-cdaa-41f5-894a-d14a08921c3c', 1)
/

update KSMG_MESSAGE set MSG_VALUE='Last term in which the course can be offered.' where MSG_ID='cluEndTerm-instruct'
/

update KSMG_MESSAGE set MSG_VALUE='useCurriculumReview' where MSG_ID='useCurriculumReview'
/

update KSMG_MESSAGE set MSG_VALUE='Duration Count' where MSG_ID='cluDurationQuantity' and GRP_NAME='course' and LOCALE='en'
/

INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('5b5cd7fc-b72f-4ba7-80ac-1f9b864dbbc0', 'remainingChars', 'en', 'common', 'characters left', '165E3CAA-BE63-BB3A-0704-19E4F3A3225A', 2)
/

insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('6b2fc022-cd8a-471c-9381-c35ffef1f8cd', 'validation.programManagingBodiesMatch', 'en', 'validation', '{0} is not a child of {1}.', SYS_GUID(), 1)
/

update ksmg_message set msg_value = 'This is the organization(s) responsible for the content of the course. This selection will determine <br/>the workflow/approval process for this <br>proposal.' where msg_id = 'cluCurriculumOversight-instruct' and locale = 'en' and grp_name = 'course'
/

UPDATE KSMG_MESSAGE SET MSG_VALUE = 'Use curriculum review process' WHERE MSG_ID = 'useCurriculumReview' AND GRP_NAME = 'course'
/

INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('21d7f2a1-fceb-49c6-9f83-43ca901599f2', 'useCurriculumReview', 'en', 'program', 'Use curriculum review process', '79C703E3-1D21-6C77-7C60-2CD7163D584A', 13)
/

UPDATE KSMG_MESSAGE  SET MSG_VALUE = 'Browse Academic Programs' WHERE MSG_ID='browseProgram' AND LOCALE='en'
/
UPDATE KSMG_MESSAGE  SET MSG_VALUE = 'Create an Academic Program' WHERE MSG_ID='createProgram' AND LOCALE='en'
/

INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('7ce6bbf4-e4f1-42c5-910e-a020950b44bd', 'approve', 'en', 'common', 'Approve', '4686F4EA-4841-9291-9802-21A0F4B84C8D', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('71bfc8d4-9796-40b7-86a1-bf80d9b38998', 'proposalStartTerm', 'en', 'course', 'Proposal''s Start Term', '0A817F7F-06D6-0BC9-0A2B-44684ED65C64', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('ed8907cd-ec75-432c-96e3-5179f6f7a4eb', 'finalApprovalProposalStartTermDialogue', 'en', 'course', 'Approving this proposal will cause the currently active version to become superseded. In order to proceed, you need to give the currently active version an end term.', '94761262-BA00-AC67-31A6-23F42BA3738A', 2)
/

INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('73a0f9d5-c622-4235-960c-1626d4b9550b', 'courseStatusLabel', 'en', 'course', 'Course Status', 'FA8895F4-34E5-DA22-AF71-1AB552CBB11B', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('bc9d4d3e-009a-4c7b-90c8-4ca2948a8525', 'programStatusLabel', 'en', 'program', 'Program Status', 'E25D3CD0-081C-CDCC-E295-B6698D358F10', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('5b550e08-fe1c-4540-9a1c-c53d22046659', 'proposalStatusLabel', 'en', 'common', 'Proposal Status', '5A5FD0F9-4ED9-DAC1-E74B-138D92AA078A', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('c069323c-5cd2-4c14-b762-7079dea98d4b', 'Approved', 'en', 'common', 'Approved', '75424795-6BA0-D797-18B7-F04B7444E9A4', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('67709655-dcd0-4df4-ad43-347d48883a6d', 'Cancelled', 'en', 'common', 'Cancelled', '565B0763-F606-670E-317C-53CC47F1E9AB', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('ba551395-ee8a-48d8-adbf-b73e13653cf6', 'Enroute', 'en', 'common', 'Enroute', 'E4383511-D1D5-34DE-1E37-8E8B2FFCE9B5', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('b4d6a38b-9255-46fd-a275-394059bf0e2b', 'Exception', 'en', 'common', 'Exception', '045D4A18-5452-50F7-3DF5-E2A11EFDB757', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('f8dff6fe-6519-4d74-8a24-9c20f0ec3254', 'Rejected', 'en', 'common', 'Rejected', '4F973F78-23DB-193D-6AEE-7A1104848D2B', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('4461a877-0316-4093-8d3e-de50c0cbb8c0', 'Saved', 'en', 'common', 'Saved', '84F92C29-6168-CC0B-54F0-081DF8F21F21', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('33d88b1c-1d2b-48d5-be73-b25531cc3f8b', 'Withdrawn', 'en', 'common', 'Withdrawn', '79C6715F-BE87-FC20-22FB-216CD2E0F465', 1)
/

UPDATE KSMG_MESSAGE  SET MSG_VALUE = 'Select by Proposed Course Code' WHERE MSG_ID='selectByProposedCourse' AND LOCALE='en'
/
