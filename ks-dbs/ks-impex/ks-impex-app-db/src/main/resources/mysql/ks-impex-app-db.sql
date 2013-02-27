
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

