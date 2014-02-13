
delete from ksen_lrc_rvg_result_value where result_value_id in ('A', 'A+', 'A-', 'B', 'B+', 'B-', 'C', 'C+', 'C-', 'D', 'D+', 'D-', 'F')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.letter.a', 'kuali.resultComponent.grade.letter')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.letter.b', 'kuali.resultComponent.grade.letter')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.letter.c', 'kuali.resultComponent.grade.letter')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.letter.d', 'kuali.resultComponent.grade.letter')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.letter.f', 'kuali.resultComponent.grade.letter')
/
delete from ksen_lrc_result_value where id in ('A', 'A+', 'A-', 'B', 'B+', 'B-', 'C', 'C+', 'C-', 'D', 'D+', 'D-', 'F')
/

insert into ksen_lrc_result_value (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, ID, NAME, NUMERIC_VALUE, OBJ_ID, RESULT_SCALE_ID, RESULT_VALUE, RESULT_VALUE_STATE, RESULT_VALUE_TYPE, UPDATEID, UPDATETIME, VER_NBR)
values ('SYSTEMLOADER', TO_DATE('20121024','YYYYMMDD'), 'Not-Satisfactory', 'Not-Satisfactory', '', '', 'kuali.result.value.grade.satisfactory.ns', 'Not-Satisfactory', 0, '', 'kuali.result.scale.grade.pnp', 'Not-Satisfactory', 'kuali.result.value.state.approved', 'kuali.result.value.type.value', '', '', 0)
/
insert into ksen_lrc_result_value (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, ID, NAME, NUMERIC_VALUE, OBJ_ID, RESULT_SCALE_ID, RESULT_VALUE, RESULT_VALUE_STATE, RESULT_VALUE_TYPE, UPDATEID, UPDATETIME, VER_NBR)
values ('SYSTEMLOADER', TO_DATE('20121024','YYYYMMDD'), 'Satisfactory', 'Satisfactory', '', '', 'kuali.result.value.grade.satisfactory.s', 'Satisfactory', 1, '', 'kuali.result.scale.grade.pnp', 'Satisfactory', 'kuali.result.value.state.approved', 'kuali.result.value.type.value', '', '', 0)
/
delete from ksen_lrc_rvg_result_value where result_value_id in ('Satisfactory', 'Not-Satisfactory')
/
delete from ksen_lrc_result_value where id in ('Satisfactory', 'Not-Satisfactory')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.satisfactory.ns', 'kuali.resultComponent.grade.satisfactory')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.satisfactory.s', 'kuali.resultComponent.grade.satisfactory')
/

insert into ksen_lrc_result_value (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, ID, NAME, NUMERIC_VALUE, OBJ_ID, RESULT_SCALE_ID, RESULT_VALUE, RESULT_VALUE_STATE, RESULT_VALUE_TYPE, UPDATEID, UPDATETIME, VER_NBR)
values ('SYSTEMLOADER', TO_DATE('20121024','YYYYMMDD'), 'Pass', 'Pass', '', '', 'kuali.result.value.grade.pf.p', 'Pass', 1, '', 'kuali.result.scale.grade.pf', 'Pass', 'kuali.result.value.state.approved', 'kuali.result.value.type.value', '', '', 0)
/
insert into ksen_lrc_result_value (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, ID, NAME, NUMERIC_VALUE, OBJ_ID, RESULT_SCALE_ID, RESULT_VALUE, RESULT_VALUE_STATE, RESULT_VALUE_TYPE, UPDATEID, UPDATETIME, VER_NBR)
values ('SYSTEMLOADER', TO_DATE('20121024','YYYYMMDD'), 'Fail', 'Fail', '', '', 'kuali.result.value.grade.pf.f', 'Fail', 0, '', 'kuali.result.scale.grade.pf', 'Fail', 'kuali.result.value.state.approved', 'kuali.result.value.type.value', '', '', 0)
/
delete from ksen_lrc_rvg_result_value where result_value_id in ('Pass', 'Fail')
/
delete from ksen_lrc_result_value where id in ('Pass', 'Fail')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.pf.p', 'kuali.resultComponent.grade.passFail')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.pf.f', 'kuali.resultComponent.grade.passFail')
/


insert into ksen_lrc_result_value (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, ID, NAME, NUMERIC_VALUE, OBJ_ID, RESULT_SCALE_ID, RESULT_VALUE, RESULT_VALUE_STATE, RESULT_VALUE_TYPE, UPDATEID, UPDATETIME, VER_NBR)
values ('SYSTEMLOADER', TO_DATE('20121024','YYYYMMDD'), 'Completed', 'Completed', '', '', 'kuali.result.value.grade.completed.c', 'Completed', 2, '', 'kuali.result.scale.grade.completed', 'Completed', 'kuali.result.value.state.approved', 'kuali.result.value.type.value', '', '', 0)
/
insert into ksen_lrc_result_value (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, ID, NAME, NUMERIC_VALUE, OBJ_ID, RESULT_SCALE_ID, RESULT_VALUE, RESULT_VALUE_STATE, RESULT_VALUE_TYPE, UPDATEID, UPDATETIME, VER_NBR)
values ('SYSTEMLOADER', TO_DATE('20121024','YYYYMMDD'), 'In-Progress', 'In-Progress', '', '', 'kuali.result.value.grade.completed.ip', 'In-Progress', 1, '', 'kuali.result.scale.grade.completed', 'In-Progress', 'kuali.result.value.state.approved', 'kuali.result.value.type.value', '', '', 0)
/
insert into ksen_lrc_result_value (CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, ID, NAME, NUMERIC_VALUE, OBJ_ID, RESULT_SCALE_ID, RESULT_VALUE, RESULT_VALUE_STATE, RESULT_VALUE_TYPE, UPDATEID, UPDATETIME, VER_NBR)
values ('SYSTEMLOADER', TO_DATE('20121024','YYYYMMDD'), 'Not-Completed', 'Not-Completed', '', '', 'kuali.result.value.grade.completed.nc', 'Not-Completed', 0, '', 'kuali.result.scale.grade.completed', 'Not-Completed', 'kuali.result.value.state.approved', 'kuali.result.value.type.value', '', '', 0)
/
delete from ksen_lrc_rvg_result_value where result_value_id in ('Completed', 'In-Progress', 'Not-Completed')
/
delete from ksen_lrc_result_value where id in ('Completed', 'In-Progress', 'Not-Completed')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.completed.c', 'kuali.resultComponent.grade.completedNotation')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.completed.ip', 'kuali.resultComponent.grade.completedNotation')
/
insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.completed.nc', 'kuali.resultComponent.grade.completedNotation')
/

delete from ksen_lrc_rvg_result_value where RVG_ID like 'kuali.resultComponent.grade.percentage'
/

delete from ksen_lrc_result_value where id = '0-59%'
/
delete from ksen_lrc_result_value where id = '60-69%'
/
delete from ksen_lrc_result_value where id = '70-79%'
/
delete from ksen_lrc_result_value where id = '80-84%'
/
delete from ksen_lrc_result_value where id = '85-89%'
/
delete from ksen_lrc_result_value where id = '90-94%'
/
delete from ksen_lrc_result_value where id = '>95%'
/

Insert into ksen_lrc_result_value (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,EXPIR_DT,ID,NAME,NUMERIC_VALUE,OBJ_ID,RESULT_SCALE_ID,RESULT_VALUE,RESULT_VALUE_STATE,RESULT_VALUE_TYPE,UPDATEID,UPDATETIME,VER_NBR)
values ('SYSTEMLOADER',TO_DATE('20121024','YYYYMMDD'),'0-59%','0-59%',null,null,'kuali.result.value.grade.percentage.0.59','0-59%','0',null,'kuali.result.scale.grade.percentage','0-59%','kuali.result.value.state.approved','kuali.result.value.type.value',null,null,0)
/
Insert into ksen_lrc_result_value (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,EXPIR_DT,ID,NAME,NUMERIC_VALUE,OBJ_ID,RESULT_SCALE_ID,RESULT_VALUE,RESULT_VALUE_STATE,RESULT_VALUE_TYPE,UPDATEID,UPDATETIME,VER_NBR)
values ('SYSTEMLOADER',TO_DATE('20121024','YYYYMMDD'),'60-69%','60-69%',null,null,'kuali.result.value.grade.percentage.60.69','60-69%','1',null,'kuali.result.scale.grade.percentage','60-69%','kuali.result.value.state.approved','kuali.result.value.type.value',null,null,0)
/
Insert into ksen_lrc_result_value (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,EXPIR_DT,ID,NAME,NUMERIC_VALUE,OBJ_ID,RESULT_SCALE_ID,RESULT_VALUE,RESULT_VALUE_STATE,RESULT_VALUE_TYPE,UPDATEID,UPDATETIME,VER_NBR)
values ('SYSTEMLOADER',TO_DATE('20121024','YYYYMMDD'),'70-79%','70-79%',null,null,'kuali.result.value.grade.percentage.70.79','70-79%','2',null,'kuali.result.scale.grade.percentage','70-79%','kuali.result.value.state.approved','kuali.result.value.type.value',null,null,0)
/
Insert into ksen_lrc_result_value (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,EXPIR_DT,ID,NAME,NUMERIC_VALUE,OBJ_ID,RESULT_SCALE_ID,RESULT_VALUE,RESULT_VALUE_STATE,RESULT_VALUE_TYPE,UPDATEID,UPDATETIME,VER_NBR)
values ('SYSTEMLOADER',TO_DATE('20121024','YYYYMMDD'),'80-84%','80-84%',null,null,'kuali.result.value.grade.percentage.80.84','80-84%','3',null,'kuali.result.scale.grade.percentage','80-84%','kuali.result.value.state.approved','kuali.result.value.type.value',null,null,0)
/
Insert into ksen_lrc_result_value (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,EXPIR_DT,ID,NAME,NUMERIC_VALUE,OBJ_ID,RESULT_SCALE_ID,RESULT_VALUE,RESULT_VALUE_STATE,RESULT_VALUE_TYPE,UPDATEID,UPDATETIME,VER_NBR)
values ('SYSTEMLOADER',TO_DATE('20121024','YYYYMMDD'),'85-89%','85-89%',null,null,'kuali.result.value.grade.percentage.85.89','85-89%','4',null,'kuali.result.scale.grade.percentage','85-89%','kuali.result.value.state.approved','kuali.result.value.type.value',null,null,0)
/
Insert into ksen_lrc_result_value (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,EXPIR_DT,ID,NAME,NUMERIC_VALUE,OBJ_ID,RESULT_SCALE_ID,RESULT_VALUE,RESULT_VALUE_STATE,RESULT_VALUE_TYPE,UPDATEID,UPDATETIME,VER_NBR)
values ('SYSTEMLOADER',TO_DATE('20121024','YYYYMMDD'),'90-94%','90-94%',null,null,'kuali.result.value.grade.percentage.90.94','90-94%','5',null,'kuali.result.scale.grade.percentage','90-94%','kuali.result.value.state.approved','kuali.result.value.type.value',null,null,0)
/
Insert into ksen_lrc_result_value (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,EXPIR_DT,ID,NAME,NUMERIC_VALUE,OBJ_ID,RESULT_SCALE_ID,RESULT_VALUE,RESULT_VALUE_STATE,RESULT_VALUE_TYPE,UPDATEID,UPDATETIME,VER_NBR)
values ('SYSTEMLOADER',TO_DATE('20121024','YYYYMMDD'),'>95%','>95%',null,null,'kuali.result.value.grade.percentage.95.100','>95%','6',null,'kuali.result.scale.grade.percentage','>95%','kuali.result.value.state.approved','kuali.result.value.type.value',null,null,0)
/

Insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.percentage.0.59','kuali.resultComponent.grade.percentage')
/
Insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.percentage.60.69','kuali.resultComponent.grade.percentage')
/
Insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.percentage.70.79','kuali.resultComponent.grade.percentage')
/
Insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.percentage.80.84','kuali.resultComponent.grade.percentage')
/
Insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.percentage.85.89','kuali.resultComponent.grade.percentage')
/
Insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.percentage.90.94','kuali.resultComponent.grade.percentage')
/
Insert into ksen_lrc_rvg_result_value (result_value_id, rvg_id) values ('kuali.result.value.grade.percentage.95.100','kuali.resultComponent.grade.percentage')
/
