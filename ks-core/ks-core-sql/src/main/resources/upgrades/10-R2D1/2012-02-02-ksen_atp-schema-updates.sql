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

-- KSEN_ATP updates
ALTER TABLE ksen_atp RENAME COLUMN atp_state_id TO atp_state
/
ALTER TABLE ksen_atp RENAME COLUMN atp_type_id TO atp_type
/

-- move data from rich text table to new columns
ALTER TABLE ksen_atp ADD (descr_plain VARCHAR2(4000) NULL, descr_formatted VARCHAR2(4000) NULL, ATP_CD VARCHAR2(255) NULL)
/
UPDATE ksen_atp SET descr_plain=(SELECT plain FROM ksen_rich_text_t where ksen_rich_text_t.id=ksen_atp.rt_descr_id)
/
UPDATE ksen_atp SET descr_formatted=(SELECT formatted FROM ksen_rich_text_t where ksen_rich_text_t.id=ksen_atp.rt_descr_id)
/
ALTER TABLE ksen_atp DROP COLUMN rt_descr_id
/
ALTER TABLE ksen_atp modify (descr_plain NOT NULL)
/

CREATE  INDEX KSEN_ATP_I1 ON KSEN_ATP
(ATP_TYPE   ASC)
/
CREATE  INDEX KSEN_ATP_I2 ON KSEN_ATP
(ATP_CD   ASC)
/

CREATE  INDEX KSEN_ATP_I3 ON KSEN_ATP
(START_DT   ASC)
/

-- KSEN_ATPATP_RELTN updates
ALTER TABLE ksen_atpatp_reltn RENAME COLUMN atp_state_id TO atp_state
/
ALTER TABLE ksen_atpatp_reltn MODIFY atp_state not null
/

ALTER TABLE ksen_atpatp_reltn RENAME COLUMN atp_reltn_type_id TO atp_type
/
ALTER TABLE ksen_atpatp_reltn MODIFY atp_type not null
/

ALTER TABLE ksen_atpatp_reltn MODIFY atp_id not null
/
ALTER TABLE ksen_atpatp_reltn MODIFY related_atp_id not null
/

CREATE  INDEX KSEN_ATPATP_RELTN_I1 ON KSEN_ATPATP_RELTN
(ATP_TYPE   ASC)
/

CREATE  INDEX KSEN_ATPATP_RELTN_IF1 ON KSEN_ATPATP_RELTN
(ATP_ID   ASC)
/

CREATE  INDEX KSEN_ATPATP_RELTN_IF2 ON KSEN_ATPATP_RELTN
(RELATED_ATP_ID   ASC)
/

-- KSEN_ATPATP_RELTN_ATTR updates
ALTER TABLE ksen_atpatp_reltn_attr MODIFY attr_value VARCHAR2(4000)
/
CREATE  INDEX KSEN_ATPATP_RELTN_ATTR_IF1 ON KSEN_ATPATP_RELTN_ATTR
(OWNER   ASC)
/


-- KSEN_ATPMSTONE_RELTN updates
ALTER TABLE ksen_atpmstone_reltn DROP COLUMN eff_dt
/
ALTER TABLE ksen_atpmstone_reltn DROP COLUMN expir_dt
/
ALTER TABLE ksen_atpmstone_reltn DROP COLUMN atp_state_id
/
ALTER TABLE ksen_atpmstone_reltn DROP COLUMN am_reltn_type_id
/
CREATE  INDEX KSEN_ATPMSTONE_RELTN_IF1 ON KSEN_ATPMSTONE_RELTN
(ATP_ID   ASC)
/
CREATE  INDEX KSEN_ATPMSTONE_RELTN_IF2 ON KSEN_ATPMSTONE_RELTN
(MSTONE_ID   ASC)
/


-- KSEN_ATP_ATTR updates
ALTER TABLE ksen_atp_attr MODIFY attr_value VARCHAR2(4000)
/
CREATE  INDEX KSEN_ATP_ATTR_IF1 ON KSEN_ATP_ATTR
(OWNER   ASC)
/

-- KSEN_MSTONE updates
ALTER TABLE ksen_mstone RENAME COLUMN milestone_type_id TO mstone_type
/
ALTER TABLE ksen_mstone MODIFY mstone_type NOT NULL
/
ALTER TABLE ksen_mstone RENAME COLUMN milestone_state_id TO mstone_state
/
ALTER TABLE ksen_mstone MODIFY mstone_state NOT NULL
/
ALTER TABLE ksen_mstone RENAME COLUMN relative_milestone_id TO relative_anchor_mstone_id
/
ALTER TABLE ksen_mstone MODIFY is_all_day NOT NULL
/
ALTER TABLE ksen_mstone MODIFY is_date_range NOT NULL
/
ALTER TABLE ksen_mstone MODIFY  is_relative NOT NULL
/

-- move description data to new columns
ALTER TABLE ksen_mstone ADD (descr_plain VARCHAR2(4000) NULL, descr_formatted VARCHAR2(4000) NULL, is_instrct_day NUMBER(1) DEFAULT 0 NOT NULL)
/
UPDATE ksen_mstone SET descr_plain=(SELECT plain FROM ksen_rich_text_t where ksen_rich_text_t.id=ksen_mstone.rt_descr_id)
/
UPDATE ksen_mstone SET descr_formatted=(SELECT formatted FROM ksen_rich_text_t where ksen_rich_text_t.id=ksen_mstone.rt_descr_id)
/
ALTER TABLE ksen_mstone DROP COLUMN rt_descr_id
/
ALTER TABLE ksen_mstone modify (descr_plain NOT NULL)
/

CREATE  INDEX KSEN_MSTONE_I1 ON KSEN_MSTONE
(MSTONE_TYPE   ASC)
/
CREATE  INDEX KSEN_MSTONE_I2 ON KSEN_MSTONE
(START_DT   ASC)
/



-- KSEN_MSTONE_ATTR updates
ALTER TABLE ksen_mstone_attr MODIFY attr_value VARCHAR2(4000)
/
CREATE  INDEX KSEN_MSTONE_ATTR_IF1 ON KSEN_MSTONE_ATTR
(OWNER   ASC)
/
