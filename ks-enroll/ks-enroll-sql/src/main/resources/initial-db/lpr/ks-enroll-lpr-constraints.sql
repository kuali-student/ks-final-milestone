--------------------------------------------------------
--  Constraints for Table KSLP_LPR
--------------------------------------------------------

  ALTER TABLE "KSLP_LPR" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSLP_LPR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSLP_LPR_STATE
--------------------------------------------------------

  ALTER TABLE "KSLP_LPR_STATE" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSLP_LPR_STATE" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSLP_LPR_TYPE
--------------------------------------------------------

  ALTER TABLE "KSLP_LPR_TYPE" MODIFY ("TYPE_KEY" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSLP_LPR_TYPE" ADD PRIMARY KEY ("TYPE_KEY") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSLP_LPR_ATTR
--------------------------------------------------------

  ALTER TABLE "KSLP_LPR_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/
 
  ALTER TABLE "KSLP_LPR_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSLP_LPR
--------------------------------------------------------

  ALTER TABLE "KSLP_LPR" ADD CONSTRAINT "FK1BE1597B1D2EFA44" FOREIGN KEY ("RELATION_TYPE_ID")
	  REFERENCES "KSLP_LPR_TYPE" ("TYPE_KEY") ENABLE
/
 
  ALTER TABLE "KSLP_LPR" ADD CONSTRAINT "FK1BE1597B61E975F6" FOREIGN KEY ("RELATION_STATE_ID")
	  REFERENCES "KSLP_LPR_STATE" ("ID") ENABLE
/

--------------------------------------------------------
--  Ref Constraints for Table KSLP_LPR_ATTR
--------------------------------------------------------

  ALTER TABLE "KSLP_LPR_ATTR" ADD CONSTRAINT "FKDF4BE635A8427FA" FOREIGN KEY ("OWNER")
	  REFERENCES "KSLP_LPR_TYPE" ("TYPE_KEY") ENABLE
/
 
  ALTER TABLE "KSLP_LPR_ATTR" ADD CONSTRAINT "FKDF4BE635DC3CD520" FOREIGN KEY ("OWNER")
	  REFERENCES "KSLP_LPR" ("ID") ENABLE
/
 
  ALTER TABLE "KSLP_LPR_ATTR" ADD CONSTRAINT "FKDF4BE635EA869E3D" FOREIGN KEY ("OWNER")
	  REFERENCES "KSLP_LPR_STATE" ("ID") ENABLE
/

