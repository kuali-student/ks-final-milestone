alter sequence KRIM_ROLE_ID_S increment by 3
/

select KRIM_ROLE_ID_S.nextval from dual
/

alter sequence KRIM_ROLE_ID_S increment by 1
/

alter sequence KRIM_ROLE_PERM_ID_S increment by 13
/

select KRIM_ROLE_PERM_ID_S.nextval from dual
/

alter sequence KRIM_ROLE_PERM_ID_S increment by 1
/