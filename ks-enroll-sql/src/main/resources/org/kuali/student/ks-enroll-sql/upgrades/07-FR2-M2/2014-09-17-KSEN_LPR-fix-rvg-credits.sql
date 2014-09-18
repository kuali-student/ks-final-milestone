UPDATE KSEN_LPR_TRANS_ITEM_RVG
   SET RESULT_VAL_GRP_ID = 'kuali.creditType.credit.degree.'||SUBSTR(RESULT_VAL_GRP_ID,LENGTH
    ('kuali.result.value.credit.degree.')+1)
WHERE
    RESULT_VAL_GRP_ID LIKE 'kuali.result.value.credit.degree.%'
/

UPDATE KSEN_LPR_RESULT_VAL_GRP
   SET RESULT_VAL_GRP_ID = 'kuali.creditType.credit.degree.'||SUBSTR(RESULT_VAL_GRP_ID,LENGTH
    ('kuali.result.value.credit.degree.')+1)
WHERE
    RESULT_VAL_GRP_ID LIKE 'kuali.result.value.credit.degree.%'
/
