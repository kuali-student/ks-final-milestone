ALTER TABLE KSEN_STATE
ADD IS_INITIAL_STATE number(1) default(0) NOT NULL
/
UPDATE KSEN_STATE
SET is_initial_state = 1
WHERE ID = 'kuali.lui.activity.offering.state.draft'
OR ID = 'kuali.lui.course.offering.state.draft'
OR ID = 'kuali.atp.state.Draft'
OR ID = 'kuali.lui.format.offering.state.draft'
OR ID = 'kuali.milestone.state.Draft'
OR ID = 'kuali.soc.state.draft'
OR ID = 'kuali.soc.scheduling.state.notstarted'
OR ID = 'kuali.lui.registration.group.state.pending'
/