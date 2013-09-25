SELECT
    foreign_table,
    foreign_column,
    local_table,
    local_column,
    cols.owner,
    cnstrt.constraint_name
FROM (
    SELECT
        foreign_table,
        -- set the primary key column based on the table
        DECODE(
          foreign_table,
          'KSLU_MEMSHIP_KSLU_SPARAM', 'KSLU_MEMSHIP_ID',
          'KSPR_PROPOSAL','PROPOSAL_ID',
          'KSDO_DOCUMENT','DOC_ID',
          'KSDO_DOCUMENT_CATEGORY', 'CATEGORY_ID',
          -- our side col name is CMP_ID and the rice name is not conformant
          'KRLC_CMP_T', 'CAMPUS_CD',
          -- these are all by design I think?
          'KSEM_ENUM_T','ENUM_KEY',
          'KSEN_TYPE', 'TYPE_KEY',
          'KSLU_CLU_LO_RELTN_TYPE', 'TYPE_KEY',
          'KSLU_CLU_PUB_TYPE', 'TYPE_KEY',
          'KSLU_CLU_RSLT_TYP', 'TYPE_KEY',
          'KSLU_LUTYPE', 'TYPE_KEY',
          'KSLU_RSLT_USG_TYPE', 'TYPE_KEY',
          'KSOR_ORG_PERS_RELTN_TYPE', 'TYPE_KEY',
          'KSOR_ORG_TYPE', 'TYPE_KEY',
          'KSST_OBJECT_SUB_TYPE', 'TYPE_KEY',
          'KSST_OBJECT_TYPE', 'TYPE_KEY',
          'KSST_REF_STMT_REL_TYPE', 'TYPE_KEY',
          'KSST_REQ_COM_TYPE', 'TYPE_KEY',
          'KSST_STMT_TYPE', 'TYPE_KEY',
          DECODE(
            SUBSTR(foreign_table, 0, 2),
            'KR',SUBSTR(SUBSTR(foreign_table, 6), 1, LENGTH(foreign_table)-7) || REGEXP_REPLACE(local_column, '.*(_ID|_CD)$','\1'),
            DECODE(
              SUBSTR(local_column,LENGTH(local_column)-9),
              'VER_IND_ID', 'VER_IND_ID',
              -- KS default PK field
              'ID'
            )
          )
        ) as foreign_column,
        local_table,
        local_column
    FROM (
        SELECT
            column_name as local_column,
            -- these are exceptions to standards except where noted
            DECODE(
                column_name,
                -- standard table prefix patter
                'RT_DESCR_ID', CONCAT(SUBSTR(c.table_name, 0, 4),'_RICH_TEXT_T'),
                'ACTIVITY_OFFERING_ID', 'KSEN_LUI',
                -- foreign table prefix (e.g. KSEN)
                'AGENDA_ID', 'KRMS_AGENDA_T',
                'AOC_SET_ID', 'KSEN_CO_AO_CLUSTER_SET',
                'APPLD_POPULATION_ID', 'KSEN_POPULATION',
                'ASSIGNED_POPULATION_ID', 'KSEN_POPULATION',
                'CAMPUS_ID', 'KRLC_CMP_T',
                'CATEGORY_ID', 'KSDO_DOCUMENT_CATEGORY',
                'DOC_ID', 'KSDO_DOCUMENT',
                'CTX_ID', 'KSEM_CTX_T',
                'ENUM_VAL_ID', 'KSEM_ENUM_VAL_T',
                'BUILDING_ID', 'KSEN_ROOM_BUILDING',
                'CHECK_ID', 'KSEN_PROCESS_CHECK',
                'CHILD_POPULATION_ID', 'KSEN_POPULATION',
                'CHILD_PROCESS_ID', 'KSEN_PROCESS',
                'CLUCLU_RELTN_ID', 'KSLU_CLUCLU_RELTN',
                -- foreign table prefix (e.g. KSEN)
                'CLU_ID', 'KSLU_CLU',
                'CMP_ID', 'KRLC_CMP_T',
                'EXISTING_LUI_ID', 'KSEN_LUI',
                'FORMAT_OFFERING_ID', 'KSEN_LUI',
                'FROM_STATE_ID', 'KSEN_STATE',
                'GROUP_ID', 'KRIM_GRP_T',
                'ISSUE_ID', 'KSEN_HOLD_ISSUE',
                'LEFT_AGENDA_ID', 'KRMS_AGENDA_T',
                'NEW_LUI_ID', 'KSEN_LUI',
                'LTI_RESULTING_LPR_ID', 'KSEN_LPR',
                'ORGID', 'KSOR_ORG',
                -- foreign table prefix (e.g. KSLU)
                'ORG_ID', 'KSOR_ORG',
                'OWNER_ORG_ID', 'KSOR_ORG',
                -- standard behavior (kinda)
                'OWNER_ID',
                -- enum_table has _T
                    DECODE(c.table_name,
                           'KSEM_ENUM_ATTR', 'KSEM_ENUM_T',
                           REPLACE(c.table_name, '_ATTR', '')
                    ),
                'ACCESS_TYPE_ID', 'KSEN_ROOM_ACCESS_TYPE',
                'AO_CLUSTER_ID', 'KSEN_CO_AO_CLUSTER',
                'ADMIN_ORG_ID', 'KSOR_ORG',
                'OWNER_TYPE_ID', 'KSEN_TYPE',
                'PERSON_ID', 'KRIM_PRNCPL_T',
                'PERS_ID', 'KRIM_PRNCPL_T',
                'PRD_MSTONE_ID', 'KSEN_MSTONE',
                'REF_POPULATION_ID', 'KSEN_POPULATION',
                'RELATIVE_ANCHOR_MSTONE_ID', 'KSEN_MSTONE',
                'REL_OBJ_STATE_ID', 'KSEN_STATE',
                'REQUESTING_PERS_ID', 'KRIM_PRNCPL_T',
                'RESULT_SCALE_ID', 'KSEN_LRC_RESULT_SCALE',
                'RESULT_VALUE_ID', 'KSEN_LRC_RESULT_VALUE',
                'RESULT_VAL_GRP_ID', 'KSEN_LRC_RVG',
                'RES_SRC_ID', 'KSEN_LRR_RES_SOURCE',
                'RES_TRANS_ID', 'KSEN_LPR_TRANS',
                'RIGHT_AGENDA_ID', 'KRMS_AGENDA_T',
                'ROR_ID', 'KSEN_SOC_ROR',
                'RVG_ID', 'KSEN_LRC_RVG',
                'SCHEDULE_ID', 'KSEN_SCHED',
                'SLOT_ID', 'KSEN_APPT_SLOT',
                'SOURCE_CO_ID', 'KSEN_LUI',
                'SOURCE_SOC_ID', 'KSEN_SOC',
                'TARGET_CO_ID', 'KSEN_LUI',
                'TARGET_SOC_ID', 'KSEN_SOC',
                'TARGET_STATE_CHG_ID', 'KSEN_STATE_CHG',
                'TARGET_TERM_ID', 'KSEN_ATP',
                'TERM_ID',
                    DECODE(
                      c.table_name,
                      'KRMS_TERM_PARM_T', 'KRMS_TERM_T',
                      'KRMS_TERM_T', 'KRMS_TERM_T',
                      'KSEN_ATP'
                    ),
                'TM_SLOT_ID', 'KSEN_SCHED_TMSLOT',
                'TO_STATE_ID', 'KSEN_STATE',
                'UNITS_CONTENT_OWNER_ID', 'KSOR_ORG',
                'LOCATEGORY_ID', 'KSLO_LO_CATEGORY',
                'LOLO_RELTN_TYPE_ID', 'KSLO_LO_RELTN_TYPE',
                'LO_LO_RELATION_TYPE_ID', 'KSLO_LO_RELTN_TYPE',
                'LO_REL_TYPE_ID', 'KSLO_LO_RELTN_TYPE',
                'LOTYPE_ID', 'KSLO_LO_TYPE',
                'LO_REPO_ID', 'KSLO_LO_REPOSITORY',
                'LO_ROOT_ID', 'KSLO_LO',
                'ACCT_ID', 'KSLU_CLU_ACCT',
                'AFFIL_ORG_ID', 'KSOR_ORG',
                'ALT_CLU_ID', 'KSLU_CLU',
                'CLUE_FEE_REC_ID', 'KSLU_CLU_FEE_REC',
                'CLULO_RELTN_TYPE_ID', 'KSLU_CLU_LO_RELTN_TYPE',
                'CLU_RES_ID', 'KSLU_CLU_RSLT',
                'CLU_RSLT_TYPE', 'KSLU_CLU_RSLT_TYP',
                'CLU_RSLT_TYPE_ID',  'KSLU_CLU_RSLT_TYP',
                'CLU_RSLT_USAGE_TYPE_ID', 'KSLU_RSLT_USG_TYPE',
                'CLU_SET_CHILD_ID', 'KSLU_CLU_SET',
                'CLU_SET_PARENT_ID', 'KSLU_CLU_SET',
                'CLU_VER_IND_ID', 'KSLU_CLU',
                'FEE_ID', 'KSLU_CLU_FEE',
                'KSLU_MEMSHIP_ID', 'KSLU_MEMSHIP',
                'KSLU_SPARAM_ID', 'KSLU_SPARAM',
                'VER_FROM_ID', 'KSLU_CLU',
                -- foreign table prefix (e.g. KSLU)
                'LO_ID', 'KSLO_LO',
                -- foreign table prefix (e.g. KSLU)
                'LO_TYPE_ID', 'KSLO_LO_TYPE',
                'LU_LU_RELTN_TYPE_ID', 'KSLU_LULU_RELTN_TYPE',
                'LU_RELTN_TYPE_ID', 'KSLU_LULU_RELTN_TYPE',
                'LU_REL_TYPE_ID', 'KSLU_LULU_RELTN_TYPE',
                'LU_TYPE_ID', 'KSLU_LUTYPE',
                'MEM_QUERY_ID', 'KSLU_MEMSHIP',
                'OFFIC_CLU_ID', 'KSLU_CLU_IDENT',
                'PRI_INSTR_ID', 'KSLU_CLU_INSTR',
                'RES_OPT_ID', 'KSLU_RSLT_OPT',
                'RES_USAGE_ID', 'KSLU_RSLT_USG_TYPE',
                -- pretty sure this was intended to have it's own type but will assume this will use the new type service
                'RSRC_TYPE_ID', 'KSEN_TYPE',
                'SEARCHPARAMETERS_ID', 'KSLU_MEMSHIP_KSLU_SPARAM',
                'TYPE_KEY_ID', 'KSLU_CLU_RSLT_TYP',
                'VALUES_ID', 'KSLU_SPVALUE',
                'ORGREF_ID', 'KSOR_ORG',
                'PERSONREF_ID', 'KRIM_PRNCPL_T',
                'WORKFLOW_ID', 'KREW_DOC_HDR_T',
                -- this one's correct but the _cd is being stripped so had to manually do it here
                'SUBJ_CD_ID', 'KSSC_SUBJ_CD',
                'CHLD_STMT_ID', 'KSST_STMT',
                'CHLD_STMT_TYPE_ID', 'KSST_STMT_TYPE',
                'OBJ_SUB_TYPE_ID', 'KSST_OBJECT_SUB_TYPE',
                'REF_STMT_REL_TYPE_ID', 'KSST_REF_STMT_REL_TYPE',
                'REQ_COMP_FIELD_TYPE_ID', 'KSST_REQ_COM_FIELD_TYPE',
                'REQ_COMP_TYPE_ID', 'KSST_REQ_COM_FIELD',
                'OBJ_TYPE_ID', 'KSST_OBJECT_TYPE',
                'SORT_ORDER_TYPE_ID', 'KSEN_POPULATION_RULE_SOT',
                -- default behavior
                -- assume the same table prefix as the source (e.g. 'KSLU_')
                CONCAT(SUBSTR(c.table_name, 0, 5),
                    -- strip related_ from all column name
                    REPLACE(
                      -- strip off _ID and _CD from column name
                      REGEXP_REPLACE(
                        -- detect table prefix
                        DECODE(
                            SUBSTR(c.table_name,0,2),
                            'KS', column_name,
                            'KR',
                                -- rice tables have _T at the end
                                CONCAT(column_name, '_T')
                        ),
                        '(_ID|_CD)(_T)?$', '\2'
                      ),
                    'RELATED_', ''
                    )
                )
            ) as foreign_table,
            c.table_name as local_table
        FROM user_tab_cols c
        LEFT JOIN all_tables t
            ON c.table_name = t.table_name
        WHERE t.table_name IS NOT NULL AND
              ( column_name LIKE ('%_ID') OR  column_name LIKE ('%_CD') ) AND
              column_name NOT IN (
                  -- metadata, alternate ids, or codes that don't relate to other tables
                  'OBJ_ID', 'CREATEID', 'UPDATEID', 'LUI_CD', 'ROOM_CD', 'SUFX_CD', 'SUFX_CD', 'BUILDING_CD', 'VER_IND_ID', 'VER_IND_ID',
                  -- this is actually a cd
                  'MSG_ID',
                  -- not yet designed
                  'ARTICULATE_ID',
                  -- these have flexible sources (e.g. rule ownership)
                  'REF_OBJECT_ID', 'REF_OBJ_ID', 'OBJECT_REFERENCE_ID', 'REFERENCE_ID',
                  -- these should be removed since it appears the referring table is gone?
                  'RES_COMP_ID', 'RSLT_COMP_ID',
                  -- this one comes from ksen_soc_ror_option.  it is a key that is typed in a constants class
                  'OPTION_ID',
                  -- used for tracking db version
                  'BUILD_ID'
              ) AND
              c.table_name != 'PLAN_TABLE'
        )
    WHERE
      foreign_table != local_table AND
      -- multi-field PK
      foreign_table != 'KRCR_CMPNT_T'

    ORDER BY local_table
    )
LEFT JOIN all_tab_columns cols
  ON  cols.table_name = foreign_table AND
    cols.column_name = foreign_column
LEFT JOIN (
    SELECT
        c.constraint_name as constraint_name,
        a.table_name as cnstrt_local_table,
        a.column_name as cnstrt_local_column,
        uc.table_name as cnstrt_foreign_table,
        uc.column_name as cnstrt_foreign_column
    FROM all_cons_columns a
    JOIN all_constraints c
      ON  a.owner = c.owner AND
        a.constraint_name = c.constraint_name
    JOIN all_constraints c_pk
      ON  c.r_owner = c_pk.owner AND
        c.r_constraint_name = c_pk.constraint_name
    JOIN USER_CONS_COLUMNS uc
      ON uc.constraint_name = c.r_constraint_name
    ) cnstrt
  ON  cnstrt_local_table = local_table AND
    cnstrt_local_column = local_column AND
    cnstrt_foreign_table = foreign_table AND
    cnstrt_foreign_column = foreign_column