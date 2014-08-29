--KSENROLL-1462
--Adding Formated mesg to the data to prevent null check
update KSEN_PROCESS_INSTRN proc
   set proc.mesg_formatted = '"messageKey":"kuali.lpr.trans.message.registration.credit.limit.exceeded"'
 where proc.check_id = 'kuali.check.best.effort.credit.load'
   and proc.process_id = 'kuali.process.registration.eligible.for.courses'
/
update KSEN_PROCESS_INSTRN proc
   set proc.mesg_formatted = '"messageKey":"kuali.lpr.trans.message.registration.time.conflict"'
 where proc.check_id = 'kuali.check.best.effort.time.conflict'
   and proc.process_id = 'kuali.process.registration.eligible.for.courses'
/
update KSEN_PROCESS_INSTRN proc
   set proc.mesg_formatted = '"messageKey":"kuali.lpr.trans.message.registration.term.not.open"'
 where proc.check_id = 'kuali.check.reg.is.open.for.term'
   and proc.process_id = 'kuali.process.registration.eligibility.for.term'
/