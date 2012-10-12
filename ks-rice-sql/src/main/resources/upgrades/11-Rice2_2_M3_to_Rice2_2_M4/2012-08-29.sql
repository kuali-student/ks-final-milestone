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

--
--     KULRICE-6676 - external message tables
--
CREATE TABLE KRAD_MSG_T
(
	NMSPC_CD VARCHAR2(20) NOT NULL,
	CMPNT_CD VARCHAR2(100) NOT NULL,
	MSG_KEY VARCHAR2(100) NOT NULL,
	LOC VARCHAR2(255) NOT NULL,
	OBJ_ID VARCHAR2(36) NOT NULL,
	VER_NBR DECIMAL(8) DEFAULT 1 NOT NULL,
	MSG_DESC VARCHAR2(255),
	TXT VARCHAR2(4000)
)
/

ALTER TABLE KRAD_MSG_T
    ADD CONSTRAINT KRAD_MSG_TC1
PRIMARY KEY (NMSPC_CD, CMPNT_CD, MSG_KEY, LOC)
/

ALTER TABLE KRAD_MSG_T
    ADD CONSTRAINT KRAD_MSG_TC2
UNIQUE (OBJ_ID)
/
