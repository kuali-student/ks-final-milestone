TRUNCATE TABLE KRIM_PERM_T DROP STORAGE
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows creation and modification of agendas via the agenda editor', 'Maintain KRMS Agenda', 'KR-RULE-TEST', 'D2F07FCB2CEEFFC9E040760A4A45207E', '10000', '10000', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Add Collaborator with Acknowledge', 'KS-SYS', 'EDD9ADB13981434E8971DE96B67F3AD7', '10001', '10003', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to remove adhoc reviewers to documents.', 'Remove Reviewers', 'KS-CM', '45FCFDF311FA4A87B36763BFD5E00D75', '10002', '66', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Course modification screen', 'Use Course Modify Screens', 'KS-SYS', '5C22F2B6FFBD4B05AD568C0668ED8D0F', '10003', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to use the curriculum review checkbox', 'Use Curriculum Review', 'KS-SYS', '802272BDA248410ABA357430652EEE4A', '10004', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to modify the clu without a version', 'Modify Clu Without Version Dialog', 'KS-SYS', '3E09FB90069B4BC9928633B832DBE95F', '10005', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow user to create course admin proposal', 'Create Course By Admin Proposal', 'KS-SYS', '10154D15D2AB4069B42419F533F9CE80', '10006', '10', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow user to create modify course admin proposal', 'Modify Course By Admin Proposal', 'KS-SYS', '24B0E0F9E21140649FA6BF1D395B3A10', '10007', '10', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow user to create a program proposal', 'Create Program By Proposal', 'KS-SYS', '3F6AB347B1614BDCBE3C862DB2293CBC', '10008', '10', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow user to mdoify a program by proposal', 'Modify Program By Proposal', 'KS-SYS', '4D19C283EABD431C814E571507A62D47', '10009', '10', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Create Course By Admin Proposal screen', 'Use Create Course By Admin Proposal', 'KS-SYS', 'B5504669C3F64E6BB27F592E8170635F', '10010', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Create Program By Proposal screen', 'Use Create Program By Proposal', 'KS-SYS', 'C1CE6425F7194C68B89C927AE526E1B8', '10011', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the browse program catalog screen', 'Use Browse Program Screen', 'KS-SYS', 'B35FB91B89BB40A0A0C1625DCF3DE081', '10012', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the "Find Academic Programs" screen', 'Use Find Program Screen', 'KS-SYS', '008B936A81764252BA9A4519825F8FB0', '10013', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the "Find a Program Proposal" screen', 'Use Find Program Proposal Screen', 'KS-SYS', 'BF823AD34C0E486C801EE5A60642C533', '10014', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the "View Core Programs" screen', 'Use View Core Programs Screen', 'KS-SYS', '39C235163D214999A70357ECFFC3E963', '10015', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the "View Credential Programs" screen', 'Use View Credential Programs Screen', 'KS-SYS', '27FD65A4ED874C54A15F254E2530D53B', '10016', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the View Course Set Management Screens', 'Use View Course Set Management Screens', 'KS-SYS', 'E51CA4A558644F5584F8B6C8B339ABBB', '10017', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the LO Category Screens screen', 'Use LO Category Screens', 'KS-SYS', '10AFA3E4624142D789826DFE3F9349B1', '10018', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the dependency analysis screen', 'Use Dependency Analysis Screen', 'KS-SYS', '8EA060B26847443BBB62506BB65D8307', '10019', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows access to the Blanket Approval button on KS Documents.', 'Blanket Approve KS Document', 'KS-SYS', '9746FD3AD65A4DD9B85A2E92780CC3A1', '10020', '4', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Blanket Approval at Route Status R.', 'Blanket Approve 1', 'KS-SYS', 'D6A5B26DDCF14E47A10D5FB705E307EA', '10021', '67', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Blanket Approval at Route Status S.', 'Blanket Approve 2', 'KS-SYS', 'E358B96867D6436ABFEDF0F5DA004597', '10022', '67', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows admins to have the choice of Admin retire or Retire by Proposal', 'Retire Clu Admin Dialog', 'KS-SYS', '63B81024B62840BBB567AEB9B89FC7B5', '10023', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Create Course By Proposal screen', 'Use Create Course By Proposal', 'KS-SYS', 'A81E111228434863B2935C285C384EF8', '10024', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the browse course catalog screen', 'Use Browse Catalog Screen', 'KS-SYS', '8D03DBDB36754CBC85D0AD8503F81022', '10025', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the find course screen', 'Use Find Course Screen', 'KS-SYS', '05FCF73BDBD149BDAC2163ED0E585BB7', '10026', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the find course proposal screen', 'Use Find Course Proposal Screen', 'KS-SYS', 'EBC7F422C6B94C3B9520A907E40B0039', '10027', '10002', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Create Course Proposals with a Route Status of Final.', 'Open Create Course Route Status F', 'KS-SYS', '1D296D97133F4B05AC8C3D9E89F96FE1', '10028', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Create Course Proposals with a Route Status of Cancelled.', 'Open Create Course Route Status X', 'KS-SYS', 'ABF70B00138749BFBC5EBD7E4FF86D18', '10029', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Create Course Proposals with a Route Status of Processed.', 'Open Create Course Route Status P', 'KS-SYS', 'F934059EDA8F434884BF2E5A08CD2025', '10030', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Create Course Admin Proposals with a Route Status of Final.', 'Open Create Course Admin Route Status F', 'KS-SYS', 'DE996CE9039541D290374C82545C1232', '10031', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Create Course Admin Proposals with a Route Status of Cancelled.', 'Open Create Course Admin Route Status X', 'KS-SYS', '77C7A0136D0F4131B0AC31A3B7D1A7AB', '10032', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Create Course Admin Proposals with a Route Status of Processed.', 'Open Create Course Admin Route Status P', 'KS-SYS', 'A727CA70B95A47D292329955C9D42CAE', '10033', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Course Modification Proposals with a Route Status of Final.', 'Open Course Modification Route Status F', 'KS-SYS', '3EA2EC03A4F844BE9B03476F7EA9B9C0', '10034', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Course Modification Proposals with a Route Status of Cancelled.', 'Open Course Modification Route Status X', 'KS-SYS', '0A2128806D0045629CA1FCF7A3D0EECB', '10035', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Course Modification Proposals with a Route Status of Processed.', 'Open Course Modification Route Status P', 'KS-SYS', '88CA4D28AA24470DB075C5A3424F264C', '10036', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Course Modification Admin Proposals with a Route Status of Final.', 'Open Course Modification Admin Route Status F', 'KS-SYS', '025063328EB54494AEF70075FE68D6CB', '10037', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Course Modification Admin Proposals with a Route Status of Cancelled.', 'Open Course Modification Admin Route Status X', 'KS-SYS', 'CC1A893F62B8467E89251F8D609BEB82', '10038', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Course Modification Admin Proposals with a Route Status of Processed.', 'Open Course Modification Admin Route Status P', 'KS-SYS', '672174C1193F4DD28C145E8D38DDF90F', '10039', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Program Creation Proposals with a Route Status of Final.', 'Open Program Creation Route Status F', 'KS-SYS', 'E26EB905306F4ED7B9091796CD3A3774', '10040', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Program Creation Proposals with a Route Status of Cancelled.', 'Open Program Creation Route Status X', 'KS-SYS', 'EC0DA085DB704BDEA1C9DA22DF346B5B', '10041', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Program Creation Proposals with a Route Status of Processed.', 'Open Program Creation Route Status P', 'KS-SYS', 'B184FB7EF191491BBFFB7C041B59A99F', '10042', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Program Modification Proposals with a Route Status of Final.', 'Open Program Modification Route Status F', 'KS-SYS', '9AB3571470A642DC85CB8B8BCB206522', '10043', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Program Modification Proposals with a Route Status of Cancelled.', 'Open Program Modification Route Status X', 'KS-SYS', '114E200336714ACFA10D08577B91685E', '10044', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Program Modification Proposals with a Route Status of Processed.', 'Open Program Modification Route Status P', 'KS-SYS', 'DEE118747B16457E94928812A221D7E4', '10045', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Course Retirement Proposals with a Route Status of Final.', 'Open Course Retirement Route Status F', 'KS-SYS', '67DA8E8C7E5E4C8D8270A8687F30D366', '10046', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Course Retirement Proposals with a Route Status of Cancelled.', 'Open Course Retirement Route Status X', 'KS-SYS', 'B402D72B9DB14BA09CFCBA890FF73AEF', '10047', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open Course Retirement Proposals with a Route Status of Processed.', 'Open Course Retirement Route Status P', 'KS-SYS', '6DDB0A7AD7CF426A9E52792AC774254B', '10048', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow user to retire course by proposal', 'Retire Course By Proposal', 'KS-SYS', '0C29276C87DC4A15A1C7BFC02FAC035C', '10049', '10', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow user to create course proposal', 'Create Course By Proposal', 'KS-SYS', 'E523C30B34854AE886E7C34E6E3434D0', '10050', '10', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow user to create a modify course proposal', 'Modify Course By Proposal', 'KS-SYS', '49A25854FD6843A182839E66B8DF2A69', '10051', '10', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users with responsibility on workflow document to open document', 'Open Document', 'KS-CM', '1F2D39C078104066B66C62B3AB5DD1E7', '10052', '10004', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Comment on Document', 'KS-CM', 'B29BD9E4FA894685BD36E30AC50DE02D', '10053', '61', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Edit Document', 'KS-CM', 'C6DF560BD66E4624BBAB741308981FA1', '10054', '62', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Add Collaborator with FYI', 'KS-SYS', '805880BAD13C4D20B80A2C1075B9762D', '10055', '10003', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Add Collaborator with Approve', 'KS-SYS', '335B2AE05108470E8CEE2CB0665922FB', '10056', '10003', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Open Create Course Route Status R', 'Open Create Course Route Status R', 'KS-SYS', 'D2F07FCB2D51FFC9E040760A4A45207E', '10057', '10001', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Open Modify Course Route Status R', 'Open Modify Course Route Status R', 'KS-SYS', 'D2F07FCB2D55FFC9E040760A4A45207E', '10058', '10001', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Open Course Retirement Route Status R', 'Open Course Retirement Route Status R', 'KS-SYS', 'D2F07FCB2D59FFC9E040760A4A45207E', '10059', '10001', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows creation and modification of agendas via the agenda editor', 'Maintain KRMS Agenda', 'KS-SYS', 'D2F07FCB2D6FFFC9E040760A4A45207E', '10060', '10005', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Open Views for Course Offering', 'Open Views for Course Offering', 'KS-ENR', 'D2F07FCB2E7EFFC9E040760A4A45207E', '10061', '122', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Edit Views for Course Offering', 'Edit Views for Course Offering', 'KS-ENR', 'D2F07FCB2E7FFFC9E040760A4A45207E', '10062', '123', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Open Views for  Manage Registration Groups', 'Open Views for Manage Registration Groups', 'KS-ENR', 'D2F07FCB2E80FFC9E040760A4A45207E', '10063', '122', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Edit Views for  Manage Registration Groups', 'Edit Views for Manage Registration Groups', 'KS-ENR', 'D2F07FCB2E81FFC9E040760A4A45207E', '10064', '123', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Open Views for Create Course Offering', 'Open Views for Create Course Offering', 'KS-ENR', 'D2F07FCB2E82FFC9E040760A4A45207E', '10065', '122', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Edit Views for Create Course Offering', 'Edit Views for Create Course Offering', 'KS-ENR', 'D2F07FCB2E83FFC9E040760A4A45207E', '10066', '123', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Edit Views for Edit Course Offering', 'Edit Views for KS-CourseOfferingEditWrapper-EditMaintenanceView', 'KS-ENR', 'D2F07FCB2E84FFC9E040760A4A45207E', '10067', '16', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Open documents for Open Course Offering', 'Open Views for KS-CourseOfferingEditWrapper-EditMaintenanceView', 'KS-ENR', 'D2F07FCB2E85FFC9E040760A4A45207E', '10068', '40', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Edit Views for Edit Activity Offering', 'Edit Views for ActivityOffering-MaintenanceView', 'KS-ENR', 'D2F07FCB2E86FFC9E040760A4A45207E', '10069', '16', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows the user to Open documents for Open Activity Offering', 'Open Views for ActivityOffering-MaintenanceView', 'KS-ENR', 'D2F07FCB2E87FFC9E040760A4A45207E', '10070', '40', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Document Operation screen.', 'Use Document Operation Screen', 'KR-WKFLW', '5B4F09744944EF33E0404F8189D84F24', '140', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Java Security Management screen.', 'Use Java Security Management Screen', 'KR-BUS', '5B4F09744945EF33E0404F8189D84F24', '141', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Message Queue screen.', 'Use Message Queue Screen', 'KR-BUS', '5B4F09744946EF33E0404F8189D84F24', '142', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Service Registry screen.', 'Use Service Registry Screen', 'KR-BUS', '5B4F09744947EF33E0404F8189D84F24', '143', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Thread Pool screen.', 'Use Thread Pool Screen', 'KR-BUS', '5B4F09744948EF33E0404F8189D84F24', '144', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Quartz Queue screen.', 'Use Quartz Queue Screen', 'KR-BUS', '5B4F09744949EF33E0404F8189D84F24', '145', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows a user to receive ad hoc requests for RICE Documents.', 'Ad Hoc Review RICE Document', 'KR-SYS', '5B4F0974494AEF33E0404F8189D84F24', '146', '9', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to open RICE Documents via the Super search option in Document Search and take Administrative workflow actions on them (such as approving the document, approving individual requests, or sending the document to a specified route node).', 'Administer Routing RICE Document', 'KR-SYS', '5B4F0974494BEF33E0404F8189D84F24', '147', '3', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows access to the Blanket Approval button on RICE Documents.', 'Blanket Approve RICE Document', 'KR-SYS', '5B4F0974494CEF33E0404F8189D84F24', '148', '4', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes the initiation of RICE Documents.', 'Initiate RICE Document', 'KR-SYS', '5B4F0974494DEF33E0404F8189D84F24', '149', '10', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to modify the information on the Assignees Tab of the Role Document and the Roles section of the Membership Tab on the Person Document for Roles with a Module Code beginning with KR.', 'Assign Role', 'KR-SYS', '5B4F0974494EEF33E0404F8189D84F24', '150', '35', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to modify the information on the Permissions tab of the Role Document for roles with a module code beginning with KR.', 'Grant Permission', 'KR-SYS', '5B4F0974494FEF33E0404F8189D84F24', '151', '36', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to modify the information on the Responsibility tab of the Role Document for roles with a Module Code that begins with KR.', 'Grant Responsibility', 'KR-SYS', '5B4F09744950EF33E0404F8189D84F24', '152', '37', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to modify the information on the Assignees Tab of the Group Document and the Group section of the Membership Tab on the Person Document for groups with namespaces beginning with KR.', 'Populate Group', 'KR-SYS', '5B4F09744953EF33E0404F8189D84F24', '155', '38', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to copy RICE Documents.', 'Copy RICE Document', 'KR-SYS', '5B4F09744954EF33E0404F8189D84F24', '156', '2', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access Kuali RICE inquiries.', 'Inquire Into RICE Records', 'KR-SYS', '5B4F09744959EF33E0404F8189D84F24', '161', '24', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access Kuali RICE lookups.', 'Look Up RICE Records', 'KR-SYS', '5B4F0974495AEF33E0404F8189D84F24', '162', '23', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes to initiate and edit the Parameter document for parameters with a module code beginning with KR.', 'Maintain System Parameter', 'KR-SYS', '5B4F0974495BEF33E0404F8189D84F24', '163', '34', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access and run Batch Jobs associated with KR modules via the Schedule link.', 'Modify Batch Job', 'KR-SYS', '5B4F0974495CEF33E0404F8189D84F24', '164', '32', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to open RICE Documents.', 'Open RICE Document', 'KR-SYS', '5B4F0974495DEF33E0404F8189D84F24', '165', '40', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access all RICE screens.', 'Use all RICE Screen', 'KR-SYS', '5B4F0974495EEF33E0404F8189D84F24', '166', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to cancel a document prior to it being submitted for routing.', 'Cancel Document', 'KUALI', '5B4F0974495FEF33E0404F8189D84F24', '167', '14', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to submit a document for routing.', 'Route Document', 'KUALI', '5B4F09744960EF33E0404F8189D84F24', '168', '5', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to take the Approve action on documents routed to them.', 'Take Requested Approve Action', 'KUALI', '5B4F09744962EF33E0404F8189D84F24', '170', '8', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to take the FYI action on documents routed to them.', 'Take Requested FYI Action', 'KUALI', '5B4F09744964EF33E0404F8189D84F24', '172', '8', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to take the Acknowledge action on documents routed to them.', 'Take Requested Acknowledge Action', 'KUALI', '5B4F09744965EF33E0404F8189D84F24', '173', '8', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to login to the Kuali portal.', 'Log In Kuali Portal', 'KUALI', '5B4F09744966EF33E0404F8189D84F24', '174', '1', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to edit Kuali documents that are in ENROUTE status.', 'Edit Kuali ENROUTE Document Node Name PreRoute', 'KUALI', '5B4F0974496CEF33E0404F8189D84F24', '180', '16', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to edit Kuali documents that are in ENROUTE status.', 'Edit Kuali ENROUTE Document Route Status Code R', 'KUALI', '5B4F0974496DEF33E0404F8189D84F24', '181', '16', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to view the entire Tax Identification Number on the Payee ACH document and Inquiry.', 'Full Unmask Tax Identification Number Payee ACH Document', 'KR-SYS', '5B4F0974496FEF33E0404F8189D84F24', '183', '27', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Administer Routing for Document', 'KS-SYS', '5A4F0974494BEAA3E0404F8189D84F24', '2000', '3', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Opens Access to all users and for all route states so we can set different perms for different states.  See other KS-SYS perms.', 'Blanket Approve Allow All', 'KS-SYS', '5B4F0974494CEF33E04AAF8189D84F24', '2100', '4', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows Save Document KS LUM Document', 'Save Document', 'KS-SYS', '5B4F0974494DEF33E0404F8189D8AA24', '2200', '15', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Ad Hoc Review Document', 'KS-SYS', '5BRF0974494DEF33E0404F8189D8AA24', '2300', '9', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Initiate Document', 'KS-SYS', '5B4F0974494BEF33E0404XX189D8AA24', '2400', '10', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Assign Role', 'KS-SYS', '5B4F097X494BEF33E0404XX189D8AA24', '2500', '35', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Users who can add notes and attachments to any document answering to the Kuali Document parent document type.', 'Add Note / Attachment Kuali Document', 'KUALI', '606763510FC396D3E0404F8189D857A2', '259', '45', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Grant Permission', 'KS-SYS', '5B4F09XX494BEF33E0404XX189D8AA24', '2600', '36', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to view notes and attachments on documents answering to the KualiDocument parent document type.', 'View Note / Attachment Kuali Document', 'KUALI', '606763510FC696D3E0404F8189D857A2', '261', '46', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to delete notes and attachments created by any user on documents answering to the RICE Document parent document type.', 'Delete Note / Attachment Kuali Document', 'KR-SYS', '606763510FCD96D3E0404F8189D857A2', '264', '47', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Use Screen XML Ingester Screen', 'KR-WKFLW', '606763510FF296D3E0404F8189D857A2', '265', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Grant Responsibility', 'KS-SYS', '5B4F09744XXBEF33E0404XX189D8AA24', '2700', '37', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Populate Group', 'KS-SYS', '5B4F0B74494BEF33XX404XX189D8AA24', '2800', '38', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Administer Pessimistic Locking', 'Administer Pessimistic Locking', 'KR-NS', '611BE30E404E5818E0404F8189D801C2', '289', '1', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Users who can save RICE documents', 'Save RICE Document', 'KR-SYS', '5B4F09744961EF33E0404F8189D84F24', '290', '15', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to upload files to documents.', 'Upload to Document', 'KS-CM', '5B4F0974884BEF33E0R04RX189D8AA24', '2910', '63', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to add adhoc reviewers to documents.', 'Add Adhoc Reviewer', 'KS-CM', '5B4F0974884BEF33E8884RX189D8AA24', '2911', '64', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to access other users action lists via the Help Desk Action List Login.', 'View Other Action List', 'KR-WKFLW', '641E580969E31B49E0404F8189D86190', '298', '1', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Users who can perform a document search with no criteria or result limits.', 'Unrestricted Document Search', 'KR-WKFLW', '641E580969E51B49E0404F8189D86190', '299', '1', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to view the entire Tax Identification Number on the Person document and inquiry.', 'Full Unmask Tax Identification Number Person Document', 'KR-SYS', '6314CC58CF58B7B5E0404F8189D84439', '306', '27', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Users who can modify entity records in Kuali Identity Management.', 'Modify Entity', 'KR-IDM', '638DD46953F9BCD5E0404F8189D86240', '307', '1', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to withdraw a KS workflow document', 'Withdraw Document', 'KS-SYS', '5B4F0974494BEF33E0R04EX189D8AAAA', '3101', '65', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Remove Reviewers', 'KS-SYS', '5B4F0974494BCF33E0R04EX189E8AAAA', '3102', '66', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows specified users to BA when the route status is Enroute', 'Blanket Approve when Routed', 'KS-SYS', '5B4F0974494BCF33E0R04EX189E8AAAB', '3103', '67', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows specified users to BA when the route status is Saved', 'Blanket Approve when Saved', 'KS-SYS', '5B4F0974494BCF33E0R04EX189E8AABA', '3104', '67', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Edit Course Title', 'edit CourseInfo transcriptTitle', 'KS-CM', '5B4F0974494BEF33E0R04EX189D8AT24', '3105', '120', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Edit Course Unit Contents Owner', 'edit CourseInfo unitsContentOwner', 'KS-CM', '5B4F0974494BEF33E0R04EX189D8AT26', '3106', '120', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Edit Course Description', 'edit CourseInfo descr', 'KS-CM', '5B4F0974494BEF33E0R04EX189D8AT27', '3107', '120', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Edit Course Duration', 'edit CourseInfo duration', 'KS-CM', '5B4F0974494BEF3AE0R04EX189D8AT27', '3108', '120', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'edit Organization orgInfo', 'KS-CM', '5B4F0974494BEF33E0R04EXQAZD8AT24', '3109', '120', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'edit Organization orgOrgRelationInfo', 'KS-CM', '5B4F0974494BEF33E0R04EXWSXD8AT26', '3110', '120', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'edit Organization OrgPositionRestrictionInfo', 'KS-CM', '5B4F0974494BEF33E0R04EX189EDCT27', '3111', '120', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'edit Organization orgPersonRelationInfo', 'KS-CM', '5B4F0974494BEF3ARFV04EX189D8AT27', '3112', '120', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Blanket Approve', 'KS-CM', '5B4F0974494BCF33E0R04EX189E8ABAA', '3113', '67', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows use of the cache administration screen', 'Use Cache Adminstration Screen', 'KR-SYS', 'D2F07FCB2CF5FFC9E040760A4A45207E', '3114', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows user to create a new Term maintainence document', 'Create Term Maintenance Document', 'KR-NS', '0dbce939-4f22-4e9b-a4bb-1615c0f411a2', '3115', '42', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows user to create a new Context maintainence document', 'Create Context Maintenance Document', 'KR-NS', 'cefeed6d-b5e2-40aa-9034-137db317b532', '3116', '42', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows user to create a new TermSpecification maintainence document', 'Create TermSpecification Maintenance Document', 'KR-NS', '02bd9acd-48d9-4fec-acbd-6a441c5ea8c2', '3117', '42', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Recall Document', 'KR-WKFLW', 'D2F07FCB2D32FFC9E040760A4A45207E', '3118', '137', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to send Complete ad hoc requests for Kuali Documents', 'Send Complete Request Kuali Document', 'KR-SYS', 'D2F07FCB2D37FFC9E040760A4A45207E', '3119', '49', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to send FYI ad hoc requests for Kuali Documents', 'Send FYI Request Kuali Document', 'KR-SYS', '662384B381B967A1E0404F8189D868A6', '332', '49', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to send Acknowledge ad hoc requests for Kuali Documents', 'Send Acknowledge Request Kuali Document', 'KR-SYS', '662384B381BD67A1E0404F8189D868A6', '333', '49', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to send Approve ad hoc requests for Kuali Documents', 'Send Approve Request Kuali Document', 'KR-SYS', '662384B381C167A1E0404F8189D868A6', '334', '49', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows a user to override entity privacy preferences', 'Override Entity Privacy Preferences', 'KR-IDM', '5B4F09744953EF33E0404F8189D84F29', '378', '1', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Rule Template lookup.', 'Look Up Rule Template', 'KR-WKFLW', '04C7706012554535AF8DC48DC6CC331C', '701', '23', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Stylesheet lookup.', 'Look Up Stylesheet', 'KR-WKFLW', '471FF4B19A4648D4B84194530AE22158', '702', '23', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the eDocLite lookup.', 'Look Up eDocLite', 'KR-WKFLW', 'E6875070DC5B4FD59193F7445C33E7AB', '703', '23', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Rule Attribute lookup.', 'Look Up Rule Attribute', 'KR-WKFLW', '28CE0127B8A14AB4BFD39920C5398A69', '707', '23', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Pessimistic Lock lookup.', 'Look Up Records', 'KR-NS', '77C20443ECBF4F9CA4E0D1E03CADCF15', '714', '23', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Parameter Component lookup.', 'Look Up Parameter Component', 'KR-NS', '45F0E8E1B9784756A3C0582980912991', '719', '23', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Namespace lookup.', 'Look Up Namespace', 'KR-NS', 'FFF2C6639C6041F1B148AA9901C8A1F7', '720', '23', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Parameter Type lookup.', 'Look Up Parameter Type', 'KR-NS', 'B1BD57EE64274E62AC9425C7FF185A44', '721', '23', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Rule Template inquiry.', 'Inquire Into Rule Template', 'KR-WKFLW', '606763510FD196D3E0404F8189D857A2', '801', '24', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Stylesheet inquiry.', 'Inquire Into Stylesheet', 'KR-WKFLW', '606763510FD496D3E0404F8189D857A2', '802', '24', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the eDocLite inquiry.', 'Inquire Into eDocLite', 'KR-WKFLW', '606763510FD796D3E0404F8189D857A2', '803', '24', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Rule Attribute inquiry.', 'Inquire Into Rule Attribute', 'KR-WKFLW', '606763510FDA96D3E0404F8189D857A2', '807', '24', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Pessimistic Lock inquiry.', 'Inquire Into Pessimistic', 'KR-NS', '606763510FE196D3E0404F8189D857A2', '814', '24', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Parameter Component inquiry.', 'Inquire Into Parameter Component', 'KR-NS', '606763510FE496D3E0404F8189D857A2', '819', '24', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Namespace inquiry.', 'Inquire Into Namespace', 'KR-NS', '606763510FE796D3E0404F8189D857A2', '820', '24', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allow users to access the Parameter Type inquiry.', 'Inquire Into Parameter Type', 'KR-NS', '606763510FEA96D3E0404F8189D857A2', '821', '24', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to modify the information on the Assignees Tab of the Group Document and the Group section of the Membership Tab on the Person Document for groups with the KUALI namespace.', 'Populate Group KUALI Namespace', 'KR-SYS', '5B4F09744953EF33E0404F8189D84F25', '833', '38', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to modify the information on the Assignees Tab of the Role Document and the Roles section of the Membership Tab on the Person Document for Roles with the KUALI namespace.', 'Assign Role KUALI Namespace', 'KR-SYS', '5B4F09744953EF33E0404F8189D84F26', '834', '35', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to modify the information on the Permissions tab of the Role Document for roles with the KUALI namespace.', 'Grant Permission KUALI Namespace', 'KR-SYS', '5B4F09744953EF33E0404F8189D84F27', '835', '36', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to modify the information on the Responsibility tab of the Role Document for roles with the KUALI namespace.', 'Grant Responsibility KUALI Namespace', 'KR-SYS', '5B4F09744953EF33E0404F8189D84F28', '836', '37', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Allows users to access the Configuration Viewer screen', 'Use Configuration Viewer Screen', 'KR-BUS', '97469975-D110-9A65-5EE5-F21FD1BEB5B2', '840', '29', 1)
/
INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', null, 'Add Message to Route Log', 'KUALI', '65677409-89e4-11df-98b1-1300c9ee50c1', '841', '51', 1)
/
