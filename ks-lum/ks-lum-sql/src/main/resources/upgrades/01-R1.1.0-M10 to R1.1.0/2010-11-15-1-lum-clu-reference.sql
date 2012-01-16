update KSLU_CLU set st = 'Draft' where ID IN ('VAR-300', 'VAR-200','d4ea77dd-b492-4554-b104-863e42c5f8b7')
/
alter table KSLU_CLU_PUBL_VARI modify (VARI_VALUE VARCHAR2(1000))
/