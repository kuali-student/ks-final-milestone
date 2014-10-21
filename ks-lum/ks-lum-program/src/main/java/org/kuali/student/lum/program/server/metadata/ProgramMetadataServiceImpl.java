/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.lum.program.server.metadata;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r1.common.assembly.data.ConstraintMetadata;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;
import org.kuali.student.r2.lum.clu.CLUConstants;


/**
 * This class provides metadata lookup for service dto objects.
 * 
 * @author Kuali Student Team
 */
public class ProgramMetadataServiceImpl extends MetadataServiceImpl {

    public ProgramMetadataServiceImpl() {
		super();
	}

	public ProgramMetadataServiceImpl(DictionaryService... dictionaryServices) {
        super(dictionaryServices);
    }


    protected List<ConstraintMetadata> getConstraints(FieldDefinition fd, String type, String state, String nextState,
            String workflowNode, String documentTypeName) {
        List<ConstraintMetadata> constraints = new ArrayList<ConstraintMetadata>();
        ConstraintMetadata constraintMetadata = new ConstraintMetadata();
        //The nextState should not get a defaulted value when we're using Modify Program Proposal functionality.
        String nextStateValue = nextState;
        if (!CLUConstants.PROPOSAL_TYPE_MAJOR_DISCIPLINE_MODIFY.equals(documentTypeName)) {
            nextStateValue = getNextState(state);
        }

        updateConstraintMetadata(constraintMetadata, fd, type, getNonNullState(state), nextStateValue, workflowNode);
        constraints.add(constraintMetadata);

        return constraints;
    }

    private String getNonNullState(String state) {
        return (null == state ? DtoState.DRAFT.toString() : state);
    }

    private String getNextState(String state) {
        if (null == state || DtoState.DRAFT.toString().equalsIgnoreCase(state)) {
            return DtoState.APPROVED.toString();
        } else if (DtoState.APPROVED.toString().equalsIgnoreCase(state)) {
            return DtoState.ACTIVE.toString();
        } else if (DtoState.ACTIVE.toString().equalsIgnoreCase(state)) {
            return DtoState.SUPERSEDED.toString();
        }
        return null;
    }
}
