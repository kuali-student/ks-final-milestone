-- KSAP-1776 Setup Course note ref data

-- PHYS263 for Spring 2014 for b.nanal
DECLARE
 var_note_text varchar2(4000):='Testing & test';
 var_term varchar2(100):='kuali.atp.2014Spring';
 var_plan_id varchar2(100):='b.nanalPlan1';
 var_plan_item_id varchar2(36);
 var_course_id varchar2(36):='b4ce4690-7289-4c9c-a39f-0f73bd193fcb';
BEGIN

  select plan_item.id into var_plan_item_id
  from KSPL_LRNG_PLAN_ITEM plan_item, KSPL_LRNG_PLAN_ITEM_ATP_ID plan_term
  where plan_item.REF_OBJ_ID = var_course_id and plan_item.plan_id = var_plan_id
    and plan_item.id = plan_term.PLAN_ITEM_ID
    and plan_term.ATP_ID = var_term;

  UPDATE KSPL_LRNG_PLAN_ITEM set DESCR_FORMATTED = var_note_text, DESCR_PLAIN = var_note_text where ID = var_plan_item_id;

END;
/

-- BSCI454 for Spring 2015 for b.nanal
DECLARE
 var_note_text varchar2(4000):='A student (Also Pupil) is a learner, or someone who attends an educational institution. In some nations, the English term (or its cognate in another language) is reserved for those who attend university, while a schoolchild under the age of eighteen is called a pupil in English (or an equivalent in other languages), although in the United States and in Australia a person enrolled in grades K–12 is often called a student. In its widest use, student is used for anyone who is learning, including mi';
 var_term varchar2(100):='kuali.atp.2015Spring';
 var_plan_id varchar2(100):='b.nanalPlan1';
 var_plan_item_id varchar2(36);
 var_course_id varchar2(36):='66384e35-d343-4203-98eb-64fd7013c223';
BEGIN

  select plan_item.id into var_plan_item_id
  from KSPL_LRNG_PLAN_ITEM plan_item, KSPL_LRNG_PLAN_ITEM_ATP_ID plan_term
  where plan_item.REF_OBJ_ID = var_course_id and plan_item.plan_id = var_plan_id
    and plan_item.id = plan_term.PLAN_ITEM_ID
    and plan_term.ATP_ID = var_term;

  UPDATE KSPL_LRNG_PLAN_ITEM set DESCR_FORMATTED = var_note_text, DESCR_PLAIN = var_note_text where ID = var_plan_item_id;

END;
/

-- PHYS121 for Summer1 2014 for b.nanal
DECLARE
 var_note_text varchar2(4000):='Be sure to take Mr. Ford''s class';
 var_term varchar2(100):='kuali.atp.2014Summer1';
 var_plan_id varchar2(100):='b.nanalPlan1';
 var_plan_item_id varchar2(36);
 var_course_id varchar2(36):='515df57a-b171-43ed-a2c6-badaf47b348a';
BEGIN

  select plan_item.id into var_plan_item_id
  from KSPL_LRNG_PLAN_ITEM plan_item, KSPL_LRNG_PLAN_ITEM_ATP_ID plan_term
  where plan_item.REF_OBJ_ID = var_course_id and plan_item.plan_id = var_plan_id
    and plan_item.id = plan_term.PLAN_ITEM_ID
    and plan_term.ATP_ID = var_term;

  UPDATE KSPL_LRNG_PLAN_ITEM set DESCR_FORMATTED = var_note_text, DESCR_PLAIN = var_note_text where ID = var_plan_item_id;

END;
/

-- ENGL329 for Spring 2014 for b.nanal
DECLARE
 var_note_text varchar2(4000):='If you end up taking this class; be sure to talk to your advisor first.';
 var_term varchar2(100):='kuali.atp.2014Spring';
 var_plan_id varchar2(100):='b.nanalPlan1';
 var_plan_item_id varchar2(36);
 var_course_id varchar2(36):='0f97b07a-8ff2-4ff0-a248-c7b2e3382b43';
BEGIN

  select plan_item.id into var_plan_item_id
  from KSPL_LRNG_PLAN_ITEM plan_item, KSPL_LRNG_PLAN_ITEM_ATP_ID plan_term
  where plan_item.REF_OBJ_ID = var_course_id and plan_item.plan_id = var_plan_id
    and plan_item.id = plan_term.PLAN_ITEM_ID
    and plan_term.ATP_ID = var_term;

  UPDATE KSPL_LRNG_PLAN_ITEM set DESCR_FORMATTED = var_note_text, DESCR_PLAIN = var_note_text where ID = var_plan_item_id;

END;
/

-- BSCI122 for Spring 2014 for b.julil
DECLARE
 var_note_text varchar2(4000):='This was Dan''s "favorite" class.';
 var_term varchar2(100):='kuali.atp.2014Spring';
 var_plan_id varchar2(100):='b.julilPlan1';
 var_plan_item_id varchar2(36);
 var_course_id varchar2(36):='4e731e87-f1b2-4dd1-883a-2af6deacb9c5';
BEGIN

  select plan_item.id into var_plan_item_id
  from KSPL_LRNG_PLAN_ITEM plan_item, KSPL_LRNG_PLAN_ITEM_ATP_ID plan_term
  where plan_item.REF_OBJ_ID = var_course_id and plan_item.plan_id = var_plan_id
    and plan_item.id = plan_term.PLAN_ITEM_ID
    and plan_term.ATP_ID = var_term;

  UPDATE KSPL_LRNG_PLAN_ITEM set DESCR_FORMATTED = var_note_text, DESCR_PLAIN = var_note_text where ID = var_plan_item_id;

END;
/

-- PHYS121 for Summer1 2014 for b.julil
DECLARE
 var_note_text varchar2(4000):=';alert(String.fromCharCode(88,83,83))//'';alert(String.fromCharCode(88,83,83))//";
alert(String.fromCharCode(88,83,83))//";alert(String.fromCharCode(88,83,83))//--
></SCRIPT>">''><SCRIPT>alert(String.fromCharCode(88,83,83))</SCRIPT>';
 var_term varchar2(100):='kuali.atp.2014Summer1';
 var_plan_id varchar2(100):='b.julilPlan1';
 var_plan_item_id varchar2(36);
 var_course_id varchar2(36):='515df57a-b171-43ed-a2c6-badaf47b348a';
BEGIN

  select plan_item.id into var_plan_item_id
  from KSPL_LRNG_PLAN_ITEM plan_item, KSPL_LRNG_PLAN_ITEM_ATP_ID plan_term
  where plan_item.REF_OBJ_ID = var_course_id and plan_item.plan_id = var_plan_id
    and plan_item.id = plan_term.PLAN_ITEM_ID
    and plan_term.ATP_ID = var_term;

  UPDATE KSPL_LRNG_PLAN_ITEM set DESCR_FORMATTED = var_note_text, DESCR_PLAIN = var_note_text where ID = var_plan_item_id;

END;
/