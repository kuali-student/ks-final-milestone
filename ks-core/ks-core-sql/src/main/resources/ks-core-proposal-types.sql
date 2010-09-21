insert into KSEMBEDDED.KSPR_PROPOSAL_DOCREL_TYPE (ID, DESCRIPTION, EFF_DT, EXPIR_DT, NAME) values ('kuali.proposal.docRelationType.attachment', 'Default proposal document relation type', {ts '2000-01-01 00:00:00.0'},null, 'kuali.proposal.docRelationType.attachment');

insert into KSEMBEDDED.KSPR_PROPOSAL_REFTYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.proposal.referenceType.clu', 'Clu proposal reference type', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'Clu Proposal Reference');

insert into KSEMBEDDED.KSPR_PROPOSAL_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.proposal.type.course.create', 'A create course proposal type', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'Create Course Proposal');
insert into KSEMBEDDED.KSPR_PROPOSAL_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.proposal.type.course.modify', 'A modify course proposal type', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'Modify Course Proposal');
insert into KSEMBEDDED.KSPR_PROPOSAL_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.proposal.type.course.retire', 'A retire course proposal type', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'Retire Course Proposal');
