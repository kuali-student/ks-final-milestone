
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