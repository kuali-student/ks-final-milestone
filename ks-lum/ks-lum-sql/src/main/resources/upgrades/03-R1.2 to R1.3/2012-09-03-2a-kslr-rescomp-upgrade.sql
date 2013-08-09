-- KSLR_RESCOMP updates
update KSLR_RESCOMP set KSLR_RESCOMP.NAME = 'Percentage' where KSLR_RESCOMP.ID = 'kuali.resultComponent.grade.percentage'
/

-- KSLR_RESCOMP inserts
insert into KSLR_RESCOMP (ID, VER_NBR, NAME, STATE, TYPE, OBJ_ID) values ('kuali.resultComponent.degree.ma', 0, 'Master of Arts', null, 'kuali.resultComponentType.degree', '81e792b9-b794-43e5-8b94-fd73d18a6029')
/
insert into KSLR_RESCOMP (ID, VER_NBR, NAME, STATE, TYPE, OBJ_ID) values ('kuali.resultComponent.degree.msc', 0, 'Master of Science', null, 'kuali.resultComponentType.degree', 'f5637e2a-640f-4ef0-b55b-982b127780d3')
/
insert into KSLR_RESCOMP (ID, VER_NBR, NAME, STATE, TYPE, OBJ_ID) values ('kuali.resultComponent.degree.mmus', 0, 'Master of Music', null, 'kuali.resultComponentType.degree', 'df816df6-5e1f-49e6-bdf7-8b9df55e55c4')
/
insert into KSLR_RESCOMP (ID, VER_NBR, NAME, STATE, TYPE, OBJ_ID) values ('kuali.resultComponent.degree.phd', 0, 'Doctor of Philosophy', null, 'kuali.resultComponentType.degree', 'bdbefe12-be05-46ce-9ab8-e5656646f7b8')
/
insert into KSLR_RESCOMP (ID, VER_NBR, NAME, STATE, TYPE, OBJ_ID) values ('kuali.resultComponent.degree.edd', 0, 'Doctor of Education', null, 'kuali.resultComponentType.degree', 'c9ea9641-05d0-4f4b-abab-4b1ff6c24ea7')
/

INSERT INTO KSLR_RESCOMP (VER_NBR, EFF_DT, STATE, NAME, OBJ_ID, EXPIR_DT, RT_DESCR_ID, CREATETIME, UPDATEID, CREATEID, ID, UPDATETIME, TYPE) VALUES (0, null, 'Active', null, '3132e3b2-a5a0-41e5-a99f-f5d5a0496d93', null, null, TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), null, null, 'kuali.creditType.credit.degree.1-8', TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), 'kuali.resultComponentType.credit.degree.range')
/
INSERT INTO KSLR_RESCOMP (VER_NBR, EFF_DT, STATE, NAME, OBJ_ID, EXPIR_DT, RT_DESCR_ID, CREATETIME, UPDATEID, CREATEID, ID, UPDATETIME, TYPE) VALUES (0, null, 'Active', null, '222656f4-e252-4443-8026-87cc69ff4cd0', null, null, TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), null, null, 'kuali.creditType.credit.degree.2-4', TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), 'kuali.resultComponentType.credit.degree.range')
/
INSERT INTO KSLR_RESCOMP (VER_NBR, EFF_DT, STATE, NAME, OBJ_ID, EXPIR_DT, RT_DESCR_ID, CREATETIME, UPDATEID, CREATEID, ID, UPDATETIME, TYPE) VALUES (0, null, 'Active', null, '39706d4b-8f5f-483a-ab8f-85fbaa2da414', null, null, TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), null, null, 'kuali.creditType.credit.degree.1-5', TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), 'kuali.resultComponentType.credit.degree.range')
/
INSERT INTO KSLR_RESCOMP (VER_NBR, EFF_DT, STATE, NAME, OBJ_ID, EXPIR_DT, RT_DESCR_ID, CREATETIME, UPDATEID, CREATEID, ID, UPDATETIME, TYPE) VALUES (0, null, 'Active', null, '8f6f8aac-b67c-4e82-b061-114fe13723a1', null, null, TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), null, null, 'kuali.creditType.credit.degree.2-3', TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), 'kuali.resultComponentType.credit.degree.range')
/
INSERT INTO KSLR_RESCOMP (VER_NBR, EFF_DT, STATE, NAME, OBJ_ID, EXPIR_DT, RT_DESCR_ID, CREATETIME, UPDATEID, CREATEID, ID, UPDATETIME, TYPE) VALUES (0, null, 'Active', null, '33ce5106-ca7b-4d15-892f-b4e8747ea7e4', null, null, TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), null, null, 'kuali.creditType.credit.degree.4-6', TO_TIMESTAMP('2011-08-10 00:00:00.0','yyyy-MM-dd HH24:MI:SS.FF'), 'kuali.resultComponentType.credit.degree.range')
/