-- KSENROLL-15098: Unable to choose partial credit values (e.g. 5.5, 3.5) in student registration

INSERT INTO KSEN_LRC_RVG (CREATEID,CREATETIME,EFF_DT,ID,OBJ_ID,RANGE_MAX_VALUE,RANGE_MIN_VALUE,RESULT_SCALE_ID,
                          RVG_STATE,RVG_TYPE,VER_NBR)
  VALUES ('SQLSCRIPT',TO_DATE( '2014-10-02', 'YYYY-MM-DD' ),TO_DATE( '2014-10-02', 'YYYY-MM-DD' ),
          'kuali.creditType.credit.degree.3.5','04737487-0F7C-67FF-E050-007F010105B3','3.5','3.5',
          'kuali.result.scale.credit.degree','ACTIVE','kuali.result.values.group.type.fixed',0)
/
INSERT INTO KSEN_LRC_RVG (CREATEID,CREATETIME,EFF_DT,ID,OBJ_ID,RANGE_MAX_VALUE,RANGE_MIN_VALUE,RESULT_SCALE_ID,
                          RVG_STATE,RVG_TYPE,VER_NBR)
  VALUES ('SQLSCRIPT',TO_DATE( '2014-10-02', 'YYYY-MM-DD' ),TO_DATE( '2014-10-02', 'YYYY-MM-DD' ),
          'kuali.creditType.credit.degree.4.5','04737487-0F7D-67FF-E050-007F010105B3','4.5','4.5',
          'kuali.result.scale.credit.degree','ACTIVE','kuali.result.values.group.type.fixed',0)
/
INSERT INTO KSEN_LRC_RVG (CREATEID,CREATETIME,EFF_DT,ID,OBJ_ID,RANGE_MAX_VALUE,RANGE_MIN_VALUE,RESULT_SCALE_ID,
                          RVG_STATE,RVG_TYPE,VER_NBR)
  VALUES ('SQLSCRIPT',TO_DATE( '2014-10-02', 'YYYY-MM-DD' ),TO_DATE( '2014-10-02', 'YYYY-MM-DD' ),
          'kuali.creditType.credit.degree.5.5','04737487-0F7E-67FF-E050-007F010105B3','5.5','5.5',
          'kuali.result.scale.credit.degree','ACTIVE','kuali.result.values.group.type.fixed',0)
/

INSERT INTO KSEN_LRC_RVG_RESULT_VALUE (RESULT_VALUE_ID,RVG_ID)
  VALUES ('kuali.result.value.credit.degree.3.5','kuali.creditType.credit.degree.3.5')
/
INSERT INTO KSEN_LRC_RVG_RESULT_VALUE (RESULT_VALUE_ID,RVG_ID)
  VALUES ('kuali.result.value.credit.degree.4.5','kuali.creditType.credit.degree.4.5')
/
INSERT INTO KSEN_LRC_RVG_RESULT_VALUE (RESULT_VALUE_ID,RVG_ID)
  VALUES ('kuali.result.value.credit.degree.5.5','kuali.creditType.credit.degree.5.5')
/