-- KSLAB-2070
-- Add a new reference type (referenceType.clu.proposal.program) to the table KSCO_REFERENCE_TYPE
INSERT INTO KSCO_REFERENCE_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('referenceType.clu.proposal.program', 'Clu reference type', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'Clu Program Proposal','c5be0928-4a50-4921-8930-eaadecb3323e',0)
/