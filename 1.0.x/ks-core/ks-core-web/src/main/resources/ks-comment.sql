// RichText
INSERT INTO KSCO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RT-DESC-COMMENT-1', '<p>Comment 1</p>', 'Comment 1')
/


// Reference Type
INSERT INTO KSCO_REFERENCE_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('referenceType.clu', 'Clu reference type', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Clu')
/
INSERT INTO KSCO_REFERENCE_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('referenceType.type1', 'A Basic Reference 1', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Reference 1')
/


//Reference
INSERT INTO KSCO_REFERENCE(ID,REFERENCE_ID,REFERENCE_TYPE) VALUES('1234567ASDF','REF-1','referenceType.type1')
/
INSERT INTO KSCO_REFERENCE(ID,REFERENCE_ID,REFERENCE_TYPE) VALUES('1234568ASDF','REF-2','referenceType.type1')
/
INSERT INTO KSCO_REFERENCE(ID,REFERENCE_ID,REFERENCE_TYPE) VALUES('1234569ASDF','REF-3','referenceType.type1')
/

// Comment Type
INSERT INTO KSCO_COMMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('commentType.kuali.lu.type.CreditCourse.draft', 'Credit Course Proposal', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Proposal')
/
INSERT INTO KSCO_COMMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('commentType.kuali.lu.type.CreditCourse.submitted', 'Credit Course Submitted', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Submitted')
/
INSERT INTO KSCO_COMMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('commentType.type1', 'A Basic Comment 1', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Comment 1')
/
INSERT INTO KSCO_COMMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('commentType.type2', 'A Basic Comment 2', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Comment 2')
/
// Comment
INSERT INTO KSCO_COMMENT (ID, RT_DESCR_ID, REFERENCE, EFF_DT, EXPIR_DT, TYPE, STATE, VERSIONIND) VALUES ('COMMENT-1', 'RT-DESC-COMMENT-1', '1234567ASDF', {ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'}, 'commentType.type1', 'Active', 0)
/
INSERT INTO KSCO_COMMENT (ID, RT_DESCR_ID, REFERENCE, EFF_DT, EXPIR_DT, TYPE, STATE, VERSIONIND) VALUES ('COMMENT-2', 'RT-DESC-COMMENT-1', '1234567ASDF', {ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'}, 'commentType.type1', 'Active', 0)
/
INSERT INTO KSCO_COMMENT (ID, RT_DESCR_ID, REFERENCE, EFF_DT, EXPIR_DT, TYPE, STATE, VERSIONIND) VALUES ('COMMENT-3', 'RT-DESC-COMMENT-1', '1234568ASDF', {ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'}, 'commentType.type2', 'Active', 0)
/

// Tag Type
INSERT INTO KSCO_TAG_TYPE(TYPE_KEY,NAME,TYPE_DESC,EFF_DT,EXPIR_DT) values ('tagType.default','Default','Default tag type',{ts '2000-01-01 00:00:00.0'},{ts '2000-12-31 00:00:00.0'})
/

// Tag
INSERT INTO KSCO_TAG(ID,NAME_SPACE,PREDICATE,VAL,REFERENCE,EFF_DT,EXPIR_DT, TYPE, STATE, VERSIONIND) VALUES ('Comment-TAG-1','UnitedStates','era','20thCentury','1234567ASDF',{ts '2000-01-01 00:00:00.0'},{ts '2000-12-31 00:00:00.0'},'tagType.default','active',0)
/
INSERT INTO KSCO_TAG(ID,NAME_SPACE,PREDICATE,VAL,REFERENCE,EFF_DT,EXPIR_DT, TYPE, STATE, VERSIONIND) VALUES ('Comment-TAG-2','UnitedStates','era','20thCentury','1234568ASDF',{ts '2000-01-01 00:00:00.0'},{ts '2000-12-31 00:00:00.0'},'tagType.default','active',0)
/

