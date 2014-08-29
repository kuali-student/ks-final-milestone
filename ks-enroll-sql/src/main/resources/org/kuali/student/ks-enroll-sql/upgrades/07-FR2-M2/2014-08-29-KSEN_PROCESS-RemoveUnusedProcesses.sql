-- KSENROLL-14629 Clean up old Process Configuration

-- Processes are constrained to Instructions which are constrained to Checks.
-- In order to delete the unused processes, we must first delete the instructions and checks.

/*
  Processes:
  'kuali.process.process.basic.eligibility',
  'kuali.process.process.dummy.active',
  'kuali.process.process.dummy.disabled',
  'kuali.process.process.dummy.inactive',
  'kuali.process.process.registration.eligibility'
 */



-- Delete the unused Instructions
DELETE FROM KSEN_PROCESS_INSTRN
WHERE PROCESS_ID IN (
  'kuali.process.process.basic.eligibility',
  'kuali.process.process.dummy.active',
  'kuali.process.process.dummy.disabled',
  'kuali.process.process.dummy.inactive',
  'kuali.process.process.registration.eligibility'
)
/

-- Delete the unused Checks
DELETE FROM KSEN_PROCESS_CHECK
WHERE ID IN (
  'kuali.process.check.is.alive',
  'kuali.process.check.is.not.expelled',
  'kuali.process.check.is.not.suspended',
  'kuali.process.check.paid.all.fees'
)
/

-- Delete the unused Processes
DELETE FROM KSEN_PROCESS
WHERE ID IN (
  'kuali.process.process.basic.eligibility',
  'kuali.process.process.dummy.active',
  'kuali.process.process.dummy.disabled',
  'kuali.process.process.dummy.inactive',
  'kuali.process.process.registration.eligibility'
)
/