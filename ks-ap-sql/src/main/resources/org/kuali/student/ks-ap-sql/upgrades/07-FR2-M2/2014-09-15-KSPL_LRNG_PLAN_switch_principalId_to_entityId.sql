-- Switch learningPlan studentId reference data from prinicipalId to entityId (see KSAP-1989)

UPDATE KSPL_LRNG_PLAN SET student_id='1100' where student_id='admin'
/
UPDATE KSPL_LRNG_PLAN SET student_id='KS-1551' where student_id='student'
/
UPDATE KSPL_LRNG_PLAN SET student_id='KS-7344' where student_id='B.JULIL'
/
UPDATE KSPL_LRNG_PLAN SET student_id='KS-7838' where student_id='B.NANAL'
/

