-- DO NOT COPY AND PASTE THIS COMMENT.  VIOLATORS WILL LOSE COMMIT ACCESS.
-- KEY1:MjAxNC0wNC0wMy1EUk9QLWtzcGwtZGVzY3ItdGFibGVzLnNxbA==
-- KEY2:U1RSVUNUVVJF
-- TYPE:STRUCTURE

/* Drop rich text description tables and add columns to base entity tables, and add NAME column */

alter table KSPL_LRNG_PLAN add
( DESCR_FORMATTED VARCHAR2(2000)
, DESCR_PLAIN VARCHAR2(2000)
, NAME VARCHAR2(255))
/
update KSPL_LRNG_PLAN p set
p.DESCR_FORMATTED=
(select pt.FORMATTED
  from KSPL_LRNG_PLAN_RICH_TEXT pt
  where pt.id=p.rt_descr_id),
p.DESCR_PLAIN=
(select pt.PLAIN
  from KSPL_LRNG_PLAN_RICH_TEXT pt
  where pt.id=p.rt_descr_id),
name=
(select pt.PLAIN
  from KSPL_LRNG_PLAN_RICH_TEXT pt
  where pt.id=p.rt_descr_id)
/
alter table KSPL_LRNG_PLAN drop constraint KSPL_LRNG_PLAN_FK2
/
alter table KSPL_LRNG_PLAN drop column rt_descr_id
/
begin execute immediate 'drop table KSPL_LRNG_PLAN_RICH_TEXT CASCADE CONSTRAINTS PURGE'; exception when others then null; end;
/
alter table KSPL_LRNG_PLAN_ITEM add
( DESCR_FORMATTED VARCHAR2(2000)
, DESCR_PLAIN VARCHAR2(2000)
, NAME VARCHAR2(255))
/
update KSPL_LRNG_PLAN_ITEM p set
p.DESCR_FORMATTED=
(select pt.FORMATTED
  from KSPL_LRNG_PI_RICH_TEXT pt
  where pt.id=p.rt_descr_id),
p.DESCR_PLAIN=
(select pt.PLAIN
  from KSPL_LRNG_PI_RICH_TEXT pt
  where pt.id=p.rt_descr_id),
p.NAME=
(select pt.PLAIN
  from KSPL_LRNG_PI_RICH_TEXT pt
  where pt.id=p.rt_descr_id)
/
alter table KSPL_LRNG_PLAN_ITEM drop constraint KSPL_LRNG_PLAN_ITEM_FK3
/
alter table KSPL_LRNG_PLAN_ITEM drop column rt_descr_id
/
begin execute immediate 'drop table KSPL_LRNG_PI_RICH_TEXT CASCADE CONSTRAINTS PURGE'; exception when others then null; end;
/
