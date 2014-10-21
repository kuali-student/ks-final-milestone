--//////////////////////////////////////////////////////////////
--KSENROLL-12869 Grade letters was change in R1 - R2 conversion.
--//////////////////////////////////////////////////////////////

UPDATE KSEN_LRC_RESULT_VALUE RESVAL
   SET RESVAL.NAME = 'A'
 WHERE RESVAL.ID = 'kuali.result.value.grade.letter.a'
   AND RESVAL.RESULT_SCALE_ID = 'kuali.result.scale.grade.letter'
/

UPDATE KSEN_LRC_RESULT_VALUE RESVAL
   SET RESVAL.NAME = 'B'
 WHERE RESVAL.ID = 'kuali.result.value.grade.letter.b'
   AND RESVAL.RESULT_SCALE_ID = 'kuali.result.scale.grade.letter'
/

UPDATE KSEN_LRC_RESULT_VALUE RESVAL
   SET RESVAL.NAME = 'C'
 WHERE RESVAL.ID = 'kuali.result.value.grade.letter.c'
   AND RESVAL.RESULT_SCALE_ID = 'kuali.result.scale.grade.letter'
/

UPDATE KSEN_LRC_RESULT_VALUE RESVAL
   SET RESVAL.NAME = 'D'
 WHERE RESVAL.ID = 'kuali.result.value.grade.letter.d'
   AND RESVAL.RESULT_SCALE_ID = 'kuali.result.scale.grade.letter'
/

UPDATE KSEN_LRC_RESULT_VALUE RESVAL
   SET RESVAL.NAME = 'F'
 WHERE RESVAL.ID = 'kuali.result.value.grade.letter.f'
   AND RESVAL.RESULT_SCALE_ID = 'kuali.result.scale.grade.letter'
/