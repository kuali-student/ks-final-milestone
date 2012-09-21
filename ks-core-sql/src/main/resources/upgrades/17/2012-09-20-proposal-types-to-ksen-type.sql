INSERT INTO KSEN_TYPE
SELECT type_key, obj_id, nvl(name,' '), type_desc, type_desc, eff_dt, expir_dt, 'http://student.kuali.org/wsdl/proposal/ProposalInfo',
    'http://student.kuali.org/wsdl/proposal/ProposalService', nvl(ver_nbr,0), to_date('2012/09/20','YYYY/MM/DD'), 'KSPRUPGRADE', null, null
FROM KSPR_PROPOSAL_TYPE
WHERE type_key NOT IN (SELECT TYPE_KEY FROM KSEN_TYPE)
/