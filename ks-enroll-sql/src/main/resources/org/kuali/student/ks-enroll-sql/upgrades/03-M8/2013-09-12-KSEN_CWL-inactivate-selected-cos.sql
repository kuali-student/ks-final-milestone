-- KSENROLL-9332 inactivate some CWLs for a set of courses
UPDATE
    KSEN_CWL
SET
    CWL_STATE = 'kuali.course.waitlist.state.inactive'
WHERE
    ID IN
    (
        SELECT
            cwl2ao.CWL_ID
        FROM
            KSEN_LUI_IDENT ci,
            KSEN_LUILUI_RELTN cfr,
            KSEN_LUILUI_RELTN far,
            KSEN_CWL_ACTIV_OFFER cwl2ao
        WHERE
            ci.LUI_CD IN('BSCI338L',
                         'BSCI473',
                         'BSCI473',
                         'ENGL201',
                         'ENGL206',
                         'ENGL206',
                         'ENGL243',
                         'ENGL245',
                         'ENGL291',
                         'ENGL369H',
                         'ENGL369L',
                         'ENGL369L',
                         'ENGL394',
                         'ENGL409P',
                         'HIST110',
                         'HIST232',
                         'HIST232',
                         'HIST319C',
                         'HIST463',
                         'HIST469T',
                         'PHYS111',
                         'WMST269G',
                         'WMST269I',
                         'WMST269W',
                         'WMST369G')
        AND cfr.LUI_ID = ci.LUI_ID
        AND cfr.LUILUI_RELTN_TYPE='kuali.lui.lui.relation.type.deliveredvia.co2fo'
        AND far.LUI_ID = cfr.RELATED_LUI_ID
        AND far.LUILUI_RELTN_TYPE='kuali.lui.lui.relation.type.deliveredvia.fo2ao'
        AND cwl2ao.ACTIV_OFFER_ID=far.RELATED_LUI_ID)
/

UPDATE
    KSEN_LUI_ATTR
SET
    ATTR_VALUE = 'false'
WHERE
    ATTR_KEY = 'kuali.attribute.wait.list.indicator'
AND OWNER_ID IN
    (
        SELECT
            ci.LUI_ID
        FROM
            KSEN_LUI_IDENT ci
        WHERE
            ci.LUI_CD IN('BSCI338L',
                         'BSCI473',
                         'BSCI473',
                         'ENGL201',
                         'ENGL206',
                         'ENGL206',
                         'ENGL243',
                         'ENGL245',
                         'ENGL291',
                         'ENGL369H',
                         'ENGL369L',
                         'ENGL369L',
                         'ENGL394',
                         'ENGL409P',
                         'HIST110',
                         'HIST232',
                         'HIST232',
                         'HIST319C',
                         'HIST463',
                         'HIST469T',
                         'PHYS111',
                         'WMST269G',
                         'WMST269I',
                         'WMST269W',
                         'WMST369G') )
/
