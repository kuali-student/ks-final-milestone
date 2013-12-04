Delete from KRMS_ACTN_ATTR_T  AT
 where AT.ACTN_ATTR_DATA_ID in
       (select AA.ACTN_ATTR_DATA_ID
          from KRMS_ACTN_T AC, KRMS_ACTN_ATTR_T AA
         where AA.ATTR_DEFN_ID IN
               ('KS-KRMS-ATTR-DEFN-10003', 'KS-KRMS-ATTR-DEFN-10004')
           and ac.actn_id = aa.actn_id
           and ac.rule_id in
               (select t.rule_id
                  from KRMS_RULE_T t
                 where t.typ_id =
                       (SELECT TYP_ID
                          FROM KRMS_TYP_T
                         WHERE NM = 'kuali.krms.rule.type.final.exam.standard')))
                 
/
