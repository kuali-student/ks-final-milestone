/**
 * Copyright 2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.infc;

import java.util.List;

/**
 * A common interface pattern for service entities. This interface is
 * applied to entities with attributes.
 *
 * @author tom
 */

public interface HasAttributes {

    /**
     * List of dynamic attributes, each holding a key-value pair that
     * can be configured to hold additional information for an
     * implementing institution.
     *
     * Note: the key may be repeated more than once to simulate a list
     * of values.
     *
     * @name Dynamic Attributes
     */
    public List<? extends Attribute> getAttributes();
}
