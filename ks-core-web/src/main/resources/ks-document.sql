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
INSERT INTO KSDO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RT-DESC-DOCUMENT-1', '', 'Documents attached to a proposal')
/
INSERT INTO KSDO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RT-DESC-DOCUMENT-2', '', 'Documents attached to a Course')
/

INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('documentCategory.proposal','Proposal Documents','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'})
/
INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('documentCategory.course','Course Documents','RT-DESC-DOCUMENT-2',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'})
/

INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.doc', 'doc Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'doc')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.pdf', 'pdf Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'pdf')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.png', 'png Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'png')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.gif', 'gif Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'gif')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.jpg', 'jpg Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'jpg')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.docx', 'docx Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'docx')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.xls', 'xls Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'xls')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.txt', 'txt Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'txt')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.rtf', 'rtf Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'rtf')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.xml', 'xml Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'xml')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.mp3', 'mp3 Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'mp3')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.wav', 'wav Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'wav')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.ppt', 'ppt Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'ppt')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.zip', 'zip Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'zip')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.rar', 'rar Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'rar')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.tar', 'tar Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'tar')
/
INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.gz', 'gz Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'gz')
/