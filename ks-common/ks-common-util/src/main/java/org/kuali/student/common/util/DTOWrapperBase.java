/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by venkat on 9/12/14
 */
package org.kuali.student.common.util;

import org.kuali.rice.krad.web.bind.RequestAccessible;

import java.util.HashMap;
import java.util.Map;

/**
 * Base implementation for all the wrapper classes in CM.
 *
 * @author Kuali Student Team
 */
public abstract class DTOWrapperBase implements DTOWrapper {

    protected Map<String, Object> extensionData;

    public DTOWrapperBase(){

    }
    /**
     * Method to indicate whether this wrapper's DTO is new or existing DTO at the DB.
     *
     * @return
     */
    @Override
    public boolean isNewDto(){
        return false;
    }

    @Override
    public Map<String, Object> getExtensionData() {
        return extensionData;
    }

    /**
     * Provides a way to add additional data to the wrapper object.
     *
     * @param key
     * @param value
     */
    @Override
    public void putExtensionData(String key,Object value) {
        if (extensionData == null){
            extensionData = new HashMap<>();
        }
        extensionData.put(key, value);
    }
}
