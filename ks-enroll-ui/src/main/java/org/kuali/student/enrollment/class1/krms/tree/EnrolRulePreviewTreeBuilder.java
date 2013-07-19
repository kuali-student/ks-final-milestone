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
package org.kuali.student.enrollment.class1.krms.tree;

import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.student.core.krms.dto.KSPropositionEditor;
import org.kuali.student.core.krms.tree.KSRulePreviewTreeBuilder;
import org.kuali.student.enrollment.class1.krms.dto.CluSetInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.List;

/**
 * Overridden class to add items to be displayed in the view trees that are not converted
 * by the natural language translater on the rule management service.
 *
 * @author Kuali Student Team
 */
public class EnrolRulePreviewTreeBuilder extends KSRulePreviewTreeBuilder {

    private static final long serialVersionUID = 1L;

    @Override
    public List<Object> getListItems(PropositionEditor propositionEditor) {
        if (propositionEditor instanceof EnrolPropositionEditor) {
            CluSetInformation cluSetInfo = ((EnrolPropositionEditor) propositionEditor).getCluSet();

            if (cluSetInfo != null) {
                return cluSetInfo.getCluViewers();
            }
        }
        return null;
    }

}
