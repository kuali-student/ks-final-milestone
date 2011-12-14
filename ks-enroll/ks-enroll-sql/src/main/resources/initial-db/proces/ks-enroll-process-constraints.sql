--------------------------------------------------------
--  Constraints for Table KSEN_PROCESS
--------------------------------------------------------

ALTER TABLE "KSEN_PROCESS" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS" MODIFY ("PROCESS_STATE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS" MODIFY ("PROCESS_TYPE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_PROCESS_ATTR
--------------------------------------------------------

ALTER TABLE "KSEN_PROCESS_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_PROCESS_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_CHECK
--------------------------------------------------------

ALTER TABLE "KSEN_CHECK" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_CHECK" MODIFY ("CHECK_STATE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_CHECK" MODIFY ("CHECK_TYPE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_CHECK" ADD PRIMARY KEY ("ID") ENABLE
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

ALTER TABLE "KSEN_INSTR" MODIFY ("INSTR_STATE_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR" MODIFY ("INSTR_TYPE_ID" NOT NULL ENABLE)
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
--  Constraints for Table KSEN_INSTR_PRSN_RELTN
--------------------------------------------------------

ALTER TABLE "KSEN_INSTR_PRSN_RELTN" MODIFY ("INSTR_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR_PRSN_RELTN" MODIFY ("PERSON_ID" NOT NULL ENABLE)
/

--------------------------------------------------------
--  Constraints for Table KSEN_INSTR_ATPTYPE_RELTN
--------------------------------------------------------

ALTER TABLE "KSEN_INSTR_ATPTYPE_RELTN" MODIFY ("INSTR_ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_INSTR_ATPTYPE_RELTN" MODIFY ("ATP_TYPE_ID" NOT NULL ENABLE)
/