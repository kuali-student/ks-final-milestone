--------------------------------------------------------
--  Constraints for Table KSEN_LUI
--------------------------------------------------------

ALTER TABLE KSEN_LUI
  ADD CONSTRAINT  KSEN_LUI_P PRIMARY KEY (ID)
/

--------------------------------------------------------
--  Constraints for Table KSEN_LUILUI_RELTN
--------------------------------------------------------

ALTER TABLE KSEN_LUILUI_RELTN
	ADD CONSTRAINT  KSEN_LUILUI_RELTNP_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUILUI_RELTN
	ADD (CONSTRAINT KSEN_LUILUI_RELTN_FK1 FOREIGN KEY (RELATED_LUI_ID) REFERENCES KSEN_LUI (ID))
/

ALTER TABLE KSEN_LUILUI_RELTN
	ADD (CONSTRAINT KSEN_LUILUI_RELTN_FK2 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/
	
--------------------------------------------------------
--  Constraints for Table KSEN_LUILUI_RELTN_ATTR
--------------------------------------------------------

ALTER TABLE KSEN_LUILUI_RELTN_ATTR
	ADD CONSTRAINT  KSEN_LUILUI_RELTN_ATTR_P PRIMARY KEY (ID)
/


ALTER TABLE KSEN_LUILUI_RELTN_ATTR
	ADD (CONSTRAINT KSEN_LUILUI_RELTN_ATTR_FK1 FOREIGN KEY (OWNER_ID) REFERENCES KSEN_LUILUI_RELTN (ID))
/

--------------------------------------------------------
--  Constraints for Table KSEN_LUI_IDENT_ATTR
--------------------------------------------------------

ALTER TABLE KSEN_LUI_IDENT_ATTR
	ADD CONSTRAINT  KSEN_LUI_IDENT_ATTR_P PRIMARY KEY (ID)
/

--------------------------------------------------------
--  Constraints for Table KSEN_LUI_ATTR
--------------------------------------------------------

ALTER TABLE KSEN_LUI_ATTR
	ADD CONSTRAINT  LSEN_LUI_ATTR_P PRIMARY KEY (ID)
/
	
ALTER TABLE KSEN_LUI_ATTR
	ADD (CONSTRAINT KSEN_LUI_ATTR_FK1 FOREIGN KEY (OWNER_ID) REFERENCES KSEN_LUI (ID))
/
 
--------------------------------------------------------
--  Constraints for Table KSEN_LUI_IDENT
--------------------------------------------------------

ALTER TABLE KSEN_LUI_IDENT
	ADD CONSTRAINT  KSEN_LUI_IDENT_P PRIMARY KEY (ID)
/

CREATE  INDEX KSEN_LUI_IDENT_IF1 ON KSEN_LUI_IDENT
(LUI_ID   ASC)
/

ALTER TABLE KSEN_LUI_IDENT
	ADD (CONSTRAINT KSEN_LUI_IDENT_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/


--------------------------------------------------------
--  Constraints for Table KSEN_LUI_LUCD
--------------------------------------------------------

ALTER TABLE KSEN_LUI_LU_CD
	ADD CONSTRAINT  KSEN_LUI_LU_CD_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_LU_CD
	ADD (CONSTRAINT KSEN_LUI_LU_CD_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/

--------------------------------------------------------
--  Constraints for Table KSEN_LUI_LUCD_ATTR 
--------------------------------------------------------

ALTER TABLE KSEN_LUI_LU_CD_ATTR
	ADD (CONSTRAINT KSEN_LUI__LU_CD_ATTR_FK1 FOREIGN KEY (OWNER_ID) REFERENCES KSEN_LUI_LU_CD (ID))
/
	  
ALTER TABLE KSEN_LUI_LU_CD_ATTR
	ADD CONSTRAINT  KSEN_LUI_LU_CD_ATTR_P PRIMARY KEY (ID)
/

--------------------------------------------------------
--  Ref Constraints for Table KSEN_LUI_IDENT_ATTR
--------------------------------------------------------

ALTER TABLE KSEN_LUI_IDENT_ATTR
	ADD (CONSTRAINT KSEN_LUI_IDENT_ATTR_FK1 FOREIGN KEY (OWNER_ID) REFERENCES KSEN_LUI_IDENT (ID))
/

--------------------------------------------------------
--  Constraints for Table KSEN_LUI_MTG_SCHE
--------------------------------------------------------
ALTER TABLE KSEN_LUI_MTG_SCHE
	ADD CONSTRAINT  KSEN_LUI_MTG_SCHE_P PRIMARY KEY (ID);
	
ALTER TABLE KSEN_LUI_MTG_SCHE
	ADD (CONSTRAINT KSEN_LUI_MTG_SCHE_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/

--------------------------------------------------------
--  KSEN_LUI_MTG_SCHE_ATTR
--------------------------------------------------------    

ALTER TABLE KSEN_LUI_MTG_SCHE_ATTR
	ADD CONSTRAINT  KSEN_LUI_MTG_SCHE_ATTR_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_MTG_SCHE_ATTR
	ADD (CONSTRAINT KSEN_LUI_MTR_SCHE_ATTR_FK1 FOREIGN KEY (OWNER_ID) REFERENCES KSEN_LUI_MTG_SCHE (ID))
/

--------------------------------------------------------
--  KSEN_LUI_EXPENDITURE
--------------------------------------------------------

ALTER TABLE KSEN_LUI_EXPENDITURE
	ADD CONSTRAINT  KSEN_LUI_EXPENDITURE_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_EXPENDITURE
	ADD (CONSTRAINT KSEN_LUI_EXPENDITURE_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/

--------------------------------------------------------
--  KSEN_LUI_EXPEND_ATTR
--------------------------------------------------------

ALTER TABLE KSEN_LUI_EXPEND_ATTR
	ADD CONSTRAINT  KSEN_EXPENDITURE_ATTR_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_EXPEND_ATTR
	ADD (CONSTRAINT KSEN_EXPENDITURE_ATTR_FK1 FOREIGN KEY (OWNER_ID) REFERENCES KSEN_LUI_EXPENDITURE (ID))
/

--------------------------------------------------------
--  KSEN_LUI_REVENUE
--------------------------------------------------------

ALTER TABLE KSEN_LUI_REVENUE
	ADD CONSTRAINT  KSEN_LUI_REVENUE_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_REVENUE
	ADD (CONSTRAINT KSEN_LUI_REVENUE_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/

--------------------------------------------------------
--  KSEN_LUI_REVENUE_ATTR
--------------------------------------------------------

ALTER TABLE KSEN_LUI_REVENUE_ATTR
	ADD CONSTRAINT  KSEN_LUI_REVENUE_ATTR_P PRIMARY KEY (ID)
/	

ALTER TABLE KSEN_LUI_REVENUE_ATTR
	ADD (CONSTRAINT KSEN_LUI_REVENUE_ATTR_FK1 FOREIGN KEY (OWNER_ID) REFERENCES KSEN_LUI_REVENUE (ID))
/
	
--------------------------------------------------------
--  Constraints for Table KSEN_LUI_AFFILIATED_ORG
--------------------------------------------------------

ALTER TABLE KSEN_LUI_AFFILIATED_ORG
	ADD CONSTRAINT  KSEN_LUI_AFFILIATED_ORG_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_AFFILIATED_ORG
	ADD (CONSTRAINT KSEN_LUI_AFFILIATED_ORG_FK1 FOREIGN KEY (EXPENDITURE_ID) REFERENCES KSEN_LUI_EXPENDITURE (ID))
/

ALTER TABLE KSEN_LUI_AFFILIATED_ORG
	ADD (CONSTRAINT KESN_LUI_AFFLIATED_ORG_FK2 FOREIGN KEY (REVENUE_ID) REFERENCES KSEN_LUI_REVENUE (ID))
/

--------------------------------------------------------
--  Constraints for Table KSEN_LUI_AFFIL_ORG_ATTR
--------------------------------------------------------
    
ALTER TABLE KSEN_LUI_AFFIL_ORG_ATTR
	ADD CONSTRAINT  KSEN_LUI_AFFIL_ORG_ATTR_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_AFFIL_ORG_ATTR
	ADD (CONSTRAINT KSEN_AFFIL_ORG_ATTR_FK1 FOREIGN KEY (OWNER_ID) REFERENCES KSEN_LUI_AFFILIATED_ORG (ID))
/

--------------------------------------------------------
--  KSEN_LUI_CAPACITY
--------------------------------------------------------

ALTER TABLE KSEN_LUI_CAPACITY
	ADD CONSTRAINT  KSEN_LUI_CAPACITY_P PRIMARY KEY (ID)
/

--------------------------------------------------------
--  KSEN_LUI_CLUCLU_RELTN
-------------------------------------------------------- 
    
ALTER TABLE KSEN_LUI_CLUCLU_RELTN
	ADD CONSTRAINT  KSEN_LUI_CLUCLU_RELTN_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_CLUCLU_RELTN
ADD CONSTRAINT  kSEN_LUI_CLUCLU_RELTN_I1 UNIQUE (LUI_ID,CLUCLU_RELTN_ID)
/

ALTER TABLE KSEN_LUI_CLUCLU_RELTN
	ADD (CONSTRAINT KSEN_LUI_CLUCLU_RELTN_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/

--------------------------------------------------------
--  KSEN_LUI_FEE
--------------------------------------------------------

ALTER TABLE KSEN_LUI_FEE
	ADD CONSTRAINT  KSEN_LUI_FEE_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_FEE
	ADD (CONSTRAINT KSEN_LUI_FEE_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/

--------------------------------------------------------
--  KSEN_LUI_FEE_ATTR
--------------------------------------------------------
    
ALTER TABLE KSEN_LUI_FEE_ATTR
	ADD CONSTRAINT  KSEN_LUI_FEE_ATTR_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_FEE_ATTR
	ADD (CONSTRAINT KSEN_LUI_FEE_ATTR_FK1 FOREIGN KEY (OWNER_ID) REFERENCES KSEN_LUI_FEE (ID))
/

--------------------------------------------------------
--  KSEN_LUI_CURRENCY_AMT
--------------------------------------------------------

ALTER TABLE KSEN_LUI_CURRENCY_AMT
	ADD CONSTRAINT  KSEN_LUI_CURRENCY_AMT_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_CURRENCY_AMT
	ADD (CONSTRAINT KSEN_LUI_CURRENCY_AMT_FK1 FOREIGN KEY (FEE_ID) REFERENCES KSEN_LUI_FEE (ID))
/

--------------------------------------------------------
--  KSEN_LUI_RESULT_VAL_GRP
--------------------------------------------------------

ALTER TABLE KSEN_LUI_RESULT_VAL_GRP
	ADD CONSTRAINT  KSEN_LUI_RESULT_VAL_GRP_P PRIMARY KEY (ID)
/	

ALTER TABLE KSEN_LUI_RESULT_VAL_GRP
ADD CONSTRAINT  KSEN_LUI_RESULT_VAL_GRP_I1 UNIQUE (LUI_ID,RESULT_VAL_GRP_ID)
/

ALTER TABLE KSEN_LUI_RESULT_VAL_GRP
	ADD (CONSTRAINT KSEN_LUI_RESULT_VAL_GRP_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/


--------------------------------------------------------
--  KSEN_LUI_UNITS_CONT_OWNER
--------------------------------------------------------

ALTER TABLE KSEN_LUI_UNITS_CONT_OWNER
	ADD CONSTRAINT  KSEN_LUI_UNITS_CONT_OWNER_P PRIMARY KEY (ID)
/	

ALTER TABLE KSEN_LUI_UNITS_CONT_OWNER
ADD CONSTRAINT  KSEN_LUI_UNITS_CONT_OWNER_I1 UNIQUE (LUI_ID,ORG_ID)
/

ALTER TABLE KSEN_LUI_UNITS_CONT_OWNER
	ADD (CONSTRAINT KSEN_LUI_UNITS_CONT_OWNER_F FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/
	
--------------------------------------------------------
--  KSEN_LUI_UNITS_DEPLOYMENT
--------------------------------------------------------

ALTER TABLE KSEN_LUI_UNITS_DEPLOYMENT
	ADD CONSTRAINT  KSEN_LUI_UNITS_DEPLOYMENT_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUI_UNITS_DEPLOYMENT
ADD CONSTRAINT  KSEN_LUI_UNITS_DEPLOYMENT_I1 UNIQUE (LUI_ID,ORG_ID)
/

ALTER TABLE KSEN_LUI_UNITS_DEPLOYMENT
	ADD (CONSTRAINT KSEN_LUI_UNITS_DEPLOYMENT_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/

--------------------------------------------------------
--  KSEN_LUICAPACITY_RELTN
--------------------------------------------------------

ALTER TABLE KSEN_LUICAPACITY_RELTN
	ADD CONSTRAINT  KSEN_REVENUE_P PRIMARY KEY (ID)
/

ALTER TABLE KSEN_LUICAPACITY_RELTN
	ADD (CONSTRAINT KSEN_LUICAPACITY_RELTN_FK1 FOREIGN KEY (LUI_ID) REFERENCES KSEN_LUI (ID))
/	

ALTER TABLE KSEN_LUICAPACITY_RELTN
	ADD (CONSTRAINT KSEN_LUICAPACITY_RELTN_FK2 FOREIGN KEY (LUI_CAPACITY_ID) REFERENCES KSEN_LUI_CAPACITY (ID))
/

/**/
