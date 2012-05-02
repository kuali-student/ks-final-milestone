--------------------------------------------------------
--  Constraints for Table KSEN_ATP
--------------------------------------------------------

  ALTER TABLE "KSEN_ATP" ADD CONSTRAINT "KSEN_ATP_P" PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_ATPATP_RELTN
--------------------------------------------------------

  ALTER TABLE "KSEN_ATPATP_RELTN"
      ADD CONSTRAINT  "KSEN_ATPATP_RELTN_P" PRIMARY KEY ("ID")
/

--------------------------------------------------------
--  Constraints for Table KSEN_ATPMSTONE_RELTN
--------------------------------------------------------

  ALTER TABLE "KSEN_ATPMSTONE_RELTN" ADD CONSTRAINT  "KSEN_ATPMSTONE_RELTN_P" PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_ATP_TYPE
--------------------------------------------------------

  ALTER TABLE "KSEN_ATP_TYPE" MODIFY ("TYPE_KEY" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSEN_ATP_TYPE" ADD PRIMARY KEY ("TYPE_KEY") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_ATP_STATE
--------------------------------------------------------

  ALTER TABLE "KSEN_ATP_STATE" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSEN_ATP_STATE" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_COMM_STATE
--------------------------------------------------------

  ALTER TABLE "KSEN_COMM_STATE" MODIFY ("ID" NOT NULL ENABLE)
/

  ALTER TABLE "KSEN_COMM_STATE" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_MSTONE
--------------------------------------------------------

  ALTER TABLE "KSEN_MSTONE" ADD CONSTRAINT "KSEN_MSTONE_P" PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_MSTONE_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_MSTONE_ATTR" ADD CONSTRAINT "KSEN_MSTONE_ATTR_P" PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_RICH_TEXT_T
--------------------------------------------------------

  ALTER TABLE "KSEN_RICH_TEXT_T" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSEN_RICH_TEXT_T" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_STATEPROCESS_RELTN
--------------------------------------------------------

  ALTER TABLE "KSEN_STATEPROCESS_RELTN" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSEN_STATEPROCESS_RELTN" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_STATE_PROCESS
--------------------------------------------------------

  ALTER TABLE "KSEN_STATE_PROCESS" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSEN_STATE_PROCESS" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_ATPMSTONE_RELTN_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_ATPMSTONE_RELTN_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSEN_ATPMSTONE_RELTN_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/


--------------------------------------------------------
--  Constraints for Table KSEN_ATPATP_RELTN_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_ATPATP_RELTN_ATTR" ADD CONSTRAINT "KSEN_ATPATP_RELTN_ATTR_P" PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_ATP_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_ATP_ATTR" ADD CONSTRAINT "KSEN_ATP_ATTR_P" PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSEN_ATPATP_RELTN
--------------------------------------------------------

  ALTER TABLE "KSEN_ATPATP_RELTN" ADD CONSTRAINT "KSEN_ATPATP_RELTN_FK1" FOREIGN KEY ("ATP_ID")
	  REFERENCES "KSEN_ATP" ("ID") ENABLE
/
 
  ALTER TABLE "KSEN_ATPATP_RELTN" ADD CONSTRAINT "KSEN_ATPATP_RELTN_FK2" FOREIGN KEY ("RELATED_ATP_ID")
	  REFERENCES "KSEN_ATP" ("ID") ENABLE
/
 
--------------------------------------------------------
--  Ref Constraints for Table KSEN_ATPMSTONE_RELTN
--------------------------------------------------------

  ALTER TABLE "KSEN_ATPMSTONE_RELTN" ADD CONSTRAINT "KSEN_ATPMSTONE_RELTN_FK1" FOREIGN KEY ("ATP_ID")
	  REFERENCES "KSEN_ATP" ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSEN_STATEPROCESS_RELTN
--------------------------------------------------------

  ALTER TABLE "KSEN_STATEPROCESS_RELTN" ADD CONSTRAINT "FKCDFDC0E024D2ACA6" FOREIGN KEY ("PRIOR_STATEKEY")
	  REFERENCES "KSEN_COMM_STATE" ("ID") ENABLE
/
 
  ALTER TABLE "KSEN_STATEPROCESS_RELTN" ADD CONSTRAINT "FKCDFDC0E09F94F29D" FOREIGN KEY ("NEXT_STATEKEY")
	  REFERENCES "KSEN_COMM_STATE" ("ID") ENABLE
/
 
  ALTER TABLE "KSEN_STATEPROCESS_RELTN" ADD CONSTRAINT "FKCDFDC0E0D6162101" FOREIGN KEY ("PROCESS_KEY")
	  REFERENCES "KSEN_STATE_PROCESS" ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSEN_ATPATP_RELTN_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_ATPATP_RELTN_ATTR" ADD CONSTRAINT "KSEN_ATPATP_RELTN_ATTR_FK1" FOREIGN KEY ("OWNER")
	  REFERENCES "KSEN_ATPATP_RELTN" ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSEN_ATPMSTONE_RELTN_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_ATPMSTONE_RELTN_ATTR" ADD CONSTRAINT "FK586D7715FC300ED8" FOREIGN KEY ("OWNER")
	  REFERENCES "KSEN_ATPMSTONE_RELTN" ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSEN_ATP_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_ATP_ATTR" ADD CONSTRAINT "KSEN_ATP_ATTR_FK1" FOREIGN KEY ("OWNER")
	  REFERENCES "KSEN_ATP" ("ID") ENABLE
/
--------------------------------------------------------
--  Ref Constraints for Table KSEN_MSTONE_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_MSTONE_ATTR" ADD CONSTRAINT "FK3DFA6EE1BA0FC113" FOREIGN KEY ("OWNER")
	  REFERENCES "KSEN_MSTONE" ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSEN_ATPTYPE_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_ATPTYPE_ATTR" ADD CONSTRAINT "FK3DFA6EE13309051A" FOREIGN KEY ("OWNER")
	  REFERENCES "KSEN_ATP_TYPE" ("TYPE_KEY") ENABLE
/
