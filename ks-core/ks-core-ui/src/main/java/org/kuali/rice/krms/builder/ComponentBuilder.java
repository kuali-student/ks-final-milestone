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

import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public interface ComponentBuilder<T extends PropositionEditor> {

    public void initialize(T propositionEditor);

    public List<String> getComponentIds();

    public void resolveTermParameters(T propositionEditor, Map<String, String> termParameters);

    public Map<String, String> buildTermParameters(T propositionEditor);

    public void onSubmit(T propositionEditor);

    public void validate(T propositionEditor);

}
