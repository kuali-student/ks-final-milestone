-- KSENROLL-11618 fix bad data in type descriptions
UPDATE
    KSEN_TYPE
SET
    DESCR_PLAIN=REPLACE(DESCR_PLAIN,'ý',''),
    DESCR_FORMATTED=REPLACE(DESCR_FORMATTED,'ý','')
WHERE
    DESCR_PLAIN LIKE '%ý%' OR DESCR_FORMATTED LIKE '%ý%'
/