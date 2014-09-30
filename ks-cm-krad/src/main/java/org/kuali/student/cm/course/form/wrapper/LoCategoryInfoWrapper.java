/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.cm.course.form.wrapper;

import org.kuali.student.common.util.DTOWrapper;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import java.util.HashMap;
import java.util.Map;

public class LoCategoryInfoWrapper extends LoCategoryInfo implements DTOWrapper{

	private static final long serialVersionUID = -5261772221177797788L;
	
	private String typeName;
	
	private String catNameAndType;

    protected Map<String,Object> extensionData;

	public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCatNameAndType() {
        return catNameAndType;
    }

    public void setCatNameAndType(String catNameAndType) {
        this.catNameAndType = catNameAndType;
    }

    public boolean isNewDto() {
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
