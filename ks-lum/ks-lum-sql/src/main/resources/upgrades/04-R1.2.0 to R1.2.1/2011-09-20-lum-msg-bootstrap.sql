update 	ksmg_message 
set 	msg_value = 'Use this tool to build a bullet-point style outline with a maximum of ${maxLength} characters for each learning objective.<br/>Or click the following link to search for existing learning objectives.'
where 	msg_id = 'cluLearningObjectives-instruct'
/

update 	ksmg_message 
set 	msg_value = 'Must be ${minLength} digit number'
where 	msg_id = 'cluCourseNumber-constraints'
/

update 	ksmg_message 
set 	msg_value = 'Must be ${minLength} letter code'
where 	msg_id = 'cluSubjectCode-constraints'
/

update 	ksmg_message 
set 	msg_value = '${maxLength} characters max for each Learning Objective'
where 	msg_id = 'cluLOInstructions'
/

update 	ksmg_message 
set 	msg_value = 'My Action List'
where 	msg_id = 'actionList'
/