

DELETE FROM KSCO_TAG_TYPE WHERE TYPE_KEY = 'tagType.default';
DELETE FROM KSCO_REFERENCE_TYPE WHERE TYPE_KEY = 'referenceType.clu.proposal';
DELETE FROM KSCO_REFERENCE_TYPE WHERE TYPE_KEY = 'referenceType.clu';
DELETE FROM KSCO_COMMENT_TYPE WHERE TYPE_KEY = 'kuali.comment.type.workflowDecisionRationale.return';
DELETE FROM KSCO_COMMENT_TYPE WHERE TYPE_KEY = 'kuali.comment.type.workflowDecisionRationale.reject';
DELETE FROM KSCO_COMMENT_TYPE WHERE TYPE_KEY = 'kuali.comment.type.workflowDecisionRationale.fyi';
DELETE FROM KSCO_COMMENT_TYPE WHERE TYPE_KEY = 'kuali.comment.type.workflowDecisionRationale.approve';
DELETE FROM KSCO_COMMENT_TYPE WHERE TYPE_KEY = 'kuali.comment.type.workflowDecisionRationale.acknowledge';
DELETE FROM KSCO_COMMENT_TYPE WHERE TYPE_KEY = 'kuali.comment.type.generalRemarks';
DELETE FROM KSCO_COMMENT_TYPE WHERE TYPE_KEY = 'commentType.kuali.lu.type.CreditCourse.submitted';
DELETE FROM KSCO_COMMENT_TYPE WHERE TYPE_KEY = 'commentType.kuali.lu.type.CreditCourse.draft';
