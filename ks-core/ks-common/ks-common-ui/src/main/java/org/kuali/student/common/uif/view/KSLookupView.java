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
package org.kuali.student.common.uif.view;

import org.kuali.rice.krad.lookup.LookupView;

/**
 * <p>
 * Overrides <code>LookupView</code> to support perform redirect if there is a single search result.
 * </p>
 *
 */
public class KSLookupView extends LookupView {

    protected String defaultSingleLookupResultAction;

    public String getDefaultSingleLookupResultAction() {
        return defaultSingleLookupResultAction;
    }

    /**
     * Sets the controller path to perform redirect if there is a single search result.
     *
     * @param defaultSingleLookupResultAction
     */
    public void setDefaultSingleLookupResultAction(String defaultSingleLookupResultAction) {
        this.defaultSingleLookupResultAction = defaultSingleLookupResultAction;
    }


}
