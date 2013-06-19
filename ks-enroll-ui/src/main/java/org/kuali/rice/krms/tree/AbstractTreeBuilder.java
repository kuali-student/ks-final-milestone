/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.tree;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.NaturalLanguageTree;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Kuali Student Team
 */
public abstract class AbstractTreeBuilder implements TreeBuilder {

    private RuleManagementService ruleManagementService;

    protected String NODE_TYPE_SUBRULEHEADER = "subruleHeader";
    protected String NODE_TYPE_SUBRULEELEMENT = "subruleElement";

    public RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

    protected String buildNodeLabel(RuleDefinitionContract rule, PropositionDefinitionContract prop){
        return StringEscapeUtils.escapeHtml(this.getDescription(prop));
    }

    protected String getDescription(PropositionDefinitionContract proposition) {
        if (proposition == null) {
            return StringUtils.EMPTY;
        }

        //Return translated nl if exist.
        if (proposition instanceof PropositionEditor){
            PropositionEditor editor = (PropositionEditor) proposition;
            return editor.getNaturalLanguageForUsage(this.getNaturalLanguageUsageKey());
        }

        //Return the description
        if (proposition.getDescription() != null){
            return proposition.getDescription();
        }

        return StringUtils.EMPTY;
    }

    protected String getPropositionPrefix(RuleEditor rule, PropositionEditor prop){
        if (rule == null){
            return StringUtils.EMPTY;
        }

        //Add the proposition with alpha code in the map if it doesn't already exist.
        if (null == prop.getKey()) {
            prop.setKey((String) rule.getAlpha().next());
        }

        //Build the prefix.
        return "<b>" + prop.getKey() + ".</b> ";
    }

    public String getNaturalLanguageUsageKey(){
        return  KsKrmsConstants.KRMS_NL_PREVIEW;
    }

    public String getHeaderAndElementNodeType(){
        return this.NODE_TYPE_SUBRULEHEADER + " " + this.NODE_TYPE_SUBRULEELEMENT;
    }

    public String getElementNodeType(){
        return this.NODE_TYPE_SUBRULEELEMENT;
    }

}
