DECLARE
   var_offic_clu_id varchar2(36);
   var_expDate timestamp(6);
BEGIN

  select TIMESTAMP '2014-06-01 00:00:00.000' into var_expDate from dual;

  select ID into var_offic_clu_id from KSLU_CLU_IDENT where CD = 'WMST452';

  -- Retire a version of WMST452
  UPDATE KSLU_CLU_IDENT set ST = 'Retired', VER_NBR = VER_NBR+1 where id = var_offic_clu_id;

  UPDATE KSLU_CLU set ST = 'Retired', EXPIR_DT=var_expDate, LAST_ATP='kuali.atp.2014Spring', VER_NBR = VER_NBR+1
    WHERE OFFIC_CLU_ID = var_offic_clu_id;

END;
/

DECLARE
   var_offic_clu_id varchar2(36);
   var_expDate timestamp(6);
BEGIN

  select TIMESTAMP '2014-09-01 00:00:00.000' into var_expDate from dual;

  select ID into var_offic_clu_id from KSLU_CLU_IDENT where CD = 'WMST602' and ST = 'Active';

  -- Retire a version of WMST602
  UPDATE KSLU_CLU_IDENT set ST = 'Retired', VER_NBR = VER_NBR+1 where id = var_offic_clu_id;

  UPDATE KSLU_CLU set ST = 'Retired', EXPIR_DT=var_expDate, LAST_ATP='kuali.atp.2014Summer1', VER_NBR = VER_NBR+1
    WHERE OFFIC_CLU_ID = var_offic_clu_id;

END;
/