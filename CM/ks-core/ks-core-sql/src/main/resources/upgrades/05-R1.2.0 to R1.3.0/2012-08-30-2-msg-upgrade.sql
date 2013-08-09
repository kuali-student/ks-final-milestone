-- ###################
-- ##### INSERTS #####
-- ###################

INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('ebd58a7d-2dfc-45ce-bb72-f5c270bbfe1f', 'validation.majorEndTermRequiresVariationEndTerm', 'en', 'validation', 'The value in this field must be equal to or greater than the values in the corresponding fields in the associated specializations in order to submit this application.', SYS_GUID(), 3)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('673088c1-511b-48eb-aff0-797545321164', 'validation.variationStartTerm', 'en', 'validation', 'The value in this field must equal to or greater than the values in the corresponding fields in the parent major in order to submit this modification. Either change the value in this field or change the corresponding fields in the parent major.', SYS_GUID(), 3)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('d818d3ed-2c39-4454-991c-c28350229e25', 'validation.variation', 'en', 'validation', 'Items in this specialization conflict with values in items in the parent major. Click on the specialization name to modify and resolve the conflict. You may also change the value in the parent major.', SYS_GUID(), 4)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('b7f303e7-215b-4155-b637-db414a3cfffa', 'validation.majorEndTermRequiresVariationStartTerm', 'en', 'validation', 'The value in this field must be equal to or less than the values in the corresponding fields in the associated specializations in order to submit this application.', SYS_GUID(), 3)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('3fb52d13-39cb-42ce-b4b4-352ba3654cf9', 'validation.variationEndTerm', 'en', 'validation', 'The value in this field must equal to or earlier than the values in the corresponding fields in the parent major in order to submit this modification. Either change the value in this field or change the corresponding fields in the parent major.', SYS_GUID(), 5)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('3c3da6ea-5678-41e0-919f-ddc093c8843f', 'validation.course.subjectAreaUsage.all', 'en', 'validation', 'All organizations must be added for the given subject area.', '8530c759-d973-44d0-8ef9-8c22da69e4d6', 5)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('70e2915c-fc38-4171-8af9-7ce8579f9da3', 'validation.course.subjectAreaUsage.one', 'en', 'validation', 'Only one organization can be added for the given subject area.', '25d9395c-07df-44bb-9b9e-c09e61539d83', 5)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('9856cfca-9a7a-4af5-b544-280a74ffff21', 'modifyProgramNoVersion', 'en', 'program', 'Modify this Version', 'SYSGUID()', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('c1866017-0225-4093-a141-f363fc2e9886', 'modifyProgramSubTitle', 'en', 'program', 'What kind of modification?', 'SYSGUID()', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('9ae7d731-51ce-49f6-a0bc-0ee33d151241', 'modifyProgramWithVersion', 'en', 'program', 'Modify with a new version', 'SYSGUID()', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('cde9a619-4d45-46e9-8b02-4b945700754c', 'courseLastTermOffered', 'en', 'course', 'Last Term Offered', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('2af23e4d-11af-4c7f-becb-51beecfd257c', 'courseLastTermOffered-instruct', 'en', 'course', 'Select the last term this course was or will be offered, if known.', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('e6c5c30c-9a4a-4467-8323-03c248bfde4d', 'coursePublicationYear', 'en', 'course', 'Last Course Catalog Publication Year', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('01bcbc8f-2270-4b2a-9237-5664295e6158', 'coursePublicationYear-instruct', 'en', 'course', 'Last academic year for which this course will be published in the course catalog.', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('435cefaf-45a2-4556-948b-752d995b9fda', 'courseRetireRationale', 'en', 'course', 'Rationale for Retirement', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('93621a65-bb35-4532-912c-aa28814c39b8', 'courseSpecialCircumstances', 'en', 'course', 'Special Circumstances', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('bf6713d4-793f-41ad-8c7a-b748b6bae8d6', 'courseSpecialCircumstances-instruct', 'en', 'course', 'Information about how the course should be treated (i.e. transferability, usage to meet major requirements, etc.) after it is retired.', SYS_GUID(), 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('01d44d37-ddbe-48ec-aed3-2a34076fbe45', 'retirement', 'en', 'course', 'Retirement Information', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('78858cc2-cf6d-48c8-855c-102a7bef985e', 'showingRequiredFields', 'en', 'common', 'Showing only required fields.      ', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('c4ae799b-17b5-4d82-be47-22f5a3a74d57', 'showingAllFields', 'en', 'common', 'Showing required and optional fields.       ', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('fa234008-491f-4337-91ca-4720e1110dd4', 'showAllFields', 'en', 'common', 'Show all fields', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('a8dea7aa-276b-4b72-8101-9a63e2621227', 'showRequiredFields', 'en', 'common', 'Show only required fields', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('47b901d4-cd65-4e6b-920d-f96a916f4eb1', 'requiredFields', 'en', 'common', 'No required fields.       ', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('0aa5f3f7-30c9-48ae-ba7d-32ee3e3bc091', 'allFields', 'en', 'common', 'Show all fields', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('947f82fc-cab9-4c23-b2e4-688d54468aa8', 'previousEndTerm', 'en', 'course', 'Currently Active Version''s End Term', '4703ebbf-cdaa-41f5-894a-d14a08921c3c', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('5b5cd7fc-b72f-4ba7-80ac-1f9b864dbbc0', 'remainingChars', 'en', 'common', 'characters left', '165E3CAA-BE63-BB3A-0704-19E4F3A3225A', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('6b2fc022-cd8a-471c-9381-c35ffef1f8cd', 'validation.programManagingBodiesMatch', 'en', 'validation', '{0} is not a child of {1}.', SYS_GUID(), 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('21d7f2a1-fceb-49c6-9f83-43ca901599f2', 'useCurriculumReview', 'en', 'program', 'Use curriculum review process', '79C703E3-1D21-6C77-7C60-2CD7163D584A', 13)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('7ce6bbf4-e4f1-42c5-910e-a020950b44bd', 'approve', 'en', 'common', 'Approve', '4686F4EA-4841-9291-9802-21A0F4B84C8D', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('71bfc8d4-9796-40b7-86a1-bf80d9b38998', 'proposalStartTerm', 'en', 'course', 'Proposal''s Start Term', '0A817F7F-06D6-0BC9-0A2B-44684ED65C64', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('ed8907cd-ec75-432c-96e3-5179f6f7a4eb', 'finalApprovalProposalStartTermDialogue', 'en', 'course', 'Approving this proposal will cause the currently active version to become superseded. In order to proceed, you need to give the currently active version an end term.', '94761262-BA00-AC67-31A6-23F42BA3738A', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('73a0f9d5-c622-4235-960c-1626d4b9550b', 'courseStatusLabel', 'en', 'course', 'Course Status', 'FA8895F4-34E5-DA22-AF71-1AB552CBB11B', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('bc9d4d3e-009a-4c7b-90c8-4ca2948a8525', 'programStatusLabel', 'en', 'program', 'Program Status', 'E25D3CD0-081C-CDCC-E295-B6698D358F10', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('5b550e08-fe1c-4540-9a1c-c53d22046659', 'proposalStatusLabel', 'en', 'common', 'Proposal Status', '5A5FD0F9-4ED9-DAC1-E74B-138D92AA078A', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('c069323c-5cd2-4c14-b762-7079dea98d4b', 'Approved', 'en', 'common', 'Approved', '75424795-6BA0-D797-18B7-F04B7444E9A4', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('67709655-dcd0-4df4-ad43-347d48883a6d', 'Cancelled', 'en', 'common', 'Cancelled', '565B0763-F606-670E-317C-53CC47F1E9AB', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('ba551395-ee8a-48d8-adbf-b73e13653cf6', 'Enroute', 'en', 'common', 'Enroute', 'E4383511-D1D5-34DE-1E37-8E8B2FFCE9B5', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('b4d6a38b-9255-46fd-a275-394059bf0e2b', 'Exception', 'en', 'common', 'Exception', '045D4A18-5452-50F7-3DF5-E2A11EFDB757', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('f8dff6fe-6519-4d74-8a24-9c20f0ec3254', 'Rejected', 'en', 'common', 'Rejected', '4F973F78-23DB-193D-6AEE-7A1104848D2B', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('4461a877-0316-4093-8d3e-de50c0cbb8c0', 'Saved', 'en', 'common', 'Saved', '84F92C29-6168-CC0B-54F0-081DF8F21F21', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('33d88b1c-1d2b-48d5-be73-b25531cc3f8b', 'Withdrawn', 'en', 'common', 'Withdrawn', '79C6715F-BE87-FC20-22FB-216CD2E0F465', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('b07aa879-1323-4e4b-8ef6-d50b7cc5cf48', 'cluCopyItem', 'en', 'course', 'Copy to New Proposal', '466bdb41-04d5-451a-a744-4fb1be53802f', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('746a2d9f-c3ec-4efe-9f8f-eb95f9329191', 'startProposal', 'en', 'course', 'Start Proposal', '8abdae27-9bc6-4306-8e2b-e1232421f9c2', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('e20e11df-721b-4b38-91dc-0658230e5186', 'createCourseSubTitle', 'en', 'course', 'How would you like to start?', '26d9ef23-8aeb-4166-b0b7-3279c54958a2', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('3d1666a2-9319-489f-b5d3-45da8d752b62', 'createCourseSubTitle-instruct', 'en', 'course', 'You can choose to start a blank proposal with no pre-filled data, or copy an existing course/proposal and then edit the copied data to meet your needs.', '6566c0b7-2e80-4383-afec-41c1a4a3afa3', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('0abc9368-f7fe-4ce8-bd10-39a96258fd1d', 'createCourseSubTitle-help', 'en', 'course', '<p>If you are looking to copy a particular course or proposal but cannot find it using the fields here, click Cancel and use either the Course Catalog or Course Search to find it.</p>', 'c2b76ae1-280b-4835-ba77-21e0126a3c04', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('a25e0d8d-a0b8-43a9-b03d-59a766d24c42', 'courseToCopy', 'en', 'course', 'Course to Copy', 'ff50b112-ad14-43e8-8ec1-2d9b5b5892b0', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('de03de36-c44f-4a98-a779-7f3296c2150d', 'courseInvalidValue', 'en', 'course', 'Course - invalid value', '8e4a2b74-f8b5-4315-850a-0e88b8807957', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('b2be5632-643b-42ca-ac83-f6e8c3c11493', 'proposalInvalidValue', 'en', 'course', 'Proposal - invalid value', 'c876546f-526e-47c9-9fd3-367e94ab7aa0', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('d6c5ef04-999b-4f2a-bc54-ec823716be7c', 'proposalToCopy', 'en', 'course', 'Proposal to Copy', 'ef268c27-277f-4336-b690-a5beca41d3a1', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('c0b3b2a5-d63d-4519-9530-0f37779a9a85', 'courseInvalidValue', 'en', 'course', 'Course - invalid value', 'e27dee7c-5fb5-4f85-b075-f2319cb8a996', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('d17d3010-ca8f-455f-bb74-80442529fcce', 'selectByCourseCode', 'en', 'course', 'Select by Course Code', '8d9b30be-0dfa-411d-bea1-acb0f7a1e7ad', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('c7275985-accd-4246-ba53-14737c6a4fc0', 'selectByCourseTitle', 'en', 'course', 'Select by Course Title', '3a272c44-5fed-48ef-a696-ddedbc80d74c', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('042239aa-679f-42f7-a42d-88637a32e15c', 'selectByProposalTitle', 'en', 'course', 'Select by Proposal Title', '7b3eba16-eeaf-442e-b1f4-a316742f57f8', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('a420434b-9a72-4679-b9ab-243bb3c9a51c', 'selectByProposedCourse', 'en', 'course', 'Select by Proposed Course Code', 'c6ee762f-23c8-4ab1-a6d5-ea4bee9f0a29', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('dadea320-82ec-40a5-b2df-892ad16e7ab4', 'startBlankProposal', 'en', 'course', 'Start a blank proposal', '8877af15-52b4-406e-a990-f0934150bd29', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('c7245a2e-4e21-4377-b0c6-2d18b61f881c', 'copyApprovedCourse', 'en', 'course', 'Copy an approved course', '0a55813b-dff8-468f-8b86-9a7af58f3ccd', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('dfece10c-dd86-43fb-889e-7ed734700bf9', 'copyProposedCourse', 'en', 'course', 'Copy a proposed course', 'ae42757b-ecbc-472b-af79-2c07c7315797', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('a70ec981-ed64-46e6-96f2-85cedd53ac95', 'useCurriculumReview', 'en', 'course', 'Use curriculum review process', 'eb57bdb8-a374-45e9-8789-64bb516205c7', 12)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('9ec9ee4d-2c5e-4368-8555-4c991671234a', 'modifyCourseNoVersion', 'en', 'course', 'Modify this Version', 'SYSGUID()', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('a08126d2-d4a2-4ff3-8720-800df5af2588', 'modifyCourseSubTitle', 'en', 'course', 'What kind of modification?', 'SYSGUID()', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('a0e2eb3b-ea48-42a1-bf1a-f1910d9f06e9', 'modifyCourseWithVersion', 'en', 'course', 'Modify with a new version', 'SYSGUID()', 2)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('d15f6ed6-9c78-49ab-a26a-adf8fd30774a', 'cluLOCategoryBrowse', 'en', 'course', 'Browse for categories', '35D03F03-EEFC-B979-A43C-E5A9D80F31CE', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('bcf56fa7-c770-4c8a-a7b7-ee376030e1d3', 'cluLOCategoryBrowsePopup', 'en', 'course', 'Select Categories', '427D3BAF-60D3-FE6F-12C7-BBF77B02AC80', 1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','e04bbf7e-dee9-4e7c-9e66-2d7960fca729','en','button.activate','Activate','ef15d36a-a577-4cdc-9056-3239ebc57f97',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','48159057-60e0-41e7-80f4-477d05348a71','en','button.approve','Approve','bf72c658-445e-490f-9fd5-b1376d2a23cb',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','685c3e84-1c02-44e2-9135-ecea8e96c296','en','comments.button','Comments','0a52bff3-033c-4c22-903a-e1879365af58',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','47cafea3-3a5f-497e-9250-31a3d18d4908','en','catalogInformation.catalogDescr','Catalog Description','3fff0599-be29-4f37-adff-cec431e97ad0',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','0ff9e066-c7a6-4fc0-a597-34aa08aab843','en','catalogInformation.catalogPublicationTargets','Publication Targets','8491bc6c-2ea2-490b-a3e1-59abac3327c7',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','3706b884-76a1-488a-81d3-5373ef3cd221','en','catalogInformation.descr','Program Description','9534ba19-8c7c-4a26-a6cd-3ba976fd7531',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','bd852ca7-bb1e-4fcf-9c43-5656fbd2809d','en','catalogInformation.durationCount','Duration Count','64e51533-de98-4832-b18c-cd7389107004',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','0ab9c900-7542-4121-b4cd-eade41df6595','en','catalogInformation.durationNotes','Duration Notes','139479cc-6262-4b4e-98a5-dff766f6193c',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','51af8b77-adec-4c9a-b54e-d175b5d128a5','en','catalogInformation.intensity','Full Time / Part Time','e0a4002b-c3a4-4079-9be4-6750c38cb10b',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','f465e685-09e5-4448-ad39-6fa0aeded643','en','catalogInformation.publishedInstructors','Core Faculty Members','8ac214e4-7805-4648-93ce-0564423632dc',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','a05f9602-f50a-4964-b141-6d8f293a1356','en','catalogInformation.referenceUrl','More Info','4163dd07-205e-4e4d-8025-5caf3396534d',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','4164fd71-147c-4a36-aea7-68f5378fced7','en','catalogInformation.stdDuration','Duration','fe87a026-30ae-4ce5-981a-936557a0c3c3',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','2cabb1ad-b7ec-49b8-8b0d-88b2036c1723','en','common.cancel','Cancel','a132abac-e1e2-48d2-8ad9-8084d24d34e6',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','e0c82507-d70c-47ea-bd1b-a2ccf690eabe','en','common.edit','Edit','0a57be65-0481-42ff-b218-20b53f75aa18',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','91199148-5ed9-4205-b351-efb3b7524efd','en','common.failedSave','Save Failed. There were validation errors: ${0}','eb136b67-54da-40a7-9472-c65a2a1579c2',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','81566720-9936-4f5c-875b-e15f8cb30f2f','en','common.newProgram','New Program','8625e6b7-4492-48ed-ba1b-c89c34695b09',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','f6b811dc-f76a-4620-8227-f4b13b5aebef','en','common.remove','Remove','e899253c-b504-4937-90d6-157820f672cb',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','6f64f841-61ac-47c0-9ef1-01977ebda66a','en','common.retrievingData','Retrieving Data...','27770fa6-8d8d-4f3b-97ac-f4a7317483d7',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','2637c4b6-af31-44d9-93c4-ed67c8c7f27e','en','common.save','Save','44f5095a-b057-4bbb-9860-d8b44b3193a2',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','ae80723d-ee18-4de6-b070-9465891773ba','en','common.savingData','Saving Data...','89e01c4e-48ed-4d02-b96f-9e3a46bafa49',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','38e28cfb-31e2-4d66-ae16-ba3a920e734f','en','common.status','Program status: ${0}','8fe51206-c202-4ccf-9de4-e61f0b0728cb',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','22de61e9-9bdb-42ac-b89d-92185599ad75','en','common.successfulSave','Save Successful','4aef77ca-9aa2-40ad-a637-b05e78a5a070',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','bd18bd4c-f89f-43d6-b386-1e93a9235e04','en','confirmDialog.text','Do you want to save data?','11775fc5-c216-4ec5-8d25-ac5effccc0d4',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','7e2b9565-107c-4932-ab01-9b8fae8467bc','en','confirmDialog.title','Program Save','5337eaca-2e1d-47c7-9d11-0c06c55079c2',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','405f692c-d26b-4bfb-974e-eba714a238ce','en','link.backCurriculum','Return to Curriculum Management','4dc336f3-ce06-4054-a2fd-fa540b00171f',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','983d9948-b836-44a0-bae3-5a81298307e0','en','link.exit','Exit','f71e3137-fe30-407f-ac07-bbd6ca9ac78f',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','68895314-4373-49c1-9f02-65182a21cf51','en','lo.title','Learning Objectives','10c59f25-bddf-4fc4-b7ff-d494f9144cf2',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','c22d5e0a-3b21-425c-9430-16d7dc406873','en','major.variationFailed','Validation has failed for ${0} specialization','e4cc2a39-dc05-4776-aaed-f46d97342116',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','93a16588-d869-4f88-b61c-4d01413c991b','en','major.variationsFailed','Validation has failed for following specializations: ${0}','33df98f2-be0e-4cbc-bf98-072b63472480',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','758b9681-ecaf-494a-b3e7-a89fe4b944ae','en','managingBodies.curriculumOversightDivision','Curriculum Oversight College','70aade22-1582-47f5-bc1d-b56a5d9bbb6c',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','f8e2e52a-b05f-459d-b126-c0ef9a99dcc7','en','managingBodies.curriculumOversightUnit','Curriculum Oversight Unit','7e6c9c01-4c2b-4274-94e8-d941e103f01a',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','834d2617-3239-4724-9262-95fe7a36d880','en','managingBodies.deploymentDivision','Deployment College','01c2c31c-dfd8-4d00-92e4-63eb525c690b',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','466de0b4-bf63-4b8f-be4d-eab0d949ef14','en','managingBodies.deploymentUnit','Deployment Unit','c4b8984f-525f-44dc-9aaa-2249db0bf005',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','3d5cb750-8171-4c42-b2ac-28010f89389b','en','managingBodies.financialControlDivision','Financial Control College','72c9fc2c-bb99-4587-8682-fc71c32d13d2',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','17e42543-206f-4118-899a-c0062f56d0c2','en','managingBodies.financialControlUnit','Financial Control Unit','15f99362-3975-4336-adb9-ae77c18dec2b',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','891069ee-1bdc-433c-90a9-000c318da91c','en','managingBodies.financialResourcesDivision','Financial Resources College','73053904-71f5-43c7-91ae-ce43479bad9d',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','ef1c877a-a5fb-4ec6-9662-ad695e3c61c1','en','managingBodies.financialResourcesUnit','Financial Resources Unit','c47177b3-f0b6-4385-8556-c957cceedf94',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','5a236918-2676-4dd7-9085-b5de55d41116','en','managingBodies.seeAll','See All Managing Bodies','4d26f449-4121-4a0b-b96b-91da96b7d650',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','385f271d-b9b3-4aee-8b4c-b4f6383cdc78','en','managingBodies.studentOversightDivision','Student Oversight College','c224915d-79c1-4635-8fab-d887cc3c7088',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','cbbd950e-556f-408a-b9df-9b8015f43ed3','en','managingBodies.studentOversightUnit','Student Oversight Unit','91fd9238-aec3-4546-a392-23745bc82830',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','fb1258a0-0882-4d37-9994-d78be73f4ddd','en','majorDiscipline.prevEndProgramEntryTerm','Previous Program Entry End Term','4e81920c-782f-4689-b37c-cc73a2c7e909',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','8611df35-705c-4260-a9c0-6cae7355da39','en','majorDiscipline.prevStartTerm','Previous Program Start Term','4e244bcb-8dcd-4ca6-8674-520a3526e57c',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','174e1dce-3766-460f-b69e-f86db1798673','en','majorDiscipline.prevEndInstAdmitTerm','Previous End Inst Admit Term','db6b1323-fef3-4066-87f5-82a648f87959',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','9c4a0388-00ed-4441-9b18-4953131fca5d','en','majorDiscipline.prevEndTerm','Previous End Term','83028d91-6527-4967-87b3-3494ae4da912',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','2ced3468-5d0a-4888-bcf1-ab630f15bd22','en','program.menu.actions.title','Actions','a4c7af1f-e274-4a66-9465-f0428f0b44a7',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','dd61bcee-84eb-4307-b441-b0dca3fe87d6','en','program.menu.history.title','History','825c0e88-bbf3-4515-a537-09b012b32cc6',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','9a43d462-41ab-45c1-988c-f56a886f9c81','en','program.menu.sections','Program Sections','94805537-2dd9-4927-b907-e0a60e51195c',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','63aca587-31ad-4828-9191-0bb4e1b76782','en','program.menu.sections.proposalInformation','Proposal Information','77b63573-e887-44d5-b619-87ed6f8d3cd6',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','1b7dd024-2f6d-4d0f-9a9a-1a2c0413637b','en','program.menu.sections.proposalChangeImpact','Change Impact','e228a436-fde7-4d9c-bcaa-6027b3c79902',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','6c94a746-c877-468a-b432-34b28b1c54b4','en','program.menu.sections.catalogInfo','Description and Catalog Information','6d01e0d6-a177-4d11-8fda-ad1e51fc1f0b',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','48bd4a1d-2fea-42bc-804a-acf5f44442c8','en','program.menu.sections.learningObjectives','Learning Objectives','2bd03f41-0498-4401-96d8-ad2c93d9fe83',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','44f7ca58-6be8-491c-8165-9b91c22821ac','en','program.menu.sections.managingBodies','Program Managing Bodies','2bb6f031-fd03-4a5e-a9a7-6d82570fa907',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','34a8eef7-26cc-4c8c-b18b-515176635aa9','en','program.menu.sections.programInformation','Key Program Information','f16c7e11-f732-4ec3-87d9-f22ae3ed819e',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','aa04316f-1339-4ae4-b86b-ef60b41d1aa6','en','program.menu.sections.requirements','Program Requirements','89cb31b1-232a-4329-a575-21633cd9947f',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','4f89f301-c56c-4089-bf63-ecd79b449abb','en','program.menu.sections.specializations','Specializations','ceb85d75-fa56-482c-be0f-8dd69175be29',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','c20d27b0-99a6-476f-b7e4-368ef7a09df2','en','program.menu.sections.summary','Program Summary','90f5ccb5-6b66-4ebf-abac-d4f7818543db',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','8b714810-3e36-4522-ac69-6bb9977d174a','en','proposal.menu.sections.summary','Proposal Summary','57263d1d-a6b2-4691-9615-59be2a70a545',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','37093af9-54b8-4be4-b198-3c44f5022add','en','program.menu.sections.collaborators','Authors and Collaborators','02db0c94-5a70-4499-bd0a-ab793882fa19',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','6e036a3b-abbc-4369-a010-479dcc4db62c','en','program.menu.sections.supportingDocuments','Supporting Documents','582d989b-251f-4f51-b92c-55f0e949d8f9',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','fff08227-30e3-4c9b-802a-5024121f3f00','en','program.menu.sections.viewAll','View All Sections','4d1d1985-3298-4507-afc4-86c11ce170ef',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','bb95c81c-824b-4080-aeb3-1027f4335fd8','en','title','Program Actions','96a2b17e-d4c5-4c7e-819d-fc3bb71e805b',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','90d26607-d79f-43c8-a86f-22a8a1cc0425','en','modify','Modify Program','24f04842-de84-4208-81cf-4bcfd1231d2e',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','018a8778-75a5-4978-b5ed-b279340b99e3','en','retire','Retire Program','c948b9d8-a464-49bc-841e-d6e3f57c6de1',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','fdd7c567-2b36-42b6-83db-07ad71f33813','en','modifyVersion','Modify (with new version)','0140a30a-4d89-4664-922c-b2b55d51e82e',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','a90a8698-dadf-45c9-a667-e56226af36a3','en','proposedProgramModification','Proposed Program Modification','87cb4cc1-6fd7-4cfa-a6bf-2369acfd60fc',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','94da7d92-bd4c-428d-956f-19f66c47e831','en','cluProposalTitle','Proposal Title','0bbf8c1b-ad2a-4691-9098-8aa23eef0fb0',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','eb7d971e-4d00-4228-84cf-b4da748a229e','en','cluProposalRationale','Program Rationale','c90b7825-b29a-48f1-8117-eb89e5be8227',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','b6fa2938-e878-4d6b-8225-de741c4f06be','en','cluModificationType','Type of Modification','6f8cb18a-9049-4246-b60f-fdee35fddcbd',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','3a762f07-c1c2-4a4d-84b2-7dec6e68bc6a','en','cluAbstractType','Abstract','cd9ba1cf-c262-4e26-a518-1d14ecf0e500',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','3bff6b8e-4307-41e3-a2ae-e306b5ed34ae','en','cluRelatedCourseChangesType','Related Course Changes','cebf2afb-df34-4799-b670-d7956b240b8b',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','33345493-118b-4cb0-a37e-2f1f80490f20','en','cluImpactedUnitsType','Impacted Units','ed250ee6-5548-443c-9222-6da2923f9d45',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','342a65a4-1839-414f-88e3-691a347dad80','en','cluImpactedArticulationTransferProgramsType','Impacted Articulation Transfer Programs','3532583b-31ae-4c35-b0a3-365709650e89',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','98a3be91-2a1f-4355-baaf-7be69700d5c1','en','cluStudentTransitionPlansType','Student Transition Plans','27a45562-6084-44b8-ae80-b86db60a5936',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','3327c6c7-9226-48bd-816b-1d93f8791c5b','en','programInformation.accreditation','Accreditation','b9d75f4f-e1d4-443e-a4ed-5262787d36a1',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','3d415da6-3abe-4fa1-9457-2c01d7af3f69','en','programInformation.accreditations','Accreditations','4a01658e-a437-4a52-ad69-813e46913b5f',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','1a6093ee-1fef-49da-89bf-4fcbc508a2f1','en','programInformation.activateInstructions','You have indicated that you intend to activate this version of the program. This will replace the currently active version. If you choose to proceed you must update the following items in the previous version of this program.','7fd6254e-6284-4786-9191-37d4616f65d0',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','bb6fc60e-8b3d-41d4-87db-f818edf30cbe','en','programInformation.activateProgram','Activate Program','ad5196c4-f6ee-44e7-ac76-17a19ecf868d',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','b90c29dc-dda3-4518-a655-a1499f498a45','en','programInformation.addAccreditation','Add Accreditation','5f0e0106-6ca2-436e-a50f-77f825ca09d2',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','56bf592b-d499-4102-9b77-2ac6ca606950','en','programInformation.admitTerm','End Inst Admit Term','84c0a518-c1f5-4a79-8556-0bac536f6dec',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','ad1be331-fe90-417a-9865-4bccd65a8924','en','programInformation.approvalDate','Program Approval Date','45fff9bb-80d7-46b1-a93c-a6e1cdf76329',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','074b0a56-1e60-4922-bdc9-3aedc99647a6','en','programInformation.cip2000','CIP 2000','760f0f38-ac64-47f7-9ad6-43af58a42104',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','a4914190-ccd8-4e38-9c6d-7822713fd1be','en','programInformation.cip2010','CIP 2010','7381d3e5-456c-4722-b4a4-697f8660ee27',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','4dca05f8-e8f1-419b-ad6d-bb8eca580298','en','programInformation.classification','Classification','6e456718-2cfa-4930-83c6-72ee07a9cd19',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','0a0e1d29-3675-4f8c-b03a-b450b115b8ba','en','programInformation.code','Code','3c0dc90d-a538-4c2a-bece-08a8df82fcfd',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','eb1a10ed-0e8d-4c88-ae32-6686423f2948','en','programInformation.credentialProgram','Credential Program','8d56f558-c7b2-437a-94b8-3741a19df766',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','46850871-9c80-4cac-9555-36944f3fdd67','en','programInformation.dates','Dates','11cbc2d7-0a02-4c86-8289-7d846cb5b4dc',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','594e013e-e1dd-4d48-a02a-ce90fdfd54ec','en','programInformation.degreeType','Degree Type','d444bc88-cfcf-4a6b-8a5e-4b4d6a3da6d5',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','b5da3ed2-cd8b-4e0c-8097-be5eda608dc4','en','programInformation.enrollTerm','End Program Enroll Term','a4241f9a-01b1-4733-985a-6a74a8be91ba',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','1d0f1d8c-c4e9-4c07-b3b6-6d1b41527436','en','programInformation.entryTerm','End Program Entry Term','a8de1b22-fc9d-4e08-b4c0-2fd1aa0e2d7a',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','734f9384-544b-4554-99d9-db0dced14c01','en','programInformation.hegis','HEGIS','991e1640-1e51-4d96-97e8-77d30020e529',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','4a313800-7f8e-4bde-8bf8-8f78b7fb3c47','en','programInformation.identifyingDetails','Identifying Details','8e97ba25-2ddb-4540-8c5c-a444133f016d',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','0d09183e-955d-4847-859e-e565edb88a05','en','programInformation.institution','Institution','aaa197bd-e3ac-45eb-b38f-1143fb4f3e4a',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','c5f41d9a-3040-45a1-b9be-b445be1a5485','en','programInformation.level','Level','8cde9366-4c11-4365-9533-7367fae2e821',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','5ffffd89-feef-473e-8da3-286b3e0e03fd','en','programInformation.location','Location','2d1049a1-f37a-4495-965d-e303ce2f817e',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','324f9405-fabb-4477-9bce-9a2458ad98f3','en','programInformation.otherInformation','Other Information','e32528c4-497c-4f97-a1e1-d23f238c903e',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','d4a9adea-55f7-42b2-bdf2-c0835939e037','en','programInformation.programTitle','Program Title','8ae4300e-2123-491a-9bd7-755ab3453654',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','7e4e1b2e-bb5a-43ea-8615-07a88d0784a2','en','programInformation.startTerm','Start Term','bdff4051-1018-4bb0-8c9e-ac19252e2501',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','1fa6b03b-a4b4-4d46-bef5-32e5218b5932','en','programInformation.title','Key Program Information','55efa305-ad15-430b-82e2-426e906b7064',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','efde3592-b3e4-46e4-b596-0ac7fc54acef','en','programInformation.titleDiploma','Title Diploma','d99a43c3-dbb1-46f9-9050-7a121973635b',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','6f75aabd-410c-419c-8e14-0f885d92d0f6','en','programInformation.titleFull','Title Full','dc38b551-d945-42dd-bccc-318ae47bad23',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','f7fe0e89-ffa5-4f2a-b96a-6ac60fbe5b7e','en','programInformation.titleShort','Title Short','27a21436-d583-4a25-b927-ae707116ab43',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','2690714e-53ab-4292-881a-9e2f10373b76','en','programInformation.titleTranscript','Title Transcript','2fea99cf-51fe-4d97-ae6d-79e2e39d9ed1',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','1ded25be-4d8e-4627-9064-191da44c3213','en','programRequirements.manageViewPageStep1Title','Step 1: Build and Add Rules','4cf14f2b-06a3-4314-9a4c-d4430647213a',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','f1ceb2f3-81ca-48b4-9152-70a7cb8faf0a','en','programRequirements.manageViewPageStep2Title','Step 2: Combine Rules with Logic','339eab3a-bd8f-47c5-9e3d-c9debc4f8f6a',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','c93fe367-d3a0-46a7-8664-409f970b69c5','en','programRequirements.manageViewPageTitle','Add <*> Rule','5a800c1d-b8a1-43d2-8ce5-ae287211ad23',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','81b2c80e-1831-438e-976f-72d709f0da91','en','programRequirements.summaryViewPageAddRule','Add ${0}','d6aca044-25c0-471c-8dbd-e8f3a8af727e',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','59c4ffec-f6cc-4534-95f7-eafc6b4df9b6','en','programRequirements.summaryViewPageDeleteRequirementDialogMsg','Are you sure you want to delete this requirement?','669f0673-9ba8-410c-9fbe-cdbd136da2d2',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','dfcee2bf-54f4-4ec7-81d5-5adcbfd6fada','en','programRequirements.summaryViewPageDeleteRequirementDialogTitle','Delete Requirement','3e2de6d5-1977-461b-94ce-b2d59ff1cfa1',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','67ac2805-d9c7-42d8-b02f-9a7aa024c6f2','en','programRequirements.summaryViewPageDeleteRuleDialogMsg','Are you sure you want to delete this rule?','408ad8e0-c513-4681-9c1e-9c5adf6bbf55',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','00cd9917-0218-4fdd-a0bc-e0c3191c4505','en','programRequirements.summaryViewPageDeleteRuleDialogTitle','Delete Rule','a9a1247e-89e5-4a92-bbae-fc605a1071fb',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','27e19700-ea41-439d-a396-3dad0ddb6b84','en','programRequirements.summaryViewPageNoRule','No ${0} currently exist for this program','963a484a-a92e-4693-8c68-6b2a49ad18b1',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','94f891ba-228a-48a8-88a3-8e556c4627c1','en','programRequirements.summaryViewPageTitle','Program Requirements','45680816-ec39-467a-88d8-a3170ccd854b',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','5bd45343-4bdd-4bc1-b941-8551b22f523f','en','sideBar.dialog.title','Edit Dates','4bb909dd-ce34-485b-84b5-18739045096f',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','684170d8-f49b-41c3-b10f-e29db6b56ef9','en','sideBar.form.lastReviewDate','Last review date','003dc16e-9695-4ac3-9f9f-17b997dabfed',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','32098f51-a5d5-40b0-bda6-3df734bc1b31','en','sideBar.form.scheduledReviewDate','Scheduled review date','95339702-b7d8-4a1b-8dfd-0182f8ee8457',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','ffb1b881-a7d0-4374-864d-f97d6ea09c83','en','sideBar.history','HISTORY','eca4180c-f960-431b-ab10-4d7731b010ad',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','4025e190-7a7c-4ba8-9387-825a0e85be9b','en','sideBar.lastReviewDate','Last review date:','436ea2da-0cce-481a-beb3-0567393ab2a3',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','31e34a00-055b-4145-83fc-d027e4bfaaa0','en','sideBar.programLastUpdated','Program last updated:','f8923626-9c99-4103-ac88-f18476318b50',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','2d8eae37-e526-4118-8c0f-6af1ab5d2c25','en','sideBar.scheduledReviewDate','Scheduled review date:','e848c723-3fe3-4b9d-8835-2289d93b17be',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','7c3a8edb-7b80-4414-a7b1-f4731783a553','en','sideBar.version','Version: ${0}','364f9570-aa7b-4fcd-aa58-1a13dadd4128',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','a0a66ecc-5b7c-428b-9006-b82abe890f35','en','sideBar.viewHistory','View version history','dc577705-f1c6-46d2-9454-4f5b46177bd1',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','af6c6bc5-9d4f-49cc-bba3-fd7887fd8051','en','active','Active','86a6fb20-040f-4fef-aed1-57f84c943444',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','dbd099de-6a8a-4c3d-bffc-a2934189411a','en','approved','Approved','5bc46af9-e1a9-4b5f-b0d9-2a4df55c7e2d',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','050d08ac-771b-4cd2-8821-3275b140bf36','en','draft','Draft','40256e44-cc3a-4307-a17e-8b446151a584',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','ec8e677b-6d05-4179-907e-7f9974bf1b09','en','notapproved','NotApproved','b07a9d29-ac40-4016-87d7-2c2f0ea9dd69',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','3be0da43-f4ed-459d-b304-12084ced5b68','en','superseded','Superseded','2ebd5d0c-7169-49d4-a2eb-fded4509cff2',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','ef5923f5-fec8-4838-b55b-e84b5bed0f06','en','variation.menu.sections','Variation Sections','186cd0da-abe1-4a0d-a14c-042f2fbbfeb8',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','d0e60707-16c4-43e7-b80d-92e5825bca4c','en','variation.menu.sections.variationInformation','Key Specialization Information','8513c1d4-5b8f-4986-a499-1e4b643b2547',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','9cee0a7e-6bc5-4863-8853-cf54c8bd63db','en','variation.new','New Variation','bb312d51-f26e-4b34-8c13-e27db37b0435',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','c34a4e9e-d564-4641-b751-1f84304b3dca','en','variation.parentProgram','Parent Program:','afe74969-fac4-41f3-98c9-bd4a5a6477f3',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','d58f2b40-3bf8-4681-a3b3-a3b9ed1292e8','en','variation.summary','Specialization Summary','305cf5c3-c3b1-46b2-88f5-8eada5305680',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','cc341ef1-7877-4af1-ac32-e1335e767e86','en','variation.title','Specialization of ${0}','e58ae6dd-7e2d-49b7-81a7-87c4eb984ba3',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','c4a65992-f7cc-4c59-a9ee-36765ff00289','en','variationCatalogInformation.descr','Specialization Description','7141ebc1-dff5-441f-8995-0bfdb223b1e5',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','897e75ee-f582-406f-9a07-114dd863fd92','en','variationInformation.button.addSpecialization','Add Specialization','5d372e5d-157b-46cb-a184-415b6b6e8bce',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','7b0b52cb-b72b-4c7a-b0fa-be988cb49900','en','variationInformation.title','Key Specialization Information','460d15e5-ec08-41f2-9865-8cbd4b537914',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','0628ff19-8f53-48f8-8032-9e1f3cc79268','en','variationInformation.variationTitle','Specialization Title','1b15ff35-9da4-47c3-a179-6f9f6ea6a55c',1)
/
INSERT INTO KSMG_MESSAGE (GRP_NAME, ID, LOCALE, MSG_ID, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('program','45fd7c16-52d7-456c-8fd2-4bb33ff6699a','en','programSpecialization.instructions','Completion of a specialization is required for this program','250a9729-5012-45ba-baff-8afd9b1d6a72',1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('3bf66599-5f4a-4528-80cd-d8245a1f57db', 'cluAuthorsAndCollaborators', 'en', 'course', 'Authors '||chr(38)||' Collaborators', '8597182f-12f7-4212-8929-5b203d2d04aa', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('D9E0819F1C514D03B80772D22E6E2D2C', 'collaboratorNameSuggest', 'en', 'course', 'Name', '7B5F42AD3B314226813877A185A2D5F0', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('d073bc38-8c4d-48b8-b240-35ecea572c79', 'crossListedAndJoints', 'en', 'course', 'Crosslisted/Jointly Offered Courses', '64fcbc4a-fe76-4ed7-b334-ff31ade5a87f', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('3D4442F02C504923AEBCC82DA20FA245', 'retireCourseSubTitle', 'en', 'course', 'Which type of retire action do you want?', 'C0C3601B6BD84A048B31DEDE8250F4FE', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('B8A65FA79D1D4A758C873A28E04966D5', 'retireCourseAdmin', 'en', 'course', 'Administrative Retire', '639B27CC80F54C91A038EC34B5073FA9', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('7DC0CDB8E21E46539AE8D8A1576FF0F7', 'retireCourseByProposal', 'en', 'course', 'Retire by Proposal', '11A4D017C52C4DE8A69A542967BD2B77', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('8AE6CFE94E8A45E89B217C8CA300B0EA', 'retireCourseWidgetTitle', 'en', 'course', 'Retire Course', 'AAEBFD9E504646B4886B69483F9565C2', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('02989C6474164E06BC107884E4F765B1', 'retireCourseProposalTitle', 'en', 'course', 'Proposal Title', 'A28465B4BDF847BE9206367C58011DFB', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('5FD4DD432FEB4062874F05F86BF140A5', 'retireCourseProposalTitle-instruct', 'en', 'course', 'This title is used for identifying the proposal through the approval process.', '0BCF63A9063D4A8D948F52D06C1C7684', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('644E21680A474D5BA3E829514EB91BDD', 'courseProposedProposalTitle', 'en', 'course', 'Proposal Title', 'AF6D3266E1004ACC90AA44F1D08E97F0', 1) 
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('37B066108559462F8580112E7165F111', 'courseProposedProposalTitle-instruct', 'en', 'course', 'This title is used for identifying the proposal through the approval process.', '723107AC3E54436A82AEF4775DDBD79D', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('83079F0F61B3490F98F9FA327CA36FB8', 'courseProposedRetireInformation', 'en', 'course', 'Retire Proposal Information', '98B2750755234ADFB7A5BDA611D613FD', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('7EF0BBBAEE6E44CB992932AFE4B196CA', 'courseProposedEndTerm', 'en', 'course', 'End Term', 'CF8B9C56F75E4EF398699808C165DCE7', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('57CAC53F828B47738003FC599543BECE', 'courseProposedEndTerm-instruct', 'en', 'course', 'The last term in which the course can be offered.', '1F37720DC13D4AF9AF50E94CCE6EB8A0', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('0C92DCA81A0F49F586AD57E8C12BA480', 'courseProposedLastTermOffered', 'en', 'course', 'Last Term Offered', 'A892230858174D28A007F8B4493A68C9', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('710EE53064BA4B46854C6642446620D6', 'courseProposedLastTermOffered-instruct', 'en', 'course', 'Select the last term this course was or will be offered, if known.', 'E809074C0FAC48AA89D947CA780B27DB', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('9196B525BD614463AE79C6B3806667A3', 'courseProposedLastCatalogYear', 'en', 'course', 'Last Course Catalog Publication Year', '16A3A11FF6E54FF5AB55BE5F18B0B813', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('488D129E24624EFFAEEF03064B09C77C', 'courseProposedLastCatalogYear-instruct', 'en', 'course', 'Last academic year for which this course will be published in the course catalog.', '8C18BFD3190041B295F6C287A2C930FB', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('CAF783BB01C846298C681EB1FCD4B0B9', 'courseOtherComments', 'en', 'course', 'Other Comments', '381EDA4D3A5D4EF5B2ACE26D1CFAD576', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('305fb5d3-5f53-456b-9069-3241f57b988e', 'courseProposeRetireSingleProposal', 'en', 'course', 'Unable to submit your proposal to retire a course into workflow. Another user is already working on a proposal to retire this course and only one proposal to retire a course is allowed at a time. To proceed with this retire course proposal, you will need to identify who is working on the proposal and have them cancel it.', '45ba8f8d-2b9b-4201-8b0d-1eac4585d178', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('644E21681A474D5BA3E829514EB91BDD', 'xlsFormat', 'en', 'common', '.xls (Microsoft Excel)', null, 1) 
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('a6574d51-ad24-4049-998f-ff33e5f7eadc', 'validation.lookup.cause', 'en', 'validation', 'The selected value is causing another field to be invalid', '718c04e4-3fa8-4738-8322-a0aa47f9e0de', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('a6574d51-ad24-4049-998f-ff33e5f7eadb', 'validation.lookup', 'en', 'validation', 'Invalid value', '718c04e4-3fa8-4738-8322-a0aa47f9e0ce', 1)
/

-- ###################
-- ##### UPDATES #####
-- ###################

UPDATE KSMG_MESSAGE 
SET MSG_VALUE = 'Last term in which the course can be offered.' 
WHERE MSG_ID = 'cluEndTerm-instruct'
/
UPDATE KSMG_MESSAGE 
SET MSG_VALUE = 'Duration Count' 
WHERE MSG_ID = 'cluDurationQuantity' 
AND GRP_NAME = 'course' 
AND LOCALE = 'en'
/
UPDATE KSMG_MESSAGE 
SET msg_value = 'This is the organization(s) responsible for the content of the course. This selection will determine <br/>the workflow/approval process for this <br>proposal.' 
WHERE msg_id = 'cluCurriculumOversight-instruct' 
AND locale = 'en' 
AND grp_name = 'course'
/
UPDATE KSMG_MESSAGE 
SET MSG_VALUE = 'Browse Academic Programs' 
WHERE MSG_ID = 'browseProgram' 
AND LOCALE = 'en'
/
UPDATE KSMG_MESSAGE 
SET MSG_VALUE = 'Create an Academic Program' 
WHERE MSG_ID = 'createProgram' 
AND LOCALE = 'en'
/
UPDATE KSMG_MESSAGE 
SET msg_value = 'Use this tool to build a bullet-point style outline with a maximum of ${maxLength} characters for each learning objective.<br/>Or click the following link to search for existing learning objectives.' 
WHERE msg_id = 'cluLearningObjectives-instruct'
/
UPDATE KSMG_MESSAGE 
SET msg_value = 'Must be ${minLength} digit number' 
WHERE msg_id = 'cluCourseNumber-constraints'
/
UPDATE KSMG_MESSAGE 
SET msg_value = 'Must be ${minLength} letter code' 
WHERE msg_id = 'cluSubjectCode-constraints'
/
UPDATE KSMG_MESSAGE 
SET msg_value = '${maxLength} characters max for each Learning Objective' 
WHERE msg_id = 'cluLOInstructions'
/
UPDATE KSMG_MESSAGE 
SET msg_value = 'My Action List' 
WHERE msg_id = 'actionList'
/
UPDATE KSMG_MESSAGE 
SET msg_value = 'Modify Course' 
WHERE msg_id = 'cluModifyItem'
/
UPDATE KSMG_MESSAGE 
SET msg_value = 'Retire Course' 
WHERE msg_id = 'cluRetireItem'
/
UPDATE KSMG_MESSAGE 
SET msg_value = 'Add Learning Objective' 
WHERE msg_id = 'cluAddLOs' 
AND grp_name = 'course' 
AND locale = 'en'
/
UPDATE KSMG_MESSAGE 
SET MSG_VALUE = 'Propose a new course or start a new program.' 
WHERE MSG_ID = 'createDesc'
/