UPDATE  KSLU_CLU
SET     EXP_FIRST_ATP = (SELECT ID
                          FROM  KSEN_ATP
                          WHERE NAME = (SELECT  NAME
                                        FROM    KSAP_ATP
                                        WHERE   ID = KSLU_CLU.EXP_FIRST_ATP))
WHERE 	EXP_FIRST_ATP LIKE 'kuali%'
/