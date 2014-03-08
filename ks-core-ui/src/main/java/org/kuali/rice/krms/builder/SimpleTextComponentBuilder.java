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
package org.kuali.rice.krms.builder;

import org.kuali.rice.krms.dto.PropositionEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class SimpleTextComponentBuilder implements ComponentBuilder<PropositionEditor>{

    private static final Logger LOG = LoggerFactory.getLogger(SimpleTextComponentBuilder.class);

    private static final String TEXT_KEY = "kuali.term.parameter.type.free.text";

    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(PropositionEditor propositionEditor, Map<String, String> termParameters) {
        String text = termParameters.get(TEXT_KEY);
        propositionEditor.setTermParameter(text);
    }

    @Override
    public Map<String, String> buildTermParameters(PropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        termParameters.put(TEXT_KEY, propositionEditor.getTermParameter());

        LOG.info("{}", termParameters);

        return termParameters;
    }

    @Override
    public void onSubmit(PropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void validate(PropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
