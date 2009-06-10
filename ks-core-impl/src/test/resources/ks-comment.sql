// RichText
INSERT INTO KS_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RT-DESC-COMMENT-1', '<p>Comment 1</p>', 'Comment 1')
/

//Reference Type
INSERT INTO KSCO_REF_TYPE(TYPE_KEY,NAME,TYPE_DESC,EFF_DT,EXPIR_DT) values ('REF-TYPE-0','Default','Default ref type',{ts '2000-01-01 00:00:00.0'},{ts '2000-12-31 00:00:00.0'})

//Reference
INSERT INTO KSCO_REF(ID,RT_DESCR_ID,EFF_DT,EXPIR_DT,STATE,TYPE,VERSIONIND) VALUES('REF-1','RT-DESC-COMMENT-1',{ts '2000-01-01 00:00:00.0'},{ts '2000-12-31 00:00:00.0'},'active','REF-TYPE-0',0)
/
// Comment Type
INSERT INTO KSCO_COMMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('commentType.type1', 'A Basic Comment 1', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'ResultComponent 1')
/

// Comment
INSERT INTO KSCO_COMMENT (ID, RT_DESCR_ID, EFF_DT, EXPIR_DT, TYPE, STATE, VERSIONIND) VALUES ('COMMENT-1', 'RT-DESC-COMMENT-1', {ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'}, 'commentType.type1', 'Active', 0)
/

// Tag Type
INSERT INTO KSCO_TAG_TYPE(TYPE_KEY,NAME,TYPE_DESC,EFF_DT,EXPIR_DT) values ('tagType.default','Default','Default tag type',{ts '2000-01-01 00:00:00.0'},{ts '2000-12-31 00:00:00.0'})
/

// Tag
INSERT INTO KSCO_TAG(ID,NAME_SPACE,PREDICATE,VAL,REF,EFF_DT,EXPIR_DT, TYPE, STATE, VERSIONIND) VALUES ('Comment-TAG-1','UnitedStates','era','20thCentury','REF-1',{ts '2000-01-01 00:00:00.0'},{ts '2000-12-31 00:00:00.0'},'tagType.default','active',0)
/


