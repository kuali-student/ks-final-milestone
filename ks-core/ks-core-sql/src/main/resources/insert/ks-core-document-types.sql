insert into KSDO_RICH_TEXT_T (ID, FORMATTED, PLAIN) values ('RT-DESC-DOCUMENT-1', null, 'Documents attached to a proposal');
insert into KSDO_RICH_TEXT_T (ID, FORMATTED, PLAIN) values ('RT-DESC-DOCUMENT-2', null, 'Documents attached to a Course');

insert into KSDO_DOCUMENT_CATEGORY (CATEGORY_ID, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID) values ('documentCategory.course', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'Course Documents', 'RT-DESC-DOCUMENT-2');
insert into KSDO_DOCUMENT_CATEGORY (CATEGORY_ID, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID) values ('documentCategory.proposal', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'Proposal Documents', 'RT-DESC-DOCUMENT-1');

insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.doc', 'doc Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'doc');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.docx', 'docx Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'docx');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.gif', 'gif Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'gif');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.gz', 'gz Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'gz');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.jpg', 'jpg Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'jpg');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.mp3', 'mp3 Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'mp3');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.pdf', 'pdf Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'pdf');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.png', 'png Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'png');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.ppt', 'ppt Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'ppt');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.rar', 'rar Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'rar');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.rtf', 'rtf Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'rtf');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.tar', 'tar Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'tar');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.tif', 'tif Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'tif');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.tiff', 'tiff Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'tiff');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.txt', 'txt Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'txt');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.wav', 'wav Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'wav');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.xls', 'xls Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'xls');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.xml', 'xml Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'xml');
insert into KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.zip', 'zip Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'zip');

insert into KSDO_REF_OBJ_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.RefObjectType.CluInfo', 'Clu Object Type', null, null, 'CluInfo');
insert into KSDO_REF_OBJ_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.RefObjectType.ProposalInfo', 'Proposal Object Type', null, null, 'ProposalInfo');

insert into KSDO_REF_OBJ_SUB_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REF_OBJ_TYPE_KEY) values ('kuali.org.RefObjectSubType.Program', 'Sub Type for Program', null, null, 'Program', 'kuali.org.RefObjectType.CluInfo');
insert into KSDO_REF_OBJ_SUB_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REF_OBJ_TYPE_KEY) values ('kuali.org.RefObjectSubType.Course', 'Sub Type for Course', null, null, 'Course', 'kuali.org.RefObjectType.CluInfo');
insert into KSDO_REF_OBJ_SUB_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REF_OBJ_TYPE_KEY) values ('kuali.org.RefObjectSubType.Proposal', 'Sub Type for Proposal', null, null, 'Proposal', 'kuali.org.RefObjectType.ProposalInfo');

insert into KSDO_REF_DOC_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.DocRelation.allObjectTypes', 'Relation for all known sub object types', null, null, 'All Relations');

insert into KSDO_REF_REL_TYP_JN_SUB_TYP (REF_DOC_RELTN_TYPE_KEY, REF_OBJ_SUB_TYPE_KEY) values ('kuali.org.DocRelation.allObjectTypes', 'kuali.org.RefObjectSubType.Program');
insert into KSDO_REF_REL_TYP_JN_SUB_TYP (REF_DOC_RELTN_TYPE_KEY, REF_OBJ_SUB_TYPE_KEY) values ('kuali.org.DocRelation.allObjectTypes', 'kuali.org.RefObjectSubType.Course');
insert into KSDO_REF_REL_TYP_JN_SUB_TYP (REF_DOC_RELTN_TYPE_KEY, REF_OBJ_SUB_TYPE_KEY) values ('kuali.org.DocRelation.allObjectTypes', 'kuali.org.RefObjectSubType.Proposal');
