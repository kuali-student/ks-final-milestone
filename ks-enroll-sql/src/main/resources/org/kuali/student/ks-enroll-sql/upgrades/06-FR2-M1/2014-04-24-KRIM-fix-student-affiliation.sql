--KSAP-1195  (change user (i.e. prncpl_id) "student" affiliation from "STAFF" to "STDNT"
update krim_entity_afltn_t set afltn_typ_cd='STDNT', last_updt_dt=current_timestamp(3)
where entity_id in (select entity_id from krim_prncpl_t where prncpl_nm='student')
and afltn_typ_cd='STAFF'
/