/**
 * Copyright 2005-2012 The Kuali Foundation
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
package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;

import java.util.List;

/**
 * abstract data class for the rule tree {@link}s
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class KSSimplePropositionNode extends RuleEditorTreeNode {

    private static final long serialVersionUID = -629088492909384804L;

    public static final String NODE_TYPE = RuleEditorTreeNode.NODE_TYPE + " simple";

    // needed for inquiry view
    public KSSimplePropositionNode() {
    }

    public KSSimplePropositionNode(PropositionEditor proposition){
        super(proposition);
    }
    
}
