--KSENROLL-1462
--Adding Formated mesg to the data to prevent null check
update KSEN_PROCESS_INSTRN proc
   set proc.mesg_formatted = '"messageKey":"kuali.lpr.trans.message.registration.fees.outstanding"'
 where proc.check_id = 'kuali.process.check.paid.all.fees'
   and proc.process_id = 'kuali.process.process.registration.eligibility'
/
update KSEN_PROCESS_INSTRN proc
   set proc.mesg_formatted = '"messageKey":"kuali.lpr.trans.message.basic.not.alive"'
 where proc.check_id = 'kuali.process.check.is.alive'
   and proc.process_id = 'kuali.process.process.basic.eligibility'
/
update KSEN_PROCESS_INSTRN proc
   set proc.mesg_formatted = '"messageKey":"kuali.lpr.trans.message.registration.suspended"'
 where proc.check_id = 'kuali.process.check.is.not.suspended'
   and proc.process_id = 'kuali.process.process.registration.eligibility'
/
update KSEN_PROCESS_INSTRN proc
   set proc.mesg_formatted = '"messageKey":"kuali.lpr.trans.message.registration.credit.limit.exceeded"'
 where proc.check_id = 'kuali.check.best.effort.credit.load'
   and proc.process_id = 'kuali.process.registration.eligible.for.courses'
/
update KSEN_PROCESS_INSTRN proc
   set proc.mesg_formatted = '"messageKey":"kuali.lpr.trans.message.registration.expelled"'
 where proc.check_id = 'kuali.process.check.is.not.expelled'
   and proc.process_id = 'kuali.process.process.registration.eligibility'
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