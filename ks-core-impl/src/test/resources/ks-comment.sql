// RichText
INSERT INTO KS_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RT-DESC-COMMENT-1', '<p>Comment 1</p>', 'Comment 1')
/

// Comment Type
INSERT INTO KSCOMMENT_COMMENT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('commentType.type1', 'A Basic Comment 1', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'ResultComponent 1')
/

// Comment
INSERT INTO KSCOMMENT_COMMENT (ID, RT_DESCR_ID, EFF_DT, EXPIR_DT, TYPE, STATE, VERSIONIND) VALUES ('COMMENT-1', 'RT-DESC-COMMENT-1', {ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'}, 'commentType.type1', 'Active', 0)
/