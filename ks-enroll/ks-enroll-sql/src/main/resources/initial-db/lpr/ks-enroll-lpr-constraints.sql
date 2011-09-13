--------------------------------------------------------
--  Constraints for Table KSEN_LPR
--------------------------------------------------------

  ALTER TABLE "KSEN_LPR" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSEN_LPR" ADD PRIMARY KEY ("ID") ENABLE
/


--------------------------------------------------------
--  Constraints for Table KSEN_LPR_TYPE
--------------------------------------------------------

  ALTER TABLE "KSEN_LPR_TYPE" MODIFY ("TYPE_KEY" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSEN_LPR_TYPE" ADD PRIMARY KEY ("TYPE_KEY") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_LPR_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_LPR_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSEN_LPR_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSEN_LPR
--------------------------------------------------------

  ALTER TABLE "KSEN_LPR" ADD CONSTRAINT "FK1BE1597B1D2EFA44" FOREIGN KEY ("RELATION_TYPE_ID")
	  REFERENCES "KSEN_LPR_TYPE" ("TYPE_KEY") ENABLE
/
 
  ALTER TABLE "KSEN_LPR" ADD CONSTRAINT "FK1BE1597B61E975F6" FOREIGN KEY ("RELATION_STATE_ID")
	  REFERENCES "KSEN_COMM_STATE" ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSEN_LPR_ATTR
--------------------------------------------------------

  ALTER TABLE "KSEN_LPR_ATTR" ADD CONSTRAINT "FKDF4BE635A8427FA" FOREIGN KEY ("OWNER")
	  REFERENCES "KSEN_LPR_TYPE" ("TYPE_KEY") ENABLE
/
 
  ALTER TABLE "KSEN_LPR_ATTR" ADD CONSTRAINT "FKDF4BE635DC3CD520" FOREIGN KEY ("OWNER")
	  REFERENCES "KSEN_LPR" ("ID") ENABLE
/
 
    ALTER TABLE "KSEN_LPR_ATTR" ADD CONSTRAINT "FKDF4BE635EA869E3D" FOREIGN KEY ("OWNER")
	  REFERENCES "KSEN_COMM_STATE" ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_LPR_ROSTER
--------------------------------------------------------

  ALTER TABLE "KSEN_LPR_ROSTER" MODIFY ("ID" NOT NULL ENABLE)
/

  ALTER TABLE "KSEN_LPR_ROSTER" ADD PRIMARY KEY ("ID") ENABLE
/

  ALTER TABLE "KSEN_LPR_ROSTER"	ADD CONSTRAINT "KSEN_LPR_ROSTER_FK1" FOREIGN KEY (RT_DESCR_ID)
    REFERENCES KSEN_RICH_TEXT_T(ID)
/

--------------------------------------------------------
--  Constraints for Table KSEN_LPR_ROSTER_ENTRY
--------------------------------------------------------

  ALTER TABLE "KSEN_LPR_ROSTER_ENTRY" MODIFY ("ID" NOT NULL ENABLE)
/

  ALTER TABLE "KSEN_LPR_ROSTER_ENTRY" ADD PRIMARY KEY ("ID") ENABLE
/

  ALTER TABLE "KSEN_LPR_ROSTER_ENTRY" ADD CONSTRAINT "KSEN_LPR_ROSTER_ENTRY_FK1" FOREIGN KEY (LPRROSTER_ID)
    REFERENCES KSEN_LPR_ROSTER(ID)
/

  ALTER TABLE "KSEN_LPR_ROSTER_ENTRY" ADD CONSTRAINT "KSEN_LPR_ROSTER_ENTRY_FK2" FOREIGN KEY (LPR_ID)
    REFERENCES KSEN_LPR(ID)
/