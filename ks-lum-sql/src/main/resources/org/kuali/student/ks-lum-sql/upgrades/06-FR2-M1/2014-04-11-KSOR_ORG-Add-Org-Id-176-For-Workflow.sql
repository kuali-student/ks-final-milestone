-- KSCM-1545: This is to add the missing org id 176 currently hardcoded and used in the workflow
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
values ('176', 'GREENTEAM', TO_DATE( '20140411000000', 'YYYYMMDDHH24MISS' ), 'GREENTEAM', TO_DATE( '20140411000000', 'YYYYMMDDHH24MISS' ), 1, '', 'Publication Office', TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), null, 'Publication Office', 'PublicationOffice', 'Active', 'kuali.org.Office')
/