// RichText
INSERT INTO KSDO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RT-DESC-DOCUMENT-1', '<p>Document 1</p>', ' 1')
/
INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('CAT_1','CATEGORY 1','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'})
/
INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('CAT_2','CATEGORY 2','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'})
/
INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('CAT_3','CATEGORY 3','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'})
/


INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.type1', 'PDF Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'PDF')
/



