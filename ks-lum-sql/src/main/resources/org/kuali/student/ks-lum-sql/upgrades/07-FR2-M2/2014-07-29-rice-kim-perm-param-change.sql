-- updating this parameter so that Document Type permissions will be checked using the KIM permissions
UPDATE KRCR_PARM_T SET VAL='Y' WHERE NMSPC_CD = 'KR-WKFLW' AND CMPNT_CD = 'All' AND PARM_NM = 'KIM_PRIORITY_ON_DOC_TYP_PERMS_IND'
/