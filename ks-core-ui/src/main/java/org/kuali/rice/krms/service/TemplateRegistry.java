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
package org.kuali.rice.krms.service;

import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.TemplateInfo;

/**
 * @author Kuali Student Team
 */
public interface TemplateRegistry {

    /**
     * Returns a TemplateInfo with the bean names for the ui to display for the given type.
     *
     * @param type
     * @return
     */
    TemplateInfo getTemplateForType(String type);

    /**
     * Return the termspec name for the given type.
     *
     * @param type
     * @return
     */
    String getTermSpecNameForType(String type);

    /**
     * Return an operator for the given type to be used as a default proposition parameter value.
     *
     * @param type
     * @return
     */
    String getOperationForType(String type);

    /**
     * Return a constant value for the given type to be used as a default proposition parameter value.
     *
     * @param type
     * @return
     */
    String getValueForType(String type);

    /**
     * Return a ComponentBuilder for the given type.
     *
     * @param type
     * @return
     */
    ComponentBuilder getComponentBuilderForType(String type);

}
