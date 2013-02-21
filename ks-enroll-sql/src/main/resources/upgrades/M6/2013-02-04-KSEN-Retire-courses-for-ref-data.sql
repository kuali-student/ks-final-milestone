--Retire these courses KSENROLL-5070
update KSLU_CLU set st='Retired', LAST_ATP='kuali.atp.2016Spring' where id='CLUID-CHEM484-198108000000'
/
update KSLU_CLU set st='Retired', LAST_ATP='kuali.atp.2015Fall' where id='5db19137-3986-43f9-8eb2-526cfc4276f1'
/
update KSLU_CLU set st='Retired', LAST_ATP='kuali.atp.2016Spring' where id='6f3d5fb4-4449-4f0f-a3ed-5e82ce85b7ef'
/
update KSLU_CLU set st='Retired', LAST_ATP='kuali.atp.2015Fall' where id='d28296de-37ed-4ac8-b3df-af7de22ba33a'
/
update KSLU_CLU set st='Retired', LAST_ATP='kuali.atp.2016Spring' where id='c13bf04e-60c9-4488-a177-4e1997d7d7b3'
/

--fix states of all formats and activities (just in case)

--Set the states of the formats to that of the clu
UPDATE
    KSLU_CLU uclu
SET
    ST =
    (
        SELECT
            c.st
        FROM
            KSLU_CLU c,
            KSLU_CLU f,
            KSLU_CLUCLU_RELTN cfr
        WHERE
            f.id=uclu.id
        AND cfr.CLU_ID=c.id
        AND f.id = cfr.RELATED_CLU_ID
    )
WHERE
    uclu.LUTYPE_ID='kuali.lu.type.CreditCourseFormatShell'
/

--update activity
UPDATE
    KSLU_CLU uclu
SET
    st=
    (
        SELECT
            f.st
        FROM
            KSLU_CLU f,
            KSLU_CLUCLU_RELTN far
        WHERE
            far.CLU_ID = f.id
        AND uclu.id = far.RELATED_CLU_ID
    )
WHERE
    LUTYPE_ID LIKE 'kuali.lu.type.activity.%'
/
