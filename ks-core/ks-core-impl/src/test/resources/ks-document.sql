--
-- Copyright 2010 The Kuali Foundation Licensed under the
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

// RichText
INSERT INTO KSDO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RT-DESC-DOCUMENT-1', '<p>Document 1</p>', ' 1')
/
INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('CAT_1','CATEGORY 1','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'})
/
INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('CAT_2','CATEGORY 2','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'})
/
INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('CAT_3','CATEGORY 3','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'})
/


INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.type1', 'PDF Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'PDF')
/



