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

package org.kuali.student.brms.repository.util;

import java.io.Serializable;

/**
 * This class is used for testing compiled rule set object references. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class CompiledObject implements Serializable {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    private Object object;
    
    /**
     * Constructor.
     * 
     * @param obj A compiled object
     */
    public CompiledObject( Object obj ) {
        this.object = obj;
    }

    /**
     * Sets a compiled object.
     * 
     * @param obj A compiled object
     */
    public void setObject( Object obj ) {
        this.object = obj;
    }

    /**
     * Gets a compiled object.
     * 
     * @return A compiled object
     */
    public Object getObject() {
        return this.object;
    }
}
