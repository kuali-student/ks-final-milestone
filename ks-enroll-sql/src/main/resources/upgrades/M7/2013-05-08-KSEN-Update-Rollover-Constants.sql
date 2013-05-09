--Update KSEN_SOC_ROR_ITEM table to reflect change in rollover constants
UPDATE KSEN_SOC_ROR_ITEM
  SET SOC_ROR_TYPE = 'kuali.soc.rollover.result.item.type.create'
    WHERE SOC_ROR_TYPE = 'kuali.soc.rollover.result.item.create'
/

UPDATE KSEN_SOC_ROR
  SET SOC_ROR_TYPE = 'kuali.soc.rollover.results.type.rollover'
    WHERE SOC_ROR_TYPE = 'kuali.soc.rollover.result.rollover'
/