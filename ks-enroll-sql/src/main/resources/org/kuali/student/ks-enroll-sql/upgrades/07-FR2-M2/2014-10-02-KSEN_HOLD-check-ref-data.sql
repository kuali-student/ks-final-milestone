insert into ksen_hold (APP_EFF_TERM_ID, APP_EXPIR_TERM_ID, CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT,
            HOLD_STATE, HOLD_TYPE, ID, ISSUE_ID, NAME, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR)
values ('kuali.atp.2012Fall', '', 'SYSTEMLOADER',SYSDATE,
       'Student has exceeded the maximum number of registration transactions and must register in person at the Registrar''s Office. Contact Registrar''s Office at 123-456-7890.',
       'Student has exceeded the maximum number of registration transactions and must register in person at the Registrar''s Office. Contact Registrar''s Office at 123-456-7890.',
       TO_DATE( '2011-07-01', 'YYYY-MM-DD' ), '', 'kuali.hold.appliedhold.state.active', 'kuali.hold.holdissue.type.studentrecord', 'registration.transactions.limit.A.JANED', 'kuali.hold.issue.registration.transactions.limit',
       'Student Record', SYS_GUID(), 'KS-8152', null,null, 0)
/

insert into ksen_hold (APP_EFF_TERM_ID, APP_EXPIR_TERM_ID, CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT,
            HOLD_STATE, HOLD_TYPE, ID, ISSUE_ID, NAME, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR)
values ('kuali.atp.2012Fall', '', 'SYSTEMLOADER',SYSDATE,
       'Student must meet and be cleared by their department / advising college in order to register. Contact Academic Advising College, or department as appropriate.',
       'Student must meet and be cleared by their department / advising college in order to register. Contact Academic Advising College, or department as appropriate.',
       TO_DATE( '2011-07-01', 'YYYY-MM-DD' ), '', 'kuali.hold.appliedhold.state.active', 'kuali.hold.holdissue.type.advising', 'mandatory.advising.A.JAYR', 'kuali.hold.issue.mandatory.advising',
       'Academic Advising Issue', SYS_GUID(), 'KS-6153', null,null, 0)
/