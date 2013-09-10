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
package org.kuali.student.enrollment.class1.krms.builder;

import org.apache.log4j.Logger;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.enrollment.class1.krms.dto.FEPropositionEditor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class TimeSlotsComponentBuilder implements ComponentBuilder<FEPropositionEditor> {

    private final static Logger LOG = Logger.getLogger(TimeSlotsComponentBuilder.class);

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(FEPropositionEditor propositionEditor, Map<String, String> termParameters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> buildTermParameters(FEPropositionEditor propositionEditor) {
       Map<String, String> termParameters = new HashMap<String, String>();
        //To change body of implemented methods use File | Settings | File Templates.
        return termParameters;
    }

    @Override
    public void onSubmit(FEPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void validate(FEPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
