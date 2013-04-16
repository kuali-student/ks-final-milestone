-- We were missing linking data in the KRIM_TYP_ATTR_T for the  'Adhoc Permissions Type' kim type. 

insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (KRIM_ATTR_DEFN_ID_S.NEXTVAL, sys_guid(), 1, 'a', 
   (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Adhoc Permissions Type' AND NMSPC_CD='KS-SYS'),
   (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='documentTypeName' AND NMSPC_CD='KR-WKFLW'), 'Y')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)
values (KRIM_ATTR_DEFN_ID_S.NEXTVAL, sys_guid(), 1, 'b', 
   (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Adhoc Permissions Type' AND NMSPC_CD='KS-SYS'),
   (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='dataId' AND NMSPC_CD='KS-SYS'), 'Y')
/
