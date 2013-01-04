--------------------------------------------------------
--  Constraints for Table KSEN_LRR_TYPE
--------------------------------------------------------
ALTER TABLE "KSEN_LRR_TYPE" MODIFY ("TYPE_KEY" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_LRR_TYPE" ADD PRIMARY KEY ("TYPE_KEY") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_LRR
--------------------------------------------------------
ALTER TABLE "KSEN_LRR" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_LRR" ADD PRIMARY KEY ("ID") ENABLE
/

ALTER TABLE "KSEN_LRR" ADD CONSTRAINT "FK1BE15Q7B1D2EF131" FOREIGN KEY ("TYPE_ID")
	  REFERENCES "KSEN_LRR_TYPE" ("TYPE_KEY") ENABLE
/

ALTER TABLE "KSEN_LRR"	ADD CONSTRAINT "FK1BE15Q7B1D2EA121" FOREIGN KEY (RT_DESCR_ID)
    REFERENCES KSEN_RICH_TEXT_T(ID)
/

ALTER TABLE "KSEN_LRR" ADD CONSTRAINT "FK1BE2597A61E975Q1" FOREIGN KEY ("STATE_ID")
	  REFERENCES "KSEN_COMM_STATE" ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_LRR_ATTR
--------------------------------------------------------
ALTER TABLE "KSEN_LRR_ATTR" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_LRR_ATTR" ADD PRIMARY KEY ("ID") ENABLE
/

ALTER TABLE "KSEN_LRR_ATTR" ADD CONSTRAINT "FKSF4BE635DC3CD510" FOREIGN KEY ("OWNER")
	  REFERENCES "KSEN_LRR" ("ID") ENABLE
/

--------------------------------------------------------
--  Constraints for Table KSEN_LRR_RES_SOURCE
--------------------------------------------------------
ALTER TABLE "KSEN_LRR_RES_SOURCE" MODIFY ("ID" NOT NULL ENABLE)
/

ALTER TABLE "KSEN_LRR_RES_SOURCE" ADD PRIMARY KEY ("ID") ENABLE
/