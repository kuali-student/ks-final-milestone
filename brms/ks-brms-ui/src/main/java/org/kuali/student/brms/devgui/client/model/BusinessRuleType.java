/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.devgui.client.model;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

public class BusinessRuleType implements ModelObject {
    private static final long serialVersionUID = 123123142351351L;

    private String id;
    private String key;
    private String anchorTypeKey;

    public String getUniqueId() {
        return id;
    }

    /**
     * @return the key
     */
    public final String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public final void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the anchorTypeKey
     */
    public final String getAnchorTypeKey() {
        return anchorTypeKey;
    }

    /**
     * @param anchorTypeKey
     *            the anchorTypeKey to set
     */
    public final void setAnchorTypeKey(String anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }

    /**
     * @return the serialVersionUID
     */
    public static final long getSerialVersionUID() {
        return serialVersionUID;
    }
}