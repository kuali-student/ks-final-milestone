/*
DELIMITER //
DROP PROCEDURE IF EXISTS `riceserverdemo`.`test`//
CREATE PROCEDURE `riceserverdemo`.`test`()
BEGIN
  DECLARE done int;
  DECLARE term_spec_id VARCHAR(50);
  DECLARE context_id VARCHAR(50);
  DECLARE CNTXT_TERM_SPEC_PREREQ_ID integer;

  DECLARE term_spec_cursor CURSOR FOR
	select
	  krmst.term_spec_id
	  , krmct.cntxt_id 
	from 
	  krms_term_spec_t krmst
	  , krms_cntxt_t krmct
	where 
	  krmct.nm = 'Stud Eligibility and Prereq'
	  and krmst.nm in (
			   'Credits'
			   ,'Org Number'
			   ,'Course'
			   ,'Course Number'
			   , 'Date'
			   , 'GPA'
			   , 'Grade'
			   , 'GradeType'
			   , 'Learning Objectives'
			   , 'Subject Code'
			   , 'Text'
			   );

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
set done = 0;
open term_spec_cursor;

  read_loop: WHILE(done=0) do


  fetch term_spec_cursor into term_spec_id, context_id;


if done = 1 
  then leave read_loop;
end if;

select 
  max(id) 
into 
  CNTXT_TERM_SPEC_PREREQ_ID  
from 
  krms_cntxt_vld_term_spec_s;



if CNTXT_TERM_SPEC_PREREQ_ID is null 
  then 
    set CNTXT_TERM_SPEC_PREREQ_ID = 0;
  else 
    set CNTXT_TERM_SPEC_PREREQ_ID = CNTXT_TERM_SPEC_PREREQ_ID+1;
end if;
 

insert into krms_cntxt_vld_term_spec_s (id) values (CNTXT_TERM_SPEC_PREREQ_ID);
commit;

insert into krms_cntxt_vld_term_spec_t (cntxt_term_spec_prereq_id, cntxt_id, term_spec_id, prereq)
values (CNTXT_TERM_SPEC_PREREQ_ID,  context_id, term_spec_id, 'N');

end while read_loop;
commit;

close term_spec_cursor;
set done = 0;

    END//

DELIMITER */

CREATE OR REPLACE PROCEDURE KSBUNDLED.TEST IS

  cursor term_spec_cursor IS
	select
	  krmst.term_spec_id
	  , krmct.cntxt_id
	from
	  krms_term_spec_t krmst
	  , krms_cntxt_t krmct
	where
	  krmct.nm = 'Stud Eligibility and Prereq'
	  and krmst.nm in (
			   'Credits'
			   ,'Org Number'
			   ,'Course'
			   ,'Course Number'
			   , 'Date'
			   , 'GPA'
			   , 'Grade'
			   , 'GradeType'
			   , 'Learning Objectives'
			   , 'Subject Code'
			   , 'Text');

BEGIN
  for rec in term_spec_cursor loop
    insert into krms_cntxt_vld_term_spec_t (cntxt_term_spec_prereq_id, cntxt_id, term_spec_id, prereq)
      values (krms_cntxt_vld_term_spec_s.NEXTVAL, rec.cntxt_id, rec.term_spec_id, 'N');
  end loop;
  commit;
END TEST;
