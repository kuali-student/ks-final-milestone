--KSENROLL-12110
UPDATE KRMS_NL_TMPL_T
   SET TMPL = 'Final Exam Based on Activity Offering'
 WHERE TYP_ID IN
       (Select TYP_ID
          from krms_typ_t
         where NM = 'kuali.krms.agenda.type.final.exam.standard')
  AND NL_USAGE_ID = (SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.catalog')
/

UPDATE KRMS_NL_TMPL_T
   SET TMPL = 'Final Exam Based on Activity Offering'
 WHERE TYP_ID IN
       (Select TYP_ID
          from krms_typ_t
         where NM = 'kuali.krms.agenda.type.final.exam.standard')
  AND NL_USAGE_ID = (SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.type.description')
/


UPDATE KRMS_NL_TMPL_T
   SET TMPL = 'Final Exam Based on Activity Offering:'
 WHERE TYP_ID IN
       (Select TYP_ID
          from krms_typ_t
         where NM ='kuali.krms.rule.type.final.exam.standard')
  AND NL_USAGE_ID = (SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.catalog')
/
 

UPDATE KRMS_NL_TMPL_T
   SET TMPL = 'Final Exam Based on Activity Offering:'
 WHERE TYP_ID IN
       (Select TYP_ID
          from krms_typ_t
         where NM ='kuali.krms.rule.type.final.exam.standard')
  AND NL_USAGE_ID = (SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.type.description')
/
   
                      
UPDATE KRMS_NL_TMPL_T
   SET TMPL = 'Final Exam Based on Course Offering'
 WHERE TYP_ID IN
       (Select TYP_ID
          from krms_typ_t
         where NM = 'kuali.krms.agenda.type.final.exam.common')
   AND NL_USAGE_ID = (SELECT NL_USAGE_ID
                        FROM KRMS_NL_USAGE_T
                       where NM like 'kuali.krms.catalog')
/

UPDATE KRMS_NL_TMPL_T
   SET TMPL = 'Final Exam Based on Course Offering'
 WHERE TYP_ID IN
       (Select TYP_ID
          from krms_typ_t
         where NM = 'kuali.krms.agenda.type.final.exam.common')
   AND NL_USAGE_ID = (SELECT NL_USAGE_ID
                        FROM KRMS_NL_USAGE_T
                       where NM like 'kuali.krms.type.description')
/

UPDATE KRMS_NL_TMPL_T
   SET TMPL = 'Final Exam Based on Course Offering:'
 WHERE TYP_ID IN
       (Select TYP_ID
          from krms_typ_t
         where NM ='kuali.krms.rule.type.final.exam.common')
   AND NL_USAGE_ID = (SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.catalog')       
/

UPDATE KRMS_NL_TMPL_T
   SET TMPL = 'Final Exam Based on Course Offering'
 WHERE TYP_ID IN
       (Select TYP_ID
          from krms_typ_t
         where NM ='kuali.krms.rule.type.final.exam.common')
   AND NL_USAGE_ID = (SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.type.description')       
/

UPDATE KRMS_TYP_T
   SET NM = 'kuali.krms.agenda.type.final.exam.activity.offering.driven'
 WHERE TYP_ID = (Select TYP_ID
          from krms_typ_t
         where NM = 'kuali.krms.agenda.type.final.exam.standard')
/

UPDATE KRMS_TYP_T
   SET NM = 'kuali.krms.rule.type.final.exam.activity.offering.driven'
 WHERE TYP_ID = (Select TYP_ID
          from krms_typ_t
         where NM = 'kuali.krms.rule.type.final.exam.standard')
/


UPDATE KRMS_TYP_T
   SET NM = 'kuali.krms.agenda.type.final.exam.course.offering.driven'
 WHERE TYP_ID = (Select TYP_ID
          from krms_typ_t
         where NM = 'kuali.krms.agenda.type.final.exam.common')
/

UPDATE KRMS_TYP_T
   SET NM = 'kuali.krms.rule.type.final.exam.course.offering.driven'
 WHERE TYP_ID = (Select TYP_ID
          from krms_typ_t
         where NM = 'kuali.krms.rule.type.final.exam.common')
/