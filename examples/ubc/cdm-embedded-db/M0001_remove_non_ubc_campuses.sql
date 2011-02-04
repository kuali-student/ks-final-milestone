set scan off;

delete from KSEM_CTX_JN_ENUM_VAL_T
where enum_val_id in ('33', '34')

delete from KSEM_ENUM_VAL_T
where id in ('33', '34');

commit;