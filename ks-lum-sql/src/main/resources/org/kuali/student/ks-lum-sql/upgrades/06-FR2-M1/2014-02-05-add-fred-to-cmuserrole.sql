-- Adding fred to 'Kuali Student CM User' role (KSCM-1452) for now. It needs more analysis which role should fred belong.

INSERT INTO KRIM_ROLE_MBR_T (ACTV_FRM_DT,ACTV_TO_DT,LAST_UPDT_DT,MBR_ID,MBR_TYP_CD,OBJ_ID,ROLE_ID,ROLE_MBR_ID,VER_NBR) VALUES (null,null,sysdate,'fred','P',SYS_GUID(),(select role_id from KRIM_ROLE_T where ROLE_NM='Kuali Student CM User'),KRIM_ROLE_MBR_ID_S.NEXTVAL,1)
/