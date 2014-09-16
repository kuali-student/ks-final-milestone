-- Add unique index on KSPL_LRNG_PLAN.studentId (see KSAP-2014)
-- Only one plan is allowed per student ...for now
CREATE unique INDEX KSPL_LRNG_PLAN_I2 on KSPL_LRNG_PLAN (student_id)
/
