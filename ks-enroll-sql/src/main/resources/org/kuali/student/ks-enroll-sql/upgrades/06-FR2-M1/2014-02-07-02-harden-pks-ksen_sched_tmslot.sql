--script to fix/harden pk of ksen_sched_tmslot
create table t_fix_tmslot_pk as select * from ksen_sched_tmslot where id='non-existent-value'
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',52200000,'09FB51B4-72D5-4790-919F-D2E1B2C86D57','1063','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'0B05FC93-96AB-49EC-82EA-6200E7BE43E4','1618','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',46200000,'0B09A835-BFCE-4610-8099-78797E45368D','1688','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',31800000,'0CE0A6CB-5C28-49DB-942A-0A71B8D8A5BE','1395','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'0DAAF846-99D0-42E9-8C16-3FECC7760B10','1624','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',107400000,'0EB42582-EDF3-48D8-96DA-31513BE4CC88','1679','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'0EECA8A5-B9D6-46EC-B5B8-D71CED234048','1260','',52200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',65700000,'0F1CC513-8622-44B7-958F-37F89950976A','1306','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58500000,'0F6438C5-18AE-4BCD-B7C9-F14B6B3199AB','1322','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'0FCF5C68-78CB-4C56-ABB5-BD3229264C30','1389','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'10186002-208C-484C-849D-9633CC5350FD','1257','',63000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'10AAAE48-6448-4380-B739-A639738A542E','1153','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48600000,'10B086B6-5BBC-4E83-992B-FD1BFCC9A499','1283','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'11A05D84-8097-400A-B0CF-D9DFB952D570','1640','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',31800000,'11A8331C-A62B-4BD1-93C4-92FD31112507','1000','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'1262EF67-1E77-4CB0-96C8-BB0B69A2B131','1598','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49500000,'12BEACDE-C8CC-4E48-B9B7-C503FFCED74D','1473','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53100000,'1337888C-CA55-47F8-A231-F76FC83C9B42','1345','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',72000000,'13C501E0-5F4E-4778-9A79-B24F8DAD10ED','1424','',63000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',74400000,'13FC3D89-C01F-4249-B9B1-7A2F36BBCCAD','1109','',65400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',73800000,'152965EE-569C-4415-A548-5C2ACB886A8F','1227','',63000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'1529F9A3-4D55-4CB9-B6CF-3001E938338C','1483','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'152BDEB1-22D7-4B60-BEFE-AAE0C71C8D87','1635','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58800000,'153C05C5-1D15-447E-8CAA-802DA793DB78','1631','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'16447854-0D2A-42AB-8740-DACA9AFF1678','1512','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'16FC822C-1DDC-4647-9461-AF0B59F89B22','1650','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49500000,'17105926-2227-4F73-8518-D456B1547E9F','1101','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'17387F5F-0330-410E-BA57-74379B7532BF','1185','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 13:30:00.000000','','',65880000,'17E8736D-EF2C-4674-B976-B1DD6704BC68','1696','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-22 13:30:00.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',42300000,'17EE8C00-4E08-434B-A7FA-C79E5213DA5C','1666','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'182C9F1C-7397-4C37-B4E9-59EA339478CD','1100','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'186D8B1D-A162-4441-A725-A8DB23DF3695','1417','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39600000,'187BEB1E-0A00-4F6F-9FA7-20005780A7C8','1110','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',52200000,'18B95DA4-62DE-418A-8A61-FCE0215BDF11','1189','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'190FD559-D115-4C2C-B3E1-6777F721822B','1245','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'194D0A81-298D-4AC1-96F4-1D9D4E4263CB','1486','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',31800000,'1A248F18-5837-424D-B752-27356DE3045B','1526','',27300000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',51300000,'1A7FD319-F720-46BF-90ED-8BD39F1ABFA1','1460','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'1AF3C83A-EC31-4406-AA28-C8162989C7ED','1284','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44100000,'33FDD480-5A3F-4579-AEA8-7EFE4017D514','1471','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',43200000,'35618F40-97EF-46A7-BA82-A9618D010F04','1329','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',77400000,'358A14A9-BD45-47A8-921E-BCE66D76ADE0','1462','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'35D5E0C1-1D35-4D91-BFEC-38194235B48A','1638','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'360233DD-5F25-4BEC-BA30-D958A28C66AD','1438','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',56400000,'3680863E-EEB9-46AC-B834-894166B1022F','1683','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54600000,'37048930-70AD-40FD-AA8B-061380662F9B','1352','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'37247B3D-6A9F-49AD-A080-439FFF79938D','1056','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'3747D8B6-70B8-474E-8045-DC3D165F6698','1192','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',78600000,'376E1034-BA81-48C2-8B1E-9029D60819C1','1327','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'378A35C9-0C0D-4FF7-8132-207BE1A9EC00','1331','',56700000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'387AA861-D1C6-46BB-A9E6-FDE89537D452','1330','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'39012C7E-872F-4FE8-B581-9B5BFAC5E10A','1117','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',35400000,'390B003C-220C-4E43-93C9-598AC09DC24F','1001','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'3961B149-9202-4871-AD6D-EA18EB5C1616','1151','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48600000,'3A070105-ADD5-4AF0-BCD4-7D9832EB6028','1196','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',47700000,'3A4C58FF-F0D6-42A5-B3DC-0D01F4D6862A','1096','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',67500000,'3B95733C-AD34-43B5-8165-97EF8B4916E7','1684','',63000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',69600000,'3B9D8231-B489-4C45-8689-DD1670677407','1295','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'3BE0BDC8-A1F1-4020-BC0C-5467D2EFF9CE','1276','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'3C44E95F-A04B-4D7A-B985-80C948FD775A','1051','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'WF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46800000,'3C9BC2E7-ED70-4C8F-A295-0114D3F6F398','1569','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'3D3A3DF5-9018-480A-AFF6-48FC673EB7D7','1091','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',69300000,'3DD40507-AB18-43B6-92DF-5B0C771DBF38','1264','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'3DF33C0F-C38A-4E7F-A883-10B384010B80','1086','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'3E5D398B-1209-4199-A8EB-9995CEF8247F','1370','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40800000,'3E61C1A3-E9D7-4E37-AAE9-F96039A9E705','1600','',37800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',51600000,'3E818655-0ED0-4C6D-A87E-25B5C907693F','1530','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',70200000,'3E9A504A-51C5-4CEF-9906-01A7FBA0DEF9','1602','',63000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'3EA8074D-9DE0-49CC-843C-E409C5C5D23E','1406','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',46200000,'3ED25D57-5A94-46DE-A50D-E236F3D9C383','1020','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'3F1C7C73-28BD-4CBB-8211-5059473BD670','1115','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',43200000,'3F396261-0B0E-451C-9767-8686E070C625','1692','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'3F924078-0261-46F7-BD10-D6356D634BF7','1436','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'3FDC2160-73B9-43DE-B010-DCC5DBDFFCB4','1160','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',72000000,'411873FA-FBB8-45B6-8B76-0505EED39BA7','1350','',63000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',77100000,'420DE58C-8E13-419B-99A5-D6F127DB2668','1068','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'1AFFD653-1DC3-4BF7-99E4-91AA1A117650','1092','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'1B0CD87C-C17C-4A3C-840D-5A979706E04F','1240','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'1B3E51CA-E995-429E-A59A-DFD71F2A59A1','1292','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'1B694511-6FE3-48B4-8DDE-F4422FA19459','1472','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76800000,'1B73D9C1-F8E5-41B0-8455-6FB5B6B4BD7C','1045','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71700000,'1B9495C1-00C2-418B-AF83-C8C996E5A880','1601','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'1BE4F12E-7B8E-4B9C-8687-D5C6355CB1A3','1641','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'1C0044E6-A689-4111-81B2-76C6DB1D3B87','1487','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58500000,'1C59E565-91F5-4DF6-B72D-00A13293354C','1311','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76800000,'1CAF4D9A-F949-483C-B9F6-FA57F6DB0FBE','1363','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'1D2F4227-EF41-4189-95EB-F3E25E0DB8DF','1365','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'1D6F7E97-ADE9-4132-B4EB-BA13382181B1','1582','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',48600000,'1E2159F9-1DBF-45E0-9EF0-AF5203369BDF','1694','',37800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'1EE4A9EB-336D-4D45-86B7-21AE31964C95','1267','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',70200000,'1F460F72-20C3-4DED-8289-95F23D2AB4F2','1671','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'1F5F4A6F-1F16-46CF-B447-CAF6A1A7DDE0','1323','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'1F9113A2-D0F3-42D8-A138-EC24B96CA771','1163','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'1FBB67D1-CDCB-4D75-9666-4C12A156AA7A','1563','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'1FEAD087-411A-4333-81A3-84077D5413D3','1170','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',33300000,'2005C983-7F6A-4F53-A210-6CF5D6FF9CAC','1594','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76500000,'2034AFF5-3311-4E75-A3C5-C5F2688ED318','1351','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45900000,'20663E5E-A6B3-4123-B50C-ECB31EAC36A1','1332','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'20BA3EF6-7473-4531-B8DC-086C21EE945B','1550','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'20D47926-8132-4549-B31D-EB6AC0ADD420','1567','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'21D1722B-1CA6-4887-A967-949AA98F955E','1643','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76500000,'21EEFB4E-F763-46E8-B084-6FFF6B6296AF','1237','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'22099533-5319-4E5C-B818-26C5009F0D0D','1474','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',37200000,'22595915-95A9-436A-A870-33F7F6860FE1','1533','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46800000,'2264FA62-1BE5-45C2-A892-CF69B797969F','1356','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'2289CEAF-19FA-4E4F-B6AF-B12611918E0A','1355','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'2299EC86-58B4-497B-B036-D7A9A2642B3D','1202','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'229F93A4-8B1F-482D-B953-BE22CA27056E','1300','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',97200000,'22ADFD44-BED3-4B2A-9F90-24380C8B2DF9','1678','',86400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',60300000,'22F92F0D-E1F8-4833-B534-04362A4496D1','1030','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',31800000,'233164AC-287F-4ED3-9102-3F4AFDAFF09B','1420','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54900000,'23851A93-52C5-44C8-A60E-5B8ABEB8FFDC','1475','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'2388DFBD-EFCE-40B5-A613-666C269DFC74','1592','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',73200000,'25D118DA-1C87-4E21-8F9E-88152F515866','1400','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'25EC533B-9EA6-4AD7-9437-E5492299A647','1367','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67500000,'26CA0E5B-4F9B-426B-A768-E2950B12B1F0','1498','',58500000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',66600000,'27CAF209-D9C1-4A80-A4BD-FC4F98E8C4C2','1266','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48600000,'2835208A-7A5C-42E6-8AD1-BBEB1E167F16','1242','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46800000,'287FC190-B9C0-4629-8D57-171F6FDB9C9C','1203','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44100000,'28CA696C-B23C-4736-8D2F-4D4FA221C25D','1408','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',33300000,'29A6497A-7719-4F1E-9A8D-37C751F0E732','1378','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',72000000,'29ACD205-537D-4764-878E-A9459BBF91A7','1673','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'29ECC068-85C3-4D1C-98FF-C206F3325F29','1090','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',49800000,'29F11954-E9A6-44B8-A2D4-EEA2BEFC7983','1021','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',55200000,'2A53EDFF-AB9E-430C-883D-79A125391D04','1404','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'2AF72018-B2D3-4C1A-97B0-E9A31EA9660F','1517','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',53400000,'2B6D9E60-A856-4C3F-9EC0-660CE3977D09','1663','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'2B7825EF-04C5-490B-B4FE-FBE057493832','1106','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',43200000,'2C0FC949-973A-4B1B-A48C-3CA7FD7D1390','1118','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49500000,'2C167A11-6B63-4D4E-A3F2-3682220F1471','1296','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',69600000,'2CDE3242-DE55-4B2F-A98F-ED55EB896FEB','1076','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',68400000,'2D996CF9-A37D-4871-AE74-6D735965DD99','1566','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'2E1D6591-942D-4369-AEE1-D2BCD1E976DF','1450','',58500000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'2E245E1C-5DEC-41E0-A33F-E354770A9E8A','1229','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',55800000,'2E3C3CCB-DC22-42F2-8C63-54D03C243F5D','1595','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'2E64D9E3-A96C-41CC-9634-7E60D72DFAAF','1220','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'2EBB4199-8F4A-47BB-87D4-5C7B966DB503','1554','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'2EEE9F41-52F2-4C47-90D1-F2AF088DCEC7','1561','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 13:30:00.000000','','',63000000,'2F0DC974-4CFC-4500-B8E0-DC943B49417A','1697','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-22 13:30:00.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',53400000,'2F7F1272-1397-4A36-88A6-138EF6D5DF9B','1006','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58500000,'2FB83E39-D6B1-496F-93C6-DC51A0B3DCAB','1551','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'2FD4CDF3-9F5A-4B22-9501-2F2858690918','1046','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',77400000,'30457FA0-AAF6-45FF-9717-0D98BEB0B24E','1677','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'307C50EC-445B-4671-9F63-BACBBAD9DD86','1562','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'31A683DC-54EA-40B3-A83A-4911913A77A1','1556','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'31DC30BB-0C20-472F-9C09-70CBC2BEF7E6','1138','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',79200000,'32CCFB24-2B79-4703-95CA-C6E16EBA4E38','1392','',69000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'32FE013C-62D6-48C0-952D-0202846C44E8','1127','',52200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'331B1984-0170-4F3B-A02B-D480FE59DB2E','1398','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'33D90FCE-B1A4-457B-9E97-7BEE0EA3E2F6','1397','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',73800000,'33DC42D5-37D2-4EE3-963F-F86ECAD7E199','1637','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',81000000,'33E07812-1826-457E-9772-608DBC1B8F11','1087','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45000000,'00173D31-0358-435C-8A5D-6A4701D92E3A','1545','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'0042A284-76D9-47C5-9985-1E9F0121C51A','1433','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'00495EA4-E6D4-4BBA-AC74-441951B2E16F','1072','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'007A5725-B29F-4DAA-8E34-A5FA9A66499E','1418','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',62400000,'007C327A-6661-4FFB-8726-B2CF3A20C399','1217','',52200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'009C6622-0F0B-4FB4-8649-68C20CC48F3C','1415','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'01495E1B-15C7-45B0-9353-770CDC897E3C','1325','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',41400000,'02264E79-8ABA-4B02-986D-0F2DB3F11E41','1648','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54900000,'029A5812-FD16-4D57-A4C9-0201AF2DA2D5','1534','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39600000,'02AF73F6-5921-4083-A593-1B609E99ED46','1606','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'02B59FCB-3E4C-4AB8-8C8A-9F6BEE58E064','1093','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'02E4F62A-C098-47F8-8844-71DAC2A7C0A5','1235','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',47400000,'03472447-C338-45FB-BD8C-C38C60485EAB','1231','',41400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',39000000,'03813AE0-7F76-4506-9BFD-D5EABE06D57C','1018','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'03B8C195-09AE-4274-AD47-D3DEF4315C11','1348','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',50400000,'043618AA-A248-4C9A-AB4D-316C5A132CC0','1532','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'044BA2D4-D13F-4ABE-B6F3-F9BB3C09AEA3','1212','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'04C23C64-0DAB-4BC2-A73C-FF3E7E8DB32D','1441','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'05213403-142D-465C-8A90-8D5520D1A610','1290','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'05923C3E-5D27-4841-A90C-ACE1375D22BD','1225','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'059A8A2D-A7D8-49C9-849C-5D0AD3BA5AB2','1644','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',77400000,'05A92EE5-6A83-4103-A102-E0EAEFFB780D','1394','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'05D02425-8976-49E2-B2CC-37721DF7C8B6','1584','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',36900000,'06198BD8-7D4B-4BBC-9947-8FB548D7B8F1','1036','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76800000,'0687CB73-714F-4168-AF87-16BE13B7211B','1396','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',77400000,'06C38C14-6974-4D40-8796-861E4979F963','1250','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',69300000,'071CACB9-F90E-4D41-993A-D4C390B01999','1613','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'077FA3EA-F4B0-45D5-A3A2-5F3FED320C69','1213','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',74700000,'07804E5E-428D-4905-893D-2DA28E77CADF','1416','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'0848C0AF-382E-4773-804E-674E64C6D60E','1497','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'08AAD2B8-8B8B-4ECE-92FD-2378A28F1B79','1448','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'08B4E3C7-237E-488F-A599-53590C730031','1184','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'08B8B2B5-4BC1-4ECE-BAEA-38BD8B6CCFD2','1320','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'09157260-5166-46E9-AC3A-0E0C47CE24F8','1511','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'0933F9E1-A9FD-45E5-A59B-61EA01E3C58A','1646','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54900000,'09915BE1-CA3E-4CF3-9AF3-52929AA58F25','1176','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'09C8E1E0-5D97-4371-BBFF-78FD3AF24BBB','1253','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'09F3836C-AFBA-4081-A0EF-C0AB5870EA17','1125','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'6974C1AB-1507-48E2-9850-E9F387C96186','1302','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',38700000,'69C83AB0-4271-49E0-A046-C2DC45A1D5CA','1226','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40500000,'6ABD9163-EDF2-426B-8B59-F1A46D2EB280','1346','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'6AC2E44C-FF57-418B-B39F-3636B35C7D5B','1148','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63900000,'6B04B39C-A287-4729-A58E-C7363235AD13','1531','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',66000000,'6BC5EA37-ACD5-418A-B56D-5C02112112F2','1575','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',43200000,'6C6D60A8-1D2E-475C-B0E0-114BE4A8F794','1294','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'6C7BE546-B8BF-4478-94B7-7C3976BE9DC7','1035','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'6C86F5CB-FF17-4F0D-BFAF-C14B18761E3C','1379','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'6CEC9C0E-F0BB-4798-A2E6-A1068DE43DF1','1119','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',72000000,'6D3BE7E3-2A40-4F64-AB64-6C54AE736443','1357','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60300000,'6D52A7F8-A188-4EA9-869E-E76107331381','1214','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',55800000,'6D8F9089-9913-4CFE-B776-EEA257F5C15F','1481','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',57000000,'6DB5CE7F-8C25-4AD6-88E8-A4BBAD9429C6','1007','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'6DF07F85-944B-425E-9841-1AFE2E897B0D','1509','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'6E654454-3594-43F9-86EF-825E66378281','1495','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',77400000,'6E775C03-D63A-4973-A3CF-41481CEFBD4B','1558','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'6E78D271-D3DF-4C37-89A8-98D4CF6EF7A0','1275','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54900000,'6EC3A08A-5AEC-4E41-8603-B8D64FFB5BE5','1364','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76800000,'70346EA1-3F81-4C4E-9453-00C382227199','1112','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'7042D730-5D28-47B2-BB78-C9CDD6DADE15','1630','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'7074B3B7-C8ED-4718-918E-AAF896A7BD92','1126','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',37200000,'714405BC-6AB5-4729-9873-B5BE6AC76155','1274','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',32400000,'71BF69AE-5092-4C86-AF05-89134788E30F','1088','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'7319D0AB-55B5-4B87-8F32-E3F41AC3EBAB','1525','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',41400000,'736E5D79-10A3-408F-9222-AEBDBFA97B65','1596','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'737E6F9D-068B-43E3-AE46-65FF187D93E6','1510','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',70200000,'73E3800C-D188-4B19-A51A-CC4F56C5A31F','1454','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'74326811-2AD2-4194-AEDB-373A34B41A4B','1651','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'743477C1-59C8-4860-B1B3-BE40A9DDCA7E','1459','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',46200000,'748C67B7-703E-4FA0-A4C3-CBA705BF926C','1667','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48000000,'74AD17F4-7F29-4E10-8975-0257007FA8EA','1328','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',51300000,'75333255-956C-4D57-9A94-A2F393E57042','1206','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',66600000,'757826A3-68F2-449E-A42B-7CE24520059F','1621','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'7588DBEC-454A-4BA4-A2F4-3688A536DA13','1504','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',44100000,'75990A1B-2A8C-4C9D-AB1C-998994BB6142','1027','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'75AA0806-5020-46A9-9711-EFCFC2660775','1273','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46800000,'7669EE15-E08F-42E1-AF56-0FDCB04D2708','1476','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'76E7B49B-C9F4-474F-A55D-DC784155C325','1134','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49200000,'A41382DA-AACB-4966-9071-136663FF9BBA','1120','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 13:30:00.000000','','',44100000,'A473436C-F213-4A4D-8F3D-916C19818398','1699','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-22 13:30:00.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'A4753584-5F60-42A1-B543-88C70B269374','1147','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',65700000,'A519014C-F997-40F3-A2C4-0B74052DA1D1','1133','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'A556EDDF-D349-46D9-BE47-DDB424A3C03E','1360','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'A5DC61AF-E707-4F2E-A60D-DDBA3B080205','1236','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'A5DFA130-5855-4A14-BBAB-B9FBC2372980','1377','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60300000,'A5F4AA65-A6E2-4D92-B52C-75E0F5B58506','1341','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',36900000,'A6666EB7-26C3-4F3A-B90B-A3AC4B5380F0','1172','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'A73FB191-DCF9-4774-95BA-BF0049A4E00A','1197','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',78000000,'A740F78B-8F16-4057-9A52-BB7117742060','1067','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',50400000,'A76886CB-5586-4E14-9044-94F8E3064709','1312','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76500000,'A78DC3B4-3115-445A-838E-5F447B7FDA2C','1085','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71100000,'A82A2ACD-923C-4E8A-AD2E-190A162FD7F8','1425','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'A88BE160-1FF8-41F3-8B4D-8F90C1D47068','1527','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'WF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'A92909B7-EE6C-40BE-860E-BEEAA3C2608D','1647','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49500000,'A92CD9FA-5E09-4ED3-8BC4-28002990E3E3','1362','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'A9607F84-5EF2-4C7A-82E1-066120081ADD','1576','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',34200000,'A9769477-49AD-4BCC-90FD-06B46603B4E9','1657','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',71100000,'A9A51C1A-222E-462F-AB86-425FD020D1F0','1668','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',43200000,'A9E9CB4E-37BE-408E-ABA2-4D7D5F371314','1280','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',78600000,'AA301B43-C225-479D-992B-CAAC2959C004','1293','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'AA313E8D-399E-4F09-AF2A-7E56EA05D6E1','1234','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'AA4FF1FC-32AA-4ADF-AC1A-2F778D760437','1552','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'WF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45000000,'AA964D74-7E85-4740-A740-541F37BB5686','1059','',41400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',78600000,'AAD830CE-90DE-4068-B2F6-F31065AB5C18','1159','',75600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58800000,'ABA809F9-FD58-4C53-85A3-5859471D65D5','1044','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71100000,'ABCBE052-F85F-45B6-B081-F3F20B983B96','1625','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'ABD6C373-4763-4CF1-9538-94F2F435C09D','1334','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'WF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'AC32FD7F-928B-41E6-9F05-C3F8B59248CF','1246','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'AC33452D-69BE-456D-B0B4-8EAD991C6011','1347','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',38400000,'AC92E960-01C5-4E0A-9094-5A141F82795B','1681','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',77400000,'AC95F554-4099-4AC4-9B97-AB20DA2AC655','1520','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'ACBF39F7-377B-4234-A114-DFA33E8BD731','1195','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40800000,'AD1D6C3A-D35B-49E7-B1F1-66EF86D2EABB','1305','',30600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',73800000,'AD30AA92-3954-40FA-BC85-BAEB8F816DF2','1682','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'AD5620D3-1EDF-4E1F-ACC7-B903BF8EF733','1287','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'7732438A-0E6D-4852-8FBB-F41BC888BCEA','1372','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76800000,'77C8E0F1-E02A-46A4-98CA-E697BD62019D','1349','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',47700000,'783C43C1-11FF-4EF5-A761-ADC904CD911D','1057','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'785A6D66-A823-49E0-A312-C8B843EC4B5C','1228','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54900000,'78E57299-B074-404E-83DC-F947A9DFA2F0','1150','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',31800000,'7BAF5890-8E6D-44CD-923B-249E468909AF','1279','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'7BB8B31B-7EB8-4881-A870-881E595FAFEB','1410','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',70200000,'7C44A9DE-14AF-43EC-956E-3D8E50F3801D','1174','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'7C49C4B7-C8EC-4619-A408-2F216EFA7302','1540','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67200000,'7C9F1246-1D47-4703-AB31-921EFEDA4725','1597','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',39000000,'7CB67391-82D0-4403-A4EC-2872CC200A20','1002','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',78600000,'7D75CDF8-ADF3-46A0-93D7-7D85FC24707F','1108','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45000000,'7E3748F4-2052-450C-8109-869FBD15521D','1102','',40500000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39600000,'7E4F9C75-63FF-4D26-A934-ECC6CF781CB2','1113','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',62400000,'7E6C6BCA-6CA0-4A2E-8FFA-24417CCE5F01','1426','',52200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',49500000,'7ED33944-B650-40E6-BC83-AA84EC6F392D','1012','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'7FAA9D7B-2E1D-4D80-9F77-FB963CEFC5E3','1314','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'80770C59-E51E-469A-B824-432D20116213','1376','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'80A2EBF6-FCFE-46EE-BA28-98A4F8EAE2D9','1071','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',33300000,'81448524-F1A5-4BAF-8CF7-76E89048C770','1564','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',68400000,'814C1DBB-B426-4FBB-AEE5-9A47E461FF69','1194','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'816FDCC4-3813-4F49-BBE3-3268F2C7E553','1506','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',68400000,'82986BE4-110F-41B4-AE90-DB5AD684163F','1649','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'82E04B68-C3A2-48AE-B63F-9B665C7D56EB','1366','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'8306DB52-3569-4210-ACB7-ECFC8A4886AC','1421','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'836693E6-89B2-434F-830B-91EB7F643B5F','1167','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',72900000,'83D2F051-C09D-41CA-9F2D-50A624AE1512','1282','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'84031499-DD4A-4C87-98A6-B5159039CC88','1620','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'844A59E5-847D-411F-AE85-EC40384D2B85','1501','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',77400000,'847A637D-1A17-4AF1-A6C7-4C353F2C6B84','1623','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',80100000,'8489C8D8-7BC8-4BD6-8B05-7F21851E797B','1050','',65700000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'85985C86-5FF8-42BE-B5E3-1092AA4A0497','1335','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'85F999AC-6D49-4972-92F2-880E9058E4D9','1084','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'8610B700-E8C6-4942-A0FC-518FB970B0C7','1628','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'86A05DB6-74F4-4DD1-911C-81FE9C88B7B9','1042','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'86B9E87E-9A56-4A42-BC81-2F63583F387B','1339','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',49800000,'86EC16E7-8015-4AF2-AFFE-7F7801997490','1655','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',62400000,'86FFB1F3-D5B7-43D6-B445-103AFA5EF2CE','1122','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'87249740-9200-4533-AD63-1F6DBD287C1B','1139','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',65700000,'8792ABF6-6F7C-4E9F-9BDD-4ABE1147647A','1015','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',38700000,'87DD0EE7-B6E5-4CFA-9403-6E570AD87830','1208','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'8834DD9E-B502-4036-874B-6B6A6EEE34F4','1627','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',78600000,'88ACBC43-62D9-49F7-B842-3545D8728103','1162','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',43200000,'898360DF-53D6-456E-AF60-DE71CEE9738F','1178','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'89F95F16-A4FC-4694-BF02-2AA6E77069C6','1244','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',74700000,'8AAD2245-7895-4516-AAC3-D65A08A0FF6D','1343','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'8AC03A9F-5B44-48EE-90C5-F4AED9D10AC9','1535','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'8ACB70AB-5E13-49E6-BEA8-D570E7049E50','1271','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',70200000,'8B029990-045D-49DA-A574-5A0459030954','1664','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'8B0D1752-F91B-4D16-B8DF-D8A9332C0D78','1368','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40500000,'8B365023-BA3B-4234-ADB6-D78A096649AD','1465','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45900000,'8B42AFF1-6DB1-4E7D-9174-4EAECA6D896D','1299','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'8B9CC82B-E55D-4002-8550-A4FBE9D539F4','1409','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76800000,'8BB5BE11-6C48-413E-A76B-C52D590C36BA','1488','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',38700000,'8BD8EDBD-29DA-43B6-B8EE-455DB6A1D4D5','1010','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67200000,'8BF8B05D-58A7-43C5-B56E-7D2F65877744','1544','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',37200000,'8D232245-6F38-414B-A401-35231E0F9020','1466','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'8D552EE7-55EC-4AA0-995F-232A355F9E8B','1288','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44700000,'8D71854F-F05F-4B97-BB05-8F6DE3344532','1205','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',54900000,'8DB1395C-193E-4A87-9846-DE188F63525C','1029','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54900000,'8DC24FC7-06CA-4943-8D76-43405BC85238','1291','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'8E31CCDE-535B-4B37-98B5-0B8F9F484282','1070','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'8E3D8E99-5B35-470D-87BC-0FE852BEB3BE','1301','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',60600000,'8E7CB99F-77EB-496D-A708-A10B69963836','1024','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40800000,'8F21D5D2-D90F-4D54-ACD1-317FC0396098','1632','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',79200000,'8F30554F-1DD2-4730-A917-16B04F191743','1636','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'8F49B826-D0CB-4DFF-947C-BF1A00374FF4','1403','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'90B6E3C7-C31A-4D55-B184-70479D31AB58','1175','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',73800000,'90CEBE12-CB1F-45A5-ACF7-C34EA45789C2','1541','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'90DD96BB-6498-4021-A7CB-CBFC27B9A61D','1041','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',31800000,'90E35203-31C7-49F6-BE88-DBC485D1926F','1538','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',43200000,'919F36B9-8A1B-4DBE-8A56-E5A89A531766','1455','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45600000,'92215356-AEFD-40D9-8CCA-6D357D7A90E8','1263','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',38400000,'926D9E42-0DF7-4B89-806B-115FEB083D55','1669','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'92B2782F-9E22-4BC1-9580-031EF7CE8270','1617','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'92C6E8EE-7C27-4311-84FD-985971B8C019','1073','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'93F0E6FE-1F1F-41A8-9C83-4E38CDCCC498','1254','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'943A4472-5114-486D-95FE-3E985143DD28','1075','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'95323F85-E26F-438D-A57E-D56765FA1411','1277','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',52200000,'95603E10-6203-43B7-BEA4-ECFA4CD1A6F4','1157','',47700000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',72000000,'95A92A71-04AA-45D0-BF08-FCB4FDFFECB4','1431','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',50400000,'96622392-F373-46CF-BB18-311F329B4D7B','1053','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'96672EB3-1E8A-4D12-8365-13D6D1B7AE6D','1080','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'97292E4D-A823-4C0A-A0B7-2DBFD9CD06BB','1537','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',33300000,'97B67D23-7636-40D4-BB86-4E1A7613F6F4','1009','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',47700000,'983BAEB3-E9DE-429E-AEF1-A1B465FE7E72','1461','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',31800000,'98C64E80-5AE3-4ACC-9DB0-2DBE170E7CA4','1399','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',38700000,'98E8D720-4CB4-4C19-8132-B202A1996419','1616','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',66000000,'9952557B-0D6E-4809-8453-B0E9C382FCD5','1578','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'99A0C649-C8F4-4179-BAF1-E35689030BB9','1309','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'9AFDA0CA-08A8-4C00-A80B-59AD84641711','1439','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'9B94D797-5A3E-41D2-AA5E-4DB2C5252086','1548','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'9BE2638B-EAB5-4374-B4B0-098FFC83D9B2','1607','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46800000,'9BFC6C5C-6A34-481C-8B0D-76B492A0B1FC','1382','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'9C084CE9-4F6D-4048-B723-23636EBBA239','1507','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'9C157583-D02E-4C29-A141-4131FC9F2E32','1128','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',70200000,'9C52D0CE-4968-49EC-A295-A1128C8B4661','1095','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'9CC8829F-E136-4835-9366-09B6848D4241','1154','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',66000000,'9CE29948-5A4B-4E03-A065-40BF27861CDE','1270','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'9DE3D268-785B-4F6E-8BC5-199F8FB635AA','1061','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'9EB3D549-DA9F-4CE8-A28A-D62271C905F5','1037','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',44100000,'9F4DD653-D441-4A46-9F53-CF9D4709711D','1011','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'9F6ED052-F84D-4882-A329-DFF794C668CB','1603','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'9F9B7437-3862-4D14-943E-1DC7782650B2','1423','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'A02F4264-366B-43AC-900E-6F6D5802C067','1318','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'A0313DC3-DC48-4B74-8F74-5ED2BF13B83D','1222','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'A04CE4F4-F039-45B2-B3E7-B1EFB16DA8D3','1358','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'A17448A6-E11D-4DBD-B3A6-102608517611','1137','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45000000,'A2790C2C-74A5-4453-8752-4FD6230C3CD0','1200','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',82800000,'A2BB2DC0-9EA7-4937-9FD4-971A2D0A9661','1685','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'A2FC70F5-A650-489C-AB8E-70A5521A765A','1168','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'A34CC591-15C5-4508-8D7B-E842E2DAFA4B','1252','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'A38BF183-314A-4999-9373-061295426102','1518','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'A3AE8F57-B3FC-4EBA-884C-870889FBC275','1591','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'4244294C-65D4-42C7-85FF-1F73B95FE0ED','1453','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'42809C59-9AA1-4D31-BEEC-2D969D917F24','1043','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'429473BF-C49E-4C19-84FF-91F32AB24162','1055','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',55200000,'42B2D6C8-B871-488B-9698-18B7D64CA528','1577','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76500000,'42D4D1AB-DE0E-4F4B-86DF-49DFB76457E8','1219','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'42DA017B-C723-473E-8DF3-7ED8BE51027B','1048','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'THF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',78600000,'42E115ED-7A69-4E3C-95AC-7F20086A4D3B','1079','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'42EC99CA-3F00-4FEA-A9AE-4624CFC2C18B','1369','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'4328255B-A604-4BF2-A7B5-F5AB91B57856','1156','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',38700000,'43789477-0F82-4372-B5B3-EB314FFEC0D6','1610','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'4380849B-639A-4519-8420-2EA56E3FB229','1428','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45000000,'43E82438-4EC1-4EB8-B292-FCF0289CFC37','1241','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',62100000,'43F89184-CF73-43BC-9B08-089D9052359E','1589','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58800000,'441961FC-0333-4E84-9AB1-4368198F3C0C','1204','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',58800000,'444E934A-81E7-425A-B3D8-29C51384172B','1693','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'449FA5B7-65AB-4DBA-809B-24181A6EA41A','1522','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'44B53B2B-A8AB-4EB7-9A6F-06B2DCF59062','1633','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',67200000,'4510E293-B0BF-4F0D-A067-07F6EDF7AEBC','1670','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58500000,'452937E7-9B68-4B8E-B375-C3B455785AC3','1149','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'45362AE2-4C41-4290-B047-25FFE3C96DE0','1078','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49500000,'4575F35C-67E3-4089-98A7-34B493F2E7A6','1645','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'45AD5BC7-2D85-41CC-A827-B5C2ADE88043','1201','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'45FEA939-87FB-42FB-A210-17249EB71322','1233','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',65700000,'466086DE-2166-4D1A-AB09-D76948A54B1E','1503','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59100000,'46B56030-4FF0-4E0B-BDE6-A0CEA8E68949','1615','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',66600000,'46CB91D4-9866-408F-B79E-CCDC0F72BF34','1434','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',55200000,'46CF9C9B-8ED3-4325-8906-7D8ADA61D283','1508','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46800000,'481A1B56-CD4F-465D-945D-61DA69EAB68D','1449','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'481CBA53-3E27-4EDE-8317-AA382E2984A4','1513','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'483A5E59-AABD-428D-A644-363F4DBA3511','1430','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'4867B4A5-F6C5-4D7D-B10A-1FC2C10C5E8A','1458','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'4A3E6E63-53A5-4B98-8111-BCC41491FF9A','1034','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-18 00:01:01.000000','','',null,'4A6971D2-447D-4359-B949-F4EDF104A0AA','1032','',null,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.tba','batchjob',TIMESTAMP '2013-10-18 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',68400000,'4A939A69-375B-4DA7-ADD8-7AC7DAD9BB05','1502','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',33300000,'4AC9BCDC-CE63-44D1-AA8F-6CC10B66560A','1025','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 13:30:00.000000','','',53100000,'4BF3EA0E-EA0A-41CC-8561-FCB29D4C27CC','1698','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-22 13:30:00.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'4C0F7C63-D0DC-4ED0-B345-9815FBFA8BF0','1612','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'4C50E02E-A012-48A2-8EDB-4354A02B7D8E','1171','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'4C55FAD3-7811-4D29-B61B-6BEFCCD6721A','1064','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'4C5B6CED-2896-4C21-82BA-03FC07F049B3','1492','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',46800000,'4CC3A7FC-5498-440C-BCB6-E58F16001856','1691','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'4D77292D-6357-4CE2-AC5C-B066BF28D518','1243','',52200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63900000,'4DBADB52-EA32-4BD6-9082-7877757A7C53','1065','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'4DD4BC6E-1B9B-4450-8180-33343980DE42','1557','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'4E66CBB6-0F7C-4E55-8BE5-D8E8C92E105E','1207','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76500000,'4E881E93-7129-43E4-BD1D-BBC33164BB96','1570','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',85800000,'4E8D8F61-3232-4CDF-97E3-734FC0406068','1672','',82800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',64200000,'4F547FCC-3AAF-4D4D-8929-F12DC8E3AA89','1674','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'4F5CD85B-4F56-46E2-A731-818F87256448','1593','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'4F8A76A5-556C-4544-AEAC-860CA981A07B','1390','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'4F9D7964-677B-46ED-805C-4363B5059530','1324','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',73800000,'4FDD69F3-884E-4EAB-A254-9185D086AC41','1658','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',55800000,'50AB577D-777A-4050-BAD4-10927E0450ED','1491','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49500000,'50EE7202-7A3B-45E4-A672-83E3100DA864','1319','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'51B43532-16A0-44DB-A3F6-AC710CDA96E5','1571','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',50400000,'52213C6B-65D0-42BF-A699-E33212623800','1432','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'522CDEFB-B88F-4777-8E42-2F800D8C248A','1405','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39600000,'5250DF67-30E4-46D6-9734-FF5602B5A549','1344','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'5299C7CF-D5CC-4238-B355-840C6C1E9C62','1542','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48600000,'532E5BE3-7332-43F1-891F-4437A24D097B','1464','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48300000,'5392AE01-4F38-4B74-9CC8-52B53EA255E0','1642','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'53CAB5BB-BD3B-4436-91BC-481FCC1C0691','1529','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'53FA1296-AACA-4227-BFD8-13C6FE4C7E45','1419','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'54CB5B26-7462-4927-93CE-DC3D8CB05262','1583','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48000000,'54E5F5BF-553E-4665-B4FE-AAB9180439ED','1221','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'55B54D67-F441-4C98-9B1F-4D90BA89A5BB','1614','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40800000,'5615537E-2CB2-4CDC-B9D1-4B702495F998','1230','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58500000,'5634F610-BAB2-4FF8-8FC3-6D7A47B3248B','1435','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',57000000,'56F2B383-1DAD-4F22-B90F-D99FD64D856F','1023','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'58866C40-95D8-4178-934E-5A4CE631E58B','1519','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'58873D0E-72A2-4B03-BE22-DCE34CC245D5','1265','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'5ACAC1BF-7D7B-4C23-ADAF-A11EC62C1EE6','1422','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48600000,'5AD04338-5A80-439D-985B-BBD687BB9E37','1143','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49200000,'5B1D7339-EFC0-4C8F-9926-5002EECFA11F','1380','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',33600000,'5BC4C36B-D5D1-41CF-B8B3-8131D3F7DABA','1528','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',52200000,'5BCB99B9-1094-4065-8E14-90A98687EB9D','1560','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48600000,'5C411C16-C8E6-4498-B465-3E97B180D81D','1463','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',60300000,'5C9150AC-5814-44B9-9607-00A2F5D2CED8','1014','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'5CA5DCC5-D9F5-435E-9D6F-0F916BB85939','1183','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'WF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'5D0492A1-C24A-42DA-B38C-AD0BC42FBB1C','1099','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',50400000,'5D120777-73E7-4B59-A186-FF78BEDDD0C9','1255','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'5D34D723-0F6F-46A1-BCFF-696DE75B366C','1599','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'5D5E64B5-AB70-498F-82E0-B56653D9C86D','1223','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'5D86A07A-FF97-40C2-BDD4-34B46799F2C3','1198','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'5D8D9062-E50D-46F2-B7C2-6015C425EC0C','1479','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42300000,'5DC1B25E-5E86-4BAE-B4D3-148D4B2B3490','1489','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',65700000,'5E0A72E0-155F-4499-91F1-CF15315CDEED','1653','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'5E510FE9-ED20-4EEC-A933-6836084B1CAA','1353','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',51600000,'5E700029-B5A7-4DCA-9DBC-CE39F35FB241','1457','',48600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'5EDF39D5-C4EB-46E5-9251-87B2D27F1816','1393','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63900000,'5F3EA70B-9FE2-405B-B037-E65FC2B25A76','1107','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'5FEA2AA2-5DB0-4AA9-9810-1B5774221FF7','1066','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'601BFD07-ADC0-48A1-925A-C458128B90A0','1114','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',56400000,'609C6A94-1CBC-4144-89BC-04D03D3B4116','1581','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',80400000,'60B17347-FE6C-401C-9EF1-CADB4D605EEC','1307','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42000000,'610C9688-8444-4458-B2CF-8E6A070146AA','1058','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'61F75277-A312-41CF-9CD7-922E9E8DD40B','1074','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'62DC1FBC-F334-40FF-8C36-F3FE62AE3FE3','1180','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'6369E9D5-2522-4FFB-BCF0-3CF28CC8E280','1268','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',35400000,'63D17753-1E28-4B87-8561-8FB918B416A0','1017','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'641B6A22-EB1B-42D6-B02C-BF0FE1F488C7','1146','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',53400000,'6439F6AB-8104-45D1-8517-5D7607B29477','1022','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',62100000,'6483085C-6B51-4A2C-98A1-3BC253CC5D54','1190','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'64A03E35-0591-400E-9535-6100745D8137','1543','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',52200000,'65732AB0-F4A2-4F73-8CA7-D4DAA095A4D4','1386','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60300000,'65D4ED85-6351-43AA-A59F-1038145EDDCA','1586','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'661F33A8-21C4-4218-91DB-EF72A8FE16E7','1539','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'668CE479-6F6F-447A-9324-DDDC0BFA81CB','1559','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',62100000,'66D9C1BE-68C5-46D3-B63B-3BC5E944E6E2','1308','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',74700000,'6766BFA0-7013-4982-8156-9CC1939C1253','1049','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',36900000,'67FAE696-A166-4EC1-B361-601853F544FC','1407','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'68C3FDC1-5E68-4250-B369-1748746D3966','1361','',37800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'68CBFF29-1352-47B0-A912-C080DAE4B1E7','1605','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',62100000,'691D33BE-8791-483F-BB5C-9A69705CED96','1686','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'AD9BF7FF-B936-4ACC-8FC5-82F114BB0603','1500','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'AE466582-0DD5-456F-ACB2-54B1CDFCEA3E','1248','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'AEC6E52D-8F4A-4E65-9084-B4305B1CC08B','1069','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',31800000,'AF0FD8C2-6483-466E-AEA3-9EA853AD6B99','1388','',27300000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',44100000,'AF6F4719-A043-4FF2-A85B-1D4122DF5F41','1654','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',66600000,'AF85975F-02F0-4DAD-B73F-FF4E0982035B','1496','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'AFE1D250-CE62-48C7-BED3-42E8BD3064EB','1052','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'B045B5EB-945B-4985-BB71-3A024531074B','1482','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76200000,'B053B93D-0990-47BC-B3F3-B1C52EDDF124','1609','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'B095DAC8-1848-4966-BB2C-4691F505BCD4','1105','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'WF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',56400000,'B0BABF0D-48A4-490C-9EAE-985D301A9422','1494','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'B1C3003C-9DA6-4664-B90E-959EA6D6D594','1391','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',57000000,'B1E89493-F841-4FC9-B415-96BA42FC42C6','1656','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MTH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',68400000,'B21F4E4B-73D9-4018-B5F8-53BB9DC92E05','1313','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'B2CB785C-12C5-4E66-8515-7999F1230E37','1340','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',43200000,'B2DD8DD0-157C-4F90-BC30-AB4F94D63531','1452','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',55800000,'B2DE7A4B-A24D-490E-91F9-E7947BB1869D','1608','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'B5280043-5E80-455A-A14C-DBE5D9808D97','1104','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',73200000,'B57591A2-9D89-4721-811E-F73BE771981A','1444','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',64200000,'B57C5E35-ABAE-4D5F-B677-87A7ED441555','1660','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'B5A7342A-A06A-4F55-8B74-B667B9A4265C','1216','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'B5F00F30-995A-48E8-869F-FC475AE2C763','1626','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'B61D4E01-82C7-4B59-9BCD-261EA5E78770','1549','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',75000000,'B637B4B3-0E62-4151-9512-BC8C56ABB55F','1662','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'B6AD57EB-7889-4F0A-B5A8-587882B7A050','1412','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'B7C642A5-3659-4B81-A766-827A6C26266E','1129','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',59400000,'B7DF3868-561F-481E-811C-06D676B0CC01','1209','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 13:30:00.000000','','',30900000,'B7F3E889-027A-41C8-90EC-2D34F39924A1','1695','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-22 13:30:00.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',66600000,'B85AF51B-8C92-46D7-8638-7A193BD2D6D0','1169','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'B971F5A5-E818-40EA-8E5E-6DFD8D1A3B31','1123','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'B9AF3393-3E65-4FFC-96C7-E4B6F3FE9674','1142','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',69600000,'B9CC9BE7-9F62-4ABB-B52D-1179B5EA8069','1437','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'B9EB9D0D-8D47-4B8B-8481-CCA9DBCEBFE9','1218','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'WF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45900000,'BA43741B-C5AA-4FF1-8994-D588FF929944','1158','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'BBB52DD6-F9A5-4C64-8584-6D1814E66EA7','1652','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'BC5E98BB-2F68-4C15-8BD8-EDF37194FEB3','1385','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'BD194678-2D2A-4AE5-922F-3FAB9769E0E3','1485','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'BDE04493-C0B3-46DB-B0C5-F3A75FF7ED17','1414','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'BDE79419-55BA-41F4-8B66-4A4B9F1A7E21','1179','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'BDF1E47A-00EB-4B26-A6ED-A2649341BC89','1132','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'BE02EFC9-493B-4166-AEB3-F159AB1C0CFA','1116','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',55800000,'BF42D090-7651-45E4-9DC6-057777DB9093','1258','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',38700000,'BF75B858-5AFB-40B1-8715-84B27B19D8A4','1572','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'BF889AC9-8246-4827-BC55-950933C295D4','1447','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'BFB6D19B-82D0-412C-B9E9-3884962589B3','1413','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'BFDE366F-6560-4E2E-9C1D-591E6351E086','1232','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',73800000,'C0654100-5805-479D-89D8-954948B853AF','1259','',63900000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'C0E0A630-B620-451F-AF1C-22EF510A612E','1574','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76200000,'C0FAFBCD-2231-428A-876C-4295011DCD94','1039','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',68400000,'C1BDAF60-EAC1-4A07-B223-86100C7E369C','1111','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'C2184E0C-CAEF-4B57-9826-D49124621470','1191','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'C23CAD01-B010-4547-810D-4AE9EBEE714D','1033','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76500000,'C2BB1E4F-CB0F-4C9A-BDAC-C3F28A13E1B7','1442','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'C2BF621E-7183-406C-9F58-53AF659444D5','1477','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'C2CD3326-63B1-47FC-85A1-28CB6C71047B','1077','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'C41B8A3C-2EAF-4A3A-B847-CDF101BC48FF','1140','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',54900000,'C4D4A49B-97E0-4966-A41F-BE359B3C1B70','1013','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',69000000,'C4D59086-DA8F-4D7E-96CD-5E5913184C08','1251','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'C560B290-2428-4FAE-9E5A-F962739CC8C5','1166','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'C5770E8B-7026-4375-91FA-2DC3046C91BF','1374','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'C5A4DECD-5E41-4792-9220-8225A144F5F6','1373','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'WF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'C5C30E2C-14A4-4BCF-8540-875855F72F40','1094','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',31800000,'C68B209A-DAC6-4A85-AE77-4749218B18A9','1445','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'C72A2558-ECDF-47E6-844C-34DC53AC43A0','1337','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'C74DEC0E-447F-434C-AEFA-3CE78956A50B','1622','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',78600000,'C76C1CA4-F764-46DA-87B1-805F4FF4AB85','1256','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'C76FDDB7-7EEB-4AFC-965D-4F7861B673A3','1187','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',55800000,'C7CDC993-247D-4B69-B15E-EA5CCF8DEA5E','1173','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',70200000,'C8D4C6D8-F97A-4A11-BDB1-477F23D0418E','1547','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',49500000,'C9198121-14B3-4B21-8DC4-AB88EEABB057','1028','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',43200000,'C9DD3D7A-5D97-4119-B142-50F0216B0407','1131','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46800000,'CA69B1EE-B005-4AF6-8BC5-832CFF5B614D','1285','',41400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',52200000,'CB262428-CBA8-4273-9981-433F67BF8373','1675','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'CC8BA79D-8DBC-4DFB-BCF7-60EB9E5503CB','1224','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',69600000,'CCFF6D3E-6A8C-4D1E-81DC-0221DBE8B6F8','1315','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63000000,'CD81DB46-2D5B-4D30-B8E7-FBED791B276D','1310','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'CE57160E-B3E3-462F-9DD4-13569B7669D1','1384','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'CF0E6EFB-EBD0-4F75-B519-DC5EF0ADBEB7','1588','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'CF215B9C-DCD3-45F4-81E8-5786A51760AC','1272','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'CF2BE9E3-A0C3-4920-92BE-EAFC9BD02781','1536','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'D0A11F80-63F9-4F2F-B238-D5B9F407B8B6','1568','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',45000000,'D101490C-1838-4029-A503-D8996C32093B','1676','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'D138B95F-0436-41EE-B1DB-602641A88D2B','1188','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'D138EC22-ECA2-4FB4-9E3F-F14FBFCD6194','1505','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',50400000,'D162424A-F5AB-47D4-B245-F972763B03EB','1383','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48000000,'D192C7E8-CB7A-4C5E-BB83-817D216BCE12','1326','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',78600000,'D1BEF14A-1204-4417-BAAC-69B2E4E6D782','1515','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'D2209FEC-568B-4F00-87AC-344C7CD279A6','1281','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40500000,'D2456A42-F766-4F0B-B14B-CA1F7117DB84','1286','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60300000,'D24C15CC-6CE2-4219-BF61-6DDEB1A8AD65','1573','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'D28494FC-02AE-4A31-8F68-3D76CCC70B03','1038','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60300000,'D2A3B9C7-2249-4F44-B33E-33D76C52A226','1239','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'D2D70167-D811-4BEE-BD8D-D0FD78CDF1F0','1484','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39600000,'D314620F-77D5-4DC1-A02C-A8AEC24E5A63','1381','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'D3178C5C-EAF4-4E4A-B6BD-4CBB50B0481A','1467','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',48000000,'D52A8142-6384-4ED7-9A49-51C74D4C1BBE','1690','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'D5448BCE-A25A-4AEA-B98C-F6E68062314F','1579','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',66600000,'D5C5E3F8-E45E-4239-8869-5A1701F47BB5','1054','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',79200000,'D5EE6A2C-E716-43FB-9DEC-BE0807000EC4','1659','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44100000,'D69E962C-C332-44A6-AF7B-4417ADD05436','1269','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45000000,'D705410D-BC89-44A2-85A1-381F84D4BEFC','1546','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46800000,'D7238E62-198D-447D-96DF-87EC8BA332D0','1634','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',50400000,'D74FDCA6-E217-4605-8CB1-ADC80D0E8DB2','1611','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60300000,'D7AA5E8C-8059-468E-8BEB-94C5ADEB6639','1130','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'D7F5559D-9F2E-4748-9A9B-BF83F4F8799C','1516','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'D862269D-1D31-4FCB-86F5-EFBBDC9DCDF9','1186','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',46200000,'D9B1D413-93C8-4A92-9641-62D179EE2F07','1004','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'DA2F7901-E4C8-4741-9376-4CE0E526C40E','1490','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'DA37E205-231A-4013-AF0C-58ACD221E10B','1165','',52200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40800000,'DA48AEF9-FDD1-460A-BB31-2F2AE3B05793','1210','',30600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'DAD84A63-8618-4EBA-84B7-0EA311A12E7B','1336','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'DB49B7DC-90B0-4695-8E90-1CA3FEEC9045','1402','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'DBD9D489-75A7-404A-B3B7-3CE770494AAE','1514','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',72000000,'DBDF6329-48D3-4461-BAF5-754CA06FF034','1238','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',65700000,'DC482A6D-90C0-40EA-887A-D5A38D0D651F','1144','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'DC549069-D914-446E-822F-71C250CDB95E','1161','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'DC97347A-FA2C-4B9F-8E01-289E4113AC7D','1321','',72000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'DCE5C67A-F4D4-4CF0-9078-F6E4F0611221','1211','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'DCF62C5D-E193-4629-842E-1F42B31B346C','1304','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'DD3026E0-B88D-46E3-8FDB-FE4063456872','1443','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'DD3A25B7-04D0-4B51-8981-C84150E57A78','1261','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60300000,'DE775B01-DD1B-43DF-9A12-2A419FF9C3FD','1587','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75000000,'DEAE395F-845D-49CF-B246-44450C1FB049','1278','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76200000,'DEE032C2-24B9-4ABA-9902-493CC1621FC7','1081','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'DEFE7100-337E-4213-841E-076213ADB796','1103','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',75600000,'DF18D7BB-770B-4DE1-9B26-A42D660BCD93','1427','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'DF8B40A8-F5FE-4D59-8C7D-5064AD89E0B0','1247','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'DF96232C-FFA3-46DF-8320-41498EAAF9D5','1478','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',69600000,'DFB7F3D7-B91F-45AF-A752-5823A084F017','1411','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35100000,'DFF1FBDE-1E5B-4479-8AFB-1B6C3C21D720','1097','',30600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'E0767C17-229F-438D-ACE8-65B07CD8EA74','1371','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'E1C69ED4-9B4E-4824-8EED-5C4A53EB3356','1297','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57600000,'E2790651-2501-4FEC-987B-ED69B1CAFF5A','1249','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'E2BFC1F2-4A10-4EB8-A55C-E5657C1F5555','1629','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48600000,'E2ED0109-CC42-4C50-8BFB-EFC3503BA017','1082','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'E3713AF6-4C04-4EB8-A4C1-4EBB34667A4B','1141','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',63900000,'E379C91E-795C-4572-815E-53BD42F224E5','1499','',59400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76200000,'E5279B57-DA8A-465C-9BA8-29C5D1FCD2A6','1098','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'E5A2126B-81DE-4E02-AFE6-E2A4A6532DE6','1145','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',61200000,'E623F378-4094-40BA-A3ED-7F6707D6A10C','1689','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'HF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39600000,'E6C85BAD-4692-4E2A-A836-F5C4680722F8','1359','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76800000,'E7067A7C-AC0C-4528-8082-A980997714B4','1468','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'E846FAB0-ABEA-4BD8-92C6-FCB07BE53A0A','1124','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',51600000,'E8815469-7438-4827-AA4F-64D0CB396A55','1590','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'E8DB0412-465F-4FF1-B885-C47F52381D78','1136','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58500000,'E8DD4EF1-66CD-43C8-963B-B4A4430B0621','1062','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',52800000,'E990556E-C4E6-4468-A2B3-43A24BE044E4','1604','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',71400000,'E9B13A9C-575A-4123-BFFC-CA013D1D1179','1375','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39600000,'E9D7EB8D-090A-4C97-9C8A-0679DDB9DA16','1619','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',54000000,'EA30F39F-F40A-4E4F-96D1-4177E0B2801E','1524','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',58500000,'EC495228-70E1-4E3A-AF5A-225DF9484478','1661','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'TW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'ECCBBE31-C2E7-4E18-9509-45AC59184B3B','1303','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40500000,'ED37CD31-F1F7-4527-8EC6-F872ACE58584','1585','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',50400000,'ED665A22-497E-4424-8737-FB5A5A8DC29E','1680','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',60600000,'EDB46AC8-A5A7-4563-AB19-90D11C85EF66','1008','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',38700000,'EDCE5E14-A70B-4DA7-800A-AF993C0953E7','1262','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',79200000,'EE0CD01A-89CB-4231-AEAE-DAAC7C2BEECA','1316','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58500000,'EE857469-D9FA-408D-BFB7-2806667CE72D','1289','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',38700000,'EEA1A874-2764-42CF-B3AA-60415ACB6B7C','1026','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',42600000,'EF7CE615-288E-442A-B679-15345127D22D','1003','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'F03CFE80-660D-4FD5-9185-AD19B1B24AF8','1451','',54000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76800000,'F0587526-54CC-4365-9FC1-7674F083A447','1181','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',75600000,'F075F022-A5D2-4929-966A-5E7CB8BE6636','1687','',68400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'F0E14E8F-5629-495C-A375-2CBAD90471B4','1177','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',57000000,'F0EEADF2-9414-4D84-8A45-F06A7AE7CCB4','1135','',52200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',31800000,'F1080879-421A-4554-93DA-9580F5B42DEE','1016','',28800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'F1984A8D-DA1A-4550-93BA-5D68FB47ED58','1553','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',58800000,'F1C42DB7-88EE-42FB-AD4E-0A771C4CFAE9','1083','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',42600000,'F1CF73F1-B779-4E84-BC73-76D0A6327183','1019','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46800000,'F24D79A3-F606-4F9E-8BF0-67A072CEFDD3','1521','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45900000,'F29526ED-F08C-44DF-AD7A-882DF62D1269','1456','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-21 00:01:01.000000','','',58500000,'F29E0905-963D-4946-80E8-CC308F5E734D','1665','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-21 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',76200000,'F2A9EABE-B877-44E8-88BB-383F7F244262','1342','',66600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',70200000,'F2C378DE-6740-4B72-B09B-EF7F464F1038','1480','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',37800000,'F310C038-B96B-41FF-B3FC-ED6155FFA505','1193','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',45600000,'F3383C72-B1F7-4500-B246-8626C8811BF1','1199','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'F3727B88-7382-47E9-8F25-A6300B242DFD','1333','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',65700000,'F390D88E-EF9B-415E-9398-C7BA6081CF44','1031','',61200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'TH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',38700000,'F3C69169-B8D5-46DD-B5FE-3228AC1ECC12','1469','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'F3CC8542-05DD-424D-B45D-782840DF7D3C','1440','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',49800000,'F4710646-27F2-4CA1-9357-316EE1EB886B','1401','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',56400000,'F49FC722-1E43-4187-B898-B92FA454C716','1317','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',50400000,'F520D9DB-A1FE-4FC1-A177-39F7849A053A','1565','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',42600000,'F675142B-8D33-4DF9-9C1B-049DFDA6DEA3','1121','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',80400000,'F694DCE4-C50F-4A12-8398-3EB7C7819E11','1060','',70200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',40500000,'F6969382-F69F-476B-B004-1E78C50BBD55','1580','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',62400000,'F6B1AB65-844D-40CA-887F-11170B5EC47E','1040','',55800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'F70355B0-DD73-470E-A403-C3CEB1AD1DC1','1215','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',69300000,'F710B162-BB47-4566-8704-354B089FACA3','1155','',64800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48600000,'F778DA78-BFEF-44DD-AB5E-B0300B4C6E1C','1446','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44400000,'F83EF37F-64F9-4397-AC78-72BD1E1E547E','1152','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MTWHF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',60600000,'F96F3038-ED4E-4D27-AC10-0F4BC5C9E76E','1639','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-02 03:05:20.000000','','',49800000,'F9ABD004-FEA0-446D-88CD-DFDF7D89A750','1005','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','batchjob',TIMESTAMP '2013-10-02 03:05:20.000000',0,'MWF')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44100000,'F9F12B11-5C9D-4598-A495-6C6708FD08B9','1182','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',52200000,'FAB218EA-3AA9-4AA9-82B2-7B1930FD9D8E','1493','',45000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64800000,'FB7228FE-DADF-4CD7-9032-BB9C2778A9CE','1429','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',35400000,'FB8F38D8-F6D5-49B8-9439-CFA30A19C17B','1354','',32400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',44100000,'FCA8C79A-3C65-4AFA-85C7-55928BD79430','1523','',39600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'W')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',61200000,'FD0667F5-3570-4E1C-A548-AA12450A1A34','1387','',50400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'MW')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',67800000,'FD562430-5E11-48E2-A98B-6C7F25F653B3','1047','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'TWH')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48000000,'FDAB76EC-45EC-4D43-9240-F2266A25CFBA','1338','',41400000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'F')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',64200000,'FDF33EF5-24F4-4F3C-91EB-97A8217C4BE2','1298','',57600000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',39000000,'FE27F9E0-9570-4277-83C2-DC8F10FD1F8D','1089','',36000000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',48000000,'FE90A03F-ED05-4F33-AAE8-DAF28E10919C','1164','',34200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'H')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',53400000,'FF9569CC-DD0D-4B51-968F-6B22C2EE2A23','1555','',46800000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'T')
/
Insert into t_fix_tmslot_pk (createid,createtime,descr_formatted,descr_plain,end_time_ms,id, name,obj_id,start_time_ms,tm_slot_state,tm_slot_type,updateid,updatetime,ver_nbr,weekdays)
 values ('batchjob',TIMESTAMP '2013-10-22 00:01:01.000000','','',46200000,'FFA59808-181D-4118-AEC1-DCD19971659C','1470','',43200000,'kuali.scheduling.timeslot.state.active','kuali.scheduling.time.slot.type.activityoffering.adhoc','batchjob',TIMESTAMP '2013-10-22 00:01:01.000000',0,'M')
/
--add old_id column...to record time-slot pk value when generated script runs..
alter table t_fix_tmslot_pk add old_id varchar2(255)
/

update t_fix_tmslot_pk t1 set t1.old_id=
(select t2.id from ksen_sched_tmslot t2
  where (t1.end_time_ms=t2.end_time_ms or (t1.end_time_ms is null and t2.end_time_ms is null))
    and (t1.start_time_ms=t2.start_time_ms or (t1.start_time_ms is null and t2.start_time_ms is null))
    and (t1.weekdays=t2.weekdays or (t1.weekdays is null and t2.weekdays is null))
    and t1.tm_slot_type=t2.tm_slot_type)
/
-- insert dup tmslots with hardened keys...and temporarily prefix tm_slot_type with 'X' to avoid unique index constraint
INSERT INTO KSEN_SCHED_TMSLOT (CREATEID,CREATETIME,END_TIME_MS,ID,NAME,START_TIME_MS,TM_SLOT_STATE,TM_SLOT_TYPE,UPDATEID,UPDATETIME,VER_NBR,WEEKDAYS,OBJ_ID)
select CREATEID, CREATETIME, END_TIME_MS, ID, NAME, START_TIME_MS,TM_SLOT_STATE, 'X'||TM_SLOT_TYPE,
UPDATEID,UPDATETIME, 0, WEEKDAYS,sys_guid()
from t_fix_tmslot_pk a
/
--No set all FK values to the new hardened key values

update ksen_sched_cmp_tmslot t1
set t1.tm_slot_id =
(select t2.id
from t_fix_tmslot_pk t2
where t1.tm_slot_id =t2.old_id)
/

update ksen_sched_tmslot_attr t1
set t1.owner_id =
(select t2.id
from t_fix_tmslot_pk t2
where t1.owner_id =t2.old_id)
/

update ksen_sched_rqst_cmp_tmslot t1
set t1.tm_slot_id =
(select t2.id
from t_fix_tmslot_pk t2
where t1.tm_slot_id =t2.old_id)
/
--drop non-hardened PK time-slots

delete from ksen_sched_tmslot t1
where t1.id in
(select t2.old_id from t_fix_tmslot_pk t2)
/
--now we can drop 'X' prefix from tm_slot_type column

update ksen_sched_tmslot set tm_slot_type=substr(tm_slot_type,2)
/

drop table  t_fix_tmslot_pk
/
