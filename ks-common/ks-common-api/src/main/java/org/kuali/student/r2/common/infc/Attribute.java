/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.infc;

/**
 * Dynamic Attributes info structure.
 *
 * A structure that holds a key value pair as a way of extending the
 * data structure for implementing institutions.
 *
 * @author nwright
 */

public interface Attribute 
    extends HasId {

    /**
     * The internally assigned id to this key/value pair.
     *
     * @name Id
     * @required on updates
     * @readOnly
     */
    public String getId();

    /**
     * The key that identifies the name of the dynamic attribute.
     * 
     * Note: this key does not have to be unique and could be repeated
     * to simulate a list.
     *
     * @name Attribute Type Key
     * @readOnly on updates
     * @required
     */
    public String getKey();
  
    /**
     * The value of the dynamic attribute.
     *
     * @name Attribute Value
     */
    public String getValue();
}

