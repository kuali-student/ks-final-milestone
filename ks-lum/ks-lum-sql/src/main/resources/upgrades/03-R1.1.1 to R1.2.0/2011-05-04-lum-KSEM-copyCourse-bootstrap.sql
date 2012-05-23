
--Add in new UI messages
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('b07aa879-1323-4e4b-8ef6-d50b7cc5cf48', 'cluCopyItem', 'en', 'course', 'Copy to a New Course Proposal', '466bdb41-04d5-451a-a744-4fb1be53802f', 1)
/

--Bad data in LOs was causing validation errors on copy
UPDATE KSLO_RICH_TEXT_T SET FORMATTED='To understand the processes that control the earth''s climate system over multiple time scales', PLAIN='To understand the processes that control the earth''s climate system over multiple time scales' WHERE ID='REF_LO_RT_GEOG123_2'
/
UPDATE KSLO_RICH_TEXT_T SET FORMATTED='To understand human society''s role in regulating climate', PLAIN='To understand human society''s role in regulating climate' WHERE ID='REF_LO_RT_GEOG123_4'
/
UPDATE KSLO_RICH_TEXT_T SET FORMATTED='Understand and explain basic concepts concerning the science of measuring the''size and shape of the earth', PLAIN='Understand and explain basic concepts concerning the science of measuring the''size and shape of the earth' WHERE ID='REF_LO_RT_GEOG170_1'
/
UPDATE KSLO_RICH_TEXT_T SET FORMATTED='To understand the basic concepts of cartography and how to design a thematic map''using statistical data, charts, and graphs', PLAIN='To understand the processes that control the earth''s climate system over multiple time scales' WHERE ID='REF_LO_RT_GEOG170_5'
/

insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('746a2d9f-c3ec-4efe-9f8f-eb95f9329191', 'startProposal', 'en', 'course', 'Start Proposal', '8abdae27-9bc6-4306-8e2b-e1232421f9c2', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('e20e11df-721b-4b38-91dc-0658230e5186', 'createCourseSubTitle', 'en', 'course', 'How would you like to start?', '26d9ef23-8aeb-4166-b0b7-3279c54958a2', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('3d1666a2-9319-489f-b5d3-45da8d752b62', 'createCourseSubTitle-instruct', 'en', 'course', 'You can choose to start a blank proposal with no pre-filled data, or copy an existing course/proposal and then edit the copied data to meet your needs.', '6566c0b7-2e80-4383-afec-41c1a4a3afa3', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('0abc9368-f7fe-4ce8-bd10-39a96258fd1d', 'createCourseSubTitle-help', 'en', 'course', '<p>If you are looking to copy a particular course or proposal but cannot find it using the fields here, click Cancel and use either the Course Catalog or Course Search to find it.</p>', 'c2b76ae1-280b-4835-ba77-21e0126a3c04', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('a25e0d8d-a0b8-43a9-b03d-59a766d24c42', 'courseToCopy', 'en', 'course', 'Course to Copy', 'ff50b112-ad14-43e8-8ec1-2d9b5b5892b0', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('de03de36-c44f-4a98-a779-7f3296c2150d', 'courseInvalidValue', 'en', 'course', 'Course - invalid value', '8e4a2b74-f8b5-4315-850a-0e88b8807957', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('b2be5632-643b-42ca-ac83-f6e8c3c11493', 'proposalInvalidValue', 'en', 'course', 'Proposal - invalid value', 'c876546f-526e-47c9-9fd3-367e94ab7aa0', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('d6c5ef04-999b-4f2a-bc54-ec823716be7c', 'proposalToCopy', 'en', 'course', 'Proposal to Copy', 'ef268c27-277f-4336-b690-a5beca41d3a1', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('c0b3b2a5-d63d-4519-9530-0f37779a9a85', 'courseInvalidValue', 'en', 'course', 'Course - invalid value', 'e27dee7c-5fb5-4f85-b075-f2319cb8a996', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('d17d3010-ca8f-455f-bb74-80442529fcce', 'selectByCourseCode', 'en', 'course', 'Select by Course Code', '8d9b30be-0dfa-411d-bea1-acb0f7a1e7ad', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('c7275985-accd-4246-ba53-14737c6a4fc0', 'selectByCourseTitle', 'en', 'course', 'Select by Course Title', '3a272c44-5fed-48ef-a696-ddedbc80d74c', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('042239aa-679f-42f7-a42d-88637a32e15c', 'selectByProposalTitle', 'en', 'course', 'Select by Proposal Title', '7b3eba16-eeaf-442e-b1f4-a316742f57f8', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('a420434b-9a72-4679-b9ab-243bb3c9a51c', 'selectByProposedCourse', 'en', 'course', 'Select by Proposed Course', 'c6ee762f-23c8-4ab1-a6d5-ea4bee9f0a29', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('dadea320-82ec-40a5-b2df-892ad16e7ab4', 'startBlankProposal', 'en', 'course', 'Start a blank proposal', '8877af15-52b4-406e-a990-f0934150bd29', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('c7245a2e-4e21-4377-b0c6-2d18b61f881c', 'copyApprovedCourse', 'en', 'course', 'Copy an approved course', '0a55813b-dff8-468f-8b86-9a7af58f3ccd', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('dfece10c-dd86-43fb-889e-7ed734700bf9', 'copyProposedCourse', 'en', 'course', 'Copy a proposed course', 'ae42757b-ecbc-472b-af79-2c07c7315797', 12)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('a70ec981-ed64-46e6-96f2-85cedd53ac95', 'useCurriculumReview', 'en', 'course', 'Use curriculum review process for the course', 'eb57bdb8-a374-45e9-8789-64bb516205c7', 12)
/
