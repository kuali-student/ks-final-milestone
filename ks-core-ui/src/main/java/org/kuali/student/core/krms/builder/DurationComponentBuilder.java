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
package org.kuali.student.core.krms.builder;

import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.core.krms.dto.KSPropositionEditor;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class DurationComponentBuilder implements ComponentBuilder<KSPropositionEditor> {

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(KSPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String durationType = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_TYPE_KEY);
        String duration = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_KEY);
        if (durationType != null) {
        propositionEditor.setDurationType(durationType);
        }
        if (duration != null) {
            propositionEditor.setDuration(Integer.parseInt(duration));
        }

    }

    @Override
    public Map<String, String> buildTermParameters(KSPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_TYPE_KEY, propositionEditor.getDurationType());
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_KEY, propositionEditor.getDuration().toString());

        return termParameters;
    }

    @Override
    public void onSubmit(KSPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void validate(KSPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
