--  INSERTING into KSEN_LUI
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, VER_NBR, ATP_ID, CREATETIME, CREATEID) VALUES ('Lui-1', 'Lui one',  'cluId1', 'kuali.lui.type.course.offering', 'kuali.lui.state.offered', 'Lui-1-Desc', 'Lui-1-Desc', to_timestamp('01-JAN-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), 0,'testTermId1', TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS'), 'admin')
/

INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID) VALUES ('Lui-2', 'Lui rwo',  'cluId2', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-2-Desc', 'Lui-2-Desc', to_timestamp('01-JAN-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') , 0, TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS'), 'admin')
/

INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, VER_NBR, ATP_ID, CREATETIME, CREATEID) VALUES ('Lui-3', 'Lui three', 'cluId3', 'kuali.lui.type.course.offering', 'kuali.lui.state.offered', 'Lui-3-Desc', 'Lui-3-Desc', to_timestamp('01-JAN-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') , 0,'testTermId1', TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS'), 'admin')
/

INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, VER_NBR, ATP_ID, CREATETIME, CREATEID) VALUES ('Lui-4', 'Lui four',  'cluId4', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-4-Desc', 'Lui-4-Desc', to_timestamp('01-JAN-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') , 0, '2009FALL', TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS'), 'admin')
/

INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID) VALUES ('Lui-fo-2', 'Lui format offering',  'cluId4',  'kuali.lui.type.format.offering', 'kuali.lui.state.draft', 'Lui-fo-Desc', 'Lui-fo-Desc',  to_timestamp('01-JAN-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),  to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') , 0, TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS'), 'admin')
/

INSERT INTO KSEN_LUILUI_RELTN (ID, NAME, RELATED_LUI_ID,LUI_ID, LUILUI_RELTN_TYPE, LUILUI_RELTN_STATE, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID) VALUES ('ao-fo-rel-1', 'ao format rel',  'Lui-4', 'Lui-fo-2', 'kuali.lui.lui.relation.associated', 'kuali.lui.state.draft', 'Lui-fo-Desc', 'Lui-fo-Desc', to_timestamp('01-JAN-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') , 0, TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS'), 'admin')
/

INSERT INTO KSEN_LUILUI_RELTN (ID, NAME, RELATED_LUI_ID,LUI_ID, LUILUI_RELTN_TYPE, LUILUI_RELTN_STATE, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID) VALUES ('co-fo-rel-1', 'fo co rel',  'Lui-fo-2', 'Lui-1', 'kuali.lui.lui.relation.associated', 'kuali.lui.state.draft', 'Lui-co-fo-rel-Desc', 'Lui-co-fo-rel-Desc', to_timestamp('01-JAN-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'), to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') , 0, TO_DATE('1-1-1970 00:00:00','MM-DD-YYYY HH24:Mi:SS'), 'admin')
/
