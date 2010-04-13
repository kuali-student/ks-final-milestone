// Proposal Type
INSERT INTO KSPR_PROPOSAL_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.proposal.type.course.create', 'A create course proposal type', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Create Course Proposal')
/

// Proposal Reference Types
INSERT INTO KSPR_PROPOSAL_REFTYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.proposal.referenceType.clu', 'Clu proposal reference type', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Clu Proposal Reference')
/
INSERT INTO kspr_proposal_docrel_type (ID, description, eff_dt, expir_dt, NAME) VALUES ('kuali.proposal.docRelationType.attachment', 'Default proposal document relation type', {ts '2000-01-01 00:00:00.0'}, null, 'kuali.proposal.docRelationType.attachment')
/