--NOTE: THIS FILE IS TEMPORARILY LOCATED IN THE UPGRADE FOLDER, NEED TO MOVE BACK TO KS-LUM-RCE

-- Cleanup inserts from 2011-07-06-Final-Status-Viewable-Role-and-Permissions.sql 

--Remove unused KIM Type (This is not used anywhere)
DELETE FROM KRIM_TYP_T WHERE KIM_TYP_ID='3000'
/

--Alter permission template for permission "Open Document Final"
UPDATE KRIM_PERM_T
SET PERM_TMPL_ID='60'
WHERE PERM_ID='3200'
/

--Remove duplicate KS-SYS "Open Document" inserted (we will use existing one with PERM_TMPL_ID=60 defined in kim.sql)  
DELETE FROM KRIM_PERM_TMPL_T WHERE PERM_TMPL_ID='4000'
/