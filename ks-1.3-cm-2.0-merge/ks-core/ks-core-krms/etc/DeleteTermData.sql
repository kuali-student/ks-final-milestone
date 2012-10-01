/*
Delete Statement
Source : MySQL documentation
*/


DELETE FROM krms_cntxt_vld_term_spec_t
	 WHERE term_spec_id in (select term_spec_id FROM krms_term_spec_t
	 WHERE nmspc_cd = 'KR-RULE-TEST') 

delete from krms_term_parm_t

DELETE FROM krms_term_t
	 WHERE term_spec_id in (select term_spec_id FROM krms_term_spec_t
	 WHERE nmspc_cd = 'KR-RULE-TEST') 

DELETE FROM krms_term_spec_t
	 WHERE nmspc_cd = 'KR-RULE-TEST'

-- select * from krms_term_rslvr_t
DELETE FROM krms_term_rslvr_t
	 WHERE typ_id = 10001

DELETE FROM krms_typ_t
	 WHERE typ_id = 10001