-- KSLR_RESCOMP_TYPE to KSEN_TYPE table
INSERT INTO KSEN_TYPE 
SELECT type_key, obj_id, nvl(name,' '), type_desc, type_desc, eff_dt, expir_dt, 'http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo', 
    'http://student.kuali.org/wsdl/lrc/LrcService', nvl(ver_nbr,0), to_date('2012/09/17','YYYY/MM/DD'), 'CMLRCUPGRADE', null, null 
FROM KSLR_RESCOMP_TYPE
WHERE type_key NOT IN (SELECT TYPE_KEY FROM KSEN_TYPE)
AND type_key NOT IN ('kuali.resultComponentType.credit.degree.range','kuali.resultComponentType.credit.degree.fixed','kuali.resultComponentType.credit.degree.multiple')
/



-- KSLR_RESCOMP to KSEN_LRC_RVG
INSERT INTO KSEN_LRC_RVG
SELECT RC.ID, RC.OBJ_ID,
(CASE
WHEN RC.TYPE LIKE 'kuali.resultComponentType.degree' THEN 'kuali.result.values.group.type.fixed'
WHEN RC.TYPE LIKE 'kuali.resultComponentType.credit.degree.range' THEN 'kuali.result.values.group.type.range'
WHEN RC.TYPE LIKE 'kuali.resultComponentType.credit.degree.fixed' THEN 'kuali.result.values.group.type.fixed'
WHEN RC.TYPE LIKE 'kuali.resultComponentType.grade.finalGrade' THEN 'kuali.resultComponentType.grade.finalGrade'
WHEN RC.TYPE LIKE 'kuali.resultComponentType.credit.degree.multiple' THEN 'kuali.result.values.group.type.multiple'
END), 
(CASE WHEN (UPPER(RC.STATE) like 'ACTIVE' ) THEN 'kuali.result.values.group.state.approved' ELSE NVL(RC.STATE,'state.null') END),
RC.NAME, NVL(RT.PLAIN,RC.NAME), NVL(RT.FORMATTED,''),
       (CASE
          -- WARNING: Case statement should be modified if source data makes use of additional scales
		      -- WARNING: Do we keep our old type names (eg. type name called resultComponent when concept no longer exists)
		      -- WARNING: Mapping to scales may be incomplete/incorrect
          WHEN RC.ID LIKE 'kuali.creditType.credit.degree%' THEN 'kuali.result.scale.credit.degree'
          WHEN RC.ID LIKE 'kuali.resultComponent.degree%'  THEN 'kuali.result.scale.degree'
          WHEN RC.ID LIKE 'kuali.resultComponent.grade.audit'  THEN 'kuali.result.scale.grade.admin'
          WHEN RC.ID LIKE 'kuali.resultComponent.grade.completedNotation' THEN 'kuali.result.scale.grade.completed'
          WHEN RC.ID LIKE 'kuali.resultComponent.grade.designReview' THEN 'kuali.result.scale.grade.review'
          WHEN RC.ID LIKE 'kuali.resultComponent.grade.letter' THEN 'kuali.result.scale.grade.letter'
          WHEN RC.ID LIKE 'kuali.resultComponent.grade.passFail' THEN 'kuali.result.scale.grade.pf'
          WHEN RC.ID LIKE 'kuali.resultComponent.grade.percentage' THEN 'kuali.result.scale.grade.percentage'
          WHEN RC.ID LIKE 'kuali.resultComponent.grade.recitalReview' THEN 'kuali.result.scale.grade.review'
          WHEN RC.ID LIKE 'kuali.resultComponent.grade.satisfactory' THEN 'kuali.result.scale.grade.pnp'

        END) ,
       MIN_CREDIT_VALUE, MAX_CREDIT_VALUE, (CASE WHEN (CREDIT_INCREMENT is null AND RC.TYPE LIKE 'kuali.resultComponentType.credit.degree.range') THEN '1.0' ELSE null END), NVL(RC.EFF_DT,to_date('0001/01/01','YYYY/MM/DD')), RC.EXPIR_DT, RC.VER_NBR, NVL(RC.CREATETIME,to_date('2012/09/17','YYYY/MM/DD'))
       , NVL(RC.CREATEID, 'CMLRCUPGRADE'), to_date('2012/09/17','YYYY/MM/DD'), 'CMLRCUPGRADE'
FROM 
        KSLR_RESCOMP RC
        
        -- Get result component min/max credits and credit increment
		    -- WARNING: Are these all the result components that need to be converted
        LEFT JOIN (SELECT
           OWNER AS RESCOMP_ID,
           COALESCE (SUM(minCreditValue), SUM(fixedCreditValue)) as MIN_CREDIT_VALUE,
           COALESCE (SUM(maxCreditValue), SUM(fixedCreditValue)) as MAX_CREDIT_VALUE,
           SUM(creditIncrementValue) as CREDIT_INCREMENT
        FROM(
        SELECT attr.owner as owner,
          (CASE attr_name WHEN 'maxCreditValue' THEN attr_value END) AS maxCreditValue,
          (CASE attr_name WHEN 'minCreditValue' THEN attr_value END) AS minCreditValue,
          (CASE attr_name WHEN 'fixedCreditValue' THEN attr_value END) AS fixedCreditValue,
          (CASE attr_name WHEN 'creditIncrementValue' THEN attr_value END) AS creditIncrementValue
        FROM KSLR_RESCOMP_ATTR attr
        )
        GROUP BY owner
        ) RC_CREDITS
        ON RC.ID=RC_CREDITS.RESCOMP_ID
        
        -- Get result component's description
        LEFT JOIN KSLR_RICH_TEXT_T RT
        ON RC.RT_DESCR_ID = RT.ID
        where RC.ID not in (SELECT ID FROM KSEN_LRC_RVG)
/



-- KSLR_RESULT_VALUE to KSEN_LRC_RESULT_VALUE
INSERT INTO KSEN_LRC_RESULT_VALUE
SELECT ID, OBJ_ID, 'kuali.result.value.type.value', 'kuali.result.value.state.approved', RV.VALUE, RV.VALUE, RV.VALUE, 
       (CASE
          -- WARNING: Case statement should be modified if source data makes use of additional scales
		      -- WARNING: Mapping to scales may be incomplete/incorrect
          WHEN RSLT_COMP_ID LIKE 'kuali.creditType.credit.degree%' THEN 'kuali.result.scale.credit.degree'
          WHEN RSLT_COMP_ID LIKE 'kuali.resultComponent.degree%'  THEN 'kuali.result.scale.degree'
          WHEN RSLT_COMP_ID LIKE 'kuali.resultComponent.grade.audit'  THEN 'kuali.result.scale.grade.admin'
          WHEN RSLT_COMP_ID LIKE 'kuali.resultComponent.grade.completedNotation' THEN 'kuali.result.scale.grade.completed'
          WHEN RSLT_COMP_ID LIKE 'kuali.resultComponent.grade.designReview' THEN 'kuali.result.scale.grade.review'
          WHEN RSLT_COMP_ID LIKE 'kuali.resultComponent.grade.letter' THEN 'kuali.result.scale.grade.letter'
          WHEN RSLT_COMP_ID LIKE 'kuali.resultComponent.grade.passFail' THEN 'kuali.result.scale.grade.pf'
          WHEN RSLT_COMP_ID LIKE 'kuali.resultComponent.grade.percentage' THEN 'kuali.result.scale.grade.percentage'
          WHEN RSLT_COMP_ID LIKE 'kuali.resultComponent.grade.recitalReview' THEN 'kuali.result.scale.grade.review'
          WHEN RSLT_COMP_ID LIKE 'kuali.resultComponent.grade.satisfactory' THEN 'kuali.result.scale.grade.pnp'

        END) as RESULT_SCALE_ID, 
       (CASE 
          WHEN RSLT_COMP_ID LIKE 'kuali.creditType.credit.degree%' THEN TO_NUMBER(RV.VALUE)
          ELSE null
          END), 
        RV.VALUE, NULL, NULL, 0, to_date('2012/09/17','YYYY/MM/DD') , 'CMLRCUPGRADE', NULL, NULL
FROM KSLR_RESULT_VALUE RV
WHERE RV.ID NOT IN (SELECT ID FROM KSEN_LRC_RESULT_VALUE)
AND RV.VALUE NOT IN (SELECT NAME FROM KSEN_LRC_RESULT_VALUE)
/

-- KSEN_LRC_RVG_RESULT_VALUE (Join table data)
INSERT INTO KSEN_LRC_RVG_RESULT_VALUE
SELECT RSLT_COMP_ID, ID FROM KSLR_RESULT_VALUE
WHERE ID in (SELECT ID FROM KSEN_LRC_RESULT_VALUE)
/