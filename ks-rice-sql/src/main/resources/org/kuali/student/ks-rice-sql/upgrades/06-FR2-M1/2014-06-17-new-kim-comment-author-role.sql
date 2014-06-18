-- new type to link in comment author derived role type service
INSERT INTO KRIM_TYP_T (ACTV_IND, KIM_TYP_ID, NM, NMSPC_CD, OBJ_ID, SRVC_NM, VER_NBR)
  VALUES ('Y', CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'Derived Role: KS Comment Author Role Type', 'KS-SYS', SYS_GUID(), 'ksCommentAuthorDerivedRoleTypeService', 1)
/

-- role record for new comment author role
INSERT INTO KRIM_ROLE_T (ACTV_IND, DESC_TXT, KIM_TYP_ID, LAST_UPDT_DT, NMSPC_CD, OBJ_ID, ROLE_ID, ROLE_NM, VER_NBR)
  VALUES ('Y', 'This role is to identify the author of a comment given a valid comment ID as a qualifier.', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM = 'Derived Role: KS Comment Author Role Type' AND NMSPC_CD = 'KS-SYS'), TO_DATE('2014-05-30', 'YYYY-MM-DD'), 'KS-SYS', SYS_GUID(), CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'Comment Author', 1)
/
