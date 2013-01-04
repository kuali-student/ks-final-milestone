 
UPDATE KRIM_PERM_T SET NM='edit CourseInfo transcriptTitle' WHERE PERM_ID='3105'
/
UPDATE KRIM_PERM_T SET NM='edit CourseInfo unitsContentOwner' WHERE PERM_ID='3106'
/
UPDATE KRIM_PERM_T SET NM='edit CourseInfo descr' WHERE PERM_ID='3107'
/
UPDATE KRIM_PERM_T SET NM='edit CourseInfo duration' WHERE PERM_ID='3108'
/
UPDATE KRIM_PERM_T SET NM='edit Organization orgInfo' WHERE PERM_ID='3109'
/
UPDATE KRIM_PERM_T SET NM='edit Organization orgOrgRelationInfo' WHERE PERM_ID='3110'
/
UPDATE KRIM_PERM_T SET NM='edit Organization OrgPositionRestrictionInfo' WHERE PERM_ID='3111'
/
UPDATE KRIM_PERM_T SET NM='edit Organization orgPersonRelationInfo' WHERE PERM_ID='3112'
/
UPDATE KRIM_PERM_T SET NM='Blanket Approve Allow All', DESC_TXT='Opens Access to all users and for all route states so we can set different perms for different states.  See other KS-SYS perms.' WHERE PERM_ID='2100'
/
UPDATE KRIM_PERM_T SET NM='Blanket Approve when Routed', DESC_TXT='Allows specified users to BA when the route status is Enroute' WHERE PERM_ID='3103'
/
UPDATE KRIM_PERM_T SET NM='Blanket Approve when Saved', DESC_TXT='Allows specified users to BA when the route status is Saved' WHERE PERM_ID='3104'
/

UPDATE KRIM_RSP_T SET NM='DepartmentReview' WHERE RSP_ID='300'
/
UPDATE KRIM_RSP_T SET NM='CollegeReview' WHERE RSP_ID='301'
/
UPDATE KRIM_RSP_T SET NM='DivisionReview' WHERE RSP_ID='303'
/
UPDATE KRIM_RSP_T SET NM='SenateReview' WHERE RSP_ID='304'
/
UPDATE KRIM_RSP_T SET NM='PublicationReview' WHERE RSP_ID='305'
/
UPDATE KRIM_RSP_T SET NM='PublicationDecisionReview' WHERE RSP_ID='306'
/
UPDATE KRIM_RSP_T SET NM='DocOrgReview' WHERE RSP_ID='307'
/

