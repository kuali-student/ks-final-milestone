--KSLAB-2615 part 1
update ksem_enum_val_t
set VAL = 'Student Eligibility and Prerequisite'
where cd = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'
and enum_key = 'kuali.dependency.course.types'
/

--KSLAB-2615 part 2
update ksem_enum_val_t
set VAL = 'Cross-Listed Courses'
where cd = 'crossListed'
and enum_key = 'kuali.dependency.course.types'
/