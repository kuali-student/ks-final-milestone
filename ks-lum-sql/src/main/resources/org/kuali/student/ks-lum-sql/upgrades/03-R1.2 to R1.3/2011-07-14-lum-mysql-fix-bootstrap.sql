--- div is a reserved word in MySQL

ALTER TABLE KSLU_CLU_IDENT RENAME COLUMN DIV to DIVISION
/

--- set dates that work with 32bit MySQL

update kslu_clu_set set eff_dt = to_date('010100', 'MMDDYY') where eff_dt > to_date('123137', 'MMDDYY')
/