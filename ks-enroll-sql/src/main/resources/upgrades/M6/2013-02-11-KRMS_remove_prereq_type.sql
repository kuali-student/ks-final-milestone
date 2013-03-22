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
delete from krms_typ_reln_t
where typ_reln_id = '10030'
/
insert into krms_typ_reln_t (actv,from_typ_id,reln_typ,seq_no,to_typ_id,typ_reln_id,ver_nbr)
values ('Y','10005','A',1,'10021','10030',0)
/
insert into krms_typ_reln_t (actv,from_typ_id,reln_typ,seq_no,to_typ_id,typ_reln_id,ver_nbr)
values ('Y','10010','A',1,'10054','10113',0)
/
insert into krms_typ_reln_t (actv,from_typ_id,reln_typ,seq_no,to_typ_id,typ_reln_id,ver_nbr)
values ('Y','10010','A',1,'10040','10114',0)
/
insert into krms_typ_reln_t (actv,from_typ_id,reln_typ,seq_no,to_typ_id,typ_reln_id,ver_nbr)
values ('Y','10010','A',1,'10055','10115',0)
/
insert into krms_typ_reln_t (actv,from_typ_id,reln_typ,seq_no,to_typ_id,typ_reln_id,ver_nbr)
values ('Y','10010','A',1,'10052','10116',0)
/
insert into krms_typ_reln_t (actv,from_typ_id,reln_typ,seq_no,to_typ_id,typ_reln_id,ver_nbr)
values ('Y','10010','A',1,'10048','10117',0)
/
update krms_rule_t
set typ_id = 10010
where typ_id = 10007
/