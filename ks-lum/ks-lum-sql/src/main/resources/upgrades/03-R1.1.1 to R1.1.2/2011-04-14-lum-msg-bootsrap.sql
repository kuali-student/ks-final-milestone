-- Program cross field validation warning messages

insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('ebd58a7d-2dfc-45ce-bb72-f5c270bbfe1f', 'validation.majorEndTermRequiresVariationEndTerm', 'en', 'validation', 'The value in this field must be equal to or greater than the values in the corresponding fields in the associated specializations in order to submit this application.', 'SYS_GUID()', 3)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('673088c1-511b-48eb-aff0-797545321164', 'validation.variationStartTerm', 'en', 'validation', 'The value in this field must equal to or greater than the values in the corresponding fields in the parent major in order to submit this modification. Either change the value in this field or change the corresponding fields in the parent major.', 'SYS_GUID()', 3)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('d818d3ed-2c39-4454-991c-c28350229e25', 'validation.variation', 'en', 'validation', 'Items in this specialization conflict with values in items in the parent major. Click on the specialization name to modify and resolve the conflict. You may also change the value in the parent major.', 'SYS_GUID()', 4)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('b7f303e7-215b-4155-b637-db414a3cfffa', 'validation.majorEndTermRequiresVariationStartTerm', 'en', 'validation', 'The value in this field must be equal to or less than the values in the corresponding fields in the associated specializations in order to submit this application.', 'SYS_GUID()', 3)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('3fb52d13-39cb-42ce-b4b4-352ba3654cf9', 'validation.variationEndTerm', 'en', 'validation', 'The value in this field must equal to or earlier than the values in the corresponding fields in the parent major in order to submit this modification. Either change the value in this field or change the corresponding fields in the parent major.', 'SYS_GUID()', 5)
/
