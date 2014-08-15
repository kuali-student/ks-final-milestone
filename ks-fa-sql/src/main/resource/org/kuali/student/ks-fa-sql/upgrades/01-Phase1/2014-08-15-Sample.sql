-- Delimiter is / on its own line.  No need for blank line at end of file

begin execute immediate 'drop table KSPL_LRNG_PI_RICH_TEXT CASCADE CONSTRAINTS PURGE'; exception when others then null; end;
/
CREATE TABLE KSFA_SAMPLE_TABLE
(
  ID VARCHAR2(255) NOT NULL
, OBJ_ID VARCHAR2(36)
, CONSTRAINT KSFA_SAMPLE_TABLE_P1 PRIMARY KEY(ID)
)
/

select sysdate from dual
/

select sysdate from dual
/