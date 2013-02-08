delete from krms_typ_reln_t
where from_typ_id = '10007'
/

delete from krms_typ_reln_t
where to_typ_id = '10007'
/

delete from krms_nl_tmpl_t
where typ_id = '10007'
/

delete from krms_typ_t
where typ_id = '10007'
/