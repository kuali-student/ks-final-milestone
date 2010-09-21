insert into KSEMBEDDED.KSDO_RICH_TEXT_T (ID, FORMATTED, PLAIN) values ('RT-DESC-DOCUMENT-1', null, 'Documents attached to a proposal');
insert into KSEMBEDDED.KSDO_RICH_TEXT_T (ID, FORMATTED, PLAIN) values ('RT-DESC-DOCUMENT-2', null, 'Documents attached to a Course');

insert into KSEMBEDDED.KSDO_DOCUMENT_CATEGORY (CATEGORY_ID, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID) values ('documentCategory.course', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'Course Documents', 'RT-DESC-DOCUMENT-2');
insert into KSEMBEDDED.KSDO_DOCUMENT_CATEGORY (CATEGORY_ID, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID) values ('documentCategory.proposal', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'Proposal Documents', 'RT-DESC-DOCUMENT-1');

insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.doc', 'doc Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'doc');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.docx', 'docx Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'docx');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.gif', 'gif Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'gif');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.gz', 'gz Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'gz');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.jpg', 'jpg Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'jpg');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.mp3', 'mp3 Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'mp3');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.pdf', 'pdf Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'pdf');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.png', 'png Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'png');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.ppt', 'ppt Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'ppt');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.rar', 'rar Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'rar');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.rtf', 'rtf Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'rtf');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.tar', 'tar Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'tar');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.tif', 'tif Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'tif');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.tiff', 'tiff Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'tiff');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.txt', 'txt Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'txt');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.wav', 'wav Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'wav');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.xls', 'xls Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'xls');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.xml', 'xml Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'xml');
insert into KSEMBEDDED.KSDO_DOCUMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('documentType.zip', 'zip Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'zip');
