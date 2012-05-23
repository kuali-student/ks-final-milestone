--Fix the permission templates for Open Document For RouteStatus and Doctype
UPDATE KRIM_PERM_T SET PERM_TMPL_ID=(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD='KS-SYS' AND NM='Open Document') WHERE PERM_TMPL_ID=(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD='KS-SYS' AND NM='Open Document With Type and Status')
/
DELETE FROM KRIM_PERM_TMPL_T WHERE NM='Open Document With Type and Status' AND NMSPC_CD='KS-SYS'
/