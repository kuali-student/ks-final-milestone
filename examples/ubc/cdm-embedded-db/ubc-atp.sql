SET DEFINE OFF;
Insert into KSAP_RICH_TEXT_T
   (ID, PLAIN)
 Values
   ('kuali.atp.desc.WI2011-2012', 'Winter Semester of 2012');

Insert into KSAP_ATP
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, STATE, RT_DESCR_ID, TYPE)
 Values
   ('kuali.atp.WI2011-2012', 'SYSTEM', TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'SYSTEM', TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 0, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('1/1/2100 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Winter 2012', 'Actual', 'kuali.atp.desc.WI2011-2012', 'kuali.atp.type.Winter');

Insert into KSAP_RICH_TEXT_T
   (ID, PLAIN)
 Values
   ('kuali.atp.desc.SU2011-2012', 'Summer Semester of 2012');

Insert into KSAP_ATP
   (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, STATE, RT_DESCR_ID, TYPE)
 Values
   ('kuali.atp.SU2011-2012', 'SYSTEM', TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'SYSTEM', TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 0, TO_TIMESTAMP('1/1/2000 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), TO_TIMESTAMP('1/1/2100 12:00:00.000000 AM','fmMMfm/fmDDfm/YYYY fmHH12fm:MI:SS.FF AM'), 'Summer 2012', 'Tentative', 'kuali.atp.desc.SU2011-2012', 'kuali.atp.type.Summer');

COMMIT;
