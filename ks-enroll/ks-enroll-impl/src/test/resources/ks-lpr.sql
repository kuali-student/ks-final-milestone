--
-- Copyright 2011 The Kuali Foundation Licensed under the
-- Educational Community License, Version 2.0 (the "License"); you may
-- not use this file except in compliance with the License. You may
-- obtain a copy of the License at
--
-- http://www.osedu.org/licenses/ECL-2.0
--
-- Unless required by applicable law or agreed to in writing,
-- software distributed under the License is distributed on an "AS IS"
-- BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
-- or implied. See the License for the specific language governing
-- permissions and limitations under the License.
--

// LuiPersonRelationEntity
INSERT INTO KSEN_LPR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, PERSONID, LUIID, COMMITMENTPERCENT, EFFECTIVEDATE, EXPIRATIONDATE, RELATION_TYPE_ID, RELATION_STATE_ID, VER_NBR) VALUES ('testLprId1', 'admin', {ts '1900-01-01 00:00:00.0'}, 'admin', {ts '2000-01-01 00:00:00.0'}, 'testPersonId1', 'testLuiId1', .5, {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.lpr.type.registrant', 'kuali.lpr.state.registered', 0)

INSERT INTO KSEN_LPR_TRANS (ID, REQ_PERSON_ID, LPR_TYPE_ID, STATE_ID, CREATETIME, UPDATEID, UPDATETIME) VALUES ('testLprTransId1', 'admin-1', 'kuali.lpr.trans.type.register', 'kuali.lpr.trans.registered', {ts '1900-01-01 00:00:00.0'}, 'admin', {ts '2000-01-01 00:00:00.0'})

INSERT INTO KSEN_LPR_TRANS_ITEMS (ID, PERSON_ID, NEW_LUI_ID, EXIST_LUI_ID, TYPE_ID, STATE_ID, LPR_TRANS_ID) VALUES ('testLprTransItemId1', 'Student1','Lui-1',null, 'kuali.lpr.trans.item.type.add','kuali.lpr.trans.item.state.new', 'testLprTransId1')