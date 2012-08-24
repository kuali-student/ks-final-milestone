-- Create a new KS USe Screen type with our use screen attribute
INSERT INTO KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD) VALUES (KRIM_TYP_ID_S.NEXTVAL,  sys_guid(), 1, 'KS Use Screen', null, 'Y', 'KS-SYS')
/
-- Remove the screen Component attribute from the default permission (which is messing up the rice screens)
DELETE FROM KRIM_TYP_ATTR_T WHERE KIM_TYP_ID = (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Default' AND NMSPC_CD='KUALI') AND KIM_ATTR_DEFN_ID = (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='screenComponent' AND NMSPC_CD='KS-SYS')
/
-- Attach the screen component attribute to our new screen type 
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR) VALUES ('Y',(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='screenComponent' AND NMSPC_CD='KS-SYS'),KRIM_TYP_ATTR_ID_S.NEXTVAL,(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS Use Screen' AND NMSPC_CD='KS-SYS'),sys_guid(),'a',1)
/
--Update the screen template to use our new type
UPDATE KRIM_PERM_TMPL_T SET KIM_TYP_ID=(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS Use Screen' AND NMSPC_CD='KS-SYS') WHERE NM='Use Screen' AND NMSPC_CD='KS-SYS'
/