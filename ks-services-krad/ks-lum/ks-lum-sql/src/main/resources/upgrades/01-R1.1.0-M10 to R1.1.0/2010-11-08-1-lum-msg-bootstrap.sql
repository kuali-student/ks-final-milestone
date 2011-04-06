update KSMG_MESSAGE set MSG_VALUE='Create a Major Discipline' where MSG_ID='createProgram'
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('b0101d5a-01f5-4586-ba83-5935b7d4c162', 'validation.endDateAfterStartDate', 'en', 'validation', 'End Date must be after Start Date', 'F3CE669D-D99A-9F22-1E81-049E668B9EFE', 1)
/
