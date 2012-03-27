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

ALTER TABLE "KSEN_ATP" MODIFY ("END_DT" NOT NULL ENABLE)
/
ALTER TABLE "KSEN_ATP" MODIFY ("START_DT" NOT NULL ENABLE)
/

--------------------------------------------------------
--  Constraints for Table KSEN_MSTONE_ATTR
--------------------------------------------------------

ALTER TABLE "KSEN_MSTONE_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/
ALTER TABLE "KSEN_MSTONE_ATTR" ADD CONSTRAINT "KSEN_MSTONE_ATTR_P" PRIMARY KEY ("ID") ENABLE
/

-- Rename and drop constraints for KSEN_ATPATP_RELTN
ALTER TABLE ksen_atpatp_reltn DROP CONSTRAINT FK607450026252E155
/
ALTER TABLE ksen_atpatp_reltn DROP CONSTRAINT FK60745002E4C71119
/
ALTER TABLE ksen_atpatp_reltn RENAME CONSTRAINT FK607450028C6658A TO KSEN_ATPATP_RELTN_FK1
/
ALTER TABLE ksen_atpatp_reltn RENAME CONSTRAINT FK60745002C681D01E TO KSEN_ATPATP_RELTN_FK2
/

-- Rename and drop constraints for KSEN_ATPMSTONE_RELTN
ALTER TABLE ksen_atpmstone_reltn DROP CONSTRAINT FK5480BC9B6252E155
/
ALTER TABLE ksen_atpmstone_reltn DROP CONSTRAINT FK5480BC9B89043C68
/
ALTER TABLE ksen_atpmstone_reltn RENAME CONSTRAINT FK5480BC9B8C6658A TO KSEN_ATPMSTONE_RELTN_FK1
/
ALTER TABLE ksen_atpmstone_reltn RENAME CONSTRAINT FK5480BC9B3B007502 TO KSEN_ATPMSTONE_RELTN_FK2
/

-- Drop constraints for KSEN_ATP
ALTER TABLE ksen_atp DROP CONSTRAINT FKFD3154F6252E155
/
ALTER TABLE ksen_atp DROP CONSTRAINT FKFD3154F6C2F628C
/
ALTER TABLE ksen_atp DROP CONSTRAINT FKFD3154F804B2705
/

-- Drop constraints for KSEN_MSTONE
ALTER TABLE ksen_mstone DROP CONSTRAINT FK996BC8066C2F628C
/
ALTER TABLE ksen_mstone DROP CONSTRAINT FK996BC806C06A3FE2
/
ALTER TABLE ksen_mstone DROP CONSTRAINT FK996BC806FB742D8
/

-- Rename constraints for KSEN_ATPATP_RELTN_ATTR
ALTER TABLE ksen_atpatp_reltn_attr RENAME CONSTRAINT FK5480EC0E8934C5C5 TO KSEN_ATPATP_RELTN_ATTR_FK1
/
ALTER TABLE ksen_atpatp_reltn_attr RENAME CONSTRAINT FK5480EC0EF1998125 TO KSEN_ATPATP_RELTN_ATTR_FK2
/

-- Rename constraints for KSEN_ATP_ATTR
ALTER TABLE ksen_atp_attr RENAME CONSTRAINT FK3DFA6EE162FD4240 TO KSEN_ATP_ATTR_FK1
/
-- Rename constraints for KSEN_MSTONE_ATTR
ALTER TABLE ksen_mstone_attr RENAME CONSTRAINT FK3DFA6EE1BA0FC113 TO KSEN_MSTONE_ATTR_FK1
/
