UPDATE ksen_lrc_rvg
SET range_min_value = substr(id, 32)
WHERE rvg_type = 'kuali.result.values.group.type.fixed' AND range_min_value IS NULL
/

UPDATE ksen_lrc_rvg
SET range_max_value = substr(id, 32)
WHERE rvg_type = 'kuali.result.values.group.type.fixed' AND range_max_value IS NULL
/