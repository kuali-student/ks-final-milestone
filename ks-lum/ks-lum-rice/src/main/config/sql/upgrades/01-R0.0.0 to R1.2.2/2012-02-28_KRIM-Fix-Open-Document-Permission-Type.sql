--Create a new document type that has the correct attribute definitions and the default permission type service
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y', KRIM_TYP_ID_S.NEXTVAL, 'Document Type and Route Node','KS-SYS',sys_guid(),null,1)
/
--Link existing attribute definitions to the new type
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR) 
  VALUES ('Y',(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='documentTypeName' AND NMSPC_CD='KR-WKFLW'),KRIM_TYP_ATTR_ID_S.NEXTVAL,KRIM_TYP_ID_S.CURRVAL,sys_guid(),'a',1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR) 
  VALUES ('Y',(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='routeStatusCode' AND NMSPC_CD='KR-WKFLW'),KRIM_TYP_ATTR_ID_S.NEXTVAL,KRIM_TYP_ID_S.CURRVAL,sys_guid(),'a',1)
/
--Set the KS Open Document Permission to point to our new type
UPDATE KRIM_PERM_TMPL_T SET KIM_TYP_ID = KRIM_TYP_ID_S.CURRVAL WHERE NM='Open Document' AND NMSPC_CD='KS-SYS'
/
--Update the existing permission attribute data
UPDATE
    KRIM_PERM_ATTR_DATA_T
SET
    KIM_TYP_ID =
    (
        SELECT
            KIM_TYP_ID
        FROM
            KRIM_TYP_T
        WHERE
            NM='Document Type and Route Node'
        AND KRIM_TYP_T.NMSPC_CD='KS-SYS'
    )
WHERE
    ATTR_DATA_ID IN
    (
        SELECT
            pd.ATTR_DATA_ID
        FROM
            KRIM_PERM_ATTR_DATA_T pd,
            KRIM_PERM_T p,
            KRIM_PERM_TMPL_T pt
        WHERE
            pt.NM='Open Document'
        AND pt.NMSPC_CD='KS-SYS'
        AND p.PERM_TMPL_ID=pt.PERM_TMPL_ID
        AND pd.PERM_ID=p.PERM_ID
    )
/
