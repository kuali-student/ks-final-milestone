-- DO NOT COPY AND PASTE THIS COMMENT.  VIOLATORS WILL LOSE COMMIT ACCESS.
-- KEY1:MjAxMy0wOS0yMC1TZXRDb3Vyc2VPZmZlcmluZ3NGaW5hbERyaXZlckF0cmlidXRlcy5zcWw=
-- KEY2:UkVGRVJFTkNF
-- TYPE:REFERENCE

BEGIN
  for LUI_REC in (select lui.id
                    from KSEN_LUI lui, ksen_lui_attr t
                   where lui.clu_id IN
                         (select clu.id
                            from kslu_clu clu
                           where clu.offic_clu_id IN
                                 (select cluI.Id
                                    from kslu_clu_ident cluI
                                   where cluI.cd in ('HIST401',
                                                     'HIST404',
                                                     'HIST405',
                                                     'HIST406',
                                                     'HIST407',
                                                     'HIST408',
                                                     'HIST415',
                                                     'BSCI411',
                                                     'BSCI413',
                                                     'BSCI412',
                                                     'BSCI414',
                                                     'BSCI400',
                                                     'BSCI410',
                                                     'ENGL408',
                                                     'ENGL403',
                                                     'ENGL409',
                                                     'PHYS401',
                                                     'PHYS270',
                                                     'PHYS402',
                                                     'PHYS404',
                                                     'PHYS161',
                                                     'PHYS260',
                                                     'PHYS131',
                                                     'CHEM105',
                                                     'CHEM131',
                                                     'CHEM132',
                                                     'CHEM135',
                                                     'CHEM136',
                                                     'CHEM146',
                                                     'CHEM147',
                                                     'CHEM136',
                                                     'CHEM177',
                                                     'CHEM231',
                                                     'CHEM232',
                                                     'CHEM241',
                                                     'CHEM242',
                                                     'CHEM247',
                                                     'CHEM271',
                                                     'CHEM272',
                                                     'CMSC122',
                                                     'CMSC131',
                                                     'CMSC132',
                                                     'CMSC216',
                                                     'CMSC250',
                                                     'CMSC330',
                                                     'CMSC351',
                                                     'ENGL402',
                                                     'ENGL404',
                                                     'ENGL407',
                                                     'WMST400',
                                                     'BIOM301',
                                                     'BMGT220',
                                                     'BMGT221',
                                                     'BMGT230',
                                                     'EDMS410',
                                                     'EDMS451',
                                                     'ENES102',
                                                     'ENES220',
                                                     'ENES221',
                                                     'ENES232',
                                                     'ENMA300',
                                                     'NFSC100',
                                                     'STAT100',
                                                     'FREN103',
                                                     'FREN203',
                                                     'GERM103',
                                                     'GERM203',
                                                     'ITAL103',
                                                     'ITAL203',
                                                     'JAPN101',
                                                     'JAPN102',
                                                     'RUSS101',
                                                     'RUSS102',
                                                     'RUSS201',
                                                     'RUSS202',
                                                     'MATH003',
                                                     'MATH110',
                                                     'MATH112',
                                                     'MATH113',
                                                     'MATH115',
                                                     'MATH115B',
                                                     'MATH130',
                                                     'MATH131',
                                                     'MATH140',
                                                     'MATH140B',
                                                     'MATH140H',
                                                     'MATH141',
                                                     'MATH141H',
                                                     'MATH212',
                                                     'MATH213',
                                                     'MATH214',
                                                     'MATH220',
                                                     'MATH221',
                                                     'MATH240',
                                                     'MATH241',
                                                     'MATH241H',
                                                     'MATH246',
                                                     'MATH246H')
                                     and cluI.ST = 'Active'))
                     and lui.lui_type = 'kuali.lui.type.course.offering'
                     and t.owner_id = lui.id
                     and t.attr_value = 'STANDARD') loop
  
    INSERT INTO KSEN_LUI_ATTR
      (ATTR_KEY, ATTR_VALUE, ID, OBJ_ID, OWNER_ID)
    VALUES
      ('kuali.attribute.final.exam.driver',
       'kuali.lu.exam.driver.CourseOffering',
       SYS_GUID(),
       SYS_GUID(),
       LUI_REC.ID);
  
    INSERT INTO KSEN_LUI_ATTR
      (ATTR_KEY, ATTR_VALUE, ID, OBJ_ID, OWNER_ID)
    VALUES
      ('kuali.attribute.final.exam.use.matrix',
       'true',
       SYS_GUID(),
       SYS_GUID(),
       LUI_REC.ID);
  end loop;
END;
/
