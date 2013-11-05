/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.messages.infc;

import org.kuali.student.r2.common.infc.Locale;

/**
 * Information about a message
 * It has no single unique or primary key.
 * Instead it has a composite key composed of:
 * (1) GroupNameKey, (2) MessageKey, (3) Locale
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface Message {
    /**
     * Key identifying the message within the message group.
     * 
     * @name Key
     * @readOnly on updates
     * @required on updates
     */
    public String getMessageKey();

    /**
     * The Locale.
     *
     * @name Locale
     * @required
     */
    Locale getLocale();

    /**
     * Unique identifier for a message group
     *
     * @name Group Name
     * @required
     */
    String getGroupName();

    /**
     * The string representation of the message. Symbols may be included within
     * the message, but the expectation is that the caller is aware of the
     * format of these symbols.
     *
     * @name Value
     * @required
     */
    String getValue();
}
