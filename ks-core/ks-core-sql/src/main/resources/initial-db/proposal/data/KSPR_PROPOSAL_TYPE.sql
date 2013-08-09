TRUNCATE TABLE KSPR_PROPOSAL_TYPE DROP STORAGE
/
INSERT INTO KSPR_PROPOSAL_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Create Course Proposal','644BEFA1E0A740418D85FDC10B93DC29','A create course proposal type','kuali.proposal.type.course.create',0)
/
INSERT INTO KSPR_PROPOSAL_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Modify Course Proposal','8F4456ECC6A140479018CCF4970A1C7E','A modify course proposal type','kuali.proposal.type.course.modify',0)
/
INSERT INTO KSPR_PROPOSAL_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Retire Course Proposal','9ADA9C5759BA40439240ED1C1A9B6EDF','A retire course proposal type','kuali.proposal.type.course.retire',0)
/
INSERT INTO KSPR_PROPOSAL_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Credit Course Admin Proposal','b0153829-89b9-4c6e-8aab-a8f914b7a417','Credit Course Admin Proposal','kuali.proposal.type.course.create.admin',0)
/

INSERT INTO KSPR_PROPOSAL_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Modify Credit Course Admin Proposal','3ba80f94-2945-49b5-90f0-a010e8ef9eb5','Modify Credit Course Admin Proposal','kuali.proposal.type.course.modify.admin',0)
/

INSERT INTO KSPR_PROPOSAL_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Create Major Discipline Proposal','b9a5ed17-ba00-4eae-bcbb-660d293d8107','Create Major Discipline Proposal','kuali.proposal.type.majorDiscipline.create',0)
/
INSERT INTO KSPR_PROPOSAL_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Modify Major Discipline Proposal','70c7efa4-d269-472e-8333-41decae7c041','Modify Major Discipline Proposal','kuali.proposal.type.majorDiscipline.modify',0)
/