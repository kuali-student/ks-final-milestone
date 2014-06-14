select a.entity_id, 
A.ACTV_IND,
C.prncpl_id, 
C.prncpl_nm,
c.PRNCPL_PSWD,
B.NM_TYP_CD,
B.DFLT_IND,
B.FIRST_NM, 
B.last_nm, 
B.middle_nm, 
B.NM_TYP_CD, 
D.AFLTN_TYP_CD,
D.CAMPUS_CD,
e.BIRTH_DT,
e.DECEASED_DT,
E.GNDR_CD
from KRIM_ENTITY_T A, 
KRIM_ENTITY_NM_T B, 
KRIM_PRNCPL_T C,
KRIM_ENTITY_AFLTN_T D,
KRIM_ENTITY_BIO_T E
where a.entity_id = b.entity_id (+)
and a.entity_id = c.entity_id (+)
and a.entity_id = d.entity_id (+)
and a.entity_id = e.entity_id (+)
order by a.entity_id
/