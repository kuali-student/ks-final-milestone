-- replace keys that are expected in Naturual Language Templates
UPDATE ksst_req_com_type_nl_tmpl
	SET template = REPLACE(
                            REPLACE(
                                    template,
                                    'kuali.resultComponent.grade.letter',
                                    'kuali.result.scale.grade.letter'
                            ),
                            'kuali.resultComponent.grade.percentage',
                            'kuali.result.scale.grade.percentage'
                        )
	WHERE template LIKE('%kuali.resultComponent.grade.%')
/

-- This will error out if grades other than letter grades or percentages are
-- referenced in natural language templates.  The purpose of doing this
-- is to force implementors to modify this script to meet their needs.
DECLARE
    unknowns NUMBER;
BEGIN
    SELECT count(*) INTO unknowns FROM ksst_req_com_type_nl_tmpl
            WHERE template LIKE('%kuali.resultComponent.%');
    IF unknowns > 0
    THEN
        RAISE_APPLICATION_ERROR (-20001, 'Unknown Kuali Student Result Component IDs found in KSST_REQ_COM_TYPE_NL_TMPL. Please modify upgrade script to replace these old IDs with new Result Scale IDs');
    END IF;
END;