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

-----------------------------------------------------------------------------
-- KSEN_PROCESS
-----------------------------------------------------------------------------

CREATE TABLE KSEN_PROCESS
 (
      ID VARCHAR2(255),
      OBJ_ID VARCHAR2(36),
      VER_NBR NUMBER(19,0),
      CREATEID VARCHAR2(255),
      CREATETIME TIMESTAMP (6),
      UPDATEID VARCHAR2(255),
      UPDATETIME TIMESTAMP (6),
      NAME VARCHAR2(255),
      RT_DESCR_ID VARCHAR2(255),
      STATE_ID VARCHAR2(255),
      TYPE_ID VARCHAR2(255),
      OWNER_ORG_ID  VARCHAR2(255)
 )
/

-----------------------------------------------------------------------------
-- KSEN_PROCESS_TYPE
-----------------------------------------------------------------------------

CREATE TABLE KSEN_PROCESS_TYPE
(
    TYPE_KEY VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
	VER_NBR NUMBER(19,0),
	TYPE_DESC VARCHAR2(2000),
	EFF_DT TIMESTAMP (6),
	EXPIR_DT TIMESTAMP (6),
	NAME VARCHAR2(255),
	REF_OBJECT_URI VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_PROCESS_ATTR
-----------------------------------------------------------------------------

CREATE TABLE KSEN_PROCESS_ATTR
(
        ID VARCHAR2(255),
        ATTR_NAME VARCHAR2(255),
        ATTR_VALUE VARCHAR2(2000),
        OWNER VARCHAR2(255),
        OBJ_ID VARCHAR2(36),
        VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_PROCESS_TYPE_ATTR
-----------------------------------------------------------------------------

CREATE TABLE KSEN_PROCESS_TYPE_ATTR
(
        ID VARCHAR2(255),
        ATTR_NAME VARCHAR2(255),
        ATTR_VALUE VARCHAR2(2000),
        OWNER VARCHAR2(255),
        OBJ_ID VARCHAR2(36),
        VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_PROCESS_RICH_TEXT
-----------------------------------------------------------------------------

CREATE TABLE KSEN_PROCESS_RICH_TEXT
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    FORMATTED VARCHAR2(2000),
    PLAIN VARCHAR2(2000)
)
/

-----------------------------------------------------------------------------
-- KSEN_CHECK
-----------------------------------------------------------------------------

CREATE TABLE KSEN_CHECK
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    CREATEID VARCHAR2(255),
    CREATETIME TIMESTAMP (6),
    UPDATEID VARCHAR2(255),
    UPDATETIME TIMESTAMP (6),
    NAME VARCHAR2(255),
    RT_DESCR_ID VARCHAR2(255),
    STATE_ID VARCHAR2(255),
    TYPE_ID VARCHAR2(255),
    ISSUE_ID VARCHAR2(255),
    MILESTONE_TYPE_ID  VARCHAR2(255),
    AGENDA_ID   VARCHAR2(255),
    PROCESS_ID  VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_CHECK_TYPE
-----------------------------------------------------------------------------

CREATE TABLE KSEN_CHECK_TYPE
(
    TYPE_KEY VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
	VER_NBR NUMBER(19,0),
	TYPE_DESC VARCHAR2(2000),
	EFF_DT TIMESTAMP (6),
	EXPIR_DT TIMESTAMP (6),
	NAME VARCHAR2(255),
	REF_OBJECT_URI VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_CHECK_ATTR
-----------------------------------------------------------------------------

CREATE TABLE KSEN_CHECK_ATTR
(
    ID VARCHAR2(255),
    ATTR_NAME VARCHAR2(255),
    ATTR_VALUE VARCHAR2(2000),
    OWNER VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_CHECK_TYPE_ATTR
-----------------------------------------------------------------------------

CREATE TABLE KSEN_CHECK_TYPE_ATTR
(
    ID VARCHAR2(255),
    ATTR_NAME VARCHAR2(255),
    ATTR_VALUE VARCHAR2(2000),
    OWNER VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_CHECK_RICH_TEXT
-----------------------------------------------------------------------------

CREATE TABLE KSEN_CHECK_RICH_TEXT
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    FORMATTED VARCHAR2(2000),
    PLAIN VARCHAR2(2000)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR
-----------------------------------------------------------------------------

CREATE TABLE KSEN_INSTR
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    CREATEID VARCHAR2(255),
    CREATETIME TIMESTAMP (6),
    UPDATEID VARCHAR2(255),
    UPDATETIME TIMESTAMP (6),
    STATE_ID VARCHAR2(255),
    TYPE_ID VARCHAR2(255),
    EFF_DT TIMESTAMP (6),
    EXPIR_DT TIMESTAMP (6),
    PROCESS_ID  VARCHAR2(255),
    CHECK_ID  VARCHAR2(255),
    MESSAGE VARCHAR2(255),
    POSITION NUMBER(19),
    IS_WARNING NUMBER(1),
    CONTINUE_ON_FAIL NUMBER(1),
    IS_EXEMPTABLE NUMBER(1)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR_ATTR
-----------------------------------------------------------------------------

CREATE TABLE KSEN_INSTR_ATTR
(
    ID VARCHAR2(255),
    ATTR_NAME VARCHAR2(255),
    ATTR_VALUE VARCHAR2(2000),
    OWNER VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR_TYPE
-----------------------------------------------------------------------------

CREATE TABLE KSEN_INSTR_TYPE
(
    TYPE_KEY VARCHAR2(255),
	OBJ_ID VARCHAR2(36),
	VER_NBR NUMBER(19,0),
	TYPE_DESC VARCHAR2(2000),
	EFF_DT TIMESTAMP (6),
	EXPIR_DT TIMESTAMP (6),
	NAME VARCHAR2(255),
	REF_OBJECT_URI VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR_TYPE_ATTR
-----------------------------------------------------------------------------

CREATE TABLE KSEN_INSTR_TYPE_ATTR
(
    ID VARCHAR2(255),
    ATTR_NAME VARCHAR2(255),
    ATTR_VALUE VARCHAR2(2000),
    OWNER VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR_POPLTN_RELTN
-----------------------------------------------------------------------------

CREATE TABLE KSEN_INSTR_POPLTN_RELTN
(
	INSTR_ID VARCHAR2(255),
    POPLTN_ID VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR_ATPTYPE_RELTN
-----------------------------------------------------------------------------

CREATE TABLE KSEN_INSTR_ATPTYPE_RELTN
(
	INSTR_ID VARCHAR2(255),
	ATP_TYPE_ID VARCHAR2(255)
)
/


-----------------------------------------------------------------------------
-- KSEN_INSTR_MESSAGE
-----------------------------------------------------------------------------

CREATE TABLE KSEN_INSTR_MESSAGE
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    FORMATTED VARCHAR2(2000),
    PLAIN VARCHAR2(2000)
)
/


-----------------------------------------------------------------------------
-- KSEN_POPULATION
-----------------------------------------------------------------------------

CREATE TABLE KSEN_POPULATION
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    CREATEID VARCHAR2(255),
    CREATETIME TIMESTAMP (6),
    UPDATEID VARCHAR2(255),
    UPDATETIME TIMESTAMP (6),
    STATE_ID VARCHAR2(255),
    TYPE_ID VARCHAR2(255)
)
/


--------------------------------------------------------
--  Constraints for Table KSEN_PROCESS
--------------------------------------------------------

ALTER TABLE "KSEN_PROCESS" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS" MODIFY ("STATE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS" MODIFY ("TYPE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_PROCESS_RICH_TEXT
--------------------------------------------------------

ALTER TABLE "KSEN_PROCESS_RICH_TEXT" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS_RICH_TEXT" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_PROCESS_TYPE
--------------------------------------------------------

  ALTER TABLE "KSEN_PROCESS_TYPE" MODIFY ("TYPE_KEY" NOT NULL ENABLE)
/

  ALTER TABLE "KSEN_PROCESS_TYPE" ADD PRIMARY KEY ("TYPE_KEY") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_PROCESS_ATTR
--------------------------------------------------------

ALTER TABLE "KSEN_PROCESS_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_PROCESS_TYPE_ATTR
--------------------------------------------------------

ALTER TABLE "KSEN_PROCESS_TYPE_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS_TYPE_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_CHECK
--------------------------------------------------------

ALTER TABLE "KSEN_CHECK" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_CHECK" MODIFY ("STATE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_CHECK" MODIFY ("TYPE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_CHECK" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_CHECK_RICH_TEXT
--------------------------------------------------------

ALTER TABLE "KSEN_CHECK_RICH_TEXT" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_CHECK_RICH_TEXT" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_CHECK_TYPE
--------------------------------------------------------

  ALTER TABLE "KSEN_CHECK_TYPE" MODIFY ("TYPE_KEY" NOT NULL ENABLE)
/

  ALTER TABLE "KSEN_CHECK_TYPE" ADD PRIMARY KEY ("TYPE_KEY") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_CHECK_TYPE_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_CHECK_TYPE_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/

  ALTER TABLE "KSEN_CHECK_TYPE_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_CHECK_ATTR
--------------------------------------------------------

ALTER TABLE "KSEN_CHECK_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_CHECK_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_INSTR
--------------------------------------------------------

ALTER TABLE "KSEN_INSTR" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR" MODIFY ("STATE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR" MODIFY ("TYPE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_INSTR_ATTR
--------------------------------------------------------

ALTER TABLE "KSEN_INSTR_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_INSTR_TYPE
--------------------------------------------------------

ALTER TABLE "KSEN_INSTR_TYPE" MODIFY ("TYPE_KEY" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR_TYPE" ADD PRIMARY KEY ("TYPE_KEY") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_INSTR_TYPE_ATTR
--------------------------------------------------------

ALTER TABLE "KSEN_INSTR_TYPE_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR_TYPE_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_INSTR_ATPTYPE_RELTN
--------------------------------------------------------

ALTER TABLE "KSEN_INSTR_ATPTYPE_RELTN" MODIFY ("INSTR_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR_ATPTYPE_RELTN" MODIFY ("ATP_TYPE_ID" NOT NULL ENABLE)
/

--------------------------------------------------------
--  Constraints for Table KSEN_INSTR_MESSAGE
--------------------------------------------------------

ALTER TABLE "KSEN_INSTR_MESSAGE" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR_MESSAGE" ADD PRIMARY KEY ("ID") ENABLE
/


-- KSEN_CHECK_TYPE data
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.hold',1,'A Check to the Hold Service.','Hold Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.milestone.startdate',1,'A Check to the ATP Service for the start of a Milestone.','Start Date Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.milestone.deadline',1,'A Check to the ATP Service for the end of a Milestone.','Deadline Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.milestone.period',1,'A Check to the ATP Service for the date range of a Milestone.','Time Period Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.process',1,'A Check that depends on another Process in the Process Service.','Process Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.rule.direct',1,'A Check based on a known or nameable rule (Agenda).','Direct Rule Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.rule.indirect',1,'A Check to some logic (rule) to determine another rule to eveluate.','Indirect Rule Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.value.equals',1,'A Check to determine if a value is equal to the value specified in the Check.','Equal Value Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.value.max',1,'A Check to determine if a value is equal to or less than the value specified in the Check.','Maximum Value Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.value.min',1,'A Check to determine if a value is equal to or greater than the value specified in the Check.','Minimum Value Check')
/
INSERT INTO KSEN_CHECK_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.check.type.acknowledgement',1,'A check based on an acknowledgement','Acknowledgement Check')
/

--KSEN_COMM_STATE data for process entities
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.check.state.enabled', 'Enabled', 'kuali.process.check.lifecycle', 'Indicates that this Check is active and should be checked across all Processes.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.check.state.disabled', 'Disabled', 'kuali.process.check.lifecycle', 'Indicates that this Check is disabled across all Processes and should be skipped with a success.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.check.state.inactive', 'Inactive', 'kuali.process.check.lifecycle', 'Indicates that this Check is inactive (out to pasture) across all Processes and should fail.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.instruction.state.enabled', 'Enabled', 'kuali.process.instruction.lifecycle', 'Indicates that this Instruction is active and enabled.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.instruction.state.disabled', 'Disabled', 'kuali.process.instruction.lifecycle', 'Indicates that this Instruction is disabled and should be skipped.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.process.state.enabled', 'Enabled', 'kuali.process.process.lifecycle', 'Indicates that this Process is enabled.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.process.state.disabled', 'Disabled', 'kuali.process.process.lifecycle', 'Indicates that this Process is disabled and should be skipped resulting in success.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.process.state.inactive', 'Inactive', 'kuali.process.process.lifecycle', 'Indicates that this Process is inactive because it was put out to pasture. Any checks for this process should fail.', 0)
/

-- KSEN_INSTR_TYPE data
INSERT INTO KSEN_INSTR_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.instruction.type',1,'An Instruction.','Instruction')
/

-- KSEN_PROCESS_TYPE data
INSERT INTO KSEN_PROCESS_TYPE (TYPE_KEY,VER_NBR,TYPE_DESC,NAME) VALUES ('kuali.process.process.type',1,'A Process.','Process')
/
