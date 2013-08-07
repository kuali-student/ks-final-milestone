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
package org.kuali.student.core.krms.tree;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.AbstractTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.rice.krms.util.NaturalLanguageHelper;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.List;
import java.util.Map;

/**
 * This is a helper class to build the view tree to be displayed on the manage requisites page on the ui to display
 * a readonly tree of the rule.
 *
 * @author Kuali Student Team
 */
public class KSRuleViewTreeBuilder extends RuleViewTreeBuilder {

    private static final long serialVersionUID = 1L;

    private NaturalLanguageHelper nlHelper;

    protected String getDescription(PropositionEditor proposition) {
        if (proposition == null) {
            return StringUtils.EMPTY;
        }

        //Get the natural language for the usage key.
        Map<String, String> nlMap = proposition.getNaturalLanguage();
        if(!nlMap.containsKey(this.getNaturalLanguageUsageKey())){
            this.getNlHelper().setNaturalLanguageForUsage(proposition, this.getNaturalLanguageUsageKey(), StudentIdentityConstants.KS_NAMESPACE_CD);
        }

        //Return empty string if nl does is null.
        String description = nlMap.get(this.getNaturalLanguageUsageKey());
        if (description==null){
            return StringUtils.EMPTY;
        }

        return description;
    }

    @Override
    public String getNaturalLanguageUsageKey() {
        return KSKRMSServiceConstants.KRMS_NL_PREVIEW;
    }

    public NaturalLanguageHelper getNlHelper() {
        return nlHelper;
    }

    public void setNlHelper(NaturalLanguageHelper nlHelper) {
        this.nlHelper = nlHelper;
    }
}
