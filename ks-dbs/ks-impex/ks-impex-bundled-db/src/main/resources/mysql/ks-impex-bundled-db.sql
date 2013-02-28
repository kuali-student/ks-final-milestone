
# -----------------------------------------------------------------------
# KRAD_MSG_T
# -----------------------------------------------------------------------
drop table if exists KRAD_MSG_T
/

CREATE TABLE KRAD_MSG_T
(
      NMSPC_CD VARCHAR(20)
        , CMPNT_CD VARCHAR(100)
        , MSG_KEY VARCHAR(100)
        , LOC VARCHAR(255)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , MSG_DESC VARCHAR(255)
        , TXT VARCHAR(4000)
    
    , CONSTRAINT KRAD_MSG_TP1 PRIMARY KEY(NMSPC_CD,CMPNT_CD,MSG_KEY,LOC)

    , CONSTRAINT KRAD_MSG_TC2 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRCR_CMPNT_SET_T
# -----------------------------------------------------------------------
drop table if exists KRCR_CMPNT_SET_T
/

CREATE TABLE KRCR_CMPNT_SET_T
(
      CMPNT_SET_ID VARCHAR(40)
        , LAST_UPDT_TS DATETIME NOT NULL
        , CHKSM VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRCR_CMPNT_SET_TP1 PRIMARY KEY(CMPNT_SET_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRCR_CMPNT_T
# -----------------------------------------------------------------------
drop table if exists KRCR_CMPNT_T
/

CREATE TABLE KRCR_CMPNT_T
(
      NMSPC_CD VARCHAR(20)
        , CMPNT_CD VARCHAR(100)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(255)
        , ACTV_IND CHAR(1) default 'Y' NOT NULL
    
    , CONSTRAINT KRCR_CMPNT_TP1 PRIMARY KEY(NMSPC_CD,CMPNT_CD)

    , CONSTRAINT KRNS_PARM_DTL_TYP_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRCR_DRVD_CMPNT_T
# -----------------------------------------------------------------------
drop table if exists KRCR_DRVD_CMPNT_T
/

CREATE TABLE KRCR_DRVD_CMPNT_T
(
      NMSPC_CD VARCHAR(20)
        , CMPNT_CD VARCHAR(100)
        , NM VARCHAR(255)
        , CMPNT_SET_ID VARCHAR(40) NOT NULL
    
    , CONSTRAINT KRCR_DRVD_CMPNT_TP1 PRIMARY KEY(NMSPC_CD,CMPNT_CD)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRCR_NMSPC_T
# -----------------------------------------------------------------------
drop table if exists KRCR_NMSPC_T
/

CREATE TABLE KRCR_NMSPC_T
(
      NMSPC_CD VARCHAR(20)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , ACTV_IND CHAR(1) default 'Y' NOT NULL
        , APPL_ID VARCHAR(255)
    
    , CONSTRAINT KRCR_NMSPC_TP1 PRIMARY KEY(NMSPC_CD)

    , CONSTRAINT KRNS_NMSPC_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRCR_PARM_T
# -----------------------------------------------------------------------
drop table if exists KRCR_PARM_T
/

CREATE TABLE KRCR_PARM_T
(
      NMSPC_CD VARCHAR(20)
        , CMPNT_CD VARCHAR(100)
        , PARM_NM VARCHAR(255)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PARM_TYP_CD VARCHAR(5) NOT NULL
        , VAL VARCHAR(4000)
        , PARM_DESC_TXT VARCHAR(4000)
        , EVAL_OPRTR_CD VARCHAR(1)
        , APPL_ID VARCHAR(255) default 'KUALI'
    
    , CONSTRAINT KRCR_PARM_TP1 PRIMARY KEY(NMSPC_CD,CMPNT_CD,PARM_NM,APPL_ID)

    , CONSTRAINT KRNS_PARM_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRCR_PARM_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRCR_PARM_TYP_T
/

CREATE TABLE KRCR_PARM_TYP_T
(
      PARM_TYP_CD VARCHAR(5)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , ACTV_IND CHAR(1) default 'Y' NOT NULL
    
    , CONSTRAINT KRCR_PARM_TYP_TP1 PRIMARY KEY(PARM_TYP_CD)

    , CONSTRAINT KRNS_PARM_TYP_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRCR_STYLE_T
# -----------------------------------------------------------------------
drop table if exists KRCR_STYLE_T
/

CREATE TABLE KRCR_STYLE_T
(
      STYLE_ID VARCHAR(40)
        , NM VARCHAR(200) NOT NULL
        , XML LONGTEXT NOT NULL
        , ACTV_IND DECIMAL(1) NOT NULL
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KRCR_STYLE_TP1 PRIMARY KEY(STYLE_ID)

    , CONSTRAINT KRCR_STYLE_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_CHNL_PRODCR_T
# -----------------------------------------------------------------------
drop table if exists KREN_CHNL_PRODCR_T
/

CREATE TABLE KREN_CHNL_PRODCR_T
(
      CHNL_ID DECIMAL(8)
        , PRODCR_ID DECIMAL(8)
    
    , CONSTRAINT KREN_CHNL_PRODCR_TP1 PRIMARY KEY(CHNL_ID,PRODCR_ID)


    , INDEX KREN_CHNL_PRODCR_TI1 (CHNL_ID)
    , INDEX KREN_CHNL_PRODCR_TI2 (PRODCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_CHNL_SUBSCRP_T
# -----------------------------------------------------------------------
drop table if exists KREN_CHNL_SUBSCRP_T
/

CREATE TABLE KREN_CHNL_SUBSCRP_T
(
      CHNL_SUBSCRP_ID DECIMAL(8)
        , CHNL_ID DECIMAL(8) NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(8)
    
    , CONSTRAINT KREN_CHNL_SUBSCRP_TP1 PRIMARY KEY(CHNL_SUBSCRP_ID)

    , CONSTRAINT KREN_CHNL_SUBSCRP_TC0 UNIQUE (CHNL_ID, PRNCPL_ID)

    , INDEX KREN_CHNL_SUBSCRP_TI1 (CHNL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_CHNL_T
# -----------------------------------------------------------------------
drop table if exists KREN_CHNL_T
/

CREATE TABLE KREN_CHNL_T
(
      CHNL_ID DECIMAL(8)
        , NM VARCHAR(200) NOT NULL
        , DESC_TXT VARCHAR(4000) NOT NULL
        , SUBSCRB_IND CHAR(1) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KREN_CHNL_TP1 PRIMARY KEY(CHNL_ID)

    , CONSTRAINT KREN_CHNL_TC0 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_CNTNT_TYP_T
# -----------------------------------------------------------------------
drop table if exists KREN_CNTNT_TYP_T
/

CREATE TABLE KREN_CNTNT_TYP_T
(
      CNTNT_TYP_ID DECIMAL(8)
        , NM VARCHAR(200) NOT NULL
        , CUR_IND CHAR(1) default 'T' NOT NULL
        , CNTNT_TYP_VER_NBR DECIMAL(8) default 0 NOT NULL
        , DESC_TXT VARCHAR(1000) NOT NULL
        , NMSPC_CD VARCHAR(1000) NOT NULL
        , XSD LONGTEXT NOT NULL
        , XSL LONGTEXT NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KREN_CNTNT_TYP_TP1 PRIMARY KEY(CNTNT_TYP_ID)

    , CONSTRAINT KREN_CNTNT_TYP_TC0 UNIQUE (NM, CNTNT_TYP_VER_NBR)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_MSG_DELIV_T
# -----------------------------------------------------------------------
drop table if exists KREN_MSG_DELIV_T
/

CREATE TABLE KREN_MSG_DELIV_T
(
      MSG_DELIV_ID DECIMAL(8)
        , MSG_ID DECIMAL(8) NOT NULL
        , TYP_NM VARCHAR(200) NOT NULL
        , SYS_ID VARCHAR(300)
        , STAT_CD VARCHAR(15) NOT NULL
        , PROC_CNT DECIMAL(4) default 0 NOT NULL
        , LOCKD_DTTM DATETIME
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KREN_MSG_DELIV_TP1 PRIMARY KEY(MSG_DELIV_ID)

    , CONSTRAINT KREN_MSG_DELIV_TC0 UNIQUE (MSG_ID, TYP_NM)

    , INDEX KREN_MSG_DELIV_TI1 (MSG_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_MSG_T
# -----------------------------------------------------------------------
drop table if exists KREN_MSG_T
/

CREATE TABLE KREN_MSG_T
(
      MSG_ID DECIMAL(8)
        , ORGN_ID VARCHAR(128)
        , DELIV_TYP VARCHAR(500) NOT NULL
        , CRTE_DTTM DATETIME NOT NULL
        , TTL VARCHAR(255)
        , CHNL VARCHAR(300) NOT NULL
        , PRODCR VARCHAR(300)
        , CNTNT LONGTEXT NOT NULL
        , CNTNT_TYP VARCHAR(128)
        , URL VARCHAR(512)
        , RECIP_ID VARCHAR(300) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KREN_MSG_TP1 PRIMARY KEY(MSG_ID)

    , CONSTRAINT KREN_MSG_TC0 UNIQUE (ORGN_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_NTFCTN_MSG_DELIV_T
# -----------------------------------------------------------------------
drop table if exists KREN_NTFCTN_MSG_DELIV_T
/

CREATE TABLE KREN_NTFCTN_MSG_DELIV_T
(
      NTFCTN_MSG_DELIV_ID DECIMAL(8)
        , NTFCTN_ID DECIMAL(8) NOT NULL
        , RECIP_ID VARCHAR(40) NOT NULL
        , STAT_CD VARCHAR(15) NOT NULL
        , SYS_ID VARCHAR(300)
        , LOCKD_DTTM DATETIME
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KREN_NTFCTN_MSG_DELIV_TP1 PRIMARY KEY(NTFCTN_MSG_DELIV_ID)

    , CONSTRAINT KREN_NTFCTN_MSG_DELIV_TC0 UNIQUE (NTFCTN_ID, RECIP_ID)

    , INDEX KREN_MSG_DELIVSI1 (NTFCTN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_NTFCTN_T
# -----------------------------------------------------------------------
drop table if exists KREN_NTFCTN_T
/

CREATE TABLE KREN_NTFCTN_T
(
      NTFCTN_ID DECIMAL(8)
        , DELIV_TYP VARCHAR(3) NOT NULL
        , CRTE_DTTM DATETIME NOT NULL
        , SND_DTTM DATETIME
        , AUTO_RMV_DTTM DATETIME
        , PRIO_ID DECIMAL(8) NOT NULL
        , TTL VARCHAR(255)
        , CNTNT LONGTEXT NOT NULL
        , CNTNT_TYP_ID DECIMAL(8) NOT NULL
        , CHNL_ID DECIMAL(8) NOT NULL
        , PRODCR_ID DECIMAL(8) NOT NULL
        , PROCESSING_FLAG VARCHAR(15) NOT NULL
        , LOCKD_DTTM DATETIME
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KREN_NTFCTN_TP1 PRIMARY KEY(NTFCTN_ID)


    , INDEX KREN_NTFCTN_I1 (CNTNT_TYP_ID)
    , INDEX KREN_NTFCTN_I2 (PRIO_ID)
    , INDEX KREN_NTFCTN_I3 (PRODCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_PRIO_T
# -----------------------------------------------------------------------
drop table if exists KREN_PRIO_T
/

CREATE TABLE KREN_PRIO_T
(
      PRIO_ID DECIMAL(8)
        , NM VARCHAR(40) NOT NULL
        , DESC_TXT VARCHAR(500) NOT NULL
        , PRIO_ORD DECIMAL(4) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KREN_PRIO_TP1 PRIMARY KEY(PRIO_ID)

    , CONSTRAINT KREN_PRIO_TC0 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_PRODCR_T
# -----------------------------------------------------------------------
drop table if exists KREN_PRODCR_T
/

CREATE TABLE KREN_PRODCR_T
(
      PRODCR_ID DECIMAL(8)
        , NM VARCHAR(200) NOT NULL
        , DESC_TXT VARCHAR(1000) NOT NULL
        , CNTCT_INFO VARCHAR(1000) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KREN_PRODCR_TP1 PRIMARY KEY(PRODCR_ID)

    , CONSTRAINT KREN_PRODCR_TC0 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_RECIP_DELIV_T
# -----------------------------------------------------------------------
drop table if exists KREN_RECIP_DELIV_T
/

CREATE TABLE KREN_RECIP_DELIV_T
(
      RECIP_DELIV_ID DECIMAL(8)
        , RECIP_ID VARCHAR(40) NOT NULL
        , CHNL VARCHAR(300) NOT NULL
        , NM VARCHAR(200) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KREN_RECIP_DELIV_TP1 PRIMARY KEY(RECIP_DELIV_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_RECIP_LIST_T
# -----------------------------------------------------------------------
drop table if exists KREN_RECIP_LIST_T
/

CREATE TABLE KREN_RECIP_LIST_T
(
      RECIP_LIST_ID DECIMAL(8)
        , CHNL_ID DECIMAL(8) NOT NULL
        , RECIP_TYP_CD VARCHAR(10) NOT NULL
        , RECIP_ID VARCHAR(40) NOT NULL
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(8)
    
    , CONSTRAINT KREN_RECIP_LIST_TP1 PRIMARY KEY(RECIP_LIST_ID)

    , CONSTRAINT KREN_RECIP_LIST_TC0 UNIQUE (CHNL_ID, RECIP_TYP_CD, RECIP_ID)

    , INDEX KREN_RECIP_LIST_TI1 (CHNL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_RECIP_PREFS_T
# -----------------------------------------------------------------------
drop table if exists KREN_RECIP_PREFS_T
/

CREATE TABLE KREN_RECIP_PREFS_T
(
      RECIP_PREFS_ID DECIMAL(8)
        , RECIP_ID VARCHAR(40) NOT NULL
        , PROP VARCHAR(200) NOT NULL
        , VAL VARCHAR(1000) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KREN_RECIP_PREFS_TP1 PRIMARY KEY(RECIP_PREFS_ID)

    , CONSTRAINT KREN_RECIP_PREFS_TC0 UNIQUE (RECIP_ID, PROP)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_RECIP_T
# -----------------------------------------------------------------------
drop table if exists KREN_RECIP_T
/

CREATE TABLE KREN_RECIP_T
(
      RECIP_ID DECIMAL(8)
        , NTFCTN_ID DECIMAL(8) NOT NULL
        , RECIP_TYP_CD VARCHAR(10) NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(8)
    
    , CONSTRAINT KREN_RECIP_TP1 PRIMARY KEY(RECIP_ID)

    , CONSTRAINT KREN_RECIP_TC0 UNIQUE (NTFCTN_ID, RECIP_TYP_CD, PRNCPL_ID)

    , INDEX KREN_RECIP_TI1 (NTFCTN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_RVWER_T
# -----------------------------------------------------------------------
drop table if exists KREN_RVWER_T
/

CREATE TABLE KREN_RVWER_T
(
      RVWER_ID DECIMAL(8)
        , CHNL_ID DECIMAL(8) NOT NULL
        , TYP VARCHAR(10) NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KREN_RVWER_TP1 PRIMARY KEY(RVWER_ID)

    , CONSTRAINT KREN_RVWER_TC0 UNIQUE (CHNL_ID, TYP, PRNCPL_ID)

    , INDEX KREN_RVWER_TI1 (CHNL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREN_SNDR_T
# -----------------------------------------------------------------------
drop table if exists KREN_SNDR_T
/

CREATE TABLE KREN_SNDR_T
(
      SNDR_ID DECIMAL(8)
        , NTFCTN_ID DECIMAL(8) NOT NULL
        , NM VARCHAR(200) NOT NULL
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(8)
    
    , CONSTRAINT KREN_SNDR_TP1 PRIMARY KEY(SNDR_ID)

    , CONSTRAINT KREN_SNDR_TC0 UNIQUE (NTFCTN_ID, NM)

    , INDEX KREN_SNDR_TI1 (NTFCTN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_ACTN_ITM_T
# -----------------------------------------------------------------------
drop table if exists KREW_ACTN_ITM_T
/

CREATE TABLE KREW_ACTN_ITM_T
(
      ACTN_ITM_ID VARCHAR(40)
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , ASND_DT DATETIME NOT NULL
        , RQST_CD CHAR(1) NOT NULL
        , ACTN_RQST_ID VARCHAR(40) NOT NULL
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , ROLE_NM VARCHAR(2000)
        , DLGN_PRNCPL_ID VARCHAR(40)
        , DOC_HDR_TTL VARCHAR(255)
        , DOC_TYP_LBL VARCHAR(128) NOT NULL
        , DOC_HDLR_URL VARCHAR(255) NOT NULL
        , DOC_TYP_NM VARCHAR(64) NOT NULL
        , RSP_ID VARCHAR(40) NOT NULL
        , DLGN_TYP VARCHAR(1)
        , VER_NBR DECIMAL(8) default 0
        , DTYPE VARCHAR(50)
        , GRP_ID VARCHAR(40)
        , DLGN_GRP_ID VARCHAR(40)
        , RQST_LBL VARCHAR(255)
    
    , CONSTRAINT KREW_ACTN_ITM_TP1 PRIMARY KEY(ACTN_ITM_ID)


    , INDEX KREW_ACTN_ITM_T1 (PRNCPL_ID)
    , INDEX KREW_ACTN_ITM_TI2 (DOC_HDR_ID)
    , INDEX KREW_ACTN_ITM_TI3 (ACTN_RQST_ID)
    , INDEX KREW_ACTN_ITM_TI5 (PRNCPL_ID, DLGN_TYP, DOC_HDR_ID)
    , INDEX KREW_ACTN_ITM_TI6 (DLGN_TYP, DLGN_PRNCPL_ID, DLGN_GRP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_ACTN_RQST_T
# -----------------------------------------------------------------------
drop table if exists KREW_ACTN_RQST_T
/

CREATE TABLE KREW_ACTN_RQST_T
(
      ACTN_RQST_ID VARCHAR(40)
        , PARNT_ID VARCHAR(40)
        , ACTN_RQST_CD CHAR(1) NOT NULL
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , RULE_ID VARCHAR(40)
        , STAT_CD CHAR(1) NOT NULL
        , RSP_ID VARCHAR(40) NOT NULL
        , PRNCPL_ID VARCHAR(40)
        , ROLE_NM VARCHAR(2000)
        , QUAL_ROLE_NM VARCHAR(2000)
        , QUAL_ROLE_NM_LBL_TXT VARCHAR(2000)
        , RECIP_TYP_CD CHAR(1)
        , PRIO_NBR DECIMAL(8) NOT NULL
        , RTE_TYP_NM VARCHAR(255)
        , RTE_LVL_NBR DECIMAL(8) NOT NULL
        , RTE_NODE_INSTN_ID VARCHAR(40)
        , ACTN_TKN_ID VARCHAR(40)
        , DOC_VER_NBR DECIMAL(8) NOT NULL
        , CRTE_DT DATETIME NOT NULL
        , RSP_DESC_TXT VARCHAR(200)
        , FRC_ACTN DECIMAL(1) default 0
        , ACTN_RQST_ANNOTN_TXT VARCHAR(2000)
        , DLGN_TYP CHAR(1)
        , APPR_PLCY CHAR(1)
        , CUR_IND DECIMAL(1) default 1
        , VER_NBR DECIMAL(8) default 0
        , GRP_ID VARCHAR(40)
        , RQST_LBL VARCHAR(255)
    
    , CONSTRAINT KREW_ACTN_RQST_TP1 PRIMARY KEY(ACTN_RQST_ID)


    , INDEX KREW_ACTN_RQST_T11 (DOC_HDR_ID)
    , INDEX KREW_ACTN_RQST_T12 (PRNCPL_ID)
    , INDEX KREW_ACTN_RQST_T13 (ACTN_TKN_ID)
    , INDEX KREW_ACTN_RQST_T14 (PARNT_ID)
    , INDEX KREW_ACTN_RQST_T15 (RSP_ID)
    , INDEX KREW_ACTN_RQST_T16 (STAT_CD, RSP_ID)
    , INDEX KREW_ACTN_RQST_T17 (RTE_NODE_INSTN_ID)
    , INDEX KREW_ACTN_RQST_T19 (STAT_CD, DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_ACTN_TKN_T
# -----------------------------------------------------------------------
drop table if exists KREW_ACTN_TKN_T
/

CREATE TABLE KREW_ACTN_TKN_T
(
      ACTN_TKN_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , DLGTR_PRNCPL_ID VARCHAR(40)
        , ACTN_CD CHAR(1) NOT NULL
        , ACTN_DT DATETIME NOT NULL
        , DOC_VER_NBR DECIMAL(8) NOT NULL
        , ANNOTN VARCHAR(2000)
        , CUR_IND DECIMAL(1) default 1
        , VER_NBR DECIMAL(8) default 0
        , DLGTR_GRP_ID VARCHAR(40)
    
    , CONSTRAINT KREW_ACTN_TKN_TP1 PRIMARY KEY(ACTN_TKN_ID)


    , INDEX KREW_ACTN_TKN_TI1 (DOC_HDR_ID, PRNCPL_ID)
    , INDEX KREW_ACTN_TKN_TI2 (DOC_HDR_ID, PRNCPL_ID, ACTN_CD)
    , INDEX KREW_ACTN_TKN_TI3 (PRNCPL_ID)
    , INDEX KREW_ACTN_TKN_TI4 (DLGTR_PRNCPL_ID)
    , INDEX KREW_ACTN_TKN_TI5 (DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_APP_DOC_STAT_TRAN_T
# -----------------------------------------------------------------------
drop table if exists KREW_APP_DOC_STAT_TRAN_T
/

CREATE TABLE KREW_APP_DOC_STAT_TRAN_T
(
      APP_DOC_STAT_TRAN_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(40)
        , APP_DOC_STAT_FROM VARCHAR(64)
        , APP_DOC_STAT_TO VARCHAR(64)
        , STAT_TRANS_DATE DATETIME
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_APP_DOC_STAT_TRAN_TP1 PRIMARY KEY(APP_DOC_STAT_TRAN_ID)

    , CONSTRAINT KREW_APP_DOC_STAT_TRAN_TC0 UNIQUE (OBJ_ID)

    , INDEX KREW_APP_DOC_STAT_TI1 (DOC_HDR_ID, STAT_TRANS_DATE)
    , INDEX KREW_APP_DOC_STAT_TI2 (DOC_HDR_ID, APP_DOC_STAT_FROM)
    , INDEX KREW_APP_DOC_STAT_TI3 (DOC_HDR_ID, APP_DOC_STAT_TO)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_ATTR_DEFN_T
# -----------------------------------------------------------------------
drop table if exists KREW_ATTR_DEFN_T
/

CREATE TABLE KREW_ATTR_DEFN_T
(
      ATTR_DEFN_ID VARCHAR(40)
        , NM VARCHAR(100) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , LBL VARCHAR(40)
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , CMPNT_NM VARCHAR(100)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , DESC_TXT VARCHAR(40)
    
    , CONSTRAINT KREW_ATTR_DEFN_TP1 PRIMARY KEY(ATTR_DEFN_ID)

    , CONSTRAINT KREW_ATTR_DEFN_TC1 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_ATT_T
# -----------------------------------------------------------------------
drop table if exists KREW_ATT_T
/

CREATE TABLE KREW_ATT_T
(
      ATTACHMENT_ID VARCHAR(40)
        , NTE_ID VARCHAR(40)
        , FILE_NM VARCHAR(255) NOT NULL
        , FILE_LOC VARCHAR(255) NOT NULL
        , MIME_TYP VARCHAR(255) NOT NULL
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_ATT_TP1 PRIMARY KEY(ATTACHMENT_ID)


    , INDEX KREW_ATT_TI1 (NTE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DLGN_RSP_T
# -----------------------------------------------------------------------
drop table if exists KREW_DLGN_RSP_T
/

CREATE TABLE KREW_DLGN_RSP_T
(
      DLGN_RULE_ID VARCHAR(40)
        , RSP_ID VARCHAR(40) NOT NULL
        , DLGN_RULE_BASE_VAL_ID VARCHAR(40) NOT NULL
        , DLGN_TYP VARCHAR(20) NOT NULL
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_DLGN_RSP_TP1 PRIMARY KEY(DLGN_RULE_ID)

    , CONSTRAINT KREW_DLGN_RSP_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_HDR_CNTNT_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_HDR_CNTNT_T
/

CREATE TABLE KREW_DOC_HDR_CNTNT_T
(
      DOC_HDR_ID VARCHAR(40)
        , DOC_CNTNT_TXT LONGTEXT
    
    , CONSTRAINT KREW_DOC_HDR_CNTNT_TP1 PRIMARY KEY(DOC_HDR_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_HDR_EXT_DT_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_HDR_EXT_DT_T
/

CREATE TABLE KREW_DOC_HDR_EXT_DT_T
(
      DOC_HDR_EXT_DT_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , KEY_CD VARCHAR(256) NOT NULL
        , VAL DATETIME
    
    , CONSTRAINT KREW_DOC_HDR_EXT_DT_TP1 PRIMARY KEY(DOC_HDR_EXT_DT_ID)


    , INDEX KREW_DOC_HDR_EXT_DT_TI1 (KEY_CD, VAL)
    , INDEX KREW_DOC_HDR_EXT_DT_TI2 (DOC_HDR_ID)
    , INDEX KREW_DOC_HDR_EXT_DT_TI3 (VAL)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_HDR_EXT_FLT_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_HDR_EXT_FLT_T
/

CREATE TABLE KREW_DOC_HDR_EXT_FLT_T
(
      DOC_HDR_EXT_FLT_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , KEY_CD VARCHAR(256) NOT NULL
        , VAL DECIMAL(30,15)
    
    , CONSTRAINT KREW_DOC_HDR_EXT_FLT_TP1 PRIMARY KEY(DOC_HDR_EXT_FLT_ID)


    , INDEX KREW_DOC_HDR_EXT_FLT_TI1 (KEY_CD, VAL)
    , INDEX KREW_DOC_HDR_EXT_FLT_TI2 (DOC_HDR_ID)
    , INDEX KREW_DOC_HDR_EXT_FLT_TI3 (VAL)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_HDR_EXT_LONG_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_HDR_EXT_LONG_T
/

CREATE TABLE KREW_DOC_HDR_EXT_LONG_T
(
      DOC_HDR_EXT_LONG_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , KEY_CD VARCHAR(256) NOT NULL
        , VAL DECIMAL(22)
    
    , CONSTRAINT KREW_DOC_HDR_EXT_LONG_TP1 PRIMARY KEY(DOC_HDR_EXT_LONG_ID)


    , INDEX KREW_DOC_HDR_EXT_LONG_TI1 (KEY_CD, VAL)
    , INDEX KREW_DOC_HDR_EXT_LONG_TI2 (DOC_HDR_ID)
    , INDEX KREW_DOC_HDR_EXT_LONG_TI3 (VAL)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_HDR_EXT_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_HDR_EXT_T
/

CREATE TABLE KREW_DOC_HDR_EXT_T
(
      DOC_HDR_EXT_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , KEY_CD VARCHAR(256) NOT NULL
        , VAL VARCHAR(2000)
    
    , CONSTRAINT KREW_DOC_HDR_EXT_TP1 PRIMARY KEY(DOC_HDR_EXT_ID)


    , INDEX KREW_DOC_HDR_EXT_TI1 (KEY_CD, VAL)
    , INDEX KREW_DOC_HDR_EXT_TI2 (DOC_HDR_ID)
    , INDEX KREW_DOC_HDR_EXT_TI3 (VAL)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_HDR_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_HDR_T
/

CREATE TABLE KREW_DOC_HDR_T
(
      DOC_HDR_ID VARCHAR(40)
        , DOC_TYP_ID VARCHAR(40)
        , DOC_HDR_STAT_CD CHAR(1) NOT NULL
        , RTE_LVL DECIMAL(8) NOT NULL
        , STAT_MDFN_DT DATETIME NOT NULL
        , CRTE_DT DATETIME NOT NULL
        , APRV_DT DATETIME
        , FNL_DT DATETIME
        , RTE_STAT_MDFN_DT DATETIME
        , TTL VARCHAR(255)
        , APP_DOC_ID VARCHAR(255)
        , DOC_VER_NBR DECIMAL(8) NOT NULL
        , INITR_PRNCPL_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0
        , RTE_PRNCPL_ID VARCHAR(40)
        , DTYPE VARCHAR(50)
        , OBJ_ID VARCHAR(36) NOT NULL
        , APP_DOC_STAT VARCHAR(64)
        , APP_DOC_STAT_MDFN_DT DATETIME
    
    , CONSTRAINT KREW_DOC_HDR_TP1 PRIMARY KEY(DOC_HDR_ID)

    , CONSTRAINT KREW_DOC_HDR_TC0 UNIQUE (OBJ_ID)

    , INDEX KREW_DOC_HDR_T10 (APP_DOC_STAT)
    , INDEX KREW_DOC_HDR_T12 (APP_DOC_STAT_MDFN_DT)
    , INDEX KREW_DOC_HDR_TI1 (DOC_TYP_ID)
    , INDEX KREW_DOC_HDR_TI2 (INITR_PRNCPL_ID)
    , INDEX KREW_DOC_HDR_TI3 (DOC_HDR_STAT_CD)
    , INDEX KREW_DOC_HDR_TI4 (TTL)
    , INDEX KREW_DOC_HDR_TI5 (CRTE_DT)
    , INDEX KREW_DOC_HDR_TI6 (RTE_STAT_MDFN_DT)
    , INDEX KREW_DOC_HDR_TI7 (APRV_DT)
    , INDEX KREW_DOC_HDR_TI8 (FNL_DT)
    , INDEX KREW_DOC_HDR_TI9 (APP_DOC_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_LNK_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_LNK_T
/

CREATE TABLE KREW_DOC_LNK_T
(
      DOC_LNK_ID VARCHAR(40)
        , ORGN_DOC_ID VARCHAR(40) NOT NULL
        , DEST_DOC_ID VARCHAR(40) NOT NULL
    
    , CONSTRAINT KREW_DOC_LNK_TP1 PRIMARY KEY(DOC_LNK_ID)


    , INDEX KREW_DOC_LNK_TI1 (ORGN_DOC_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_NTE_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_NTE_T
/

CREATE TABLE KREW_DOC_NTE_T
(
      DOC_NTE_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , AUTH_PRNCPL_ID VARCHAR(40) NOT NULL
        , CRT_DT DATETIME NOT NULL
        , TXT VARCHAR(4000)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_DOC_NTE_TP1 PRIMARY KEY(DOC_NTE_ID)


    , INDEX KREW_DOC_NTE_TI1 (DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_TYP_APP_DOC_STAT_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_TYP_APP_DOC_STAT_T
/

CREATE TABLE KREW_DOC_TYP_APP_DOC_STAT_T
(
      DOC_TYP_ID VARCHAR(40)
        , DOC_STAT_NM VARCHAR(64)
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
        , CAT_NM VARCHAR(64)
        , SEQ_NO DECIMAL(5)
    
    , CONSTRAINT KREW_DOC_TYP_APP_DOC_STAT_TP1 PRIMARY KEY(DOC_TYP_ID,DOC_STAT_NM)

    , CONSTRAINT KREW_DOC_TYP_APP_DOC_STAT_TC0 UNIQUE (OBJ_ID)

    , INDEX KREW_DOC_TYP_APP_DOC_STAT_T1 (DOC_TYP_ID)
    , INDEX KREW_DOC_TYP_APP_DOC_STAT_T2 (DOC_TYP_ID, CAT_NM)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_TYP_APP_STAT_CAT_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_TYP_APP_STAT_CAT_T
/

CREATE TABLE KREW_DOC_TYP_APP_STAT_CAT_T
(
      DOC_TYP_ID VARCHAR(40)
        , CAT_NM VARCHAR(64)
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_DOC_TYP_APP_STAT_CAT_TP1 PRIMARY KEY(DOC_TYP_ID,CAT_NM)

    , CONSTRAINT KREW_DOC_TYP_APP_STAT_CAT_TC1 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_TYP_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_TYP_ATTR_T
/

CREATE TABLE KREW_DOC_TYP_ATTR_T
(
      DOC_TYP_ATTRIB_ID VARCHAR(40)
        , DOC_TYP_ID VARCHAR(40) NOT NULL
        , RULE_ATTR_ID VARCHAR(40) NOT NULL
        , ORD_INDX DECIMAL(4) default 0
    
    , CONSTRAINT KREW_DOC_TYP_ATTR_TP1 PRIMARY KEY(DOC_TYP_ATTRIB_ID)


    , INDEX KREW_DOC_TYP_ATTR_TI1 (DOC_TYP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_TYP_PLCY_RELN_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_TYP_PLCY_RELN_T
/

CREATE TABLE KREW_DOC_TYP_PLCY_RELN_T
(
      DOC_TYP_ID VARCHAR(40)
        , DOC_PLCY_NM VARCHAR(255)
        , PLCY_NM DECIMAL(1) NOT NULL
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
        , PLCY_VAL VARCHAR(1024)
    
    , CONSTRAINT KREW_DOC_TYP_PLCY_RELN_TP1 PRIMARY KEY(DOC_TYP_ID,DOC_PLCY_NM)

    , CONSTRAINT KREW_DOC_TYP_PLCY_RELN_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_TYP_PROC_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_TYP_PROC_T
/

CREATE TABLE KREW_DOC_TYP_PROC_T
(
      DOC_TYP_PROC_ID VARCHAR(40)
        , DOC_TYP_ID VARCHAR(40) NOT NULL
        , INIT_RTE_NODE_ID VARCHAR(40)
        , NM VARCHAR(255) NOT NULL
        , INIT_IND DECIMAL(1) default 0 NOT NULL
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_DOC_TYP_PROC_TP1 PRIMARY KEY(DOC_TYP_PROC_ID)


    , INDEX KREW_DOC_TYP_PROC_TI1 (DOC_TYP_ID)
    , INDEX KREW_DOC_TYP_PROC_TI2 (INIT_RTE_NODE_ID)
    , INDEX KREW_DOC_TYP_PROC_TI3 (NM)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_DOC_TYP_T
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_TYP_T
/

CREATE TABLE KREW_DOC_TYP_T
(
      DOC_TYP_ID VARCHAR(40)
        , PARNT_ID VARCHAR(40)
        , DOC_TYP_NM VARCHAR(64)
        , DOC_TYP_VER_NBR DECIMAL(10) default 0
        , ACTV_IND DECIMAL(1)
        , CUR_IND DECIMAL(1)
        , LBL VARCHAR(128) NOT NULL
        , PREV_DOC_TYP_VER_NBR VARCHAR(40)
        , DOC_TYP_DESC VARCHAR(4000)
        , DOC_HDLR_URL VARCHAR(255)
        , POST_PRCSR VARCHAR(255)
        , JNDI_URL VARCHAR(255)
        , BLNKT_APPR_PLCY VARCHAR(10)
        , ADV_DOC_SRCH_URL VARCHAR(255)
        , RTE_VER_NBR VARCHAR(2) default '1'
        , NOTIFY_ADDR VARCHAR(255)
        , APPL_ID VARCHAR(255)
        , EMAIL_XSL VARCHAR(255)
        , SEC_XML LONGTEXT
        , VER_NBR DECIMAL(8) default 0
        , BLNKT_APPR_GRP_ID VARCHAR(40)
        , RPT_GRP_ID VARCHAR(40)
        , GRP_ID VARCHAR(40)
        , HELP_DEF_URL VARCHAR(4000)
        , OBJ_ID VARCHAR(36) NOT NULL
        , DOC_SEARCH_HELP_URL VARCHAR(4000)
        , DOC_HDR_ID VARCHAR(40)
    
    , CONSTRAINT KREW_DOC_TYP_TP1 PRIMARY KEY(DOC_TYP_ID)

    , CONSTRAINT KREW_DOC_TYP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KREW_DOC_TYP_TI1 UNIQUE (DOC_TYP_NM, DOC_TYP_VER_NBR)

    , INDEX KREW_DOC_TYP_TI2 (PARNT_ID)
    , INDEX KREW_DOC_TYP_TI3 (DOC_TYP_ID, PARNT_ID)
    , INDEX KREW_DOC_TYP_TI4 (PREV_DOC_TYP_VER_NBR)
    , INDEX KREW_DOC_TYP_TI5 (CUR_IND)
    , INDEX KREW_DOC_TYP_TI6 (DOC_TYP_NM)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_EDL_ASSCTN_T
# -----------------------------------------------------------------------
drop table if exists KREW_EDL_ASSCTN_T
/

CREATE TABLE KREW_EDL_ASSCTN_T
(
      EDOCLT_ASSOC_ID DECIMAL(19)
        , DOC_TYP_NM VARCHAR(64) NOT NULL
        , EDL_DEF_NM VARCHAR(200)
        , STYLE_NM VARCHAR(200)
        , ACTV_IND DECIMAL(1) NOT NULL
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_EDL_ASSCTN_TP1 PRIMARY KEY(EDOCLT_ASSOC_ID)

    , CONSTRAINT KREW_EDL_ASSCTN_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_EDL_DEF_T
# -----------------------------------------------------------------------
drop table if exists KREW_EDL_DEF_T
/

CREATE TABLE KREW_EDL_DEF_T
(
      EDOCLT_DEF_ID DECIMAL(19)
        , NM VARCHAR(200) NOT NULL
        , XML LONGTEXT NOT NULL
        , ACTV_IND DECIMAL(1) NOT NULL
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_EDL_DEF_TP1 PRIMARY KEY(EDOCLT_DEF_ID)

    , CONSTRAINT KREW_EDL_DEF_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_EDL_DMP_T
# -----------------------------------------------------------------------
drop table if exists KREW_EDL_DMP_T
/

CREATE TABLE KREW_EDL_DMP_T
(
      DOC_HDR_ID VARCHAR(40)
        , DOC_TYP_NM VARCHAR(64) NOT NULL
        , DOC_HDR_STAT_CD CHAR(1) NOT NULL
        , DOC_HDR_MDFN_DT DATETIME NOT NULL
        , DOC_HDR_CRTE_DT DATETIME NOT NULL
        , DOC_HDR_TTL VARCHAR(255)
        , DOC_HDR_INITR_PRNCPL_ID VARCHAR(40) NOT NULL
        , CRNT_NODE_NM VARCHAR(30) NOT NULL
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_EDL_DMP_TP1 PRIMARY KEY(DOC_HDR_ID)


    , INDEX KREW_EDL_DMP_TI1 (DOC_TYP_NM, DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_EDL_FLD_DMP_T
# -----------------------------------------------------------------------
drop table if exists KREW_EDL_FLD_DMP_T
/

CREATE TABLE KREW_EDL_FLD_DMP_T
(
      EDL_FIELD_DMP_ID DECIMAL(14)
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , FLD_NM VARCHAR(255) NOT NULL
        , FLD_VAL VARCHAR(4000)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_EDL_FLD_DMP_TP1 PRIMARY KEY(EDL_FIELD_DMP_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_INIT_RTE_NODE_INSTN_T
# -----------------------------------------------------------------------
drop table if exists KREW_INIT_RTE_NODE_INSTN_T
/

CREATE TABLE KREW_INIT_RTE_NODE_INSTN_T
(
      DOC_HDR_ID VARCHAR(40)
        , RTE_NODE_INSTN_ID VARCHAR(40)
    
    , CONSTRAINT KREW_INIT_RTE_NODE_INSTN_TP1 PRIMARY KEY(DOC_HDR_ID,RTE_NODE_INSTN_ID)


    , INDEX KREW_INIT_RTE_NODE_INSTN_TI1 (DOC_HDR_ID)
    , INDEX KREW_INIT_RTE_NODE_INSTN_TI2 (RTE_NODE_INSTN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_OUT_BOX_ITM_T
# -----------------------------------------------------------------------
drop table if exists KREW_OUT_BOX_ITM_T
/

CREATE TABLE KREW_OUT_BOX_ITM_T
(
      ACTN_ITM_ID VARCHAR(40)
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , ASND_DT DATETIME NOT NULL
        , RQST_CD CHAR(1) NOT NULL
        , ACTN_RQST_ID VARCHAR(40) NOT NULL
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , ROLE_NM VARCHAR(2000)
        , DLGN_PRNCPL_ID VARCHAR(40)
        , DOC_HDR_TTL VARCHAR(255)
        , DOC_TYP_LBL VARCHAR(128) NOT NULL
        , DOC_HDLR_URL VARCHAR(255) NOT NULL
        , DOC_TYP_NM VARCHAR(64) NOT NULL
        , RSP_ID VARCHAR(40) NOT NULL
        , DLGN_TYP VARCHAR(1)
        , VER_NBR DECIMAL(8) default 0
        , GRP_ID VARCHAR(40)
        , DLGN_GRP_ID VARCHAR(40)
        , RQST_LBL VARCHAR(255)
    
    , CONSTRAINT KREW_OUT_BOX_ITM_TP1 PRIMARY KEY(ACTN_ITM_ID)


    , INDEX KREW_OUT_BOX_ITM_TI1 (PRNCPL_ID)
    , INDEX KREW_OUT_BOX_ITM_TI2 (DOC_HDR_ID)
    , INDEX KREW_OUT_BOX_ITM_TI3 (ACTN_RQST_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_PPL_FLW_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KREW_PPL_FLW_ATTR_T
/

CREATE TABLE KREW_PPL_FLW_ATTR_T
(
      PPL_FLW_ATTR_ID VARCHAR(40)
        , PPL_FLW_ID VARCHAR(40) NOT NULL
        , ATTR_DEFN_ID VARCHAR(40) NOT NULL
        , ATTR_VAL VARCHAR(400)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KREW_PPL_FLW_ATTR_TP1 PRIMARY KEY(PPL_FLW_ATTR_ID)


    , INDEX KREW_PPL_FLW_ATTR_TI1 (PPL_FLW_ID)
    , INDEX KREW_PPL_FLW_ATTR_TI2 (ATTR_DEFN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_PPL_FLW_DLGT_T
# -----------------------------------------------------------------------
drop table if exists KREW_PPL_FLW_DLGT_T
/

CREATE TABLE KREW_PPL_FLW_DLGT_T
(
      PPL_FLW_DLGT_ID VARCHAR(40)
        , PPL_FLW_MBR_ID VARCHAR(40) NOT NULL
        , MBR_ID VARCHAR(40) NOT NULL
        , MBR_TYP_CD VARCHAR(1) NOT NULL
        , DLGN_TYP_CD VARCHAR(1) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , ACTN_RQST_PLCY_CD VARCHAR(1)
        , RSP_ID VARCHAR(40) NOT NULL
    
    , CONSTRAINT KREW_PPL_FLW_DLGT_TP1 PRIMARY KEY(PPL_FLW_DLGT_ID)


    , INDEX KREW_PPL_FLW_DLGT_TI1 (PPL_FLW_MBR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_PPL_FLW_MBR_T
# -----------------------------------------------------------------------
drop table if exists KREW_PPL_FLW_MBR_T
/

CREATE TABLE KREW_PPL_FLW_MBR_T
(
      PPL_FLW_MBR_ID VARCHAR(40)
        , PPL_FLW_ID VARCHAR(40) NOT NULL
        , MBR_TYP_CD VARCHAR(1) NOT NULL
        , MBR_ID VARCHAR(40) NOT NULL
        , PRIO DECIMAL(8)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , ACTN_RQST_PLCY_CD VARCHAR(1)
        , RSP_ID VARCHAR(40) NOT NULL
    
    , CONSTRAINT KREW_PPL_FLW_MBR_TP1 PRIMARY KEY(PPL_FLW_MBR_ID)


    , INDEX KREW_PPL_FLW_MBR_TI1 (PPL_FLW_ID)
    , INDEX KREW_PPL_FLW_MBR_TI2 (PPL_FLW_ID, PRIO)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_PPL_FLW_T
# -----------------------------------------------------------------------
drop table if exists KREW_PPL_FLW_T
/

CREATE TABLE KREW_PPL_FLW_T
(
      PPL_FLW_ID VARCHAR(40)
        , NM VARCHAR(100) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , TYP_ID VARCHAR(40)
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , DESC_TXT VARCHAR(4000)
    
    , CONSTRAINT KREW_PPL_FLW_TP1 PRIMARY KEY(PPL_FLW_ID)

    , CONSTRAINT KREW_PPL_FLW_TC1 UNIQUE (NM, NMSPC_CD)

    , INDEX KREW_PPL_FLW_FK1 (TYP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RTE_BRCH_PROTO_T
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_BRCH_PROTO_T
/

CREATE TABLE KREW_RTE_BRCH_PROTO_T
(
      RTE_BRCH_PROTO_ID VARCHAR(40)
        , BRCH_NM VARCHAR(255) NOT NULL
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_RTE_BRCH_PROTO_TP1 PRIMARY KEY(RTE_BRCH_PROTO_ID)


    , INDEX KREW_RTE_BRCH_PROTO_TI1 (BRCH_NM)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RTE_BRCH_ST_T
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_BRCH_ST_T
/

CREATE TABLE KREW_RTE_BRCH_ST_T
(
      RTE_BRCH_ST_ID VARCHAR(40)
        , RTE_BRCH_ID VARCHAR(40) NOT NULL
        , KEY_CD VARCHAR(255) NOT NULL
        , VAL VARCHAR(2000)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_RTE_BRCH_ST_TP1 PRIMARY KEY(RTE_BRCH_ST_ID)


    , INDEX KREW_RTE_BRCH_ST_TI1 (RTE_BRCH_ID, KEY_CD)
    , INDEX KREW_RTE_BRCH_ST_TI2 (RTE_BRCH_ID)
    , INDEX KREW_RTE_BRCH_ST_TI3 (KEY_CD, VAL)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RTE_BRCH_T
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_BRCH_T
/

CREATE TABLE KREW_RTE_BRCH_T
(
      RTE_BRCH_ID VARCHAR(40)
        , NM VARCHAR(255) NOT NULL
        , PARNT_ID VARCHAR(40)
        , INIT_RTE_NODE_INSTN_ID VARCHAR(40)
        , SPLT_RTE_NODE_INSTN_ID VARCHAR(40)
        , JOIN_RTE_NODE_INSTN_ID VARCHAR(40)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_RTE_BRCH_TP1 PRIMARY KEY(RTE_BRCH_ID)


    , INDEX KREW_RTE_BRCH_TI1 (NM)
    , INDEX KREW_RTE_BRCH_TI2 (PARNT_ID)
    , INDEX KREW_RTE_BRCH_TI3 (INIT_RTE_NODE_INSTN_ID)
    , INDEX KREW_RTE_BRCH_TI4 (SPLT_RTE_NODE_INSTN_ID)
    , INDEX KREW_RTE_BRCH_TI5 (JOIN_RTE_NODE_INSTN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RTE_NODE_CFG_PARM_T
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_NODE_CFG_PARM_T
/

CREATE TABLE KREW_RTE_NODE_CFG_PARM_T
(
      RTE_NODE_CFG_PARM_ID VARCHAR(40)
        , RTE_NODE_ID VARCHAR(40) NOT NULL
        , KEY_CD VARCHAR(255) NOT NULL
        , VAL VARCHAR(4000)
    
    , CONSTRAINT KREW_RTE_NODE_CFG_PARM_TP1 PRIMARY KEY(RTE_NODE_CFG_PARM_ID)


    , INDEX KREW_RTE_NODE_CFG_PARM_TI1 (RTE_NODE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RTE_NODE_INSTN_LNK_T
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_NODE_INSTN_LNK_T
/

CREATE TABLE KREW_RTE_NODE_INSTN_LNK_T
(
      FROM_RTE_NODE_INSTN_ID VARCHAR(40)
        , TO_RTE_NODE_INSTN_ID VARCHAR(40)
    
    , CONSTRAINT KREW_RTE_NODE_INSTN_LNK_TP1 PRIMARY KEY(FROM_RTE_NODE_INSTN_ID,TO_RTE_NODE_INSTN_ID)


    , INDEX KREW_RTE_NODE_INSTN_LNK_TI1 (FROM_RTE_NODE_INSTN_ID)
    , INDEX KREW_RTE_NODE_INSTN_LNK_TI2 (TO_RTE_NODE_INSTN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RTE_NODE_INSTN_ST_T
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_NODE_INSTN_ST_T
/

CREATE TABLE KREW_RTE_NODE_INSTN_ST_T
(
      RTE_NODE_INSTN_ST_ID VARCHAR(40)
        , RTE_NODE_INSTN_ID VARCHAR(40) NOT NULL
        , KEY_CD VARCHAR(255) NOT NULL
        , VAL VARCHAR(2000)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_RTE_NODE_INSTN_ST_TP1 PRIMARY KEY(RTE_NODE_INSTN_ST_ID)


    , INDEX KREW_RTE_NODE_INSTN_ST_TI1 (RTE_NODE_INSTN_ID, KEY_CD)
    , INDEX KREW_RTE_NODE_INSTN_ST_TI2 (RTE_NODE_INSTN_ID)
    , INDEX KREW_RTE_NODE_INSTN_ST_TI3 (KEY_CD, VAL)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RTE_NODE_INSTN_T
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_NODE_INSTN_T
/

CREATE TABLE KREW_RTE_NODE_INSTN_T
(
      RTE_NODE_INSTN_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(40) NOT NULL
        , RTE_NODE_ID VARCHAR(40) NOT NULL
        , BRCH_ID VARCHAR(40)
        , PROC_RTE_NODE_INSTN_ID VARCHAR(40)
        , ACTV_IND DECIMAL(1) default 0 NOT NULL
        , CMPLT_IND DECIMAL(1) default 0 NOT NULL
        , INIT_IND DECIMAL(1) default 0 NOT NULL
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_RTE_NODE_INSTN_TP1 PRIMARY KEY(RTE_NODE_INSTN_ID)


    , INDEX KREW_RTE_NODE_INSTN_TI1 (DOC_HDR_ID, ACTV_IND, CMPLT_IND)
    , INDEX KREW_RTE_NODE_INSTN_TI2 (RTE_NODE_ID)
    , INDEX KREW_RTE_NODE_INSTN_TI3 (BRCH_ID)
    , INDEX KREW_RTE_NODE_INSTN_TI4 (PROC_RTE_NODE_INSTN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RTE_NODE_LNK_T
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_NODE_LNK_T
/

CREATE TABLE KREW_RTE_NODE_LNK_T
(
      FROM_RTE_NODE_ID VARCHAR(40)
        , TO_RTE_NODE_ID VARCHAR(40)
    
    , CONSTRAINT KREW_RTE_NODE_LNK_TP1 PRIMARY KEY(FROM_RTE_NODE_ID,TO_RTE_NODE_ID)


    , INDEX KREW_RTE_NODE_LNK_TI1 (FROM_RTE_NODE_ID)
    , INDEX KREW_RTE_NODE_LNK_TI2 (TO_RTE_NODE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RTE_NODE_T
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_NODE_T
/

CREATE TABLE KREW_RTE_NODE_T
(
      RTE_NODE_ID VARCHAR(40)
        , DOC_TYP_ID VARCHAR(40)
        , NM VARCHAR(255) NOT NULL
        , TYP VARCHAR(255) NOT NULL
        , RTE_MTHD_NM VARCHAR(255)
        , RTE_MTHD_CD VARCHAR(2)
        , FNL_APRVR_IND DECIMAL(1)
        , MNDTRY_RTE_IND DECIMAL(1)
        , ACTVN_TYP VARCHAR(1)
        , BRCH_PROTO_ID VARCHAR(40)
        , VER_NBR DECIMAL(8) default 0
        , CONTENT_FRAGMENT VARCHAR(4000)
        , GRP_ID VARCHAR(40)
        , NEXT_DOC_STAT VARCHAR(64)
    
    , CONSTRAINT KREW_RTE_NODE_TP1 PRIMARY KEY(RTE_NODE_ID)


    , INDEX KREW_RTE_NODE_TI1 (NM, DOC_TYP_ID)
    , INDEX KREW_RTE_NODE_TI2 (DOC_TYP_ID, FNL_APRVR_IND)
    , INDEX KREW_RTE_NODE_TI3 (BRCH_PROTO_ID)
    , INDEX KREW_RTE_NODE_TI4 (DOC_TYP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RULE_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_ATTR_T
/

CREATE TABLE KREW_RULE_ATTR_T
(
      RULE_ATTR_ID VARCHAR(40)
        , NM VARCHAR(255) NOT NULL
        , LBL VARCHAR(2000) NOT NULL
        , RULE_ATTR_TYP_CD VARCHAR(2000) NOT NULL
        , DESC_TXT VARCHAR(2000)
        , CLS_NM VARCHAR(2000)
        , XML LONGTEXT
        , VER_NBR DECIMAL(8) default 0
        , APPL_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_RULE_ATTR_TP1 PRIMARY KEY(RULE_ATTR_ID)

    , CONSTRAINT KREW_RULE_ATTR_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KREW_RULE_ATTR_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RULE_EXPR_T
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_EXPR_T
/

CREATE TABLE KREW_RULE_EXPR_T
(
      RULE_EXPR_ID VARCHAR(40)
        , TYP VARCHAR(256) NOT NULL
        , RULE_EXPR VARCHAR(4000)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_RULE_EXPR_TP1 PRIMARY KEY(RULE_EXPR_ID)

    , CONSTRAINT KREW_RULE_EXPR_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RULE_EXT_T
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_EXT_T
/

CREATE TABLE KREW_RULE_EXT_T
(
      RULE_EXT_ID VARCHAR(40)
        , RULE_TMPL_ATTR_ID VARCHAR(40) NOT NULL
        , RULE_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_RULE_EXT_TP1 PRIMARY KEY(RULE_EXT_ID)


    , INDEX KREW_RULE_EXT_T1 (RULE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RULE_EXT_VAL_T
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_EXT_VAL_T
/

CREATE TABLE KREW_RULE_EXT_VAL_T
(
      RULE_EXT_VAL_ID VARCHAR(40)
        , RULE_EXT_ID VARCHAR(40) NOT NULL
        , VAL VARCHAR(2000) NOT NULL
        , KEY_CD VARCHAR(2000) NOT NULL
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_RULE_EXT_VAL_TP1 PRIMARY KEY(RULE_EXT_VAL_ID)


    , INDEX KREW_RULE_EXT_VAL_T1 (RULE_EXT_ID)
    , INDEX KREW_RULE_EXT_VAL_T2 (RULE_EXT_VAL_ID, KEY_CD)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RULE_RSP_T
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_RSP_T
/

CREATE TABLE KREW_RULE_RSP_T
(
      RULE_RSP_ID VARCHAR(40)
        , RSP_ID VARCHAR(40) NOT NULL
        , RULE_ID VARCHAR(40) NOT NULL
        , PRIO DECIMAL(5)
        , ACTN_RQST_CD VARCHAR(2000)
        , NM VARCHAR(200)
        , TYP VARCHAR(1)
        , APPR_PLCY CHAR(1)
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_RULE_RSP_TP1 PRIMARY KEY(RULE_RSP_ID)

    , CONSTRAINT KREW_RULE_RSP_TC0 UNIQUE (OBJ_ID)

    , INDEX KREW_RULE_RSP_TI1 (RULE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RULE_T
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_T
/

CREATE TABLE KREW_RULE_T
(
      RULE_ID VARCHAR(40)
        , NM VARCHAR(256)
        , RULE_TMPL_ID VARCHAR(40)
        , RULE_EXPR_ID VARCHAR(40)
        , ACTV_IND DECIMAL(1) NOT NULL
        , RULE_BASE_VAL_DESC VARCHAR(2000)
        , FRC_ACTN DECIMAL(1) NOT NULL
        , DOC_TYP_NM VARCHAR(64) NOT NULL
        , DOC_HDR_ID VARCHAR(40)
        , TMPL_RULE_IND DECIMAL(1)
        , FRM_DT DATETIME
        , TO_DT DATETIME
        , DACTVN_DT DATETIME
        , CUR_IND DECIMAL(1) default 0
        , RULE_VER_NBR DECIMAL(8) default 0
        , DLGN_IND DECIMAL(1)
        , PREV_VER_RULE_ID VARCHAR(40)
        , ACTVN_DT DATETIME
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_RULE_TP1 PRIMARY KEY(RULE_ID)

    , CONSTRAINT KREW_RULE_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RULE_TMPL_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_TMPL_ATTR_T
/

CREATE TABLE KREW_RULE_TMPL_ATTR_T
(
      RULE_TMPL_ATTR_ID VARCHAR(40)
        , RULE_TMPL_ID VARCHAR(40) NOT NULL
        , RULE_ATTR_ID VARCHAR(40) NOT NULL
        , REQ_IND DECIMAL(1) NOT NULL
        , ACTV_IND DECIMAL(1) NOT NULL
        , DSPL_ORD DECIMAL(5) NOT NULL
        , DFLT_VAL VARCHAR(2000)
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_RULE_TMPL_ATTR_TP1 PRIMARY KEY(RULE_TMPL_ATTR_ID)

    , CONSTRAINT KREW_RULE_TMPL_ATTR_TC0 UNIQUE (OBJ_ID)

    , INDEX KREW_RULE_TMPL_ATTR_TI1 (RULE_TMPL_ID)
    , INDEX KREW_RULE_TMPL_ATTR_TI2 (RULE_ATTR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RULE_TMPL_OPTN_T
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_TMPL_OPTN_T
/

CREATE TABLE KREW_RULE_TMPL_OPTN_T
(
      RULE_TMPL_OPTN_ID VARCHAR(40)
        , RULE_TMPL_ID VARCHAR(40)
        , KEY_CD VARCHAR(250)
        , VAL VARCHAR(2000)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_RULE_TMPL_OPTN_TP1 PRIMARY KEY(RULE_TMPL_OPTN_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_RULE_TMPL_T
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_TMPL_T
/

CREATE TABLE KREW_RULE_TMPL_T
(
      RULE_TMPL_ID VARCHAR(40)
        , NM VARCHAR(250) NOT NULL
        , RULE_TMPL_DESC VARCHAR(2000)
        , DLGN_RULE_TMPL_ID VARCHAR(40)
        , VER_NBR DECIMAL(8) default 0
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KREW_RULE_TMPL_TP1 PRIMARY KEY(RULE_TMPL_ID)

    , CONSTRAINT KREW_RULE_TMPL_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KREW_RULE_TMPL_TI1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_TYP_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KREW_TYP_ATTR_T
/

CREATE TABLE KREW_TYP_ATTR_T
(
      TYP_ATTR_ID VARCHAR(40)
        , SEQ_NO DECIMAL(5) NOT NULL
        , TYP_ID VARCHAR(40) NOT NULL
        , ATTR_DEFN_ID VARCHAR(255) NOT NULL
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KREW_TYP_ATTR_TP1 PRIMARY KEY(TYP_ATTR_ID)

    , CONSTRAINT KREW_TYP_ATTR_TC1 UNIQUE (TYP_ID, ATTR_DEFN_ID)

    , INDEX KREW_TYP_ATTR_TI1 (ATTR_DEFN_ID)
    , INDEX KREW_TYP_ATTR_TI2 (TYP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_TYP_T
# -----------------------------------------------------------------------
drop table if exists KREW_TYP_T
/

CREATE TABLE KREW_TYP_T
(
      TYP_ID VARCHAR(40)
        , NM VARCHAR(100) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , SRVC_NM VARCHAR(200)
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KREW_TYP_TP1 PRIMARY KEY(TYP_ID)

    , CONSTRAINT KREW_TYP_TC1 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KREW_USR_OPTN_T
# -----------------------------------------------------------------------
drop table if exists KREW_USR_OPTN_T
/

CREATE TABLE KREW_USR_OPTN_T
(
      PRNCPL_ID VARCHAR(40)
        , PRSN_OPTN_ID VARCHAR(200)
        , VAL VARCHAR(2000)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KREW_USR_OPTN_TP1 PRIMARY KEY(PRNCPL_ID,PRSN_OPTN_ID)


    , INDEX KREW_USR_OPTN_TI1 (PRNCPL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ADDR_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ADDR_TYP_T
/

CREATE TABLE KRIM_ADDR_TYP_T
(
      ADDR_TYP_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , DISPLAY_SORT_CD VARCHAR(2)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ADDR_TYP_TP1 PRIMARY KEY(ADDR_TYP_CD)

    , CONSTRAINT KRIM_ADDR_TYP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_ADDR_TYP_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_AFLTN_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_AFLTN_TYP_T
/

CREATE TABLE KRIM_AFLTN_TYP_T
(
      AFLTN_TYP_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , EMP_AFLTN_TYP_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , DISPLAY_SORT_CD VARCHAR(2)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_AFLTN_TYP_TP1 PRIMARY KEY(AFLTN_TYP_CD)

    , CONSTRAINT KRIM_AFLTN_TYP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_AFLTN_TYP_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ATTR_DEFN_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ATTR_DEFN_T
/

CREATE TABLE KRIM_ATTR_DEFN_T
(
      KIM_ATTR_DEFN_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(100)
        , LBL VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , NMSPC_CD VARCHAR(40)
        , CMPNT_NM VARCHAR(100)
    
    , CONSTRAINT KRIM_ATTR_DEFN_TP1 PRIMARY KEY(KIM_ATTR_DEFN_ID)

    , CONSTRAINT KRIM_ATTR_DEFN_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_CTZNSHP_STAT_T
# -----------------------------------------------------------------------
drop table if exists KRIM_CTZNSHP_STAT_T
/

CREATE TABLE KRIM_CTZNSHP_STAT_T
(
      CTZNSHP_STAT_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , DISPLAY_SORT_CD VARCHAR(2)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_CTZNSHP_STAT_TP1 PRIMARY KEY(CTZNSHP_STAT_CD)

    , CONSTRAINT KRIM_CTZNSHP_STAT_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_CTZNSHP_STAT_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_DLGN_MBR_ATTR_DATA_T
# -----------------------------------------------------------------------
drop table if exists KRIM_DLGN_MBR_ATTR_DATA_T
/

CREATE TABLE KRIM_DLGN_MBR_ATTR_DATA_T
(
      ATTR_DATA_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , DLGN_MBR_ID VARCHAR(40)
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , KIM_ATTR_DEFN_ID VARCHAR(40)
        , ATTR_VAL VARCHAR(400)
    
    , CONSTRAINT KRIM_DLGN_MBR_ATTR_DATA_TP1 PRIMARY KEY(ATTR_DATA_ID)

    , CONSTRAINT KRIM_DLGN_MBR_ATTR_DATA_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_DLGN_MBR_T
# -----------------------------------------------------------------------
drop table if exists KRIM_DLGN_MBR_T
/

CREATE TABLE KRIM_DLGN_MBR_T
(
      DLGN_MBR_ID VARCHAR(40)
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
        , DLGN_ID VARCHAR(40)
        , MBR_ID VARCHAR(40)
        , MBR_TYP_CD CHAR(1) default 'P'
        , ACTV_FRM_DT DATETIME
        , ACTV_TO_DT DATETIME
        , LAST_UPDT_DT DATETIME
        , ROLE_MBR_ID VARCHAR(40)
    
    , CONSTRAINT KRIM_DLGN_MBR_TP1 PRIMARY KEY(DLGN_MBR_ID)

    , CONSTRAINT KRIM_DLGN_MBR_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_DLGN_T
# -----------------------------------------------------------------------
drop table if exists KRIM_DLGN_T
/

CREATE TABLE KRIM_DLGN_T
(
      DLGN_ID VARCHAR(40)
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
        , ROLE_ID VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , DLGN_TYP_CD VARCHAR(1)
    
    , CONSTRAINT KRIM_DLGN_TP1 PRIMARY KEY(DLGN_ID)

    , CONSTRAINT KRIM_DLGN_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_EMAIL_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_EMAIL_TYP_T
/

CREATE TABLE KRIM_EMAIL_TYP_T
(
      EMAIL_TYP_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , DISPLAY_SORT_CD VARCHAR(2)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_EMAIL_TYP_TP1 PRIMARY KEY(EMAIL_TYP_CD)

    , CONSTRAINT KRIM_EMAIL_TYP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_EMAIL_TYP_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_EMP_STAT_T
# -----------------------------------------------------------------------
drop table if exists KRIM_EMP_STAT_T
/

CREATE TABLE KRIM_EMP_STAT_T
(
      EMP_STAT_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , DISPLAY_SORT_CD VARCHAR(2)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_EMP_STAT_TP1 PRIMARY KEY(EMP_STAT_CD)

    , CONSTRAINT KRIM_EMP_STAT_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_EMP_STAT_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_EMP_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_EMP_TYP_T
/

CREATE TABLE KRIM_EMP_TYP_T
(
      EMP_TYP_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , DISPLAY_SORT_CD VARCHAR(2)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_EMP_TYP_TP1 PRIMARY KEY(EMP_TYP_CD)

    , CONSTRAINT KRIM_EMP_TYP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_EMP_TYP_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_ADDR_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_ADDR_T
/

CREATE TABLE KRIM_ENTITY_ADDR_T
(
      ENTITY_ADDR_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENTITY_ID VARCHAR(40)
        , ENT_TYP_CD VARCHAR(40)
        , ADDR_TYP_CD VARCHAR(40)
        , ADDR_LINE_1 VARCHAR(45)
        , ADDR_LINE_2 VARCHAR(45)
        , ADDR_LINE_3 VARCHAR(45)
        , CITY VARCHAR(30)
        , STATE_PVC_CD VARCHAR(2)
        , POSTAL_CD VARCHAR(20)
        , POSTAL_CNTRY_CD VARCHAR(2)
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
        , ATTN_LINE VARCHAR(45)
        , ADDR_FMT VARCHAR(256)
        , MOD_DT DATETIME
        , VALID_DT DATETIME
        , VALID_IND VARCHAR(1)
        , NOTE_MSG VARCHAR(1024)
    
    , CONSTRAINT KRIM_ENTITY_ADDR_TP1 PRIMARY KEY(ENTITY_ADDR_ID)

    , CONSTRAINT KRIM_ENTITY_ADDR_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ENTITY_ADDR_TI1 (ENTITY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_AFLTN_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_AFLTN_T
/

CREATE TABLE KRIM_ENTITY_AFLTN_T
(
      ENTITY_AFLTN_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENTITY_ID VARCHAR(40)
        , AFLTN_TYP_CD VARCHAR(40)
        , CAMPUS_CD VARCHAR(2)
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ENTITY_AFLTN_TP1 PRIMARY KEY(ENTITY_AFLTN_ID)

    , CONSTRAINT KRIM_ENTITY_AFLTN_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ENTITY_AFLTN_TI1 (ENTITY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_BIO_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_BIO_T
/

CREATE TABLE KRIM_ENTITY_BIO_T
(
      ENTITY_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , BIRTH_DT DATETIME
        , GNDR_CD VARCHAR(1) NOT NULL
        , LAST_UPDT_DT DATETIME
        , DECEASED_DT DATETIME
        , MARITAL_STATUS VARCHAR(40)
        , PRIM_LANG_CD VARCHAR(40)
        , SEC_LANG_CD VARCHAR(40)
        , BIRTH_CNTRY_CD VARCHAR(2)
        , BIRTH_STATE_PVC_CD VARCHAR(2)
        , BIRTH_CITY VARCHAR(30)
        , GEO_ORIGIN VARCHAR(100)
        , NOTE_MSG VARCHAR(1024)
        , GNDR_CHG_CD VARCHAR(20)
    
    , CONSTRAINT KRIM_ENTITY_BIO_TP1 PRIMARY KEY(ENTITY_ID)

    , CONSTRAINT KRIM_ENTITY_BIO_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_CACHE_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_CACHE_T
/

CREATE TABLE KRIM_ENTITY_CACHE_T
(
      ENTITY_ID VARCHAR(40)
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , PRNCPL_NM VARCHAR(40)
        , ENTITY_TYP_CD VARCHAR(40)
        , FIRST_NM VARCHAR(40)
        , MIDDLE_NM VARCHAR(40)
        , LAST_NM VARCHAR(40)
        , PRSN_NM VARCHAR(255)
        , CAMPUS_CD VARCHAR(40)
        , PRMRY_DEPT_CD VARCHAR(40)
        , EMP_ID VARCHAR(40)
        , LAST_UPDT_TS DATETIME
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KRIM_ENTITY_CACHE_TP1 PRIMARY KEY(ENTITY_ID)

    , CONSTRAINT KRIM_ENTITY_CACHE_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_ENTITY_CACHE_TC1 UNIQUE (PRNCPL_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_CTZNSHP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_CTZNSHP_T
/

CREATE TABLE KRIM_ENTITY_CTZNSHP_T
(
      ENTITY_CTZNSHP_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENTITY_ID VARCHAR(40)
        , POSTAL_CNTRY_CD VARCHAR(2)
        , CTZNSHP_STAT_CD VARCHAR(40)
        , STRT_DT DATETIME
        , END_DT DATETIME
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ENTITY_CTZNSHP_TP1 PRIMARY KEY(ENTITY_CTZNSHP_ID)

    , CONSTRAINT KRIM_ENTITY_CTZNSHP_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_EMAIL_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_EMAIL_T
/

CREATE TABLE KRIM_ENTITY_EMAIL_T
(
      ENTITY_EMAIL_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENTITY_ID VARCHAR(40)
        , ENT_TYP_CD VARCHAR(40)
        , EMAIL_TYP_CD VARCHAR(40)
        , EMAIL_ADDR VARCHAR(200)
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ENTITY_EMAIL_TP1 PRIMARY KEY(ENTITY_EMAIL_ID)

    , CONSTRAINT KRIM_ENTITY_EMAIL_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ENTITY_EMAIL_TI1 (ENTITY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_EMP_INFO_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_EMP_INFO_T
/

CREATE TABLE KRIM_ENTITY_EMP_INFO_T
(
      ENTITY_EMP_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENTITY_ID VARCHAR(40)
        , ENTITY_AFLTN_ID VARCHAR(40)
        , EMP_STAT_CD VARCHAR(40)
        , EMP_TYP_CD VARCHAR(40)
        , BASE_SLRY_AMT DECIMAL(15,2)
        , PRMRY_IND VARCHAR(1)
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
        , PRMRY_DEPT_CD VARCHAR(40)
        , EMP_ID VARCHAR(40)
        , EMP_REC_ID VARCHAR(40)
    
    , CONSTRAINT KRIM_ENTITY_EMP_INFO_TP1 PRIMARY KEY(ENTITY_EMP_ID)

    , CONSTRAINT KRIM_ENTITY_EMP_INFO_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ENTITY_EMP_INFO_TI1 (ENTITY_ID)
    , INDEX KRIM_ENTITY_EMP_INFO_TI2 (ENTITY_AFLTN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_ENT_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_ENT_TYP_T
/

CREATE TABLE KRIM_ENTITY_ENT_TYP_T
(
      ENT_TYP_CD VARCHAR(40)
        , ENTITY_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ENTITY_ENT_TYP_TP1 PRIMARY KEY(ENT_TYP_CD,ENTITY_ID)

    , CONSTRAINT KRIM_ENTITY_ENT_TYP_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ENTITY_ENT_TYP_TI1 (ENTITY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_ETHNIC_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_ETHNIC_T
/

CREATE TABLE KRIM_ENTITY_ETHNIC_T
(
      ID VARCHAR(40)
        , ENTITY_ID VARCHAR(40)
        , ETHNCTY_CD VARCHAR(40)
        , SUB_ETHNCTY_CD VARCHAR(40)
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KRIM_ENTITY_ETHNIC_TP1 PRIMARY KEY(ID)

    , CONSTRAINT KRIM_ENTITY_ETHNIC_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_EXT_ID_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_EXT_ID_T
/

CREATE TABLE KRIM_ENTITY_EXT_ID_T
(
      ENTITY_EXT_ID_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENTITY_ID VARCHAR(40)
        , EXT_ID_TYP_CD VARCHAR(40)
        , EXT_ID VARCHAR(100)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ENTITY_EXT_ID_TP1 PRIMARY KEY(ENTITY_EXT_ID_ID)

    , CONSTRAINT KRIM_ENTITY_EXT_ID_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ENTITY_EXT_ID_TI1 (ENTITY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_NM_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_NM_T
/

CREATE TABLE KRIM_ENTITY_NM_T
(
      ENTITY_NM_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENTITY_ID VARCHAR(40)
        , NM_TYP_CD VARCHAR(40)
        , FIRST_NM VARCHAR(40)
        , MIDDLE_NM VARCHAR(40)
        , LAST_NM VARCHAR(80)
        , SUFFIX_NM VARCHAR(20)
        , PREFIX_NM VARCHAR(20)
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
        , TITLE_NM VARCHAR(20)
        , NOTE_MSG VARCHAR(1024)
        , NM_CHNG_DT DATETIME
    
    , CONSTRAINT KRIM_ENTITY_NM_TP1 PRIMARY KEY(ENTITY_NM_ID)

    , CONSTRAINT KRIM_ENTITY_NM_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ENTITY_NM_TI1 (ENTITY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_PHONE_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_PHONE_T
/

CREATE TABLE KRIM_ENTITY_PHONE_T
(
      ENTITY_PHONE_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENTITY_ID VARCHAR(40)
        , ENT_TYP_CD VARCHAR(40)
        , PHONE_TYP_CD VARCHAR(40)
        , PHONE_NBR VARCHAR(20)
        , PHONE_EXTN_NBR VARCHAR(8)
        , POSTAL_CNTRY_CD VARCHAR(2)
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ENTITY_PHONE_TP1 PRIMARY KEY(ENTITY_PHONE_ID)

    , CONSTRAINT KRIM_ENTITY_PHONE_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ENTITY_PHONE_TI1 (ENTITY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_PRIV_PREF_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_PRIV_PREF_T
/

CREATE TABLE KRIM_ENTITY_PRIV_PREF_T
(
      ENTITY_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , SUPPRESS_NM_IND VARCHAR(1) default 'N'
        , SUPPRESS_EMAIL_IND VARCHAR(1) default 'Y'
        , SUPPRESS_ADDR_IND VARCHAR(1) default 'Y'
        , SUPPRESS_PHONE_IND VARCHAR(1) default 'Y'
        , SUPPRESS_PRSNL_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ENTITY_PRIV_PREF_TP1 PRIMARY KEY(ENTITY_ID)

    , CONSTRAINT KRIM_ENTITY_PRIV_PREF_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_RESIDENCY_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_RESIDENCY_T
/

CREATE TABLE KRIM_ENTITY_RESIDENCY_T
(
      ID VARCHAR(40)
        , ENTITY_ID VARCHAR(40)
        , DETERMINATION_METHOD VARCHAR(40)
        , IN_STATE VARCHAR(40)
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KRIM_ENTITY_RESIDENCY_TP1 PRIMARY KEY(ID)

    , CONSTRAINT KRIM_ENTITY_RESIDENCY_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_T
/

CREATE TABLE KRIM_ENTITY_T
(
      ENTITY_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ENTITY_TP1 PRIMARY KEY(ENTITY_ID)

    , CONSTRAINT KRIM_ENTITY_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENTITY_VISA_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_VISA_T
/

CREATE TABLE KRIM_ENTITY_VISA_T
(
      ID VARCHAR(40)
        , ENTITY_ID VARCHAR(40)
        , VISA_TYPE_KEY VARCHAR(40)
        , VISA_ENTRY VARCHAR(40)
        , VISA_ID VARCHAR(40)
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
    
    , CONSTRAINT KRIM_ENTITY_VISA_TP1 PRIMARY KEY(ID)

    , CONSTRAINT KRIM_ENTITY_VISA_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENT_NM_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENT_NM_TYP_T
/

CREATE TABLE KRIM_ENT_NM_TYP_T
(
      ENT_NM_TYP_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , DISPLAY_SORT_CD VARCHAR(2)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ENT_NM_TYP_TP1 PRIMARY KEY(ENT_NM_TYP_CD)

    , CONSTRAINT KRIM_ENT_NM_TYP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_ENT_NM_TYP_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ENT_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ENT_TYP_T
/

CREATE TABLE KRIM_ENT_TYP_T
(
      ENT_TYP_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , DISPLAY_SORT_CD VARCHAR(2)
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_ENT_TYP_TP1 PRIMARY KEY(ENT_TYP_CD)

    , CONSTRAINT KRIM_ENT_TYP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_ENT_TYP_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_EXT_ID_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_EXT_ID_TYP_T
/

CREATE TABLE KRIM_EXT_ID_TYP_T
(
      EXT_ID_TYP_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(40)
        , ENCR_REQ_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , DISPLAY_SORT_CD VARCHAR(2)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_EXT_ID_TYP_TP1 PRIMARY KEY(EXT_ID_TYP_CD)

    , CONSTRAINT KRIM_EXT_ID_TYP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_EXT_ID_TYP_TC1 UNIQUE (NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_GRP_ATTR_DATA_T
# -----------------------------------------------------------------------
drop table if exists KRIM_GRP_ATTR_DATA_T
/

CREATE TABLE KRIM_GRP_ATTR_DATA_T
(
      ATTR_DATA_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , GRP_ID VARCHAR(40)
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , KIM_ATTR_DEFN_ID VARCHAR(40)
        , ATTR_VAL VARCHAR(400)
    
    , CONSTRAINT KRIM_GRP_ATTR_DATA_TP1 PRIMARY KEY(ATTR_DATA_ID)

    , CONSTRAINT KRIM_GRP_ATTR_DATA_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_GRP_DOCUMENT_T
# -----------------------------------------------------------------------
drop table if exists KRIM_GRP_DOCUMENT_T
/

CREATE TABLE KRIM_GRP_DOCUMENT_T
(
      FDOC_NBR VARCHAR(14)
        , GRP_ID VARCHAR(40) NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , GRP_NMSPC VARCHAR(100) NOT NULL
        , GRP_NM VARCHAR(400)
        , GRP_DESC VARCHAR(400)
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_GRP_DOCUMENT_TP1 PRIMARY KEY(FDOC_NBR)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_GRP_MBR_T
# -----------------------------------------------------------------------
drop table if exists KRIM_GRP_MBR_T
/

CREATE TABLE KRIM_GRP_MBR_T
(
      GRP_MBR_ID VARCHAR(40)
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
        , GRP_ID VARCHAR(40) NOT NULL
        , MBR_ID VARCHAR(40) NOT NULL
        , MBR_TYP_CD CHAR(1) default 'P' NOT NULL
        , ACTV_FRM_DT DATETIME
        , ACTV_TO_DT DATETIME
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_GRP_MBR_TP1 PRIMARY KEY(GRP_MBR_ID)

    , CONSTRAINT KRIM_GRP_MBR_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_GRP_MBR_TI1 (MBR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_GRP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_GRP_T
/

CREATE TABLE KRIM_GRP_T
(
      GRP_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , GRP_NM VARCHAR(80) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , GRP_DESC VARCHAR(4000)
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_GRP_TP1 PRIMARY KEY(GRP_ID)

    , CONSTRAINT KRIM_GRP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_GRP_TC1 UNIQUE (GRP_NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PERM_ATTR_DATA_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PERM_ATTR_DATA_T
/

CREATE TABLE KRIM_PERM_ATTR_DATA_T
(
      ATTR_DATA_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PERM_ID VARCHAR(40)
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , KIM_ATTR_DEFN_ID VARCHAR(40)
        , ATTR_VAL VARCHAR(400)
    
    , CONSTRAINT KRIM_PERM_ATTR_DATA_TP1 PRIMARY KEY(ATTR_DATA_ID)

    , CONSTRAINT KRIM_PERM_ATTR_DATA_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_PERM_ATTR_DATA_TI1 (PERM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PERM_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PERM_T
/

CREATE TABLE KRIM_PERM_T
(
      PERM_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PERM_TMPL_ID VARCHAR(40)
        , NMSPC_CD VARCHAR(40) NOT NULL
        , NM VARCHAR(100) NOT NULL
        , DESC_TXT VARCHAR(400)
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_PERM_TP1 PRIMARY KEY(PERM_ID)

    , CONSTRAINT KRIM_PERM_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_PERM_T_TC1 UNIQUE (NM, NMSPC_CD)

    , INDEX KRIM_PERM_TI1 (PERM_TMPL_ID)
    , INDEX KRIM_PERM_TI2 (PERM_TMPL_ID, ACTV_IND)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PERM_TMPL_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PERM_TMPL_T
/

CREATE TABLE KRIM_PERM_TMPL_T
(
      PERM_TMPL_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , NM VARCHAR(100) NOT NULL
        , DESC_TXT VARCHAR(400)
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_PERM_TMPL_TP1 PRIMARY KEY(PERM_TMPL_ID)

    , CONSTRAINT KRIM_PERM_TMPL_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_PERM_TMPL_TC1 UNIQUE (NM, NMSPC_CD)

    , INDEX KRIM_PERM_TMPL_TI1 (NMSPC_CD, NM)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PERSON_DOCUMENT_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PERSON_DOCUMENT_T
/

CREATE TABLE KRIM_PERSON_DOCUMENT_T
(
      FDOC_NBR VARCHAR(14)
        , ENTITY_ID VARCHAR(40) NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , PRNCPL_NM VARCHAR(100) NOT NULL
        , PRNCPL_PSWD VARCHAR(400)
        , UNIV_ID VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_PERSON_DOCUMENT_TP1 PRIMARY KEY(FDOC_NBR)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PHONE_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PHONE_TYP_T
/

CREATE TABLE KRIM_PHONE_TYP_T
(
      PHONE_TYP_CD VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PHONE_TYP_NM VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , DISPLAY_SORT_CD VARCHAR(2)
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_PHONE_TYP_TP1 PRIMARY KEY(PHONE_TYP_CD)

    , CONSTRAINT KRIM_PHONE_TYP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_PHONE_TYP_TC1 UNIQUE (PHONE_TYP_NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_ADDR_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_ADDR_MT
/

CREATE TABLE KRIM_PND_ADDR_MT
(
      FDOC_NBR VARCHAR(14)
        , ADDR_TYP_CD VARCHAR(40)
        , ADDR_LINE_1 VARCHAR(50)
        , ADDR_LINE_2 VARCHAR(50)
        , ADDR_LINE_3 VARCHAR(50)
        , CITY VARCHAR(30)
        , STATE_PVC_CD VARCHAR(2)
        , POSTAL_CD VARCHAR(20)
        , POSTAL_CNTRY_CD VARCHAR(2)
        , DISPLAY_SORT_CD VARCHAR(2)
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , ENTITY_ADDR_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , EDIT_FLAG VARCHAR(1) default 'N'
        , ATTN_LINE VARCHAR(45)
        , ADDR_FMT VARCHAR(256)
        , MOD_DT DATETIME
        , VALID_DT DATETIME
        , VALID_IND VARCHAR(1)
        , NOTE_MSG VARCHAR(1024)
    
    , CONSTRAINT KRIM_PND_ADDR_MTP1 PRIMARY KEY(FDOC_NBR,ENTITY_ADDR_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_AFLTN_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_AFLTN_MT
/

CREATE TABLE KRIM_PND_AFLTN_MT
(
      FDOC_NBR VARCHAR(14)
        , ENTITY_AFLTN_ID VARCHAR(40)
        , AFLTN_TYP_CD VARCHAR(40)
        , CAMPUS_CD VARCHAR(2)
        , EDIT_FLAG VARCHAR(1) default 'N'
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
    
    , CONSTRAINT KRIM_PND_AFLTN_MTP1 PRIMARY KEY(FDOC_NBR,ENTITY_AFLTN_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_CTZNSHP_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_CTZNSHP_MT
/

CREATE TABLE KRIM_PND_CTZNSHP_MT
(
      FDOC_NBR VARCHAR(14)
        , ENTITY_CTZNSHP_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , POSTAL_CNTRY_CD VARCHAR(2)
        , CTZNSHP_STAT_CD VARCHAR(40)
        , STRT_DT DATETIME
        , END_DT DATETIME
        , ACTV_IND VARCHAR(1) default 'Y'
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_CTZNSHP_MTP1 PRIMARY KEY(FDOC_NBR,ENTITY_CTZNSHP_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_DLGN_MBR_ATTR_DATA_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_DLGN_MBR_ATTR_DATA_T
/

CREATE TABLE KRIM_PND_DLGN_MBR_ATTR_DATA_T
(
      FDOC_NBR VARCHAR(14)
        , ATTR_DATA_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , DLGN_MBR_ID VARCHAR(40)
        , KIM_TYP_ID VARCHAR(40)
        , KIM_ATTR_DEFN_ID VARCHAR(40)
        , ATTR_VAL VARCHAR(400)
        , ACTV_IND VARCHAR(1) default 'Y'
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_DLGN_MBR_ATTR_DATAP1 PRIMARY KEY(FDOC_NBR,ATTR_DATA_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_DLGN_MBR_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_DLGN_MBR_T
/

CREATE TABLE KRIM_PND_DLGN_MBR_T
(
      FDOC_NBR VARCHAR(14)
        , DLGN_MBR_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , DLGN_ID VARCHAR(40) NOT NULL
        , MBR_ID VARCHAR(40)
        , MBR_NM VARCHAR(40)
        , MBR_TYP_CD VARCHAR(40) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
        , ACTV_FRM_DT DATETIME
        , ACTV_TO_DT DATETIME
        , ROLE_MBR_ID VARCHAR(40)
    
    , CONSTRAINT KRIM_PND_DLGN_MBR_TP1 PRIMARY KEY(FDOC_NBR,DLGN_MBR_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_DLGN_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_DLGN_T
/

CREATE TABLE KRIM_PND_DLGN_T
(
      FDOC_NBR VARCHAR(14)
        , DLGN_ID VARCHAR(40)
        , ROLE_ID VARCHAR(40) NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , KIM_TYP_ID VARCHAR(40)
        , DLGN_TYP_CD VARCHAR(100) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_PND_DLGN_TP1 PRIMARY KEY(FDOC_NBR,DLGN_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_EMAIL_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_EMAIL_MT
/

CREATE TABLE KRIM_PND_EMAIL_MT
(
      FDOC_NBR VARCHAR(14)
        , ENTITY_EMAIL_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENT_TYP_CD VARCHAR(40)
        , EMAIL_TYP_CD VARCHAR(40)
        , EMAIL_ADDR VARCHAR(200)
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_EMAIL_MTP1 PRIMARY KEY(FDOC_NBR,ENTITY_EMAIL_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_EMP_INFO_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_EMP_INFO_MT
/

CREATE TABLE KRIM_PND_EMP_INFO_MT
(
      FDOC_NBR VARCHAR(14)
        , PRMRY_DEPT_CD VARCHAR(40)
        , ENTITY_EMP_ID VARCHAR(40)
        , EMP_ID VARCHAR(40)
        , EMP_REC_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENTITY_AFLTN_ID VARCHAR(40)
        , EMP_STAT_CD VARCHAR(40)
        , EMP_TYP_CD VARCHAR(40)
        , BASE_SLRY_AMT DECIMAL(15,2)
        , PRMRY_IND VARCHAR(1)
        , ACTV_IND VARCHAR(1) default 'Y'
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_EMP_INFO_MTP1 PRIMARY KEY(FDOC_NBR,ENTITY_EMP_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_GRP_ATTR_DATA_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_GRP_ATTR_DATA_T
/

CREATE TABLE KRIM_PND_GRP_ATTR_DATA_T
(
      FDOC_NBR VARCHAR(14)
        , ATTR_DATA_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , GRP_ID VARCHAR(40)
        , KIM_TYP_ID VARCHAR(40)
        , KIM_ATTR_DEFN_ID VARCHAR(40)
        , ATTR_VAL VARCHAR(400)
        , ACTV_IND VARCHAR(1) default 'Y'
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_GRP_ATTR_DATA_TP1 PRIMARY KEY(FDOC_NBR,ATTR_DATA_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_GRP_MBR_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_GRP_MBR_T
/

CREATE TABLE KRIM_PND_GRP_MBR_T
(
      FDOC_NBR VARCHAR(14)
        , GRP_MBR_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , GRP_ID VARCHAR(40) NOT NULL
        , MBR_ID VARCHAR(40)
        , MBR_NM VARCHAR(100)
        , MBR_TYP_CD VARCHAR(40) NOT NULL
        , ACTV_FRM_DT DATETIME
        , ACTV_TO_DT DATETIME
    
    , CONSTRAINT KRIM_PND_GRP_MBR_TP1 PRIMARY KEY(FDOC_NBR,GRP_MBR_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_GRP_PRNCPL_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_GRP_PRNCPL_MT
/

CREATE TABLE KRIM_PND_GRP_PRNCPL_MT
(
      GRP_MBR_ID VARCHAR(40)
        , FDOC_NBR VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , GRP_ID VARCHAR(40) NOT NULL
        , PRNCPL_ID VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , GRP_NM VARCHAR(80) NOT NULL
        , GRP_TYPE VARCHAR(80)
        , KIM_TYP_ID VARCHAR(40)
        , NMSPC_CD VARCHAR(40)
        , ACTV_FRM_DT DATETIME
        , ACTV_TO_DT DATETIME
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_GRP_PRNCPL_MTP1 PRIMARY KEY(GRP_MBR_ID,FDOC_NBR)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_NM_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_NM_MT
/

CREATE TABLE KRIM_PND_NM_MT
(
      FDOC_NBR VARCHAR(14)
        , ENTITY_NM_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM_TYP_CD VARCHAR(40)
        , FIRST_NM VARCHAR(40)
        , MIDDLE_NM VARCHAR(40)
        , LAST_NM VARCHAR(80)
        , SUFFIX_NM VARCHAR(20)
        , PREFIX_NM VARCHAR(20)
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , EDIT_FLAG VARCHAR(1) default 'N'
        , TITLE_NM VARCHAR(20)
        , NOTE_MSG VARCHAR(1024)
        , NM_CHNG_DT DATETIME
    
    , CONSTRAINT KRIM_PND_NM_MTP1 PRIMARY KEY(FDOC_NBR,ENTITY_NM_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_PHONE_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_PHONE_MT
/

CREATE TABLE KRIM_PND_PHONE_MT
(
      FDOC_NBR VARCHAR(14)
        , ENTITY_PHONE_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ENT_TYP_CD VARCHAR(40)
        , PHONE_TYP_CD VARCHAR(40)
        , PHONE_NBR VARCHAR(20)
        , PHONE_EXTN_NBR VARCHAR(8)
        , POSTAL_CNTRY_CD VARCHAR(2)
        , DFLT_IND VARCHAR(1) default 'N'
        , ACTV_IND VARCHAR(1) default 'Y'
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_PHONE_MTP1 PRIMARY KEY(FDOC_NBR,ENTITY_PHONE_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_PRIV_PREF_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_PRIV_PREF_MT
/

CREATE TABLE KRIM_PND_PRIV_PREF_MT
(
      FDOC_NBR VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , SUPPRESS_NM_IND VARCHAR(1) default 'N'
        , SUPPRESS_EMAIL_IND VARCHAR(1) default 'Y'
        , SUPPRESS_ADDR_IND VARCHAR(1) default 'Y'
        , SUPPRESS_PHONE_IND VARCHAR(1) default 'Y'
        , SUPPRESS_PRSNL_IND VARCHAR(1) default 'Y'
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_PRIV_PREF_MTP1 PRIMARY KEY(FDOC_NBR)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_ROLE_MBR_ATTR_DATA_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_ROLE_MBR_ATTR_DATA_MT
/

CREATE TABLE KRIM_PND_ROLE_MBR_ATTR_DATA_MT
(
      FDOC_NBR VARCHAR(14)
        , ATTR_DATA_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_MBR_ID VARCHAR(40)
        , KIM_TYP_ID VARCHAR(40)
        , KIM_ATTR_DEFN_ID VARCHAR(40)
        , ATTR_VAL VARCHAR(400)
        , ACTV_IND VARCHAR(1) default 'Y'
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_ROLE_MBR_ATTR_DATAP1 PRIMARY KEY(FDOC_NBR,ATTR_DATA_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_ROLE_MBR_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_ROLE_MBR_MT
/

CREATE TABLE KRIM_PND_ROLE_MBR_MT
(
      FDOC_NBR VARCHAR(14)
        , ROLE_MBR_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_ID VARCHAR(40) NOT NULL
        , MBR_ID VARCHAR(40)
        , MBR_TYP_CD VARCHAR(40) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
        , ACTV_FRM_DT DATETIME
        , ACTV_TO_DT DATETIME
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_ROLE_MBR_MTP1 PRIMARY KEY(FDOC_NBR,ROLE_MBR_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_ROLE_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_ROLE_MT
/

CREATE TABLE KRIM_PND_ROLE_MT
(
      FDOC_NBR VARCHAR(14)
        , ROLE_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_NM VARCHAR(100) NOT NULL
        , KIM_TYP_ID VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
        , NMSPC_CD VARCHAR(40)
        , EDIT_FLAG VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_PND_ROLE_MTP1 PRIMARY KEY(FDOC_NBR,ROLE_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_ROLE_PERM_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_ROLE_PERM_T
/

CREATE TABLE KRIM_PND_ROLE_PERM_T
(
      FDOC_NBR VARCHAR(14)
        , ROLE_PERM_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_ID VARCHAR(40) NOT NULL
        , PERM_ID VARCHAR(40) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_PND_ROLE_PERM_TP1 PRIMARY KEY(FDOC_NBR,ROLE_PERM_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_ROLE_RSP_ACTN_MT
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_ROLE_RSP_ACTN_MT
/

CREATE TABLE KRIM_PND_ROLE_RSP_ACTN_MT
(
      ROLE_RSP_ACTN_ID VARCHAR(40)
        , FDOC_NBR VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ACTN_TYP_CD VARCHAR(40)
        , PRIORITY_NBR DECIMAL(3)
        , ACTN_PLCY_CD VARCHAR(40)
        , ROLE_MBR_ID VARCHAR(40)
        , ROLE_RSP_ID VARCHAR(40)
        , EDIT_FLAG VARCHAR(1) default 'N'
        , FRC_ACTN VARCHAR(1)
    
    , CONSTRAINT KRIM_PND_ROLE_RSP_ACTN_MTP1 PRIMARY KEY(ROLE_RSP_ACTN_ID,FDOC_NBR)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PND_ROLE_RSP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PND_ROLE_RSP_T
/

CREATE TABLE KRIM_PND_ROLE_RSP_T
(
      FDOC_NBR VARCHAR(14)
        , ROLE_RSP_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_ID VARCHAR(40) NOT NULL
        , RSP_ID VARCHAR(40) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_PND_ROLE_RSP_TP1 PRIMARY KEY(FDOC_NBR,ROLE_RSP_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_PRNCPL_T
# -----------------------------------------------------------------------
drop table if exists KRIM_PRNCPL_T
/

CREATE TABLE KRIM_PRNCPL_T
(
      PRNCPL_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PRNCPL_NM VARCHAR(100) NOT NULL
        , ENTITY_ID VARCHAR(40)
        , PRNCPL_PSWD VARCHAR(400)
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_PRNCPL_TP1 PRIMARY KEY(PRNCPL_ID)

    , CONSTRAINT KRIM_PRNCPL_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_PRNCPL_TC1 UNIQUE (PRNCPL_NM)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ROLE_DOCUMENT_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_DOCUMENT_T
/

CREATE TABLE KRIM_ROLE_DOCUMENT_T
(
      FDOC_NBR VARCHAR(14)
        , ROLE_ID VARCHAR(40) NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_TYP_ID VARCHAR(40) NOT NULL
        , ROLE_NMSPC VARCHAR(100) NOT NULL
        , ROLE_NM VARCHAR(400)
        , ACTV_IND VARCHAR(1) default 'Y'
        , DESC_TXT VARCHAR(4000)
    
    , CONSTRAINT KRIM_ROLE_DOCUMENT_TP1 PRIMARY KEY(FDOC_NBR)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ROLE_MBR_ATTR_DATA_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_MBR_ATTR_DATA_T
/

CREATE TABLE KRIM_ROLE_MBR_ATTR_DATA_T
(
      ATTR_DATA_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_MBR_ID VARCHAR(40)
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , KIM_ATTR_DEFN_ID VARCHAR(40)
        , ATTR_VAL VARCHAR(400)
    
    , CONSTRAINT KRIM_ROLE_MBR_ATTR_DATA_TP1 PRIMARY KEY(ATTR_DATA_ID)

    , CONSTRAINT KRIM_ROLE_MBR_ATTR_DATA_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ROLE_MBR_ATTR_DATA_TI1 (ROLE_MBR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ROLE_MBR_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_MBR_T
/

CREATE TABLE KRIM_ROLE_MBR_T
(
      ROLE_MBR_ID VARCHAR(40)
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , OBJ_ID VARCHAR(36) NOT NULL
        , ROLE_ID VARCHAR(40) NOT NULL
        , MBR_ID VARCHAR(40) NOT NULL
        , MBR_TYP_CD CHAR(1) default 'P' NOT NULL
        , ACTV_FRM_DT DATETIME
        , ACTV_TO_DT DATETIME
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ROLE_MBR_TP1 PRIMARY KEY(ROLE_MBR_ID)

    , CONSTRAINT KRIM_ROLE_MBR_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ROLE_MBR_TI1 (MBR_ID)
    , INDEX KRIM_ROLE_MBR_TI2 (ROLE_ID, MBR_ID, MBR_TYP_CD)
    , INDEX KRIM_ROLE_MBR_TI3 (MBR_ID, MBR_TYP_CD)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ROLE_PERM_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_PERM_T
/

CREATE TABLE KRIM_ROLE_PERM_T
(
      ROLE_PERM_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_ID VARCHAR(40) NOT NULL
        , PERM_ID VARCHAR(40) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_ROLE_PERM_TP1 PRIMARY KEY(ROLE_PERM_ID)

    , CONSTRAINT KRIM_ROLE_PERM_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ROLE_PERM_TI1 (PERM_ID)
    , INDEX KRIM_ROLE_PERM_TI2 (PERM_ID, ACTV_IND)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ROLE_RSP_ACTN_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_RSP_ACTN_T
/

CREATE TABLE KRIM_ROLE_RSP_ACTN_T
(
      ROLE_RSP_ACTN_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ACTN_TYP_CD VARCHAR(40)
        , PRIORITY_NBR DECIMAL(3)
        , ACTN_PLCY_CD VARCHAR(40)
        , ROLE_MBR_ID VARCHAR(40)
        , ROLE_RSP_ID VARCHAR(40)
        , FRC_ACTN VARCHAR(1) default 'N'
    
    , CONSTRAINT KRIM_ROLE_RSP_ACTN_TP1 PRIMARY KEY(ROLE_RSP_ACTN_ID)

    , CONSTRAINT KRIM_ROLE_RSP_ACTN_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_ROLE_RSP_ACTN_TC1 UNIQUE (ROLE_RSP_ID, ROLE_MBR_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ROLE_RSP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_RSP_T
/

CREATE TABLE KRIM_ROLE_RSP_T
(
      ROLE_RSP_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_ID VARCHAR(40)
        , RSP_ID VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_ROLE_RSP_TP1 PRIMARY KEY(ROLE_RSP_ID)

    , CONSTRAINT KRIM_ROLE_RSP_TC0 UNIQUE (OBJ_ID)

    , INDEX KRIM_ROLE_RSP_TI1 (RSP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_ROLE_T
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_T
/

CREATE TABLE KRIM_ROLE_T
(
      ROLE_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ROLE_NM VARCHAR(80) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , DESC_TXT VARCHAR(4000)
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y'
        , LAST_UPDT_DT DATETIME
    
    , CONSTRAINT KRIM_ROLE_TP1 PRIMARY KEY(ROLE_ID)

    , CONSTRAINT KRIM_ROLE_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_ROLE_TC1 UNIQUE (ROLE_NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_RSP_ATTR_DATA_T
# -----------------------------------------------------------------------
drop table if exists KRIM_RSP_ATTR_DATA_T
/

CREATE TABLE KRIM_RSP_ATTR_DATA_T
(
      ATTR_DATA_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , RSP_ID VARCHAR(40)
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , KIM_ATTR_DEFN_ID VARCHAR(40)
        , ATTR_VAL VARCHAR(400)
    
    , CONSTRAINT KRIM_RSP_ATTR_DATA_TP1 PRIMARY KEY(ATTR_DATA_ID)

    , CONSTRAINT KRIM_RSP_ATTR_DATA_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_RSP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_RSP_T
/

CREATE TABLE KRIM_RSP_T
(
      RSP_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , RSP_TMPL_ID VARCHAR(40)
        , NMSPC_CD VARCHAR(40) NOT NULL
        , NM VARCHAR(100) NOT NULL
        , DESC_TXT VARCHAR(400)
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_RSP_TP1 PRIMARY KEY(RSP_ID)

    , CONSTRAINT KRIM_RSP_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_RSP_T_TC1 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_RSP_TMPL_T
# -----------------------------------------------------------------------
drop table if exists KRIM_RSP_TMPL_T
/

CREATE TABLE KRIM_RSP_TMPL_T
(
      RSP_TMPL_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , NM VARCHAR(100) NOT NULL
        , KIM_TYP_ID VARCHAR(100) NOT NULL
        , DESC_TXT VARCHAR(400)
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_RSP_TMPL_TP1 PRIMARY KEY(RSP_TMPL_ID)

    , CONSTRAINT KRIM_RSP_TMPL_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_RSP_TMPL_TC1 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_TYP_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KRIM_TYP_ATTR_T
/

CREATE TABLE KRIM_TYP_ATTR_T
(
      KIM_TYP_ATTR_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , SORT_CD VARCHAR(2)
        , KIM_TYP_ID VARCHAR(40) NOT NULL
        , KIM_ATTR_DEFN_ID VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y'
    
    , CONSTRAINT KRIM_TYP_ATTR_TP1 PRIMARY KEY(KIM_TYP_ATTR_ID)

    , CONSTRAINT KRIM_TYP_ATTR_TC0 UNIQUE (OBJ_ID)
    , CONSTRAINT KRIM_TYP_ATTR_TC1 UNIQUE (SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND)

    , INDEX KRIM_TYP_ATTRIBUTE_TI1 (KIM_TYP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRIM_TYP_T
/

CREATE TABLE KRIM_TYP_T
(
      KIM_TYP_ID VARCHAR(40)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , NM VARCHAR(100)
        , SRVC_NM VARCHAR(200)
        , ACTV_IND VARCHAR(1) default 'Y'
        , NMSPC_CD VARCHAR(40)
    
    , CONSTRAINT KRIM_TYP_TP1 PRIMARY KEY(KIM_TYP_ID)

    , CONSTRAINT KRIM_TYP_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRLC_CMP_T
# -----------------------------------------------------------------------
drop table if exists KRLC_CMP_T
/

CREATE TABLE KRLC_CMP_T
(
      CAMPUS_CD VARCHAR(2)
        , CAMPUS_NM VARCHAR(250)
        , CAMPUS_SHRT_NM VARCHAR(250)
        , CAMPUS_TYP_CD VARCHAR(1)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y' NOT NULL
    
    , CONSTRAINT KRLC_CMP_TP1 PRIMARY KEY(CAMPUS_CD)

    , CONSTRAINT KRNS_CAMPUS_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRLC_CMP_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRLC_CMP_TYP_T
/

CREATE TABLE KRLC_CMP_TYP_T
(
      CAMPUS_TYP_CD VARCHAR(1)
        , CMP_TYP_NM VARCHAR(250)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y' NOT NULL
    
    , CONSTRAINT KRLC_CMP_TYP_TP1 PRIMARY KEY(CAMPUS_TYP_CD)

    , CONSTRAINT KRNS_CMP_TYP_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRLC_CNTRY_T
# -----------------------------------------------------------------------
drop table if exists KRLC_CNTRY_T
/

CREATE TABLE KRLC_CNTRY_T
(
      POSTAL_CNTRY_CD VARCHAR(2)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , POSTAL_CNTRY_NM VARCHAR(40)
        , PSTL_CNTRY_RSTRC_IND VARCHAR(1) NOT NULL
        , ACTV_IND VARCHAR(1) default 'Y' NOT NULL
        , ALT_POSTAL_CNTRY_CD VARCHAR(3)
    
    , CONSTRAINT KRLC_CNTRY_TP1 PRIMARY KEY(POSTAL_CNTRY_CD)

    , CONSTRAINT KR_COUNTRY_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRLC_CNTY_T
# -----------------------------------------------------------------------
drop table if exists KRLC_CNTY_T
/

CREATE TABLE KRLC_CNTY_T
(
      COUNTY_CD VARCHAR(10)
        , STATE_CD VARCHAR(2)
        , POSTAL_CNTRY_CD VARCHAR(2) default 'US'
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , COUNTY_NM VARCHAR(100)
        , ACTV_IND VARCHAR(1)
    
    , CONSTRAINT KRLC_CNTY_TP1 PRIMARY KEY(COUNTY_CD,STATE_CD,POSTAL_CNTRY_CD)

    , CONSTRAINT KR_COUNTY_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRLC_PSTL_CD_T
# -----------------------------------------------------------------------
drop table if exists KRLC_PSTL_CD_T
/

CREATE TABLE KRLC_PSTL_CD_T
(
      POSTAL_CD VARCHAR(20)
        , POSTAL_CNTRY_CD VARCHAR(2) default 'US'
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , POSTAL_STATE_CD VARCHAR(2)
        , COUNTY_CD VARCHAR(10)
        , POSTAL_CITY_NM VARCHAR(30)
        , ACTV_IND VARCHAR(1) default 'Y' NOT NULL
    
    , CONSTRAINT KRLC_PSTL_CD_TP1 PRIMARY KEY(POSTAL_CD,POSTAL_CNTRY_CD)

    , CONSTRAINT KR_POSTAL_CODE_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRLC_ST_T
# -----------------------------------------------------------------------
drop table if exists KRLC_ST_T
/

CREATE TABLE KRLC_ST_T
(
      POSTAL_STATE_CD VARCHAR(2)
        , POSTAL_CNTRY_CD VARCHAR(2) default 'US'
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , POSTAL_STATE_NM VARCHAR(40)
        , ACTV_IND VARCHAR(1) default 'Y' NOT NULL
    
    , CONSTRAINT KRLC_ST_TP1 PRIMARY KEY(POSTAL_STATE_CD,POSTAL_CNTRY_CD)

    , CONSTRAINT KR_STATE_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_ACTN_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KRMS_ACTN_ATTR_T
/

CREATE TABLE KRMS_ACTN_ATTR_T
(
      ACTN_ATTR_DATA_ID VARCHAR(40)
        , ACTN_ID VARCHAR(40) NOT NULL
        , ATTR_DEFN_ID VARCHAR(40) NOT NULL
        , ATTR_VAL VARCHAR(400)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_ACTN_ATTR_TP1 PRIMARY KEY(ACTN_ATTR_DATA_ID)


    , INDEX KRMS_ACTN_ATTR_TI1 (ACTN_ID)
    , INDEX KRMS_ACTN_ATTR_TI2 (ATTR_DEFN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_ACTN_T
# -----------------------------------------------------------------------
drop table if exists KRMS_ACTN_T
/

CREATE TABLE KRMS_ACTN_T
(
      ACTN_ID VARCHAR(40)
        , NM VARCHAR(40)
        , DESC_TXT VARCHAR(4000)
        , TYP_ID VARCHAR(40) NOT NULL
        , RULE_ID VARCHAR(40)
        , SEQ_NO DECIMAL(5)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
    
    , CONSTRAINT KRMS_ACTN_TP1 PRIMARY KEY(ACTN_ID)

    , CONSTRAINT KRMS_ACTN_TC2 UNIQUE (ACTN_ID, RULE_ID, SEQ_NO)

    , INDEX KRMS_ACTN_TI1 (TYP_ID)
    , INDEX KRMS_ACTN_TI2 (RULE_ID)
    , INDEX KRMS_ACTN_TI3 (RULE_ID, SEQ_NO)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_AGENDA_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KRMS_AGENDA_ATTR_T
/

CREATE TABLE KRMS_AGENDA_ATTR_T
(
      AGENDA_ATTR_ID VARCHAR(40)
        , AGENDA_ID VARCHAR(40) NOT NULL
        , ATTR_VAL VARCHAR(400)
        , ATTR_DEFN_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_AGENDA_ATTR_TP1 PRIMARY KEY(AGENDA_ATTR_ID)


    , INDEX KRMS_AGENDA_ATTR_T12 (ATTR_DEFN_ID)
    , INDEX KRMS_AGENDA_ATTR_TI1 (AGENDA_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_AGENDA_ITM_T
# -----------------------------------------------------------------------
drop table if exists KRMS_AGENDA_ITM_T
/

CREATE TABLE KRMS_AGENDA_ITM_T
(
      AGENDA_ITM_ID VARCHAR(40)
        , RULE_ID VARCHAR(40)
        , SUB_AGENDA_ID VARCHAR(40)
        , AGENDA_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , WHEN_TRUE VARCHAR(40)
        , WHEN_FALSE VARCHAR(40)
        , ALWAYS VARCHAR(40)
    
    , CONSTRAINT KRMS_AGENDA_ITM_TP1 PRIMARY KEY(AGENDA_ITM_ID)


    , INDEX KRMS_AGENDA_ITM_TI1 (RULE_ID)
    , INDEX KRMS_AGENDA_ITM_TI2 (AGENDA_ID)
    , INDEX KRMS_AGENDA_ITM_TI3 (SUB_AGENDA_ID)
    , INDEX KRMS_AGENDA_ITM_TI4 (WHEN_TRUE)
    , INDEX KRMS_AGENDA_ITM_TI5 (WHEN_FALSE)
    , INDEX KRMS_AGENDA_ITM_TI6 (ALWAYS)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_AGENDA_T
# -----------------------------------------------------------------------
drop table if exists KRMS_AGENDA_T
/

CREATE TABLE KRMS_AGENDA_T
(
      AGENDA_ID VARCHAR(40)
        , NM VARCHAR(100) NOT NULL
        , CNTXT_ID VARCHAR(40) NOT NULL
        , INIT_AGENDA_ITM_ID VARCHAR(40)
        , TYP_ID VARCHAR(40)
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_AGENDA_TP1 PRIMARY KEY(AGENDA_ID)

    , CONSTRAINT KRMS_AGENDA_TC1 UNIQUE (NM, CNTXT_ID)

    , INDEX KRMS_AGENDA_TI1 (CNTXT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_ATTR_DEFN_T
# -----------------------------------------------------------------------
drop table if exists KRMS_ATTR_DEFN_T
/

CREATE TABLE KRMS_ATTR_DEFN_T
(
      ATTR_DEFN_ID VARCHAR(255)
        , NM VARCHAR(100) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , LBL VARCHAR(40)
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , CMPNT_NM VARCHAR(100)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , DESC_TXT VARCHAR(255) default 'null'
    
    , CONSTRAINT KRMS_ATTR_DEFN_TP1 PRIMARY KEY(ATTR_DEFN_ID)

    , CONSTRAINT KRMS_ATTR_DEFN_TC1 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_CMPND_PROP_PROPS_T
# -----------------------------------------------------------------------
drop table if exists KRMS_CMPND_PROP_PROPS_T
/

CREATE TABLE KRMS_CMPND_PROP_PROPS_T
(
      CMPND_PROP_ID VARCHAR(40)
        , PROP_ID VARCHAR(40)
    
    , CONSTRAINT KRMS_CMPND_PROP_PROPS_TP1 PRIMARY KEY(CMPND_PROP_ID,PROP_ID)


    , INDEX KRMS_CMPND_PROP_PROPS_FK2 (CMPND_PROP_ID)
    , INDEX KRMS_CMPND_PROP_PROPS_TI1 (PROP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_CNTXT_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_ATTR_T
/

CREATE TABLE KRMS_CNTXT_ATTR_T
(
      CNTXT_ATTR_ID VARCHAR(40)
        , CNTXT_ID VARCHAR(40) NOT NULL
        , ATTR_VAL VARCHAR(400)
        , ATTR_DEFN_ID VARCHAR(40)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_CNTXT_ATTR_TP1 PRIMARY KEY(CNTXT_ATTR_ID)


    , INDEX KRMS_CNTXT_ATTR_TI1 (CNTXT_ID)
    , INDEX KRMS_CNTXT_ATTR_TI2 (ATTR_DEFN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_CNTXT_T
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_T
/

CREATE TABLE KRMS_CNTXT_T
(
      CNTXT_ID VARCHAR(40)
        , NMSPC_CD VARCHAR(40) NOT NULL
        , NM VARCHAR(100) NOT NULL
        , TYP_ID VARCHAR(40)
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , DESC_TXT VARCHAR(255) default 'null'
    
    , CONSTRAINT KRMS_CNTXT_TP1 PRIMARY KEY(CNTXT_ID)

    , CONSTRAINT KRMS_CNTXT_TC1 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_ACTN_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_ACTN_TYP_T
/

CREATE TABLE KRMS_CNTXT_VLD_ACTN_TYP_T
(
      CNTXT_VLD_ACTN_ID VARCHAR(40)
        , CNTXT_ID VARCHAR(40) NOT NULL
        , ACTN_TYP_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_CNTXT_VLD_ACTN_TYP_TP1 PRIMARY KEY(CNTXT_VLD_ACTN_ID)


    , INDEX KRMS_CNTXT_VLD_ACTN_TYP_TI1 (CNTXT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_AGENDA_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_AGENDA_TYP_T
/

CREATE TABLE KRMS_CNTXT_VLD_AGENDA_TYP_T
(
      CNTXT_VLD_AGENDA_ID VARCHAR(40)
        , CNTXT_ID VARCHAR(40) NOT NULL
        , AGENDA_TYP_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_CNTXT_VLD_AGENDA_TYP_TP1 PRIMARY KEY(CNTXT_VLD_AGENDA_ID)


    , INDEX KRMS_CNTXT_VLD_AGENDA_TYP_TI1 (CNTXT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_FUNC_T
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_FUNC_T
/

CREATE TABLE KRMS_CNTXT_VLD_FUNC_T
(
      CNTXT_VLD_FUNC_ID VARCHAR(40)
        , CNTXT_ID VARCHAR(40) NOT NULL
        , FUNC_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_CNTXT_VLD_FUNC_TP1 PRIMARY KEY(CNTXT_VLD_FUNC_ID)


    , INDEX KRMS_CNTXT_VLD_FUNC_TI1 (CNTXT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_RULE_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_RULE_TYP_T
/

CREATE TABLE KRMS_CNTXT_VLD_RULE_TYP_T
(
      CNTXT_VLD_RULE_ID VARCHAR(40)
        , CNTXT_ID VARCHAR(40) NOT NULL
        , RULE_TYP_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_CNTXT_VLD_RULE_TYP_TP1 PRIMARY KEY(CNTXT_VLD_RULE_ID)


    , INDEX KRMS_CNTXT_VLD_RULE_TYP_TI1 (CNTXT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_TERM_SPEC_T
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_TERM_SPEC_T
/

CREATE TABLE KRMS_CNTXT_VLD_TERM_SPEC_T
(
      CNTXT_TERM_SPEC_PREREQ_ID VARCHAR(40)
        , CNTXT_ID VARCHAR(40) NOT NULL
        , TERM_SPEC_ID VARCHAR(40) NOT NULL
        , PREREQ VARCHAR(1) default 'n'
    
    , CONSTRAINT KRMS_CNTXT_VLD_TERM_SPEC_TP1 PRIMARY KEY(CNTXT_TERM_SPEC_PREREQ_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_CTGRY_T
# -----------------------------------------------------------------------
drop table if exists KRMS_CTGRY_T
/

CREATE TABLE KRMS_CTGRY_T
(
      CTGRY_ID VARCHAR(40)
        , NM VARCHAR(255) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KRMS_CTGRY_TP1 PRIMARY KEY(CTGRY_ID)

    , CONSTRAINT KRMS_CTGRY_TC0 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_FUNC_CTGRY_T
# -----------------------------------------------------------------------
drop table if exists KRMS_FUNC_CTGRY_T
/

CREATE TABLE KRMS_FUNC_CTGRY_T
(
      FUNC_ID VARCHAR(40)
        , CTGRY_ID VARCHAR(40)
    
    , CONSTRAINT KRMS_FUNC_CTGRY_TP1 PRIMARY KEY(FUNC_ID,CTGRY_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_FUNC_PARM_T
# -----------------------------------------------------------------------
drop table if exists KRMS_FUNC_PARM_T
/

CREATE TABLE KRMS_FUNC_PARM_T
(
      FUNC_PARM_ID VARCHAR(40)
        , NM VARCHAR(100) NOT NULL
        , DESC_TXT VARCHAR(255)
        , TYP VARCHAR(255) NOT NULL
        , FUNC_ID VARCHAR(40) NOT NULL
        , SEQ_NO DECIMAL(5) NOT NULL
    
    , CONSTRAINT KRMS_FUNC_PARM_TP1 PRIMARY KEY(FUNC_PARM_ID)


    , INDEX KRMS_FUNC_PARM_TI1 (FUNC_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_FUNC_T
# -----------------------------------------------------------------------
drop table if exists KRMS_FUNC_T
/

CREATE TABLE KRMS_FUNC_T
(
      FUNC_ID VARCHAR(40)
        , NMSPC_CD VARCHAR(40) NOT NULL
        , NM VARCHAR(100) NOT NULL
        , DESC_TXT VARCHAR(255)
        , RTRN_TYP VARCHAR(255) NOT NULL
        , TYP_ID VARCHAR(40) NOT NULL
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_FUNC_TP1 PRIMARY KEY(FUNC_ID)

    , CONSTRAINT KRMS_FUNC_TC1 UNIQUE (NM, NMSPC_CD)

    , INDEX KRMS_FUNC_TI1 (TYP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_NL_TMPL_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KRMS_NL_TMPL_ATTR_T
/

CREATE TABLE KRMS_NL_TMPL_ATTR_T
(
      NL_TMPL_ATTR_ID VARCHAR(40)
        , NL_TMPL_ID VARCHAR(40) NOT NULL
        , ATTR_DEFN_ID VARCHAR(40) NOT NULL
        , ATTR_VAL VARCHAR(400)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_NL_TMPL_ATTR_TP1 PRIMARY KEY(NL_TMPL_ATTR_ID)

    , CONSTRAINT KRMS_NL_TMPL_ATTR_TC1 UNIQUE (NL_TMPL_ID, ATTR_DEFN_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_NL_TMPL_T
# -----------------------------------------------------------------------
drop table if exists KRMS_NL_TMPL_T
/

CREATE TABLE KRMS_NL_TMPL_T
(
      NL_TMPL_ID VARCHAR(40)
        , LANG_CD VARCHAR(2) NOT NULL
        , NL_USAGE_ID VARCHAR(40) NOT NULL
        , TYP_ID VARCHAR(40) NOT NULL
        , TMPL VARCHAR(4000) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_NL_TMPL_TP1 PRIMARY KEY(NL_TMPL_ID)

    , CONSTRAINT KRMS_NL_TMPL_TC1 UNIQUE (LANG_CD, NL_USAGE_ID, TYP_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_NL_USAGE_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KRMS_NL_USAGE_ATTR_T
/

CREATE TABLE KRMS_NL_USAGE_ATTR_T
(
      NL_USAGE_ATTR_ID VARCHAR(40)
        , NL_USAGE_ID VARCHAR(40) NOT NULL
        , ATTR_DEFN_ID VARCHAR(40) NOT NULL
        , ATTR_VAL VARCHAR(400)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_NL_USAGE_ATTR_TP1 PRIMARY KEY(NL_USAGE_ATTR_ID)

    , CONSTRAINT KRMS_NL_USAGE_ATTR_TC1 UNIQUE (NL_USAGE_ID, ATTR_DEFN_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_NL_USAGE_T
# -----------------------------------------------------------------------
drop table if exists KRMS_NL_USAGE_T
/

CREATE TABLE KRMS_NL_USAGE_T
(
      NL_USAGE_ID VARCHAR(40)
        , NM VARCHAR(255) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , DESC_TXT VARCHAR(255)
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_NL_USAGE_TP1 PRIMARY KEY(NL_USAGE_ID)

    , CONSTRAINT KRMS_NL_USAGE_TC1 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_PROP_PARM_T
# -----------------------------------------------------------------------
drop table if exists KRMS_PROP_PARM_T
/

CREATE TABLE KRMS_PROP_PARM_T
(
      PROP_PARM_ID VARCHAR(40)
        , PROP_ID VARCHAR(40) NOT NULL
        , PARM_VAL VARCHAR(255)
        , PARM_TYP_CD VARCHAR(1) NOT NULL
        , SEQ_NO DECIMAL(5) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_PROP_PARM_TP1 PRIMARY KEY(PROP_PARM_ID)


    , INDEX KRMS_PROP_PARM_TI1 (PROP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_PROP_T
# -----------------------------------------------------------------------
drop table if exists KRMS_PROP_T
/

CREATE TABLE KRMS_PROP_T
(
      PROP_ID VARCHAR(40)
        , DESC_TXT VARCHAR(100)
        , TYP_ID VARCHAR(40)
        , DSCRM_TYP_CD VARCHAR(10) NOT NULL
        , CMPND_OP_CD VARCHAR(40)
        , RULE_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , CMPND_SEQ_NO DECIMAL(5) default null
    
    , CONSTRAINT KRMS_PROP_TP1 PRIMARY KEY(PROP_ID)


    , INDEX KRMS_PROP_FK2 (TYP_ID)
    , INDEX KRMS_PROP_TI1 (RULE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_REF_OBJ_KRMS_OBJ_T
# -----------------------------------------------------------------------
drop table if exists KRMS_REF_OBJ_KRMS_OBJ_T
/

CREATE TABLE KRMS_REF_OBJ_KRMS_OBJ_T
(
      REF_OBJ_KRMS_OBJ_ID VARCHAR(40)
        , COLLECTION_NM VARCHAR(40)
        , KRMS_OBJ_ID VARCHAR(40) NOT NULL
        , KRMS_DSCR_TYP VARCHAR(40) NOT NULL
        , REF_OBJ_ID VARCHAR(255) NOT NULL
        , REF_DSCR_TYP VARCHAR(255) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_REF_OBJ_KRMS_OBJ_TP1 PRIMARY KEY(REF_OBJ_KRMS_OBJ_ID)

    , CONSTRAINT KRMS_REF_OBJ_KRMS_OBJ_TC1 UNIQUE (COLLECTION_NM, KRMS_OBJ_ID, KRMS_DSCR_TYP, REF_OBJ_ID, REF_DSCR_TYP, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_RULE_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KRMS_RULE_ATTR_T
/

CREATE TABLE KRMS_RULE_ATTR_T
(
      RULE_ATTR_ID VARCHAR(40)
        , RULE_ID VARCHAR(40) NOT NULL
        , ATTR_DEFN_ID VARCHAR(40) NOT NULL
        , ATTR_VAL VARCHAR(400)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_RULE_ATTR_TP1 PRIMARY KEY(RULE_ATTR_ID)


    , INDEX KRMS_RULE_ATTR_TI1 (RULE_ID)
    , INDEX KRMS_RULE_ATTR_TI2 (ATTR_DEFN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_RULE_T
# -----------------------------------------------------------------------
drop table if exists KRMS_RULE_T
/

CREATE TABLE KRMS_RULE_T
(
      RULE_ID VARCHAR(40)
        , NMSPC_CD VARCHAR(40) NOT NULL
        , NM VARCHAR(100) NOT NULL
        , TYP_ID VARCHAR(40)
        , PROP_ID VARCHAR(40)
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , DESC_TXT VARCHAR(4000)
    
    , CONSTRAINT KRMS_RULE_TP1 PRIMARY KEY(RULE_ID)

    , CONSTRAINT KRMS_RULE_TC1 UNIQUE (NM, NMSPC_CD)

    , INDEX KRMS_RULE_TI1 (PROP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TERM_PARM_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_PARM_T
/

CREATE TABLE KRMS_TERM_PARM_T
(
      TERM_PARM_ID VARCHAR(40)
        , TERM_ID VARCHAR(40) NOT NULL
        , NM VARCHAR(255) NOT NULL
        , VAL VARCHAR(255)
        , VER_NBR DECIMAL(8) NOT NULL
    
    , CONSTRAINT KRMS_TERM_PARM_TP1 PRIMARY KEY(TERM_PARM_ID)


    , INDEX KRMS_TERM_PARM_TI1 (TERM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TERM_RSLVR_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_RSLVR_ATTR_T
/

CREATE TABLE KRMS_TERM_RSLVR_ATTR_T
(
      TERM_RSLVR_ATTR_ID VARCHAR(40)
        , TERM_RSLVR_ID VARCHAR(40) NOT NULL
        , ATTR_DEFN_ID VARCHAR(40) NOT NULL
        , ATTR_VAL VARCHAR(400)
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_TERM_RSLVR_ATTR_TP1 PRIMARY KEY(TERM_RSLVR_ATTR_ID)


    , INDEX KRMS_TERM_RSLVR_ATTR_TI1 (TERM_RSLVR_ID)
    , INDEX KRMS_TERM_RSLVR_ATTR_TI2 (ATTR_DEFN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TERM_RSLVR_INPUT_SPEC_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_RSLVR_INPUT_SPEC_T
/

CREATE TABLE KRMS_TERM_RSLVR_INPUT_SPEC_T
(
      TERM_SPEC_ID VARCHAR(40)
        , TERM_RSLVR_ID VARCHAR(40)
    
    , CONSTRAINT KRMS_TERM_RSLVR_INPUT_SPEC_P1 PRIMARY KEY(TERM_SPEC_ID,TERM_RSLVR_ID)


    , INDEX KRMS_INPUT_ASSET_TI1 (TERM_SPEC_ID)
    , INDEX KRMS_INPUT_ASSET_TI2 (TERM_RSLVR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TERM_RSLVR_PARM_SPEC_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_RSLVR_PARM_SPEC_T
/

CREATE TABLE KRMS_TERM_RSLVR_PARM_SPEC_T
(
      TERM_RSLVR_PARM_SPEC_ID VARCHAR(40)
        , TERM_RSLVR_ID VARCHAR(40) NOT NULL
        , NM VARCHAR(45) NOT NULL
        , VER_NBR DECIMAL(8) NOT NULL
    
    , CONSTRAINT KRMS_TERM_RSLVR_PARM_SPEC_TP1 PRIMARY KEY(TERM_RSLVR_PARM_SPEC_ID)


    , INDEX KRMS_TERM_RESLV_PARM_FK1 (TERM_RSLVR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TERM_RSLVR_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_RSLVR_T
/

CREATE TABLE KRMS_TERM_RSLVR_T
(
      TERM_RSLVR_ID VARCHAR(40)
        , NMSPC_CD VARCHAR(40) NOT NULL
        , NM VARCHAR(100) NOT NULL
        , TYP_ID VARCHAR(40) NOT NULL
        , OUTPUT_TERM_SPEC_ID VARCHAR(40) NOT NULL
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_TERM_RSLVR_TP1 PRIMARY KEY(TERM_RSLVR_ID)

    , CONSTRAINT KRMS_TERM_RSLVR_TC1 UNIQUE (NM, NMSPC_CD)

    , INDEX KRMS_TERM_RSLVR_TI2 (TYP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TERM_SPEC_CTGRY_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_SPEC_CTGRY_T
/

CREATE TABLE KRMS_TERM_SPEC_CTGRY_T
(
      TERM_SPEC_ID VARCHAR(40)
        , CTGRY_ID VARCHAR(40)
    
    , CONSTRAINT KRMS_TERM_SPEC_CTGRY_TP1 PRIMARY KEY(TERM_SPEC_ID,CTGRY_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TERM_SPEC_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_SPEC_T
/

CREATE TABLE KRMS_TERM_SPEC_T
(
      TERM_SPEC_ID VARCHAR(40)
        , NM VARCHAR(255) NOT NULL
        , TYP VARCHAR(255) NOT NULL
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) NOT NULL
        , DESC_TXT VARCHAR(255) default 'null'
        , NMSPC_CD VARCHAR(40) NOT NULL
    
    , CONSTRAINT KRMS_TERM_SPEC_TP1 PRIMARY KEY(TERM_SPEC_ID)

    , CONSTRAINT KRMS_TERM_SPEC_TC1 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TERM_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_T
/

CREATE TABLE KRMS_TERM_T
(
      TERM_ID VARCHAR(40)
        , TERM_SPEC_ID VARCHAR(40) NOT NULL
        , VER_NBR DECIMAL(8) NOT NULL
        , DESC_TXT VARCHAR(255) default 'null'
    
    , CONSTRAINT KRMS_TERM_TP1 PRIMARY KEY(TERM_ID)


    , INDEX KRMS_TERM_TI1 (TERM_SPEC_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TYP_ATTR_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TYP_ATTR_T
/

CREATE TABLE KRMS_TYP_ATTR_T
(
      TYP_ATTR_ID VARCHAR(40)
        , SEQ_NO DECIMAL(5) NOT NULL
        , TYP_ID VARCHAR(40) NOT NULL
        , ATTR_DEFN_ID VARCHAR(255) NOT NULL
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_TYP_ATTR_TP1 PRIMARY KEY(TYP_ATTR_ID)

    , CONSTRAINT KRMS_TYP_ATTR_TC1 UNIQUE (TYP_ID, ATTR_DEFN_ID)

    , INDEX KRMS_TYP_ATTR_TI1 (ATTR_DEFN_ID)
    , INDEX KRMS_TYP_ATTR_TI2 (TYP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TYP_RELN_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TYP_RELN_T
/

CREATE TABLE KRMS_TYP_RELN_T
(
      TYP_RELN_ID VARCHAR(40)
        , FROM_TYP_ID VARCHAR(40) NOT NULL
        , TO_TYP_ID VARCHAR(40) NOT NULL
        , RELN_TYP VARCHAR(40) NOT NULL
        , SEQ_NO DECIMAL(5) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
        , ACTV VARCHAR(1) default 'Y' NOT NULL
    
    , CONSTRAINT KRMS_TYP_RELN_TP1 PRIMARY KEY(TYP_RELN_ID)

    , CONSTRAINT KRMS_TYP_RELN_TC1 UNIQUE (FROM_TYP_ID, TO_TYP_ID, RELN_TYP)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRMS_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRMS_TYP_T
/

CREATE TABLE KRMS_TYP_T
(
      TYP_ID VARCHAR(40)
        , NM VARCHAR(100) NOT NULL
        , NMSPC_CD VARCHAR(40) NOT NULL
        , SRVC_NM VARCHAR(200)
        , ACTV VARCHAR(1) default 'Y' NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRMS_TYP_TP1 PRIMARY KEY(TYP_ID)

    , CONSTRAINT KRMS_TYP_TC1 UNIQUE (NM, NMSPC_CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_ADHOC_RTE_ACTN_RECIP_T
# -----------------------------------------------------------------------
drop table if exists KRNS_ADHOC_RTE_ACTN_RECIP_T
/

CREATE TABLE KRNS_ADHOC_RTE_ACTN_RECIP_T
(
      RECIP_TYP_CD DECIMAL(1)
        , ACTN_RQST_CD VARCHAR(30)
        , ACTN_RQST_RECIP_ID VARCHAR(70)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , DOC_HDR_ID VARCHAR(14)
    
    , CONSTRAINT KRNS_ADHOC_RTE_ACTN_RECIP_TP1 PRIMARY KEY(RECIP_TYP_CD,ACTN_RQST_CD,ACTN_RQST_RECIP_ID,DOC_HDR_ID)

    , CONSTRAINT KRNS_ADHOC_RTE_ACTN_RECIP_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_ADHOC_RTE_ACTN_RECIP_T2 (DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_ATT_T
# -----------------------------------------------------------------------
drop table if exists KRNS_ATT_T
/

CREATE TABLE KRNS_ATT_T
(
      NTE_ID DECIMAL(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , MIME_TYP VARCHAR(255)
        , FILE_NM VARCHAR(250)
        , ATT_ID VARCHAR(36)
        , FILE_SZ DECIMAL(14)
        , ATT_TYP_CD VARCHAR(40)
    
    , CONSTRAINT KRNS_ATT_TP1 PRIMARY KEY(NTE_ID)

    , CONSTRAINT KRNS_ATT_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_DOC_HDR_T
# -----------------------------------------------------------------------
drop table if exists KRNS_DOC_HDR_T
/

CREATE TABLE KRNS_DOC_HDR_T
(
      DOC_HDR_ID VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , FDOC_DESC VARCHAR(255)
        , ORG_DOC_HDR_ID VARCHAR(10)
        , TMPL_DOC_HDR_ID VARCHAR(14)
        , EXPLANATION VARCHAR(400)
    
    , CONSTRAINT KRNS_DOC_HDR_TP1 PRIMARY KEY(DOC_HDR_ID)

    , CONSTRAINT KRNS_DOC_HDR_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_DOC_HDR_TI3 (ORG_DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_LOOKUP_RSLT_T
# -----------------------------------------------------------------------
drop table if exists KRNS_LOOKUP_RSLT_T
/

CREATE TABLE KRNS_LOOKUP_RSLT_T
(
      LOOKUP_RSLT_ID VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , LOOKUP_DT DATETIME NOT NULL
        , SERIALZD_RSLTS LONGTEXT
    
    , CONSTRAINT KRNS_LOOKUP_RSLT_TP1 PRIMARY KEY(LOOKUP_RSLT_ID)

    , CONSTRAINT KRNS_LOOKUP_RSLT_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_LOOKUP_SEL_T
# -----------------------------------------------------------------------
drop table if exists KRNS_LOOKUP_SEL_T
/

CREATE TABLE KRNS_LOOKUP_SEL_T
(
      LOOKUP_RSLT_ID VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
        , LOOKUP_DT DATETIME NOT NULL
        , SEL_OBJ_IDS LONGTEXT
    
    , CONSTRAINT KRNS_LOOKUP_SEL_TP1 PRIMARY KEY(LOOKUP_RSLT_ID)

    , CONSTRAINT KRNS_LOOKUP_SEL_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_MAINT_DOC_ATT_LST_T
# -----------------------------------------------------------------------
drop table if exists KRNS_MAINT_DOC_ATT_LST_T
/

CREATE TABLE KRNS_MAINT_DOC_ATT_LST_T
(
      ATT_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(14) NOT NULL
        , ATT_CNTNT LONGBLOB NOT NULL
        , FILE_NM VARCHAR(150)
        , CNTNT_TYP VARCHAR(255)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRNS_MAINT_DOC_ATT_LST_TP1 PRIMARY KEY(ATT_ID)

    , CONSTRAINT KRNS_MAINT_DOC_ATT_LST_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_MAINT_DOC_ATT_LST_TI1 (DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_MAINT_DOC_ATT_T
# -----------------------------------------------------------------------
drop table if exists KRNS_MAINT_DOC_ATT_T
/

CREATE TABLE KRNS_MAINT_DOC_ATT_T
(
      DOC_HDR_ID VARCHAR(14)
        , ATT_CNTNT LONGBLOB NOT NULL
        , FILE_NM VARCHAR(150)
        , CNTNT_TYP VARCHAR(255)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
    
    , CONSTRAINT KRNS_MAINT_DOC_ATT_TP1 PRIMARY KEY(DOC_HDR_ID)

    , CONSTRAINT KRNS_MAINT_DOC_ATT_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_MAINT_DOC_T
# -----------------------------------------------------------------------
drop table if exists KRNS_MAINT_DOC_T
/

CREATE TABLE KRNS_MAINT_DOC_T
(
      DOC_HDR_ID VARCHAR(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , DOC_CNTNT LONGTEXT
    
    , CONSTRAINT KRNS_MAINT_DOC_TP1 PRIMARY KEY(DOC_HDR_ID)

    , CONSTRAINT KRNS_MAINT_DOC_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_MAINT_LOCK_T
# -----------------------------------------------------------------------
drop table if exists KRNS_MAINT_LOCK_T
/

CREATE TABLE KRNS_MAINT_LOCK_T
(
      MAINT_LOCK_REP_TXT VARCHAR(500)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , DOC_HDR_ID VARCHAR(14) NOT NULL
        , MAINT_LOCK_ID VARCHAR(14)
    
    , CONSTRAINT KRNS_MAINT_LOCK_TP1 PRIMARY KEY(MAINT_LOCK_ID)

    , CONSTRAINT KRNS_MAINT_LOCK_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_MAINT_LOCK_TI2 (DOC_HDR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_NTE_T
# -----------------------------------------------------------------------
drop table if exists KRNS_NTE_T
/

CREATE TABLE KRNS_NTE_T
(
      NTE_ID DECIMAL(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , RMT_OBJ_ID VARCHAR(36) NOT NULL
        , AUTH_PRNCPL_ID VARCHAR(40) NOT NULL
        , POST_TS DATETIME NOT NULL
        , NTE_TYP_CD VARCHAR(4) NOT NULL
        , TXT VARCHAR(800)
        , PRG_CD VARCHAR(1)
        , TPC_TXT VARCHAR(40)
    
    , CONSTRAINT KRNS_NTE_TP1 PRIMARY KEY(NTE_ID)

    , CONSTRAINT KRNS_NTE_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_NTE_TI1 (RMT_OBJ_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_NTE_TYP_T
# -----------------------------------------------------------------------
drop table if exists KRNS_NTE_TYP_T
/

CREATE TABLE KRNS_NTE_TYP_T
(
      NTE_TYP_CD VARCHAR(4)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , TYP_DESC_TXT VARCHAR(100)
        , ACTV_IND VARCHAR(1)
    
    , CONSTRAINT KRNS_NTE_TYP_TP1 PRIMARY KEY(NTE_TYP_CD)

    , CONSTRAINT KRNS_NTE_TYP_TC0 UNIQUE (OBJ_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_PESSIMISTIC_LOCK_T
# -----------------------------------------------------------------------
drop table if exists KRNS_PESSIMISTIC_LOCK_T
/

CREATE TABLE KRNS_PESSIMISTIC_LOCK_T
(
      PESSIMISTIC_LOCK_ID DECIMAL(14)
        , OBJ_ID VARCHAR(36) NOT NULL
        , VER_NBR DECIMAL(8) default 1 NOT NULL
        , LOCK_DESC_TXT VARCHAR(4000)
        , DOC_HDR_ID VARCHAR(14) NOT NULL
        , GNRT_DT DATETIME NOT NULL
        , PRNCPL_ID VARCHAR(40) NOT NULL
    
    , CONSTRAINT KRNS_PESSIMISTIC_LOCK_TP1 PRIMARY KEY(PESSIMISTIC_LOCK_ID)

    , CONSTRAINT KRNS_PESSIMISTIC_LOCK_TC0 UNIQUE (OBJ_ID)

    , INDEX KRNS_PESSIMISTIC_LOCK_TI1 (DOC_HDR_ID)
    , INDEX KRNS_PESSIMISTIC_LOCK_TI2 (PRNCPL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRNS_SESN_DOC_T
# -----------------------------------------------------------------------
drop table if exists KRNS_SESN_DOC_T
/

CREATE TABLE KRNS_SESN_DOC_T
(
      SESN_DOC_ID VARCHAR(40)
        , DOC_HDR_ID VARCHAR(14)
        , PRNCPL_ID VARCHAR(40)
        , IP_ADDR VARCHAR(60)
        , SERIALZD_DOC_FRM LONGBLOB
        , LAST_UPDT_DT DATETIME
        , CONTENT_ENCRYPTED_IND CHAR(1) default 'N'
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KRNS_SESN_DOC_TP1 PRIMARY KEY(SESN_DOC_ID,DOC_HDR_ID,PRNCPL_ID,IP_ADDR)


    , INDEX KRNS_SESN_DOC_TI1 (LAST_UPDT_DT)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_BAM_PARM_T
# -----------------------------------------------------------------------
drop table if exists KRSB_BAM_PARM_T
/

CREATE TABLE KRSB_BAM_PARM_T
(
      BAM_PARM_ID DECIMAL(14)
        , BAM_ID DECIMAL(14) NOT NULL
        , PARM LONGTEXT NOT NULL
    
    , CONSTRAINT KRSB_BAM_PARM_TP1 PRIMARY KEY(BAM_PARM_ID)


    , INDEX KREW_BAM_PARM_TI1 (BAM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_BAM_T
# -----------------------------------------------------------------------
drop table if exists KRSB_BAM_T
/

CREATE TABLE KRSB_BAM_T
(
      BAM_ID DECIMAL(14)
        , SVC_NM VARCHAR(255) NOT NULL
        , SVC_URL VARCHAR(500) NOT NULL
        , MTHD_NM VARCHAR(2000) NOT NULL
        , THRD_NM VARCHAR(500) NOT NULL
        , CALL_DT DATETIME NOT NULL
        , TGT_TO_STR VARCHAR(2000) NOT NULL
        , SRVR_IND DECIMAL(1) NOT NULL
        , EXCPN_TO_STR VARCHAR(2000)
        , EXCPN_MSG LONGTEXT
    
    , CONSTRAINT KRSB_BAM_TP1 PRIMARY KEY(BAM_ID)


    , INDEX KRSB_BAM_TI1 (SVC_NM, MTHD_NM)
    , INDEX KRSB_BAM_TI2 (SVC_NM)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_MSG_PYLD_T
# -----------------------------------------------------------------------
drop table if exists KRSB_MSG_PYLD_T
/

CREATE TABLE KRSB_MSG_PYLD_T
(
      MSG_QUE_ID DECIMAL(14)
        , MSG_PYLD LONGTEXT NOT NULL
    
    , CONSTRAINT KRSB_MSG_PYLD_TP1 PRIMARY KEY(MSG_QUE_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_MSG_QUE_T
# -----------------------------------------------------------------------
drop table if exists KRSB_MSG_QUE_T
/

CREATE TABLE KRSB_MSG_QUE_T
(
      MSG_QUE_ID DECIMAL(14)
        , DT DATETIME NOT NULL
        , EXP_DT DATETIME
        , PRIO DECIMAL(8) NOT NULL
        , STAT_CD CHAR(1) NOT NULL
        , RTRY_CNT DECIMAL(8) NOT NULL
        , IP_NBR VARCHAR(2000) NOT NULL
        , SVC_NM VARCHAR(255)
        , APPL_ID VARCHAR(255) NOT NULL
        , SVC_MTHD_NM VARCHAR(2000)
        , APP_VAL_ONE VARCHAR(2000)
        , APP_VAL_TWO VARCHAR(2000)
        , VER_NBR DECIMAL(8) default 0
    
    , CONSTRAINT KRSB_MSG_QUE_TP1 PRIMARY KEY(MSG_QUE_ID)


    , INDEX KRSB_MSG_QUE_TI1 (SVC_NM, SVC_MTHD_NM)
    , INDEX KRSB_MSG_QUE_TI2 (APPL_ID, STAT_CD, IP_NBR, DT)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_BLOB_TRIGGERS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_BLOB_TRIGGERS
/

CREATE TABLE KRSB_QRTZ_BLOB_TRIGGERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , BLOB_DATA LONGBLOB
    
    , CONSTRAINT KRSB_QRTZ_BLOB_TRIGGERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_CALENDARS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_CALENDARS
/

CREATE TABLE KRSB_QRTZ_CALENDARS
(
      CALENDAR_NAME VARCHAR(80)
        , CALENDAR LONGBLOB NOT NULL
    
    , CONSTRAINT KRSB_QRTZ_CALENDARSP1 PRIMARY KEY(CALENDAR_NAME)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_CRON_TRIGGERS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_CRON_TRIGGERS
/

CREATE TABLE KRSB_QRTZ_CRON_TRIGGERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , CRON_EXPRESSION VARCHAR(80) NOT NULL
        , TIME_ZONE_ID VARCHAR(80)
    
    , CONSTRAINT KRSB_QRTZ_CRON_TRIGGERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_FIRED_TRIGGERS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_FIRED_TRIGGERS
/

CREATE TABLE KRSB_QRTZ_FIRED_TRIGGERS
(
      ENTRY_ID VARCHAR(95)
        , TRIGGER_NAME VARCHAR(80) NOT NULL
        , TRIGGER_GROUP VARCHAR(80) NOT NULL
        , IS_VOLATILE VARCHAR(1) NOT NULL
        , INSTANCE_NAME VARCHAR(80) NOT NULL
        , FIRED_TIME DECIMAL(13) NOT NULL
        , PRIORITY DECIMAL(13) NOT NULL
        , STATE VARCHAR(16) NOT NULL
        , JOB_NAME VARCHAR(80)
        , JOB_GROUP VARCHAR(80)
        , IS_STATEFUL VARCHAR(1)
        , REQUESTS_RECOVERY VARCHAR(1)
    
    , CONSTRAINT KRSB_QRTZ_FIRED_TRIGGERSP1 PRIMARY KEY(ENTRY_ID)


    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI1 (JOB_GROUP)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI2 (JOB_NAME)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI3 (REQUESTS_RECOVERY)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI4 (IS_STATEFUL)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI5 (TRIGGER_GROUP)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI6 (INSTANCE_NAME)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI7 (TRIGGER_NAME)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI8 (TRIGGER_NAME, TRIGGER_GROUP)
    , INDEX KRSB_QRTZ_FIRED_TRIGGERS_TI9 (IS_VOLATILE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_JOB_DETAILS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_JOB_DETAILS
/

CREATE TABLE KRSB_QRTZ_JOB_DETAILS
(
      JOB_NAME VARCHAR(80)
        , JOB_GROUP VARCHAR(80)
        , DESCRIPTION VARCHAR(120)
        , JOB_CLASS_NAME VARCHAR(128) NOT NULL
        , IS_DURABLE VARCHAR(1) NOT NULL
        , IS_VOLATILE VARCHAR(1) NOT NULL
        , IS_STATEFUL VARCHAR(1) NOT NULL
        , REQUESTS_RECOVERY VARCHAR(1) NOT NULL
        , JOB_DATA LONGBLOB
    
    , CONSTRAINT KRSB_QRTZ_JOB_DETAILSP1 PRIMARY KEY(JOB_NAME,JOB_GROUP)


    , INDEX KRSB_QRTZ_JOB_DETAILS_TI1 (REQUESTS_RECOVERY)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_JOB_LISTENERS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_JOB_LISTENERS
/

CREATE TABLE KRSB_QRTZ_JOB_LISTENERS
(
      JOB_NAME VARCHAR(80)
        , JOB_GROUP VARCHAR(80)
        , JOB_LISTENER VARCHAR(80)
    
    , CONSTRAINT KRSB_QRTZ_JOB_LISTENERSP1 PRIMARY KEY(JOB_NAME,JOB_GROUP,JOB_LISTENER)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_LOCKS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_LOCKS
/

CREATE TABLE KRSB_QRTZ_LOCKS
(
      LOCK_NAME VARCHAR(40)
    
    , CONSTRAINT KRSB_QRTZ_LOCKSP1 PRIMARY KEY(LOCK_NAME)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_PAUSED_TRIGGER_GRPS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_PAUSED_TRIGGER_GRPS
/

CREATE TABLE KRSB_QRTZ_PAUSED_TRIGGER_GRPS
(
      TRIGGER_GROUP VARCHAR(80)
    
    , CONSTRAINT KRSB_QRTZ_PAUSED_TRIGGER_GRP1 PRIMARY KEY(TRIGGER_GROUP)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_SCHEDULER_STATE
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_SCHEDULER_STATE
/

CREATE TABLE KRSB_QRTZ_SCHEDULER_STATE
(
      INSTANCE_NAME VARCHAR(80)
        , LAST_CHECKIN_TIME DECIMAL(13) NOT NULL
        , CHECKIN_INTERVAL DECIMAL(13) NOT NULL
    
    , CONSTRAINT KRSB_QRTZ_SCHEDULER_STATEP1 PRIMARY KEY(INSTANCE_NAME)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_SIMPLE_TRIGGERS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_SIMPLE_TRIGGERS
/

CREATE TABLE KRSB_QRTZ_SIMPLE_TRIGGERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , REPEAT_COUNT DECIMAL(7) NOT NULL
        , REPEAT_INTERVAL DECIMAL(12) NOT NULL
        , TIMES_TRIGGERED DECIMAL(7) NOT NULL
    
    , CONSTRAINT KRSB_QRTZ_SIMPLE_TRIGGERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_TRIGGERS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_TRIGGERS
/

CREATE TABLE KRSB_QRTZ_TRIGGERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , JOB_NAME VARCHAR(80) NOT NULL
        , JOB_GROUP VARCHAR(80) NOT NULL
        , IS_VOLATILE VARCHAR(1) NOT NULL
        , DESCRIPTION VARCHAR(120)
        , NEXT_FIRE_TIME DECIMAL(13)
        , PREV_FIRE_TIME DECIMAL(13)
        , PRIORITY DECIMAL(13)
        , TRIGGER_STATE VARCHAR(16) NOT NULL
        , TRIGGER_TYPE VARCHAR(8) NOT NULL
        , START_TIME DECIMAL(13) NOT NULL
        , END_TIME DECIMAL(13)
        , CALENDAR_NAME VARCHAR(80)
        , MISFIRE_INSTR DECIMAL(2)
        , JOB_DATA LONGBLOB
    
    , CONSTRAINT KRSB_QRTZ_TRIGGERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP)


    , INDEX KRSB_QRTZ_TRIGGERS_TI1 (NEXT_FIRE_TIME)
    , INDEX KRSB_QRTZ_TRIGGERS_TI2 (NEXT_FIRE_TIME, TRIGGER_STATE)
    , INDEX KRSB_QRTZ_TRIGGERS_TI3 (TRIGGER_STATE)
    , INDEX KRSB_QRTZ_TRIGGERS_TI4 (IS_VOLATILE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_QRTZ_TRIGGER_LISTENERS
# -----------------------------------------------------------------------
drop table if exists KRSB_QRTZ_TRIGGER_LISTENERS
/

CREATE TABLE KRSB_QRTZ_TRIGGER_LISTENERS
(
      TRIGGER_NAME VARCHAR(80)
        , TRIGGER_GROUP VARCHAR(80)
        , TRIGGER_LISTENER VARCHAR(80)
    
    , CONSTRAINT KRSB_QRTZ_TRIGGER_LISTENERSP1 PRIMARY KEY(TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_LISTENER)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_SVC_DEF_T
# -----------------------------------------------------------------------
drop table if exists KRSB_SVC_DEF_T
/

CREATE TABLE KRSB_SVC_DEF_T
(
      SVC_DEF_ID VARCHAR(40)
        , SVC_NM VARCHAR(255) NOT NULL
        , SVC_URL VARCHAR(500) NOT NULL
        , INSTN_ID VARCHAR(255) NOT NULL
        , APPL_ID VARCHAR(255) NOT NULL
        , SRVR_IP VARCHAR(40) NOT NULL
        , TYP_CD VARCHAR(40) NOT NULL
        , SVC_VER VARCHAR(40) NOT NULL
        , STAT_CD VARCHAR(1) NOT NULL
        , SVC_DSCRPTR_ID VARCHAR(40) NOT NULL
        , CHKSM VARCHAR(30) NOT NULL
        , VER_NBR DECIMAL(8) default 0 NOT NULL
    
    , CONSTRAINT KRSB_SVC_DEF_TP1 PRIMARY KEY(SVC_DEF_ID)


    , INDEX KRSB_SVC_DEF_TI1 (INSTN_ID)
    , INDEX KRSB_SVC_DEF_TI2 (SVC_NM, STAT_CD)
    , INDEX KRSB_SVC_DEF_TI3 (STAT_CD)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRSB_SVC_DSCRPTR_T
# -----------------------------------------------------------------------
drop table if exists KRSB_SVC_DSCRPTR_T
/

CREATE TABLE KRSB_SVC_DSCRPTR_T
(
      SVC_DSCRPTR_ID VARCHAR(40)
        , DSCRPTR LONGTEXT NOT NULL
    
    , CONSTRAINT KRSB_SVC_DSCRPTR_TP1 PRIMARY KEY(SVC_DSCRPTR_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_COMMENT
# -----------------------------------------------------------------------
drop table if exists KSCO_COMMENT
/

CREATE TABLE KSCO_COMMENT
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , STATE VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , REFERENCE VARCHAR(255)
        , TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSCO_COMMENTP1 PRIMARY KEY(ID)


    , INDEX KSCO_COMMENT_I1 (TYPE)
    , INDEX KSCO_COMMENT_I2 (RT_DESCR_ID)
    , INDEX KSCO_COMMENT_I3 (REFERENCE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_COMMENT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSCO_COMMENT_ATTR
/

CREATE TABLE KSCO_COMMENT_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_COMMENT_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSCO_COMMENT_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_COMMENT_TYPE
# -----------------------------------------------------------------------
drop table if exists KSCO_COMMENT_TYPE
/

CREATE TABLE KSCO_COMMENT_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_COMMENT_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_COMMENT_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSCO_COMMENT_TYPE_ATTR
/

CREATE TABLE KSCO_COMMENT_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_COMMENT_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSCO_COMMENT_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_REFERENCE
# -----------------------------------------------------------------------
drop table if exists KSCO_REFERENCE
/

CREATE TABLE KSCO_REFERENCE
(
      ID VARCHAR(255)
        , REFERENCE_ID VARCHAR(255)
        , REFERENCE_TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_REFERENCEP1 PRIMARY KEY(ID)

    , CONSTRAINT SYS_C0033779 UNIQUE (REFERENCE_ID, REFERENCE_TYPE)

    , INDEX KSCO_REFERENCE_I1 (REFERENCE_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_REFERENCE_TYPE
# -----------------------------------------------------------------------
drop table if exists KSCO_REFERENCE_TYPE
/

CREATE TABLE KSCO_REFERENCE_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_REFERENCE_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_REFERENCE_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSCO_REFERENCE_TYPE_ATTR
/

CREATE TABLE KSCO_REFERENCE_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_REFERENCE_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSCO_REFERENCE_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_RICH_TEXT_T
# -----------------------------------------------------------------------
drop table if exists KSCO_RICH_TEXT_T
/

CREATE TABLE KSCO_RICH_TEXT_T
(
      ID VARCHAR(255)
        , FORMATTED VARCHAR(2000)
        , PLAIN VARCHAR(2000)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_RICH_TEXT_TP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_TAG
# -----------------------------------------------------------------------
drop table if exists KSCO_TAG
/

CREATE TABLE KSCO_TAG
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME_SPACE VARCHAR(255)
        , PREDICATE VARCHAR(255)
        , STATE VARCHAR(255)
        , VAL VARCHAR(255)
        , REFERENCE VARCHAR(255)
        , TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSCO_TAGP1 PRIMARY KEY(ID)


    , INDEX KSCO_TAG_I1 (REFERENCE)
    , INDEX KSCO_TAG_I2 (TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_TAG_ATTR
# -----------------------------------------------------------------------
drop table if exists KSCO_TAG_ATTR
/

CREATE TABLE KSCO_TAG_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_TAG_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSCO_TAG_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_TAG_TYPE
# -----------------------------------------------------------------------
drop table if exists KSCO_TAG_TYPE
/

CREATE TABLE KSCO_TAG_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_TAG_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSCO_TAG_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSCO_TAG_TYPE_ATTR
/

CREATE TABLE KSCO_TAG_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSCO_TAG_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSCO_TAG_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_DOCUMENT
# -----------------------------------------------------------------------
drop table if exists KSDO_DOCUMENT
/

CREATE TABLE KSDO_DOCUMENT
(
      DOC_ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , DOCUMENT LONGTEXT
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , FILE_NAME VARCHAR(255)
        , NAME VARCHAR(255)
        , STATE VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSDO_DOCUMENTP1 PRIMARY KEY(DOC_ID)


    , INDEX KSDO_DOCUMENT_I1 (RT_DESCR_ID)
    , INDEX KSDO_DOCUMENT_I2 (TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_DOCUMENT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSDO_DOCUMENT_ATTR
/

CREATE TABLE KSDO_DOCUMENT_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_DOCUMENT_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSDO_DOCUMENT_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_DOCUMENT_CATEGORY
# -----------------------------------------------------------------------
drop table if exists KSDO_DOCUMENT_CATEGORY
/

CREATE TABLE KSDO_DOCUMENT_CATEGORY
(
      CATEGORY_ID VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_DOCUMENT_CATEGORYP1 PRIMARY KEY(CATEGORY_ID)


    , INDEX KSDO_DOCUMENT_CATEGORY_I1 (RT_DESCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_DOCUMENT_CATEGORY_ATTR
# -----------------------------------------------------------------------
drop table if exists KSDO_DOCUMENT_CATEGORY_ATTR
/

CREATE TABLE KSDO_DOCUMENT_CATEGORY_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_DOCUMENT_CATEGORY_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSDO_DOC_CATEGORY_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_DOCUMENT_JN_DOC_CATEGORY
# -----------------------------------------------------------------------
drop table if exists KSDO_DOCUMENT_JN_DOC_CATEGORY
/

CREATE TABLE KSDO_DOCUMENT_JN_DOC_CATEGORY
(
      DOC_ID VARCHAR(255) NOT NULL
        , CATEGORY_ID VARCHAR(255) NOT NULL
    


    , INDEX KSDO_DOC_JN_DOC_CATEGORY_I1 (DOC_ID)
    , INDEX KSDO_DOC_JN_DOC_CATEGORY_I2 (CATEGORY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_DOCUMENT_TYPE
# -----------------------------------------------------------------------
drop table if exists KSDO_DOCUMENT_TYPE
/

CREATE TABLE KSDO_DOCUMENT_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_DOCUMENT_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_DOCUMENT_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSDO_DOCUMENT_TYPE_ATTR
/

CREATE TABLE KSDO_DOCUMENT_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_DOCUMENT_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSDO_DOCUMENT_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_REF_DOC_RELTN
# -----------------------------------------------------------------------
drop table if exists KSDO_REF_DOC_RELTN
/

CREATE TABLE KSDO_REF_DOC_RELTN
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , REF_OBJ_ID VARCHAR(255)
        , ST VARCHAR(255)
        , TITLE VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , DOC_ID VARCHAR(255)
        , TYPE_KEY VARCHAR(255)
        , REF_OBJ_TYPE_KEY VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSDO_REF_DOC_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSDO_REF_DOC_RELTN_I1 (TYPE_KEY)
    , INDEX KSDO_REF_DOC_RELTN_I2 (REF_OBJ_TYPE_KEY)
    , INDEX KSDO_REF_DOC_RELTN_I3 (RT_DESCR_ID)
    , INDEX KSDO_REF_DOC_RELTN_I4 (DOC_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_REF_DOC_RELTN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSDO_REF_DOC_RELTN_TYPE
/

CREATE TABLE KSDO_REF_DOC_RELTN_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_REF_DOC_RELTN_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_REF_DOC_RELTN_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSDO_REF_DOC_RELTN_TYPE_ATTR
/

CREATE TABLE KSDO_REF_DOC_RELTN_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_REF_DOC_RELTN_TYPE_ATTP1 PRIMARY KEY(ID)


    , INDEX KSDO_REF_DOC_REL_TYP_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_REF_DOC_REL_ATTR
# -----------------------------------------------------------------------
drop table if exists KSDO_REF_DOC_REL_ATTR
/

CREATE TABLE KSDO_REF_DOC_REL_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_REF_DOC_REL_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSDO_REF_DOC_REL_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_REF_OBJ_SUB_TYPE
# -----------------------------------------------------------------------
drop table if exists KSDO_REF_OBJ_SUB_TYPE
/

CREATE TABLE KSDO_REF_OBJ_SUB_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , REF_OBJ_TYPE_KEY VARCHAR(255)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_REF_OBJ_SUB_TYPEP1 PRIMARY KEY(TYPE_KEY)


    , INDEX KSDO_REF_OBJ_SUB_TYPE_I1 (REF_OBJ_TYPE_KEY)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_REF_OBJ_SUB_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSDO_REF_OBJ_SUB_TYPE_ATTR
/

CREATE TABLE KSDO_REF_OBJ_SUB_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_REF_OBJ_SUB_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSDO_REF_OBJ_SUB_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_REF_OBJ_TYPE
# -----------------------------------------------------------------------
drop table if exists KSDO_REF_OBJ_TYPE
/

CREATE TABLE KSDO_REF_OBJ_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_REF_OBJ_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_REF_OBJ_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSDO_REF_OBJ_TYPE_ATTR
/

CREATE TABLE KSDO_REF_OBJ_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_REF_OBJ_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSDO_REF_OBJ_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_REF_REL_TYP_JN_SUB_TYP
# -----------------------------------------------------------------------
drop table if exists KSDO_REF_REL_TYP_JN_SUB_TYP
/

CREATE TABLE KSDO_REF_REL_TYP_JN_SUB_TYP
(
      REF_DOC_RELTN_TYPE_KEY VARCHAR(255) NOT NULL
        , REF_OBJ_SUB_TYPE_KEY VARCHAR(255) NOT NULL
    


    , INDEX KSDO_REF_REL_TYP_JN_SUBTYP_I1 (REF_OBJ_SUB_TYPE_KEY)
    , INDEX KSDO_REF_REL_TYP_JN_SUBTYP_I2 (REF_DOC_RELTN_TYPE_KEY)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSDO_RICH_TEXT_T
# -----------------------------------------------------------------------
drop table if exists KSDO_RICH_TEXT_T
/

CREATE TABLE KSDO_RICH_TEXT_T
(
      ID VARCHAR(255)
        , FORMATTED VARCHAR(2000)
        , PLAIN VARCHAR(2000)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSDO_RICH_TEXT_TP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEM_CTX_JN_ENUM_VAL_T
# -----------------------------------------------------------------------
drop table if exists KSEM_CTX_JN_ENUM_VAL_T
/

CREATE TABLE KSEM_CTX_JN_ENUM_VAL_T
(
      ENUM_VAL_ID VARCHAR(255) NOT NULL
        , CTX_ID VARCHAR(255) NOT NULL
    


    , INDEX KSEM_CTX_JN_ENUM_VAL_T_I1 (CTX_ID)
    , INDEX KSEM_CTX_JN_ENUM_VAL_T_I2 (ENUM_VAL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEM_CTX_T
# -----------------------------------------------------------------------
drop table if exists KSEM_CTX_T
/

CREATE TABLE KSEM_CTX_T
(
      ID VARCHAR(255)
        , CTX_KEY VARCHAR(255)
        , CTX_VAL VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , CREATEID VARCHAR(255)
        , UPDATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATETIME DATETIME
    
    , CONSTRAINT KSEM_CTX_TP1 PRIMARY KEY(ID)

    , CONSTRAINT SYS_C00285110 UNIQUE (CTX_KEY, CTX_VAL)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEM_ENUM_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEM_ENUM_ATTR
/

CREATE TABLE KSEM_ENUM_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEM_ENUM_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEM_ENUM_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEM_ENUM_T
# -----------------------------------------------------------------------
drop table if exists KSEM_ENUM_T
/

CREATE TABLE KSEM_ENUM_T
(
      ENUM_KEY VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , CREATEID VARCHAR(255)
        , UPDATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATETIME DATETIME
        , ENUM_TYPE VARCHAR(255)
        , ENUM_STATE VARCHAR(255)
        , DESCR_FORMATTED VARCHAR(4000)
    
    , CONSTRAINT KSEM_ENUM_TP1 PRIMARY KEY(ENUM_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEM_ENUM_VAL_T
# -----------------------------------------------------------------------
drop table if exists KSEM_ENUM_VAL_T
/

CREATE TABLE KSEM_ENUM_VAL_T
(
      ID VARCHAR(255)
        , ABBREV_VAL VARCHAR(255)
        , CD VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VAL VARCHAR(255)
        , ENUM_KEY VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , CREATEID VARCHAR(255)
        , UPDATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATETIME DATETIME
        , SORT_KEY VARCHAR(255)
    
    , CONSTRAINT KSEM_ENUM_VAL_TP1 PRIMARY KEY(ID)


    , INDEX KSEM_ENUM_VAL_I2 (CD)
    , INDEX KSEM_ENUM_VAL_T_I1 (ENUM_KEY)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_APPT
# -----------------------------------------------------------------------
drop table if exists KSEN_APPT
/

CREATE TABLE KSEN_APPT
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(255)
        , APPT_TYPE VARCHAR(255) NOT NULL
        , APPT_STATE VARCHAR(255) NOT NULL
        , PERS_ID VARCHAR(255) NOT NULL
        , SLOT_ID VARCHAR(255) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_APPTP1 PRIMARY KEY(ID)


    , INDEX KSEN_APPT_I1 (APPT_TYPE)
    , INDEX KSEN_APPT_I2 (PERS_ID)
    , INDEX KSEN_APPT_IF1 (SLOT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_APPT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_APPT_ATTR
/

CREATE TABLE KSEN_APPT_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_APPT_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_APPT_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_APPT_SLOT
# -----------------------------------------------------------------------
drop table if exists KSEN_APPT_SLOT
/

CREATE TABLE KSEN_APPT_SLOT
(
      OBJ_ID VARCHAR(255)
        , APPT_SLOT_TYPE VARCHAR(255) NOT NULL
        , APPT_SLOT_STATE VARCHAR(255) NOT NULL
        , START_DT DATETIME NOT NULL
        , END_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , ID VARCHAR(255)
        , APPT_WINDOW_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_APPT_SLOTP1 PRIMARY KEY(ID)


    , INDEX KSEN_APPT_SLOT_IF1 (APPT_WINDOW_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_APPT_SLOT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_APPT_SLOT_ATTR
/

CREATE TABLE KSEN_APPT_SLOT_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_APPT_SLOT_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_APPT_SLOT_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_APPT_WINDOW
# -----------------------------------------------------------------------
drop table if exists KSEN_APPT_WINDOW
/

CREATE TABLE KSEN_APPT_WINDOW
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(255)
        , APPT_WINDOW_TYPE VARCHAR(255) NOT NULL
        , APPT_WINDOW_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255) NOT NULL
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , START_DT DATETIME
        , END_DT DATETIME
        , SR_WEEKDAYS VARCHAR(255)
        , SR_START_TIME_MS DECIMAL(22)
        , SR_END_TIME_MS DECIMAL(22)
        , SR_START_INTVL_DUR_TYPE VARCHAR(255)
        , SR_START_INTVL_TIME_QTY DECIMAL(22)
        , SR_DUR_TYPE VARCHAR(255)
        , SR_DUR_TIME_QTY DECIMAL(22)
        , PRD_MSTONE_ID VARCHAR(255)
        , ASSIGNED_POPULATION_ID VARCHAR(255)
        , ASSIGNED_ORDER_TYPE VARCHAR(255)
        , MAX_APPT_PER_SLOT DECIMAL(22)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_APPT_WINDOWP1 PRIMARY KEY(ID)


    , INDEX KSEN_APPT_WINDOW_I1 (APPT_WINDOW_TYPE)
    , INDEX KSEN_APPT_WINDOW_I2 (ASSIGNED_POPULATION_ID)
    , INDEX KSEN_APPT_WINDOW_I3 (PRD_MSTONE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_APPT_WINDOW_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_APPT_WINDOW_ATTR
/

CREATE TABLE KSEN_APPT_WINDOW_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_APPT_WINDOW_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_APPT_WINDOW_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ATP
# -----------------------------------------------------------------------
drop table if exists KSEN_ATP
/

CREATE TABLE KSEN_ATP
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATP_TYPE VARCHAR(255) NOT NULL
        , ATP_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000) NOT NULL
        , DESCR_FORMATTED VARCHAR(4000)
        , ATP_CD VARCHAR(255)
        , END_DT DATETIME NOT NULL
        , START_DT DATETIME NOT NULL
        , ADMIN_ORG_ID VARCHAR(50)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_ATPP1 PRIMARY KEY(ID)


    , INDEX KSEN_ATP_I1 (ATP_TYPE)
    , INDEX KSEN_ATP_I2 (ATP_CD)
    , INDEX KSEN_ATP_I3 (START_DT)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ATPATP_RELTN
# -----------------------------------------------------------------------
drop table if exists KSEN_ATPATP_RELTN
/

CREATE TABLE KSEN_ATPATP_RELTN
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATP_TYPE VARCHAR(255) NOT NULL
        , ATP_STATE VARCHAR(255) NOT NULL
        , ATP_ID VARCHAR(255) NOT NULL
        , RELATED_ATP_ID VARCHAR(255) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_ATPATP_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSEN_ATPATP_RELTN_I1 (ATP_TYPE)
    , INDEX KSEN_ATPATP_RELTN_IF1 (ATP_ID)
    , INDEX KSEN_ATPATP_RELTN_IF2 (RELATED_ATP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ATPATP_RELTN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_ATPATP_RELTN_ATTR
/

CREATE TABLE KSEN_ATPATP_RELTN_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_ATPATP_RELTN_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_ATPATP_RELTN_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ATPMSTONE_RELTN
# -----------------------------------------------------------------------
drop table if exists KSEN_ATPMSTONE_RELTN
/

CREATE TABLE KSEN_ATPMSTONE_RELTN
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , ATP_ID VARCHAR(255)
        , MSTONE_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_ATPMSTONE_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSEN_ATPMSTONE_RELTN_IF1 (ATP_ID)
    , INDEX KSEN_ATPMSTONE_RELTN_IF2 (MSTONE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ATP_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_ATP_ATTR
/

CREATE TABLE KSEN_ATP_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_ATP_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_ATP_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_CODE_GENERATOR_LOCKS
# -----------------------------------------------------------------------
drop table if exists KSEN_CODE_GENERATOR_LOCKS
/

CREATE TABLE KSEN_CODE_GENERATOR_LOCKS
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , CODE VARCHAR(255) NOT NULL
        , UNIQUE_KEY VARCHAR(255) NOT NULL
        , NAMESPACE VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_CODE_GENERATOR_LOCKSP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_CODE_GENERATOR_LOCKS_I1 UNIQUE (CODE, UNIQUE_KEY, NAMESPACE)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_CO_AO_CLUSTER
# -----------------------------------------------------------------------
drop table if exists KSEN_CO_AO_CLUSTER
/

CREATE TABLE KSEN_CO_AO_CLUSTER
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , AO_CLUSTER_TYPE VARCHAR(255) NOT NULL
        , AO_CLUSTER_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , FORMAT_OFFERING_ID VARCHAR(255) NOT NULL
        , PRIVATE_NAME VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_CO_AO_CLUSTERP1 PRIMARY KEY(ID)


    , INDEX KSEN_CO_AO_CLUSTER_I1 (FORMAT_OFFERING_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_CO_AO_CLUSTER_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_CO_AO_CLUSTER_ATTR
/

CREATE TABLE KSEN_CO_AO_CLUSTER_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_CO_AO_CLUSTER_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_CO_AO_CLUSTER_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_CO_AO_CLUSTER_SET
# -----------------------------------------------------------------------
drop table if exists KSEN_CO_AO_CLUSTER_SET
/

CREATE TABLE KSEN_CO_AO_CLUSTER_SET
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , AO_CLUSTER_ID VARCHAR(255) NOT NULL
        , ACTIVITY_OFFERING_TYPE VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_CO_AO_CLUSTER_SETP1 PRIMARY KEY(ID)


    , INDEX KSEN_CO_AO_CLISTER_SET_IF1 (AO_CLUSTER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_CO_AO_CLUSTER_SET_AO
# -----------------------------------------------------------------------
drop table if exists KSEN_CO_AO_CLUSTER_SET_AO
/

CREATE TABLE KSEN_CO_AO_CLUSTER_SET_AO
(
      AOC_SET_ID VARCHAR(255)
        , ACTIVITY_OFFERING_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_CO_AO_CLUSTER_SET_AOP1 PRIMARY KEY(AOC_SET_ID,ACTIVITY_OFFERING_ID)


    , INDEX XIF1KSEN_CO_AOC_SET_AO_IF1 (AOC_SET_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_CO_SEAT_POOL_DEFN
# -----------------------------------------------------------------------
drop table if exists KSEN_CO_SEAT_POOL_DEFN
/

CREATE TABLE KSEN_CO_SEAT_POOL_DEFN
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , SEAT_POOL_DEFN_TYPE VARCHAR(255) NOT NULL
        , SEAT_POOL_DEFN_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , ACTIVITY_OFFERING_ID VARCHAR(255)
        , EXPIR_MSTONE_TYPE VARCHAR(255)
        , PERCENTAGE_IND DECIMAL(1)
        , SEAT_LIMIT DECIMAL(22)
        , PROCESSING_PRIORITY DECIMAL(22)
        , POPULATION_ID VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_CO_SEAT_POOL_DEFNP1 PRIMARY KEY(ID)


    , INDEX KSEN_CO_SEAT_POOL_DEFN_I1 (ACTIVITY_OFFERING_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_CO_SEAT_POOL_DEFN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_CO_SEAT_POOL_DEFN_ATTR
/

CREATE TABLE KSEN_CO_SEAT_POOL_DEFN_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_CO_SEAT_POOL_DEFN_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_CO_SPD_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ENROLLMENT_FEE
# -----------------------------------------------------------------------
drop table if exists KSEN_ENROLLMENT_FEE
/

CREATE TABLE KSEN_ENROLLMENT_FEE
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ENRL_FEE_TYPE VARCHAR(255) NOT NULL
        , ENRL_FEE_STATE VARCHAR(255) NOT NULL
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , CURRENCY_TYPE VARCHAR(255)
        , CURRENCY_QUANTITY DECIMAL(22)
        , ORG_ID VARCHAR(255)
        , REF_OBJECT_URI VARCHAR(255)
        , REF_OBJECT_ID VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_ENROLLMENT_FEEP1 PRIMARY KEY(ID)


    , INDEX KSEN_ENRL_FEE_I1 (ENRL_FEE_TYPE)
    , INDEX KSEN_ENRL_FEE_I2 (REF_OBJECT_URI)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ENROLLMENT_FEE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_ENROLLMENT_FEE_ATTR
/

CREATE TABLE KSEN_ENROLLMENT_FEE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_ENROLLMENT_FEE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_ENRL_FEE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_HOLD
# -----------------------------------------------------------------------
drop table if exists KSEN_HOLD
/

CREATE TABLE KSEN_HOLD
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , HOLD_TYPE VARCHAR(255) NOT NULL
        , HOLD_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , ISSUE_ID VARCHAR(55) NOT NULL
        , PERS_ID VARCHAR(255) NOT NULL
        , EFF_DT DATETIME NOT NULL
        , RELEASED_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_HOLDP1 PRIMARY KEY(ID)


    , INDEX KSEN_HOLD_I1 (HOLD_TYPE)
    , INDEX KSEN_HOLD_I2 (PERS_ID, ISSUE_ID)
    , INDEX KSEN_HOLD_IF1 (ISSUE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_HOLD_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_HOLD_ATTR
/

CREATE TABLE KSEN_HOLD_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_HOLD_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_HOLD_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_HOLD_ISSUE
# -----------------------------------------------------------------------
drop table if exists KSEN_HOLD_ISSUE
/

CREATE TABLE KSEN_HOLD_ISSUE
(
      ID VARCHAR(55)
        , OBJ_ID VARCHAR(36)
        , HOLD_ISSUE_TYPE VARCHAR(255) NOT NULL
        , HOLD_ISSUE_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(255)
        , ORG_ID VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_HOLD_ISSUEP1 PRIMARY KEY(ID)


    , INDEX KSEN_HOLD_ISSUE_I1 (HOLD_ISSUE_TYPE)
    , INDEX KSEN_HOLD_ISSUE_I2 (ORG_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_HOLD_ISSUE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_HOLD_ISSUE_ATTR
/

CREATE TABLE KSEN_HOLD_ISSUE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(55)
    
    , CONSTRAINT KSEN_HOLD_ISSUE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_HOLD_ISSUE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR
/

CREATE TABLE KSEN_LPR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LPR_TYPE VARCHAR(255) NOT NULL
        , LPR_STATE VARCHAR(255) NOT NULL
        , LUI_ID VARCHAR(255) NOT NULL
        , PERS_ID VARCHAR(255) NOT NULL
        , COMMIT_PERCT DECIMAL(22)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_I1 (PERS_ID, LPR_TYPE, LPR_STATE)
    , INDEX KSEN_LPR_I2 (LUI_ID, LPR_TYPE, LPR_STATE)
    , INDEX KSEN_LPR_I3 (PERS_ID, LUI_ID)
    , INDEX KSEN_LPR_I4 (PERS_ID, LPR_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_ATTR
/

CREATE TABLE KSEN_LPR_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_RESULT_VAL_GRP
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_RESULT_VAL_GRP
/

CREATE TABLE KSEN_LPR_RESULT_VAL_GRP
(
      ID VARCHAR(255)
        , LPR_ID VARCHAR(255) NOT NULL
        , RESULT_VAL_GRP_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_LPR_RESULT_VAL_GRPP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_LPR_RESULT_VAL_GRP_I1 UNIQUE (LPR_ID, RESULT_VAL_GRP_ID)

    , INDEX KSEN_LPR_RESULT_VAL_GRP_IF1 (LPR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_ROSTER
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_ROSTER
/

CREATE TABLE KSEN_LPR_ROSTER
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LPR_ROSTER_TYPE VARCHAR(255) NOT NULL
        , LPR_ROSTER_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , MAX_CAPACITY DECIMAL(22)
        , CHECK_IN_REQ_IND VARCHAR(1)
        , CHECK_IN_FREQ_DUR_TYPE VARCHAR(255)
        , CHECK_IN_FREQ_TIME_QTY DECIMAL(22)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_ROSTERP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_ROSTER_I1 (LPR_ROSTER_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_ROSTER_ASSO_LUI_ID
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_ROSTER_ASSO_LUI_ID
/

CREATE TABLE KSEN_LPR_ROSTER_ASSO_LUI_ID
(
      ID VARCHAR(255)
        , LPR_ROSTER_ID VARCHAR(255) NOT NULL
        , LUI_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_LPR_ROSTER_ASSO_LUI_IDP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_LPR_ROSTER_ASSO_LUI_ID_I1 UNIQUE (LPR_ROSTER_ID, LUI_ID)

    , INDEX KSEN_LPR_ROSTER_ASSO_LUI_ID_I2 (LUI_ID)
    , INDEX KSEN_LPR_ROSTER_ASSO_LUI_ID_IF (LPR_ROSTER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_ROSTER_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_ROSTER_ATTR
/

CREATE TABLE KSEN_LPR_ROSTER_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_ROSTER_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_ROSTER_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_ROSTER_ENTRY
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_ROSTER_ENTRY
/

CREATE TABLE KSEN_LPR_ROSTER_ENTRY
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LPR_ROSTER_ENTRY_TYPE VARCHAR(255) NOT NULL
        , LPR_ROSTER_ENTRY_STATE VARCHAR(255) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , POSITION DECIMAL(22)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , LPR_ID VARCHAR(255)
        , LPR_ROSTER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_ROSTER_ENTRYP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_ROSTER_ENTRY_IF1 (LPR_ID)
    , INDEX KSEN_LPR_ROSTER_ENTRY_IF2 (LPR_ROSTER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_ROSTER_ENTRY_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_ROSTER_ENTRY_ATTR
/

CREATE TABLE KSEN_LPR_ROSTER_ENTRY_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_ROSTER_ENTRY_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_ROSTER_ENTRY_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_TRANS
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_TRANS
/

CREATE TABLE KSEN_LPR_TRANS
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LPR_TRANS_TYPE VARCHAR(255) NOT NULL
        , LPR_TRANS_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , REQUESTING_PERS_ID VARCHAR(255)
        , ATP_ID VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , VER_NBR DECIMAL(9) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_TRANSP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_TRANS_I1 (LPR_TRANS_STATE)
    , INDEX KSEN_LPR_TRANS_I2 (REQUESTING_PERS_ID, ATP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_TRANS_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_TRANS_ATTR
/

CREATE TABLE KSEN_LPR_TRANS_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_TRANS_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_TRANS_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_TRANS_ITEM
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_TRANS_ITEM
/

CREATE TABLE KSEN_LPR_TRANS_ITEM
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LPR_TRANS_ITEM_TYPE VARCHAR(255) NOT NULL
        , LPR_TRANS_ITEM_STATE VARCHAR(255) NOT NULL
        , PERS_ID VARCHAR(255)
        , NEW_LUI_ID VARCHAR(255)
        , EXISTING_LUI_ID VARCHAR(255)
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , VER_NBR DECIMAL(9) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , GROUP_ID VARCHAR(255)
        , LTI_RESULTING_LPR_ID VARCHAR(255)
        , LTI_RESULT_MESSAGE VARCHAR(4000)
        , LTI_RESULT_STATUS VARCHAR(255)
        , LPR_TRANS_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_TRANS_ITEMP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_TRANS_ITEM_I1 (PERS_ID)
    , INDEX KSEN_LPR_TRANS_ITEM_I2 (NEW_LUI_ID)
    , INDEX KSEN_LPR_TRANS_ITEM_I3 (EXISTING_LUI_ID)
    , INDEX KSEN_LPR_TRANS_ITEM_I4 (LTI_RESULTING_LPR_ID)
    , INDEX KSEN_LPR_TRANS_ITEM_IF1 (LPR_TRANS_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_TRANS_ITEM_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_TRANS_ITEM_ATTR
/

CREATE TABLE KSEN_LPR_TRANS_ITEM_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_TRANS_ITEM_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_TRANS_ITEM_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_TRANS_ITEM_RQST_OPT
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_TRANS_ITEM_RQST_OPT
/

CREATE TABLE KSEN_LPR_TRANS_ITEM_RQST_OPT
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , OPTION_KEY VARCHAR(255)
        , OPTION_VALUE VARCHAR(255)
        , LPR_TRANS_ITEM_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LPR_TRANS_ITEM_RQST_OPP1 PRIMARY KEY(ID)


    , INDEX KSEN_LPR_LTI_RQST_OPT_IF1 (LPR_TRANS_ITEM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LPR_TRANS_ITEM_RVG
# -----------------------------------------------------------------------
drop table if exists KSEN_LPR_TRANS_ITEM_RVG
/

CREATE TABLE KSEN_LPR_TRANS_ITEM_RVG
(
      ID VARCHAR(255)
        , LPR_TRANS_ITEM_ID VARCHAR(255) NOT NULL
        , RESULT_VAL_GRP_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_LPR_TRANS_ITEM_RVGP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_LPR_LTI_RVG_I1 UNIQUE (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID)

    , INDEX KSEN_LPR_LTI_RVG_IF1 (LPR_TRANS_ITEM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRC_RESULT_SCALE
# -----------------------------------------------------------------------
drop table if exists KSEN_LRC_RESULT_SCALE
/

CREATE TABLE KSEN_LRC_RESULT_SCALE
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , RESULT_SCALE_TYPE VARCHAR(255) NOT NULL
        , RESULT_SCALE_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , RANGE_MIN_VALUE VARCHAR(255)
        , RANGE_MAX_VALUE VARCHAR(255)
        , RANGE_INCREMENT VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRC_RESULT_SCALEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRC_RESULT_SCALE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LRC_RESULT_SCALE_ATTR
/

CREATE TABLE KSEN_LRC_RESULT_SCALE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRC_RESULT_SCALE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LRC_RESULT_SCALE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRC_RESULT_VALUE
# -----------------------------------------------------------------------
drop table if exists KSEN_LRC_RESULT_VALUE
/

CREATE TABLE KSEN_LRC_RESULT_VALUE
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , RESULT_VALUE_TYPE VARCHAR(255) NOT NULL
        , RESULT_VALUE_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , RESULT_SCALE_ID VARCHAR(255) NOT NULL
        , NUMERIC_VALUE DECIMAL(22)
        , RESULT_VALUE VARCHAR(255) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRC_RESULT_VALUEP1 PRIMARY KEY(ID)


    , INDEX KSEN_LRC_RESULT_VALUE_I1 (RESULT_VALUE_TYPE, RESULT_VALUE_STATE, RESULT_VALUE)
    , INDEX KSEN_LRC_RESULT_VAL_IF1 (RESULT_SCALE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRC_RESULT_VALUE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LRC_RESULT_VALUE_ATTR
/

CREATE TABLE KSEN_LRC_RESULT_VALUE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRC_RESULT_VALUE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LRC_RESULT_VALUE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRC_RVG
# -----------------------------------------------------------------------
drop table if exists KSEN_LRC_RVG
/

CREATE TABLE KSEN_LRC_RVG
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , RVG_TYPE VARCHAR(255) NOT NULL
        , RVG_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , RESULT_SCALE_ID VARCHAR(255) NOT NULL
        , RANGE_MIN_VALUE VARCHAR(255)
        , RANGE_MAX_VALUE VARCHAR(255)
        , RANGE_INCREMENT VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRC_RVGP1 PRIMARY KEY(ID)


    , INDEX KSEN_LRC_RVG_I1 (RVG_TYPE)
    , INDEX KSEN_LRC_RVG_IF1 (RESULT_SCALE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRC_RVG_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LRC_RVG_ATTR
/

CREATE TABLE KSEN_LRC_RVG_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRC_RVG_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LRC_RVG_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRC_RVG_RESULT_VALUE
# -----------------------------------------------------------------------
drop table if exists KSEN_LRC_RVG_RESULT_VALUE
/

CREATE TABLE KSEN_LRC_RVG_RESULT_VALUE
(
      RVG_ID VARCHAR(255)
        , RESULT_VALUE_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRC_RVG_RESULT_VALUEP1 PRIMARY KEY(RVG_ID,RESULT_VALUE_ID)


    , INDEX KSEN_LRC_RVG_RV_IF1 (RVG_ID)
    , INDEX KSEN_LRC_RVG_RV_IF2 (RESULT_VALUE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRR
# -----------------------------------------------------------------------
drop table if exists KSEN_LRR
/

CREATE TABLE KSEN_LRR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , NAME VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , STATE_ID VARCHAR(255)
        , TYPE_ID VARCHAR(255)
        , LPR_ID VARCHAR(255)
        , RESULT_VALUE_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRRP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRR_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LRR_ATTR
/

CREATE TABLE KSEN_LRR_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRR_ATTRP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRR_RES_SOURCE
# -----------------------------------------------------------------------
drop table if exists KSEN_LRR_RES_SOURCE
/

CREATE TABLE KSEN_LRR_RES_SOURCE
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , NAME VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , TYPE_ID VARCHAR(255)
        , ARTICULATE_ID VARCHAR(255)
        , RES_TRANS_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LRR_RES_SOURCEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRR_RES_SOURCE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LRR_RES_SOURCE_ATTR
/

CREATE TABLE KSEN_LRR_RES_SOURCE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER_ID VARCHAR(255)
    



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRR_RES_SRC_RELTN
# -----------------------------------------------------------------------
drop table if exists KSEN_LRR_RES_SRC_RELTN
/

CREATE TABLE KSEN_LRR_RES_SRC_RELTN
(
      LRR_ID VARCHAR(255)
        , RES_SRC_ID VARCHAR(255)
    



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LRR_TYPE
# -----------------------------------------------------------------------
drop table if exists KSEN_LRR_TYPE
/

CREATE TABLE KSEN_LRR_TYPE
(
      TYPE_KEY VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , REF_OBJECT_URI VARCHAR(255)
    
    , CONSTRAINT KSEN_LRR_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI
/

CREATE TABLE KSEN_LUI
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LUI_TYPE VARCHAR(255) NOT NULL
        , LUI_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , CLU_ID VARCHAR(255)
        , ATP_ID VARCHAR(255)
        , SCHEDULE_ID VARCHAR(255)
        , MAX_SEATS DECIMAL(10)
        , MIN_SEATS DECIMAL(10)
        , REF_URL VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUIP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUI_I1 (CLU_ID, ATP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUICAPACITY_RELTN
# -----------------------------------------------------------------------
drop table if exists KSEN_LUICAPACITY_RELTN
/

CREATE TABLE KSEN_LUICAPACITY_RELTN
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LUI_ID VARCHAR(255)
        , LUI_CAPACITY_ID VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUICAPACITY_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUICAPACITY_RELTN_IF1 (LUI_ID)
    , INDEX KSEN_LUICAPACITY_RELTN_IF2 (LUI_CAPACITY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUILUI_RELTN
# -----------------------------------------------------------------------
drop table if exists KSEN_LUILUI_RELTN
/

CREATE TABLE KSEN_LUILUI_RELTN
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LUILUI_RELTN_TYPE VARCHAR(255) NOT NULL
        , LUILUI_RELTN_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , LUI_ID VARCHAR(255)
        , RELATED_LUI_ID VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUILUI_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUILUI_RELTN_I1 (LUILUI_RELTN_TYPE, RELATED_LUI_ID)
    , INDEX KSEN_LUILUI_RELTN_I2 (LUILUI_RELTN_TYPE, LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUILUI_RELTN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LUILUI_RELTN_ATTR
/

CREATE TABLE KSEN_LUILUI_RELTN_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUILUI_RELTN_ATTRP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_ATTR
/

CREATE TABLE KSEN_LUI_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUI_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_CAMPUS_LOC
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_CAMPUS_LOC
/

CREATE TABLE KSEN_LUI_CAMPUS_LOC
(
      ID VARCHAR(255)
        , LUI_ID VARCHAR(255) NOT NULL
        , CAMPUS_LOC VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_LUI_CAMPUS_LOCP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_LUI_CAMPUS_LOC_I1 UNIQUE (LUI_ID, CAMPUS_LOC)

    , INDEX KSEN_LUI_CAMPUS_LOC_IF1 (LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_CAPACITY
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_CAPACITY
/

CREATE TABLE KSEN_LUI_CAPACITY
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LUI_CAPACITY_TYPE VARCHAR(255) NOT NULL
        , LUI_CAPACITY_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , MAX_SEATS DECIMAL(22)
        , PROCESSING_ORDER DECIMAL(22)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_CAPACITYP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUI_CAPACITY_I1 (LUI_CAPACITY_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_CLUCLU_RELTN
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_CLUCLU_RELTN
/

CREATE TABLE KSEN_LUI_CLUCLU_RELTN
(
      ID VARCHAR(255)
        , LUI_ID VARCHAR(255) NOT NULL
        , CLUCLU_RELTN_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_LUI_CLUCLU_RELTNP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_LUI_CLUCLU_RELTN_I1 UNIQUE (LUI_ID, CLUCLU_RELTN_ID)

    , INDEX KSEN_LUI_CLUCLU_RELTN_IF1 (LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_IDENT
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_IDENT
/

CREATE TABLE KSEN_LUI_IDENT
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LUI_ID_TYPE VARCHAR(255) NOT NULL
        , LUI_ID_STATE VARCHAR(255) NOT NULL
        , LUI_CD VARCHAR(255)
        , SHRT_NAME VARCHAR(255)
        , LNG_NAME VARCHAR(255)
        , DIVISION VARCHAR(255)
        , SUFX_CD VARCHAR(255)
        , VARTN VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , LUI_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_IDENTP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUI_IDENT_IF1 (LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_IDENT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_IDENT_ATTR
/

CREATE TABLE KSEN_LUI_IDENT_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_IDENT_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUI_IDENT_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_LU_CD
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_LU_CD
/

CREATE TABLE KSEN_LUI_LU_CD
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LUI_LUCD_TYPE VARCHAR(255) NOT NULL
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , VALUE VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , LUI_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_LU_CDP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUI_LU_CD_IF1 (LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_LU_CD_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_LU_CD_ATTR
/

CREATE TABLE KSEN_LUI_LU_CD_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_LU_CD_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUI_LU_CD_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_RELATED_LUI_TYPES
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_RELATED_LUI_TYPES
/

CREATE TABLE KSEN_LUI_RELATED_LUI_TYPES
(
      RELATED_LUI_TYPE VARCHAR(255)
        , LUI_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_RELATED_LUI_TYPESP1 PRIMARY KEY(RELATED_LUI_TYPE,LUI_ID)


    , INDEX KSEN_LUI_RELATED_LUI_TYPES_IF1 (LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_RESULT_VAL_GRP
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_RESULT_VAL_GRP
/

CREATE TABLE KSEN_LUI_RESULT_VAL_GRP
(
      LUI_ID VARCHAR(255) NOT NULL
        , RESULT_VAL_GRP_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT KSEN_LUI_RESULT_VAL_GRP_I1 UNIQUE (LUI_ID, RESULT_VAL_GRP_ID)

    , INDEX KSEN_LUI_RESULT_VAL_GRP_IF1 (LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_SET
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_SET
/

CREATE TABLE KSEN_LUI_SET
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , LUI_SET_TYPE VARCHAR(255) NOT NULL
        , LUI_SET_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_SETP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUI_SET_I1 (LUI_SET_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_SET_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_SET_ATTR
/

CREATE TABLE KSEN_LUI_SET_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_SET_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_LUI_SET_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_SET_LUI
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_SET_LUI
/

CREATE TABLE KSEN_LUI_SET_LUI
(
      LUI_ID VARCHAR(255)
        , LUI_SET_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_LUI_SET_LUIP1 PRIMARY KEY(LUI_ID,LUI_SET_ID)


    , INDEX KSEN_LUI_SET_LUI_IF1 (LUI_SET_ID)
    , INDEX KSEN_LUI_SET_LUI_IF2 (LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_UNITS_CONT_OWNER
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_UNITS_CONT_OWNER
/

CREATE TABLE KSEN_LUI_UNITS_CONT_OWNER
(
      LUI_ID VARCHAR(255) NOT NULL
        , ORG_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT KSEN_LUI_UNITS_CONT_OWNER_I1 UNIQUE (LUI_ID, ORG_ID)

    , INDEX KSEN_LUI_UNITS_CONT_OWNER_IF1 (LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_LUI_UNITS_DEPLOYMENT
# -----------------------------------------------------------------------
drop table if exists KSEN_LUI_UNITS_DEPLOYMENT
/

CREATE TABLE KSEN_LUI_UNITS_DEPLOYMENT
(
      ID VARCHAR(255)
        , LUI_ID VARCHAR(255) NOT NULL
        , ORG_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_LUI_UNITS_DEPLOYMENTP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_LUI_UNITS_DEPLOYMENT_I1 UNIQUE (LUI_ID, ORG_ID)

    , INDEX KSEN_LUI_UNITS_DEPLOYMENT_IF1 (LUI_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_MSTONE
# -----------------------------------------------------------------------
drop table if exists KSEN_MSTONE
/

CREATE TABLE KSEN_MSTONE
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , MSTONE_TYPE VARCHAR(255) NOT NULL
        , MSTONE_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000) NOT NULL
        , DESCR_FORMATTED VARCHAR(4000)
        , IS_ALL_DAY DECIMAL(1) NOT NULL
        , IS_INSTRCT_DAY DECIMAL(1) NOT NULL
        , IS_RELATIVE DECIMAL(1) NOT NULL
        , RELATIVE_ANCHOR_MSTONE_ID VARCHAR(255)
        , IS_DATE_RANGE DECIMAL(1) NOT NULL
        , START_DT DATETIME
        , END_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_MSTONEP1 PRIMARY KEY(ID)


    , INDEX KSEN_MSTONE_I1 (MSTONE_TYPE)
    , INDEX KSEN_MSTONE_I2 (START_DT)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_MSTONE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_MSTONE_ATTR
/

CREATE TABLE KSEN_MSTONE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_MSTONE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_MSTONE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION
/

CREATE TABLE KSEN_POPULATION
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , POPULATION_TYPE VARCHAR(255) NOT NULL
        , POPULATION_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , POPULATION_RULE_ID VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATIONP1 PRIMARY KEY(ID)


    , INDEX KSEN_POP_I1 (POPULATION_TYPE)
    , INDEX KSEN_POP_IF1 (POPULATION_RULE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_ATTR
/

CREATE TABLE KSEN_POPULATION_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_POP_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_CAT
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_CAT
/

CREATE TABLE KSEN_POPULATION_CAT
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , POPULATION_CAT_TYPE VARCHAR(255) NOT NULL
        , POPULATION_CAT_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_CATP1 PRIMARY KEY(ID)


    , INDEX KSEN_POP_CAT_I1 (POPULATION_CAT_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_CAT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_CAT_ATTR
/

CREATE TABLE KSEN_POPULATION_CAT_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_CAT_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_POP_CAT_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_CAT_RELTN
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_CAT_RELTN
/

CREATE TABLE KSEN_POPULATION_CAT_RELTN
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , POPULATION_ID VARCHAR(255) NOT NULL
        , POPULATION_CAT_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_POPULATION_CAT_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSEN_POP_CAT_RELTN_IF1 (POPULATION_ID)
    , INDEX KSEN_POP_CAT_RELTN_IF2 (POPULATION_CAT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_RULE
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_RULE
/

CREATE TABLE KSEN_POPULATION_RULE
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , POPULATION_RULE_TYPE VARCHAR(255) NOT NULL
        , POPULATION_RULE_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , REF_POPULATION_ID VARCHAR(255)
        , VARIES_BY_TIME_IND DECIMAL(1) NOT NULL
        , SUPPORTS_GET_MBR_IND DECIMAL(1) NOT NULL
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_RULEP1 PRIMARY KEY(ID)


    , INDEX KSEN_POP_RULE_I1 (POPULATION_RULE_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_RULE_AGENDA
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_RULE_AGENDA
/

CREATE TABLE KSEN_POPULATION_RULE_AGENDA
(
      AGENDA_ID VARCHAR(255)
        , POPULATION_RULE_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_RULE_AGENDAP1 PRIMARY KEY(AGENDA_ID,POPULATION_RULE_ID)


    , INDEX KSEN_POP_RULE_AGENDA_IF1 (POPULATION_RULE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_RULE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_RULE_ATTR
/

CREATE TABLE KSEN_POPULATION_RULE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_RULE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_POP_RULE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_RULE_CHILD_POP
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_RULE_CHILD_POP
/

CREATE TABLE KSEN_POPULATION_RULE_CHILD_POP
(
      POPULATION_RULE_ID VARCHAR(255)
        , CHILD_POPULATION_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_RULE_CHILD_P1 PRIMARY KEY(POPULATION_RULE_ID,CHILD_POPULATION_ID)


    , INDEX KSEN_POP_RULE_CHILD_POP_IF1 (POPULATION_RULE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_RULE_GRP
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_RULE_GRP
/

CREATE TABLE KSEN_POPULATION_RULE_GRP
(
      POPULATION_RULE_ID VARCHAR(255)
        , GROUP_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_RULE_GRPP1 PRIMARY KEY(POPULATION_RULE_ID,GROUP_ID)


    , INDEX KSEN_POP_RULE_GRP_IF1 (POPULATION_RULE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_RULE_PERS
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_RULE_PERS
/

CREATE TABLE KSEN_POPULATION_RULE_PERS
(
      POPULATION_RULE_ID VARCHAR(255)
        , PERSON_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_RULE_PERSP1 PRIMARY KEY(POPULATION_RULE_ID,PERSON_ID)


    , INDEX KSEN_POP_RULE_PERS_IF1 (POPULATION_RULE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_POPULATION_RULE_SOT
# -----------------------------------------------------------------------
drop table if exists KSEN_POPULATION_RULE_SOT
/

CREATE TABLE KSEN_POPULATION_RULE_SOT
(
      POPULATION_RULE_ID VARCHAR(255)
        , SORT_ORDER_TYPE_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_POPULATION_RULE_SOTP1 PRIMARY KEY(POPULATION_RULE_ID,SORT_ORDER_TYPE_ID)


    , INDEX KSEN_POP_RULE_SOT_IF1 (POPULATION_RULE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS
/

CREATE TABLE KSEN_PROCESS
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , PROCESS_TYPE VARCHAR(255) NOT NULL
        , PROCESS_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , OWNER_ORG_ID VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_PROCESSP1 PRIMARY KEY(ID)


    , INDEX KSEN_PROC_I1 (PROCESS_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS_ATTR
/

CREATE TABLE KSEN_PROCESS_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_PROCESS_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_PROC_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS_CATEGORY
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS_CATEGORY
/

CREATE TABLE KSEN_PROCESS_CATEGORY
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , PROCESS_CATEGORY_TYPE VARCHAR(255) NOT NULL
        , PROCESS_CATEGORY_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_PROCESS_CATEGORYP1 PRIMARY KEY(ID)


    , INDEX KSEN_PROC_CAT_I1 (PROCESS_CATEGORY_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS_CATEGORY_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS_CATEGORY_ATTR
/

CREATE TABLE KSEN_PROCESS_CATEGORY_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_PROCESS_CATEGORY_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_PROC_CAT_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS_CATEGORY_RELTN
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS_CATEGORY_RELTN
/

CREATE TABLE KSEN_PROCESS_CATEGORY_RELTN
(
      ID CHAR(255)
        , OBJ_ID VARCHAR(36)
        , PROCESS_ID VARCHAR(255) NOT NULL
        , PROCESS_CATEGORY_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_PROCESS_CATEGORY_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSEN_PROC_CAT_RELTN_IF1 (PROCESS_ID)
    , INDEX KSEN_PROC_CAT_RELTN_IF2 (PROCESS_CATEGORY_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS_CHECK
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS_CHECK
/

CREATE TABLE KSEN_PROCESS_CHECK
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(255)
        , PROCESS_CHECK_TYPE VARCHAR(255) NOT NULL
        , PROCESS_CHECK_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , ISSUE_ID VARCHAR(255)
        , MSTONE_TYPE VARCHAR(255)
        , AGENDA_ID VARCHAR(255)
        , RIGHT_AGENDA_ID VARCHAR(255)
        , LEFT_AGENDA_ID VARCHAR(255)
        , CHILD_PROCESS_ID VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_PROCESS_CHECKP1 PRIMARY KEY(ID)


    , INDEX KSEN_PROCESS_CHECK_IF1 (CHILD_PROCESS_ID)
    , INDEX KSEN_PROCE_CHECK_I1 (PROCESS_CHECK_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS_CHECK_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS_CHECK_ATTR
/

CREATE TABLE KSEN_PROCESS_CHECK_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_PROCESS_CHECK_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_PROCESS_CHECK_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS_INSTRN
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS_INSTRN
/

CREATE TABLE KSEN_PROCESS_INSTRN
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , PROCESS_INSTRN_TYPE VARCHAR(255) NOT NULL
        , PROCESS_INSTRN_STATE VARCHAR(255) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , PROCESS_ID VARCHAR(255) NOT NULL
        , CHECK_ID VARCHAR(255) NOT NULL
        , APPLD_POPULATION_ID VARCHAR(255)
        , MESG_PLAIN VARCHAR(4000)
        , MESG_FORMATTED VARCHAR(4000)
        , POSITION DECIMAL(22)
        , WARNING_IND VARCHAR(1)
        , CONT_ON_FAILED_IND VARCHAR(1)
        , EXEMPTIBLE_IND VARCHAR(1)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(6)
    
    , CONSTRAINT KSEN_PROCESS_INSTRNP1 PRIMARY KEY(ID)


    , INDEX KSEN_PROC_INSTRN_I1 (PROCESS_INSTRN_TYPE)
    , INDEX KSEN_PROC_INSTRN_I2 (PROCESS_ID, CHECK_ID)
    , INDEX KSEN_PROC_INSTRN_IF1 (CHECK_ID)
    , INDEX KSEN_PROC_INSTRN_IF2 (PROCESS_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS_INSTRN_AAT
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS_INSTRN_AAT
/

CREATE TABLE KSEN_PROCESS_INSTRN_AAT
(
      PROCESS_INSTRN_ID VARCHAR(255)
        , APPLD_ATP_TYPE VARCHAR(255)
    
    , CONSTRAINT KSEN_PROCESS_INSTRN_AATP1 PRIMARY KEY(PROCESS_INSTRN_ID,APPLD_ATP_TYPE)


    , INDEX KSEN_PROC_INSTRN_AAT_IF1 (PROCESS_INSTRN_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_PROCESS_INSTRN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_PROCESS_INSTRN_ATTR
/

CREATE TABLE KSEN_PROCESS_INSTRN_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_PROCESS_INSTRN_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_PROC_INSTRN_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_RICH_TEXT_T
# -----------------------------------------------------------------------
drop table if exists KSEN_RICH_TEXT_T
/

CREATE TABLE KSEN_RICH_TEXT_T
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , FORMATTED VARCHAR(2000)
        , PLAIN VARCHAR(2000)
    
    , CONSTRAINT KSEN_RICH_TEXT_TP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM
/

CREATE TABLE KSEN_ROOM
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ROOM_TYPE VARCHAR(255) NOT NULL
        , ROOM_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , ROOM_CD VARCHAR(255) NOT NULL
        , BUILDING_ID VARCHAR(255) NOT NULL
        , FLOOR VARCHAR(255) NOT NULL
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_ROOMP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_ROOM_I4 UNIQUE (BUILDING_ID, ROOM_CD)

    , INDEX KSEN_ROOM_I1 (BUILDING_ID, FLOOR)
    , INDEX KSEN_ROOM_I2 (ROOM_TYPE)
    , INDEX KSEN_ROOM_I3 (BUILDING_ID, ROOM_TYPE)
    , INDEX KSEN_ROOM_IF1 (BUILDING_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_ACCESS_TYPE
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_ACCESS_TYPE
/

CREATE TABLE KSEN_ROOM_ACCESS_TYPE
(
      ID VARCHAR(255)
        , ROOM_ID VARCHAR(255) NOT NULL
        , ACCESS_TYPE_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_ROOM_ACCESS_TYPEP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_ROOM_ACCESS_TYPE_I1 UNIQUE (ROOM_ID, ACCESS_TYPE_ID)

    , INDEX KSEN_ROOM_ACCESS_TYPE_IF1 (ROOM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_ATTR
/

CREATE TABLE KSEN_ROOM_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_ROOM_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_ROOM_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_BUILDING
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_BUILDING
/

CREATE TABLE KSEN_ROOM_BUILDING
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , BUILDING_TYPE VARCHAR(255) NOT NULL
        , BUILDING_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , BUILDING_CD VARCHAR(255) NOT NULL
        , CAMPUS_KEY VARCHAR(255) NOT NULL
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_ROOM_BUILDINGP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_ROOM_BUILDING_I2 UNIQUE (BUILDING_CD)

    , INDEX KSEN_ROOM_BUILDING_I1 (CAMPUS_KEY)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_BUILDING_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_BUILDING_ATTR
/

CREATE TABLE KSEN_ROOM_BUILDING_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_ROOM_BUILDING_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_ROOM_BUILDING_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_FIXED_RSRC
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_FIXED_RSRC
/

CREATE TABLE KSEN_ROOM_FIXED_RSRC
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , FIXED_RSRC_TYPE VARCHAR(255) NOT NULL
        , QUANTITY DECIMAL(22)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , ROOM_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_ROOM_FIXED_RSRCP1 PRIMARY KEY(ID)


    , INDEX KSEN_ROOM_FIXED_RSRC_IF1 (ROOM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_FIXED_RSRC_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_FIXED_RSRC_ATTR
/

CREATE TABLE KSEN_ROOM_FIXED_RSRC_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_ROOM_FIXED_RSRC_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_ROOM_FIXED_RSRC_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_RESP_ORG
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_RESP_ORG
/

CREATE TABLE KSEN_ROOM_RESP_ORG
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , RESP_ORG_TYPE VARCHAR(255) NOT NULL
        , RESP_ORG_STATE VARCHAR(255) NOT NULL
        , ROOM_ID VARCHAR(255) NOT NULL
        , ORG_ID VARCHAR(255) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_ROOM_RESP_ORGP1 PRIMARY KEY(ID)


    , INDEX KSEN_ROOM_RESP_ORG_I1 (RESP_ORG_TYPE)
    , INDEX KSEN_ROOM_RESP_ORG_IF1 (ROOM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_RESP_ORG_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_RESP_ORG_ATTR
/

CREATE TABLE KSEN_ROOM_RESP_ORG_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_ROOM_RESP_ORG_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_ROOM_RESP_ORG_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_USAGE
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_USAGE
/

CREATE TABLE KSEN_ROOM_USAGE
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , USAGE_TYPE VARCHAR(255) NOT NULL
        , LAYOUT_TYPE VARCHAR(255) NOT NULL
        , PREFERRED_CAPACITY DECIMAL(22)
        , HARD_CAPACITY DECIMAL(22)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID CHAR(18)
        , ROOM_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_ROOM_USAGEP1 PRIMARY KEY(ID)


    , INDEX KSEN_ROOM_USAGE_I1 (USAGE_TYPE)
    , INDEX KSEN_ROOM_USAGE_IF1 (ROOM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_ROOM_USAGE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_ROOM_USAGE_ATTR
/

CREATE TABLE KSEN_ROOM_USAGE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_ROOM_USAGE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_ROOM_USAGE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED
/

CREATE TABLE KSEN_SCHED
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , SCHED_TYPE VARCHAR(255) NOT NULL
        , SCHED_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , ATP_ID VARCHAR(255) NOT NULL
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_SCHEDP1 PRIMARY KEY(ID)


    , INDEX KSEN_SCHED_I1 (SCHED_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_ATTR
/

CREATE TABLE KSEN_SCHED_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_SCHED_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_SCHED_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_CMP
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_CMP
/

CREATE TABLE KSEN_SCHED_CMP
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ROOM_ID VARCHAR(255)
        , SCHED_ID VARCHAR(255)
        , TBA_IND DECIMAL(22) NOT NULL
    
    , CONSTRAINT KSEN_SCHED_CMPP1 PRIMARY KEY(ID)


    , INDEX KSEN_SCHED_CMP_IF1 (SCHED_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_CMP_TMSLOT
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_CMP_TMSLOT
/

CREATE TABLE KSEN_SCHED_CMP_TMSLOT
(
      SCHED_CMP_ID VARCHAR(255)
        , TM_SLOT_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_SCHED_CMP_TMSLOTP1 PRIMARY KEY(SCHED_CMP_ID,TM_SLOT_ID)


    , INDEX KSEN_SCHED_CMP_TMSLOT_IF1 (SCHED_CMP_ID)
    , INDEX KSEN_SCHED_CMP_TMSLOT_IF2 (TM_SLOT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_RQST
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_RQST
/

CREATE TABLE KSEN_SCHED_RQST
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(255)
        , SCHED_RQST_TYPE VARCHAR(255) NOT NULL
        , SCHED_RQST_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , REF_OBJECT_ID VARCHAR(255) NOT NULL
        , REF_OBJECT_TYPE VARCHAR(255) NOT NULL
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_SCHED_RQSTP1 PRIMARY KEY(ID)


    , INDEX KSEN_SCHED_RQST_I1 (REF_OBJECT_ID, REF_OBJECT_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_RQST_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_RQST_ATTR
/

CREATE TABLE KSEN_SCHED_RQST_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_SCHED_RQST_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_SCHED_RQST_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_RQST_CMP
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_RQST_CMP
/

CREATE TABLE KSEN_SCHED_RQST_CMP
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , SCHED_RQST_ID VARCHAR(255)
        , TBA_IND DECIMAL(22) NOT NULL
    
    , CONSTRAINT KSEN_SCHED_RQST_CMPP1 PRIMARY KEY(ID)


    , INDEX KSEN_SCHED_RQST_CMP_IF1 (SCHED_RQST_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_RQST_CMP_BLDG
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_RQST_CMP_BLDG
/

CREATE TABLE KSEN_SCHED_RQST_CMP_BLDG
(
      CMP_ID VARCHAR(255) NOT NULL
        , BUILDING_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT KSEN_SCHED_RQST_CMP_BLDG_I1 UNIQUE (CMP_ID, BUILDING_ID)

    , INDEX KSEN_SCHED_RQST_CMP_BLDG_IF1 (CMP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_RQST_CMP_CAMPUS
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_RQST_CMP_CAMPUS
/

CREATE TABLE KSEN_SCHED_RQST_CMP_CAMPUS
(
      CMP_ID VARCHAR(255) NOT NULL
        , CAMPUS_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT KSEN_SCHED_RQST_CMP_CAMPUS_I1 UNIQUE (CMP_ID, CAMPUS_ID)

    , INDEX KSEN_SCHED_RQST_CMP_CAMPUS_IF1 (CMP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_RQST_CMP_ORG
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_RQST_CMP_ORG
/

CREATE TABLE KSEN_SCHED_RQST_CMP_ORG
(
      CMP_ID VARCHAR(255) NOT NULL
        , ORG_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT KSEN_SCHED_RQST_CMP_ORG_I1 UNIQUE (CMP_ID, ORG_ID)

    , INDEX KSEN_SCHED_RQST_CMP_ORG_IF1 (CMP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_RQST_CMP_ROOM
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_RQST_CMP_ROOM
/

CREATE TABLE KSEN_SCHED_RQST_CMP_ROOM
(
      CMP_ID VARCHAR(255) NOT NULL
        , ROOM_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT KSEN_SCHED_RQST_CMP_ROOM_I1 UNIQUE (CMP_ID, ROOM_ID)

    , INDEX KSEN_SCHED_RQST_CMP_ROOM_IF1 (CMP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_RQST_CMP_RT
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_RQST_CMP_RT
/

CREATE TABLE KSEN_SCHED_RQST_CMP_RT
(
      CMP_ID VARCHAR(255) NOT NULL
        , RSRC_TYPE_KEY VARCHAR(255) NOT NULL
    

    , CONSTRAINT KSEN_SCHED_RQST_RT_I1 UNIQUE (CMP_ID, RSRC_TYPE_KEY)

    , INDEX KSEN_SCHED_RQST_RT_IF1 (CMP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_RQST_CMP_TMSLOT
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_RQST_CMP_TMSLOT
/

CREATE TABLE KSEN_SCHED_RQST_CMP_TMSLOT
(
      CMP_ID VARCHAR(255) NOT NULL
        , TM_SLOT_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT KSEN_SCHED_RQST_CMP_TMSLOT_I1 UNIQUE (CMP_ID, TM_SLOT_ID)

    , INDEX KSEN_SCHED_RQST_CMP_TMSLOT_IF1 (CMP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_TMSLOT
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_TMSLOT
/

CREATE TABLE KSEN_SCHED_TMSLOT
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , TM_SLOT_TYPE VARCHAR(255) NOT NULL
        , TM_SLOT_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , WEEKDAYS VARCHAR(255)
        , START_TIME_MS DECIMAL(22)
        , END_TIME_MS DECIMAL(22)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_SCHED_TMSLOTP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SCHED_TMSLOT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_SCHED_TMSLOT_ATTR
/

CREATE TABLE KSEN_SCHED_TMSLOT_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_SCHED_TMSLOT_ATTRP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SOC
# -----------------------------------------------------------------------
drop table if exists KSEN_SOC
/

CREATE TABLE KSEN_SOC
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , SOC_TYPE VARCHAR(255) NOT NULL
        , SOC_STATE VARCHAR(255) NOT NULL
        , NAME VARCHAR(255)
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , TERM_ID VARCHAR(255) NOT NULL
        , SUBJECT_AREA VARCHAR(255)
        , UNITS_CONTENT_OWNER_ID VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_SOCP1 PRIMARY KEY(ID)


    , INDEX KSEN_SOC_I1 (TERM_ID, SUBJECT_AREA)
    , INDEX KSEN_SOC_I2 (TERM_ID, UNITS_CONTENT_OWNER_ID)
    , INDEX KSEN_SOC_I3 (SOC_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SOC_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_SOC_ATTR
/

CREATE TABLE KSEN_SOC_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(255)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_SOC_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_SOC_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SOC_ROR
# -----------------------------------------------------------------------
drop table if exists KSEN_SOC_ROR
/

CREATE TABLE KSEN_SOC_ROR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , SOC_ROR_TYPE VARCHAR(255) NOT NULL
        , SOC_ROR_STATE VARCHAR(255) NOT NULL
        , TARGET_TERM_ID VARCHAR(255) NOT NULL
        , ITEMS_PROCESSED DECIMAL(22)
        , ITEMS_EXPECTED DECIMAL(22)
        , SOURCE_SOC_ID VARCHAR(255) NOT NULL
        , TARGET_SOC_ID VARCHAR(255) NOT NULL
        , MESG_PLAIN VARCHAR(4000)
        , MESG_FORMATTED VARCHAR(4000)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_SOC_RORP1 PRIMARY KEY(ID)


    , INDEX KSEN_SOC_ROR_IF1 (SOURCE_SOC_ID)
    , INDEX KSEN_SOC_ROR_IF2 (TARGET_SOC_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SOC_ROR_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_SOC_ROR_ATTR
/

CREATE TABLE KSEN_SOC_ROR_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(255)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_SOC_ROR_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_SOC_ROR_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SOC_ROR_ITEM
# -----------------------------------------------------------------------
drop table if exists KSEN_SOC_ROR_ITEM
/

CREATE TABLE KSEN_SOC_ROR_ITEM
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , SOC_ROR_TYPE VARCHAR(255) NOT NULL
        , SOC_ROR_STATE VARCHAR(255) NOT NULL
        , SOURCE_CO_ID VARCHAR(255) NOT NULL
        , TARGET_CO_ID VARCHAR(255)
        , ROR_ID VARCHAR(255)
        , MESG_PLAIN VARCHAR(4000)
        , MESG_FORMATTED VARCHAR(4000)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_SOC_ROR_ITEMP1 PRIMARY KEY(ID)


    , INDEX KSEN_SOC_ROR_ITEM_I1 (SOURCE_CO_ID)
    , INDEX KSEN_SOC_ROR_ITEM_I2 (TARGET_CO_ID)
    , INDEX KSEN_SOC_ROR_ITEM_IF1 (ROR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SOC_ROR_ITEM_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_SOC_ROR_ITEM_ATTR
/

CREATE TABLE KSEN_SOC_ROR_ITEM_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(255)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_SOC_ROR_ITEM_ATTRP1 PRIMARY KEY(ID)


    , INDEX XIF1KSEN_SOC_ROR_ITEM_ATTR (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_SOC_ROR_OPTION
# -----------------------------------------------------------------------
drop table if exists KSEN_SOC_ROR_OPTION
/

CREATE TABLE KSEN_SOC_ROR_OPTION
(
      ID VARCHAR(255)
        , OPTION_ID VARCHAR(255) NOT NULL
        , ROR_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_SOC_ROR_OPTIONP1 PRIMARY KEY(ID)

    , CONSTRAINT KSEN_SOC_ROR_OPTION_I1 UNIQUE (ROR_ID, OPTION_ID)

    , INDEX KSEN_SOC_ROR_OPTION_IF1 (ROR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE
/

CREATE TABLE KSEN_STATE
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , NAME VARCHAR(255) NOT NULL
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , LIFECYCLE_KEY VARCHAR(255) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATEP1 PRIMARY KEY(ID)


    , INDEX KSEN_STATE_IF1 (LIFECYCLE_KEY)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_ATTR
/

CREATE TABLE KSEN_STATE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_STATE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_CHG
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_CHG
/

CREATE TABLE KSEN_STATE_CHG
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , STATE_CHG_TYPE VARCHAR(255) NOT NULL
        , STATE_CHG_STATE VARCHAR(255) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , FROM_STATE_ID VARCHAR(255) NOT NULL
        , TO_STATE_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_STATE_CHGP1 PRIMARY KEY(ID)


    , INDEX KSEN_STATE_CHG_I1 (STATE_CHG_TYPE)
    , INDEX KSEN_STATE_CHG_I2 (FROM_STATE_ID, TO_STATE_ID)
    , INDEX KSEN_STATE_CHG_IF1 (FROM_STATE_ID)
    , INDEX KSEN_STATE_CHG_IF2 (TO_STATE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_CHG_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_CHG_ATTR
/

CREATE TABLE KSEN_STATE_CHG_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_CHG_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_STATE_CHG_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_CHG_CNSTRNT
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_CHG_CNSTRNT
/

CREATE TABLE KSEN_STATE_CHG_CNSTRNT
(
      STATE_CHG_ID VARCHAR(255)
        , STATE_CNSTRNT_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_CHG_CNSTRNTP1 PRIMARY KEY(STATE_CHG_ID,STATE_CNSTRNT_ID)


    , INDEX KSEN_STATE_CHG_C_IF1 (STATE_CHG_ID)
    , INDEX KSEN_STATE_CHG_C_IF2 (STATE_CNSTRNT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_CHG_PROPAGT
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_CHG_PROPAGT
/

CREATE TABLE KSEN_STATE_CHG_PROPAGT
(
      STATE_CHG_ID VARCHAR(255)
        , STATE_PROPAGT_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_CHG_PROPAGTP1 PRIMARY KEY(STATE_CHG_ID,STATE_PROPAGT_ID)


    , INDEX KSEN_STATE_CHG_P_IF1 (STATE_CHG_ID)
    , INDEX KSEN_STATE_CHG_P_IF2 (STATE_PROPAGT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_CNSTRNT
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_CNSTRNT
/

CREATE TABLE KSEN_STATE_CNSTRNT
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , STATE_CNSTRNT_TYPE VARCHAR(255) NOT NULL
        , STATE_CNSTRNT_STATE VARCHAR(255) NOT NULL
        , STATE_CNSTRNT_OPERATOR VARCHAR(255) NOT NULL
        , AGENDA_ID VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_CNSTRNTP1 PRIMARY KEY(ID)


    , INDEX KSEN_STATE_CNSTRNT_I1 (STATE_CNSTRNT_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_CNSTRNT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_CNSTRNT_ATTR
/

CREATE TABLE KSEN_STATE_CNSTRNT_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_CNSTRNT_ATTRP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_CNSTRNT_ROS
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_CNSTRNT_ROS
/

CREATE TABLE KSEN_STATE_CNSTRNT_ROS
(
      STATE_CNSTRNT_ID VARCHAR(255)
        , REL_OBJ_STATE_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_CNSTRNT_ROSP1 PRIMARY KEY(STATE_CNSTRNT_ID,REL_OBJ_STATE_ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_LIFECYCLE
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_LIFECYCLE
/

CREATE TABLE KSEN_STATE_LIFECYCLE
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , NAME VARCHAR(255) NOT NULL
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , REF_OBJECT_URI VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_LIFECYCLEP1 PRIMARY KEY(ID)


    , INDEX KSEN_STATE_LIFECYCLE_I1 (REF_OBJECT_URI)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_LIFECYCLE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_LIFECYCLE_ATTR
/

CREATE TABLE KSEN_STATE_LIFECYCLE_ATTR
(
      OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
        , ID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_LIFECYCLE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_STATE_LIFECYCLE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_PROCESS
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_PROCESS
/

CREATE TABLE KSEN_STATE_PROCESS
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , DESCR VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_PROCESSP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_PROPAGT
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_PROPAGT
/

CREATE TABLE KSEN_STATE_PROPAGT
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , STATE_PROPAGT_TYPE VARCHAR(255) NOT NULL
        , STATE_PROPAGT_STATE VARCHAR(255) NOT NULL
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , TARGET_STATE_CHG_ID VARCHAR(255) NOT NULL
    
    , CONSTRAINT KSEN_STATE_PROPAGTP1 PRIMARY KEY(ID)


    , INDEX KSEN_STATE_PROPAGT_I1 (STATE_PROPAGT_TYPE)
    , INDEX KSEN_STATE_PROPAGT_I2 (TARGET_STATE_CHG_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_PROPAGT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_PROPAGT_ATTR
/

CREATE TABLE KSEN_STATE_PROPAGT_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_PROPAGT_ATTRP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_STATE_PROPAGT_CNSTRNT
# -----------------------------------------------------------------------
drop table if exists KSEN_STATE_PROPAGT_CNSTRNT
/

CREATE TABLE KSEN_STATE_PROPAGT_CNSTRNT
(
      STATE_PROPAGT_ID VARCHAR(255)
        , STATE_CNSTRNT_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_STATE_PROPAGT_CNSTRNTP1 PRIMARY KEY(STATE_PROPAGT_ID,STATE_CNSTRNT_ID)


    , INDEX KSEN_STATE_PROPAGT_C_IF1 (STATE_CNSTRNT_ID)
    , INDEX KSEN_STATE_PROPAGT_C_IF2 (STATE_PROPAGT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSEN_TYPE
/

CREATE TABLE KSEN_TYPE
(
      TYPE_KEY VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , NAME VARCHAR(255) NOT NULL
        , DESCR_PLAIN VARCHAR(4000)
        , DESCR_FORMATTED VARCHAR(4000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , REF_OBJECT_URI VARCHAR(255)
        , SERVICE_URI VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_TYPEP1 PRIMARY KEY(TYPE_KEY)


    , INDEX KSEN_TYPE_I1 (REF_OBJECT_URI)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_TYPETYPE_RELTN
# -----------------------------------------------------------------------
drop table if exists KSEN_TYPETYPE_RELTN
/

CREATE TABLE KSEN_TYPETYPE_RELTN
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , TYPETYPE_RELTN_TYPE VARCHAR(255) NOT NULL
        , TYPETYPE_RELTN_STATE VARCHAR(255) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , OWNER_TYPE_ID VARCHAR(255) NOT NULL
        , RELATED_TYPE_ID VARCHAR(255) NOT NULL
        , RANK DECIMAL(10)
        , VER_NBR DECIMAL(19) NOT NULL
        , CREATETIME DATETIME NOT NULL
        , CREATEID VARCHAR(255) NOT NULL
        , UPDATETIME DATETIME
        , UPDATEID VARCHAR(255)
    
    , CONSTRAINT KSEN_TYPETYPE_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSEN_TYPETYPE_RELTN_IF1 (OWNER_TYPE_ID)
    , INDEX KSEN_TYPETYPE_RELTN_IF2 (RELATED_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_TYPETYPE_RELTN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_TYPETYPE_RELTN_ATTR
/

CREATE TABLE KSEN_TYPETYPE_RELTN_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_TYPETYPE_RELTN_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_TYPETYPE_RELTN_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSEN_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSEN_TYPE_ATTR
/

CREATE TABLE KSEN_TYPE_ATTR
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , ATTR_KEY VARCHAR(255)
        , ATTR_VALUE VARCHAR(4000)
        , OWNER_ID VARCHAR(255)
    
    , CONSTRAINT KSEN_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSEN_TYPE_ATTR_IF1 (OWNER_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLO_ATTR
/

CREATE TABLE KSLO_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLO_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLO_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO
# -----------------------------------------------------------------------
drop table if exists KSLO_LO
/

CREATE TABLE KSLO_LO
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , ST VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , LO_REPO_ID VARCHAR(255)
        , LOTYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLO_LOP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_I1 (RT_DESCR_ID)
    , INDEX KSLO_LO_I2 (LOTYPE_ID)
    , INDEX KSLO_LO_I3 (LO_REPO_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_ALLOWED_RELTN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_ALLOWED_RELTN_TYPE
/

CREATE TABLE KSLO_LO_ALLOWED_RELTN_TYPE
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , LO_TYPE_ID VARCHAR(255)
        , LO_REL_TYPE_ID VARCHAR(255)
        , LOLO_RELTN_TYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLO_LO_ALLOWED_RELTN_TYPEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_CATEGORY
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_CATEGORY
/

CREATE TABLE KSLO_LO_CATEGORY
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , STATE VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , LO_CATEGORY_TYPE_ID VARCHAR(255)
        , LO_REPO_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLO_LO_CATEGORYP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_CATEGORY_I1 (RT_DESCR_ID)
    , INDEX KSLO_LO_CATEGORY_I2 (LO_REPO_ID)
    , INDEX KSLO_LO_CATEGORY_I3 (LO_CATEGORY_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_CATEGORY_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_CATEGORY_ATTR
/

CREATE TABLE KSLO_LO_CATEGORY_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLO_LO_CATEGORY_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_CATEGORY_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_CATEGORY_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_CATEGORY_TYPE
/

CREATE TABLE KSLO_LO_CATEGORY_TYPE
(
      ID VARCHAR(255)
        , DESCR VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , TYPE_KEY VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , TYPE_DESC VARCHAR(2000)
    
    , CONSTRAINT KSLO_LO_CATEGORY_TYPEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_CATEGORY_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_CATEGORY_TYPE_ATTR
/

CREATE TABLE KSLO_LO_CATEGORY_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLO_LO_CATEGORY_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_CATEGORY_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_JN_LOCATEGORY
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_JN_LOCATEGORY
/

CREATE TABLE KSLO_LO_JN_LOCATEGORY
(
      ID VARCHAR(255)
        , LO_ID VARCHAR(255)
        , LOCATEGORY_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLO_LO_JN_LOCATEGORYP1 PRIMARY KEY(ID)

    , CONSTRAINT SYS_C0011301 UNIQUE (LO_ID, LOCATEGORY_ID)

    , INDEX KSLO_LO_JN_LOCATEGORY_I1 (LOCATEGORY_ID)
    , INDEX KSLO_LO_JN_LOCATEGORY_I2 (LO_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_RELTN
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_RELTN
/

CREATE TABLE KSLO_LO_RELTN
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , ST VARCHAR(255)
        , LO_ID VARCHAR(255)
        , LO_LO_RELATION_TYPE_ID VARCHAR(255)
        , RELATED_LO_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLO_LO_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_RELTN_I1 (LO_LO_RELATION_TYPE_ID)
    , INDEX KSLO_LO_RELTN_I2 (RELATED_LO_ID)
    , INDEX KSLO_LO_RELTN_I3 (LO_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_RELTN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_RELTN_ATTR
/

CREATE TABLE KSLO_LO_RELTN_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLO_LO_RELTN_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_RELTN_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_RELTN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_RELTN_TYPE
/

CREATE TABLE KSLO_LO_RELTN_TYPE
(
      ID VARCHAR(255)
        , DESCR VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , REV_DESCR VARCHAR(255)
        , REV_NAME VARCHAR(255)
        , TYPE_KEY VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , TYPE_DESC VARCHAR(2000)
    
    , CONSTRAINT KSLO_LO_RELTN_TYPEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_RELTN_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_RELTN_TYPE_ATTR
/

CREATE TABLE KSLO_LO_RELTN_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLO_LO_RELTN_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_RELTN_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_REPOSITORY
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_REPOSITORY
/

CREATE TABLE KSLO_LO_REPOSITORY
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , LO_ROOT_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLO_LO_REPOSITORYP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_REPOSITORY_I1 (LO_ROOT_ID)
    , INDEX KSLO_LO_REPOSITORY_I2 (RT_DESCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_REPOSITORY_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_REPOSITORY_ATTR
/

CREATE TABLE KSLO_LO_REPOSITORY_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLO_LO_REPOSITORY_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_REPOSITORY_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_TYPE
/

CREATE TABLE KSLO_LO_TYPE
(
      ID VARCHAR(255)
        , DESCR VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , TYPE_KEY VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , TYPE_DESC VARCHAR(2000)
    
    , CONSTRAINT KSLO_LO_TYPEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_LO_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLO_LO_TYPE_ATTR
/

CREATE TABLE KSLO_LO_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLO_LO_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLO_LO_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLO_RICH_TEXT_T
# -----------------------------------------------------------------------
drop table if exists KSLO_RICH_TEXT_T
/

CREATE TABLE KSLO_RICH_TEXT_T
(
      ID VARCHAR(255)
        , FORMATTED VARCHAR(2000)
        , PLAIN VARCHAR(2000)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLO_RICH_TEXT_TP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU
/

CREATE TABLE KSLU_CLU
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CURR_VER_END DATETIME
        , CURR_VER_START DATETIME
        , SEQ_NUM DECIMAL(19)
        , VER_CMT VARCHAR(255)
        , VER_IND_ID VARCHAR(255)
        , VER_FROM_ID VARCHAR(255)
        , CAN_CREATE_LUI DECIMAL(1)
        , DEF_ENRL_EST DECIMAL(10)
        , DEF_MAX_ENRL DECIMAL(10)
        , EFF_DT DATETIME
        , EXP_FIRST_ATP VARCHAR(255)
        , EXPIR_DT DATETIME
        , HAS_EARLY_DROP_DEDLN DECIMAL(1)
        , CLU_INTSTY_QTY VARCHAR(255)
        , CLU_INTSTY_TYPE VARCHAR(255)
        , IS_ENRL DECIMAL(1)
        , IS_HAZR_DISBLD_STU DECIMAL(1)
        , LAST_ADMIT_ATP VARCHAR(255)
        , LAST_ATP VARCHAR(255)
        , NEXT_REVIEW_PRD VARCHAR(255)
        , REF_URL VARCHAR(255)
        , ST VARCHAR(255)
        , ATP_DUR_TYP_KEY VARCHAR(255)
        , TM_QUANTITY DECIMAL(10)
        , STDY_SUBJ_AREA VARCHAR(255)
        , ACCT_ID VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , FEE_ID VARCHAR(255)
        , LUTYPE_ID VARCHAR(255)
        , OFFIC_CLU_ID VARCHAR(255)
        , PRI_INSTR_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLUP1 PRIMARY KEY(ID)

    , CONSTRAINT SYS_C0011370 UNIQUE (VER_IND_ID, SEQ_NUM)

    , INDEX KSLU_CLU_I1 (PRI_INSTR_ID)
    , INDEX KSLU_CLU_I2 (LUTYPE_ID)
    , INDEX KSLU_CLU_I3 (RT_DESCR_ID)
    , INDEX KSLU_CLU_I4 (FEE_ID)
    , INDEX KSLU_CLU_I5 (OFFIC_CLU_ID)
    , INDEX KSLU_CLU_I6 (ACCT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLUCLU_RELTN
# -----------------------------------------------------------------------
drop table if exists KSLU_CLUCLU_RELTN
/

CREATE TABLE KSLU_CLUCLU_RELTN
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , CLU_RELTN_REQ DECIMAL(1)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , ST VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , LU_RELTN_TYPE_ID VARCHAR(255)
        , RELATED_CLU_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLUCLU_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLUCLU_RELTN_I1 (CLU_ID)
    , INDEX KSLU_CLUCLU_RELTN_I2 (RELATED_CLU_ID)
    , INDEX KSLU_CLUCLU_RELTN_I3 (LU_RELTN_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLUCLU_RELTN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLUCLU_RELTN_ATTR
/

CREATE TABLE KSLU_CLUCLU_RELTN_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLUCLU_RELTN_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLUCLU_RELTN_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLURES_JN_RESOPT
# -----------------------------------------------------------------------
drop table if exists KSLU_CLURES_JN_RESOPT
/

CREATE TABLE KSLU_CLURES_JN_RESOPT
(
      CLU_RES_ID VARCHAR(255) NOT NULL
        , RES_OPT_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C0011378 UNIQUE (RES_OPT_ID)

    , INDEX KSLU_CLURES_JN_RESOPT_I1 (CLU_RES_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_ACCRED
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_ACCRED
/

CREATE TABLE KSLU_CLU_ACCRED
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , ORG_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_ACCREDP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_ACCRED_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_ACCRED_ATTR
/

CREATE TABLE KSLU_CLU_ACCRED_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_ACCRED_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_ACCRED_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_ACCT
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_ACCT
/

CREATE TABLE KSLU_CLU_ACCT
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_ACCTP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_ACCT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_ACCT_ATTR
/

CREATE TABLE KSLU_CLU_ACCT_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_ACCT_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_ACCT_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_ACCT_JN_AFFIL_ORG
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_ACCT_JN_AFFIL_ORG
/

CREATE TABLE KSLU_CLU_ACCT_JN_AFFIL_ORG
(
      CLU_ACCT_ID VARCHAR(255) NOT NULL
        , AFFIL_ORG_ID VARCHAR(255) NOT NULL
    


    , INDEX KSLU_CLU_ACCT_JN_AFFIL_ORG_I1 (AFFIL_ORG_ID)
    , INDEX KSLU_CLU_ACCT_JN_AFFIL_ORG_I2 (CLU_ACCT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_ADMIN_ORG
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_ADMIN_ORG
/

CREATE TABLE KSLU_CLU_ADMIN_ORG
(
      ID VARCHAR(255)
        , IS_PR DECIMAL(1)
        , ORG_ID VARCHAR(255)
        , TYPE VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_ADMIN_ORGP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_ADMIN_ORG_I1 (CLU_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_ADMIN_ORG_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_ADMIN_ORG_ATTR
/

CREATE TABLE KSLU_CLU_ADMIN_ORG_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_ADMIN_ORG_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_ADMIN_ORG_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_AFFIL_ORG
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_AFFIL_ORG
/

CREATE TABLE KSLU_CLU_AFFIL_ORG
(
      ID VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , ORG_ID VARCHAR(255)
        , PERCT DECIMAL(19)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_AFFIL_ORGP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_ATP_TYPE_KEY
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_ATP_TYPE_KEY
/

CREATE TABLE KSLU_CLU_ATP_TYPE_KEY
(
      ID VARCHAR(255)
        , ATP_TYPE_KEY VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_ATP_TYPE_KEYP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_ATP_TYPE_KEY_I1 (CLU_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_ATTR
/

CREATE TABLE KSLU_CLU_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_CR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_CR
/

CREATE TABLE KSLU_CLU_CR
(
      ID VARCHAR(255)
        , INSTR_UNIT DECIMAL(10)
        , MAX_ALOW_INACV_ATP VARCHAR(255)
        , MAX_ALOW_INACV_TMQ DECIMAL(10)
        , MAX_TM_RSLT_RCGZ_ATP VARCHAR(255)
        , MAX_TM_RSLT_RCGZ_TMQ DECIMAL(10)
        , MAX_TM_TO_COMP_ATP VARCHAR(255)
        , MAX_TM_TO_COMP_TMQ DECIMAL(10)
        , MAX_TOT_UNIT DECIMAL(10)
        , MIN_TM_TO_COMP_ATP VARCHAR(255)
        , MIN_TM_TO_COMP_TMQ DECIMAL(10)
        , MIN_TOT_UNIT DECIMAL(10)
        , REPEAT_CNT VARCHAR(255)
        , REPEAT_TM_ATP VARCHAR(255)
        , REPEAT_TM_TMQ DECIMAL(10)
        , REPEAT_UNIT VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_CRP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_FEE
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_FEE
/

CREATE TABLE KSLU_CLU_FEE
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , RT_DESCR_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_FEEP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_FEE_I1 (RT_DESCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_FEEREC_JN_AFFIL_ORG
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_FEEREC_JN_AFFIL_ORG
/

CREATE TABLE KSLU_CLU_FEEREC_JN_AFFIL_ORG
(
      CLU_FEE_REC_ID VARCHAR(255) NOT NULL
        , AFFIL_ORG_ID VARCHAR(255) NOT NULL
    


    , INDEX KSLU_CLU_FEEREC_JN_AFF_ORG_I1 (AFFIL_ORG_ID)
    , INDEX KSLU_CLU_FEEREC_JN_AFF_ORG_I2 (CLU_FEE_REC_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_FEE_AMOUNT
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_FEE_AMOUNT
/

CREATE TABLE KSLU_CLU_FEE_AMOUNT
(
      ID VARCHAR(255)
        , CURRENCY_QUANT DECIMAL(10)
        , CURRENCY_TYPE VARCHAR(255)
        , CLUE_FEE_REC_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_FEE_AMOUNTP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_FEE_AMOUNT_I1 (CLUE_FEE_REC_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_FEE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_FEE_ATTR
/

CREATE TABLE KSLU_CLU_FEE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_FEE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_FEE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_FEE_JN_CLU_FEE_REC
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_FEE_JN_CLU_FEE_REC
/

CREATE TABLE KSLU_CLU_FEE_JN_CLU_FEE_REC
(
      CLU_FEE_ID VARCHAR(255) NOT NULL
        , CLU_FEE_REC_ID VARCHAR(255) NOT NULL
    


    , INDEX KSLU_CLUFEE_JN_CLUFEE_REC_I1 (CLU_FEE_REC_ID)
    , INDEX KSLU_CLUFEE_JN_CLUFEE_REC_I2 (CLU_FEE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_FEE_REC
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_FEE_REC
/

CREATE TABLE KSLU_CLU_FEE_REC
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , FEE_TYPE VARCHAR(255)
        , RATE_TYPE VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_FEE_RECP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_FEE_REC_I1 (RT_DESCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_FEE_REC_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_FEE_REC_ATTR
/

CREATE TABLE KSLU_CLU_FEE_REC_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_FEE_REC_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_FEE_REC_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_IDENT
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_IDENT
/

CREATE TABLE KSLU_CLU_IDENT
(
      ID VARCHAR(255)
        , CD VARCHAR(255)
        , DIVISION VARCHAR(255)
        , LVL VARCHAR(255)
        , LNG_NAME VARCHAR(255)
        , ORG_ID VARCHAR(255)
        , SHRT_NAME VARCHAR(255)
        , ST VARCHAR(255)
        , SUFX_CD VARCHAR(255)
        , TYPE VARCHAR(255)
        , VARTN VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_IDENTP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_IDENT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_IDENT_ATTR
/

CREATE TABLE KSLU_CLU_IDENT_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_INSTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_INSTR
/

CREATE TABLE KSLU_CLU_INSTR
(
      ID VARCHAR(255)
        , ORG_ID VARCHAR(255)
        , PERS_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_INSTRP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_INSTR_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_INSTR_ATTR
/

CREATE TABLE KSLU_CLU_INSTR_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_INSTR_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_INSTR_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_JN_ACCRED
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_JN_ACCRED
/

CREATE TABLE KSLU_CLU_JN_ACCRED
(
      CLU_ID VARCHAR(255) NOT NULL
        , CLU_ACCRED_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C0011436 UNIQUE (CLU_ACCRED_ID)

    , INDEX KSLU_CLU_JN_ACCRED_I1 (CLU_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_JN_CAMP_LOC
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_JN_CAMP_LOC
/

CREATE TABLE KSLU_CLU_JN_CAMP_LOC
(
      ID VARCHAR(255)
        , CAMP_LOC VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_JN_CAMP_LOCP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_JN_CAMP_LOC_I1 (CLU_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_JN_CLU_IDENT
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_JN_CLU_IDENT
/

CREATE TABLE KSLU_CLU_JN_CLU_IDENT
(
      CLU_ID VARCHAR(255) NOT NULL
        , ALT_CLU_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C0011441 UNIQUE (ALT_CLU_ID)

    , INDEX KSLU_CLU_JN_CLU_IDENT_I2 (CLU_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_JN_CLU_INSTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_JN_CLU_INSTR
/

CREATE TABLE KSLU_CLU_JN_CLU_INSTR
(
      CLU_ID VARCHAR(255) NOT NULL
        , CLU_INSTR_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C0011444 UNIQUE (CLU_INSTR_ID)

    , INDEX KSLU_CLU_JN_CLU_INSTR_I1 (CLU_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_JN_SUBJ_ORG
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_JN_SUBJ_ORG
/

CREATE TABLE KSLU_CLU_JN_SUBJ_ORG
(
      ID VARCHAR(255)
        , ORG_ID VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_JN_SUBJ_ORGP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_JN_SUBJ_ORG_I1 (CLU_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_LO_ALOW_RELTN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_LO_ALOW_RELTN_TYPE
/

CREATE TABLE KSLU_CLU_LO_ALOW_RELTN_TYPE
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , LO_TYPE_ID VARCHAR(255)
        , CLULO_RELTN_TYPE_ID VARCHAR(255)
        , LU_TYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_LO_ALOW_RELTN_TYPEP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_LO_ALOW_REL_TYPE_I1 (LU_TYPE_ID)
    , INDEX KSLU_CLU_LO_ALOW_REL_TYPE_I2 (CLULO_RELTN_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_LO_RELTN
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_LO_RELTN
/

CREATE TABLE KSLU_CLU_LO_RELTN
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , LO_ID VARCHAR(255)
        , ST VARCHAR(255)
        , TYPE VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_LO_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_LO_RELTN_I1 (CLU_ID)
    , INDEX KSLU_CLU_LO_RELTN_I2 (TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_LO_RELTN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_LO_RELTN_ATTR
/

CREATE TABLE KSLU_CLU_LO_RELTN_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_LO_RELTN_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_LO_RELTN_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_LO_RELTN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_LO_RELTN_TYPE
/

CREATE TABLE KSLU_CLU_LO_RELTN_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_LO_RELTN_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_LO_RELTN_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_LO_RELTN_TYPE_ATTR
/

CREATE TABLE KSLU_CLU_LO_RELTN_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_LO_RELTN_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_LO_REL_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_PUBL
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_PUBL
/

CREATE TABLE KSLU_CLU_PUBL
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , END_CYCLE VARCHAR(255)
        , EXPIR_DT DATETIME
        , START_CYCLE VARCHAR(255)
        , ST VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , CLU_PUB_TYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_PUBLP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_PUBL_I1 (CLU_ID)
    , INDEX KSLU_CLU_PUBL_I2 (CLU_PUB_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_PUBL_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_PUBL_ATTR
/

CREATE TABLE KSLU_CLU_PUBL_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_PUBL_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_PUBL_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_PUBL_JN_CLU_INSTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_PUBL_JN_CLU_INSTR
/

CREATE TABLE KSLU_CLU_PUBL_JN_CLU_INSTR
(
      CLU_PUBL_ID VARCHAR(255) NOT NULL
        , CLU_INSTR_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C009456 UNIQUE (CLU_INSTR_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_PUBL_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_PUBL_TYPE
/

CREATE TABLE KSLU_CLU_PUBL_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_PUBL_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_PUBL_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_PUBL_TYPE_ATTR
/

CREATE TABLE KSLU_CLU_PUBL_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_PUBL_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_PUBL_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_PUBL_VARI
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_PUBL_VARI
/

CREATE TABLE KSLU_CLU_PUBL_VARI
(
      ID VARCHAR(255)
        , VARI_KEY VARCHAR(255)
        , VARI_VALUE VARCHAR(2500)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_PUBL_VARIP1 PRIMARY KEY(ID)

    , CONSTRAINT SYS_C0011470 UNIQUE (VARI_KEY, OWNER)

    , INDEX KSLU_CLU_PUBL_VARI_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_PUB_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_PUB_TYPE
/

CREATE TABLE KSLU_CLU_PUB_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_PUB_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_PUB_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_PUB_TYPE_ATTR
/

CREATE TABLE KSLU_CLU_PUB_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_PUB_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_PUB_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_RESULT_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_RESULT_TYPE_ATTR
/

CREATE TABLE KSLU_CLU_RESULT_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_RESULT_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_RESULT_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_RSLT
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_RSLT
/

CREATE TABLE KSLU_CLU_RSLT
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , ST VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , TYPE_KEY_ID VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_RSLTP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_RSLT_I1 (TYPE_KEY_ID)
    , INDEX KSLU_CLU_RSLT_I2 (CLU_ID)
    , INDEX KSLU_CLU_RSLT_I3 (RT_DESCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_RSLT_LU_ALOW_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_RSLT_LU_ALOW_TYPE
/

CREATE TABLE KSLU_CLU_RSLT_LU_ALOW_TYPE
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , CLU_RSLT_TYPE_ID VARCHAR(255)
        , LU_TYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_RSLT_LU_ALOW_TYPEP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_RSLT_LU_ALOW_TYPE_I1 (CLU_RSLT_TYPE_ID)
    , INDEX KSLU_CLU_RSLT_LU_ALOW_TYPE_I2 (LU_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_RSLT_TYP
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_RSLT_TYP
/

CREATE TABLE KSLU_CLU_RSLT_TYP
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_RSLT_TYPP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_SET
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_SET
/

CREATE TABLE KSLU_CLU_SET
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , ADMIN_ORG_ID VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , REFERENCEABLE DECIMAL(1)
        , REUSABLE DECIMAL(1)
        , NAME VARCHAR(255)
        , ST VARCHAR(255)
        , TYPE VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , MEM_QUERY_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_SETP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_SET_I1 (MEM_QUERY_ID)
    , INDEX KSLU_CLU_SET_I2 (RT_DESCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_SET_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_SET_ATTR
/

CREATE TABLE KSLU_CLU_SET_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_SET_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_SET_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_SET_JN_CLU
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_SET_JN_CLU
/

CREATE TABLE KSLU_CLU_SET_JN_CLU
(
      CLU_SET_ID VARCHAR(255) NOT NULL
        , CLU_VER_IND_ID VARCHAR(255) NOT NULL
        , ID VARCHAR(255)
        , VER_NBR DECIMAL(19)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_CLU_SET_JN_CLUP1 PRIMARY KEY(ID)

    , CONSTRAINT KSLU_CLU_SET_JN_CLU_I1 UNIQUE (CLU_SET_ID, CLU_VER_IND_ID)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_SET_JN_CLU_SET
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_SET_JN_CLU_SET
/

CREATE TABLE KSLU_CLU_SET_JN_CLU_SET
(
      CLU_SET_PARENT_ID VARCHAR(255) NOT NULL
        , CLU_SET_CHILD_ID VARCHAR(255) NOT NULL
    


    , INDEX KSLU_CLU_SET_JN_CLU_SET_I1 (CLU_SET_PARENT_ID)
    , INDEX KSLU_CLU_SET_JN_CLU_SET_I2 (CLU_SET_CHILD_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_SET_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_SET_TYPE
/

CREATE TABLE KSLU_CLU_SET_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_SET_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_CLU_SET_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_CLU_SET_TYPE_ATTR
/

CREATE TABLE KSLU_CLU_SET_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_CLU_SET_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_CLU_SET_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_DLVMTHD_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_DLVMTHD_TYPE
/

CREATE TABLE KSLU_DLVMTHD_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_DLVMTHD_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_DLVMTHD_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_DLVMTHD_TYPE_ATTR
/

CREATE TABLE KSLU_DLVMTHD_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_DLVMTHD_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_DLVMTHD_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_INSTFRMT_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_INSTFRMT_TYPE
/

CREATE TABLE KSLU_INSTFRMT_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_INSTFRMT_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_INSTFRMT_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_INSTFRMT_TYPE_ATTR
/

CREATE TABLE KSLU_INSTFRMT_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_INSTFRMT_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_INSTFRMT_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LULU_RELTN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_LULU_RELTN_TYPE
/

CREATE TABLE KSLU_LULU_RELTN_TYPE
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , DESCR VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , REV_DESC VARCHAR(255)
        , REV_NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_LULU_RELTN_TYPEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LULU_RELTN_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_LULU_RELTN_TYPE_ATTR
/

CREATE TABLE KSLU_LULU_RELTN_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_LULU_RELTN_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_LULU_RELTN_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LULU_RELTN_TYPE_JN_LU_TYP
# -----------------------------------------------------------------------
drop table if exists KSLU_LULU_RELTN_TYPE_JN_LU_TYP
/

CREATE TABLE KSLU_LULU_RELTN_TYPE_JN_LU_TYP
(
      LULU_RELTN_TYPE_ID VARCHAR(255) NOT NULL
        , LU_TYPE_ID VARCHAR(255) NOT NULL
    


    , INDEX KSLU_LULU_RELTYP_JN_LUTYP_I1 (LULU_RELTN_TYPE_ID)
    , INDEX KSLU_LULU_RELTYP_JN_LUTYP_I2 (LU_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LUTYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_LUTYPE
/

CREATE TABLE KSLU_LUTYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , DLVR_MTHD VARCHAR(255)
        , INSTR_FRMT VARCHAR(255)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_LUTYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LU_CD_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_LU_CD_TYPE
/

CREATE TABLE KSLU_LU_CD_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_LU_CD_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LU_CD_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_LU_CD_TYPE_ATTR
/

CREATE TABLE KSLU_LU_CD_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_LU_CD_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_LU_CD_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LU_CODE
# -----------------------------------------------------------------------
drop table if exists KSLU_LU_CODE
/

CREATE TABLE KSLU_LU_CODE
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , DESCR VARCHAR(255)
        , TYPE VARCHAR(255)
        , VALUE VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_LU_CODEP1 PRIMARY KEY(ID)


    , INDEX KSLU_LU_CODE_I1 (CLU_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LU_CODE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_LU_CODE_ATTR
/

CREATE TABLE KSLU_LU_CODE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_LU_CODE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_LU_CODE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LU_LU_ALOW_RELTN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_LU_LU_ALOW_RELTN_TYPE
/

CREATE TABLE KSLU_LU_LU_ALOW_RELTN_TYPE
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , LU_TYPE_ID VARCHAR(255)
        , LU_REL_TYPE_ID VARCHAR(255)
        , LU_LU_RELTN_TYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_LU_LU_ALOW_RELTN_TYPEP1 PRIMARY KEY(ID)


    , INDEX KSLU_LU_LU_ALOW_RELTN_TYPE_I1 (LU_REL_TYPE_ID)
    , INDEX KSLU_LU_LU_ALOW_RELTN_TYPE_I2 (LU_LU_RELTN_TYPE_ID)
    , INDEX KSLU_LU_LU_ALOW_RELTN_TYPE_I3 (LU_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LU_PUBL_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_LU_PUBL_TYPE
/

CREATE TABLE KSLU_LU_PUBL_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_LU_PUBL_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LU_PUBL_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_LU_PUBL_TYPE_ATTR
/

CREATE TABLE KSLU_LU_PUBL_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_LU_PUBL_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_LU_PUBL_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_LU_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_LU_TYPE_ATTR
/

CREATE TABLE KSLU_LU_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_LU_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_LU_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_MEMSHIP
# -----------------------------------------------------------------------
drop table if exists KSLU_MEMSHIP
/

CREATE TABLE KSLU_MEMSHIP
(
      ID VARCHAR(255)
        , SEARCH_TYPE_KEY VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_MEMSHIPP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_MEMSHIP_KSLU_SPARAM
# -----------------------------------------------------------------------
drop table if exists KSLU_MEMSHIP_KSLU_SPARAM
/

CREATE TABLE KSLU_MEMSHIP_KSLU_SPARAM
(
      KSLU_MEMSHIP_ID VARCHAR(255) NOT NULL
        , SEARCHPARAMETERS_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C0011543 UNIQUE (SEARCHPARAMETERS_ID)

    , INDEX KSLU_MEMSHIP_KSLU_SPARAM_I1 (KSLU_MEMSHIP_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_RICH_TEXT_T
# -----------------------------------------------------------------------
drop table if exists KSLU_RICH_TEXT_T
/

CREATE TABLE KSLU_RICH_TEXT_T
(
      ID VARCHAR(255)
        , FORMATTED VARCHAR(2000)
        , PLAIN VARCHAR(4000)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_RICH_TEXT_TP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_RSLTUSAGE_LU_ALOW_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_RSLTUSAGE_LU_ALOW_TYPE
/

CREATE TABLE KSLU_RSLTUSAGE_LU_ALOW_TYPE
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , LU_TYPE_ID VARCHAR(255)
        , CLU_RSLT_USAGE_TYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_RSLTUSAGE_LU_ALOW_TYPEP1 PRIMARY KEY(ID)


    , INDEX KSLU_RSLTUSAGE_LU_ALOW_TYP_I1 (CLU_RSLT_USAGE_TYPE_ID)
    , INDEX KSLU_RSLTUSAGE_LU_ALOW_TYP_I2 (LU_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_RSLT_COMP_USG_ALOW_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_RSLT_COMP_USG_ALOW_TYPE
/

CREATE TABLE KSLU_RSLT_COMP_USG_ALOW_TYPE
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , RSLT_COMP_ID VARCHAR(255)
        , RSLT_USG_TYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_RSLT_COMP_USG_ALOW_TYPP1 PRIMARY KEY(ID)


    , INDEX KSLU_RSLTCOMP_USG_ALOW_TYP_I1 (RSLT_USG_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_RSLT_OPT
# -----------------------------------------------------------------------
drop table if exists KSLU_RSLT_OPT
/

CREATE TABLE KSLU_RSLT_OPT
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , RES_COMP_ID VARCHAR(255)
        , ST VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , RES_USAGE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSLU_RSLT_OPTP1 PRIMARY KEY(ID)


    , INDEX KSLU_RSLT_OPT_I1 (RES_USAGE_ID)
    , INDEX KSLU_RSLT_OPT_I2 (RT_DESCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_RSLT_USG_TYPE
# -----------------------------------------------------------------------
drop table if exists KSLU_RSLT_USG_TYPE
/

CREATE TABLE KSLU_RSLT_USG_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_RSLT_USG_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_RSLT_USG_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSLU_RSLT_USG_TYPE_ATTR
/

CREATE TABLE KSLU_RSLT_USG_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_RSLT_USG_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSLU_RSLT_USG_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_RSRC
# -----------------------------------------------------------------------
drop table if exists KSLU_RSRC
/

CREATE TABLE KSLU_RSRC
(
      ID VARCHAR(255)
        , RSRC_TYPE_ID VARCHAR(255)
        , CLU_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_RSRCP1 PRIMARY KEY(ID)

    , CONSTRAINT SYS_C0011561 UNIQUE (RSRC_TYPE_ID, CLU_ID)

    , INDEX KSLU_RSRC_I1 (CLU_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_SPARAM
# -----------------------------------------------------------------------
drop table if exists KSLU_SPARAM
/

CREATE TABLE KSLU_SPARAM
(
      ID VARCHAR(255)
        , SEARCH_PARAM_KEY VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_SPARAMP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_SPARAM_KSLU_SPVALUE
# -----------------------------------------------------------------------
drop table if exists KSLU_SPARAM_KSLU_SPVALUE
/

CREATE TABLE KSLU_SPARAM_KSLU_SPVALUE
(
      KSLU_SPARAM_ID VARCHAR(255) NOT NULL
        , VALUES_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C0011566 UNIQUE (VALUES_ID)

    , INDEX KSLU_SPARAM_KSLU_SPVALUE_I1 (KSLU_SPARAM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSLU_SPVALUE
# -----------------------------------------------------------------------
drop table if exists KSLU_SPVALUE
/

CREATE TABLE KSLU_SPVALUE
(
      ID VARCHAR(255)
        , VALUE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSLU_SPVALUEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSMG_MESSAGE
# -----------------------------------------------------------------------
drop table if exists KSMG_MESSAGE
/

CREATE TABLE KSMG_MESSAGE
(
      ID VARCHAR(255)
        , MSG_ID VARCHAR(255)
        , LOCALE VARCHAR(255)
        , GRP_NAME VARCHAR(255)
        , MSG_VALUE VARCHAR(2000)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSMG_MESSAGEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG
/

CREATE TABLE KSOR_ORG
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , LNG_DESCR VARCHAR(2000)
        , LNG_NAME VARCHAR(255)
        , SHRT_DESCR VARCHAR(500)
        , SHRT_NAME VARCHAR(255)
        , ST VARCHAR(255)
        , TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSOR_ORGP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_I1 (TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_ATTR
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_ATTR
/

CREATE TABLE KSOR_ORG_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_HIRCHY
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_HIRCHY
/

CREATE TABLE KSOR_ORG_HIRCHY
(
      ID VARCHAR(255)
        , DESCR VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , ROOT_ORG VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_HIRCHYP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_HIRCHY_I1 (ROOT_ORG)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_HIRCHY_ATTR
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_HIRCHY_ATTR
/

CREATE TABLE KSOR_ORG_HIRCHY_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_HIRCHY_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_HIRCHY_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_HIRCHY_JN_ORG_TYPE
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_HIRCHY_JN_ORG_TYPE
/

CREATE TABLE KSOR_ORG_HIRCHY_JN_ORG_TYPE
(
      ORG_HIRCHY_ID VARCHAR(255) NOT NULL
        , ORG_TYPE_ID VARCHAR(255) NOT NULL
    


    , INDEX KSOR_ORG_HIRCHY_JN_ORG_TYP_I1 (ORG_HIRCHY_ID)
    , INDEX KSOR_ORG_HIRCHY_JN_ORG_TYP_I2 (ORG_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_JN_ORG_PERS_REL_TYPE
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_JN_ORG_PERS_REL_TYPE
/

CREATE TABLE KSOR_ORG_JN_ORG_PERS_REL_TYPE
(
      ORG_ID VARCHAR(255) NOT NULL
        , ORG_PERS_RELTN_TYPE_ID VARCHAR(255) NOT NULL
    


    , INDEX KSOR_ORG_JN_ORG_PERREL_TYP_I1 (ORG_PERS_RELTN_TYPE_ID)
    , INDEX KSOR_ORG_JN_ORG_PERREL_TYP_I2 (ORG_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_ORG_RELTN
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_ORG_RELTN
/

CREATE TABLE KSOR_ORG_ORG_RELTN
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , ST VARCHAR(255)
        , ORG VARCHAR(255)
        , RELATED_ORG VARCHAR(255)
        , TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSOR_ORG_ORG_RELTNP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_ORG_RELTN_I1 (ORG)
    , INDEX KSOR_ORG_ORG_RELTN_I2 (RELATED_ORG)
    , INDEX KSOR_ORG_ORG_RELTN_I3 (TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_ORG_RELTN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_ORG_RELTN_ATTR
/

CREATE TABLE KSOR_ORG_ORG_RELTN_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_ORG_RELTN_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_ORG_RELTN_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_ORG_RELTN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_ORG_RELTN_TYPE
/

CREATE TABLE KSOR_ORG_ORG_RELTN_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , REV_DESCR VARCHAR(255)
        , REV_NAME VARCHAR(255)
        , ORG_HIRCHY VARCHAR(255)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_ORG_RELTN_TYPEP1 PRIMARY KEY(TYPE_KEY)


    , INDEX KSOR_ORG_ORG_RELTN_TYPE_I1 (ORG_HIRCHY)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_ORG_RELTN_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_ORG_RELTN_TYPE_ATTR
/

CREATE TABLE KSOR_ORG_ORG_RELTN_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_ORG_RELTN_TYPE_ATTP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_ORG_REL_TYP_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_PERS_RELTN
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_PERS_RELTN
/

CREATE TABLE KSOR_ORG_PERS_RELTN
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , PERS_ID VARCHAR(255)
        , ST VARCHAR(255)
        , ORG VARCHAR(255)
        , ORG_PERS_RELTN_TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSOR_ORG_PERS_RELTNP1 PRIMARY KEY(ID)

    , CONSTRAINT SYS_C0011691 UNIQUE (ORG_PERS_RELTN_TYPE, ORG, PERS_ID)

    , INDEX KSOR_ORG_PERS_RELTN_I1 (ORG)
    , INDEX KSOR_ORG_PERS_RELTN_I2 (ORG_PERS_RELTN_TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_PERS_RELTN_ATTR
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_PERS_RELTN_ATTR
/

CREATE TABLE KSOR_ORG_PERS_RELTN_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_PERS_RELTN_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_PERS_RELTN_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_PERS_RELTN_TYPE
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_PERS_RELTN_TYPE
/

CREATE TABLE KSOR_ORG_PERS_RELTN_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_PERS_RELTN_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_PERS_RELTN_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_PERS_RELTN_TYPE_ATTR
/

CREATE TABLE KSOR_ORG_PERS_RELTN_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_PERS_RELTN_TYPE_ATP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_PERS_REL_TYP_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_POS_RESTR
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_POS_RESTR
/

CREATE TABLE KSOR_ORG_POS_RESTR
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , DESCR VARCHAR(2000)
        , MAX_NUM_RELTN VARCHAR(255)
        , MIN_NUM_RELTN DECIMAL(10)
        , ATP_DUR_TYP_KEY VARCHAR(255)
        , TM_QUANTITY DECIMAL(10)
        , TTL VARCHAR(255)
        , ORG VARCHAR(255)
        , PERS_RELTN_TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSOR_ORG_POS_RESTRP1 PRIMARY KEY(ID)

    , CONSTRAINT SYS_C0011701 UNIQUE (ORG, PERS_RELTN_TYPE)

    , INDEX KSOR_ORG_POS_RESTR_I1 (PERS_RELTN_TYPE)
    , INDEX KSOR_ORG_POS_RESTR_I2 (ORG)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_POS_RESTR_ATTR
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_POS_RESTR_ATTR
/

CREATE TABLE KSOR_ORG_POS_RESTR_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_POS_RESTR_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_POS_RESTR_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_TYPE
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_TYPE
/

CREATE TABLE KSOR_ORG_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_TYPE_ATTR
/

CREATE TABLE KSOR_ORG_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSOR_ORG_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSOR_ORG_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSOR_ORG_TYPE_JN_ORG_PERRL_TYP
# -----------------------------------------------------------------------
drop table if exists KSOR_ORG_TYPE_JN_ORG_PERRL_TYP
/

CREATE TABLE KSOR_ORG_TYPE_JN_ORG_PERRL_TYP
(
      ORG_TYPE_ID VARCHAR(255) NOT NULL
        , ORG_PERS_RELTN_TYPE_ID VARCHAR(255) NOT NULL
    


    , INDEX KSOR_ORGTYP_JN_ORGPREL_TYP_I1 (ORG_PERS_RELTN_TYPE_ID)
    , INDEX KSOR_ORGTYP_JN_ORGPREL_TYP_I2 (ORG_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL
/

CREATE TABLE KSPR_PROPOSAL
(
      PROPOSAL_ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , DETAIL_DESC VARCHAR(255)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , RATIONALE VARCHAR(255)
        , STATE VARCHAR(255)
        , WORKFLOW_ID VARCHAR(255)
        , TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSPR_PROPOSALP1 PRIMARY KEY(PROPOSAL_ID)


    , INDEX KSPR_PROPOSAL_I1 (TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL_ATTR
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL_ATTR
/

CREATE TABLE KSPR_PROPOSAL_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSPR_PROPOSAL_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSPR_PROPOSAL_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL_JN_ORG
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL_JN_ORG
/

CREATE TABLE KSPR_PROPOSAL_JN_ORG
(
      ORGREF_ID VARCHAR(255)
        , ORG_ID VARCHAR(255)
        , PROPOSAL_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSPR_PROPOSAL_JN_ORGP1 PRIMARY KEY(ORGREF_ID)


    , INDEX KSPR_PROPOSAL_JN_ORG_I1 (PROPOSAL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL_JN_PERSON
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL_JN_PERSON
/

CREATE TABLE KSPR_PROPOSAL_JN_PERSON
(
      ID VARCHAR(255)
        , PERSONREF_ID VARCHAR(255)
        , PROPOSAL_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSPR_PROPOSAL_JN_PERSONP1 PRIMARY KEY(ID)


    , INDEX KSPR_PROPOSAL_JN_PERSON_I1 (PROPOSAL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL_JN_REFERENCE
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL_JN_REFERENCE
/

CREATE TABLE KSPR_PROPOSAL_JN_REFERENCE
(
      PROPOSAL_ID VARCHAR(255) NOT NULL
        , REFERENCE_ID VARCHAR(255) NOT NULL
    


    , INDEX KSPR_PROPOSAL_JN_REFERENCE_I1 (REFERENCE_ID)
    , INDEX KSPR_PROPOSAL_JN_REFERENCE_I2 (PROPOSAL_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL_REFERENCE
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL_REFERENCE
/

CREATE TABLE KSPR_PROPOSAL_REFERENCE
(
      REFERENCE_ID VARCHAR(255)
        , OBJECT_REFERENCE_ID VARCHAR(255)
        , TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSPR_PROPOSAL_REFERENCEP1 PRIMARY KEY(REFERENCE_ID)


    , INDEX KSPR_PROPOSAL_REFERENCE_I1 (TYPE)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL_REFTYPE
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL_REFTYPE
/

CREATE TABLE KSPR_PROPOSAL_REFTYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSPR_PROPOSAL_REFTYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL_REFTYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL_REFTYPE_ATTR
/

CREATE TABLE KSPR_PROPOSAL_REFTYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSPR_PROPOSAL_REFTYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSPR_PROPOSAL_REFTYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL_TYPE
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL_TYPE
/

CREATE TABLE KSPR_PROPOSAL_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSPR_PROPOSAL_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_PROPOSAL_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSPR_PROPOSAL_TYPE_ATTR
/

CREATE TABLE KSPR_PROPOSAL_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSPR_PROPOSAL_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSPR_PROPOSAL_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSPR_RICH_TEXT_T
# -----------------------------------------------------------------------
drop table if exists KSPR_RICH_TEXT_T
/

CREATE TABLE KSPR_RICH_TEXT_T
(
      ID VARCHAR(255)
        , FORMATTED VARCHAR(2000)
        , PLAIN VARCHAR(2000)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSPR_RICH_TEXT_TP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSSC_SUBJ_CD
# -----------------------------------------------------------------------
drop table if exists KSSC_SUBJ_CD
/

CREATE TABLE KSSC_SUBJ_CD
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , NAME VARCHAR(255)
        , STATE VARCHAR(255)
        , CD VARCHAR(255)
        , TYPE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSSC_SUBJ_CDP1 PRIMARY KEY(ID)

    , CONSTRAINT KSSC_SUBJ_CD_IX1 UNIQUE (CD)


) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSSC_SUBJ_CD_JN_ORG
# -----------------------------------------------------------------------
drop table if exists KSSC_SUBJ_CD_JN_ORG
/

CREATE TABLE KSSC_SUBJ_CD_JN_ORG
(
      ID VARCHAR(255)
        , ORG_ID VARCHAR(255)
        , SUBJ_CD_ID VARCHAR(255)
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSSC_SUBJ_CD_JN_ORGP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSSC_SUBJ_CD_TYPE
# -----------------------------------------------------------------------
drop table if exists KSSC_SUBJ_CD_TYPE
/

CREATE TABLE KSSC_SUBJ_CD_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSSC_SUBJ_CD_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_NL_USAGE_TYPE
# -----------------------------------------------------------------------
drop table if exists KSST_NL_USAGE_TYPE
/

CREATE TABLE KSST_NL_USAGE_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_NL_USAGE_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_OBJECT_SUB_TYPE
# -----------------------------------------------------------------------
drop table if exists KSST_OBJECT_SUB_TYPE
/

CREATE TABLE KSST_OBJECT_SUB_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_OBJECT_SUB_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_OBJECT_SUB_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSST_OBJECT_SUB_TYPE_ATTR
/

CREATE TABLE KSST_OBJECT_SUB_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_OBJECT_SUB_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSST_OBJECT_SUB_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_OBJECT_TYPE
# -----------------------------------------------------------------------
drop table if exists KSST_OBJECT_TYPE
/

CREATE TABLE KSST_OBJECT_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_OBJECT_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_OBJECT_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSST_OBJECT_TYPE_ATTR
/

CREATE TABLE KSST_OBJECT_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_OBJECT_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSST_OBJECT_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_OBJ_TYP_JN_OBJ_SUB_TYP
# -----------------------------------------------------------------------
drop table if exists KSST_OBJ_TYP_JN_OBJ_SUB_TYP
/

CREATE TABLE KSST_OBJ_TYP_JN_OBJ_SUB_TYP
(
      OBJ_TYPE_ID VARCHAR(255) NOT NULL
        , OBJ_SUB_TYPE_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C0011792 UNIQUE (OBJ_SUB_TYPE_ID)

    , INDEX KSST_OBJ_TYP_JN_OBJ_SUBTYP_I2 (OBJ_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_RCTYP_JN_RCFLDTYP
# -----------------------------------------------------------------------
drop table if exists KSST_RCTYP_JN_RCFLDTYP
/

CREATE TABLE KSST_RCTYP_JN_RCFLDTYP
(
      REQ_COMP_TYPE_ID VARCHAR(255) NOT NULL
        , REQ_COMP_FIELD_TYPE_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT IX1 UNIQUE (REQ_COMP_TYPE_ID, REQ_COMP_FIELD_TYPE_ID)

    , INDEX KSST_RCTYP_JN_RCFLDTYP_I1 (REQ_COMP_FIELD_TYPE_ID)
    , INDEX KSST_RCTYP_JN_RCFLDTYP_I2 (REQ_COMP_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_RC_JN_RC_FIELD
# -----------------------------------------------------------------------
drop table if exists KSST_RC_JN_RC_FIELD
/

CREATE TABLE KSST_RC_JN_RC_FIELD
(
      REQ_COM_ID VARCHAR(255) NOT NULL
        , REQ_COM_FIELD_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C0011797 UNIQUE (REQ_COM_FIELD_ID)

    , INDEX KSST_RC_JN_RC_FIELD_I1 (REQ_COM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REF_STMT_REL
# -----------------------------------------------------------------------
drop table if exists KSST_REF_STMT_REL
/

CREATE TABLE KSST_REF_STMT_REL
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , REF_OBJ_ID VARCHAR(255)
        , REF_OBJ_TYPE_KEY VARCHAR(255)
        , ST VARCHAR(255)
        , REF_STMT_REL_TYPE_ID VARCHAR(255)
        , STMT_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSST_REF_STMT_RELP1 PRIMARY KEY(ID)


    , INDEX KSST_REF_STMT_REL_I1 (REF_STMT_REL_TYPE_ID)
    , INDEX KSST_REF_STMT_REL_I2 (STMT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REF_STMT_REL_ATTR
# -----------------------------------------------------------------------
drop table if exists KSST_REF_STMT_REL_ATTR
/

CREATE TABLE KSST_REF_STMT_REL_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_REF_STMT_REL_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSST_REF_STMT_REL_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REF_STMT_REL_TYPE
# -----------------------------------------------------------------------
drop table if exists KSST_REF_STMT_REL_TYPE
/

CREATE TABLE KSST_REF_STMT_REL_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_REF_STMT_REL_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REF_STMT_REL_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSST_REF_STMT_REL_TYPE_ATTR
/

CREATE TABLE KSST_REF_STMT_REL_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_REF_STMT_REL_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSST_REF_STMT_REL_TYP_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REQ_COM
# -----------------------------------------------------------------------
drop table if exists KSST_REQ_COM
/

CREATE TABLE KSST_REQ_COM
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , ST VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , REQ_COM_TYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSST_REQ_COMP1 PRIMARY KEY(ID)


    , INDEX KSST_REQ_COM_I1 (REQ_COM_TYPE_ID)
    , INDEX KSST_REQ_COM_I2 (RT_DESCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REQ_COM_FIELD
# -----------------------------------------------------------------------
drop table if exists KSST_REQ_COM_FIELD
/

CREATE TABLE KSST_REQ_COM_FIELD
(
      ID VARCHAR(255)
        , REQ_COM_FIELD_TYPE VARCHAR(255)
        , VALUE VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_REQ_COM_FIELDP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REQ_COM_FIELD_TYPE
# -----------------------------------------------------------------------
drop table if exists KSST_REQ_COM_FIELD_TYPE
/

CREATE TABLE KSST_REQ_COM_FIELD_TYPE
(
      ID VARCHAR(255)
        , DATA_TYPE VARCHAR(255) NOT NULL
        , DESCR VARCHAR(255) NOT NULL
        , INVALID_CHARS VARCHAR(255)
        , MAX_LENGTH DECIMAL(10)
        , MAX_OCCURS DECIMAL(10)
        , MAX_VALUE VARCHAR(255)
        , MIN_LENGTH DECIMAL(10)
        , MIN_OCCURS DECIMAL(10)
        , MIN_VALUE VARCHAR(255)
        , NAME VARCHAR(255) NOT NULL
        , READ_ONLY DECIMAL(1) NOT NULL
        , VALID_CHARS VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_REQ_COM_FIELD_TYPEP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REQ_COM_TYPE
# -----------------------------------------------------------------------
drop table if exists KSST_REQ_COM_TYPE
/

CREATE TABLE KSST_REQ_COM_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_REQ_COM_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REQ_COM_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSST_REQ_COM_TYPE_ATTR
/

CREATE TABLE KSST_REQ_COM_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_REQ_COM_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSST_REQ_COM_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_REQ_COM_TYPE_NL_TMPL
# -----------------------------------------------------------------------
drop table if exists KSST_REQ_COM_TYPE_NL_TMPL
/

CREATE TABLE KSST_REQ_COM_TYPE_NL_TMPL
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , LANGUAGE VARCHAR(2)
        , NL_USUAGE_TYPE_KEY VARCHAR(255)
        , TEMPLATE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_REQ_COM_TYPE_NL_TMPLP1 PRIMARY KEY(ID)


    , INDEX KSST_REQ_COM_TYPE_NL_TMPL_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_RICH_TEXT_T
# -----------------------------------------------------------------------
drop table if exists KSST_RICH_TEXT_T
/

CREATE TABLE KSST_RICH_TEXT_T
(
      ID VARCHAR(255)
        , FORMATTED VARCHAR(2000)
        , PLAIN VARCHAR(2000)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_RICH_TEXT_TP1 PRIMARY KEY(ID)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_RSTMT_RTYP_JN_OSUB_TYP
# -----------------------------------------------------------------------
drop table if exists KSST_RSTMT_RTYP_JN_OSUB_TYP
/

CREATE TABLE KSST_RSTMT_RTYP_JN_OSUB_TYP
(
      REF_STMT_REL_TYPE_ID VARCHAR(255) NOT NULL
        , OBJ_SUB_TYPE_ID VARCHAR(255) NOT NULL
    


    , INDEX KSST_RSTMT_RTYP_JN_OSUBTYP_I1 (OBJ_SUB_TYPE_ID)
    , INDEX KSST_RSTMT_RTYP_JN_OSUBTYP_I2 (REF_STMT_REL_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_RSTMT_RTYP_JN_STMT_TYP
# -----------------------------------------------------------------------
drop table if exists KSST_RSTMT_RTYP_JN_STMT_TYP
/

CREATE TABLE KSST_RSTMT_RTYP_JN_STMT_TYP
(
      REF_STMT_REL_TYPE_ID VARCHAR(255) NOT NULL
        , STMT_TYPE_ID VARCHAR(255) NOT NULL
    


    , INDEX KSST_RSTMT_RTYP_JN_ST_TYP_I1 (REF_STMT_REL_TYPE_ID)
    , INDEX KSST_RSTMT_RTYP_JN_ST_TYP_I2 (STMT_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_STMT
# -----------------------------------------------------------------------
drop table if exists KSST_STMT
/

CREATE TABLE KSST_STMT
(
      ID VARCHAR(255)
        , CREATEID VARCHAR(255)
        , CREATETIME DATETIME
        , UPDATEID VARCHAR(255)
        , UPDATETIME DATETIME
        , VER_NBR DECIMAL(19) NOT NULL
        , NAME VARCHAR(255)
        , OPERATOR VARCHAR(255)
        , ST VARCHAR(255)
        , RT_DESCR_ID VARCHAR(255)
        , STMT_TYPE_ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
    
    , CONSTRAINT KSST_STMTP1 PRIMARY KEY(ID)


    , INDEX KSST_STMT_I1 (STMT_TYPE_ID)
    , INDEX KSST_STMT_I2 (RT_DESCR_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_STMT_ATTR
# -----------------------------------------------------------------------
drop table if exists KSST_STMT_ATTR
/

CREATE TABLE KSST_STMT_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_STMT_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSST_STMT_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_STMT_JN_REQ_COM
# -----------------------------------------------------------------------
drop table if exists KSST_STMT_JN_REQ_COM
/

CREATE TABLE KSST_STMT_JN_REQ_COM
(
      STMT_ID VARCHAR(255) NOT NULL
        , REQ_COM_ID VARCHAR(255) NOT NULL
    


    , INDEX KSST_STMT_JN_REQ_COM_I1 (STMT_ID)
    , INDEX KSST_STMT_JN_REQ_COM_I2 (REQ_COM_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_STMT_JN_STMT
# -----------------------------------------------------------------------
drop table if exists KSST_STMT_JN_STMT
/

CREATE TABLE KSST_STMT_JN_STMT
(
      STMT_ID VARCHAR(255) NOT NULL
        , CHLD_STMT_ID VARCHAR(255) NOT NULL
    

    , CONSTRAINT SYS_C0011839 UNIQUE (CHLD_STMT_ID)

    , INDEX KSST_STMT_JN_STMT_I1 (STMT_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_STMT_TYPE
# -----------------------------------------------------------------------
drop table if exists KSST_STMT_TYPE
/

CREATE TABLE KSST_STMT_TYPE
(
      TYPE_KEY VARCHAR(255)
        , TYPE_DESC VARCHAR(2000)
        , EFF_DT DATETIME
        , EXPIR_DT DATETIME
        , NAME VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_STMT_TYPEP1 PRIMARY KEY(TYPE_KEY)



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_STMT_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSST_STMT_TYPE_ATTR
/

CREATE TABLE KSST_STMT_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_STMT_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSST_STMT_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_STMT_TYP_JN_RC_TYP
# -----------------------------------------------------------------------
drop table if exists KSST_STMT_TYP_JN_RC_TYP
/

CREATE TABLE KSST_STMT_TYP_JN_RC_TYP
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , REQ_COM_TYPE_ID VARCHAR(255)
        , SORT_ORDER DECIMAL(10)
        , STMT_TYPE_ID VARCHAR(255)
    
    , CONSTRAINT KSST_STMT_TYP_JN_RC_TYPP1 PRIMARY KEY(ID)


    , INDEX KSST_STMT_TYP_JN_RC_TYP_I1 (REQ_COM_TYPE_ID)
    , INDEX KSST_STMT_TYP_JN_RC_TYP_I2 (STMT_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_STMT_TYP_JN_STMT_TYP
# -----------------------------------------------------------------------
drop table if exists KSST_STMT_TYP_JN_STMT_TYP
/

CREATE TABLE KSST_STMT_TYP_JN_STMT_TYP
(
      ID VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
        , CHLD_STMT_TYPE_ID VARCHAR(255)
        , SORT_ORDER DECIMAL(10)
        , STMT_TYPE_ID VARCHAR(255)
    
    , CONSTRAINT KSST_STMT_TYP_JN_STMT_TYPP1 PRIMARY KEY(ID)


    , INDEX KSST_STMT_TYP_JN_STMT_TYP_I1 (CHLD_STMT_TYPE_ID)
    , INDEX KSST_STMT_TYP_JN_STMT_TYP_I2 (STMT_TYPE_ID)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KSST_USAGE_TYPE_ATTR
# -----------------------------------------------------------------------
drop table if exists KSST_USAGE_TYPE_ATTR
/

CREATE TABLE KSST_USAGE_TYPE_ATTR
(
      ID VARCHAR(255)
        , ATTR_NAME VARCHAR(255)
        , ATTR_VALUE VARCHAR(2000)
        , OWNER VARCHAR(255)
        , OBJ_ID VARCHAR(36)
        , VER_NBR DECIMAL(19)
    
    , CONSTRAINT KSST_USAGE_TYPE_ATTRP1 PRIMARY KEY(ID)


    , INDEX KSST_USAGE_TYPE_ATTR_I1 (OWNER)

) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KS_DB_VERSION
# -----------------------------------------------------------------------
drop table if exists KS_DB_VERSION
/

CREATE TABLE KS_DB_VERSION
(
      VERSION VARCHAR(255)
        , MODULE_NAME VARCHAR(255)
        , UPGRADE_TIME DATETIME
        , BUILD_ID VARCHAR(255)
        , BUILD_TIMESTAMP VARCHAR(255)
    



) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


# -----------------------------------------------------------------------
# KRIM_GRP_MBR_V
# -----------------------------------------------------------------------
drop view if exists KRIM_GRP_MBR_V
/
CREATE VIEW KRIM_GRP_MBR_V AS 
SELECT g.NMSPC_CD
, g.grp_nm
, g.GRP_ID
, p.PRNCPL_NM
, p.PRNCPL_ID
, mg.GRP_NM AS mbr_grp_nm
, mg.GRP_ID AS mbr_grp_id
FROM KRIM_GRP_MBR_T gm
LEFT JOIN krim_grp_t g
ON g.GRP_ID = gm.GRP_ID
LEFT OUTER JOIN krim_grp_t mg
ON mg.GRP_ID = gm.MBR_ID
AND gm.MBR_TYP_CD = 'G'
LEFT OUTER JOIN krim_prncpl_t p
ON p.PRNCPL_ID = gm.MBR_ID
AND gm.MBR_TYP_CD = 'P'
LEFT OUTER JOIN krim_entity_nm_t en
ON en.ENTITY_ID = p.ENTITY_ID
AND en.DFLT_IND = 'Y'
AND en.ACTV_IND = 'Y'
ORDER BY nmspc_cd, grp_nm, prncpl_nm
/


# -----------------------------------------------------------------------
# KRIM_GRP_V
# -----------------------------------------------------------------------
drop view if exists KRIM_GRP_V
/
CREATE VIEW KRIM_GRP_V AS 
SELECT g.NMSPC_CD
, g.grp_nm
, g.GRP_ID
, t.NM AS grp_typ_nm
, a.NM AS attr_nm
, d.ATTR_VAL AS attr_val
FROM krim_grp_t g
LEFT OUTER JOIN KRIM_GRP_ATTR_DATA_T d
ON d.grp_id = g.GRP_ID
LEFT OUTER JOIN KRIM_ATTR_DEFN_T a
ON a.KIM_ATTR_DEFN_ID = d.KIM_ATTR_DEFN_ID
LEFT OUTER JOIN KRIM_TYP_T t
ON g.KIM_TYP_ID = t.KIM_TYP_ID
/


# -----------------------------------------------------------------------
# KRIM_PERM_ATTR_V
# -----------------------------------------------------------------------
drop view if exists KRIM_PERM_ATTR_V
/
CREATE VIEW KRIM_PERM_ATTR_V AS 
SELECT
t.nmspc_cd AS tmpl_nmspc_cd
, t.NM AS tmpl_nm
, t.PERM_TMPL_ID
, p.nmspc_cd AS perm_nmspc_cd
, p.NM AS perm_nm
, p.PERM_ID
, a.NM AS attr_nm
, ad.ATTR_VAL AS attr_val
FROM KRIM_PERM_T p
LEFT JOIN KRIM_PERM_TMPL_T t
ON p.PERM_TMPL_ID = t.PERM_TMPL_ID
LEFT OUTER JOIN KRIM_PERM_ATTR_DATA_T ad
ON p.PERM_ID = ad.perm_id
LEFT OUTER JOIN KRIM_ATTR_DEFN_T a
ON ad.KIM_ATTR_DEFN_ID = a.KIM_ATTR_DEFN_ID
ORDER BY tmpl_nmspc_cd, tmpl_nm, perm_nmspc_cd, perm_id, attr_nm
/


# -----------------------------------------------------------------------
# KRIM_PERM_V
# -----------------------------------------------------------------------
drop view if exists KRIM_PERM_V
/
CREATE VIEW KRIM_PERM_V AS 
SELECT
t.nmspc_cd AS tmpl_nmspc_cd
, t.NM AS tmpl_nm
, t.PERM_TMPL_ID
, p.nmspc_cd AS perm_nmspc_cd
, p.NM AS perm_nm
, p.PERM_ID
, typ.NM AS perm_typ_nm
, typ.SRVC_NM
FROM KRIM_PERM_T p
INNER JOIN KRIM_PERM_TMPL_T t
ON p.PERM_TMPL_ID = t.PERM_TMPL_ID
LEFT OUTER JOIN KRIM_TYP_T typ
ON t.KIM_TYP_ID = typ.KIM_TYP_ID
/


# -----------------------------------------------------------------------
# KRIM_PRNCPL_V
# -----------------------------------------------------------------------
drop view if exists KRIM_PRNCPL_V
/
CREATE VIEW KRIM_PRNCPL_V AS 
SELECT
p.PRNCPL_ID
,p.PRNCPL_NM
,en.FIRST_NM
,en.LAST_NM
,ea.AFLTN_TYP_CD
,ea.CAMPUS_CD
,eei.EMP_STAT_CD
,eei.EMP_TYP_CD
FROM krim_prncpl_t p
LEFT OUTER JOIN krim_entity_emp_info_t eei
ON eei.ENTITY_ID = p.ENTITY_ID
LEFT OUTER JOIN krim_entity_afltn_t ea
ON ea.ENTITY_ID = p.ENTITY_ID
LEFT OUTER JOIN krim_entity_nm_t en
ON p.ENTITY_ID = en.ENTITY_ID
AND 'Y' = en.DFLT_IND
/


# -----------------------------------------------------------------------
# KRIM_ROLE_GRP_V
# -----------------------------------------------------------------------
drop view if exists KRIM_ROLE_GRP_V
/
CREATE VIEW KRIM_ROLE_GRP_V AS 
SELECT r.NMSPC_CD
, r.ROLE_NM
, r.role_id
, g.NMSPC_CD AS grp_nmspc_cd
, g.GRP_NM
, rm.ROLE_MBR_ID
, a.NM AS attr_nm
, d.ATTR_VAL AS attr_val
FROM KRIM_ROLE_MBR_T rm
LEFT JOIN KRIM_ROLE_T r
ON r.ROLE_ID = rm.ROLE_ID
LEFT JOIN KRIM_GRP_T g
ON g.GRP_ID = rm.MBR_ID
LEFT OUTER JOIN KRIM_ROLE_MBR_ATTR_DATA_T d
ON d.role_mbr_id = rm.ROLE_MBR_ID
LEFT OUTER JOIN KRIM_ATTR_DEFN_T a
ON a.KIM_ATTR_DEFN_ID = d.KIM_ATTR_DEFN_ID
WHERE rm.MBR_TYP_CD = 'G'
ORDER BY nmspc_cd, role_nm, grp_nmspc_cd, grp_nm, role_mbr_id, attr_nm
/


# -----------------------------------------------------------------------
# KRIM_ROLE_PERM_V
# -----------------------------------------------------------------------
drop view if exists KRIM_ROLE_PERM_V
/
CREATE VIEW KRIM_ROLE_PERM_V AS 
SELECT r.NMSPC_CD
, r.ROLE_NM
, r.role_id
, pt.NMSPC_CD AS tmpl_nmspc_cd
, pt.NM AS tmpl_nm
, pt.PERM_TMPL_ID
, p.NMSPC_CD AS perm_nmpsc_cd
, p.NM AS perm_nm
, p.PERM_ID
, a.NM AS attr_nm
, ad.ATTR_VAL AS attr_val
FROM KRIM_PERM_T p
LEFT JOIN KRIM_PERM_TMPL_T pt
ON p.PERM_TMPL_ID = pt.PERM_TMPL_ID
LEFT OUTER JOIN KRIM_PERM_ATTR_DATA_T ad
ON p.PERM_ID = ad.perm_id
LEFT OUTER JOIN KRIM_ATTR_DEFN_T a
ON ad.KIM_ATTR_DEFN_ID = a.KIM_ATTR_DEFN_ID
LEFT OUTER JOIN KRIM_ROLE_PERM_T rp
ON rp.PERM_ID = p.PERM_ID
LEFT OUTER JOIN KRIM_ROLE_T r
ON rp.ROLE_ID = r.ROLE_ID
ORDER BY NMSPC_CD, role_nm, tmpl_nmspc_cd, tmpl_nm, perm_id, attr_nm
/


# -----------------------------------------------------------------------
# KRIM_ROLE_PRNCPL_V
# -----------------------------------------------------------------------
drop view if exists KRIM_ROLE_PRNCPL_V
/
CREATE VIEW KRIM_ROLE_PRNCPL_V AS 
SELECT r.NMSPC_CD
, r.ROLE_NM
, r.ROLE_ID
, p.PRNCPL_NM
, p.PRNCPL_ID
, en.FIRST_NM
, en.LAST_NM
, rm.ROLE_MBR_ID
, ad.NM AS attr_nm
, rmad.ATTR_VAL AS attr_val
FROM KRIM_ROLE_T r
LEFT OUTER JOIN KRIM_ROLE_MBR_T rm
ON r.ROLE_ID = rm.ROLE_ID
LEFT OUTER JOIN KRIM_ROLE_MBR_ATTR_DATA_T rmad
ON rm.ROLE_MBR_ID = rmad.role_mbr_id
LEFT OUTER JOIN KRIM_ATTR_DEFN_T ad
ON rmad.KIM_ATTR_DEFN_ID = ad.KIM_ATTR_DEFN_ID
LEFT OUTER JOIN KRIM_PRNCPL_T p
ON rm.MBR_ID = p.PRNCPL_ID
AND rm.mbr_typ_cd = 'P'
LEFT OUTER JOIN KRIM_ENTITY_NM_T en
ON p.ENTITY_ID = en.ENTITY_ID
WHERE (en.DFLT_IND = 'Y')
ORDER BY nmspc_cd, role_nm, prncpl_nm, rm.ROLE_MBR_ID, attr_nm
/


# -----------------------------------------------------------------------
# KRIM_ROLE_ROLE_V
# -----------------------------------------------------------------------
drop view if exists KRIM_ROLE_ROLE_V
/
CREATE VIEW KRIM_ROLE_ROLE_V AS 
SELECT r.NMSPC_CD
, r.ROLE_NM
, r.role_id
, mr.NMSPC_CD AS mbr_role_nmspc_cd
, mr.role_NM AS mbr_role_nm
, mr.role_id AS mbr_role_id
, rm.role_mbr_id
, a.NM AS attr_nm
, d.ATTR_VAL AS attr_val
FROM KRIM_ROLE_MBR_T rm
LEFT JOIN KRIM_ROLE_T r
ON r.ROLE_ID = rm.ROLE_ID
LEFT JOIN KRIM_role_T mr
ON mr.role_ID = rm.MBR_ID
LEFT OUTER JOIN KRIM_ROLE_MBR_ATTR_DATA_T d
ON d.role_mbr_id = rm.ROLE_MBR_ID
LEFT OUTER JOIN KRIM_ATTR_DEFN_T a
ON a.KIM_ATTR_DEFN_ID = d.KIM_ATTR_DEFN_ID
WHERE rm.MBR_TYP_CD = 'R'
ORDER BY nmspc_cd, role_nm, mbr_role_nmspc_cd, mbr_role_nm, role_mbr_id, attr_nm
/


# -----------------------------------------------------------------------
# KRIM_ROLE_V
# -----------------------------------------------------------------------
drop view if exists KRIM_ROLE_V
/
CREATE VIEW KRIM_ROLE_V AS 
SELECT r.NMSPC_CD
, r.ROLE_NM
, r.ROLE_ID
, t.nm AS role_typ_nm
, t.SRVC_NM
, t.KIM_TYP_ID
FROM KRIM_ROLE_T r
, KRIM_TYP_T t
WHERE t.KIM_TYP_ID = r.KIM_TYP_ID
AND r.ACTV_IND = 'Y'
ORDER BY nmspc_cd
, role_nm
/


# -----------------------------------------------------------------------
# KRIM_RSP_ATTR_V
# -----------------------------------------------------------------------
drop view if exists KRIM_RSP_ATTR_V
/
CREATE VIEW KRIM_RSP_ATTR_V AS 
SELECT
krim_typ_t.NM AS responsibility_type_name
, KRIM_rsp_TMPL_T.NM AS rsp_TEMPLATE_NAME
, KRIM_rsp_T.nmspc_cd AS rsp_namespace_code
, KRIM_rsp_T.NM AS rsp_NAME
, krim_rsp_t.RSP_ID AS rsp_id
, KRIM_ATTR_DEFN_T.NM AS attribute_name
, KRIM_rsp_ATTR_DATA_T.ATTR_VAL AS attribute_value
FROM KRIM_rsp_T KRIM_rsp_T
INNER JOIN KRIM_rsp_ATTR_DATA_T KRIM_rsp_ATTR_DATA_T
ON KRIM_rsp_T.rsp_ID = KRIM_rsp_ATTR_DATA_T.rsp_id
INNER JOIN KRIM_ATTR_DEFN_T KRIM_ATTR_DEFN_T
ON KRIM_rsp_ATTR_DATA_T.KIM_ATTR_DEFN_ID = KRIM_ATTR_DEFN_T.KIM_ATTR_DEFN_ID
INNER JOIN KRIM_rsp_TMPL_T KRIM_rsp_TMPL_T
ON KRIM_rsp_T.rsp_TMPL_ID = KRIM_rsp_TMPL_T.rsp_TMPL_ID
INNER JOIN KRIM_TYP_T KRIM_TYP_T
ON KRIM_rsp_TMPL_T.KIM_TYP_ID = KRIM_TYP_T.KIM_TYP_ID
ORDER BY rsp_TEMPLATE_NAME, rsp_NAME, attribute_name
/


# -----------------------------------------------------------------------
# KRIM_RSP_ROLE_ACTN_V
# -----------------------------------------------------------------------
drop view if exists KRIM_RSP_ROLE_ACTN_V
/
CREATE VIEW KRIM_RSP_ROLE_ACTN_V AS 
select
rsp.nmspc_cd as rsp_nmspc_cd
, rsp.rsp_id
, r.NMSPC_CD
, r.ROLE_NM
, rr.ROLE_ID
, rm.MBR_ID
, rm.MBR_TYP_CD
, rm.ROLE_MBR_ID
, actn.ACTN_TYP_CD
, actn.ACTN_PLCY_CD
, actn.FRC_ACTN
, actn.PRIORITY_NBR
from krim_rsp_t rsp
left join krim_rsp_tmpl_t rspt
on rsp.rsp_tmpl_id = rspt.rsp_tmpl_id
left outer join krim_role_rsp_t rr
on rr.rsp_id = rsp.rsp_id
left outer join KRIM_ROLE_MBR_T rm
ON rm.ROLE_ID = rr.ROLE_ID
left outer join KRIM_ROLE_RSP_ACTN_T actn
ON actn.ROLE_RSP_ID = rr.ROLE_RSP_ID
AND (actn.ROLE_MBR_ID = rm.ROLE_MBR_ID OR actn.ROLE_MBR_ID = '*')
left outer join krim_role_t r
on rr.role_id = r.role_id
order by rsp_nmspc_cd
, rsp_id
, role_id
, role_mbr_id
/


# -----------------------------------------------------------------------
# KRIM_RSP_ROLE_V
# -----------------------------------------------------------------------
drop view if exists KRIM_RSP_ROLE_V
/
CREATE VIEW KRIM_RSP_ROLE_V AS 
select
rspt.nmspc_cd as rsp_tmpl_nmspc_cd
, rspt.nm as rsp_tmpl_nm
, rsp.nmspc_cd as rsp_nmspc_cd
, rsp.nm as rsp_nm
, rsp.rsp_id
, a.nm as attr_nm
, d.attr_val
, r.NMSPC_CD
, r.ROLE_NM
, rr.ROLE_ID
from krim_rsp_t rsp
left join krim_rsp_tmpl_t rspt
on rsp.rsp_tmpl_id = rspt.rsp_tmpl_id
left outer join krim_rsp_attr_data_t d
on rsp.rsp_id = d.rsp_id
left outer join krim_attr_defn_t a
on d.kim_attr_defn_id = a.kim_attr_defn_id
left outer join krim_role_rsp_t rr
on rr.rsp_id = rsp.rsp_id
left outer join krim_role_t r
on rr.role_id = r.role_id
order by rsp_tmpl_nmspc_cd, rsp_tmpl_nm, rsp_nmspc_cd, rsp_nm, rsp_id, attr_nm, attr_val
/


# -----------------------------------------------------------------------
# KREN_CHNL_S
# -----------------------------------------------------------------------
drop table if exists KREN_CHNL_S
/

CREATE TABLE KREN_CHNL_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_CHNL_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_CHNL_SUBSCRP_S
# -----------------------------------------------------------------------
drop table if exists KREN_CHNL_SUBSCRP_S
/

CREATE TABLE KREN_CHNL_SUBSCRP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_CHNL_SUBSCRP_S auto_increment = 1020
/

# -----------------------------------------------------------------------
# KREN_CNTNT_TYP_S
# -----------------------------------------------------------------------
drop table if exists KREN_CNTNT_TYP_S
/

CREATE TABLE KREN_CNTNT_TYP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_CNTNT_TYP_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_MSG_DELIV_S
# -----------------------------------------------------------------------
drop table if exists KREN_MSG_DELIV_S
/

CREATE TABLE KREN_MSG_DELIV_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_MSG_DELIV_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_MSG_S
# -----------------------------------------------------------------------
drop table if exists KREN_MSG_S
/

CREATE TABLE KREN_MSG_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_MSG_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_NTFCTN_MSG_DELIV_S
# -----------------------------------------------------------------------
drop table if exists KREN_NTFCTN_MSG_DELIV_S
/

CREATE TABLE KREN_NTFCTN_MSG_DELIV_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_NTFCTN_MSG_DELIV_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_NTFCTN_S
# -----------------------------------------------------------------------
drop table if exists KREN_NTFCTN_S
/

CREATE TABLE KREN_NTFCTN_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_NTFCTN_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_PRIO_S
# -----------------------------------------------------------------------
drop table if exists KREN_PRIO_S
/

CREATE TABLE KREN_PRIO_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_PRIO_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_PRODCR_S
# -----------------------------------------------------------------------
drop table if exists KREN_PRODCR_S
/

CREATE TABLE KREN_PRODCR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_PRODCR_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_RECIP_DELIV_S
# -----------------------------------------------------------------------
drop table if exists KREN_RECIP_DELIV_S
/

CREATE TABLE KREN_RECIP_DELIV_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_RECIP_DELIV_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_RECIP_LIST_S
# -----------------------------------------------------------------------
drop table if exists KREN_RECIP_LIST_S
/

CREATE TABLE KREN_RECIP_LIST_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_RECIP_LIST_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_RECIP_PREF_S
# -----------------------------------------------------------------------
drop table if exists KREN_RECIP_PREF_S
/

CREATE TABLE KREN_RECIP_PREF_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_RECIP_PREF_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_RECIP_S
# -----------------------------------------------------------------------
drop table if exists KREN_RECIP_S
/

CREATE TABLE KREN_RECIP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_RECIP_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_RVWER_S
# -----------------------------------------------------------------------
drop table if exists KREN_RVWER_S
/

CREATE TABLE KREN_RVWER_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_RVWER_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREN_SNDR_S
# -----------------------------------------------------------------------
drop table if exists KREN_SNDR_S
/

CREATE TABLE KREN_SNDR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREN_SNDR_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KREW_ACTN_ITM_S
# -----------------------------------------------------------------------
drop table if exists KREW_ACTN_ITM_S
/

CREATE TABLE KREW_ACTN_ITM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_ACTN_ITM_S auto_increment = 10226
/

# -----------------------------------------------------------------------
# KREW_ACTN_LIST_OPTN_S
# -----------------------------------------------------------------------
drop table if exists KREW_ACTN_LIST_OPTN_S
/

CREATE TABLE KREW_ACTN_LIST_OPTN_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_ACTN_LIST_OPTN_S auto_increment = 1269
/

# -----------------------------------------------------------------------
# KREW_ACTN_RQST_S
# -----------------------------------------------------------------------
drop table if exists KREW_ACTN_RQST_S
/

CREATE TABLE KREW_ACTN_RQST_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_ACTN_RQST_S auto_increment = 2369
/

# -----------------------------------------------------------------------
# KREW_ACTN_TKN_S
# -----------------------------------------------------------------------
drop table if exists KREW_ACTN_TKN_S
/

CREATE TABLE KREW_ACTN_TKN_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_ACTN_TKN_S auto_increment = 2329
/

# -----------------------------------------------------------------------
# KREW_ATTR_DEFN_S
# -----------------------------------------------------------------------
drop table if exists KREW_ATTR_DEFN_S
/

CREATE TABLE KREW_ATTR_DEFN_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_ATTR_DEFN_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KREW_DOC_HDR_S
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_HDR_S
/

CREATE TABLE KREW_DOC_HDR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_DOC_HDR_S auto_increment = 3010
/

# -----------------------------------------------------------------------
# KREW_DOC_LNK_S
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_LNK_S
/

CREATE TABLE KREW_DOC_LNK_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_DOC_LNK_S auto_increment = 2000
/

# -----------------------------------------------------------------------
# KREW_DOC_NTE_S
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_NTE_S
/

CREATE TABLE KREW_DOC_NTE_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_DOC_NTE_S auto_increment = 2020
/

# -----------------------------------------------------------------------
# KREW_DOC_TYP_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KREW_DOC_TYP_ATTR_S
/

CREATE TABLE KREW_DOC_TYP_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_DOC_TYP_ATTR_S auto_increment = 2010
/

# -----------------------------------------------------------------------
# KREW_EDL_FLD_DMP_S
# -----------------------------------------------------------------------
drop table if exists KREW_EDL_FLD_DMP_S
/

CREATE TABLE KREW_EDL_FLD_DMP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_EDL_FLD_DMP_S auto_increment = 5000
/

# -----------------------------------------------------------------------
# KREW_EDL_S
# -----------------------------------------------------------------------
drop table if exists KREW_EDL_S
/

CREATE TABLE KREW_EDL_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_EDL_S auto_increment = 2022
/

# -----------------------------------------------------------------------
# KREW_OUT_BOX_ITM_S
# -----------------------------------------------------------------------
drop table if exists KREW_OUT_BOX_ITM_S
/

CREATE TABLE KREW_OUT_BOX_ITM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_OUT_BOX_ITM_S auto_increment = 10043
/

# -----------------------------------------------------------------------
# KREW_PPL_FLW_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KREW_PPL_FLW_ATTR_S
/

CREATE TABLE KREW_PPL_FLW_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_PPL_FLW_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KREW_PPL_FLW_DLGT_S
# -----------------------------------------------------------------------
drop table if exists KREW_PPL_FLW_DLGT_S
/

CREATE TABLE KREW_PPL_FLW_DLGT_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_PPL_FLW_DLGT_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KREW_PPL_FLW_MBR_S
# -----------------------------------------------------------------------
drop table if exists KREW_PPL_FLW_MBR_S
/

CREATE TABLE KREW_PPL_FLW_MBR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_PPL_FLW_MBR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KREW_PPL_FLW_S
# -----------------------------------------------------------------------
drop table if exists KREW_PPL_FLW_S
/

CREATE TABLE KREW_PPL_FLW_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_PPL_FLW_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KREW_RSP_S
# -----------------------------------------------------------------------
drop table if exists KREW_RSP_S
/

CREATE TABLE KREW_RSP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_RSP_S auto_increment = 2065
/

# -----------------------------------------------------------------------
# KREW_RTE_NODE_CFG_PARM_S
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_NODE_CFG_PARM_S
/

CREATE TABLE KREW_RTE_NODE_CFG_PARM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_RTE_NODE_CFG_PARM_S auto_increment = 2485
/

# -----------------------------------------------------------------------
# KREW_RTE_NODE_S
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_NODE_S
/

CREATE TABLE KREW_RTE_NODE_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_RTE_NODE_S auto_increment = 2923
/

# -----------------------------------------------------------------------
# KREW_RTE_TMPL_S
# -----------------------------------------------------------------------
drop table if exists KREW_RTE_TMPL_S
/

CREATE TABLE KREW_RTE_TMPL_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_RTE_TMPL_S auto_increment = 1645
/

# -----------------------------------------------------------------------
# KREW_RULE_EXPR_S
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_EXPR_S
/

CREATE TABLE KREW_RULE_EXPR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_RULE_EXPR_S auto_increment = 2002
/

# -----------------------------------------------------------------------
# KREW_RULE_TMPL_OPTN_S
# -----------------------------------------------------------------------
drop table if exists KREW_RULE_TMPL_OPTN_S
/

CREATE TABLE KREW_RULE_TMPL_OPTN_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_RULE_TMPL_OPTN_S auto_increment = 2020
/

# -----------------------------------------------------------------------
# KREW_SRCH_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KREW_SRCH_ATTR_S
/

CREATE TABLE KREW_SRCH_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_SRCH_ATTR_S auto_increment = 2060
/

# -----------------------------------------------------------------------
# KREW_TYP_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KREW_TYP_ATTR_S
/

CREATE TABLE KREW_TYP_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_TYP_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KREW_TYP_S
# -----------------------------------------------------------------------
drop table if exists KREW_TYP_S
/

CREATE TABLE KREW_TYP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_TYP_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KREW_USR_S
# -----------------------------------------------------------------------
drop table if exists KREW_USR_S
/

CREATE TABLE KREW_USR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KREW_USR_S auto_increment = 100000000000
/

# -----------------------------------------------------------------------
# KRIM_ATTR_DATA_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ATTR_DATA_ID_S
/

CREATE TABLE KRIM_ATTR_DATA_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ATTR_DATA_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ATTR_DEFN_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ATTR_DEFN_ID_S
/

CREATE TABLE KRIM_ATTR_DEFN_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ATTR_DEFN_ID_S auto_increment = 10003
/

# -----------------------------------------------------------------------
# KRIM_DLGN_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_DLGN_ID_S
/

CREATE TABLE KRIM_DLGN_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_DLGN_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_DLGN_MBR_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_DLGN_MBR_ID_S
/

CREATE TABLE KRIM_DLGN_MBR_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_DLGN_MBR_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_ADDR_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_ADDR_ID_S
/

CREATE TABLE KRIM_ENTITY_ADDR_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_ADDR_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_AFLTN_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_AFLTN_ID_S
/

CREATE TABLE KRIM_ENTITY_AFLTN_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_AFLTN_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_CTZNSHP_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_CTZNSHP_ID_S
/

CREATE TABLE KRIM_ENTITY_CTZNSHP_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_CTZNSHP_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_EMAIL_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_EMAIL_ID_S
/

CREATE TABLE KRIM_ENTITY_EMAIL_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_EMAIL_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_EMP_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_EMP_ID_S
/

CREATE TABLE KRIM_ENTITY_EMP_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_EMP_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_ETHNIC_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_ETHNIC_ID_S
/

CREATE TABLE KRIM_ENTITY_ETHNIC_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_ETHNIC_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_EXT_ID_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_EXT_ID_ID_S
/

CREATE TABLE KRIM_ENTITY_EXT_ID_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_EXT_ID_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_ID_S
/

CREATE TABLE KRIM_ENTITY_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_NM_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_NM_ID_S
/

CREATE TABLE KRIM_ENTITY_NM_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_NM_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_PHONE_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_PHONE_ID_S
/

CREATE TABLE KRIM_ENTITY_PHONE_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_PHONE_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_RESIDENCY_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_RESIDENCY_ID_S
/

CREATE TABLE KRIM_ENTITY_RESIDENCY_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_RESIDENCY_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ENTITY_VISA_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ENTITY_VISA_ID_S
/

CREATE TABLE KRIM_ENTITY_VISA_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ENTITY_VISA_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_GRP_ATTR_DATA_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_GRP_ATTR_DATA_ID_S
/

CREATE TABLE KRIM_GRP_ATTR_DATA_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_GRP_ATTR_DATA_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_GRP_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_GRP_ID_S
/

CREATE TABLE KRIM_GRP_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_GRP_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_GRP_MBR_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_GRP_MBR_ID_S
/

CREATE TABLE KRIM_GRP_MBR_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_GRP_MBR_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_PERM_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_PERM_ID_S
/

CREATE TABLE KRIM_PERM_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_PERM_ID_S auto_increment = 10003
/

# -----------------------------------------------------------------------
# KRIM_PERM_RQRD_ATTR_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_PERM_RQRD_ATTR_ID_S
/

CREATE TABLE KRIM_PERM_RQRD_ATTR_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_PERM_RQRD_ATTR_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_PERM_TMPL_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_PERM_TMPL_ID_S
/

CREATE TABLE KRIM_PERM_TMPL_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_PERM_TMPL_ID_S auto_increment = 10002
/

# -----------------------------------------------------------------------
# KRIM_PRNCPL_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_PRNCPL_ID_S
/

CREATE TABLE KRIM_PRNCPL_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_PRNCPL_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ROLE_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_ID_S
/

CREATE TABLE KRIM_ROLE_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ROLE_ID_S auto_increment = 10003
/

# -----------------------------------------------------------------------
# KRIM_ROLE_MBR_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_MBR_ID_S
/

CREATE TABLE KRIM_ROLE_MBR_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ROLE_MBR_ID_S auto_increment = 10003
/

# -----------------------------------------------------------------------
# KRIM_ROLE_PERM_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_PERM_ID_S
/

CREATE TABLE KRIM_ROLE_PERM_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ROLE_PERM_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ROLE_RSP_ACTN_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_RSP_ACTN_ID_S
/

CREATE TABLE KRIM_ROLE_RSP_ACTN_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ROLE_RSP_ACTN_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_ROLE_RSP_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_ROLE_RSP_ID_S
/

CREATE TABLE KRIM_ROLE_RSP_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_ROLE_RSP_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_RSP_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_RSP_ID_S
/

CREATE TABLE KRIM_RSP_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_RSP_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_RSP_RQRD_ATTR_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_RSP_RQRD_ATTR_ID_S
/

CREATE TABLE KRIM_RSP_RQRD_ATTR_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_RSP_RQRD_ATTR_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_RSP_TMPL_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_RSP_TMPL_ID_S
/

CREATE TABLE KRIM_RSP_TMPL_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_RSP_TMPL_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_TYP_ATTR_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_TYP_ATTR_ID_S
/

CREATE TABLE KRIM_TYP_ATTR_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_TYP_ATTR_ID_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRIM_TYP_ID_S
# -----------------------------------------------------------------------
drop table if exists KRIM_TYP_ID_S
/

CREATE TABLE KRIM_TYP_ID_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRIM_TYP_ID_S auto_increment = 10002
/

# -----------------------------------------------------------------------
# KRMS_ACTN_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KRMS_ACTN_ATTR_S
/

CREATE TABLE KRMS_ACTN_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_ACTN_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_ACTN_S
# -----------------------------------------------------------------------
drop table if exists KRMS_ACTN_S
/

CREATE TABLE KRMS_ACTN_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_ACTN_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_AGENDA_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KRMS_AGENDA_ATTR_S
/

CREATE TABLE KRMS_AGENDA_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_AGENDA_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_AGENDA_ITM_S
# -----------------------------------------------------------------------
drop table if exists KRMS_AGENDA_ITM_S
/

CREATE TABLE KRMS_AGENDA_ITM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_AGENDA_ITM_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_AGENDA_S
# -----------------------------------------------------------------------
drop table if exists KRMS_AGENDA_S
/

CREATE TABLE KRMS_AGENDA_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_AGENDA_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_ATTR_DEFN_S
# -----------------------------------------------------------------------
drop table if exists KRMS_ATTR_DEFN_S
/

CREATE TABLE KRMS_ATTR_DEFN_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_ATTR_DEFN_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_CMPND_PROP_PROPS_S
# -----------------------------------------------------------------------
drop table if exists KRMS_CMPND_PROP_PROPS_S
/

CREATE TABLE KRMS_CMPND_PROP_PROPS_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_CMPND_PROP_PROPS_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_CNTXT_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_ATTR_S
/

CREATE TABLE KRMS_CNTXT_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_CNTXT_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_CNTXT_S
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_S
/

CREATE TABLE KRMS_CNTXT_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_CNTXT_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_ACTN_TYP_S
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_ACTN_TYP_S
/

CREATE TABLE KRMS_CNTXT_VLD_ACTN_TYP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_CNTXT_VLD_ACTN_TYP_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_AGENDA_TYP_S
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_AGENDA_TYP_S
/

CREATE TABLE KRMS_CNTXT_VLD_AGENDA_TYP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_CNTXT_VLD_AGENDA_TYP_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_FUNC_S
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_FUNC_S
/

CREATE TABLE KRMS_CNTXT_VLD_FUNC_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_CNTXT_VLD_FUNC_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_RULE_TYP_S
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_RULE_TYP_S
/

CREATE TABLE KRMS_CNTXT_VLD_RULE_TYP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_CNTXT_VLD_RULE_TYP_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_CNTXT_VLD_TERM_SPEC_S
# -----------------------------------------------------------------------
drop table if exists KRMS_CNTXT_VLD_TERM_SPEC_S
/

CREATE TABLE KRMS_CNTXT_VLD_TERM_SPEC_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_CNTXT_VLD_TERM_SPEC_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_CTGRY_S
# -----------------------------------------------------------------------
drop table if exists KRMS_CTGRY_S
/

CREATE TABLE KRMS_CTGRY_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_CTGRY_S auto_increment = 1
/

# -----------------------------------------------------------------------
# KRMS_FUNC_PARM_S
# -----------------------------------------------------------------------
drop table if exists KRMS_FUNC_PARM_S
/

CREATE TABLE KRMS_FUNC_PARM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_FUNC_PARM_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_FUNC_S
# -----------------------------------------------------------------------
drop table if exists KRMS_FUNC_S
/

CREATE TABLE KRMS_FUNC_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_FUNC_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_NL_TMPL_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KRMS_NL_TMPL_ATTR_S
/

CREATE TABLE KRMS_NL_TMPL_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_NL_TMPL_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_NL_TMPL_S
# -----------------------------------------------------------------------
drop table if exists KRMS_NL_TMPL_S
/

CREATE TABLE KRMS_NL_TMPL_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_NL_TMPL_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_NL_USAGE_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KRMS_NL_USAGE_ATTR_S
/

CREATE TABLE KRMS_NL_USAGE_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_NL_USAGE_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_NL_USAGE_S
# -----------------------------------------------------------------------
drop table if exists KRMS_NL_USAGE_S
/

CREATE TABLE KRMS_NL_USAGE_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_NL_USAGE_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_PROP_PARM_S
# -----------------------------------------------------------------------
drop table if exists KRMS_PROP_PARM_S
/

CREATE TABLE KRMS_PROP_PARM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_PROP_PARM_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_PROP_S
# -----------------------------------------------------------------------
drop table if exists KRMS_PROP_S
/

CREATE TABLE KRMS_PROP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_PROP_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_REF_OBJ_KRMS_OBJ_S
# -----------------------------------------------------------------------
drop table if exists KRMS_REF_OBJ_KRMS_OBJ_S
/

CREATE TABLE KRMS_REF_OBJ_KRMS_OBJ_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_REF_OBJ_KRMS_OBJ_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_RULE_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KRMS_RULE_ATTR_S
/

CREATE TABLE KRMS_RULE_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_RULE_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_RULE_S
# -----------------------------------------------------------------------
drop table if exists KRMS_RULE_S
/

CREATE TABLE KRMS_RULE_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_RULE_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TERM_PARM_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_PARM_S
/

CREATE TABLE KRMS_TERM_PARM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TERM_PARM_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TERM_RSLVR_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_RSLVR_ATTR_S
/

CREATE TABLE KRMS_TERM_RSLVR_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TERM_RSLVR_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TERM_RSLVR_INPUT_SPEC_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_RSLVR_INPUT_SPEC_S
/

CREATE TABLE KRMS_TERM_RSLVR_INPUT_SPEC_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TERM_RSLVR_INPUT_SPEC_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TERM_RSLVR_PARM_SPEC_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_RSLVR_PARM_SPEC_S
/

CREATE TABLE KRMS_TERM_RSLVR_PARM_SPEC_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TERM_RSLVR_PARM_SPEC_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TERM_RSLVR_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_RSLVR_S
/

CREATE TABLE KRMS_TERM_RSLVR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TERM_RSLVR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TERM_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_S
/

CREATE TABLE KRMS_TERM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TERM_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TERM_SPEC_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TERM_SPEC_S
/

CREATE TABLE KRMS_TERM_SPEC_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TERM_SPEC_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TYP_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TYP_ATTR_S
/

CREATE TABLE KRMS_TYP_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TYP_ATTR_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TYP_RELN_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TYP_RELN_S
/

CREATE TABLE KRMS_TYP_RELN_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TYP_RELN_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRMS_TYP_S
# -----------------------------------------------------------------------
drop table if exists KRMS_TYP_S
/

CREATE TABLE KRMS_TYP_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRMS_TYP_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRNS_DOC_TYP_ATTR_S
# -----------------------------------------------------------------------
drop table if exists KRNS_DOC_TYP_ATTR_S
/

CREATE TABLE KRNS_DOC_TYP_ATTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRNS_DOC_TYP_ATTR_S auto_increment = 1000
/

# -----------------------------------------------------------------------
# KRNS_LOCK_S
# -----------------------------------------------------------------------
drop table if exists KRNS_LOCK_S
/

CREATE TABLE KRNS_LOCK_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRNS_LOCK_S auto_increment = 2000
/

# -----------------------------------------------------------------------
# KRNS_LOOKUP_RSLT_S
# -----------------------------------------------------------------------
drop table if exists KRNS_LOOKUP_RSLT_S
/

CREATE TABLE KRNS_LOOKUP_RSLT_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRNS_LOOKUP_RSLT_S auto_increment = 2000
/

# -----------------------------------------------------------------------
# KRNS_MAINT_DOC_ATT_S
# -----------------------------------------------------------------------
drop table if exists KRNS_MAINT_DOC_ATT_S
/

CREATE TABLE KRNS_MAINT_DOC_ATT_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRNS_MAINT_DOC_ATT_S auto_increment = 10000
/

# -----------------------------------------------------------------------
# KRNS_MAINT_LOCK_S
# -----------------------------------------------------------------------
drop table if exists KRNS_MAINT_LOCK_S
/

CREATE TABLE KRNS_MAINT_LOCK_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRNS_MAINT_LOCK_S auto_increment = 2020
/

# -----------------------------------------------------------------------
# KRNS_NTE_S
# -----------------------------------------------------------------------
drop table if exists KRNS_NTE_S
/

CREATE TABLE KRNS_NTE_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRNS_NTE_S auto_increment = 2020
/

# -----------------------------------------------------------------------
# KRSB_BAM_PARM_S
# -----------------------------------------------------------------------
drop table if exists KRSB_BAM_PARM_S
/

CREATE TABLE KRSB_BAM_PARM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRSB_BAM_PARM_S auto_increment = 2000
/

# -----------------------------------------------------------------------
# KRSB_BAM_S
# -----------------------------------------------------------------------
drop table if exists KRSB_BAM_S
/

CREATE TABLE KRSB_BAM_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRSB_BAM_S auto_increment = 2000
/

# -----------------------------------------------------------------------
# KRSB_MSG_QUE_S
# -----------------------------------------------------------------------
drop table if exists KRSB_MSG_QUE_S
/

CREATE TABLE KRSB_MSG_QUE_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRSB_MSG_QUE_S auto_increment = 467
/

# -----------------------------------------------------------------------
# KRSB_SVC_DEF_S
# -----------------------------------------------------------------------
drop table if exists KRSB_SVC_DEF_S
/

CREATE TABLE KRSB_SVC_DEF_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRSB_SVC_DEF_S auto_increment = 10105
/

# -----------------------------------------------------------------------
# KRSB_SVC_DSCRPTR_S
# -----------------------------------------------------------------------
drop table if exists KRSB_SVC_DSCRPTR_S
/

CREATE TABLE KRSB_SVC_DSCRPTR_S
(
	id bigint(19) not null auto_increment, primary key (id) 
) ENGINE MyISAM
/
ALTER TABLE KRSB_SVC_DSCRPTR_S auto_increment = 10105
/
