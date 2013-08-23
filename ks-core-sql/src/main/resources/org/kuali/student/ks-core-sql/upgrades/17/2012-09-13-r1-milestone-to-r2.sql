
-- KSAP_MLSTN to KSEN_MSTONE
INSERT INTO KSEN_MSTONE
SELECT m.id, m.obj_id, NVL(m.mlstn_type_id,'type.null'), NVL(m.state,'null.state'), m.name, NVL(r.plain,''),
    NVL(r.plain,''), 1, 0, 0, null, 0, NVL(m.mlstn_dt,to_date('2000/01/01','YYYY/MM/DD')), null, m.ver_nbr, NVL(m.createtime,to_date('2012/09/12','YYYY/MM/DD')),
    NVL(m.createid,'CMMSTONEUPGRADE'),  to_date('2012/09/12','YYYY/MM/DD'), 'CMMSTONEUPGRADE'
FROM
    KSAP_MLSTN m,
    KSAP_RICH_TEXT_T r
WHERE
    m.rt_descr_id=r.id
AND m.id not in (select id from KSEN_MSTONE)
/

-- KSAP_DT_RANGE to KSEN_MSTONE
INSERT INTO KSEN_MSTONE
SELECT m.daterange_key, m.obj_id, NVL(m.dt_range_type_id,'type.null'), NVL(m.state,'null.state'), m.name, NVL(r.plain,''),
    NVL(r.plain,''), 0, 0, 0, null, 1, NVL(m.start_dt,to_date('2000/01/01','YYYY/MM/DD')), NVL(m.end_dt,to_date('2000/01/01','YYYY/MM/DD')), m.ver_nbr, NVL(m.createtime,to_date('2012/09/12','YYYY/MM/DD')),
    NVL(m.createid,'CMMSTONEUPGRADE'),  to_date('2012/09/12','YYYY/MM/DD'), 'CMMSTONEUPGRADE'
FROM
    KSAP_DT_RANGE m,
    KSAP_RICH_TEXT_T r
WHERE
    m.rt_descr_id=r.id
AND m.daterange_key not in (select id from KSEN_MSTONE)
/

-- KSAP_DT_RANGE_TYPE to KSEN_TYPE
INSERT INTO KSEN_TYPE
SELECT type_key, obj_id, nvl(name,' '), type_desc, type_desc, eff_dt, expir_dt, 'http://student.kuali.org/wsdl/atp/MilestoneInfo',
    'http://student.kuali.org/wsdl/atp/AtpService', nvl(ver_nbr,0), to_date('2012/09/12','YYYY/MM/DD'), 'CMMSTONEUPGRADE', null, null
FROM KSAP_DT_RANGE_TYPE WHERE type_key NOT IN (SELECT TYPE_KEY FROM KSEN_TYPE)
/

-- KSAP_MLSTN_TYPE to KSEN_TYPE
INSERT INTO KSEN_TYPE
SELECT type_key, obj_id, nvl(name,' '), type_desc, type_desc, eff_dt, expir_dt, 'http://student.kuali.org/wsdl/atp/MilestoneInfo',
    'http://student.kuali.org/wsdl/atp/AtpService', nvl(ver_nbr,0), to_date('2012/09/12','YYYY/MM/DD'), 'CMMSTONEUPGRADE', null, null
FROM KSAP_MLSTN_TYPE WHERE type_key NOT IN (SELECT TYPE_KEY FROM KSEN_TYPE)
/

-- KSAP_DT_RANGE.ATP_ID to KSEN_ATPMSTONE_RELTN
INSERT INTO KSEN_ATPMSTONE_RELTN
select d.atp_id||'_INCLUDES_'||d.daterange_key,sys_guid(), d.ver_nbr, d.createid,d.createtime,'CMMSTONEUPGRADE',to_date('2012/09/12','YYYY/MM/DD'),d.atp_id,d.daterange_key
from ksap_dt_range d
where d.atp_id is not null
and d.atp_id||'_INCLUDES_'||d.daterange_key not in (select id from KSEN_ATPMSTONE_RELTN)
/

-- KSAP_MLSTN.ATP_ID to KSEN_ATPMSTONE_RELTN
INSERT INTO KSEN_ATPMSTONE_RELTN
select m.atp_id||'_INCLUDES_'||m.id,sys_guid(), m.ver_nbr, m.createid,m.createtime,'CMMSTONEUPGRADE',to_date('2012/09/12','YYYY/MM/DD'),m.atp_id,m.id
from ksap_mlstn m
where m.atp_id is not null
and m.atp_id||'_INCLUDES_'||m.id not in (select id from KSEN_ATPMSTONE_RELTN)
/

