--
-- Copyright 2005-2012 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

Insert into KSEN_ATP_TYPE (TYPE_KEY,OBJ_ID,VER_NBR,TYPE_DESC,EFF_DT,EXPIR_DT,NAME,REF_OBJECT_URI) values ('kuali.atp.milestone.LaborDay',null,0,'Labor Day',null,null,'Labor Day','http:--student.kuali.org/wsdl/atp/MilestoneInfo')
/
Insert into KSEN_ATP_TYPE (TYPE_KEY,OBJ_ID,VER_NBR,TYPE_DESC,EFF_DT,EXPIR_DT,NAME,REF_OBJECT_URI) values ('kuali.atp.milestone.FallBreak',null,0,'Fall Break',null,null,'Fall Break','http:--student.kuali.org/wsdl/atp/MilestoneInfo')
/
Insert into KSEN_ATP_TYPE (TYPE_KEY,OBJ_ID,VER_NBR,TYPE_DESC,EFF_DT,EXPIR_DT,NAME,REF_OBJECT_URI) values ('kuali.atp.milestone.ThanksgivingBreak',null,0,'Thanksgiving Break',null,null,'Thanksgiving Break','http:--student.kuali.org/wsdl/atp/MilestoneInfo')
/

Insert into KSEN_MSTONE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,END_DT,IS_ALL_DAY,IS_DATE_RANGE,IS_RELATIVE,RELATIVE_ANCHOR_MSTONE_ID,NAME,START_DT,MSTONE_STATE,MSTONE_TYPE,DESCR_FORMATTED,DESCR_PLAIN) values ('hc-holiday1',null,0,null,null,null,null,to_timestamp('04-SEP-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),0,1,0,NULL,'hc-holiday-one',to_timestamp('03-SEP-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'kuali.milestone.state.Draft','kuali.atp.milestone.LaborDay','<p>2012 Labor Day</p>','2012 Labor Day')
/
Insert into KSEN_MSTONE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,END_DT,IS_ALL_DAY,IS_DATE_RANGE,IS_RELATIVE,RELATIVE_ANCHOR_MSTONE_ID,NAME,START_DT,MSTONE_STATE,MSTONE_TYPE,DESCR_FORMATTED,DESCR_PLAIN) values ('hc-holiday2',null,0,null,null,null,null,to_timestamp('01-OCT-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),0,1,0,NULL,'hc-holiday-two',to_timestamp('01-AUG-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'kuali.milestone.state.Draft','kuali.atp.milestone.FallBreak','<p>2012 Fall Break</p>','2012 Fall Break')
/

Insert into KSEN_ATPMSTONE_RELTN (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,ATP_ID,MSTONE_ID) values ('hc-holiday-rel-1',null,0,null,null,null,null,'testAtpId2','hc-holiday1')
/
Insert into KSEN_ATPMSTONE_RELTN (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,ATP_ID,MSTONE_ID) values ('hc-holiday-rel-2',null,0,null,null,null,null,'testAtpId2','hc-holiday2')
/
