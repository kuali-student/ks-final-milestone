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
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.NaturalLanguageHelper;

import java.util.Map;

/**
 * @author Kuali Student Team
 */
public abstract class AbstractTreeBuilder implements TreeBuilder {

    public static String NODE_TYPE_SUBRULEHEADER = "subruleHeader";
    public static String NODE_TYPE_SUBRULEELEMENT = "subruleElement";
    public static String NODE_TYPE_COMPAREELEMENT = "compareElement";

    private NaturalLanguageHelper nlHelper;

    protected String buildNodeLabel(PropositionEditor prop){
        return StringEscapeUtils.escapeHtml(this.getDescription(prop));
    }

    protected String getDescription(PropositionEditor proposition) {
        if (proposition == null) {
            return StringUtils.EMPTY;
        }

        //Get the natural language for the usage key.
        Map<String, String> nlMap = proposition.getNaturalLanguage();
        if(!nlMap.containsKey(this.getNaturalLanguageUsageKey())){
            this.getNlHelper().setNaturalLanguageForUsage(proposition, this.getNaturalLanguageUsageKey());
        }

        //Return empty string if nl does is null.
        String description = nlMap.get(this.getNaturalLanguageUsageKey());
        if (description==null){
            return StringUtils.EMPTY;
        }

        return description;
    }

    protected String getPropositionPrefix(PropositionEditor prop){
        return "<b>" + prop.getKey() + ".</b> ";
    }

    public abstract String getNaturalLanguageUsageKey();

    protected void addNodeType(Node node, String type){
        if ((node.getNodeType()==null)||(node.getNodeType().length()==0)){
            node.setNodeType(type);
        } else {
            node.setNodeType(node.getNodeType() + " " + type);
        }
    }

    public NaturalLanguageHelper getNlHelper() {
        return nlHelper;
    }

    public void setNlHelper(NaturalLanguageHelper nlHelper) {
        this.nlHelper = nlHelper;
    }
}
