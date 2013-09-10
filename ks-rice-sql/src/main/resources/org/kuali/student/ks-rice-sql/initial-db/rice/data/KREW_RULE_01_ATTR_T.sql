-- Original RULE_ATTR_ID: 1645
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'SearchableXmlAttribute', 'CluCreditCourse.OrgSearchAttribute', 'org.kuali.student.lum.workflow.search.OrgSearchAttribute', 'CluCreditCourse Searchable attribute', 'CluCreditCourse Searchable attribute', 'E186958C-5553-87E3-E46D-67F5EC3AB225', 1, '<searchingConfig>
                <fieldDef name="id" title="Organization">
                    <display>
                        <type>select</type>
                           <values>kuali_values_finder_class(org.kuali.rice.student.lookup.keyvalues.OrgCocValuesFinder)</values>
                    </display>
                    <fieldEvaluation>
                        <xpathexpression>wf:xstreamsafe(''//cluProposalDocInfo/orgId'')</xpathexpression>
                    </fieldEvaluation>
                </fieldDef>
            </searchingConfig>')
/
