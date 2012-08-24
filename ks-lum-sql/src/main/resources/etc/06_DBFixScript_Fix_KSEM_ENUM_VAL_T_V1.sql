-- Run by hand

-- Replacing
-- TRUNCATE TABLE KSEM_ENUM_VAL_T; 
--ALTER TABLE KSEM_ENUM_VAL_T
--MODIFY(SORT_KEY NUMBER(10));

ALTER TABLE KSEM_ENUM_VAL_T
 ADD (SORT_KEY2 NUMBER(10));
 
--  select sort_key, sort_key2 from KSEM_ENUM_VAL_T
 
update KSEM_ENUM_VAL_T b set sort_key2 = (select sort_key from KSEM_ENUM_VAL_T a  where a.id = b.id)

ALTER TABLE KSEM_ENUM_VAL_T DROP COLUMN SORT_KEY;
alter table KSEM_ENUM_VAL_T rename column sort_key2 to sort_key;
