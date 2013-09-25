-- TODO

TRUNCATE TABLE KREW_RULE_ATTR_T DROP STORAGE
/
-- Original RULE_ATTR_ID: 1000
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'RuleAttribute', 'RuleRoutingAttribute', 'org.kuali.rice.kew.rule.RuleRoutingAttribute', 'Rule Routing Attribute', 'Rule Routing Attribute', '6166CBA1B94F644DE0404F8189D86C09', 3, null)
/
-- Original RULE_ATTR_ID: 1003
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'ActionListAttribute', 'NotificationCustomActionListAttribute', 'org.kuali.rice.ken.kew.NotificationCustomActionListAttribute', 'Notification Action List Attribute', 'Notification  Action List Attribute', '6166CBA1B952644DE0404F8189D86C09', 2, null)
/
-- Original RULE_ATTR_ID: 1004
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'SearchableXmlAttribute', 'NotificationChannelSearchableAttribute', 'org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute', 'The associated channel that this message was sent on.', 'Notification Channel', '6166CBA1B953644DE0404F8189D86C09', 1, '<searchingConfig>
              <fieldDef name="notificationChannel" title="Notification Channel">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification/channel/name)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
-- Original RULE_ATTR_ID: 1005
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'SearchableXmlAttribute', 'NotificationPrioritySearchableAttribute', 'org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute', 'The priority of this notification.', 'Notification Priority', '6166CBA1B954644DE0404F8189D86C09', 1, '<searchingConfig>
              <fieldDef name="notificationPriority" title="Notification Priority">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification/priority/name)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
-- Original RULE_ATTR_ID: 1006
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'SearchableXmlAttribute', 'NotificationContentTypeSearchableAttribute', 'org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute', 'The content type of this notification.', 'Notification Content Type', '6166CBA1B955644DE0404F8189D86C09', 1, '<searchingConfig>
              <fieldDef name="notificationContentType" title="Notification Content Type">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification/contentType/name)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
-- Original RULE_ATTR_ID: 1007
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'SearchableXmlAttribute', 'NotificationProducerSearchableAttribute', 'org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute', 'The producer of this notification.', 'Notification Producer', '6166CBA1B956644DE0404F8189D86C09', 1, '<searchingConfig>
              <fieldDef name="notificationProducer" title="Notification Producer">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification/producer/name)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
-- Original RULE_ATTR_ID: 1008
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'SearchableXmlAttribute', 'NotificationRecipientsSearchableAttribute', 'org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute', 'Those who are receiving this notification.', 'Notification Recipient', '6166CBA1B957644DE0404F8189D86C09', 1, '<searchingConfig>
              <fieldDef name="notificationRecipients" title="Notification Recipients">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification//recipients)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
-- Original RULE_ATTR_ID: 1009
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'SearchableXmlAttribute', 'NotificationSendersSearchableAttribute', 'org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute', 'Those who this notification is being sent on behalf of.', 'Notification Senders', '6166CBA1B958644DE0404F8189D86C09', 1, '<searchingConfig>
              <fieldDef name="notificationSenders" title="Notification Senders">
                  <display>
                      <type>text</type>
                  </display>
                  <validation required="false"/>
                  <fieldEvaluation>
                      <xpathexpression>string(/documentContent/applicationContent/notification//senders)</xpathexpression>
                  </fieldEvaluation>
              </fieldDef>
           </searchingConfig>')
/
-- Original RULE_ATTR_ID: 1010
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'RuleAttribute', 'ChannelReviewerRoleAttribute', 'org.kuali.rice.ken.kew.ChannelReviewerRoleAttribute', 'Channel Reviewer Role Attribute', 'Channel Reviewer Role Attribute', '6166CBA1B959644DE0404F8189D86C09', 1, null)
/
-- Original RULE_ATTR_ID: 1233
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, RULE_ATTR_TYP_CD, NM, CLS_NM, DESC_TXT, LBL, OBJ_ID, VER_NBR, XML)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), 'SearchableXmlAttribute', 'XMLSearchableAttribute', 'org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute', 'XML Searchable attribute', 'XML Searchable attribute', '6166CBA1B9C5644DE0404F8189D86C09', 1, '<searchingConfig>
        <fieldDef name="givenname" title="First name">
          <display>
            <type>text</type>
          </display>
          <searchDefinition autoWildcardLocation="prefixonly"/>
          <validation required="true">
            <regex>^[a-zA-Z ]+$</regex>
            <message>Invalid first name</message>
          </validation>
          <fieldEvaluation>
            <xpathexpression>//givenname/value</xpathexpression>
          </fieldEvaluation>
        </fieldDef>
      </searchingConfig>')
/
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
