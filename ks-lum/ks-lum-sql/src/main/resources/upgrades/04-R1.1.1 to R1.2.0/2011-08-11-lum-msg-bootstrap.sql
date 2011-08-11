insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('6b2fc022-cd8a-471c-9381-c35ffef1f8cd', 'validation.programManagingBodiesMatch', 'en', 'validation', '{0} is not a child of {1}.', SYS_GUID(), 1)
/

update ksmg_message set msg_value = 'This is the organization(s) responsible for the content of the course. This selection will determine <br/>the workflow/approval process for this <br>proposal.' where msg_id = 'cluCurriculumOversight-instruct' and locale = 'en' and grp_name = 'course'
/

UPDATE 
		KSMG_MESSAGE 
SET 
		MSG_VALUE = 'Use curriculum review process' 
WHERE 
		MSG_ID = 'useCurriculumReview' AND GRP_NAME = 'course'
/