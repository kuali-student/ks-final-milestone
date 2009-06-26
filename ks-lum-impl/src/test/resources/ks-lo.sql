// RichText
INSERT INTO KS_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-1', '<p>Desc</p>', 'Desc')
INSERT INTO KS_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-2', '<p>Learning Objectives maintained generally for Florida State University. These are distinct from those maintained in a particular subject area or by a particular department.</p>', 'Learning Objectives maintained generally for Florida State University. These are distinct from those maintained in a particular subject area or by a particular department.')
INSERT INTO KS_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-3', '<p>Default Learning Objective type</p>', 'Default Learning Objective type')
INSERT INTO KS_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-4', '<p>Basic Learning Objective type</p>', 'Basic Learning Objective type')
INSERT INTO KS_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-5', '<p>Advanced Learning Objective type</p>', 'Advanced Learning Objective type')
INSERT INTO KS_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-6', 'The ability to use sensory cues to ...', 'The ability to use sensory cues to guide motor activity.  This ranges from sensory stimulation, through cue selection, to translation.')

// LoType
INSERT INTO KSLU_LO_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('loType.default', 'Default','Default type of learning objective.', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)

// LoHierarchy
// INSERT INTO KSLU_LO_HIRCHY (ID, NAME, RT_DESCR_ID, LO_ROOT_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('loHierarchy.fsu', 'FSU', 'RICHTEXT-2', '81ABEA67-3BCC-4088-8348-E265F3670145', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLU_LO_HIRCHY (ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('loHierarchy.fsu', 'FSU', 'RICHTEXT-2', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLU_LO_HIRCHY (ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('loHierarchy.kualiproject.common', 'Kuali', 'RICHTEXT-1', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)

// LoCategory
INSERT INTO KSLU_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LOHIRCHY_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Perception', 'RICHTEXT-6', 'loHierarchy.kualiproject.common', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLU_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LOHIRCHY_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('054CAA88-C21D-4496-8287-36A311A11D68', 'Test Category 2', 'RICHTEXT-1', 'loHierarchy.fsu', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLU_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LOHIRCHY_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('162979A3-25B9-4921-BC8F-C861B2267A73', 'Test Category 3', 'RICHTEXT-1', 'loHierarchy.fsu', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLU_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LOHIRCHY_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('7114D2A4-F66D-4D3A-9D41-A7AA4299C797', 'Test Category 4', 'RICHTEXT-1', 'loHierarchy.kualiproject.common', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)

// Lo 
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LOHIRCHY_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('81ABEA67-3BCC-4088-8348-E265F3670145', 'Edit Wiki Message Structure', 'RICHTEXT-1', 'loHierarchy.fsu', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'loType.default', 'Active', 1)
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LOHIRCHY_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF', 'Navigate Wiki', 'RICHTEXT-4', 'loHierarchy.fsu', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'loType.default', 'Active', 1)
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LOHIRCHY_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('DD0658D2-FDC9-48FA-9578-67A2CE53BF8A', 'Install Wiki Engine', 'RICHTEXT-5', 'loHierarchy.fsu', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'loType.default', 'Active', 1)
