-- Drop index KSPL_LRNG_PLAN_ITEM_ATP_ID_I2 since atp_id is 1st col of the PK index
drop index KSPL_LRNG_PLAN_ITEM_ATP_ID_I2
/

-- drop index KSPL_LRNG_PLAN_ITEM_ATTR_I1 since OWNER_ID is 1st col of the PK index
drop index KSPL_LRNG_PLAN_ITEM_ATTR_I1
/

--RENAME PLANITEMATTR_UNIQUE TO comply w/ std naming conventions */
alter index "PLANITEMATTR_UNIQUE" RENAME TO "KSPL_LRNG_PLAN_ITEM_ATTR_I1"
/

--add index to allow efficent lookup of owner_id by attr_key+attr_value
--....and we'll include owner_id ...to avoid a base table lookup 
create index KSPL_LRNG_PLAN_ITEM_ATTR_I2  on KSPL_LRNG_PLAN_ITEM_ATTR (ATTR_KEY, ATTR_VALUE,OWNER_ID)
/

-- drop index on owner_id since it will be 1st col of unique key below
drop index KSPL_LRNG_PLAN_ATTR_I1
/
create unique index KSPL_LRNG_PLAN_ATTR_I1 on KSPL_LRNG_PLAN_ATTR (OWNER_ID,ATTR_KEY,ATTR_VALUE)
/
--add index on to allow efficent lookup of owner_id by attr_key+attr_value
--....and we'll include owner_id ...to avoid a base table lookup 
create index KSPL_LRNG_PLAN_ATTR_I2  on KSPL_LRNG_PLAN_ATTR (ATTR_KEY, ATTR_VALUE,OWNER_ID)
/
