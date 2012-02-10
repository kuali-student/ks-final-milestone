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
-- KSEM_ENUM_T
-----------------------------------------------------------------------------


ALTER TABLE ksem_enum_t ADD (descr_plain VARCHAR2(4000) NULL, descr_formatted VARCHAR2(4000) NULL)
/
UPDATE ksem_enum_t SET descr_plain=descr
/
UPDATE ksem_enum_t SET descr_formatted=descr
/
ALTER TABLE ksem_enum_t DROP COLUMN descr
/
ALTER TABLE ksem_enum_t modify (descr_plain NOT NULL)
/
ALTER TABLE ksem_enum_t DROP COLUMN eff_dt
/
ALTER TABLE ksem_enum_t DROP COLUMN expir_dt
/

-----------------------------------------------------------------------------
-- KSEM_ENUM_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEM_ENUM_ATTR_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEM_ENUM_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEM_ENUM_ATTR_T 
   (	
    ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(4000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
   )
/

CREATE UNIQUE INDEX KSEM_ENUM_ATTR_P ON KSEM_ENUM_ATTR_T
(ID   ASC)
/

CREATE  INDEX KSEM_ENUM_ATTR_IF1 ON KSEM_ENUM_ATTR_T
(OWNER_ID   ASC)
/

-----------------------------------------------------------------------------
-- KSEM_ENUM_VAL_T
-----------------------------------------------------------------------------

ALTER TABLE ksem_enum_val_t ADD (new_sort_key VARCHAR2(255) NULL)
/
UPDATE ksem_enum_val_t SET new_sort_key=TO_CHAR(sort_key)
/
ALTER TABLE ksem_enum_val_t DROP COLUMN sort_key
/
ALTER TABLE ksem_enum_val_t RENAME COLUMN new_sort_key TO sort_key
/




