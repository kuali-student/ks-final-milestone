// RichText
INSERT INTO KS_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RT-DESC-LCR-1', '<p>Credit</p>', 'Credit')
/

// Credit Type
INSERT INTO KSLU_LCR_CREDIT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('lcrType.credit.1', 'A Basic Credit', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Credit')
/

// Credit
INSERT INTO KSLU_LCR_CREDIT (ID, NAME, RT_DESCR_ID, VALUE, EFF_DT, EXPIR_DT, TYPE, VERSIONIND) VALUES ('LRC-CREDIT-1', 'Credit 1', 'RT-DESC-LCR-1','LCR Value 1',  {ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'}, 'lcrType.credit.1', 0)
/
