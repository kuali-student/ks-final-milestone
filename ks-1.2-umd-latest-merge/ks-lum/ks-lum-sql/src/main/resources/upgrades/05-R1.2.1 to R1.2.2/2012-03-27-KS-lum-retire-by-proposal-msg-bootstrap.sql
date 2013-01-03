-- KSLAB 2583 Retire By Proposal Labels
-- retireCourseProposalTitle
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('644E21680A474D5BA3E829514EB91BDD', 'courseProposedProposalTitle', 'en', 'course', 'Proposal Title', 'AF6D3266E1004ACC90AA44F1D08E97F0', 1) 
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('37B066108559462F8580112E7165F111', 'courseProposedProposalTitle-instruct', 'en', 'course', 'This title is used for identifying the proposal through the approval process.', '723107AC3E54436A82AEF4775DDBD79D', 1)
/
-- courseProposedRetireInformation - Summary Section Label KSCM-977 KSCM-1744
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('83079F0F61B3490F98F9FA327CA36FB8', 'courseProposedRetireInformation', 'en', 'course', 'Retire Proposal Information', '98B2750755234ADFB7A5BDA611D613FD', 1)
/
-- courseProposedEndTerm
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('7EF0BBBAEE6E44CB992932AFE4B196CA', 'courseProposedEndTerm', 'en', 'course', 'End Term', 'CF8B9C56F75E4EF398699808C165DCE7', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('57CAC53F828B47738003FC599543BECE', 'courseProposedEndTerm-instruct', 'en', 'course', 'The last term in which the course can be offered.', '1F37720DC13D4AF9AF50E94CCE6EB8A0', 1)
/
-- courseProposedLastTermOffered
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('0C92DCA81A0F49F586AD57E8C12BA480', 'courseProposedLastTermOffered', 'en', 'course', 'Last Term Offered', 'A892230858174D28A007F8B4493A68C9', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('710EE53064BA4B46854C6642446620D6', 'courseProposedLastTermOffered-instruct', 'en', 'course', 'Select the last term this course was or will be offered, if known.', 'E809074C0FAC48AA89D947CA780B27DB', 1)
/
-- courseProposedLastCatalogYear
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('9196B525BD614463AE79C6B3806667A3', 'courseProposedLastCatalogYear', 'en', 'course', 'Last Course Catalog Publication Year', '16A3A11FF6E54FF5AB55BE5F18B0B813', 1)
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('488D129E24624EFFAEEF03064B09C77C', 'courseProposedLastCatalogYear-instruct', 'en', 'course', 'Last academic year for which this course will be published in the course catalog.', '8C18BFD3190041B295F6C287A2C930FB', 1)
/
-- otherComments
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('CAF783BB01C846298C681EB1FCD4B0B9', 'courseOtherComments', 'en', 'course', 'Other Comments', '381EDA4D3A5D4EF5B2ACE26D1CFAD576', 1)
/
--preventSubmission of more than one retire proposal
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('305fb5d3-5f53-456b-9069-3241f57b988e', 'courseProposeRetireSingleProposal', 'en', 'course', 'Unable to submit your proposal to retire a course into workflow. Another user is already working on a proposal to retire this course and only one proposal to retire a course is allowed at a time. To proceed with this retire course proposal, you will need to identify who is working on the proposal and have them cancel it.', '45ba8f8d-2b9b-4201-8b0d-1eac4585d178', 1)
/