INSERT
INTO
    KSEN_LUI_RELATED_LUI_TYPES
    (
        LUI_ID,
        RELATED_LUI_TYPE
    )
SELECT
    fo.id,
    ttr.RELATED_TYPE_ID
FROM
    KSEN_LUI fo,
    KSLU_CLU a,
    KSLU_CLUCLU_RELTN ccr,
    KSEN_TYPETYPE_RELTN ttr
WHERE
    fo.LUI_TYPE='kuali.lui.type.course.format.offering'
AND fo.CLU_ID=ccr.CLU_ID
AND a.ID=ccr.RELATED_CLU_ID
AND ccr.LU_RELTN_TYPE_ID='luLuRelationType.contains'
AND ttr.TYPETYPE_RELTN_TYPE='kuali.type.type.relation.type.allowed'
AND ttr.OWNER_TYPE_ID=a.LUTYPE_ID
/
