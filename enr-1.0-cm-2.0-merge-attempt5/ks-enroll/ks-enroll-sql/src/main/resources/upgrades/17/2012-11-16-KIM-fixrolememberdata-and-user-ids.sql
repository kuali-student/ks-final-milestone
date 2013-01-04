-- Fix the kim principals to lowercase since the AuthN code checks against LOWER() always.
update KRIM_PRNCPL_T set KRIM_PRNCPL_T.PRNCPL_NM = lower (KRIM_PRNCPL_T.PRNCPL_NM)
/

--Update the role-mem table so non existent user ids are replaced
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.edwardb' WHERE MBR_ID='dev1'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.elhananb' WHERE MBR_ID='dev2'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.elizabeths' WHERE MBR_ID='edna'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.emanuilt' WHERE MBR_ID='eric'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.emerd' WHERE MBR_ID='fran'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.emikot' WHERE MBR_ID='fred'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.ericac' WHERE MBR_ID='guest'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.erikl' WHERE MBR_ID='test1'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.eriny' WHERE MBR_ID='test2'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.ernestt' WHERE MBR_ID='testuser1'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.ever' WHERE MBR_ID='testuser2'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.farhana' WHERE MBR_ID='testuser3'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.fooklingc' WHERE MBR_ID='testuser4'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.frankc' WHERE MBR_ID='testuser5'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.frederickg' WHERE MBR_ID='testuser6'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.fumis' WHERE MBR_ID='testuser7'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.gaila' WHERE MBR_ID='testuser8'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.gailw' WHERE MBR_ID='testuser9'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.genevan' WHERE MBR_ID='user1'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.giovannid' WHERE MBR_ID='user2'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.glenisem' WHERE MBR_ID='user3'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.gordonn' WHERE MBR_ID='user4'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.gregoryc' WHERE MBR_ID='user5'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.guangz' WHERE MBR_ID='user6'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.guol' WHERE MBR_ID='user7'
/
UPDATE KRIM_ROLE_MBR_T SET MBR_ID='a.hailingl' WHERE MBR_ID='user8'
/
