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

package org.kuali.student.common.test.mock;

import java.util.HashMap;
import java.util.Map;

/**
 *  Use this mapper to set method return for mock proxy to return values you
 *  based on predefined arguments.  
 *  
 *  NOTE: Currently only supports single argument methods.
 */
public class MockArgumentMapper {
    private Map<Object,Object> argumentMapper;

    public MockArgumentMapper() {
        super();
        argumentMapper = new HashMap<Object, Object>();
    }
    
    /**
     * 
     * This method returns the return value for the specified arguments
     * 
     * @param arguments
     * @return return value for arguments.
     */
    public Object getReturnValue(Object[] arguments){
        if (arguments!=null ){
            return argumentMapper.get(arguments[0]);
        }
        return null;
    }
    
    /**
     * @return the methodReturnMap
     */
    public Map<Object, Object> getArgumentMapper() {
        return argumentMapper;
    }

    /**
     * @param methodReturnMap the methodReturnMap to set
     */
    public void setArgumentMapper(Map<Object, Object> argumentMapper) {
        this.argumentMapper = argumentMapper;
    }

}
