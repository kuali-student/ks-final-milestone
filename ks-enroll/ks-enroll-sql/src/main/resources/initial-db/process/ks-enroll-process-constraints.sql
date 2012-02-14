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
--  Constraints for Table KSEN_INSTR_PRSN_RELTN
-- TODO this table is not yet defined, should these constraints be here?
--------------------------------------------------------

--ALTER TABLE "KSEN_INSTR_PRSN_RELTN" MODIFY ("INSTR_ID" NOT NULL ENABLE)
--/

--ALTER TABLE "KSEN_INSTR_PRSN_RELTN" MODIFY ("PERSON_ID" NOT NULL ENABLE)
--/

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