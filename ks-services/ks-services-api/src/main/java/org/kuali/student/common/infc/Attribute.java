/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.infc;

/**
 * Dynamic Attributes info structure.
 *
 * A structure that holds a key value pair as a way of extending the data structure
 * for implementing institutions
 *
 * @author nwright
 */
public interface Attribute {

    /**
     * Name: Id
     * 
     * The internally assigned id to this key/value pair
     */
    public String getId();

    /**
     * Name: Attribute Type Key
     *
     * The key that identifies the name of the dynamic attribute
     */
    public String getKey();

    /**
     * Name: Attribute Value
     * 
     * The value of the dynamic attribute
     */
    public String getValue();
}

