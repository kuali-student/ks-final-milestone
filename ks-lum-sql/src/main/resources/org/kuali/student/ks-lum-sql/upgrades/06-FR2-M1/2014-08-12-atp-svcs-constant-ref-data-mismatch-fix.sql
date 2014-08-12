-- Blocker kscm-2572 Ref-data is inconsistent with the values of ATP service constants (which matches the source of authority - type/state pages)
update KSLU_CLU set CLU_INTSTY_TYPE ='kuali.atp.duration.Week' where CLU_INTSTY_TYPE = 'kuali.atp.duration.week'
/
update KSLU_CLU set CLU_INTSTY_TYPE ='kuali.atp.duration.Month' where CLU_INTSTY_TYPE = 'kuali.atp.duration.month';
/
