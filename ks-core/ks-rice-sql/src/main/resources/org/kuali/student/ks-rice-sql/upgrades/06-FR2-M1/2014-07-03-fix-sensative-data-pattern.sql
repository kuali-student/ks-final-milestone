-- forcing 9 sequential digits to beginning
UPDATE KRCR_PARM_T SET VAL='^[0-9]{9};[0-9]{3}-[0-9]{2}-[0-9]{4}' WHERE NMSPC_CD = 'KR-NS' AND CMPNT_CD = 'All' AND PARM_NM = 'SENSITIVE_DATA_PATTERNS'
/