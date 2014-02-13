create table jira7294_f_atypes as (select a.clu_id as fId2,  t.related_type_id as aotype2 from KSLU_CLUCLU_RELTN a inner join KSLU_CLU b on a.related_clu_id = b.id inner join KSEN_TYPETYPE_RELTN t on t.owner_type_id = b.lutype_id where a.lu_reltn_type_id = 'luLuRelationType.contains')
/
create table jira7294_fo_aotypes as (select lui_id as foId, subquery.clu_id as fId, related_lui_type as aotype  from ksen_lui_related_lui_types, (select id, clu_id from ksen_lui where lui_type = 'kuali.lui.type.course.format.offering') subquery where subquery.id = ksen_lui_related_lui_types.lui_id group by lui_id, subquery.clu_id,related_lui_type)
/
create table jira7294_missing as (select fo.foid, f.aotype2 as aotype from jira7294_fo_aotypes fo, jira7294_f_atypes f where fo.fid = f.fid2 and fo.aotype != f.aotype2 and (not exists (select * from jira7294_fo_aotypes fo2 where fo2.foid = fo.foid and fo2.fid = fo.fid and fo2.aotype = f.aotype2)))
/
insert into ksen_lui_related_lui_types (lui_id, related_lui_type) (select * from jira7294_missing)
/
drop table jira7294_f_atypes
/
drop table jira7294_fo_aotypes
/
drop table jira7294_missing
/
