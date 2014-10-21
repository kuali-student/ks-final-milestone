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

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.util.DTOWrapper;
import org.kuali.student.common.util.DisplayWrapper;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper around the {@link CluInstructorInfo} instance for use with KRAD UI components like the StackedCollection
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class CluInstructorInfoWrapper extends CluInstructorInfo implements DisplayWrapper, DTOWrapper {
	
	private static final long serialVersionUID = 7495209564517379554L;

	private String displayName;
	
	private String givenName;
	
	private String principalName;

    protected Map<String, Object> extensionData;
	
	public CluInstructorInfoWrapper() {
		
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	
	public String getPrincipalName() {
		return principalName;
	}

    @Override
    public boolean isUserEntered() {
        if (StringUtils.isNotBlank(displayName)) {
            return true;
        }
        return false;
    }

    @Override
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
