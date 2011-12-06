alter table KSEMBEDDED.KREW_PPL_FLW_MBR_T drop constraint KREW_PPL_FLW_MBR_TC1
/

alter table KSEMBEDDED.KREW_PPL_FLW_MBR_T add (constraint KREW_PPL_FLW_MBR_TC1 unique ( PPL_FLW_ID, MBR_TYP_CD, MBR_ID ))
/

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